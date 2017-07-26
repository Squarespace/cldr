package com.squarespace.cldr.codegen.reader;

import java.util.List;
import java.util.Map;

import com.squarespace.cldr.codegen.LocaleID;
import com.squarespace.cldr.numbers.NumberPattern;


/**
 * Container to hold CLDR number data during code generation.
 */
public class NumberData {

  private LocaleID id;

  public String decimal;
  public String group;
  public String percent;
  public String minus;
  public String plus;
  public String exponential;
  public String superscriptingExponent;
  public String perMille;
  public String infinity;
  public String nan;
  public String currencyDecimal;
  public String currencyGroup;
  public int minimumGroupingDigits;

  public List<NumberPattern> decimalFormatStandard;
  public List<NumberPattern> percentFormatStandard;
  public List<NumberPattern> currencyFormatStandard;
  public List<NumberPattern> currencyFormatAccounting;

  public Map<String, List<NumberPattern>> decimalFormatShort;
  public Map<String, List<NumberPattern>> decimalFormatLong;
  public Map<String, List<NumberPattern>> currencyFormatShort;

  public Map<String, String> currencyUnitPattern;

  // MAPS BELOW KEYED BY CURRENCY CODE

  // Currency symbols in this locale, e.g. '$' in en-US or 'US$' in fr
  public Map<String, String> currencySymbols;

  // Default currency display name, e.g. as proper name "US Dollar"
  public Map<String, String> currencyDisplayName;

  // Pluralized currency display names in this locale, e.g. "US dollar" or "US dollars"
  public Map<String, Map<String, String>> currencyDisplayNames;

  public NumberData() {
  }

  public void setID(LocaleID value) {
    this.id = value;
  }

  public LocaleID id() {
    return id;
  }

}