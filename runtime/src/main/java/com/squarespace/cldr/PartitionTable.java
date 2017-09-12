package com.squarespace.cldr;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;


/**
 * Builds a set of partitions for use in enhanced language matching. This splits
 * world regions into several clusters based on distance.
*/
class PartitionTable {

  // Partition identifier's base character Greek alpha.
  private static final char BASE_ID = '\u03b1';

  // Set containing a single element that will force a lookup on ANY.
  private static final Set<String> EMPTY_PARTITION = Collections.singleton("");
  
  // Since this is a pre-generated mapping based on external data keep one
  // application-wide instance.
  private static final PartitionTable INSTANCE = new PartitionTable();
  
  // Maps variable set to a partition identifier.
  private final Map<String, String> partitionIds = new HashMap<>();

  // Maps a partition's identifier to the variables in the partition.
  private final Map<String, String> partitionIdReverse = new TreeMap<>();

  // Maps a language match variable to the set of partitions it covers.
  private final Map<String, Set<String>> variableToPartitionIds = new HashMap<>();
  
  // Maps a territory to the variables that match it. For example the
  // country "US" would match the variables [$enUS, $americas, $!cnsar, $!maghreb]
  private final Map<String, Set<String>> territoryToVariables = new HashMap<>();

  // Maps the partition identifier to the set of territories it contains.
  private final Map<String, Set<String>> partitionToTerritories = new HashMap<>();

  // Maps all UN M.49 macro regions to the list of contained partitions.
  private final Map<String, Set<String>> macroRegionToPartitions = new TreeMap<>();

  // Maps each territory to the partition it belongs to.
  private final Map<String, Set<String>> territoryToPartition = new HashMap<>();
  
  // All territories in the world, contained within UN M.49 code 001.
  private final Set<String> allTerritories;
  
  // Sequence for generating unique partition identifiers.
  private int idSequence = 0;

  private PartitionTable() {
    Map<String, Set<String>> territories = _LanguageRules.TERRITORIES;
    this.allTerritories = territories.get("001");
    
    for (String variable : sortedList(_LanguageRules.VARIABLES.keySet())) {
      List<String> countries = sortedList(_LanguageRules.VARIABLES.get(variable));

      // Map each country to the variable it matches.
      for (String country : countries) {
        set(territoryToVariables, country, variable);
      }
      
      // Map the inverse by subtracting this variable's matched countries from
      // the set of all world countries.
      Set<String> inverse = new HashSet<>(allTerritories);
      inverse.removeAll(countries);
      
      variable = "$!" + variable.substring(1);
      for (String country : sortedList(inverse)) {
        set(territoryToVariables, country, variable);
      }
    }
    
    // Map the variables to a partition. Using CLDR v31.0.1 this splits the
    // world into 6 partitions, with each country belonging to 1 partition.
    for (String country : sortedList(territoryToVariables.keySet())) {
      Set<String> variables = territoryToVariables.get(country);
      String id = getPartition(variables);
      set(partitionToTerritories, id, country);
      set(territoryToPartition, country, id);
      for (String variable : variables) {
        set(variableToPartitionIds, variable, id);
      }
    }
    
    // Map each UN M.49 macro region to one or more partitions.
    for (String macro : sortedList(territories.keySet())) {
      for (String country : territories.get(macro)) {
        Set<String> vars = territoryToVariables.get(country);
        String id = getPartition(vars);
        set(macroRegionToPartitions, macro, id);
      }
    }
   
    // Create an inverse mapping of partition id -> variables.
    partitionIds.entrySet().forEach(e -> partitionIdReverse.put(e.getValue(), e.getKey()));
    
    // Rebuild sets we expose publicly as immutable.
    makeSetsImmutable(variableToPartitionIds);
    makeSetsImmutable(territoryToPartition);
    makeSetsImmutable(macroRegionToPartitions);
  }
  
  /**
   * Return the singleton instance.
   */
  public static PartitionTable get() {
    return INSTANCE;
  }
  
  public Set<String> variableToPartition(String variable) {
    return variableToPartitionIds.get(variable);
  }
  
  public Set<String> getRegionPartition(String region) {
    Set<String> result = territoryToPartition.get(region);
    return result == null ? macroRegionToPartitions.getOrDefault(region, EMPTY_PARTITION) : result;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    for (String id : partitionIdReverse.keySet()) {
      buf.append(id).append(" -> ").append(partitionIdReverse.get(id)).append('\n');
    }
    buf.append('\n');
    for (String macro : macroRegionToPartitions.keySet()) {
      buf.append(macro).append("  ").append(macroRegionToPartitions.get(macro)).append('\n');
    }
    buf.append('\n');
    for (String country : sortedList(territoryToPartition.keySet())) {
      buf.append(country).append("  ").append(territoryToPartition.get(country)).append('\n');
    }
    return buf.toString();
  }
  
  /**
   * Retrieve the partition identifier for this set of matching variables.
   */
  private String getPartition(Set<String> variables) {
    String key = buildKey(variables);
    return partitionIds.computeIfAbsent(key, k -> Character.toString((char)(BASE_ID + idSequence++)));
  }
  
  /**
   * Sort the set of variables and join to a comma-delimited string.
   */
  private static String buildKey(Set<String> variables) {
    return variables.stream().sorted().collect(Collectors.joining(", "));
  }

  private static List<String> sortedList(Set<String> set) {
    return set.stream().sorted().collect(Collectors.toList());
  }
  
  /**
   * Helper to set values in Map<K, Set<V>>.
   */
  private static <K, V> void set(Map<K, Set<V>> map, K key, V value) {
    Set<V> values = map.get(key);
    if (values == null) {
      values = new HashSet<>();
      map.put(key, values);
    }
    values.add(value);
  }
  
  /**
   * Convert values to immutable sets.
   */
  private static <K, V> void makeSetsImmutable(Map<K, Set<V>> map) {
    Set<K> keys = map.keySet();
    for (K key : keys) {
      Set<V> value = map.get(key);
      map.put(key, Collections.unmodifiableSet(value));
    }
  }
  
}
