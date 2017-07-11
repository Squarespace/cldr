package com.squarespace.cldr.numbers;


public class NumberFormatterParams {

  protected String decimal = ".";
  protected String group = ",";
  protected String percent = "%";
  protected String minus = "-";
  protected String plus = "+";
  protected String exponential = "E";
  protected String superscriptingExponent = "×";
  protected String perMille = "‰";
  protected String infinity = "∞";
  protected String nan = "NaN";
  protected String currencyDecimal = ".";
  protected String currencyGroup = ",";
  protected int minimumGroupingDigits = 1;

  //Currently we only output Latin digits, but generalize it for future use.
  protected String[] digits = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

}
