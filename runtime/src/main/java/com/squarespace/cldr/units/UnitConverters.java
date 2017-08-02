package com.squarespace.cldr.units;

import static com.squarespace.cldr.units.MeasurementSystem.METRIC;
import static com.squarespace.cldr.units.MeasurementSystem.METRIC_WITH_US_TEMPERATURE;
import static com.squarespace.cldr.units.MeasurementSystem.UK;
import static com.squarespace.cldr.units.MeasurementSystem.US;
import static com.squarespace.cldr.units.MeasurementSystem.US_WITH_METRIC_TEMPERATURE;

import java.util.EnumMap;
import java.util.Map;

import com.squarespace.cldr.CLDR;


public class UnitConverters {

  /**
   * Retrieves the correct measurement system for a given locale.
   */
  public static UnitConverter get(CLDR.Locale locale) {
    MeasurementSystem system = MeasurementSystem.fromLocale(locale);
    return SYSTEMS.getOrDefault(system, METRIC_SYSTEM);
  }

  private static final UnitConverter METRIC_SYSTEM = new SystemMetric();

  private static final Map<MeasurementSystem, UnitConverter> SYSTEMS 
    = new EnumMap<MeasurementSystem, UnitConverter>(MeasurementSystem.class) {{
    
      put(METRIC, METRIC_SYSTEM);
      put(US, new SystemUS());
      put(US_WITH_METRIC_TEMPERATURE, new SystemUSWithMetricTemperature());
      put(METRIC_WITH_US_TEMPERATURE, new SystemMetricWithUSTemperature());
      put(UK, new SystemUK());
  }};
  
  private static class SystemMetric extends BaseUnitConverter {
  }
  
  private static class SystemUS extends BaseUnitConverter {

    public SystemUS() {
      this(MeasurementSystem.US);
    }
    
    public SystemUS(MeasurementSystem system) {
      super(system);
    }

    @Override
    public Unit consumptionUnit() {
      return Unit.MILE_PER_GALLON;
    }

    @Override
    public UnitFactorSet lengthFactors() {
      return UnitFactorSets.LENGTH_ENGLISH;
    }

    @Override
    public UnitFactorSet massFactors() {
      return UnitFactorSets.MASS_AVOIRDUPOIS;
    }

    @Override
    public Unit speedUnit() {
      return Unit.MILE_PER_HOUR;
    }

    @Override
    public Unit temperatureUnit() {
      return Unit.FAHRENHEIT;
    }
    
    @Override
    public UnitFactorSet volumeFactors() {
      return UnitFactorSets.VOLUME_ENGLISH;
    }
   
    @Override
    public UnitFactorSet volumeLiquidFactors() {
      return UnitFactorSets.VOLUME_LIQUID_ENGLISH;
    }
  }
  
  private static class SystemUSWithMetricTemperature extends SystemUS {
    
    public SystemUSWithMetricTemperature() {
      super(MeasurementSystem.US_WITH_METRIC_TEMPERATURE);
    }
    
    @Override
    public Unit temperatureUnit() {
      return Unit.CELSIUS;
    }

  }

  private static class SystemMetricWithUSTemperature extends BaseUnitConverter {
    
    public SystemMetricWithUSTemperature() {
      super(MeasurementSystem.METRIC_WITH_US_TEMPERATURE);
    }
    
    @Override
    public Unit temperatureUnit() {
      return Unit.FAHRENHEIT;
    }
  }
  
  private static class SystemUK extends BaseUnitConverter {
    
    public SystemUK() {
      super(MeasurementSystem.UK);
    }

    @Override
    public UnitFactorSet areaFactors() {
      return UnitFactorSets.AREA_ENGLISH;
    }
    
    @Override
    public Unit consumptionUnit() {
      return Unit.MILE_PER_GALLON_IMPERIAL;
    }

    @Override
    public UnitFactorSet lengthFactors() {
      return UnitFactorSets.LENGTH_ENGLISH;
    }
    
    @Override
    public UnitFactorSet massFactors() {
      return UnitFactorSets.MASS_AVOIRDUPOIS;
    }
    
    @Override
    public Unit speedUnit() {
      return Unit.MILE_PER_HOUR;
    }
 
    @Override
    public Unit temperatureUnit() {
      return Unit.FAHRENHEIT;
    }
    
    @Override
    public UnitFactorSet volumeFactors() {
      return UnitFactorSets.VOLUME_ENGLISH_UK;
    }
    
    @Override
    public UnitFactorSet volumeLiquidFactors() {
      return UnitFactorSets.VOLUME_LIQUID_ENGLISH_UK;
    }
  }
  
}
