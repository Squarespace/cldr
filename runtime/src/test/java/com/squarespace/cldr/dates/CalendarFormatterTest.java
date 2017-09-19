package com.squarespace.cldr.dates;



import static com.squarespace.cldr.CLDR.Locale.de;
import static com.squarespace.cldr.CLDR.Locale.en;
import static com.squarespace.cldr.CLDR.Locale.en_GB;
import static com.squarespace.cldr.CLDR.Locale.fr;
import static org.testng.Assert.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;

import org.testng.annotations.Test;

import com.squarespace.cldr.CLDR;
import com.squarespace.cldr.CLDR.Locale;
import com.squarespace.cldr.dates.CalendarFormatter.FieldWidth;


/**
 * Test the field-level date formatting logic.
 */
public class CalendarFormatterTest extends CalendarFormatterTestBase {

  @Test
  public void testWeekdays() {
    CalendarFormatter formatter = CLDR.get().getCalendarFormatter(Locale.en_US);
    assertEquals(formatter.getWeekdayName(DayOfWeek.THURSDAY, FieldWidth.WIDE), "Thursday");
    assertEquals(formatter.getWeekdayName(DayOfWeek.THURSDAY, FieldWidth.SHORT), "Th");
    assertEquals(formatter.getWeekdayName(DayOfWeek.SATURDAY, FieldWidth.WIDE), "Saturday");
    assertEquals(formatter.getWeekdayName(DayOfWeek.SATURDAY, FieldWidth.SHORT), "Sa");

    formatter = CLDR.get().getCalendarFormatter(Locale.de_DE);
    assertEquals(formatter.getWeekdayName(DayOfWeek.THURSDAY, FieldWidth.WIDE), "Donnerstag");
    assertEquals(formatter.getWeekdayName(DayOfWeek.THURSDAY, FieldWidth.SHORT), "Do.");
    assertEquals(formatter.getWeekdayName(DayOfWeek.SATURDAY, FieldWidth.WIDE), "Samstag");
    assertEquals(formatter.getWeekdayName(DayOfWeek.SATURDAY, FieldWidth.SHORT), "Sa.");

    formatter = CLDR.get().getCalendarFormatter(Locale.ja_JP);
    assertEquals(formatter.getWeekdayName(DayOfWeek.THURSDAY, FieldWidth.WIDE), "木曜日");
    assertEquals(formatter.getWeekdayName(DayOfWeek.THURSDAY, FieldWidth.SHORT), "木");
    assertEquals(formatter.getWeekdayName(DayOfWeek.SATURDAY, FieldWidth.WIDE), "土曜日");
    assertEquals(formatter.getWeekdayName(DayOfWeek.SATURDAY, FieldWidth.SHORT), "土");
  }
  
  @Test
  public void testMonths() {
    CalendarFormatter formatter = CLDR.get().getCalendarFormatter(Locale.en_US);
    assertEquals(formatter.getMonthName(Month.AUGUST, FieldWidth.WIDE), "August");
    assertEquals(formatter.getMonthName(Month.AUGUST, FieldWidth.SHORT), "Aug");
    assertEquals(formatter.getMonthName(Month.OCTOBER, FieldWidth.WIDE), "October");
    assertEquals(formatter.getMonthName(Month.OCTOBER, FieldWidth.SHORT), "Oct");

    formatter = CLDR.get().getCalendarFormatter(Locale.de_DE);
    assertEquals(formatter.getMonthName(Month.AUGUST, FieldWidth.WIDE), "August");
    assertEquals(formatter.getMonthName(Month.AUGUST, FieldWidth.SHORT), "Aug");
    assertEquals(formatter.getMonthName(Month.OCTOBER, FieldWidth.WIDE), "Oktober");
    assertEquals(formatter.getMonthName(Month.OCTOBER, FieldWidth.SHORT), "Okt");

    formatter = CLDR.get().getCalendarFormatter(Locale.ja_JP);
    assertEquals(formatter.getMonthName(Month.AUGUST, FieldWidth.WIDE), "8月");
    assertEquals(formatter.getMonthName(Month.AUGUST, FieldWidth.SHORT), "8月");
    assertEquals(formatter.getMonthName(Month.OCTOBER, FieldWidth.WIDE), "10月");
    assertEquals(formatter.getMonthName(Month.OCTOBER, FieldWidth.SHORT), "10月");
  }
  
  @Test
  public void testEra() {
    Datetime dt = may_1_2017();
    assertFormat(en, 'G', dt.year(-10), "BC", "BC", "BC", "Before Christ", "B");
    assertFormat(en, 'G', dt.year(10), "AD", "AD", "AD", "Anno Domini", "A");

    assertFormat(fr, 'G', dt.year(10), "ap. J.-C.", "ap. J.-C.", "ap. J.-C.", "après Jésus-Christ", "ap. J.-C.");
    assertFormat(de, 'G', dt.year(10), "n. Chr.", "n. Chr.", "n. Chr.", "n. Chr.", "n. Chr.");
  }

  @Test
  public void testYear() {
    Datetime dt = may_1_2017();
    assertFormat(en, 'y', dt.year(2), "2", "02", "002", "0002", "00002");
    assertFormat(en, 'y', dt.year(20), "20", "20", "020", "0020", "00020");
    assertFormat(en, 'y', dt.year(201), "201", "01", "201", "0201", "00201");
    assertFormat(en, 'y', dt.year(2017), "2017", "17", "2017", "2017", "02017");
    assertFormat(en, 'y', dt.year(20173), "20173", "73", "20173", "20173", "20173");
  }

  @Test
  public void testQuarter() {
    Datetime dt = may_1_2017();
    for (int i : new int[] {1, 2, 3}) {
      assertFormat(en, 'Q', dt.month(i), "1", "01", "Q1", "1st quarter", "1");
      assertFormat(fr, 'Q', dt.month(i), "1", "01", "T1", "1er trimestre", "1");
    }
    for (int i : new int[] {4, 5, 6}) {
      assertFormat(en, 'Q', dt.month(i), "2", "02", "Q2", "2nd quarter", "2");
      assertFormat(fr, 'Q', dt.month(i), "2", "02", "T2", "2e trimestre", "2");
    }
    for (int i : new int[] {7, 8, 9}) {
      assertFormat(en, 'Q', dt.month(i), "3", "03", "Q3", "3rd quarter", "3");
      assertFormat(fr, 'Q', dt.month(i), "3", "03", "T3", "3e trimestre", "3");
    }
    for (int i : new int[] {10, 11, 12}) {
      assertFormat(en, 'Q', dt.month(i), "4", "04", "Q4", "4th quarter", "4");
      assertFormat(fr, 'Q', dt.month(i), "4", "04", "T4", "4e trimestre", "4");
    }
  }

  @Test
  public void testFormatMonth() {
    Datetime dt = may_1_2017();
    assertFormat(en, 'M', dt.month(9), "9", "09", "Sep", "September", "S");
    assertFormat(en, 'M', dt.month(12), "12", "12", "Dec", "December", "D");

    assertFormat(fr, 'M', dt.month(9), "9", "09", "sept.", "septembre", "S");
    assertFormat(fr, 'M', dt.month(12), "12", "12", "déc.", "décembre", "D");
  }

  @Test
  public void testFormatDayOfMonth() {
    Datetime dt = may_1_2017();
    assertFormat(en, 'd', dt.dayOfMonth(1), "1", "01", "");
    assertFormat(en, 'd', dt.dayOfMonth(15), "15", "15", "");
  }

  @Test
  public void testFormatDayOfYear() {
    Datetime dt = may_1_2017();
    assertFormat(en, 'D', dt.dayOfYear(1), "1", "01", "001", "001");
    assertFormat(en, 'D', dt.dayOfYear(10), "10", "10", "010", "010");
    assertFormat(en, 'D', dt.dayOfYear(103), "103", "103", "103", "103");
  }

  @Test
  public void testFormatDayOfWeekInMonth() {
    Datetime dt = may_1_2017();

    // 1st monday and sunday
    assertFormat(en, 'F', dt.dayOfMonth(1), "1");
    assertFormat(en, 'F', dt.dayOfMonth(7), "1");

    // 2nd monday and sunday
    assertFormat(en, 'F', dt.dayOfMonth(8), "2");
    assertFormat(en, 'F', dt.dayOfMonth(14), "2");

    // 3rd monday and sunday
    assertFormat(en, 'F', dt.dayOfMonth(15), "3");
    assertFormat(en, 'F', dt.dayOfMonth(21), "3");

    // 4th monday and sunday
    assertFormat(en, 'F', dt.dayOfMonth(23), "4");
    assertFormat(en, 'F', dt.dayOfMonth(28), "4");

    // 5th monday and wednesday
    assertFormat(en, 'F', dt.dayOfMonth(29), "5");
    assertFormat(en, 'F', dt.dayOfMonth(31), "5");
  }

  @Test
  public void testWeekday() {
    Datetime dt = may_1_2017();
    for (int i : new int[] {1, 8, 15}) {
      assertFormat(en, 'E', dt.dayOfMonth(i), "Mon", "Mon", "Mon", "Monday", "M", "Mo");
    }
    for (int i : new int[] {2, 9, 16}) {
      assertFormat(en, 'E', dt.dayOfMonth(i), "Tue", "Tue", "Tue", "Tuesday", "T", "Tu");
    }
    for (int i : new int[] {3, 10, 17}) {
      assertFormat(en, 'E', dt.dayOfMonth(i), "Wed", "Wed", "Wed", "Wednesday", "W", "We");
    }
    for (int i : new int[] {4, 11, 18}) {
      assertFormat(en, 'E', dt.dayOfMonth(i), "Thu", "Thu", "Thu", "Thursday", "T", "Th");
    }
    for (int i : new int[] {5, 12, 19}) {
      assertFormat(en, 'E', dt.dayOfMonth(i), "Fri", "Fri", "Fri", "Friday", "F", "Fr");
    }
    for (int i : new int[] {6, 13, 20}) {
      assertFormat(en, 'E', dt.dayOfMonth(i), "Sat", "Sat", "Sat", "Saturday", "S", "Sa");
    }
    for (int i : new int[] {7, 14, 21}) {
      assertFormat(en, 'E', dt.dayOfMonth(i), "Sun", "Sun", "Sun", "Sunday", "S", "Su");
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
      assertFormat(en, 'e', dt.dayOfMonth(i), "1", "01", "Mon", "Monday", "M", "Mo");
      assertFormat(fr, 'e', dt.dayOfMonth(i), "1", "01", "lun.", "lundi", "L", "lu");
      assertFormat(de, 'e', dt.dayOfMonth(i), "1", "01", "Mo.", "Montag", "M", "Mo.");
    }
    for (int i : new int[] {2, 9, 16}) {
      assertFormat(en, 'e', dt.dayOfMonth(i), "2", "02", "Tue", "Tuesday", "T", "Tu");
      assertFormat(fr, 'e', dt.dayOfMonth(i), "2", "02", "mar.", "mardi", "M", "ma");
      assertFormat(de, 'e', dt.dayOfMonth(i), "2", "02", "Di.", "Dienstag", "D", "Di.");
    }
    for (int i : new int[] {3, 10, 17}) {
      assertFormat(en, 'e', dt.dayOfMonth(i), "3", "03", "Wed", "Wednesday", "W", "We");
      assertFormat(fr, 'e', dt.dayOfMonth(i), "3", "03", "mer.", "mercredi", "M", "me");
      assertFormat(de, 'e', dt.dayOfMonth(i), "3", "03", "Mi.", "Mittwoch", "M", "Mi.");
    }
    for (int i : new int[] {4, 11, 18}) {
      assertFormat(en, 'e', dt.dayOfMonth(i), "4", "04", "Thu", "Thursday", "T", "Th");
      assertFormat(fr, 'e', dt.dayOfMonth(i), "4", "04", "jeu.", "jeudi", "J", "je");
      assertFormat(de, 'e', dt.dayOfMonth(i), "4", "04", "Do.", "Donnerstag", "D", "Do.");
    }
    for (int i : new int[] {5, 12, 19}) {
      assertFormat(en, 'e', dt.dayOfMonth(i), "5", "05", "Fri", "Friday", "F", "Fr");
      assertFormat(fr, 'e', dt.dayOfMonth(i), "5", "05", "ven.", "vendredi", "V", "ve");
      assertFormat(de, 'e', dt.dayOfMonth(i), "5", "05", "Fr.", "Freitag", "F", "Fr.");
    }
    for (int i : new int[] {6, 13, 20}) {
      assertFormat(en, 'e', dt.dayOfMonth(i), "6", "06", "Sat", "Saturday", "S", "Sa");
      assertFormat(fr, 'e', dt.dayOfMonth(i), "6", "06", "sam.", "samedi", "S", "sa");
      assertFormat(de, 'e', dt.dayOfMonth(i), "6", "06", "Sa.", "Samstag", "S", "Sa.");
    }
    for (int i : new int[] {7, 14, 21}) {
      assertFormat(en, 'e', dt.dayOfMonth(i), "7", "07", "Sun", "Sunday", "S", "Su");
      assertFormat(fr, 'e', dt.dayOfMonth(i), "7", "07", "dim.", "dimanche", "D", "di");
      assertFormat(de, 'e', dt.dayOfMonth(i), "7", "07", "So.", "Sonntag", "S", "So.");
    }
  }

  @Test
  public void testLocaleWeekdayFirstDay() {
    Datetime dt = may_1_2017();

    assertFormat(en, 'e', dt.dayOfMonth(1), "1", "01", "Mon");
    assertFormat(fr, 'e', dt.dayOfMonth(1), "1", "01", "lun.");
    assertFormat(de, 'e', dt.dayOfMonth(1), "1", "01", "Mo.");

    dt = nov_11_2017();
    assertFormat(en, 'e', dt.dayOfMonth(1), "3", "03", "Wed");
    assertFormat(en, 'e', dt.dayOfMonth(2), "4", "04", "Thu");
    assertFormat(en, 'e', dt.dayOfMonth(3), "5", "05", "Fri");
    assertFormat(en, 'e', dt.dayOfMonth(4), "6", "06", "Sat");
  }

  @Test
  public void testLocalWeekdayStandalone() {
    Datetime dt = may_1_2017();
    for (int i : new int[] {1, 8, 15}) {
      assertFormat(en, 'c', dt.dayOfMonth(i), "1", "1", "Mon", "Monday", "M", "Mo");
    }
    for (int i : new int[] {2, 9, 16}) {
      assertFormat(en, 'c', dt.dayOfMonth(i), "2", "2", "Tue", "Tuesday", "T", "Tu");
    }
    for (int i : new int[] {3, 10, 17}) {
      assertFormat(en, 'c', dt.dayOfMonth(i), "3", "3", "Wed", "Wednesday", "W", "We");
    }
    for (int i : new int[] {4, 11, 18}) {
      assertFormat(en, 'c', dt.dayOfMonth(i), "4", "4", "Thu", "Thursday", "T", "Th");
    }
    for (int i : new int[] {5, 12, 19}) {
      assertFormat(en, 'c', dt.dayOfMonth(i), "5", "5", "Fri", "Friday", "F", "Fr");
    }
    for (int i : new int[] {6, 13, 20}) {
      assertFormat(en, 'c', dt.dayOfMonth(i), "6", "6", "Sat", "Saturday", "S", "Sa");
    }
    for (int i : new int[] {7, 14, 21}) {
      assertFormat(en, 'c', dt.dayOfMonth(i), "7", "7", "Sun", "Sunday", "S", "Su");
    }
  }

  @Test
  public void testWeekOfMonth() {
    Datetime dt = may_1_2017();
    assertFormat(en, 'W', dt, "1", "");
    assertFormat(en, 'W', dt.dayOfMonth(10), "2", "");
    assertFormat(en, 'W', dt.dayOfMonth(18), "3", "");
  }
  
  @Test
  public void testISOYearWeekOfYear() {
    Datetime dt = jan_1_2005();
    
    assertFormat(en, 'Y', dt, "2004", "04", "2004", "2004", "02004");
    
    dt = dt.month(12).dayOfMonth(31);
    
    assertFormat(en, 'Y', dt, "2005", "05", "2005", "2005", "02005");
  }
  
  @Test
  public void testISOWeekOfYear() {
    Datetime dt = jan_1_2005();

    assertFormat(en, 'w', dt, "53", "53", "");
    
    dt = dt.month(12).dayOfMonth(31);
    
    assertFormat(en, 'w', dt, "52", "52", "");
  }
  
  @Test
  public void testDayPeriod() {
    Datetime dt = may_1_2017();
    assertFormat(en, 'a', dt.hour(11), "AM", "AM", "AM", "AM", "a");
    assertFormat(en, 'a', dt.hour(13), "PM", "PM", "PM", "PM", "p");
  }

  @Test
  public void testHour() {
    Datetime dt = may_1_2017();

    assertFormat(en, 'h', dt.hour(3), "3", "03", "");
    assertFormat(en, 'h', dt.hour(15), "3", "03", "");
    assertFormat(en, 'h', dt.hour(0), "12", "12", "");

    assertFormat(en, 'H', dt.hour(3), "3", "03", "");
    assertFormat(en, 'H', dt.hour(15), "15", "15", "");
    assertFormat(en, 'H', dt.hour(0), "0", "00", "");
  }

  @Test
  public void testHourAlt() {
    Datetime dt = may_1_2017();
    
    assertFormat(en, 'K', dt.hour(0), "0", "00", "");
    assertFormat(en, 'K', dt.hour(3), "3", "03", "");
    assertFormat(en, 'K', dt.hour(11), "11", "11", "");
    assertFormat(en, 'K', dt.hour(12), "0", "00", "");
    assertFormat(en, 'K', dt.hour(13), "1", "01", "");
    assertFormat(en, 'K', dt.hour(23), "11", "11", "");
    
    assertFormat(en, 'k', dt.hour(0), "1", "01", "");
    assertFormat(en, 'k', dt.hour(3), "4", "04", "");
    assertFormat(en, 'k', dt.hour(11), "12", "12", "");
    assertFormat(en, 'k', dt.hour(12), "13", "13", "");
    assertFormat(en, 'k', dt.hour(13), "14", "14", "");
    assertFormat(en, 'k', dt.hour(23), "24", "24", "");
  }
  
  @Test
  public void testMinute() {
    Datetime dt = may_1_2017();
    assertFormat(en, 'm', dt.minute(3), "3", "03", "");
    assertFormat(en, 'm', dt.minute(45), "45", "45", "");
  }

  @Test
  public void testSecond() {
    Datetime dt = may_1_2017();
    assertFormat(en, 's', dt.second(3), "3", "03", "");
    assertFormat(en, 's', dt.second(45), "45", "45", "");
  }

  @Test
  public void testFractionalSecond() {
    Datetime dt = may_1_2017();
    assertFormat(en, 'S', dt.nanos(1234567),
        "0", "00", "001", "0012", "00123", "001234", "0012345", "00123456", "001234567", "0012345670", "00123456700");
  }

  @Test
  public void testTimeZone_O() {
    Datetime dt = may_1_2017();
    
    assertFormat(en, 'O', dt, "GMT-4", "", "", "GMT-04:00");
    assertFormat(en, 'O', dt.timeZone(UTC), "GMT", "", "", "GMT");
    assertFormat(en, 'O', dt.timeZone(LONDON), "GMT+1", "", "", "GMT+01:00");

    assertFormat(fr, 'O', dt, "UTC−4", "", "", "UTC−04:00");
    assertFormat(fr, 'O', dt.timeZone(UTC), "UTC", "", "", "UTC");
    assertFormat(fr, 'O', dt.timeZone(LONDON), "UTC+1", "", "", "UTC+01:00");
  }
  
  @Test
  public void testTimeZone_v() {
    Datetime dt = may_1_2017();
    
    assertFormat(en, 'v', dt, "ET", "", "", "Eastern Time");
    assertFormat(en, 'v', dt.timeZone(LOS_ANGELES), "PT", "", "", "Pacific Time");
    assertFormat(en, 'v', dt.timeZone(LONDON), "GMT+1", "", "", "GMT+01:00");
    assertFormat(en, 'v', dt.timeZone(PARIS), "GMT+2", "", "", "Central European Time");
    assertFormat(en, 'v', dt.timeZone(TOKYO), "GMT+9", "", "", "Japan Time");
  }

  @Test
  public void testTimeZone_V() {
    Datetime dt = may_1_2017();
    
    assertFormat(en, 'V', dt, "unk", "America/New_York", "New York", "New York Time");
    assertFormat(en, 'V', dt.timeZone(LOS_ANGELES), "unk", "America/Los_Angeles", "Los Angeles", "Los Angeles Time");
    assertFormat(en, 'V', dt.timeZone(LONDON), "unk", "Europe/London", "London", "London Time");
    assertFormat(en, 'V', dt.timeZone(PARIS), "unk", "Europe/Paris", "Paris", "Paris Time");
    assertFormat(en, 'V', dt.timeZone(TOKYO), "unk", "Asia/Tokyo", "Tokyo", "Tokyo Time");
  }
  
  @Test
  public void testTimeZone_X() {
    Datetime dt = may_1_2017();
    
    assertFormat(en, 'X', dt.second(0), "-04", "-0400", "-04:00", "-0400", "-04:00");
    assertFormat(en, 'X', dt.timeZone(UTC), "Z", "Z", "Z", "Z", "Z");
    assertFormat(en, 'X', dt.timeZone(LONDON), "+01", "+0100", "+01:00", "+0100", "+01:00");

    assertFormat(en, 'x', dt.second(0), "-04", "-0400", "-04:00", "-0400", "-04:00");
    assertFormat(en, 'x', dt.timeZone(UTC), "+00", "+0000", "+00:00", "+0000", "+00:00");
    assertFormat(en, 'x', dt.timeZone(LONDON), "+01", "+0100", "+01:00", "+0100", "+01:00");
  }

  @Test
  public void testTimeZone_z() {
    Datetime dt = may_1_2017();
    
    assertFormat(en, 'z', dt, "EDT", "EDT", "EDT", "Eastern Daylight Time");
    assertFormat(en, 'z', dt.timeZone(LOS_ANGELES), "PDT", "PDT", "PDT", "Pacific Daylight Time");
    assertFormat(en, 'z', dt.timeZone(UTC), "GMT", "GMT", "GMT", "GMT");
    assertFormat(en, 'z', dt.timeZone(LONDON), "GMT+1", "GMT+1", "GMT+1", "British Summer Time");
    assertFormat(en, 'z', dt.timeZone(PARIS), "GMT+2", "GMT+2", "GMT+2", "Central European Summer Time");
    assertFormat(en, 'z', dt.timeZone(TOKYO), "GMT+9", "GMT+9", "GMT+9", "Japan Standard Time");

    // confirming case that came up in intl chat.
    assertFormat(en_GB, 'z', dt.timeZone(LONDON), "BST", "BST", "BST", "British Summer Time");
    
    dt = dt.month(12);
    
    assertFormat(en, 'z', dt, "EST", "EST", "EST", "Eastern Standard Time");
    assertFormat(en, 'z', dt.timeZone(LOS_ANGELES), "PST", "PST", "PST", "Pacific Standard Time");
    assertFormat(en, 'z', dt.timeZone(UTC), "GMT", "GMT", "GMT", "GMT");
    assertFormat(en, 'z', dt.timeZone(LONDON), "GMT", "GMT", "GMT", "GMT");
    assertFormat(en, 'z', dt.timeZone(PARIS), "GMT+1", "GMT+1", "GMT+1", "Central European Standard Time");
    assertFormat(en, 'z', dt.timeZone(TOKYO), "GMT+9", "GMT+9", "GMT+9", "Japan Standard Time");
  }
  
  @Test
  public void testTimeZone_Z() {
    Datetime dt = may_1_2017();
    
    assertFormat(en, 'Z', dt, "-0400", "-0400", "-0400", "GMT-04:00", "-04:00");
    assertFormat(en, 'Z', dt.timeZone(UTC), "+0000", "+0000", "+0000", "GMT", "Z");
    assertFormat(en, 'Z', dt.timeZone(LONDON), "+0100", "+0100", "+0100", "GMT+01:00", "+01:00");

    assertFormat(fr, 'Z', dt, "-0400", "-0400", "-0400", "UTC−04:00", "-04:00");
    assertFormat(fr, 'Z', dt.timeZone(UTC), "+0000", "+0000", "+0000", "UTC", "Z");
    assertFormat(fr, 'Z', dt.timeZone(LONDON), "+0100", "+0100", "+0100", "UTC+01:00", "+01:00");
  }

}
