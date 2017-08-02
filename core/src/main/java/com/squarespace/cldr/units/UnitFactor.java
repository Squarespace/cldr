package com.squarespace.cldr.units;

import java.math.BigDecimal;

/**
 * Multiplication factor to convert from one unit to another.
 */
public class UnitFactor {

  private final Rational rational;
  private final Unit unit;

  public UnitFactor(String number, Unit unit) {
    this(new Rational(new BigDecimal(number), BigDecimal.ONE), unit);
  }

  public UnitFactor(Rational rational, Unit unit) {
    this.rational = rational;
    this.unit = unit;
  }

  public UnitFactor multiply(UnitFactor other) {
    return new UnitFactor(rational.multiply(other.rational), other.unit);
  }

  public Rational rational() {
    return rational;
  }

  public Unit unit() {
    return unit;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    buf.append("Factor(").append(rational).append(", ").append(unit).append(')');
    return buf.toString();
  }
}