package com.squarespace.cldr.examples;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.squarespace.cldr.CLDR;
import com.squarespace.cldr.dates.CalendarFormat;
import com.squarespace.cldr.dates.CalendarFormatOptions;
import com.squarespace.cldr.dates.CalendarFormatter;
import com.squarespace.cldr.dates.CalendarSkeleton;
import com.squarespace.cldr.dates.CalendarUtils;
import com.squarespace.cldr.dates.DateTimeField;
import com.squarespace.cldr.dates.DateTimeIntervalSkeleton;
import com.squarespace.cldr.numbers.CurrencyFormatOptions;
import com.squarespace.cldr.numbers.CurrencyFormatStyle;
import com.squarespace.cldr.numbers.DecimalFormatOptions;
import com.squarespace.cldr.numbers.DecimalFormatStyle;
import com.squarespace.cldr.numbers.NumberFormatMode;
import com.squarespace.cldr.numbers.NumberFormatter;
import com.squarespace.cldr.numbers.NumberRoundMode;

public class ReadmeExamples {

  public static void main(String[] args) {
    datetime();
    datetimeIntervals();
    numbers();
    currencies();
  }

  private static void numbers() {
    NumberFormatter f = CLDR.get().getNumberFormatter(CLDR.EN_US);
    StringBuilder buffer = new StringBuilder();

    BigDecimal n = BigDecimal.valueOf(Math.PI);
    
    DecimalFormatOptions options = new DecimalFormatOptions();
    f.formatDecimal(n, buffer, options);
    System.out.println(buffer);
    // 3.142

    buffer.setLength(0);
    options = new DecimalFormatOptions().setMaximumFractionDigits(5);
    f.formatDecimal(n, buffer, options);
    System.out.println(buffer);
    // 3.14159
    
    buffer.setLength(0);
    options = new DecimalFormatOptions().setRoundMode(NumberRoundMode.FLOOR);
    f.formatDecimal(n, buffer, options);
    System.out.println(buffer);
    // 3.141
    
    n = new BigDecimal("10000");

    buffer.setLength(0);
    options = new DecimalFormatOptions().setMinimumFractionDigits(2).setGrouping(true);
    f.formatDecimal(n, buffer, options);
    System.out.println(buffer);
    // 10,000.00
    
    n = new BigDecimal("0.5");
    
    buffer.setLength(0);
    options = new DecimalFormatOptions().setStyle(DecimalFormatStyle.PERCENT);
    f.formatDecimal(n, buffer, options);
    System.out.println(buffer);
    // 50%
  }
  
  private static void currencies() {
    NumberFormatter f = CLDR.get().getNumberFormatter(CLDR.EN_US);
    
    StringBuilder buffer = new StringBuilder();

    BigDecimal n = new BigDecimal("1");
    
    CurrencyFormatOptions options = new CurrencyFormatOptions();
    f.formatCurrency(n, CLDR.Currency.USD, buffer, options);
    System.out.println(buffer);
    // $1.00
    
    n = new BigDecimal("-1");
    options = new CurrencyFormatOptions(CurrencyFormatStyle.ACCOUNTING);

    buffer.setLength(0);
    f.formatCurrency(n, CLDR.Currency.USD, buffer, options);
    System.out.println(buffer);
    // ($1.00)
    
    n = new BigDecimal("69900");
    options = new CurrencyFormatOptions(CurrencyFormatStyle.NAME);

    buffer.setLength(0);
    f.formatCurrency(n, CLDR.Currency.USD, buffer, options);
    System.out.println(buffer);
    // 69,900.00 US dollars
    
    options = new CurrencyFormatOptions(CurrencyFormatStyle.CODE);

    buffer.setLength(0);
    f.formatCurrency(n, CLDR.Currency.USD, buffer, options);
    System.out.println(buffer);
    // 69,900.00 USD
    

    n = new BigDecimal("1.491");
    options = new CurrencyFormatOptions().setRoundMode(NumberRoundMode.CEIL);
    
    buffer.setLength(0);
    f.formatCurrency(n, CLDR.Currency.USD, buffer, options);
    System.out.println(buffer);
    // $1.50

    options = new CurrencyFormatOptions(CurrencyFormatStyle.SHORT)
        .setFormatMode(NumberFormatMode.SIGNIFICANT_MAXFRAC)
        .setMaximumFractionDigits(1);

    n = new BigDecimal("1200");

    buffer.setLength(0);
    f.formatCurrency(n, CLDR.Currency.USD, buffer, options);
    System.out.println(buffer);
    // $1.2K
    
    
    n = new BigDecimal("999999");

    buffer.setLength(0);
    f.formatCurrency(n, CLDR.Currency.USD, buffer, options);
    System.out.println(buffer);
    // $1M
    
  }
  
  private static void datetimeIntervals() {
    StringBuilder buffer = new StringBuilder();

    long epoch = 1288648500000L;
    ZoneId zoneId = ZoneId.of("America/New_York");
    ZonedDateTime start = ZonedDateTime.ofInstant(Instant.ofEpochMilli(epoch), zoneId);
    ZonedDateTime end = start.withYear(2020).withMonth(12);

    CalendarFormatter f = CLDR.get().getCalendarFormatter(CLDR.EN_US);
    f.format(start, end, DateTimeIntervalSkeleton.y, buffer);
    System.out.println(buffer);
    // 2010 – 2020
    
    buffer.setLength(0);
    f.format(start, end, DateTimeIntervalSkeleton.yMMM, buffer);
    System.out.println(buffer);
    // Nov 2010 – Dec 2020
    
    end = start.withMonth(12);
    buffer.setLength(0);
    f.format(start, end, DateTimeIntervalSkeleton.yMMM, buffer);
    System.out.println(buffer);
    // Nov – Dec 2010
    
    end = start.withDayOfMonth(23);
    buffer.setLength(0);
    f.format(start, end, DateTimeIntervalSkeleton.yMMMd, buffer);
    System.out.println(buffer);
    // Nov 1 – 23, 2010
    
    end = start.withHour(22);
    buffer.setLength(0);
    f.format(start, end, DateTimeIntervalSkeleton.hmv, buffer);
    System.out.println(buffer);
    // 5:55 – 10:55 PM ET
    
    start = start.withMinute(1);
    end = start.withMinute(27);
    buffer.setLength(0);
    f.format(start, end, DateTimeIntervalSkeleton.Hm, buffer);
    System.out.println(buffer);
    // 17:01 – 17:27
    
    end = start.withMonth(12);
    DateTimeField field = CalendarUtils.fieldOfGreatestDifference(start, end);
    System.out.println(field);
    // MONTH

    // TODO: future. need to enable wrapping date with time range by exposing
    // the localized wrapper function.
//    switch (CalendarUtils.fieldOfGreatestDifference(start, end)) {
//      case YEAR:
//      case MONTH:
//      case DAY:
//        f.format(start, end, DateTimeIntervalSkeleton.yMMMd, buffer);
//        break;
//        
//      default:
//        CalendarFormatOptions options = new CalendarFormatOptions();
//        options.setDateSkeleton(CalendarSkeleton.yMMMd);
//        f.format(start, options, buffer);
//        buffer.append('\u00a0');
//        f.format(start, end, DateTimeIntervalSkeleton.hmv, buffer);  
//        break;
//    }
  }
  
  private static void datetime() {
    long epoch = 1288648500000L;
    ZoneId zoneId = ZoneId.of("America/New_York");
    ZonedDateTime datetime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(epoch), zoneId);
    
    StringBuilder buffer = new StringBuilder();
    CalendarFormatter f = CLDR.get().getCalendarFormatter(CLDR.EN_US);

    CalendarFormatOptions options = new CalendarFormatOptions();
    
    f.format(datetime, options, buffer);
    System.out.println(buffer);
    // 11/1/2010

    options = new CalendarFormatOptions().setDateSkeleton(CalendarSkeleton.GyMMMd);

    buffer.setLength(0);
    f.format(datetime, options, buffer);
    System.out.println(buffer);
    // Nov 1, 2010 AD
    
    options = new CalendarFormatOptions().setDateFormat(CalendarFormat.MEDIUM);
    
    buffer.setLength(0);
    f.format(datetime, options, buffer);
    System.out.println(buffer);
    // Nov 1, 2010

    options = new CalendarFormatOptions().setTimeFormat(CalendarFormat.MEDIUM);
    
    buffer.setLength(0);
    f.format(datetime, options, buffer);
    System.out.println(buffer);
    // 5:55:00 PM
    
    options = new CalendarFormatOptions().setWrapperFormat(CalendarFormat.MEDIUM);
    
    buffer.setLength(0);
    
    f.format(datetime, options, buffer);
    System.out.println(buffer);
    // Nov 1, 2010, 5:55:00 PM
    
    epoch = 1288598100000L;
    datetime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(epoch), zoneId);
    
    options = new CalendarFormatOptions().setWrapperFormat(CalendarFormat.FULL);
    
    buffer.setLength(0);
    f.format(datetime, options, buffer);
    System.out.println(buffer);
    // Monday, November 1, 2010 at 3:55:00 AM Eastern Daylight Time
    
    zoneId = ZoneId.of("America/Los_Angeles");
    datetime = datetime.withZoneSameInstant(zoneId);

    buffer.setLength(0);
    f.format(datetime, options, buffer);

    System.out.println(buffer);
    // Monday, November 1, 2010 at 12:55:00 AM Pacific Daylight Time
    
    
    // Individual date and time fields
    buffer.setLength(0);
    f.formatField(datetime, "EEEE", buffer);
    System.out.println(buffer);
    // Monday
    
    buffer.setLength(0);
    f.formatField(datetime, "LLLL", buffer);
    System.out.println(buffer);
    // November
    
  }
  
}
