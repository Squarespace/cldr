package com.squarespace.cldr.numbers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.testng.Assert;

import com.squarespace.cldr.CLDR;
import com.squarespace.cldr.units.Unit;
import com.squarespace.cldr.units.UnitFormat;
import com.squarespace.cldr.units.UnitFormatOptions;
import com.squarespace.cldr.units.UnitValue;


public abstract class NumberFormatterTestBase {
  
  protected List<Pair> numbers(String ...numbers) {
    return Arrays.stream(numbers).map(s -> new Pair(s, "-" + s)).collect(Collectors.toList());
  }
  
  protected Pair pair(String positive, String negative) {
    return new Pair(positive, negative);
  }
  
  protected List<Pair> pairs(Pair ...results) {
    return Arrays.asList(results);
  }
  
  protected DecimalFormatOptions decimal() {
    return new DecimalFormatOptions();
  }
  
  protected DecimalFormatOptions decimal(DecimalFormatStyle style) {
    return new DecimalFormatOptions(style);
  }

  protected UnitFormatOptions unit() {
    return new UnitFormatOptions();
  }
  
  protected UnitFormatOptions unit(UnitFormat format) {
    return new UnitFormatOptions(format);
  }
  
  protected CurrencyFormatOptions currency() {
    return new CurrencyFormatOptions();
  }
  
  protected CurrencyFormatOptions currency(CurrencyFormatStyle style) {
    return new CurrencyFormatOptions(style);
  }

  protected void test(CLDR.Locale locale, UnitFormatOptions options, String n, Unit u, String expected) {
    NumberFormatter fmt = CLDR.get().getNumberFormatter(locale);
    StringBuilder buf = new StringBuilder();
    UnitValue value = new UnitValue(new BigDecimal(n), u);
    fmt.formatUnit(value, buf, options);
    Assert.assertEquals(buf.toString(), expected);
  }
  
  protected void test(CLDR.Locale locale, UnitFormatOptions options, List<UnitValue> values, String expected) {
    NumberFormatter fmt = CLDR.get().getNumberFormatter(locale);
    StringBuilder buf = new StringBuilder();
    fmt.formatUnits(values, buf, options);
    Assert.assertEquals(buf.toString(), expected);
  }
  
  protected void test(CLDR.Locale locale, DecimalFormatOptions options, List<Pair> numbers, List<Pair> expecteds) {
    test(locale, null, options, numbers, expecteds);
  }

  protected void test(
      CLDR.Locale locale, 
      CLDR.Currency currency, 
      NumberFormatOptions<?> options, 
      List<Pair> numbers, 
      List<Pair> expecteds) {
    
    NumberFormatter fmt = CLDR.get().getNumberFormatter(locale);
    StringBuilder buf = new StringBuilder();

    int size = numbers.size();

    if (size != expecteds.size()) {
      Assert.fail("input and expected sizes are not equal: " + size + " != " + expecteds.size());
    }

    for (int i = 0; i < size; i++) {
      Pair num = numbers.get(i);

      BigDecimal n = new BigDecimal(num.positive);
      buf.setLength(0);
      if (options instanceof DecimalFormatOptions) {
        fmt.formatDecimal(n, buf, (DecimalFormatOptions) options);
      } else {
        fmt.formatCurrency(n, currency, buf, (CurrencyFormatOptions) options);
      }
      String positive = buf.toString();
      
      n = new BigDecimal(num.negative);
      buf.setLength(0);
      if (options instanceof DecimalFormatOptions) {
        fmt.formatDecimal(n, buf, (DecimalFormatOptions) options);
      } else {
        fmt.formatCurrency(n, currency, buf, (CurrencyFormatOptions) options);
      }
      String negative = buf.toString();
      
      Pair expected = expecteds.get(i);
      Assert.assertEquals(positive, expected.positive,
          "case " + i + " for input \"" + num.positive + "\" failed.");
      Assert.assertEquals(negative, expected.negative,
          "case " + i + " for input \"" + num.negative + "\" failed.");
    }
  }
  
  
  protected static class Pair {

    private final String positive;
    private final String negative;
    
    public Pair(String positive, String negative) {
      this.positive = positive;
      this.negative = negative;
    }

    public String positive() {
      return positive;
    }
    
    public String negative() {
      return negative;
    }

  }
}
