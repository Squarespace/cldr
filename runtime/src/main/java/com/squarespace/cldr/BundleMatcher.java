package com.squarespace.cldr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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

  private final LanguageResolver languageResolver;
  private final Map<MetaLocale, MetaLocale> availableBundlesMap = new HashMap<>();
  
  public BundleMatcher(LanguageResolver languageResolver, List<CLDR.Locale> availableBundles) {
    this.languageResolver = languageResolver;
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
   */
  public CLDR.Locale match(MetaLocale locale) {
    MetaLocale result = availableBundlesMap.get(locale);
    MetaLocale maxBundleId = null;
    if (result == null) {
      // If the locale isn't expanded, do that here by adding likely subtags.
      // For the vast majority of cases this code shouldn't be executed, since
      // we've indexed permutations of locales to create a static mapping to
      // bundle identifiers.
      maxBundleId = languageResolver.addLikelySubtags(locale);
      MetaLocale minBundleId = languageResolver.removeLikelySubtags(maxBundleId);
      result = availableBundlesMap.get(minBundleId);
    }
    if (result == null) {
      // Fallback necessary to find best match for language tags that have
      // invalid fields, e.g. those with an unknown region, etc.
      MetaLocale key = maxBundleId.copy();
      for (int flags : LanguageResolver.MATCH_ORDER) {
        LanguageResolver.set(maxBundleId, key, flags);
        result = availableBundlesMap.get(key);
        if (result != null && result.hasLanguage()) {
          return result;
        }
      }
    }
    return result != null ? result : availableBundlesMap.get((MetaLocale)CLDR.Locale.en);
  }

  /**
   * Inspect the mapping between permutations of locale fields and bundle identifiers.
   */
  public String dump() {
    StringBuilder buf = new StringBuilder();
    buf.append("            LOCALE        MATCHED BUNDLE\n");
    buf.append("            ======        ==============\n");
    List<MetaLocale> keys = availableBundlesMap.keySet().stream()
        .map(k -> (MetaLocale)k).collect(Collectors.toList()); 
    Collections.sort(keys);
    for (CLDR.Locale key : keys) {
      buf.append(String.format("%20s  ->  %s\n", key, availableBundlesMap.get(key)));
    }
    return buf.toString();
  }
  
  /**
   * Iterate over the available bundle identifiers, expand them and index all
   * permutations to enable a fast direct lookup.
   */
  private void buildMap(List<CLDR.Locale> availableBundles) {
    // Index all bundles by their original bundle identifier and the maximum
    // bundle identifier.
    List<MetaLocale> maxBundleIds = new ArrayList<>();
    for (CLDR.Locale locale : availableBundles) {
      MetaLocale source = (MetaLocale) locale;
      if (source.isRoot()) {
        continue;
      }
      
      MetaLocale maxBundleId = languageResolver.addLikelySubtags(source);
      maxBundleIds.add(maxBundleId);
      availableBundlesMap.put(source.copy(), source); 
      if (!source.equals(maxBundleId)) {
        availableBundlesMap.putIfAbsent(maxBundleId.copy(), source);
      }
    }
    
    // Iterate over the maximum bundle identifiers, indexing the permutations
    // generated by our matching order.
    for (MetaLocale bundleId : maxBundleIds) {
      indexPermutations(bundleId);
    }
  }

  /**
   * Index permutations of the given bundle identifier. We iterate over the field
   * permutations and expand each to the maximum bundle identifier, then query
   * to see if that max bundle identifier matches a bundle.  Then we index the
   * permutation to that bundle. This creates a static mapping mimicking a dynamic lookup.
   */
  private void indexPermutations(MetaLocale bundleId) {
    MetaLocale key = bundleId.copy();
    for (int flags : LanguageResolver.MATCH_ORDER) {
      LanguageResolver.set(bundleId, key, flags);
      MetaLocale maxBundleId = languageResolver.addLikelySubtags(key);
      MetaLocale source = availableBundlesMap.get(maxBundleId);
      if (source != null) {
        availableBundlesMap.putIfAbsent(key.copy(), source);
      } else if (maxBundleId.hasVariant()) {
        maxBundleId.setVariant(null);
        source = availableBundlesMap.get(maxBundleId);
        if (source != null) {
          availableBundlesMap.putIfAbsent(key.copy(), source);
        }
      }
    }
  }
  
}
