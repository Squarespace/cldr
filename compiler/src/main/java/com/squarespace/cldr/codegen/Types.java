package com.squarespace.cldr.codegen;

import static com.squareup.javapoet.ClassName.get;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;


public class Types {

  public static final String PACKAGE_CLDR = "com.squarespace.cldr";
  public static final String PACKAGE_CLDR_DATES = "com.squarespace.cldr.dates";
  public static final String PACKAGE_CLDR_NUMBERS = "com.squarespace.cldr.numbers";
  public static final String PACKAGE_CLDR_PLURALS = "com.squarespace.cldr.plurals";
  public static final String PACKAGE_CLDR_UNITS = "com.squarespace.cldr.units";
  public static final String PACKAGE_CLDR_PARSE = "com.squarespace.cldr.parse";
  
  public static final ClassName STRING = get("java.lang", "String");
  public static final ClassName LIST = get("java.util", "List");
  public static final ClassName MAP = get("java.util", "Map");
  public static final ClassName SET = get("java.util", "Set");
  public static final ClassName HASHMAP = get("java.util", "HashMap");
  public static final ClassName HASHSET = get("java.util", "HashSet");
  public static final ClassName ENUM_MAP = get("java.util", "EnumMap");
  
  public static final TypeName LIST_STRING = ParameterizedTypeName.get(LIST, STRING);
  public static final TypeName SET_STRING = ParameterizedTypeName.get(SET, STRING);
  
  public static final ClassName CLDR = get(PACKAGE_CLDR, "CLDR");
  public static final ClassName CLDR_BASE = get(PACKAGE_CLDR, "CLDRBase");
  public static final ClassName CLDR_LOCALE_IF = get(PACKAGE_CLDR, "CLDR", "Locale");
  public static final ClassName CLDR_CURRENCY_ENUM = get(PACKAGE_CLDR, "CLDR", "Currency");
  public static final ClassName META_LOCALE = get(PACKAGE_CLDR, "MetaLocale");
  public static final ClassName LANGUAGE_MATCH = get(PACKAGE_CLDR, "LanguageMatch");
  
  public static final TypeName LIST_LANGUAGE_MATCH = ParameterizedTypeName.get(LIST, LANGUAGE_MATCH);
  
  public static final ClassName PLURAL_RULES = get(PACKAGE_CLDR_PLURALS, "_PluralRules");

  public static final TypeName LIST_CLDR_LOCALE_IF = ParameterizedTypeName.get(LIST, CLDR_LOCALE_IF);

  public static final ClassName PLURAL_CATEGORY = get(PACKAGE_CLDR_PLURALS, "PluralCategory");
  public static final ClassName PLURAL_CONDITION = get("", "Condition");

  public static final ClassName NUMBER_FORMATTER_BASE = get(PACKAGE_CLDR_NUMBERS, "NumberFormatterBase");
  public static final ClassName NUMBER_OPERANDS = get(PACKAGE_CLDR_NUMBERS, "NumberOperands");
  
  public static final ClassName UNIT = get(PACKAGE_CLDR_UNITS, "Unit");
  public static final ClassName FIELDPATTERN_NODE = get(PACKAGE_CLDR_PARSE, "FieldPattern", "Node");

  public static final TypeName LIST_UNITPATTERN = ParameterizedTypeName.get(LIST, FIELDPATTERN_NODE);
  public static final TypeName MAP_UNIT_LIST_UNITPATTERN = ParameterizedTypeName.get(MAP, UNIT, LIST_UNITPATTERN);
  
  public static final ClassName CALENDAR_FORMAT = get(PACKAGE_CLDR_DATES, "CalendarFormat");
  public static final ClassName CALENDAR_FORMATTER = get(PACKAGE_CLDR_DATES, "CalendarFormatterBase");
  public static final ClassName SKELETON = get(PACKAGE_CLDR_DATES, "SkeletonType");
  public static final ClassName TIMEZONE_NAMES = get(PACKAGE_CLDR_DATES, "TimeZoneNames");
  public static final ClassName FIELD_VARIANTS = get(PACKAGE_CLDR_DATES, "FieldVariants");
  
}
