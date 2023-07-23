package org.catools.common.extensions.states.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * CMapState is an interface for Map state related methods.
 *
 * <p>We need this interface to have possibility of adding state to any exists objects with the
 * minimum change in the code.
 */
public interface CMapState<K, V> extends CObjectState<Map<K, V>> {

  /**
   * Check if actual map contains the expected key and value.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @return execution boolean result
   */
  default boolean contains(K expectedKey, V expectedValue) {
    return contains(Map.entry(expectedKey, expectedValue));
  }

  /**
   * Check if actual map contains the expected key and value.
   *
   * @param expected value to compare
   * @return execution boolean result
   */
  default boolean contains(Map.Entry<K, V> expected) {
    if (expected == null) {
      return false;
    }
    Map<K, V> a = _get();
    return a.containsKey(expected.getKey())
        && Objects.equals(a.get(expected.getKey()), expected.getValue());
  }

  /**
   * Check if actual map contains all entries from the expected map. Please note that actual map
   * might have more entries.
   *
   * @param expected value to compare
   * @return execution boolean result
   */
  default boolean containsAll(Map<K, V> expected) {
    return containsAll(expected, null);
  }

  /**
   * Check if actual map contains all entries from the expected map. Please note that actual map
   * might have more entries.
   *
   * @param expected   value to compare
   * @param onNotMatch consumer to call if miss match found
   * @return execution boolean result
   */
  default boolean containsAll(Map<K, V> expected, Consumer<Map.Entry<K, V>> onNotMatch) {
    if (expected == null) {
      return false;
    }
    boolean result = true;
    Map<K, V> a = _get();

    for (Map.Entry<K, V> t : expected.entrySet()) {
      if (!a.containsKey(t.getKey()) || !Objects.equals(a.get(t.getKey()), t.getValue())) {
        if (onNotMatch == null) {
          return false;
        }
        result = false;
        onNotMatch.accept(t);
      }
    }
    return result;
  }

  /**
   * Check if actual map contains none of entries from the expected map.
   *
   * @param expected value to compare
   * @return execution boolean result
   */
  default boolean containsNone(Map<K, V> expected) {
    return containsNone(expected, null);
  }

  /**
   * Check if actual map contains none of entries from the expected map.
   *
   * @param expected value to compare
   * @param onMatch  consumer to call if match found
   * @return execution boolean result
   */
  default boolean containsNone(Map<K, V> expected, Consumer<Map.Entry<K, V>> onMatch) {
    if (expected == null) {
      return false;
    }
    boolean result = !expected.isEmpty();
    Map<K, V> a = _get();

    for (Map.Entry<K, V> t : expected.entrySet()) {
      if (a.containsKey(t.getKey()) && Objects.equals(a.get(t.getKey()), t.getValue())) {
        if (onMatch == null) {
          return false;
        }
        result = false;
        onMatch.accept(t);
      }
    }
    return result;
  }

  /**
   * Check if actual map either is empty or contains the expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @return execution boolean result
   */
  default boolean emptyOrContains(K expectedKey, V expectedValue) {
    return emptyOrContains(Map.entry(expectedKey, expectedValue));
  }

  /**
   * Check if actual map either is empty or contains the expected entry.
   *
   * @param expected value to compare
   * @return execution boolean result
   */
  default boolean emptyOrContains(Map.Entry<K, V> expected) {
    if (expected == null) {
      return false;
    }

    Map<K, V> a = _get();
    if (a == null) {
      return true;
    }

    return a.isEmpty()
        || (a.containsKey(expected.getKey())
        && Objects.equals(a.get(expected.getKey()), expected.getValue()));
  }

  /**
   * Check if actual map either is empty or does not contains the expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @return execution boolean result
   */
  default boolean emptyOrNotContains(K expectedKey, V expectedValue) {
    return emptyOrNotContains(Map.entry(expectedKey, expectedValue));
  }

  /**
   * Check if actual map either is empty or does not contains the expected entry.
   *
   * @param expected value to compare
   * @return execution boolean result
   */
  default boolean emptyOrNotContains(Map.Entry<K, V> expected) {
    if (expected == null) {
      return false;
    }

    Map<K, V> a = _get();
    if (a == null) {
      return true;
    }

    return a.isEmpty()
        || !(a.containsKey(expected.getKey())
        && Objects.equals(a.get(expected.getKey()), expected.getValue()));
  }

  default boolean isEqual(final Map<K, V> expected) {
    return Objects.equals(_get(), expected);
  }

  /**
   * Check if actual and expected maps have the exact same entries. (Ignore entry order) First we
   * compare that actual map contains all expected map entries and then we verify that expected has
   * all entries from actual.
   *
   * @param expected              value to compare
   * @param onActualNotContains   consumer to call if match found
   * @param onExpectedNotContains consumer to call if match found
   * @return execution boolean result
   */
  default boolean isEqual(
      Map<K, V> expected,
      Consumer<Map.Entry<K, V>> onActualNotContains,
      Consumer<Map.Entry<K, V>> onExpectedNotContains) {
    if (expected == null) {
      return false;
    }

    Map<K, V> actual = _get();
    boolean result = true;

    for (Map.Entry<K, V> t : expected.entrySet()) {
      if (!actual.containsKey(t.getKey())
          || !Objects.equals(actual.get(t.getKey()), t.getValue())) {
        if (onActualNotContains == null) {
          return false;
        }
        result = false;
        onActualNotContains.accept(t);
      }
    }

    for (Map.Entry<K, V> t : actual.entrySet()) {
      if (!expected.containsKey(t.getKey())
          || !Objects.equals(expected.get(t.getKey()), t.getValue())) {
        if (onExpectedNotContains == null) {
          return false;
        }
        result = false;
        onExpectedNotContains.accept(t);
      }
    }
    return expected.size() == actual.size() && result;
  }

  /**
   * Check if that actual map is not empty. (might contains null values)
   *
   * @return execution boolean result
   */
  @JsonIgnore
  default boolean isNotEmpty() {
    return _get() != null && !_get().isEmpty();
  }

  /**
   * Check if actual map does not contain the expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @return execution boolean result
   */
  default boolean notContains(K expectedKey, V expectedValue) {
    return notContains(Map.entry(expectedKey, expectedValue));
  }

  /**
   * Check if actual map does not contain the expected entry.
   *
   * @param expected value to compare
   * @return execution boolean result
   */
  default boolean notContains(Map.Entry<K, V> expected) {
    if (expected == null) {
      return false;
    }

    Map<K, V> a = _get();
    return !(a.containsKey(expected.getKey())
        && Objects.equals(a.get(expected.getKey()), expected.getValue()));
  }

  /**
   * Check if actual map might contains some but not all entries from the expected map. Please note
   * that actual map might have some entries but the point is to ensure that not all expected
   * entries are exist in it. We do verify that both key and value match in this comparision
   *
   * @param expected value to compare
   * @return execution boolean result
   */
  default boolean notContainsAll(Map<K, V> expected) {
    if (expected == null || expected.isEmpty()) {
      return false;
    }

    boolean result = false;
    Map<K, V> a = _get();

    for (Map.Entry<K, V> t : expected.entrySet()) {
      if (!a.containsKey(t.getKey()) || !Objects.equals(a.get(t.getKey()), t.getValue())) {
        return true;
      }
    }
    return result;
  }

  /**
   * Check if the map size is equal to expected value.
   *
   * @param expected value to compare
   * @return execution boolean result
   */
  default boolean sizeEquals(int expected) {
    return _get() == null ? 0 == expected : _get().size() == expected;
  }

  /**
   * Check if that actual has value greater than expected.
   *
   * @param expected value to compare
   * @return execution boolean result
   */
  default boolean sizeIsGreaterThan(int expected) {
    return _get() == null ? 0 > expected : _get().size() > expected;
  }

  /**
   * Check if that actual has value greater than or equal to expected.
   *
   * @param expected value to compare
   * @return execution boolean result
   */
  default boolean sizeIsGreaterThanOrEqual(int expected) {
    return _get() == null ? 0 >= expected : _get().size() >= expected;
  }

  /**
   * Check if that actual has value less than expected.
   *
   * @param expected value to compare
   * @return execution boolean result
   */
  default boolean sizeIsLessThan(int expected) {
    return _get() == null ? 0 < expected : _get().size() < expected;
  }

  /**
   * Check if that actual has value less than or equal to expected.
   *
   * @param expected value to compare
   * @return execution boolean result
   */
  default boolean sizeIsLessThanOrEqual(int expected) {
    return _get() == null ? 0 <= expected : _get().size() <= expected;
  }
}
