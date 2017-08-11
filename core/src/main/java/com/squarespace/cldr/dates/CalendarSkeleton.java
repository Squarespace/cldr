package com.squarespace.cldr.dates;

import java.util.HashMap;
import java.util.Map;

/**
 * Date and time skeletons common to all 360 modern locales.
 */
public enum CalendarSkeleton {

  E("E"),
  EHm("EHm"),
  EHms("EHms"),
  Ed("Ed"),
  Ehm("Ehm"),
  Ehms("Ehms"),
  Gy("Gy"),
  GyMMM("GyMMM"),
  GyMMMEd("GyMMMEd"),
  GyMMMd("GyMMMd"),
  H("H"),
  Hm("Hm"),
  Hms("Hms"),
  Hmsv("Hmsv"),
  Hmv("Hmv"),
  M("M"),
  MEd("MEd"),
  MMM("MMM"),
  MMMEd("MMMEd"),
  MMMMW_count_other("MMMMW-count-other"),
  MMMMd("MMMMd"),
  MMMd("MMMd"),
  Md("Md"),
  d("d"),
  h("h"),
  hm("hm"),
  hms("hms"),
  hmsv("hmsv"),
  hmv("hmv"),
  ms("ms"),
  y("y"),
  yM("yM"),
  yMEd("yMEd"),
  yMMM("yMMM"),
  yMMMEd("yMMMEd"),
  yMMMM("yMMMM"),
  yMMMd("yMMMd"),
  yMd("yMd"),
  yQQQ("yQQQ"),
  yQQQQ("yQQQQ"),
  yw_count_other("yw-count-other")
  ;
  
  private static final Map<String, CalendarSkeleton> strings = new HashMap<>();
  
  static {
    for (CalendarSkeleton c : CalendarSkeleton.values()) {
      strings.put(c.skeleton, c);
    }
  }
  
  private final String skeleton;
  
  private CalendarSkeleton(String skeleton) {
    this.skeleton = skeleton;
  }
  
  public String skeleton() {
    return skeleton;
  }
  
  public static CalendarSkeleton fromString(String string) {
    return strings.get(string);
  }
}
