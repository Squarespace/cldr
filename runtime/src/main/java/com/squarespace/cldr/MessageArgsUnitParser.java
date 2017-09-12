package com.squarespace.cldr;

import static com.squarespace.cldr.MessageArgsDecimalParser.isEmpty;

import com.squarespace.cldr.units.Unit;
import com.squarespace.cldr.units.UnitCategory;
import com.squarespace.cldr.units.UnitConverter;
import com.squarespace.cldr.units.UnitFactorSet;
import com.squarespace.cldr.units.UnitFactorSets;
import com.squarespace.cldr.units.UnitFormat;
import com.squarespace.cldr.units.UnitFormatOptions;


class MessageArgsUnitParser implements MessageArgsParser {

  private final UnitFormatOptions opts = new UnitFormatOptions();
  
  protected Unit inputUnit;
  protected Unit exactUnit;
  protected Unit[] exactUnits;
  protected String compact;
  protected Unit[] sequence;
  protected UnitFactorSet factorSet;
  
  public UnitFormatOptions options() {
    return opts;
  }
  
  public void reset() {
    opts.reset();
    inputUnit = null;
    exactUnit = null;
    exactUnits = null;
    compact = null;
    sequence = null;
    factorSet = null;
  }
  
  @Override
  public void set(String key, String value) {
    if (!isEmpty(value)) {
      switch (key) {
        case "compact":
          compact = value;
          break;
          
        case "in":
          inputUnit = Unit.fromIdentifier(value);
          break;
          
        case "out":
          if (value.indexOf(",") == -1) {
            exactUnit = Unit.fromIdentifier(value);
          } else {
            exactUnits = parseUnitList(value);
          }
          break;
        
        case "sequence":
          if (sequence == null && compact == null) {
            sequence = parseUnitList(value);
          }
          break;
          
        case "format":
          switch (value) {
            case "long":
              opts.setFormat(UnitFormat.LONG);
              break;
            case "narrow":
              opts.setFormat(UnitFormat.NARROW);
              break;
            case "short":
              opts.setFormat(UnitFormat.SHORT);
              break;
            default:
              break;
          }
          
        default:
          MessageArgsDecimalParser.setNumberOption(key, value, opts);
          break;
      }
      
    } else {
      MessageArgsDecimalParser.setNumberOption(key, value, opts);
    }
  }
  
  /**
   * Some categories only have a single possible unit depending the locale.
   */
  protected static Unit selectExactUnit(String compact, UnitConverter converter) {
    if (compact != null) {
      switch (compact) {
        case "consumption":
          return converter.consumptionUnit();
        case "light":
          return Unit.LUX;
        case "speed":
          return converter.speedUnit();
        case "temp":
        case "temperature":
          return converter.temperatureUnit();
        default:
          break;
      }
    }
    return null;
  }

  /**
   * Select a factor set based on a name, used to format compact units. For example,
   * if compact="bytes" we return a factor set DIGITAL_BYTES. This set is then used
   * to produce the most compact form for a given value, e.g. "1.2MB", "37TB", etc.
   */
  protected static UnitFactorSet selectFactorSet(String compact, UnitConverter converter) {
    if (compact != null) {
      switch (compact) {
        case "angle":
        case "angles":
          return UnitFactorSets.ANGLE;
        case "area":
          return converter.areaFactors();
        case "bit":
        case "bits":
          return UnitFactorSets.DIGITAL_BITS;
        case "byte":
        case "bytes":
          return UnitFactorSets.DIGITAL_BYTES;
        case "duration":
          return UnitFactorSets.DURATION;
        case "duration-large":
          return UnitFactorSets.DURATION_LARGE;
        case "duration-small":
          return UnitFactorSets.DURATION_SMALL;
        case "energy":
          return UnitFactorSets.ENERGY;
        case "frequency":
          return UnitFactorSets.FREQUENCY;
        case "length":
          return converter.lengthFactors();
        case "mass":
          return converter.massFactors();
        case "power":
          return UnitFactorSets.POWER;
        case "volume":
          return converter.volumeFactors();
        case "liquid":
          return converter.volumeLiquidFactors();
        default:
          break;
      }
    }
    return null;
  }

  /**
   * Based on the unit we're converting to, guess the input unit. For example, if we're
   * converting to MEGABIT and no input unit was specified, assume BIT.
   */
  protected static Unit inputFromExactUnit(Unit exact, UnitConverter converter) {
    switch (exact) {
      case TERABIT:
      case GIGABIT:
      case MEGABIT:
      case KILOBIT:
      case BIT:
        return Unit.BIT;

      case TERABYTE:
      case GIGABYTE:
      case MEGABYTE:
      case KILOBYTE:
      case BYTE:
        return Unit.BYTE;

      default:
        break;
    }

    UnitCategory category = exact.category();
    switch (category) {
      case CONSUMPTION:
        return converter.consumptionUnit();
      case FREQUENCY:
        return Unit.HERTZ;
      case LIGHT:
        return Unit.LUX;
      case PRESSURE:
        return Unit.MILLIBAR;
      case SPEED:
        return converter.speedUnit();
      case TEMPERATURE:
        return converter.temperatureUnit();

      default:
        UnitFactorSet factorSet = getDefaultFactorSet(category, converter);
        if (factorSet != null) {
          return factorSet.base();
        }
        break;
    }
    return null;
  }
  
  /**
   * Default conversion factors for each category. Some of these differ based on the locale
   * of the converter.
   */
  protected static UnitFactorSet getDefaultFactorSet(UnitCategory category, UnitConverter converter) {
    switch (category) {
      case ANGLE:
        return UnitFactorSets.ANGLE;
      case AREA:
        return converter.areaFactors();
      case DURATION:
        return UnitFactorSets.DURATION;
      case ENERGY:
        return converter.energyFactors();
      case FREQUENCY:
        return UnitFactorSets.FREQUENCY;
      case LENGTH:
        return converter.lengthFactors();
      case MASS:
        return converter.massFactors();
      case POWER:
        return UnitFactorSets.POWER;
      case VOLUME:
        return converter.volumeFactors();
      default:
        break;
    }
    return null;
  }


  private static Unit[] parseUnitList(String value) {
    String[] parts = value.split(",");
    UnitCategory category = null;
    Unit[] units = new Unit[parts.length];
    for (int i = 0; i < parts.length; i++) {
      Unit unit = Unit.fromIdentifier(parts[i]);

      // Bail out on invalid or incompatible units
      if (unit == null) {
        return null;
      }

      if (category == null) {
        category = unit.category();
      } else if (unit.category() != category) {
        return null;
      }
      units[i] = unit;
    }
    return units;
  }

}
