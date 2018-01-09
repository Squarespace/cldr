package com.squarespace.cldr.numbers;


public enum DecimalFormatStyle {

  DECIMAL,
  PERCENT,
  PERCENT_SCALED,
  PERMILLE,
  PERMILLE_SCALED,
  SHORT,
  LONG
  ;

  public static DecimalFormatStyle fromString(String v) {
    switch (v) {
      case "percent":
        return PERCENT;

      case "percent-scaled":
        return PERCENT_SCALED;

      case "permille":
        return PERMILLE;

      case "permille-scaled":
        return PERMILLE_SCALED;

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
