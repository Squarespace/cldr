package com.squarespace.cldr.codegen;

import com.google.common.base.Objects;


public class LocaleID {

  // TODO: locale parsing, matching, etc

  public final String language;

  public final String territory;

  public final String script;

  public final String variant;

  private final String repr;

  public LocaleID(String language, String territory, String script, String variant) {
    this.language = language;
    this.territory = territory;
    this.script = script;
    this.variant = variant;
    this.repr = buildRepr();
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

  private String buildRepr() {
    StringBuilder buf = new StringBuilder();
    buf.append(language);
    if (valid(script)) {
      buf.append('-').append(script);
    }
    if (valid(territory)) {
      buf.append('-').append(territory);
    }
    if (valid(variant)) {
      buf.append('-').append(variant);
    }
    return buf.toString();
  }

  private boolean valid(String v) {
    return v != null && !v.isEmpty();
  }
}
