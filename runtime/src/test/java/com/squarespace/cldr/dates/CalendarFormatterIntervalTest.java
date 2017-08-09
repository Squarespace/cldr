package com.squarespace.cldr.dates;

import static com.squarespace.cldr.CLDR.Locale.en;
import static com.squarespace.cldr.CLDR.Locale.fr;

import org.testng.Assert;
import org.testng.annotations.Test;


public class CalendarFormatterIntervalTest extends CalendarFormatterTestBase {

  @Test
  public void testSameDay() {
    Datetime d = may_1_2017();
    Assert.assertTrue(CalendarFormattingUtils.sameDay(d.get(), d.hour(23).get()));
  }

  @Test
  public void testGreatestDifference() {
    Datetime d = may_1_2017();

    assertDiff(d, d.year(2020), DateTimeField.YEAR);
    assertDiff(d, d.year(2020).month(7), DateTimeField.YEAR);

    assertDiff(d, d.month(7), DateTimeField.MONTH);
    
    assertDiff(d, d.dayOfMonth(12), DateTimeField.DAY);
  }
  
  /**
   * Formats date time intervals where greatest difference is the year.
   */
  @Test
  public void testYears() {
    Datetime d1 = may_1_2017();
    Datetime d2 = d1.year(2020).month(9).dayOfMonth(5);
    assertFormat(en, DateTimeIntervalSkeleton.y, d1,  d2, "2017 – 2020");
    assertFormat(en, DateTimeIntervalSkeleton.yM, d1, d2, "5/2017 – 9/2020");
    assertFormat(en, DateTimeIntervalSkeleton.yMd, d1, d2, "5/2/2017 – 9/5/2020");
    assertFormat(en, DateTimeIntervalSkeleton.yMMM, d1, d2, "May 2017 – Sep 2020");
    assertFormat(en, DateTimeIntervalSkeleton.yMMMd, d1, d2, "May 2, 2017 – Sep 5, 2020");
    assertFormat(en, DateTimeIntervalSkeleton.yMMMM, d1, d2, "May 2017 – September 2020");
    assertFormat(en, DateTimeIntervalSkeleton.yMMMEd, d1, d2, "Tue, May 2, 2017 – Sat, Sep 5, 2020");
    
    assertFormat(fr, DateTimeIntervalSkeleton.y, d1,  d2, "2017–2020");
    assertFormat(fr, DateTimeIntervalSkeleton.yM, d1, d2, "05/2017 – 09/2020");
    assertFormat(fr, DateTimeIntervalSkeleton.yMd, d1, d2, "02/05/2017 – 05/09/2020");
    assertFormat(fr, DateTimeIntervalSkeleton.yMMM, d1, d2, "mai 2017 – sept. 2020");
    assertFormat(fr, DateTimeIntervalSkeleton.yMMMd, d1, d2, "2 mai 2017 – 5 sept. 2020");
    assertFormat(fr, DateTimeIntervalSkeleton.yMMMM, d1, d2, "mai 2017 – septembre 2020");
    assertFormat(fr, DateTimeIntervalSkeleton.yMMMEd, d1, d2, "mar. 2 mai 2017 – sam. 5 sept. 2020");
  }
  
  /**
   * Formats date time intervals where greatest difference is the month.
   */
  @Test
  public void testMonths() {
    Datetime d1 = may_1_2017();
    Datetime d2 = d1.month(9).dayOfMonth(6);
    assertFormat(en, DateTimeIntervalSkeleton.M, d1, d2, "5 – 9");
    assertFormat(en, DateTimeIntervalSkeleton.Md, d1, d2, "5/2 – 9/6");
    assertFormat(en, DateTimeIntervalSkeleton.MEd, d1, d2, "Tue, 5/2 – Wed, 9/6");
    assertFormat(en, DateTimeIntervalSkeleton.MMM, d1, d2, "May – Sep");
    assertFormat(en, DateTimeIntervalSkeleton.MMMd, d1, d2, "May 2 – Sep 6");
    assertFormat(en, DateTimeIntervalSkeleton.MMMEd, d1, d2, "Tue, May 2 – Wed, Sep 6");

    // include year in format
    assertFormat(en, DateTimeIntervalSkeleton.yMMMd, d1, d2, "May 2 – Sep 6, 2017");

    assertFormat(fr, DateTimeIntervalSkeleton.M, d1, d2, "5–9");
    assertFormat(fr, DateTimeIntervalSkeleton.Md, d1, d2, "02/05 – 06/09");
    assertFormat(fr, DateTimeIntervalSkeleton.MEd, d1, d2, "mar. 02/05 – mer. 06/09");
    assertFormat(fr, DateTimeIntervalSkeleton.MMM, d1, d2, "mai–sept.");
    assertFormat(fr, DateTimeIntervalSkeleton.MMMd, d1, d2, "2 mai – 6 sept.");
    assertFormat(fr, DateTimeIntervalSkeleton.MMMEd, d1, d2, "mar. 2 mai – mer. 6 sept.");

    // include year in format
    assertFormat(fr, DateTimeIntervalSkeleton.yMMMd, d1, d2, "2 mai – 6 sept. 2017");
  }
  
  /**
   * Formats date time intervals where greatest difference is the day.
   */
  @Test
  public void testDays() {
    Datetime d = may_1_2017();
    assertFormat(en, DateTimeIntervalSkeleton.Md, d, d.dayOfMonth(7), "5/2 – 5/7");
    assertFormat(en, DateTimeIntervalSkeleton.MEd, d, d.dayOfMonth(7), "Tue, 5/2 – Sun, 5/7");
    assertFormat(en, DateTimeIntervalSkeleton.yMMMd, d, d.dayOfMonth(7), "May 2 – 7, 2017");
  }

  /**
   * Formats date time intervals where greatest difference is the hours or minutes.
   */
  @Test
  public void testHoursMinutes() {
    Datetime d = may_1_2017().hour(12).minute(47);
    assertFormat(en, DateTimeIntervalSkeleton.Hm, d, d.minute(53), "12:47 – 12:53");
    assertFormat(en, DateTimeIntervalSkeleton.hmv, d.hour(7), d.hour(17), "7:47 AM – 5:47 PM ET");
  }
}
