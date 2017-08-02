package com.squarespace.cldr.units;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;

import org.testng.annotations.Test;


public class UnitFactorSetTest {

  @Test
  public void testAngleFactors() {
    UnitFactorSet set = new UnitFactorSet(UnitFactors.ANGLE, Unit.ARC_MINUTE, Unit.ARC_SECOND, Unit.DEGREE);
    assertEquals(set.base(), Unit.ARC_SECOND);
    assertEquals(set.factors(), Arrays.asList(
        new UnitValue("3600", Unit.DEGREE),
        new UnitValue("60", Unit.ARC_MINUTE),
        new UnitValue("1", Unit.ARC_SECOND)
    ));
  }

  @Test
  public void testDurationFactors() {
    UnitFactorSet set = new UnitFactorSet(UnitFactors.DURATION, Unit.YEAR, Unit.DAY, Unit.MONTH);
    assertEquals(set.factors(), Arrays.asList(
        new UnitValue("365.2425", Unit.YEAR),
        new UnitValue("30.436875", Unit.MONTH),
        new UnitValue("1", Unit.DAY)
    ));
  }

  @Test
  public void testLengthFactors() {
    UnitFactorSet set = new UnitFactorSet(UnitFactors.LENGTH, Unit.KILOMETER, Unit.MICROMETER, Unit.METER);
    assertEquals(set.base(), Unit.MICROMETER);
    assertEquals(set.factors(), Arrays.asList(
        new UnitValue("1000000000", Unit.KILOMETER),
        new UnitValue("1000000", Unit.METER),
        new UnitValue("1", Unit.MICROMETER)
    ));
  }
  
}
