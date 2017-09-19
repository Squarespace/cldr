package com.squarespace.cldr;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import org.testng.annotations.Test;

import com.squarespace.cldr.CLDR.Currency;


public class CurrencyRoundTripTest {

  @Test
  public void testCurrency() {
    for (Currency currency : Currency.values()) {
      String name = currency.name();
      Currency other = Currency.fromString(name);
      assertEquals(other, currency);
      assertNotEquals(currency, Currency.fromString(name.toLowerCase()));
      assertEquals(null, Currency.fromString(name.toLowerCase()));
    }
    
    assertEquals(null, Currency.fromString(null));
  }
}
