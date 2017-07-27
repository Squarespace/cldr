package com.squarespace.cldr.numbers;

import static com.squarespace.cldr.numbers.NumberFormatMode.DEFAULT;
import static com.squarespace.cldr.numbers.NumberFormatMode.SIGNIFICANT;
import static com.squarespace.cldr.numbers.NumberFormatMode.SIGNIFICANT_MAXFRAC;
import static com.squarespace.cldr.numbers.NumberRoundMode.CEIL;
import static com.squarespace.cldr.numbers.NumberRoundMode.FLOOR;
import static com.squarespace.cldr.numbers.NumberRoundMode.ROUND;
import static com.squarespace.cldr.numbers.NumberRoundMode.TRUNCATE;
import static org.testng.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.testng.annotations.Test;


/**
 * Exercises the low-level number formatting implementation.
 */
public class NumberFormattingUtilsTest {

  @Test
  public void testIntegerDigits() {
    for (int i = 1; i < 10; i++) {
      intDigits(num("12345", i), 5);
      intDigits(num("0.12345", i), 0);
      intDigits(num("12345.12345", i), 5);
    }
  }

  @Test
  public void testSetup() {
    Builder fmt = format(SIGNIFICANT).minSigDigits(1).maxSigDigits(1);

    setup(fmt.get(), "0", "0");
    setup(fmt.get(), "0.0", "0");
    setup(fmt.get(), "1.0", "1");
    setup(fmt.get(), "1.23004", "1");
    setup(fmt.get(), "12345", "10000");
    setup(fmt.get(), "0.12345", "0.1");
    setup(fmt.get(), "3.14159", "3");
    setup(fmt.get(), "0.1", "0.1");
    setup(fmt.get(), "0.999999", "1");

    fmt = format(NumberFormatMode.DEFAULT).minFracDigits(1).maxFracDigits(3);

    setup(fmt.get(), "0", "0.0");
    setup(fmt.get(), "0.0", "0.0");
    setup(fmt.get(), "1.0", "1.0");
    setup(fmt.get(), "1.23004", "1.23");
    setup(fmt.get(), "12345", "12345.0");
    setup(fmt.get(), "0.12345", "0.123");
    setup(fmt.get(), "3.14159", "3.142");
    setup(fmt.get(), "0.1", "0.1");
    setup(fmt.get(), "0.999999", "1.0");
  }

  @Test
  public void testSignificantDigits() {
    Builder fmt = format(SIGNIFICANT).minSigDigits(1).maxSigDigits(1);

    setup(fmt.get(), "0", "0");
    setup(fmt.get(), "0.0", "0");
    setup(fmt.get(), "1.0", "1");
    setup(fmt.get(), "1.23004", "1");
    setup(fmt.get(), "12345", "10000");
    setup(fmt.get(), "0.12345", "0.1");
    setup(fmt.get(), "3.14159", "3");
    setup(fmt.get(), "0.1", "0.1");
    setup(fmt.get(), "0.999999", "1");

    fmt.minSigDigits(1).maxSigDigits(2);

    setup(fmt.get(), "0", "0");
    setup(fmt.get(), "0.0", "0");
    setup(fmt.get(), "1.0", "1");
    setup(fmt.get(), "1.23004", "1.2");
    setup(fmt.get(), "12345", "12000");
    setup(fmt.get(), "0.12345", "0.12");
    setup(fmt.get(), "3.14159", "3.1");
    setup(fmt.get(), "0.1", "0.1");
    setup(fmt.get(), "0.999999", "1");

    fmt.minSigDigits(1).maxSigDigits(3);

    setup(fmt.get(), "0", "0");
    setup(fmt.get(), "0.0", "0");
    setup(fmt.get(), "1.0", "1");
    setup(fmt.get(), "1.23004", "1.23");
    setup(fmt.get(), "12345", "12300");
    setup(fmt.get(), "0.12345", "0.123");
    setup(fmt.get(), "3.14159", "3.14");
    setup(fmt.get(), "0.1", "0.1");
    setup(fmt.get(), "0.999999", "1");

    fmt.minSigDigits(1).maxSigDigits(4);

    setup(fmt.get(), "0", "0");
    setup(fmt.get(), "0.0", "0");
    setup(fmt.get(), "1.0", "1");
    setup(fmt.get(), "1.23004", "1.23");
    setup(fmt.get(), "12345", "12340");
    setup(fmt.get(), "0.12345", "0.1234");
    setup(fmt.get(), "3.14159", "3.142");
    setup(fmt.get(), "0.1", "0.1");
    setup(fmt.get(), "0.999999", "1");

    fmt.minSigDigits(1).maxSigDigits(5);

    setup(fmt.get(), "0", "0");
    setup(fmt.get(), "0.0", "0");
    setup(fmt.get(), "1.0", "1");
    setup(fmt.get(), "1.23004", "1.23");
    setup(fmt.get(), "12345", "12345");
    setup(fmt.get(), "0.12345", "0.12345");
    setup(fmt.get(), "3.14159", "3.1416");
    setup(fmt.get(), "0.1", "0.1");
    setup(fmt.get(), "0.999999", "1");

    fmt.minSigDigits(1).maxSigDigits(6);

    setup(fmt.get(), "0", "0");
    setup(fmt.get(), "0.0", "0");
    setup(fmt.get(), "1.0", "1");
    setup(fmt.get(), "1.23004", "1.23004");
    setup(fmt.get(), "12345", "12345");
    setup(fmt.get(), "0.12345", "0.12345");
    setup(fmt.get(), "3.14159", "3.14159");
    setup(fmt.get(), "0.1", "0.1");
    setup(fmt.get(), "0.999999", "0.999999");

    fmt.minSigDigits(2).maxSigDigits(2);

    setup(fmt.get(), "0", "0.0");
    setup(fmt.get(), "0.0", "0.0");
    setup(fmt.get(), "1.0", "1.0");
    setup(fmt.get(), "1.23004", "1.2");
    setup(fmt.get(), "12345", "12000");
    setup(fmt.get(), "0.12345", "0.12");
    setup(fmt.get(), "3.14159", "3.1");
    setup(fmt.get(), "0.1", "0.10");
    setup(fmt.get(), "0.999999", "1.0");

    fmt.minSigDigits(2).maxSigDigits(3);

    setup(fmt.get(), "0", "0.0");
    setup(fmt.get(), "0.0", "0.0");
    setup(fmt.get(), "1.0", "1.0");
    setup(fmt.get(), "1.23004", "1.23");
    setup(fmt.get(), "12345", "12300");
    setup(fmt.get(), "0.12345", "0.123");
    setup(fmt.get(), "3.14159", "3.14");
    setup(fmt.get(), "0.1", "0.10");
    setup(fmt.get(), "0.999999", "1.0");

    fmt.minSigDigits(2).maxSigDigits(4);

    setup(fmt.get(), "0", "0.0");
    setup(fmt.get(), "0.0", "0.0");
    setup(fmt.get(), "1.0", "1.0");
    setup(fmt.get(), "1.23004", "1.23");
    setup(fmt.get(), "12345", "12340");
    setup(fmt.get(), "0.12345", "0.1234");
    setup(fmt.get(), "3.14159", "3.142");
    setup(fmt.get(), "0.1", "0.10");
    setup(fmt.get(), "0.999999", "1.0");

    fmt.minSigDigits(2).maxSigDigits(5);

    setup(fmt.get(), "0", "0.0");
    setup(fmt.get(), "0.0", "0.0");
    setup(fmt.get(), "1.0", "1.0");
    setup(fmt.get(), "1.23004", "1.23");
    setup(fmt.get(), "12345", "12345");
    setup(fmt.get(), "0.12345", "0.12345");
    setup(fmt.get(), "3.14159", "3.1416");
    setup(fmt.get(), "0.1", "0.10");
    setup(fmt.get(), "0.999999", "1.0");

    fmt.minSigDigits(2).maxSigDigits(6);

    setup(fmt.get(), "0", "0.0");
    setup(fmt.get(), "0.0", "0.0");
    setup(fmt.get(), "1.0", "1.0");
    setup(fmt.get(), "1.23004", "1.23004");
    setup(fmt.get(), "12345", "12345");
    setup(fmt.get(), "0.12345", "0.12345");
    setup(fmt.get(), "3.14159", "3.14159");
    setup(fmt.get(), "0.1", "0.10");
    setup(fmt.get(), "0.999999", "0.999999");

    fmt.minSigDigits(3).maxSigDigits(3);

    setup(fmt.get(), "0", "0.00");
    setup(fmt.get(), "0.0", "0.00");
    setup(fmt.get(), "1.0", "1.00");
    setup(fmt.get(), "1.23004", "1.23");
    setup(fmt.get(), "12345", "12300");
    setup(fmt.get(), "0.12345", "0.123");
    setup(fmt.get(), "3.14159", "3.14");
    setup(fmt.get(), "0.1", "0.100");
    setup(fmt.get(), "0.999999", "1.00");

    fmt.minSigDigits(3).maxSigDigits(4);

    setup(fmt.get(), "0", "0.00");
    setup(fmt.get(), "0.0", "0.00");
    setup(fmt.get(), "1.0", "1.00");
    setup(fmt.get(), "1.23004", "1.23");
    setup(fmt.get(), "12345", "12340");
    setup(fmt.get(), "0.12345", "0.1234");
    setup(fmt.get(), "3.14159", "3.142");
    setup(fmt.get(), "0.1", "0.100");
    setup(fmt.get(), "0.999999", "1.00");

    fmt.minSigDigits(3).maxSigDigits(5);

    setup(fmt.get(), "0", "0.00");
    setup(fmt.get(), "0.0", "0.00");
    setup(fmt.get(), "1.0", "1.00");
    setup(fmt.get(), "1.23004", "1.23");
    setup(fmt.get(), "12345", "12345");
    setup(fmt.get(), "0.12345", "0.12345");
    setup(fmt.get(), "3.14159", "3.1416");
    setup(fmt.get(), "0.1", "0.100");
    setup(fmt.get(), "0.999999", "1.00");

    fmt.minSigDigits(3).maxSigDigits(6);

    setup(fmt.get(), "0", "0.00");
    setup(fmt.get(), "0.0", "0.00");
    setup(fmt.get(), "1.0", "1.00");
    setup(fmt.get(), "1.23004", "1.23004");
    setup(fmt.get(), "12345", "12345");
    setup(fmt.get(), "0.12345", "0.12345");
    setup(fmt.get(), "3.14159", "3.14159");
    setup(fmt.get(), "0.1", "0.100");
    setup(fmt.get(), "0.999999", "0.999999");

    fmt.minSigDigits(4).maxSigDigits(4);

    setup(fmt.get(), "0", "0.000");
    setup(fmt.get(), "0.0", "0.000");
    setup(fmt.get(), "1.0", "1.000");
    setup(fmt.get(), "1.23004", "1.230");
    setup(fmt.get(), "12345", "12340");
    setup(fmt.get(), "0.12345", "0.1234");
    setup(fmt.get(), "3.14159", "3.142");
    setup(fmt.get(), "0.1", "0.1000");
    setup(fmt.get(), "0.999999", "1.000");

    fmt.minSigDigits(4).maxSigDigits(5);

    setup(fmt.get(), "0", "0.000");
    setup(fmt.get(), "0.0", "0.000");
    setup(fmt.get(), "1.0", "1.000");
    setup(fmt.get(), "1.23004", "1.230");
    setup(fmt.get(), "12345", "12345");
    setup(fmt.get(), "0.12345", "0.12345");
    setup(fmt.get(), "3.14159", "3.1416");
    setup(fmt.get(), "0.1", "0.1000");
    setup(fmt.get(), "0.999999", "1.000");

    fmt.minSigDigits(4).maxSigDigits(6);

    setup(fmt.get(), "0", "0.000");
    setup(fmt.get(), "0.0", "0.000");
    setup(fmt.get(), "1.0", "1.000");
    setup(fmt.get(), "1.23004", "1.23004");
    setup(fmt.get(), "12345", "12345");
    setup(fmt.get(), "0.12345", "0.12345");
    setup(fmt.get(), "3.14159", "3.14159");
    setup(fmt.get(), "0.1", "0.1000");
    setup(fmt.get(), "0.999999", "0.999999");

    fmt.minSigDigits(5).maxSigDigits(5);

    setup(fmt.get(), "0", "0.0000");
    setup(fmt.get(), "0.0", "0.0000");
    setup(fmt.get(), "1.0", "1.0000");
    setup(fmt.get(), "1.23004", "1.2300");
    setup(fmt.get(), "12345", "12345");
    setup(fmt.get(), "0.12345", "0.12345");
    setup(fmt.get(), "3.14159", "3.1416");
    setup(fmt.get(), "0.1", "0.10000");
    setup(fmt.get(), "0.999999", "1.0000");

    fmt.minSigDigits(5).maxSigDigits(6);

    setup(fmt.get(), "0", "0.0000");
    setup(fmt.get(), "0.0", "0.0000");
    setup(fmt.get(), "1.0", "1.0000");
    setup(fmt.get(), "1.23004", "1.23004");
    setup(fmt.get(), "12345", "12345");
    setup(fmt.get(), "0.12345", "0.12345");
    setup(fmt.get(), "3.14159", "3.14159");
    setup(fmt.get(), "0.1", "0.10000");
    setup(fmt.get(), "0.999999", "0.999999");

    fmt.minSigDigits(6).maxSigDigits(6);

    setup(fmt.get(), "0", "0.00000");
    setup(fmt.get(), "0.0", "0.00000");
    setup(fmt.get(), "1.0", "1.00000");
    setup(fmt.get(), "1.23004", "1.23004");
    setup(fmt.get(), "12345", "12345.0");
    setup(fmt.get(), "0.12345", "0.123450");
    setup(fmt.get(), "3.14159", "3.14159");
    setup(fmt.get(), "0.1", "0.100000");
    setup(fmt.get(), "0.999999", "0.999999");

    fmt.minSigDigits(7).maxSigDigits(7);

    setup(fmt.get(), "0", "0.000000");
    setup(fmt.get(), "0.0", "0.000000");
    setup(fmt.get(), "1.0", "1.000000");
    setup(fmt.get(), "1.23004", "1.230040");
    setup(fmt.get(), "12345", "12345.00");
    setup(fmt.get(), "0.12345", "0.1234500");
    setup(fmt.get(), "3.14159", "3.141590");
    setup(fmt.get(), "0.1", "0.1000000");
    setup(fmt.get(), "0.999999", "0.9999990");
  }

  @Test
  public void testMoreSignificantDigits() {
    Builder fmt = format(SIGNIFICANT).minSigDigits(1).maxSigDigits(3);

    setup(fmt.get(), "0", "0");
    setup(fmt.get(), "0.1", "0.1");
    setup(fmt.get(), "1.0", "1");
    setup(fmt.get(), "12.1", "12.1");
    setup(fmt.get(), "12.01", "12");
    setup(fmt.get(), "0.123", "0.123");
    setup(fmt.get(), "0.1245", "0.124");
    setup(fmt.get(), "0.1256", "0.126");
    setup(fmt.get(), "0.199999", "0.2");
    setup(fmt.get(), "0.111111", "0.111");

    setup(fmt.get(), "12345", "12300");
    setup(fmt.get(), "12356", "12400");
    setup(fmt.get(), "12356.789", "12400");
    setup(fmt.get(), "19999", "20000");

    fmt.minSigDigits(1).maxSigDigits(1);

    setup(fmt.get(), "0", "0");
    setup(fmt.get(), "0.22", "0.2");
    setup(fmt.get(), "0.25", "0.2");
    setup(fmt.get(), "0.26", "0.3");
    setup(fmt.get(), "0.32", "0.3");
    setup(fmt.get(), "0.35", "0.4");
    setup(fmt.get(), "0.36", "0.4");
    setup(fmt.get(), "1", "1");
    setup(fmt.get(), "1.3", "1");
    setup(fmt.get(), "1.5", "2");
    setup(fmt.get(), "1.6", "2");

    fmt.minSigDigits(1).maxSigDigits(2);

    setup(fmt.get(), "0", "0");
    setup(fmt.get(), "0.22", "0.22");
    setup(fmt.get(), "0.25", "0.25");
    setup(fmt.get(), "0.26", "0.26");
    setup(fmt.get(), "0.32", "0.32");
    setup(fmt.get(), "0.35", "0.35");
    setup(fmt.get(), "0.36", "0.36");
    setup(fmt.get(), "1", "1");
    setup(fmt.get(), "1.3", "1.3");
    setup(fmt.get(), "1.5", "1.5");
    setup(fmt.get(), "1.6", "1.6");

    fmt.minSigDigits(2).maxSigDigits(3);

    setup(fmt.get(), "0", "0.0");
    setup(fmt.get(), "0.22", "0.22");
    setup(fmt.get(), "0.25", "0.25");
    setup(fmt.get(), "0.26", "0.26");
    setup(fmt.get(), "0.32", "0.32");
    setup(fmt.get(), "0.35", "0.35");
    setup(fmt.get(), "0.36", "0.36");
    setup(fmt.get(), "1", "1.0");
    setup(fmt.get(), "1.3", "1.3");
    setup(fmt.get(), "1.5", "1.5");
    setup(fmt.get(), "1.6", "1.6");

    fmt.minSigDigits(3).maxSigDigits(3);

    setup(fmt.get(), "0", "0.00");
    setup(fmt.get(), "0.22", "0.220");
    setup(fmt.get(), "0.25", "0.250");
    setup(fmt.get(), "0.26", "0.260");
    setup(fmt.get(), "0.32", "0.320");
    setup(fmt.get(), "0.35", "0.350");
    setup(fmt.get(), "0.36", "0.360");
    setup(fmt.get(), "1", "1.00");
    setup(fmt.get(), "1.3", "1.30");
    setup(fmt.get(), "1.5", "1.50");
    setup(fmt.get(), "1.6", "1.60");
  }

  @Test
  public void testEvenMoreSignificantDigits() {
    Builder fmt = format(SIGNIFICANT).minSigDigits(1).maxSigDigits(3);

    setup(fmt.get(), "123456789123456789", "123000000000000000");
    setup(fmt.get(), "0.123456789", "0.123");
    setup(fmt.get(), "0.000000000000123456789", "0.000000000000123");

    setup(fmt.get(), "0.000", "0");
    setup(fmt.get(), "0.10", "0.1");
    setup(fmt.get(), "0.01", "0.01");
    setup(fmt.get(), "0.001", "0.001");
    setup(fmt.get(), "0.0001", "0.0001");

    fmt.minSigDigits(2);

    setup(fmt.get(), "0.000", "0.0");
    setup(fmt.get(), "0.10", "0.10");
    setup(fmt.get(), "0.01", "0.010");
    setup(fmt.get(), "0.001", "0.0010");
    setup(fmt.get(), "0.0001", "0.00010");

    fmt.minSigDigits(3);

    setup(fmt.get(), "0.000", "0.00");
    setup(fmt.get(), "0.10", "0.100");
    setup(fmt.get(), "0.01", "0.0100");
    setup(fmt.get(), "0.001", "0.00100");
    setup(fmt.get(), "0.0001", "0.000100");
  }

  /**
   * Examples from the CLDR documentation.
   */
  @Test
  public void testExamples() {
    Builder fmt = format(SIGNIFICANT).minSigDigits(3).maxSigDigits(3);

    setup(fmt.get(), "12345", "12300");
    setup(fmt.get(), "0.12345", "0.123");

    fmt.minSigDigits(2).maxSigDigits(4);

    setup(fmt.get(), "3.14159", "3.142");
    setup(fmt.get(), "1.23004", "1.23");
  }

  /**
   * Use significant digits along with maximum fractions. Useful for compact
   * currency formatting.
   */
  @Test
  public void testSignificantMaxFractions() {
    Builder fmt = format(SIGNIFICANT_MAXFRAC).minSigDigits(1).maxSigDigits(50).maxFracDigits(2);

    setup(fmt.get(), "1.00", "1");
    setup(fmt.get(), "1.03", "1.03");
    setup(fmt.get(), "1.3", "1.3");
    setup(fmt.get(), "1.003", "1");
    setup(fmt.get(), "1.005", "1");
    setup(fmt.get(), "1.006", "1.01");
    setup(fmt.get(), "144.756", "144.76");
    setup(fmt.get(), "144.00756", "144.01");
    setup(fmt.get(), "144.003", "144");
  }

  /**
   * Ensures the alternate decimal and group characters are used in currency mode,
   * when the locale supports them.
   */
  @Test
  public void testCurrency() {
    Builder fmt = format(DEFAULT)
        .grouping(true)
        .currency(true)
        .currencyDecimal("^")
        .currencyGroup(" ")
        .minFracDigits(2)
        .secondaryGroupingSize(2);

    format(fmt.get(), "1234567890.126", "1 23 45 67 890^13");
  }

  @Test
  public void testIntegers() {
    Builder fmt = format(DEFAULT).minIntDigits(3);

    format(fmt.get(), "0", "000");
    format(fmt.get(), "1", "001");
    format(fmt.get(), "12345", "12345");
    format(fmt.get(), "12.6", "012.6");

    fmt.minIntDigits(1).maxFracDigits(0);

    format(fmt.get(), "1", "1");
    format(fmt.get(), "1.1", "1");
    format(fmt.get(), "1.5", "2");
    format(fmt.get(), "0", "0");
    format(fmt.get(), "0.1", "0");
    format(fmt.get(), "0.5", "0");
    format(fmt.get(), "0.6", "1");
    format(fmt.get(), "1.4", "1");
    format(fmt.get(), "1.5", "2");
    format(fmt.get(), "0.0001", "0");

    fmt.minFracDigits(1).maxFracDigits(3);

    format(fmt.get(), "1.20000", "1.2");
    format(fmt.get(), "0.0020000", "0.002");

    fmt.minFracDigits(3);

    format(fmt.get(), "1.20000", "1.200");
    format(fmt.get(), "0.0020000", "0.002");

    fmt.minIntDigits(0);

    format(fmt.get(), "0.002", ".002");
    format(fmt.get(), "0.556", ".556");
    format(fmt.get(), "0.66", ".660");
    format(fmt.get(), "1.234", "1.234");
    format(fmt.get(), "12345", "12345.000");

    fmt.minFracDigits(0).maxFracDigits(1);

    format(fmt.get(), "0.94", ".9");
    format(fmt.get(), "0.95", "1");
    format(fmt.get(), "0.99", "1");
  }

  @Test
  public void testFractions() {
    Builder fmt = format(DEFAULT).maxFracDigits(3).minFracDigits(2);

    setup(fmt.get(), "12.0", "12.00");
    setup(fmt.get(), "12.0012", "12.001");
    setup(fmt.get(), "12.00001", "12.00");

    setup(fmt.get(), "0.0", "0.00");

    // NOTE: indicators for negative numbers are handled in a separate formatter.
    // Here we only format the absolute value.
    setup(fmt.get(), "-1.00", "1.00");

    fmt.minIntDigits(1).maxFracDigits(1).minFracDigits(1);

    setup(fmt.get(), "1", "1.0");
    setup(fmt.get(), "1.1", "1.1");
    setup(fmt.get(), "1.5", "1.5");
    setup(fmt.get(), "0", "0.0");
    setup(fmt.get(), "0.1", "0.1");
    setup(fmt.get(), "0.5", "0.5");
    setup(fmt.get(), "0.6", "0.6");
    setup(fmt.get(), "1.4", "1.4");
    setup(fmt.get(), "1.5", "1.5");
    setup(fmt.get(), "0.0001", "0.0");
  }

  /**
   * Tests that significant digits mode is only activated when a valid
   * minimum and maximum are both set.
   */
  @Test
  public void testInvalidSignificantDigits() {
    Builder fmt = format(DEFAULT)
        .minFracDigits(2)
        .minSigDigits(1)
        .maxSigDigits(-1);

    setup(fmt.get(), "12345.0", "12345.00");

    fmt.maxSigDigits(2).minSigDigits(0);

    setup(fmt.get(), "12345.0", "12345.00");
  }


  @Test
  public void testRoundingModes() {
    Builder fmt = format(DEFAULT).roundMode(ROUND);

    setup(fmt.get(), "12.032", "12.03");
    setup(fmt.get(), "12.035", "12.04");
    setup(fmt.get(), "12.045", "12.04");
    setup(fmt.get(), "12.055", "12.06");

    setup(fmt.get(), "-12.032", "12.03");
    setup(fmt.get(), "-12.035", "12.04");
    setup(fmt.get(), "-12.045", "12.04");
    setup(fmt.get(), "-12.055", "12.06");

    fmt.roundMode(TRUNCATE);

    setup(fmt.get(), "12.032", "12.03");
    setup(fmt.get(), "12.035", "12.03");
    setup(fmt.get(), "12.045", "12.04");
    setup(fmt.get(), "12.055", "12.05");

    setup(fmt.get(), "-12.032", "12.03");
    setup(fmt.get(), "-12.035", "12.03");
    setup(fmt.get(), "-12.045", "12.04");
    setup(fmt.get(), "-12.055", "12.05");

    fmt.roundMode(FLOOR);

    setup(fmt.get(), "12.032", "12.03");
    setup(fmt.get(), "12.035", "12.03");
    setup(fmt.get(), "12.045", "12.04");
    setup(fmt.get(), "12.055", "12.05");

    setup(fmt.get(), "-12.032", "12.04");
    setup(fmt.get(), "-12.035", "12.04");
    setup(fmt.get(), "-12.045", "12.05");
    setup(fmt.get(), "-12.055", "12.06");

    fmt.roundMode(CEIL);

    setup(fmt.get(), "12.032", "12.04");
    setup(fmt.get(), "12.035", "12.04");
    setup(fmt.get(), "12.045", "12.05");
    setup(fmt.get(), "12.055", "12.06");

    setup(fmt.get(), "-12.032", "12.03");
    setup(fmt.get(), "-12.035", "12.03");
    setup(fmt.get(), "-12.045", "12.04");
    setup(fmt.get(), "-12.055", "12.05");

    // Same as ROUND
    fmt.roundMode(null);

    setup(fmt.get(), "12.032", "12.03");
  }

  @Test
  public void testGrouping() {
    Builder fmt = format(DEFAULT).grouping(true);

    format(fmt.get(), "1234567", "1,234,567");
    format(fmt.get(), "11111111111111.123", "11,111,111,111,111.12");

    fmt.minimumGroupingDigits(2);

    format(fmt.get(), "1234", "1234");
    format(fmt.get(), "12345", "12,345");
    format(fmt.get(), "1234567", "1,234,567");

    fmt.primaryGroupingSize(4).secondaryGroupingSize(2);

    format(fmt.get(), "1234567", "1,23,4567");
    format(fmt.get(), "11111111111111.123", "11,11,11,11,11,1111.12");

    fmt.minIntDigits(12);

    format(fmt.get(), "123", "00,00,00,00,0123");

    fmt.primaryGroupingSize(3).secondaryGroupingSize(3);

    format(fmt.get(), "123", "000,000,000,123");
  }

  private static BigDecimal num(String num, int significant) {
    BigDecimal n = new BigDecimal(num);
    int scale = significant - n.precision() + n.scale();
    return n.setScale(scale, RoundingMode.HALF_EVEN);
  }

  private static void intDigits(BigDecimal n, int expected) {
    int actual = NumberFormattingUtils.integerDigits(n);
    assertEquals(actual, expected);
  }

  private static void format(Fmt call, String number, String expected) {
    BigDecimal n = new BigDecimal(number);
    String result = call.format(n);
    assertEquals(result, expected);
  }

  private static void setup(Fmt call, String number, String expected) {
    String result = call.setup(new BigDecimal(number));
    assertEquals(result, expected);
  }

  private static Builder format(NumberFormatMode formatMode) {
    return new Builder(formatMode);
  }

  /**
   * Compact setup of arguments for low-level formatting calls.
   */
  private static class Builder {

    private NumberRoundMode roundMode = ROUND;
    private NumberFormatMode formatMode;
    private boolean grouping;
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

    Builder(NumberFormatMode formatMode) {
      this.formatMode = formatMode;
    }

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

      return new Fmt() {
        @Override
        public String setup(BigDecimal n) {
          n = NumberFormattingUtils.setup(
              n,
              roundMode,
              formatMode,
              minIntDigits,
              maxFracDigits,
              minFracDigits,
              maxSigDigits,
              minSigDigits);

          if (n.signum() == -1) {
            n = n.negate();
          }
          return n.toPlainString();
        }

        @Override
        public String format(BigDecimal n) {
          DigitBuffer buf = new DigitBuffer();
          n = NumberFormattingUtils.setup(
              n,
              roundMode,
              formatMode,
              minIntDigits,
              maxFracDigits,
              minFracDigits,
              maxSigDigits,
              minSigDigits);

          NumberFormattingUtils.format(
              n,
              buf,
              params,
              currency,
              grouping,
              minIntDigits,
              primaryGroupingSize,
              secondaryGroupingSize);

          return buf.toString();
        }

      };
    }
  }

  interface Fmt {
    String format(BigDecimal n);
    String setup(BigDecimal n);
  }
}
