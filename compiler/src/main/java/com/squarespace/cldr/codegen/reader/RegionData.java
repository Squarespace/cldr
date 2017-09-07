package com.squarespace.cldr.codegen.reader;

public class RegionData {
  
  public final boolean isRegion;
  public final String code;

  public RegionData(boolean isRegion, String code) {
    this.isRegion = isRegion;
    this.code = code;
  }
}