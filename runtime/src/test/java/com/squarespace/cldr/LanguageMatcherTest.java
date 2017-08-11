package com.squarespace.cldr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;


public class LanguageMatcherTest {

  @Test
  public void testBasics() {
    assertMatch("en-US", "en");
    assertMatch("en_US", "en");
    assertMatch("en-Zzzz-US", "en");
    assertMatch("en-US-u-cu-USD", "en");

    assertMatch("en-GB", "en-GB");
    
    assertMatch("sr-Cyrl", "sr");
    assertMatch("sr-RS", "sr");
    assertMatch("sr-Cyrl-BA", "sr-BA");
    assertMatch("sr_Latn_BA", "sr-Latn-BA");
    assertMatch("sr-Latn-RS", "sr-Latn-RS");
    assertMatch("sr-Cyrl-XY", "sr-XY");
    
    assertMatch("zh-TW", "zh-TW");
    assertMatch("zh-CN", "zh");
  }
  
  /**
   * Ensure a mix of valid and invalid strings always resolve to a minimum language identifier.
   */
  @Test
  public void testWebsiteLocales() {
    LanguageMatcher matcher = CLDR.get().getLanguageMatcher();
    for (String tag : WEBSITE_LANGUAGE_TAGS) {
      CLDR.Locale locale = matcher.matchLanguageTag(tag);
      
      // Expect either "en", "<lang>" or original tag.
      assertThat(locale.compact(), anyOf(is("en"), is(locale.language()), is(tag)));
    }
  }
  
  private void assertMatch(String inputTag, String expectedTag) {
    LanguageMatcher matcher = CLDR.get().getLanguageMatcher();
    CLDR.Locale actual = matcher.matchLanguageTag(inputTag);
    CLDR.Locale expected = MetaLocale.fromLanguageTag(expectedTag);
    assertEquals(actual, expected);
  }
  
  // Locale strings collected across all websites (including errors) as of 2017-08-09.
  private static final String[] WEBSITE_LANGUAGE_TAGS = new String[] {
       "",
       "0",
       "24",
       "4",
       "ar-AE",
       "ar-BH",
       "ar-DZ",
       "ar-EG",
       "ar-IQ",
       "ar-JO",
       "ar-KW",
       "ar-LB",
       "ar-LY",
       "ar-MA",
       "ar-OM",
       "ar-QA",
       "ar-SA",
       "ar-SD",
       "ar-SY",
       "ar-TN",
       "ar-YE",
       "be-BY",
       "bg-BG",
       "ca-ES",
       "cs-CZ",
       "da-DK",
       "de-AT",
       "de-CH",
       "de-DE",
       "de-LU",
       "el-GR",
       "en-AU",
       "en-CA",
       "en-GB",
       "en-IE",
       "en-IN",
       "en-NZ",
       "en-UK",
       "en-US",
       "en-ZA",
       "es-AR",
       "es-BO",
       "es-CL",
       "es-CO",
       "es-CR",
       "es-DO",
       "es-EC",
       "es-ES",
       "es-GT",
       "es-HN",
       "es-MX",
       "es-NI",
       "es-PA",
       "es-PE",
       "es-PR",
       "es-PY",
       "es-SV",
       "es-UY",
       "es-VE",
       "et-EE",
       "fi-FI",
       "fr-BE",
       "fr-CA",
       "fr-CH",
       "fr-FR",
       "fr-LU",
       "hi-IN",
       "hr-HR",
       "hu-HU",
       "is-IS",
       "it-CH",
       "it-IT",
       "iw-IL",
       "ja-JP",
       "ko-KR",
       "lt-LT",
       "lv-LV",
       "mk-MK",
       "nl-BE",
       "nl-NL",
       "no-NO",
       "pl-PL",
       "pt-BR",
       "pt-PT",
       "removeRange",
       "ro-RO",
       "ru-RU",
       "sh-YU",
       "sk-SK",
       "sl-SI",
       "sq-AL",
       "sr-YU",
       "sv-SE",
       "th-TH",
       "tr-TR",
       "uk-UA",
       "zh-CN",
       "zh-HK"
  };

}
