package com.squarespace.cldr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.squarespace.cldr.dates.CalendarFormatter;
import com.squarespace.cldr.numbers.NumberFormatter;
import com.squarespace.cldr.plurals.PluralRules;
import com.squarespace.cldr.units.UnitConverter;
import com.squarespace.cldr.units.UnitConverters;
import com.squarespace.compiler.parse.Pair;


/**
 * Common Localization Data Repository (CLDR) base class.
 */
abstract class CLDRBase {

  protected static final Map<String, List<Pair<LanguageAlias, LanguageAlias>>> LANGUAGE_ALIAS_MAP = new HashMap<>();
  protected static final Map<String, Set<String>> TERRITORY_ALIAS_MAP = new HashMap<>();
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
    return resolve(meta);
  }
  
  /**
   * Resolve a (possibly incomplete) Java locale into an expanded
   * CLDR locale. This performs the "add likely subtags" operation.
   * http://www.unicode.org/reports/tr35/tr35.html#Likely_Subtags
   */
  public CLDR.Locale resolve(java.util.Locale javaLocale) {
    MetaLocale meta = MetaLocale.fromJavaLocale(javaLocale);
    return resolve(meta);
  }
  
  /**
   * Produce the minimal representation of this locale by removing likely subtags.
   */
  public CLDR.Locale minimize(CLDR.Locale locale) {
    return languageResolver.removeLikelySubtags((MetaLocale)locale);
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

  protected CLDR.Locale resolve(MetaLocale meta) {
    meta = substituteLanguageAliases(meta);
    meta = substituteTerritoryAliases(meta);
    return languageResolver.addLikelySubtags(meta);
  }

  protected static MetaLocale substituteLanguageAliases(MetaLocale meta) {
    List<Pair<LanguageAlias, LanguageAlias>> aliases = LANGUAGE_ALIAS_MAP.get(meta.language());
    if (aliases == null) {
      return meta;
    }
    meta = meta.copy();
    for (Pair<LanguageAlias, LanguageAlias> pair : aliases) {
      LanguageAlias type = pair._1;
      LanguageAlias repl = pair._2;
      
      if (type.count() == 1 || type.equals(meta)) {
        meta.setLanguage(repl._language());
        if (!meta.hasScript()) {
          meta.setScript(repl._script());
        }
        if (!meta.hasTerritory()) {
          meta.setTerritory(repl._territory());
        }
        break;
      }
    }
    return meta;
  }
  
  protected static MetaLocale substituteTerritoryAliases(MetaLocale meta) {
    String territory = meta.territory();
    Set<String> replacement = TERRITORY_ALIAS_MAP.get(territory);
    if (replacement == null) {
      return meta;
    }

    meta = meta.copy();

    // HACK: for now, just use the first in the list.
    meta.setTerritory(replacement.iterator().next());
    return meta;
    
// TODO: Get best regions for this language and optional script combination.
// If one is in the replacement set, use it. Complete the code below.
// 
//    String language = meta.language();
//    
//    if (replacement.size() == 1) {
//      meta.setTerritory(replacement.iterator().next());
//      return meta;
//    }
//
//    meta.setTerritory(first);
//    return meta;
  }

  /**
   * Add a language alias entry.
   */
  protected static void addLanguageAlias(String rawType, String rawReplacement) {
    LanguageAlias type = LanguageAlias.parse(rawType);
    LanguageAlias replacement = LanguageAlias.parse(rawReplacement);
    String language = type.language();
    List<Pair<LanguageAlias, LanguageAlias>> aliases = LANGUAGE_ALIAS_MAP.get(language);
    if (aliases == null) {
      aliases = new ArrayList<>();
      LANGUAGE_ALIAS_MAP.put(language, aliases);
    }
    aliases.add(Pair.pair(type, replacement));
  }
  
  protected static void addTerritoryAlias(String type, String rawReplacement) {
    String[] parts = rawReplacement.split("\\s+");
    Set<String> replacement = new LinkedHashSet<>(Arrays.asList(parts));
    TERRITORY_ALIAS_MAP.put(type, replacement);
  }
  
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
