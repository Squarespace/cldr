package com.squarespace.cldr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Implements enhanced language matching:
 * http://www.unicode.org/reports/tr35/tr35.html#EnhancedLanguageMatching
 *
 * Given a list of application supported locales, and a list of a user's
 * desired locales (sorted in order of preference descending), return the
 * application locale that best matches the user's request.
 */
public class LanguageMatcher {

  private static final CLDR CLDR_INSTANCE = CLDR.get();
  private static final Map<CLDR.Locale, Integer> PARADIGM_LOCALES = buildParadigmLocales();
  
  private static final DistanceTable DISTANCE_TABLE = DistanceTable.get();
  
  private final Set<CLDR.Locale> exactMatch;
  private final List<CLDR.Locale> supportedLocales; 
  
  public LanguageMatcher(String supportedLocales) {
    this(parse(supportedLocales));
  }
  
  private LanguageMatcher(List<CLDR.Locale> supportedLocales) {
    this.exactMatch = new HashSet<>(supportedLocales);
    this.supportedLocales = new ArrayList<>(supportedLocales);
    
    if (supportedLocales.isEmpty()) {
      throw new IllegalArgumentException("You must provide at least one supported locale.");
    }
    
    CLDR.Locale first = supportedLocales.get(0);
    
    this.supportedLocales.sort((a, b) -> {
      // Make sure we keep the first locale in position, since it also serves as the default.
      if (a.equals(first)) {
        return -1;
      }
      if (b.equals(first)) {
        return 1;
      }
      
      // Sort all paradigm locales to the front.
      Integer pa = PARADIGM_LOCALES.get(a);
      Integer pb = PARADIGM_LOCALES.get(b);
      if (pa != null) {
        return pb == null ? -1 : Integer.compare(pa, pb);
      } else if (pb != null) {
        return 1;
      }
      
      // Leave all other locales in their relative positions.
      return 0;
    });
  }

  public CLDR.Locale match(String desiredRaw) {
    return match(parse(desiredRaw));
  }
  
  private CLDR.Locale match(List<CLDR.Locale> desiredLocales) {
    int bestDistance = 100;
    CLDR.Locale bestMatch = null;
    for (CLDR.Locale desired : desiredLocales) {
      if (exactMatch.contains(desired)) {
        return desired;
      }
      for (CLDR.Locale supported : supportedLocales) {
        int distance = DISTANCE_TABLE.distance(desired, supported);
        if (distance < bestDistance) {
          bestDistance = distance;
          bestMatch = supported;
        }
      }
    }
    return bestMatch == null ? supportedLocales.get(0) : bestMatch;
  }
  
  private static List<CLDR.Locale> parse(String locales) {
    return Arrays.stream(locales.split("(,|\\s+)"))
        .map(s -> s.trim())
        .filter(s -> !s.isEmpty())
        .map(CLDR_INSTANCE::resolve)
        .collect(Collectors.toList());
  }
  
  private static Map<CLDR.Locale, Integer> buildParadigmLocales() {
    Map<CLDR.Locale, Integer> res = new HashMap<>();
    int size = _LanguageRules.PARADIGM_LOCALES.size();
    for (int i = 0; i < size; i++) {
      CLDR.Locale locale = CLDR_INSTANCE.resolve(_LanguageRules.PARADIGM_LOCALES.get(i));
      res.put(locale, i);
    }
    return res;
  }

}
