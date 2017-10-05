package com.squarespace.cldr.parse;

import static com.squarespace.compiler.match.Recognizers.charClass;
import static com.squarespace.compiler.match.Recognizers.characters;
import static com.squarespace.compiler.match.Recognizers.choice;
import static com.squarespace.compiler.match.Recognizers.oneOrMore;
import static com.squarespace.compiler.match.Recognizers.sequence;
import static com.squarespace.compiler.match.Recognizers.zeroOrOne;
import static com.squarespace.compiler.parse.Parser.matcher;
import static com.squarespace.compiler.text.DefaultCharClassifier.LOWERCASE;
import static com.squarespace.compiler.text.DefaultCharClassifier.UPPERCASE;

import com.squarespace.cldr.LanguageMatch;
import com.squarespace.cldr.LanguageMatch.Pattern;
import com.squarespace.compiler.common.Maybe;
import com.squarespace.compiler.match.Recognizers.Recognizer;
import com.squarespace.compiler.parse.Pair;
import com.squarespace.compiler.parse.Parser;
import com.squarespace.compiler.text.CharClassifier;
import com.squarespace.compiler.text.DefaultCharClassifier;


/**
 * Grammar to parse a language match rule. Used internally only.
 */
public class LanguageMatchGrammar {

  private static final CharClassifier CLASSIFIER = new DefaultCharClassifier();

  private static final Recognizer R_WILDCARD = characters('*');

  private static final Recognizer R_LANGUAGE =
      choice(R_WILDCARD, LanguageTagGrammar.R_LANGUAGE);

  private static final Recognizer R_SCRIPT =
      choice(R_WILDCARD, LanguageTagGrammar.R_SCRIPT);

  private static final Recognizer R_VARIABLE =
      sequence(
          characters('$'),
          zeroOrOne(characters('!')),
          oneOrMore(charClass(UPPERCASE | LOWERCASE, CLASSIFIER)));

  private static final Recognizer R_TERRITORY =
      choice(R_WILDCARD, LanguageTagGrammar.R_TERRITORY, R_VARIABLE);

  private static Parser<CharSequence> P_SEP = matcher(characters('_'));
  private static Parser<CharSequence> P_LANGUAGE = matcher(R_LANGUAGE);
  private static Parser<CharSequence> P_SCRIPT = P_SEP.flatMap(s -> matcher(R_SCRIPT));
  private static Parser<CharSequence> P_TERRITORY = P_SEP.flatMap(s -> matcher(R_TERRITORY));

  private static Parser<LanguageMatch.Pattern> P_LANGUAGE_MATCH =
      P_LANGUAGE.flatMap(l ->
        P_SCRIPT.orDefault(null).flatMap(s ->
          P_TERRITORY.orDefault(null).map(t ->
            new Pattern(string(l), string(s), string(t)))));

  public static Pattern parse(String raw) {
    Maybe<Pair<Pattern, CharSequence>> res = P_LANGUAGE_MATCH.parse(raw);
    String suffix = res.isNothing() ? null : res.get()._2.toString();
    if (suffix == null || !suffix.isEmpty()) {
      throw new IllegalArgumentException("Parse of '" + raw + "' failed: " +
          (res.isNothing() ? "invalid" : "incomplete: " + suffix));
    }
    return res.get()._1;
  }

  private static String string(CharSequence s) {
    return s == null ? null : s.toString();
  }

}
