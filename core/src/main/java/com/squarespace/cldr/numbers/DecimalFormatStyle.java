package com.squarespace.cldr.numbers;


public enum DecimalFormatStyle {

  DECIMAL,
  PERCENT,
  PERMILLE,
  SHORT,
  LONG
  ;

  public static DecimalFormatStyle fromString(String v) {
    switch (v) {
      case "percent":
        return PERCENT;

      case "permille":
        return PERMILLE;

      case "short":
        return SHORT;

      case "long":
        return LONG;

      case "standard":
      case "decimal":
      default:
          return DECIMAL;
    }
  }

}