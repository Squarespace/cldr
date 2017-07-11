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
 * Locale "zh"
 * See http://www.unicode.org/reports/tr35/tr35-dates.html#Date_Field_Symbol_Table
 */
public class _CalendarFormatter_zh extends CalendarFormatterBase {
  public _CalendarFormatter_zh() {
    this.locale = new CLDRLocale("zh", "", "", "");
    this.firstDay = 1;
    this.minDays = 1;
    this.eras = new FieldVariants(
          new String[] {"公元前", "公元"}, 
          new String[] {"公元前", "公元"}, 
          new String[] {}, 
          new String[] {"公元前", "公元"});
    this.quartersFormat = new FieldVariants(
          new String[] {"1季度", "2季度", "3季度", "4季度"}, 
          new String[] {"1", "2", "3", "4"}, 
          new String[] {}, 
          new String[] {"第一季度", "第二季度", "第三季度", "第四季度"});
    this.quartersStandalone = new FieldVariants(
          new String[] {"1季度", "2季度", "3季度", "4季度"}, 
          new String[] {"1", "2", "3", "4"}, 
          new String[] {}, 
          new String[] {"第一季度", "第二季度", "第三季度", "第四季度"});
    this.monthsFormat = new FieldVariants(
          new String[] {"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"}, 
          new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}, 
          new String[] {}, 
          new String[] {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"});
    this.monthsStandalone = new FieldVariants(
          new String[] {"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"}, 
          new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}, 
          new String[] {}, 
          new String[] {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"});
    this.weekdaysFormat = new FieldVariants(
          new String[] {"周日", "周一", "周二", "周三", "周四", "周五", "周六"}, 
          new String[] {"日", "一", "二", "三", "四", "五", "六"}, 
          new String[] {"周日", "周一", "周二", "周三", "周四", "周五", "周六"}, 
          new String[] {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"});
    this.weekdaysStandalone = new FieldVariants(
          new String[] {"周日", "周一", "周二", "周三", "周四", "周五", "周六"}, 
          new String[] {"日", "一", "二", "三", "四", "五", "六"}, 
          new String[] {"周日", "周一", "周二", "周三", "周四", "周五", "周六"}, 
          new String[] {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"});
    this.dayPeriodsFormat = new FieldVariants(
          new String[] {"上午", "下午"}, 
          new String[] {"上午", "下午"}, 
          new String[] {}, 
          new String[] {"上午", "下午"});
    this.dayPeriodsStandalone = new FieldVariants(
          new String[] {"上午", "下午"}, 
          new String[] {"上午", "下午"}, 
          new String[] {}, 
          new String[] {"上午", "下午"});
  }

  @Override
  public void formatDate(FormatType type, ZonedDateTime d, StringBuilder b) {
    switch (type) {
      case SHORT: {
        // "y/M/d"
        formatField(d, b, 'y', 1);
        b.append("/");
        formatField(d, b, 'M', 1);
        b.append("/");
        formatField(d, b, 'd', 1);
        break;
      }
      case MEDIUM: {
        // "y年M月d日"
        formatField(d, b, 'y', 1);
        b.append("年");
        formatField(d, b, 'M', 1);
        b.append("月");
        formatField(d, b, 'd', 1);
        b.append("日");
        break;
      }
      case LONG: {
        // "y年M月d日"
        formatField(d, b, 'y', 1);
        b.append("年");
        formatField(d, b, 'M', 1);
        b.append("月");
        formatField(d, b, 'd', 1);
        b.append("日");
        break;
      }
      case FULL: {
        // "y年M月d日EEEE"
        formatField(d, b, 'y', 1);
        b.append("年");
        formatField(d, b, 'M', 1);
        b.append("月");
        formatField(d, b, 'd', 1);
        b.append("日");
        formatField(d, b, 'E', 4);
        break;
      }
    }
  }

  @Override
  public void formatTime(FormatType type, ZonedDateTime d, StringBuilder b) {
    switch (type) {
      case SHORT: {
        // "ah:mm"
        formatField(d, b, 'a', 1);
        formatField(d, b, 'h', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        break;
      }
      case MEDIUM: {
        // "ah:mm:ss"
        formatField(d, b, 'a', 1);
        formatField(d, b, 'h', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
        break;
      }
      case LONG: {
        // "z ah:mm:ss"
        formatField(d, b, 'z', 1);
        b.append(" ");
        formatField(d, b, 'a', 1);
        formatField(d, b, 'h', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
        break;
      }
      case FULL: {
        // "zzzz ah:mm:ss"
        formatField(d, b, 'z', 4);
        b.append(" ");
        formatField(d, b, 'a', 1);
        formatField(d, b, 'h', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
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
      case LONG:
      case FULL:
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
    }
  }

  @Override
  public boolean formatSkeleton(String skeleton, ZonedDateTime d, StringBuilder b) {
    switch (skeleton) {
      case "d": {
        // Pattern: "d日"
        formatField(d, b, 'd', 1);
        b.append("日");
        break;
      }
      case "E": {
        // Pattern: "ccc"
        formatField(d, b, 'c', 3);
        break;
      }
      case "Ed": {
        // Pattern: "d日E"
        formatField(d, b, 'd', 1);
        b.append("日");
        formatField(d, b, 'E', 1);
        break;
      }
      case "Ehm": {
        // Pattern: "Eah:mm"
        formatField(d, b, 'E', 1);
        formatField(d, b, 'a', 1);
        formatField(d, b, 'h', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        break;
      }
      case "EHm": {
        // Pattern: "EHH:mm"
        formatField(d, b, 'E', 1);
        formatField(d, b, 'H', 2);
        b.append(":");
        formatField(d, b, 'm', 2);
        break;
      }
      case "Ehms": {
        // Pattern: "Eah:mm:ss"
        formatField(d, b, 'E', 1);
        formatField(d, b, 'a', 1);
        formatField(d, b, 'h', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
        break;
      }
      case "EHms": {
        // Pattern: "EHH:mm:ss"
        formatField(d, b, 'E', 1);
        formatField(d, b, 'H', 2);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
        break;
      }
      case "Gy": {
        // Pattern: "Gy年"
        formatField(d, b, 'G', 1);
        formatField(d, b, 'y', 1);
        b.append("年");
        break;
      }
      case "GyMMM": {
        // Pattern: "Gy年M月"
        formatField(d, b, 'G', 1);
        formatField(d, b, 'y', 1);
        b.append("年");
        formatField(d, b, 'M', 1);
        b.append("月");
        break;
      }
      case "GyMMMd": {
        // Pattern: "Gy年M月d日"
        formatField(d, b, 'G', 1);
        formatField(d, b, 'y', 1);
        b.append("年");
        formatField(d, b, 'M', 1);
        b.append("月");
        formatField(d, b, 'd', 1);
        b.append("日");
        break;
      }
      case "GyMMMEd": {
        // Pattern: "Gy年M月d日E"
        formatField(d, b, 'G', 1);
        formatField(d, b, 'y', 1);
        b.append("年");
        formatField(d, b, 'M', 1);
        b.append("月");
        formatField(d, b, 'd', 1);
        b.append("日");
        formatField(d, b, 'E', 1);
        break;
      }
      case "h": {
        // Pattern: "ah时"
        formatField(d, b, 'a', 1);
        formatField(d, b, 'h', 1);
        b.append("时");
        break;
      }
      case "H": {
        // Pattern: "H时"
        formatField(d, b, 'H', 1);
        b.append("时");
        break;
      }
      case "hm": {
        // Pattern: "ah:mm"
        formatField(d, b, 'a', 1);
        formatField(d, b, 'h', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
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
        // Pattern: "ah:mm:ss"
        formatField(d, b, 'a', 1);
        formatField(d, b, 'h', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
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
        // Pattern: "v ah:mm:ss"
        formatField(d, b, 'v', 1);
        b.append(" ");
        formatField(d, b, 'a', 1);
        formatField(d, b, 'h', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
        break;
      }
      case "Hmsv": {
        // Pattern: "v HH:mm:ss"
        formatField(d, b, 'v', 1);
        b.append(" ");
        formatField(d, b, 'H', 2);
        b.append(":");
        formatField(d, b, 'm', 2);
        b.append(":");
        formatField(d, b, 's', 2);
        break;
      }
      case "hmv": {
        // Pattern: "v ah:mm"
        formatField(d, b, 'v', 1);
        b.append(" ");
        formatField(d, b, 'a', 1);
        formatField(d, b, 'h', 1);
        b.append(":");
        formatField(d, b, 'm', 2);
        break;
      }
      case "Hmv": {
        // Pattern: "v HH:mm"
        formatField(d, b, 'v', 1);
        b.append(" ");
        formatField(d, b, 'H', 2);
        b.append(":");
        formatField(d, b, 'm', 2);
        break;
      }
      case "M": {
        // Pattern: "M月"
        formatField(d, b, 'M', 1);
        b.append("月");
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
        // Pattern: "M/dE"
        formatField(d, b, 'M', 1);
        b.append("/");
        formatField(d, b, 'd', 1);
        formatField(d, b, 'E', 1);
        break;
      }
      case "MMdd": {
        // Pattern: "MM/dd"
        formatField(d, b, 'M', 2);
        b.append("/");
        formatField(d, b, 'd', 2);
        break;
      }
      case "MMM": {
        // Pattern: "LLL"
        formatField(d, b, 'L', 3);
        break;
      }
      case "MMMd": {
        // Pattern: "M月d日"
        formatField(d, b, 'M', 1);
        b.append("月");
        formatField(d, b, 'd', 1);
        b.append("日");
        break;
      }
      case "MMMEd": {
        // Pattern: "M月d日E"
        formatField(d, b, 'M', 1);
        b.append("月");
        formatField(d, b, 'd', 1);
        b.append("日");
        formatField(d, b, 'E', 1);
        break;
      }
      case "MMMMd": {
        // Pattern: "M月d日"
        formatField(d, b, 'M', 1);
        b.append("月");
        formatField(d, b, 'd', 1);
        b.append("日");
        break;
      }
      case "MMMMW-count-other": {
        // Pattern: "MMM第W周"
        formatField(d, b, 'M', 3);
        b.append("第");
        formatField(d, b, 'W', 1);
        b.append("周");
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
        // Pattern: "y年"
        formatField(d, b, 'y', 1);
        b.append("年");
        break;
      }
      case "yM": {
        // Pattern: "y年M月"
        formatField(d, b, 'y', 1);
        b.append("年");
        formatField(d, b, 'M', 1);
        b.append("月");
        break;
      }
      case "yMd": {
        // Pattern: "y/M/d"
        formatField(d, b, 'y', 1);
        b.append("/");
        formatField(d, b, 'M', 1);
        b.append("/");
        formatField(d, b, 'd', 1);
        break;
      }
      case "yMEd": {
        // Pattern: "y/M/dE"
        formatField(d, b, 'y', 1);
        b.append("/");
        formatField(d, b, 'M', 1);
        b.append("/");
        formatField(d, b, 'd', 1);
        formatField(d, b, 'E', 1);
        break;
      }
      case "yMM": {
        // Pattern: "y年M月"
        formatField(d, b, 'y', 1);
        b.append("年");
        formatField(d, b, 'M', 1);
        b.append("月");
        break;
      }
      case "yMMM": {
        // Pattern: "y年M月"
        formatField(d, b, 'y', 1);
        b.append("年");
        formatField(d, b, 'M', 1);
        b.append("月");
        break;
      }
      case "yMMMd": {
        // Pattern: "y年M月d日"
        formatField(d, b, 'y', 1);
        b.append("年");
        formatField(d, b, 'M', 1);
        b.append("月");
        formatField(d, b, 'd', 1);
        b.append("日");
        break;
      }
      case "yMMMEd": {
        // Pattern: "y年M月d日E"
        formatField(d, b, 'y', 1);
        b.append("年");
        formatField(d, b, 'M', 1);
        b.append("月");
        formatField(d, b, 'd', 1);
        b.append("日");
        formatField(d, b, 'E', 1);
        break;
      }
      case "yMMMM": {
        // Pattern: "y年M月"
        formatField(d, b, 'y', 1);
        b.append("年");
        formatField(d, b, 'M', 1);
        b.append("月");
        break;
      }
      case "yQQQ": {
        // Pattern: "y年第Q季度"
        formatField(d, b, 'y', 1);
        b.append("年第");
        formatField(d, b, 'Q', 1);
        b.append("季度");
        break;
      }
      case "yQQQQ": {
        // Pattern: "y年第Q季度"
        formatField(d, b, 'y', 1);
        b.append("年第");
        formatField(d, b, 'Q', 1);
        b.append("季度");
        break;
      }
      case "yw-count-other": {
        // Pattern: "y年第w周"
        formatField(d, b, 'y', 1);
        b.append("年第");
        formatField(d, b, 'w', 1);
        b.append("周");
        break;
      }
      default: {
        return false;
      }
    }
    return true;
  }
}
