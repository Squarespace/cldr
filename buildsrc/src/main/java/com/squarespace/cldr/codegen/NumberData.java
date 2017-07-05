package com.squarespace.cldr.codegen;


/**
 * Container to hold CLDR number data.
 */
public class NumberData {

  private LocaleID id;

  private String decimal;

  private String group;

  private String list;

  private String percentSign;

  private String minusSign;

  private String plusSign;

  private String exponential;

  private String superscriptingExponent;

  private String perMille;

  private String infinity;

  private String nan;

  private String currencyDecimal;

  private String currencyGroup;

  private String timeSeparator;

  public NumberData() {
  }

  public void setID(LocaleID value) {
    this.id = value;
  }

  public LocaleID id() {
    return id;
  }

  public String decimal() {
    return decimal;
  }

  public void setDecimal(String value) {
    this.decimal = value;
  }

  public String group() {
    return group;
  }

  public void setGroup(String value) {
    this.group = value;
  }

  public String list() {
    return list;
  }

  public void setList(String value) {
    this.list = value;
  }

  public String percentSign() {
    return percentSign;
  }

  public void setPercentSign(String value) {
    this.percentSign = value;
  }

  public String minusSign() {
    return minusSign;
  }

  public void setMinusSign(String value) {
    this.minusSign = value;
  }

  public String plusSign() {
    return plusSign;
  }

  public void setPlusSign(String value) {
    this.plusSign = value;
  }

  public String exponential() {
    return exponential;
  }

  public void setExponential(String value) {
    this.exponential = value;
  }

  public String superscriptingExponent() {
    return superscriptingExponent;
  }

  public void setSuperscriptingExponent(String value) {
    this.superscriptingExponent = value;
  }

  public String perMille() {
    return perMille;
  }

  public void setPerMille(String value) {
    this.perMille = value;
  }

  public String infinity() {
    return infinity;
  }

  public void setInfinity(String value) {
    this.infinity = value;
  }

  public String nan() {
    return nan;
  }

  public void setNan(String value) {
    this.nan = value;
  }

  public String currencyDecimal() {
    return currencyDecimal;
  }

  public void setCurrencyDecimal(String value) {
    this.currencyDecimal = value;
  }

  public String currencyGroup() {
    return currencyGroup;
  }

  public void setCurrencyGroup(String value) {
    this.currencyGroup = value;
  }

  public String timeSeparator() {
    return timeSeparator;
  }

  public void setTimeSeparator(String value) {
    this.timeSeparator = value;
  }
}
