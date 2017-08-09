package com.squarespace.cldr;

import java.util.HashMap;
import java.util.Map;

import com.squarespace.cldr.dates.CalendarFormatter;
import com.squarespace.cldr.numbers.NumberFormatter;


/**
 * Common Localization Data Repository (CLDR) base class.
 */
abstract class CLDRBase {

  // TODO: method to resolve appropriate cldr locale from java locale
  // TODO: locale matching logic
  
  protected static final Map<CLDR.Locale, CLDR.Locale> defaultContent = new HashMap<>();
  protected static final Map<String, String> availableBundlesMap = new HashMap<>();
  protected static final LazyLoader<CLDR.Locale, CalendarFormatter> calendarFormatters = new LazyLoader<>();
  protected static final LazyLoader<CLDR.Locale, NumberFormatter> numberFormatters = new LazyLoader<>();
  
  /**
   * Map a language tag  to the corresponding CLDR locale, e.g. "en-US", "fr".
   */
  public CLDR.Locale getLocale(String locale) {
    
    return null;
  }
  
  /**
   * Map a Java locale to the corresponding CLDR locale.
   */
  public CLDR.Locale getLocale(java.util.Locale locale) {
//    CLDRLocale initial = javaLocaleMap.get(locale);
//    if (initial == null) {
//      return CLDR.en;
//    }
//    return getLocale(initial);
    return null;
  }
  
//  public CLDRLocale getLocale(String localeId) {
//    Locale.forLanguageTag(languageId);
//  }
  
  /**
   * Fetches a translated locale, mapping it to a locale in defaultContent if necessary.
   */
  public CLDR.Locale resolve(CLDR.Locale locale) {
    return defaultContent.getOrDefault(locale, locale);
  } 
  
  /**
   * Returns a calendar formatter for the given locale, translating it before lookup.
   */
  public CalendarFormatter getCalendarFormatter(CLDR.Locale locale) {
    CalendarFormatter formatter = calendarFormatters.get(resolve(locale));
    return formatter == null ? calendarFormatters.get(CLDR.Locale.en_US) : formatter;
  }

  /**
   * Returns a number formatter for the given locale, translating it before lookup.
   */
  public NumberFormatter getNumberFormatter(CLDR.Locale locale) {
    NumberFormatter formatter = numberFormatters.get(resolve(locale));
    return formatter == null ? numberFormatters.get(CLDR.Locale.en_US) : formatter;
  }
  
  public abstract PluralRules getPluralRules();
  
  protected static void registerCalendarFormatter(CLDR.Locale locale, Class<? extends CalendarFormatter> cls) {
    calendarFormatters.put(locale, cls);
  }
  
  protected static void registerNumberFormatter(CLDR.Locale locale, Class<? extends NumberFormatter> cls) {
    numberFormatters.put(locale, cls);
  }

  /**
   * Hidden base class for constructing locale instances.
   */
  protected static class _LocaleBase {

    private static final String UND = "und";

    protected final String language;
    protected final String script;
    protected final String territory;
    protected final String variant;
    protected final String repr;

    public _LocaleBase(String language, String script, String territory, String variant) {
      if (language == null || language.isEmpty()) {
        language = UND;
      }

      String repr = language;
      if (script == null) {
        script = "";
      }
      if (territory == null) {
        territory = "";
      }
      if (variant == null) {
        variant = "";
      }

      this.language = language;
      this.script = script;
      this.territory = territory;
      this.variant = variant;

      if (!script.isEmpty()) {
        repr += "-" + script;
      }
      if (!territory.isEmpty()) {
        repr += "-" + territory;
      }
      if (!variant.isEmpty()) {
        repr += "-" + variant;
      }
      this.repr = repr;
    }

    public String language() {
      return language;
    }

    public String script() {
      return script;
    }

    public String territory() {
      return territory;
    }

    public String variant() {
      return variant;
    }

    @Override
    public String toString() {
      return repr;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof _LocaleBase) {
        return repr.equals(((_LocaleBase)obj).repr);
      }
      return false;
    }

    @Override
    public int hashCode() {
      return repr.hashCode();
    }
    
  }
  
}
