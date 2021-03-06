package com.squarespace.cldr;

import static com.squarespace.cldr.DistanceMap.ANY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.squarespace.cldr.DistanceMap.Node;
import com.squarespace.cldr.LanguageMatch.Pattern;


/**
 * Table supporting fast lookup of distances between locales.
 */
class DistanceTable {

  public static final int MAX_DISTANCE = 100;
  public static final int DEFAULT_THRESHOLD = 50;
  private static final String WILDCARD = "*";

  private static final PartitionTable PARTITION_TABLE = PartitionTable.get();
  private static final DistanceTable INSTANCE = new DistanceTable();
  private final DistanceMap distanceMap = new DistanceMap();
  
  private DistanceTable() {
    List<LanguageMatch> matches = new ArrayList<>(_LanguageRules.LANGUAGE_MATCHES);
    matches.sort((a, b) -> {
      int r = Integer.compare(a.desired().width(), b.desired().width());
      return r != 0 ? r : Integer.compare(a.desired().wildcards(), b.desired().wildcards());
    });

    List<LanguageMatch> wildcards = new ArrayList<>();
    
    for (LanguageMatch match : matches) {
      // Defer application of full wildcards to later.
      if (match.desired().width() == match.desired().wildcards()) {
        wildcards.add(match);
        continue;
      }
      
      // Index the matching rule.
      index(match.desired(), match.supported(), match.distance());
      if (!match.oneway()) {
        // Distance is symmetrical so index the reverse.
        index(match.supported(), match.desired(), match.distance());
      }
    }
    
    // Fill out all levels of the table with wildcard distances.
    for (LanguageMatch match : wildcards) {
      switch (match.desired().wildcards()) {
        case 1:
          distanceMap.put(ANY, ANY, match.distance());
          break;
        
        case 2:
          for (Node node : distanceMap) {
            node.init();
            node.map().put(ANY, ANY, match.distance());
          }
          break;
        
        case 3:
          for (Node node1 : distanceMap) {
            node1.init();
            for (Node node2 : node1.map()) {
              node2.init();
              node2.map().put(ANY, ANY, match.distance());
            }
          }
          break;
      }
    }
  }

  public static DistanceTable get() {
    return INSTANCE;
  }

  /**
   * Returns the distance between the desired and supported locale, using the
   * default distance threshold.
   */
  public int distance(CLDR.Locale desired, CLDR.Locale supported) {
    return distance(desired, supported, DEFAULT_THRESHOLD);
  }
  
  /**
   * Returns the distance between the desired and supported locale, using the
   * given distance threshold. Any distance equal to or greater than the threshold
   * will return the maximum distance.
   */
  public int distance(CLDR.Locale desired, CLDR.Locale supported, int threshold) {
    // Query the top level LANGUAGE
    boolean langEquals = desired.language().equals(supported.language());
    Node node = distanceMap.get(desired.language(), supported.language());
    if (node == null) {
      node = distanceMap.get(ANY, ANY);
    }
    int distance = node.wildcard() ? (langEquals ? 0 : node.distance()) : node.distance();
    
    if (distance >= threshold) {
      return MAX_DISTANCE;
    }
    
    // Go to the next level SCRIPT
    DistanceMap map = node.map();

    boolean scriptEquals = desired.script().equals(supported.script());
    node = map.get(desired.script(), supported.script());
    if (node == null) {
      node = map.get(ANY, ANY);
    }
    distance += node.wildcard() ? (scriptEquals ? 0 : node.distance()) : node.distance();
    
    if (distance >= threshold) {
      return MAX_DISTANCE;
    }

    // Go to the next level TERRITORY
    map = node.map();

    // If the territories happen to be equal, distance is 0 so we're done.
    if (desired.territory().equals(supported.territory())) {
      return distance;
    }

    // Check if the map contains a distance between these two regions.
    node = map.get(desired.territory(), supported.territory());
    if (node == null) {
      // Compare the desired region against supported partitions, and vice-versa.
      node = scanTerritory(map, desired.territory(), supported.territory());
    }
    if (node != null) {
      distance += node.distance();
      return distance < threshold ? distance : MAX_DISTANCE;
    }
    
    // Find the maximum distance between the partitions.
    int maxDistance = 0;

    // These partition sets are always guaranteed to exist and contain at least 1 member.
    boolean match = false;
    Set<String> desiredPartitions = PARTITION_TABLE.getRegionPartition(desired.territory());
    Set<String> supportedPartitions = PARTITION_TABLE.getRegionPartition(supported.territory());

    for (String desiredPartition : desiredPartitions) {
      for (String supportedPartition : supportedPartitions) {
        node = map.get(desiredPartition, supportedPartition);
        if (node != null) {
          maxDistance = Math.max(maxDistance, node.distance());
          match = true;
        }
      }
    }

    if (!match) {
      node = map.get(ANY, ANY);
      maxDistance = Math.max(maxDistance, node.distance());
    }
    
    distance += maxDistance;
    return distance < threshold ? distance : MAX_DISTANCE;
  }
  
  /**
   * Scan the desired region against the supported partitions and vice versa.
   * Return the first matching node.
   */
  private Node scanTerritory(DistanceMap map, String desired, String supported) {
    Node node;
    for (String partition : PARTITION_TABLE.getRegionPartition(desired)) {
      node = map.get(partition, supported);
      if (node != null) {
        return node;
      }
    }
    
    for (String partition : PARTITION_TABLE.getRegionPartition(supported)) {
      node = map.get(desired, partition);
      if (node != null) {
        return node;
      }
    }
    
    return null;
  }

  private void index(Pattern desired, Pattern supported, int distance) {
    switch (desired.width()) {
      case 1:
      {
        String want = key(desired.language());
        String have = key(supported.language());
        distanceMap.put(want, have, distance);
        break;
      } 
      
      case 2:
      {
        String want = key(desired.language());
        String have = key(supported.language());
        Node node = distanceMap.put(want, have, 0);
        node.init();
        
        want = key(desired.script());
        have = key(supported.script());
        node.map().put(want, have, distance);
        break;
      }
     
      case 3:
      {
        String want = key(desired.language());
        String have = key(supported.language());
        Node node = distanceMap.put(want, have, 0);
        node.init();

        want = key(desired.script());
        have = key(supported.script());
        node = node.map().put(want, have, want.equals(ANY) ? DEFAULT_THRESHOLD : 0);
        node.init();
        
        want = key(desired.territory());
        have = key(supported.territory());
        if (want.equals(ANY)) {
          node.map().put(want, have, distance);
        } else {
          Set<String> wantPartitions = want.startsWith("$") ?
              PARTITION_TABLE.variableToPartition(want) : Collections.singleton(want);
          Set<String> havePartitions = have.startsWith("$") ?
              PARTITION_TABLE.variableToPartition(have) : Collections.singleton(have);
          for (String w : wantPartitions) {
            for (String h : havePartitions) {
              node.map().put(w, h, distance);
            }
          }
        }
        
        break;
      }
    }
  }
  
  private static String key(String key) {
    return key.equals(WILDCARD) ? ANY : key;
  }
  
  @Override
  public String toString() {
    return String.format("# Generated by %s.toString()\n\n%s", getClass().getName(), distanceMap);
  }
  
}
