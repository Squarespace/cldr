package com.squarespace.cldr.numbers;

import java.util.List;


public class NumberPattern {

  private static final Format DEFAULT_FORMAT = buildDefaultFormat();

  protected String pattern;

  protected List<Node> parsed;

  protected Format format;

  protected NumberPattern(String pattern, List<Node> parsed, Format format) {
    this.pattern = pattern;
    this.parsed = parsed;
    this.format = format;
  }

  public String pattern() {
    return pattern;
  }

  public List<Node> parsed() {
    return parsed;
  }

  public Format format() {
    return format == null ? DEFAULT_FORMAT : format;
  }

  /**
   * Render a parseable representation of this pattern.
   */
  public String render() {
    StringBuilder buf = new StringBuilder();
    for (Node node : parsed) {
      if (node instanceof Symbol) {
        switch ((Symbol)node) {
          case MINUS:
            buf.append('-');
            break;

          case CURRENCY:
            buf.append('\u00a4');
            break;

          case PERCENT:
            buf.append('%');
            break;
        }
      } else if (node instanceof Text) {
        String text = ((Text)node).text;

        int len = text.length();
        for (int i = 0; i < len; i++) {
          char ch = text.charAt(i);
          switch (ch) {
            case '.':
            case '#':
            case ',':
            case '-':
              buf.append('\'').append(ch).append('\'');
              break;

            default:
              buf.append(ch);
              break;
          }
        }
      } else if (node instanceof Format) {
        Format format = (Format) node;
        format.render(buf);
      }
    }

    return buf.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof NumberPattern) {
      NumberPattern other = (NumberPattern) obj;
      return pattern.equals(other.pattern)
          && parsed.equals(other.parsed)
          && format.equals(other.format);
    }
    return false;
  }

  @Override
  public String toString() {
    return "NumberPattern(pattern=" + pattern + ", parsed=" + parsed + ")";
  }

  /**
   * Node in a number format pattern.
   */
  public interface Node {
  }

  /**
   * A static character in a format.
   */
  public static class Text implements Node {

    protected String text;

    public Text(String s) {
      this.text = s;
    }

    public String text() {
      return text;
    }

    @Override
    public boolean equals(Object obj) {
      return obj instanceof Text ? this.text.equals(((Text)obj).text) : false;
    }

    @Override
    public String toString() {
      return "text=" + text;
    }
  }

  public static enum Symbol implements Node {
    CURRENCY,
    MINUS,
    PERCENT
  }

  /**
   * Fields computed from the decimal section of the format pattern.
   */
  public static class Format implements Node {

    protected int minimumIntegerDigits;
    protected int maximumFractionDigits;
    protected int minimumFractionDigits;
    protected int primaryGroupingSize;
    protected int secondaryGroupingSize;

    public int minimumIntegerDigits() {
      return minimumIntegerDigits;
    }

    public int maximumFractionDigits() {
      return maximumFractionDigits;
    }

    public void setMaximumFractionDigits(int value) {
      this.maximumFractionDigits = value;
    }

    public int minimumFractionDigits() {
      return minimumFractionDigits;
    }

    public void setMinimumFractionDigits(int value) {
      this.minimumFractionDigits = value;
    }

    public int primaryGroupingSize() {
      return primaryGroupingSize == 0 ? 3 : primaryGroupingSize;
    }

    public int secondaryGroupingSize() {
      return secondaryGroupingSize == 0 ? primaryGroupingSize : secondaryGroupingSize;
    }

    /**
     * Render a parseable representation of this number format.
     */
    public void render(StringBuilder buf) {
      boolean grouped = false;
      if (primaryGroupingSize > 0) {
        grouped = true;
        buf.append('#');
        if (secondaryGroupingSize > 0 && secondaryGroupingSize != primaryGroupingSize) {
          buf.append(',');
          for (int i = 0; i < secondaryGroupingSize; i++) {
            buf.append('#');
          }
        }
        buf.append(',');
        for (int i = 0; i < primaryGroupingSize; i++) {
          buf.append('#');
        }
      }

      if (grouped) {
        int end = buf.length() - 1;
        for (int i = 0; i < minimumIntegerDigits; i++) {
          buf.setCharAt(end - i, '0');
        }
      } else {
        for (int i = 0; i < minimumIntegerDigits; i++) {
          buf.append('0');
        }
      }

      if (minimumFractionDigits > 0 || maximumFractionDigits > 0) {
        buf.append('.');
        for (int i = 0; i < minimumFractionDigits; i++) {
          buf.append('0');
        }
        for (int i = 0; i < maximumFractionDigits - minimumFractionDigits; i++) {
          buf.append('#');
        }
      }
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Format) {
        Format other = (Format) obj;
        return this.minimumIntegerDigits == other.minimumIntegerDigits
            && this.minimumFractionDigits == other.minimumFractionDigits
            && this.maximumFractionDigits == other.maximumFractionDigits
            && this.primaryGroupingSize == other.primaryGroupingSize
            && this.secondaryGroupingSize == other.secondaryGroupingSize;
      }
      return false;
    }

    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append("format(");
      buf.append("min_i=").append(minimumIntegerDigits).append(' ');
      buf.append("min_f=").append(minimumFractionDigits).append(' ');
      buf.append("max_f=").append(maximumFractionDigits).append(' ');
      buf.append("pgs=").append(primaryGroupingSize).append(' ');
      buf.append("sgs=").append(secondaryGroupingSize).append(')');
      return buf.toString();
    }
  }

  private static Format buildDefaultFormat() {
    Format format = new Format();
    format.minimumIntegerDigits = 1;
    format.minimumFractionDigits = 2;
    format.maximumFractionDigits = 2;
    format.primaryGroupingSize = 3;
    format.secondaryGroupingSize = 3;
    return format;
  }

}
