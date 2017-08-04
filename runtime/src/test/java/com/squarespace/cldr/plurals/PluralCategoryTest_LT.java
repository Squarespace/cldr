package com.squarespace.cldr.plurals;

import static com.squarespace.cldr.plurals.PluralCategory.FEW;
import static com.squarespace.cldr.plurals.PluralCategory.MANY;
import static com.squarespace.cldr.plurals.PluralCategory.ONE;
import static com.squarespace.cldr.plurals.PluralCategory.OTHER;

import org.testng.annotations.Test;

import com.squarespace.cldr.CLDR;


public class PluralCategoryTest_LT extends PluralBaseTest {

  @Test
  public void testCardinal() {
    Fixture f = cardinal(CLDR.LT);
    
    //   integer 1, 21, 31, 41, 51, 61, 71, 81, 101, 1001, … 
    //   decimal 1.0, 21.0, 31.0, 41.0, 51.0, 61.0, 71.0, 81.0, 101.0, 1001.0, …
    for (int i : new int[] { 1, 21, 31, 41, 51 }) {
      f.check(Integer.toString(i), ONE);
      f.check(i + ".0", ONE);
    }
    
    //   integer 2~9, 22~29, 102, 1002, … 
    //   decimal 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 22.0, 102.0, 1002.0, …
    for (int i = 2; i < 10; i++) {
      f.check(Integer.toString(i), FEW);
      f.check(Integer.toString(20 + i), FEW);
    }
    
    //   decimal 0.1~0.9, 1.1~1.7, 10.1, 100.1, 1000.1, …
    for (int i = 1; i < 10; i++) {
      f.check("0." + i, MANY);
      f.check("1." + i, MANY);
      f.check("10." + i, MANY);
      f.check("100." + i, MANY);
    }
    
    // ... etc
    f.check("10", OTHER);
  }

}
