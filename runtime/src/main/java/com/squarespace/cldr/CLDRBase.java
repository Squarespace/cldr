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

  protected static final Map<String, String> LANGUAGE_ALIAS_MAP = new HashMap<>();
  protected static final Map<MetaLocale, MetaLocale> LIKELY_SUBTAGS_MAP = new HashMap<>();
  protected static final Map<CLDR.Locale, CLDR.Locale> DEFAULT_CONTENT = new HashMap<>();
  
  protected static final LazyLoader<CLDR.Locale, CalendarFormatter> CALENDAR_FORMATTERS = new LazyLoader<>();
  protected static final LazyLoader<CLDR.Locale, NumberFormatter> NUMBER_FORMATTERS = new LazyLoader<>();
  
  protected final LanguageResolver languageResolver;
  protected final BundleMatcher bundleMatcher;
  
  public CLDRBase() {
    languageResolver = new LanguageResolver(LIKELY_SUBTAGS_MAP);
    bundleMatcher = new BundleMatcher(languageResolver, availableBundles());
  }

  public abstract List<CLDR.Locale> availableLocales();
  protected abstract List<CLDR.Locale> availableBundles();
  
  /**
   * Resolve a, possibly incomplete, language tag into an expanded
   * CLDR locale. This performs the "add likely subtags" operation.
   * http://www.unicode.org/reports/tr35/tr35.html#Likely_Subtags
   */
  public CLDR.Locale resolve(String languageTag) {
    MetaLocale meta = MetaLocale.fromLanguageTag(languageTag);
    return languageResolver.addLikelySubtags(meta);
  }
  
  /**
   * Resolve a (possibly incomplete) Java locale into an expanded
   * CLDR locale. This performs the "add likely subtags" operation.
   * http://www.unicode.org/reports/tr35/tr35.html#Likely_Subtags
   */
  public CLDR.Locale resolve(java.util.Locale javaLocale) {
    MetaLocale meta = MetaLocale.fromJavaLocale(javaLocale);
    return languageResolver.addLikelySubtags(meta);
  }

  /**
   * Returns a calendar formatter for the given locale, translating it before lookup.
   */
  public CalendarFormatter getCalendarFormatter(CLDR.Locale locale) {
    locale = bundleMatch(locale);
    CalendarFormatter formatter = CALENDAR_FORMATTERS.get(locale);
    return formatter == null ? CALENDAR_FORMATTERS.get(CLDR.Locale.en_US) : formatter;
  }

  /**
   * Returns a number formatter for the given locale, translating it before lookup.
   */
  public NumberFormatter getNumberFormatter(CLDR.Locale locale) {
    locale = bundleMatch(locale);
    NumberFormatter formatter = NUMBER_FORMATTERS.get(locale);
    return formatter == null ? NUMBER_FORMATTERS.get(CLDR.Locale.en_US) : formatter;
  }

  /**
   * Returns a unit converter for the given resolved locale. Note that the
   * resolved (expanded) locale is expected to have a populated and valid region field.
   * Otherwise this will default to the METRIC converter.
   */
  public UnitConverter getUnitConverter(CLDR.Locale locale) {
    return UnitConverters.get(locale);
  }
  
  /**
   * Returns a class for evaluating plural rules for a given language.
   */
  public abstract PluralRules getPluralRules();
  
  /**
   * Locate the correct bundle identifier for the given resolved locale. Note that
   * the bundle identifier is not terribly useful outside of the CLDR library, since
   * it may in many cases carry incomplete information for the intended public locale.
   * For example, the bundle used for "en-Latn-US" is "en" which is missing critical
   * subtags and as such should not be used to identify the "en-Latn-US" locale.
   */
  protected CLDR.Locale bundleMatch(CLDR.Locale locale) {
    locale = DEFAULT_CONTENT.getOrDefault(locale, locale);
    return bundleMatcher.match((MetaLocale)locale);
  }

  protected LanguageResolver getLanguageResolver() {
    return languageResolver;
  }
  
  protected BundleMatcher getBundleMatcher() {
    return bundleMatcher;
  }
  
  protected static void registerCalendarFormatter(CLDR.Locale locale, Class<? extends CalendarFormatter> cls) {
    CALENDAR_FORMATTERS.put(locale, cls);
  }
  
  protected static void registerNumberFormatter(CLDR.Locale locale, Class<? extends NumberFormatter> cls) {
    NUMBER_FORMATTERS.put(locale, cls);
  }
  
}
