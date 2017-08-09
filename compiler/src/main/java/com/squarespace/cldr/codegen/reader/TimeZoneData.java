package com.squarespace.cldr.codegen.reader;

import java.time.ZonedDateTime;
import java.util.List;


/**
 * Holds time zone data during code generation.
 */
public class TimeZoneData {

  public String hourFormat;
  public String gmtFormat;
  public String gmtZeroFormat;
  public String regionFormat;
  public String regionFormatStandard;
  public String regionFormatDaylight;
  public String fallbackFormat;
  
  public List<TimeZoneInfo> timeZoneInfo;
  public List<MetaZoneInfo> metaZoneInfo;
  
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    buf.append("TimeZoneData(");
    buf.append("hourFormat=").append(hourFormat);
    buf.append(" gmtFormat=").append(gmtFormat);
    buf.append(" gmtZeroFormat=").append(gmtZeroFormat);
    buf.append(" regionFormat=").append(regionFormat);
    buf.append(" regionFormatStandard=").append(regionFormatStandard);
    buf.append(" regionFormatDaylight=").append(regionFormatDaylight);
    buf.append(" fallbackFormat=").append(fallbackFormat).append(')');
    return buf.toString();
  }
  
  /**
   * Holds translations for a timezone.
   */
  public static class TimeZoneInfo {
    
    public String zone;
    public String exemplarCity;
    public TimeZoneName nameLong;
    public TimeZoneName nameShort;
    
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append("TimeZoneInfo(").append(zone);
      buf.append(" exemplarCity=").append(exemplarCity);
      if (nameLong != null) {
        buf.append(" nameLong=").append(nameLong);
      }
      if (nameShort != null) {
        buf.append(" nameShort=").append(nameShort);
      }
      buf.append(')');
      return buf.toString();
    }
  }
  
  /**
   * Variants of a timezone name.
   */
  public static class TimeZoneName {
    
    public String generic;
    public String standard;
    public String daylight;

    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append("TimeZoneName(generic=").append(generic);
      buf.append(" standard=").append(standard);
      buf.append(" daylight=").append(daylight);
      buf.append(')');
      return buf.toString();
    }
  }

  /**
   * Holds translations for a metazone.
   */
  public static class MetaZoneInfo {

    public String zone;
    public TimeZoneName nameLong;
    public TimeZoneName nameShort;
    
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append("MetaZoneInfo(").append(zone);
      if (nameLong != null) {
        buf.append(" nameLong=").append(nameLong);
      }
      if (nameShort != null) {
        buf.append(" nameShort").append(nameShort);
      }
      return buf.toString();
    }
  }
  
  /**
   * Holds info about metazones that are in effect at a certain time, for a given timezone.
   */
  public static class MetaZone {
    
    public String zone;
    public List<MetaZoneEntry> metazones;
    
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append("MetaZone(").append(zone);
      if (metazones != null) {
        for (int i = 0; i < metazones.size(); i++) {
          buf.append("\n  ").append(metazones.get(i));
        }
      }
      buf.append(')');
      return buf.toString();
    }

  }

  public static class MetaZoneEntry {
    public String metazone;
    public String fromString;
    public ZonedDateTime from;
    public String toString;
    public ZonedDateTime to;
    
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append("MetaZoneEntry(").append(metazone);
      if (from != null) {
        buf.append(" from=").append(fromString);
        buf.append(" [").append(from.toEpochSecond()).append(']');
      }
      if (to != null) {
        buf.append(" to=").append(toString);
        buf.append(" [").append(to.toEpochSecond()).append(']');
      }
      buf.append(')');
      return buf.toString();
    }
  }

  
}
