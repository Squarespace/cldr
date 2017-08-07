package com.squarespace.cldr.dates;


public enum DateTimeField {

  YEAR,
  MONTH,
  DAY,
  AM_PM,
  HOUR,
  MINUTE,
  SECOND
  ;

  public static DateTimeField fromString(String string) {
    switch (string) {
      case "y":
        return YEAR;
      case "M":
        return MONTH;
      case "d":
        return DAY;
      case "a":
        return AM_PM;
      case "h":
      case "H":
        return HOUR;
      case "m":
        return MINUTE;
      case "s":
      default:
        return SECOND;
    }
  }
}
