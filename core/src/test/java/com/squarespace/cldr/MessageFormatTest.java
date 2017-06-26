package com.squarespace.cldr;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;



public class MessageFormatTest {

  private static final MessageArg[] EMPTY_ARGS = new MessageArg[]{ };

  @Test
  public void testPlural() {
    String msg = "{0 cardinal one{# is one} few{# is few} many{# is many} other{# is other}}";
    assertEquals(format(msg, "en", "USD", args("1")), "1 is one");
    assertEquals(format(msg, "en", "USD", args("1.0")), "1.0 is other");
    assertEquals(format(msg, "en", "USD", args("1.3")), "1.3 is other");
    assertEquals(format(msg, "en", "USD", args("6")), "6 is other");

    assertEquals(format(msg, "pl", "USD", args("1")), "1 is one");
    assertEquals(format(msg, "pl", "USD", args("3")), "3 is few");
    assertEquals(format(msg, "pl", "USD", args("6")), "6 is many");
    assertEquals(format(msg, "pl", "USD", args("1.0")), "1.0 is other");
    assertEquals(format(msg, "pl", "USD", args("1.3")), "1.3 is other");
  }

  @Test
  public void testCurrency() {
    String msg = "{0 currency style:name min-fractional-digits:2}";
    assertEquals(format(msg, "en", "USD", args("10.25")), "$10.25");
  }

  @Test
  public void testNumber() {
    String msg = "{0 number max-fractional-digits:2}";
    assertEquals(format(msg, "en", "USD", args("3.141592653589793")), "3.14");
  }

  private String format(String format, String language, String currency, MessageArg[] args) {
    MessageFormat msg = new MessageFormat(format, language, currency);
    StringBuilder buf = new StringBuilder();
    msg.format(buf, args);
    return buf.toString();
  }

  private static MessageArg[] args(String... value) {
    List<MessageArg> result = new ArrayList<>();
    for (String v : value) {
      result.add(new MessageArg(v));
    }
    return result.toArray(EMPTY_ARGS);
  }

}
