package com.squarespace.cldr;

import java.util.Arrays;


/**
 * Locale container which serves as the base class for public locale. Includes
 * protected methods used for internal language matching. 
 * 
 * Null is used to represent the undefined value for a field:
 * 
 *   languag    "und"
 *   script     "Zzzz"
 *   territory  "ZZ"
 *   variant    ""
 */
class MetaLocale implements CLDR.Locale {

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
  private int hashCode = 0;

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
    hashCode = 0;
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
   return new MetaLocale(java.getLanguage(), java.getScript(), java.getCountry(), java.getVariant());
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
  public int hashCode() {
    if (hashCode == 0) {
      hashCode = Arrays.hashCode(fields);
      if (hashCode == 0) {
        hashCode = 1;
      }
    }
    return hashCode;
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

  protected boolean hasLanguage() {
    return fields[LANGUAGE] != null;
  }

  protected boolean hasScript() {
    return fields[SCRIPT] != null;
  }

  protected boolean hasTerritory() {
    return fields[TERRITORY] != null;
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
    hashCode = 0;
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
    hashCode = 0;
  }

  /**
   * All protected set methods pass through here.
   */
  private void setFieldRaw(int key, String value) {
    this.fields[key] = value;
    hashCode = 0;
  }
}
