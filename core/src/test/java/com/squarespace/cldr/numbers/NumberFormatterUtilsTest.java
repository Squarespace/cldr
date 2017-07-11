package com.squarespace.cldr.numbers;

import static com.squarespace.cldr.numbers.NumberRoundMode.CEIL;
import static com.squarespace.cldr.numbers.NumberRoundMode.FLOOR;
import static com.squarespace.cldr.numbers.NumberRoundMode.ROUND;
import static com.squarespace.cldr.numbers.NumberRoundMode.TRUNCATE;
import static org.testng.Assert.assertEquals;

import java.math.BigDecimal;

import org.testng.annotations.Test;


/**
 * Exercises the low-level number formatting implementation.
 */
public class NumberFormatterUtilsTest {

  @Test
  public void testSignificantDigits() {
    Builder fmt = format().minSigDigits(1).maxSigDigits(1);

    assertFmt(fmt.get(), "0", "0");
    assertFmt(fmt.get(), "0.0", "0");
    assertFmt(fmt.get(), "1.0", "1");
    assertFmt(fmt.get(), "1.23004", "1");
    assertFmt(fmt.get(), "12345", "10000");
    assertFmt(fmt.get(), "0.12345", "0.1");
    assertFmt(fmt.get(), "3.14159", "3");
    assertFmt(fmt.get(), "0.1", "0.1");
    assertFmt(fmt.get(), "0.999999", "1");

    fmt.minSigDigits(1).maxSigDigits(2);

    assertFmt(fmt.get(), "0", "0");
    assertFmt(fmt.get(), "0.0", "0");
    assertFmt(fmt.get(), "1.0", "1");
    assertFmt(fmt.get(), "1.23004", "1.2");
    assertFmt(fmt.get(), "12345", "12000");
    assertFmt(fmt.get(), "0.12345", "0.12");
    assertFmt(fmt.get(), "3.14159", "3.1");
    assertFmt(fmt.get(), "0.1", "0.1");
    assertFmt(fmt.get(), "0.999999", "1");

    fmt.minSigDigits(1).maxSigDigits(3);

    assertFmt(fmt.get(), "0", "0");
    assertFmt(fmt.get(), "0.0", "0");
    assertFmt(fmt.get(), "1.0", "1");
    assertFmt(fmt.get(), "1.23004", "1.23");
    assertFmt(fmt.get(), "12345", "12300");
    assertFmt(fmt.get(), "0.12345", "0.123");
    assertFmt(fmt.get(), "3.14159", "3.14");
    assertFmt(fmt.get(), "0.1", "0.1");
    assertFmt(fmt.get(), "0.999999", "1");

    fmt.minSigDigits(1).maxSigDigits(4);

    assertFmt(fmt.get(), "0", "0");
    assertFmt(fmt.get(), "0.0", "0");
    assertFmt(fmt.get(), "1.0", "1");
    assertFmt(fmt.get(), "1.23004", "1.23");
    assertFmt(fmt.get(), "12345", "12340");
    assertFmt(fmt.get(), "0.12345", "0.1234");
    assertFmt(fmt.get(), "3.14159", "3.142");
    assertFmt(fmt.get(), "0.1", "0.1");
    assertFmt(fmt.get(), "0.999999", "1");

    fmt.minSigDigits(1).maxSigDigits(5);

    assertFmt(fmt.get(), "0", "0");
    assertFmt(fmt.get(), "0.0", "0");
    assertFmt(fmt.get(), "1.0", "1");
    assertFmt(fmt.get(), "1.23004", "1.23");
    assertFmt(fmt.get(), "12345", "12345");
    assertFmt(fmt.get(), "0.12345", "0.12345");
    assertFmt(fmt.get(), "3.14159", "3.1416");
    assertFmt(fmt.get(), "0.1", "0.1");
    assertFmt(fmt.get(), "0.999999", "1");

    fmt.minSigDigits(1).maxSigDigits(6);

    assertFmt(fmt.get(), "0", "0");
    assertFmt(fmt.get(), "0.0", "0");
    assertFmt(fmt.get(), "1.0", "1");
    assertFmt(fmt.get(), "1.23004", "1.23004");
    assertFmt(fmt.get(), "12345", "12345");
    assertFmt(fmt.get(), "0.12345", "0.12345");
    assertFmt(fmt.get(), "3.14159", "3.14159");
    assertFmt(fmt.get(), "0.1", "0.1");
    assertFmt(fmt.get(), "0.999999", "0.999999");

    fmt.minSigDigits(2).maxSigDigits(2);

    assertFmt(fmt.get(), "0", "0.0");
    assertFmt(fmt.get(), "0.0", "0.0");
    assertFmt(fmt.get(), "1.0", "1.0");
    assertFmt(fmt.get(), "1.23004", "1.2");
    assertFmt(fmt.get(), "12345", "12000");
    assertFmt(fmt.get(), "0.12345", "0.12");
    assertFmt(fmt.get(), "3.14159", "3.1");
    assertFmt(fmt.get(), "0.1", "0.10");
    assertFmt(fmt.get(), "0.999999", "1.0");

    fmt.minSigDigits(2).maxSigDigits(3);

    assertFmt(fmt.get(), "0", "0.0");
    assertFmt(fmt.get(), "0.0", "0.0");
    assertFmt(fmt.get(), "1.0", "1.0");
    assertFmt(fmt.get(), "1.23004", "1.23");
    assertFmt(fmt.get(), "12345", "12300");
    assertFmt(fmt.get(), "0.12345", "0.123");
    assertFmt(fmt.get(), "3.14159", "3.14");
    assertFmt(fmt.get(), "0.1", "0.10");
    assertFmt(fmt.get(), "0.999999", "1.0");

    fmt.minSigDigits(2).maxSigDigits(4);

    assertFmt(fmt.get(), "0", "0.0");
    assertFmt(fmt.get(), "0.0", "0.0");
    assertFmt(fmt.get(), "1.0", "1.0");
    assertFmt(fmt.get(), "1.23004", "1.23");
    assertFmt(fmt.get(), "12345", "12340");
    assertFmt(fmt.get(), "0.12345", "0.1234");
    assertFmt(fmt.get(), "3.14159", "3.142");
    assertFmt(fmt.get(), "0.1", "0.10");
    assertFmt(fmt.get(), "0.999999", "1.0");

    fmt.minSigDigits(2).maxSigDigits(5);

    assertFmt(fmt.get(), "0", "0.0");
    assertFmt(fmt.get(), "0.0", "0.0");
    assertFmt(fmt.get(), "1.0", "1.0");
    assertFmt(fmt.get(), "1.23004", "1.23");
    assertFmt(fmt.get(), "12345", "12345");
    assertFmt(fmt.get(), "0.12345", "0.12345");
    assertFmt(fmt.get(), "3.14159", "3.1416");
    assertFmt(fmt.get(), "0.1", "0.10");
    assertFmt(fmt.get(), "0.999999", "1.0");

    fmt.minSigDigits(2).maxSigDigits(6);

    assertFmt(fmt.get(), "0", "0.0");
    assertFmt(fmt.get(), "0.0", "0.0");
    assertFmt(fmt.get(), "1.0", "1.0");
    assertFmt(fmt.get(), "1.23004", "1.23004");
    assertFmt(fmt.get(), "12345", "12345");
    assertFmt(fmt.get(), "0.12345", "0.12345");
    assertFmt(fmt.get(), "3.14159", "3.14159");
    assertFmt(fmt.get(), "0.1", "0.10");
    assertFmt(fmt.get(), "0.999999", "0.999999");

    fmt.minSigDigits(3).maxSigDigits(3);

    assertFmt(fmt.get(), "0", "0.00");
    assertFmt(fmt.get(), "0.0", "0.00");
    assertFmt(fmt.get(), "1.0", "1.00");
    assertFmt(fmt.get(), "1.23004", "1.23");
    assertFmt(fmt.get(), "12345", "12300");
    assertFmt(fmt.get(), "0.12345", "0.123");
    assertFmt(fmt.get(), "3.14159", "3.14");
    assertFmt(fmt.get(), "0.1", "0.100");
    assertFmt(fmt.get(), "0.999999", "1.00");

    fmt.minSigDigits(3).maxSigDigits(4);

    assertFmt(fmt.get(), "0", "0.00");
    assertFmt(fmt.get(), "0.0", "0.00");
    assertFmt(fmt.get(), "1.0", "1.00");
    assertFmt(fmt.get(), "1.23004", "1.23");
    assertFmt(fmt.get(), "12345", "12340");
    assertFmt(fmt.get(), "0.12345", "0.1234");
    assertFmt(fmt.get(), "3.14159", "3.142");
    assertFmt(fmt.get(), "0.1", "0.100");
    assertFmt(fmt.get(), "0.999999", "1.00");

    fmt.minSigDigits(3).maxSigDigits(5);

    assertFmt(fmt.get(), "0", "0.00");
    assertFmt(fmt.get(), "0.0", "0.00");
    assertFmt(fmt.get(), "1.0", "1.00");
    assertFmt(fmt.get(), "1.23004", "1.23");
    assertFmt(fmt.get(), "12345", "12345");
    assertFmt(fmt.get(), "0.12345", "0.12345");
    assertFmt(fmt.get(), "3.14159", "3.1416");
    assertFmt(fmt.get(), "0.1", "0.100");
    assertFmt(fmt.get(), "0.999999", "1.00");

    fmt.minSigDigits(3).maxSigDigits(6);

    assertFmt(fmt.get(), "0", "0.00");
    assertFmt(fmt.get(), "0.0", "0.00");
    assertFmt(fmt.get(), "1.0", "1.00");
    assertFmt(fmt.get(), "1.23004", "1.23004");
    assertFmt(fmt.get(), "12345", "12345");
    assertFmt(fmt.get(), "0.12345", "0.12345");
    assertFmt(fmt.get(), "3.14159", "3.14159");
    assertFmt(fmt.get(), "0.1", "0.100");
    assertFmt(fmt.get(), "0.999999", "0.999999");

    fmt.minSigDigits(4).maxSigDigits(4);

    assertFmt(fmt.get(), "0", "0.000");
    assertFmt(fmt.get(), "0.0", "0.000");
    assertFmt(fmt.get(), "1.0", "1.000");
    assertFmt(fmt.get(), "1.23004", "1.230");
    assertFmt(fmt.get(), "12345", "12340");
    assertFmt(fmt.get(), "0.12345", "0.1234");
    assertFmt(fmt.get(), "3.14159", "3.142");
    assertFmt(fmt.get(), "0.1", "0.1000");
    assertFmt(fmt.get(), "0.999999", "1.000");

    fmt.minSigDigits(4).maxSigDigits(5);

    assertFmt(fmt.get(), "0", "0.000");
    assertFmt(fmt.get(), "0.0", "0.000");
    assertFmt(fmt.get(), "1.0", "1.000");
    assertFmt(fmt.get(), "1.23004", "1.230");
    assertFmt(fmt.get(), "12345", "12345");
    assertFmt(fmt.get(), "0.12345", "0.12345");
    assertFmt(fmt.get(), "3.14159", "3.1416");
    assertFmt(fmt.get(), "0.1", "0.1000");
    assertFmt(fmt.get(), "0.999999", "1.000");

    fmt.minSigDigits(4).maxSigDigits(6);

    assertFmt(fmt.get(), "0", "0.000");
    assertFmt(fmt.get(), "0.0", "0.000");
    assertFmt(fmt.get(), "1.0", "1.000");
    assertFmt(fmt.get(), "1.23004", "1.23004");
    assertFmt(fmt.get(), "12345", "12345");
    assertFmt(fmt.get(), "0.12345", "0.12345");
    assertFmt(fmt.get(), "3.14159", "3.14159");
    assertFmt(fmt.get(), "0.1", "0.1000");
    assertFmt(fmt.get(), "0.999999", "0.999999");

    fmt.minSigDigits(5).maxSigDigits(5);

    assertFmt(fmt.get(), "0", "0.0000");
    assertFmt(fmt.get(), "0.0", "0.0000");
    assertFmt(fmt.get(), "1.0", "1.0000");
    assertFmt(fmt.get(), "1.23004", "1.2300");
    assertFmt(fmt.get(), "12345", "12345");
    assertFmt(fmt.get(), "0.12345", "0.12345");
    assertFmt(fmt.get(), "3.14159", "3.1416");
    assertFmt(fmt.get(), "0.1", "0.10000");
    assertFmt(fmt.get(), "0.999999", "1.0000");

    fmt.minSigDigits(5).maxSigDigits(6);

    assertFmt(fmt.get(), "0", "0.0000");
    assertFmt(fmt.get(), "0.0", "0.0000");
    assertFmt(fmt.get(), "1.0", "1.0000");
    assertFmt(fmt.get(), "1.23004", "1.23004");
    assertFmt(fmt.get(), "12345", "12345");
    assertFmt(fmt.get(), "0.12345", "0.12345");
    assertFmt(fmt.get(), "3.14159", "3.14159");
    assertFmt(fmt.get(), "0.1", "0.10000");
    assertFmt(fmt.get(), "0.999999", "0.999999");

    fmt.minSigDigits(6).maxSigDigits(6);

    assertFmt(fmt.get(), "0", "0.00000");
    assertFmt(fmt.get(), "0.0", "0.00000");
    assertFmt(fmt.get(), "1.0", "1.00000");
    assertFmt(fmt.get(), "1.23004", "1.23004");
    assertFmt(fmt.get(), "12345", "12345.0");
    assertFmt(fmt.get(), "0.12345", "0.123450");
    assertFmt(fmt.get(), "3.14159", "3.14159");
    assertFmt(fmt.get(), "0.1", "0.100000");
    assertFmt(fmt.get(), "0.999999", "0.999999");

    fmt.minSigDigits(7).maxSigDigits(7);

    assertFmt(fmt.get(), "0", "0.000000");
    assertFmt(fmt.get(), "0.0", "0.000000");
    assertFmt(fmt.get(), "1.0", "1.000000");
    assertFmt(fmt.get(), "1.23004", "1.230040");
    assertFmt(fmt.get(), "12345", "12345.00");
    assertFmt(fmt.get(), "0.12345", "0.1234500");
    assertFmt(fmt.get(), "3.14159", "3.141590");
    assertFmt(fmt.get(), "0.1", "0.1000000");
    assertFmt(fmt.get(), "0.999999", "0.9999990");
  }

  @Test
  public void testMoreSignificantDigits() {
    Builder fmt = format().minSigDigits(1).maxSigDigits(3);

    assertFmt(fmt.get(), "0", "0");
    assertFmt(fmt.get(), "0.1", "0.1");
    assertFmt(fmt.get(), "1.0", "1");
    assertFmt(fmt.get(), "12.1", "12.1");
    assertFmt(fmt.get(), "12.01", "12");
    assertFmt(fmt.get(), "0.123", "0.123");
    assertFmt(fmt.get(), "0.1245", "0.124");
    assertFmt(fmt.get(), "0.1256", "0.126");
    assertFmt(fmt.get(), "0.199999", "0.2");
    assertFmt(fmt.get(), "0.111111", "0.111");

    assertFmt(fmt.get(), "12345", "12300");
    assertFmt(fmt.get(), "12356", "12400");
    assertFmt(fmt.get(), "12356.789", "12400");
    assertFmt(fmt.get(), "19999", "20000");

    fmt.minSigDigits(1).maxSigDigits(1);

    assertFmt(fmt.get(), "0", "0");
    assertFmt(fmt.get(), "0.22", "0.2");
    assertFmt(fmt.get(), "0.25", "0.2");
    assertFmt(fmt.get(), "0.26", "0.3");
    assertFmt(fmt.get(), "0.32", "0.3");
    assertFmt(fmt.get(), "0.35", "0.4");
    assertFmt(fmt.get(), "0.36", "0.4");
    assertFmt(fmt.get(), "1", "1");
    assertFmt(fmt.get(), "1.3", "1");
    assertFmt(fmt.get(), "1.5", "2");
    assertFmt(fmt.get(), "1.6", "2");

    fmt.minSigDigits(1).maxSigDigits(2);

    assertFmt(fmt.get(), "0", "0");
    assertFmt(fmt.get(), "0.22", "0.22");
    assertFmt(fmt.get(), "0.25", "0.25");
    assertFmt(fmt.get(), "0.26", "0.26");
    assertFmt(fmt.get(), "0.32", "0.32");
    assertFmt(fmt.get(), "0.35", "0.35");
    assertFmt(fmt.get(), "0.36", "0.36");
    assertFmt(fmt.get(), "1", "1");
    assertFmt(fmt.get(), "1.3", "1.3");
    assertFmt(fmt.get(), "1.5", "1.5");
    assertFmt(fmt.get(), "1.6", "1.6");

    fmt.minSigDigits(2).maxSigDigits(3);

    assertFmt(fmt.get(), "0", "0.0");
    assertFmt(fmt.get(), "0.22", "0.22");
    assertFmt(fmt.get(), "0.25", "0.25");
    assertFmt(fmt.get(), "0.26", "0.26");
    assertFmt(fmt.get(), "0.32", "0.32");
    assertFmt(fmt.get(), "0.35", "0.35");
    assertFmt(fmt.get(), "0.36", "0.36");
    assertFmt(fmt.get(), "1", "1.0");
    assertFmt(fmt.get(), "1.3", "1.3");
    assertFmt(fmt.get(), "1.5", "1.5");
    assertFmt(fmt.get(), "1.6", "1.6");

    fmt.minSigDigits(3).maxSigDigits(3);

    assertFmt(fmt.get(), "0", "0.00");
    assertFmt(fmt.get(), "0.22", "0.220");
    assertFmt(fmt.get(), "0.25", "0.250");
    assertFmt(fmt.get(), "0.26", "0.260");
    assertFmt(fmt.get(), "0.32", "0.320");
    assertFmt(fmt.get(), "0.35", "0.350");
    assertFmt(fmt.get(), "0.36", "0.360");
    assertFmt(fmt.get(), "1", "1.00");
    assertFmt(fmt.get(), "1.3", "1.30");
    assertFmt(fmt.get(), "1.5", "1.50");
    assertFmt(fmt.get(), "1.6", "1.60");
  }

  @Test
  public void testEvenMoreSignificantDigits() {
    Builder fmt = format().minSigDigits(1).maxSigDigits(3);

    assertFmt(fmt.get(), "123456789123456789", "123000000000000000");
    assertFmt(fmt.get(), "0.123456789", "0.123");
    assertFmt(fmt.get(), "0.000000000000123456789", "0.000000000000123");

    assertFmt(fmt.get(), "0.000", "0");
    assertFmt(fmt.get(), "0.10", "0.1");
    assertFmt(fmt.get(), "0.01", "0.01");
    assertFmt(fmt.get(), "0.001", "0.001");
    assertFmt(fmt.get(), "0.0001", "0.0001");

    fmt.minSigDigits(2);
    assertFmt(fmt.get(), "0.000", "0.0");
    assertFmt(fmt.get(), "0.10", "0.10");
    assertFmt(fmt.get(), "0.01", "0.010");
    assertFmt(fmt.get(), "0.001", "0.0010");
    assertFmt(fmt.get(), "0.0001", "0.00010");

    fmt.minSigDigits(3);
    assertFmt(fmt.get(), "0.000", "0.00");
    assertFmt(fmt.get(), "0.10", "0.100");
    assertFmt(fmt.get(), "0.01", "0.0100");
    assertFmt(fmt.get(), "0.001", "0.00100");
    assertFmt(fmt.get(), "0.0001", "0.000100");
  }

  /**
   * Examples from the CLDR documentation.
   */
  @Test
  public void testExamples() {
    Builder fmt = format().minSigDigits(3).maxSigDigits(3);

    assertFmt(fmt.get(), "12345", "12300");
    assertFmt(fmt.get(), "0.12345", "0.123");

    fmt.minSigDigits(2).maxSigDigits(4);

    assertFmt(fmt.get(), "3.14159", "3.142");
    assertFmt(fmt.get(), "1.23004", "1.23");
  }

  /**
   * Ensures the alternate decimal and group characters are used in currency mode,
   * when the locale supports them.
   */
  @Test
  public void testCurrency() {
    Builder fmt = format()
        .grouping(true)
        .currency(true)
        .currencyDecimal("^")
        .currencyGroup(" ")
        .minFracDigits(2)
        .secondaryGroupingSize(2);

    assertFmt(fmt.get(), "1234567890.126", "1 23 45 67 890^13");
  }

  @Test
  public void testIntegers() {
    Builder fmt = format().minIntDigits(3);

    assertFmt(fmt.get(), "0", "000");
    assertFmt(fmt.get(), "1", "001");
    assertFmt(fmt.get(), "12345", "12345");
    assertFmt(fmt.get(), "12.6", "012.6");

    fmt.minIntDigits(1).maxFracDigits(0);

    assertFmt(fmt.get(), "1", "1");
    assertFmt(fmt.get(), "1.1", "1");
    assertFmt(fmt.get(), "1.5", "2");
    assertFmt(fmt.get(), "0", "0");
    assertFmt(fmt.get(), "0.1", "0");
    assertFmt(fmt.get(), "0.5", "0");
    assertFmt(fmt.get(), "0.6", "1");
    assertFmt(fmt.get(), "1.4", "1");
    assertFmt(fmt.get(), "1.5", "2");
    assertFmt(fmt.get(), "0.0001", "0");

    fmt.minFracDigits(1).maxFracDigits(3);

    assertFmt(fmt.get(), "1.20000", "1.2");
    assertFmt(fmt.get(), "0.0020000", "0.002");

    fmt.minFracDigits(3);

    assertFmt(fmt.get(), "1.20000", "1.200");
    assertFmt(fmt.get(), "0.0020000", "0.002");
  }

  @Test
  public void testFractions() {
    Builder fmt = format().maxFracDigits(3).minFracDigits(2);

    assertFmt(fmt.get(), "12.0", "12.00");
    assertFmt(fmt.get(), "12.0012", "12.001");
    assertFmt(fmt.get(), "12.00001", "12.00");

    assertFmt(fmt.get(), "0.0", "0.00");

    // NOTE: indicators for negative numbers are handled in a separate formatter.
    // Here we only format the absolute value.
    assertFmt(fmt.get(), "-1.00", "1.00");

    fmt.minIntDigits(1).maxFracDigits(1).minFracDigits(1);

    assertFmt(fmt.get(), "1", "1.0");
    assertFmt(fmt.get(), "1.1", "1.1");
    assertFmt(fmt.get(), "1.5", "1.5");
    assertFmt(fmt.get(), "0", "0.0");
    assertFmt(fmt.get(), "0.1", "0.1");
    assertFmt(fmt.get(), "0.5", "0.5");
    assertFmt(fmt.get(), "0.6", "0.6");
    assertFmt(fmt.get(), "1.4", "1.4");
    assertFmt(fmt.get(), "1.5", "1.5");
    assertFmt(fmt.get(), "0.0001", "0.0");
  }

  /**
   * Tests that significant digits mode is only activated when a valid
   * minimum and maximum are both set.
   */
  @Test
  public void testInvalidSignificantDigits() {
    Builder fmt = format()
        .minFracDigits(2)
        .minSigDigits(1)
        .maxSigDigits(-1);

    assertFmt(fmt.get(), "12345.0", "12345.00");

    fmt.maxSigDigits(2).minSigDigits(0);

    assertFmt(fmt.get(), "12345.0", "12345.00");
  }


  @Test
  public void testRoundingModes() {
    Builder fmt = format().roundMode(ROUND);

    assertFmt(fmt.get(), "12.032", "12.03");
    assertFmt(fmt.get(), "12.035", "12.04");
    assertFmt(fmt.get(), "12.045", "12.04");
    assertFmt(fmt.get(), "12.055", "12.06");

    assertFmt(fmt.get(), "-12.032", "12.03");
    assertFmt(fmt.get(), "-12.035", "12.04");
    assertFmt(fmt.get(), "-12.045", "12.04");
    assertFmt(fmt.get(), "-12.055", "12.06");

    fmt.roundMode(TRUNCATE);

    assertFmt(fmt.get(), "12.032", "12.03");
    assertFmt(fmt.get(), "12.035", "12.03");
    assertFmt(fmt.get(), "12.045", "12.04");
    assertFmt(fmt.get(), "12.055", "12.05");

    assertFmt(fmt.get(), "-12.032", "12.03");
    assertFmt(fmt.get(), "-12.035", "12.03");
    assertFmt(fmt.get(), "-12.045", "12.04");
    assertFmt(fmt.get(), "-12.055", "12.05");

    fmt.roundMode(FLOOR);

    assertFmt(fmt.get(), "12.032", "12.03");
    assertFmt(fmt.get(), "12.035", "12.03");
    assertFmt(fmt.get(), "12.045", "12.04");
    assertFmt(fmt.get(), "12.055", "12.05");

    assertFmt(fmt.get(), "-12.032", "12.04");
    assertFmt(fmt.get(), "-12.035", "12.04");
    assertFmt(fmt.get(), "-12.045", "12.05");
    assertFmt(fmt.get(), "-12.055", "12.06");

    fmt.roundMode(CEIL);

    assertFmt(fmt.get(), "12.032", "12.04");
    assertFmt(fmt.get(), "12.035", "12.04");
    assertFmt(fmt.get(), "12.045", "12.05");
    assertFmt(fmt.get(), "12.055", "12.06");

    assertFmt(fmt.get(), "-12.032", "12.03");
    assertFmt(fmt.get(), "-12.035", "12.03");
    assertFmt(fmt.get(), "-12.045", "12.04");
    assertFmt(fmt.get(), "-12.055", "12.05");

    // Same as ROUND
    fmt.roundMode(null);

    assertFmt(fmt.get(), "12.032", "12.03");
  }

  @Test
  public void testGrouping() {
    Builder fmt = format().grouping(true);

    assertFmt(fmt.get(), "1234567", "1,234,567");
    assertFmt(fmt.get(), "11111111111111.123", "11,111,111,111,111.12");

    fmt.minimumGroupingDigits(2);

    assertFmt(fmt.get(), "1234", "1234");
    assertFmt(fmt.get(), "12345", "12,345");
    assertFmt(fmt.get(), "1234567", "1,234,567");

    fmt.primaryGroupingSize(4).secondaryGroupingSize(2);

    assertFmt(fmt.get(), "1234567", "1,23,4567");
    assertFmt(fmt.get(), "11111111111111.123", "11,11,11,11,11,1111.12");
  }

  private static void assertFmt(Fmt call, String number, String expected) {
    BigDecimal n = new BigDecimal(number);
    String result = call.format(n);
    assertEquals(result, expected);
  }

  private static Builder format() {
    return new Builder();
  }

  private static class Builder {

    private NumberRoundMode roundMode = ROUND;
    private boolean grouping = false;
    private int minIntDigits = 1;
    private int maxFracDigits = 2;
    private int minFracDigits = 0;
    private int maxSigDigits = -1;
    private int minSigDigits = -1;
    private int primaryGroupingSize = 3;
    private int secondaryGroupingSize = 3;
    private int minimumGroupingDigits = 1;
    private boolean currency = false;
    private String currencyDecimal = ".";
    private String currencyGroup = ",";

    Builder roundMode(NumberRoundMode mode) {
      this.roundMode = mode == null ? ROUND : mode;
      return this;
    }

    Builder grouping(boolean f) {
      this.grouping = f;
      return this;
    }

    Builder minIntDigits(int d) {
      this.minIntDigits = d;
      return this;
    }

    Builder maxFracDigits(int d) {
      this.maxFracDigits = d;
      return this;
    }

    Builder minFracDigits(int d) {
      this.minFracDigits = d;
      return this;
    }

    Builder maxSigDigits(int d) {
      this.maxSigDigits = d;
      return this;
    }

    Builder minSigDigits(int d) {
      this.minSigDigits = d;
      return this;
    }

    Builder primaryGroupingSize(int d) {
      this.primaryGroupingSize = d;
      return this;
    }

    Builder secondaryGroupingSize(int d) {
      this.secondaryGroupingSize = d;
      return this;
    }

    Builder minimumGroupingDigits(int d) {
      this.minimumGroupingDigits = d;
      return this;
    }

    Builder currency(boolean flag) {
      this.currency = flag;
      return this;
    }

    Builder currencyDecimal(String s) {
      this.currencyDecimal = s;
      return this;
    }

    Builder currencyGroup(String s) {
      this.currencyGroup = s;
      return this;
    }

    Fmt get() {
      NumberFormatterParams params = new NumberFormatterParams();

      params.currencyDecimal = currencyDecimal;
      params.currencyGroup = currencyGroup;
      params.minimumGroupingDigits = minimumGroupingDigits;

      NumberOperands operands = new NumberOperands("0");
      return new Fmt() {

        @Override
        public String format(BigDecimal n) {
          DigitBuffer buf = new DigitBuffer();
          long[] digits = NumberFormattingUtils.setup(
              n,
              operands,
              roundMode,
              minIntDigits,
              maxFracDigits,
              minFracDigits,
              maxSigDigits,
              minSigDigits);

          NumberFormattingUtils.format(
              digits,
              buf,
              operands,
              params,
              currency,
              grouping,
              primaryGroupingSize,
              secondaryGroupingSize);

          return buf.toString();
        }

      };
    }
  }

  interface Fmt {
    String format(BigDecimal n);
  }
}
