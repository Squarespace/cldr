package com.squarespace.cldr.numbers;

import java.util.ArrayList;
import java.util.List;

import com.squarespace.cldr.numbers.NumberPattern.Format;
import com.squarespace.cldr.numbers.NumberPattern.Node;


/**
 * Parses number formats for CLDR. Note that this is currently fitted to parsing formats
 * contained within the cldr-numbers-modern package and is not a general-purpose number
 * format parser. It is only used to extract information for purposes of code generation.
 *
 * Number pattern character meanings are described here:
 * http://www.unicode.org/reports/tr35/tr35-numbers.html#Number_Pattern_Character_Definitions
 */
public class NumberPatternParser {

  private StringBuilder buf = new StringBuilder();

  private List<Node> nodes;

  private boolean attached = false;

  private Format format;

  public NumberPatternParser() {
  }

  /**
   * Parse a number formatting pattern.
   */
  public NumberPattern parse(String raw) {
    this.buf.setLength(0);
    this.nodes = new ArrayList<>();
    this.attached = false;
    this.format = new Format();

    // Length of the pattern.
    int length = raw.length();

    // Position in the pattern.
    int i = 0;

    // Indicate we've seen a group separator and are "inside" a digit group.
    boolean inGroup = false;

    // Indicate we're in the decimal portion of the format.
    boolean inDecimal = false;

    while (i < length) {
      char ch = raw.charAt(i);

      switch (ch) {
        case '\'':
          // Single-quote escaping of reserved characters.
          while (i++ < length) {
            ch = raw.charAt(i);
            if (ch == '\'') {
              break;
            }
            buf.append(ch);
          }
          break;

        case '-':
          pushText();
          nodes.add(NumberPattern.Symbol.MINUS);
          break;

        case '%':
          pushText();
          nodes.add(NumberPattern.Symbol.PERCENT);
          break;

        case '\u00a4':
          pushText();
          nodes.add(NumberPattern.Symbol.CURRENCY);
          break;

        case '#':
          // Optional digit placeholder.
          attach();
          if (inGroup) {
            format.primaryGroupingSize++;
          } else if (inDecimal) {
            format.maximumFractionDigits++;
          }
          break;

        case ',':
          // Digit grouping delimiter.
          attach();
          if (inGroup) {
            format.secondaryGroupingSize = format.primaryGroupingSize;
            format.primaryGroupingSize = 0;
          } else {
            inGroup = true;
          }
          break;

        case '.':
          // Decimal point character.
          inGroup = false;
          attach();
          inDecimal = true;
          break;

        case '0':
          // Required digit placeholder. If the number being formatted does not
          // contain a digit, a zero will be output.
          attach();
          if (inGroup) {
            format.primaryGroupingSize++;
          } else if (inDecimal) {
            format.minimumFractionDigits++;
            format.maximumFractionDigits++;
          }
          if (!inDecimal) {
            format.minimumIntegerDigits++;
          }
          break;

        default:
          // Rest of characters are assumed to be static.
          buf.append(ch);
          break;
      }
      i++;
    }
    pushText();

    return new NumberPattern(raw, nodes, format);
  }

  /**
   * Attach the format to the node list.
   */
  private void attach() {
    pushText();
    if (!attached) {
      nodes.add(format);
      attached = true;
    }
  }

  /**
   * If the buffer is not empty, push it onto the node list and reset it.
   */
  private void pushText() {
    if (buf.length() > 0) {
      nodes.add(new NumberPattern.Text(buf.toString()));
      buf.setLength(0);
    }
  }

}
