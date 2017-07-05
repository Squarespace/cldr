package com.squarespace.cldr;

import static com.squarespace.cldr.plurals.CLDRPluralRules.evalCardinal;
import static com.squarespace.cldr.plurals.CLDRPluralRules.evalOrdinal;
import static com.squarespace.compiler.match.Recognizers.charClass;
import static com.squarespace.compiler.match.Recognizers.characters;
import static com.squarespace.compiler.match.Recognizers.choice;
import static com.squarespace.compiler.match.Recognizers.digits;
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

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import com.squarespace.cldr.dates.CLDRCalendarFormatter;
import com.squarespace.cldr.plurals.PluralCategory;
import com.squarespace.cldr.plurals.PluralOperands;
import com.squarespace.compiler.match.Recognizers.Recognizer;
import com.squarespace.compiler.text.CharClassifier;
import com.squarespace.compiler.text.Chars;
import com.squarespace.compiler.text.DefaultCharClassifier;
import com.squarespace.compiler.text.Scanner;
import com.squarespace.compiler.text.Scanner.Stream;


/**
 * MessageFormat is an implementation of the ICU MessageFormat class. It is designed to be
 * evaluated in a single pass rather than compiled for later evaluation. This is intended
 * for applications where caching of compiled formats isn't possible for various reasons.
 * For example, applications which have a very large set of messages each of which is evaluated
 * infrequently.
 *
 * A MessageFormat string contains text and one or more tags. Each tag has a simple internal
 * structure starting with the index or name of an argument, followed by the tag type (plural,
 * datetime, etc). The content that follows is determined by the tag type. For example, a
 * plural tag contains a list of exact or plural category matches.
 *
 * We use the term "choice" to denote the string emitted when a tag is evaluated. For example,
 * the format string:
 *
 *      {0, plural, =1{one} other{# other}}
 *
 * The strings "{one}" and "{# other}" are choices. MessageFormat is recursive, so evaluating
 * a choice is equivalent to evaluating a MessageFormat.
 *
 * MessageFormat uses a simple state machine. To handle recursion, the outermost state is
 * represented by a stack of streams.  The remaining 2 streams, tag and choice, are used for
 * matching tags and other temporary values.
 *
 *          "There {0 plural one {is # entry} other {are # entries}} posted to the {1} blog."
 *          ^_______________________________________________________________________________^
 *                 ^_______________________________________________^               ^_^
 *                               ^__________^       ^_____________^
 *
 */
public class MessageFormat {

  private static final int DEFAULT_MAXDEPTH = 4;

  private static final CLDRLocale EN_US = new CLDRLocale("en", "", "US", "POSIX");
  private static final CLDRCurrency USD = new CLDRCurrency("USD");
  private static final ZoneId DEFAULT_ZONEID = ZoneId.of("America/New_York");

  // Names of tag types.
  private static final String PLURAL = "plural";
  private static final String SELECTORDINAL = "selectordinal";
  private static final String DATETIME = "datetime";
  private static final String CURRENCY = "currency";
  private static final String NUMBER = "number";

  // Pattern to match a valid tag type.
  private static final Recognizer FORMATTER = choice(
      literal(PLURAL),
      literal(SELECTORDINAL),
      literal(DATETIME),
      literal(CURRENCY),
      literal(NUMBER));

  // Pattern matchers for syntax fragments
  private static final CharClassifier CLASSIFIER = new DefaultCharClassifier();
  private static final Recognizer COMMA_WS = oneOrMore(choice(characters(','), whitespace()));
  private static final Recognizer DIGITS = digits();
  private static final Recognizer PLURAL_EXPLICIT = sequence(characters('='), digits());
  private static final Recognizer PLURAL_OFFSET = sequence(
      literal("offset"), characters(':'), digits());
  private static final Recognizer IDENTIFIER = sequence(
      charClass(UPPERCASE | LOWERCASE | DASH | UNDERSCORE, CLASSIFIER),
      zeroOrMore(charClass(UPPERCASE | LOWERCASE | DASH | UNDERSCORE | DIGIT, CLASSIFIER)));
  private static final Recognizer ARG_SEP = characters(':');
  private static final Recognizer ARG_VAL = oneOrMore(notWhitespace());

  // Patterns to match CLDR plural categories.
  private static final Recognizer[] PLURAL_MATCHERS = new Recognizer[] {
      literal("zero"),
      literal("one"),
      literal("two"),
      literal("few"),
      literal("many"),
      literal("other")
  };

  // Locale, currency and time zone defaults.
  private CLDRLocale locale = EN_US;
  private CLDRCurrency currency = USD;
  private ZoneId timeZone = DEFAULT_ZONEID;

  // Stack of streams for recursive formatting.
  private final Stream[] streams;
  private int streamIndex;

  // Temporary streams for parsing tags, choices, arguments, and other syntax fragments.
  private Stream tag;
  private Stream choice;

  // These fields are overwritten on each format() call.
  private String format;
  private MessageArgs args;
  private StringBuilder buf;

  /**
   * Construct a message formatter with the given locale and currency.
   */
  public MessageFormat(CLDRLocale locale, CLDRCurrency currency) {
    this(locale, currency, DEFAULT_MAXDEPTH);
  }

  /**
   * Construct a message formatter with the given locale, currency and maximum recursion depth.
   */
  public MessageFormat(CLDRLocale locale, CLDRCurrency currency, int maxDepth) {
    this.streams = new Stream[maxDepth];
    this.locale = locale;
    this.currency = currency;
  }

  public void setLocale(CLDRLocale locale) {
    this.locale = locale;
  }

  public void setCurrency(CLDRCurrency currency) {
    this.currency = currency;
  }

  public void setTimeZone(ZoneId timeZone) {
    this.timeZone = timeZone;;
  }

  /**
   * Formats a pluralization message using the given arguments, and writing the output
   * to the buffer.
   */
  public void format(String format, MessageArgs args, StringBuilder buf) {
    configure(format, args, buf);
    format(streams[0], null);
  }

  /**
   * Sets the format message and streams before evaluation.
   */
  private void configure(String format, MessageArgs args, StringBuilder buf) {
    this.format = format;
    this.args = args;
    this.buf = buf;

    // Track multiple streams over the format string.
    Scanner scanner = new Scanner(format);
    this.streams[0] = scanner.stream();
    this.streams[1] = scanner.stream();
    for (int i = 2; i < this.streams.length; i++) {
      this.streams[i] = null;
    }

    // Streams for temporary matching.
    this.tag = scanner.stream();
    this.choice = scanner.stream();
  }

  /**
   * Format the current stream, with the optional substitution argument, replacing '#'.
   */
  private void format(Stream outer, String arg) {
    // Iterate over text and tags, alternately.
    int prev = outer.pos;
    int length = outer.end;
    int next = -1;

    // Anchor to the starting '{'.
    while ((next = outer.find('{')) != -1) {
      // Emit characters before the tag's opening '{'
      if (next > prev) {
        emit(prev, next, arg);
        prev = next;
      }

      // Locate a tag's bounds and evaluate it. Otherwise return, since we've
      // found a possibly incomplete tag and cannot correctly recover.
      if (outer.seekBounds(tag, '{', '}')) {
        prev = tag.end;
        evaluateTag(buf, args);
      } else {
        return;
      }
    }

    // Copy the remaining trailing characters, if any.
    if (prev < length) {
      emit(prev, length, arg);
    }
  }

  /**
   * Push a stream onto the stack and format it.
   */
  private void recurse(Stream bounds, String arg) {
    // Refuse if we've reached our recursion limit.
    streamIndex++;
    if (streamIndex == streams.length) {
      return;
    }

    // Trim the '{' and '}' bounding characters.
    bounds.pos++;
    bounds.end--;

    // Set the bounds of the tag, allocating a new stream on the stack if necessary.
    if (streams[streamIndex] == null) {
      streams[streamIndex] = bounds.copy();
    } else {
      streams[streamIndex].setFrom(bounds);
    }

    // Execute the formatter recursively on the current bounds, then
    // pop the stack.
    format(streams[streamIndex], arg);
    streamIndex--;
  }


  /**
   * Parse and evaluate a tag.
   */
  private void evaluateTag(StringBuilder buf, MessageArgs args) {
    tag.pos++;
    tag.end--;

    MessageArg arg = null;

    // Arguments can be referenced with an integer index or name.
    if (tag.seek(DIGITS, choice)) {
      int index = (int)toLong(format, choice.pos, choice.end);
      arg = args.get(index);

    } else if (tag.seek(IDENTIFIER, choice)) {
      String key = choice.token().toString();
      arg = args.get(key);
    }

    // If no argument is present, bail out.
    if (arg == null) {
      return;
    }

    // Jump over the index value.
    tag.jump(choice);
    tag.skip(COMMA_WS);

    // Process a short tag like "{1}" by just appending the argument value.
    if (tag.peek() == Chars.EOF) {
      buf.append(arg.value());
      return;
    }

    // Parse the formatter name and execute the associated formatting method.
    if (tag.seek(FORMATTER, choice)) {
      String formatter = choice.token().toString();
      tag.jump(choice);
      switch (formatter) {
        case PLURAL:
          evalPlural(arg, true);
          break;

        case SELECTORDINAL:
          evalPlural(arg, false);
          break;

        case CURRENCY:
          evalCurrency(arg);
          break;

        case DATETIME:
          evalDateTime(arg);
          break;

        case NUMBER:
          evalNumber(arg);
          break;
      }
    }
  }


  /**
   * PLURAL - Evaluate the argument as a plural, either cardinal or ordinal.
   */
  private void evalPlural(MessageArg arg, boolean cardinal) {
    tag.skip(COMMA_WS);
    String value = arg.value();
    String offsetValue = value;

    PluralOperands operands = arg.getPluralOperands();

    // Look for optional "offset:<number>" argument. This modifies the argument value,
    // changing the plural operands calculation.
    long offset = 0;
    if (tag.seek(PLURAL_OFFSET, choice)) {
      tag.jump(choice);
      tag.skip(COMMA_WS);

      // Parse the offset digits and the argument value.
      offset = toLong(format, choice.pos + 7, choice.end);
      long newValue = toLong(value, 0, value.length()) - offset;

      // Operands are evaluated based on the offset-adjusted value.
      offsetValue = Long.toString(newValue);
      operands.set(offsetValue);
    } else {
      operands.set(value);
    }

    // Evaluate the plural operands for the current argument.
    String language = locale.language();
    PluralCategory category = cardinal ? evalCardinal(language, operands) : evalOrdinal(language, operands);
    Recognizer matcher = PLURAL_MATCHERS[category.ordinal()];

    // Scan over the potential choices inside this tag. We will evaluate
    // the first choice that matches our plural category.
    char ch;
    while ((ch = tag.peek()) != Chars.EOF) {
      // First look for explicit match, falling back to a plural category.
      if (ch == '=' && tag.seek(PLURAL_EXPLICIT, choice)) {
        tag.jump(choice);
        choice.pos++;

        // If our argument matches the exact value, recurse and then return.
        String repr = choice.token().toString();
        if (repr.equals(value) && tag.seekBounds(choice, '{', '}')) {
          recurse(choice, offsetValue);
          return;
        }

      } else if (tag.seek(matcher, choice)) {
        // Jump over the plural category.
        tag.jump(choice);
        tag.skipWs();

        // If the choice format is valid, recurse and then return.
        if (tag.peek() == '{' && tag.seekBounds(choice, '{', '}')) {
          recurse(choice, offsetValue);
        }
        return;
      }

      // Skip over this choice and any trailing whitespace, or bail out if no match.
      if (!tag.seekBounds(choice, '{', '}')) {
        return;
      }
      tag.jump(choice);
      tag.skip(COMMA_WS);
    }
  }

  /**
   * CURRENCY - Evaluate the argument as a currency, with options specified as key=value.
   */
  private void evalCurrency(MessageArg arg) {
    Map<String, String> args = parseArgs();
//    System.out.println(args);
  }

  /**
   * NUMBER - Evaluate the argument as a number, with options specified as key=value.
   */
  private void evalNumber(MessageArg arg) {
    Map<String, String> args = parseArgs();
//    System.out.println(args);
  }

  /**
   * DATETIME - Evaluate the argument as a datetime, with options specified as key=value.
   */
  private void evalDateTime(MessageArg arg) {
    Map<String, String> args = parseArgs();
    DateTimeOptions options = new DateTimeOptions();
    for (String option : args.keySet()) {
      options.add(option);
    }
    options.done();

    // TODO: timezone support, which has to be passed to the formatter separately

    String value = arg.value();
    long instant = toLong(value, 0, value.length());
    ZonedDateTime dateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(instant), DEFAULT_ZONEID);
    CLDRCalendarFormatter formatter = CLDR.get().getCalendarFormatter(locale);

    if (options.wrapperType() != null) {
      formatter.formatWrapped(options.wrapperType(), options.dateType(), options.timeType(),
          options.dateSkeleton(), options.timeSkeleton(), dateTime, buf);
    } else if (options.dateType() != null) {
      formatter.formatDate(options.dateType(), dateTime, buf);
    } else if (options.timeType() != null) {
      formatter.formatTime(options.timeType(), dateTime, buf);
    } else {
      String skeleton = options.dateSkeleton() != null ? options.dateSkeleton() : options.timeSkeleton();
      formatter.formatSkeleton(skeleton, dateTime, buf);
    }
  }

  /**
   * Parse a list of zero or more arguments of the form:  <key>:<value>.  The ":<value>" is
   * optional and will set the key = null.  If no arguments are present this returns null.
   */
  private Map<String, String> parseArgs() {
    Map<String, String> result = null;
    while (tag.peek() != Chars.EOF) {
      tag.skip(COMMA_WS);

      // Look for the argument key
      if (!tag.seek(IDENTIFIER, choice)) {
        return result;
      }

      // Once we have at least one argument, initialize the container.
      if (result == null) {
        result = new HashMap<>();
      }

      // Parse the <key>(:<value>)? sequence
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
      } else {
        result.put(key, null);
        if (!tag.seek(COMMA_WS, choice)) {
          return result;
        }
      }
    }
    return result;
  }

  /**
   * Append characters from 'raw' in the range [pos, end) to the output buffer.
   */
  private void emit(int pos, int end, String arg) {
    while (pos < end) {
      char ch = format.charAt(pos);
      if (ch == '#' && arg != null) {
        buf.append(arg);
      } else {
        buf.append(ch);
      }
      pos++;
    }
  }

  /**
   * Quick string to integer conversion.
   */
  public static long toLong(CharSequence seq, int pos, int length) {
    long n = 0;
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
