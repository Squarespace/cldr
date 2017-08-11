package com.squarespace.cldr.dates;


/**
 * Formatting options for dates and times.
 */
public class CalendarFormatOptions {

  protected CalendarFormat dateFormat;
  protected CalendarFormat timeFormat;
  protected CalendarFormat wrapperFormat;
  
  protected CalendarSkeleton dateSkeleton;
  protected CalendarSkeleton timeSkeleton;
  
  public CalendarFormatOptions() {
  }

  public void reset() {
    this.dateFormat = null;
    this.timeFormat = null;
    this.wrapperFormat = null;
    this.dateSkeleton = null;
    this.timeSkeleton = null;
  }
  
  public CalendarFormat dateFormat() {
    return dateFormat;
  }
  
  public CalendarFormatOptions setDateFormat(CalendarFormat type) {
    this.dateFormat = type;
    return this;
  }

  public CalendarFormat timeFormat() {
    return timeFormat;
  }
  
  public CalendarFormatOptions setTimeFormat(CalendarFormat type) {
    this.timeFormat = type;
    return this;
  }
  
  public CalendarFormat wrapperFormat() {
    return wrapperFormat;
  }
  
  public CalendarFormatOptions setWrapperFormat(CalendarFormat type) {
    this.wrapperFormat = type;
    return this;
  }
  
  public CalendarSkeleton dateSkeleton() {
    return dateSkeleton;
  }
  
  public CalendarFormatOptions setDateSkeleton(CalendarSkeleton skeleton) {
    this.dateSkeleton = skeleton;
    return this;
  }
  
  public CalendarSkeleton timeSkeleton() {
    return timeSkeleton;
  }
  
  public CalendarFormatOptions setTimeSkeleton(CalendarSkeleton skeleton) {
    this.timeSkeleton = skeleton;
    return this;
  }
  
}
