package com.squarespace.cldr.plurals;

import com.squarespace.cldr.numbers.NumberOperands;


public abstract class PluralRulesBase {

  protected interface Condition {
    boolean eval(NumberOperands o);
  }

}
