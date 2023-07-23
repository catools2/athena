package org.catools.common.extensions.verify.interfaces.waitVerifier;

import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CLinkedMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.collections.interfaces.CCollection;
import org.catools.common.collections.interfaces.CMap;
import org.catools.common.extensions.states.interfaces.CMapState;
import org.catools.common.extensions.verify.CVerificationQueue;
import org.catools.common.extensions.verify.interfaces.verifier.CMapVerifier;

import java.util.Map;
import java.util.Objects;

/**
 * CMapVerifier is an interface for Map verification related methods.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extend cross all other objects:
 *
 * <p>Please Note that we should extend manually other verify and verifier classes for each new added verification here</p>
 *
 * @see Map
 * @see CCollection
 * @see CHashMap
 * @see CLinkedMap
 * @see CSet
 * @see CList
 */
public interface CMapWaitVerifier<K, V> extends CMapVerifier<K, V>, CObjectWaitVerifier<Map<K, V>, CMapState<K, V>> {

  /**
   * Verify that actual map contains the expected key and value.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyContains(CVerificationQueue verifier, K expectedKey, V expectedValue, final int waitInSeconds) {
    verifyContains(verifier, Map.entry(expectedKey, expectedValue), waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
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
  default void verifyContains(CVerificationQueue verifier, K expectedKey, V expectedValue, final int waitInSeconds, final String message, final Object... params) {
    verifyContains(verifier, Map.entry(expectedKey, expectedValue), waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual map contains the expected key and value.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyContains(final CVerificationQueue verifier, Map.Entry<K, V> expected, final int waitInSeconds) {
    verifyContains(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual map contains the expected key and value.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyContains(CVerificationQueue verifier, Map.Entry<K, V> expected, final int waitInSeconds, final String message, final Object... params) {
    verifyContains(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual map contains the expected key and value.
   *
   * @param expectedKey            key to compare
   * @param expectedValue          value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContains(CVerificationQueue verifier, K expectedKey, V expectedValue, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContains(verifier, Map.entry(expectedKey, expectedValue), waitInSeconds, intervalInMilliSeconds);
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
  default void verifyContains(CVerificationQueue verifier, K expectedKey, V expectedValue, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContains(verifier, Map.entry(expectedKey, expectedValue), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual map contains the expected key and value.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContains(CVerificationQueue verifier, Map.Entry<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContains(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Contains The Expected Value"));
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
  default void verifyContains(CVerificationQueue verifier, Map.Entry<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, e) -> _toState(a).contains(e), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual map contains all entries from the expected map. Please note that actual map
   * might have more entries.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyContainsAll(final CVerificationQueue verifier, Map<K, V> expected, final int waitInSeconds) {
    verifyContainsAll(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual map contains all entries from the expected map. Please note that actual map
   * might have more entries.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyContainsAll(CVerificationQueue verifier, Map<K, V> expected, final int waitInSeconds, final String message, final Object... params) {
    verifyContainsAll(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual map contains all entries from the expected map. Please note that actual map
   * might have more entries.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContainsAll(CVerificationQueue verifier, Map<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContainsAll(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Contains All The Expected Map"));
  }

  /**
   * Verify that actual map contains all entries from the expected map. Please note that actual map
   * might have more entries.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifyContainsAll(CVerificationQueue verifier, Map<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, e) -> {
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
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyContainsNone(final CVerificationQueue verifier, Map<K, V> expected, final int waitInSeconds) {
    verifyContainsNone(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual map contains none of entries from the expected map.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyContainsNone(CVerificationQueue verifier, Map<K, V> expected, final int waitInSeconds, final String message, final Object... params) {
    verifyContainsNone(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual map contains none of entries from the expected map.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContainsNone(CVerificationQueue verifier, Map<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContainsNone(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Not Contains Any Record From The Expected Map"));
  }

  /**
   * Verify that actual map contains none of entries from the expected map.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifyContainsNone(CVerificationQueue verifier, Map<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, e) -> {
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
  default void verifyEmptyOrContains(CVerificationQueue verifier, K expectedKey, V expectedValue, final int waitInSeconds) {
    verifyEmptyOrContains(verifier, Map.entry(expectedKey, expectedValue), waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
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
  default void verifyEmptyOrContains(CVerificationQueue verifier, K expectedKey, V expectedValue, final int waitInSeconds, final String message, final Object... params) {
    verifyEmptyOrContains(verifier, Map.entry(expectedKey, expectedValue), waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual map either is empty or contains the expected entry.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyEmptyOrContains(CVerificationQueue verifier, Map.Entry<K, V> expected, final int waitInSeconds) {
    Objects.requireNonNull(expected.getKey());
    verifyEmptyOrContains(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual map either is empty or contains the expected entry.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyEmptyOrContains(CVerificationQueue verifier, Map.Entry<K, V> expected, final int waitInSeconds, final String message, final Object... params) {
    Objects.requireNonNull(expected.getKey());
    verifyEmptyOrContains(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual map either is empty or contains the expected entry.
   *
   * @param expectedKey            key to compare
   * @param expectedValue          value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEmptyOrContains(CVerificationQueue verifier, K expectedKey, V expectedValue, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEmptyOrContains(verifier, Map.entry(expectedKey, expectedValue), waitInSeconds, intervalInMilliSeconds);
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
  default void verifyEmptyOrContains(CVerificationQueue verifier, K expectedKey, V expectedValue, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyEmptyOrContains(verifier, Map.entry(expectedKey, expectedValue), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual map either is empty or contains the expected entry.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEmptyOrContains(CVerificationQueue verifier, Map.Entry<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEmptyOrContains(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Empty Or Contains The Expected Value"));
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
  default void verifyEmptyOrContains(CVerificationQueue verifier, Map.Entry<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    Objects.requireNonNull(expected.getKey());
    _verify(verifier, expected, (a, e) -> _toState(a).emptyOrContains(e), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual map either is empty or does not contains the expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyEmptyOrNotContains(CVerificationQueue verifier, K expectedKey, V expectedValue, final int waitInSeconds) {
    verifyEmptyOrNotContains(verifier, Map.entry(expectedKey, expectedValue), waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
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
  default void verifyEmptyOrNotContains(CVerificationQueue verifier, K expectedKey, V expectedValue, final int waitInSeconds, final String message, final Object... params) {
    verifyEmptyOrNotContains(verifier, Map.entry(expectedKey, expectedValue), waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual map either is empty or does not contains the expected entry.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyEmptyOrNotContains(CVerificationQueue verifier, Map.Entry<K, V> expected, final int waitInSeconds) {
    Objects.requireNonNull(expected.getKey());
    verifyEmptyOrNotContains(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual map either is empty or does not contains the expected entry.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyEmptyOrNotContains(CVerificationQueue verifier, Map.Entry<K, V> expected, final int waitInSeconds, final String message, final Object... params) {
    Objects.requireNonNull(expected.getKey());
    verifyEmptyOrNotContains(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual map either is empty or does not contains the expected entry.
   *
   * @param expectedKey            key to compare
   * @param expectedValue          value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEmptyOrNotContains(CVerificationQueue verifier, K expectedKey, V expectedValue, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEmptyOrNotContains(verifier, Map.entry(expectedKey, expectedValue), waitInSeconds, intervalInMilliSeconds);
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
  default void verifyEmptyOrNotContains(CVerificationQueue verifier, K expectedKey, V expectedValue, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyEmptyOrNotContains(verifier, Map.entry(expectedKey, expectedValue), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual map either is empty or does not contains the expected entry.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEmptyOrNotContains(CVerificationQueue verifier, Map.Entry<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEmptyOrNotContains(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Empty Or Not Contains The Expected Value"));
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
  default void verifyEmptyOrNotContains(CVerificationQueue verifier, Map.Entry<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    Objects.requireNonNull(expected.getKey());
    _verify(verifier, expected, (a, e) -> _toState(a).emptyOrNotContains(expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual and expected maps have the exact same entries. (Ignore entry order) First we
   * compare that actual map contains all expected map entries and then we verify that expected has
   * all entries from actual.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      map to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyEquals(CVerificationQueue verifier, Map<K, V> expected, final int waitInSeconds) {
    verifyEquals(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual and expected maps have the exact same entries. (Ignore entry order) First we
   * compare that actual map contains all expected map entries and then we verify that expected has
   * all entries from actual.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      map to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyEquals(CVerificationQueue verifier, Map<K, V> expected, final int waitInSeconds, final String message, final Object... params) {
    verifyEquals(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual and expected maps have the exact same entries. (Ignore entry order) First we
   * compare that actual map contains all expected map entries and then we verify that expected has
   * all entries from actual.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               map to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEquals(CVerificationQueue verifier, Map<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEquals(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Equals Expected Value"));
  }

  /**
   * Verify that actual and expected maps have the exact same entries. (Ignore entry order) First we
   * compare that actual map contains all expected map entries and then we verify that expected has
   * all entries from actual.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               map to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifyEquals(CVerificationQueue verifier, Map<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> {
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
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsEmpty(final CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsEmpty(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual map is empty.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyIsEmpty(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsEmpty(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual map is empty.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsEmpty(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsEmpty(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Empty"));
  }

  /**
   * Verify that actual map is empty.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifyIsEmpty(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (o, o2) -> _get() == null || _get().isEmpty(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual map is not empty. (might contains null values)
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotEmpty(final CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsNotEmpty(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual map is not empty. (might contains null values)
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyIsNotEmpty(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsNotEmpty(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual map is not empty. (might contains null values)
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotEmpty(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNotEmpty(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Not Empty"));
  }

  /**
   * Verify that actual map is not empty. (might contains null values)
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifyIsNotEmpty(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (o, o2) -> _toState(o).isNotEmpty(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual map does not contain the expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotContains(CVerificationQueue verifier, K expectedKey, V expectedValue, final int waitInSeconds) {
    verifyNotContains(verifier, Map.entry(expectedKey, expectedValue), waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
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
  default void verifyNotContains(CVerificationQueue verifier, K expectedKey, V expectedValue, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContains(verifier, Map.entry(expectedKey, expectedValue), waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual map does not contain the expected entry.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotContains(final CVerificationQueue verifier, Map.Entry<K, V> expected, final int waitInSeconds) {
    verifyNotContains(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual map does not contain the expected entry.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyNotContains(CVerificationQueue verifier, Map.Entry<K, V> expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContains(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual map does not contain the expected entry.
   *
   * @param expectedKey            key to compare
   * @param expectedValue          value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContains(CVerificationQueue verifier, K expectedKey, V expectedValue, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContains(verifier, Map.entry(expectedKey, expectedValue), waitInSeconds, intervalInMilliSeconds);
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
  default void verifyNotContains(CVerificationQueue verifier, K expectedKey, V expectedValue, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyNotContains(verifier, Map.entry(expectedKey, expectedValue), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual map does not contain the expected entry.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContains(CVerificationQueue verifier, Map.Entry<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContains(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Not Contains Expected Value"));
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
  default void verifyNotContains(CVerificationQueue verifier, Map.Entry<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, e) -> _toState(a).notContains(e), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual map might contains some but not all entries from the expected map. Please
   * note that actual map might have some of entries but the point is to ensure that not all
   * expected entries are exist in it. We do verify that both key and value match in this
   * comparision
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotContainsAll(final CVerificationQueue verifier, Map<K, V> expected, final int waitInSeconds) {
    verifyNotContainsAll(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual map might contains some but not all entries from the expected map. Please
   * note that actual map might have some of entries but the point is to ensure that not all
   * expected entries are exist in it. We do verify that both key and value match in this
   * comparision
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyNotContainsAll(CVerificationQueue verifier, Map<K, V> expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContainsAll(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual map might contains some but not all entries from the expected map. Please
   * note that actual map might have some of entries but the point is to ensure that not all
   * expected entries are exist in it. We do verify that both key and value match in this
   * comparision
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContainsAll(CVerificationQueue verifier, Map<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContainsAll(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Not Contains All Expected Values"));
  }

  /**
   * Verify that actual map might contains some but not all entries from the expected map. Please
   * note that actual map might have some of entries but the point is to ensure that not all
   * expected entries are exist in it. We do verify that both key and value match in this
   * comparision
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifyNotContainsAll(CVerificationQueue verifier, Map<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).notContainsAll(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify the map size is equal to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifySizeEquals(CVerificationQueue verifier, int expected, final int waitInSeconds) {
    verifySizeEquals(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify the map size is equal to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifySizeEquals(CVerificationQueue verifier, int expected, final int waitInSeconds, final String message, final Object... params) {
    verifySizeEquals(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify the map size is equal to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySizeEquals(CVerificationQueue verifier, int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySizeEquals(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Size Is Equal To Expected Value"));
  }

  /**
   * Verify the map size is equal to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifySizeEquals(CVerificationQueue verifier, int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).sizeEquals(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifySizeIsGreaterThan(final CVerificationQueue verifier, int expected, final int waitInSeconds) {
    verifySizeIsGreaterThan(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifySizeIsGreaterThan(CVerificationQueue verifier, int expected, final int waitInSeconds, final String message, final Object... params) {
    verifySizeIsGreaterThan(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySizeIsGreaterThan(CVerificationQueue verifier, int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySizeIsGreaterThan(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Size Is Greater Than Expected Value"));
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifySizeIsGreaterThan(CVerificationQueue verifier, int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).sizeIsGreaterThan(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual has value greater than or equals to expected.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifySizeIsGreaterThanOrEqual(final CVerificationQueue verifier, int expected, final int waitInSeconds) {
    verifySizeIsGreaterThanOrEqual(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual has value greater than or equals to expected.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifySizeIsGreaterThanOrEqual(CVerificationQueue verifier, int expected, final int waitInSeconds, final String message, final Object... params) {
    verifySizeIsGreaterThanOrEqual(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual has value greater than or equals to expected.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySizeIsGreaterThanOrEqual(CVerificationQueue verifier, int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySizeIsGreaterThanOrEqual(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Size Is Greater Than Expected Value"));
  }

  /**
   * Verify that actual has value greater than or equals to expected.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifySizeIsGreaterThanOrEqual(CVerificationQueue verifier, int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).sizeIsGreaterThanOrEqual(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifySizeIsLessThan(final CVerificationQueue verifier, int expected, final int waitInSeconds) {
    verifySizeIsLessThan(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifySizeIsLessThan(CVerificationQueue verifier, int expected, final int waitInSeconds, final String message, final Object... params) {
    verifySizeIsLessThan(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySizeIsLessThan(CVerificationQueue verifier, int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySizeIsLessThan(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Size Is Less Than Expected Value"));
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifySizeIsLessThan(CVerificationQueue verifier, int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).sizeIsLessThan(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual has value less than or equals to expected.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifySizeIsLessThanOrEqual(final CVerificationQueue verifier, int expected, final int waitInSeconds) {
    verifySizeIsLessThanOrEqual(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual has value less than or equals to expected.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifySizeIsLessThanOrEqual(CVerificationQueue verifier, int expected, final int waitInSeconds, final String message, final Object... params) {
    verifySizeIsLessThanOrEqual(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual has value less than or equals to expected.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySizeIsLessThanOrEqual(CVerificationQueue verifier, int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySizeIsLessThanOrEqual(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Size Is Less Than Or Equal To Expected Value"));
  }

  /**
   * Verify that actual has value less than or equals to expected.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters for message if message is a string format
   */
  default void verifySizeIsLessThanOrEqual(CVerificationQueue verifier, int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).sizeIsLessThanOrEqual(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }
}
