package com.squarespace.cldr;


/**
 * A CLDR locale.
 *
 * CLDR Territory / Language coverage:
 * http://www.unicode.org/cldr/charts/latest/supplemental/territory_language_information.html
 */
public class CLDRLocale {

  private static final String UND = "und";

  private final String language;
  private final String script;
  private final String territory;
  private final String variant;
  private final String repr;

  public CLDRLocale(String language, String script, String territory, String variant) {
    if (language == null || language.isEmpty()) {
      language = UND;
    }

    String repr = language;
    if (script == null) {
      script = "";
    }
    if (territory == null) {
      territory = "";
    }
    if (variant == null) {
      variant = "";
    }

    this.language = language;
    this.script = script;
    this.territory = territory;
    this.variant = variant;

    if (!script.isEmpty()) {
      repr += "-" + script;
    }
    if (!territory.isEmpty()) {
      repr += "-" + territory;
    }
    if (!variant.isEmpty()) {
      repr += "-" + variant;
    }
    this.repr = repr;
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

  public String variant() {
    return variant;
  }

  @Override
  public String toString() {
    return repr;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof CLDRLocale) {
      return repr.equals(((CLDRLocale)obj).repr);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return repr.hashCode();
  }
}
