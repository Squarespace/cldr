package com.squarespace.cldr.codegen.reader;

import java.util.Arrays;
import java.util.List;

import com.squarespace.cldr.codegen.LocaleID;


/**
 * Container to hold CLDR date-time data.
 */
public class DateTimeData {

  public LocaleID id;
  public int minDays;
  public int firstDay;
  public Variants eras;
  
  public Variants dayPeriodsFormat;
  public Variants dayPeriodsStandalone;
  public Variants monthsFormat;
  public Variants monthsStandalone;
  public Variants weekdaysFormat;
  public Variants weekdaysStandalone;
  public Variants quartersFormat;
  public Variants quartersStandalone;
  public Format dateFormats;
  public Format timeFormats;
  public Format dateTimeFormats;
  public List<Skeleton> dateTimeSkeletons;

  /**
   * Holds a datetime skeleton.
   */
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

  /**
   * Holds a list of datetime formats of different widths.
   */
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

  /**
   * Holds a generic set of variants of different widths.
   */
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

