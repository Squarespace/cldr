package com.squarespace.cldr.numbers;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;


public class DigitBufferTest {

  @Test
  public void testBasics() {
    DigitBuffer buf = new DigitBuffer();
    assertEquals(buf.capacity(), 24);

    assertEquals(buf.size(), 0);
    assertEquals(buf.toString(), "");

    buf.append('a');
    buf.append('b');
    buf.append('c');
    assertEquals(buf.size(), 3);
    assertEquals(buf.toString(), "abc");

    assertEquals(buf.charAt(0), 'a');
    assertEquals(buf.charAt(1), 'b');
    assertEquals(buf.charAt(2), 'c');

    buf.append('1');
    buf.append('2');
    assertEquals(buf.size(), 5);
    assertEquals(buf.toString(), "abc12");

    buf.reverse(3);
    assertEquals(buf.size(), 5);
    assertEquals(buf.toString(), "abc21");

    buf.reverse(3);
    buf.append('3');
    buf.append('4');
    buf.append('5');
    buf.reverse(3);
    assertEquals(buf.size(), 8);
    assertEquals(buf.toString(), "abc54321");

    buf.reset();
    assertEquals(buf.size(), 0);
    assertEquals(buf.toString(), "");

    buf.append('a');
    assertEquals(buf.size(), 1);
    assertEquals(buf.toString(), "a");
  }

  @Test
  public void testString() {
    DigitBuffer buf = new DigitBuffer(6);
    assertEquals(buf.capacity(), 6);

    buf.append("abc");
    assertEquals(buf.size(), 3);

    buf.append("defghi");
    assertEquals(buf.size(), 9);

    buf.append("jklmno");
    assertEquals(buf.size(), 15);
  }

}
