package com.squarespace.cldr.dates;



import static org.testng.Assert.assertEquals;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.testng.annotations.Test;

import com.squarespace.cldr.CLDR;
import com.squarespace.cldr.CLDRLocale;


/**
 * Test the field-level date formatting logic.
 */
public class CLDRDateTimeFormatterTest {

//  private static final ZoneId LONDON = ZoneId.of("Europe/London");

  private static final ZoneId NEW_YORK = ZoneId.of("America/New_York");

//  private static final ZoneId PARIS = ZoneId.of("Europe/Paris");
//
//  private static final ZoneId TOKYO = ZoneId.of("Asia/Tokyo");

  private final CLDRLocale EN_US_LOCALE = new CLDRLocale("en", "", "US", "POSIX");

  private final CLDRLocale FR_LOCALE = new CLDRLocale("fr", "", "", "");

  private final CLDRLocale DE_LOCALE = new CLDRLocale("de", "", "", "");

  private final CLDRCalendarFormatter EN_US = CLDR.get().getCalendarFormatter(EN_US_LOCALE);

  private final CLDRCalendarFormatter FR = CLDR.get().getCalendarFormatter(FR_LOCALE);

  private final CLDRCalendarFormatter DE = CLDR.get().getCalendarFormatter(DE_LOCALE);

  @Test
  public void testFormatEra() {
    Datetime dt = may_1_2017();
    assertFormat(EN_US, 'G', dt.year(-10), "BC", "BC", "BC", "Before Christ", "B");
    assertFormat(EN_US, 'G', dt.year(10), "AD", "AD", "AD", "Anno Domini", "A");

    assertFormat(FR, 'G', dt.year(10), "ap. J.-C.", "ap. J.-C.", "ap. J.-C.", "après Jésus-Christ", "ap. J.-C.");
    assertFormat(DE, 'G', dt.year(10), "n. Chr.", "n. Chr.", "n. Chr.", "n. Chr.", "n. Chr.");
  }

  @Test
  public void testFormatYear() {
    Datetime dt = may_1_2017();
    assertFormat(EN_US, 'y', dt.year(2), "2", "02", "002", "0002", "00002");
    assertFormat(EN_US, 'y', dt.year(20), "20", "20", "020", "0020", "00020");
    assertFormat(EN_US, 'y', dt.year(201), "201", "01", "201", "0201", "00201");
    assertFormat(EN_US, 'y', dt.year(2017), "2017", "17", "2017", "2017", "02017");
    assertFormat(EN_US, 'y', dt.year(20173),"20173", "73", "20173", "20173", "20173");
  }

  @Test
  public void testQuarter() {
    Datetime dt = may_1_2017();
    for (int i : new int[] {1, 2, 3}) {
      assertFormat(EN_US, 'Q', dt.month(i), "1", "01", "Q1", "1st quarter", "1");
      assertFormat(FR, 'Q', dt.month(i), "1", "01", "T1", "1er trimestre", "1");
    }
    for (int i : new int[] {4, 5, 6}) {
      assertFormat(EN_US, 'Q', dt.month(i), "2", "02", "Q2", "2nd quarter", "2");
      assertFormat(FR, 'Q', dt.month(i), "2", "02", "T2", "2e trimestre", "2");
    }
    for (int i : new int[] {7, 8, 9}) {
      assertFormat(EN_US, 'Q', dt.month(i), "3", "03", "Q3", "3rd quarter", "3");
      assertFormat(FR, 'Q', dt.month(i), "3", "03", "T3", "3e trimestre", "3");
    }
    for (int i : new int[] {10, 11, 12}) {
      assertFormat(EN_US, 'Q', dt.month(i), "4", "04", "Q4", "4th quarter", "4");
      assertFormat(FR, 'Q', dt.month(i), "4", "04", "T4", "4e trimestre", "4");
    }
  }

  @Test
  public void testFormatMonth() {
    Datetime dt = may_1_2017();
    assertFormat(EN_US, 'M', dt.month(9), "9", "09", "Sep", "September", "S");
    assertFormat(EN_US, 'M', dt.month(12), "12", "12", "Dec", "December", "D");

    assertFormat(FR, 'M', dt.month(9), "9", "09", "sept.", "septembre", "S");
    assertFormat(FR, 'M', dt.month(12), "12", "12", "déc.", "décembre", "D");
  }

  @Test
  public void testFormatDayOfMonth() {
    Datetime dt = may_1_2017();
    assertFormat(EN_US, 'd', dt.dayOfMonth(1), "1", "01", "");
    assertFormat(EN_US, 'd', dt.dayOfMonth(15), "15", "15", "");
  }

  @Test
  public void testFormatDayOfYear() {
    Datetime dt = may_1_2017();
    assertFormat(EN_US, 'D', dt.dayOfYear(1), "1", "01", "001", "001");
    assertFormat(EN_US, 'D', dt.dayOfYear(10), "10", "10", "010", "010");
    assertFormat(EN_US, 'D', dt.dayOfYear(103), "103", "103", "103", "103");
  }

  @Test
  public void testWeekday() {
    Datetime dt = may_1_2017();
    for (int i : new int[] {1, 8, 15}) {
      assertFormat(EN_US, 'E', dt.dayOfMonth(i), "Mon", "Mon", "Mon", "Monday", "M", "Mo");
    }
    for (int i : new int[] {2, 9, 16}) {
      assertFormat(EN_US, 'E', dt.dayOfMonth(i), "Tue", "Tue", "Tue", "Tuesday", "T", "Tu");
    }
    for (int i : new int[] {3, 10, 17}) {
      assertFormat(EN_US, 'E', dt.dayOfMonth(i), "Wed", "Wed", "Wed", "Wednesday", "W", "We");
    }
    for (int i : new int[] {4, 11, 18}) {
      assertFormat(EN_US, 'E', dt.dayOfMonth(i), "Thu", "Thu", "Thu", "Thursday", "T", "Th");
    }
    for (int i : new int[] {5, 12, 19}) {
      assertFormat(EN_US, 'E', dt.dayOfMonth(i), "Fri", "Fri", "Fri", "Friday", "F", "Fr");
    }
    for (int i : new int[] {6, 13, 20}) {
      assertFormat(EN_US, 'E', dt.dayOfMonth(i), "Sat", "Sat", "Sat", "Saturday", "S", "Sa");
    }
    for (int i : new int[] {7, 14, 21}) {
      assertFormat(EN_US, 'E', dt.dayOfMonth(i), "Sun", "Sun", "Sun", "Sunday", "S", "Su");
    }
  }

  /**
   * Note: In "en-US" the week starts on Sunday, so:
   *   Sunday = 1st day of the week
   *   Monday = 2nd
   *   ... etc ...
   */
  @Test
  public void testLocalWeekday() {
    Datetime dt = may_1_2017();
    for (int i : new int[] {1, 8, 15}) {
      assertFormat(EN_US, 'e', dt.dayOfMonth(i), "2", "02", "Mon", "Monday", "M", "Mo");
      assertFormat(FR, 'e', dt.dayOfMonth(i), "1", "01", "lun.", "lundi", "L", "lu");
    }
    for (int i : new int[] {2, 9, 16}) {
      assertFormat(EN_US, 'e', dt.dayOfMonth(i), "3", "03", "Tue", "Tuesday", "T", "Tu");
      assertFormat(FR, 'e', dt.dayOfMonth(i), "2", "02", "mar.", "mardi", "M", "ma");
    }
    for (int i : new int[] {3, 10, 17}) {
      assertFormat(EN_US, 'e', dt.dayOfMonth(i), "4", "04", "Wed", "Wednesday", "W", "We");
      assertFormat(FR, 'e', dt.dayOfMonth(i), "3", "03", "mer.", "mercredi", "M", "me");
    }
    for (int i : new int[] {4, 11, 18}) {
      assertFormat(EN_US, 'e', dt.dayOfMonth(i), "5", "05", "Thu", "Thursday", "T", "Th");
      assertFormat(FR, 'e', dt.dayOfMonth(i), "4", "04", "jeu.", "jeudi", "J", "je");
    }
    for (int i : new int[] {5, 12, 19}) {
      assertFormat(EN_US, 'e', dt.dayOfMonth(i), "6", "06", "Fri", "Friday", "F", "Fr");
      assertFormat(FR, 'e', dt.dayOfMonth(i), "5", "05", "ven.", "vendredi", "V", "ve");
    }
    for (int i : new int[] {6, 13, 20}) {
      assertFormat(EN_US, 'e', dt.dayOfMonth(i), "7", "07", "Sat", "Saturday", "S", "Sa");
      assertFormat(FR, 'e', dt.dayOfMonth(i), "6", "06", "sam.", "samedi", "S", "sa");
    }
    for (int i : new int[] {7, 14, 21}) {
      assertFormat(EN_US, 'e', dt.dayOfMonth(i), "1", "01", "Sun", "Sunday", "S", "Su");
      assertFormat(FR, 'e', dt.dayOfMonth(i), "7", "07", "dim.", "dimanche", "D", "di");
    }
  }

  @Test
  public void testLocaleWeekdayFirstDay() {
    Datetime dt = may_1_2017();

    // US, Sunday is first day of week
    assertFormat(EN_US, 'e', dt.dayOfMonth(1), "2", "02", "Mon");

    // France and Germany, Monday is first day of week.
    assertFormat(FR, 'e', dt.dayOfMonth(1), "1", "01", "lun.");
    assertFormat(DE, 'e', dt.dayOfMonth(1), "1", "01", "Mo.");

    dt = nov_11_2017();
    assertFormat(EN_US, 'e', dt.dayOfMonth(1), "4", "04", "Wed");
    assertFormat(EN_US, 'e', dt.dayOfMonth(2), "5", "05", "Thu");
    assertFormat(EN_US, 'e', dt.dayOfMonth(3), "6", "06", "Fri");
    assertFormat(EN_US, 'e', dt.dayOfMonth(4), "7", "07", "Sat");
  }

  /**
   * Note: In "en-US" the week starts on Sunday, so:
   *   Sunday = 1st day of the week
   *   Monday = 2nd
   *   ... etc ...
   */
  @Test
  public void testLocalWeekdayStandalone() {
    Datetime dt = may_1_2017();
    for (int i : new int[] {1, 8, 15}) {
      assertFormat(EN_US, 'c', dt.dayOfMonth(i), "2", "2", "Mon", "Monday", "M", "Mo");
    }
    for (int i : new int[] {2, 9, 16}) {
      assertFormat(EN_US, 'c', dt.dayOfMonth(i), "3", "3", "Tue", "Tuesday", "T", "Tu");
    }
    for (int i : new int[] {3, 10, 17}) {
      assertFormat(EN_US, 'c', dt.dayOfMonth(i), "4", "4", "Wed", "Wednesday", "W", "We");
    }
    for (int i : new int[] {4, 11, 18}) {
      assertFormat(EN_US, 'c', dt.dayOfMonth(i), "5", "5", "Thu", "Thursday", "T", "Th");
    }
    for (int i : new int[] {5, 12, 19}) {
      assertFormat(EN_US, 'c', dt.dayOfMonth(i), "6", "6", "Fri", "Friday", "F", "Fr");
    }
    for (int i : new int[] {6, 13, 20}) {
      assertFormat(EN_US, 'c', dt.dayOfMonth(i), "7", "7", "Sat", "Saturday", "S", "Sa");
    }
    for (int i : new int[] {7, 14, 21}) {
      assertFormat(EN_US, 'c', dt.dayOfMonth(i), "1", "1", "Sun", "Sunday", "S", "Su");
    }
  }

  @Test
  public void testDayPeriod() {
    Datetime dt = may_1_2017();
    assertFormat(EN_US, 'a', dt.hour(11), "AM", "AM", "AM", "AM", "a");
    assertFormat(EN_US, 'a', dt.hour(13), "PM", "PM", "PM", "PM", "p");
  }

  @Test
  public void testFormatHour() {
    Datetime dt = may_1_2017();

    assertFormat(EN_US, 'h', dt.hour(3), "3", "03", "");
    assertFormat(EN_US, 'h', dt.hour(15), "3", "03", "");

    assertFormat(EN_US, 'H', dt.hour(3), "3", "03", "");
    assertFormat(EN_US, 'H', dt.hour(15), "15", "15", "");
  }

  @Test
  public void testFormatMinute() {
    Datetime dt = may_1_2017();
    assertFormat(EN_US, 'm', dt.minute(3), "3", "03", "");
    assertFormat(EN_US, 'm', dt.minute(45), "45", "45", "");
  }

  @Test
  public void testFormatSecond() {
    Datetime dt = may_1_2017();
    assertFormat(EN_US, 's', dt.second(3), "3", "03", "");
    assertFormat(EN_US, 's', dt.second(45), "45", "45", "");
  }

  private Datetime may_1_2017() {
    return may_1_2017(NEW_YORK);
  }

  private Datetime may_1_2017(ZoneId zone) {
    return datetime(ZonedDateTime.of(2017, 5, 2, 12, 13, 14, 1516, zone));
  }

  private Datetime nov_11_2017() {
    return nov_11_2017(NEW_YORK);
  }

  private Datetime nov_11_2017(ZoneId zone) {
    return epoch(1509626157000L, zone);
  }

  private Datetime epoch(long epochMillis, ZoneId zone) {
    return new Datetime(epochMillis, zone);
  }

  private Datetime datetime(ZonedDateTime d) {
    return new Datetime(d);
  }

  private static class Datetime {

    private final ZonedDateTime d;

    public Datetime(ZonedDateTime initial) {
      this.d = initial;
    }

    public Datetime(long epochMillis, ZoneId zone) {
      this(ZonedDateTime.ofInstant(Instant.ofEpochMilli(epochMillis), zone));
    }

    private ZonedDateTime year(int year) {
      return d.withYear(year);
    }

    private ZonedDateTime month(int month) {
      return d.withMonth(month);
    }

    private ZonedDateTime dayOfMonth(int dayOfMonth) {
      return d.withDayOfMonth(dayOfMonth);
    }

    private ZonedDateTime dayOfYear(int dayOfYear) {
      return d.withDayOfYear(dayOfYear);
    }

    private ZonedDateTime hour(int hour) {
      return d.withHour(hour);
    }

    private ZonedDateTime minute(int minute) {
      return d.withMinute(minute);
    }

    private ZonedDateTime second(int second) {
      return d.withSecond(second);
    }

  }

  /**
   * Formats a date using the given field. The array of expected strings correspond to increasing field
   * width, starting width 1, increasing by 1.
   */
  private void assertFormat(CLDRCalendarFormatter fmt, char field, ZonedDateTime d, String... expected) {
    StringBuilder b = new StringBuilder();
    for (int i = 0; i < expected.length; i++) {
      int width = i + 1;
      fmt.formatField(d, b, field, width);
      assertEquals(b.toString(), expected[i]);
      b.setLength(0);
    }
  }

}
