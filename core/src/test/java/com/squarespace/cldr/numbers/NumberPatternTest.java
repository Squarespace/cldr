package com.squarespace.cldr.numbers;

import static com.squarespace.cldr.numbers.NumberPattern.Symbol.*;
import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import com.squarespace.cldr.numbers.NumberPattern;
import com.squarespace.cldr.numbers.NumberPatternParser;
import com.squarespace.cldr.numbers.NumberPattern.Format;
import com.squarespace.cldr.numbers.NumberPattern.Node;
import com.squarespace.cldr.numbers.NumberPattern.Text;


public class NumberPatternTest {

  private static final String NBSP = "\u00a0";
  private static final String RLM = "\u200f";
  private static final String LPAREN = "(";
  private static final String RPAREN = ")";

  @Test
  public void testDecimalStandard() {
    assertPattern("-#,##0.###", MINUS, format(3, 0, 1, 3, 0));
    assertPattern("#,##,##0.###", format(3, 2, 1, 3, 0));
    assertPattern("-#0.######", MINUS, format(0, 0, 1, 6, 0));
  }

  @Test
  public void testDecimalPercent() {
    assertPattern("#,##0%", format(3, 0, 1, 0, 0), PERCENT);
    assertPattern("#,##0\u00a0%", format(3, 0, 1, 0, 0), text(NBSP), PERCENT);
    assertPattern("#,##,##0%", format(3, 2, 1, 0, 0), PERCENT);
    assertPattern("-%#,##0", MINUS, PERCENT, format(3, 0, 1, 0, 0));
    assertPattern("%\u00a0#,##0", PERCENT, text(NBSP), format(3, 0, 1, 0, 0));
  }

  @Test
  public void testDecimalLong() {
    assertPattern("0 billion", format(0, 0, 1, 0, 0), text(" billion"));
    assertPattern("000 thousand", format(0, 0, 3, 0, 0), text(" thousand"));
  }

  @Test
  public void testCurrencyStandard() {
    assertPattern("¤#,##0.00", CURRENCY, format(3, 0, 1, 2, 2));
    assertPattern("#,##0.00\u00a0¤", format(3, 0, 1, 2, 2), text(NBSP), CURRENCY);
    assertPattern("¤\u00a0-#,##0.00", CURRENCY, text(NBSP), MINUS, format(3, 0, 1, 2, 2));
    assertPattern("\u200f-#,##0.00¤", text(RLM), MINUS, format(3, 0, 1, 2, 2), CURRENCY);
  }

  @Test
  public void testCurrencyAccounting() {
    assertPattern("(#,##0.00\u00a0¤)", text(LPAREN), format(3, 0, 1, 2, 2), text(NBSP), CURRENCY, text(RPAREN));
    assertPattern("(#,##,##0.00¤)", text(LPAREN), format(3, 2, 1, 2, 2), CURRENCY, text(RPAREN));
  }

  private static Node text(String s) {
    return new Text(s);
  }

  private static Node format(int pgs, int sgs, int min_i, int max_f, int min_f) {
    Format node = new Format();
    node.primaryGroupingSize = pgs;
    node.secondaryGroupingSize = sgs;
    node.minimumIntegerDigits = min_i;
    node.maximumFractionDigits = max_f;
    node.minimumFractionDigits = min_f;
    return node;
  }

  private static void assertPattern(String pattern, Node... nodes) {
    NumberPatternParser parser = new NumberPatternParser();
    List<Node> expected = Arrays.asList(nodes);
    NumberPattern actual = parser.parse(pattern);
    assertEquals(actual.parsed(), expected);

    // Render and re-parse
    String rendered = actual.render();
    actual = parser.parse(rendered);
    assertEquals(actual.parsed(), expected);
  }

}
