package com.squarespace.cldr;

import static org.testng.Assert.assertEquals;

import java.time.ZoneId;

import org.testng.annotations.Test;


public class MessageFormatTest {

  private static CLDRLocale EN_US = new CLDRLocale("en", "", "US", "POSIX");
  private static CLDRLocale PL = new CLDRLocale("pl", "", "", "");

  private static String EUR = "EUR";
  private static String USD = "USD";

  private static final ZoneId NY_ZONE = ZoneId.of("America/New_York");

  // For tags to be parsed and evaluated, sequences of '{' and '}' delimiters
  // must be balanced. Otherwise the entire section must be ignored. Extraneous
  // unbalanced '}' are currently not skipped and so may be emitted into the
  // output as they are not bound to a valid tag.
  private static final String[] INCOMPLETE_PATTERNS = new String[] {
    "{",
    "{{{{{{}",
    "{0{{{{{}",
    "{bcd efg",
    "{}",
    "{2}",
    "{xyz, plural}",
    "{xyz, plural {{{{}"
  };

  @Test
  public void testIncomplete() {
    for (String prefix : new String[] { "", " ", "a ", "a }" }) {
      for (String pattern : INCOMPLETE_PATTERNS) {
        MessageFormat msg = new MessageFormat(EN_US, NY_ZONE, prefix + pattern);
        assertEquals(format(msg, args("1")), prefix);
      }
    }
  }

  @Test
  public void testPlural() {
    String format = "{0 plural =2{is two} one{# is one} few{# is few} many{# is many} other{# is other}}";
    MessageFormat msg = new MessageFormat(EN_US, NY_ZONE, format);
    assertEquals(format(msg, args("1")), "1 is one");
    assertEquals(format(msg, args("1.0")), "1.0 is other");
    assertEquals(format(msg, args("1.3")), "1.3 is other");
    assertEquals(format(msg, args("6")), "6 is other");

    msg = new MessageFormat(PL, NY_ZONE, format);
    assertEquals(format(msg, args("1")), "1 is one");
    assertEquals(format(msg, args("2")), "is two");
    assertEquals(format(msg, args("3")), "3 is few");
    assertEquals(format(msg, args("6")), "6 is many");
    assertEquals(format(msg, args("1.0")), "1.0 is other");
    assertEquals(format(msg, args("1.3")), "1.3 is other");
  }

  @Test
  public void testPluralOffset() {
    String format = "{0, plural, offset:1 " +
        "     =0 {Be the first to like this}" +
        "     =1 {You liked this}" +
        "    one {You and someone else liked this}" +
        "  other {You and # others liked this}" +
        "}";
    MessageFormat msg = new MessageFormat(EN_US, NY_ZONE, format);
    assertEquals(format(msg, args("0")), "Be the first to like this");
    assertEquals(format(msg, args("1")), "You liked this");
    assertEquals(format(msg, args("2")), "You and someone else liked this");
    assertEquals(format(msg, args("3")), "You and 2 others liked this");
    assertEquals(format(msg, args("4")), "You and 3 others liked this");
  }

  @Test
  public void testRecursive() {
    String format = "{0 plural " +
        "    =1 {# action on {1 datetime date-long}} " +
        " other {# actions starting on {1 datetime date-long}}" +
        "}";
    MessageFormat msg = new MessageFormat(EN_US, NY_ZONE, format);
    assertEquals(format(msg, args("1", "1498583746000")), "1 action on June 27, 2017");
    assertEquals(format(msg, args("5", "1498583746000")), "5 actions starting on June 27, 2017");
  }

// TODO: implement
//  @Test
//  public void testCurrency() {
//    String format = "{0 currency style:name min-fractional-digits:2}";
//    MessageFormat msg = new MessageFormat(EN_US, NY_ZONE, format);
//    assertEquals(format(msg, args(money("10.25", "USD"))), "$10.25");
//  }

//TODO: implement
//  @Test
//  public void testNumber() {
//    String format = "{0 number max-frac:2}";
//    MessageFormat msg = new MessageFormat(EN_US, NY_ZONE, format);
//    assertEquals(format(msg, args("3.141592653589793")), "3.14");
//  }

  @Test
  public void testDateTime() {
    String format = "{0 datetime date-long time-medium}";
    MessageFormat msg = new MessageFormat(EN_US, NY_ZONE, format);
    assertEquals(format(msg, args("1498583746000")), "June 27, 2017 at 1:15:46 PM");
  }

  private String format(MessageFormat msg, MessageArgs args) {
    StringBuilder buf = new StringBuilder();
    msg.format(args, buf);
    return buf.toString();
  }

  private static MessageArg money(String value, String currency) {
    StringMessageArg arg = new StringMessageArg(value);
    arg.setCurrency(currency);
    return arg;
  }

  private static MessageArgs args(MessageArg... args) {
    MessageArgs.Builder result = MessageArgs.newBuilder();
    for (MessageArg arg : args) {
      result.add(arg);
    }
    return result.build();
  }

  private static MessageArgs args(String... value) {
    MessageArgs.Builder args = MessageArgs.newBuilder();
    for (String v : value) {
      args.add(v);
    }
    return args.build();
  }

}
