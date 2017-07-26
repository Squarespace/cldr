package com.squarespace.cldr.numbers;

public abstract class NumberFormatOptions<T extends NumberFormatOptions<T>> {

  private static final int MAX_SIGNIFICANT_DIGITS = 100;

  protected NumberFormatMode formatMode;
  protected NumberRoundMode roundMode = NumberRoundMode.ROUND;
  protected Boolean grouping;

  // Which of these get used depends on the formatMode setting.
  protected Integer minimumIntegerDigits;
  protected Integer maximumFractionDigits;
  protected Integer minimumFractionDigits;
  protected Integer maximumSignificantDigits;
  protected Integer minimumSignificantDigits;

  @SuppressWarnings("unchecked")
  private T self() {
    return (T)this;
  }

  /**
   * Reset the options to their defaults.
   */
  public void reset() {
    this.formatMode = null;
    this.roundMode = NumberRoundMode.ROUND;
    this.grouping = null;
    this.minimumIntegerDigits = null;
    this.maximumFractionDigits = null;
    this.minimumFractionDigits = null;
    this.maximumSignificantDigits = null;
    this.minimumSignificantDigits = null;
  }

  public NumberFormatMode formatMode() {
    return formatMode;
  }

  public T setFormatMode(NumberFormatMode mode) {
    this.formatMode = mode;
    return self();
  }

  public NumberRoundMode roundMode() {
    return roundMode;
  }

  public T setRoundMode(NumberRoundMode mode) {
    this.roundMode = mode == null ? NumberRoundMode.ROUND : mode;
    return self();
  }

  public Boolean grouping() {
    return grouping;
  }

  public T setGrouping(Boolean value) {
    this.grouping = value;
    return self();
  }

  public Integer minimumIntegerDigits() {
    return minimumIntegerDigits;
  }

  public T setMinimumIntegerDigits(Integer value) {
    this.minimumIntegerDigits = clamp(value, 0);
    return self();
  }

  public Integer maximumFractionDigits() {
    return maximumFractionDigits;
  }

  public T setMaximumFractionDigits(Integer value) {
    this.maximumFractionDigits = clamp(value, 0);
    if (this.minimumFractionDigits == null) {
      this.minimumFractionDigits = this.maximumFractionDigits;
    }
    return self();
  }

  public Integer minimumFractionDigits() {
    return minimumFractionDigits;
  }

  public T setMinimumFractionDigits(Integer value) {
    this.minimumFractionDigits = clamp(value, 0);
    if (this.maximumFractionDigits == null) {
      this.maximumFractionDigits = this.minimumFractionDigits;
    }
    return self();
  }

  public Integer maximumSignificantDigits() {
    return maximumSignificantDigits;
  }

  public T setMaximumSignificantDigits(Integer value) {
    this.maximumSignificantDigits = clamp(value, 1);
    if (this.minimumSignificantDigits == null) {
      this.minimumSignificantDigits = 1;
    }
    return self();
  }

  public Integer minimumSignificantDigits() {
    return minimumSignificantDigits;
  }

  public T setMinimumSignificantDigits(Integer value) {
    this.minimumSignificantDigits = clamp(value, 1);
    if (this.maximumSignificantDigits == null) {
      this.maximumSignificantDigits = MAX_SIGNIFICANT_DIGITS;
    }
    return self();
  }

  private static final String[] KEYS = new String[] {
    "format=", "rounding=", "grouping=", "minInt=",
    "maxFrac=", "minFrac=", "maxSig=", "minSig="
  };

  public void repr(StringBuilder buf, String sep) {
    Object[] vals = new Object[] {
        formatMode, roundMode, grouping,
        minimumIntegerDigits, maximumFractionDigits, minimumFractionDigits,
        maximumSignificantDigits, minimumSignificantDigits };
    for (int i = 0; i < KEYS.length; i++) {
      Object val = vals[i];
      if (val != null) {
        if (buf.length() > 0) {
          buf.append(sep);
        }
        buf.append(KEYS[i]).append(val);
      }
    }
  }

  private static Integer clamp(Integer value, int limit) {
    if (value == null) {
      return null;
    }
    return value < limit ? limit : value;
  }
}
