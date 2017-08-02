package com.squarespace.cldr.numbers;

import static com.squarespace.cldr.CLDR.Locale.en_US;

import java.util.List;

import org.testng.annotations.Test;

import com.squarespace.cldr.CLDR;
import com.squarespace.cldr.units.Unit;
import com.squarespace.cldr.units.UnitFactorSets;
import com.squarespace.cldr.units.UnitConverter;
import com.squarespace.cldr.units.UnitFactorSet;
import com.squarespace.cldr.units.UnitFactors;
import com.squarespace.cldr.units.UnitFormat;
import com.squarespace.cldr.units.UnitFormatOptions;
import com.squarespace.cldr.units.UnitValue;


public class UnitFormatterTest_en_US extends NumberFormatterTestBase {

  @Test
  public void testAngleSequence() {
    UnitConverter converter = CLDR.get().getUnitConverter(en_US);
    UnitValue degrees = new UnitValue("13.536613", Unit.DEGREE);
    List<UnitValue> seq = converter.sequence(degrees, UnitFactorSets.ANGLE);
    
    UnitFormatOptions options = unit(UnitFormat.LONG);
    test(en_US, options, seq, "13 degrees 32 arcminutes 11.8 arcseconds");
    
    options = unit(UnitFormat.NARROW);
    test(en_US, options, seq, "13° 32′ 11.8″");
    
    degrees = new UnitValue("1.5", Unit.DEGREE);
    seq = converter.sequence(degrees, UnitFactorSets.ANGLE);

    options = unit(UnitFormat.LONG);
    test(en_US, options, seq, "1 degree 30 arcminutes");

    options = unit(UnitFormat.NARROW);
    test(en_US, options, seq, "1° 30′");
  }

  @Test
  public void testDigital() {
    UnitFormatOptions options = unit(UnitFormat.SHORT);
    test(en_US, options, "512", Unit.KILOBYTE, "512 kB");
    test(en_US, options, "122.1533", Unit.MEGABYTE, "122.2 MB");
    test(en_US, options, "538.999", Unit.GIGABYTE, "539 GB");
    
    options = unit(UnitFormat.SHORT).setMaximumFractionDigits(3);
    test(en_US, options, "122.1533", Unit.MEGABYTE, "122.153 MB");
    
    options = unit(UnitFormat.NARROW).setMaximumFractionDigits(0);
    test(en_US, options, "122.1533", Unit.MEGABYTE, "122MB");

    options = unit(UnitFormat.LONG).setMaximumFractionDigits(0);
    test(en_US, options, "1", Unit.MEGABYTE, "1 megabyte");
    test(en_US, options, "122.1533", Unit.MEGABYTE, "122 megabytes");
  }
  
  @Test
  public void testDuration() {
    UnitFormatOptions options = unit(UnitFormat.LONG);
    test(en_US, options, "1", Unit.DAY, "1 day");
    test(en_US, options, "1.2", Unit.DAY, "1.2 days");
    test(en_US, options, "513", Unit.DAY, "513 days");
  }

  @Test
  public void testDurationSequence() {
    UnitConverter converter = CLDR.get().getUnitConverter(en_US);
    UnitValue days = new UnitValue("16.351", Unit.DAY);
    List<UnitValue> seq = converter.sequence(days, UnitFactorSets.DURATION);
    
    UnitFormatOptions options = unit(UnitFormat.LONG);
    test(en_US, options, seq, "16 days 8 hours 25 minutes 26.4 seconds");
    
    options = unit(UnitFormat.NARROW);
    test(en_US, options, seq, "16d 8h 25m 26.4s");
    
    days = new UnitValue("-25.735", Unit.DAY);
    seq = converter.sequence(days, UnitFactorSets.DURATION);
    test(en_US, options, seq, "-25d 17h 38m 24s");
  }

  @Test
  public void testLength() {
    UnitFormatOptions options = unit(UnitFormat.LONG);
    test(en_US, options, "1", Unit.FOOT, "1 foot");
    test(en_US, options, "1.2", Unit.FOOT, "1.2 feet");
    test(en_US, options, "17", Unit.FOOT, "17 feet");
  }
  
  @Test
  public void testLengthSequence() {
    UnitFactorSet factorSet = new UnitFactorSet(UnitFactors.LENGTH, Unit.MILE, Unit.FOOT);
    UnitConverter converter = CLDR.get().getUnitConverter(en_US);
    UnitValue days = new UnitValue("16.351", Unit.MILE);
    List<UnitValue> seq = converter.sequence(days, factorSet);
    
    UnitFormatOptions options = unit(UnitFormat.LONG).setMaximumFractionDigits(0);
    test(en_US, options, seq, "16 miles 1853 feet");
  }
}
