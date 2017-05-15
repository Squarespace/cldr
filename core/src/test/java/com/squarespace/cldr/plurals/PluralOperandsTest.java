package com.squarespace.cldr.plurals;



import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;


public class PluralOperandsTest {

  @Test
  public void testParseOperands() {
    assertOperands("1", "neg=false n=1 i=1 v=0 w=0 f=0 t=0");
    assertOperands("1.230", "neg=false n=1.230 i=1 v=3 w=2 f=230 t=23");
    assertOperands("-5.23000", "neg=true n=5.23000 i=5 v=5 w=2 f=23000 t=23");

    // ignore invalid trailing data
    assertOperands("1x...", "neg=false n=1 i=1 v=0 w=0 f=0 t=0");
    assertOperands("1.230xyz", "neg=false n=1.230 i=1 v=3 w=2 f=230 t=23");
    assertOperands("1.230..", "neg=false n=1.230 i=1 v=3 w=2 f=230 t=23");
  }

  private void assertOperands(String number, String expected) {
    PluralOperands op = new PluralOperands(number);
    assertEquals(op.repr(), expected);
  }

}
