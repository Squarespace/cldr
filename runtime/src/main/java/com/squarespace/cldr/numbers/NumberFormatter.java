package com.squarespace.cldr.numbers;

import java.math.BigDecimal;

import com.squarespace.cldr.CLDR;


/**
 * Common interface for number formatters.
 */
public interface NumberFormatter {

  /**
   * Returns the locale associated with this formatter.
   */
  CLDR.Locale locale();

  /**
   * Format the number as a decimal, using the options, and write it to the buffer.
   */
  void formatDecimal(BigDecimal n, StringBuilder buf, DecimalFormatOptions options);

  /**
   * Format the number as a currency, using the options, and write it to the buffer.
   */
  void formatCurrency(BigDecimal n, String currencyCode, StringBuilder buf, CurrencyFormatOptions options);
  
}
