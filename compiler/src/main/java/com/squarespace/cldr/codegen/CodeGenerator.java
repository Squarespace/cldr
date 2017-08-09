package com.squarespace.cldr.codegen;

import static com.squarespace.cldr.codegen.Types.CLDR;
import static com.squarespace.cldr.codegen.Types.CLDR_BASE;
import static com.squarespace.cldr.codegen.Types.CLDR_LOCALE_BASE_CLS;
import static com.squarespace.cldr.codegen.Types.CLDR_LOCALE_CLS;
import static com.squarespace.cldr.codegen.Types.CLDR_LOCALE_IF;
import static com.squarespace.cldr.codegen.Types.LIST_CLDR_LOCALE;
import static com.squarespace.cldr.codegen.Types.LIST_STRING;
import static com.squarespace.cldr.codegen.Types.PACKAGE_CLDR;
import static com.squarespace.cldr.codegen.Types.PLURAL_RULES;
import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PROTECTED;
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
import com.squarespace.cldr.codegen.reader.DataReader;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;


/**
 * Generates the CLDR classes that encapsulate the core data.
 *
 * TODO: embed version/build information into generated classes
 */
public class CodeGenerator {

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
    CalendarCodeGenerator datetimeGenerator = new CalendarCodeGenerator();
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

    FieldSpec instance = FieldSpec.builder(CLDR, "instance", PRIVATE, STATIC, FINAL)
        .initializer("new $T()", CLDR)
        .build();

    MethodSpec getter = MethodSpec.methodBuilder("get")
        .addModifiers(PUBLIC, STATIC)
        .returns(CLDR)
        .addStatement("return instance")
        .build();

    TypeSpec.Builder type = TypeSpec.classBuilder("CLDR")
        .addModifiers(PUBLIC)
        .superclass(CLDR_BASE)
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
    createCurrencies(type, numberGenerator.getCurrencies(reader));
    
    addPluralRules(type);
    
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
   * Add static instance of plural rules and accessor method.
   */
  private static void addPluralRules(TypeSpec.Builder type) {
    FieldSpec field = FieldSpec.builder(PLURAL_RULES, "pluralRules", PRIVATE, STATIC, FINAL)
        .initializer("new $T()", PLURAL_RULES)
        .build();
    
    MethodSpec method = MethodSpec.methodBuilder("getPluralRules")
        .addModifiers(PUBLIC)
        .returns(PLURAL_RULES)
        .addStatement("return pluralRules")
        .build();

    type.addField(field);
    type.addMethod(method);
  }
  
  /**
   * Generates a static code block that populates the formatter map.
   */
  private static CodeBlock indexFormatters(String methodName, Map<LocaleID, ClassName> dateClasses) {
    CodeBlock.Builder block = CodeBlock.builder();
    for (Map.Entry<LocaleID, ClassName> entry : dateClasses.entrySet()) {
      LocaleID localeId = entry.getKey();
      ClassName className = entry.getValue();
      block.addStatement("$T.$L(Locale.$L, $L.class)", CLDR_BASE, 
          methodName, localeId.safe, className);
    }
    return block.build();
  }
  
  /**
   * Generate the mapping for default content locales.
   * See: http://cldr.unicode.org/translation/default-content
   */
  private static void createLocales(TypeSpec.Builder type, List<LocaleID> defaultContent, Set<LocaleID> available) {
    TypeSpec.Builder localeBase = TypeSpec.classBuilder("_Locale")
        .addModifiers(PROTECTED, STATIC)
        .superclass(CLDR_LOCALE_BASE_CLS)
        .addSuperinterface(CLDR_LOCALE_IF)
        .addMethod(MethodSpec.constructorBuilder()
            .addParameter(String.class, "language")
            .addParameter(String.class, "script")
            .addParameter(String.class, "territory")
            .addParameter(String.class, "variant")
            .addStatement("super(language, script, territory, variant)").build());

    type.addType(localeBase.build());
    
    TypeSpec.Builder localeInterface = TypeSpec.interfaceBuilder("Locale")
        .addModifiers(PUBLIC, STATIC)
        .addMethod(MethodSpec.methodBuilder("language")
            .addModifiers(PUBLIC, ABSTRACT)
            .returns(String.class).build())
        .addMethod(MethodSpec.methodBuilder("script")
            .addModifiers(PUBLIC, ABSTRACT)
            .returns(String.class).build())
        .addMethod(MethodSpec.methodBuilder("territory")
            .addModifiers(PUBLIC, ABSTRACT)
            .returns(String.class).build())
        .addMethod(MethodSpec.methodBuilder("variant")
            .addModifiers(PUBLIC, ABSTRACT)
            .returns(String.class).build());
    
    List<LocaleID> localeFields = new ArrayList<>();
    
    int localeCount = 0;
    List<String> allLocales = new ArrayList<>();
    for (LocaleID locale : available) {
      localeFields.add(locale);
      allLocales.add(locale.safe);
      localeCount++;
    }
    
    CodeBlock.Builder block = CodeBlock.builder();
    for (LocaleID locale : defaultContent) {
      LocaleID dest = new LocaleID(locale.language, "", "", "");
      if (available.contains(dest)) {
        localeFields.add(locale);
        allLocales.add(locale.safe);
        localeCount++;
        block.addStatement("defaultContent.put(Locale.$L, Locale.$L)",
            locale.safe,
            dest.safe);
      }
    }

    Collections.sort(localeFields);
    for (LocaleID locale : localeFields) {
      addLocaleField(localeInterface, locale.safe, locale);
    }
    
    type.addStaticBlock(block.build());

    StringBuilder buf = new StringBuilder("$T.unmodifiableList($T.asList(\n");
    for (int i = 0; i < localeCount; i++) {
      if (i > 0) {
        buf.append(",\n");
      }
      buf.append("  Locale.$L");
    }
    buf.append("))");

    Collections.sort(allLocales);
    
    List<Object> arguments = new ArrayList<>();
    arguments.add(Collections.class);
    arguments.add(Arrays.class);
    arguments.addAll(allLocales);
    
    FieldSpec.Builder field = FieldSpec.builder(LIST_CLDR_LOCALE, "AVAILABLE_LOCALES", PRIVATE, STATIC, FINAL);
    field.initializer(buf.toString(), arguments.toArray());
    type.addField(field.build());
    
    MethodSpec.Builder method = MethodSpec.methodBuilder("availableLocales")
        .addModifiers(PUBLIC, STATIC, FINAL)
        .returns(LIST_CLDR_LOCALE)
        .addStatement("return AVAILABLE_LOCALES");
    
    type.addMethod(method.build());
    type.addType(localeInterface.build());
  }

  /**
   * Create a public locale field.
   */
  private static void addLocaleField(TypeSpec.Builder type, String name, LocaleID locale) {
    FieldSpec.Builder field = FieldSpec.builder(CLDR_LOCALE_IF, name, PUBLIC, STATIC, FINAL)
        .initializer("new $T($S, $S, $S, $S)",
            CLDR_LOCALE_CLS, locale.language, locale.script, locale.territory, locale.variant);
    type.addField(field.build());
  }

  /**
   * Create top-level container to hold currency constants.
   */
  private static void createCurrencies(TypeSpec.Builder type, List<String> currencies) {
    TypeSpec.Builder currencyType = TypeSpec.interfaceBuilder("Currency")
        .addModifiers(PUBLIC, STATIC);

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
    FieldSpec.Builder field = FieldSpec.builder(LIST_STRING, "AVAILABLE_CURRENCIES", PRIVATE, STATIC, FINAL);
    field.initializer(buf.toString(), arguments.toArray());
    type.addField(field.build());
    
    MethodSpec.Builder method = MethodSpec.methodBuilder("availableCurrencies")
        .addModifiers(PUBLIC, STATIC, FINAL)
        .returns(LIST_STRING)
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
