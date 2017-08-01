package com.squarespace.cldr;

import java.util.HashMap;
import java.util.Map;

import com.squarespace.cldr.dates.CalendarFormatter;
import com.squarespace.cldr.numbers.NumberFormatter;


/**
 * Common Localization Data Repository (CLDR) base class.
 */
public abstract class CLDRBase {

  // TODO: method to resolve appropriate cldr locale from java locale
  // TODO: locale matching logic
  
  protected static final Map<CLDRLocale, CLDRLocale> defaultContent = new HashMap<>();
  protected static final LazyLoader<CLDRLocale, CalendarFormatter> calendarFormatters = new LazyLoader<>();
  protected static final LazyLoader<CLDRLocale, NumberFormatter> numberFormatters = new LazyLoader<>();
  
  public CLDRLocale getLocale(CLDRLocale locale) {
    return defaultContent.getOrDefault(locale, locale);
  } 
  
  public CalendarFormatter getCalendarFormatter(CLDRLocale locale) {
    return calendarFormatters.get(getLocale(locale));
  }

  public NumberFormatter getNumberFormatter(CLDRLocale locale) {
    return numberFormatters.get(getLocale(locale));
  }
  
  public abstract PluralRules getPluralRules();
  
  protected static void registerCalendarFormatter(CLDRLocale locale, Class<? extends CalendarFormatter> cls) {
    calendarFormatters.put(locale, cls);
  }
  
  protected static void registerNumberFormatter(CLDRLocale locale, Class<? extends NumberFormatter> cls) {
    numberFormatters.put(locale, cls);
  }

}
