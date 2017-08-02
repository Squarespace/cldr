package com.squarespace.cldr.codegen.reader;

import java.util.Map;

import com.squarespace.cldr.plurals.PluralCategory;
import com.squarespace.cldr.units.Unit;


public class UnitData {

  public UnitPatterns long_;
  public UnitPatterns narrow;
  public UnitPatterns short_;

  public static class UnitPatterns {
    public String compoundUnitPattern;
    public Map<Unit, UnitPattern> unitPatterns;
    public Map<String, String> coordinatePatterns;
  }

  public static class UnitPattern {
    public String displayName;
    public String perUnitPattern;
    public Map<PluralCategory, String> patterns;
  }
  
}
