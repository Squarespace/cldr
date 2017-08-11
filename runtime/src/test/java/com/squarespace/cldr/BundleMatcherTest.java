package com.squarespace.cldr;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;


public class BundleMatcherTest {

  @Test
  public void testBasics() {
    assertMatch("sr-Cyrl-XY", "sr-Cyrl-RS");
    assertMatch("sr-XY", "sr-Cyrl-RS");
    assertMatch("men-Zzzz-US", "en-Latn-US");
    assertMatch("da", "da-DK");
  }
  
  private void assertMatch(String inputTag, String expectedTag) {
    BundleMatcher matcher = CLDR.get().getBundleMatcher();
    CLDR.Locale actual = matcher.match(inputTag);
    CLDR.Locale expected = matcher.match(expectedTag);
    assertEquals(actual, expected);
  }
}
