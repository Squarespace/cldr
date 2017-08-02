package com.squarespace.cldr.units;

import static com.squarespace.cldr.units.UnitCategory.ACCELERATION;
import static com.squarespace.cldr.units.UnitCategory.ANGLE;
import static com.squarespace.cldr.units.UnitCategory.AREA;
import static com.squarespace.cldr.units.UnitCategory.CONCENTR;
import static com.squarespace.cldr.units.UnitCategory.CONSUMPTION;
import static com.squarespace.cldr.units.UnitCategory.DIGITAL;
import static com.squarespace.cldr.units.UnitCategory.DURATION;
import static com.squarespace.cldr.units.UnitCategory.ELECTRIC;
import static com.squarespace.cldr.units.UnitCategory.ENERGY;
import static com.squarespace.cldr.units.UnitCategory.FREQUENCY;
import static com.squarespace.cldr.units.UnitCategory.LENGTH;
import static com.squarespace.cldr.units.UnitCategory.LIGHT;
import static com.squarespace.cldr.units.UnitCategory.MASS;
import static com.squarespace.cldr.units.UnitCategory.POWER;
import static com.squarespace.cldr.units.UnitCategory.PRESSURE;
import static com.squarespace.cldr.units.UnitCategory.SPEED;
import static com.squarespace.cldr.units.UnitCategory.TEMPERATURE;
import static com.squarespace.cldr.units.UnitCategory.VOLUME;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Units for conversions and formatting.
 */
public enum Unit {

  G_FORCE(ACCELERATION, "g-force", "g-forces", "g"),
  METER_PER_SECOND_SQUARED(ACCELERATION, "meter-per-second-squared", "meters-per-second-squared"),

  ARC_MINUTE(ANGLE, "arc-minute", "arc-minutes", "arcminute", "arcminutes"),
  ARC_SECOND(ANGLE, "arc-second", "arc-seconds", "arcsecond", "arcseconds"),
  DEGREE(ANGLE, "degree", "degrees"),
  RADIAN(ANGLE, "radian", "radians"),
  REVOLUTION(ANGLE, "revolution", "revolutions"),

  ACRE(AREA, "acre", "acres"),
  HECTARE(AREA, "hectare", "hectares"),
  SQUARE_CENTIMETER(AREA, "square-centimeter", "square-centimeters"),
  SQUARE_FOOT(AREA, "square-foot", "square-feet"),
  SQUARE_INCH(AREA, "square-inch", "square-inches"),
  SQUARE_KILOMETER(AREA, "square-kilometer", "square-kilometers"),
  SQUARE_METER(AREA, "square-meter", "square-meters"),
  SQUARE_MILE(AREA, "square-mile", "square-miles"),
  SQUARE_YARD(AREA, "square-yard", "square-yards"),

  KARAT(CONCENTR, "karat", "karats"),
  MILLIGRAM_PER_DECILITER(CONCENTR, "milligram-per-deciliter", "milligrams-per-deciliter"),
  MILLIMOLE_PER_LITER(CONCENTR, "millimole-per-liter", "millimoles-per-liter"),
  PART_PER_MILLION(CONCENTR, "part-per-million", "parts-per-million"),

  LITER_PER_100KILOMETERS(CONSUMPTION, "liter-per-100kilometers", "liters-per-100kilometers"),
  LITER_PER_KILOMETER(CONSUMPTION, "liter-per-kilometer", "liters-per-kilometer"),
  MILE_PER_GALLON(CONSUMPTION, "mile-per-gallon", "miles-per-gallon"),
  MILE_PER_GALLON_IMPERIAL(CONSUMPTION, "mile-per-gallon-imperial", "miles-per-gallon-imperial"),

  BIT(DIGITAL, "bit", "bits"),
  BYTE(DIGITAL, "byte", "bytes"),
  GIGABIT(DIGITAL, "gigabit", "gigabits"),
  GIGABYTE(DIGITAL, "gigabyte", "gigabytes"),
  KILOBIT(DIGITAL, "kilobit", "kilobits"),
  KILOBYTE(DIGITAL, "kilobyte", "kilobytes"),
  MEGABIT(DIGITAL, "megabit", "megabits"),
  MEGABYTE(DIGITAL, "megabyte", "megabytes"),
  TERABIT(DIGITAL, "terabit", "terabits"),
  TERABYTE(DIGITAL, "terabyte", "terabytes"),

  CENTURY(DURATION, "century", "centuries"),
  DAY(DURATION, "day", "days"),
  HOUR(DURATION, "hour", "hours"),
  MICROSECOND(DURATION, "microsecond", "microseconds"),
  MILLISECOND(DURATION, "millisecond", "milliseconds"),
  MINUTE(DURATION, "minute", "minutes"),
  MONTH(DURATION, "month", "months"),
  NANOSECOND(DURATION, "nanosecond", "nanoseconds"),
  SECOND(DURATION, "second", "seconds"),
  WEEK(DURATION, "week", "weeks"),
  YEAR(DURATION, "year", "years"),

  AMPERE(ELECTRIC, "ampere", "amperes"),
  MILLIAMPERE(ELECTRIC, "milliampere", "milliamperes"),
  OHM(ELECTRIC, "ohm", "ohms"),
  VOLT(ELECTRIC, "volt", "volts"),

  CALORIE(ENERGY, "calorie", "calories"),
  FOODCALORIE(ENERGY, "foodcalorie", "foodcalories"),
  JOULE(ENERGY, "joule", "joules"),
  KILOCALORIE(ENERGY, "kilocalorie", "kilocalories"),
  KILOJOULE(ENERGY, "kilojoule", "kilojoules"),
  KILOWATT_HOUR(ENERGY, "kilowatt-hour", "kilowatt-hours"),

  GIGAHERTZ(FREQUENCY, "gigahertz"),
  HERTZ(FREQUENCY, "hertz"),
  KILOHERTZ(FREQUENCY, "kilohertz"),
  MEGAHERTZ(FREQUENCY, "megahertz"),

  ASTRONOMICAL_UNIT(LENGTH, "astronomical-unit", "astronomical-units"),
  CENTIMETER(LENGTH, "centimeter", "centimeters"),
  DECIMETER(LENGTH, "decimeter", "decimeters"),
  FATHOM(LENGTH, "fathom", "fathoms"),
  FOOT(LENGTH, "foot", "feet"),
  FURLONG(LENGTH, "furlong", "furlongs"),
  INCH(LENGTH, "inch", "inches"),
  KILOMETER(LENGTH, "kilometer", "kilometers"),
  LIGHT_YEAR(LENGTH, "light-year", "light-years"),
  METER(LENGTH, "meter", "meters"),
  MICROMETER(LENGTH, "micrometer", "micrometers"),
  MILE(LENGTH, "mile", "miles"),
  MILE_SCANDINAVIAN(LENGTH, "mile-scandinavian", "miles-scandinavian"),
  MILLIMETER(LENGTH, "millimeter", "millimeters"),
  NANOMETER(LENGTH, "nanometer", "nanometers"),
  NAUTICAL_MILE(LENGTH, "nautical-mile", "nautical-miles"),
  PARSEC(LENGTH, "parsec", "parsecs"),
  PICOMETER(LENGTH, "picometer", "picometers"),
  POINT(LENGTH, "point", "points"),
  YARD(LENGTH, "yard", "yards"),

  LUX(LIGHT, "lux"),

  CARAT(MASS, "carat", "carats"),
  GRAM(MASS, "gram", "grams"),
  KILOGRAM(MASS, "kilogram", "kilograms"),
  METRIC_TON(MASS, "metric-ton", "metric-tons"),
  MICROGRAM(MASS, "microgram", "micrograms"),
  MILLIGRAM(MASS, "milligram", "milligrams"),
  OUNCE(MASS, "ounce", "ounces"),
  OUNCE_TROY(MASS, "ounce-troy", "troy-ounce", "troy-ounces"),
  POUND(MASS, "pound", "pounds"),
  STONE(MASS, "stone"),
  TON(MASS, "ton", "tons"),

  GIGAWATT(POWER, "gigawatt", "gigawatts"),
  HORSEPOWER(POWER, "horsepower"),
  KILOWATT(POWER, "kilowatt", "kilowatts"),
  MEGAWATT(POWER, "megawatt", "megawatts"),
  MILLIWATT(POWER, "milliwatt", "milliwatts"),
  WATT(POWER, "watt", "watts"),

  HECTOPASCAL(PRESSURE, "hectopascal", "hectopascals"),
  INCH_HG(PRESSURE, "inch-hg", "inches-hg"),
  MILLIBAR(PRESSURE, "millibar", "millibars"),
  MILLIMETER_OF_MERCURY(PRESSURE, "millimeter-of-mercury", "millimeters-of-mercury"),
  POUND_PER_SQUARE_INCH(PRESSURE, "pound-per-square-inch", "pounds-per-square-inch"),

  KILOMETER_PER_HOUR(SPEED, "kilometer-per-hour", "kilometers-per-hour"),
  KNOT(SPEED, "knot", "knots"),
  METER_PER_SECOND(SPEED, "meter-per-second", "meters-per-second"),
  MILE_PER_HOUR(SPEED, "mile-per-hour", "miles-per-hour"),

  CELSIUS(TEMPERATURE, "celsius", "C"),
  FAHRENHEIT(TEMPERATURE, "fahrenheit", "F"),
  TEMPERATURE_GENERIC(TEMPERATURE, "degrees-generic", "generic-degrees"),
  KELVIN(TEMPERATURE, "kelvin", "kelvins", "K"),

  ACRE_FOOT(VOLUME, "acre-foot", "acre-feet"),
  BUSHEL(VOLUME, "bushel", "bushels"),
  CENTILITER(VOLUME, "centiliter", "centiliters"),
  CUBIC_CENTIMETER(VOLUME, "cubic-centimeter", "cubic-centimeters"),
  CUBIC_FOOT(VOLUME, "cubic-foot", "cubic-feett"),
  CUBIC_INCH(VOLUME, "cubic-inch", "cubic-inches"),
  CUBIC_KILOMETER(VOLUME, "cubic-kilometer", "cubic-kilometers"),
  CUBIC_METER(VOLUME, "cubic-meter", "cubic-meters"),
  CUBIC_MILE(VOLUME, "cubic-mile", "cubic-miles"),
  CUBIC_YARD(VOLUME, "cubic-yard", "cubic-yards"),
  CUP(VOLUME, "cup", "cups"),
  CUP_METRIC(VOLUME, "cup-metric", "cups-metric"),
  DECILITER(VOLUME, "deciliter", "deciliters"),
  FLUID_OUNCE(VOLUME, "fluid-ounce", "fluid-ounces"),
  GALLON(VOLUME, "gallon", "gallons"),
  GALLON_IMPERIAL(VOLUME, "gallon-imperial", "gallons-imperial"),
  HECTOLITER(VOLUME, "hectoliter", "hectoliters"),
  LITER(VOLUME, "liter", "liters"),
  MEGALITER(VOLUME, "megaliter", "megaliters"),
  MILLILITER(VOLUME, "milliliter", "milliliters"),
  PINT(VOLUME, "pint", "pints"),
  // NOTE: no clear conversion factor could be found for metric pints
  PINT_METRIC(VOLUME, "pint-metric", "pints-metric"),
  QUART(VOLUME, "quart", "quarts"),
  TABLESPOON(VOLUME, "tablespoon", "tablespoons"),
  TEASPOON(VOLUME, "teaspoon", "teaspoons"),

  ;

  private static final Map<String, Unit> IDENTIFIER_MAP = new HashMap<>();
  private static final Map<UnitCategory, List<Unit>> CATEGORY_MAP = new EnumMap<>(UnitCategory.class);

  static {
    Map<UnitCategory, List<Unit>> categoryMap = new EnumMap<>(UnitCategory.class);
    for (Unit unit : Unit.values()) {
      UnitCategory category = unit.category;
      for (String id : unit.identifiers) {
        IDENTIFIER_MAP.put(id, unit);

        // Add prefixes using the unit category.
        for (String catId : category.identifiers()) {
          id = catId + "-" + id;
          IDENTIFIER_MAP.put(id, unit);
        }
      }

      List<Unit> units = categoryMap.get(category);
      if (units == null) {
        units = new ArrayList<>();
        categoryMap.put(category, units);
      }
      units.add(unit);
    }

    for (Map.Entry<UnitCategory, List<Unit>> entry : categoryMap.entrySet()) {
      CATEGORY_MAP.put(entry.getKey(), Collections.unmodifiableList(entry.getValue()));
    }
  }

  private final UnitCategory category;
  private final String[] identifiers;

  /**
   * Constructs a unit with a given category and one or more identifiers.
   */
  private Unit(UnitCategory category, String...identifiers) {
    this.category = category;
    this.identifiers = identifiers;
  }

  /**
   * Returns the lowercase identifier for this unit.
   */
  public String identifier() {
    return identifiers[0];
  }

  /**
   * Returns the category this unit belongs to (area, speed, etc.).
   */
  public UnitCategory category() {
    return category;
  }

  /**
   * Return the unit corresponding to the identifier or null if nothing matches.
   */
  public static Unit fromIdentifier(String identifier) {
    return IDENTIFIER_MAP.get(identifier.toLowerCase());
  }

  /**
   * Return an immutable list of all units for a given category.
   */
  public static List<Unit> forCategory(UnitCategory category) {
    return CATEGORY_MAP.get(category);
  }

  /**
   * If the numerator / denominator corresponds to an existing compound
   * unit, return it; otherwise return null.
   */
  public Unit getCompoundUnit(Unit denominator) {
    switch (this) {
      case KILOMETER:
        if (denominator == HOUR) {
          return Unit.KILOMETER_PER_HOUR;
        }
        break;

      case LITER:
        if (denominator == Unit.KILOMETER) {
          return LITER_PER_KILOMETER;
        }
        break;

      case METER:
        if (denominator == SECOND) {
          return METER_PER_SECOND;
        }
        break;

      case MILE:
        switch (denominator) {
          case GALLON:
            return MILE_PER_GALLON;
          case GALLON_IMPERIAL:
            return MILE_PER_GALLON_IMPERIAL;
          case HOUR:
            return MILE_PER_HOUR;
          default:
            break;
        }

      case MILLIGRAM:
        if (denominator == DECILITER) {
          return MILLIGRAM_PER_DECILITER;
        }
        break;

      case POUND:
        if (denominator == SQUARE_INCH) {
          return POUND_PER_SQUARE_INCH;
        }
        break;

      default:
        break;
    }
    return null;
  }
}
