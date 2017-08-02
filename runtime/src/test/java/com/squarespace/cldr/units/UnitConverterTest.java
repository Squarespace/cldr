package com.squarespace.cldr.units;

import static com.squarespace.cldr.CLDR.Locale.en;
import static org.testng.Assert.assertEquals;

import java.math.BigDecimal;

import org.testng.annotations.Test;

import com.squarespace.cldr.CLDR;
import com.squarespace.cldr.numbers.NumberFormatterTestBase;


public class UnitConverterTest extends NumberFormatterTestBase {

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
  }
  
  private static void run(CLDR.Locale locale, String n, Unit u, String nex, Unit uex) {
    UnitConverter converter = CLDR.get().getUnitConverter(locale);
    UnitValue input = value(n, u);
    UnitValue expected = value(nex, uex);
    UnitValue actual = converter.convert(input, uex);
    assertEquals(actual, expected);
  }

  private static UnitValue value(String n, Unit unit) {
    return new UnitValue(new BigDecimal(n), unit);
  }

}
