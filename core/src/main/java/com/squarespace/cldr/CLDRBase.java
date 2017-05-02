package com.squarespace.cldr;

import java.util.HashMap;
import java.util.Map;

import com.squarespace.cldr.dates.CLDRCalendarFormatter;

/**
 * Common Localization Data Repository (CLDR) base class.
 */
public abstract class CLDRBase {

  // TODO: resolve cldr locale from java locale
  // TODO: locale matching logic

  private static final CLDRLocale DEFAULT_LOCALE = new CLDRLocale("en", "", "US", "POSIX");

  protected static final Map<CLDRLocale, CLDRCalendarFormatter> formatterMap = new HashMap<>();

  public CLDRCalendarFormatter getCalendarFormatter(CLDRLocale locale) {
    CLDRCalendarFormatter impl = formatterMap.get(locale);
    if (impl == null) {
      impl = formatterMap.get(DEFAULT_LOCALE);
    }
    return impl;
  }

  protected static void registerFormatter(CLDRCalendarFormatter formatter) {
    formatterMap.put(formatter.locale(), formatter);
  }

}
