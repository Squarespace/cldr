package com.squarespace.cldr;

import static org.testng.Assert.assertEquals;

import java.util.Locale;

import org.testng.annotations.Test;


public class MetaLocaleTest {

  @Test
  public void testBasic() {
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
