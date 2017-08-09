package com.squarespace.cldr.numbers;

import static com.squarespace.cldr.numbers.DecimalFormatStyle.LONG;
import static com.squarespace.cldr.numbers.DecimalFormatStyle.PERCENT;
import static com.squarespace.cldr.numbers.DecimalFormatStyle.PERMILLE;
import static com.squarespace.cldr.numbers.DecimalFormatStyle.SHORT;

import java.util.List;

import org.testng.annotations.Test;

import com.squarespace.cldr.CLDR;


public class NumberFormatterDecimalTest_en_US extends NumberFormatterBaseTest {

  private final List<Pair> NUMBERS = numbers(
      "0"
      , "1"
      , "1.00"
      , "3.14159"
      , "990"
      , "1200"
      , "12345"
      , "99999"
      , "999999"
      , "1200000"
      , "10000000001"
      , "9223372036854775807"
  );

  @Test
  public void testDecimalDefault() {
    DecimalFormatOptions options = decimal().setGrouping(true);
    test(CLDR.Locale.en_US, options, NUMBERS, pairs(
        pair("0", "0"),
        pair("1", "-1"),
        pair("1", "-1"),
        pair("3.142", "-3.142"),
        pair("990", "-990"),
        pair("1,200", "-1,200"),
        pair("12,345", "-12,345"),
        pair("99,999", "-99,999"),
        pair("999,999", "-999,999"),
        pair("1,200,000", "-1,200,000"),
        pair("10,000,000,001", "-10,000,000,001"),
        pair("9,223,372,036,854,775,807", "-9,223,372,036,854,775,807")
    ));
  }
  
  @Test
  public void testPercentDefault() {
    DecimalFormatOptions options = decimal(PERCENT).setGrouping(true);
    test(CLDR.Locale.en_US, options, NUMBERS, pairs(
        pair("0%", "0%"),
        pair("100%", "-100%"),
        pair("100%", "-100%"),
        pair("314%", "-314%"),
        pair("99,000%", "-99,000%"),
        pair("120,000%", "-120,000%"),
        pair("1,234,500%", "-1,234,500%"),
        pair("9,999,900%", "-9,999,900%"),
        pair("99,999,900%", "-99,999,900%"),
        pair("120,000,000%", "-120,000,000%"),
        pair("1,000,000,000,100%", "-1,000,000,000,100%"),
        pair("922,337,203,685,477,580,700%", "-922,337,203,685,477,580,700%")
    ));
  }
  
  @Test
  public void testPermilleDefault() {
    DecimalFormatOptions options = decimal(PERMILLE).setGrouping(true);
    test(CLDR.Locale.en_US, options, NUMBERS, pairs(
        pair("0‰", "0‰"),
        pair("1,000‰", "-1,000‰"),
        pair("1,000‰", "-1,000‰"),
        pair("3,142‰", "-3,142‰"),
        pair("990,000‰", "-990,000‰"),
        pair("1,200,000‰", "-1,200,000‰"),
        pair("12,345,000‰", "-12,345,000‰"),
        pair("99,999,000‰", "-99,999,000‰"),
        pair("999,999,000‰", "-999,999,000‰"),
        pair("1,200,000,000‰", "-1,200,000,000‰"),
        pair("10,000,000,001,000‰", "-10,000,000,001,000‰"),
        pair("9,223,372,036,854,775,807,000‰", "-9,223,372,036,854,775,807,000‰")
    ));
  }
  
  @Test
  public void testShortDefault() {
    DecimalFormatOptions options = decimal(SHORT).setGrouping(true);
    test(CLDR.Locale.en_US, options, NUMBERS, pairs(
        pair("0", "0"),
        pair("1", "-1"),
        pair("1", "-1"),
        pair("3.1", "-3.1"),
        pair("990", "-990"),
        pair("1.2K", "-1.2K"),
        pair("12.3K", "-12.3K"),
        pair("100K", "-100K"),
        pair("1M", "-1M"),
        pair("1.2M", "-1.2M"),
        pair("10B", "-10B"),
        pair("9,223,372T", "-9,223,372T")
    ));
  }

  @Test
  public void testLongDefault() {
    DecimalFormatOptions options = decimal(LONG).setGrouping(true);
    test(CLDR.Locale.en_US, options, NUMBERS, pairs(
        pair("0", "0"),
        pair("1", "-1"),
        pair("1", "-1"),
        pair("3.1", "-3.1"),
        pair("990", "-990"),
        pair("1.2 thousand", "-1.2 thousand"),
        pair("12.3 thousand", "-12.3 thousand"),
        pair("100 thousand", "-100 thousand"),
        pair("1 million", "-1 million"),
        pair("1.2 million", "-1.2 million"),
        pair("10 billion", "-10 billion"),
        pair("9,223,372 trillion", "-9,223,372 trillion")
    ));
  }

}
