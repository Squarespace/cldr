package com.squarespace.cldr.dates;

import java.time.ZonedDateTime;


/**
 * Metazone mapping and rules indicating the time range(s) in which a metazone applies.
 */
public class MetaZone {

  private final String zoneId;
  
  // Entries stored in reverse-chronological order, e.g. first entry most recent.
  private final Entry[] entries;
  private final int size;
  
  public MetaZone(String zoneId, Entry[] entries) {
    this.zoneId = zoneId;
    this.entries = entries;
    this.size = this.entries.length;
  }
  
  public String zoneId() {
    return zoneId;
  }

  /**
   * Find the metazone that applies for a given timestamp.
   */
  public String applies(ZonedDateTime d) {
    long epoch = d.toEpochSecond();
    for (int i = 0; i < size; i++) {
      Entry entry = entries[i];
      if (entry.from == -1) {
        if (entry.to == -1 || epoch < entry.to) {
          return entry.metazoneId;
        }
      } else if (entry.to == -1) {
        if (entry.from <= epoch) {
          return entry.metazoneId;
        }
      } else if (entry.from <= epoch && epoch < entry.to) {
        return entry.metazoneId;
      }
    }
    return null;
  }
  
  public static class Entry {
    private final String metazoneId;
    private final long from;
    private final long to;
    
    public Entry(String metazoneId, long from, long to) {
      this.metazoneId = metazoneId;
      this.from = from;
      this.to = to;
    }
    
    public String metazoneId() {
      return metazoneId;
    }
    
  }
  
}

