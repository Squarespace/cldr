package com.squarespace.cldr;

import com.squarespace.cldr.dates.CalendarFormat;
import com.squarespace.cldr.dates.CalendarFormatOptions;
import com.squarespace.cldr.dates.CalendarSkeleton;


class MessageArgsCalendarParser implements MessageArgsParser {

  private final CalendarFormatOptions opts = new CalendarFormatOptions();
  
  public CalendarFormatOptions options() {
    return opts;
  }
  
  public void reset() {
    opts.reset();
  }
  
  @Override
  public void set(String key, String value) {
    switch (key) {
      case "date":
      {
        CalendarFormat format = calendarFormat(value);
        if (format == null) {
          opts.setDateSkeleton(CalendarSkeleton.fromString(value));
        } else {
          opts.setDateFormat(format);
        }
        break;
      }
        
      case "time":
      {
        CalendarFormat format = calendarFormat(value);
        if (format == null) {
          opts.setTimeSkeleton(CalendarSkeleton.fromString(value));
        } else {
          opts.setTimeFormat(calendarFormat(value));
        }
        break;
      }
      
      case "datetime":
      case "wrap":
      case "wrapper":
        opts.setWrapperFormat(calendarFormat(value));
        break;
    }

  }
  
  private CalendarFormat calendarFormat(String arg) {
    if (arg == null) {
      return null;
    }
    switch (arg) {
      case "short":
        return CalendarFormat.SHORT;
        
      case "medium":
        return CalendarFormat.MEDIUM;
        
      case "long":
        return CalendarFormat.LONG;
        
      case "full":
        return CalendarFormat.FULL;
        
      default:
        return null;
    }
  }

}
