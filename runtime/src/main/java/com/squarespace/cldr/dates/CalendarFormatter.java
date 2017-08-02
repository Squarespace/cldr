package com.squarespace.cldr.dates;

import java.time.ZonedDateTime;

import com.squarespace.cldr.CLDRLocale;


/**
 * Common interface for date time formatters.
 */
public interface CalendarFormatter {

  /**
   * Returns the locale associated with this formatter.
   */
  CLDRLocale locale();

  /**
   * Format a date time with the given options, writing the output to the buffer.
   */
  void format(ZonedDateTime datetime, CalendarFormatOptions options, StringBuilder buffer);

  /**
   * Format a single field of a given width.
   */
  void formatField(ZonedDateTime datetime, char field, int width, StringBuilder buffer);
  
}
