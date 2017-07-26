package com.squarespace.cldr.numbers;


/**
 * Options to select and customize decimal / percent formats. This lets you override
 * the defaults for a given format. For example, to force display of leading zeroes
 * on an integer, or a specific number of decimal digits.
 */
public class DecimalFormatOptions extends NumberFormatOptions<DecimalFormatOptions> {

  protected NumberFormatStyle style;

  public DecimalFormatOptions() {
    this(NumberFormatStyle.DECIMAL);
  }

  public DecimalFormatOptions(NumberFormatStyle style) {
    this.style = style;
  }

  /**
   * Reset the options to their defaults.
   */
  public void reset() {
    this.style = NumberFormatStyle.DECIMAL;
    super.reset();
  }

  public NumberFormatStyle style() {
    return style;
  }

  public DecimalFormatOptions setStyle(NumberFormatStyle style) {
    this.style = style;
    return this;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    buf.append("NumberFormatOptions(style=").append(style);
    repr(buf);
    buf.append(')');
    return buf.toString();
  }

}
