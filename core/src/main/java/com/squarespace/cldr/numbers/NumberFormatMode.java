package com.squarespace.cldr.numbers;


public enum NumberFormatMode {

  // Use whatever is defined in the pattern.
  DEFAULT,

  // Override the pattern's defaults with our fraction settings.
  FRACTIONS,

  // Override the pattern's defaults with significant digit settings.
  SIGNIFICANT
  ;

  public static NumberFormatMode fromString(String v) {
    switch (v) {
      case "fractions":
        return FRACTIONS;

      case "significant":
        return SIGNIFICANT;

      case "default":
      default:
        return DEFAULT;
    }
  }
}
