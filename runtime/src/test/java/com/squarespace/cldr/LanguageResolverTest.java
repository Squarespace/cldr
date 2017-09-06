package com.squarespace.cldr;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;


public class LanguageResolverTest {

  @Test
  public void testAddLikelySubtags() {
    assertMatch("en", "en-Latn-US");
    assertMatch("und-US", "en-Latn-US");
    assertMatch("en-US", "en-Latn-US");
    assertMatch("en_US", "en-Latn-US");
    assertMatch("en-Zzzz-US", "en-Latn-US");
    assertMatch("en-US-u-cu-USD", "en-Latn-US");

    assertMatch("en-GB", "en-Latn-GB");

    assertMatch("es", "es-Latn-ES");
    assertMatch("es_ES", "es-Latn-ES");
    assertMatch("es_419", "es-Latn-419");
    assertMatch("es-Latn-419", "es-Latn-419");
    
    assertMatch("sr", "sr-Cyrl-RS");
    assertMatch("sr-Cyrl", "sr-Cyrl-RS");
    assertMatch("sr-RS", "sr-Cyrl-RS");
    assertMatch("sr-Cyrl-BA", "sr-Cyrl-BA");
    assertMatch("sr-Latn", "sr-Latn-RS");
    assertMatch("sr_Latn_BA", "sr-Latn-BA");
    assertMatch("sr-Latn-RS", "sr-Latn-RS");
    assertMatch("sr-Cyrl-XY", "sr-Cyrl-XY");

    assertMatch("be", "be-Cyrl-BY");
    assertMatch("be-BY", "be-Cyrl-BY");
    assertMatch("be-Cyrl-BY", "be-Cyrl-BY");
    assertMatch("und-BY", "be-Cyrl-BY");

    assertMatch("zh-TW", "zh-Hant-TW");
    assertMatch("zh-CN", "zh-Hans-CN");
    assertMatch("zh", "zh-Hans-CN");
  }
  
  @Test
  public void testWebsiteLocales() {
    assertMatch("", "en-Latn-US");
    assertMatch("0", "en-Latn-US");
    assertMatch("24", "en-Latn-US");
    assertMatch("4", "en-Latn-US");
    assertMatch("removeRange", "en-Latn-US");
    
    assertMatch("ar-AE", "ar-Arab-AE");
    assertMatch("ar-BH", "ar-Arab-BH");
    assertMatch("ar-DZ", "ar-Arab-DZ");
    assertMatch("ar-EG", "ar-Arab-EG");
    assertMatch("ar-IQ", "ar-Arab-IQ");
    assertMatch("ar-JO", "ar-Arab-JO");
    assertMatch("ar-KW", "ar-Arab-KW");
    assertMatch("ar-LB", "ar-Arab-LB");
    assertMatch("ar-LY", "ar-Arab-LY");
    assertMatch("ar-MA", "ar-Arab-MA");
    assertMatch("ar-OM", "ar-Arab-OM");
    assertMatch("ar-QA", "ar-Arab-QA");
    assertMatch("ar-SA", "ar-Arab-SA");
    assertMatch("ar-SD", "ar-Arab-SD");
    assertMatch("ar-SY", "ar-Arab-SY");
    assertMatch("ar-TN", "ar-Arab-TN");
    assertMatch("ar-YE", "ar-Arab-YE");

    assertMatch("be-BY", "be-Cyrl-BY");
    
    assertMatch("bg-BG", "bg-Cyrl-BG");
    
    assertMatch("ca-ES", "ca-Latn-ES");

    assertMatch("cs-CZ", "cs-Latn-CZ");
    
    assertMatch("da-DK", "da-Latn-DK");
    
    assertMatch("de-AT", "de-Latn-AT");
    assertMatch("de-CH", "de-Latn-CH");
    assertMatch("de-DE", "de-Latn-DE");
    assertMatch("de-LU", "de-Latn-LU");
    
    assertMatch("el-GR", "el-Grek-GR");
    assertMatch("en-AU", "en-Latn-AU");
    assertMatch("en-CA", "en-Latn-CA");
    assertMatch("en-GB", "en-Latn-GB");
    assertMatch("en-IE", "en-Latn-IE");
    assertMatch("en-IN", "en-Latn-IN");
    assertMatch("en-NZ", "en-Latn-NZ");
    assertMatch("en-UK", "en-Latn-UK");
    assertMatch("en-US", "en-Latn-US");
    assertMatch("en-ZA", "en-Latn-ZA");
    assertMatch("es-AR", "es-Latn-AR");
    assertMatch("es-BO", "es-Latn-BO");
    assertMatch("es-CL", "es-Latn-CL");
    assertMatch("es-CO", "es-Latn-CO");
    assertMatch("es-CR", "es-Latn-CR");
    assertMatch("es-DO", "es-Latn-DO");
    assertMatch("es-EC", "es-Latn-EC");
    assertMatch("es-ES", "es-Latn-ES");
    assertMatch("es-GT", "es-Latn-GT");
    assertMatch("es-HN", "es-Latn-HN");
    assertMatch("es-MX", "es-Latn-MX");
    assertMatch("es-NI", "es-Latn-NI");
    assertMatch("es-PA", "es-Latn-PA");
    assertMatch("es-PE", "es-Latn-PE");
    assertMatch("es-PR", "es-Latn-PR");
    assertMatch("es-PY", "es-Latn-PY");
    assertMatch("es-SV", "es-Latn-SV");
    assertMatch("es-UY", "es-Latn-UY");
    assertMatch("es-VE", "es-Latn-VE");
    assertMatch("et-EE", "et-Latn-EE");
        
    assertMatch("fi-FI", "fi-Latn-FI");
    assertMatch("fr-BE", "fr-Latn-BE");
    assertMatch("fr-CA", "fr-Latn-CA");
    assertMatch("fr-CH", "fr-Latn-CH");
    assertMatch("fr-FR", "fr-Latn-FR");
    assertMatch("fr-LU", "fr-Latn-LU");
    
    assertMatch("hi-IN", "hi-Deva-IN");
    assertMatch("hr-HR", "hr-Latn-HR");
    assertMatch("hu-HU", "hu-Latn-HU");
    
    assertMatch("is-IS", "is-Latn-IS");
    assertMatch("it-CH", "it-Latn-CH");
    assertMatch("it-IT", "it-Latn-IT");
    assertMatch("iw-IL", "iw-Hebr-IL");
    
    assertMatch("ja-JP", "ja-Jpan-JP");
    
    assertMatch("ko-KR", "ko-Kore-KR");
    
    assertMatch("lt-LT", "lt-Latn-LT");
    assertMatch("lv-LV", "lv-Latn-LV");
    
    assertMatch("mk-MK", "mk-Cyrl-MK");
        
    assertMatch("nl-BE", "nl-Latn-BE");
    assertMatch("nl-NL", "nl-Latn-NL");
    assertMatch("no-NO", "no-Latn-NO");
    
    assertMatch("pl-PL", "pl-Latn-PL");
    assertMatch("pt-BR", "pt-Latn-BR");
    assertMatch("pt-PT", "pt-Latn-PT");
    
    assertMatch("ro-RO", "ro-Latn-RO");
    assertMatch("ru-RU", "ru-Cyrl-RU");

    assertMatch("sh-YU", "sh-Latn-YU");
    assertMatch("sk-SK", "sk-Latn-SK");
    assertMatch("sl-SI", "sl-Latn-SI");
    assertMatch("sq-AL", "sq-Latn-AL");
    assertMatch("sr-YU", "sr-Cyrl-YU");
    assertMatch("sv-SE", "sv-Latn-SE");

    assertMatch("th-TH", "th-Thai-TH");
    assertMatch("tr-TR", "tr-Latn-TR");
    
    assertMatch("uk-UA", "uk-Cyrl-UA");
    
    assertMatch("zh-CN", "zh-Hans-CN");
    assertMatch("zh-HK", "zh-Hant-HK");
  }

  private void assertMatch(String inputTag, String expectedTag) {
    LanguageResolver matcher = CLDR.get().getLanguageResolver();
    CLDR.Locale actual = matcher.matchLanguageTag(inputTag);
    CLDR.Locale expected = MetaLocale.fromLanguageTag(expectedTag);
    assertEquals(actual, expected);
  }

}
