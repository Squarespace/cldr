package com.squarespace.cldr.units;

/**
 * Factors for converting between units.
 */
public class UnitFactors {

  public static final UnitFactorMap ACCELERATION = new UnitFactorMap(UnitCategory.ACCELERATION)
      .add(Unit.G_FORCE, "9.80665", Unit.METER_PER_SECOND_SQUARED)
      ;

  public static final UnitFactorMap ANGLE = new UnitFactorMap(UnitCategory.ANGLE)
      .add(Unit.REVOLUTION, "360", Unit.DEGREE)
      .add(Unit.ARC_MINUTE, "1 / 60", Unit.DEGREE)
      .add(Unit.ARC_SECOND, "1 / 60", Unit.ARC_MINUTE)
      .add(Unit.RADIAN, "0.5 / pi", Unit.REVOLUTION)
      ;

  public static final UnitFactorMap AREA = new UnitFactorMap(UnitCategory.AREA)
      .add(Unit.SQUARE_KILOMETER, "1000000", Unit.SQUARE_METER)
      .add(Unit.HECTARE, "10000", Unit.SQUARE_METER)
      .add(Unit.SQUARE_CENTIMETER, "1 / 10000", Unit.SQUARE_METER)
      .add(Unit.SQUARE_MILE, "2589988.1103359996", Unit.SQUARE_METER)
      .add(Unit.SQUARE_MILE, "4840", Unit.SQUARE_YARD)
      .add(Unit.ACRE, "43560", Unit.SQUARE_FOOT)
      .add(Unit.SQUARE_YARD, "9", Unit.SQUARE_FOOT)
      .add(Unit.SQUARE_FOOT, "144", Unit.SQUARE_INCH)
      ;

  public static final UnitFactorMap CONSUMPTION = new UnitFactorMap(UnitCategory.CONSUMPTION)
      .add(Unit.LITER_PER_KILOMETER, "1 / 100", Unit.LITER_PER_100KILOMETERS)
      .add(Unit.MILE_PER_GALLON, "235.2145", Unit.LITER_PER_100KILOMETERS)
      .add(Unit.MILE_PER_GALLON_IMPERIAL, "282.4815", Unit.LITER_PER_100KILOMETERS)
      ;

  public static final UnitFactorMap CONSUMPTION_UK = CONSUMPTION.copy()
      .add(Unit.MILE_PER_GALLON, "282.4815", Unit.LITER_PER_100KILOMETERS)
      ;

  public static final UnitFactorMap DIGITAL = new UnitFactorMap(UnitCategory.DIGITAL)
      .add(Unit.TERABIT, "1000", Unit.GIGABIT)
      .add(Unit.GIGABIT, "1000", Unit.MEGABIT)
      .add(Unit.MEGABIT, "1000", Unit.KILOBIT)
      .add(Unit.KILOBIT, "1000", Unit.BIT)
      .add(Unit.BYTE, "8", Unit.BIT)

      .add(Unit.TERABYTE, "1024", Unit.GIGABYTE)
      .add(Unit.GIGABYTE, "1024", Unit.MEGABYTE)
      .add(Unit.MEGABYTE, "1024", Unit.KILOBYTE)
      .add(Unit.KILOBYTE, "1024", Unit.BYTE)
      ;

  public static final UnitFactorMap DIGITAL_DECIMAL = DIGITAL.copy()
      .add(Unit.TERABYTE, "1000", Unit.GIGABYTE)
      .add(Unit.GIGABYTE, "1000", Unit.MEGABYTE)
      .add(Unit.MEGABYTE, "1000", Unit.KILOBYTE)
      .add(Unit.KILOBYTE, "1000", Unit.BYTE)
      ;

  public static final UnitFactorMap DURATION = new UnitFactorMap(UnitCategory.DURATION)
      .add(Unit.CENTURY, "3155695200", Unit.SECOND)
      .add(Unit.YEAR, "31556952", Unit.SECOND)
      .add(Unit.MONTH, "30.436875", Unit.DAY)
      .add(Unit.WEEK, "7", Unit.DAY)
      .add(Unit.DAY, "86400", Unit.SECOND)
      .add(Unit.HOUR, "3600", Unit.SECOND)
      .add(Unit.MINUTE, "60", Unit.SECOND)
      .add(Unit.MILLISECOND, "1 / 1000", Unit.SECOND)
      .add(Unit.MICROSECOND, "1 / 1000", Unit.MILLISECOND)
      .add(Unit.NANOSECOND, "1 / 1000", Unit.MICROSECOND)
      ;

  public static final UnitFactorMap ELECTRIC = new UnitFactorMap(UnitCategory.ELECTRIC)
      .add(Unit.AMPERE, "1000", Unit.MILLIAMPERE)
      ;

  // TODO: ELECTRIC is a mixed category containing units for different types.
  // Functions with multiple inputs are required to convert between electric / power units.

  public static final UnitFactorMap ENERGY = new UnitFactorMap(UnitCategory.ENERGY)
      .add(Unit.KILOJOULE, "1000", Unit.JOULE)
      .add(Unit.KILOWATT_HOUR, "3600000", Unit.JOULE)
      .add(Unit.CALORIE, "4.1868", Unit.JOULE)
      .add(Unit.FOODCALORIE, "523 / 125", Unit.JOULE)
      .add(Unit.KILOCALORIE, "1000", Unit.CALORIE)
      ;

  public static final UnitFactorMap FREQUENCY = new UnitFactorMap(UnitCategory.FREQUENCY)
      .add(Unit.GIGAHERTZ, "1000", Unit.MEGAHERTZ)
      .add(Unit.MEGAHERTZ, "1000", Unit.KILOHERTZ)
      .add(Unit.KILOHERTZ, "1000", Unit.HERTZ)
      ;

  public static final UnitFactorMap LENGTH = new UnitFactorMap(UnitCategory.LENGTH)
      .add(Unit.KILOMETER, "100000", Unit.CENTIMETER)
      .add(Unit.METER, "100", Unit.CENTIMETER)
      .add(Unit.DECIMETER, "10", Unit.CENTIMETER)
      .add(Unit.MILLIMETER, "1 / 10", Unit.CENTIMETER)
      .add(Unit.MICROMETER, "1 / 10000", Unit.CENTIMETER)
      .add(Unit.NANOMETER, "1 / 10000000", Unit.CENTIMETER)
      .add(Unit.PICOMETER, "1 / 10000000000", Unit.CENTIMETER)

      .add(Unit.MILE, "5280", Unit.FOOT)
      .add(Unit.YARD, "36", Unit.INCH)
      .add(Unit.FOOT, "12", Unit.INCH)
      .add(Unit.INCH, "2.54", Unit.CENTIMETER)

      .add(Unit.LIGHT_YEAR, "9460730472580800", Unit.METER)
      .add(Unit.ASTRONOMICAL_UNIT, "149597870700", Unit.METER)
      .add(Unit.PARSEC, "648000 / pi", Unit.ASTRONOMICAL_UNIT)

      .add(Unit.FURLONG, "220", Unit.YARD)
      .add(Unit.FATHOM, "6", Unit.FOOT)
      .add(Unit.NAUTICAL_MILE, "1852", Unit.METER)
      .add(Unit.MILE_SCANDINAVIAN, "10000", Unit.METER)

      .add(Unit.POINT, "1 / 72", Unit.INCH)
      ;

  public static final UnitFactorMap MASS = new UnitFactorMap(UnitCategory.MASS)
      .add(Unit.METRIC_TON, "1000", Unit.KILOGRAM)
      .add(Unit.GRAM, "1 / 1000", Unit.KILOGRAM)
      .add(Unit.MILLIGRAM, "1 / 1000", Unit.GRAM)
      .add(Unit.MICROGRAM, "1 / 1000", Unit.MILLIGRAM)

      .add(Unit.CARAT, "200", Unit.MILLIGRAM)

      .add(Unit.POUND, "45359237 / 100000000", Unit.KILOGRAM)
      .add(Unit.TON, "2000", Unit.POUND)
      .add(Unit.STONE, "14", Unit.POUND)
      .add(Unit.OUNCE, "1 / 16", Unit.POUND)
      .add(Unit.OUNCE_TROY, "12 / 175", Unit.POUND)
      ;

  public static final UnitFactorMap POWER = new UnitFactorMap(UnitCategory.POWER)
      .add(Unit.GIGAWATT, "1000", Unit.MEGAWATT)
      .add(Unit.MEGAWATT, "1000", Unit.KILOWATT)
      .add(Unit.KILOWATT, "1000", Unit.WATT)
      .add(Unit.MILLIWATT, "1 / 1000", Unit.WATT)
      .add(Unit.HORSEPOWER, "745.69987158227", Unit.WATT)
      ;

  public static final UnitFactorMap PRESSURE = new UnitFactorMap(UnitCategory.PRESSURE)
      .add(Unit.HECTOPASCAL, "1", Unit.MILLIBAR)
      .add(Unit.HECTOPASCAL, "129032000000 / 8896443230521", Unit.POUND_PER_SQUARE_INCH)
      .add(Unit.INCH_HG, "33.86389", Unit.HECTOPASCAL)
      .add(Unit.MILLIMETER_OF_MERCURY, "1013.25 / 760", Unit.HECTOPASCAL)
      ;

  public static final UnitFactorMap SPEED = new UnitFactorMap(UnitCategory.SPEED)
      .add(Unit.KILOMETER_PER_HOUR, "3.6", Unit.METER_PER_SECOND)
      .add(Unit.MILE_PER_HOUR, "1397 / 3125", Unit.METER_PER_SECOND)
      .add(Unit.KNOT, "463 / 900", Unit.METER_PER_SECOND)
      ;

  public static final UnitFactorMap VOLUME = new UnitFactorMap(UnitCategory.VOLUME)
      .add(Unit.CUBIC_KILOMETER, "1000000000", Unit.CUBIC_METER)
      .add(Unit.CUBIC_METER, "1000000", Unit.CUBIC_CENTIMETER)
      .add(Unit.CUBIC_CENTIMETER, "0.06102374409473", Unit.CUBIC_INCH)

      .add(Unit.LITER, "1000", Unit.CUBIC_CENTIMETER)
      .add(Unit.MEGALITER, "1000000", Unit.LITER)
      .add(Unit.HECTOLITER, "100", Unit.LITER)
      .add(Unit.DECILITER, "1 / 10", Unit.LITER)
      .add(Unit.CENTILITER, "1 / 100", Unit.LITER)
      .add(Unit.MILLILITER, "1 / 1000", Unit.LITER)

      .add(Unit.CUP_METRIC, "1 / 4", Unit.LITER)

      .add(Unit.CUBIC_MILE, "5451776000", Unit.CUBIC_YARD)
      .add(Unit.CUBIC_YARD, "27", Unit.CUBIC_FOOT)
      .add(Unit.CUBIC_FOOT, "1 / 35.31466672148859", Unit.CUBIC_METER)
      .add(Unit.CUBIC_INCH, "1 / 1728", Unit.CUBIC_FOOT)

      .add(Unit.ACRE_FOOT, "43560", Unit.CUBIC_FOOT)
      .add(Unit.GALLON_IMPERIAL, "4.54609", Unit.LITER)

      // To be correct, metric pint conversions would need to be localized.
      // Pinning this at 500mL for now.
      .add(Unit.PINT_METRIC, "500", Unit.MILLILITER)

      .add(Unit.TABLESPOON, "1 / 2", Unit.FLUID_OUNCE)
      .add(Unit.TEASPOON, "1 / 6", Unit.FLUID_OUNCE)

      // These are US units. Grouped to be overridden below for UK.
      .add(Unit.BUSHEL, "2150.42", Unit.CUBIC_INCH)
      .add(Unit.GALLON, "231", Unit.CUBIC_INCH)
      .add(Unit.FLUID_OUNCE, "1 / 128", Unit.GALLON)
      .add(Unit.QUART, "1 / 4", Unit.GALLON)
      .add(Unit.PINT, "1 / 8", Unit.GALLON)
      .add(Unit.CUP, "8", Unit.FLUID_OUNCE)
      ;

  public static final UnitFactorMap VOLUME_UK = VOLUME.copy()
      .add(Unit.BUSHEL, "8", Unit.GALLON_IMPERIAL)
      .add(Unit.GALLON, "4.54609", Unit.LITER)
      .add(Unit.FLUID_OUNCE, "1 / 160", Unit.GALLON_IMPERIAL)
      .add(Unit.QUART, "1 / 4", Unit.GALLON_IMPERIAL)
      .add(Unit.PINT, "1 / 8", Unit.GALLON_IMPERIAL)
      .add(Unit.CUP, "284.1", Unit.MILLILITER)
      ;

  static {
    // Derive all 1:1 conversion factors from the existing factors.
    // We defer this since we made copies of some factor maps above to override
    // some factors based on US / UK region differences.
    ACCELERATION.complete();
    ANGLE.complete();
    AREA.complete();
    CONSUMPTION.complete();
    CONSUMPTION_UK.complete();
    DIGITAL.complete();
    DIGITAL_DECIMAL.complete();
    DURATION.complete();
    ELECTRIC.complete();
    ENERGY.complete();
    FREQUENCY.complete();
    LENGTH.complete();
    MASS.complete();
    POWER.complete();
    PRESSURE.complete();
    SPEED.complete();
    VOLUME.complete();
    VOLUME_UK.complete();
  }

}
