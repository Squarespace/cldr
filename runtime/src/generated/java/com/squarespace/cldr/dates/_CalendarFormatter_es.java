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
 * Locale "es"
 * See http://www.unicode.org/reports/tr35/tr35-dates.html#Date_Field_Symbol_Table
 */
public class _CalendarFormatter_es extends CalendarFormatterBase {
  public _CalendarFormatter_es() {
    this.locale = new CLDRLocale("es", "", "", "");
    this.firstDay = 1;
    this.minDays = 1;
    this.eras = new FieldVariants(
          new String[] {"a. C.", "d. C."}, 
          new String[] {"a. C.", "d. C."}, 
          new String[] {}, 
          new String[] {"antes de Cristo", "después de Cristo"});
    this.quartersFormat = new FieldVariants(
          new String[] {"T1", "T2", "T3", "T4"}, 
          new String[] {"1", "2", "3", "4"}, 
          new String[] {}, 
          new String[] {"1.er trimestre", "2.º trimestre", "3.er trimestre", "4.º trimestre"});
    this.quartersStandalone = new FieldVariants(
          new String[] {"T1", "T2", "T3", "T4"}, 
          new String[] {"1", "2", "3", "4"}, 
          new String[] {}, 
          new String[] {"1.er trimestre", "2.º trimestre", "3.er trimestre", "4.º trimestre"});
    this.monthsFormat = new FieldVariants(
          new String[] {"ene.", "feb.", "mar.", "abr.", "may.", "jun.", "jul.", "ago.", "sept.", "oct.", "nov.", "dic."}, 
          new String[] {"E", "F", "M", "A", "M", "J", "J", "A", "S", "O", "N", "D"}, 
          new String[] {}, 
          new String[] {"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"});
    this.monthsStandalone = new FieldVariants(
          new String[] {"ene.", "feb.", "mar.", "abr.", "may.", "jun.", "jul.", "ago.", "sept.", "oct.", "nov.", "dic."}, 
          new String[] {"E", "F", "M", "A", "M", "J", "J", "A", "S", "O", "N", "D"}, 
          new String[] {}, 
          new String[] {"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"});
    this.weekdaysFormat = new FieldVariants(
          new String[] {"dom.", "lun.", "mar.", "mié.", "jue.", "vie.", "sáb."}, 
          new String[] {"D", "L", "M", "X", "J", "V", "S"}, 
          new String[] {"DO", "LU", "MA", "MI", "JU", "VI", "SA"}, 
          new String[] {"domingo", "lunes", "martes", "miércoles", "jueves", "viernes", "sábado"});
    this.weekdaysStandalone = new FieldVariants(
          new String[] {"dom.", "lun.", "mar.", "mié.", "jue.", "vie.", "sáb."}, 
          new String[] {"D", "L", "M", "X", "J", "V", "S"}, 
          new String[] {"DO", "LU", "MA", "MI", "JU", "VI", "SA"}, 
          new String[] {"domingo", "lunes", "martes", "miércoles", "jueves", "viernes", "sábado"});
    this.dayPeriodsFormat = new FieldVariants(
          new String[] {"a. m.", "p. m."}, 
          new String[] {"a. m.", "p. m."}, 
          new String[] {}, 
          new String[] {"a. m.", "p. m."});
    this.dayPeriodsStandalone = new FieldVariants(
          new String[] {"a. m.", "p. m."}, 
          new String[] {"a. m.", "p. m."}, 
          new String[] {}, 
          new String[] {"a. m.", "p. m."});
  }

  @Override
  public void formatDate(FormatType type, ZonedDateTime d, StringBuilder b) {
    switch (type) {
      case SHORT: {
        // "d/M/yy"
        formatField(d, b, 'd', 1);
        b.append("/");
        formatField(d, b, 'M', 1);
        b.append("/");
        formatField(d, b, 'y', 2);
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
        // "d 'de' MMMM 'de' y"
        formatField(d, b, 'd', 1);
        b.append(" de ");
        formatField(d, b, 'M', 4);
        b.append(" de ");
        formatField(d, b, 'y', 1);
        break;
      }
      case FULL: {
        // "EEEE, d 'de' MMMM 'de' y"
        formatField(d, b, 'E', 4);
        b.append(", ");
        formatField(d, b, 'd', 1);
        b.append(" de ");
        formatField(d, b, 'M', 4);
        b.append(" de ");
        formatField(d, b, 'y', 1);
        break;
      }
    }
  }

  @Override
  public void formatTime(FormatType type, ZonedDateTime d, StringBuilder b) {
    switch (type) {
      case SHORT: {
        // "H:mm"
        formatField(d, b, 'H', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        break;
      }
      case MEDIUM: {
        // "H:mm:ss"
        formatField(d, b, 'H', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
        break;
      }
      case LONG: {
        // "H:mm:ss z"
        formatField(d, b, 'H', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
        b.append(" ");
        formatField(d, b, 'z', 1);
        break;
      }
      case FULL: {
        // "H:mm:ss (zzzz)"
        formatField(d, b, 'H', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
        b.append(" (");
        formatField(d, b, 'z', 4);
        b.append(")");
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
      case LONG:
      case FULL:
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
        // Pattern: "E d"
        formatField(d, b, 'E', 1);
        b.append(" ");
        formatField(d, b, 'd', 1);
        break;
      }
      case "Ehm": {
        // Pattern: "E, h:mm a"
        formatField(d, b, 'E', 1);
        b.append(", ");
        formatField(d, b, 'h', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(" ");
        formatField(d, b, 'a', 1);
        break;
      }
      case "EHm": {
        // Pattern: "E, H:mm"
        formatField(d, b, 'E', 1);
        b.append(", ");
        formatField(d, b, 'H', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        break;
      }
      case "Ehms": {
        // Pattern: "E, h:mm:ss a"
        formatField(d, b, 'E', 1);
        b.append(", ");
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
        // Pattern: "E, H:mm:ss"
        formatField(d, b, 'E', 1);
        b.append(", ");
        formatField(d, b, 'H', 1);
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
        // Pattern: "E, d MMM y G"
        formatField(d, b, 'E', 1);
        b.append(", ");
        formatField(d, b, 'd', 1);
        b.append(" ");
        formatField(d, b, 'M', 3);
        b.append(" ");
        formatField(d, b, 'y', 1);
        b.append(" ");
        formatField(d, b, 'G', 1);
        break;
      }
      case "GyMMMM": {
        // Pattern: "MMMM 'de' y G"
        formatField(d, b, 'M', 4);
        b.append(" de ");
        formatField(d, b, 'y', 1);
        b.append(" ");
        formatField(d, b, 'G', 1);
        break;
      }
      case "GyMMMMd": {
        // Pattern: "d 'de' MMMM 'de' y G"
        formatField(d, b, 'd', 1);
        b.append(" de ");
        formatField(d, b, 'M', 4);
        b.append(" de ");
        formatField(d, b, 'y', 1);
        b.append(" ");
        formatField(d, b, 'G', 1);
        break;
      }
      case "GyMMMMEd": {
        // Pattern: "E, d 'de' MMMM 'de' y G"
        formatField(d, b, 'E', 1);
        b.append(", ");
        formatField(d, b, 'd', 1);
        b.append(" de ");
        formatField(d, b, 'M', 4);
        b.append(" de ");
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
        // Pattern: "H"
        formatField(d, b, 'H', 1);
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
        // Pattern: "H:mm"
        formatField(d, b, 'H', 1);
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
        // Pattern: "H:mm:ss"
        formatField(d, b, 'H', 1);
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
        // Pattern: "H:mm:ss v"
        formatField(d, b, 'H', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
        b.append(" ");
        formatField(d, b, 'v', 1);
        break;
      }
      case "hmsvvvv": {
        // Pattern: "h:mm:ss a (vvvv)"
        formatField(d, b, 'h', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
        b.append(" ");
        formatField(d, b, 'a', 1);
        b.append(" (");
        formatField(d, b, 'v', 4);
        b.append(")");
        break;
      }
      case "Hmsvvvv": {
        // Pattern: "H:mm:ss (vvvv)"
        formatField(d, b, 'H', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
        b.append(" (");
        formatField(d, b, 'v', 4);
        b.append(")");
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
        // Pattern: "H:mm v"
        formatField(d, b, 'H', 1);
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
        // Pattern: "d/M"
        formatField(d, b, 'd', 1);
        b.append("/");
        formatField(d, b, 'M', 1);
        break;
      }
      case "MEd": {
        // Pattern: "E, d/M"
        formatField(d, b, 'E', 1);
        b.append(", ");
        formatField(d, b, 'd', 1);
        b.append("/");
        formatField(d, b, 'M', 1);
        break;
      }
      case "MMd": {
        // Pattern: "d/M"
        formatField(d, b, 'd', 1);
        b.append("/");
        formatField(d, b, 'M', 1);
        break;
      }
      case "MMdd": {
        // Pattern: "d/M"
        formatField(d, b, 'd', 1);
        b.append("/");
        formatField(d, b, 'M', 1);
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
        // Pattern: "E, d MMM"
        formatField(d, b, 'E', 1);
        b.append(", ");
        formatField(d, b, 'd', 1);
        b.append(" ");
        formatField(d, b, 'M', 3);
        break;
      }
      case "MMMMd": {
        // Pattern: "d 'de' MMMM"
        formatField(d, b, 'd', 1);
        b.append(" de ");
        formatField(d, b, 'M', 4);
        break;
      }
      case "MMMMEd": {
        // Pattern: "E, d 'de' MMMM"
        formatField(d, b, 'E', 1);
        b.append(", ");
        formatField(d, b, 'd', 1);
        b.append(" de ");
        formatField(d, b, 'M', 4);
        break;
      }
      case "MMMMW-count-one": {
        // Pattern: "'semana' W 'de' MMM"
        b.append("semana ");
        formatField(d, b, 'W', 1);
        b.append(" de ");
        formatField(d, b, 'M', 3);
        break;
      }
      case "MMMMW-count-other": {
        // Pattern: "'semana' W 'de' MMM"
        b.append("semana ");
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
        // Pattern: "M/y"
        formatField(d, b, 'M', 1);
        b.append("/");
        formatField(d, b, 'y', 1);
        break;
      }
      case "yMd": {
        // Pattern: "d/M/y"
        formatField(d, b, 'd', 1);
        b.append("/");
        formatField(d, b, 'M', 1);
        b.append("/");
        formatField(d, b, 'y', 1);
        break;
      }
      case "yMEd": {
        // Pattern: "EEE, d/M/y"
        formatField(d, b, 'E', 3);
        b.append(", ");
        formatField(d, b, 'd', 1);
        b.append("/");
        formatField(d, b, 'M', 1);
        b.append("/");
        formatField(d, b, 'y', 1);
        break;
      }
      case "yMM": {
        // Pattern: "M/y"
        formatField(d, b, 'M', 1);
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
        // Pattern: "EEE, d MMM y"
        formatField(d, b, 'E', 3);
        b.append(", ");
        formatField(d, b, 'd', 1);
        b.append(" ");
        formatField(d, b, 'M', 3);
        b.append(" ");
        formatField(d, b, 'y', 1);
        break;
      }
      case "yMMMM": {
        // Pattern: "MMMM 'de' y"
        formatField(d, b, 'M', 4);
        b.append(" de ");
        formatField(d, b, 'y', 1);
        break;
      }
      case "yMMMMd": {
        // Pattern: "d 'de' MMMM 'de' y"
        formatField(d, b, 'd', 1);
        b.append(" de ");
        formatField(d, b, 'M', 4);
        b.append(" de ");
        formatField(d, b, 'y', 1);
        break;
      }
      case "yMMMMEd": {
        // Pattern: "EEE, d 'de' MMMM 'de' y"
        formatField(d, b, 'E', 3);
        b.append(", ");
        formatField(d, b, 'd', 1);
        b.append(" de ");
        formatField(d, b, 'M', 4);
        b.append(" de ");
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
        // Pattern: "QQQQ 'de' y"
        formatField(d, b, 'Q', 4);
        b.append(" de ");
        formatField(d, b, 'y', 1);
        break;
      }
      case "yw-count-one": {
        // Pattern: "'semana' w 'de' y"
        b.append("semana ");
        formatField(d, b, 'w', 1);
        b.append(" de ");
        formatField(d, b, 'y', 1);
        break;
      }
      case "yw-count-other": {
        // Pattern: "'semana' w 'de' y"
        b.append("semana ");
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
