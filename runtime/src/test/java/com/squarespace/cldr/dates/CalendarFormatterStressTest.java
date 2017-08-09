package com.squarespace.cldr.dates;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.testng.annotations.Test;

import com.squarespace.cldr.CLDR;


public class CalendarFormatterStressTest {

  private static final int DATE = 0x01;
  private static final int TIME = 0x02;
  private static final int WRAPPER = 0x04;
  private static final int DATESKEL = 0x08;
  private static final int TIMESKEL = 0x10;
  
  private static final CalendarFormat[] FORMATS = CalendarFormat.values();
  private static final int FORMATS_LEN = FORMATS.length;
  private static final CalendarSkeleton[] SKELETONS = CalendarSkeleton.values();
  private static final int SKELETONS_LEN = SKELETONS.length;
  
  @Test
  public void testCalendarFormatter() {
    StringBuilder buffer = new StringBuilder();
    long epoch = 1288648500000L;
    CLDR.Locale[] locales = new CLDR.Locale[] {
      CLDR.Locale.en_US,
      CLDR.Locale.am,
      CLDR.Locale.ar,
      CLDR.Locale.fr,
      CLDR.Locale.de,
      CLDR.Locale.en_GB
    };
    
    ZoneId zoneId = ZoneId.of("America/New_York");
    ZonedDateTime datetime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(epoch), zoneId);

    for (CLDR.Locale locale : locales) {
      CalendarFormatter f = CLDR.get().getCalendarFormatter(locale);
      int outer = DATE * TIME * WRAPPER * DATESKEL * TIMESKEL;
      int inner = FORMATS_LEN * SKELETONS_LEN;
      for (int i = 0; i < outer; i++) {
        for (int j = 0; j < inner; j++) {
          CalendarFormatOptions options = options(i, j);
          buffer.setLength(0);
          f.format(datetime, options, buffer);
        }
      }
    }
  }
  
  private static CalendarFormatOptions options(int flag, int choice) {
    CalendarFormatOptions opts = new CalendarFormatOptions();
    if ((flag & DATE) != 0) {
      opts.setDateFormat(format(choice));
    }
    if ((flag & TIME) != 0) {
      opts.setTimeFormat(format(choice));
    }
    if ((flag & WRAPPER) != 0) {
      opts.setWrapperFormat(format(choice));
    }
    if ((flag & DATESKEL) != 0) {
      opts.setDateSkeleton(skeleton(choice));
    }
    if ((flag & TIMESKEL) != 0) {
      opts.setTimeSkeleton(skeleton(choice));
    }
    return opts;
  }
  
  private static CalendarFormat format(int choice) {
    return FORMATS[choice % FORMATS_LEN];
  }
  
  private static CalendarSkeleton skeleton(int choice) {
    return SKELETONS[choice % SKELETONS_LEN];
  }
  
}
