package com.squarespace.cldr.codegen;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.io.CharSink;
import com.google.common.io.Files;
import com.squarespace.cldr.CLDRLocale;
import com.squarespace.cldr.codegen.reader.DataReader;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;


/**
 * Generates the CLDR classes that encapsulate the core data.
 *
 * TODO: embed version/build information into generated classes
 */
public class CodeGenerator {

  public static final String PACKAGE_CLDR = "com.squarespace.cldr";
  public static final String PACKAGE_CLDR_DATES = "com.squarespace.cldr.dates";
  public static final String PACKAGE_CLDR_PLURALS = "com.squarespace.cldr.plurals";
  public static final String PACKAGE_CLDR_NUMBERS = "com.squarespace.cldr.numbers";

  private static final TypeName CLDR_TYPE = ClassName.get(PACKAGE_CLDR, "CLDR");
  private static final ClassName CLDRBASE_TYPE = ClassName.get(PACKAGE_CLDR, "CLDRBase");
  private static final TypeName LOCALE_LIST_TYPE = ParameterizedTypeName.get(List.class, CLDRLocale.class);
  private static final TypeName CURRENCY_LIST_TYPE = ParameterizedTypeName.get(List.class, String.class);
  
  private static final String[] EMPTY = new String[] { };

  public static void main(String[] args) throws IOException {
    Path root = Paths.get("/Users/phensley/dev/squarespace-cldr");
    generate(root.resolve("runtime/src/generated/java"));
  }

  /**
   * Loads all CLDR data and invokes the code generators for each data type. Output
   * is a series of Java classes under the outputDir.
   */
  public static void generate(Path outputDir) throws IOException {
    DataReader reader = DataReader.get();
    DateTimeCodeGenerator datetimeGenerator = new DateTimeCodeGenerator();
    Map<LocaleID, ClassName> dateClasses = datetimeGenerator.generate(outputDir, reader);

    PluralCodeGenerator pluralGenerator = new PluralCodeGenerator();
    pluralGenerator.generate(outputDir, reader);

    NumberCodeGenerator numberGenerator = new NumberCodeGenerator();
    Map<LocaleID, ClassName> numberClasses = numberGenerator.generate(outputDir, reader);
    
    CodeBlock calendarIndex = indexFormatters("registerCalendarFormatter", dateClasses);
    CodeBlock numberIndex = indexFormatters("registerNumberFormatter", numberClasses);
    
    MethodSpec constructor = MethodSpec.constructorBuilder()
        .addModifiers(PRIVATE)
        .build();

    FieldSpec instance = FieldSpec.builder(CLDR_TYPE, "instance", PRIVATE, STATIC, FINAL)
        .initializer("new $T()", CLDR_TYPE)
        .build();

    MethodSpec getter = MethodSpec.methodBuilder("get")
        .addModifiers(PUBLIC, STATIC)
        .returns(CLDR_TYPE)
        .addStatement("return instance")
        .build();

    TypeSpec.Builder type = TypeSpec.classBuilder("CLDR")
        .addModifiers(PUBLIC)
        .superclass(CLDRBASE_TYPE)
        .addField(instance)
        .addMethod(constructor)
        .addMethod(getter)
        .addStaticBlock(calendarIndex)
        .addStaticBlock(numberIndex);

    Set<LocaleID> availableLocales = reader.getAvailableLocales()
        .stream()
        .map(s -> LocaleID.parse(s))
        .collect(Collectors.toSet());
    
    createLocales(type, reader.getDefaultContent(), availableLocales);

    List<String> currencies = numberGenerator.getCurrencies(reader);
    createCurrencies(type, currencies);
    
    saveClass(outputDir, PACKAGE_CLDR, "CLDR", type.build());
  }

  /**
   * Saves a Java class file to a path for the given package, rooted in rootDir.
   */
  public static void saveClass(Path rootDir, String packageName, String className, TypeSpec type)
      throws IOException {

    List<String> packagePath = Splitter.on('.').splitToList(packageName);
    Path classPath = Paths.get(rootDir.toString(), packagePath.toArray(EMPTY));
    Path outputFile = classPath.resolve(className + ".java");

    JavaFile javaFile = JavaFile.builder(packageName, type)
        .addFileComment("\n\nAUTO-GENERATED CLASS - DO NOT EDIT\n\n")
        // TODO: build timestamp and version info
        .build();

    System.out.println("saving " + outputFile);
    Files.createParentDirs(outputFile.toFile());
    CharSink sink = Files.asCharSink(outputFile.toFile(), Charsets.UTF_8);
    sink.write(javaFile.toString());
  }

  /**
   * Generates a static code block that populates the formatter map.
   */
  private static CodeBlock indexFormatters(String methodName, Map<LocaleID, ClassName> dateClasses) {
    CodeBlock.Builder block = CodeBlock.builder();
    for (Map.Entry<LocaleID, ClassName> entry : dateClasses.entrySet()) {
      LocaleID localeId = entry.getKey();
      ClassName className = entry.getValue();
      block.addStatement("$T.$L($L, $L.class)", CLDRBASE_TYPE, 
          methodName, localeId.safe.toUpperCase(), className);
    }
    return block.build();
  }
  
  /**
   * Generate the mapping for default content locales.
   * See: http://cldr.unicode.org/translation/default-content
   */
  private static void createLocales(TypeSpec.Builder type, List<LocaleID> defaultContent, Set<LocaleID> available) {
    int localeCount = 0;
    List<String> allLocales = new ArrayList<>();
    for (LocaleID locale : available) {
      String name = addLocaleField(type, locale);
      allLocales.add(name);
      localeCount++;
    }
    
    CodeBlock.Builder block = CodeBlock.builder();
    for (LocaleID locale : defaultContent) {
      LocaleID dest = new LocaleID(locale.language, "", "", "");
      if (available.contains(dest)) {
        String name = addLocaleField(type, locale);
        allLocales.add(name);
        localeCount++;
        block.addStatement("defaultContent.put($L, $L)",
            locale.safe.toUpperCase(),
            dest.safe.toUpperCase());
      }
    }
    type.addStaticBlock(block.build());

    StringBuilder buf = new StringBuilder("$T.unmodifiableList($T.asList(\n");
    for (int i = 0; i < localeCount; i++) {
      if (i > 0) {
        buf.append(",\n");
      }
      buf.append("  $L");
    }
    buf.append("))");

    Collections.sort(allLocales);
    
    List<Object> arguments = new ArrayList<>();
    arguments.add(Collections.class);
    arguments.add(Arrays.class);
    arguments.addAll(allLocales);
    
    FieldSpec.Builder field = FieldSpec.builder(LOCALE_LIST_TYPE, "AVAILABLE_LOCALES", PRIVATE, STATIC, FINAL);
    field.initializer(buf.toString(), arguments.toArray());
    type.addField(field.build());
    
    MethodSpec.Builder method = MethodSpec.methodBuilder("availableLocales")
        .addModifiers(PUBLIC, STATIC, FINAL)
        .returns(LOCALE_LIST_TYPE)
        .addStatement("return AVAILABLE_LOCALES");
    type.addMethod(method.build());
  }

  /**
   * Create a public locale field.
   */
  private static String addLocaleField(TypeSpec.Builder type, LocaleID locale) {
    String name = locale.safe.toUpperCase();
    FieldSpec.Builder field = FieldSpec.builder(CLDRLocale.class, name, PUBLIC, STATIC, FINAL)
        .initializer("new $T($S, $S, $S, $S)", 
            CLDRLocale.class, locale.language, locale.script, locale.territory, locale.variant);
    type.addField(field.build());
    return name;
  }
  
  /**
   * Create top-level container to hold currency constants.
   */
  private static void createCurrencies(TypeSpec.Builder type, List<String> currencies) {
    TypeSpec.Builder currencyType = TypeSpec.classBuilder("Currency")
        .addModifiers(PUBLIC, STATIC, FINAL);
    
    List<String> codes = new ArrayList<>();
    codes.addAll(currencies);
    Collections.sort(codes);
    
    StringBuilder buf = new StringBuilder("$T.unmodifiableList($T.asList(\n");
    for (int i = 0; i < codes.size(); i++) {
      if (i > 0) {
        buf.append(",\n");
      }
      addCurrencyField(currencyType, codes.get(i));
      buf.append("  Currency.$L");
    }
    buf.append("))");
    
    type.addType(currencyType.build());

    // Initialize field containing all currencies
    List<Object> arguments = new ArrayList<>();
    arguments.add(Collections.class);
    arguments.add(Arrays.class);
    arguments.addAll(codes);
    FieldSpec.Builder field = FieldSpec.builder(CURRENCY_LIST_TYPE, "AVAILABLE_CURRENCIES", PRIVATE, STATIC, FINAL);
    field.initializer(buf.toString(), arguments.toArray());
    type.addField(field.build());
    
    MethodSpec.Builder method = MethodSpec.methodBuilder("availableCurrencies")
        .addModifiers(PUBLIC, STATIC, FINAL)
        .returns(CURRENCY_LIST_TYPE)
        .addStatement("return AVAILABLE_CURRENCIES");
    type.addMethod(method.build());
  }
  
  /**
   * Add a currency field.
   */
  private static void addCurrencyField(TypeSpec.Builder type, String currencyCode) {
    FieldSpec.Builder field = FieldSpec.builder(String.class, currencyCode, PUBLIC, STATIC, FINAL)
        .initializer("$S", currencyCode);
    type.addField(field.build());
  }
}
