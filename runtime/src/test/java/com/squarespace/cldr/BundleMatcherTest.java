package com.squarespace.cldr;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;


public class BundleMatcherTest {

  @Test
  public void testBasics() {
    assertMatch("und-Zzzz-ZZ", "en");
    
    assertMatch("und-Zzzz-US", "en");
    assertMatch("und-Latn", "en");
    assertMatch("en", "en");
    assertMatch("en-XY", "en");
    assertMatch("men-Zzzz-US", "en");

    assertMatch("es-LA", "es");
    assertMatch("und-419", "es-419");
    
    assertMatch("und-Gujr", "gu");
    assertMatch("und-Hebr", "he");
    
    assertMatch("sr-Cyrl-XY", "sr-Cyrl");
    assertMatch("sr-XY", "sr-Cyrl");
    
    assertMatch("be-BY", "be");
    assertMatch("da-DK", "da");
    
    assertMatch("zh", "zh");
    assertMatch("zh-Hant", "zh-Hant");
    assertMatch("zh-TW", "zh-Hant");
    assertMatch("zh-MO", "zh-Hant-MO");
    assertMatch("zh-SG", "zh-Hans-SG");
  }

  private void assertMatch(String inputTag, String expectedTag) {
    BundleMatcher matcher = CLDR.get().getBundleMatcher();
    assertMatch(matcher, inputTag, expectedTag);
  }
  
  private void assertMatch(BundleMatcher matcher, String inputTag, String expectedTag) {
    CLDR.Locale actual = matcher.match(inputTag);
    CLDR.Locale expected = MetaLocale.parse(expectedTag);
    assertEquals(actual, expected);
  }
  
}
