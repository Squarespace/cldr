package com.squarespace.cldr.plurals;

import com.squarespace.cldr.numbers.NumberOperands;
import com.squarespace.cldr.plurals.PluralCategory;


public interface PluralRules {

  PluralCategory evalCardinal(String language, NumberOperands operands);
  PluralCategory evalOrdinal(String language, NumberOperands operands);
  
}
