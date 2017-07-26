package com.squarespace.cldr.numbers;


/**
 * Operands for use in number formatting and evaluating localized plural rules:
 * See: http://www.unicode.org/reports/tr35/tr35-numbers.html#Number_Format_Patterns
 * See: http://www.unicode.org/reports/tr35/tr35-numbers.html#Plural_Operand_Meanings
 *
 * symbol    value
 * ----------------
 *   n       absolute value of the source number (integer and decimals)
 *   i       integer digits of n
 *   v       number of visible fraction digits in n, with trailing zeros
 *   w       number of visible fraction digits in n, without trailing zeros
 *   f       visible fractional digits in n, with trailing zeros
 *   t       visible fractional digits in n, without trailing zeros
 *
 */
public class NumberOperands {

  private static final long LIMIT = 100000000000000000L;
  private static final long INTEGER_REDUCER = 100000000000000L;

  private boolean negative;

  private boolean decimal;

  // Integer portion of 'n'. This is also used for operand 'i'
  private long ni;

  // Decimal portion of 'n'. This is also used for operand 'f'
  private long nd;

  // Number of visible fraction digits in n, with trailing zeroes
  private long v;

  // Number of visible fraction digits in n, without trailing zeroes
  private long w;

  // Visible fractional digits in n, without trailing zeroes
  private long t;

  // Number of leading digits on the decimal portion of 'n'
  private long z;


  public NumberOperands() {
  }

  public NumberOperands(CharSequence number) {
    set(number);
  }

  /**
   * Indicates the number is negative.
   */
  public boolean negative() {
    return negative;
  }

  /**
   * Indicates the number has a decimal part.
   */
  public boolean hasDecimal() {
    return decimal;
  }

  /**
   * Integer portion of the absolute value of 'n'.
   */
  public long n() {
    return ni;
  }

  /**
   * Decimal portion of the absolute value of 'n'.
   */
  public long nd() {
    return nd;
  }

  /**
   * Integer digits of 'n'.
   */
  public long i() {
    return ni;
  }

  /**
   * Number of visible fraction digits in 'n', with trailing zeros.
   */
  public long v() {
    return v;
  }

  /**
   * Number of visible fraction digits in 'n', without trailing zeros.
   */
  public long w() {
    return w;
  }

  /**
   * Visible fractional digits in 'n', with trailing zeros.
   */
  public long f() {
    return nd;
  }

  /**
   * Visible fractional digits in 'n', without trailing zeros.
   */
  public long t() {
    return t;
  }

  /**
   * Number of leading zeroes on the decimal portion of 'n'.
   */
  public long z() {
    return z;
  }

  /**
   * Parses a number in one pass, updating the operands. Note, this assumes the number
   * is in its expanded string representation in the en-US locale, e.g. no exponent part.
   */
  public void set(CharSequence number) {
    // reset the operands
    negative = false;
    decimal = false;
    ni = 0;
    nd = 0;
    v = 0;
    w = 0;
    t = 0;
    z = 0;

    int length = number.length();
    if (length < 1) {
      return;
    }

    // index into the string
    int i = 0;
    if (number.charAt(0) == '-') {
      negative = true;
      i++;
    }

    // track number of trailing zeros in floating mode
    long trail = 0;

    // indicates a non-zero leading digit on the decimal part.
    boolean leading = false;

    // indicates an unexpected character was encountered.
    boolean fail = false;

    while (i < length) {
      char ch = number.charAt(i);
      switch (ch) {
        case '0':
          if (decimal) {
            v++;
            if (leading) {
              trail++;
            } else {
              z++;
            }
          }
          if (nd > 0) {
            nd *= 10;
          }
          nd += (long)(ch - '0');
          break;

        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
          if (decimal) {
            v++;
            leading = true;
          }
          trail = 0;
          if (nd > 0) {
            nd *= 10;
          }
          nd += (long)(ch - '0');
          break;

        case '.':
          if (decimal) {
            fail = true;
          } else {
            decimal = true;
            ni = nd;
            nd = 0;
          }
          break;

        default:
          fail = true;
          break;
      }

      if (fail) {
        break;
      }

      // Check for imminent overflow and reduce or bail out.
      if (decimal) {
        if (nd >= LIMIT) {
          break;
        }
      } else {
        nd = integerReduce(nd);
      }

      i++;
    }

    // If no non-zero leading digit was detected, record the zeroes as trailing
    // digits and reset the leading zero count.
    if (!leading) {
      trail = z;
      z = 0;
    }

    // calculate the final values of operands tracking digit counts
    if (decimal) {
      w = v - trail;
    } else {
      // swap if we never saw a decimal point
      ni = nd;
      nd = 0;
    }

    t = nd;
    for (int j = 0; j < trail; j++) {
      t /= 10;
    }
  }

  /**
   * Periodically reduce the integer digits to prevent overflow.
   * Once the number exceeds this limit we truncate it, preserving the
   * values in the lowest N digits.
   */
  private static long integerReduce(long n) {
    long m = n > LIMIT ? n % INTEGER_REDUCER : n;
    return m;
  }

  /**
   * Used as a shortcut for comparisons in test cases.
   */
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    buf.append("neg=").append(negative).append(' ');
    buf.append("n=").append(ni);
    if (decimal) {
      buf.append('.');
      for (int i = 0; i < z; i++) {
        buf.append('0');
      }
      buf.append(nd);
    }
    buf.append(' ');
    buf.append("i=").append(ni).append(' ');
    buf.append("v=").append(v).append(' ');
    buf.append("w=").append(w).append(' ');
    buf.append("f=").append(nd).append(' ');
    buf.append("t=").append(t).append(' ');
    buf.append("z=").append(z);
    return buf.toString();
  }


}
