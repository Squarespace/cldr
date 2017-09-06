package com.squarespace.cldr.parse;

import static com.squarespace.compiler.match.Recognizers.cardinality;
import static com.squarespace.compiler.match.Recognizers.charClass;
import static com.squarespace.compiler.match.Recognizers.characters;
import static com.squarespace.compiler.match.Recognizers.choice;
import static com.squarespace.compiler.match.Recognizers.literal;
import static com.squarespace.compiler.match.Recognizers.sequence;
import static com.squarespace.compiler.parse.Parser.matcher;
import static com.squarespace.compiler.text.DefaultCharClassifier.DIGIT;
import static com.squarespace.compiler.text.DefaultCharClassifier.LOWERCASE;
import static com.squarespace.compiler.text.DefaultCharClassifier.UPPERCASE;

import com.squarespace.compiler.parse.Parser;
import com.squarespace.compiler.text.CharClassifier;
import com.squarespace.compiler.text.DefaultCharClassifier;


/**
 * Simplified grammar for parsing well-formed language tags with no extensions.
 */
public class LanguageTagGrammar {

  private static final CharClassifier CLASSIFIER = new DefaultCharClassifier();

  // Language tag separator character
  public static Parser<CharSequence> P_SEP =
      matcher(characters('-'));

  // ISO 639 code
  public static Parser<CharSequence> P_LANGUAGE =
      matcher(choice(
          cardinality(charClass(LOWERCASE, CLASSIFIER), 2, 3),
          literal("root")));

  // ISO 15924 code
  public static Parser<CharSequence> P_SCRIPT =
      P_SEP.flatMap(s -> matcher(
          sequence(
              charClass(UPPERCASE, CLASSIFIER),
              cardinality(charClass(LOWERCASE, CLASSIFIER), 3, 3))));

  // ISO 3166-1 code or UN M.49 code
  public static Parser<CharSequence> P_TERRITORY =
      P_SEP.flatMap(s -> matcher(
          choice(
              cardinality(charClass(UPPERCASE, CLASSIFIER), 2, 2),
              cardinality(charClass(DIGIT, CLASSIFIER), 3, 3))));

  // Registered variants
  public static Parser<CharSequence> P_VARIANT =
      P_SEP.flatMap(s -> matcher(
          cardinality(charClass(LOWERCASE | UPPERCASE | DIGIT, CLASSIFIER), 5, 8)));

}
