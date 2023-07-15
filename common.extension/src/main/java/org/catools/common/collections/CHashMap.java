package org.catools.common.collections;

import org.catools.common.collections.interfaces.CMap;
import org.catools.common.utils.CJsonUtil;

import java.util.HashMap;
import java.util.Map;

public class CHashMap<K, V> extends HashMap<K, V> implements CMap<K, V> {
  public static <A, B> CHashMap<A, B> of(Map<A, B> map) {
    return new CHashMap<>(map);
  }

  public static <A, B> CHashMap<A, B> of(int initialCapacity) {
    return new CHashMap<>(initialCapacity);
  }

  public static <A, B> CHashMap<A, B> of(int initialCapacity, float loadFactor) {
    return new CHashMap<>(initialCapacity, loadFactor);
  }

  public CHashMap() {
  }

  public CHashMap(int initialCapacity, float loadFactor) {
    super(initialCapacity, loadFactor);
  }

  public CHashMap(int initialCapacity) {
    super(initialCapacity);
  }

  public CHashMap(Map<? extends K, ? extends V> m) {
    super(m);
  }

  /**
   * Returns a {@link CSet} of the keys contained in this map.
   *
   * @return a CSet of the keys contained in this map
   */
  @Override
  public CSet<K> keySet() {
    return new CSet<>(super.keySet());
  }

  /**
   * Returns a {@link CList} of the values contained in this map.
   *
   * @return a {@link CList} of the values contained in this map.
   */
  @Override
  public CList<V> values() {
    return new CList<>(super.values());
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(Object c) {
    return c instanceof Map
        && ((Map<K, V>) c).size() == size()
        && containsAll((Map<K, V>) c)
        && (CHashMap.of((Map<K, V>) c)).containsAll(this);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    try {
      return CJsonUtil.toString(this);
    } catch (Throwable t) {
      return keySet().mapToList(CJsonUtil::toString).toString();
    }
  }

}
