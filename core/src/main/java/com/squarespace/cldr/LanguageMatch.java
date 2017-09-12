package com.squarespace.cldr;

import com.squarespace.cldr.parse.LanguageMatchGrammar;


public class LanguageMatch {

  private static final String WILDCARD = "*";

  private final Pattern desired;
  private final String _desired;

  private final Pattern supported;
  private final String _supported;
  private final int distance;
  private final boolean oneway;

  public LanguageMatch(String desired, String supported, int distance, boolean oneway) {
    this.desired = LanguageMatchGrammar.parse(desired);
    this._desired = desired;
    this.supported = LanguageMatchGrammar.parse(supported);
    this._supported = supported;
    this.distance = distance;
    this.oneway = oneway;
  }

  public Pattern desired() {
    return desired;
  }

  public String desiredRaw() {
    return _desired;
  }

  public Pattern supported() {
    return supported;
  }

  public String supportedRaw() {
    return _supported;
  }

  public int distance() {
    return distance;
  }

  public boolean oneway() {
    return oneway;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    buf.append("LanguageMatch(desired=").append(_desired);
    buf.append(", supported=").append(_supported);
    buf.append(", distance=").append(distance);
    buf.append(", oneway=").append(oneway).append(')');
    return buf.toString();
  }

  public static class Pattern {

    private final String language;
    private final String script;
    private final String territory;
    private final int width;
    private final int wildcards;

    public Pattern(String language, String script, String territory) {
      this.language = language;
      this.script = script;
      this.territory = territory;
      this.width = 1 + (script == null ? 0 : 1) + (territory == null ? 0 : 1);

      // Count how many of the fields are wildcards.
      int count = 0;
      if (WILDCARD.equals(language)) {
        count++;
      }
      if (WILDCARD.equals(script)) {
        count++;
      }
      if (WILDCARD.equals(territory)) {
        count++;
      }
      this.wildcards = count;
    }

    public String language() {
      return language;
    }

    public String script() {
      return script;
    }

    public String territory() {
      return territory;
    }

    public int width() {
      return width;
    }

    public int wildcards() {
      return wildcards;
    }

    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append("Pattern(language=").append(language);
      buf.append(", script=").append(script);
      buf.append(", territory=").append(territory);
      buf.append(", width=").append(width).append(')');
      return buf.toString();
    }

  }
}