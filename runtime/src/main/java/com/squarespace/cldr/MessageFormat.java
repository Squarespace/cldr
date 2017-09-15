package com.squarespace.cldr;

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

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.squarespace.cldr.dates.CalendarFormatter;
import com.squarespace.cldr.dates.DateTimeIntervalSkeleton;
import com.squarespace.cldr.numbers.NumberFormatter;
import com.squarespace.cldr.numbers.NumberOperands;
import com.squarespace.cldr.plurals.PluralCategory;
import com.squarespace.cldr.plurals.PluralRules;
import com.squarespace.cldr.units.Unit;
import com.squarespace.cldr.units.UnitCategory;
import com.squarespace.cldr.units.UnitConverter;
import com.squarespace.cldr.units.UnitFactorSet;
import com.squarespace.cldr.units.UnitValue;
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
 * date, time, datetime, etc). The content that follows is determined by the tag type. For example, a
 * plural tag contains a list of exact or plural category matches.
 *
 * The code is structured in such a way that a tag is parsed by first matching balanced pairs
 * of '{' '}' delimiters. This simplifies isolating the interior of the tag and quickly bailing
 * out of a tag if the format appears invalid, and maintaining a bit less state during the parse.
 *
 * Since this code will be used in environments where speed is a priority, and conditions for
 * error-handling are not ideal (user-defined messages, evaluated in a template compiler) we
 * skip over malformed tags instead of raising exceptions. MessageFormat validation can be done as a
 * separate implementation if desired.
 *
 * TODO: we may want to allow for a "bad tag placeholder" string to be set and emitted when
 * a bad tag is encountered, to assist in detection and correction of malformed message strings.
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

  private static final CLDR CLDR_INSTANCE = CLDR.get();
  private static final int DEFAULT_MAXDEPTH = 4;
  
  // Names of tag types.
  private static final String PLURAL = "plural";
  private static final String SELECTORDINAL = "selectordinal";
  private static final String DECIMAL = "decimal";
  private static final String DATETIME = "datetime";
  private static final String DATETIME_INTERVAL = "datetime-interval";
  private static final String CURRENCY = "currency";
  private static final String MONEY = "money";
  private static final String NUMBER = "number";
  private static final String UNIT = "unit";

  private static final PluralRules PLURAL_RULES = CLDR_INSTANCE.getPluralRules();
  
  // Pattern to match a valid tag type.
  private static final Recognizer FORMATTER = choice(
      literal(PLURAL),
      literal(SELECTORDINAL),
      literal(DATETIME),
      literal(DATETIME_INTERVAL),
      literal(CURRENCY),
      literal(MONEY),
      literal(NUMBER),
      literal(DECIMAL),
      literal(UNIT));

  // Pattern matchers for syntax fragments
  private static final CharClassifier CLASSIFIER = new DefaultCharClassifier();
  private static final Recognizer COMMA_WS = oneOrMore(choice(characters(','), whitespace()));
  private static final Recognizer SEMICOLON = characters(';');
  private static final Recognizer DIGITS = digits();
  private static final Recognizer PLURAL_EXPLICIT = sequence(characters('='), digits());
  private static final Recognizer PLURAL_OFFSET = sequence(
      literal("offset"), characters(':'), digits());
  private static final Recognizer IDENTIFIER = sequence(
      charClass(UPPERCASE | LOWERCASE | DASH | UNDERSCORE, CLASSIFIER),
      zeroOrMore(charClass(UPPERCASE | LOWERCASE | DASH | UNDERSCORE | DIGIT, CLASSIFIER)));
  private static final Recognizer SKELETON = oneOrMore(
      charClass(UPPERCASE | LOWERCASE, CLASSIFIER));
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

  private final NumberOperands operands = new NumberOperands("0");

  // Typed argument parsers initialized on first use
  private MessageArgsCalendarParser calendarArgsParser;
  private MessageArgsCurrencyParser currencyArgsParser;
  private MessageArgsDecimalParser decimalArgsParser;
  private MessageArgsUnitParser unitArgsParser;
  
  // Locale, currency and time zone defaults.
  private CLDR.Locale locale = CLDR.Locale.en_US;
  private ZoneId timeZone;

  // Stack of streams for recursive formatting.
  private final Stream[] streams;
  private int streamIndex;

  // Temporary streams for parsing tags, choices, arguments, and other syntax fragments.
  private Stream tag;
  private Stream choice;

  private String format;

  // These fields are overwritten on each format() call.
  private MessageArgs args;
  private StringBuilder buf;

  /**
   * Construct a message formatter with the given locale, time zone and message format.
   */
  public MessageFormat(CLDR.Locale locale, ZoneId timeZone, String format) {
    this(locale, timeZone, format, DEFAULT_MAXDEPTH);
  }

  /**
   * Construct a message formatter with the given locale, time zone, message format,
   * and maximum recursion depth.
   */
  public MessageFormat(CLDR.Locale locale, ZoneId timeZone, String format, int maxDepth) {
    this.locale = locale;
    this.timeZone = timeZone;
    this.streams = new Stream[maxDepth];
    this.setFormat(format);
  }

  /**
   * Set the locale for this message.
   */
  public void setLocale(CLDR.Locale locale) {
    this.locale = locale;
  }

  /**
   * Set the time zone for this message.
   */
  public void setTimeZone(ZoneId timeZone) {
    this.timeZone = timeZone;;
  }

  /**
   * Sets the message format.
   */
  public void setFormat(String format) {
    this.format = format;

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
   * Formats the message using the given arguments, and writing the output to the buffer.
   */
  public void format(MessageArgs args, StringBuilder buf) {
    this.args = args;
    this.buf = buf;
    this.reset();
    format(streams[0], null);
  }

  private void reset() {
    int length = this.format.length();
    for (int i = 0; i < this.streams.length; i++) {
      Stream s = this.streams[i];
      if (s != null) {
        s.set(0, length);
      }
    }
    this.tag.set(0, length);
    this.choice.set(0, length);
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
        evaluateTag(buf);
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
  private void evaluateTag(StringBuilder buf) {
    // Trim the '{' and '}' bounding characters.
    tag.pos++;
    tag.end--;

    List<MessageArg> args = getArgs();
    if (args == null || args.size() == 0) {
      return;
    }

    MessageArg arg = args.get(0);

    // Process a short tag like "{1}" by just appending the argument value.
    if (tag.peek() == Chars.EOF) {
      if (arg.resolve()) {
        buf.append(arg.asString());
      }
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
        case MONEY:
          evalCurrency(arg);
          break;

        case DATETIME:
          evalDateTime(arg);
          break;
          
        case DATETIME_INTERVAL:
          evalDateTimeInterval(args);
          break;

        case DECIMAL:
        case NUMBER:
          evalNumber(arg);
          break;
          
        case UNIT:
          evalUnit(arg);
          break;
      }
    }
  }

  private List<MessageArg> getArgs() {
    List<MessageArg> tuple = new ArrayList<>(2);
    MessageArg arg = null;
    
    do {
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
        return tuple;
      }
      
      tuple.add(arg);
      
      // Jump over the index value.
      tag.jump(choice);
      tag.skip(COMMA_WS);
      
      if (!tag.seek(SEMICOLON, choice)) {
        return tuple;
      }
      tag.jump(choice);
      arg = null;
      
    } while (true);
  }
  
  /**
   * PLURAL - Evaluate the argument as a plural, either cardinal or ordinal.
   */
  private void evalPlural(MessageArg arg, boolean cardinal) {
    tag.skip(COMMA_WS);
    if (!arg.resolve()) {
      return;
    }

    String value = arg.asString();
    String offsetValue = value;

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
    PluralCategory category = cardinal 
        ? PLURAL_RULES.evalCardinal(language, operands) 
        : PLURAL_RULES.evalOrdinal(language, operands);
        
    Recognizer matcher = PLURAL_MATCHERS[category.ordinal()];

    // Scan over the potential choices inside this tag. We will evaluate
    // the first choice that matches our plural category.
    char ch;
    while ((ch = tag.peek()) != Chars.EOF) {
      // First look for explicit match, falling back to a plural category.
      if (ch == '=' && tag.seek(PLURAL_EXPLICIT, choice)) {
        tag.jump(choice);

        // Skip the '=' character.
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
   * NUMBER - Evaluate the argument as a number, with options specified as key=value.
   */
  private void evalNumber(MessageArg arg) {
    initDecimalArgsParser();
    parseArgs(decimalArgsParser);
    
    BigDecimal number = arg.asBigDecimal();
    NumberFormatter formatter = CLDR_INSTANCE.getNumberFormatter(locale);
    formatter.formatDecimal(number, buf, decimalArgsParser.options());
  }

  /**
   * CURRENCY - Evaluate the argument as a currency, with options specified as key=value.
   */
  private void evalCurrency(MessageArg arg) {
    String currencyCode = arg.currency();
    if (currencyCode == null) {
      return;
    }
    CLDR.Currency currency = CLDR.Currency.fromString(currencyCode);
    if (currency == null) {
      return;
    }
    
    initCurrencyArgsParser();
    parseArgs(currencyArgsParser);
    
    BigDecimal number = arg.asBigDecimal();
    NumberFormatter formatter = CLDR_INSTANCE.getNumberFormatter(locale);
    formatter.formatCurrency(number, currency, buf, currencyArgsParser.options());
  }

  /**
   * DATETIME - Evaluate the argument as a datetime, with options specified as key=value.
   */
  private void evalDateTime(MessageArg arg) {
    if (!arg.resolve()) {
      return;
    }
    
    initCalendarArgsParser();
    parseArgs(calendarArgsParser);
    
    ZonedDateTime datetime = parseDateTime(arg);
    CalendarFormatter formatter = CLDR_INSTANCE.getCalendarFormatter(locale);
    formatter.format(datetime, calendarArgsParser.options(), buf);
  }
  
  /**
   * DATETIME-INTERVAL - Evaluate the argument as a date-time interval with an
   * optional skeleton argument.
   */
  private void evalDateTimeInterval(List<MessageArg> args) {
    int size = args.size();
    if (size != 2) {
      return;
    }
    for (int i = 0; i < size; i++) {
      if (!args.get(i).resolve()) {
        return;
      }
    }

    ZonedDateTime start = parseDateTime(args.get(0));
    ZonedDateTime end = parseDateTime(args.get(1));
    
    // Parse optional skeleton argument
    DateTimeIntervalSkeleton skeleton = null;
    tag.skip(COMMA_WS);
    if (tag.seek(SKELETON, choice)) {
      String skel = choice.toString();
      tag.jump(choice);
      skeleton = DateTimeIntervalSkeleton.fromString(skel);
    }
    
    CalendarFormatter formatter = CLDR_INSTANCE.getCalendarFormatter(locale);
    formatter.format(start, end, skeleton, buf);
  }
  
  /**
   * UNIT - Conversion and formatting of units and unit sequences. 
   */
  private void evalUnit(MessageArg arg) {
    // TODO: format unit ranges
    if (!arg.resolve()) {
      return;
    }
    
    initUnitArgsParser();
    parseArgs(unitArgsParser);

    BigDecimal amount = arg.asBigDecimal();

    // TODO: For future refactoring:
    // This method closely follows the template compiler's UnitFormatter which
    // was written first. It is relatively complex. In the near future, generalize the individual
    // typed formatters to be reusable, and rewrite UnitFormatter and MessageFormat to depend on them.
    
    MessageArgsUnitParser opts = unitArgsParser;
    Unit inputUnit = opts.inputUnit;
    Unit exactUnit = opts.exactUnit;
    Unit[] exactUnits = opts.exactUnits;
    String compact = opts.compact;
    Unit[] sequence = opts.sequence;

    // == FALLBACK ==
    
    // If no arguments were set, we don't know the unit type. Format as plain number and bail out.
    if (inputUnit == null && exactUnit == null) {
      UnitValue value = new UnitValue(amount, null);
      NumberFormatter formatter = CLDR_INSTANCE.getNumberFormatter(locale);
      formatter.formatUnit(value, buf, unitArgsParser.options());
      return;
    }

    // At least one argument was provided. We will try to infer the others where possible.
    UnitConverter converter = CLDR_INSTANCE.getUnitConverter(locale);
    UnitFactorSet factorSet = null;

    // == INTERPRET ARGUMENTS ==

    if (compact != null) {
      // First see if compact format matches a direct unit conversion (e.g. temperature, speed)
      Unit unit = MessageArgsUnitParser.selectExactUnit(compact, converter);
      if (unit != null) {
        exactUnit = unit;
      } else if (opts.factorSet != null) {
        // Compact format might correspond to a factor set (e.g. digital bits, bytes).
        factorSet = opts.factorSet;
      } else {
        factorSet = MessageArgsUnitParser.selectFactorSet(compact, converter);
        opts.factorSet = factorSet;
      }

    } else if (exactUnits != null && exactUnits.length > 0) {
      if (opts.factorSet != null) {
        factorSet = opts.factorSet;
      } else {
        UnitCategory category = exactUnits[0].category();
        factorSet = converter.getFactorSet(category, exactUnits);
        opts.factorSet = factorSet;
      }

    } else if (sequence != null && sequence.length > 0) {
      if (opts.factorSet != null) {
        factorSet = opts.factorSet;
      } else {
        UnitCategory category = sequence[0].category();
        factorSet = converter.getFactorSet(category, sequence);
        opts.factorSet = factorSet;
      }
    }

    // Make sure we know what the input units are.
    if (inputUnit == null) {
      inputUnit = MessageArgsUnitParser.inputFromExactUnit(exactUnit, converter);
    }
    
    // == CONVERSION ==

    UnitValue value = new UnitValue(amount, inputUnit);

    // In sequence mode this will get set below.
    List<UnitValue> values = null;

    if (exactUnit != null) {
      // Convert to exact units using the requested unit.
      value = converter.convert(value, exactUnit);

    } else if (factorSet == null) {
        // Convert directly to "best" unit using the default built-in factor sets.
        value = converter.convert(value);

    } else if (compact != null || exactUnits != null) {
        // Use the factor set to build a compact form.
        value = converter.convert(value, factorSet);

    } else if (sequence != null) {
      // Use the factor set to produce a sequence.
      values = converter.sequence(value, factorSet);
    }

    // == FORMATTING ==

    NumberFormatter formatter = CLDR_INSTANCE.getNumberFormatter(locale);
    if (values == null) {
      formatter.formatUnit(value, buf, opts.options());
    } else {
      formatter.formatUnits(values, buf, opts.options());
    }
  }
  
  /**
   * Parse the argument as a date-time with the format-wide time zone.
   */
  private ZonedDateTime parseDateTime(MessageArg arg) {
    long instant = arg.asLong();
    return ZonedDateTime.ofInstant(Instant.ofEpochMilli(instant), timeZone);
  }
  
  private void initDecimalArgsParser() {
    if (decimalArgsParser == null) {
      decimalArgsParser = new MessageArgsDecimalParser();
    } else {
      decimalArgsParser.reset();
    }
  }
  
  private void initCurrencyArgsParser() {
    if (currencyArgsParser == null) {
      currencyArgsParser = new MessageArgsCurrencyParser();
    } else {
      currencyArgsParser.reset();
    }
  }
  
  private void initCalendarArgsParser() {
    if (calendarArgsParser == null) {
      calendarArgsParser = new MessageArgsCalendarParser();
    } else {
      calendarArgsParser.reset();
    }
  }
  
  private void initUnitArgsParser() {
    if (unitArgsParser == null) {
      unitArgsParser = new MessageArgsUnitParser();
    } else {
      unitArgsParser.reset();
    }
  }

  /**
   * Parse a list of zero or more arguments of the form:  <key>:<value>.  The ":<value>" is
   * optional and will set the key = null.  If no arguments are present this returns null.
   */
  private void parseArgs(MessageArgsParser parser) {
    while (tag.peek() != Chars.EOF) {
      tag.skip(COMMA_WS);

      // Look for the argument key
      if (!tag.seek(IDENTIFIER, choice)) {
        return;
      }

      // Parse the <key>(:<value>)? sequence
      String key = choice.toString();
      tag.jump(choice);
      if (tag.seek(ARG_SEP, choice)) {
        tag.jump(choice);
        if (tag.seek(ARG_VAL, choice)) {
          tag.jump(choice);
          parser.set(key, choice.toString());
        } else {
          parser.set(key, "");
        }
      } else {
        parser.set(key, "");
        if (!tag.seek(COMMA_WS, choice)) {
          return;
        }
      }
    }
  }

  /**
   * Append characters from 'raw' in the range [pos, end) to the output buffer.
   */
  private void emit(int pos, int end, String arg) {
    while (pos < end) {
      char ch = format.charAt(pos);
      if (ch == '#') {
        buf.append(arg == null ? ch : arg);
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
