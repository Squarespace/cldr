package com.squarespace.cldr.codegen;

import static com.squarespace.cldr.codegen.CodeGenerator.PACKAGE_CLDR_NUMBERS;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PROTECTED;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.lang.model.element.Modifier;

import com.squarespace.cldr.CLDRLocale;
import com.squarespace.cldr.codegen.reader.CurrencyData;
import com.squarespace.cldr.codegen.reader.DataReader;
import com.squarespace.cldr.codegen.reader.NumberData;
import com.squarespace.cldr.numbers.DigitBuffer;
import com.squarespace.cldr.numbers.NumberFormatterParams;
import com.squarespace.cldr.numbers.NumberPattern;
import com.squarespace.cldr.numbers.NumberPattern.Format;
import com.squarespace.cldr.numbers.NumberPatternParser;
import com.squarespace.cldr.parse.FieldPattern.Field;
import com.squarespace.cldr.parse.FieldPattern.Node;
import com.squarespace.cldr.parse.FieldPattern.Text;
import com.squarespace.cldr.parse.WrapperPatternParser;
import com.squarespace.cldr.plurals.PluralCategory;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;


public class NumberCodeGenerator {

  private static final ClassName TYPE_FORMATTER_BASE = ClassName.get(PACKAGE_CLDR_NUMBERS, "NumberFormatterBase");
  private static final WrapperPatternParser WRAPPER_PARSER = new WrapperPatternParser();
  private static final NumberPatternParser NUMBER_PARSER = new NumberPatternParser();

  /**
   * Generate all number formatting classes.
   */
  public Map<LocaleID, ClassName> generate(Path outputDir, DataReader reader) throws IOException {
    Map<LocaleID, ClassName> numberClasses = new LinkedHashMap<>();
    
    for (Map.Entry<LocaleID, NumberData> entry : reader.numbers().entrySet()) {
      NumberData data = entry.getValue();
      LocaleID localeId = entry.getKey();
      
      String className = "_NumberFormatter_" + localeId.safe;
      TypeSpec type = create(data, className);
      CodeGenerator.saveClass(outputDir, PACKAGE_CLDR_NUMBERS, className, type);

      ClassName cls = ClassName.get(PACKAGE_CLDR_NUMBERS, className);
      numberClasses.put(localeId, cls);
    }
    
    // generate currency digit utility class
    String className = "_CurrencyUtil";
    TypeSpec type = createCurrencyUtil(className, reader.currencies());
    CodeGenerator.saveClass(outputDir, PACKAGE_CLDR_NUMBERS, className, type);
    
    return numberClasses;
  }

  public List<String> getCurrencies(DataReader reader) {
    Set<String> currencies = new HashSet<>();
    for (NumberData data : reader.numbers().values()) {
      for (String code : data.currencyDisplayName.keySet()) {
        currencies.add(code);
      }
    }
    return new ArrayList<>(currencies);
  }
  
  /**
   * Creates a number formatter class.
   */
  private TypeSpec create(NumberData data, String className) {
    LocaleID id = data.id();

    // TODO: add descriptive javadoc to type
    TypeSpec.Builder type = TypeSpec.classBuilder(className)
        .superclass(TYPE_FORMATTER_BASE)
        .addModifiers(Modifier.PUBLIC);
    
    // Build the constructor's code block
    String fmt = "super(\n";
    List<Object> args = new ArrayList<>();

    fmt += "new $T($S, $S, $S, $S),\n";
    args.addAll(Arrays.asList(CLDRLocale.class, id.language, id.script, id.territory, id.variant));
    fmt += "new _Params(),\n";

    fmt += "// decimal standard\n";
    fmt += "patterns($S, $S),\n";
    args.addAll(getPatterns(data.decimalFormatStandard));
    
    fmt += "// percent standard\n";
    fmt += "patterns($S, $S),\n";
    args.addAll(getPatterns(data.percentFormatStandard));

    fmt += "// currency standard\n";
    fmt += "patterns($S, $S),\n";
    args.addAll(getPatterns(data.currencyFormatStandard));
    
    fmt += "// currency accounting\n";
    fmt += "patterns($S, $S)\n";
    args.addAll(getPatterns(data.currencyFormatAccounting));

    fmt += ")";

    // Build the constructor.
    MethodSpec.Builder code = MethodSpec.constructorBuilder()
        .addModifiers(PUBLIC)
        .addStatement(fmt, args.toArray());
    type.addMethod(code.build());
    
    // Create a private instance of the NumberFormatterParams type
    addParams(type, id, data);

    // Create fields for the basic formats.
    type.addField(buildPatternField("DECIMAL_STANDARD", data.decimalFormatStandard));
    type.addField(buildPatternField("PERCENT_STANDARD", data.percentFormatStandard));
    type.addField(buildPatternField("CURRENCY_STANDARD", data.currencyFormatStandard));
    type.addField(buildPatternField("CURRENCY_ACCOUNTING", data.currencyFormatAccounting));

    // Create default patterns for compact forms.
    List<NumberPattern> decimalCompact = makeCompactPatterns(data.decimalFormatStandard);
    List<NumberPattern> currencyCompact = makeCompactPatterns(data.currencyFormatStandard);
    type.addField(buildPatternField("DECIMAL_STANDARD_COMPACT", decimalCompact));
    type.addField(buildPatternField("CURRENCY_STANDARD_COMPACT", currencyCompact));
    
    // Create methods for pluralized patterns, which require some logic to choose.
    addPluralPatterns(type, "DECIMAL_SHORT", data.decimalFormatShort, false, data);
    addPluralPatterns(type, "DECIMAL_LONG", data.decimalFormatLong, false, data);
    addPluralPatterns(type, "CURRENCY_SHORT", data.currencyFormatShort, true, data);
    
    addCurrencyInfo(type, data);
    addUnitWrappers(type, data);
    
    return type.build();
  }

  /**
   * Create class to hold global currency information.
   */
  public TypeSpec createCurrencyUtil(String className, Map<String, CurrencyData> currencies) {
    TypeSpec.Builder type = TypeSpec.classBuilder(className)
        .addModifiers(Modifier.PUBLIC);
    
    CurrencyData defaultData = currencies.get("DEFAULT");
    currencies.remove("DEFAULT");
    
    MethodSpec.Builder code = MethodSpec.methodBuilder("getCurrencyDigits")
        .addModifiers(PUBLIC, STATIC)
        .addParameter(String.class, "code")
        .returns(int.class);
    
    code.beginControlFlow("switch (code)");
    for (Map.Entry<String, CurrencyData> entry : currencies.entrySet()) {
      CurrencyData data = entry.getValue();
      code.addStatement("case $S: return $L", entry.getKey(), data.digits);
    }
    code.addStatement("default: return $L", defaultData.digits);
    code.endControlFlow();
    
    type.addMethod(code.build());
    return type.build();
  }
  
  /**
   * Return a list of the string patterns embedded in the NumberPattern instances.
   */
  private List<String> getPatterns(List<NumberPattern> patterns) {
    return patterns.stream()
        .map(p -> p.pattern())
        .collect(Collectors.toList());
  }
  
  /**
   * Construct's the locale's number formatter params.
   */
  private void addParams(TypeSpec.Builder parent, LocaleID id, NumberData data) {
    MethodSpec.Builder code = MethodSpec.constructorBuilder();
    code.addStatement("this.decimal = $S", data.decimal);
    code.addStatement("this.group = $S", data.group);
    code.addStatement("this.percent = $S", data.percent);
    code.addStatement("this.minus = $S", data.minus);
    code.addStatement("this.plus = $S", data.plus);
    code.addStatement("this.exponential = $S", data.exponential);
    code.addStatement("this.superscriptingExponent = $S", data.superscriptingExponent);
    code.addStatement("this.perMille = $S", data.perMille);
    code.addStatement("this.infinity = $S", data.infinity);
    code.addStatement("this.nan = $S", data.nan);
    code.addStatement("this.currencyDecimal = $S", data.currencyDecimal);
    code.addStatement("this.currencyGroup = $S", data.currencyGroup);

    TypeSpec.Builder params = TypeSpec.classBuilder("_Params")
        .superclass(NumberFormatterParams.class)
        .addModifiers(PRIVATE, STATIC)
        .addMethod(code.build());

    parent.addType(params.build());
  }

  /**
   * Generates a field containing 2 number patterns [positive, negative].
   */
  private FieldSpec buildPatternField(String name, List<NumberPattern> patterns) {
    return FieldSpec.builder(NumberPattern[].class, name, PUBLIC, STATIC, FINAL)
        .initializer("patterns($S, $S)", patterns.get(0).pattern(), patterns.get(1).pattern())
        .build();
  }
  
  /**
   * Group plural patterns by the divisor.
   */
  private void addPluralPatterns(
      TypeSpec.Builder type, String name, Map<String, List<NumberPattern>> patterns, boolean currency,
      NumberData data) {
    
    Map<String, PluralPattern> patternMap = new LinkedHashMap<>();
    for (Map.Entry<String, List<NumberPattern>> entry : patterns.entrySet()) {
      String key = entry.getKey();
      String[] parts = key.split("-");

      PluralPattern pattern = patternMap.get(parts[0]);
      if (pattern == null) {
        pattern = new PluralPattern(new BigDecimal(parts[0]));
        patternMap.put(parts[0], pattern);
      }

      PluralCategory category = PluralCategory.fromString(parts[2]);
      pattern.add(category, entry.getValue());
    }
    pluralPatternField(type, name, patternMap, currency, data);
  }
  
  /**
   * Create the fields used to format compact plural patterns.
   */
  private void pluralPatternField(
      TypeSpec.Builder type, String name, Map<String, PluralPattern> patternMap, boolean currency,
      NumberData data) {

    List<FieldSpec> patternFields = new ArrayList<>();
    List<FieldSpec> divisorFields = new ArrayList<>();

    for (Map.Entry<String, PluralPattern> entry : patternMap.entrySet()) {
      String magnitude = entry.getKey();
      String shortMagnitude = MAGNITUDE_MAP.get(magnitude);
      PluralPattern pattern = entry.getValue();

      // Compute the divisor to scale down the magnitude.
      int minIntDigits = pattern.format.minimumIntegerDigits();
      int divisor = (int) Math.pow(10, (minIntDigits - 1));
      BigDecimal n = new BigDecimal(magnitude);
      n = n.divide(new BigDecimal(divisor));

      // Create the pattern fields for each magnitude and plural category.
      boolean defaulted = false;
      for (Map.Entry<PluralCategory, List<NumberPattern>> plural : pattern.map.entrySet()) {
        PluralCategory category = plural.getKey();
        String fieldName = String.format("%s_%s_%s", name, shortMagnitude, category);
        List<NumberPattern> patterns = plural.getValue();

        // For any pattern that is explicitly "0" we substitute the standard format, but
        // modified to be compact by default.
        String positive = patterns.get(0).pattern();
        if (positive.equals("0")) {
          List<NumberPattern> standard = currency ? data.currencyFormatStandard : data.decimalFormatStandard;
          patterns = makeCompactPatterns(standard);
          defaulted = true;
        }

        FieldSpec field = FieldSpec.builder(NumberPattern[].class, fieldName, PRIVATE, FINAL)
            .initializer("patterns($S, $S)", patterns.get(0).pattern(), patterns.get(1).pattern())
            .build();
        patternFields.add(field);
      }

      // Add the field we'll use to scale the number by a power of ten for compact forms.
      String fieldName = String.format("%s_%s_POWER", name, shortMagnitude);
      int scale = n.precision() - 1;
      if (defaulted) {
        scale = 1;
      }
      FieldSpec field = FieldSpec.builder(int.class, fieldName, PRIVATE, FINAL)
          .initializer("$L", scale)
          .build();
      divisorFields.add(field);
    }
    
    // Add the fields to the parent type.
    for (FieldSpec field : patternFields) {
      type.addField(field);
    }
    for (FieldSpec field : divisorFields) {
      type.addField(field);
    }
    
    pluralDivisorMethod(type, name, patternMap.keySet());
    pluralPatternMethod(type, name, patternMap, currency);
  }
  
  /**
   * Converts a standard decimal or currency pattern into one usable for compact forms
   * for numbers < 1000. We parse the pattern, modify it, then render it.
   */
  private List<NumberPattern> makeCompactPatterns(List<NumberPattern> standard) {
    NumberPattern pos = NUMBER_PARSER.parse(standard.get(0).pattern());
    NumberPattern neg = NUMBER_PARSER.parse(standard.get(1).pattern());
    
    pos.format().setMaximumFractionDigits(0);
    pos.format().setMinimumFractionDigits(0);
 
    neg.format().setMaximumFractionDigits(0);
    neg.format().setMinimumFractionDigits(0);
    
    NumberPattern positive = NUMBER_PARSER.parse(pos.render());
    NumberPattern negative = NUMBER_PARSER.parse(neg.render());

    return Arrays.asList(positive, negative);
  }
  
  /**
   * Creates the method that returns the correct divisor for a given pluralized pattern.
   */
  private void pluralDivisorMethod(TypeSpec.Builder type, String name, Set<String> magnitudes) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("getPowerOfTen_" + name)
        .addModifiers(PROTECTED)
        .addParameter(int.class, "digits")
        .returns(int.class);

    String magnitude = null;
    int maxDigits = 0;
    for (String key : magnitudes) {
      maxDigits = Math.max(maxDigits, key.length());
      magnitude = key;
    }
    
    method.beginControlFlow("if (digits < 4)");
    method.addStatement("return 0");
    method.endControlFlow();

    method.beginControlFlow("switch (digits)");
    for (String key : magnitudes) {
      String shortMagnitude = MAGNITUDE_MAP.get(key);
      String fieldName = String.format("%s_%s_POWER", name, shortMagnitude);
      int digits = key.length();
      if (digits < maxDigits) {
        method.addStatement("case $L: return $L", digits, fieldName);
      }
    }
    
    String shortMagnitude = MAGNITUDE_MAP.get(magnitude);
    String fieldName = String.format("%s_%s_POWER", name, shortMagnitude);
    method.addStatement("case $L:\ndefault: return $L", maxDigits, fieldName);
    method.endControlFlow();

    type.addMethod(method.build());
  }
  
  /**
   * Creates the method that returns the correct pluralized pattern.
   */
  private void pluralPatternMethod(
      TypeSpec.Builder type, String name, Map<String, PluralPattern> patternMap, boolean currency) {
    
    MethodSpec.Builder method = MethodSpec.methodBuilder("getPattern_" + name)
        .addModifiers(PROTECTED)
        .addParameter(int.class, "digits")
        .addParameter(PluralCategory.class, "category")
        .returns(NumberPattern[].class);
    
    method.beginControlFlow("if (digits < 4 || category == null)");
    method.addStatement("return $L", currency ? "CURRENCY_STANDARD_COMPACT" : "DECIMAL_STANDARD_COMPACT");
    method.endControlFlow();

    int maxDigits = 0;
    for (String key : patternMap.keySet()) {
      maxDigits = Math.max(maxDigits, key.length());
    }
    
    method.beginControlFlow("switch (digits)");
    for (Map.Entry<String, PluralPattern> entry : patternMap.entrySet()) {
      String magnitude = entry.getKey();
      String shortMagnitude = MAGNITUDE_MAP.get(magnitude);
      PluralPattern pattern = entry.getValue();

      int digits = magnitude.length();
      if (digits < maxDigits) {
        method.beginControlFlow("case $L:", digits);
      } else {
        method.beginControlFlow("case $L: default:", digits);
      }

      method.beginControlFlow("switch (category)");
      
      // Create the pattern fields for each magnitude and plural category.
      for (Map.Entry<PluralCategory, List<NumberPattern>> plural : pattern.map.entrySet()) {
        PluralCategory category = plural.getKey();
        String fieldName = String.format("%s_%s_%s", name, shortMagnitude, category);
        if (category != PluralCategory.OTHER) {
          method.addStatement("case $L: return $L", category, fieldName);
        }
      }

      String fieldName = String.format("%s_%s_OTHER", name, shortMagnitude);
      method.addStatement("case $L:\ndefault: return $L", PluralCategory.OTHER, fieldName);

      method.endControlFlow();
      method.endControlFlow();
    }
    
    method.endControlFlow();

    type.addMethod(method.build());
  }
  
  /**
   * Adds methods to return information about a currency.
   */
  private void addCurrencyInfo(TypeSpec.Builder type, NumberData data) {
    addCurrencyInfoMethod(type, "getCurrencySymbol", data.currencySymbols);
    addCurrencyInfoMethod(type, "getCurrencyDisplayName", data.currencyDisplayName);
    
    MethodSpec.Builder method = MethodSpec.methodBuilder("getCurrencyPluralName")
        .addModifiers(PROTECTED)
        .addParameter(String.class, "code")
        .addParameter(PluralCategory.class, "category")
        .returns(String.class);

    method.beginControlFlow("switch (code)");
    for (Map.Entry<String, Map<String, String>> currencyMap : data.currencyDisplayNames.entrySet()) {
      String code = currencyMap.getKey();
      Map<String, String> mapping = currencyMap.getValue();
      if (mapping.isEmpty()) {
        continue;
      }
      
      method.beginControlFlow("case $S:", code);
      method.beginControlFlow("switch (category)");
      for (Map.Entry<String, String> entry : mapping.entrySet()) {
        String[] parts = entry.getKey().split("-");
        PluralCategory category = PluralCategory.fromString(parts[2]);
        if (category == PluralCategory.OTHER) {
          method.addStatement("case $L:\ndefault: return $S", category, entry.getValue());
        } else {
          method.addStatement("case $L: return $S", category, entry.getValue());
        }
      }
      method.endControlFlow();
      method.endControlFlow();
    }
    
    method.addStatement("default: return $S", "");
    method.endControlFlow();
    
    type.addMethod(method.build());
  }
  
  /**
   * Adds a method to return the symbol or display name for a currency.
   */
  private void addCurrencyInfoMethod(TypeSpec.Builder type, String name, Map<String, String> mapping) {
    MethodSpec.Builder method = MethodSpec.methodBuilder(name)
        .addModifiers(PUBLIC)
        .addParameter(String.class, "code")
        .returns(String.class);

    method.beginControlFlow("switch (code)");
    for (Map.Entry<String, String> entry : mapping.entrySet()) {
      method.addStatement("case $S: return $S", entry.getKey(), entry.getValue());
    }
    method.addStatement("default: return $S", "");
    method.endControlFlow();
    type.addMethod(method.build());
  }
  
  /**
   * Adds wrappers of the form "{0} {1}" for wrapping a formatted number with a
   * prefix or suffix.
   */
  private void addUnitWrappers(TypeSpec.Builder type, NumberData data) {
    // TODO: optimize this by grouping on distinct patterns to eliminate redundancy.
    MethodSpec.Builder method = MethodSpec.methodBuilder("wrapUnits")
        .addModifiers(PROTECTED)
        .addParameter(PluralCategory.class, "category")
        .addParameter(DigitBuffer.class, "dbuf")
        .addParameter(String.class, "unit")
        .addParameter(StringBuilder.class, "dest");
    
    method.beginControlFlow("switch (category)");
    for (Map.Entry<String, String> entry : data.currencyUnitPattern.entrySet()) {
      String[] parts = entry.getKey().split("-");
      PluralCategory category = PluralCategory.fromString(parts[2]);
      String pattern = entry.getValue();
      List<Node> nodes = WRAPPER_PARSER.parseWrapper(pattern);
      if (category != PluralCategory.OTHER) {
        method.beginControlFlow("case $L:", category);
      } else {
        method.beginControlFlow("case $L:\ndefault:", category);
      }
      for (Node node : nodes) {
        if (node instanceof Field) {
          Field field = (Field)node;
          if (field.ch() == '0') {
            method.addStatement("dbuf.appendTo(dest)");
          } else {
            method.addStatement("dest.append(unit)");
          }
        } else if (node instanceof Text) {
          method.addStatement("dest.append($S)", ((Text)node).text());
        }
      }
      method.addStatement("break");
      method.endControlFlow();
    }
    method.endControlFlow();
    
    type.addMethod(method.build());
  }
  
  // Maps a number of a given magnitude (10000, 1000000) to its short version (10K, 1M)
  private static final Map<String, String> MAGNITUDE_MAP = new HashMap<>();
  
  static {
    MAGNITUDE_MAP.put("1000", "1K");
    MAGNITUDE_MAP.put("10000", "10K");
    MAGNITUDE_MAP.put("100000", "100K");
    MAGNITUDE_MAP.put("1000000", "1M");
    MAGNITUDE_MAP.put("10000000", "10M");
    MAGNITUDE_MAP.put("100000000", "100M");
    MAGNITUDE_MAP.put("1000000000", "1B");
    MAGNITUDE_MAP.put("10000000000", "10B");
    MAGNITUDE_MAP.put("100000000000", "100B");
    MAGNITUDE_MAP.put("1000000000000", "1T");
    MAGNITUDE_MAP.put("10000000000000", "10T");
    MAGNITUDE_MAP.put("100000000000000", "100T");
  }

  /**
   * Temporary class to group plural patterns by magnitude and category.
   */
  static class PluralPattern {
    
    private BigDecimal factor;

    private Map<PluralCategory, List<NumberPattern>> map = new LinkedHashMap<>();

    // Base format common to all plural forms for this pattern.
    private Format format;
    
    public PluralPattern(BigDecimal factor) {
      this.factor = factor;
    }
    
    public BigDecimal factor() {
      return factor;
    }
    
    public void add(PluralCategory category, List<NumberPattern> patterns) {
      for (NumberPattern p : patterns) {
        if (format == null) {
          format = p.format();
        } else if (!p.format().equals(format)) {
          throw new RuntimeException("Fatal error: found plural number patterns that differ in base format:\n"
              + format + "\n" + p.format());
        }
      }
      map.put(category, patterns);
    }
    
  }

}
