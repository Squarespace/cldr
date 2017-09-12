package com.squarespace.cldr;

import static com.squarespace.cldr.numbers.CurrencyFormatStyle.ACCOUNTING;
import static com.squarespace.cldr.numbers.CurrencyFormatStyle.CODE;
import static com.squarespace.cldr.numbers.CurrencyFormatStyle.NAME;
import static com.squarespace.cldr.numbers.CurrencyFormatStyle.SHORT;
import static com.squarespace.cldr.numbers.CurrencyFormatStyle.SYMBOL;

import com.squarespace.cldr.numbers.CurrencyFormatOptions;
import com.squarespace.cldr.numbers.CurrencySymbolWidth;


class MessageArgsCurrencyParser implements MessageArgsParser {

  private final CurrencyFormatOptions opts = new CurrencyFormatOptions();
  
  public CurrencyFormatOptions options() {
    return opts;
  }
  
  public void reset() {
    opts.reset();
  }
  
  @Override
  public void set(String key, String value) {
    if (key.equals("style")) {
      switch (value) {
        case "accounting":
          opts.setStyle(ACCOUNTING);
          break;

        case "code":
          opts.setStyle(CODE);
          break;

        case "name":
          opts.setStyle(NAME);
          break;

        case "short":
          opts.setStyle(SHORT);
          break;

        case "symbol":
        case "standard":
          opts.setStyle(SYMBOL);
          break;

        default:
          break;
      }
      return;

    } else if (key.equals("symbol")) {
      switch (value) {
        case "default":
          opts.setSymbolWidth(CurrencySymbolWidth.DEFAULT);
          break;

        case "narrow":
          opts.setSymbolWidth(CurrencySymbolWidth.NARROW);
          break;

        default:
          break;
      }
      return;
    }
    
    MessageArgsDecimalParser.setNumberOption(key, value, opts);
  }
  
}
