package com.squarespace.cldr.numbers;


public enum NumberFormatStyle {

  DECIMAL,
  PERCENT,
  PERMILLE,
  SHORT,
  LONG
  ;

  public static NumberFormatStyle fromString(String v) {
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
