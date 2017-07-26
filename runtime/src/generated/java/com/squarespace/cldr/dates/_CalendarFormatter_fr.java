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
 * Locale "fr"
 * See http://www.unicode.org/reports/tr35/tr35-dates.html#Date_Field_Symbol_Table
 */
public class _CalendarFormatter_fr extends CalendarFormatterBase {
  public _CalendarFormatter_fr() {
    this.locale = new CLDRLocale("fr", "", "", "");
    this.firstDay = 1;
    this.minDays = 1;
    this.eras = new FieldVariants(
          new String[] {"av. J.-C.", "ap. J.-C."}, 
          new String[] {"av. J.-C.", "ap. J.-C."}, 
          new String[] {}, 
          new String[] {"avant Jésus-Christ", "après Jésus-Christ"});
    this.quartersFormat = new FieldVariants(
          new String[] {"T1", "T2", "T3", "T4"}, 
          new String[] {"1", "2", "3", "4"}, 
          new String[] {}, 
          new String[] {"1er trimestre", "2e trimestre", "3e trimestre", "4e trimestre"});
    this.quartersStandalone = new FieldVariants(
          new String[] {"T1", "T2", "T3", "T4"}, 
          new String[] {"1", "2", "3", "4"}, 
          new String[] {}, 
          new String[] {"1er trimestre", "2e trimestre", "3e trimestre", "4e trimestre"});
    this.monthsFormat = new FieldVariants(
          new String[] {"janv.", "févr.", "mars", "avr.", "mai", "juin", "juil.", "août", "sept.", "oct.", "nov.", "déc."}, 
          new String[] {"J", "F", "M", "A", "M", "J", "J", "A", "S", "O", "N", "D"}, 
          new String[] {}, 
          new String[] {"janvier", "février", "mars", "avril", "mai", "juin", "juillet", "août", "septembre", "octobre", "novembre", "décembre"});
    this.monthsStandalone = new FieldVariants(
          new String[] {"janv.", "févr.", "mars", "avr.", "mai", "juin", "juil.", "août", "sept.", "oct.", "nov.", "déc."}, 
          new String[] {"J", "F", "M", "A", "M", "J", "J", "A", "S", "O", "N", "D"}, 
          new String[] {}, 
          new String[] {"janvier", "février", "mars", "avril", "mai", "juin", "juillet", "août", "septembre", "octobre", "novembre", "décembre"});
    this.weekdaysFormat = new FieldVariants(
          new String[] {"dim.", "lun.", "mar.", "mer.", "jeu.", "ven.", "sam."}, 
          new String[] {"D", "L", "M", "M", "J", "V", "S"}, 
          new String[] {"di", "lu", "ma", "me", "je", "ve", "sa"}, 
          new String[] {"dimanche", "lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi"});
    this.weekdaysStandalone = new FieldVariants(
          new String[] {"dim.", "lun.", "mar.", "mer.", "jeu.", "ven.", "sam."}, 
          new String[] {"D", "L", "M", "M", "J", "V", "S"}, 
          new String[] {"di", "lu", "ma", "me", "je", "ve", "sa"}, 
          new String[] {"dimanche", "lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi"});
    this.dayPeriodsFormat = new FieldVariants(
          new String[] {"AM", "PM"}, 
          new String[] {"AM", "PM"}, 
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
        // "dd/MM/y"
        formatField(d, b, 'd', 2);
        b.append("/");
        formatField(d, b, 'M', 2);
        b.append("/");
        formatField(d, b, 'y', 1);
        break;
      }
      case MEDIUM: {
        // "d MMM y"
        formatField(d, b, 'd', 1);
        b.append(" ");
        formatField(d, b, 'M', 3);
        b.append(" ");
        formatField(d, b, 'y', 1);
        break;
      }
      case LONG: {
        // "d MMMM y"
        formatField(d, b, 'd', 1);
        b.append(" ");
        formatField(d, b, 'M', 4);
        b.append(" ");
        formatField(d, b, 'y', 1);
        break;
      }
      case FULL: {
        // "EEEE d MMMM y"
        formatField(d, b, 'E', 4);
        b.append(" ");
        formatField(d, b, 'd', 1);
        b.append(" ");
        formatField(d, b, 'M', 4);
        b.append(" ");
        formatField(d, b, 'y', 1);
        break;
      }
    }
  }

  @Override
  public void formatTime(FormatType type, ZonedDateTime d, StringBuilder b) {
    switch (type) {
      case SHORT: {
        // "HH:mm"
        formatField(d, b, 'H', 2);
        b.append(":");
        formatField(d, b, 'm', 2);
        break;
      }
      case MEDIUM: {
        // "HH:mm:ss"
        formatField(d, b, 'H', 2);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
        break;
      }
      case LONG: {
        // "HH:mm:ss z"
        formatField(d, b, 'H', 2);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
        b.append(" ");
        formatField(d, b, 'z', 1);
        break;
      }
      case FULL: {
        // "HH:mm:ss zzzz"
        formatField(d, b, 'H', 2);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
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
       {
        // "{1} {0}"
        if (dateType != null) {
          formatDate(dateType, d, b);
        } else {
          formatSkeleton(dateSkel, d, b);
        }
        b.append(" ");
        if (timeType != null) {
          formatTime(timeType, d, b);
        } else {
          formatSkeleton(timeSkel, d, b);
        }
        break;
      }
      case MEDIUM:
      case LONG:
      case FULL:
       {
        // "{1} 'à' {0}"
        if (dateType != null) {
          formatDate(dateType, d, b);
        } else {
          formatSkeleton(dateSkel, d, b);
        }
        b.append(" à ");
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
        // Pattern: "E"
        formatField(d, b, 'E', 1);
        break;
      }
      case "Ed": {
        // Pattern: "E d"
        formatField(d, b, 'E', 1);
        b.append(" ");
        formatField(d, b, 'd', 1);
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
        // Pattern: "d MMM y G"
        formatField(d, b, 'd', 1);
        b.append(" ");
        formatField(d, b, 'M', 3);
        b.append(" ");
        formatField(d, b, 'y', 1);
        b.append(" ");
        formatField(d, b, 'G', 1);
        break;
      }
      case "GyMMMEd": {
        // Pattern: "E d MMM y G"
        formatField(d, b, 'E', 1);
        b.append(" ");
        formatField(d, b, 'd', 1);
        b.append(" ");
        formatField(d, b, 'M', 3);
        b.append(" ");
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
        // Pattern: "HH 'h'"
        formatField(d, b, 'H', 2);
        b.append(" h");
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
        // Pattern: "dd/MM"
        formatField(d, b, 'd', 2);
        b.append("/");
        formatField(d, b, 'M', 2);
        break;
      }
      case "MEd": {
        // Pattern: "E dd/MM"
        formatField(d, b, 'E', 1);
        b.append(" ");
        formatField(d, b, 'd', 2);
        b.append("/");
        formatField(d, b, 'M', 2);
        break;
      }
      case "MMM": {
        // Pattern: "LLL"
        formatField(d, b, 'L', 3);
        break;
      }
      case "MMMd": {
        // Pattern: "d MMM"
        formatField(d, b, 'd', 1);
        b.append(" ");
        formatField(d, b, 'M', 3);
        break;
      }
      case "MMMEd": {
        // Pattern: "E d MMM"
        formatField(d, b, 'E', 1);
        b.append(" ");
        formatField(d, b, 'd', 1);
        b.append(" ");
        formatField(d, b, 'M', 3);
        break;
      }
      case "MMMMd": {
        // Pattern: "d MMMM"
        formatField(d, b, 'd', 1);
        b.append(" ");
        formatField(d, b, 'M', 4);
        break;
      }
      case "MMMMW-count-one": {
        // Pattern: "'semaine' W 'de' MMM"
        b.append("semaine ");
        formatField(d, b, 'W', 1);
        b.append(" de ");
        formatField(d, b, 'M', 3);
        break;
      }
      case "MMMMW-count-other": {
        // Pattern: "'semaine' W 'de' MMM"
        b.append("semaine ");
        formatField(d, b, 'W', 1);
        b.append(" de ");
        formatField(d, b, 'M', 3);
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
        // Pattern: "MM/y"
        formatField(d, b, 'M', 2);
        b.append("/");
        formatField(d, b, 'y', 1);
        break;
      }
      case "yMd": {
        // Pattern: "dd/MM/y"
        formatField(d, b, 'd', 2);
        b.append("/");
        formatField(d, b, 'M', 2);
        b.append("/");
        formatField(d, b, 'y', 1);
        break;
      }
      case "yMEd": {
        // Pattern: "E dd/MM/y"
        formatField(d, b, 'E', 1);
        b.append(" ");
        formatField(d, b, 'd', 2);
        b.append("/");
        formatField(d, b, 'M', 2);
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
        // Pattern: "d MMM y"
        formatField(d, b, 'd', 1);
        b.append(" ");
        formatField(d, b, 'M', 3);
        b.append(" ");
        formatField(d, b, 'y', 1);
        break;
      }
      case "yMMMEd": {
        // Pattern: "E d MMM y"
        formatField(d, b, 'E', 1);
        b.append(" ");
        formatField(d, b, 'd', 1);
        b.append(" ");
        formatField(d, b, 'M', 3);
        b.append(" ");
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
        // Pattern: "'semaine' w 'de' y"
        b.append("semaine ");
        formatField(d, b, 'w', 1);
        b.append(" de ");
        formatField(d, b, 'y', 1);
        break;
      }
      case "yw-count-other": {
        // Pattern: "'semaine' w 'de' y"
        b.append("semaine ");
        formatField(d, b, 'w', 1);
        b.append(" de ");
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
