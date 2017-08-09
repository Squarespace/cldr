package com.squarespace.cldr.plurals;

import org.testng.Assert;

import com.squarespace.cldr.CLDR;
import com.squarespace.cldr.PluralRules;
import com.squarespace.cldr.numbers.NumberOperands;

public class PluralBaseTest {

  private static final PluralRules PLURAL_RULES = CLDR.get().getPluralRules();
  
  protected Fixture cardinal(CLDR.Locale locale) {
    return new Fixture(locale, false);
  }
  
  protected Fixture ordinal(CLDR.Locale locale) {
    return new Fixture(locale, true);
  }
  
  protected static class Fixture {
    
    private final CLDR.Locale locale;
    private final boolean ordinal;
    
    public Fixture(CLDR.Locale locale, boolean ordinal) {
      this.locale = locale;
      this.ordinal = ordinal;
    }
    
    public void check(String number, PluralCategory expected) {
      String language = locale.language();
      NumberOperands operands = new NumberOperands(number);
      PluralCategory actual = ordinal 
          ? PLURAL_RULES.evalOrdinal(language, operands) 
          : PLURAL_RULES.evalCardinal(language, operands);
      Assert.assertEquals(actual, expected, "Failed plural for '" + number + "'");
    }
  }
  
}
