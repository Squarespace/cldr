package com.squarespace.cldr.parse;

import static com.squarespace.cldr.parse.LanguageTagGrammar.CLASSIFIER;
import static com.squarespace.compiler.match.Recognizers.cardinality;
import static com.squarespace.compiler.match.Recognizers.charClass;
import static com.squarespace.compiler.match.Recognizers.characters;
import static com.squarespace.compiler.match.Recognizers.sequence;
import static com.squarespace.compiler.match.Recognizers.zeroOrMore;
import static com.squarespace.compiler.parse.Parser.matcher;
import static com.squarespace.compiler.text.DefaultCharClassifier.LOWERCASE;

import com.squarespace.compiler.match.Recognizers.Recognizer;
import com.squarespace.compiler.parse.Parser;


/**
 * Grammar for a deprecated locale code that is to be replaced by an alias.
 * This code may include invalid fields, like longer language subtag.
 */
public class LanguageAliasGrammar {

  private static final Recognizer R_LANG_SEGMENT =
      cardinality(charClass(LOWERCASE, CLASSIFIER), 1, 8);

  // Language aliases support grand-fathered tags that use longer language identifiers.
  private static final Recognizer R_LANGUAGE =
      sequence(R_LANG_SEGMENT,
          zeroOrMore(sequence(characters('-'), R_LANG_SEGMENT)));

  public static final Parser<CharSequence> P_LANGUAGE = matcher(R_LANGUAGE);

}
