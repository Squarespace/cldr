package com.squarespace.cldr.codegen.parse;


/**
 * Typing information for nodes in the abstract syntax tree
 * for the pluralization rules
 */
public enum PluralType {

  AND_CONDITION,
  EXPR,
  INTEGER,
  MODOP,
  OPERAND,
  OR_CONDITION,
  RANGE,
  RANGELIST,
  RELOP,
  RULE,
  SAMPLE

}
