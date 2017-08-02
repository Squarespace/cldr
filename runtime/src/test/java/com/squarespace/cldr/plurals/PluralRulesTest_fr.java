package com.squarespace.cldr.plurals;

import static com.squarespace.cldr.plurals.PluralCategory.*;

import org.testng.annotations.Test;

import com.squarespace.cldr.CLDR;

public class PluralRulesTest_fr extends PluralBaseTest {

  @Test
  public void testCardinal() {
    Fixture f = cardinal(CLDR.Locale.fr);
    f.check("1.2", ONE);
    f.check("1.7", ONE);
    f.check("2", OTHER);
  }
}
