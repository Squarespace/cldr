package com.squarespace.cldr.codegen.parse;

import java.util.List;

import com.squarespace.compiler.parse.Node;


/**
 * Restores human-readable plural rule representations for commenting
 * generated code.
 */
public class PluralRulePrinter {

  /**
   * Return a recursive representation of the given pluralization node or tree.
   */
  public static String print(Node<PluralType> node) {
    StringBuilder buf = new StringBuilder();
    print(buf, node);
    return buf.toString();
  }

  /**
   * Recursively visit the structs and atoms in the pluralization node or tree,
   * appending the representations to a string buffer.
   */
  private static void print(StringBuilder buf, Node<PluralType> node) {
    switch (node.type()) {
      case RULE:
        join(buf, node, " ");
        break;

      case AND_CONDITION:
        join(buf, node, " and ");
        break;

      case OR_CONDITION:
        join(buf, node, " or ");
        break;

      case RANGELIST:
        join(buf, node, ",");
        break;

      case RANGE:
        join(buf, node, "..");
        break;

      case EXPR:
        join(buf, node, " ");
        break;

      case MODOP:
        buf.append("% ").append(node.asAtom().value());
        break;

      case INTEGER:
      case OPERAND:
      case RELOP:
      case SAMPLE:
        buf.append(node.asAtom().value());
        break;
    }
  }

  /**
   * Print each child node of a struct, joining them together with a
   * delimiter string.
   */
  private static void join(StringBuilder buf, Node<PluralType> parent, String delimiter) {
    List<Node<PluralType>> nodes = parent.asStruct().nodes();
    int size = nodes.size();
    for (int i = 0; i < size; i++) {
      if (i > 0) {
        buf.append(delimiter);
      }
      print(buf, nodes.get(i));
    }
  }

}
