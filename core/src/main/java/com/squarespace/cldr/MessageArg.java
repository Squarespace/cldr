package com.squarespace.cldr;

import com.squarespace.cldr.plurals.PluralOperands;


/**
 * Represents an argument to {@link MessageFormatOld}.
 */
public class MessageArg {

  private String value;

  private PluralOperands operands;

  public MessageArg() {
  }

  public MessageArg(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public PluralOperands getPluralOperands() {
    if (this.operands == null) {
      this.operands = new PluralOperands(value);
    }
    return this.operands;
  }

}
