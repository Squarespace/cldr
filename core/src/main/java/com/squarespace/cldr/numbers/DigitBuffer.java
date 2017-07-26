package com.squarespace.cldr.numbers;

import java.util.Arrays;


/**
 * Simple character buffer for formatting decimal numbers.
 */
public class DigitBuffer implements CharSequence {

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

  private DigitBuffer(char[] buf, int size) {
    this.buf = buf;
    this.size = size;
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
  @Override
  public int length() {
    return size;
  }

  @Override
  public DigitBuffer subSequence(int start, int end) {
    start = clamp(start, 0, size - 1);
    end = clamp(end, start, size - 1);
    int sz = end - start;
    char[] newbuf = new char[16 + sz];
    System.arraycopy(buf, start, newbuf, 0, sz);
    return new DigitBuffer(newbuf, sz);
  }

  /**
   * Returns the character at the given position.
   */
  @Override
  public char charAt(int i) {
    return buf[i];
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
    int i = size - 1;
    return i < buf.length ? buf[i] : ' ';
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
   * Append the contents of the other buffer to this one.
   */
  public void append(DigitBuffer other) {
    check(other.size);
    System.arraycopy(other.buf, 0, buf, size, other.size);
    size += other.size;
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

  private int clamp(int v, int min, int max) {
    return v < min ? min : (v > max ? max : v);
  }

}