package com.squarespace.cldr.plurals;

import static com.squarespace.cldr.plurals.PluralCategory.FEW;
import static com.squarespace.cldr.plurals.PluralCategory.ONE;
import static com.squarespace.cldr.plurals.PluralCategory.OTHER;
import static com.squarespace.cldr.plurals.PluralCategory.TWO;

import org.testng.annotations.Test;

import com.squarespace.cldr.CLDR;


public class PluralCategoryTest_EN_US extends PluralBaseTest {

  @Test
  public void testCardinal() {
    Fixture f = cardinal(CLDR.EN_US);
    f.check("0", OTHER);
    f.check("0.0", OTHER);
    f.check("1", ONE);
    f.check("1.0", OTHER);
    f.check("3", OTHER);
  }
    
  @Test
  public void testOrdinal() {
    Fixture f = ordinal(CLDR.EN_US);
    f.check("0", OTHER);
    f.check("1", ONE);
    f.check("2", TWO);
    f.check("3", FEW);
    f.check("4", OTHER);
  }
  
}
