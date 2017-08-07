
squarespace-cldr - Java CLDR code generation.

WARNING: This code is a work in progress, and the API will likely change
significantly in the near future.

See: http://cldr.unicode.org/

[![Build Status](https://travis-ci.org/Squarespace/cldr.svg?branch=master)](https://travis-ci.org/Squarespace/cldr)
[![Coverage Status](https://coveralls.io/repos/github/Squarespace/cldr/badge.svg)](https://coveralls.io/github/Squarespace/cldr)

License: [Apache 2.0](LICENSE)


### Date and Time formatting

```java
StringBuilder buffer = new StringBuilder();

long epoch = 1288648500000L;
ZoneId zoneId = ZoneId.of("America/New_York");
ZonedDateTime datetime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(epoch), zoneId);
CalendarFormatter f = CLDR.get().getCalendarFormatter(CLDR.EN_US);
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
ZonedDateTime end = start.withYear(2020).withMonth(9);

CalendarFormatter f = CLDR.get().getCalendarFormatter(CLDR.EN_US);
f.format(start, end, DateTimeIntervalSkeleton.y, buffer);
System.out.println(buffer);
```
> "2010 – 2020"

```java
f.format(start, end, DateTimeIntervalSkeleton.yMMM, buffer);
```
> "Nov 2010 – Sep 2020"

```java
end = start.withMonth(9);
f.format(start, end, DateTimeIntervalSkeleton.yMMM, buffer);
```
> "Nov – Sep 2010"

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

A helper method exists to find the field of greatest distance, which will help
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
NumberFormatter f = CLDR.get().getNumberFormatter(CLDR.EN_US);
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


### Currency formatting

```java
StringBuilder buffer = new StringBuilder();
NumberFormatter f = CLDR.get().getNumberFormatter(CLDR.EN_US);

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
