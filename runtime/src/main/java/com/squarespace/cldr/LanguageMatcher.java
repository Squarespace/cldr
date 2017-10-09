package com.squarespace.cldr;

import static com.squarespace.cldr.DistanceTable.DEFAULT_THRESHOLD;
import static com.squarespace.cldr.DistanceTable.MAX_DISTANCE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
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
  private final List<Entry> supportedLocales;
  private Entry firstEntry;
  
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
    
    this.firstEntry = supportedLocales.get(0);
    
    this.supportedLocales.sort((a, b) -> {
      // Make sure we keep the first locale in position, since it also serves as the default.
      if (a.equals(firstEntry)) {
        return -1;
      }
      if (b.equals(firstEntry)) {
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

    for (Entry match : supportedLocales) {
      List<String> bundles = exactMatch.get(match.locale);
      if (bundles == null) {
        bundles = new ArrayList<>();
        exactMatch.put(match.locale, bundles);
      }
      bundles.add(match.bundleId);
    }
  }

  /**
   * Return the best match that exceeds the default threshold.
   * If the default is returned its distance is set to MAX_DISTANCE.
   */
  public Match match(String desiredRaw) {
    return matchImpl(parse(desiredRaw), DEFAULT_THRESHOLD);
  }
  
  /**
   * Return the best match that exceeds the threshold.
   * If the default is returned its distance is set to MAX_DISTANCE.
   */
  public Match match(String desiredRaw, int threshold) {
    return matchImpl(parse(desiredRaw), threshold);
  }
  
  /**
   * Return the best match that exceeds the default threshold.
   * If the default is returned its distance is set to MAX_DISTANCE.
   */
  public Match match(List<String> desired) {
    return matchImpl(parse(desired), DEFAULT_THRESHOLD);
  }
  
  /**
   * Return the best match that exceeds the threshold.
   * If the default is returned its distance is set to MAX_DISTANCE.
   */
  public Match match(List<String> desired, int threshold) {
    return matchImpl(parse(desired), threshold);
  }
  
  /**
   * Return all matches and their distances, if they exceed the default threshold.
   * If no match exceeds the threshold this returns an empty list.
   */
  public List<Match> matches(String desiredRaw) {
    return matchesImpl(parse(desiredRaw), DEFAULT_THRESHOLD);
  }
  
  /**
   * Return all matches and their distances, if they exceed the given threshold.
   * If no match exceeds the threshold this returns an empty list.
   */
  public List<Match> matches(String desiredRaw, int threshold) {
    return matchesImpl(parse(desiredRaw), threshold);
  }
  
  /**
   * Return all matches and their distances, if they exceed the default threshold.
   * If no match exceeds the threshold this returns an empty list.
   */
  public List<Match> matches(List<String> desired) {
    return matchesImpl(parse(desired), DEFAULT_THRESHOLD);
  }
  
  /**
   * Return all matches and their distances, if they exceed the given threshold.
   * If no match exceeds the threshold this returns an empty list.
   */
  public List<Match> matches(List<String> desired, int threshold) {
    return matchesImpl(parse(desired), threshold);
  }
  
  /**
   * Return the best match that exceeds the threshold, or the default.
   * If the default is returned its distance is set to MAX_DISTANCE.
   * An exact match has distance of 0.
   */
  private Match matchImpl(List<Entry> desiredLocales, int threshold) {
    int bestDistance = MAX_DISTANCE;
    Entry bestMatch = null;
    for (Entry desired : desiredLocales) {
      List<String> exact = exactMatch.get(desired.locale);
      if (exact != null) {
        return new Match(exact.get(0), 0);
      }
      for (Entry supported : supportedLocales) {
        int distance = DISTANCE_TABLE.distance(desired.locale, supported.locale, threshold);
        if (distance < bestDistance) {
          bestDistance = distance;
          bestMatch = supported;
        }
      }
    }
    return bestMatch == null 
        ? new Match(firstEntry.bundleId, MAX_DISTANCE)
        : new Match(bestMatch.bundleId, bestDistance);
  }
  
  /**
   * Return all matches and their distances, if they exceed the given threshold.
   * If no match exceeds the threshold this returns an empty list.
   * Exact matches have distance of 0. Each distinct bundleId should appear at
   * most once in the results, even if their meaning is equivalent, e.g. "en" and
   * "en-US" are distinct but mean the same, and both will appear in the results.
   */
  private List<Match> matchesImpl(List<Entry> desiredLocales, int threshold) {
    int bestDistance = MAX_DISTANCE;

    // TreeMap is used to maintain the original ordering captured in the
    // supportedLocales list. Even though we sort the values before returning,
    // the stable sort order would change due to passing through a hashing function.
    Map<String, Match> result = new TreeMap<>();
    for (Entry desired : desiredLocales) {
      List<String> exact = exactMatch.get(desired.locale);
      if (exact != null) {
        updateMatch(result, exact.get(0), 0);
      }
      
      for (Entry supported : supportedLocales) {
        int distance = DISTANCE_TABLE.distance(desired.locale, supported.locale, threshold);
        if (distance < bestDistance) {
          updateMatch(result, supported.bundleId, distance);
        }
      }
    }
    
    // Stable sort, so an earlier match with equal distance will come first.
    return result.values().stream()
        .sorted((a, b) -> Integer.compare(a.distance, b.distance))
        .collect(Collectors.toList());
  }
  
  private static void updateMatch(Map<String, Match> map, String key, int distance) {
    Match m = map.get(key);
    if (m == null) {
      map.put(key, new Match(key, distance));
    } else if (distance < m.distance) {
      m.distance = distance;
    }
  }
  
  private static List<Entry> parse(String locales) {
    return convert(Arrays.stream(COMMA_SPACE.split(locales)));
  }
  
  private static List<Entry> parse(List<String> locales) {
    Stream<String> stream = locales.stream()
        .flatMap(COMMA_SPACE::splitAsStream);
    return convert(stream);
  }
  
  private static List<Entry> convert(Stream<String> stream) {
    return stream.map(s -> s.trim())
        .filter(s -> !s.isEmpty())
        .map(s -> new Entry(s, CLDR_INSTANCE.resolve(s)))
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

  public static class Match {
    
    private final String bundleId;
    private int distance;
    
    private Match(String bundleId, int distance) {
      this.bundleId = bundleId;
      this.distance = distance;
    }
    
    public String bundleId() {
      return bundleId;
    }
    
    public int distance() {
      return distance;
    }
    
    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Match) {
        Match other = (Match) obj;
        return bundleId.equals(other.bundleId) || distance == other.distance;
      }
      return false;
    }

    @Override
    public int hashCode() {
      return Objects.hash(bundleId, distance);
    }
    
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append("Match(").append(bundleId).append(", ").append(distance).append(')');
      return buf.toString();
    }
  }
  
  private static class Entry {
    
    private final String bundleId;
    private final CLDR.Locale locale;
    
    public Entry(String bundleId, CLDR.Locale locale) {
      this.bundleId = bundleId;
      this.locale = locale;
    }
    
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append("Entry(").append(bundleId).append(", ").append(locale).append(')');
      return buf.toString();
    }
  }
  
}
