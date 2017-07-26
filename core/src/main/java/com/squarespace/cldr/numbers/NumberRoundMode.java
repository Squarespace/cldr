package com.squarespace.cldr.numbers;

import java.math.RoundingMode;

/**
 * Indicates the rounding mode to use during formatting.
 */
public enum NumberRoundMode {

  ROUND,
  CEIL,
  FLOOR,
  TRUNCATE
  ;

  public static NumberRoundMode fromString(String v) {
    switch (v) {
      case "ceil":
        return CEIL;

      case "floor":
        return FLOOR;

      case "truncate":
        return TRUNCATE;

      case "round":
      default:
        return ROUND;
    }
  }

  /**
   * Map our public rounding mode value to RoundingMode, the one used by internals.
   */
  public RoundingMode toRoundingMode() {
    switch (this) {
      case CEIL:
        return RoundingMode.CEILING;

      case FLOOR:
        return RoundingMode.FLOOR;

      case TRUNCATE:
        return RoundingMode.DOWN;

      case ROUND:
      default:
        return RoundingMode.HALF_EVEN;
    }
  }

}
