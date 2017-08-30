package com.squarespace.cldr.units;

import static com.squarespace.cldr.CLDR.Locale.en;
import static com.squarespace.cldr.CLDR.Locale.en_GB;
import static com.squarespace.cldr.CLDR.Locale.en_US;
import static org.testng.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.testng.annotations.Test;

import com.squarespace.cldr.CLDR;
import com.squarespace.cldr.numbers.NumberFormatterTestBase;


public class UnitConverterTest extends NumberFormatterTestBase {

  @Test
  public void testArea() {
    run(en_US, "1", Unit.SQUARE_CENTIMETER, "0.1550", Unit.SQUARE_INCH, 4);
    run(en_US, "1", Unit.SQUARE_INCH, "6.4516", Unit.SQUARE_CENTIMETER, 4);
    run(en_US, "1", Unit.SQUARE_MILE, "27878400", Unit.SQUARE_FOOT);
    run(en_US, "479001600", Unit.SQUARE_METER, "184.9436", Unit.SQUARE_MILE, 4);
    run(en_US, "479001600", Unit.SQUARE_METER, "5155930311.8606", Unit.SQUARE_FOOT, 4);
    run(en_US, "1", Unit.HECTARE, "11959.9005", Unit.SQUARE_YARD, 4);
  }
  
  @Test
  public void testConsumption() {
    run(en_US, "1", Unit.MILE_PER_GALLON, "235.2146", Unit.LITER_PER_100KILOMETERS, 4);
    run(en_US, "1", Unit.LITER_PER_100KILOMETERS, "235.2146", Unit.MILE_PER_GALLON, 4);
    run(en_US, "1", Unit.MILE_PER_GALLON, "2.352146", Unit.LITER_PER_KILOMETER, 6);
    run(en_US, "1", Unit.LITER_PER_KILOMETER, "2.352146", Unit.MILE_PER_GALLON, 6);

    run(en_US, "3", Unit.MILE_PER_GALLON, "78.4049", Unit.LITER_PER_100KILOMETERS, 4);
    run(en_US, "3", Unit.LITER_PER_100KILOMETERS, "78.4049", Unit.MILE_PER_GALLON, 4);

    run(en_GB, "1", Unit.MILE_PER_GALLON, "282.4809", Unit.LITER_PER_100KILOMETERS, 4);
    run(en_GB, "1", Unit.LITER_PER_100KILOMETERS, "282.4809", Unit.MILE_PER_GALLON, 4);

    run(en_GB, "3", Unit.MILE_PER_GALLON, "94.1603", Unit.LITER_PER_100KILOMETERS, 4);
    run(en_GB, "3", Unit.LITER_PER_100KILOMETERS, "94.1603", Unit.MILE_PER_GALLON, 4);
    
    run(en_US, "1", Unit.MILE_PER_GALLON, "1.20095", Unit.MILE_PER_GALLON_IMPERIAL, 5);
    run(en_US, "1", Unit.MILE_PER_GALLON_IMPERIAL, "0.83267", Unit.MILE_PER_GALLON, 5);
  }
  
  @Test
  public void testDigital() {
    run(en, "8", Unit.BIT, "1", Unit.BYTE);
    run(en, "1024", Unit.BIT, "128", Unit.BYTE);

    run(en, "1048576", Unit.BYTE, "1", Unit.MEGABYTE);
    run(en, "1", Unit.GIGABYTE, "1024", Unit.MEGABYTE);
    run(en, "1000", Unit.BIT, "0.001", Unit.MEGABIT);
  }
  
  @Test
  public void testLength() {
    run(en, "12", Unit.INCH, "1", Unit.FOOT);
    run(en, "1", Unit.INCH, "2.54", Unit.CENTIMETER);
  }
  
  @Test
  public void testSpeed() {
    run(en_US, "1", Unit.KILOMETER_PER_HOUR, "0.6214", Unit.MILE_PER_HOUR, 4);
  }
  
  @Test
  public void testTemperature() {
    run(en, "-459.67", Unit.FAHRENHEIT, "-273.15", Unit.CELSIUS);
    run(en, "-40", Unit.FAHRENHEIT, "-40", Unit.CELSIUS);
    run(en, "0", Unit.FAHRENHEIT, "-17.777777777778", Unit.CELSIUS);
    run(en, "32", Unit.FAHRENHEIT, "0", Unit.CELSIUS);
    run(en, "50", Unit.FAHRENHEIT, "10", Unit.CELSIUS);
    run(en, "98.6", Unit.FAHRENHEIT, "37", Unit.CELSIUS);
    run(en, "212", Unit.FAHRENHEIT, "100", Unit.CELSIUS);
    run(en, "500", Unit.FAHRENHEIT, "260", Unit.CELSIUS);
    
    run(en, "-273.15", Unit.CELSIUS, "-459.67", Unit.FAHRENHEIT);
    run(en, "-40", Unit.CELSIUS, "-40", Unit.FAHRENHEIT);
    run(en, "-20", Unit.CELSIUS, "-4", Unit.FAHRENHEIT);
    run(en, "0", Unit.CELSIUS, "32", Unit.FAHRENHEIT);
    run(en, "21", Unit.CELSIUS, "69.8", Unit.FAHRENHEIT);
    run(en, "37", Unit.CELSIUS, "98.6", Unit.FAHRENHEIT);
    run(en, "100", Unit.CELSIUS, "212", Unit.FAHRENHEIT);
    run(en, "500", Unit.CELSIUS, "932", Unit.FAHRENHEIT);
    run(en, "1000", Unit.CELSIUS, "1832", Unit.FAHRENHEIT);

    run(en, "0", Unit.KELVIN, "-273.15", Unit.CELSIUS);
    run(en, "150", Unit.KELVIN, "-123.15", Unit.CELSIUS);
    run(en, "210", Unit.KELVIN, "-63.15", Unit.CELSIUS);
    run(en, "500", Unit.KELVIN, "226.85", Unit.CELSIUS);
    run(en, "1000", Unit.KELVIN, "726.85", Unit.CELSIUS);
    
    run(en,  "-273.15", Unit.CELSIUS, "0", Unit.KELVIN);
    run(en, "-123.15", Unit.CELSIUS, "150", Unit.KELVIN);
    run(en, "-63.15", Unit.CELSIUS, "210", Unit.KELVIN);
    run(en, "226.85", Unit.CELSIUS, "500", Unit.KELVIN);
    run(en, "726.85", Unit.CELSIUS, "1000", Unit.KELVIN);
    
    run(en, "0", Unit.KELVIN, "-459.67", Unit.FAHRENHEIT);
    run(en, "150", Unit.KELVIN, "-189.67", Unit.FAHRENHEIT);
    run(en, "210", Unit.KELVIN, "-81.67", Unit.FAHRENHEIT);
    run(en, "500", Unit.KELVIN, "440.33", Unit.FAHRENHEIT);
    run(en, "1000", Unit.KELVIN, "1340.33", Unit.FAHRENHEIT);

    run(en,  "-459.67", Unit.FAHRENHEIT, "0", Unit.KELVIN);
    run(en, "-189.67", Unit.FAHRENHEIT, "150", Unit.KELVIN);
    run(en, "-81.67", Unit.FAHRENHEIT, "210", Unit.KELVIN);
    run(en, "440.33", Unit.FAHRENHEIT, "500", Unit.KELVIN);
    run(en, "1340.33", Unit.FAHRENHEIT, "1000", Unit.KELVIN);
  }
  
  @Test
  public void testVolume() {
    run(en, "1", Unit.GALLON, "4", Unit.QUART);
    run(en, "1", Unit.QUART, "4", Unit.CUP);
    
    run(en_US, "1", Unit.GALLON, "0.83267", Unit.GALLON_IMPERIAL, 5);
    run(en_US, "1", Unit.GALLON_IMPERIAL, "1.20095", Unit.GALLON, 5);
  }
  
  private static void run(CLDR.Locale locale, String n, Unit u, String nex, Unit uex) {
    run(locale, n, u, nex, uex, null);
  }

  private static void run(CLDR.Locale locale, String n, Unit u, String nex, Unit uex, Integer scale) {
    UnitConverter converter = CLDR.get().getUnitConverter(locale);
    UnitValue input = value(n, u);
    UnitValue expected = value(nex, uex);
    UnitValue actual = converter.convert(input, uex);
    
    if (scale != null) {
      RoundingMode mode = RoundingMode.HALF_EVEN;
      BigDecimal a = expected.amount().setScale(scale, mode);
      BigDecimal b = actual.amount().setScale(scale, mode);
      assertEquals(a.compareTo(b), 0, b.toPlainString() + " != " + a.toPlainString());
      assertEquals(actual.unit(), expected.unit());
    } else {
      assertEquals(actual, expected);
    }
  }

  private static UnitValue value(String n, Unit unit) {
    return new UnitValue(new BigDecimal(n), unit);
  }

}
