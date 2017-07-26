package com.squarespace.cldr.numbers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.squarespace.cldr.CLDR;
import com.squarespace.cldr.CLDRLocale;


public class NumberFormatterStress {

  public static void main(String[] args) throws IOException {

    String[] numbers = new String[] {
        "0",
        "1",
        "1.0",
        "-1.224",
        "3.14159",
        "1200",
        "12345",
        "999999",
        "100000000001",
        "0.99222222111233333"
    };

    DecimalFormatOptions[] dopts = new DecimalFormatOptions[] {
        new DecimalFormatOptions(DecimalFormatStyle.DECIMAL),
        new DecimalFormatOptions(DecimalFormatStyle.PERCENT),
        new DecimalFormatOptions(DecimalFormatStyle.PERMILLE),
        new DecimalFormatOptions(DecimalFormatStyle.SHORT),
        new DecimalFormatOptions(DecimalFormatStyle.LONG),
    };

    CurrencyFormatOptions[] copts = new CurrencyFormatOptions[] {
        new CurrencyFormatOptions(CurrencyFormatStyle.SYMBOL),
        new CurrencyFormatOptions(CurrencyFormatStyle.ACCOUNTING),
        new CurrencyFormatOptions(CurrencyFormatStyle.NAME),
        new CurrencyFormatOptions(CurrencyFormatStyle.CODE),
        new CurrencyFormatOptions(CurrencyFormatStyle.SHORT),
    };

    int iterations = 1000000;
    CLDR cldr = CLDR.get();

    CLDRLocale locale = CLDR.EN_US;
    String currency = CLDR.Currency.USD;

    List<BigDecimal> nums = new ArrayList<>();
    for (String num : numbers) {
      nums.add(new BigDecimal(num));
    }
    StringBuilder buf = new StringBuilder();

    pause();
    for (int i = 0; i < iterations; i++) {
      NumberFormatter fmt = cldr.getNumberFormatter(locale);

      for (int j = 0; j < nums.size(); j++) {
        BigDecimal n = nums.get(j);
        for (DecimalFormatOptions d : dopts) {
          buf.setLength(0);
          fmt.formatDecimal(n, buf, d);
        }

        for (CurrencyFormatOptions c : copts) {
          buf.setLength(0);
          fmt.formatCurrency(n, currency, buf, c);
        }
      }
      
      if (i % 10000 == 0) {
        System.out.println(i);
      }
    }
    pause();
  }

  private static void pause() throws IOException {
    System.out.println("hit a key.");
    System.in.read();
  }
  
}
