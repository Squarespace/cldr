package com.squarespace.cldr.units;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;


/**
 * Rational number N/D.
 */
public class Rational {

  private static final int MIN_SCALE = 12;
  private static final int MAX_SCALE = 30;

  public static final BigDecimal PI = BigDecimal.valueOf(Math.PI);
  public static final Rational ONE = new Rational(BigDecimal.ONE, BigDecimal.ONE);

  private final BigDecimal numerator;
  private final BigDecimal denominator;

  /**
   * Parse the given string as an integer or a rational number separated
   * by "/".
   */
  public Rational(String raw) {
    String[] parts = raw.split("/");
    this.numerator = parse(parts[0]);
    if (parts.length == 2) {
      this.denominator = parse(parts[1]);
    } else {
      this.denominator = BigDecimal.ONE;
    }
  }

  /**
   * Construct a rational from the given numerator and denominator.
   */
  protected Rational(BigDecimal numerator, BigDecimal denominator) {
    this.numerator = numerator;
    this.denominator = denominator;
  }

  /**
   * Multiply this rational by another and return a new rational.
   */
  public Rational multiply(Rational other) {
    return new Rational(
        numerator.multiply(other.numerator),
        denominator.multiply(other.denominator));
  }

  /**
   * Divide the numerator by the denominator and return the resulting
   * decimal number.
   */
  public BigDecimal compute(RoundingMode mode) {
    int scale = numerator.precision() + denominator.precision();
    scale = Math.max(MIN_SCALE, Math.min(MAX_SCALE, scale));
    return numerator.divide(denominator, scale, mode).stripTrailingZeros();
  }

  /**
   * Divide the numerator by the denominator, using the given scale and
   * rounding mode, and return the resulting decimal number.
   */
  public BigDecimal compute(int scale, RoundingMode mode) {
    return numerator.divide(denominator, scale, mode).stripTrailingZeros();
  }

  /**
   * Return the reciprocal of this rational, e.g calling this for
   * "2/3" would return "3/2"
   */
  public Rational reciprocal() {
    return new Rational(denominator, numerator);
  }

  /**
   * Return the numerator.
   */
  public BigDecimal numerator() {
    return numerator;
  }

  /**
   * Return the denominator.
   */
  public BigDecimal denominator() {
    return denominator;
  }

  @Override
  public int hashCode() {
    return Objects.hash(numerator, denominator);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Rational) {
      Rational other = (Rational) obj;
      return numerator.compareTo(other.numerator) == 0 && denominator.compareTo(other.denominator) == 0;
    }
    return false;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    buf.append(numerator.toPlainString());
    if (denominator.compareTo(BigDecimal.ONE) != 0) {
      buf.append(" / ").append(denominator.toPlainString());
    }
    return buf.toString();
  }

  /**
   * Convert the given string to a BigDecimal or a constant if given, e.g. "pi"
   */
  private BigDecimal parse(String raw) {
    raw = raw.trim();
    switch (raw) {
      case "pi":
        return PI;
      default:
        return new BigDecimal(raw);
    }
  }

}