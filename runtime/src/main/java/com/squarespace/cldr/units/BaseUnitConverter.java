package com.squarespace.cldr.units;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;


public class BaseUnitConverter implements UnitConverter {

  private static final Map<UnitCategory, UnitFactorMap> FACTORS_MAP = 
      new EnumMap<UnitCategory, UnitFactorMap>(UnitCategory.class) {{

    put(UnitCategory.ACCELERATION, UnitFactors.ACCELERATION);
    put(UnitCategory.ANGLE, UnitFactors.ANGLE);
    put(UnitCategory.AREA, UnitFactors.AREA);
    put(UnitCategory.DIGITAL, UnitFactors.DIGITAL);
    put(UnitCategory.DURATION, UnitFactors.DURATION);
    put(UnitCategory.ELECTRIC, UnitFactors.ELECTRIC);
    put(UnitCategory.ENERGY, UnitFactors.ENERGY);
    put(UnitCategory.FREQUENCY, UnitFactors.FREQUENCY);
    put(UnitCategory.LENGTH, UnitFactors.LENGTH);
    put(UnitCategory.MASS, UnitFactors.MASS);
    put(UnitCategory.POWER, UnitFactors.POWER);
    put(UnitCategory.PRESSURE, UnitFactors.PRESSURE);
    put(UnitCategory.SPEED, UnitFactors.SPEED);
  }};
  
  private final MeasurementSystem measurementSystem;

  public BaseUnitConverter() {
    this(MeasurementSystem.METRIC);
  }

  public BaseUnitConverter(MeasurementSystem measurementSystem) {
    this.measurementSystem = measurementSystem;
  }

  /**
   * Returns the measurement system for this converter. Use this to select
   * the units or factors for converting and formatting values.
   */
  @Override
  public MeasurementSystem measurementSystem() {
    return measurementSystem;
  }

  /**
   * Convert the given unit value into the best unit for its category
   * relative to the current measurement system.
   */
  @Override
  public UnitValue convert(UnitValue value) {
    Unit src = value.unit();
    switch (src.category()) {
      case CONCENTR:
      case LIGHT:
        // Not available
        break;
        
      case ACCELERATION:
        return convert(value, Unit.METER_PER_SECOND_SQUARED, UnitFactors.ACCELERATION);
      case ANGLE:
        return convert(value, UnitFactorSets.ANGLE);
      case AREA:
        return convert(value, areaFactors());
      case CONSUMPTION:
        return convert(value, consumptionUnit());
      case DIGITAL:
        return convert(value, UnitFactorSets.DIGITAL_BYTES);
      case DURATION:
        return convert(value, UnitFactorSets.DURATION);
      case ELECTRIC:
        return convert(value, UnitFactorSets.ELECTRIC);
      case ENERGY:
        return convert(value, energyFactors());
      case FREQUENCY:
        return convert(value, UnitFactorSets.FREQUENCY);
      case LENGTH:
        return convert(value, lengthFactors());
      case MASS:
        return convert(value, massFactors());
      case POWER:
        return convert(value, UnitFactorSets.POWER);
      case PRESSURE:
        return convert(value, Unit.MILLIBAR);
      case SPEED:
        return convert(value, speedUnit());
      case TEMPERATURE:
        return convert(value, temperatureUnit());
      case VOLUME:
        return convert(value, volumeFactors());
    }
    
    return value;
  }

  /**
   * Convert the given unit value into the target unit relative to
   * the current measurement system.
   */
  @Override
  public UnitValue convert(UnitValue value, Unit target) {
    Unit src = value.unit();
    UnitCategory category = src.category();
    if (src == target || category != target.category()) {
      return value;
    }
    
    switch (category) {
      case CONCENTR:
      case LIGHT:
        // Not available
        break;

      case CONSUMPTION:
        return consumption(value, target);

      case TEMPERATURE:
        return temperature(value, target);
        
      case VOLUME:
        if (measurementSystem == MeasurementSystem.UK) {
          return convert(value, target, UnitFactors.VOLUME_UK);
        }
        return convert(value, target, UnitFactors.VOLUME);
        
      default:
        UnitFactorMap factors = FACTORS_MAP.get(category);
        if (factors != null) {
          return convert(value, target, factors);
        }
        break;
    }
    
    return value;
  }

  public UnitFactorSet getFactorSet(UnitCategory category, Unit...units) {
    switch (category) {
      case CONSUMPTION:
        return new UnitFactorSet(UnitFactors.CONSUMPTION, units);

      case VOLUME:
        if (measurementSystem == MeasurementSystem.UK) {
          return new UnitFactorSet(UnitFactors.VOLUME_UK, units);
        }
        return new UnitFactorSet(UnitFactors.VOLUME);
        
      default:
        UnitFactorMap map = FACTORS_MAP.get(category);
        if (map != null) {
          return new UnitFactorSet(map, units);
        }
    }
    
    return null;
  }
  
  /**
   * Convert to the best display unit among the available factors. By "best"
   * we mean the most compact representation.
   */
  public UnitValue convert(UnitValue value, UnitFactorSet factorSet) {
    value = convert(value, factorSet.base());
    BigDecimal n = value.amount();
    
    // Use absolute value to simplify comparisons with divisors
    boolean negative = false;
    if (n.signum() == -1) {
      negative = true;
      n = n.abs();
    }
    
    List<UnitValue> factors = factorSet.factors();
    int size = factors.size();
    int last = size - 1;
    for (int i = 0; i < size; i++) {
      UnitValue factor = factors.get(i);
      BigDecimal divisor = factor.amount();
      if (divisor == null) {
        continue;
      }
      
      // Use this divisor if it is <= the number or if we've run out of
      // factors to try.
      if (divisor.compareTo(n) <= 0 || i == last) {
        int scale = n.precision() + divisor.precision();
        n = n.divide(divisor, scale, RoundingMode.HALF_EVEN);
        if (negative) {
          n = n.negate();
        }
        return new UnitValue(n.stripTrailingZeros(), factor.unit());
      }
    }

    // Should only be reached if list of factors is empty.
    return value;
  }
  
  /**
   * Breaks down the unit value into a sequence of units, using the given divisors and
   * allowed units.
   */
  public List<UnitValue> sequence(UnitValue value, UnitFactorSet factorSet) {
    value = convert(value, factorSet.base());
    boolean negative = false;
    BigDecimal n = value.amount();
    
    // Use absolute value for all comparisons.
    if (n.signum() == -1) {
      negative = true;
      n = n.abs();
    }
    
    List<UnitValue> result = new ArrayList<>();
    
    List<UnitValue> factors = factorSet.factors();
    int size = factors.size();
    int last = size - 1;
    for (int i = 0; i < size; i++) {
      UnitValue factor = factors.get(i);
      BigDecimal divisor = factor.amount();
      if (divisor == null) {
        continue;
      }
      
      if (i == last && (n.compareTo(BigDecimal.ZERO) != 0 || result.isEmpty())) {
        // Include decimals on the last unit.
        int scale = n.precision() + divisor.precision();
        BigDecimal res = n.divide(divisor, scale, RoundingMode.HALF_EVEN);
        if (negative && result.isEmpty()) {
          res = res.negate();
        }
        result.add(new UnitValue(res.stripTrailingZeros(), factor.unit()));
        
      } else if (divisor.compareTo(n) <= 0) {
        BigDecimal[] res = n.divideAndRemainder(divisor);
        if (negative && result.isEmpty()) {
          res[0] = res[0].negate();
        }
        result.add(new UnitValue(res[0].stripTrailingZeros(), factor.unit()));
        n = res[1];
      }
    }

    return result;
  }
  
  protected UnitValue convert(UnitValue value, Unit target, UnitFactorMap factors) {
    UnitFactor factor = factors.get(value.unit(), target);
    Rational n = new Rational(value.amount(), BigDecimal.ONE);
    n = factor.rational().multiply(n);
    BigDecimal result = n.compute(RoundingMode.HALF_EVEN);
    return new UnitValue(result, target);
  }
  
  public UnitFactorSet areaFactors() {
    return UnitFactorSets.AREA;
  }
  
  public Unit consumptionUnit() {
    return Unit.LITER_PER_100KILOMETERS;
  }

  public UnitFactorSet durationFactors() {
    return UnitFactorSets.DURATION;
  }

  public UnitFactorSet energyFactors() {
    return UnitFactorSets.ENERGY;
  }

  public UnitFactorSet lengthFactors() {
    return UnitFactorSets.LENGTH;
  }
  
  public UnitFactorSet massFactors() {
    return UnitFactorSets.MASS;
  }
  
  public Unit speedUnit() {
    return Unit.KILOMETER_PER_HOUR;
  }
  
  public Unit temperatureUnit() {
    return Unit.CELSIUS;
  }
  
  public UnitFactorSet volumeFactors() {
    return UnitFactorSets.VOLUME;
  }
  
  public UnitFactorSet volumeLiquidFactors() {
    return UnitFactorSets.VOLUME_LIQUID;
  }

  private static final Rational ONE_HUNDRED = new Rational("100");
  private static final Rational ONE_HUNDREDTH = ONE_HUNDRED.reciprocal();
  private static final Rational USGAL_TO_LITER = UnitFactors.VOLUME.get(Unit.GALLON, Unit.LITER).rational();
  private static final Rational UKGAL_TO_LITER = UnitFactors.VOLUME.get(Unit.GALLON_IMPERIAL, Unit.LITER).rational();
  private static final Rational USGAL_PER_UKGAL = UnitFactors.VOLUME.get(Unit.GALLON, Unit.GALLON_IMPERIAL).rational();
  private static final Rational UKGAL_PER_USGAL = USGAL_PER_UKGAL.reciprocal();
  private static final Rational MILE_TO_KM = UnitFactors.LENGTH.get(Unit.MILE, Unit.KILOMETER).rational();
  private static final Rational KM_TO_MILE = MILE_TO_KM.reciprocal();
  private static final Rational USMPG_TO_L100KM = KM_TO_MILE.multiply(ONE_HUNDRED).multiply(USGAL_TO_LITER);
  private static final Rational UKMPG_TO_L100KM = KM_TO_MILE.multiply(ONE_HUNDRED).multiply(UKGAL_TO_LITER);
  
  /**
   * Convert between MPG and L/100KM.
   */
  private UnitValue consumption(UnitValue value, Unit target) {
    BigDecimal amt = value.amount();
    Unit unit = value.unit();
    if (unit == target) {
      return value;
    }
    
    boolean uk = measurementSystem == MeasurementSystem.UK;
    if (uk && unit == Unit.MILE_PER_GALLON && target == Unit.MILE_PER_GALLON_IMPERIAL) {
      return value;
    }
    
    BigDecimal result = null;

    switch (unit) {
      case MILE_PER_GALLON:
      case MILE_PER_GALLON_IMPERIAL:
        switch (target) {
          case LITER_PER_KILOMETER:
          case LITER_PER_100KILOMETERS:
            result = litersToMpg(target, unit, amt);
            break;
          
          case MILE_PER_GALLON:
            result = USGAL_PER_UKGAL.multiply(new Rational(amt, BigDecimal.ONE)).compute();
            break;
            
          case MILE_PER_GALLON_IMPERIAL:
            result = UKGAL_PER_USGAL.multiply(new Rational(amt, BigDecimal.ONE)).compute();
            break;
            
          default:
            break;
        }
        break;
        
      case LITER_PER_KILOMETER:
      case LITER_PER_100KILOMETERS:
        switch (target) {
          case MILE_PER_GALLON:
          case MILE_PER_GALLON_IMPERIAL:
            result = litersToMpg(unit, target, amt);
            break;
            
          case LITER_PER_100KILOMETERS:
            result = new Rational(amt, BigDecimal.ONE).multiply(ONE_HUNDRED).compute();
            break;
            
          case LITER_PER_KILOMETER:
            result = new Rational(amt, BigDecimal.ONE).multiply(ONE_HUNDREDTH).compute();
            break;
            
          default:
            break;
        }
        break;
        
      default:
        break;
    }
    
    if (result != null) {
      return new UnitValue(result, target);
    }
    
    return value;
  }
  
  private BigDecimal litersToMpg(Unit from, Unit to, BigDecimal amount) {
    Rational mpgFormula = UKMPG_TO_L100KM;
    if (to == Unit.MILE_PER_GALLON) {
      mpgFormula = measurementSystem == MeasurementSystem.UK ? UKMPG_TO_L100KM : USMPG_TO_L100KM;
    }
    Rational rational = mpgFormula.multiply(new Rational(BigDecimal.ONE, amount));
    if (from == Unit.LITER_PER_KILOMETER) {
      rational = rational.multiply(ONE_HUNDREDTH);
    }
    return rational.compute();
  }
  
  private static final BigDecimal THIRTY_TWO = new BigDecimal("32");
  private static final BigDecimal KELVIN_CELSIUS = new BigDecimal("273.15");
  private static final BigDecimal KELVIN_FAHRENHEIT = new BigDecimal("459.67");
  private static final Rational FIVE_NINTHS = new Rational("5 / 9");
  private static final Rational NINE_FIFTHS = FIVE_NINTHS.reciprocal();

  /**
   * Temperature conversions.
   */
  private static UnitValue temperature(UnitValue value, Unit target) {
    BigDecimal n = value.amount();
    if (target == Unit.TEMPERATURE_GENERIC) {
      return new UnitValue(n, Unit.TEMPERATURE_GENERIC);
    }

    switch (value.unit()) {
      case FAHRENHEIT:
        if (target == Unit.CELSIUS) {
            n = n.subtract(THIRTY_TWO);
            n = new Rational(n, BigDecimal.ONE).multiply(FIVE_NINTHS).compute(RoundingMode.HALF_EVEN);
        } else if (target == Unit.KELVIN) {
            n = n.add(KELVIN_FAHRENHEIT);
            n = new Rational(n, BigDecimal.ONE).multiply(FIVE_NINTHS).compute(RoundingMode.HALF_EVEN);
        }
        break;
        
      case CELSIUS:
        if (target == Unit.FAHRENHEIT) {
          Rational r = new Rational(n, BigDecimal.ONE);
          n = r.multiply(NINE_FIFTHS).compute(RoundingMode.HALF_EVEN).add(THIRTY_TWO);
        } else if (target == Unit.KELVIN) {
          n = n.add(KELVIN_CELSIUS);
        }
        break;
        
      case KELVIN:
        if (target == Unit.CELSIUS) {
          n = n.subtract(KELVIN_CELSIUS);
        } else if (target == Unit.FAHRENHEIT) {
          Rational r = new Rational(n, BigDecimal.ONE).multiply(NINE_FIFTHS);
          n = r.compute(RoundingMode.HALF_EVEN).subtract(KELVIN_FAHRENHEIT);
        }
        break;
        
      default:
        return new UnitValue(n, Unit.TEMPERATURE_GENERIC);
    }

    return new UnitValue(n.stripTrailingZeros(), target);
  }

}
