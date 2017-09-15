package com.squarespace.cldr.units;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Utility class for generating a map containing conversion factors for
 * all convertible units.
 */
public class UnitFactorMap {

  private final UnitCategory category;
  private final Map<Unit, Map<Unit, UnitFactor>> factors;

  // Units sorted from largest to smallest, speeding up building sequences of units.
  private final Map<Unit, Integer> unitOrder;

  /**
   * Construct a unit factor map for the given unit category. It will
   * only accept conversions between units belonging to this category.
   */
  public UnitFactorMap(UnitCategory category) {
    this.category = category;
    this.factors = new EnumMap<>(Unit.class);
    this.unitOrder = new EnumMap<>(Unit.class);
  }

  /**
   * Copy constructor.
   */
  private UnitFactorMap(UnitCategory category, Map<Unit, Map<Unit, UnitFactor>> factors) {
    this(category);
    // Deep-copy the factor mappings since we may modify the copy.
    for (Map.Entry<Unit, Map<Unit, UnitFactor>> entry : factors.entrySet()) {
      Unit key = entry.getKey();
      Map<Unit, UnitFactor> value = new EnumMap<>(entry.getValue());
      this.factors.put(key, value);
    }
  }

  public UnitCategory getUnitCategory() {
    return category;
  }

  public UnitValue convert(UnitValue value, Unit to) {
    return convert(value, RoundingMode.HALF_EVEN, to);
  }

  public UnitValue convert(UnitValue value, RoundingMode mode, Unit to) {
    UnitFactor factor = resolve(value.unit(), to);
    if (factor == null) {
      return value;
    }
    BigDecimal amt = factor.rational().compute(mode).multiply(value.amount());
    return new UnitValue(amt, to);
  }

  public void sortUnits(Unit[] units) {
    Arrays.sort(units, (a, b) -> {
      Integer ia = unitOrder.get(a);
      Integer ib = unitOrder.get(b);
      if (ia == null) {
        return 1;
      }
      if (ib == null) {
        return -1;
      }
      return Integer.compare(ia, ib);
    });
  }

  public List<UnitValue> getFactors(Unit base, Unit...units) {
    return getFactors(base, RoundingMode.HALF_EVEN, units);
  }

  /**
   * Return an array of factors to convert the array of units to the given base.
   */
  public List<UnitValue> getFactors(Unit base, RoundingMode mode, Unit...units) {
    List<UnitValue> result = new ArrayList<>();
    for (int i = 0; i < units.length; i++) {
      UnitFactor factor = resolve(units[i], base);
      if (factor != null) {
        BigDecimal n = factor.rational().compute(mode);
        result.add(new UnitValue(n, units[i]));
      }
    }
    return result;
  }

  /**
   * Set a factor to convert the unit into a given base.
   */
  protected UnitFactorMap add(Unit unit, String n, Unit base) {
    check(unit);
    check(base);
    if (unit == base) {
      throw new IllegalArgumentException("Attempt to define a unit " + unit + " in terms of itself.");
    }

    // Set the factor and its inverse if nothing already exists.
    Rational rational = new Rational(n);
    set(unit, rational, base, true);
    return this;
  }

  /**
   * Copy this map.
   */
  protected UnitFactorMap copy() {
    return new UnitFactorMap(category, factors);
  }

  /**
   * Creates direct conversion factors between all units by resolving the
   * best conversion path and setting an explicit mapping if one does
   * not already exist.
   */
  protected UnitFactorMap complete() {
    Set<Unit> units = new LinkedHashSet<>();

    // Add reverse mappings for all populated units.
    for (Unit from : factors.keySet()) {
      units.add(from);
      for (Map.Entry<Unit, UnitFactor> entry : factors.get(from).entrySet()) {
        Unit to = entry.getKey();
        units.add(to);
        UnitFactor factor = entry.getValue();
        set(to, factor.rational().reciprocal(), from, false);
      }
    }

    // For each pair of units that differ, resolve the best conversion path
    // to produce a final conversion factor.
    for (Unit from : units) {
      for (Unit to : units) {
        if (from == to) {
          continue;
        }

        // If a direct conversion exists continue.
        UnitFactor factor = get(from, to);
        if (factor != null) {
          continue;
        }

        // Resolve the conversion factor using the best path.
        factor = resolve(from, to);
        if (factor == null) {
          throw new IllegalStateException("Failed to find a conversion path between " + from + " and " + to);
        }

        // Get or create the mapping.
        Map<Unit, UnitFactor> map = factors.get(from);
        if (map == null) {
          map = new EnumMap<>(Unit.class);
          factors.put(from, map);
        }

        // If a factor exists we overwrite it if we can set a factor with a lower total precision.
        UnitFactor old = map.get(from);

        // Add this conversion factor if none exists or if this factor's precision
        // is lower than the existing conversion.
        Rational rational = factor.rational();
        if (old == null || totalPrecision(rational) < totalPrecision(old.rational())) {
          set(from, rational, to, true);
        }
      }
    }

    // Finally, sort the units from largest to smallest, where possible.
    List<Unit> unitList = new ArrayList<>(Unit.forCategory(category));
    unitList.sort((a, b) -> {
      UnitFactor fa = get(a, b);
      // Units in some categories cannot be compared so return an arbitrary order.
      if (fa == null) {
        return -1;
      }
      BigDecimal r = fa.rational().compute(RoundingMode.HALF_EVEN);
      return BigDecimal.ONE.compareTo(r);
    });

    // Update the unit ordering map with the indices from the sorted list.
    for (int i = 0; i < unitList.size(); i++) {
      unitOrder.put(unitList.get(i), i);
    }

    return this;
  }

  /**
   * Find the best conversion factor path between the FROM and TO
   * units, multiply them together and return the resulting factor.
   */
  protected UnitFactor resolve(Unit from, Unit to) {
    if (from == to) {
      return new UnitFactor(Rational.ONE, to);
    }

    List<UnitFactor> path = getPath(from, to);
    if (path.isEmpty()) {
      throw new IllegalStateException("Can't find a path to convert " + from + " units into " + to);
    }
    UnitFactor factor = path.get(0);
    int size = path.size();
    for (int i = 1; i < size; i++) {
      factor = factor.multiply(path.get(i));
    }
    return factor;
  }

  /**
   * Find the shortest conversion path between the from and to units. Currently
   * we just look for path with the lowest precision, falling back to the
   * shortest path (fewest number of conversions to get from A to B).
   */
  protected List<UnitFactor> getPath(Unit from, Unit to) {
    if (from == to) {
      return list(new UnitFactor(Rational.ONE, to));
    }

    // Find all paths between the two units.
    List<List<UnitFactor>> paths = getPaths(from, to, new HashSet<>());
    if (paths.isEmpty()) {
      return Collections.emptyList();
    }

    // Sort and return the path with the smallest precision. If two paths
    // have equal precision return the shorter path.
    paths.sort((a, b) -> {
      int cmp = Integer.compare(precisionSum(a), precisionSum(b));
      if (cmp == 0) {
        return Integer.compare(a.size(), b.size());
      }
      return cmp;
    });
    return paths.get(0);
  }

  /**
   * Reduces the list of factors by adding together the precision
   * for all numerators and denominators.
   */
  private int precisionSum(List<UnitFactor> factors) {
    return factors.stream().map(f -> totalPrecision(f.rational())).reduce(0, Math::addExact);
  }

  private int totalPrecision(Rational r) {
    return r.numerator().precision() + r.denominator().precision();
  }

  /**
   * Search into the mapping to find lists of factors that can convert
   * the 'from' unit into the 'to' unit.  For example, to convert MILE
   * to KILOMETER, we might find the path:
   *
   *   [(MILE -> INCH), (INCH -> METER), (METER -> KILOMETER)]
   */
  private List<List<UnitFactor>> getPaths(Unit from, Unit to, Set<Unit> seen) {
    List<List<UnitFactor>> result = new ArrayList<>();

    // See if there is a direct path and return it.
    UnitFactor factor = get(from, to);
    if (factor != null) {
      result.add(list(factor));
      return result;
    }

    // Prune the search space..
    seen.add(from);

    // Scan each base for the 'from' unit, trying to find a complete path
    // to the 'to' unit. Collect all of the paths.
    for (Unit base : getBases(from)) {
      if (seen.contains(base)) {
        continue;
      }

      // Create this link in the chain.
      factor = get(from, base);

      // Scan another level deeper, starting from 'base' unit.
      for (List<UnitFactor> factors : getPaths(base, to, seen)) {
        // If we found a complete path, add it..
        if (!factors.isEmpty()) {
          // Insert the current factor into the chain and add to the result set.
          factors.add(0, factor);
          result.add(factors);
        }
      }
    }
    return result;
  }

  /**
   * Find an exact conversion factor between the from and to units, or return
   * null if none exists.
   */
  public UnitFactor get(Unit from, Unit to) {
    Map<Unit, UnitFactor> map = factors.get(from);
    if (map != null) {
      UnitFactor factor = map.get(to);
      if (factor != null) {
        return factor;
      }
    }
    return null;
  }

  /**
   * Debugging..
   */
  public String dump() {
    StringBuilder buf = new StringBuilder();
    for (Unit unit : factors.keySet()) {
      buf.append(unit).append(":\n");
      Map<Unit, UnitFactor> map = factors.get(unit);
      for (Unit base : map.keySet()) {
        UnitFactor factor = map.get(base);
        buf.append("  ").append(factor).append('\n');
      }
    }
    return buf.toString();
  }

  /**
   * Debugging..
   */
  public String dump(Unit unit) {
    StringBuilder buf = new StringBuilder();
    buf.append(unit).append(":\n");
    Map<Unit, UnitFactor> map = factors.get(unit);
    for (Unit base : map.keySet()) {
      UnitFactor factor = map.get(base);
      buf.append("  ").append(factor).append("  ");
      buf.append(factor.rational().compute(RoundingMode.HALF_EVEN).toPlainString()).append('\n');
    }
    return buf.toString();
  }

  /**
   * Set a conversion factor that converts UNIT to BASE. If the replace
   * flag is true we always overwrite the mapping.
   */
  private void set(Unit unit, Rational rational, Unit base, boolean replace) {
    Map<Unit, UnitFactor> map = factors.get(unit);
    if (map == null) {
      map = new EnumMap<>(Unit.class);
      factors.put(unit, map);
    }
    if (replace || !map.containsKey(base)) {
      map.put(base,  new UnitFactor(rational, base));
    }
  }

  /**
   * Construct a list containing just this factor.
   */
  private List<UnitFactor> list(UnitFactor factor) {
    List<UnitFactor> list = new ArrayList<>();
    list.add(factor);
    return list;
  }

  /**
   * Check if the unit belongs to this mapper's category.
   */
  private void check(Unit unit) {
    if (unit.category() != category) {
      throw new IllegalArgumentException("Unit " + unit + " is not a member of " + category);
    }
  }

  /**
   * Fetch all of the existing bases for the given unit. A base is a
   * conversion factor that was directly added, or the inverse of one that
   * was directly added.
   */
  private List<Unit> getBases(Unit unit) {
    Map<Unit, UnitFactor> map = factors.get(unit);
    if (map != null) {
      return map.values().stream().map(u -> u.unit()).collect(Collectors.toList());
    }
    return Collections.emptyList();
  }

}