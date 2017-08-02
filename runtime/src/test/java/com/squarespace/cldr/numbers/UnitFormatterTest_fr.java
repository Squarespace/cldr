package com.squarespace.cldr.numbers;


import static com.squarespace.cldr.CLDR.Locale.fr;

import org.testng.annotations.Test;

import com.squarespace.cldr.units.Unit;
import com.squarespace.cldr.units.UnitFormat;
import com.squarespace.cldr.units.UnitFormatOptions;


public class UnitFormatterTest_fr extends NumberFormatterTestBase {

  @Test
  public void testDigital() {
    UnitFormatOptions options = unit();
    test(fr, options, "512", Unit.KILOBYTE, "512 ko");
    test(fr, options, "122.1533", Unit.MEGABYTE, "122,2 Mo");
    test(fr, options, "538.999", Unit.GIGABYTE, "539 Go");

    options = unit().setMaximumFractionDigits(3);
    test(fr, options, "122.1533", Unit.MEGABYTE, "122,153 Mo");
    test(fr, options, "538.999", Unit.GIGABYTE, "538,999 Go");
    
    options = unit(UnitFormat.NARROW).setMaximumFractionDigits(0);
    test(fr, options, "122.1533", Unit.MEGABYTE, "122 Mo");

    options = unit(UnitFormat.LONG).setMaximumFractionDigits(0);
    test(fr, options, "1", Unit.MEGABYTE, "1 mégaoctet");
    test(fr, options, "122.1533", Unit.MEGABYTE, "122 mégaoctets");
}
  
  @Test
  public void testDuration() {
    UnitFormatOptions options = unit(UnitFormat.LONG);
    test(fr, options, "1", Unit.DAY, "1 jour");
    test(fr, options, "1.2", Unit.DAY, "1,2 jour");
    test(fr, options, "513", Unit.DAY, "513 jours");
  }

}
