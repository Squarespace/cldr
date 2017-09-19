package com.squarespace.cldr.numbers;

/**
 * Options to select and customize decimal / percent formats. This lets you override
 * the defaults for a given format. For example, to force display of leading zeroes
 * on an integer, or a specific number of decimal digits.
 */
public class DecimalFormatOptions extends NumberFormatOptions<DecimalFormatOptions> {

  protected static final DecimalFormatStyle DEFAULT_STYLE = DecimalFormatStyle.DECIMAL;
  protected DecimalFormatStyle style;

  public DecimalFormatOptions() {
    this(DEFAULT_STYLE);
  }

  public DecimalFormatOptions(DecimalFormatStyle style) {
    setStyle(style);
  }

  /**
   * Reset the options to their defaults.
   */
  public void reset() {
    this.style = DEFAULT_STYLE;
    super.reset();
  }

  public DecimalFormatStyle style() {
    return style;
  }

  public DecimalFormatOptions setStyle(DecimalFormatStyle style) {
    this.style = style == null ? DEFAULT_STYLE : style;
    return this;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    buf.append("DecimalFormatOptions(");
    repr(buf, ", ");
    buf.append(')');
    return buf.toString();
  }

  /**
   * Format this object's properties using the given separator.
   */
  public void repr(StringBuilder buf, String sep) {
    if (style != null) {
      buf.append("style=").append(style);
    }
    super.repr(buf, sep);
  }

}
