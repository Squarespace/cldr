package com.squarespace.cldr;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.util.Locale;

import org.testng.annotations.Test;


public class MetaLocaleTest {

  @Test
  public void testLocaleSubtags() {
    // Confusing cases for locale parsing with deprecated subtags, e.g. Hebrew, Indonesian.
    // The language tag methods have the expected result, but Locale.getLanguage() returns
    // the deprecated language identifiers.
    // https://www.iana.org/assignments/language-subtag-registry/language-subtag-registry
    
    // Hebrew
    Locale he = Locale.forLanguageTag("he");
    assertEquals(he.toLanguageTag(), "he");
    assertEquals(he.getLanguage(), "iw");   // "iw" is deprecated.
    
    // Indonesian
    Locale id = Locale.forLanguageTag("id");
    assertEquals(id.toLanguageTag(), "id");
    assertEquals(id.getLanguage(), "in");   // "in" is deprecated
    
    // Yiddish
    Locale yi = Locale.forLanguageTag("yi");
    assertEquals(yi.toLanguageTag(), "yi");
    assertEquals(yi.getLanguage(), "ji");   // "ji" is deprecated
  }
  
  @Test
  public void testDeprecated() {
    MetaLocale meta = MetaLocale.fromLanguageTag("he");
    assertEquals(meta.language(), "he");
    assertEquals(meta.toString(), "he");
    
    meta = MetaLocale.fromLanguageTag("id");
    assertEquals(meta.language(), "id");
    assertEquals(meta.toString(), "id");

    meta = MetaLocale.fromLanguageTag("yi");
    assertEquals(meta.language(), "yi");
    assertEquals(meta.toString(), "yi");
  }

  @Test
  public void testCompare() {
    MetaLocale meta1 = new MetaLocale(null, "Latn", null, null);
    MetaLocale meta2 = new MetaLocale(null, "Grek", null, null);
    assertEquals(meta1.compareTo(meta2), 5);
    assertEquals(meta2.compareTo(meta1), -5);
    
    meta2 = new MetaLocale("en", "Latn", null, null);
    assertEquals(meta1.compareTo(meta2), -1);
    assertEquals(meta2.compareTo(meta1), 1);
  }
  
  @Test
  public void testEquals() {
    MetaLocale meta1 = new MetaLocale(null, "Latn", null, null);
    MetaLocale meta2 = new MetaLocale(null, "Latn", null, null);
    assertEquals(meta1, meta2);
    assertEquals(meta1.hashCode(), meta2.hashCode());

    meta2 = new MetaLocale("und", "Latn", null, null);
    assertEquals(meta1, meta2);
    assertEquals(meta1.hashCode(), meta2.hashCode());
    
    meta2 = new MetaLocale("en", "Latn", null, null);
    assertNotEquals(meta1, meta2);
    assertNotEquals(meta1.hashCode(), meta2.hashCode());
  }
  
  @Test
  public void testRepresentation() {
    MetaLocale meta = new MetaLocale("ca", "Latn", "ES", "VALENCIA");
    String expected = "ca-Latn-ES-VALENCIA";
    assertEquals(meta.compact(), expected);
    assertEquals(meta.expanded(), expected);

    meta = new MetaLocale("ca", null, "ES", null);
    assertEquals(meta.compact(), "ca-ES");
    assertEquals(meta.expanded(), "ca-Zzzz-ES");
  }

  @Test
  public void testJavaLocale() {
    MetaLocale meta = MetaLocale.fromJavaLocale(Locale.US);
    assertEquals(meta.compact(), "en-US");
    assertEquals(meta.expanded(), "en-Zzzz-US");
  }

  @Test
  public void testRoundTrip() {
    for (String tag : new String[] { "en-US-POSIX" }) {
      MetaLocale meta1 = MetaLocale.fromLanguageTag(tag);
      Locale locale = Locale.forLanguageTag(meta1.expanded());
      MetaLocale meta2 = MetaLocale.fromJavaLocale(locale);
      assertEquals(meta1, meta2);

      locale = Locale.forLanguageTag(meta1.compact());
      meta2 = MetaLocale.fromJavaLocale(locale);
      assertEquals(meta1, meta2);
    }
  }

  @Test
  public void testVariant() {
    MetaLocale meta = new MetaLocale("en", null, "US", "POSIX");
    assertEquals(meta.compact(), "en-US-POSIX");
    assertEquals(meta.expanded(), "en-Zzzz-US-POSIX");
  }

  @Test
  public void testNulls() {
    MetaLocale meta = new MetaLocale(null, null, null, null);
    assertEquals(meta.compact(), "und");
    assertEquals(meta.expanded(), "und-Zzzz-ZZ");
  }

}
