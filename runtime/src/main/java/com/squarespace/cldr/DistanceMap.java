package com.squarespace.cldr;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.squarespace.cldr.DistanceMap.Node;

/**
 * Map for storing language distances where each level of the map represents
 * two keys and a distance between them, and an optional child map with 
 * further comparisons.
 */
public class DistanceMap implements Iterable<Node> {

  public static final String ANY = "\ufffd";

  // Initial capacity of the node array. Keep this relatively small to
  // reduce memory usage at each nested level.
  private static final int INITIAL_CAPACITY = 8;
  
  // Percent array is filled before expanding.
  private static final float LOAD_FACTOR = 0.75f;
  
  // Array of nodes.
  private Node[] nodes;
  
  // Mask for truncating key hash.
  private int mask;
  
  // Current size of the map.
  private int size;

  public DistanceMap() {
    this.nodes = new Node[INITIAL_CAPACITY];
    this.mask = this.nodes.length - 1;
  }
  
  @Override
  public Iterator<Node> iterator() {
    return new DistanceMapIterator(nodes);
  }

  public int size() {
    return size;
  }
  
  public Node get(String want, String have) {
    int index = search(want, have, false);
    return index == -1 ? null : nodes[index];
  }

  public Node put(String want, String have, int distance) {
    expand();
    int index = search(want, have, true);
    if (nodes[index] != null) {
      return nodes[index];
    }

    size++;
    Node node = new Node(want, have, distance);
    nodes[index] = node;
    return node;
  }
  
  private int search(String want, String have, boolean free) {
    int index = hash(want, have) & mask;
    while (nodes[index] != null) {
      Node n = nodes[index];
      if (n.want.equals(want) && n.have.equals(have)) {
        return index;
      }
      index = (index + 1) & mask;
    }
    return free ? index : -1;
  }

  private void expand() {
    if (size < Math.floor(nodes.length * LOAD_FACTOR)) {
      return;
    }

    Node[] oldNodes = nodes;
    nodes = new Node[nodes.length * 2];
    mask = nodes.length - 1;

    // Rehash the nodes to their new locations
    for (int i = oldNodes.length - 1; i >= 0; i--) {
      Node node = oldNodes[i];
      if (node != null) {
        int index = hash(node.want, node.have) & mask;
        while (nodes[index] != null) {
          index = (index + 1) & mask;
        }
        nodes[index] = node;
      }
    }
  }
  
  /**
   * Combine the hash codes for the two keys, then mix.
   */
  private static int hash(String k1, String k2) {
    int h = (k1.hashCode() * 33) + k2.hashCode();
    return h ^ (h >>> 16);
  }
  
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    render(buf, 0);
    return buf.toString();
  }

  private static final String INDENT = "               ";
  
  private void render(StringBuilder buf, int level) {
    Node[] temp = new Node[nodes.length];
    System.arraycopy(nodes, 0, temp, 0, nodes.length);
    Arrays.sort(temp, (a, b) -> {
      if (a == null) {
        return b == null ? 0 : -1;
      } else if (b == null) {
        return 1;
      }
     
      int c = a.want.compareTo(b.want);
      return c != 0 ? c : a.have.compareTo(b.have);
    });

    String prior = null;
    int rows = 0;
    for (Node node : temp) {
      if (node == null) {
        continue;
      }
      if (rows != 0) {
        for (int i = 0; i < level; i++) {
          buf.append(INDENT);
        }
      }
      rows++;

      String want = wildcard(node.want);
      String have = wildcard(node.have);

      if (prior == null || !prior.equals(want) || want.equals("*")) {
        prior = want;
        pad(buf, 5, want);
        buf.append(want);
        buf.append(' ');
      } else {
        buf.append("      ");
      }
      
      pad(buf, 5, have);
      buf.append(have);
      buf.append(' ');
      
      pad(buf, 3, Integer.toString(node.distance));
      buf.append(node.distance);

      if (level == 2) {
        buf.append('\n');
      }
      DistanceMap child = node.map();
      if (child != null) {
        child.render(buf, level + 1);
      }
    }
  }
  
  private static String wildcard(String v) {
    return v.codePointAt(0) == 0xFFFD ? "*" : v;
  }
  
  private static void pad(StringBuilder buf, int width, String v) {
    int delta = width - v.length();
    for (int i = 0; i < delta; i++) {
      buf.append(' ');
    }
  }
  
  private static class DistanceMapIterator implements Iterator<Node> {
    
    private final Node[] nodes;
    private int cursor = -1;
    private boolean hasNext = false;
    
    public DistanceMapIterator(Node[] nodes) {
      this.nodes = nodes;
      fetchNext();
    }
    
    @Override
    public boolean hasNext() {
      return hasNext;
    }
    
    @Override
    public Node next() {
      if (!hasNext) {
        throw new NoSuchElementException();
      }
      Node node = nodes[cursor];
      fetchNext();
      return node;
    }
    
    @Override
    public void remove() {
      throw new UnsupportedOperationException("remove() not supported");
    }
    
    private void fetchNext() {
      if (nodes.length == 0) {
        return;
      }
      cursor++;
      while (cursor < nodes.length) {
        if (nodes[cursor] != null) {
          hasNext = true;
          return;
        }
        cursor++;
      }
      hasNext = false;
    }
  }
  
  public static class Node {
    
    private final String want;
    private final String have;
    private final int distance;
    private final boolean wildcard;
    private DistanceMap map;
    
    public Node(String want, String have, int distance) {
      this.want = want;
      this.have = have;
      this.distance = distance;
      this.wildcard = want.equals(ANY) || have.equals(ANY);
    }
    
    public String want() {
      return want;
    }
    
    public String have() {
      return have;
    }
    
    public int distance() {
      return distance;
    }
    
    public boolean wildcard() {
      return wildcard;
    }
    
    public void init() {
      if (map == null) {
        map = new DistanceMap();
      }
    }
    
    public DistanceMap map() {
      return map;
    }
    
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append("node(want=").append(want);
      buf.append(", have=").append(have);
      buf.append(", distance=").append(distance).append(")");
      return buf.toString();
    }
  }
  
}
