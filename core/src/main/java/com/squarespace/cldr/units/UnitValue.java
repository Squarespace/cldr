package com.squarespace.cldr.units;

import java.math.BigDecimal;
import java.util.Objects;


/**
 * Combines an amount with a unit.
 */
public class UnitValue {

  private final BigDecimal amount;
  private final Unit unit;

  public UnitValue(String amount, Unit unit) {
    this(new BigDecimal(amount), unit);
  }

  public UnitValue(BigDecimal amount, Unit unit) {
    this.amount = amount;
    this.unit = unit;
  }

  public BigDecimal amount() {
    return amount;
  }

  public Unit unit() {
    return unit;
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, unit);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof UnitValue) {
      UnitValue other = (UnitValue) obj;
      return unit.equals(other.unit) && amount.compareTo(other.amount) == 0;
    }
    return false;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    buf.append("UnitValue(").append(amount.toPlainString()).append(", ").append(unit).append(')');
    return buf.toString();
  }

}