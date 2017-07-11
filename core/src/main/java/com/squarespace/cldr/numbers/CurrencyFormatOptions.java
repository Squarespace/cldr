package com.squarespace.cldr.numbers;


/**
 * Options to select and customize a currency format.
 */
public class CurrencyFormatOptions extends NumberFormatOptions<CurrencyFormatOptions> {

  protected CurrencyFormatStyle style;

  public CurrencyFormatOptions() {
    this(CurrencyFormatStyle.SYMBOL);
  }

  public CurrencyFormatOptions(CurrencyFormatStyle style) {
    this.style = style;
  }

  /**
   * Reset the options to their defaults.
   */
  public void reset() {
    this.style = CurrencyFormatStyle.SYMBOL;
    super.reset();
  }

  public CurrencyFormatStyle style() {
    return style;
  }

  public CurrencyFormatOptions setStyle(CurrencyFormatStyle style) {
    this.style = style;
    return this;
  }

}
