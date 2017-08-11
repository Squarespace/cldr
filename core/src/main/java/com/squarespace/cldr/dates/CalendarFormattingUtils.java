package com.squarespace.cldr.dates;

import static com.squarespace.cldr.dates.DateTimeField.AM_PM;
import static com.squarespace.cldr.dates.DateTimeField.DAY;
import static com.squarespace.cldr.dates.DateTimeField.HOUR;
import static com.squarespace.cldr.dates.DateTimeField.MINUTE;
import static com.squarespace.cldr.dates.DateTimeField.MONTH;
import static com.squarespace.cldr.dates.DateTimeField.SECOND;
import static com.squarespace.cldr.dates.DateTimeField.YEAR;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;


public class CalendarFormattingUtils {

  /**
   * Returns a boolean indicating the two date times are on the same day.
   */
  public static boolean sameDay(ZonedDateTime d1, ZonedDateTime d2) {
    DateTimeField field = greatestDifference(d1, d2);
    return (field != YEAR && field != MONTH && field != DAY);
  }
  
  /**
   * Return the field of greatest difference between the two date times.
   */
  public static DateTimeField greatestDifference(ZonedDateTime d1, ZonedDateTime d2) {
    d2 = sameTimeZone(d1, d2);
    if (d1.getYear() != d2.getYear()) {
      return YEAR;
    }
    if (d1.getMonthValue() != d2.getMonthValue()) {
      return MONTH;
    }
    if (d1.getDayOfMonth() != d2.getDayOfMonth()) {
      return DAY;
    }
    if (d1.get(ChronoField.AMPM_OF_DAY) != d2.get(ChronoField.AMPM_OF_DAY)) {
      return AM_PM;
    }
    if (d1.getHour() != d2.getHour()) {
      return HOUR;
    }
    if (d1.getMinute() != d2.getMinute()) {
      return MINUTE;
    }
    // Interval formatting works at the >= minute resolution, so if two
    // dates differ by only seconds the application may want to display
    // something else.
    return SECOND;
  }

  private static ZonedDateTime sameTimeZone(ZonedDateTime d1, ZonedDateTime d2) {
    ZoneId id1 = d1.getZone();
    ZoneId id2 = d2.getZone();
    if (!id1.equals(id2)) {
      d2 = d2.withZoneSameInstant(id1);
    }
    return d2;
  }
  
}
