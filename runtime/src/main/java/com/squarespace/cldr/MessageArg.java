package com.squarespace.cldr;

import java.math.BigDecimal;


/**
 * Represents an argument to {@link MessageFormat}.
 */
public abstract class MessageArg {

  public MessageArg() {
  }

  /**
   * Resolve the value for this argument. The argument is a "thunk" that is
   * evaluated on demand. This allows for customizing how the value is
   * retrieved from some other environment, and avoiding fetching values that
   * may not be used in a particular call.
   */
  public abstract boolean resolve();

  /**
   * Retrieves this argument as a String. This is used for plural, datetime and
   * generic argument output, e.g. "{0}".
   */
  public abstract String asString();

  /**
   * Retrieves this argument as a BigDecimal. Returning null indicates this value
   * fails the cast. You can also choose to return a default if desired
   * (ZERO). This is used for number and currency formatting.
   *
   * Note: Since numeric arguments may be accessed multiple times, and BigDecimal
   * conversion from String involves a few copies, you may want to retain the
   * converted value in a field.
   */
  public abstract BigDecimal asBigDecimal();

  /**
   * Retrieves the currency code associated with this argument. This lets us format
   * amounts with multiple currencies in a single MessageFormat.
   *
   * Note: It is important that this be set correctly for all money values. If
   * a null currency is returned nothing will be output, since defaulting to
   * USD is not an option for money.
   */
  public abstract String currency();

}
