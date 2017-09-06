package com.squarespace.cldr.codegen;

import static com.squarespace.cldr.parse.LanguageTagGrammar.P_LANGUAGE;
import static com.squarespace.cldr.parse.LanguageTagGrammar.P_SCRIPT;
import static com.squarespace.cldr.parse.LanguageTagGrammar.P_TERRITORY;
import static com.squarespace.cldr.parse.LanguageTagGrammar.P_VARIANT;

import com.google.common.base.Objects;
import com.squarespace.compiler.common.Maybe;
import com.squarespace.compiler.parse.Pair;
import com.squarespace.compiler.parse.Parser;


/**
 * Simple locale class for use during code generation.
 */
public class LocaleID implements Comparable<LocaleID> {

  // Parses a string language tag into a LocaleID object
  public static Parser<LocaleID> P_LANGUAGE_TAG =
      P_LANGUAGE.flatMap(l -> 
        P_SCRIPT.orDefault("").flatMap(s -> 
          P_TERRITORY.orDefault("").flatMap(t -> 
            P_VARIANT.orDefault("").map(v -> 
              new LocaleID(l.toString(), s.toString(), t.toString(), v.toString())))));

  public final String language;
  public final String script;
  public final String territory;
  public final String variant;

  public final String repr;
  public final String safe;
  
  public LocaleID(String language, String script, String territory, String variant) {
    this.language = language;
    this.script = script;
    this.territory = territory;
    this.variant = variant;
    this.repr = buildRepr('-');
    this.safe = buildRepr('_');
  }
  
  public static LocaleID parse(String languageTag) {
    Maybe<Pair<LocaleID, CharSequence>> result = P_LANGUAGE_TAG.parse(languageTag);
    if (result.isNothing()) {
      // This class is only used during code generation, parsing language tags
      // that are well-formed, so blow up immediately.
      throw new RuntimeException("Failed to parse language tag: '" + languageTag + "'");
    }
    return result.get()._1;
  }
  
  @Override
  public int compareTo(LocaleID o) {
    return this.safe.compareTo(o.safe);
  }
  
  @Override
  public int hashCode() {
    return safe.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof LocaleID) {
      LocaleID other = (LocaleID) obj;
      return Objects.equal(safe, other.safe);
    }
    return false;
  }
  
  @Override
  public String toString() {
    return safe;
  }

  private String buildRepr(char ch) {
    StringBuilder buf = new StringBuilder();
    buf.append(language);
    for (String value : new String[] { script, territory, variant }) {
      if (valid(value)) {
        buf.append(ch);
        buf.append(value);
      }
    }
    return buf.toString();
  }

  private boolean valid(String v) {
    return v != null && !v.isEmpty();
  }
  
}
