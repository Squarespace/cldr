package com.squarespace.cldr;

import static com.squarespace.cldr.CLDR.Locale.en_US;
import static com.squarespace.cldr.CLDR.Locale.fr_FR;
import static com.squarespace.cldr.CLDR.Locale.pl;
import static org.testng.Assert.assertEquals;

import java.time.ZoneId;

import org.testng.annotations.Test;


public class MessageFormatTest {

  private static final ZoneId NY_ZONE = ZoneId.of("America/New_York");
  private static final ZoneId PARIS_ZONE = ZoneId.of("Europe/Paris");

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
        MessageFormat msg = new MessageFormat(en_US, NY_ZONE, prefix + pattern);
        assertEquals(format(msg, args("1")), prefix);
      }
    }
  }
  
  @Test
  public void testHideTag() {
    String plural = "0 plural =2{is two} one{# is one} few{# is few} many{# is many} other{# is other}";
    String format = "{city} {-city} {--city} {state} {-{{state}}} {-" + plural + "}";
    MessageFormat msg = new MessageFormat(en_US, NY_ZONE, format);
    MessageArgs args = args().add("state", "California").add("city", "Los Angeles").build();
    assertEquals(format(msg, args), "Los Angeles {city} {-city} California {{{state}}} {" + plural + "}");
  }

  @Test
  public void testSelect() {
    String format = "{gender, select, male {MALE} female {FEMALE} other {OTHER}}";
    MessageFormat msg = new MessageFormat(en_US, NY_ZONE, format);
    assertEquals(format(msg, args().add("gender", "female").build()), "FEMALE");
    assertEquals(format(msg, args().add("gender", "male").build()), "MALE");
    assertEquals(format(msg, args().add("gender", "xyz").build()), "OTHER");
  }
  
  @Test
  public void testPlural() {
    String format = "{0 plural =2{is two} one{# is one} few{# is few} many{# is many} other{# is other}}";
    MessageFormat msg = new MessageFormat(en_US, NY_ZONE, format);
    assertEquals(format(msg, args("1")), "1 is one");
    assertEquals(format(msg, args("1.0")), "1.0 is other");
    assertEquals(format(msg, args("1.3")), "1.3 is other");
    assertEquals(format(msg, args("6")), "6 is other");

    msg = new MessageFormat(pl, NY_ZONE, format);
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
    MessageFormat msg = new MessageFormat(en_US, NY_ZONE, format);
    assertEquals(format(msg, args("0")), "Be the first to like this");
    assertEquals(format(msg, args("1")), "You liked this");
    assertEquals(format(msg, args("2")), "You and someone else liked this");
    assertEquals(format(msg, args("3")), "You and 2 others liked this");
    assertEquals(format(msg, args("4")), "You and 3 others liked this");
  }

  @Test
  public void testOrdinal() {
    String format = "{0 selectordinal one{#st} two{#nd} few{#rd} other{#th}}";
    MessageFormat msg = new MessageFormat(en_US, NY_ZONE, format);
    assertEquals(format(msg, args("1")), "1st");
    assertEquals(format(msg, args("-2")), "-2nd");
    assertEquals(format(msg, args("3")), "3rd");
    assertEquals(format(msg, args("-4")), "-4th");
    assertEquals(format(msg, args("5")), "5th");
    assertEquals(format(msg, args("22")), "22nd");
    assertEquals(format(msg, args("34")), "34th");
    assertEquals(format(msg, args("101")), "101st");
    assertEquals(format(msg, args("103")), "103rd");
    assertEquals(format(msg, args("-1047")), "-1047th");
  }
  
  @Test
  public void testRecursive() {
    String format = "{0 plural " +
        "    =1 {# action on {1 datetime date:long}} " +
        " other {# actions starting on {1 datetime date:long}}" +
        "}";
    MessageFormat msg = new MessageFormat(en_US, NY_ZONE, format);
    assertEquals(format(msg, args("1", "1498583746000")), "1 action on June 27, 2017");
    assertEquals(format(msg, args("5", "1498583746000")), "5 actions starting on June 27, 2017");
  }

  @Test
  public void testUnits() {
    String format = "{0 unit format:long compact:duration in:seconds}";
    MessageFormat msg = new MessageFormat(en_US, NY_ZONE, format);
    assertEquals(format(msg, args("123456")), "1.4 days");
    
    format = "{0 unit format:long sequence:day,hour in:seconds maxfrac:0}";
    msg = new MessageFormat(en_US, NY_ZONE, format);
    assertEquals(format(msg, args("123456")), "1 day 10 hours");
    
    msg = new MessageFormat(fr_FR, PARIS_ZONE, format);
    assertEquals(format(msg, args("123456")), "1 jour 10 heures");

    format = "Transmission of {0 unit in:bytes compact:bytes} " +
        "took {1 unit in:second sequence:hour,minute,second format:long}";
    msg = new MessageFormat(en_US, NY_ZONE, format);
    assertEquals(format(msg, args("12345678900", "12345")), 
        "Transmission of 11.5GB took 3 hours 25 minutes 45 seconds");

    msg = new MessageFormat(fr_FR, PARIS_ZONE, format);
    assertEquals(format(msg, args("12345678900", "12345")), 
        "Transmission of 11,5 Go took 3 heures 25 minutes 45 secondes");
    
    format = "{0 unit in:inches sequence:mile,foot,inch group}";
    msg = new MessageFormat(en_US, NY_ZONE, format);
    assertEquals(format(msg, args("123456789")), "1,948mi 2,625′ 9″");
    
    format = "{0 unit in:centimeter sequence:kilometer,meter,centimeter group}";
    msg = new MessageFormat(fr_FR, PARIS_ZONE, format);
    assertEquals(format(msg, args("123456789")), "1 234km 567m 89cm");
  }
  
  @Test
  public void testCurrency() {
    String format = "{0 currency style:name}";
    MessageFormat msg = new MessageFormat(en_US, NY_ZONE, format);
    assertEquals(format(msg, args(money("10.25", "USD"))), "10.25 US dollars");
    assertEquals(format(msg, args(money("10.25", "EUR"))), "10.25 euros");
    
    msg = new MessageFormat(fr_FR, PARIS_ZONE, format);
    assertEquals(format(msg, args(money("10.25", "USD"))), "10,25 dollars des États-Unis");
    assertEquals(format(msg, args(money("10.25", "EUR"))), "10,25 euros");

    format = "{0 currency style:short}";
    msg = new MessageFormat(en_US, NY_ZONE, format);
    assertEquals(format(msg, args(money("1234", "USD"))), "$1.2K");
    assertEquals(format(msg, args(money("999990", "USD"))), "$1M");

    format = "{0 currency style:short mode:significant-maxfrac maxsig:5 minfrac:2}";
    msg = new MessageFormat(en_US, NY_ZONE, format);
    assertEquals(format(msg, args(money("1234", "USD"))), "$1.23K");
    assertEquals(format(msg, args(money("999990", "USD"))), "$999.99K");

    // Invalid currency
    assertEquals(format(msg, args(money("1234", "XYZ"))), "");
  }

  @Test
  public void testNumber() {
    String format = "{0 number maxfrac:2}";
    MessageFormat msg = new MessageFormat(en_US, NY_ZONE, format);
    assertEquals(format(msg, args("3.141592653589793")), "3.14");
    
    format = "{0 number mode:significant maxsig:6}";
    msg = new MessageFormat(en_US, NY_ZONE, format);
    assertEquals(format(msg, args("3.141592653589793")), "3.14159");
  }

  @Test
  public void testDateTime() {
    String format = "{0 datetime date:long time:medium}";
    MessageFormat msg = new MessageFormat(en_US, NY_ZONE, format);
    assertEquals(format(msg, args("1498583746000")), "June 27, 2017 at 1:15:46 PM");
  }

  @Test
  public void testMultiTimezones() {
    String format = "{0 datetime date:long time:medium} ({1 datetime date:long time:medium} UTC)";
    MessageFormat msg = new MessageFormat(en_US, NY_ZONE, format);
    assertEquals(format(msg, args(zoneDateTime("1498583746000", ZoneId.of("America/New_York")),
        zoneDateTime("1498583746000", ZoneId.of("Etc/UTC")))),
        "June 27, 2017 at 1:15:46 PM (June 27, 2017 at 5:15:46 PM UTC)");
  }

  @Test
  public void testDateTimeInterval() {
    String format = "{0;1 datetime-interval}";
    MessageFormat msg = new MessageFormat(en_US, NY_ZONE, format);
    assertEquals(format(msg, args("1509647217000", "1510641417000")), "Nov 2 – 14, 2017");
    assertEquals(format(msg, args("1502298000000", "1502305200000")), "1:00 – 3:00 PM ET");

    msg = new MessageFormat(fr_FR, PARIS_ZONE, format);
    assertEquals(format(msg, args("1509647217000", "1510641417000")), "2–14 nov. 2017");
    assertEquals(format(msg, args("1502298000000", "1502305200000")), "7:00 – 9:00 PM UTC+2");
   
    // Invalid, only start argument referenced
    format = "{0 datetime-interval}";
    msg = new MessageFormat(en_US, NY_ZONE, format);
    assertEquals(format(msg, args("1509647217000", "1510641417000")), "");
    
    // Forcing the skeleton
    format = "{0;1 datetime-interval yMMMd}";
    msg = new MessageFormat(en_US, NY_ZONE, format);
    assertEquals(format(msg, args("1509647217000", "1510641417000")), "Nov 2 – 14, 2017");
    assertEquals(format(msg, args("1502298000000", "1502305200000")), "Aug 9, 2017 – Aug 9, 2017");
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

  private static MessageArg zoneDateTime(String value, ZoneId zoneId) {
    StringMessageArg arg = new StringMessageArg(value);
    arg.setTimeZone(zoneId);
    return arg;
  }

  private static MessageArgs.Builder args() {
    return MessageArgs.newBuilder();
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
