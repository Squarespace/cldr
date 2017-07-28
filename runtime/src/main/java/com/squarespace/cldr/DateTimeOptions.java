package com.squarespace.cldr;

import static com.squarespace.cldr.dates._CalendarUtils.skeletonType;

import java.util.List;

import com.squarespace.cldr.dates.FormatType;
import com.squarespace.cldr.dates.SkeletonType;


/**
 * Provides options for "datetime" argument types in MessageFormat.
 *
 * You can format a date, a time, or a combination of either a date and time,
 * named (short, medium, etc) or a skeleton (yMMMd, hm, etc).
 */
public class DateTimeOptions {

  private FormatType wrapperType;
  private FormatType dateType;
  private FormatType timeType;
  private String dateSkeleton;
  private String timeSkeleton;
  private boolean isSet;

  public DateTimeOptions() {
  }

  public DateTimeOptions(List<String> args) {
  }

  public FormatType wrapperType() {
    return wrapperType;
  }

  public FormatType dateType() {
    return dateType;
  }

  public FormatType timeType() {
    return timeType;
  }

  public String dateSkeleton() {
    return dateSkeleton;
  }

  public String timeSkeleton() {
    return timeSkeleton;
  }

  public void set(List<String> args) {
    for (String arg : args) {
      add(arg);
    }
  }

  public void add(String arg) {
    // Note: Specifying at least one of the date-* or time-* flags will ignore
    // all other arguments.
    boolean miss = false;
    switch (arg) {
      case "date":
      case "date-short":
        this.dateType = FormatType.SHORT;
        break;

      case "date-medium":
        this.dateType = FormatType.MEDIUM;
        break;

      case "date-long":
        this.dateType = FormatType.LONG;
        break;

      case "date-full":
        this.dateType = FormatType.FULL;
        break;

      case "time":
      case "time-short":
        this.timeType = FormatType.SHORT;
        break;

      case "time-medium":
        this.timeType = FormatType.MEDIUM;
        break;

      case "time-long":
        this.timeType = FormatType.LONG;
        break;

      case "time-full":
        this.timeType = FormatType.FULL;
        break;

      case "wrap":
      case "wrap-short":
        this.wrapperType = FormatType.SHORT;
        break;

      case "wrap-medium":
        this.wrapperType = FormatType.MEDIUM;
        break;

      case "wrap-long":
        this.wrapperType = FormatType.LONG;
        break;

      case "wrap-full":
        this.wrapperType = FormatType.FULL;
        break;

      default:
        SkeletonType type = skeletonType(arg);
        if (type == SkeletonType.DATE) {
          dateSkeleton = arg;
        } else if (type == SkeletonType.TIME) {
          timeSkeleton = arg;
        } else {
          miss = true;
        }
        break;
    }
    if (!miss) {
      isSet = true;
    }
  }

  public void done() {
    if (!isSet) {
      // If you specify no options you'll still get something
      dateType = FormatType.LONG;
      timeType = FormatType.LONG;
      wrapperType = FormatType.LONG;
    }

    boolean haveBoth = (dateType != null || dateSkeleton != null)
                    && (timeType != null || timeSkeleton != null);

    // Based on which values exist, select an automatic wrapper type.
    if (wrapperType == null) {
      if (timeSkeleton != null || timeType != null) {
        wrapperType = (dateType != null) ? dateType : (dateSkeleton != null ? FormatType.SHORT : null);
      }
    } else if (!haveBoth) {
      // can't use the wrapper for just a date or time.
      wrapperType = null;
    }
  }

}