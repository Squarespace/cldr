package com.squarespace.cldr;

import static com.squarespace.cldr.parse.LanguageAliasGrammar.P_LANGUAGE;
import static com.squarespace.cldr.parse.LanguageTagGrammar.P_SCRIPT;
import static com.squarespace.cldr.parse.LanguageTagGrammar.P_TERRITORY;
import static com.squarespace.cldr.parse.LanguageTagGrammar.P_VARIANT;

import com.squarespace.compiler.common.Maybe;
import com.squarespace.compiler.parse.Pair;
import com.squarespace.compiler.parse.Parser;


/**
 * Parser for a language alias which may include invalid deprecated fields.
 */
public class LanguageAlias extends MetaLocale {

  private static Parser<LanguageAlias> P_LANGUAGE_ALIAS =
      P_LANGUAGE.flatMap(l -> 
        P_SCRIPT.orDefault(null).flatMap(s ->
          P_VARIANT
            .map(v -> new LanguageAlias(string(l), string(s), null, string(v)))
          .or(
          P_TERRITORY.orDefault(null).flatMap(t ->
            P_VARIANT.orDefault(null).map(v ->
              new LanguageAlias(string(l), string(s), string(t), string(v)))))));
  
  private static String string(CharSequence s) {
    return s == null ? null : s.toString();
  }
  
  public LanguageAlias(String language, String script, String territory, String variant) {
    super(language, script, territory, variant);
  }
  
  public static LanguageAlias parse(String alias) {
    Maybe<Pair<LanguageAlias, CharSequence>> result = P_LANGUAGE_ALIAS.parse(alias);
    if (result.isNothing()) {
      throw new IllegalArgumentException("Failed to parse language alias: '" + alias + "'");
    }
    return result.get()._1;
  }
  
}
