
squarespace-cldr - Java CLDR code generation.

WARNING: This code is a work in progress and the API may change.

See: http://cldr.unicode.org/

[![Build Status](https://travis-ci.org/Squarespace/cldr.svg?branch=master)](https://travis-ci.org/Squarespace/cldr)
[![Coverage Status](https://coveralls.io/repos/github/Squarespace/cldr/badge.svg)](https://coveralls.io/github/Squarespace/cldr)

License: [Apache 2.0](LICENSE)

### Resolving Locales

```java
CLDR.Locale locale = CLDR.get().get("zh-Hant-CN");
System.out.println(locale);
```

> "zh"

```java
locale = CLDR.get().get("zh-Hant-HK");
System.out.println(locale);
System.out.printf("%s / %s / %s\n", locale.language(), locale.script(), locale.territory());
```

> "zh-Hant-HK"

> "zh / Hant / HK"

```java
locale = CLDR.get().get("sr-RU");
System.out.println(locale);
```

> "sr"

```java
locale = CLDR.get().get(java.util.Locale.CANADA_FRENCH);
System.out.println(locale);
```

> "fr-CA"

```java
locale = CLDR.get().get("en_XY");
System.out.println(locale);
```

> "en"

```java
locale = CLDR.get().get("und-Zzzz-ZZ");
System.out.println(locale);
```

> "en-US"

```java
locale = CLDR.get().get(java.util.Locale.JAPANESE);
System.out.println(locale);
```

> "ja-JP"

### Accessing Formatters

For testing you can use a known locale using a pre-defined field:
```java
NumberFormatter f = CLDR.get().getNumberFormatter(CLDR.Locale.en_US);
System.out.println(f.locale());
```
> "en"

.. or using a Java format locale string:
```java
f = CLDR.get().getNumberFormatter("en_US");
System.out.println(f.locale());
```
> "en"

.. or using a BCP 47 language tag:
```java
f = CLDR.get().getNumberFormatter("en-Latn-US");
System.out.println(f.locale());
```
> "en"

.. or using a Java locale object:
```java
f = CLDR.get().getNumberFormatter(java.util.Locale.US);
System.out.println(f.locale());
```
> "en"

### Date and Time formatting

```java
StringBuilder buffer = new StringBuilder();

long epoch = 1288648500000L;
ZoneId zoneId = ZoneId.of("America/New_York");
ZonedDateTime datetime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(epoch), zoneId);
CalendarFormatter f = CLDR.get().getCalendarFormatter(CLDR.Locale.en_US);
CalendarFormatOptions options = new CalendarFormatOptions();
f.format(datetime, options, buffer);
System.out.println(buffer);
```

> "11/1/2010"

```java
options = new CalendarFormatOptions().setDateSkeleton(CalendarSkeleton.GyMMMd);
f.format(datetime, options, buffer);
```

> "Nov 1, 2010 AD"

```java
options = new CalendarFormatOptions().setDateFormat(CalendarFormat.MEDIUM);
f.format(datetime, options, buffer);
```

> "Nov 1, 2010"

```java
options = new CalendarFormatOptions().setTimeFormat(CalendarFormat.MEDIUM);
f.format(datetime, options, buffer);
```

> "5:55:00 PM"

```java
options = new CalendarFormatOptions().setWrapperFormat(CalendarFormat.MEDIUM);
f.format(datetime, options, buffer);
```

> "Nov 1, 2010, 5:55:00 PM"

```java
epoch = 1288598100000L;
datetime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(epoch), zoneId);
options = new CalendarFormatOptions().setWrapperFormat(CalendarFormat.FULL);
f.format(datetime, options, buffer);
```

> "Monday, November 1, 2010 at 3:55:00 AM Eastern Daylight Time"

```java
zoneId = ZoneId.of("America/Los_Angeles");
datetime = datetime.withZoneSameInstant(zoneId);
f.format(datetime, options, buffer);
```

> "Monday, November 1, 2010 at 12:55:00 AM Pacific Daylight Time"


#### Formatting individual date and time fields

```java
f.formatField(datetime, "EEEE", buffer);
```
> "Monday"

```java
f.formatField(datetime, "LLLL", buffer);
```
> "November"


#### Formatting date time intervals

```java
long epoch = 1288648500000L;
ZoneId zoneId = ZoneId.of("America/New_York");
ZonedDateTime start = ZonedDateTime.ofInstant(Instant.ofEpochMilli(epoch), zoneId);
ZonedDateTime end = start.withYear(2020).withMonth(12);

CalendarFormatter f = CLDR.get().getCalendarFormatter(CLDR.Locale.en_US);
f.format(start, end, DateTimeIntervalSkeleton.y, buffer);
System.out.println(buffer);
```
> "2010 – 2020"

```java
f.format(start, end, DateTimeIntervalSkeleton.yMMM, buffer);
```
> "Nov 2010 – Dec 2020"

```java
end = start.withMonth(12);
f.format(start, end, DateTimeIntervalSkeleton.yMMM, buffer);
```
> "Nov – Dec 2010"

```java
end = start.withDayOfMonth(23);
f.format(start, end, DateTimeIntervalSkeleton.yMMMd, buffer);
```
> "Nov 1 – 23, 2010"

```java
end = start.withHour(22);
f.format(start, end, DateTimeIntervalSkeleton.hmv, buffer);
```
> "5:55 – 10:55 PM ET"

```java
start = start.withMinute(1);
end = start.withMinute(27);
f.format(start, end, DateTimeIntervalSkeleton.Hm, buffer);
```
> "17:01 – 17:27"

A helper method exists to find the field of greatest difference, which will help
you choose whether to use a date or time skeleton:

```java
end = start.withMonth(12);
DateTimeField field = CalendarUtils.fieldOfGreatestDifference(start, end);
System.out.println(field);
```
> MONTH


### Decimal number formatting

```java
StringBuilder buffer = new StringBuilder();
NumberFormatter f = CLDR.get().getNumberFormatter(CLDR.Locale.en_US);
BigDecimal n = BigDecimal.valueOf(Math.PI);
DecimalFormatOptions options = new DecimalFormatOptions();
f.formatDecimal(n, buffer, options);
System.out.println(buffer);
```

> "3.142"

```java
options = new DecimalFormatOptions().setMaximumFractionDigits(5);
f.formatDecimal(n, buffer, options);
```

> "3.14159"

```java
options = new DecimalFormatOptions().setRoundMode(NumberRoundMode.FLOOR);
f.formatDecimal(n, buffer, options);
```

> "3.141"

```java
n = new BigDecimal("10000");

options = new DecimalFormatOptions().setMinimumFractionDigits(2).setGrouping(true);
f.formatDecimal(n, buffer, options);
```

> "10,000.00"

```java
n = new BigDecimal("0.5");

options = new DecimalFormatOptions().setStyle(DecimalFormatStyle.PERCENT);
f.formatDecimal(n, buffer, options);
```

> "50%"

#### Compact forms

```java
n = new BigDecimal("999.95");

options = new DecimalFormatOptions(DecimalFormatStyle.LONG);
f.formatDecimal(n, buffer, options);
```

> "1 thousand"

```java
CLDR.get().getNumberFormatter(CLDR.Locale.fr).formatDecimal(n, buffer, options);
```

> "1 millier"

Pattern is chosen using plural category of the visible number.

```java
for (String num : new String[] { "1000", "1200", "2000", "5000" }) {
    n = new BigDecimal(num);
    CLDR.get().getNumberFormatter(CLDR.Locale.pl).formatDecimal(n, buffer, options);
}
```

> "1 tysiąc"

> "1,2 tysiąca"

> "2 tysiące"

> "5 tysięcy"

### Unit formatting

```java
StringBuilder buffer = new StringBuilder();
UnitFormatOptions options = new UnitFormatOptions();
NumberFormatter f = CLDR.get().getNumberFormatter(CLDR.Locale.en_US);

UnitConverter converter = CLDR.get().getUnitConverter(CLDR.Locale.en_CA);
System.out.println(converter.measurementSystem());
```

> METRIC

```java
converter = CLDR.get().getUnitConverter(CLDR.Locale.en_GB);
System.out.println(converter.measurementSystem());
```

> UK


```java
converter = CLDR.get().getUnitConverter(CLDR.Locale.en_US);
System.out.println(converter.measurementSystem());
```

> US

```java
UnitValue value = new UnitValue(("1234567", Unit.FOOT);
UnitValue converted = converter.convert(value, Unit.KILOMETER);
f.formatUnit(converted, buffer, options);
System.out.println(converted);
System.out.println(buffer)
```

> UnitValue(376.2960216, KILOMETER)

> "376.3km"

```java
value = new UnitValue("125.785", Unit.MEGABYTE);
f.formatUnit(value, buffer, options);
```

> "125.8MB"

```java
converted = converter.convert(value, Unit.GIGABYTE);
f.formatUnit(converted, buffer, options);
System.out.println(converted);
System.out.println(buffer);
```

> UnitValue(0.1228369140625, GIGABYTE)

> "0.1GB"

```java
options.setGrouping(true);
value = new UnitValue("112233445566778899", Unit.BYTE);
converted = converter.bytes(value);
f.formatUnit(converted, buffer, options);
```

> "102,075.7TB"

```java
options = new UnitFormatOptions(UnitFormat.LONG).setGrouping(true);
f = CLDR.get().getNumberFormatter(CLDR.Locale.fr);
f.formatUnit(converted, buffer, options);
```

> "102075,7 téraoctets"


#### Unit sequence formatting

```java
StringBuilder buffer = new StringBuilder();
UnitFormatOptions longOptions = new UnitFormatOptions(UnitFormat.LONG);
UnitFormatOptions narrowOptions = new UnitFormatOptions(UnitFormat.NARROW);

UnitConverter converter = CLDR.get().getUnitConverter(CLDR.Locale.en_US);
UnitValue degrees = new UnitValue("13.536613", Unit.DEGREE);
List<UnitValue> angle = converter.sequence(degrees, UnitConstants.ANGLE_FACTORS);
System.out.println(angle);
```

> [UnitValue(13, DEGREE), UnitValue(32, ARC_MINUTE), UnitValue(11.8068, ARC_SECOND)]

```java
NumberFormatter fmt = CLDR.get().getNumberFormatter(CLDR.Locale.en_US);
fmt.formatUnits(angle, buffer, longOptions);
```

> "13 degrees 32 arcminutes 11.8 arcseconds"

```java
buffer.setLength(0);
fmt.formatUnits(angle, buffer, narrowOptions);
```

> "13° 32′ 11.8″"

```java
buffer.setLength(0);
UnitValue days = new UnitValue("753", Unit.DAY);
List<UnitValue> duration = converter.sequence(days, UnitConstants.DURATION_FACTORS);
fmt.formatUnits(duration, buffer, longOptions);
```

> "2 years 22 days 12 hours 21 minutes 36 seconds"

```java
fmt.formatUnits(duration, buffer, narrowOptions);
```

> "2y 22d 12h 21m 36s"

```java
fmt = CLDR.get().getNumberFormatter(CLDR.Locale.ko);
fmt.formatUnits(duration, buffer, narrowOptions);
```

> "2년 22일 12시간 21분 36초"

#### Formatting with custom unit factor sets

```java
fmt = CLDR.get().getNumberFormatter(CLDR.Locale.en_US);
days = new UnitValue("753.35", Unit.DAY);
UnitFactorSet durationFactors = new UnitFactorSet(UnitFactors.DURATION, Unit.WEEK, Unit.HOUR);
duration = converter.sequence(days, durationFactors);
System.out.println(duration);
```

> [UnitValue(107, WEEK), UnitValue(104.4, HOUR)]

```java
fmt.formatUnits(duration, buffer, longOptions);
```
> "107 weeks 104.4 hours"

```java
UnitValue inches = new UnitValue("1234567", Unit.INCH);
UnitFactorSet lengthFactors = new UnitFactorSet(UnitFactors.LENGTH, Unit.MILE, Unit.FOOT);
List<UnitValue> length = converter.sequence(inches, lengthFactors);
System.out.println(length);
```

> [UnitValue(19, MILE), UnitValue(2560.583333333333, FOOT)]

```java
fmt.formatUnits(length, buffer, narrowOptions);
```

> "19mi 2560.6′"

#### Switching on Metric vs English

```java
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
```
> "en-CA 31 kilometers 358 meters 0.2 centimeters"

> "en-US 19 miles 853 yards 1 foot 7 inches"


### Currency formatting

```java
StringBuilder buffer = new StringBuilder();
NumberFormatter f = CLDR.get().getNumberFormatter(CLDR.Locale.en_US);

BigDecimal n = new BigDecimal("1");
CurrencyFormatOptions options = new CurrencyFormatOptions();
f.formatCurrency(n, CLDR.Currency.USD, buffer, options);
System.out.println(buffer);
```

> "$1.00"

```java
n = new BigDecimal("-1");
options = new CurrencyFormatOptions(CurrencyFormatStyle.ACCOUNTING);
f.formatCurrency(n, CLDR.Currency.USD, buffer, options);
```

> "($1.00)"

```java
n = new BigDecimal("69900");
options = new CurrencyFormatOptions(CurrencyFormatStyle.NAME);
f.formatCurrency(n, CLDR.Currency.USD, buffer, options);
```

> "69,900.00 US dollars"

```java
options = new CurrencyFormatOptions(CurrencyFormatStyle.CODE);
f.formatCurrency(n, CLDR.Currency.USD, buffer, options);
```

> "69,900.00 USD"

```java
n = new BigDecimal("1.491");
options = new CurrencyFormatOptions().setRoundMode(NumberRoundMode.CEIL);
f.formatCurrency(n, CLDR.Currency.USD, buffer, options);
```

> "$1.50"

```java
options = new CurrencyFormatOptions(CurrencyFormatStyle.SHORT)
    .setFormatMode(NumberFormatMode.SIGNIFICANT_MAXFRAC)
    .setMaximumFractionDigits(1);
n = new BigDecimal("1200");
f.formatCurrency(n, CLDR.Currency.USD, buffer, options);
```

> "$1.2K"

```java
n = new BigDecimal("999999");
f.formatCurrency(n, CLDR.Currency.USD, buffer, options);
```

> "$1M"

```java
f.formatCurrency(n, CLDR.Currency.EUR, buffer, options);
```

> "€1M"
