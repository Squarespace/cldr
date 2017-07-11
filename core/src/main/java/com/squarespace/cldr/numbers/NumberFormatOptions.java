package com.squarespace.cldr.numbers;


public abstract class NumberFormatOptions<T extends NumberFormatOptions<T>> {

  protected NumberFormatMode formatMode = NumberFormatMode.DEFAULT;
  protected NumberRoundMode roundMode = NumberRoundMode.ROUND;
  protected boolean grouping = false;

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
    this.formatMode = NumberFormatMode.DEFAULT;
    this.roundMode = NumberRoundMode.ROUND;
    this.grouping = false;
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
    this.formatMode = mode == null ? NumberFormatMode.DEFAULT : mode;
    return self();
  }

  public NumberRoundMode roundMode() {
    return roundMode;
  }

  public T setRoundMode(NumberRoundMode mode) {
    this.roundMode = mode == null ? NumberRoundMode.ROUND : mode;
    return self();
  }

  public boolean grouping() {
    return grouping;
  }

  public T setGrouping(boolean value) {
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
    return self();
  }

  public Integer minimumSignificantDigits() {
    return minimumSignificantDigits;
  }

  public T setMinimumSignificantDigits(Integer value) {
    this.minimumSignificantDigits = clamp(value, 1);
    return self();
  }

  protected void repr(StringBuilder buf) {
    buf.append(", formatMode=").append(formatMode);
    buf.append(", roundMode=").append(roundMode);
    buf.append(", useGrouping=").append(grouping);
    buf.append(", minimumIntegerDigits=").append(orEmpty(minimumIntegerDigits));
    buf.append(", maximumFractionDigits=").append(orEmpty(maximumFractionDigits));
    buf.append(", minimumFractionDigits=").append(orEmpty(minimumFractionDigits));
    buf.append(", maximumSignificantDigits=").append(orEmpty(maximumSignificantDigits));
    buf.append(", minimumSignificantDigits=").append(orEmpty(minimumSignificantDigits));
  }

  private static Object orEmpty(Object o) {
    return o == null ? "" : o;
  }

  private static Integer clamp(Integer value, int limit) {
    if (value == null) {
      return null;
    }
    return value < limit ? limit : value;
  }
}
