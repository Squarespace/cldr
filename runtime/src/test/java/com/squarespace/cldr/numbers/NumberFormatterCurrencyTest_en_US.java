package com.squarespace.cldr.numbers;

import static com.squarespace.cldr.numbers.CurrencyFormatStyle.ACCOUNTING;
import static com.squarespace.cldr.numbers.CurrencyFormatStyle.CODE;
import static com.squarespace.cldr.numbers.CurrencyFormatStyle.NAME;
import static com.squarespace.cldr.numbers.CurrencyFormatStyle.SHORT;
import static com.squarespace.cldr.numbers.CurrencyFormatStyle.SYMBOL;

import java.util.List;

import org.testng.annotations.Test;

import com.squarespace.cldr.CLDR;


public class NumberFormatterCurrencyTest_en_US extends NumberFormatterBaseTest {

  private final List<Pair> NUMBERS = numbers(
      "0"
      , "1"
      , "1.00"
      , "3.59"
      , "1200"
      , "12345"
      , "99999"
      , "999999"
      , "1200000"
      , "10000000001"
  );

  @Test
  public void testSymbolDefault() {
    CurrencyFormatOptions options = currency(SYMBOL).setGrouping(true);
    test(CLDR.Locale.en_US, CLDR.Currency.USD, options, NUMBERS, pairs(
        pair("$0.00", "$0.00"),
        pair("$1.00", "-$1.00"),
        pair("$1.00", "-$1.00"),
        pair("$3.59", "-$3.59"),
        pair("$1,200.00", "-$1,200.00"),
        pair("$12,345.00", "-$12,345.00"),
        pair("$99,999.00", "-$99,999.00"),
        pair("$999,999.00", "-$999,999.00"),
        pair("$1,200,000.00", "-$1,200,000.00"),
        pair("$10,000,000,001.00", "-$10,000,000,001.00")
    ));
  }
  
  @Test
  public void testAccountingDefault() {
    CurrencyFormatOptions options = currency(ACCOUNTING).setGrouping(true);
    test(CLDR.Locale.en_US, CLDR.Currency.USD, options, NUMBERS, pairs(
        pair("$0.00", "$0.00"),
        pair("$1.00", "($1.00)"),
        pair("$1.00", "($1.00)"),
        pair("$3.59", "($3.59)"),
        pair("$1,200.00", "($1,200.00)"),
        pair("$12,345.00", "($12,345.00)"),
        pair("$99,999.00", "($99,999.00)"),
        pair("$999,999.00", "($999,999.00)"),
        pair("$1,200,000.00", "($1,200,000.00)"),
        pair("$10,000,000,001.00", "($10,000,000,001.00)")
    ));
  }
  
  @Test
  public void testNameDefault() {
    CurrencyFormatOptions options = currency(NAME).setGrouping(true);
    test(CLDR.Locale.en_US, CLDR.Currency.USD, options, NUMBERS, pairs(
        pair("0.00 US dollars", "0.00 US dollars"),
        pair("1.00 US dollars", "-1.00 US dollars"),
        pair("1.00 US dollars", "-1.00 US dollars"),
        pair("3.59 US dollars", "-3.59 US dollars"),
        pair("1,200.00 US dollars", "-1,200.00 US dollars"),
        pair("12,345.00 US dollars", "-12,345.00 US dollars"),
        pair("99,999.00 US dollars", "-99,999.00 US dollars"),
        pair("999,999.00 US dollars", "-999,999.00 US dollars"),
        pair("1,200,000.00 US dollars", "-1,200,000.00 US dollars"),
        pair("10,000,000,001.00 US dollars", "-10,000,000,001.00 US dollars")
    ));
  }
  
  @Test
  public void testCodeDefault() {
    CurrencyFormatOptions options = currency(CODE).setGrouping(true);
    test(CLDR.Locale.en_US, CLDR.Currency.USD, options, NUMBERS, pairs(
        pair("0.00 USD", "0.00 USD"),
        pair("1.00 USD", "-1.00 USD"),
        pair("1.00 USD", "-1.00 USD"),
        pair("3.59 USD", "-3.59 USD"),
        pair("1,200.00 USD", "-1,200.00 USD"),
        pair("12,345.00 USD", "-12,345.00 USD"),
        pair("99,999.00 USD", "-99,999.00 USD"),
        pair("999,999.00 USD", "-999,999.00 USD"),
        pair("1,200,000.00 USD", "-1,200,000.00 USD"),
        pair("10,000,000,001.00 USD", "-10,000,000,001.00 USD")
    ));
  }
  
  @Test
  public void testShortDefault() {
    CurrencyFormatOptions options = currency(SHORT).setGrouping(true);
    test(CLDR.Locale.en_US, CLDR.Currency.USD, options, NUMBERS, pairs(
        pair("$0", "$0"),
        pair("$1", "-$1"),
        pair("$1", "-$1"),
        pair("$4", "-$4"),
        pair("$1K", "-$1K"),
        pair("$12K", "-$12K"),
        pair("$100K", "-$100K"),
        pair("$1M", "-$1M"),
        pair("$1M", "-$1M"),
        pair("$10B", "-$10B")
    ));
  }

  @Test
  public void testShortSignificant() {
    CurrencyFormatOptions options = currency(SHORT)
        .setFormatMode(NumberFormatMode.SIGNIFICANT_MAXFRAC)
        .setMaximumFractionDigits(1)
        .setGrouping(true);
       
    test(CLDR.Locale.en_US, CLDR.Currency.USD, options, NUMBERS, pairs(
        pair("$0", "$0"),
        pair("$1", "-$1"),
        pair("$1", "-$1"),
        pair("$3.6", "-$3.6"),
        pair("$1.2K", "-$1.2K"),
        pair("$12.3K", "-$12.3K"),
        pair("$100K", "-$100K"),
        pair("$1M", "-$1M"),
        pair("$1.2M", "-$1.2M"),
        pair("$10B", "-$10B")
    ));
    
    options.setMaximumFractionDigits(2).setMaximumSignificantDigits(5);

    test(CLDR.Locale.en_US, CLDR.Currency.USD, options, NUMBERS, pairs(
        pair("$0", "$0"),
        pair("$1", "-$1"),
        pair("$1", "-$1"),
        pair("$3.59", "-$3.59"),
        pair("$1.2K", "-$1.2K"),
        pair("$12.34K", "-$12.34K"),
        pair("$100K", "-$100K"),
        pair("$1M", "-$1M"),
        pair("$1.2M", "-$1.2M"),
        pair("$10B", "-$10B")
    ));
  }
  
  
  @Test
  public void testNarrowCurrencySymbols() {
    CurrencyFormatOptions options = currency(SYMBOL)
        .setFormatMode(NumberFormatMode.SIGNIFICANT_MAXFRAC)
        .setSymbolWidth(CurrencySymbolWidth.NARROW)
        .setMaximumFractionDigits(1)
        .setGrouping(true);
    
    test(CLDR.Locale.en_US, CLDR.Currency.SEK, options, numbers("3.59", "1200"), pairs(
        pair("kr\u00a03.6", "-kr\u00a03.6"),
        pair("kr\u00a01,200.0", "-kr\u00a01,200.0")
    ));
  }
  
  @Test
  public void testCurrencies() {
    CurrencyFormatOptions options = currency(SYMBOL)
        .setSymbolWidth(CurrencySymbolWidth.NARROW);
    test(CLDR.Locale.en_US, CLDR.Currency.JPY, options, numbers("1234.56"), pairs(
        pair("¥1,235", "-¥1,235")
    ));
    test(CLDR.Locale.en_US, CLDR.Currency.SEK, options, numbers("1234.56"), pairs(
        pair("kr\u00a01,234.56", "-kr\u00a01,234.56")
    ));
  }
}
