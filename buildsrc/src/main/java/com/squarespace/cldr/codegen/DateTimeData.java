package com.squarespace.cldr.codegen;

import java.util.Arrays;
import java.util.List;


/**
 * Container to hold CLDR date-time data.
 */
public class DateTimeData {

  private LocaleID id;

  private int minDays;

  private int firstDay;

  private Variants eras;

  private Variants dayPeriodsFormat;

  private Variants dayPeriodsStandalone;

  private Variants monthsFormat;

  private Variants monthsStandalone;

  private Variants weekdaysFormat;

  private Variants weekdaysStandalone;

  private Variants quartersFormat;

  private Variants quartersStandalone;

  private Format dateFormats;

  private Format timeFormats;

  private Format dateTimeFormats;

  private List<Skeleton> dateTimeSkeletons;


  public DateTimeData() {
  }

  public void setID(LocaleID value) {
    this.id = value;
  }

  public LocaleID id() {
    return id;
  }

  public int minDays() {
    return minDays;
  }

  public void setMinDays(int value) {
    this.minDays = value;
  }

  public int firstDay() {
    return firstDay;
  }

  public void setFirstDay(int value) {
    this.firstDay = value;
  }

  public Variants eras() {
    return eras;
  }

  public void setEras(Variants value) {
    this.eras = value;
  }

  public Variants dayPeriodsFormat() {
    return dayPeriodsFormat;
  }

  public void setDayPeriodsFormat(Variants value) {
    this.dayPeriodsFormat = value;
  }

  public Variants dayPeriodsStandalone() {
    return dayPeriodsStandalone;
  }

  public void setDayPeriodsStandalone(Variants value) {
    this.dayPeriodsStandalone = value;
  }

  public Variants monthsFormat() {
    return monthsFormat;
  }

  public void setMonthsFormat(Variants value) {
    this.monthsFormat = value;
  }

  public Variants monthsStandalone() {
    return monthsStandalone;
  }

  public void setMonthsStandalone(Variants value) {
    this.monthsStandalone = value;
  }

  public Variants weekdaysFormat() {
    return this.weekdaysFormat;
  }

  public void setWeekdaysFormat(Variants value) {
    this.weekdaysFormat = value;
  }

  public Variants weekdaysStandalone() {
    return this.weekdaysStandalone;
  }

  public void setWeekdaysStandalone(Variants value) {
    this.weekdaysStandalone = value;
  }

  public Variants quartersFormat() {
    return quartersFormat;
  }

  public void setQuartersFormat(Variants value) {
    this.quartersFormat = value;
  }

  public Variants quartersStandalone() {
    return quartersStandalone;
  }

  public void setQuartersStandalone(Variants value) {
    this.quartersStandalone = value;
  }

  public Format dateFormats() {
    return dateFormats;
  }

  public void setDateFormats(Format value) {
    this.dateFormats = value;
  }

  public Format timeFormats() {
    return timeFormats;
  }

  public void setTimeFormats(Format value) {
    this.timeFormats = value;
  }

  public Format dateTimeFormats() {
    return dateTimeFormats;
  }

  public void setDateTimeFormats(Format value) {
    this.dateTimeFormats = value;
  }

  public List<Skeleton> dateTimeSkeletons() {
    return dateTimeSkeletons;
  }

  public void setDateTimeSkeletons(List<Skeleton> value) {
    this.dateTimeSkeletons = value;
  }

  public static class Skeleton {

    public final String skeleton;

    public final String pattern;

    public Skeleton(String skeleton, String pattern) {
      this.skeleton = skeleton;
      this.pattern = pattern;
    }

    public String toString() {
      return "Skeleton(skeleton" + skeleton + ", pattern=" + pattern + ")";
    }
  }

  public static class Format {

    public final String short_;

    public final String medium;

    public final String long_;

    public final String full;

    public Format(String short_, String medium, String long_, String full) {
      this.short_ = short_;
      this.medium = medium;
      this.long_ = long_;
      this.full = full;
    }

    @Override
    public String toString() {
      return "Format(short=" + short_ + ", medium=" + medium + ", long=" + long_  + ", full=" + full + ")";
    }
  }

  public static class Variants {

    public final String[] abbreviated;

    public final String[] narrow;

    public final String[] short_;

    public final String[] wide;

    public Variants(String[] abbreviated, String[] narrow, String[] short_, String[] wide) {
      this.abbreviated = abbreviated;
      this.narrow = narrow;
      this.short_ = short_;
      this.wide = wide;
    }

    @Override
    public String toString() {
      return "Variants(abbreviated=" + Arrays.toString(abbreviated)
          + ", narrow=" + Arrays.toString(narrow)
          + ", short=" + Arrays.toString(short_)
          + ", wide=" + Arrays.toString(wide) + ")";
    }
  }

}

