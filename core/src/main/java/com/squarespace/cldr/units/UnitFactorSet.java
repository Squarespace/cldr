package com.squarespace.cldr.units;

import java.util.List;


/**
 * Helper to create an ordered list of conversion factors, simplifying
 * customization of unit sequence formatting.
 */
public class UnitFactorSet {

  private final Unit base;
  private final List<UnitValue> factors;

  /**
   * Create a factor set with an explicit base unit, providing some control
   * over the resolution of the factors.
   */
  public UnitFactorSet(Unit base, UnitFactorMap factorMap, Unit...units) {
    this(base, true, factorMap, units);
  }

  /**
   * Create a factor set and automatically set the base unit to the smallest
   * in the set.
   */
  public UnitFactorSet(UnitFactorMap factorMap, Unit...units) {
    this(null, false, factorMap, units);
  }

  private UnitFactorSet(Unit unitBase, boolean forceBase, UnitFactorMap factorMap, Unit...units) {
    int len = units.length;
    factorMap.sortUnits(units);
    this.base = forceBase ? unitBase : units[len - 1];
    this.factors = factorMap.getFactors(base, units);
  }

  /**
   * Return the base unit (smallest) for the set.
   */
  public Unit base() {
    return base;
  }

  /**
   * Return the conversion factors for the set.
   */
  public List<UnitValue> factors() {
    return factors;
  }

}
