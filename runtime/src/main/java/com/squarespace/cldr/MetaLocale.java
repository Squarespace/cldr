package com.squarespace.cldr;

import static com.squarespace.cldr.parse.LanguageTagGrammar.P_LANGUAGE;
import static com.squarespace.cldr.parse.LanguageTagGrammar.P_SCRIPT;
import static com.squarespace.cldr.parse.LanguageTagGrammar.P_TERRITORY;
import static com.squarespace.cldr.parse.LanguageTagGrammar.P_VARIANT;

import java.util.Arrays;

import com.squarespace.compiler.common.Maybe;
import com.squarespace.compiler.parse.Pair;
import com.squarespace.compiler.parse.Parser;


/**
 * Locale container which serves as the base class for public locale. Includes
 * protected methods used for internal language matching. 
 * 
 * Null is used to represent the undefined value for a field:
 * 
 *   language   "und"
 *   script     "Zzzz"
 *   territory  "ZZ"
 *   variant    ""
 */
class MetaLocale implements CLDR.Locale, Comparable<MetaLocale> {

  private static Parser<MetaLocale> P_LANGUAGE_TAG =
      P_LANGUAGE.flatMap(l -> 
        P_SCRIPT.orDefault("").flatMap(s -> 
          P_TERRITORY.orDefault("").flatMap(t -> 
            P_VARIANT.orDefault("").map(v -> 
              new MetaLocale(l.toString(), s.toString(), t.toString(), v.toString())))));
  
  private static final char SEP = '-';

  private static final String UNDEF_LANGUAGE = "und";
  private static final String UNDEF_SCRIPT = "Zzzz";
  private static final String UNDEF_TERRITORY = "ZZ";
  private static final String UNDEF_VARIANT = null;

  protected static final int LANGUAGE = 0;
  protected static final int SCRIPT = 1;
  protected static final int TERRITORY = 2;
  protected static final int VARIANT = 3;

  protected final String[] fields = new String[] { null, null, null, null };

  public MetaLocale() {
  }

  /**
   * Public constructor. All fields are checked for null, empty or match on
   * the undefined value and set accordingly.
   */
  public MetaLocale(String language, String script, String territory, String variant) {
    setFieldIf(LANGUAGE, language, UNDEF_LANGUAGE);
    setFieldIf(SCRIPT, script, UNDEF_SCRIPT);
    setFieldIf(TERRITORY, territory, UNDEF_TERRITORY);
    setFieldIf(VARIANT, variant, UNDEF_VARIANT);
  }

  /**
   * Protected constructor, all fields are fast-copied from a known valid locale.
   */
  protected MetaLocale(MetaLocale other) {
    System.arraycopy(other.fields, 0, fields, 0, fields.length);
  }

  /**
   * Returns the language field, properly defaulted ("und").
   */
  public String language() {
    return getField(LANGUAGE, UNDEF_LANGUAGE);
  }

  /**
   * Returns the script field, properly defaulted ("Zzzz").
   */
  public String script() {
    return getField(SCRIPT, UNDEF_SCRIPT);
  }

  /**
   * Returns the territory field, properly defaulted ("ZZ")
   */
  public String territory() {
    return getField(TERRITORY, UNDEF_TERRITORY);
  }

  /**
   * Returns the variant field or empty string if undefined.
   */
  public String variant() {
    return getField(VARIANT, "");
  }

  /**
   * Internal method for parsing well-formed language tags.
   */
  protected static MetaLocale parse(String tag) {
    if (tag.indexOf('_') != -1) {
      tag = tag.replace('_', SEP);
    }
    Maybe<Pair<MetaLocale, CharSequence>> result = P_LANGUAGE_TAG.parse(tag);
    // This parser is for internal use only during code generation, so we blow up
    // severely if a language tag fails to parse..
    if (result.isNothing()) {
      throw new RuntimeException("Failed to parse language tag: '" + tag + "'");
    }
    return result.get()._1;
  }
  
  /**
   * Constructs a MetaLocale from a language tag string.
   */
  public static MetaLocale fromLanguageTag(String tag) {
    if (tag.indexOf('_') != -1) {
      tag = tag.replace('_', SEP);
    }
    return fromJavaLocale(java.util.Locale.forLanguageTag(tag));
  }

  /**
   * Constructs a MetaLocale from a Java Locale object.
   */
  public static MetaLocale fromJavaLocale(java.util.Locale java) {
    // Some confusing cases can arise here based on the getLanguage() method
    // returning the deprecated language codes in a handful of cases. See
    // MetaLocaleTest for test cases for these examples.
    String language = java.getLanguage();
    switch (language) {
      case "iw":
        language = "he";
        break;
        
      case "in":
        language = "id";
        break;
        
      case "ji":
        language = "yi";
        break;
    }
   return new MetaLocale(language, java.getScript(), java.getCountry(), java.getVariant());
  }

  /**
   * Return expanded form, with undefined fields filled in, e.g. "en-Zzzz-ZZ".
   */
  public String expanded() {
    return render(true);
  }

  /**
   * Return compact form, with undefined fields omitted. Language is always output.
   */
  public String compact() {
    return render(false);
  }

  @Override
  public int compareTo(MetaLocale o) {
    int r = compare(fields[LANGUAGE], o.fields[LANGUAGE]);
    if (r == 0) {
      r = compare(fields[SCRIPT], o.fields[SCRIPT]);
    }
    if (r == 0) {
      r = compare(fields[TERRITORY], o.fields[TERRITORY]);
    }
    if (r == 0) {
      r = compare(fields[VARIANT], o.fields[VARIANT]);
    }
    return r;
  }
  
  private int compare(String a, String b) {
    if (a != null && b != null) {
      return a.compareTo(b);
    }
    if (a == null) {
      if (b == null) {
        return 0;
      }
      return -1;
    }
    return 1;
  }
  
  @Override
  public int hashCode() {
    return Arrays.hashCode(fields);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof MetaLocale) {
      MetaLocale other = (MetaLocale) obj;
      return Arrays.equals(fields, other.fields);
    }
    return false;
  }

  @Override
  public String toString() {
    return compact();
  }

  /**
   * Returns the number of populated fields, for comparison purposes.
   */
  protected int count() {
    int count = 0;
    for (String value : fields) {
      if (value != null) {
        count++;
      }
    }
    return count;
  }
  
  /**
   * Copy this locale.
   */
  protected MetaLocale copy() {
    return new MetaLocale(this);
  }

  /**
   * Return the language or null if none defined.
   */
  protected String _language() {
    return fields[LANGUAGE];
  }

  /**
   * Return the script or null if none defined.
   */
  protected String _script() {
    return fields[SCRIPT];
  }

  /**
   * Return the territory or null if none defined.
   */
  protected String _territory() {
    return fields[TERRITORY];
  }

  /**
   * Return the variant or null if none defined.
   */
  protected String _variant() {
    return fields[VARIANT];
  }
  
  /**
   * Fast-set the language field.
   */
  protected void setLanguage(String value) {
    setFieldRaw(LANGUAGE, value);
  }

  /**
   * Fast-set the script field.
   */
  protected void setScript(String value) {
    setFieldRaw(SCRIPT, value);
  }

  /**
   * Fast-set the territory field.
   */
  protected void setTerritory(String value) {
    setFieldRaw(TERRITORY, value);
  }

  /**
   * Fast-set the variant field.
   */
  protected void setVariant(String value) {
    setFieldRaw(VARIANT, value);
  }

  protected boolean isRoot() {
    return !hasLanguage() && !hasScript() && !hasTerritory();
  }
  
  protected boolean hasLanguage() {
    return fields[LANGUAGE] != null;
  }

  protected boolean hasScript() {
    return fields[SCRIPT] != null;
  }

  protected boolean hasTerritory() {
    return fields[TERRITORY] != null;
  }
  
  protected boolean hasVariant() {
    return fields[VARIANT] != null;
  }

  protected boolean hasAll() {
    return hasLanguage() && hasScript() && hasTerritory();
  }

  /**
   * Resets this locale to full undefined state.
   */
  protected void reset() {
    fields[LANGUAGE] = null;
    fields[SCRIPT] = null;
    fields[TERRITORY] = null;
    fields[VARIANT] = null;
  }

  /**
   * Render this locale to a string in compact or expanded form.
   */
  private String render(boolean compact) {
    StringBuilder buf = new StringBuilder();
    render(buf, LANGUAGE, UNDEF_LANGUAGE, compact);
    render(buf, SCRIPT, UNDEF_SCRIPT, compact);
    render(buf, TERRITORY, UNDEF_TERRITORY, compact);
    render(buf, VARIANT, UNDEF_VARIANT, compact);
    return buf.toString();
  }

  /**
   * Render a locale field to the buffer. Language field is always emitted,
   * and variant field is always omitted unless it has a value.
   */
  private void render(StringBuilder buf, int key, String undef, boolean expanded) {
    boolean force = key != VARIANT && (key == LANGUAGE || expanded);
    String value = fields[key];
    if (value != null || force) {
      if (buf.length() > 0) {
        buf.append(SEP);
      }
      buf.append(value == null ? undef : value);
    }
  }

  /**
   * Return a field's value or the undefined value.
   */
  private String getField(int key, String undef) {
    String value = fields[key];
    return value == null ? undef : value;
  }

  /**
   * All public set methods pass through here.
   */
  private void setFieldIf(int key, String value, String undef) {
    if (value == null) {
      this.fields[key] = value;
    } else {
      // Special case for language: replace BCP 47 field "root" with undefined value.
      if (key == LANGUAGE && value.equals("root")) {
        this.fields[key] = null;
      } else {
        this.fields[key] = value.equals("") || value.equals(undef) ? null : value;
      }
    }
  }

  /**
   * All protected set methods pass through here.
   */
  private void setFieldRaw(int key, String value) {
    this.fields[key] = value;
  }
}
