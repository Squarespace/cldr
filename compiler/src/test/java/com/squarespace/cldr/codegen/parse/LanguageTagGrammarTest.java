package com.squarespace.cldr.codegen.parse;

import static com.squarespace.cldr.codegen.LocaleID.P_LANGUAGE_TAG;
import static com.squarespace.cldr.codegen.parse.PluralRuleGrammarTest.assertParse;

import org.testng.annotations.Test;

import com.squarespace.cldr.codegen.LocaleID;


public class LanguageTagGrammarTest {

  @Test
  public void testLanguageTags() {
    assertParse(P_LANGUAGE_TAG, "en-Latn-US-VARIANT", new LocaleID("en", "Latn", "US", "VARIANT"));
    assertParse(P_LANGUAGE_TAG, "en-US", new LocaleID("en", "", "US", ""));
    assertParse(P_LANGUAGE_TAG, "en", new LocaleID("en", "", "", ""));
    assertParse(P_LANGUAGE_TAG, "es-Latn-ES-VALENCIA", new LocaleID("es", "Latn", "ES", "VALENCIA"));
  }

}
