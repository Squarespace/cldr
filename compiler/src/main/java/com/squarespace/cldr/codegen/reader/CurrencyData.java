package com.squarespace.cldr.codegen.reader;


/**
 * Container to hold supplemental CLDR currency data during code generation.
 */
public class CurrencyData {

  public String code;

  // decimal digits
  public int digits;

  public CurrencyData() {
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String code() {
    return code;
  }

}
