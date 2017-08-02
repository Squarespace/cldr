package com.squarespace.cldr.examples;

import java.math.BigDecimal;

import com.squarespace.cldr.CLDR;
import com.squarespace.cldr.numbers.CurrencyFormatOptions;
import com.squarespace.cldr.numbers.CurrencyFormatStyle;
import com.squarespace.cldr.numbers.DecimalFormatOptions;
import com.squarespace.cldr.numbers.DecimalFormatStyle;
import com.squarespace.cldr.numbers.NumberFormatMode;
import com.squarespace.cldr.numbers.NumberFormatter;

public class NumberFormatterDemo2 {

  public static void main(String[] args) {
    BigDecimal[] numbers = new BigDecimal[] {
        new BigDecimal("0"),
        new BigDecimal("1.0"),
        new BigDecimal("990"),
        new BigDecimal("1200"),
        new BigDecimal("999990"),
        new BigDecimal("999999"),
        new BigDecimal("1000001")
    };
    
    DecimalFormatOptions[] dopts = new DecimalFormatOptions[] {
      new DecimalFormatOptions(DecimalFormatStyle.SHORT)
        .setGrouping(true).setFormatMode(NumberFormatMode.DEFAULT)
        .setMinimumFractionDigits(0)
        .setMaximumFractionDigits(2),
        
      new DecimalFormatOptions(DecimalFormatStyle.LONG)
        .setFormatMode(NumberFormatMode.SIGNIFICANT)
        .setMinimumSignificantDigits(3)
        .setMaximumSignificantDigits(5)
    };

    for (DecimalFormatOptions o : dopts) {
      NumberFormatter fmt = CLDR.get().getNumberFormatter(CLDR.EN_US);
      for (BigDecimal n : numbers) {
        StringBuilder buf = new StringBuilder();
        fmt.formatDecimal(n, buf, o);
        System.out.println(buf.toString());
      }
      System.out.println();
    }
    
    System.out.println("-----------------------------");
    CurrencyFormatOptions[] copts = new CurrencyFormatOptions[] {
        new CurrencyFormatOptions(CurrencyFormatStyle.NAME),

        new CurrencyFormatOptions(CurrencyFormatStyle.NAME)
          .setFormatMode(NumberFormatMode.SIGNIFICANT_MAXFRAC)
          .setMinimumSignificantDigits(1)
    };
      
    for (CurrencyFormatOptions o : copts) {
      NumberFormatter fmt = CLDR.get().getNumberFormatter(CLDR.EN_US);
      for (BigDecimal n : numbers) {
        StringBuilder buf = new StringBuilder();
        fmt.formatCurrency(n, "USD", buf, o);
        System.out.println(buf.toString());
      }
      System.out.println();
    }
  }
  
}
