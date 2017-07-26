package com.squarespace.cldr.numbers;

import java.util.Arrays;


/**
 * Simple character buffer for formatting decimal numbers.
 */
public class DigitBuffer {

  private char buf[];

  private int size;

  /**
   * Construct a buffer with default capacity.
   */
  public DigitBuffer() {
    this(24);
  }

  /**
   * Construct a buffer with given capacity.
   */
  public DigitBuffer(int capacity) {
    this.buf = new char[capacity];
  }

  /**
   * Reset the buffer to be used again.
   */
  public void reset() {
    this.size = 0;
  }

  /**
   * Number of characters in the buffer.
   */
  public int size() {
    return size;
  }

  /**
   * Total number of characters this buffer can store.
   */
  public int capacity() {
    return buf.length;
  }

  public char first() {
    return size > 0 ? buf[size] : ' ';
  }

  public char last() {
    return size < buf.length ? buf[size] : ' ';
  }

  /**
   * Append a string to the buffer, expanding it if necessary.
   */
  public DigitBuffer append(String s) {
    int len = s.length();
    check(len);
    for (int i = 0; i < len; i++) {
      this.buf[size] = s.charAt(i);
      size++;
    }
    return this;
  }

  /**
   * Append a character to the buffer, expanding it if necessary.
   */
  public DigitBuffer append(char ch) {
    check(1);
    this.buf[size] = ch;
    size++;
    return this;
  }

  /**
   * Copy the populated chars to the destination buffer.
   * @param buf
   */
  public void appendTo(StringBuilder dest) {
    dest.append(buf, 0, size);
  }

  /**
   * Reverse all characters from start to end of buffer.
   */
  public void reverse(int start) {
    int end = size - 1;
    int num = end - start >> 1;
    for (int i = 0; i <= num; i++) {
      int j = start + i;
      int k = end - i;
      char c = buf[k];
      buf[k] = buf[j];
      buf[j] = c;
    }
  }

  /**
   * Returns the character at the given position.
   */
  public char charAt(int i) {
    return buf[i];
  }

  /**
   * Render the buffer to a String.
   */
  @Override
  public String toString() {
    return new String(buf, 0, size);
  }

  /**
   * Ensure the buffer is large enough for the next append.
   */
  private void check(int length) {
    if (size + length >= buf.length) {
      buf = Arrays.copyOf(buf, buf.length + length + 16);
    }
  }

}