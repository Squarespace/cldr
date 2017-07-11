package com.squarespace.cldr.numbers;


public enum CurrencyFormatStyle {

  SYMBOL,
  ACCOUNTING,
  CODE,
  NAME,
  SHORT
  ;

  public static CurrencyFormatStyle fromString(String v) {
    switch (v) {
      case "accounting":
        return ACCOUNTING;

      case "code":
        return CODE;

      case "name":
        return NAME;

      case "short":
        return SHORT;

      case "symbol":
      case "standard":
      default:
        return SYMBOL;
    }
  }

}
