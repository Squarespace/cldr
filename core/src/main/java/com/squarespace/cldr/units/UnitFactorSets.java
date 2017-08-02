package com.squarespace.cldr.units;


/**
 * Sets of unit factors, useful to format compact forms or sequences.
 */
public class UnitFactorSets {

  public static final UnitFactorSet ANGLE = new UnitFactorSet(UnitFactors.ANGLE,
      Unit.DEGREE, Unit.ARC_MINUTE, Unit.ARC_SECOND
  );

  public static final UnitFactorSet AREA = new UnitFactorSet(UnitFactors.AREA,
      Unit.SQUARE_KILOMETER, Unit.SQUARE_METER, Unit.SQUARE_CENTIMETER
  );

  public static final UnitFactorSet AREA_ENGLISH = new UnitFactorSet(UnitFactors.AREA,
      Unit.SQUARE_MILE, Unit.SQUARE_FOOT, Unit.SQUARE_INCH
  );

  public static final UnitFactorSet DIGITAL_BITS = new UnitFactorSet(UnitFactors.DIGITAL,
      Unit.TERABIT, Unit.GIGABIT, Unit.MEGABIT, Unit.KILOBIT, Unit.BIT
  );

  public static final UnitFactorSet DIGITAL_BYTES = new UnitFactorSet(UnitFactors.DIGITAL,
      Unit.TERABYTE, Unit.GIGABYTE, Unit.MEGABYTE, Unit.KILOBYTE, Unit.BYTE
  );

  public static final UnitFactorSet DURATION = new UnitFactorSet(UnitFactors.DURATION,
      Unit.CENTURY, Unit.YEAR, Unit.MONTH, Unit.DAY, Unit.HOUR, Unit.MINUTE, Unit.SECOND
  );

  public static final UnitFactorSet DURATION_LARGE = new UnitFactorSet(UnitFactors.DURATION,
      Unit.CENTURY, Unit.YEAR, Unit.MONTH, Unit.DAY
  );

  public static final UnitFactorSet DURATION_SMALL = new UnitFactorSet(UnitFactors.DURATION,
      Unit.DAY, Unit.HOUR, Unit.MINUTE, Unit.SECOND
  );

  public static final UnitFactorSet ENERGY = new UnitFactorSet(UnitFactors.ENERGY,
      Unit.KILOJOULE, Unit.JOULE
  );

  public static final UnitFactorSet FREQUENCY = new UnitFactorSet(UnitFactors.FREQUENCY,
      Unit.GIGAHERTZ, Unit.MEGAHERTZ, Unit.KILOHERTZ, Unit.HERTZ
  );

  public static final UnitFactorSet LENGTH = new UnitFactorSet(UnitFactors.LENGTH,
      Unit.KILOMETER, Unit.METER, Unit.CENTIMETER
  );

  public static final UnitFactorSet LENGTH_ENGLISH = new UnitFactorSet(UnitFactors.LENGTH,
      Unit.MILE, Unit.YARD, Unit.FOOT, Unit.INCH
  );

  public static final UnitFactorSet MASS = new UnitFactorSet(UnitFactors.MASS,
      Unit.METRIC_TON, Unit.KILOGRAM, Unit.GRAM, Unit.MILLIGRAM
  );

  public static final UnitFactorSet MASS_AVOIRDUPOIS = new UnitFactorSet(UnitFactors.MASS,
      Unit.TON, Unit.POUND, Unit.OUNCE
  );

  public static final UnitFactorSet POWER = new UnitFactorSet(UnitFactors.POWER,
      Unit.GIGAWATT, Unit.MEGAWATT, Unit.KILOWATT, Unit.WATT, Unit.MILLIWATT
  );

  public static final UnitFactorSet VOLUME = new UnitFactorSet(UnitFactors.VOLUME,
      Unit.CUBIC_KILOMETER, Unit.CUBIC_METER, Unit.CUBIC_CENTIMETER
  );

  public static final UnitFactorSet VOLUME_ENGLISH = new UnitFactorSet(UnitFactors.VOLUME,
      Unit.CUBIC_MILE, Unit.CUBIC_YARD, Unit.CUBIC_FOOT, Unit.CUBIC_INCH
  );

  public static final UnitFactorSet VOLUME_ENGLISH_UK = new UnitFactorSet(UnitFactors.VOLUME_UK,
      Unit.CUBIC_MILE, Unit.CUBIC_YARD, Unit.CUBIC_FOOT, Unit.CUBIC_INCH
  );

  public static final UnitFactorSet VOLUME_LIQUID = new UnitFactorSet(UnitFactors.VOLUME,
      Unit.MEGALITER, Unit.HECTOLITER, Unit.LITER, Unit.DECILITER, Unit.CENTILITER, Unit.MILLILITER
  );

  public static final UnitFactorSet VOLUME_LIQUID_ENGLISH = new UnitFactorSet(UnitFactors.VOLUME,
      Unit.GALLON, Unit.QUART, Unit.PINT, Unit.CUP, Unit.FLUID_OUNCE
  );

  public static final UnitFactorSet VOLUME_LIQUID_ENGLISH_UK = new UnitFactorSet(UnitFactors.VOLUME_UK,
      Unit.GALLON, Unit.QUART, Unit.PINT, Unit.CUP, Unit.FLUID_OUNCE
  );

}
