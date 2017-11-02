package com.squarespace.cldr;

import java.math.BigDecimal;
import java.time.ZoneId;


/**
 * Default class for generic String arguments. This may or may not be numbers,
 * so we attempt to force-cast to BigDecimal when needed. Returns a null
 * currency by default.
 */
public class StringMessageArg implements MessageArg {

  private final String value;
  private BigDecimal decimalValue;
  private String currency;
  private ZoneId timeZone;

  public StringMessageArg(String value) {
    this.value = value;
  }

  @Override
  public void reset() {
    this.decimalValue = null;
    this.currency = null;
    this.timeZone = null;
  }
  
  @Override
  public boolean resolve() {
    return true;
  }

  @Override
  public String asString() {
    return value;
  }
  
  @Override
  public long asLong() {
    return value == null ? 0 : MessageFormat.toLong(value, 0, value.length());
  }

  @Override
  public BigDecimal asBigDecimal() {
    try {
      if (decimalValue == null) {
        decimalValue = new BigDecimal(value);
      }
    } catch (NumberFormatException e) {
      decimalValue = BigDecimal.ZERO;
    }
    return decimalValue;
  }

  @Override
  public String currency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  @Override
  public ZoneId timeZone() {
    return timeZone;
  }

  public void setTimeZone(ZoneId timeZone) {
    this.timeZone = timeZone;
  }

}
