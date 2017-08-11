package com.squarespace.cldr.codegen;

import static com.squareup.javapoet.ClassName.get;

import java.util.List;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;


public class Types {

  public static final String PACKAGE_CLDR = "com.squarespace.cldr";
  public static final String PACKAGE_CLDR_DATES = "com.squarespace.cldr.dates";
  public static final String PACKAGE_CLDR_NUMBERS = "com.squarespace.cldr.numbers";
  public static final String PACKAGE_CLDR_PLURALS = "com.squarespace.cldr.plurals";

  public static final ClassName STRING = get("java.lang", "String");
  public static final ClassName LIST = get("java.util", "List");
  public static final ClassName MAP = get("java.util", "Map");
  public static final ClassName HASHMAP = get("java.util", "HashMap");

  public static final TypeName LIST_STRING = ParameterizedTypeName.get(List.class, String.class);

  public static final ClassName CLDR = get(PACKAGE_CLDR, "CLDR");
  public static final ClassName CLDR_BASE = get(PACKAGE_CLDR, "CLDRBase");
  public static final ClassName CLDR_LOCALE_IF = get(PACKAGE_CLDR, "CLDR", "Locale");
//  public static final ClassName CLDR_LOCALE_CLS = get(PACKAGE_CLDR, "CLDR", "_Locale");
//  public static final ClassName LOCALE = get(PACKAGE_CLDR, "Locale");
  public static final ClassName META_LOCALE = get(PACKAGE_CLDR, "MetaLocale");

//  public static final ClassName CLDR_LOCALE_BASE_CLS = get(PACKAGE_CLDR, "_LocaleBase");

  public static final ClassName PLURAL_RULES = get(PACKAGE_CLDR_PLURALS, "_PluralRules");

  public static final TypeName LIST_CLDR_LOCALE_IF = ParameterizedTypeName.get(LIST, CLDR_LOCALE_IF);

  public static final ClassName PLURAL_CATEGORY = get(PACKAGE_CLDR_PLURALS, "PluralCategory");
  public static final ClassName PLURAL_CONDITION = get("", "Condition");

  public static final ClassName NUMBER_FORMATTER_BASE = get(PACKAGE_CLDR_NUMBERS, "NumberFormatterBase");
  public static final ClassName NUMBER_OPERANDS = get(PACKAGE_CLDR_NUMBERS, "NumberOperands");
  
  public static final ClassName CALENDAR_FORMAT = get(PACKAGE_CLDR_DATES, "CalendarFormat");
  public static final ClassName CALENDAR_FORMATTER = get(PACKAGE_CLDR_DATES, "CalendarFormatterBase");
  public static final ClassName SKELETON = get(PACKAGE_CLDR_DATES, "SkeletonType");
  public static final ClassName TIMEZONE_NAMES = get(PACKAGE_CLDR_DATES, "TimeZoneNames");
  public static final ClassName FIELD_VARIANTS = get(PACKAGE_CLDR_DATES, "FieldVariants");
  
  
}
