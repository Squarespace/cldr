package com.squarespace.cldr.dates;

import java.util.HashMap;
import java.util.Map;


/**
 * Date and time interval skeletons. These skeletons are common to all
 * 360 locales with modern coverage.
 */
public enum DateTimeIntervalSkeleton {

  H("H"),
  Hm("Hm"),
  Hmv("Hmv"),
  Hv("Hv"),
  M("M"),
  MEd("MEd"),
  MMM("MMM"),
  MMMEd("MMMEd"),
  MMMd("MMMd"),
  Md("Md"),
  d("d"),
  h("h"),
  hm("hm"),
  hmv("hmv"),
  hv("hv"),
  y("y"),
  yM("yM"),
  yMEd("yMEd"),
  yMMM("yMMM"),
  yMMMEd("yMMMEd"),
  yMMMM("yMMMM"),
  yMMMd("yMMMd"),
  yMd("yMd")
  ;

  private static final Map<String, DateTimeIntervalSkeleton> strings = new HashMap<>();

  static {
    for (DateTimeIntervalSkeleton d : DateTimeIntervalSkeleton.values()) {
      strings.put(d.skeleton, d);
    }
  }

  private final String skeleton;

  private DateTimeIntervalSkeleton(String skeleton) {
    this.skeleton = skeleton;
  }

  public String skeleton() {
    return skeleton;
  }

  public static DateTimeIntervalSkeleton fromString(String string) {
    return strings.get(string);
  }
}
