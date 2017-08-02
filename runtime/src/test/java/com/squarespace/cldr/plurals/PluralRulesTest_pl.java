package com.squarespace.cldr.plurals;

import static com.squarespace.cldr.plurals.PluralCategory.FEW;
import static com.squarespace.cldr.plurals.PluralCategory.MANY;
import static com.squarespace.cldr.plurals.PluralCategory.ONE;
import static com.squarespace.cldr.plurals.PluralCategory.OTHER;

import org.testng.annotations.Test;

import com.squarespace.cldr.CLDR;


public class PluralRulesTest_pl extends PluralBaseTest {

  @Test
  public void testCardinal() {
    Fixture f = cardinal(CLDR.Locale.pl);
    
    //   integer 1
    f.check("1", ONE);

    //   integer 2~4, 22~24, 32~34, 42~44, 52~54, 62, 102, 1002, …
    for (int i = 2; i < 5; i++) {
      f.check(Integer.toString(i), FEW);
      f.check(Integer.toString(20 + i), FEW);
      f.check(Integer.toString(30 + i), FEW);
    }
    
    //   integer 0, 5~19, 100, 1000, 10000, 100000, 1000000, …
    f.check("0", MANY);
    for (int i = 5; i < 20; i++) {
      f.check(Integer.toString(i), MANY);
    }
    f.check("100", MANY);
    
    // ... etc
    f.check("0.0", OTHER);
    f.check("1.0", OTHER);
    f.check("1.1", OTHER);
  }
  
}
