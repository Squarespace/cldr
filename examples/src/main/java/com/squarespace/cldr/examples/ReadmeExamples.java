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
  }
}
