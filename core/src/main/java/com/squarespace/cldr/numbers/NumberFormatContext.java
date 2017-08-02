package com.squarespace.cldr.numbers;

import static com.squarespace.cldr.numbers.NumberFormatMode.SIGNIFICANT;
import static com.squarespace.cldr.numbers.NumberFormatMode.SIGNIFICANT_MAXFRAC;

import java.math.BigDecimal;

import com.squarespace.cldr.numbers.NumberPattern.Format;

/**
 * WIP: Gradually reorganizing some number formatting logic.
 */
public class NumberFormatContext {

  private final NumberFormatOptions<?> options;
  private final NumberFormatMode formatMode;
  private int minIntDigits;
  private int maxFracDigits;
  private int minFracDigits;
  private int maxSigDigits = -1;
  private int minSigDigits = -1;
  private int currencyDigits = -1;

  public NumberFormatContext(NumberFormatOptions<?> options, NumberFormatMode defaultFormatMode) {
    this(options, defaultFormatMode, -1);
  }

  public NumberFormatContext(NumberFormatOptions<?> options, NumberFormatMode defaultFormatMode, int currencyDigits) {
    this.options = options;
    this.formatMode = orDefault(options.formatMode(), defaultFormatMode);
    this.currencyDigits = currencyDigits;
  }

  public int minIntDigits() {
    return minIntDigits;
  }

  public void set(NumberPattern pattern) {
    setPattern(pattern, -1, -1);
  }

  public void setCompact(NumberPattern pattern, int integerDigits, int divisor) {
    int _maxSigDigits = Math.max(pattern.format.minimumIntegerDigits, integerDigits) + 1;
    int _minSigDigits = 1;
    if (divisor == 0) {
      _maxSigDigits = integerDigits + 1;
    }
    setPattern(pattern, _maxSigDigits, _minSigDigits);
  }

  /**
   * Set the pattern and initialize parameters based on the arguments, pattern and options settings.
   */
  public void setPattern(NumberPattern pattern, int _maxSigDigits, int _minSigDigits) {
    Format format = pattern.format();
    minIntDigits = orDefault(options.minimumIntegerDigits(), format.minimumIntegerDigits());

    maxFracDigits = currencyDigits == -1 ? format.maximumFractionDigits() : currencyDigits;
    maxFracDigits = orDefault(options.maximumFractionDigits(), maxFracDigits);

    minFracDigits = currencyDigits == -1 ? format.minimumFractionDigits() : currencyDigits;
    minFracDigits = orDefault(options.minimumFractionDigits(), minFracDigits);

    boolean useSignificant = formatMode == SIGNIFICANT || formatMode == SIGNIFICANT_MAXFRAC;
    if (useSignificant) {
        maxSigDigits = orDefault(options.maximumSignificantDigits(), _maxSigDigits);
        minSigDigits = orDefault(options.minimumSignificantDigits(), _minSigDigits);
    } else {
        maxSigDigits = -1;
        minSigDigits = -1;
    }
  }

  /**
   * Adjust the number using all of the options.
   */
  public BigDecimal adjust(BigDecimal n) {
    return NumberFormattingUtils.setup(n,
        options.roundMode(), formatMode, minIntDigits, maxFracDigits, minFracDigits, maxSigDigits, minSigDigits);
  }

  /**
   * Return the integer, or a default if null.
  */
  protected static int orDefault(Integer n, int alt) {
    return n == null ? alt : n;
  }

  protected static NumberFormatMode orDefault(NumberFormatMode m, NumberFormatMode alt) {
    return m == null ? alt : m;
  }

}
