package com.squarespace.cldr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.squarespace.cldr.dates.CalendarFormatter;
import com.squarespace.cldr.numbers.NumberFormatter;
import com.squarespace.cldr.plurals.PluralRules;
import com.squarespace.cldr.units.UnitConverter;
import com.squarespace.cldr.units.UnitConverters;


/**
 * Common Localization Data Repository (CLDR) base class.
 */
abstract class CLDRBase {

  protected static final Map<MetaLocale, MetaLocale> languageAliasMap = new HashMap<>();
  protected static final Map<MetaLocale, MetaLocale> likelySubtagsMap = new HashMap<>();
  protected static final Map<CLDR.Locale, CLDR.Locale> defaultContent = new HashMap<>();
  
  protected static final LazyLoader<CLDR.Locale, CalendarFormatter> calendarFormatters = new LazyLoader<>();
  protected static final LazyLoader<CLDR.Locale, NumberFormatter> numberFormatters = new LazyLoader<>();
  
  protected final LanguageMatcher languageMatcher;
  protected final BundleMatcher bundleMatcher;
  
  public CLDRBase() {
    languageMatcher = new LanguageMatcher(likelySubtagsMap);
    bundleMatcher = new BundleMatcher(languageMatcher, availableLocales());
  }

  public abstract List<CLDR.Locale> availableLocales();
  
  /**
   * Map a language tag to the best available CLDR locale.
   */
  public CLDR.Locale get(String languageTag) {
    return bundleMatcher.match(languageTag);
  }
  
  // TODO: support weighted string language resolving, e.g. Accept-Language header
  // public CLDR.Locale get(List<LanguagePreference> preferences);
  
  /**
   * Map a Java locale to the corresponding CLDR locale.
   */
  public CLDR.Locale get(java.util.Locale javaLocale) {
    return bundleMatcher.match(javaLocale);
  }

  /**
   * Get the bundle identifier for a given locale.
   */
  public CLDR.Locale get(CLDR.Locale locale) {
    return defaultContent.getOrDefault(locale, locale);
  }

  /**
   * Returns a best match calendar formatter for the given language tag string.
   */
  public CalendarFormatter getCalendarFormatter(String languageTag) {
    return getCalendarFormatter(get(languageTag));
  }

  /**
   * Returns a best match calendar formatter for the given Java locale.
   */
  public CalendarFormatter getCalendarFormatter(java.util.Locale locale) {
    return getCalendarFormatter(get(locale));
  }
  
  /**
   * Returns a best match number formatter for the given language tag string.
   */
  public NumberFormatter getNumberFormatter(String languageTag) {
    return getNumberFormatter(get(languageTag));
  }
  
  /**
   * Returns a best match number formatter for the given Java locale.
   */
  public NumberFormatter getNumberFormatter(java.util.Locale locale) {
    return getNumberFormatter(get(locale));
  }
  
  /**
   * Returns a calendar formatter for the given locale, translating it before lookup.
   */
  public CalendarFormatter getCalendarFormatter(CLDR.Locale locale) {
    CalendarFormatter formatter = calendarFormatters.get(get(locale));
    return formatter == null ? calendarFormatters.get(CLDR.Locale.en_US) : formatter;
  }

  /**
   * Returns a number formatter for the given locale, translating it before lookup.
   */
  public NumberFormatter getNumberFormatter(CLDR.Locale locale) {
    NumberFormatter formatter = numberFormatters.get(get(locale));
    return formatter == null ? numberFormatters.get(CLDR.Locale.en_US) : formatter;
  }

  public UnitConverter getUnitConverter(CLDR.Locale locale) {
    return UnitConverters.get(locale);
  }
  
  /**
   * Returns a class for evaluating plural rules for a given language.
   */
  public abstract PluralRules getPluralRules();
  
  protected LanguageMatcher getLanguageMatcher() {
    return languageMatcher;
  }
  
  protected BundleMatcher getBundleMatcher() {
    return bundleMatcher;
  }
  
  protected static void registerCalendarFormatter(CLDR.Locale locale, Class<? extends CalendarFormatter> cls) {
    calendarFormatters.put(locale, cls);
  }
  
  protected static void registerNumberFormatter(CLDR.Locale locale, Class<? extends NumberFormatter> cls) {
    numberFormatters.put(locale, cls);
  }
  
}
