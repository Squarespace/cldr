package com.squarespace.cldr.numbers;

import static com.squarespace.cldr.numbers.NumberFormatMode.SIGNIFICANT;
import static com.squarespace.cldr.numbers.NumberFormatMode.SIGNIFICANT_MAXFRAC;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * Low-level number formatting.
 */
public class NumberFormattingUtils {

  /**
   * Count the number of integer digits in the number.
   */
  public static int integerDigits(BigDecimal n) {
    return n.precision() - n.scale();
  }

  /**
   * Calculates the number of integer and fractional digits to emit, returning
   * them in a 2-element array, and updates the operands.
   *
   * In one of the two significant digit modes, SIGNIFICANT and SIGNIFICANT_MAXFRAC,
   * a min/max number significant digits will be emitted. This is useful for formatting
   * compact formats where the width of the final output is constrained to <= N chars.
   *
   * For example, with the settings minSigDigits=1 and maxSigDigits=2, and a format
   * pattern "0K", the value 1000 would produce "1K", the values 1200 and 1234 would
   * produce "1.2K", and 1599 would produce "1.6K".
   */
  public static BigDecimal setup(
      BigDecimal n,                     // number to be formatted
      NumberRoundMode roundMode,        // rounding mode
      NumberFormatMode formatMode,      // formatting mode (significant digits, etc)
      int minIntDigits,                 // maximum integer digits to emit
      int maxFracDigits,                // maximum fractional digits to emit
      int minFracDigits,                // minimum fractional digits to emit
      int maxSigDigits,                 // maximum significant digits to emit
      int minSigDigits) {               // minimum significant digits to emit

    RoundingMode mode = roundMode.toRoundingMode();
    boolean useSignificant = formatMode == SIGNIFICANT || formatMode == SIGNIFICANT_MAXFRAC;

    // Select "significant digits" or "integer/fraction digits" mode
    if (useSignificant && minSigDigits > 0 && maxSigDigits > 0) {
      // Scale the number to have at most the maximum significant digits.
      if (n.precision() > maxSigDigits) {
        int scale = maxSigDigits - n.precision() + n.scale();
        n = n.setScale(scale, mode);
      }

      // Ensure we don't exceed the maximum number of fraction digits allowed.
      if (formatMode == NumberFormatMode.SIGNIFICANT_MAXFRAC && maxFracDigits < n.scale()) {
        n = n.setScale(maxFracDigits, mode);
      }

      // Ensure that one less digit is emitted if the number is exactly zero.
      n = n.stripTrailingZeros();
      boolean zero = n.signum() == 0;
      int precision = n.precision();
      if (zero && n.scale() == 1) {
        precision--;
      }

      // Scale the number to have at least the minimum significant digits.
      if (precision < minSigDigits) {
        int scale = minSigDigits - precision + n.scale();
        n = n.setScale(scale, mode);
      }

    } else {

      // This mode provides precise control over the number of integer and fractional
      // digits to include in the result, e.g. when formatting exact currency values.
      int scale = Math.max(minFracDigits, Math.min(n.scale(), maxFracDigits));
      n = n.setScale(scale, mode);
      n = n.stripTrailingZeros();

      // Ensure minimum fraction digits is met, even if it means outputting trailing zeros
      if (n.scale() < minFracDigits) {
        n = n.setScale(minFracDigits, mode);
      }
    }

    return n;
  }

  /**
   * Formats the number into the digit buffer.
   */
  public static void format(
      BigDecimal n,
      DigitBuffer buf,
      NumberFormatterParams params,
      boolean currencyMode,
      boolean grouping,
      int minIntDigits,
      int primaryGroupingSize,
      int secondaryGroupingSize) {

    // Setup integer digit grouping.
    if (secondaryGroupingSize <= 0) {
      secondaryGroupingSize = primaryGroupingSize;
    }

    // Check if we need to perform digit grouping.
    int groupSize = primaryGroupingSize;
    boolean shouldGroup = false;

    // Output as many integer digits as are available, unless minIntDigits == 0
    // and our value is < 1.
    long intDigits = integerDigits(n);
    if (minIntDigits == 0 && n.compareTo(BigDecimal.ONE) == -1) {
      intDigits = 0;
    } else {
      intDigits = Math.max(intDigits, minIntDigits);
    }

    if (grouping) {
      if (primaryGroupingSize > 0) {
        shouldGroup = intDigits >= (params.minimumGroupingDigits + primaryGroupingSize);
      }
    }

    // Select the decimal and grouping characters.
    String decimal = currencyMode ? params.currencyDecimal : params.decimal;
    String group = currencyMode ? params.currencyGroup : params.group;

    // Keep a pointer to the end of the buffer, since we render the formatted number
    // in reverse to be a bit more compact.
    int bufferStart = buf.length();

    // We only emit the absolute value of the number, since the indication of
    // negative numbers is pattern and locale-specific.
    if (n.signum() == -1) {
      n = n.negate();
    }

    // Translate the standard decimal representation into the localized form.
    // At this point the number of integer and decimal digits is set, all we're
    // doing is outputting the correct decimal symbol and grouping symbols.

    String s = n.toPlainString();
    int end = s.length() - 1;

    // Emit decimal digits and decimal point.
    int idx = s.lastIndexOf('.');
    if (idx != -1) {
      while (end > idx) {
        buf.append(s.charAt(end));
        end--;
      }

      end--;
      buf.append(decimal);
    }

    // Emit integer part with optional grouping
    int emitted = 0;
    while (intDigits > 0 && end >= 0) {
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
      buf.append(s.charAt(end));
      end--;
      emitted++;
      intDigits--;
    }

    // Emit zeroes to pad integer part with optional grouping
    if (intDigits > 0) {
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
        buf.append('0');
        emitted++;
      }
    }

    buf.reverse(bufferStart);
  }

}
