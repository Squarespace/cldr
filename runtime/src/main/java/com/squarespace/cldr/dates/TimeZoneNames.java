package com.squarespace.cldr.dates;


/**
 * Localized name variants for a given timezone.
 */
public class TimeZoneNames {
  
  private final String zoneId;
  private final Name long_;
  private final Name short_;
  
  public TimeZoneNames(String zoneId, Name long_, Name short_) {
    this.zoneId = zoneId;
    this.long_ = long_;
    this.short_ = short_;
  }
  
  public String zoneId() {
    return zoneId;
  }

  public Name longName() {
    return long_;
  }
  
  public Name shortName() {
    return short_;
  }
  
  public static class Name {
    
    private final String generic;
    private final String standard;
    private final String daylight;
    
    public Name(String generic, String standard, String daylight) {
      this.generic = generic;
      this.standard = standard;
      this.daylight = daylight;
    }
    
    public String generic() {
      return generic;
    }
    
    public String standard() {
      return standard;
    }
    
    public String daylight() {
      return daylight;
    }

  }
}
