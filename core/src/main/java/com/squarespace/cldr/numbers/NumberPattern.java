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

    public int minimumFractionDigits() {
      return minimumFractionDigits;
    }

    public int primaryGroupingSize() {
      return primaryGroupingSize == 0 ? 3 : primaryGroupingSize;
    }

    public int secondaryGroupingSize() {
      return secondaryGroupingSize == 0 ? primaryGroupingSize : secondaryGroupingSize;
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
