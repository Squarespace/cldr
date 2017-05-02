package com.squarespace.cldr.codegen;

import static com.squarespace.cldr.codegen.CodeGenerator.PACKAGE_CLDR;
import static com.squarespace.cldr.codegen.CodeGenerator.PACKAGE_CLDR_DATES;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.squarespace.cldr.codegen.DateTimeData.Format;
import com.squarespace.cldr.codegen.DateTimeData.Skeleton;
import com.squarespace.cldr.codegen.DateTimeData.Variants;
import com.squarespace.cldr.codegen.DateTimePatternParser.Field;
import com.squarespace.cldr.codegen.DateTimePatternParser.Node;
import com.squarespace.cldr.codegen.DateTimePatternParser.Text;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;


/**
 * Generates the CLDR classes that encapsulate the formatting instructions for
 * date-time patterns in a given locale.
 */
public class DateTimeCodeGenerator {

  private static final ClassName ZONED_DATETIME_TYPE = ClassName.get("java.time", "ZonedDateTime");
  private static final ClassName STRINGBUILDER_TYPE = ClassName.get("java.lang", "StringBuilder");

  private static final ClassName LOCALE_TYPE = ClassName.get(PACKAGE_CLDR, "CLDRLocale");

  private static final ClassName FORMATTER_TYPE = ClassName.get(PACKAGE_CLDR_DATES, "CLDRCalendarFormatter");
  private static final ClassName FIELD_VARIANTS_TYPE = ClassName.get(PACKAGE_CLDR_DATES, "FieldVariants");
  private static final ClassName DATEFORMAT_TYPE = ClassName.get(PACKAGE_CLDR_DATES, "DateFormatType");
  private static final ClassName TIMEFORMAT_TYPE = ClassName.get(PACKAGE_CLDR_DATES, "TimeFormatType");
  private static final TypeName INDEX_TYPE = ParameterizedTypeName.get(Map.class, String.class, Integer.class);

  private static final DateTimePatternParser PARSER = new DateTimePatternParser();

  /**
   * Generates the date-time classes into the given output directory.
   */
  public List<ClassName> generate(Path outputDir, DataReader reader, Set<LocaleID> active)
      throws IOException {

    List<ClassName> dateClasses = new ArrayList<>();

    int i = 0;
    for (Map.Entry<LocaleID, DateTimeData> entry : reader.calendars().entrySet()) {
      LocaleID id = entry.getKey();
      if (!active.contains(id)) {
        continue;
      }
      DateTimeData data = entry.getValue();

      // TODO: better naming

      String className = "CLDRCalendarFormatter" + i;
      ClassName cls = ClassName.get(PACKAGE_CLDR_DATES, className);
      dateClasses.add(cls);

      TypeSpec type = create(data, className);
      CodeGenerator.saveClass(outputDir, PACKAGE_CLDR_DATES, className, type);

      i++;
    }

    return dateClasses;
  }

  /**
   * Create a Java class that captures all data formats for a given locale.
   */
  private TypeSpec create(DateTimeData data, String className) {
    LocaleID id = data.id();

    MethodSpec.Builder constructor = MethodSpec.constructorBuilder()
        .addModifiers(PUBLIC);

    constructor.addStatement("this.locale = new $T($S, $S, $S, $S)",
        LOCALE_TYPE, id.language, id.script, id.territory, id.variant);
    constructor.addStatement("this.indexMap = this.buildIndex()");
    constructor.addStatement("this.firstDay = $L", data.firstDay());
    constructor.addStatement("this.minDays = $L", data.minDays());

    variantsFieldInit(constructor, "this.eras", data.eras());
    variantsFieldInit(constructor, "this.quartersFormat", data.quartersFormat());
    variantsFieldInit(constructor, "this.quartersStandalone", data.quartersStandalone());
    variantsFieldInit(constructor, "this.monthsFormat", data.monthsFormat());
    variantsFieldInit(constructor, "this.monthsStandalone", data.monthsStandalone());
    variantsFieldInit(constructor, "this.weekdaysFormat", data.weekdaysFormat());
    variantsFieldInit(constructor, "this.weekdaysStandalone", data.weekdaysStandalone());
    variantsFieldInit(constructor, "this.dayPeriodsFormat", data.dayPeriodsFormat());
    variantsFieldInit(constructor, "this.dayPeriodsStandalone", data.dayPeriodsStandalone());

    TypeSpec.Builder type = TypeSpec.classBuilder(className)
        .superclass(FORMATTER_TYPE)
        .addModifiers(PUBLIC)
        .addJavadoc(
            "Locale " + data.id() + "\n" +
            "See http://www.unicode.org/reports/tr35/tr35-dates.html#Date_Field_Symbol_Table\n")
        .addMethod(constructor.build());

    MethodSpec.Builder dateMethod = MethodSpec.methodBuilder("formatDate")
        .addAnnotation(Override.class)
        .addModifiers(PUBLIC)
        .addParameter(DATEFORMAT_TYPE, "type")
        .addParameter(ZONED_DATETIME_TYPE, "d")
        .addParameter(STRINGBUILDER_TYPE, "b");

    addTypedPattern(dateMethod, DATEFORMAT_TYPE, data.dateFormats());

    MethodSpec.Builder timeMethod = MethodSpec.methodBuilder("formatTime")
      .addAnnotation(Override.class)
      .addModifiers(PUBLIC)
      .addParameter(TIMEFORMAT_TYPE, "type")
      .addParameter(ZONED_DATETIME_TYPE, "d")
      .addParameter(STRINGBUILDER_TYPE, "b");

    addTypedPattern(timeMethod, TIMEFORMAT_TYPE, data.timeFormats());

    MethodSpec.Builder dateTimeMethod = addDateTimeWrappers(data.dateTimeFormats());

    // Integer identifying a given skeleton pattern
    int index = 0;

    MethodSpec.Builder formatMethod = MethodSpec.methodBuilder("formatSkeleton")
        .addAnnotation(Override.class)
        .addModifiers(PUBLIC)
        .addParameter(int.class, "i")
        .addParameter(ZONED_DATETIME_TYPE, "d")
        .addParameter(STRINGBUILDER_TYPE, "b")
        .returns(boolean.class);

    // Map of pattern name to integer index.
    MethodSpec.Builder indexMethod = MethodSpec.methodBuilder("buildIndex")
        .addModifiers(PRIVATE)
        .returns(INDEX_TYPE);

    formatMethod.beginControlFlow("switch (i)");
    indexMethod.addStatement("$T r = new $T<>()", INDEX_TYPE, HashMap.class);

    // Skeleton patterns.
    for (Skeleton skeleton : data.dateTimeSkeletons()) {
      indexMethod.addStatement("r.put($S, $L)", skeleton.skeleton, index);
      formatMethod.beginControlFlow("case $L:", index)
        .addComment("$S -> $S", skeleton.skeleton, skeleton.pattern);

      addPattern(formatMethod, skeleton.pattern);

      formatMethod.addStatement("break");
      formatMethod.endControlFlow();
      index++;
    }

    formatMethod.beginControlFlow("default:");
    formatMethod.addStatement("return false");
    formatMethod.endControlFlow();

    formatMethod.endControlFlow();
    formatMethod.addStatement("return true");
    indexMethod.addStatement("return r");

    return type
        .addMethod(dateMethod.build())
        .addMethod(timeMethod.build())
        .addMethod(dateTimeMethod.build())
        .addMethod(formatMethod.build())
        .addMethod(indexMethod.build())
        .build();
  }

  /**
   * The CLDR contains 4 standard pattern types for date and time: short, medium, long and full.
   * This generates a switch statement to format patterns of this type.
   * See CLDR "dateFormats" and "timeFormats" nodes.
   */
  private void addTypedPattern(MethodSpec.Builder method, ClassName type, Format format) {
    method.beginControlFlow("switch (type)", type);
    addTypedPattern(method, "SHORT", format.short_);
    addTypedPattern(method, "MEDIUM", format.medium);
    addTypedPattern(method, "LONG", format.long_);
    addTypedPattern(method, "FULL", format.full);
    method.endControlFlow();
  }

  /**
   * Adds the pattern builder statements for a typed pattern.
   */
  private void addTypedPattern(MethodSpec.Builder method, String formatType, String pattern) {
    method.beginControlFlow("case $L:", formatType);
    method.addComment("$S", pattern);
    addPattern(method, pattern);
    method.addStatement("break");
    method.endControlFlow();
  }

  /**
   * Add a named date-time pattern, adding statements to the formatting and indexing methods.
   */
  private void addPattern(MethodSpec.Builder method, String pattern) {
    // Parse the pattern and populate the method body with instructions to format the date.
    for (Node node : PARSER.parse(pattern)) {
      if (node instanceof Text) {
        method.addStatement("b.append($S)", ((Text)node).text);
      } else if (node instanceof Field) {
        Field field = (Field)node;
        method.addStatement("formatField(d, b, '$L', $L)", field.ch, field.width);
      }
    }
  }

  private MethodSpec.Builder addDateTimeWrappers(Format format) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("formatDateTime")
        .addAnnotation(Override.class)
        .addModifiers(PUBLIC)
        .addParameter(DATEFORMAT_TYPE, "dateType")
        .addParameter(TIMEFORMAT_TYPE, "timeType")
        .addParameter(ZONED_DATETIME_TYPE, "d")
        .addParameter(STRINGBUILDER_TYPE, "b");

    method.beginControlFlow("switch (dateType)");
    addDateTimeWrapper(method, "SHORT", format.short_);
    addDateTimeWrapper(method, "MEDIUM", format.medium);
    addDateTimeWrapper(method, "LONG", format.long_);
    addDateTimeWrapper(method, "FULL", format.full);
    method.endControlFlow();

    return method;
  }

  private void addDateTimeWrapper(MethodSpec.Builder method, String type, String pattern) {
    method.beginControlFlow("case $L:", type);
    method.addComment("$S", pattern);
    for (Node node : PARSER.parseWrapper(pattern)) {
      if (node instanceof Text) {
        method.addStatement("b.append($S)", ((Text)node).text);

      } else if (node instanceof Field) {
        Field field = (Field)node;
        switch (field.ch) {
          case '0':
            method.addStatement("formatTime(timeType, d, b)");
            break;
          case '1':
            method.addStatement("formatDate(dateType, d, b)");
            break;
        }
      }
    }
    method.addStatement("break");
    method.endControlFlow();
  }

  /**
   * Appends a statement that initializes a FieldVariants field in the superclass.
   */
  private void variantsFieldInit(MethodSpec.Builder method, String fieldName, Variants v) {
    Stmt b = new Stmt();
    b.append("$L = new $T(", fieldName, FIELD_VARIANTS_TYPE);
    b.append("\n  ").append(v.abbreviated).comma();
    b.append("\n  ").append(v.narrow).comma();
    b.append("\n  ").append(v.short_).comma();
    b.append("\n  ").append(v.wide);
    b.append(")");
    method.addStatement(b.format(), b.args());
  }

  /**
   * Helper to build up longer formatted statements.
   */
  private static class Stmt {

    private final ArrayList<Object> args = new ArrayList<>();

    private final StringBuilder buf = new StringBuilder();

    public Stmt append(String fmt, Object ...objects) {
      buf.append(fmt);
      for (Object o : objects) {
        args.add(o);
      }
      return this;
    }

    public Stmt append(String[] values) {
      buf.append("new $T[] {");
      args.add(String.class);
      for (int i= 0; i < values.length; i++) {
        if (i > 0) {
          buf.append(", ");
        }
        buf.append("$S");
        args.add(values[i]);
      }
      buf.append("}");
      return this;
    }

    public Stmt comma() {
      buf.append(", ");
      return this;
    }

    public Object[] args() {
      return args.toArray();
    }

    public String format() {
      return buf.toString();
    }
  }

}
