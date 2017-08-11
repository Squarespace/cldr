package com.squarespace.cldr.plurals;

import com.squarespace.cldr.numbers.NumberOperands;


abstract class PluralRulesBase implements PluralRules {

  protected interface Condition {
    boolean eval(NumberOperands o);
  }

}
