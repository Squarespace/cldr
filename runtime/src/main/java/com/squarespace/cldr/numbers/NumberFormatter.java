package com.squarespace.cldr.numbers;

import java.math.BigDecimal;
import java.util.List;

import com.squarespace.cldr.CLDR;
import com.squarespace.cldr.units.UnitFormatOptions;
import com.squarespace.cldr.units.UnitValue;


/**
 * Common interface for number formatters.
 */
public interface NumberFormatter {

  /**
   * Returns the bundle identifier associated with this formatter.
   */
  CLDR.Locale bundleId();

  /**
   * Return the symbol for a currency.
   */
  String getCurrencySymbol(CLDR.Currency code);

  /**
   * Return the localized narrow symbol for a currency.
   */
  String getNarrowCurrencySymbol(CLDR.Currency code);
  
  /**
   * Return the localized display name for a currency.
   */
  String getCurrencyDisplayName(CLDR.Currency code);
  
  /**
   * Format the number as a decimal, using the options, and write it to the buffer.
   */
  void formatDecimal(BigDecimal n, StringBuilder buf, DecimalFormatOptions options);

  /**
   * Format a number in localized units, with the given options, and write it to the buffer.
   */
  void formatUnit(UnitValue value, StringBuilder buf, UnitFormatOptions options);
  
  /**
   * Format a sequence of units together.
   */
  void formatUnits(List<UnitValue> values, StringBuilder destination, UnitFormatOptions options);
  
  /**
   * Format the number as a currency, using the options, and write it to the buffer.
   */
  void formatCurrency(BigDecimal n, CLDR.Currency code, StringBuilder buf, CurrencyFormatOptions options);
  
}
