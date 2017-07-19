package com.squarespace.cldr.numbers;

import static java.lang.Integer.valueOf;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;


/**
 * TODO: incomplete
 */
public class NumberFormatOptionsTest {

  @Test
  public void testDecimal() {
    DecimalFormatOptions opts = new DecimalFormatOptions(NumberFormatStyle.LONG);
    opts.setMaximumFractionDigits(1)
        .setMinimumFractionDigits(2)
        .setGrouping(true)
        .setRoundMode(NumberRoundMode.FLOOR);

    assertEquals(opts.style(), NumberFormatStyle.LONG);

    NumberFormatOptions<?> base = (NumberFormatOptions<DecimalFormatOptions>) opts;
    assertEquals(base.maximumFractionDigits(), valueOf(1));
    assertEquals(base.minimumFractionDigits(), valueOf(2));
    assertEquals(base.minimumIntegerDigits(), null);
    assertEquals(base.grouping(), true);
    assertEquals(base.roundMode(), NumberRoundMode.FLOOR);

    opts.setMinimumIntegerDigits(3);
    assertEquals(base.minimumIntegerDigits(), valueOf(3));
  }

  @Test
  public void testCurrency() {
    CurrencyFormatOptions opts = new CurrencyFormatOptions(CurrencyFormatStyle.CODE);
    opts.setMaximumFractionDigits(1)
        .setMinimumIntegerDigits(2)
        .setGrouping(true);
    assertEquals(opts.style(), CurrencyFormatStyle.CODE);

    NumberFormatOptions<?> base = (NumberFormatOptions<CurrencyFormatOptions>) opts;
    assertEquals(base.maximumFractionDigits(), valueOf(1));
    assertEquals(base.minimumFractionDigits(), valueOf(1));
    assertEquals(base.minimumIntegerDigits(), valueOf(2));

    opts.setMinimumFractionDigits(3);
    assertEquals(base.minimumFractionDigits(), valueOf(3));
  }

}
