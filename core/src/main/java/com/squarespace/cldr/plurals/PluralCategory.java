package com.squarespace.cldr.plurals;

import static java.lang.String.format;


public enum PluralCategory {

  ZERO,
  ONE,
  TWO,
  FEW,
  MANY,
  OTHER
  ;

  public static PluralCategory fromString(String name) {
    switch (name) {
      case "zero":
        return ZERO;
      case "one":
        return ONE;
      case "two":
        return TWO;
      case "few":
        return FEW;
      case "many":
        return MANY;
      case "other":
        return OTHER;
      default:
        throw new IllegalArgumentException(format("%s is not a valid plural category", name));
    }
  }
}
