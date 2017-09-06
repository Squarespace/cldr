package com.squarespace.cldr.codegen.parse;

import static com.squarespace.cldr.codegen.parse.PluralRuleGrammar.P_AND_CONDITION;
import static com.squarespace.cldr.codegen.parse.PluralRuleGrammar.P_COMMA;
import static com.squarespace.cldr.codegen.parse.PluralRuleGrammar.P_EXPR;
import static com.squarespace.cldr.codegen.parse.PluralRuleGrammar.P_INTEGER;
import static com.squarespace.cldr.codegen.parse.PluralRuleGrammar.P_MODOP;
import static com.squarespace.cldr.codegen.parse.PluralRuleGrammar.P_RANGE;
import static com.squarespace.cldr.codegen.parse.PluralRuleGrammar.P_RANGELIST;
import static com.squarespace.cldr.codegen.parse.PluralRuleGrammar.P_RULE;
import static com.squarespace.cldr.codegen.parse.PluralRuleGrammar.P_SAMPLE;
import static com.squarespace.cldr.codegen.parse.PluralRuleGrammar.P_SPACE;
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
import static com.squarespace.compiler.parse.Atom.atom;
import static com.squarespace.compiler.parse.Struct.struct;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.apache.commons.text.StringEscapeUtils;
import org.testng.annotations.Test;

import com.squarespace.compiler.common.Maybe;
import com.squarespace.compiler.parse.Pair;
import com.squarespace.compiler.parse.Parser;


public class PluralRuleGrammarTest {

  @Test
  public void testParseSpace() {
    assertParse(P_SPACE, " \n\t\r ", " \n\t\r ");
  }

  @Test
  public void testParseComma() {
    assertParse(P_COMMA, ",", ",");
    assertParse(P_COMMA, "  \r\n  ,", ",");
  }

  @Test
  public void testParseInteger() {
    assertParse(P_INTEGER, "1", atom(INTEGER, 1));
    assertParse(P_INTEGER, "  1", atom(INTEGER, 1));
    assertParse(P_INTEGER, "  12345", atom(INTEGER, 12345));
  }

  @Test
  public void testParseModop() {
    assertParse(P_MODOP, "% 100",
        atom(MODOP, 100));
  }

  @Test
  public void testParseRange() {
    assertParse(P_RANGE, "5..9",
        struct(RANGE,
            atom(INTEGER, 5),
            atom(INTEGER, 9)));
  }

  @Test
  public void testParseRangeList() {
    assertParse(P_RANGELIST, "2,3,5,7..10",
        struct(RANGELIST,
            atom(INTEGER, 2),
            atom(INTEGER, 3),
            atom(INTEGER, 5),
            struct(RANGE,
                atom(INTEGER, 7),
                atom(INTEGER, 10))));
  }

  @Test
  public void testParseExpression() {
    assertParse(P_EXPR, "v % 10 != 1",
        struct(EXPR,
            atom(OPERAND, "v"),
            atom(MODOP, 10),
            atom(RELOP, "!="),
            struct(RANGELIST,
                atom(INTEGER, 1))));

    assertParse(P_EXPR, "n = 2",
        struct(EXPR,
            atom(OPERAND, "n"),
            atom(RELOP, "="),
            struct(RANGELIST,
                atom(INTEGER, 2))));
  }

  @Test
  public void testParseAndCondition() {
    assertParse(P_AND_CONDITION, "n = 1,2,4..5 and v != 0 and i = 2",
        struct(AND_CONDITION,
            struct(EXPR,
                atom(OPERAND, "n"),
                atom(RELOP, "="),
                struct(RANGELIST,
                    atom(INTEGER, 1),
                    atom(INTEGER, 2),
                    struct(RANGE,
                        atom(INTEGER, 4),
                        atom(INTEGER, 5)))),
            struct(EXPR,
                atom(OPERAND, "v"),
                atom(RELOP, "!="),
                struct(RANGELIST,
                    atom(INTEGER, 0))),
            struct(EXPR,
                atom(OPERAND, "i"),
                atom(RELOP, "="),
                struct(RANGELIST,
                    atom(INTEGER, 2)))));
  }

  @Test
  public void testParseRule() {
    assertParse(P_RULE, "v = 1 and n != 2,3 or v = 3..5",
        struct(RULE,
            struct(OR_CONDITION,
              struct(AND_CONDITION,
                  struct(EXPR,
                      atom(OPERAND, "v"),
                      atom(RELOP, "="),
                      struct(RANGELIST,
                          atom(INTEGER, 1))),
                  struct(EXPR,
                      atom(OPERAND, "n"),
                      atom(RELOP, "!="),
                      struct(RANGELIST,
                          atom(INTEGER, 2),
                          atom(INTEGER, 3)))),
              struct(AND_CONDITION,
                  struct(EXPR,
                      atom(OPERAND, "v"),
                      atom(RELOP, "="),
                      struct(RANGELIST,
                          struct(RANGE,
                              atom(INTEGER, 3),
                              atom(INTEGER, 5))))))));
  }

  @Test
  public void testParseRuleSamples() {
    assertParse(P_SAMPLE, "", null);
    assertParse(P_SAMPLE, " \n\t @integer 1, 21, 31, …",
        atom(SAMPLE, "@integer 1, 21, 31, …"));
  }

  @Test
  public void testParseRuleEmpty() {
    assertParse(P_RULE, " \n", struct(RULE));
  }

  @Test
  public void testParseRuleIgnoreSamples() {
    String input = "v = 0 and i % 10 = 1 and i % 100 != 11 @integer 1, 21, 31, 41, 51, 61, 71, 81, 101, 1001, …";
    assertParse(P_RULE, input,
        struct(RULE,
            struct(OR_CONDITION,
              struct(AND_CONDITION,
                  struct(EXPR,
                      atom(OPERAND, "v"),
                      atom(RELOP, "="),
                      struct(RANGELIST,
                          atom(INTEGER, 0))),
                  struct(EXPR,
                      atom(OPERAND, "i"),
                      atom(MODOP, 10),
                      atom(RELOP, "="),
                      struct(RANGELIST,
                          atom(INTEGER, 1))),
                  struct(EXPR,
                      atom(OPERAND, "i"),
                      atom(MODOP, 100),
                      atom(RELOP, "!="),
                      struct(RANGELIST,
                          atom(INTEGER, 11))))),
            atom(SAMPLE, "@integer 1, 21, 31, 41, 51, 61, 71, 81, 101, 1001, …")));
  }

  public static <T> void assertParse(Parser<T> parser, String input, T expected) {
    Maybe<Pair<T, CharSequence>> result = parser.parse(input);
    assertTrue(result.isJust(), failed(input));
    assertEquals(result.get()._1, expected);
    assertEquals(result.get()._2, "");
  }

  private static String failed(String input) {
    return String.format("parse failed for input: \"%s\"", StringEscapeUtils.escapeJava(input));
  }
}
