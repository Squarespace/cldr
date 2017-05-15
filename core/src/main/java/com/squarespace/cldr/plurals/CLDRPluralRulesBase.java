package com.squarespace.cldr.plurals;


public abstract class CLDRPluralRulesBase {

  protected interface Condition {
    boolean eval(PluralOperands o);
  }

}
