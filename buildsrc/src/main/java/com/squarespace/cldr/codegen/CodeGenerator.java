package com.squarespace.cldr.codegen;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.google.common.io.CharSink;
import com.google.common.io.Files;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
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

  private static final TypeName CLDR_TYPE = ClassName.get(PACKAGE_CLDR, "CLDR");

  private static final ClassName CLDRBASE_TYPE = ClassName.get(PACKAGE_CLDR, "CLDRBase");

  private static final String[] EMPTY = new String[] { };

  public static final Set<LocaleID> activeLocales = Sets.newHashSet(
      new LocaleID("en", "US", "", "POSIX"),
      new LocaleID("fr", "", "", ""),
      new LocaleID("de", "", "", "")
  );

  public static void main(String[] args) throws IOException {
    Path root = Paths.get("/Users/phensley/dev/squarespace-cldr");
    generate(root.resolve("core/src/generated/java"));
  }

  public static void generate(Path outputDir) throws IOException {
    DataReader reader = DataReader.get();
    DateTimeCodeGenerator generator = new DateTimeCodeGenerator();
    List<ClassName> dateClasses = generator.generate(outputDir, reader, activeLocales);

    CodeBlock indexFormatters = indexFormatters(dateClasses);

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

    TypeSpec type = TypeSpec.classBuilder("CLDR")
        .addModifiers(PUBLIC)
        .superclass(CLDRBASE_TYPE)
        .addField(instance)
        .addMethod(constructor)
        .addMethod(getter)
        .addStaticBlock(indexFormatters)
        .build();

    saveClass(outputDir, PACKAGE_CLDR, "CLDR", type);
  }

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
   * Genreates a static code block that populates the formatter map.
   */
  private static CodeBlock indexFormatters(List<ClassName> dateClasses) {
    CodeBlock.Builder block = CodeBlock.builder();
    for (ClassName className : dateClasses) {
      block.addStatement("$T.registerFormatter(new $T())", CLDRBASE_TYPE, className);
    }
    return block.build();
  }

}
