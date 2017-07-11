package com.squarespace.cldr.numbers;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberFormattingUtils {

  /**
   * Calculates the number of integer and fractional digits to emit, returning
   * them in a 2-element array, and updates the operands.
   *
   * Note: If the significant digit parameters are both positive, we switch into
   * "significant digit mode". Some formatting operations require a minimum number
   * of significant digits to be emitted, as in the case of compact formats.
   * For example, the value 1200 may be emitted as 1.2K using a format "0K" with
   * minSigInt=1 and maxSigInt=2.
   */
  public static long[] setup(
      BigDecimal n,                     // number to be formatted
      NumberOperands operands,          // helper to compute digit counts
      NumberRoundMode roundMode,        // rounding mode
      int minIntDigits,                 // maximum integer digits to emit
      int maxFracDigits,                // maximum fractional digits to emit
      int minFracDigits,                // minimum fractional digits to emit
      int maxSigDigits,                 // maximum significant digits to emit
      int minSigDigits) {               // minimum significant digits to emit

    // Hold the exact number of digits to emit for the integer and fractional
    // parts of the formatted number.
    long intDigits = 0;
    long fracDigits = 0;
    RoundingMode mode = roundMode.toRoundingMode();

    // Select "significant digits" or "integer/fraction digits" mode
    if (minSigDigits > 0 && maxSigDigits > 0) {
      // Scale the number to have at most the maximum significant digits.
      if (n.precision() > maxSigDigits) {
        int scale = maxSigDigits - n.precision() + n.scale();
        n = n.setScale(scale, RoundingMode.HALF_EVEN);
      }

      // Ensure that one less digit is emitted if the number is exactly zero.
      n = n.stripTrailingZeros();
      boolean zero = n.compareTo(BigDecimal.ZERO) == 0;
      int precision = n.precision();
      if (zero && n.scale() == 1) {
        precision--;
      }

      // Scale the number to have at least the minimum significant digits.
      if (precision < minSigDigits) {
        int scale = minSigDigits - precision + n.scale();
        n = n.setScale(scale, RoundingMode.HALF_EVEN);
      }

      intDigits = n.precision() - n.scale();
      intDigits = intDigits <= 0 ? 1 : intDigits;
      fracDigits = n.scale() > 0 ? n.scale() : 0;

      // Recompute the operands.
      operands.set(n.toPlainString());

    } else {

      // This mode provides precise control over the number of integer and fractional
      // digits to include in the result, e.g. when formatting exact currency values.
      int scale = Math.max(minFracDigits, Math.min(n.scale(), maxFracDigits));
      n = n.setScale(scale, mode);

      // Recompute the operands before using the 'w' operand below. In this mode
      // we ignore trailing zeroes.
      operands.set(n.toPlainString());

      // Compute how many digits we'll emit based on the scaled number.
      intDigits = Math.max(n.precision() - n.scale(), minIntDigits);
      fracDigits = Math.min(Math.max(minFracDigits, operands.w()), maxFracDigits);
    }

    return new long[] { intDigits, fracDigits };
  }

  /**
   * Formats the number into the buffer.
   */
  public static void format(
      long[] digits,
      DigitBuffer buf,
      NumberOperands operands,
      NumberFormatterParams params,
      boolean currencyMode,
      boolean grouping,
      int primaryGroupingSize,
      int secondaryGroupingSize) {

    long intDigits = digits[0];
    long fracDigits = digits[1];

    // Setup integer digit grouping.
    if (secondaryGroupingSize == 0) {
      secondaryGroupingSize = primaryGroupingSize;
    }
    int groupSize = primaryGroupingSize;
    boolean shouldGroup = grouping && intDigits >= (params.minimumGroupingDigits + primaryGroupingSize);

    // Select the decimal and grouping characters.
    String decimal = currencyMode ? params.currencyDecimal : params.decimal;
    String group = currencyMode ? params.currencyGroup : params.group;

    // Keep a pointer to the end of the buffer, since we render the formatted number
    // in reverse to be a bit more compact.
    int bufferStart = buf.size();

    // Fraction part.
    if (fracDigits > 0) {
      long value = operands.nd();
      long zeroes = operands.v() - fracDigits;
      for (int i = 0; i < zeroes; i++) {
        value /= 10;
      }
      for (int i = 0; i < fracDigits; i++) {
        int ix = (int)(value % 10);
        buf.append(params.digits[ix]);
        value /= 10;
      }
      buf.append(decimal);
    }

    // Integer part.
    long value = operands.n();
    int emitted = 0;
    for (int i = 0; i < intDigits; i++) {

      // Emit grouping digits.
      if (shouldGroup) {
        boolean emit = emitted > 0 && emitted % groupSize == 0;
        if (emit) {
          // Append a grouping character and switch to the secondary grouping size
          buf.append(group);
          emitted -= groupSize;
          groupSize = secondaryGroupingSize;
        }
      }

      int ix = (int)(value % 10);
      buf.append(params.digits[ix]);
      value /= 10;
      emitted++;
    }

    // Reverse just the characters we formatted.
    buf.reverse(bufferStart);
  }

}
