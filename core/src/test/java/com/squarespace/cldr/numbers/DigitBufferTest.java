package com.squarespace.cldr.numbers;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;


public class DigitBufferTest {

  @Test
  public void testBasics() {
    DigitBuffer buf = new DigitBuffer();
    assertEquals(buf.capacity(), 24);

    assertEquals(buf.length(), 0);
    assertEquals(buf.toString(), "");

    buf.append('a');
    buf.append('b');
    buf.append('c');
    assertEquals(buf.length(), 3);
    assertEquals(buf.toString(), "abc");

    assertEquals(buf.charAt(0), 'a');
    assertEquals(buf.charAt(1), 'b');
    assertEquals(buf.charAt(2), 'c');

    buf.append('1');
    buf.append('2');
    assertEquals(buf.length(), 5);
    assertEquals(buf.toString(), "abc12");

    buf.reverse(3);
    assertEquals(buf.length(), 5);
    assertEquals(buf.toString(), "abc21");

    buf.reverse(3);
    buf.append('3');
    buf.append('4');
    buf.append('5');
    buf.reverse(3);
    assertEquals(buf.length(), 8);
    assertEquals(buf.toString(), "abc54321");

    buf.reset();
    assertEquals(buf.length(), 0);
    assertEquals(buf.toString(), "");

    buf.append('a');
    assertEquals(buf.length(), 1);
    assertEquals(buf.toString(), "a");
  }

  @Test
  public void testAppendOther() {
    DigitBuffer buf1 = new DigitBuffer();
    buf1.append("abcdef");

    DigitBuffer buf2 = new DigitBuffer();
    buf2.append("xyz");
    buf1.append(buf2);
    assertEquals(buf1.length(), 9);
    assertEquals(buf1.toString(), "abcdefxyz");

    buf2 = new DigitBuffer();
    for (int i = 0; i < 100; i++) {
      buf2.append((char)('0' + (i % 10)));
    }
    buf1.append(buf2);
    assertEquals(buf1.length(), 109);
    assertEquals(buf1.last(), '9');
  }

  @Test
  public void testSubsequence() {
    DigitBuffer buf = new DigitBuffer();
    for (int i = 0; i < 10; i++) {
      char ch = (char)('a' + i);
      buf.append(ch);
    }
    assertEquals(buf.length(), 10);

    DigitBuffer sub = buf.subSequence(2, 7);
    assertEquals(sub.length(), 5);
    assertEquals(sub.toString(), "cdefg");

    sub = buf.subSequence(2, 2);
    assertEquals(sub.length(), 0);
    assertEquals(sub.toString(), "");

    sub = buf.subSequence(-1, 5);
    assertEquals(sub.length(), 5);
    assertEquals(sub.toString(), "abcde");

    sub = buf.subSequence(2, 20);
    assertEquals(sub.length(), 7);
    assertEquals(sub.toString(), "cdefghi");

    sub = buf.subSequence(7, 2);
    assertEquals(sub.length(), 0);
    assertEquals(sub.toString(), "");
  }

  @Test
  public void testString() {
    DigitBuffer buf = new DigitBuffer(6);
    assertEquals(buf.capacity(), 6);

    buf.append("abc");
    assertEquals(buf.length(), 3);

    buf.append("defghi");
    assertEquals(buf.length(), 9);

    buf.append("jklmno");
    assertEquals(buf.length(), 15);
  }

}
