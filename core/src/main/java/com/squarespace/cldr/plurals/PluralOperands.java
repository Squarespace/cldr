package com.squarespace.cldr.plurals;

/**
 * Operands for use in evaluating localized plural rules.
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
public class PluralOperands {

  private boolean negative;

  private boolean decimal;

  // Integer portion of 'n'. This is also used for operand 'i'
  private long ni;

  // Decimal portion of 'n'. This is also used for operand 'f'
  private long nd;

  private long v;

  private long w;

  private long t;

  public PluralOperands() {
  }

  public PluralOperands(String number) {
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
   * Parses a number in one pass, updating the plural operands. Ideally the caller will
   * reuse this object to parse many numbers.
   */
  public void set(CharSequence number) {
    // reset the operands
    negative = false;
    decimal = false;
    nd = 0;
    ni = 0;
    v = 0;
    w = 0;
    t = 0;

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
    int trail = 0;

    // indicates an unexpected character was encountered.
    boolean fail = false;

    while (i < length) {
      char ch = number.charAt(i);
      switch (ch) {
        case '0':
          if (decimal) {
            trail++;
            v++;
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

      i++;
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
    while (trail > 0) {
      t /= 10;
      trail--;
    }
  }

  /**
   * Used comparisons in test cases.
   */
  public String repr() {
    StringBuilder buf = new StringBuilder();
    buf.append("neg=").append(negative).append(' ');
    buf.append("n=").append(ni);
    if (decimal) {
      buf.append('.').append(nd);
    }
    buf.append(' ');
    buf.append("i=").append(ni).append(' ');
    buf.append("v=").append(v).append(' ');
    buf.append("w=").append(w).append(' ');
    buf.append("f=").append(nd).append(' ');
    buf.append("t=").append(t);
    return buf.toString();
  }


}
