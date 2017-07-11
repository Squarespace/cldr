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
   * Formats a date using a given pattern. See CLDR "dateFormats"
   */
  void formatDate(FormatType type, ZonedDateTime d, StringBuilder b);

  /**
   * Formats a time using a given pattern. See CLDR "timeFormats"
   */
  void formatTime(FormatType type, ZonedDateTime d, StringBuilder b);
  
  /**
   * Formats a date and time (either a named format or a skeleton) using a localized wrapper.
   */
  void formatWrapped(FormatType wrapperType, FormatType dateType, FormatType timeType,
      String dateSkel, String timeSkel, ZonedDateTime d, StringBuilder b);

  /**
   * Formats using a skeleton pattern. See CLDR "dateTimeFormats / availableFormats".
   */
  boolean formatSkeleton(String skeleton, ZonedDateTime d, StringBuilder b);

  /**
   * Format a single field of a given width.
   */
  void formatField(ZonedDateTime d, StringBuilder b, char field, int width);

}
