package com.squarespace.cldr.numbers;

import static com.squarespace.cldr.numbers.DecimalFormatStyle.LONG;
import static com.squarespace.cldr.numbers.NumberFormatMode.SIGNIFICANT;

import java.util.List;

import org.testng.annotations.Test;

import com.squarespace.cldr.CLDR;


public class NumberFormatterDecimalTest_pl extends NumberFormatterTestBase {

  @Test
  public void testLongDefault() {
    // In Polish the large number indicators "thousand", "million", etc change
    // based on the plural category of the formatted, visible number.
    List<Pair> numbers = numbers("999.9", "999.99", "1000", "2000", "6000", "9900", "9999");
    DecimalFormatOptions options = decimal(LONG).setFormatMode(SIGNIFICANT);
    test(CLDR.Locale.pl, options, numbers, pairs(
        pair("999,9", "-999,9"),
        pair("1 tysiąc", "-1 tysiąc"),
        pair("1 tysiąc", "-1 tysiąc"),
        pair("2 tysiące", "-2 tysiące"),
        pair("6 tysięcy", "-6 tysięcy"),
        pair("9,9 tysiąca", "-9,9 tysiąca"),
        pair("10 tysięcy", "-10 tysięcy")
    ));
  }
  
}
