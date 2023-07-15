package org.catools.common.extensions.verify.interfaces.waitVerify;

import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CLinkedMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.collections.interfaces.CCollection;
import org.catools.common.collections.interfaces.CMap;
import org.catools.common.extensions.states.interfaces.CMapState;
import org.catools.common.extensions.verify.hard.CMapVerification;
import org.catools.common.extensions.verify.interfaces.base.CMapVerify;

import java.util.Map;
import java.util.Objects;

/**
 * CMapVerifier is an interface for Map verification related methods.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 *
 * <p>Please Note that we should extend manually {@link
 * CMapVerification} for each new added verification here
 *
 * @see Map
 * @see CCollection
 * @see CHashMap
 * @see CLinkedMap
 * @see CSet
 * @see CList
 */
public interface CMapWaitVerify<K, V> extends CMapVerify<K, V>, CObjectWaitVerify<Map<K, V>, CMapState<K, V>> {

  /**
   * Verify that actual map contains the expected key and value.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyContains(K expectedKey, V expectedValue, final int waitInSeconds) {
    verifyContains(Map.entry(expectedKey, expectedValue), waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual map contains the expected key and value.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyContains(K expectedKey, V expectedValue, final int waitInSeconds, final String message, final Object... params) {
    verifyContains(Map.entry(expectedKey, expectedValue), waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual map contains the expected key and value.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyContains(Map.Entry<K, V> expected, final int waitInSeconds) {
    verifyContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual map contains the expected key and value.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyContains(Map.Entry<K, V> expected, final int waitInSeconds, final String message, final Object... params) {
    verifyContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual map contains the expected key and value.
   *
   * @param expectedKey            key to compare
   * @param expectedValue          value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContains(K expectedKey, V expectedValue, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContains(Map.entry(expectedKey, expectedValue), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify that actual map contains the expected key and value.
   *
   * @param expectedKey            key to compare
   * @param expectedValue          value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifyContains(K expectedKey, V expectedValue, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContains(Map.entry(expectedKey, expectedValue), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual map contains the expected key and value.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContains(Map.Entry<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContains(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Contains The Expected Value"));
  }

  /**
   * Verify that actual map contains the expected key and value.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifyContains(Map.Entry<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, e) -> _toState(a).contains(e), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual map contains all entries from the expected map. Please note that actual map
   * might have more entries.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyContainsAll(Map<K, V> expected, final int waitInSeconds) {
    verifyContainsAll(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual map contains all entries from the expected map. Please note that actual map
   * might have more entries.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyContainsAll(Map<K, V> expected, final int waitInSeconds, final String message, final Object... params) {
    verifyContainsAll(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual map contains all entries from the expected map. Please note that actual map
   * might have more entries.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContainsAll(Map<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContainsAll(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Contains All The Expected Map"));
  }

  /**
   * Verify that actual map contains all entries from the expected map. Please note that actual map
   * might have more entries.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifyContainsAll(Map<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, e) -> {
      if (expected == null) {
        return false;
      }
      CMap<K, V> diff = new CHashMap<>();
      boolean result = _toState(a).containsAll(e, entry -> diff.put(entry.getKey(), entry.getValue()));
      if (!diff.isEmpty()) {
        logger.trace("Actual list does not contain following records:\n" + diff);
      }

      return result;
    }, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual map contains none of entries from the expected map.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyContainsNone(Map<K, V> expected, final int waitInSeconds) {
    verifyContainsNone(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual map contains none of entries from the expected map.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyContainsNone(Map<K, V> expected, final int waitInSeconds, final String message, final Object... params) {
    verifyContainsNone(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual map contains none of entries from the expected map.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContainsNone(Map<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContainsNone(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Not Contains Any Record From The Expected Map"));
  }

  /**
   * Verify that actual map contains none of entries from the expected map.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifyContainsNone(Map<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, e) -> {
      if (expected == null) {
        return false;
      }
      CMap<K, V> diff = new CHashMap<>();
      boolean result = _toState(a).containsNone(e, entry -> diff.put(entry.getKey(), entry.getValue()));
      if (!diff.isEmpty()) {
        logger.trace("Actual list contains following records:\n" + diff);
      }

      return result;
    }, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual map either is empty or contains the expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyEmptyOrContains(K expectedKey, V expectedValue, final int waitInSeconds) {
    verifyEmptyOrContains(Map.entry(expectedKey, expectedValue), waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual map either is empty or contains the expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyEmptyOrContains(K expectedKey, V expectedValue, final int waitInSeconds, final String message, final Object... params) {
    verifyEmptyOrContains(Map.entry(expectedKey, expectedValue), waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual map either is empty or contains the expected entry.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyEmptyOrContains(Map.Entry<K, V> expected, final int waitInSeconds) {
    Objects.requireNonNull(expected.getKey());
    verifyEmptyOrContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual map either is empty or contains the expected entry.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyEmptyOrContains(Map.Entry<K, V> expected, final int waitInSeconds, final String message, final Object... params) {
    Objects.requireNonNull(expected.getKey());
    verifyEmptyOrContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual map either is empty or contains the expected entry.
   *
   * @param expectedKey            key to compare
   * @param expectedValue          value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEmptyOrContains(K expectedKey, V expectedValue, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEmptyOrContains(Map.entry(expectedKey, expectedValue), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify that actual map either is empty or contains the expected entry.
   *
   * @param expectedKey            key to compare
   * @param expectedValue          value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifyEmptyOrContains(K expectedKey, V expectedValue, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyEmptyOrContains(Map.entry(expectedKey, expectedValue), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual map either is empty or contains the expected entry.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEmptyOrContains(Map.Entry<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEmptyOrContains(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Empty Or Contains The Expected Value"));
  }

  /**
   * Verify that actual map either is empty or contains the expected entry.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifyEmptyOrContains(Map.Entry<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    Objects.requireNonNull(expected.getKey());
    _verify(expected, (a, e) -> _toState(a).emptyOrContains(e), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual map either is empty or does not contains the expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyEmptyOrNotContains(K expectedKey, V expectedValue, final int waitInSeconds) {
    verifyEmptyOrNotContains(Map.entry(expectedKey, expectedValue), waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual map either is empty or does not contains the expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyEmptyOrNotContains(K expectedKey, V expectedValue, final int waitInSeconds, final String message, final Object... params) {
    verifyEmptyOrNotContains(Map.entry(expectedKey, expectedValue), waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual map either is empty or does not contains the expected entry.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyEmptyOrNotContains(Map.Entry<K, V> expected, final int waitInSeconds) {
    Objects.requireNonNull(expected.getKey());
    verifyEmptyOrNotContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual map either is empty or does not contains the expected entry.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyEmptyOrNotContains(Map.Entry<K, V> expected, final int waitInSeconds, final String message, final Object... params) {
    Objects.requireNonNull(expected.getKey());
    verifyEmptyOrNotContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual map either is empty or does not contains the expected entry.
   *
   * @param expectedKey            key to compare
   * @param expectedValue          value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEmptyOrNotContains(K expectedKey, V expectedValue, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEmptyOrNotContains(Map.entry(expectedKey, expectedValue), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify that actual map either is empty or does not contains the expected entry.
   *
   * @param expectedKey            key to compare
   * @param expectedValue          value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifyEmptyOrNotContains(K expectedKey, V expectedValue, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyEmptyOrNotContains(Map.entry(expectedKey, expectedValue), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual map either is empty or does not contains the expected entry.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEmptyOrNotContains(Map.Entry<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEmptyOrNotContains(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Empty Or Not Contains The Expected Value"));
  }

  /**
   * Verify that actual map either is empty or does not contains the expected entry.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifyEmptyOrNotContains(Map.Entry<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    Objects.requireNonNull(expected.getKey());
    _verify(expected, (a, e) -> _toState(a).emptyOrNotContains(expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual and expected maps have the exact same entries. (Ignore entry order) First we
   * compare that actual map contains all expected map entries and then we verify that expected has
   * all entries from actual.
   *
   * @param expected      map to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyEquals(Map<K, V> expected, final int waitInSeconds) {
    verifyEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual and expected maps have the exact same entries. (Ignore entry order) First we
   * compare that actual map contains all expected map entries and then we verify that expected has
   * all entries from actual.
   *
   * @param expected      map to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyEquals(Map<K, V> expected, final int waitInSeconds, final String message, final Object... params) {
    verifyEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual and expected maps have the exact same entries. (Ignore entry order) First we
   * compare that actual map contains all expected map entries and then we verify that expected has
   * all entries from actual.
   *
   * @param expected               map to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEquals(Map<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEquals(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Equals Expected Value"));
  }

  /**
   * Verify that actual and expected maps have the exact same entries. (Ignore entry order) First we
   * compare that actual map contains all expected map entries and then we verify that expected has
   * all entries from actual.
   *
   * @param expected               map to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifyEquals(Map<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (o, o2) -> {
      CMap<K, V> diffActual = new CHashMap<>();
      CMap<K, V> diffExpected = new CHashMap<>();
      boolean equals = _toState(o).isEqual(o2, entry -> diffActual.put(entry.getKey(), entry.getValue()), entry -> diffExpected.put(entry.getKey(), entry.getValue()));
      if (!diffExpected.isEmpty()) {
        logger.trace("Actual list does not contain following records:\n" + diffExpected);
      }

      if (!diffActual.isEmpty()) {
        logger.trace("Expected list does not contain following records:\n" + diffActual);
      }
      return equals;
    }, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual map is empty.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsEmpty(final int waitInSeconds) {
    verifyIsEmpty(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual map is empty.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyIsEmpty(final int waitInSeconds, final String message, final Object... params) {
    verifyIsEmpty(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual map is empty.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsEmpty(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsEmpty(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Empty"));
  }

  /**
   * Verify that actual map is empty.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifyIsEmpty(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (o, o2) -> _get() == null || _get().isEmpty(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual map is not empty. (might contains null values)
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotEmpty(final int waitInSeconds) {
    verifyIsNotEmpty(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual map is not empty. (might contains null values)
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyIsNotEmpty(final int waitInSeconds, final String message, final Object... params) {
    verifyIsNotEmpty(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual map is not empty. (might contains null values)
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotEmpty(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNotEmpty(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Not Empty"));
  }

  /**
   * Verify that actual map is not empty. (might contains null values)
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifyIsNotEmpty(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (o, o2) -> _toState(o).isNotEmpty(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual map does not contain the expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotContains(K expectedKey, V expectedValue, final int waitInSeconds) {
    verifyNotContains(Map.entry(expectedKey, expectedValue), waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual map does not contain the expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyNotContains(K expectedKey, V expectedValue, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContains(Map.entry(expectedKey, expectedValue), waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual map does not contain the expected entry.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotContains(Map.Entry<K, V> expected, final int waitInSeconds) {
    verifyNotContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual map does not contain the expected entry.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyNotContains(Map.Entry<K, V> expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual map does not contain the expected entry.
   *
   * @param expectedKey            key to compare
   * @param expectedValue          value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContains(K expectedKey, V expectedValue, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContains(Map.entry(expectedKey, expectedValue), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify that actual map does not contain the expected entry.
   *
   * @param expectedKey            key to compare
   * @param expectedValue          value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifyNotContains(K expectedKey, V expectedValue, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyNotContains(Map.entry(expectedKey, expectedValue), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual map does not contain the expected entry.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContains(Map.Entry<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContains(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Not Contains Expected Value"));
  }

  /**
   * Verify that actual map does not contain the expected entry.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifyNotContains(Map.Entry<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, e) -> _toState(a).notContains(e), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual map might contains some but not all entries from the expected map. Please
   * note that actual map might have some entries but the point is to ensure that not all
   * expected entries are exist in it. We do verify that both key and value match in this
   * comparision
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotContainsAll(Map<K, V> expected, final int waitInSeconds) {
    verifyNotContainsAll(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual map might contains some but not all entries from the expected map. Please
   * note that actual map might have some entries but the point is to ensure that not all
   * expected entries are exist in it. We do verify that both key and value match in this
   * comparision
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyNotContainsAll(Map<K, V> expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContainsAll(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual map might contains some but not all entries from the expected map. Please
   * note that actual map might have some entries but the point is to ensure that not all
   * expected entries are exist in it. We do verify that both key and value match in this
   * comparision
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContainsAll(Map<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContainsAll(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Not Contains All Expected Values"));
  }

  /**
   * Verify that actual map might contains some but not all entries from the expected map. Please
   * note that actual map might have some entries but the point is to ensure that not all
   * expected entries are exist in it. We do verify that both key and value match in this
   * comparision
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifyNotContainsAll(Map<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (o, o2) -> _toState(o).notContainsAll(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify the map size is equal to expected value.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifySizeEquals(int expected, final int waitInSeconds) {
    verifySizeEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify the map size is equal to expected value.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifySizeEquals(int expected, final int waitInSeconds, final String message, final Object... params) {
    verifySizeEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify the map size is equal to expected value.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySizeEquals(int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySizeEquals(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Size Is Equal To Expected Value"));
  }

  /**
   * Verify the map size is equal to expected value.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifySizeEquals(int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (o, o2) -> _toState(o).sizeEquals(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifySizeIsGreaterThan(int expected, final int waitInSeconds) {
    verifySizeIsGreaterThan(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifySizeIsGreaterThan(int expected, final int waitInSeconds, final String message, final Object... params) {
    verifySizeIsGreaterThan(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySizeIsGreaterThan(int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySizeIsGreaterThan(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Size Is Greater Than Expected Value"));
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifySizeIsGreaterThan(int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (o, o2) -> _toState(o).sizeIsGreaterThan(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual has value greater than or equals to expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifySizeIsGreaterThanOrEqual(int expected, final int waitInSeconds) {
    verifySizeIsGreaterThanOrEqual(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual has value greater than or equals to expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifySizeIsGreaterThanOrEqual(int expected, final int waitInSeconds, final String message, final Object... params) {
    verifySizeIsGreaterThanOrEqual(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual has value greater than or equals to expected.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySizeIsGreaterThanOrEqual(int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySizeIsGreaterThanOrEqual(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Size Is Greater Than Expected Value"));
  }

  /**
   * Verify that actual has value greater than or equals to expected.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifySizeIsGreaterThanOrEqual(int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (o, o2) -> _toState(o).sizeIsGreaterThanOrEqual(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifySizeIsLessThan(int expected, final int waitInSeconds) {
    verifySizeIsLessThan(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifySizeIsLessThan(int expected, final int waitInSeconds, final String message, final Object... params) {
    verifySizeIsLessThan(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySizeIsLessThan(int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySizeIsLessThan(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Size Is Less Than Expected Value"));
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifySizeIsLessThan(int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (o, o2) -> _toState(o).sizeIsLessThan(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual has value less than or equals to expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifySizeIsLessThanOrEqual(int expected, final int waitInSeconds) {
    verifySizeIsLessThanOrEqual(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual has value less than or equals to expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifySizeIsLessThanOrEqual(int expected, final int waitInSeconds, final String message, final Object... params) {
    verifySizeIsLessThanOrEqual(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual has value less than or equals to expected.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySizeIsLessThanOrEqual(int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySizeIsLessThanOrEqual(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Size is less than or equal to the expected value"));
  }

  /**
   * Verify that actual has value less than or equals to expected.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifySizeIsLessThanOrEqual(int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (o, o2) -> _toState(o).sizeIsLessThanOrEqual(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }
}
