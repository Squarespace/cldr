package com.squarespace.cldr.dates;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

class Datetime {

  ZonedDateTime d;

  public Datetime(ZonedDateTime initial) {
    this.d = initial;
  }

  public Datetime(long epochMillis, ZoneId zone) {
    this(ZonedDateTime.ofInstant(Instant.ofEpochMilli(epochMillis), zone));
  }

  public ZonedDateTime get() {
    return d;
  }
  
  private Datetime make(ZonedDateTime datetime) {
    return new Datetime(datetime);
  }
  
  Datetime year(int year) {
    return make(d.withYear(year));
  }

  Datetime month(int month) {
    return make(d.withMonth(month));
  }

  Datetime dayOfMonth(int dayOfMonth) {
    return make(d.withDayOfMonth(dayOfMonth));
  }

  Datetime dayOfYear(int dayOfYear) {
    return make(d.withDayOfYear(dayOfYear));
  }

  Datetime hour(int hour) {
    return make(d.withHour(hour));
  }

  Datetime minute(int minute) {
    return make(d.withMinute(minute));
  }

  Datetime second(int second) {
    return make(d.withSecond(second));
  }

  Datetime nanos(int nanos) {
    return make(d.withNano(nanos));
  }
  
  Datetime timeZone(ZoneId zoneId) {
    return make(d.withZoneSameLocal(zoneId));
  }

}