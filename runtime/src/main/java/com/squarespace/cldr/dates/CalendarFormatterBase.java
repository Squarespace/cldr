package com.squarespace.cldr.dates;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.time.zone.ZoneRules;
import java.time.zone.ZoneRulesException;
import java.util.Map;

import com.squarespace.cldr.CLDR;
import com.squarespace.cldr.dates.TimeZoneNames.Name;


/**
 * Format date/time fields in a given calendar. Formatting rules are implemented according to CLDR:
 *  http://www.unicode.org/reports/tr35/tr35-dates.html#Date_Field_Symbol_Table
 */
public abstract class CalendarFormatterBase implements CalendarFormatter {

  // TODO: complete, rename, etc
  // TODO: field order-invariant lookup
  // TODO: fuzzy skeleton matching (needed?)

  // Locale-specific data populated through code generation of the subclasses.
  protected CLDR.Locale bundleId;
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
  protected String gmtZeroFormat;
  protected Map<String, String> exemplarCities;
  protected Map<String, TimeZoneNames> timezoneNames;
  protected Map<String, TimeZoneNames> metazoneNames;
  
  public CLDR.Locale bundleId() {
    return bundleId;
  }
  
  /**
   * Formats a date using a given pattern. See CLDR "dateFormats"
   */
  protected abstract void formatDate(CalendarFormat type, ZonedDateTime d, StringBuilder b);

  /**
   * Formats a time using a given pattern. See CLDR "timeFormats"
   */
  protected abstract void formatTime(CalendarFormat type, ZonedDateTime d, StringBuilder b);

  /**
   * Formats a date and time (either a named format or a skeleton) using a localized wrapper.
   */
  protected abstract void formatWrapped(
      CalendarFormat wrapperType, CalendarFormat dateType, CalendarFormat timeType,
      String dateSkel, String timeSkel, ZonedDateTime d, StringBuilder b);

  /**
   * Formats using a skeleton pattern. See CLDR "dateTimeFormats / availableFormats".
   */
  protected abstract boolean formatSkeleton(String skeleton, ZonedDateTime d, StringBuilder b);

  /**
   * Wraps the GMT hours and minutes, e.g. "GMT{0}" -> "GMT-04:00".
   */
  protected abstract void wrapTimeZoneGMT(StringBuilder b, boolean negative, int hours, int mins, boolean _short);
  
  /**
   * Wraps a location in the region format, e.g. "{0} Time" -> "Los Angeles Time"
   */
  protected abstract void wrapTimeZoneRegion(StringBuilder b, String region);
  
  /**
   * Format a date time interval using the given interval skeleton. Selects the best
   * pattern to use for the given skeleton based on the field of greatest difference
   * between the start and end date-time.
   */
  protected abstract void formatInterval(
      ZonedDateTime start, ZonedDateTime end,
      String skeleton, DateTimeField field, StringBuilder buffer);
  
  @Override
  public String getWeekdayName(DayOfWeek dayOfWeek, FieldWidth width) {
    if (dayOfWeek == null) {
      dayOfWeek = DayOfWeek.SUNDAY;
    }
    if (width == null) {
      width = FieldWidth.WIDE;
    }
    int weekday = dayOfWeek.getValue() % 7;
    switch (width) {
      case ABBREVIATED:
        return weekdaysStandalone.abbreviated[weekday];
      case NARROW:
        return weekdaysStandalone.narrow[weekday];
      case SHORT:
        return weekdaysStandalone.short_[weekday];
      case WIDE:
      default:
        return weekdaysStandalone.wide[weekday];
    }
  }
  
  @Override
  public String getMonthName(Month monthName, FieldWidth width) {
    if (monthName == null) {
      monthName = Month.JANUARY;
    }
    if (width == null) {
      width = FieldWidth.WIDE;
    }
    int month = monthName.getValue() - 1;
    switch (width) {
      case ABBREVIATED:
      case SHORT:
        return monthsStandalone.abbreviated[month];
      case NARROW:
        return monthsStandalone.narrow[month];
      case WIDE:
      default:
        return monthsStandalone.wide[month];  
    }
  }
  
  /**
   * Main entry point for date time formatting.
   */
  @Override
  public void format(ZonedDateTime datetime, CalendarFormatOptions options, StringBuilder buffer) {
    String dateSkeleton = options.dateSkeleton() == null ? null : options.dateSkeleton().skeleton();
    String timeSkeleton = options.timeSkeleton() == null ? null : options.timeSkeleton().skeleton();

    CalendarFormat wrapperFormat = options.wrapperFormat();
    CalendarFormat dateFormat = options.dateFormat();
    CalendarFormat timeFormat = options.timeFormat();
    if (wrapperFormat != null) {
      // If wrapper is set it implies we want to output both a date and a time. If either is unset
      // we need to set it to the wrapper's format, unless a skeleton is defined.
      dateFormat = dateFormat != null ? dateFormat : (dateSkeleton == null ? wrapperFormat : null);
      timeFormat = timeFormat != null ? timeFormat : (timeSkeleton == null ? wrapperFormat : null);
    } else {
      // If wrapper is null, attempt to default it using the date's format or SHORT.
      if ((dateFormat != null || dateSkeleton != null) && (timeFormat != null || timeSkeleton != null)) {
        wrapperFormat = dateFormat != null ? dateFormat : CalendarFormat.SHORT;
      }
    }

    if (wrapperFormat != null) {
      formatWrapped(wrapperFormat, dateFormat, timeFormat, dateSkeleton, timeSkeleton, datetime, buffer);
      
    } else if (options.dateFormat() != null) {
      formatDate(options.dateFormat(), datetime, buffer);

    } else if (options.timeFormat() != null) {
      formatTime(options.timeFormat(), datetime, buffer);
    
    } else {
      String skeleton = dateSkeleton != null ? dateSkeleton : timeSkeleton;

      // If every property is null, fall back to this default "yMd"
      if (skeleton == null) {
        skeleton = CalendarSkeleton.yMd.skeleton();
      }
      formatSkeleton(skeleton, datetime, buffer);
    }
  }

  /**
   * Format a date time interval, guessing at the best skeleton to use based on the field
   * of greatest difference between the start and end date-time.  If the end date-time has
   * a different time zone than the start, this is corrected for comparison. If the
   * skeleton is null, one is selected automatically using the field of greatest difference.
   * 
   * Greatest difference is calculated by comparing fields in the following order:
   * 
   *    year, month, date, day-of-week, am-pm, hour, hour-of-day, minute, and second
   */
  @Override
  public void format(
      ZonedDateTime start, ZonedDateTime end, DateTimeIntervalSkeleton skeleton, StringBuilder buffer) {
    
    DateTimeField field = CalendarFormattingUtils.greatestDifference(start, end);
    if (skeleton == null) {
      switch (field) {
        case YEAR:
        case MONTH:
        case DAY:
          skeleton = DateTimeIntervalSkeleton.yMMMd;
          break;
        
        default:
          skeleton = DateTimeIntervalSkeleton.hmv;
          break;
      }
    }
    formatInterval(start, end, skeleton.skeleton(), field, buffer);
  }
  
  
  /**
   * Retrieves the exemplar city for a timezone, e.g. "America/Los_Angeles" -> "Los Angeles".
   */
  public String getExemplarCity(String zoneId) {
    return exemplarCities.get(zoneId);
  }
  
  public String resolveTimeZoneId(String zoneId) {
    String alias = _CalendarUtils.getTimeZoneAlias(zoneId);
    if (alias != null) {
      zoneId = alias;
    }
    alias = TimeZoneAliases.getAlias(zoneId);
    if (alias != null) {
      zoneId = alias;
    }
    return zoneId;
  }
  
  /**
   * Lookup the time zone name variants (long or short) for the given zoneId and datetime.
   */
  public Name getTimeZoneName(String zoneId, ZonedDateTime date, boolean long_) {
    TimeZoneNames names = timezoneNames.get(zoneId);
    if (names != null) {
      return long_ ? names.longName() : names.shortName();
    }
    
    zoneId = _CalendarUtils.getMetazone(zoneId, date);
    if (zoneId == null) {
      return null;
    }
    names = metazoneNames.get(zoneId);
    if (names != null) {
      return long_ ? names.longName() : names.shortName();
    }
    return null;
  }

  /**
   * Formats a single field, based on the first character in the pattern string,
   * with repeated characters indicating the field width.
   */
  public void formatField(ZonedDateTime datetime, String pattern, StringBuilder buffer) {
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

    formatField(datetime, field, width, buffer);
  }

  /**
   * Format a single field of a given width.
   */
  public void formatField(ZonedDateTime datetime, char field, int width, StringBuilder buffer) {
    switch (field) {
      case 'G':
        formatEra(buffer, datetime, width, eras);
        break;

      case 'y':
        formatYear(buffer, datetime, width);
        break;

      case 'Y':
        formatISOYearWeekOfYear(buffer, datetime, width);
        break;

      case 'u':
      case 'U':
      case 'r':
        // Only used for non-Gregorian calendars
        break;

      case 'Q':
        formatQuarter(buffer, datetime, width, quartersFormat);
        break;

      case 'q':
        formatQuarter(buffer, datetime, width, quartersStandalone);
        break;

      case 'M':
        formatMonth(buffer, datetime, width, monthsFormat);
        break;

      case 'L':
        formatMonth(buffer, datetime, width, monthsStandalone);
        break;

      case 'l':
        // deprecated, ignore
        break;

      case 'w':
        formatISOWeekOfYear(buffer, datetime, width);
        break;

      case 'W':
        formatWeekOfMonth(buffer, datetime, width);
        break;

      case 'd':
        formatDayOfMonth(buffer, datetime, width);
        break;

      case 'D':
        formatDayOfYear(buffer, datetime, width);
        break;

      case 'F':
        formatDayOfWeekInMonth(buffer, datetime, width);
        break;

      case 'g':
        // modified julian day
        break;

      case 'E':
        formatWeekday(buffer, datetime, width, weekdaysFormat);
        break;

      case 'e':
        formatLocalWeekday(buffer, datetime, width, weekdaysFormat, firstDay);
        break;

      case 'c':
        formatLocalWeekdayStandalone(buffer, datetime, width, weekdaysStandalone, firstDay);
        break;

      case 'a':
        formatDayPeriod(buffer, datetime, width, dayPeriodsFormat);
        break;

      case 'b':
        // Not yet in use.
        // TODO: day periods: am, pm, noon, midnight
        break;
        
      case 'B':
        // Not yet in use.
        // TODO: flexible day periods: "at night"
        break;

      case 'h':
        formatHours(buffer, datetime, width, true);
        break;

      case 'H':
        formatHours(buffer, datetime, width, false);
        break;

      case 'K':
        formatHoursAlt(buffer, datetime, width, true);
        break;

      case 'k':
        formatHoursAlt(buffer, datetime, width, false);
        break;
        
      case 'j':
      case 'J':
      case 'C':
        // Input skeleton symbols, not implemented.
        break;

      case 'm':
        formatMinutes(buffer, datetime, width);
        break;

      case 's':
        formatSeconds(buffer, datetime, width);
        break;

      case 'S':
        formatFractionalSeconds(buffer, datetime, width);
        break;

      case 'A':
        // not implemented
        break;

      case 'z':
        formatTimeZone_z(buffer, datetime, width);
        break;

      case 'Z':
        formatTimeZone_Z(buffer, datetime, width);
        break;
        
      case 'O':
        formatTimeZone_O(buffer, datetime, width);
        break;
        
      case 'v':
        formatTimeZone_v(buffer, datetime, width);
        break;
        
      case 'V':
        formatTimeZone_V(buffer, datetime, width);
        break;
        
      case 'X':
      case 'x':
        formatTimeZone_X(buffer, datetime, width, field);
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
    _formatYearValue(b, year, width);
  }

  /**
   * Formats the year according to ISO week-year.
   */
  void formatISOYearWeekOfYear(StringBuilder b, ZonedDateTime d, int width) {
    int year = d.get(IsoFields.WEEK_BASED_YEAR);
    _formatYearValue(b, year, width);
  }
  
  void _formatYearValue(StringBuilder b, int year, int width) {
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
    int w = d.get(ChronoField.ALIGNED_WEEK_OF_MONTH);
    if (width == 1) {
      b.append(w);
    }
  }

  /**
   * Format the week number of the year.
   */
  void formatISOWeekOfYear(StringBuilder b, ZonedDateTime d, int width) {
    int w = d.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
    switch (width) {
      case 2:
        zeroPad2(b, w, 2);
        break;
        
      case 1:
        b.append(w);
        break;
    }
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
  void formatHours(StringBuilder b, ZonedDateTime d, int width, boolean twelveHour) {
    int hours = d.getHour();
    if (twelveHour && hours > 12) {
      hours = hours - 12;
    }
    if (twelveHour && hours == 0) {
      hours = 12;
    }
    zeroPad2(b, hours, width);
  }

  /**
   * Format hours in 0-11 and 1-24 format, optionally zero-padded.
   */
  void formatHoursAlt(StringBuilder b, ZonedDateTime d, int width, boolean twelveHour) {
    int hours = d.getHour();
    if (twelveHour) {
      if (hours >= 12) {
        hours = hours - 12;
      }
    } else {
      hours++;
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
   * Format timezone in localized GMT format for field 'O'.
   */
  void formatTimeZone_O(StringBuilder b, ZonedDateTime d, int width) {
    int[] tz = getTzComponents(d);
    switch (width) {
      case 1:
        wrapTimeZoneGMT(b, tz[TZNEG] == -1, tz[TZHOURS], tz[TZMINS], true);
        break;
        
      case 4:
        wrapTimeZoneGMT(b, tz[TZNEG] == -1, tz[TZHOURS], tz[TZMINS], false);
        break;
    }
  }
  
  /**
   * Timezone as short / long generic non-location format.
   */
  void formatTimeZone_v(StringBuilder b, ZonedDateTime d, int width) {
    if (width != 1 && width != 4) {
      return;
    }
    
    ZoneId zone = d.getZone();
    Name variants = getTimeZoneName(zone.getId(), d, width == 4);
    String name = variants == null ? null : variants.generic();

    if (name != null) {
      b.append(name);
    } else {
      // Falls back to 'O' or 'OOOO'.
      formatTimeZone_O(b, d, width);
    }
  }
  
  /**
   * Timezone as short / long ids and exemplar city.
   */
  void formatTimeZone_V(StringBuilder b, ZonedDateTime d, int width) {
    switch (width) {
      case 4:
      {
        // "VVVV" - Generic location format, e.g. "Los Angeles Time".
        String zoneId = d.getZone().getId();
        String region = getExemplarCity(zoneId);
        if (region != null) {
          wrapTimeZoneRegion(b, region);
        }
        break;
      }
      
      case 3:
      {
        // "VVV" - Exemplar city (location) for the time zone, e.g. "Los Angeles"
        String zoneId = d.getZone().getId();
        String city = getExemplarCity(zoneId);
        if (city != null) {
          b.append(city);
        }
        break;
      }
      
      case 2:
        // "VV" - Long time zone ID, e.g. "America/Los_Angeles"
        String zoneId = d.getZone().getId();
        zoneId = resolveTimeZoneId(zoneId);
        if (zoneId != null) {
          b.append(zoneId);
        }
        break;
      
      case 1:
        // "V" - Short time zone ID, e.g. "uslax". 
        // Not available in the JSON CLDR data, so appending "unk".
        b.append("unk");
        break;
    }
  }
  
  /**
   * Format timezone in ISO8601 basic or extended format for field 'x' or 'X'.
   * http://www.unicode.org/reports/tr35/tr35-dates.html#dfst-zone
   */
  void formatTimeZone_X(StringBuilder b, ZonedDateTime d, int width, char ch) {
    int[] tz = getTzComponents(d);
    
    // Emit a 'Z' by itself for X if time is exactly GMT
    if (ch == 'X' && tz[TZOFFSET] == 0) {
      b.append('Z');
      return;
    }

    switch (width) {
      case 5:
      case 4:
      case 3:
      case 2:
      case 1:
        if (tz[TZNEG] == -1) {
          b.append('-');
        } else {
          b.append('+');
        }

        zeroPad2(b, tz[TZHOURS], 2);
        
        // Delimiter is omitted for X, XX and XXXX
        if (width == 3 || width == 5) {
          b.append(':');
        }
        int mins = tz[TZMINS];

        // Minutes are optional for X
        if (width != 1 || mins > 0) {
          zeroPad2(b, mins, 2);
        }
        break;
    }
  }
  
  /**
   * Format a time zone using a non-location format.
   */
  void formatTimeZone_z(StringBuilder b, ZonedDateTime d, int width) {
    if (width > 4) {
      return;
    }

    ZoneId zone = d.getZone();
    ZoneRules zoneRules = null;
    try {
      zoneRules = zone.getRules();
    } catch (ZoneRulesException e) {
      // not expected, but catching for safety
      return;
    }
    
    boolean daylight = zoneRules.isDaylightSavings(d.toInstant());

    // Select long or short name variants and select the standard or daylight name.
    Name variants = getTimeZoneName(zone.getId(), d, width == 4);
    String name = variants == null ? null : (daylight ? variants.daylight() : variants.standard());

    switch (width) {
      case 4:
      {
        if (name != null) {
          b.append(name);
        } else {
          // Falls back to 'OOOO'
          formatTimeZone_O(b, d, 4);
        }
        break;
      }
        
      case 3:
      case 2:
      case 1:
      {
        if (name != null) {
          b.append(name);
        } else {
          // Falls back to 'O'
          formatTimeZone_O(b, d, 1);
        }
        break;
      }
    }
  }
  
  /**
   * Format time zone in ISO8601 basic or extended format for field 'Z'.
   * http://www.unicode.org/reports/tr35/tr35-dates.html#dfst-zone
   */
  void formatTimeZone_Z(StringBuilder b, ZonedDateTime d, int width) {
    if (width == 4) {
      // ZZZZ is same as OOOO
      formatTimeZone_O(b, d, width);
      return;
    }

    int[] tz = getTzComponents(d);
    if (width == 5 && tz[TZOFFSET] == 0) {
      b.append('Z');
      return;
    }

    switch (width) {
      case 5:
      case 3:
      case 2:
      case 1:
        if (tz[TZNEG] == -1) {
          b.append('-');
        } else {
          b.append('+');
        }
        
        zeroPad2(b, tz[TZHOURS], 2);
        // Delimiter omitted for all except XXXXX
        if (width == 5) {
          b.append(':');
        }
        zeroPad2(b, tz[TZMINS], 2);
        break;
    }
  }

  /** Indicates time zone offset is negative if == -1 */
  private static final int TZNEG = 0;
  /** Absolute value of the original offset in seconds */
  private static final int TZOFFSET = 1;
  /** Absolute value of the hours */
  private static final int TZHOURS = 2;
  /** Absolute value of the minutes */
  private static final int TZMINS = 3;
  
  /**
   * Decode some fields about a time zone.
   */
  private int[] getTzComponents(ZonedDateTime d) {
    ZoneOffset offset = d.getOffset();
    int secs = offset.getTotalSeconds();
    boolean negative = secs < 0;
    if (negative) {
      secs = -secs;
    }
    int hours = secs / 3600;
    int mins = (secs % 3600) / 60;
    return new int[] { negative ? -1 : 1, secs, hours, mins };
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
