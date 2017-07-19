package com.squarespace.cldr.numbers;


public enum NumberFormatMode {

  /**
   *  Use whatever is defined in the pattern, using the fraction settings
   *  to override the pattern defaults.
   */
  DEFAULT,

  /**
   * Ignore the pattern defaults and use only the significant digits settings
   * in the options.
   */
  SIGNIFICANT,

 /**
  * Use minimum significant digits but honor maximum fractions. This is intended
  * mainly for currency formatting in compact forms.
  *
  * In this mode, we use significant digits settings but honor the maximum fraction
  * digits setting of the pattern, or the user's override.
  *
  * For example, if minSig=1 and maxFrac=2 (as is the case with most currencies)
  * we would have:
  *
  *    1.00    -> "1 US dollar"
  *    1.0345  -> "1.03 US dollars"
  */
  SIGNIFICANT_MAXFRAC
  ;

  public static NumberFormatMode fromString(String v) {
    switch (v) {
      case "significant":
        return SIGNIFICANT;

      case "significant-maxfrac":
        return SIGNIFICANT_MAXFRAC;

      case "default":
      default:
        return DEFAULT;
    }
  }

}
