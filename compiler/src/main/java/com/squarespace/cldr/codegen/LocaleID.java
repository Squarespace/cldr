package com.squarespace.cldr.codegen;

import com.google.common.base.Objects;


public class LocaleID {

  public final String language;
  public final String territory;
  public final String script;
  public final String variant;
  public final String repr;
  public final String safe;

  public LocaleID(String language, String territory, String script, String variant) {
    this.language = language;
    this.territory = territory;
    this.script = script;
    this.variant = variant;
    this.repr = buildRepr('-');
    this.safe = buildRepr('_');
  }

  /**
   * Hack to parse locales from defaultContent.
   */
  public static LocaleID parse(String raw) {
    String[] parts = raw.split("-");
    switch (parts.length) {
      case 1:
        return new LocaleID(parts[0], "", "", "");
      case 2:
        return new LocaleID(parts[0], parts[1], "", "");
      case 3:
        return new LocaleID(parts[0], parts[2], parts[1], "");
      default:
        throw new RuntimeException("Unparseable locale id: " + raw);
    }
  }
  
  @Override
  public int hashCode() {
    return repr.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof LocaleID) {
      LocaleID other = (LocaleID) obj;
      return Objects.equal(repr, other.repr);
    }
    return false;
  }
  
  @Override
  public String toString() {
    return repr;
  }

  private String buildRepr(char ch) {
    StringBuilder buf = new StringBuilder();
    buf.append(language);
    for (String value : new String[] { script, territory, variant }) {
      if (valid(value)) {
        buf.append(ch);
        buf.append(value);
      }
    }
    return buf.toString();
  }

  private boolean valid(String v) {
    return v != null && !v.isEmpty();
  }
}
