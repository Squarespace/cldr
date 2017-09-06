package com.squarespace.cldr;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * Language resolution is defined here as taking a potentially-incomplete locale
 * object and finding the best, fully-expanded CLDR locale by adding likely
 * subtags to "fill in the blanks". 
 *  
 * For example, the resolved locale for "en" is "en-Latn-US".  Note that the 
 * resolved locale for "en-XY" is "en-Latn-XY" since resolution does not
 * correct bad field values, it only fills in the most likely values for 
 * missing fields.
 */
class LanguageResolver {

  protected static final int LANGUAGE = 1;
  protected static final int SCRIPT = 2;
  protected static final int TERRITORY = 4;

  protected static final List<Integer> MATCH_ORDER = Arrays.asList(
      LANGUAGE | SCRIPT | TERRITORY,
      LANGUAGE | TERRITORY,
      LANGUAGE | SCRIPT,
      LANGUAGE,
      SCRIPT
  );

  private final Map<MetaLocale, MetaLocale> likelySubtagsMap;

  public LanguageResolver(Map<MetaLocale, MetaLocale> likelySubtagsMap) {
    this.likelySubtagsMap = likelySubtagsMap;
  }

  public CLDR.Locale matchLanguageTag(String tag) {
    return match(MetaLocale.fromLanguageTag(tag));
  }
  
  public CLDR.Locale matchLocale(java.util.Locale locale) {
    return match(MetaLocale.fromJavaLocale(locale));
  }
  
  protected CLDR.Locale match(MetaLocale locale) {
    return addLikelySubtags(locale);
  }
  
  /**
   * Add likely subtags, producing the max bundle ID.
   */
  protected MetaLocale addLikelySubtags(MetaLocale src) {
    // Always return a copy.
    MetaLocale dst = src.copy();

    // If the locale has all fields populated (language, script, territory)
    // then do nothing.
    if (src.hasAll()) {
      return dst;
    }

    // Build a temporary locale for matching and clear the variant since it
    // is not used for likely subtags.
    MetaLocale temp = src.copy();
    temp.setVariant(null);

    // Iterate over the match flags, from most specific to least.
    for (int flags : MATCH_ORDER) {
      set(src, temp, flags);
      MetaLocale match = likelySubtagsMap.get(temp);
      if (match != null) {
        // Use the first match we find. We only replace subtags that
        // are undefined, copying them from the matched locale.
        if (!dst.hasLanguage()) {
          dst.setLanguage(match._language());
        }
        if (!dst.hasScript()) {
          dst.setScript(match._script());
        }
        if (!dst.hasTerritory()) {
          dst.setTerritory(match._territory());
        }
        break;
      }
    }

    return dst;
  }

  /**
   * Removes all subtags that would be added by addLikelySubtags. This
   * produces the min bundle ID.
   */
  protected MetaLocale removeLikelySubtags(MetaLocale src) {
    // Using "en-Latn-US" for examples.

    // 1. match "en-Zzzz-ZZ"
    MetaLocale temp = new MetaLocale();
    temp.setLanguage(src._language());
    MetaLocale match = addLikelySubtags(temp);
    if (match.equals(src)) {
      return temp;
    }

    // 2. match "en-Zzzz-US"
    temp.setTerritory(src._territory());
    match = addLikelySubtags(temp);
    if (match.equals(src)) {
      temp.setLanguage(src._language());
      return temp;
    }

    // 3. match "en-Latn-ZZ"
    temp.setTerritory(null);
    temp.setScript(temp._script());
    match = addLikelySubtags(temp);
    if (match.equals(src)) {
      return temp;
    }

    // 4. Nothing matched, return a copy of the original.
    return src.copy();
  }

  /**
   * Set or clear fields on the destination locale according to the flags.
   */
  protected static void set(MetaLocale src, MetaLocale dst, int flags) {
    dst.setLanguage((flags & LANGUAGE) == 0 ? null : src._language());
    dst.setScript((flags & SCRIPT) == 0 ? null : src._script());
    dst.setTerritory((flags & TERRITORY) == 0 ? null : src._territory());
  }

}
