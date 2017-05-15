package com.squarespace.cldr.codegen.parse;

import static com.squarespace.cldr.codegen.parse.PluralType.AND_CONDITION;
import static com.squarespace.cldr.codegen.parse.PluralType.EXPR;
import static com.squarespace.cldr.codegen.parse.PluralType.INTEGER;
import static com.squarespace.cldr.codegen.parse.PluralType.MODOP;
import static com.squarespace.cldr.codegen.parse.PluralType.OPERAND;
import static com.squarespace.cldr.codegen.parse.PluralType.OR_CONDITION;
import static com.squarespace.cldr.codegen.parse.PluralType.RANGE;
import static com.squarespace.cldr.codegen.parse.PluralType.RANGELIST;
import static com.squarespace.cldr.codegen.parse.PluralType.RELOP;
import static com.squarespace.cldr.codegen.parse.PluralType.RULE;
import static com.squarespace.cldr.codegen.parse.PluralType.SAMPLE;
import static com.squarespace.compiler.match.Recognizers.any;
import static com.squarespace.compiler.match.Recognizers.characters;
import static com.squarespace.compiler.match.Recognizers.choice;
import static com.squarespace.compiler.match.Recognizers.digits;
import static com.squarespace.compiler.match.Recognizers.literal;
import static com.squarespace.compiler.match.Recognizers.oneOrMore;
import static com.squarespace.compiler.match.Recognizers.sequence;
import static com.squarespace.compiler.match.Recognizers.whitespace;
import static com.squarespace.compiler.match.Recognizers.zeroOrMore;
import static com.squarespace.compiler.parse.Atom.atom;
import static com.squarespace.compiler.parse.Parser.matcher;
import static com.squarespace.compiler.parse.Struct.struct;

import com.squarespace.compiler.common.Maybe;
import com.squarespace.compiler.match.Recognizers.Recognizer;
import com.squarespace.compiler.parse.Node;
import com.squarespace.compiler.parse.Pair;
import com.squarespace.compiler.parse.Parser;


/**
 * Recursive descent parser for a subset of the CLDR plural rules defined here:
 * http://www.unicode.org/reports/tr35/tr35-numbers.html#Plural_rules_syntax
 */
public class PluralRuleGrammar {

  public static Parser<String> P_SPACE =
      matcher(zeroOrMore(whitespace()));

  public static Parser<String> P_COMMA =
      matcher(characters(',')).prefix(P_SPACE);

  public static Parser<String> P_AND =
      matcher(literal("and")).prefix(P_SPACE);

  public static Parser<String> P_OR =
      matcher(literal("or")).prefix(P_SPACE);

  public static Parser<String> P_ELLIPSES =
      matcher(literal(".."));

  public static Parser<Node<PluralType>> P_OPERAND =
      matcher(characters('n', 'i', 'v', 'w', 'f', 't')).prefix(P_SPACE)
          .map(v -> atom(OPERAND, v));

  public static Parser<Node<PluralType>> P_RELOP =
      matcher(choice(characters('='), literal("!="))).prefix(P_SPACE)
          .map(v -> atom(RELOP, v));

  public static Recognizer M_DIGITS = digits();

  public static Parser<Node<PluralType>> P_INTEGER =
      matcher(M_DIGITS).prefix(P_SPACE)
          .map(v -> atom(INTEGER, toInteger(v)));

  public static Parser<Node<PluralType>> P_MODOP =
      matcher(characters('%')).prefix(P_SPACE).flatMap(o -> matcher(M_DIGITS).prefix(P_SPACE)
          .map(v -> atom(MODOP, toInteger(v))));

  public static Parser<Node<PluralType>> P_RANGE =
      P_INTEGER.prefix(P_SPACE).flatMap(i1 -> P_INTEGER.prefix(P_ELLIPSES)
          .map(i2 -> struct(RANGE, i1, i2)));

  public static Parser<Node<PluralType>> P_RANGELIST =
      P_RANGE.or(P_INTEGER).separated(P_COMMA).prefix(P_SPACE)
          .map(v -> struct(RANGELIST, v));

  public static Parser<Node<PluralType>> P_EXPR =
      P_OPERAND.flatMap(o -> P_MODOP.orDefault(null).flatMap(m -> P_RELOP.flatMap(op -> P_RANGELIST
          .map(r -> m == null ? struct(EXPR, o, op, r) : struct(EXPR, o, m, op, r)))));

  public static Parser<Node<PluralType>> P_AND_CONDITION =
      P_EXPR.separated(P_AND)
          .map(v -> struct(AND_CONDITION, v));

  public static Parser<Node<PluralType>> P_OR_CONDITION =
      P_AND_CONDITION.separated(P_OR).orDefault(null).prefix(P_SPACE)
          .map(c -> c == null ? null : struct(OR_CONDITION, c));

  private static Recognizer M_SAMPLE =
      sequence(choice(literal("@integer"), literal("@decimal")), oneOrMore(any()));

  public static Parser<Node<PluralType>> P_SAMPLE =
      matcher(M_SAMPLE).orDefault(null).prefix(P_SPACE)
          .map(s -> s == null ? null : atom(SAMPLE, s));

  public static Parser<Node<PluralType>> P_RULE =
      P_OR_CONDITION.orDefault(null).flatMap(c -> P_SAMPLE.orDefault(null)
          .map(s -> struct(RULE).asStruct().addNotNull(c, s)));

  /**
   * Parse a plural rule into an abstract syntax tree.
   */
  public static Maybe<Pair<Node<PluralType>, String>> parse(String input) {
    return P_RULE.parse(input);
  }

  /**
   * Quick string to integer conversion.
   */
  private static int toInteger(CharSequence seq) {
    int len = seq.length();
    int n = 0;
    int i = 0;
    while (i < len) {
      char c = seq.charAt(i);
      if (c >= '0' && c <= '9') {
        n *= 10;
        n += (int)(c - '0');
      } else {
        break;
      }
      i++;
    }
    return n;
  }

}
