package com.squarespace.cldr.units;

import java.util.List;

/**
 * Converts the unit value to the target units. Conversions are
 * performed relative to a given measurement system. For example,
 * in US a pint 
 */
public interface UnitConverter {

  /**
   * Converts a unit value to the most compact unit.
   */
  UnitValue convert(UnitValue value);

  /**
   * Converts a unit value to the target units.
   */
  UnitValue convert(UnitValue value, Unit target);
  
  /**
   * Convert to the best display unit among the available factors. By "best"
   * we mean the most compact representation.
   */
  UnitValue convert(UnitValue value, UnitFactorSet factorSet);
  
  /**
   * Break down the value into components using the available factors. Useful
   * for producing displays like "3 days 12 hours 15 minutes".
   */
  List<UnitValue> sequence(UnitValue value, UnitFactorSet factorSet);
  
  /**
   * Measurement system allowing the client to select appropriate units for
   * conversion and display.
   */
  MeasurementSystem measurementSystem();
  
  UnitFactorSet getFactorSet(UnitCategory category, Unit...units);
  
  UnitFactorSet areaFactors();

  Unit consumptionUnit();
  
  UnitFactorSet durationFactors();
  
  UnitFactorSet energyFactors();
  
  UnitFactorSet lengthFactors();
  
  UnitFactorSet massFactors();
  
  Unit speedUnit();
  
  Unit temperatureUnit();
  
  UnitFactorSet volumeFactors();

  UnitFactorSet volumeLiquidFactors();

}
