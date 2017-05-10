package com.squarespace.cldr.codegen;

import static com.squarespace.cldr.codegen.CodeGenerator.PACKAGE_CLDR;
import static com.squarespace.cldr.codegen.CodeGenerator.PACKAGE_CLDR_DATES;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Splitter;
import com.squarespace.cldr.codegen.DateTimeData.Format;
import com.squarespace.cldr.codegen.DateTimeData.Skeleton;
import com.squarespace.cldr.codegen.DateTimeData.Variants;
import com.squarespace.cldr.codegen.DateTimePatternParser.Field;
import com.squarespace.cldr.codegen.DateTimePatternParser.Node;
import com.squarespace.cldr.codegen.DateTimePatternParser.Text;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
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
  private static final ClassName SKELETON_TYPE = ClassName.get(PACKAGE_CLDR_DATES, "SkeletonType");
  private static final ClassName FORMAT_TYPE = ClassName.get(PACKAGE_CLDR_DATES, "FormatType");

  private static final DateTimePatternParser PARSER = new DateTimePatternParser();

  /**
   * Generates the date-time classes into the given output directory.
   */
  public List<ClassName> generate(Path outputDir, DataReader reader)
      throws IOException {

    List<ClassName> dateClasses = new ArrayList<>();
    List<DateTimeData> dateTimeData = new ArrayList<>();

    int i = 0;
    for (Map.Entry<LocaleID, DateTimeData> entry : reader.calendars().entrySet()) {
      DateTimeData data = entry.getValue();

      // TODO: better naming

      String className = "CLDRCalendarFormatter" + i;
      ClassName cls = ClassName.get(PACKAGE_CLDR_DATES, className);
      dateClasses.add(cls);

      dateTimeData.add(data);
      TypeSpec type = createFormatter(data, className);
      CodeGenerator.saveClass(outputDir, PACKAGE_CLDR_DATES, className, type);

      i++;
    }

    TypeSpec type = createSkeletonClassifier(dateTimeData);
    CodeGenerator.saveClass(outputDir, PACKAGE_CLDR, "CLDRCalendarUtils", type);

    return dateClasses;
  }

  /**
   * Create a helper class to classify skeletons as either DATE or TIME.
   */
  private TypeSpec createSkeletonClassifier(List<DateTimeData> dataList) {
    Set<String> dates = new LinkedHashSet<>();
    Set<String> times = new LinkedHashSet<>();
    for (DateTimeData data : dataList) {
      for (Skeleton skeleton : data.dateTimeSkeletons()) {
        if (isDateSkeleton(skeleton.skeleton)) {
          dates.add(skeleton.skeleton);
        } else {
          times.add(skeleton.skeleton);
        }
      }
    }

    MethodSpec.Builder skeletonTypeMethod = buildSkeletonType(dates, times);

    return TypeSpec.classBuilder("CLDRCalendarUtils")
        .addModifiers(PUBLIC)
        .addMethod(skeletonTypeMethod.build())
        .build();
  }

  /**
   * Create a Java class that captures all data formats for a given locale.
   */
  private TypeSpec createFormatter(DateTimeData data, String className) {
    LocaleID id = data.id();

    MethodSpec.Builder constructor = MethodSpec.constructorBuilder()
        .addModifiers(PUBLIC);

    constructor.addStatement("this.locale = new $T($S, $S, $S, $S)",
        LOCALE_TYPE, id.language, id.script, id.territory, id.variant);
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
            "Locale \"" + data.id() + "\"\n" +
            "See http://www.unicode.org/reports/tr35/tr35-dates.html#Date_Field_Symbol_Table\n")
        .addMethod(constructor.build());

    MethodSpec.Builder dateMethod = MethodSpec.methodBuilder("formatDate")
        .addAnnotation(Override.class)
        .addModifiers(PUBLIC)
        .addParameter(FORMAT_TYPE, "type")
        .addParameter(ZONED_DATETIME_TYPE, "d")
        .addParameter(STRINGBUILDER_TYPE, "b");

    addTypedPattern(dateMethod, FORMAT_TYPE, data.dateFormats());

    MethodSpec.Builder timeMethod = MethodSpec.methodBuilder("formatTime")
      .addAnnotation(Override.class)
      .addModifiers(PUBLIC)
      .addParameter(FORMAT_TYPE, "type")
      .addParameter(ZONED_DATETIME_TYPE, "d")
      .addParameter(STRINGBUILDER_TYPE, "b");

    addTypedPattern(timeMethod, FORMAT_TYPE, data.timeFormats());

    MethodSpec.Builder wrapperMethod = buildWrappers(data.dateTimeFormats());

    MethodSpec.Builder formatMethod = MethodSpec.methodBuilder("formatSkeleton")
        .addAnnotation(Override.class)
        .addModifiers(PUBLIC)
        .addParameter(String.class, "skeleton")
        .addParameter(ZONED_DATETIME_TYPE, "d")
        .addParameter(STRINGBUILDER_TYPE, "b")
        .returns(boolean.class);

    formatMethod.beginControlFlow("switch (skeleton)");

    // Skeleton patterns.
    for (Skeleton skeleton : data.dateTimeSkeletons()) {
      formatMethod.beginControlFlow("case $S:", skeleton.skeleton)
        .addComment("Pattern: $S", skeleton.pattern);

      addPattern(formatMethod, skeleton.pattern);

      formatMethod.addStatement("break");
      formatMethod.endControlFlow();
    }

    formatMethod.beginControlFlow("default:");
    formatMethod.addStatement("return false");
    formatMethod.endControlFlow();

    formatMethod.endControlFlow();
    formatMethod.addStatement("return true");

    return type
        .addMethod(dateMethod.build())
        .addMethod(timeMethod.build())
        .addMethod(wrapperMethod.build())
        .addMethod(formatMethod.build())
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
   * Returns true if the pattern corresponds to a date; false if a time.
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

  /**
   * Build a method that will format a date and time (named or skeleton) in a
   * localized wrapper.
   */
  private MethodSpec.Builder buildWrappers(Format format) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("formatWrapped")
        .addAnnotation(Override.class)
        .addModifiers(PUBLIC)
        .addParameter(FORMAT_TYPE, "wrapperType")
        .addParameter(FORMAT_TYPE, "dateType")
        .addParameter(FORMAT_TYPE, "timeType")
        .addParameter(String.class, "dateSkel")
        .addParameter(String.class, "timeSkel")
        .addParameter(ZONED_DATETIME_TYPE, "d")
        .addParameter(STRINGBUILDER_TYPE, "b");

    Map<String, List<String>> map = deduplicateFormats(format);

    method.beginControlFlow("switch (wrapperType)");
    for (Map.Entry<String, List<String>> entry : map.entrySet()) {
      for (String type : entry.getValue()) {
        method.addCode("case $L:\n", type);
      }
      method.beginControlFlow("");
      addWrapper(method, entry.getKey());
      method.addStatement("break");
      method.endControlFlow();
    }
    method.endControlFlow();

    return method;
  }

  private void addWrapper(MethodSpec.Builder method, String pattern) {
    method.addComment("$S", pattern);
    for (Node node : PARSER.parseWrapper(pattern)) {
      if (node instanceof Text) {
        method.addStatement("b.append($S)", ((Text)node).text);

      } else if (node instanceof Field) {
        Field field = (Field)node;
        switch (field.ch) {
          case '0':
            method.beginControlFlow("if (timeType != null)");
            method.addStatement("formatTime(timeType, d, b)");
            method.nextControlFlow("else");
            method.addStatement("formatSkeleton(timeSkel, d, b)");
            method.endControlFlow();
            break;

          case '1':
            method.beginControlFlow("if (dateType != null)");
            method.addStatement("formatDate(dateType, d, b)");
            method.nextControlFlow("else");
            method.addStatement("formatSkeleton(dateSkel, d, b)");
            method.endControlFlow();
            break;
        }
      }
    }
  }

  /**
   * Indicates a skeleton represents a date based on the fields it contains.
   * Any time-related field will cause this to return false.
   */
  private boolean isDateSkeleton(String skeleton) {
    List<String> parts = Splitter.on('-').splitToList(skeleton);
    if (parts.size() > 1) {
      skeleton = parts.get(0);
    }
    for (Node node : PARSER.parse(skeleton)) {
      if (node instanceof Field) {
        Field field = (Field) node;
        switch (field.ch) {
          case 'H':
          case 'h':
          case 'm':
          case 's':
            return false;
        }
      }
    }
    return true;
  }

  /**
   * Constructs a method to indicate if the skeleton is a DATE or TIME, or null if unsupported.
   */
  private MethodSpec.Builder buildSkeletonType(Set<String> dateSkeletons, Set<String> timeSkeletons) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("skeletonType")
        .addJavadoc("Indicates whether a given skeleton pattern is a DATE or TIME.\n")
        .addModifiers(PUBLIC, STATIC)
        .addParameter(String.class, "skeleton")
        .returns(SKELETON_TYPE);

    method.beginControlFlow("switch (skeleton)");
    for (String skel : dateSkeletons) {
      method.addCode("case $S:\n", skel);
    }
    method.addStatement("  return $T.DATE", SKELETON_TYPE);
    for (String skel : timeSkeletons) {
      method.addCode("case $S:\n", skel);
    }
    method.addStatement("  return $T.TIME", SKELETON_TYPE);
    method.addCode("default:\n");
    method.addStatement("  return null");
    method.endControlFlow();

    return method;
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
   * De-duplication of formats.
   */
  public static Map<String, List<String>> deduplicateFormats(Format format) {
   Map<String, List<String>> res = new LinkedHashMap<>();
   mapAdd(res, format.short_, "SHORT");
   mapAdd(res, format.medium, "MEDIUM");
   mapAdd(res, format.long_, "LONG");
   mapAdd(res, format.full, "FULL");
   return res;
  }

  /**
   * Adds a key and value to a Map<String, List<String>>.
   */
  private static void mapAdd(Map<String, List<String>> map, String key, String val) {
    List<String> list = map.get(key);
    if (list == null) {
      list = new ArrayList<>();
      map.put(key, list);
    }
    list.add(val);
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
