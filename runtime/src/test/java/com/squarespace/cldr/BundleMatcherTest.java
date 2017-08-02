package com.squarespace.cldr;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;


public class BundleMatcherTest {

  @Test
  public void testBasics() {
    assertMatch("sr-Cyrl-XY", "sr");
    assertMatch("sr-XY", "sr");
    assertMatch("be-BY", "be-BY");
    assertMatch("men-Zzzz-US", "en");
    assertMatch("da", "da-DK");
  }
  
  private void assertMatch(String inputTag, String expectedTag) {
    BundleMatcher matcher = CLDR.get().getBundleMatcher();
    CLDR.Locale actual = matcher.match(inputTag);
    CLDR.Locale expected = MetaLocale.fromLanguageTag(expectedTag);
    assertEquals(actual, expected);
  }
  
}
