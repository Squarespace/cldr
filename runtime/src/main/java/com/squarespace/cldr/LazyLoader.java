package com.squarespace.cldr;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Lazy loader for classes, builds an instance on the first request.
 */
class LazyLoader<K, V> {

  private final Map<K, Entry<V>> classMap = new ConcurrentHashMap<>();

  /**
   * Gets a new instance of the class corresponding to the key.
   */
  public V get(K key) {
    Entry<V> entry = classMap.get(key);
    return entry == null ? null : entry.get();
  }
  
  /**
   * Puts a class into the map.
   */
  public void put(K key, Class<? extends V> cls) {
    classMap.put(key, new Entry<>(cls));
  }
  
  private static class Entry<V> {
  
    private Class<? extends V> cls;
    private AtomicReference<V> ref;
    private AtomicBoolean failure;

    public Entry(Class<? extends V> cls) {
      this.cls = cls;
      this.ref = new AtomicReference<>();
      this.failure = new AtomicBoolean();
    }
    
    /**
     * Return the constructed value, or constructs a new one. If the creation
     * of the instance fails, this entry is marked as failed and will not retry.
     */
    public V get() {
      if (failure.get()) {
        return null;
      }
      V value = ref.get();
      if (value != null) {
        return value;
      }
      try {
        value = cls.newInstance();
        ref.lazySet(value);
        return value;
        
      } catch (InstantiationException | IllegalAccessException e) {
        failure.set(true);
        return null;
      }
    }
  }
  
}
