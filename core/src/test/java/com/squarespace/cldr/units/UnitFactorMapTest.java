package com.squarespace.cldr.units;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.List;

import org.testng.annotations.Test;


public class UnitFactorMapTest {

  @Test
  public void testBasics() {
    assertEquals(UnitCategory.ANGLE, new UnitFactorMap(UnitCategory.ANGLE).getUnitCategory());
  }

  @Test
  public void testSortUnits() {
    Unit[] units = new Unit[] { Unit.KILOMETER, Unit.NANOMETER, Unit.MILE, Unit.INCH, Unit.LIGHT_YEAR };
    UnitFactors.LENGTH.sortUnits(units);
    Unit[] expected = new Unit[] { Unit.LIGHT_YEAR, Unit.MILE, Unit.KILOMETER, Unit.INCH, Unit.NANOMETER };
    assertEquals(units, expected);

    // Units that don't belong to the category will sort to the end.
    units = new Unit[] { Unit.AMPERE, Unit.INCH, Unit.BUSHEL, Unit.MILE };
    UnitFactors.LENGTH.sortUnits(units);
    expected = new Unit[] { Unit.MILE, Unit.INCH, Unit.AMPERE, Unit.BUSHEL };
    assertEquals(units, expected);
  }

  @Test
  public void testConversion() {
    UnitFactorMap map = new UnitFactorMap(UnitCategory.FREQUENCY)
        .add(Unit.GIGAHERTZ, "1000", Unit.MEGAHERTZ)
        .add(Unit.MEGAHERTZ, "1000", Unit.KILOHERTZ)
        .add(Unit.KILOHERTZ, "1000", Unit.HERTZ)
        .complete();

    UnitValue input = new UnitValue("23", Unit.KILOHERTZ);
    UnitValue value = map.convert(input, Unit.HERTZ);
    assertEquals(value, new UnitValue("23000", Unit.HERTZ));

    try {
      input = new UnitValue("17", Unit.INCH);
      value = map.convert(input, Unit.HERTZ);
      fail("Expected IllegalStateException on invalid conversion from INCH to HERTZ");
    } catch (IllegalStateException e) {
      // Passed..
    }
  }

  @Test
  public void testConversionPaths() {
    List<UnitFactor> factors = UnitFactors.LENGTH.getPath(Unit.INCH, Unit.INCH);
    assertEquals(factors.size(), 1);
    assertEquals(factors.get(0), new UnitFactor(Rational.ONE, Unit.INCH));
  }

  @Test
  public void testMixed() {
    try {
      new UnitFactorMap(UnitCategory.ANGLE).add(Unit.ACRE, "1 / 10", Unit.DEGREE).complete();
      fail("Expected IllegalArgumentException on invalid mixed units");
    } catch (IllegalArgumentException e) {
      // Passed..
    }
  }

  @Test
  public void testIncomplete() {
    try {
      // Throw if there is no reachable conversion factor between
      // one or more of the given units.
      new UnitFactorMap(UnitCategory.LENGTH)
          .add(Unit.INCH, "2.54", Unit.CENTIMETER)
          .add(Unit.METER, "1000", Unit.KILOMETER)
          .complete();
      fail("Expected IllegalStateException for incomplete pairwise mapping");
    } catch (IllegalStateException e) {
      // Passed..
    }
  }

  @Test
  public void testFailedResolve() {
    UnitFactorMap map = new UnitFactorMap(UnitCategory.LENGTH)
        .add(Unit.INCH, "2.54", Unit.CENTIMETER)
        .complete();
    try {
      map.resolve(Unit.INCH, Unit.KILOMETER);
      fail("Expected IllegalStateException for failed resolution of INCH to KILOS");
    } catch (IllegalStateException e) {
      // Passed..
    }
  }

  @Test
  public void testDumpMapping() {
    String factors = UnitFactors.LENGTH.dump(Unit.INCH);
    assertTrue(factors.contains("Factor(2.54, CENTIMETER)"));
    assertTrue(factors.contains("Factor(1 / 12, FOOT)"));
  }

}
