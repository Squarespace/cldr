package com.squarespace.cldr.units;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Categories of units.
 */
public enum UnitCategory {

  ACCELERATION("acceleration"),
  ANGLE("angle"),
  AREA("area"),
  CONCENTR("concentr", "concentration"),
  CONSUMPTION("consumption"),
  DIGITAL("digital"),
  DURATION("duration"),
  ELECTRIC("electric"),
  ENERGY("energy"),
  FREQUENCY("frequency"),
  LENGTH("length"),
  LIGHT("light"),
  MASS("mass"),
  POWER("power"),
  PRESSURE("pressure"),
  SPEED("speed"),
  TEMPERATURE("temperature"),
  VOLUME("volume")
  ;

  private static final Map<String, UnitCategory> identifierMap = new HashMap<>();

  static {
    for (UnitCategory category : UnitCategory.values()) {
      for (String identifier : category.identifiers) {
        identifierMap.put(identifier, category);
      }
    }
  }

  private final List<String> identifiers;

  private UnitCategory(String... identifiers) {
    this.identifiers = Collections.unmodifiableList(Arrays.asList(identifiers));
  }

  public String identifier() {
    return identifiers.get(0);
  }

  public List<String> identifiers() {
    return identifiers;
  }

  public static UnitCategory fromIdentifier(String identifier) {
    return identifierMap.get(identifier);
  }
}
