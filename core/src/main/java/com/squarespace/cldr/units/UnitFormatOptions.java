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

  /**
   * Reset the options to their defaults.
   */
  public void reset() {
    this.format = DEFAULT_FORMAT;
    super.reset();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    buf.append("UnitFormatOptions(");
    repr(buf, ", ");
    buf.append(')');
    return buf.toString();
  }

  /**
   * Format this object's properties using the given separator.
   */
  public void repr(StringBuilder buf, String sep) {
    buf.append("format=").append(format);
    super.repr(buf, sep);
  }

}
