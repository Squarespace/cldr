package com.squarespace.cldr.parse;

import java.util.ArrayList;
import java.util.List;

import com.squarespace.cldr.parse.FieldPattern.Field;
import com.squarespace.cldr.parse.FieldPattern.Node;
import com.squarespace.cldr.parse.FieldPattern.Text;

public class WrapperPatternParser {

  /**
   * Parses a date-time wrapper format, e.g. "{1} at {0}" where 1 is the date and
   * 0 is the time. See CLDR "dateTimeFormats".
   */
  public List<Node> parseWrapper(String raw) {
    // holds literal text as we parse
    StringBuilder buf = new StringBuilder();

    // indicates we're inside a quote.
    boolean inquote = false;

    // indicates we're inside a {x} tag.
    boolean intag = false;

    // length of input pattern
    int length = raw.length();

    // current input pattern index
    int i = 0;

    List<Node> nodes = new ArrayList<>();

    while (i < length) {
      char ch = raw.charAt(i);
      switch (ch) {
        case '{':
          if (buf.length() > 0) {
            nodes.add(new Text(buf.toString()));
            buf.setLength(0);
          }
          intag = true;
          break;

        case '}':
          intag = false;
          break;

        case '\'':
          if (inquote) {
            inquote = false;
          } else {
            inquote = true;
          }
          break;

        default:
          if (intag) {
            nodes.add(new Field(ch, 0));
          } else {
            buf.append(ch);
          }
      }

      i++;
    }

    if (buf.length() > 0) {
      nodes.add(new Text(buf.toString()));
    }
    return nodes;
  }


}
