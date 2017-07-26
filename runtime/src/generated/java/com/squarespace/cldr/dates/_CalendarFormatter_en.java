//
//
// AUTO-GENERATED CLASS - DO NOT EDIT
//
//
package com.squarespace.cldr.dates;

import com.squarespace.cldr.CLDRLocale;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.time.ZonedDateTime;

/**
 * Locale "en"
 * See http://www.unicode.org/reports/tr35/tr35-dates.html#Date_Field_Symbol_Table
 */
public class _CalendarFormatter_en extends CalendarFormatterBase {
  public _CalendarFormatter_en() {
    this.locale = new CLDRLocale("en", "", "", "");
    this.firstDay = 1;
    this.minDays = 1;
    this.eras = new FieldVariants(
          new String[] {"BC", "AD"}, 
          new String[] {"B", "A"}, 
          new String[] {}, 
          new String[] {"Before Christ", "Anno Domini"});
    this.quartersFormat = new FieldVariants(
          new String[] {"Q1", "Q2", "Q3", "Q4"}, 
          new String[] {"1", "2", "3", "4"}, 
          new String[] {}, 
          new String[] {"1st quarter", "2nd quarter", "3rd quarter", "4th quarter"});
    this.quartersStandalone = new FieldVariants(
          new String[] {"Q1", "Q2", "Q3", "Q4"}, 
          new String[] {"1", "2", "3", "4"}, 
          new String[] {}, 
          new String[] {"1st quarter", "2nd quarter", "3rd quarter", "4th quarter"});
    this.monthsFormat = new FieldVariants(
          new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"}, 
          new String[] {"J", "F", "M", "A", "M", "J", "J", "A", "S", "O", "N", "D"}, 
          new String[] {}, 
          new String[] {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
    this.monthsStandalone = new FieldVariants(
          new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"}, 
          new String[] {"J", "F", "M", "A", "M", "J", "J", "A", "S", "O", "N", "D"}, 
          new String[] {}, 
          new String[] {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
    this.weekdaysFormat = new FieldVariants(
          new String[] {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}, 
          new String[] {"S", "M", "T", "W", "T", "F", "S"}, 
          new String[] {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"}, 
          new String[] {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"});
    this.weekdaysStandalone = new FieldVariants(
          new String[] {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}, 
          new String[] {"S", "M", "T", "W", "T", "F", "S"}, 
          new String[] {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"}, 
          new String[] {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"});
    this.dayPeriodsFormat = new FieldVariants(
          new String[] {"AM", "PM"}, 
          new String[] {"a", "p"}, 
          new String[] {}, 
          new String[] {"AM", "PM"});
    this.dayPeriodsStandalone = new FieldVariants(
          new String[] {"AM", "PM"}, 
          new String[] {"AM", "PM"}, 
          new String[] {}, 
          new String[] {"AM", "PM"});
  }

  @Override
  public void formatDate(FormatType type, ZonedDateTime d, StringBuilder b) {
    switch (type) {
      case SHORT: {
        // "M/d/yy"
        formatField(d, b, 'M', 1);
        b.append("/");
        formatField(d, b, 'd', 1);
        b.append("/");
        formatField(d, b, 'y', 2);
        break;
      }
      case MEDIUM: {
        // "MMM d, y"
        formatField(d, b, 'M', 3);
        b.append(" ");
        formatField(d, b, 'd', 1);
        b.append(", ");
        formatField(d, b, 'y', 1);
        break;
      }
      case LONG: {
        // "MMMM d, y"
        formatField(d, b, 'M', 4);
        b.append(" ");
        formatField(d, b, 'd', 1);
        b.append(", ");
        formatField(d, b, 'y', 1);
        break;
      }
      case FULL: {
        // "EEEE, MMMM d, y"
        formatField(d, b, 'E', 4);
        b.append(", ");
        formatField(d, b, 'M', 4);
        b.append(" ");
        formatField(d, b, 'd', 1);
        b.append(", ");
        formatField(d, b, 'y', 1);
        break;
      }
    }
  }

  @Override
  public void formatTime(FormatType type, ZonedDateTime d, StringBuilder b) {
    switch (type) {
      case SHORT: {
        // "h:mm a"
        formatField(d, b, 'h', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(" ");
        formatField(d, b, 'a', 1);
        break;
      }
      case MEDIUM: {
        // "h:mm:ss a"
        formatField(d, b, 'h', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
        b.append(" ");
        formatField(d, b, 'a', 1);
        break;
      }
      case LONG: {
        // "h:mm:ss a z"
        formatField(d, b, 'h', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
        b.append(" ");
        formatField(d, b, 'a', 1);
        b.append(" ");
        formatField(d, b, 'z', 1);
        break;
      }
      case FULL: {
        // "h:mm:ss a zzzz"
        formatField(d, b, 'h', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
        b.append(" ");
        formatField(d, b, 'a', 1);
        b.append(" ");
        formatField(d, b, 'z', 4);
        break;
      }
    }
  }

  @Override
  public void formatWrapped(FormatType wrapperType, FormatType dateType, FormatType timeType,
      String dateSkel, String timeSkel, ZonedDateTime d, StringBuilder b) {
    switch (wrapperType) {
      case SHORT:
      case MEDIUM:
       {
        // "{1}, {0}"
        if (dateType != null) {
          formatDate(dateType, d, b);
        } else {
          formatSkeleton(dateSkel, d, b);
        }
        b.append(", ");
        if (timeType != null) {
          formatTime(timeType, d, b);
        } else {
          formatSkeleton(timeSkel, d, b);
        }
        break;
      }
      case LONG:
      case FULL:
       {
        // "{1} 'at' {0}"
        if (dateType != null) {
          formatDate(dateType, d, b);
        } else {
          formatSkeleton(dateSkel, d, b);
        }
        b.append(" at ");
        if (timeType != null) {
          formatTime(timeType, d, b);
        } else {
          formatSkeleton(timeSkel, d, b);
        }
        break;
      }
    }
  }

  @Override
  public boolean formatSkeleton(String skeleton, ZonedDateTime d, StringBuilder b) {
    switch (skeleton) {
      case "d": {
        // Pattern: "d"
        formatField(d, b, 'd', 1);
        break;
      }
      case "E": {
        // Pattern: "ccc"
        formatField(d, b, 'c', 3);
        break;
      }
      case "Ed": {
        // Pattern: "d E"
        formatField(d, b, 'd', 1);
        b.append(" ");
        formatField(d, b, 'E', 1);
        break;
      }
      case "Ehm": {
        // Pattern: "E h:mm a"
        formatField(d, b, 'E', 1);
        b.append(" ");
        formatField(d, b, 'h', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(" ");
        formatField(d, b, 'a', 1);
        break;
      }
      case "EHm": {
        // Pattern: "E HH:mm"
        formatField(d, b, 'E', 1);
        b.append(" ");
        formatField(d, b, 'H', 2);
        b.append(":");
        formatField(d, b, 'm', 2);
        break;
      }
      case "Ehms": {
        // Pattern: "E h:mm:ss a"
        formatField(d, b, 'E', 1);
        b.append(" ");
        formatField(d, b, 'h', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
        b.append(" ");
        formatField(d, b, 'a', 1);
        break;
      }
      case "EHms": {
        // Pattern: "E HH:mm:ss"
        formatField(d, b, 'E', 1);
        b.append(" ");
        formatField(d, b, 'H', 2);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
        break;
      }
      case "Gy": {
        // Pattern: "y G"
        formatField(d, b, 'y', 1);
        b.append(" ");
        formatField(d, b, 'G', 1);
        break;
      }
      case "GyMMM": {
        // Pattern: "MMM y G"
        formatField(d, b, 'M', 3);
        b.append(" ");
        formatField(d, b, 'y', 1);
        b.append(" ");
        formatField(d, b, 'G', 1);
        break;
      }
      case "GyMMMd": {
        // Pattern: "MMM d, y G"
        formatField(d, b, 'M', 3);
        b.append(" ");
        formatField(d, b, 'd', 1);
        b.append(", ");
        formatField(d, b, 'y', 1);
        b.append(" ");
        formatField(d, b, 'G', 1);
        break;
      }
      case "GyMMMEd": {
        // Pattern: "E, MMM d, y G"
        formatField(d, b, 'E', 1);
        b.append(", ");
        formatField(d, b, 'M', 3);
        b.append(" ");
        formatField(d, b, 'd', 1);
        b.append(", ");
        formatField(d, b, 'y', 1);
        b.append(" ");
        formatField(d, b, 'G', 1);
        break;
      }
      case "h": {
        // Pattern: "h a"
        formatField(d, b, 'h', 1);
        b.append(" ");
        formatField(d, b, 'a', 1);
        break;
      }
      case "H": {
        // Pattern: "HH"
        formatField(d, b, 'H', 2);
        break;
      }
      case "hm": {
        // Pattern: "h:mm a"
        formatField(d, b, 'h', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(" ");
        formatField(d, b, 'a', 1);
        break;
      }
      case "Hm": {
        // Pattern: "HH:mm"
        formatField(d, b, 'H', 2);
        b.append(":");
        formatField(d, b, 'm', 2);
        break;
      }
      case "hms": {
        // Pattern: "h:mm:ss a"
        formatField(d, b, 'h', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
        b.append(" ");
        formatField(d, b, 'a', 1);
        break;
      }
      case "Hms": {
        // Pattern: "HH:mm:ss"
        formatField(d, b, 'H', 2);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
        break;
      }
      case "hmsv": {
        // Pattern: "h:mm:ss a v"
        formatField(d, b, 'h', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
        b.append(" ");
        formatField(d, b, 'a', 1);
        b.append(" ");
        formatField(d, b, 'v', 1);
        break;
      }
      case "Hmsv": {
        // Pattern: "HH:mm:ss v"
        formatField(d, b, 'H', 2);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
        b.append(" ");
        formatField(d, b, 'v', 1);
        break;
      }
      case "hmv": {
        // Pattern: "h:mm a v"
        formatField(d, b, 'h', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(" ");
        formatField(d, b, 'a', 1);
        b.append(" ");
        formatField(d, b, 'v', 1);
        break;
      }
      case "Hmv": {
        // Pattern: "HH:mm v"
        formatField(d, b, 'H', 2);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(" ");
        formatField(d, b, 'v', 1);
        break;
      }
      case "M": {
        // Pattern: "L"
        formatField(d, b, 'L', 1);
        break;
      }
      case "Md": {
        // Pattern: "M/d"
        formatField(d, b, 'M', 1);
        b.append("/");
        formatField(d, b, 'd', 1);
        break;
      }
      case "MEd": {
        // Pattern: "E, M/d"
        formatField(d, b, 'E', 1);
        b.append(", ");
        formatField(d, b, 'M', 1);
        b.append("/");
        formatField(d, b, 'd', 1);
        break;
      }
      case "MMM": {
        // Pattern: "LLL"
        formatField(d, b, 'L', 3);
        break;
      }
      case "MMMd": {
        // Pattern: "MMM d"
        formatField(d, b, 'M', 3);
        b.append(" ");
        formatField(d, b, 'd', 1);
        break;
      }
      case "MMMEd": {
        // Pattern: "E, MMM d"
        formatField(d, b, 'E', 1);
        b.append(", ");
        formatField(d, b, 'M', 3);
        b.append(" ");
        formatField(d, b, 'd', 1);
        break;
      }
      case "MMMMd": {
        // Pattern: "MMMM d"
        formatField(d, b, 'M', 4);
        b.append(" ");
        formatField(d, b, 'd', 1);
        break;
      }
      case "MMMMW-count-one": {
        // Pattern: "'week' W 'of' MMMM"
        b.append("week ");
        formatField(d, b, 'W', 1);
        b.append(" of ");
        formatField(d, b, 'M', 4);
        break;
      }
      case "MMMMW-count-other": {
        // Pattern: "'week' W 'of' MMMM"
        b.append("week ");
        formatField(d, b, 'W', 1);
        b.append(" of ");
        formatField(d, b, 'M', 4);
        break;
      }
      case "ms": {
        // Pattern: "mm:ss"
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
        break;
      }
      case "y": {
        // Pattern: "y"
        formatField(d, b, 'y', 1);
        break;
      }
      case "yM": {
        // Pattern: "M/y"
        formatField(d, b, 'M', 1);
        b.append("/");
        formatField(d, b, 'y', 1);
        break;
      }
      case "yMd": {
        // Pattern: "M/d/y"
        formatField(d, b, 'M', 1);
        b.append("/");
        formatField(d, b, 'd', 1);
        b.append("/");
        formatField(d, b, 'y', 1);
        break;
      }
      case "yMEd": {
        // Pattern: "E, M/d/y"
        formatField(d, b, 'E', 1);
        b.append(", ");
        formatField(d, b, 'M', 1);
        b.append("/");
        formatField(d, b, 'd', 1);
        b.append("/");
        formatField(d, b, 'y', 1);
        break;
      }
      case "yMMM": {
        // Pattern: "MMM y"
        formatField(d, b, 'M', 3);
        b.append(" ");
        formatField(d, b, 'y', 1);
        break;
      }
      case "yMMMd": {
        // Pattern: "MMM d, y"
        formatField(d, b, 'M', 3);
        b.append(" ");
        formatField(d, b, 'd', 1);
        b.append(", ");
        formatField(d, b, 'y', 1);
        break;
      }
      case "yMMMEd": {
        // Pattern: "E, MMM d, y"
        formatField(d, b, 'E', 1);
        b.append(", ");
        formatField(d, b, 'M', 3);
        b.append(" ");
        formatField(d, b, 'd', 1);
        b.append(", ");
        formatField(d, b, 'y', 1);
        break;
      }
      case "yMMMM": {
        // Pattern: "MMMM y"
        formatField(d, b, 'M', 4);
        b.append(" ");
        formatField(d, b, 'y', 1);
        break;
      }
      case "yQQQ": {
        // Pattern: "QQQ y"
        formatField(d, b, 'Q', 3);
        b.append(" ");
        formatField(d, b, 'y', 1);
        break;
      }
      case "yQQQQ": {
        // Pattern: "QQQQ y"
        formatField(d, b, 'Q', 4);
        b.append(" ");
        formatField(d, b, 'y', 1);
        break;
      }
      case "yw-count-one": {
        // Pattern: "'week' w 'of' y"
        b.append("week ");
        formatField(d, b, 'w', 1);
        b.append(" of ");
        formatField(d, b, 'y', 1);
        break;
      }
      case "yw-count-other": {
        // Pattern: "'week' w 'of' y"
        b.append("week ");
        formatField(d, b, 'w', 1);
        b.append(" of ");
        formatField(d, b, 'y', 1);
        break;
      }
      default: {
        return false;
      }
    }
    return true;
  }
}
