package com.squarespace.cldr.numbers;

import java.math.BigDecimal;

import com.squarespace.cldr.CLDR;
import com.squarespace.cldr.CLDRLocale;

/**
 * Demonstration of number / currency formatting.
 * 
 * TODO: Convert into unit test and remove.
 */
public class NumberDemo {

  public static void main(String[] args) {
    demo();
  }
  
  private static void demo() {
    String[] numbers = new String[] {
        "0",
        "1",
        "1.00",
        "3.59",
        "-7.53",
        "1200",
        "-15789.12",
        "99999",
        "-100200300.40",
        "10000000001"
    };
    
    CLDRLocale[] locales = new CLDRLocale[] { 
        CLDR.EN_US,
        CLDR.ES,
        CLDR.FR,
        CLDR.DE,
        CLDR.ZH 
    };

    System.out.println("DECIMAL NUMBER FORMATTING\n");
    DecimalFormatOptions[] decimalOpts = new DecimalFormatOptions[] {
      options(NumberFormatStyle.DECIMAL)
        .setGrouping(true),
      options(NumberFormatStyle.PERCENT)
        .setGrouping(true),
      options(NumberFormatStyle.PERMILLE)
        .setGrouping(true),
      options(NumberFormatStyle.LONG)
        .setFormatMode(NumberFormatMode.SIGNIFICANT),
      options(NumberFormatStyle.SHORT)
        .setFormatMode(NumberFormatMode.SIGNIFICANT)
    };

    for (CLDRLocale locale : locales) {
      for (DecimalFormatOptions opts : decimalOpts) {
        System.out.println("Locale \"" + locale + "\" in " + opts.style() + " style\n");
        decimal(locale, numbers, opts);
        System.out.println();
      }
      System.out.println();
    }

   
    System.out.println("\n\nCURRENCY FORMATTING\n");
    CurrencyFormatOptions[] currencyOpts = new CurrencyFormatOptions[] {
        options(CurrencyFormatStyle.SYMBOL)
          .setGrouping(true),
        options(CurrencyFormatStyle.ACCOUNTING)
          .setGrouping(true),
        options(CurrencyFormatStyle.CODE)
          .setGrouping(true),
        options(CurrencyFormatStyle.NAME)
          .setFormatMode(NumberFormatMode.SIGNIFICANT_MAXFRAC)
          .setMinimumSignificantDigits(1),
        options(CurrencyFormatStyle.SHORT)
          .setFormatMode(NumberFormatMode.SIGNIFICANT_MAXFRAC),
      };
    
    String[] currencies = new String[] {
      "USD",
      "GBP",
      "EUR",
      "JPY",
      "RUB"
    };
    
    for (CLDRLocale locale : locales) {
      for (CurrencyFormatOptions opts : currencyOpts) {
        System.out.println("Locale \"" + locale + "\" in " + opts.style() + " style\n");
        money(locale, currencies, numbers, opts);
        System.out.println();
      }
    }
  }
  
  /**
   * Format decimal numbers in this locale.
   */
  private static void decimal(CLDRLocale locale, String[] numbers, DecimalFormatOptions opts) {
    for (String num : numbers) {
      BigDecimal n = new BigDecimal(num);
      StringBuilder buf = new StringBuilder("  ");
      NumberFormatter fmt = CLDR.get().getNumberFormatter(locale);
      fmt.formatDecimal(n, buf, opts);
      System.out.println(buf.toString());
    }
  }

  /**
   * Format numbers in this locale for several currencies.
   */
  private static void money(CLDRLocale locale, String[] currencies, String[] numbers, CurrencyFormatOptions opts) {
    for (String currency : currencies) {
      System.out.println("Currency " + currency);
      for (String num : numbers) {
        BigDecimal n = new BigDecimal(num);
        NumberFormatter fmt = CLDR.get().getNumberFormatter(locale);
        StringBuilder buf = new StringBuilder("  ");
        fmt.formatCurrency(n, currency, buf, opts);
        System.out.println(buf.toString());
      }
      System.out.println();
    }
    System.out.println();
  }
  
  private static DecimalFormatOptions options(NumberFormatStyle style) {
    return new DecimalFormatOptions(style);
  }
  
  private static CurrencyFormatOptions options(CurrencyFormatStyle style) {
    return new CurrencyFormatOptions(style);
  }
 
}
