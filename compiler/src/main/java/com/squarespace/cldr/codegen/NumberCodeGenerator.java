package com.squarespace.cldr.codegen;

import static com.squarespace.cldr.codegen.Types.CLDR_LOCALE_IF;
import static com.squarespace.cldr.codegen.Types.MAP_UNIT_LIST_UNITPATTERN;
import static com.squarespace.cldr.codegen.Types.NUMBER_FORMATTER_BASE;
import static com.squarespace.cldr.codegen.Types.PACKAGE_CLDR_NUMBERS;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PROTECTED;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.lang.model.element.Modifier;

import org.apache.commons.lang3.StringUtils;

import com.squarespace.cldr.codegen.reader.CurrencyData;
import com.squarespace.cldr.codegen.reader.DataReader;
import com.squarespace.cldr.codegen.reader.NumberData;
import com.squarespace.cldr.codegen.reader.UnitData;
import com.squarespace.cldr.codegen.reader.UnitData.UnitPatterns;
import com.squarespace.cldr.numbers.DigitBuffer;
import com.squarespace.cldr.numbers.NumberFormatterParams;
import com.squarespace.cldr.numbers.NumberPattern;
import com.squarespace.cldr.numbers.NumberPatternParser;
import com.squarespace.cldr.parse.FieldPattern.Field;
import com.squarespace.cldr.parse.FieldPattern.Node;
import com.squarespace.cldr.parse.FieldPattern.Text;
import com.squarespace.cldr.parse.WrapperPatternParser;
import com.squarespace.cldr.plurals.PluralCategory;
import com.squarespace.cldr.units.Unit;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;


/**
 * Generates code to format numbers and currencies using CLDR data.
 */
public class NumberCodeGenerator {

  private static final WrapperPatternParser WRAPPER_PARSER = new WrapperPatternParser();
  private static final NumberPatternParser NUMBER_PARSER = new NumberPatternParser();

  public static void main(String[] args) throws IOException {
    Path root = Paths.get("/Users/phensley/dev/squarespace-cldr");
    new NumberCodeGenerator().generate(root.resolve("runtime/src/generated/java"), DataReader.get());
  }
  /**
   * Generate all number formatting classes.
   */
  public Map<LocaleID, ClassName> generate(Path outputDir, DataReader reader) throws IOException {
    Map<LocaleID, ClassName> numberClasses = new TreeMap<>();
    Map<LocaleID, UnitData> unitMap = reader.units();
    for (Map.Entry<LocaleID, NumberData> entry : reader.numbers().entrySet()) {
      
      NumberData data = entry.getValue();
      LocaleID localeId = entry.getKey();
      
      String className = "_NumberFormatter_" + localeId.safe;
      UnitData unitData = unitMap.get(localeId);
      
      TypeSpec type = create(data, unitData, className);
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
  private TypeSpec create(NumberData data, UnitData unitData, String className) {
    LocaleID id = data.id();

    // TODO: add descriptive javadoc to type
    TypeSpec.Builder type = TypeSpec.classBuilder(className)
        .superclass(NUMBER_FORMATTER_BASE)
        .addModifiers(Modifier.PUBLIC);
    
    // Build the constructor's code block
    String fmt = "super(\n";
    List<Object> args = new ArrayList<>();

    fmt += "$T.$L,\n";
    args.add(CLDR_LOCALE_IF);
    args.add(id.safe);

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
    fmt += "patterns($S, $S),\n";
    args.addAll(getPatterns(data.currencyFormatAccounting));

    fmt += "// units standard\n";
    fmt += "patterns($S, $S)\n";
    List<NumberPattern> unitPatterns = data.decimalFormatStandard.stream()
        .map(p -> {
          p = NUMBER_PARSER.parse(p.render());
          p.format().setMaximumFractionDigits(1);
          p.format().setMinimumFractionDigits(0);
          return p;
        }).collect(Collectors.toList());
    args.addAll(getPatterns(unitPatterns));
    
    fmt += ")";

    // Build the constructor.
    MethodSpec.Builder code = MethodSpec.constructorBuilder()
        .addModifiers(PUBLIC)
        .addStatement(fmt, args.toArray());
    type.addMethod(code.build());
    
    // Create a private instance of the NumberFormatterParams type
    addParams(type, id, data);

    addCompactPattern(type, "DECIMAL_SHORT", data.decimalFormatShort, false, data);
    addCompactPattern(type, "DECIMAL_LONG", data.decimalFormatLong, false, data);
    addCompactPattern(type, "CURRENCY_SHORT", data.currencyFormatShort, true, data);
    
    addUnitPattern(type, "UNITS_LONG", unitData.long_);
    addUnitPattern(type, "UNITS_NARROW", unitData.narrow);
    addUnitPattern(type, "UNITS_SHORT", unitData.short_);
    
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
        .addParameter(Types.CLDR_CURRENCY_ENUM, "code")
        .returns(int.class);
    
    code.beginControlFlow("switch (code)");
    for (Map.Entry<String, CurrencyData> entry : currencies.entrySet()) {
      CurrencyData data = entry.getValue();
      code.addStatement("case $L: return $L", entry.getKey(), data.digits);
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
        .map(p -> p.render())
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

  private void addCompactPattern(
    TypeSpec.Builder type, String name, Map<String, List<NumberPattern>> patternMap, boolean currency,
    NumberData data) {

    List<NumberPattern> stdPattern = currency ? data.currencyFormatStandard : data.decimalFormatStandard;
    String[] standard = new String[] {
        stdPattern.get(0).render(),
        stdPattern.get(1).render()
    };
    
    // Group patterns by plural category. This associates a list with each category.
    Map<PluralCategory, List<String[]>> grouped = new LinkedHashMap<>();
    List<Integer> divisors = new ArrayList<>(Arrays.asList(0, 0, 0));
    List<String> magnitudes = new ArrayList<>(Arrays.asList("1", "10", "100"));
    
    // Accumulate the patterns..
    for (Map.Entry<String, List<NumberPattern>> entry : patternMap.entrySet()) {
      String key = entry.getKey();
      String[] parts = key.split("-");
      
      List<NumberPattern> patterns = entry.getValue();
      PluralCategory category = PluralCategory.fromString(parts[2]);
      
      List<String[]> group = grouped.get(category);
      if (group == null) {
        group = new ArrayList<>(Arrays.asList(standard, standard, standard));
        grouped.put(category, group);
      }
      
      // Append the (positive, negative) pattern pair.
      String positive = patterns.get(0).render();
      String negative = patterns.get(1).render();
      boolean defaulted = false;
      if (positive.equals("0")) {
        group.add(standard);
        defaulted = true;
      } else {
        group.add(new String[] { positive, negative });
      }
      
      // Compute the divisor to scale down the number being formatted.
      if (category == PluralCategory.OTHER) {
        String shortName = MAGNITUDE_MAP.get(parts[0]);
        magnitudes.add(shortName);
        if (defaulted) {
          divisors.add(0);
        } else {
          int minIntDigits = patterns.get(0).format().minimumIntegerDigits();
          int divisor = (int) Math.pow(10, (minIntDigits - 1));
          BigDecimal n = new BigDecimal(parts[0]);
          n = n.divide(new BigDecimal(divisor));     
          divisors.add(n.precision() - 1);
        }
      }
    }

    // Add a divisors array.
    String fieldName = String.format("%s_DIVISORS", name);
    FieldSpec.Builder field = FieldSpec.builder(int[].class, fieldName, STATIC, FINAL, PROTECTED);
    CodeBlock.Builder code = CodeBlock.builder();
    code.add("new int[] { $L }", StringUtils.join(divisors, ","));
    field.initializer(code.build());
    type.addField(field.build());

    // Add method to access divisors array.
    MethodSpec.Builder method = MethodSpec.methodBuilder("getDivisor_" + name)
        .addModifiers(PROTECTED)
        .addParameter(int.class, "digits")
        .returns(int.class);
    method.addStatement("int index = $T.min(14, $T.max(0, digits - 1))", Math.class, Math.class);
    method.addStatement("return $L_DIVISORS[index]", name);
    type.addMethod(method.build());
    
    // Add a field for each non-empty set of patterns.
    for (Map.Entry<PluralCategory, List<String[]>> entry : grouped.entrySet()) {
      PluralCategory category = entry.getKey();
      fieldName = String.format("%s_%s", name, category);
      field = FieldSpec.builder(NumberPattern[][].class, fieldName, STATIC, FINAL, PROTECTED);

      code = CodeBlock.builder();
      code.beginControlFlow("new $T", NumberPattern[][].class);
      
      List<String[]> patterns = entry.getValue();
      int size = patterns.size();
      for (int i = 0; i < size; i++) {
        if (i > 0) {
          code.add(",\n");
        }
        String[] pair = patterns.get(i);
        String comment = String.format("%4s", magnitudes.get(i));
        code.add("/* $L */  patterns($S, $S)", comment, pair[0], pair[1]);
      }
      code.endControlFlow();
      field.initializer(code.build());
      
      type.addField(field.build());
    }
    
    // Create a method to retrieve the correct pattern based on the number
    // of digits and the plural category.
    method = MethodSpec.methodBuilder("getPattern_" + name)
        .addModifiers(PROTECTED)
        .addParameter(int.class, "digits")
        .addParameter(PluralCategory.class, "category")
        .returns(NumberPattern[].class);

    method.beginControlFlow("if (category == null)");
    method.addStatement("category = $T.OTHER", PluralCategory.class);
    method.endControlFlow();
    
    method.addStatement("int index = $T.min(14, $T.max(0, digits - 1))", Math.class, Math.class);
    method.beginControlFlow("switch (category)");
    
    for (PluralCategory category : grouped.keySet()) {
      fieldName = String.format("%s_%s", name, category);
      if (category == PluralCategory.OTHER) {
        method.addStatement("case $L:\ndefault: return $L[index]", category, fieldName);
      } else {
        method.addStatement("case $L: return $L[index]", category, fieldName);
      }
    }

    method.endControlFlow();
    type.addMethod(method.build());
  }

  private void addUnitPattern(TypeSpec.Builder type, String name, UnitPatterns unitPatterns) {
    Set<PluralCategory> categories = new LinkedHashSet<>();
    for (PluralCategory category : PluralCategory.values()) {
      boolean found = false;

      // Add fields holding the units for this plural category.
      String fieldName = String.format("%s_%s", name, category.name());
      FieldSpec.Builder field = FieldSpec.builder(MAP_UNIT_LIST_UNITPATTERN, fieldName, STATIC, FINAL, PROTECTED);
      CodeBlock.Builder code = CodeBlock.builder();
      code.add("new $T<$T, $T>($T.class) {{\n", Types.ENUM_MAP, Types.UNIT, Types.LIST_UNITPATTERN, Types.UNIT);

      for (Unit unit : Unit.values()) {
        String pattern = unitPatterns.unitPatterns.get(unit).patterns.get(category);
        if (pattern == null) {
          break;
        }
        
        found = true;
        categories.add(category);
        code.addStatement("  put($T.$L, unitPattern($S))", Types.UNIT, unit, pattern);
      }
      
      if (!found) {
        continue;
      }
      
      code.add("}}");
      field.initializer(code.build());
      type.addField(field.build());
    }
    
    // Add unit format accessor methods
    MethodSpec.Builder method = MethodSpec.methodBuilder("getPattern_" + name)
        .addModifiers(PROTECTED)
        .addParameter(Types.UNIT, "unit")
        .addParameter(PluralCategory.class, "category")
        .returns(Types.LIST_UNITPATTERN);
    
    method.beginControlFlow("if (category == null)");
    method.addStatement("category = $T.OTHER", PluralCategory.class);
    method.endControlFlow();
    
    method.beginControlFlow("switch (category)");
    for (PluralCategory category : categories) {
      if (category == PluralCategory.OTHER) {
        method.addStatement("case $L: default: return $L_$L.get(unit)", category, name, category);
      } else {
        method.addStatement("case $L: return $L_$L.get(unit)", category, name, category);
      }
    }
    
    method.endControlFlow();
    type.addMethod(method.build());
  }
  
  /**
   * Adds methods to return information about a currency.
   */
  private void addCurrencyInfo(TypeSpec.Builder type, NumberData data) {
    addCurrencyInfoMethod(type, "getCurrencySymbol", data.currencySymbols);
    addCurrencyInfoMethod(type, "getNarrowCurrencySymbol", data.narrowCurrencySymbols);
    addCurrencyInfoMethod(type, "getCurrencyDisplayName", data.currencyDisplayName);

    MethodSpec.Builder method = MethodSpec.methodBuilder("getCurrencyPluralName")
        .addModifiers(PROTECTED)
        .addParameter(Types.CLDR_CURRENCY_ENUM, "code")
        .addParameter(PluralCategory.class, "category")
        .returns(String.class);

    method.beginControlFlow("switch (code)");
    for (Map.Entry<String, Map<String, String>> currencyMap : data.currencyDisplayNames.entrySet()) {
      String code = currencyMap.getKey();
      Map<String, String> mapping = currencyMap.getValue();
      if (mapping.isEmpty()) {
        continue;
      }
      
      method.beginControlFlow("case $L:", code);
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
    
    method = MethodSpec.methodBuilder("getCurrencyDigits")
        .addModifiers(PUBLIC)
        .addParameter(Types.CLDR_CURRENCY_ENUM, "code")
        .returns(int.class);
    method.addStatement("return _CurrencyUtil.getCurrencyDigits(code)");
    type.addMethod(method.build());
  }
  
  /**
   * Adds a method to return the symbol or display name for a currency.
   */
  private void addCurrencyInfoMethod(TypeSpec.Builder type, String name, Map<String, String> mapping) {
    MethodSpec.Builder method = MethodSpec.methodBuilder(name)
        .addModifiers(PUBLIC)
        .addParameter(Types.CLDR_CURRENCY_ENUM, "code")
        .returns(String.class);

    method.beginControlFlow("if (code == null)");
    method.addStatement("return $S", "");
    method.endControlFlow();
    
    method.beginControlFlow("switch (code)");
    for (Map.Entry<String, String> entry : mapping.entrySet()) {
      String key = entry.getKey();
      String val = entry.getValue();
      if (!key.equals(val)) {
        method.addStatement("case $L: return $S", key, val);
      }
    }
    
    method.addStatement("default: return code.name()");
    method.endControlFlow();
    type.addMethod(method.build());
  }
  
  /**
   * Adds wrappers of the form "{0} {1}" for wrapping a formatted number with a
   * prefix or suffix.
   */
  private void addUnitWrappers(TypeSpec.Builder type, NumberData data) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("wrapUnits")
        .addModifiers(PROTECTED)
        .addParameter(PluralCategory.class, "category")
        .addParameter(DigitBuffer.class, "dbuf")
        .addParameter(String.class, "unit")
        .addParameter(StringBuilder.class, "dest");
    
    
    List<String> patterns = new ArrayList<>(new HashSet<>(data.currencyUnitPattern.values()));
    if (patterns.size() == 1) {
      // Short cut if all patterns are the same.
      List<Node> nodes = WRAPPER_PARSER.parseWrapper(patterns.get(0));
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
    } else {
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
    }
    
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

}
