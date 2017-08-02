package com.squarespace.cldr.units;

import com.squarespace.cldr.numbers.NumberFormatOptions;


/**
 * Formats a number as a unit with the given options.
 */
public class UnitFormatOptions extends NumberFormatOptions<UnitFormatOptions> {

  private static final UnitFormat DEFAULT_FORMAT = UnitFormat.NARROW;
  private UnitFormat format = DEFAULT_FORMAT;

  public UnitFormatOptions() {
  }

  public UnitFormatOptions(UnitFormat format) {
    this.format = format;
  }

  public UnitFormat format() {
    return format;
  }

  public UnitFormatOptions setFormat(UnitFormat format) {
    this.format = format;
    return this;
  }

}
