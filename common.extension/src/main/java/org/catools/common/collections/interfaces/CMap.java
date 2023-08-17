package org.catools.common.collections.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CLinkedMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.extensions.states.interfaces.CMapState;
import org.catools.common.extensions.verify.interfaces.base.CMapVerify;
import org.catools.common.extensions.verify.interfaces.verifier.CMapVerifier;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * This interface {@code CMap} uses to facilitate Map related functionality for testing. We hope to
 * extend this class with more useful methods.
 *
 * @see Collection
 * @see CHashMap
 * @see CLinkedMap
 * @see CSet
 * @see CList
 */
@JsonDeserialize(as = CHashMap.class)
@JsonIgnoreProperties(value = {"empty"})
public interface CMap<K, V> extends Map<K, V>, CMapVerify<K, V>, CMapVerifier<K, V>, CMapState<K, V> {
  /**
   * get the {@link Map<K,V>} of items
   *
   * @return the CMap to be used in related interface
   */
  @Override
  @JsonIgnore
  default Map<K, V> _get() {
    return this;
  }

  /**
   * Returns a {@link CSet} view of the mappings contained in this map. The set is backed by the
   * map, so changes to the map are reflected in the set, and vice-versa. If the map is modified
   * while an iteration over the set is in progress (except through the iterator's own {@code
   * remove} operation, or through the {@code setValue} operation on a map entry returned by the
   * iterator) the results of the iteration are undefined. The set supports element removal, which
   * removes the corresponding mapping from the map, via the {@code Iterator.remove}, {@code
   * CSet.remove}, {@code removeAll}, {@code retainAll} and {@code clear} operations. It does not
   * support the {@code add} or {@code addAll} operations.
   *
   * @return CSet of the mappings contained in this map
   */
  default CSet<Map.Entry<K, V>> asSet() {
    return new CSet<>(entrySet());
  }

  /**
   * Returns a {@link CSet} view of the keys contained in this map.
   *
   * @return CSet of the mappings contained in this map
   */
  @Override
  default CSet<K> keySet() {
    return new CSet<>(((Map<K, V>) this).keySet());
  }

  /**
   * Returns a {@link CSet} view of the keys contained in this map.
   *
   * @return CSet of the mappings contained in this map
   */
  @Override
  default CList<V> values() {
    return new CList<>(((Map<K, V>) this).values());
  }

  /**
   * Returns all element from map which matched the {@code predicate} otherwise return empty {@code
   * CHashMap}. The preferred alternative to this method is {@see #getFirst(BiPredicate)}, {@link
   * #getFirstValueByKey(Predicate)} {@see #getFirstKeyByValue(Predicate)} {@link
   * #getAllValues(Predicate)}, {@see #getAllKeys(Predicate)}.
   *
   * @param predicate tests against key and value
   * @return {@code CHashMap} with all elements which matched the {@code predicate}
   */
  default CHashMap<K, V> getAll(BiPredicate<K, V> predicate) {
    CHashMap<K, V> result = new CHashMap<>();
    for (java.util.Map.Entry<K, V> entry : asSet()) {
      if (predicate.test(entry.getKey(), entry.getValue())) {
        result.put(entry.getKey(), entry.getValue());
      }
    }
    return result;
  }

  /**
   * Returns {@code CList} of key of elements from map which matched the {@code predicate} otherwise
   * return empty list. The preferred alternative to this method is {@see #getFirst(BiPredicate)},
   * {@see #getAll(BiPredicate)}, {@see #getFirstValueByKey(Predicate)} {@link
   * #getFirstKeyByValue(Predicate)} {@see #getAllValues(Predicate)}.
   *
   * @param predicate tests against value
   * @return {@code CList} of key of elements from map which matched the {@code predicate} otherwise
   * return empty list.
   */
  default CSet<K> getAllKeys(Predicate<V> predicate) {
    CSet<K> result = new CSet<>();
    for (java.util.Map.Entry<K, V> entry : asSet()) {
      if (predicate.test(entry.getValue())) {
        result.add(entry.getKey());
      }
    }
    return result;
  }

  /**
   * Returns {@code CList} of value of elements from map which matched the {@code predicate}
   * otherwise return empty list. The preferred alternative to this method is {@link
   * #getFirst(BiPredicate)}, {@see #getAll(BiPredicate)}, {@see #getFirstValueByKey(Predicate)}
   * {@see #getFirstKeyByValue(Predicate)} {@see #getAllKeys(Predicate)}.
   *
   * @param predicate tests against key
   * @return {@code CList} of value of elements from map which matched the {@code predicate}
   * otherwise return empty list.
   */
  default CList<V> getAllValues(Predicate<K> predicate) {
    CList<V> result = new CList<>();
    for (java.util.Map.Entry<K, V> entry : asSet()) {
      if (predicate.test(entry.getKey())) {
        result.add(entry.getValue());
      }
    }
    return result;
  }

  /**
   * Returns first element from map which matched the {@code predicate} otherwise throws {@code
   * NoSuchElementException}. The preferred alternative to this method is {@link
   * #getAll(BiPredicate)}, {@see #getFirstValueByKey(Predicate)} {@link
   * #getFirstKeyByValue(Predicate)} {@see #getAllValues(Predicate)}, {@link
   * #getAllKeys(Predicate)}.
   *
   * @param predicate tests against key and value
   * @return the non-{@code null} value described by this {@code Optional}
   * @throws NoSuchElementException if no value is present
   */
  default java.util.Map.Entry<K, V> getFirst(BiPredicate<K, V> predicate) {
    for (java.util.Map.Entry<K, V> entry : asSet()) {
      if (predicate.test(entry.getKey(), entry.getValue())) {
        return entry;
      }
    }
    throw new NoSuchElementException("No value present for provided predicate");
  }

  /**
   * Returns key of first element from map which its value match the {@code predicate} otherwise
   * throw {@code NoSuchElementException}. The preferred alternative to this method is {@link
   * #getFirst(BiPredicate)}, {@see #getAll(BiPredicate)}, {@see #getFirstValueByKey(Predicate)}
   * {@see #getAllValues(Predicate)} {@see #getAllKeys(Predicate)}.
   *
   * @param value value to search for
   * @return key of first element from map which matched the {@code predicate} otherwise return
   * null.
   * @throws NoSuchElementException in case no match found
   */
  default K getFirstKeyByValue(V value) {
    return getFirstKeyByValue(v -> Objects.equals(v, value));
  }

  /**
   * Returns key of first element from map which its value match the {@code predicate} otherwise
   * throw {@code NoSuchElementException}. The preferred alternative to this method is {@link
   * #getFirst(BiPredicate)}, {@see #getAll(BiPredicate)}, {@see #getFirstValueByKey(Predicate)}
   * {@see #getAllValues(Predicate)} {@see #getAllKeys(Predicate)}.
   *
   * @param predicate tests against value
   * @return key of first element from map which matched the {@code predicate} otherwise return
   * null.
   * @throws NoSuchElementException in case no match found
   */
  default K getFirstKeyByValue(Predicate<V> predicate) {
    K k = getFirstKeyByValueOrNull(predicate);
    if (k == null) {
      throw new NoSuchElementException("No key present for provided predicate");
    }
    return k;
  }

  /**
   * Returns key of first element from map which its value match the {@code predicate} otherwise
   * return {@code otherKey}. The preferred alternative to this method is {@link
   * #getFirst(BiPredicate)}, {@see #getAll(BiPredicate)}, {@see #getFirstValueByKey(Predicate)}
   * {@see #getAllValues(Predicate)} {@see #getAllKeys(Predicate)}.
   *
   * @param value    value to search for
   * @param otherKey to return if no match found
   * @return key of first element from map which matched the {@code predicate} otherwise return
   * null.
   */
  default K getFirstKeyByValueOrElse(V value, K otherKey) {
    return getFirstKeyByValueOrElse(v -> Objects.equals(v, value), otherKey);
  }

  /**
   * Returns key of first element from map which its value match the {@code predicate} otherwise
   * return {@code otherKey}. The preferred alternative to this method is {@link
   * #getFirst(BiPredicate)}, {@see #getAll(BiPredicate)}, {@see #getFirstValueByKey(Predicate)}
   * {@see #getAllValues(Predicate)} {@see #getAllKeys(Predicate)}.
   *
   * @param predicate tests against value
   * @param otherKey  to return if no match found
   * @return key of first element from map which matched the {@code predicate} otherwise return
   * null.
   */
  default K getFirstKeyByValueOrElse(Predicate<V> predicate, K otherKey) {
    for (java.util.Map.Entry<K, V> entry : asSet()) {
      if (predicate.test(entry.getValue())) {
        return entry.getKey();
      }
    }
    return otherKey;
  }

  /**
   * Returns key of first element from map which its value match the {@code predicate} otherwise
   * return null. The preferred alternative to this method is {@see #getFirst(BiPredicate)}, {@link
   * #getAll(BiPredicate)}, {@see #getFirstValueByKey(Predicate)} {@see #getAllValues(Predicate)}
   * {@see #getAllKeys(Predicate)}.
   *
   * @param value value to search for
   * @return key of first element from map which matched the {@code predicate} otherwise return
   * null.
   */
  default K getFirstKeyByValueOrNull(V value) {
    return getFirstKeyByValueOrNull(v -> Objects.equals(v, value));
  }

  /**
   * Returns key of first element from map which its value match the {@code predicate} otherwise
   * return null. The preferred alternative to this method is {@see #getFirst(BiPredicate)}, {@link
   * #getAll(BiPredicate)}, {@see #getFirstValueByKey(Predicate)} {@see #getAllValues(Predicate)}
   * {@see #getAllKeys(Predicate)}.
   *
   * @param predicate tests against value
   * @return key of first element from map which matched the {@code predicate} otherwise return
   * null.
   */
  default K getFirstKeyByValueOrNull(Predicate<V> predicate) {
    return getFirstKeyByValueOrElse(predicate, null);
  }

  /**
   * Returns value of first element from map which its key match the {@code predicate} otherwise
   * throw {@code NoSuchElementException}. The preferred alternative to this method is {@link
   * #getFirst(BiPredicate)}, {@see #getAll(BiPredicate)}, {@see #getFirstValueByKey(Predicate)}
   * {@see #getFirstKeyByValue(Predicate)} {@see #getAllKeys(Predicate)}.
   *
   * @param key the key we are searching for
   * @return value of first element from map which matched the {@code predicate} otherwise return
   * null.
   * @throws NoSuchElementException if not match found
   */
  default V getFirstValueByKey(K key) {
    return getFirstValueByKey(k -> Objects.equals(k, key));
  }

  /**
   * Returns value of first element from map which its key match the {@code predicate} otherwise
   * throw {@code NoSuchElementException}. The preferred alternative to this method is {@link
   * #getFirst(BiPredicate)}, {@see #getAll(BiPredicate)}, {@see #getFirstValueByKey(Predicate)}
   * {@see #getFirstKeyByValue(Predicate)} {@see #getAllKeys(Predicate)}.
   *
   * @param predicate tests against key
   * @return value of first element from map which matched the {@code predicate} otherwise return
   * null.
   * @throws NoSuchElementException if not match found
   */
  default V getFirstValueByKey(Predicate<K> predicate) {
    V v = getFirstValueByKeyOrNull(predicate);
    if (v == null) {
      throw new NoSuchElementException("No value present for provided predicate");
    }
    return v;
  }

  /**
   * Returns value of first element from map which its key match the {@code predicate} otherwise
   * return otherValue. The preferred alternative to this method is {@see #getFirst(BiPredicate)},
   * {@see #getAll(BiPredicate)}, {@see #getFirstValueByKey(Predicate)} {@link
   * #getFirstKeyByValue(Predicate)} {@see #getAllKeys(Predicate)}.
   *
   * @param key        the key we are searching for
   * @param otherValue to return in case if predicate did not match any value
   * @return value of first element from map which matched the {@code predicate} otherwise return
   * null.
   */
  default V getFirstValueByKeyOrElse(K key, V otherValue) {
    return getFirstValueByKeyOrElse(k -> Objects.equals(k, key), otherValue);
  }

  /**
   * Returns value of first element from map which its key match the {@code predicate} otherwise
   * return otherValue. The preferred alternative to this method is {@see #getFirst(BiPredicate)},
   * {@see #getAll(BiPredicate)}, {@see #getFirstValueByKey(Predicate)} {@link
   * #getFirstKeyByValue(Predicate)} {@see #getAllKeys(Predicate)}.
   *
   * @param predicate  tests against key
   * @param otherValue to return in case if predicate did not match any value
   * @return value of first element from map which matched the {@code predicate} otherwise return
   * null.
   */
  default V getFirstValueByKeyOrElse(Predicate<K> predicate, V otherValue) {
    for (java.util.Map.Entry<K, V> entry : asSet()) {
      if (predicate.test(entry.getKey())) {
        return entry.getValue();
      }
    }
    return otherValue;
  }

  /**
   * Returns value of first element from map which its key match the {@code predicate} otherwise
   * return otherValue. The preferred alternative to this method is {@see #getFirst(BiPredicate)},
   * {@see #getAll(BiPredicate)}, {@see #getFirstValueByKey(Predicate)} {@link
   * #getFirstKeyByValue(Predicate)} {@see #getAllKeys(Predicate)}.
   *
   * @param key the key we are searching for
   * @return value of first element from map which matched the {@code predicate} otherwise return
   * null.
   */
  default V getFirstValueByKeyOrNull(K key) {
    return getFirstValueByKeyOrNull(k -> Objects.equals(k, key));
  }

  /**
   * Returns value of first element from map which its key match the {@code predicate} otherwise
   * return otherValue. The preferred alternative to this method is {@see #getFirst(BiPredicate)},
   * {@see #getAll(BiPredicate)}, {@see #getFirstValueByKey(Predicate)} {@link
   * #getFirstKeyByValue(Predicate)} {@see #getAllKeys(Predicate)}.
   *
   * @param predicate tests against key
   * @return value of first element from map which matched the {@code predicate} otherwise return
   * null.
   */
  default V getFirstValueByKeyOrNull(Predicate<K> predicate) {
    return getFirstValueByKeyOrElse(predicate, null);
  }

  /**
   * Sorts the CMap according to the order induced by the specified comparator. Key and Value of all
   * elements in the map must be <i>mutually comparable</i> using the specified comparator (that is,
   * {@code c.compare(e1, e2)} must not throw {@code ClassCastException} for any elements {@code e1}
   * and {@code e2} in the list).
   *
   * <p>This sort is guaranteed to be <i>stable</i>: equal elements will not be reordered as result
   * of the sort.
   *
   * <p>The specified list must be modifiable, but need not be resizable.
   *
   * @param comparator the comparator to determine the order of the list. A {@code null} value
   *                   indicates that the elements' <i>natural ordering</i> should be used.
   * @return {@link CLinkedMap} sorted based on defined comparator
   * @throws ClassCastException            if the list contains elements that are not <i>mutually
   *                                       comparable</i> using the specified comparator.
   * @throws UnsupportedOperationException if the specified list's list-iterator does not support
   *                                       the {@code set} operation.
   * @throws IllegalArgumentException      (optional) if the comparator is found to violate the {@link
   *                                       Comparator} contract
   * @see CList#sort(Comparator)
   */
  default CLinkedMap<K, V> getSortedMap(Comparator<java.util.Map.Entry<K, V>> comparator) {
    List<java.util.Map.Entry<K, V>> list = new LinkedList<>();
    asSet().forEach(v -> list.add(v));
    Collections.sort(list, comparator);
    CLinkedMap<K, V> output = new CLinkedMap<>();
    for (java.util.Map.Entry<K, V> entry : list) {
      output.put(entry.getKey(), entry.getValue());
    }
    return output;
  }
}
