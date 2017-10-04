package com.squarespace.cldr;

import java.time.ZoneId;
import java.util.Random;

import org.testng.annotations.Test;


public class MessageFormatGarbageTest {

  private static final ZoneId NY_ZONE = ZoneId.of("America/New_York");

  private static final String[] VALID = new String[] {
      "{0 plural =1 {1} one {one} many {many} other {other}}",
      "{0 plural offset:1 =1 {1} other{other}}",
      "{0 select male {male} female {female} other {other}}",
      "{0 datetime long}",
      "{0;1 datetime-interval yMMMd}",
      "{0 currency format:long}",
      "{0 unit in:seconds sequence:day,hour,minute}",
      "{0 selectordinal one{st} other{th}}",
      "{0 number format:long}"
  };

  private static final char[] GARBAGE = new char[] {
      ',', ' ', '{', '}', '*',
  };
  
  private final Random random = new Random(1309L);
  
  @Test
  public void testGarbage() {
    StringMessageArg arg1 = new StringMessageArg("123456.789");
    arg1.setCurrency("USD");
    StringMessageArg arg2 = new StringMessageArg("123458");
    MessageArgs args = MessageArgs.newBuilder()
        .add("x", arg1)
        .add("y", arg2)
        .build();

    double[] thresholds = new double[] { 0.03, 0.10 };
    StringBuilder buf = new StringBuilder();
    for (int i = 0; i < 100000; i++) {
      for (double threshold : thresholds) {
        String format = generate(threshold);
        MessageFormat message = new MessageFormat(CLDR.Locale.en_US, NY_ZONE, format);
        buf.setLength(0);
        message.format(args, buf);
      }
    }
  }
  
  private String generate(double threshold) {
    int index = random.nextInt(VALID.length);
    char[] chars = VALID[index].toCharArray();
    for (int i = 0; i < chars.length; i++) {
      if (random.nextDouble() < threshold) {
        chars[i] = GARBAGE[i % GARBAGE.length];
      }
    }
    return new String(chars);
  }
  
}
