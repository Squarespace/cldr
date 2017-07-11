package com.squarespace.cldr;

import java.time.ZoneId;


/**
 * Trivial demonstration of recursive formatting, showing 2 levels of pluralization.
 */
public class MsgDemo {

  private static final CLDRLocale EN_US = new CLDRLocale("en", "", "US", "POSIX");
  private static final ZoneId ZONEID = ZoneId.of("America/New_York");

  public static void main(String[] ignore) {
    run("");
    System.out.println("---------------------------------------------");
    run("offset:1");
  }

  private static void run(String extra) {
    String format = "{0, plural, " + extra +
        "     =0 {Be the first to like this}" +
        "     =1 {You liked this}" +
        "    one {You and someone else liked this}" +
        "  other {You and # others liked {1, plural, =1{this item} other{these # items}}}" +
        "}";
    MessageFormat msg = new MessageFormat(EN_US, ZONEID, format);
    for (int i = 0; i < 6; i++) {
      StringBuilder buf = new StringBuilder();
      msg.format(args("" + i, i % 2 == 0 ? "1" : "2"), buf);
      System.out.println(buf.toString());
    }
  }

  private static MessageArgs args(String...values) {
    MessageArgs.Builder builder = MessageArgs.newBuilder();
    for (String v : values) {
      builder.add(v);
    }
    return builder.build();
  }

}
