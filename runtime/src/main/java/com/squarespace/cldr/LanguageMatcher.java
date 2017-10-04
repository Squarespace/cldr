package com.squarespace.cldr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Implements enhanced language matching:
 * http://www.unicode.org/reports/tr35/tr35.html#EnhancedLanguageMatching
 *
 * Given a list of application supported locales, and a list of a user's
 * desired locales (sorted in order of preference descending), return the
 * application locale that best matches the user's request.
 */
public class LanguageMatcher {

  private static final Pattern COMMA_SPACE = Pattern.compile("[,\\s]+");
  
  private static final CLDR CLDR_INSTANCE = CLDR.get();
  private static final Map<CLDR.Locale, Integer> PARADIGM_LOCALES = buildParadigmLocales();
  
  private static final DistanceTable DISTANCE_TABLE = DistanceTable.get();
  
  private final Map<CLDR.Locale, List<String>> exactMatch = new HashMap<>();
  private final List<Match> supportedLocales;
  
  public LanguageMatcher(String supportedLocales) {
    this.supportedLocales = parse(supportedLocales);
    init();
  }
  
  public LanguageMatcher(List<String> supportedLocales) {
    this.supportedLocales = parse(supportedLocales);
    init();
  }
  
  private void init() {
    if (supportedLocales.isEmpty()) {
      throw new IllegalArgumentException("You must provide at least one supported locale.");
    }
    
    Match first = supportedLocales.get(0);
    
    this.supportedLocales.sort((a, b) -> {
      // Make sure we keep the first locale in position, since it also serves as the default.
      if (a.equals(first)) {
        return -1;
      }
      if (b.equals(first)) {
        return 1;
      }
      
      // Sort all paradigm locales to the front.
      Integer pa = PARADIGM_LOCALES.get(a.locale);
      Integer pb = PARADIGM_LOCALES.get(b.locale);
      if (pa != null) {
        return pb == null ? -1 : Integer.compare(pa, pb);
      } else if (pb != null) {
        return 1;
      }
      
      // Leave all other locales in their relative positions.
      return 0;
    });

    for (Match match : supportedLocales) {
      List<String> bundles = exactMatch.get(match.locale);
      if (bundles == null) {
        bundles = new ArrayList<>();
        exactMatch.put(match.locale, bundles);
      }
      bundles.add(match.bundleId);
    }
  }

  public String match(String desiredRaw) {
    return matchImpl(parse(desiredRaw));
  }
  
  public String match(List<String> desired) {
    return matchImpl(parse(desired));
  }
  
  private String matchImpl(List<Match> desiredLocales) {
    int bestDistance = 100;
    Match bestMatch = null;
    for (Match desired : desiredLocales) {
      List<String> exact = exactMatch.get(desired.locale);
      if (exact != null) {
        return exact.get(0);
      }
      for (Match supported : supportedLocales) {
        int distance = DISTANCE_TABLE.distance(desired.locale, supported.locale);
        if (distance < bestDistance) {
          bestDistance = distance;
          bestMatch = supported;
        }
      }
    }
    return bestMatch == null ? supportedLocales.get(0).bundleId : bestMatch.bundleId;
  }
  
  private static List<Match> parse(String locales) {
    return convert(Arrays.stream(COMMA_SPACE.split(locales)));
  }
  
  private static List<Match> parse(List<String> locales) {
    Stream<String> stream = locales.stream()
        .flatMap(COMMA_SPACE::splitAsStream);
    return convert(stream);
  }
  
  private static List<Match> convert(Stream<String> stream) {
    return stream.map(s -> s.trim())
        .filter(s -> !s.isEmpty())
        .map(s -> new Match(s, CLDR_INSTANCE.resolve(s)))
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

  private static class Match {
    
    private final String bundleId;
    private final CLDR.Locale locale;
    
    public Match(String bundleId, CLDR.Locale locale) {
      this.bundleId = bundleId;
      this.locale = locale;
    }
    
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append("Match(id=").append(bundleId).append(", max=").append(locale).append(')');
      return buf.toString();
    }
  }
  
}
