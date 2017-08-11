package com.squarespace.cldr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Given a MetaLocale, the bundle matching attempts to find the best match
 * amongst those available in the application. For example, "sr-Cyrl-XY"
 * may be a valid locale but no bundle is available. A fallback process would
 * match the bundle that best approximates "sr-Cyrl-XY" from those available.
 * 
 * Attempts to match the logic used by Globalize to implement Language Matching.
 * See:
 *  https://github.com/rxaviers/cldrjs/blob/master/doc/bundle_lookup_matcher.md#implementation-details
 *  http://www.unicode.org/reports/tr35/#LanguageMatching
 */
class BundleMatcher {

  private final LanguageMatcher languageMatcher;
  private final Map<MetaLocale, MetaLocale> availableBundlesMap = new HashMap<>();
  
  public BundleMatcher(LanguageMatcher languageMatcher, List<CLDR.Locale> availableBundles) {
    this.languageMatcher = languageMatcher;
    buildMap(availableBundles);
  }

  /**
   * Given a language tag, parse it and return the best available CLDR bundle identifier.
   */
  public CLDR.Locale match(String languageTag) {
    return match(MetaLocale.fromLanguageTag(languageTag));
  }

  /**
   * Given a Java Locale object, return the best available CLDR bundle identifier.
   */
  public CLDR.Locale match(java.util.Locale locale) {
    return match(MetaLocale.fromJavaLocale(locale));
  }

  /**
   * Given a MetaLocale object (internal to CLDR), return the best available
   * CLDR bundle identifier.
   * 
   * TODO: use language matching instead of truncation
   */
  public CLDR.Locale match(MetaLocale locale) {
    MetaLocale maxBundleId = languageMatcher.addLikelySubtags(locale);
    MetaLocale minBundleId = languageMatcher.removeLikelySubtags(maxBundleId);
    
    MetaLocale key = minBundleId.copy();
    for (int flags : LanguageMatcher.MATCH_ORDER) {
      LanguageMatcher.set(minBundleId, key, flags);
      MetaLocale result = availableBundlesMap.get(key);
      if (result != null && result.hasLanguage()) {
        return result;
      }
    }
    return availableBundlesMap.get((MetaLocale)CLDR.Locale.en);
  }
  
  /**
   * Construct a mapping from a bundle's minimum bundle identifier to the available
   * bundle identifier.
   */
  private void buildMap(List<CLDR.Locale> availableLocales) {
    for (CLDR.Locale locale : availableLocales) {
      MetaLocale meta = (MetaLocale)locale;
      MetaLocale maxBundleId = languageMatcher.addLikelySubtags(meta);
      MetaLocale minBundleId = languageMatcher.removeLikelySubtags(maxBundleId);
      
      MetaLocale match = availableBundlesMap.get(minBundleId);
      if (match != null && match.count() < minBundleId.count()) {
        continue;
      }
      
      availableBundlesMap.put(minBundleId, meta);
    }
  }

}
