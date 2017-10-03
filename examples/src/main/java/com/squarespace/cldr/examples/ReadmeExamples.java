package com.squarespace.cldr.examples;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import com.squarespace.cldr.CLDR;
import com.squarespace.cldr.LanguageMatcher;
import com.squarespace.cldr.MessageArgs;
import com.squarespace.cldr.MessageFormat;
import com.squarespace.cldr.StringMessageArg;
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
import com.squarespace.cldr.units.Unit;
import com.squarespace.cldr.units.UnitCategory;
import com.squarespace.cldr.units.UnitConverter;
import com.squarespace.cldr.units.UnitFactorSet;
import com.squarespace.cldr.units.UnitFactorSets;
import com.squarespace.cldr.units.UnitFactors;
import com.squarespace.cldr.units.UnitFormat;
import com.squarespace.cldr.units.UnitFormatOptions;
import com.squarespace.cldr.units.UnitValue;


public class ReadmeExamples {

  public static void main(String[] args) {
    matching();
    locales();
    bundle();
    datetime();
    datetimeIntervals();
    messages();
    numbers();
    numbersCompact();
    currencies();
    units();
    unitSequences();
  }

  private static void matching() {
    LanguageMatcher matcher = new LanguageMatcher("es-419, es-ES, es-PT");
    String locale = matcher.match("es-AR");
    System.out.println(locale);
    // "es-Latn-419"
    
    locale = new LanguageMatcher("es, es-419, es-PT").match("es-MX");
    System.out.println(locale);
    // "es-Latn-419"

    locale = new LanguageMatcher("es, es-419, es-MX").match("es-PT");
    System.out.println(locale);
    // "es-Latn-ES"

    locale = new LanguageMatcher("es-419, es-PT, es-MX").match("es");
    System.out.println(locale);
    // "es-Latn-PT"

    locale = new LanguageMatcher("en, en-GU, en-IN, en-GB").match("en-VI");
    System.out.println(locale);
    // "en-Latn-US"
    
    locale = new LanguageMatcher("en, en-GU, en-IN, en-GB").match("en-AU");
    System.out.println(locale);
    // "en-Latn-GB"
    
    locale = new LanguageMatcher("en, en-GU, en-IN, en-GB").match("en-ZA");
    System.out.println(locale);
    // "en-Latn-GB"
  }
  
  private static void locales() {
    CLDR.Locale locale = CLDR.get().resolve("zh-CN");
    System.out.println(locale);
    // zh-Hant-CN

    locale = CLDR.get().resolve("zh-HK");
    System.out.println(locale);
    System.out.printf("%s / %s / %s\n", locale.language(), locale.script(), locale.territory());
    // zh-Hant-HK
    // zh / Hant / HK
    
    locale = CLDR.get().resolve("sr");
    System.out.println(locale);
    // sr-Cyrl-RS
    
    locale = CLDR.get().resolve(java.util.Locale.CANADA_FRENCH);
    System.out.println(locale);
    // fr-Latn-CA

    locale = CLDR.get().resolve(java.util.Locale.JAPANESE);
    System.out.println(locale);
    // ja-Jpan-JP

    locale = CLDR.get().resolve("en_XY");
    System.out.println(locale);
    // en-Latn-XY
    
    locale = CLDR.get().resolve("und-Zzzz-ZZ");
    System.out.println(locale);
    // en-Latn-US
    
    // MINIMIZE
    
    locale = CLDR.get().resolve("en");
    System.out.println(locale);
    // "en-Latn-US"
    
    locale = CLDR.get().minimize(locale);
    System.out.println(locale);
    // "en"
  }
  
  private static void bundle() {
    NumberFormatter f = CLDR.get().getNumberFormatter(CLDR.Locale.en_US);
    System.out.println(f.bundleId());
    // "en"

    CLDR.Locale locale = CLDR.get().resolve("en_US");
    f = CLDR.get().getNumberFormatter(locale);
    System.out.println(f.bundleId());
    // "en"

    locale = CLDR.get().resolve(java.util.Locale.US);
    f = CLDR.get().getNumberFormatter(locale);
    System.out.println(f.bundleId());
    // "en"

    locale = CLDR.get().resolve("en-Latn-US");
    f = CLDR.get().getNumberFormatter(locale);
    System.out.println(f.bundleId());
    // "en"
  }
  
  private static void messages() {
    String format = "Transmission of {0 unit compact:bytes} "
        + "took {1 unit in:second sequence:hour,minute,second format:long}";
    ZoneId tzNewYork = ZoneId.of("America/New_York");
    MessageFormat msg = new MessageFormat(CLDR.Locale.en_US, tzNewYork, format);
    MessageArgs args = MessageArgs.newBuilder().add("1234567890").add("12345").build();
    StringBuilder buf = new StringBuilder();
    msg.format(args, buf);
    System.out.println(buf);
    // "Transmission of 1.1GB took 3 hours 25 minutes 45 seconds"

    buf.setLength(0);
    ZoneId tzParis = ZoneId.of("Europe/Paris");
    msg = new MessageFormat(CLDR.Locale.fr_FR, tzParis, format);
    msg.format(args, buf);
    System.out.println(buf);
    // "Transmission of 1,1 Go took 3 heures 25 minutes 45 secondes"

    buf.setLength(0);
    format = "The total for the {count plural one{product} other{# products}} "
        + "you ordered is {amount currency} "
        + "on {2 datetime wrap:full}.";
    
    msg = new MessageFormat(CLDR.Locale.en_US, tzNewYork, format);
    StringMessageArg amount = new StringMessageArg("1234.56");
    amount.setCurrency("USD");
    args = MessageArgs.newBuilder()
        .add("count", "1")
        .add("amount", amount)
        .add("1498584124000")
        .build();
    
    msg.format(args, buf);
    System.out.println(buf);
    // The total for the product you ordered is $1,234.56 on
    // Tuesday, June 27, 2017 at 1:22:04 PM Eastern Daylight Time.

    buf.setLength(0);
    msg = new MessageFormat(CLDR.Locale.en_US, tzNewYork, format);
    args = MessageArgs.newBuilder()
        .add("count", "23")
        .add("amount", amount)
        .add("1498584124000")
        .build();
    msg.format(args, buf);
    System.out.println(buf);
    // The total for the 23 products you ordered is $1,234.56 on 
    // Tuesday, June 27, 2017 at 1:22:04 PM Eastern Daylight Time.
    
    buf.setLength(0);
    format = "The event takes place from {0;1 datetime-interval MMMd} and we hope to "
        + "raise {2 currency style:short} for our foundation.";
    msg = new MessageFormat(CLDR.Locale.en_US, tzNewYork, format);
    amount = new StringMessageArg("999990");
    amount.setCurrency("EUR");
    args = MessageArgs.newBuilder()
        .add("1509647217000")
        .add("1509819011000")
        .add(amount)
        .build();
    msg.format(args, buf);
    System.out.println(buf);
    // The event takes place from Nov 2 – 4 and we hope to raise $1M for our foundation.
    
    buf.setLength(0);
    format = "Congratulations, you came in {0 selectordinal one{#st} two{#nd} few{#rd} other{#th}} place, "
        + "up from {1 selectordinal one{#st} two{#nd} few{#rd} other{#th}}, "
        + "that's quite an improvement!";
    msg = new MessageFormat(CLDR.Locale.en_US, tzNewYork, format);
    args = MessageArgs.newBuilder().add("3").add("27").build();
    msg.format(args, buf);
    System.out.println(buf);
    // Congratulations, you came in 3rd place, up from 27th, that's quite an improvement!
  }

  private static void numbers() {
    NumberFormatter f = CLDR.get().getNumberFormatter(CLDR.Locale.en_US);
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

  private static void numbersCompact() {
    StringBuilder buffer = new StringBuilder();
    BigDecimal n = new BigDecimal("999.95");
    
    DecimalFormatOptions options = new DecimalFormatOptions(DecimalFormatStyle.LONG);
    NumberFormatter f = CLDR.get().getNumberFormatter(CLDR.Locale.en_US);
    f.formatDecimal(n, buffer, options);
    System.out.println(buffer);
    // 1 thousand

    buffer.setLength(0);
    CLDR.get().getNumberFormatter(CLDR.Locale.fr).formatDecimal(n, buffer, options);
    System.out.println(buffer);
    // 1 millier

    for (String num : new String[] { "1000", "1200", "2000", "5000" }) {
      n = new BigDecimal(num);

      buffer.setLength(0);
      CLDR.get().getNumberFormatter(CLDR.Locale.pl).formatDecimal(n, buffer, options);
      System.out.println(buffer);
    }
    // 1 tysiąc
    // 1,2 tysiąca
    // 2 tysiące
    // 5 tysięcy
  }
  
  private static void units() {
    NumberFormatter f = CLDR.get().getNumberFormatter(CLDR.Locale.en_US);

    StringBuilder buffer = new StringBuilder();

    UnitFormatOptions options = new UnitFormatOptions();

    UnitConverter converter = CLDR.get().getUnitConverter(CLDR.Locale.en_CA);
    System.out.println(converter.measurementSystem());
    // METRIC
    
    converter = CLDR.get().getUnitConverter(CLDR.Locale.en_GB);
    System.out.println(converter.measurementSystem());
    // UK

    converter = CLDR.get().getUnitConverter(CLDR.Locale.en_US);
    System.out.println(converter.measurementSystem());
    // US
    
    UnitValue value = new UnitValue("1234567", Unit.FOOT);
    UnitValue converted = converter.convert(value, Unit.KILOMETER);
    System.out.println(converted);
    // UnitValue(376.2960216, KILOMETER)
    
    f.formatUnit(converted, buffer, options);
    System.out.println(buffer);
    // 376.3 km
    
    value = new UnitValue("125.785", Unit.MEGABYTE);

    buffer.setLength(0);
    f.formatUnit(value, buffer, options);
    System.out.println(buffer);
    // 125.8MB
    
    converted = converter.convert(value, Unit.GIGABYTE);
    System.out.println(converted);
    // UnitValue(0.1228369140625, GIGABYTE)
    
    buffer.setLength(0);
    f.formatUnit(converted, buffer, options);
    System.out.println(buffer);
    // 0.1GB

    buffer.setLength(0);
    options.setGrouping(true);
    value = new UnitValue("112233445566778899", Unit.BYTE);
    converted = converter.convert(value, UnitFactorSets.DIGITAL_BYTES);
    f.formatUnit(converted, buffer, options);
    System.out.println(buffer);
    // 102,075.7TB

    options = new UnitFormatOptions(UnitFormat.LONG).setGrouping(true);
    buffer.setLength(0);
    f = CLDR.get().getNumberFormatter(CLDR.Locale.fr);
    f.formatUnit(converted, buffer, options);
    System.out.println(buffer);
    // 102 075,7 téraoctets
  }
  
  private static void unitSequences() {
    StringBuilder buffer = new StringBuilder();

    UnitFormatOptions longOptions = new UnitFormatOptions(UnitFormat.LONG);
    UnitFormatOptions narrowOptions = new UnitFormatOptions(UnitFormat.NARROW);
    
    UnitConverter converter = CLDR.get().getUnitConverter(CLDR.Locale.en_US);
    UnitValue degrees = new UnitValue("13.536613", Unit.DEGREE);
    List<UnitValue> angle = converter.sequence(degrees, UnitFactorSets.ANGLE);
    System.out.println(angle);
    // [UnitValue(13, DEGREE), UnitValue(32, ARC_MINUTE), UnitValue(11.8068, ARC_SECOND)]
    
    NumberFormatter fmt = CLDR.get().getNumberFormatter(CLDR.Locale.en_US);
    fmt.formatUnits(angle, buffer, longOptions);
    System.out.println(buffer);
    // 13 degrees 32 arcminutes 11.8 arcseconds

    buffer.setLength(0);
    fmt.formatUnits(angle, buffer, narrowOptions);
    System.out.println(buffer);
    // 13° 32′ 11.8″
    
    buffer.setLength(0);
    UnitValue days = new UnitValue("753", Unit.DAY);
    List<UnitValue> duration = converter.sequence(days, UnitFactorSets.DURATION);
    fmt.formatUnits(duration, buffer, longOptions);
    System.out.println(buffer);
    // 2 years 22 days 12 hours 21 minutes 36 seconds
    
    buffer.setLength(0);
    fmt.formatUnits(duration, buffer, narrowOptions);
    System.out.println(buffer);
    // 2y 22d 12h 21m 36s

    buffer.setLength(0);
    fmt = CLDR.get().getNumberFormatter(CLDR.Locale.ko);
    fmt.formatUnits(duration, buffer, narrowOptions);
    System.out.println(buffer);
    // 2년 22일 12시간 21분 36초
    
    buffer.setLength(0);
    fmt = CLDR.get().getNumberFormatter(CLDR.Locale.en_US);
    days = new UnitValue("753.35", Unit.DAY);
    UnitFactorSet durationFactors = new UnitFactorSet(UnitFactors.DURATION, Unit.WEEK, Unit.HOUR);
    duration = converter.sequence(days, durationFactors);
    System.out.println(duration);
    // [UnitValue(107, WEEK), UnitValue(104.4, HOUR)]
    
    fmt.formatUnits(duration, buffer, longOptions);
    System.out.println(buffer);
    // 107 weeks 104.4 hours 
    
    buffer.setLength(0);
    UnitValue inches = new UnitValue("1234567", Unit.INCH);
    UnitFactorSet lengthFactors = new UnitFactorSet(UnitFactors.LENGTH, Unit.MILE, Unit.FOOT);
    List<UnitValue> length = converter.sequence(inches, lengthFactors);
    System.out.println(length);
    // [UnitValue(19, MILE), UnitValue(2560.583333333333, FOOT)]
    
    fmt.formatUnits(length, buffer, narrowOptions);
    System.out.println(buffer);
    // 19mi 2560.6′

    buffer.setLength(0);
    for (CLDR.Locale locale : new CLDR.Locale[] { CLDR.Locale.en_CA, CLDR.Locale.en_US }) {
      converter = CLDR.get().getUnitConverter(locale);
      if (converter.measurementSystem().usesMetric(UnitCategory.LENGTH)) {
        lengthFactors = UnitFactorSets.LENGTH;
      } else {
        lengthFactors = UnitFactorSets.LENGTH_ENGLISH;
      }
      length = converter.sequence(inches, lengthFactors);
      
      buffer.append(locale).append(' ');
      fmt.formatUnits(length, buffer, longOptions);
      buffer.append('\n');
    }
    System.out.println(buffer);
    // en-CA  31 kilometers 358 meters 0.2 centimeters
    // en-US  19 miles 853 yards 1 foot 7 inches
  }

  private static void currencies() {
    NumberFormatter f = CLDR.get().getNumberFormatter(CLDR.Locale.en_US);
    
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

    buffer.setLength(0);
    f.formatCurrency(n, CLDR.Currency.EUR, buffer, options);
    System.out.println(buffer);
    // €1M
  }
  
  private static void datetimeIntervals() {
    StringBuilder buffer = new StringBuilder();

    long epoch = 1288648500000L;
    ZoneId zoneId = ZoneId.of("America/New_York");
    ZonedDateTime start = ZonedDateTime.ofInstant(Instant.ofEpochMilli(epoch), zoneId);
    ZonedDateTime end = start.withYear(2020).withMonth(12);

    CalendarFormatter f = CLDR.get().getCalendarFormatter(CLDR.Locale.en_US);
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
  }
  
  private static void datetime() {
    long epoch = 1288648500000L;
    ZoneId zoneId = ZoneId.of("America/New_York");
    ZonedDateTime datetime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(epoch), zoneId);
    
    StringBuilder buffer = new StringBuilder();
    CalendarFormatter f = CLDR.get().getCalendarFormatter(CLDR.Locale.en_US);

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
