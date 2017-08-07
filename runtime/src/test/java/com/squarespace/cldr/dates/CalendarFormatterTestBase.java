package com.squarespace.cldr.dates;

import static org.testng.Assert.assertEquals;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.squarespace.cldr.CLDR;
import com.squarespace.cldr.CLDRLocale;

public class CalendarFormatterTestBase {

  protected static final ZoneId UTC = ZoneId.of("Universal");
  protected static final ZoneId LONDON = ZoneId.of("Europe/London");
  protected static final ZoneId NEW_YORK = ZoneId.of("America/New_York");
  protected static final ZoneId LOS_ANGELES = ZoneId.of("America/Los_Angeles");
  protected static final ZoneId PARIS = ZoneId.of("Europe/Paris");
  protected static final ZoneId TOKYO = ZoneId.of("Asia/Tokyo");

  protected Datetime may_1_2017() {
    return may_1_2017(NEW_YORK);
  }

  protected Datetime may_1_2017(ZoneId zone) {
    return datetime(ZonedDateTime.of(2017, 5, 2, 12, 13, 14, 1516, zone));
  }

  protected Datetime jan_1_2005() {
    return jan_1_2005(NEW_YORK);
  }
  
  protected Datetime jan_1_2005(ZoneId zone) {
    return datetime(ZonedDateTime.of(2005, 1, 1, 0, 0, 0, 0, zone));
  }
  
  protected Datetime nov_11_2017() {
    return nov_11_2017(NEW_YORK);
  }

  protected Datetime nov_11_2017(ZoneId zone) {
    return epoch(1509626157000L, zone);
  }

  protected Datetime epoch(long epochMillis, ZoneId zone) {
    return new Datetime(epochMillis, zone);
  }

  protected Datetime datetime(ZonedDateTime d) {
    return new Datetime(d);
  }
  
  protected void assertDiff(Datetime d1, Datetime d2, DateTimeField expected) {
    DateTimeField actual = CalendarUtils.fieldOfGreatestDifference(d1.get(), d2.get());
    assertEquals(actual, expected);
  }
  
  /**
   * Formats a date time interval using the given skeleton.
   */
  protected void assertFormat(
      CLDRLocale locale, DateTimeIntervalSkeleton skeleton, Datetime start, Datetime end, String expected) {
    CalendarFormatter fmt = CLDR.get().getCalendarFormatter(locale);
    StringBuilder buffer = new StringBuilder();
    fmt.format(start.d, end.d, skeleton, buffer);
    assertEquals(buffer.toString(), expected);
  }
  
  /**
   * Formats a date using the given field. The array of expected strings correspond to increasing field
   * width, starting width 1, increasing by 1.
   */
  protected void assertFormat(CLDRLocale locale, char field, Datetime datetime, String... expected) {
    CalendarFormatter fmt = CLDR.get().getCalendarFormatter(locale);
    StringBuilder buffer = new StringBuilder();
    for (int i = 0; i < expected.length; i++) {
      int width = i + 1;
      fmt.formatField(datetime.d, field, width, buffer);
      assertEquals(buffer.toString(), expected[i], "width " + width + " failed");
      buffer.setLength(0);
    }
  }


}
