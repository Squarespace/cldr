package com.squarespace.cldr;

import static com.squarespace.cldr.plurals.CLDRPluralRules.evalCardinal;
import static com.squarespace.cldr.plurals.CLDRPluralRules.evalOrdinal;
import static com.squarespace.compiler.match.Recognizers.charClass;
import static com.squarespace.compiler.match.Recognizers.characters;
import static com.squarespace.compiler.match.Recognizers.literal;
import static com.squarespace.compiler.match.Recognizers.notWhitespace;
import static com.squarespace.compiler.match.Recognizers.oneOrMore;
import static com.squarespace.compiler.match.Recognizers.sequence;
import static com.squarespace.compiler.match.Recognizers.whitespace;
import static com.squarespace.compiler.match.Recognizers.zeroOrMore;
import static com.squarespace.compiler.text.DefaultCharClassifier.DASH;
import static com.squarespace.compiler.text.DefaultCharClassifier.DIGIT;
import static com.squarespace.compiler.text.DefaultCharClassifier.LOWERCASE;
import static com.squarespace.compiler.text.DefaultCharClassifier.UNDERSCORE;
import static com.squarespace.compiler.text.DefaultCharClassifier.UPPERCASE;

import java.util.LinkedHashMap;
import java.util.Map;

import com.squarespace.cldr.plurals.PluralCategory;
import com.squarespace.cldr.plurals.PluralOperands;
import com.squarespace.compiler.match.Recognizers;
import com.squarespace.compiler.match.Recognizers.Recognizer;
import com.squarespace.compiler.text.CharClassifier;
import com.squarespace.compiler.text.Chars;
import com.squarespace.compiler.text.DefaultCharClassifier;
import com.squarespace.compiler.text.Scanner;
import com.squarespace.compiler.text.Scanner.Stream;


/**
 * Simple state machine that formats messages with typed arguments. There are 3 states
 * encountered when traversing the message string: outer, tag and choice. Should run
 * less than O(3N).
 *
 *          "There {0 cardinal one {is # entry} other {are # entries}} posted to the {1} blog."
 * <state>
 *  OUTER    ^______________________________________________________________________^
 *    TAG          ^________________________________________^               ^_^
 * CHOICE             ^______________^ ^___________________^                ^_^
 *
 */
public class MessageFormat {

  private static final CharClassifier CLASSIFIER = new DefaultCharClassifier();

  private static final Recognizer CARDINAL = literal("cardinal");

  private static final Recognizer ORDINAL = literal("ordinal");

  private static final Recognizer CURRENCY = literal("currency");

  private static final Recognizer NUMBER = literal("number");

  private static final Recognizer WHITESPACE = whitespace();

  private static final Recognizer ARG_KEY = sequence(
      charClass(UPPERCASE | LOWERCASE | DASH | UNDERSCORE, CLASSIFIER),
      zeroOrMore(charClass(UPPERCASE | LOWERCASE | DASH | UNDERSCORE | DIGIT, CLASSIFIER)));

  private static final Recognizer ARG_SEP = characters(':');

  private static final Recognizer ARG_VAL = oneOrMore(notWhitespace());

  private static final Recognizer[] MATCHERS = new Recognizer[] {
      literal("zero"),
      literal("one"),
      literal("two"),
      literal("few"),
      literal("many"),
      literal("other")
  };

  private static final Recognizer DIGITS = Recognizers.digits();

  private String format;

  private String language;

  private String currency;

  public MessageFormat(String format, String language, String currency) {
    this.format = format;
    this.language = language;
    this.currency = currency;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  /**
   * Formats a pluralization message.
   */
  public void format(StringBuilder buf, MessageArg[] args) {
    // Scanner letting us track multiple streams over the same underlying string.
    Scanner s = new Scanner(format);

    // Streams representing the states of the formatter:
    //   outer - text blocks and tags
    //     tag - index and choices
    //  choice - single choice or argument
    //
    Scanner.Stream outer = s.stream();
    Scanner.Stream tag = s.stream();
    Scanner.Stream choice = s.stream();

    // Iterate over text and tags, alternately.
    int prev = 0;
    int length = format.length();
    while (outer.seekBounds(tag, '{', '}')) {
      // Copy any leading characters to the output.
      emit(buf, format, prev, tag.pos);
      prev = tag.end;

      // Process this tag, evaluating the choice, if any.
      process(buf, args, tag, choice);
    }

    // Copy the remaining trailing characters, if any.
    if (prev < length) {
      emit(buf, format, prev, length);
    }
  }

  /**
   * Parse and evaluates a tag.
   */
  private void process(StringBuilder buf, MessageArg[] args, Stream tag, Stream choice) {
    // Don't process the delimiters '{' and '}', just the contents.
    tag.pos++;
    tag.end--;

    // A tag must start with an index number.
    if (!tag.seek(DIGITS, choice)) {
      return;
    }

    // Parse the index number and ensure it points to a valid argument.
    int index = toInteger(tag.raw(), choice.pos, choice.end);
    if (index >= args.length) {
      return;
    }

    // Jump over the index value.
    tag.jump(choice);
    MessageArg arg = args[index];

    // Process a short tag like "{1}" by just appending the argument value.
    if (tag.peek() == Chars.EOF) {
      buf.append(arg.getValue());
      return;
    }

    // Check if plural type is specified: cardinal or ordinal. If not we default to 'cardinal'.
    tag.skipWs();
    if (tag.seek(CARDINAL, choice)) {
      tag.jump(choice);
      evalPlural(buf, arg, tag, choice, true);

    } else if (tag.seek(ORDINAL, choice)) {
      tag.jump(choice);
      evalPlural(buf, arg, tag, choice, false);

    } else if (tag.seek(CURRENCY, choice)) {
      tag.jump(choice);
      evalCurrency(buf, arg, tag, choice);

    } else if (tag.seek(NUMBER, choice)) {
      tag.jump(choice);
      evalNumber(buf, arg, tag, choice);
    }


  }

  /**
   * Evaluate the argument as a plural, either cardinal or ordinal.
   */
  private void evalPlural(StringBuilder buf, MessageArg arg, Stream tag, Stream choice, boolean cardinal) {
    // Evaluate a plural choice.
    PluralOperands operands = arg.getPluralOperands();
    PluralCategory category = cardinal ? evalCardinal(language, operands) : evalOrdinal(language, operands);
    Recognizer matcher = MATCHERS[category.ordinal()];
    tag.skipWs();

    // Scan over the potential choices inside this tag. We will evaluate
    // the first choice that matches our plural category.
    while (tag.peek() != Chars.EOF) {
      if (tag.seek(matcher, choice)) {
        // Jump over the plural category.
        tag.jump(choice);

        // If the choice format is valid, evaluate it, then return.
        if (tag.peek() == '{' && tag.seekBounds(choice, '{', '}')) {
          eval(buf, choice, arg.getValue());
        }
        return;
      }

      // Skip over this choice and any trailing whitespace.
      if (!tag.seekBounds(choice, '{', '}')) {
        return;
      }
      tag.skipWs();
    }
  }

  /**
   * Evaluate the argument as a currency, with options specified as key=value.
   */
  private static void evalCurrency(StringBuilder buf, MessageArg arg, Stream tag, Stream choice) {
    Map<String, String> args = parseArgs(tag, choice);
    System.out.println(args);
  }

  /**
   * Evaluate the argument as a number, with options specified as key=value.
   */
  private static void evalNumber(StringBuilder buf, MessageArg arg, Stream tag, Stream choice) {
    Map<String, String> args = parseArgs(tag, choice);
    System.out.println(args);
  }

  /**
   * Parse a list of zero or more arguments of the form:  <key>:<value>.  The ":<value>" is
   * optional and will set the key = null.  If no arguments are present this returns null.
   */
  private static Map<String, String> parseArgs(Stream tag, Stream choice) {
    Map<String, String> result = null;
    while (tag.peek() != Chars.EOF) {
      tag.skipWs();

      // Look for the argument key
      if (!tag.seek(ARG_KEY, choice)) {
        return result;
      }

      // Once we have at least one argument, initialize the container.
      if (result == null) {
        result = new LinkedHashMap<>();
      }

      // Parse the <key>(=<value>)? sequence
      String key = choice.toString();
      tag.jump(choice);
      if (tag.seek(ARG_SEP, choice)) {
        tag.jump(choice);
        if (tag.seek(ARG_VAL, choice)) {
          tag.jump(choice);
          result.put(key, choice.toString());
        } else {
          result.put(key, "");
        }
      } else if (!tag.seek(WHITESPACE, choice)) {
        result.put(key, null);
        return result;
      }
    }
    return result;
  }

  /**
   * Evaluate the template for a given choice.
   */
  private static void eval(StringBuilder buf, Stream choice, String value) {
    // Don't process the delimiters '{' and '}', just the contents.
    choice.pos++;
    choice.end--;

    // Emit the characters, substituting instances of '#' with the argument value.
    char ch;
    while ((ch = choice.seek()) != Chars.EOF) {
      if (ch == '#') {
        buf.append(value);
      } else {
        buf.append(ch);
      }
    }
  }

  /**
   * Append characters from 'raw' in the range [pos, end) to the output buffer.
   */
  private static void emit(StringBuilder buf, String raw, int pos, int end) {
    while (pos < end) {
      buf.append(raw.charAt(pos));
      pos++;
    }
  }

  /**
   * Quick string to integer conversion.
   */
  public static int toInteger(CharSequence seq, int pos, int length) {
    int n = 0;
    int i = pos;
    while (i < length) {
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
