package com.squarespace.cldr.dates;

import java.time.ZonedDateTime;

import com.squarespace.cldr.CLDRLocale;


/**
 * Format date/time fields in a given calendar. Formatting rules are implemented according to CLDR:
 *  http://www.unicode.org/reports/tr35/tr35-dates.html#Date_Field_Symbol_Table
 */
public abstract class CLDRCalendarFormatter {

  // TODO: complete, rename, etc
  // TODO: field order-invariant lookup
  // TODO: fuzzy skeleton matching (needed?)

  // Locale-specific data populated through code generation of the subclasses.
  protected CLDRLocale locale;
  protected int firstDay;
  protected int minDays;
  protected FieldVariants eras;
  protected FieldVariants quartersFormat;
  protected FieldVariants quartersStandalone;
  protected FieldVariants monthsFormat;
  protected FieldVariants monthsStandalone;
  protected FieldVariants weekdaysFormat;
  protected FieldVariants weekdaysStandalone;
  protected FieldVariants dayPeriodsFormat;
  protected FieldVariants dayPeriodsStandalone;

  public CLDRLocale locale() {
    return locale;
  }

  /**
   * Formats a date using a given pattern. See CLDR "dateFormats"
   */
  public abstract void formatDate(FormatType type, ZonedDateTime d, StringBuilder b);

  /**
   * Formats a time using a given pattern. See CLDR "timeFormats"
   */
  public abstract void formatTime(FormatType type, ZonedDateTime d, StringBuilder b);

  /**
   * Formats a date and time (either a named format or a skeleton) using a localized wrapper.
   */
  public abstract void formatWrapped(
      FormatType wrapperType, FormatType dateType, FormatType timeType,
      String dateSkel, String timeSkel, ZonedDateTime d, StringBuilder b);

  /**
   * Formats using a skeleton pattern. See CLDR "dateTimeFormats / availableFormats".
   */
  public abstract boolean formatSkeleton(String skeleton, ZonedDateTime d, StringBuilder b);


  /**
   * Formats a single field, based on the first character in the pattern string,
   * with repeated characters indicating the field width.
   */
  public void formatField(String pattern, ZonedDateTime d, StringBuilder b) {
    int length = pattern.length();
    if (length == 0) {
      return;
    }

    // Current position in pattern
    int i = 0;

    // Skip over prefix indicating we want to format a single field.
    if (pattern.charAt(i) == '+') {
      if (length == 1) {
        return;
      }
      i++;
    }

    int width = 0;
    char field = pattern.charAt(i);
    while (i < length) {
      if (pattern.charAt(i) != field) {
        break;
      }
      width++;
      i++;
    }

    formatField(d, b, field, width);
  }

  /**
   * Format a single field of a given width.
   */
  public void formatField(ZonedDateTime d, StringBuilder b, char field, int width) {
    switch (field) {
      case 'G':
        formatEra(b, d, width, eras);
        break;

      case 'y':
        formatYear(b, d, width);
        break;

      case 'Y':
        formatYearWeekOfYear(b, d, width);
        break;

      case 'u':
      case 'U':
      case 'r':
        // only used for non-Gregorian calendars
        break;

      case 'Q':
        formatQuarter(b, d, width, quartersFormat);
        break;

      case 'q':
        formatQuarter(b, d, width, quartersStandalone);
        break;

      case 'M':
        formatMonth(b, d, width, monthsFormat);
        break;

      case 'L':
        formatMonth(b, d, width, monthsStandalone);
        break;

      case 'l':
        // deprecated, ignore
        break;

      case 'w':
        formatWeekOfYear(b, d, width);
        break;

      case 'W':
        formatWeekOfMonth(b, d, width);
        break;

      case 'd':
        formatDayOfMonth(b, d, width);
        break;

      case 'D':
        formatDayOfYear(b, d, width);
        break;

      case 'F':
        formatDayOfWeekInMonth(b, d, width);
        break;

      case 'g':
        // modified julian day
        break;

      case 'E':
        formatWeekday(b, d, width, weekdaysFormat);
        break;

      case 'e':
        formatLocalWeekday(b, d, width, weekdaysFormat, firstDay);
        break;

      case 'c':
        formatLocalWeekdayStandalone(b, d, width, weekdaysStandalone, firstDay);
        break;

      case 'a':
        formatDayPeriod(b, d, width, dayPeriodsFormat);
        break;

      case 'b':
      case 'B':
        // TODO
        break;

      case 'h':
        formatHours(b, d, width, true);
        break;

      case 'H':
        formatHours(b, d, width, false);
        break;

      case 'K':
        break;

      case 'k':
      case 'j':
      case 'J':
      case 'C':
        break;

      case 'm':
        formatMinutes(b, d, width);
        break;

      case 's':
        formatSeconds(b, d, width);
        break;

      case 'S':
        formatFractionalSeconds(b, d, width);
        break;

      case 'A':
        // not implemented
        break;

      case 'z':
        formatTimeZone(b, d, width);
        break;

      case 'Z':
      case 'O':
      case 'v':
      case 'V':
      case 'X':
      case 'x':
        // TODO:
        break;
    }
  }

  /**
   * Format the era based on the year.
   */
  void formatEra(StringBuilder b, ZonedDateTime d, int width, FieldVariants eras) {
    int year = d.getYear();
    int index = year < 0 ? 0 : 1;
    switch (width) {
      case 5:
        b.append(eras.narrow[index]);
        break;

      case 4:
        b.append(eras.wide[index]);
        break;

      case 3:
      case 2:
      case 1:
        b.append(eras.abbreviated[index]);
        break;
    }
  }

  /**
   * Format the numeric year, zero padding as necessary.
   */
  void formatYear(StringBuilder b, ZonedDateTime d, int width) {
    int year = d.getYear();
    if (width == 2) {
      year %= 100;
    }

    // Note: revisit this code in year 99,999.
    int digits = year >= 10000 ? 5 : year >= 1000 ? 4 : year >= 100 ? 3 : year >= 10 ? 2 : 1;

    // Pad leading '0' digits
    if (width > 1) {
      int zeroes = width - digits;
      for (int i = 0; i < zeroes; i++) {
        b.append('0');
      }
    }
    b.append(year);
  }

  /**
   *
   */
  void formatYearWeekOfYear(StringBuilder b, ZonedDateTime d, int width) {
    throw new RuntimeException("TODO: implement me");
  }

  /**
   * Format the quarter based on the month.
   */
  void formatQuarter(StringBuilder b, ZonedDateTime d, int width, FieldVariants quarters) {
    int quarter = (d.getMonth().getValue() - 1) / 3;
    switch (width) {
      case 5:
        b.append(quarters.narrow[quarter]);
        break;

      case 4:
        b.append(quarters.wide[quarter]);
        break;

      case 3:
        b.append(quarters.abbreviated[quarter]);
        break;

      case 2:
        b.append('0');
        // fall through

      case 1:
        b.append(quarter + 1);
        break;
    }
  }

  /**
   * Format the month, numeric or a string name variant.
   */
  void formatMonth(StringBuilder b, ZonedDateTime d, int width, FieldVariants months) {
    int month = d.getMonth().getValue();
    switch (width) {
      case 5:
        b.append(months.narrow[month-1]);
        break;

      case 4:
        b.append(months.wide[month-1]);
        break;

      case 3:
        b.append(months.abbreviated[month-1]);
        break;

      case 2:
        if (month < 10) {
          b.append('0');
        }
        // fall through

      case 1:
        b.append(month);
        break;
    }
  }

  /**
   * Format the week number of the month.
   */
  void formatWeekOfMonth(StringBuilder b, ZonedDateTime d, int width) {
    throw new RuntimeException("TODO: implement me");
  }

  /**
   * Format the week number of the year.
   */
  void formatWeekOfYear(StringBuilder b, ZonedDateTime d, int width) {
    throw new RuntimeException("TODO: implement me");
  }

  /**
   * Format the weekday name.
   */
  void formatWeekday(StringBuilder b, ZonedDateTime d, int width, FieldVariants weekdays) {
    int weekday = d.getDayOfWeek().getValue() % 7;
    switch (width) {
      case 6:
        b.append(weekdays.short_[weekday]);
        break;

      case 5:
        b.append(weekdays.narrow[weekday]);
        break;

      case 4:
        b.append(weekdays.wide[weekday]);
        break;

      case 3:
      case 2:
      case 1:
        b.append(weekdays.abbreviated[weekday]);
        break;
    }
  }

  /**
   * Format the numeric weekday, or the format name variant.
   */
  void formatLocalWeekday(StringBuilder b, ZonedDateTime d, int width, FieldVariants weekdays, int firstDay) {
    if (width > 2) {
      formatWeekday(b, d, width, weekdays);
      return;
    }
    if (width == 2) {
      b.append('0');
    }
    formatWeekdayNumeric(b, d, firstDay);
  }

  /**
   * Format the numeric weekday, or the stand-alone name variant.
   */
  void formatLocalWeekdayStandalone(StringBuilder b, ZonedDateTime d, int width, FieldVariants weekdays, int firstDay) {
    if (width > 2) {
      formatWeekday(b, d, width, weekdays);
      return;
    }
    formatWeekdayNumeric(b, d, firstDay);
  }

  /**
   * Convert from Java's ISO-8601 week number, where Monday = 1 and Sunday = 7.
   * We need to adjust this according to the locale's "first day of the week" which
   * in the US is Sunday = 0.
   *
   * In the US, Tuesday will produce '3' or the 3rd day of the week:
   *
   *   weekday = 2  (ISO-8601 Tuesday == 2)
   *   int w = (7 - 0 + weekday) % 7 + 1
   *   w == 3
   *
   * In the US, Saturday will produce '7', ending the week:
   *
   *   weekday = 6  (ISO-8601 Saturday == 6)
   *   int w = (7 - 0 + weekday) % 7 + 1
   *   w == 7
   *
   */
  private void formatWeekdayNumeric(StringBuilder b, ZonedDateTime d, int firstDay) {
    // Java returns ISO-8601 where Monday = 1 and Sunday = 7.
    int weekday = d.getDayOfWeek().getValue();

    // Adjust to the localized "first day of the week"
    int w = (7 - firstDay + weekday) % 7 + 1;
    b.append(w);
  }

  /**
   * Format the day of the month, optionally zero-padded.
   */
  void formatDayOfMonth(StringBuilder b, ZonedDateTime d, int width) {
    int day = d.getDayOfMonth();
    zeroPad2(b, day, width);
  }

  /**
   * Format the 3-digit day of the year, zero padded.
   */
  void formatDayOfYear(StringBuilder b, ZonedDateTime d, int width) {
    int day = d.getDayOfYear();
    int digits = day < 10 ? 1 : day < 100 ? 2 : 3;
    switch (digits) {
      case 1:
        if (width > 1) {
          b.append('0');
        }
        // fall through

      case 2:
        if (width > 2) {
          b.append('0');
        }
        // fall through

      case 3:
        b.append(day);
        break;
    }
  }

  /**
   * Numeric day of week in month, as in "2nd Wednesday in July".
   */
  void formatDayOfWeekInMonth(StringBuilder b, ZonedDateTime d, int width) {
    int day = ((d.getDayOfMonth() - 1) / 7) + 1;
    b.append(day);
  }

  /**
   * Format the day period variant.
   */
  void formatDayPeriod(StringBuilder b, ZonedDateTime d, int width, FieldVariants dayPeriods) {
    int hours = d.getHour();
    int index = hours < 12 ? 0 : 1;
    switch (width) {
      case 5:
        b.append(dayPeriods.narrow[index]);
        break;

      case 4:
        b.append(dayPeriods.wide[index]);
        break;

      case 3:
      case 2:
      case 1:
        b.append(dayPeriods.abbreviated[index]);
        break;
    }
  }

  /**
   * Format the hours in 12- or 24-hour format, optionally zero-padded.
   */
  void formatHours(StringBuilder b, ZonedDateTime d, int width, boolean truncate) {
    int hours = d.getHour();
    if (truncate && hours > 12) {
      hours = hours - 12;
    }
    zeroPad2(b, hours, width);
  }

  /**
   * Format the minutes, optionally zero-padded.
   */
  void formatMinutes(StringBuilder b, ZonedDateTime d, int width) {
    zeroPad2(b, d.getMinute(), width);
  }

  /**
   * Format the seconds, optionally zero-padded.
   */
  void formatSeconds(StringBuilder b, ZonedDateTime d, int width) {
    zeroPad2(b, d.getSecond(), width);
  }

  /**
   * Format fractional seconds up to N digits.
   */
  void formatFractionalSeconds(StringBuilder b, ZonedDateTime d, int width) {
    // format up to 9 digits at nanosecond resolution.
    int nano = d.getNano();
    int f = 100000000;
    while (width > 0 && f > 0) {
      int digit = nano / f;
      nano -= (digit * f);
      f /= 10;
      b.append(digit);
      width--;
    }

    // fill out any trailing zeros if any are requested
    while (width > 0) {
      b.append('0');
      width--;
    }
  }

  /**
   *
   */
  void formatTimeZone(StringBuilder b, ZonedDateTime d, int width) {
// TODO: complete timezone implementation
//    ZoneId zone = d.getZone();
  }

  /**
   * Format 2-digit number with 0-padding.
   */
  void zeroPad2(StringBuilder b, int value, int width) {
    switch (width) {
      case 2:
        if (value < 10) {
          b.append('0');
        }
        // fall through

      case 1:
        b.append(value);
        break;
    }
  }

}
