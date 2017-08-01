package com.squarespace.cldr.plurals;

import com.squarespace.cldr.PluralRules;
import com.squarespace.cldr.numbers.NumberOperands;


public abstract class PluralRulesBase implements PluralRules {

  protected interface Condition {
    boolean eval(NumberOperands o);
  }

}
