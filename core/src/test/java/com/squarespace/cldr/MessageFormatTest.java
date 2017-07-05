package com.squarespace.cldr;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;


public class MessageFormatTest {

  private static CLDRLocale EN_US = new CLDRLocale("en", "", "US", "POSIX");

  private static CLDRLocale PL = new CLDRLocale("pl", "", "", "");

  private static CLDRCurrency USD = new CLDRCurrency("USD");

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
        assertEquals(format(prefix + pattern, EN_US, USD, args("1")), prefix);
      }
    }
  }

  @Test
  public void testPlural() {
    String msg = "{0 plural =2{is two} one{# is one} few{# is few} many{# is many} other{# is other}}";
    assertEquals(format(msg, EN_US, USD, args("1")), "1 is one");
    assertEquals(format(msg, EN_US, USD, args("1.0")), "1.0 is other");
    assertEquals(format(msg, EN_US, USD, args("1.3")), "1.3 is other");
    assertEquals(format(msg, EN_US, USD, args("6")), "6 is other");

    assertEquals(format(msg, PL, USD, args("1")), "1 is one");
    assertEquals(format(msg, PL, USD, args("2")), "is two");
    assertEquals(format(msg, PL, USD, args("3")), "3 is few");
    assertEquals(format(msg, PL, USD, args("6")), "6 is many");
    assertEquals(format(msg, PL, USD, args("1.0")), "1.0 is other");
    assertEquals(format(msg, PL, USD, args("1.3")), "1.3 is other");
  }

  @Test
  public void testPluralOffset() {
    String msg = "{0, plural, offset:1 " +
        "     =0 {Be the first to like this}" +
        "     =1 {You liked this}" +
        "    one {You and someone else liked this}" +
        "  other {You and # others liked this}" +
        "}";
    assertEquals(format(msg, EN_US, USD, args("0")), "Be the first to like this");
    assertEquals(format(msg, EN_US, USD, args("1")), "You liked this");
    assertEquals(format(msg, EN_US, USD, args("2")), "You and someone else liked this");
    assertEquals(format(msg, EN_US, USD, args("3")), "You and 2 others liked this");
    assertEquals(format(msg, EN_US, USD, args("4")), "You and 3 others liked this");
  }

  @Test
  public void testRecursive() {
    String msg = "{0 plural " +
        "    =1 {# action on {1 datetime date-long}} " +
        " other {# actions starting on {1 datetime date-long}}" +
        "}";
    assertEquals(format(msg, EN_US, USD, args("1", "1498583746000")), "1 action on June 27, 2017");
    assertEquals(format(msg, EN_US, USD, args("5", "1498583746000")), "5 actions starting on June 27, 2017");
  }

  @Test
  public void testCurrency() {
    String msg = "{0 currency style:name min-fractional-digits:2}";
    assertEquals(format(msg, EN_US, USD, args("10.25")), "$10.25");
  }

  @Test
  public void testNumber() {
    String msg = "{0 number max-fractional-digits:2}";
    assertEquals(format(msg, EN_US, USD, args("3.141592653589793")), "3.14");
  }

  @Test
  public void testDateTime() {
    String msg = "{0 datetime date-long time-medium}";
    assertEquals(format(msg, EN_US, USD, args("1498583746000")), "June 27, 2017 at 1:15:46 PM");
  }

  private String format(String format, CLDRLocale locale, CLDRCurrency currency, MessageArgs args) {
    MessageFormat msg = new MessageFormat(locale, currency);
    StringBuilder buf = new StringBuilder();
    msg.format(format, args, buf);
    return buf.toString();
  }

  private static MessageArgs args(String... value) {
    MessageArgs.Builder args = MessageArgs.newBuilder();
    for (String v : value) {
      args.add(v);
    }
    return args.build();
  }

}
