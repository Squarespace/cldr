package com.squarespace.cldr.codegen;

import static com.squarespace.cldr.codegen.Types.CALENDAR_FORMAT;
import static com.squarespace.cldr.codegen.Types.CALENDAR_FORMATTER;
import static com.squarespace.cldr.codegen.Types.CLDR_LOCALE_IF;
import static com.squarespace.cldr.codegen.Types.FIELD_VARIANTS;
import static com.squarespace.cldr.codegen.Types.HASHMAP;
import static com.squarespace.cldr.codegen.Types.MAP;
import static com.squarespace.cldr.codegen.Types.SKELETON;
import static com.squarespace.cldr.codegen.Types.STRING;
import static com.squarespace.cldr.codegen.Types.TIMEZONE_NAMES;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PROTECTED;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.google.common.base.Splitter;
import com.squarespace.cldr.codegen.reader.DataReader;
import com.squarespace.cldr.codegen.reader.DateTimeData;
import com.squarespace.cldr.codegen.reader.DateTimeData.Format;
import com.squarespace.cldr.codegen.reader.DateTimeData.Skeleton;
import com.squarespace.cldr.codegen.reader.DateTimeData.Variants;
import com.squarespace.cldr.codegen.reader.TimeZoneData;
import com.squarespace.cldr.codegen.reader.TimeZoneData.MetaZone;
import com.squarespace.cldr.codegen.reader.TimeZoneData.MetaZoneEntry;
import com.squarespace.cldr.codegen.reader.TimeZoneData.MetaZoneInfo;
import com.squarespace.cldr.codegen.reader.TimeZoneData.TimeZoneInfo;
import com.squarespace.cldr.dates.DateTimeField;
import com.squarespace.cldr.parse.DateTimePatternParser;
import com.squarespace.cldr.parse.FieldPattern.Field;
import com.squarespace.cldr.parse.FieldPattern.Node;
import com.squarespace.cldr.parse.FieldPattern.Text;
import com.squarespace.cldr.parse.WrapperPatternParser;
import com.squarespace.compiler.parse.Pair;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;


/**
 * Generates code to format dates and times using CLDR data.
 */
public class CalendarCodeGenerator {

  private static final DateTimePatternParser DATETIME_PARSER = new DateTimePatternParser();
  private static final WrapperPatternParser WRAPPER_PARSER = new WrapperPatternParser();
  
  public static void main(String[] args) throws IOException {
    DataReader reader = DataReader.get();
    Path outputDir = Paths.get("/Users/phensley/dev/squarespace-cldr/runtime/src/generated/java");
    new CalendarCodeGenerator().generate(outputDir, reader);
  }
  
  /**
   * Generates the date-time classes into the given output directory.
   */
  public Map<LocaleID, ClassName> generate(Path outputDir, DataReader reader)
      throws IOException {

    Map<LocaleID, ClassName> dateClasses = new TreeMap<>();
    List<DateTimeData> dateTimeDataList = new ArrayList<>();

    for (Map.Entry<LocaleID, DateTimeData> entry : reader.calendars().entrySet()) {
      DateTimeData dateTimeData = entry.getValue();

      LocaleID localeId = entry.getKey();
      String className = "_CalendarFormatter_" + localeId.safe;

      TimeZoneData timeZoneData = reader.timezones().get(localeId);
      
      TypeSpec type = createFormatter(dateTimeData, timeZoneData, className);
      CodeGenerator.saveClass(outputDir, Types.PACKAGE_CLDR_DATES, className, type);

      ClassName cls = ClassName.get(Types.PACKAGE_CLDR_DATES, className);
      dateClasses.put(localeId, cls);
      dateTimeDataList.add(dateTimeData);
    }

    String className = "_CalendarUtils";
    TypeSpec.Builder utilsType = TypeSpec.classBuilder(className)
        .addModifiers(PUBLIC);
    
    addSkeletonClassifierMethod(utilsType, dateTimeDataList);
    addMetaZones(utilsType, reader.metazones());
    buildTimeZoneAliases(utilsType, reader.timezoneAliases());

    CodeGenerator.saveClass(outputDir, Types.PACKAGE_CLDR_DATES, "_CalendarUtils", utilsType.build());

    return dateClasses;
  }

  /**
   * Create a helper class to classify skeletons as either DATE or TIME.
   */
  private void addSkeletonClassifierMethod(TypeSpec.Builder type, List<DateTimeData> dataList) {
    Set<String> dates = new LinkedHashSet<>();
    Set<String> times = new LinkedHashSet<>();
    for (DateTimeData data : dataList) {
      for (Skeleton skeleton : data.dateTimeSkeletons) {
        if (isDateSkeleton(skeleton.skeleton)) {
          dates.add(skeleton.skeleton);
        } else {
          times.add(skeleton.skeleton);
        }
      }
    }

    MethodSpec.Builder method = buildSkeletonType(dates, times);
    type.addMethod(method.build());
  }

  /**
   * Populate the metaZone mapping.
   */
  private void addMetaZones(TypeSpec.Builder type, Map<String, MetaZone> metazones) {
    ClassName metazoneType = ClassName.get(Types.PACKAGE_CLDR_DATES, "MetaZone");
    TypeName mapType = ParameterizedTypeName.get(MAP, STRING, metazoneType);
    FieldSpec.Builder field = FieldSpec.builder(mapType, "metazones", PROTECTED, STATIC, FINAL);
    
    CodeBlock.Builder code = CodeBlock.builder();
    code.beginControlFlow("new $T<$T, $T>() {", HashMap.class, String.class, metazoneType);
    for (Map.Entry<String, MetaZone> entry : metazones.entrySet()) {
      String zoneId = entry.getKey();
      MetaZone zone = entry.getValue();

      code.beginControlFlow("\nput($S, new $T($S,\n  new $T.Entry[] ", zoneId, metazoneType, zoneId, metazoneType);
      int size = zone.metazones.size();
      Collections.reverse(zone.metazones);
      for (int i = 0; i < size; i++) {
        MetaZoneEntry meta = zone.metazones.get(i);
        if (i > 0) {
          code.add(",\n");
        }
        
        code.add("  new $T.Entry($S, ", metazoneType, meta.metazone);
        if (meta.from != null) {
          code.add("/* $L */ $L, ", meta.fromString, meta.from.toEpochSecond());
        } else {
          code.add("-1, ");
        }

        if (meta.to != null) {
          code.add("/* $L */ $L)", meta.toString, meta.to.toEpochSecond());
        } else {
          code.add("-1)");
        }
      }
      code.endControlFlow("))");
    }
    code.endControlFlow("\n}");
    field.initializer(code.build());
    
    type.addField(field.build());
    
    MethodSpec.Builder method = MethodSpec.methodBuilder("getMetazone")
        .addModifiers(PUBLIC, STATIC)
        .addParameter(String.class, "zoneId")
        .addParameter(ZonedDateTime.class, "date")
        .returns(String.class);
    method.addStatement("$T zone = metazones.get(zoneId)", metazoneType);
    method.addStatement("return zone == null ? null : zone.applies(date)");
    type.addMethod(method.build());
  }
  
  /**
   * Creates a switch table to resolve a retired time zone to a valid one.
   */
  private void buildTimeZoneAliases(TypeSpec.Builder type, Map<String, String> map) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("getTimeZoneAlias")
        .addModifiers(PUBLIC, STATIC)
        .addParameter(String.class, "zoneId")
        .returns(String.class);

    method.beginControlFlow("switch (zoneId)");
    for (Map.Entry<String, String> entry : map.entrySet()) {
      method.addStatement("case $S: return $S", entry.getKey(), entry.getValue());
    }
    method.addStatement("default: return null");
    method.endControlFlow();
    type.addMethod(method.build());
  }

  /**
   * Create a Java class that captures all data formats for a given locale.
   */
  private TypeSpec createFormatter(DateTimeData dateTimeData, TimeZoneData timeZoneData, String className) {
    LocaleID id = dateTimeData.id;

    MethodSpec.Builder constructor = MethodSpec.constructorBuilder()
        .addModifiers(PUBLIC);

    constructor.addStatement("this.bundleId = $T.$L", CLDR_LOCALE_IF, id.safe);
    constructor.addStatement("this.firstDay = $L", dateTimeData.firstDay);
    constructor.addStatement("this.minDays = $L", dateTimeData.minDays);

    variantsFieldInit(constructor, "this.eras", dateTimeData.eras);
    variantsFieldInit(constructor, "this.quartersFormat", dateTimeData.quartersFormat);
    variantsFieldInit(constructor, "this.quartersStandalone", dateTimeData.quartersStandalone);
    variantsFieldInit(constructor, "this.monthsFormat", dateTimeData.monthsFormat);
    variantsFieldInit(constructor, "this.monthsStandalone", dateTimeData.monthsStandalone);
    variantsFieldInit(constructor, "this.weekdaysFormat", dateTimeData.weekdaysFormat);
    variantsFieldInit(constructor, "this.weekdaysStandalone", dateTimeData.weekdaysStandalone);
    variantsFieldInit(constructor, "this.dayPeriodsFormat", dateTimeData.dayPeriodsFormat);
    variantsFieldInit(constructor, "this.dayPeriodsStandalone", dateTimeData.dayPeriodsStandalone);

    buildTimeZoneExemplarCities(constructor, timeZoneData);
    buildTimeZoneNames(constructor, timeZoneData);
    buildMetaZoneNames(constructor, timeZoneData);
    
    TypeSpec.Builder type = TypeSpec.classBuilder(className)
        .superclass(CALENDAR_FORMATTER)
        .addModifiers(PUBLIC)
        .addJavadoc(
            "Locale \"" + dateTimeData.id + "\"\n" +
            "See http://www.unicode.org/reports/tr35/tr35-dates.html#Date_Field_Symbol_Table\n")
        .addMethod(constructor.build());

    MethodSpec dateMethod = buildTypedPatternMethod("formatDate", CALENDAR_FORMAT, dateTimeData.dateFormats);
    MethodSpec timeMethod = buildTypedPatternMethod("formatTime", CALENDAR_FORMAT, dateTimeData.timeFormats);
    MethodSpec wrapperMethod = buildWrapperMethod(dateTimeData.dateTimeFormats);
    MethodSpec skeletonMethod = buildSkeletonFormatter(dateTimeData.dateTimeSkeletons);
    MethodSpec intervalMethod = buildIntervalMethod(dateTimeData.intervalFormats, dateTimeData.intervalFallbackFormat);
    MethodSpec gmtMethod = buildWrapTimeZoneGMTMethod(timeZoneData);
    MethodSpec regionFormatMethod = buildWrapTimeZoneRegionMethod(timeZoneData);

    return type
        .addMethod(dateMethod)
        .addMethod(timeMethod)
        .addMethod(wrapperMethod)
        .addMethod(skeletonMethod)
        .addMethod(intervalMethod)
        .addMethod(gmtMethod)
        .addMethod(regionFormatMethod)
        .build();
  }

  /**
   * The CLDR contains 4 standard pattern types for date and time: short, medium, long and full.
   * This generates a switch statement to format patterns of this type.
   * See CLDR "dateFormats" and "timeFormats" nodes.
   */
  private MethodSpec buildTypedPatternMethod(String methodName, ClassName type, Format format) {
    MethodSpec.Builder method = MethodSpec.methodBuilder(methodName)
        .addAnnotation(Override.class)
        .addModifiers(PUBLIC)
        .addParameter(CALENDAR_FORMAT, "type")
        .addParameter(ZonedDateTime.class, "d")
        .addParameter(StringBuilder.class, "b");

    method.beginControlFlow("if (type == null)");
    method.addStatement("return");
    method.endControlFlow();
    
    method.beginControlFlow("switch (type)", type);
    addTypedPattern(method, "SHORT", format.short_);
    addTypedPattern(method, "MEDIUM", format.medium);
    addTypedPattern(method, "LONG", format.long_);
    addTypedPattern(method, "FULL", format.full);
    method.endControlFlow();
    
    return method.build();
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
    for (Node node : DATETIME_PARSER.parse(pattern)) {
      if (node instanceof Text) {
        method.addStatement("b.append($S)", ((Text)node).text());
      } else if (node instanceof Field) {
        Field field = (Field)node;
        method.addStatement("formatField(d, '$L', $L, b)", field.ch(), field.width());
      }
    }
  }
  
  /**
   * Build a method that will format a date and time (named or skeleton) in a
   * localized wrapper.
   */
  private MethodSpec buildWrapperMethod(Format format) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("formatWrapped")
        .addAnnotation(Override.class)
        .addModifiers(PUBLIC)
        .addParameter(CALENDAR_FORMAT, "wrapperType")
        .addParameter(CALENDAR_FORMAT, "dateType")
        .addParameter(CALENDAR_FORMAT, "timeType")
        .addParameter(String.class, "dateSkel")
        .addParameter(String.class, "timeSkel")
        .addParameter(ZonedDateTime.class, "d")
        .addParameter(StringBuilder.class, "b");

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

    return method.build();
  }

  /**
   * Formats a date-time combination into a wrapper format, e.g. "{1} at {0}"
   */
  private void addWrapper(MethodSpec.Builder method, String pattern) {
    method.addComment("$S", pattern);
    for (Node node : WRAPPER_PARSER.parseWrapper(pattern)) {
      if (node instanceof Text) {
        method.addStatement("b.append($S)", ((Text)node).text());

      } else if (node instanceof Field) {
        Field field = (Field)node;
        switch (field.ch()) {
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
   * Implements the formatSkeleton method.
   */
  private MethodSpec buildSkeletonFormatter(List<Skeleton> skeletons) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("formatSkeleton")
        .addAnnotation(Override.class)
        .addModifiers(PUBLIC)
        .addParameter(String.class, "skeleton")
        .addParameter(ZonedDateTime.class, "d")
        .addParameter(StringBuilder.class, "b")
        .returns(boolean.class);

    method.beginControlFlow("if (skeleton == null)");
    method.addStatement("return false");
    method.endControlFlow();
    
    method.beginControlFlow("switch (skeleton)");

    // Skeleton patterns.
    for (Skeleton skeleton : skeletons) {
      method.beginControlFlow("case $S:", skeleton.skeleton)
        .addComment("Pattern: $S", skeleton.pattern);

      addPattern(method, skeleton.pattern);

      method.addStatement("break");
      method.endControlFlow();
    }

    method.beginControlFlow("default:");
    method.addStatement("return false");
    method.endControlFlow();

    method.endControlFlow();
    method.addStatement("return true");
    
    return method.build();
  }
  
  /**
   * Build methods to format date time intervals using the field of greatest difference.
   */
  private MethodSpec buildIntervalMethod(Map<String, Map<String, String>> intervalFormats, String fallback) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("formatInterval")
        .addAnnotation(Override.class)
        .addModifiers(PUBLIC)
        .addParameter(ZonedDateTime.class, "s")
        .addParameter(ZonedDateTime.class, "e")
        .addParameter(String.class, "k")
        .addParameter(DateTimeField.class, "f") 
        .addParameter(StringBuilder.class, "b");

    // Only enter the switches if both params are non-null.
    method.beginControlFlow("if (k != null && f != null)");
  
    // BEGIN switch (k)
    method.beginControlFlow("switch (k)");
    for (Map.Entry<String, Map<String, String>> format : intervalFormats.entrySet()) {
      String skeleton = format.getKey();
  
      // BEGIN "case skeleton:"
      method.beginControlFlow("case $S:", skeleton);
      method.beginControlFlow("switch (f)");
      
      for (Map.Entry<String, String> entry : format.getValue().entrySet()) {
        String field = entry.getKey();
        
        // Split the interval pattern on the boundary. We end up with two patterns, one for
        // start and end respectively.
        Pair<List<Node>, List<Node>> patterns = DATETIME_PARSER.splitIntervalPattern(entry.getValue());

        // BEGIN "case field:"
        // Render this pair of patterns when the given field matches.
        method.beginControlFlow("case $L:", DateTimeField.fromString(field));
        method.addComment("$S", DateTimePatternParser.render(patterns._1));
        addIntervalPattern(method, patterns._1, "s");
        method.addComment("$S", DateTimePatternParser.render(patterns._2));
        addIntervalPattern(method, patterns._2, "e");
        method.addStatement("return");
        method.endControlFlow(); 
        // END "case field:"
      }

      method.addStatement("default: break");
      method.endControlFlow(); // switch (f)
      method.addStatement("break");
      method.endControlFlow(); 
      // END "case skeleton:"
    }
    
    method.addStatement("default: break");
    method.endControlFlow();
    // END switch (k)

    // One of the parameters was null, or nothing matched, so render the
    // fallback, e.g. format the start / end separately as "{0} - {1}"
    addIntervalFallback(method, fallback);
    method.endControlFlow();

    return method.build();
  }
  
  /**
   * Adds code to format a date time interval pattern, which may be the start or end
   * date time, signified by the 'which' parameter.
   */
  private void addIntervalPattern(MethodSpec.Builder method, List<Node> pattern, String which) {
    for (Node node : pattern) {
      if (node instanceof Text) {
        method.addStatement("b.append($S)", ((Text)node).text());
      } else if (node instanceof Field) {
        Field field = (Field)node;
        method.addStatement("formatField($L, '$L', $L, b)", which, field.ch(), field.width());
      }
    }
  }
  
  /**
   * Adds code to render the fallback pattern.
   */
  private void addIntervalFallback(MethodSpec.Builder method, String pattern) {
    method.addComment("$S", pattern);
    for (Node node : WRAPPER_PARSER.parseWrapper(pattern)) {
      if (node instanceof Text) {
        method.addStatement("b.append($S)", ((Text)node).text());

      } else if (node instanceof Field) {
        Field field = (Field)node;
        String which = "s";
        if (field.ch() == '1') {
          which = "e";
        }
        method.addStatement("formatSkeleton(k, $L, b)", which);
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
    for (Node node : DATETIME_PARSER.parse(skeleton)) {
      if (node instanceof Field) {
        Field field = (Field) node;
        switch (field.ch()) {
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
        .returns(SKELETON);

    method.beginControlFlow("switch (skeleton)");
    
    for (String skel : dateSkeletons) {
      method.addCode("case $S:\n", skel);
    }
    method.addStatement("  return $T.DATE", SKELETON);
    
    for (String skel : timeSkeletons) {
      method.addCode("case $S:\n", skel);
    }
    method.addStatement("  return $T.TIME", SKELETON);

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
    b.append("$L = new $T(", fieldName, FIELD_VARIANTS);
    b.append("\n  ").append(v.abbreviated).comma();
    b.append("\n  ").append(v.narrow).comma();
    b.append("\n  ").append(v.short_).comma();
    b.append("\n  ").append(v.wide);
    b.append(")");
    method.addStatement(b.format(), b.args());
  }

  /**
   * Mapping locale identifiers to their localized exemplar cities.
   */
  private void buildTimeZoneExemplarCities(MethodSpec.Builder method, TimeZoneData data) {
    CodeBlock.Builder code = CodeBlock.builder();
    code.beginControlFlow("this.exemplarCities = new $T<$T, $T>() {", HashMap.class, String.class, String.class);
    for (TimeZoneInfo info : data.timeZoneInfo) {
      code.addStatement("put($S, $S)", info.zone, info.exemplarCity);
    }
    code.endControlFlow("}");
    method.addCode(code.build());
  }

  /**
   * Builds localized timezone name mapping.
   */
  private void buildTimeZoneNames(MethodSpec.Builder method, TimeZoneData data) {
    CodeBlock.Builder code = CodeBlock.builder();
    code.beginControlFlow("\nthis.$L = new $T<$T, $T>() {", "timezoneNames", 
        HASHMAP, STRING, TIMEZONE_NAMES);
    
    for (TimeZoneInfo info : data.timeZoneInfo) {
      if (info.nameLong == null && info.nameShort == null) {
        continue;
      }
      code.add("\nput($S, new $T($S, ", info.zone, TIMEZONE_NAMES, info.zone);
      if (info.nameLong == null) {
        code.add("  null,");
      } else {
        code.add("  new $T.Name($S, $S, $S),", TIMEZONE_NAMES, 
            info.nameLong.generic, info.nameLong.standard, info.nameLong.daylight);
      }
      if (info.nameShort == null) {
        code.add("\n  null");
      } else {
        code.add("\n  new $T.Name($S, $S, $S)", TIMEZONE_NAMES, 
            info.nameShort.generic, info.nameShort.standard, info.nameShort.daylight);
      }
      code.add("));\n");
    }
    code.endControlFlow("}");
    method.addCode(code.build());
  }
  
  /**
   * Builds localized metazone name mapping.
   */
  private void buildMetaZoneNames(MethodSpec.Builder method, TimeZoneData data) {
    CodeBlock.Builder code = CodeBlock.builder();
    code.beginControlFlow("\nthis.$L = new $T<$T, $T>() {", "metazoneNames", 
        HASHMAP, STRING, TIMEZONE_NAMES);
    
    for (MetaZoneInfo info : data.metaZoneInfo) {
      if (info.nameLong == null && info.nameShort == null) {
        continue;
      }
      code.add("\nput($S, new $T($S, ", info.zone, TIMEZONE_NAMES, info.zone);
      if (info.nameLong == null) {
        code.add("\n  null,");
      } else {
        code.add("\n  new $T.Name($S, $S, $S),", TIMEZONE_NAMES, 
            info.nameLong.generic, info.nameLong.standard, info.nameLong.daylight);
      }
      if (info.nameShort == null) {
        code.add("\n  null");
      } else {
        code.add("\n  new $T.Name($S, $S, $S)", TIMEZONE_NAMES, 
            info.nameShort.generic, info.nameShort.standard, info.nameShort.daylight);
      }
      code.add("));\n");
    }
    code.endControlFlow("}");
    method.addCode(code.build());
  }
  
  /**
   * Builds a method to format the timezone as hourFormat with a GMT wrapper.
   */
  private MethodSpec buildWrapTimeZoneGMTMethod(TimeZoneData data) {
    String[] hourFormat = data.hourFormat.split(";");
    List<Node> positive = DATETIME_PARSER.parse(hourFormat[0]);
    List<Node> negative = DATETIME_PARSER.parse(hourFormat[1]);
    List<Node> format = WRAPPER_PARSER.parseWrapper(data.gmtFormat);
    
    MethodSpec.Builder method = MethodSpec.methodBuilder("wrapTimeZoneGMT")
        .addModifiers(PROTECTED)
        .addParameter(StringBuilder.class, "b")
        .addParameter(boolean.class, "neg")
        .addParameter(int.class, "hours")
        .addParameter(int.class, "mins")
        .addParameter(boolean.class, "_short");

    // Special format for zero
    method.beginControlFlow("if (hours == 0 && mins == 0)");
    method.addStatement("b.append($S)", data.gmtZeroFormat);
    method.addStatement("return");
    method.endControlFlow();

    method.addStatement("boolean emitMins = !_short || mins > 0");

    for (Node node : format) {
      if (node instanceof Text) {
        Text text = (Text) node;
        method.addStatement("b.append($S)", text.text());
      } else {
        method.beginControlFlow("if (neg)");
        appendHourFormat(method, negative);
        method.endControlFlow();
        method.beginControlFlow("else");
        appendHourFormat(method, positive);
        method.endControlFlow();
      }
    }
    return method.build();
  }
  
  /**
   * Build a method to wrap a region in the regionFormat.
   */
  private MethodSpec buildWrapTimeZoneRegionMethod(TimeZoneData data) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("wrapTimeZoneRegion")
        .addModifiers(PROTECTED)
        .addParameter(StringBuilder.class, "b")
        .addParameter(String.class, "region");
        
    List<Node> format = WRAPPER_PARSER.parseWrapper(data.regionFormat);
    
    for (Node node : format) {
      if (node instanceof Text) {
        Text text = (Text) node;
        method.addStatement("b.append($S)", text.text());
      } else {
        method.addStatement("b.append(region)");
      }
    }
    
    return method.build();
  }
  
  /**
   * Appends code to emit the hourFormat for positive or negative.
   */
  private void appendHourFormat(MethodSpec.Builder method, List<Node> fmt) {
    for (Node n : fmt) {
      if (n instanceof Text) {
        String t = ((Text)n).text();
        boolean minute = t.equals(":") || t.equals(".");
        if (minute) {
          method.beginControlFlow("if (emitMins)");
        }
        method.addStatement("b.append($S)", t);
        if (minute) {
          method.endControlFlow();
        }
      } else {
        Field f = (Field)n;
        if (f.ch() == 'H') {
          if (f.width() == 1) {
            method.addStatement("zeroPad2(b, hours, 1)");
          } else {
            method.addStatement("zeroPad2(b, hours, _short ? 1 : $L)", f.width());
          }
        } else {
          method.beginControlFlow("if (emitMins)");
          method.addStatement("zeroPad2(b, mins, $L)", f.width());
          method.endControlFlow();
        }
      }
    }
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
