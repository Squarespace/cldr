package com.squarespace.cldr.parse;

import java.util.ArrayList;
import java.util.List;

import com.squarespace.cldr.parse.FieldPattern.Field;
import com.squarespace.cldr.parse.FieldPattern.Node;
import com.squarespace.cldr.parse.FieldPattern.Text;


/**
 * http://www.unicode.org/reports/tr35/tr35-dates.html#Date_Field_Symbol_Table
 */
public class DateTimePatternParser {

  /**
   * Parses a CLDR date-time pattern into a series of Text and Field nodes.
   */
  public List<Node> parse(String raw) {
    // holds literal text as we parse
    StringBuilder buf = new StringBuilder();

    // current field character, \0 if none
    char field = '\0';

    // current field width
    int width = 0;

    // flag indicating we're inside a literal string
    boolean inquote = false;

    // length of input pattern
    int length = raw.length();

    // current input pattern index
    int i = 0;

    List<Node> nodes = new ArrayList<>();

    while (i < length) {
      char ch = raw.charAt(i);

      // Handle appending / terminating the single-quote delimited sequence.
      if (inquote) {
        if (ch == '\'') {
          inquote = false;
          field = '\0';
        } else {
          buf.append(ch);
        }
        i++;
        continue;
      }

      switch (ch) {

        // Date pattern field characters.
        case 'G':
        case 'y':
        case 'Y':
        case 'u':
        case 'U':
        case 'r':
        case 'Q':
        case 'q':
        case 'M':
        case 'L':
        case 'l':
        case 'w':
        case 'W':
        case 'd':
        case 'D':
        case 'F':
        case 'g':
        case 'E':
        case 'e':
        case 'c':
        case 'a':
        case 'b':
        case 'B':
        case 'h':
        case 'H':
        case 'K':
        case 'k':
        case 'j':
        case 'J':
        case 'C':
        case 'm':
        case 's':
        case 'S':
        case 'A':
        case 'z':
        case 'Z':
        case 'O':
        case 'v':
        case 'V':
        case 'X':
        case 'x':
          // Before we start this field, check if we have text to push.
          if (buf.length() > 0) {
            nodes.add(new Text(buf.toString()));
            buf.setLength(0);
          }

          // A change in character indicates we're starting a new field;
          // otherwise we're widening an existing field.
          if (ch != field) {

            // If we have a field defined, add it.
            if (field != '\0') {
              nodes.add(new Field(field, width));
            }

            // Start a new field.
            field = ch;
            width = 1;

          } else {
            // Widen the current field.
            width++;
          }

          // Indicate we've started a new field.
          break;

        default:
          // If we have a current field, add it.
          if (field != '\0') {
            nodes.add(new Field(field, width));
          }

          // Clear the field.
          field = '\0';

          // Single quotes escape literal characters used for field patterns.
          if (ch == '\'') {
            inquote = true;
          } else {
            // Append a character
            buf.append(ch);
          }
          break;
      }
      i++;

    }

    if (width > 0 && field != '\0') {
      nodes.add(new Field(field, width));
    } else if (buf.length() > 0) {
      nodes.add(new Text(buf.toString()));
    }

    return nodes;
  }

}
