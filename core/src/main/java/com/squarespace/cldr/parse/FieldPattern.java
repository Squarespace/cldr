package com.squarespace.cldr.parse;


/**
 * Generic nodes to parse fields from pattern strings.
 */
public class FieldPattern {

  public interface Node {
  }

  /**
   * Field node.
   */
  public static class Field implements Node {

    char ch;

    int width;

    public Field(char field, int width) {
      this.ch = field;
      this.width = width;
    }

    public char ch() {
      return ch;
    }

    public int width() {
      return width;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Field) {
        Field other = (Field)obj;
        return ch == other.ch && width == other.width;
      }
      return false;
    }

    @Override
    public String toString() {
      return "Field(" + ch + ", w=" + width + ")";
    }
  }

  /**
   * Literal text node.
   */
  public static class Text implements Node {

    String text;

    public Text(String text) {
      this.text = text;
    }

    public String text() {
      return text;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Text) {
        return text.equals(((Text)obj).text);
      }
      return false;
    }

    @Override
    public String toString() {
      return "Text(" + text + ")";
    }
  }


}
