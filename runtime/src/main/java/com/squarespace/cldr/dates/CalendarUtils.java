package com.squarespace.cldr.dates;

import java.time.ZonedDateTime;


public class CalendarUtils extends _CalendarUtils {

  /**
   * Returns the enumerated value representing the field of greatest difference between
   * the two dates. Useful for code to switch formats based on how far apart the two
   * dates are. If the second date-time has a different time zone than the start, this 
   * is corrected for comparison.
   */
  public static DateTimeField fieldOfGreatestDifference(ZonedDateTime d1, ZonedDateTime d2) {
    return CalendarFormattingUtils.greatestDifference(d1, d2);
  }

}
