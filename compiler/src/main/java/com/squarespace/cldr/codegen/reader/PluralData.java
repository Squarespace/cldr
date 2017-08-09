package com.squarespace.cldr.codegen.reader;

import java.util.LinkedHashMap;
import java.util.Map;

import com.squarespace.cldr.codegen.parse.PluralType;
import com.squarespace.compiler.parse.Node;
import com.squarespace.compiler.parse.Struct;


/**
 * Container to hold CLDR pluralization rules, either ordinal or cardinal.
 * Used during code generation.
 */
public class PluralData {

  private final Map<String, Rule> rules = new LinkedHashMap<>();

  public void add(String category, Rule rule) {
    rules.put(category, rule);
  }

  public Map<String, Rule> rules() {
    return rules;
  }

  @Override
  public String toString() {
    return rules.toString();
  }

  public static class Rule {

    public final String raw;

    public final Struct<PluralType> condition;

    public final String sample;

    public Rule(String raw, Node<PluralType> condition, String sample) {
      this.raw = raw;
      this.condition = condition == null ? null : condition.asStruct();
      this.sample = sample;
    }
  }

}
