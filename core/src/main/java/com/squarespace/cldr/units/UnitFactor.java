package com.squarespace.cldr.units;

import java.math.BigDecimal;
import java.util.Objects;

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
  public boolean equals(Object obj) {
    if (obj instanceof UnitFactor) {
      UnitFactor other = (UnitFactor) obj;
      return unit == other.unit && Objects.equals(rational, rational);
    }
    return false;
  }

  @Override
  public int hashCode() {
    throw new UnsupportedOperationException("hashCode() not supported");
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    buf.append("Factor(").append(rational).append(", ").append(unit).append(')');
    return buf.toString();
  }
}