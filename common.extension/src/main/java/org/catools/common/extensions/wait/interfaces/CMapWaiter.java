package org.catools.common.extensions.wait.interfaces;

import org.catools.common.collections.interfaces.CMap;
import org.catools.common.extensions.states.interfaces.CMapState;

import java.util.Map;

/**
 * CMapWaiter is an interface for CMap waiter related methods.
 */
public interface CMapWaiter<K, V> extends CObjectWaiter<Map<K, V>> {

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual map contains the expected key and value.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitContains(K expectedKey, V expectedValue) {
    return waitContains(
        Map.entry(expectedKey, expectedValue),
        getDefaultWaitInSeconds(),
        getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual map contains the expected key and value.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param waitInSeconds maximum wait time
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitContains(K expectedKey, V expectedValue, final int waitInSeconds) {
    return waitContains(
        Map.entry(expectedKey, expectedValue),
        waitInSeconds,
        getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual map contains the expected key and value.
   *
   * @param expectedKey            key to compare
   * @param expectedValue          value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitContains(
      K expectedKey, V expectedValue, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitContains(
        Map.entry(expectedKey, expectedValue), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual map contains the expected key and value.
   *
   * @param expected value to compare
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitContains(Map.Entry<K, V> expected) {
    return waitContains(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual map contains the expected key and value.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitContains(Map.Entry<K, V> expected, final int waitInSeconds) {
    return waitContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual map contains the expected key and value.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitContains(
      Map.Entry<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).contains(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual map contains all entries from the expected map. Please note that actual map might
   * have more entries.
   *
   * @param expected value to compare
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitContainsAll(Map<K, V> expected) {
    return waitContainsAll(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual map contains all entries from the expected
   * map. Please note that actual map might have more entries.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitContainsAll(Map<K, V> expected, final int waitInSeconds) {
    return waitContainsAll(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual map contains all entries from the expected
   * map. Please note that actual map might have more entries.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitContainsAll(
      Map<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).containsAll(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual map contains none of entries from the expected map.
   *
   * @param expected value to compare
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitContainsNone(Map<K, V> expected) {
    return waitContainsNone(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual map contains none of entries from the
   * expected map.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitContainsNone(Map<K, V> expected, final int waitInSeconds) {
    return waitContainsNone(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual map contains none of entries from the
   * expected map.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitContainsNone(
      Map<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).containsNone(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual map either is empty or contains the expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitEmptyOrContains(K expectedKey, V expectedValue) {
    return waitEmptyOrContains(
        Map.entry(expectedKey, expectedValue),
        getDefaultWaitInSeconds(),
        getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual map either is empty or contains the expected
   * entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param waitInSeconds maximum wait time
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitEmptyOrContains(K expectedKey, V expectedValue, final int waitInSeconds) {
    return waitEmptyOrContains(
        Map.entry(expectedKey, expectedValue),
        waitInSeconds,
        getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual map either is empty or contains the expected
   * entry.
   *
   * @param expectedKey            key to compare
   * @param expectedValue          value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitEmptyOrContains(
      K expectedKey, V expectedValue, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitEmptyOrContains(
        Map.entry(expectedKey, expectedValue), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual map either is empty or contains the expected entry.
   *
   * @param expected value to compare
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitEmptyOrContains(Map.Entry<K, V> expected) {
    return waitEmptyOrContains(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual map either is empty or contains the expected
   * entry.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitEmptyOrContains(Map.Entry<K, V> expected, final int waitInSeconds) {
    return waitEmptyOrContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual map either is empty or contains the expected
   * entry.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitEmptyOrContains(
      Map.Entry<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        o -> toState(o).emptyOrContains(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual map either is empty or does not contains the expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitEmptyOrNotContains(K expectedKey, V expectedValue) {
    return waitEmptyOrNotContains(
        Map.entry(expectedKey, expectedValue),
        getDefaultWaitInSeconds(),
        getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual map either is empty or does not contains the
   * expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param waitInSeconds maximum wait time
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitEmptyOrNotContains(K expectedKey, V expectedValue, final int waitInSeconds) {
    return waitEmptyOrNotContains(
        Map.entry(expectedKey, expectedValue),
        waitInSeconds,
        getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual map either is empty or does not contains the
   * expected entry.
   *
   * @param expectedKey            key to compare
   * @param expectedValue          value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitEmptyOrNotContains(
      K expectedKey, V expectedValue, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitEmptyOrNotContains(
        Map.entry(expectedKey, expectedValue), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual map either is empty or does not contains the expected entry.
   *
   * @param expected value to compare
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitEmptyOrNotContains(Map.Entry<K, V> expected) {
    return waitEmptyOrNotContains(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual map either is empty or does not contains the
   * expected entry.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitEmptyOrNotContains(Map.Entry<K, V> expected, final int waitInSeconds) {
    return waitEmptyOrNotContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual map either is empty or does not contains the
   * expected entry.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitEmptyOrNotContains(
      Map.Entry<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        o -> toState(o).emptyOrNotContains(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual and expected maps have the exact same entries. (Ignore entry order) First we compare
   * that actual map contains all expected map entries and then we Wait for {@code
   * CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till the expected has
   * all entries from actual.
   *
   * @param expected map to compare
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitEquals(Map<K, V> expected) {
    return waitEquals(expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual and expected maps have the exact same
   * entries. (Ignore entry order) First we compare that actual map contains all expected map
   * entries and then we Wait for defined number of seconds till the expected has all entries from
   * actual.
   *
   * @param expected      map to compare
   * @param waitInSeconds maximum wait time
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitEquals(Map<K, V> expected, final int waitInSeconds) {
    return waitEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual and expected maps have the exact same
   * entries. (Ignore entry order) First we compare that actual map contains all expected map
   * entries and then we Wait for defined number of seconds till the expected has all entries from
   * actual.
   *
   * @param expected               map to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitEquals(
      Map<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        o -> toState(o).isEqual(expected, null, null), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual map is empty.
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmpty() {
    return waitIsEmpty(getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual map is empty.
   *
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmpty(final int waitInSeconds) {
    return waitIsEmpty(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual map is empty.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmpty(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> o == null || o.isEmpty(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual map is not empty. (might contains null values)
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotEmpty() {
    return waitIsNotEmpty(getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual map is not empty. (might contains null
   * values)
   *
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotEmpty(final int waitInSeconds) {
    return waitIsNotEmpty(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual map is not empty. (might contains null
   * values)
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotEmpty(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).isNotEmpty(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual map does not contain the expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitNotContains(K expectedKey, V expectedValue) {
    return waitNotContains(
        Map.entry(expectedKey, expectedValue),
        getDefaultWaitInSeconds(),
        getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual map does not contain the expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param waitInSeconds maximum wait time
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitNotContains(K expectedKey, V expectedValue, final int waitInSeconds) {
    return waitNotContains(
        Map.entry(expectedKey, expectedValue),
        waitInSeconds,
        getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual map does not contain the expected entry.
   *
   * @param expectedKey            key to compare
   * @param expectedValue          value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitNotContains(
      K expectedKey, V expectedValue, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitNotContains(
        Map.entry(expectedKey, expectedValue), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual map does not contain the expected entry.
   *
   * @param expected value to compare
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitNotContains(Map.Entry<K, V> expected) {
    return waitNotContains(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual map does not contain the expected entry.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitNotContains(Map.Entry<K, V> expected, final int waitInSeconds) {
    return waitNotContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual map does not contain the expected entry.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitNotContains(
      Map.Entry<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).notContains(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual map might contains some but not all entries from the expected map. Please note that
   * actual map might have some entries but the point is to ensure that not all expected entries
   * are exist in it. We do Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number
   * of milliseconds till the both key and value match in this comparision
   *
   * @param expected value to compare
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitNotContainsAll(Map<K, V> expected) {
    return waitNotContainsAll(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual map might contains some but not all entries
   * from the expected map. Please note that actual map might have some entries but the point is
   * to ensure that not all expected entries are exist in it. We do Wait for defined number of
   * seconds till the both key and value match in this comparision
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitNotContainsAll(Map<K, V> expected, final int waitInSeconds) {
    return waitNotContainsAll(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual map might contains some but not all entries
   * from the expected map. Please note that actual map might have some entries but the point is
   * to ensure that not all expected entries are exist in it. We do Wait for defined number of
   * seconds till the both key and value match in this comparision
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return caller {@link CMapWaiter} so we can do chain calls
   */
  default boolean waitNotContainsAll(
      Map<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).notContainsAll(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the map size is equal to expected value.
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSizeEquals(int expected) {
    return waitSizeEquals(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the map size is equal to expected value.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSizeEquals(int expected, final int waitInSeconds) {
    return waitSizeEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the map size is equal to expected value.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSizeEquals(
      int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).sizeEquals(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual has value greater than expected.
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSizeIsGreaterThan(int expected) {
    return waitSizeIsGreaterThan(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual has value greater than expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSizeIsGreaterThan(int expected, final int waitInSeconds) {
    return waitSizeIsGreaterThan(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual has value greater than expected.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSizeIsGreaterThan(
      int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        o -> toState(o).sizeIsGreaterThan(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual has value less than expected.
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSizeIsLessThan(int expected) {
    return waitSizeIsLessThan(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual has value less than expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSizeIsLessThan(int expected, final int waitInSeconds) {
    return waitSizeIsLessThan(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual has value less than expected.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSizeIsLessThan(
      int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).sizeIsLessThan(expected), waitInSeconds, intervalInMilliSeconds);
  }

  private CMapState<K, V> toState(Object e) {
    return () -> (CMap<K, V>) e;
  }
}
