package org.catools.common.extensions.verify.interfaces.verifier;

import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CLinkedMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.collections.interfaces.CCollection;
import org.catools.common.collections.interfaces.CMap;
import org.catools.common.extensions.base.CBaseMapExtension;
import org.catools.common.extensions.states.interfaces.CMapState;
import org.catools.common.extensions.verify.CVerificationQueue;
import org.catools.common.extensions.verify.hard.CMapVerification;

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
public interface CMapVerifier<K, V> extends CBaseMapExtension<K, V>, CObjectVerifier<Map<K, V>, CMapState<K, V>> {

  /**
   * Verify that actual map contains the expected key and value.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   */
  default void verifyContains(final CVerificationQueue verifier, K expectedKey, V expectedValue) {
    verifyContains(verifier, Map.entry(expectedKey, expectedValue));
  }

  /**
   * Verify that actual map contains the expected key and value.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyContains(CVerificationQueue verifier, K expectedKey, V expectedValue, final String message, final Object... params) {
    verifyContains(verifier, Map.entry(expectedKey, expectedValue), message, params);
  }

  /**
   * Verify that actual map contains the expected key and value.
   *
   * @param expected value to compare
   */
  default void verifyContains(final CVerificationQueue verifier, Map.Entry<K, V> expected) {
    verifyContains(verifier, expected, getDefaultMessage(("Contains The Expected Value")));
  }

  /**
   * Verify that actual map contains the expected key and value.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifyContains(CVerificationQueue verifier, Map.Entry<K, V> expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, e) -> {
      if (_get() == null || expected == null) {
        return false;
      }

      if (!_get().containsKey(expected.getKey())) {
        logger.warn("Key does not exists in map. Expected: '{}' Actual: '{}'", expected.getKey(), _get().keySet());
        return false;
      }

      V value = _get().get(expected.getKey());
      if (!Objects.equals(value, expected.getValue())) {
        logger.warn("Expected Value does not match the map entity. Expected: '{}' Actual: '{}'", expected.getValue(), value);
        return false;
      }

      return true;
    }, message, params);
  }

  /**
   * Verify that actual map contains all entries from the expected map. Please note that actual map
   * might have more entries.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifyContainsAll(CVerificationQueue verifier, Map<K, V> expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, e) -> {
      if (expected == null) {
        return false;
      }
      CMap<K, V> diff = new CHashMap<>();
      boolean result = _toState(a).containsAll(e, entry -> diff.put(entry.getKey(), entry.getValue()));
      if (!diff.isEmpty()) {
        logger.warn("Actual list does not contain following records:\n" + diff);
      }

      return result;
    }, message, params);
  }

  /**
   * Verify that actual map contains all entries from the expected map. Please note that actual map
   * might have more entries.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   */
  default void verifyContainsAll(final CVerificationQueue verifier, Map<K, V> expected) {
    verifyContainsAll(verifier, expected, getDefaultMessage(("Contains All The Expected Map")));
  }

  /**
   * Verify that actual map contains none of entries from the expected map.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifyContainsNone(CVerificationQueue verifier, Map<K, V> expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, e) -> {
      if (expected == null) {
        return false;
      }
      CMap<K, V> diff = new CHashMap<>();
      boolean result = _toState(a).containsNone(e, entry -> diff.put(entry.getKey(), entry.getValue()));
      if (!diff.isEmpty()) {
        logger.warn("Actual list contains following records:\n" + diff);
      }

      return result;
    }, message, params);
  }

  /**
   * Verify that actual map contains none of entries from the expected map.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   */
  default void verifyContainsNone(final CVerificationQueue verifier, Map<K, V> expected) {
    verifyContainsNone(verifier, expected, getDefaultMessage(("Not Contains Any Record From The Expected Map")));
  }

  /**
   * Verify that actual map either is empty or contains the expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   */
  default void verifyEmptyOrContains(CVerificationQueue verifier, K expectedKey, V expectedValue) {
    verifyEmptyOrContains(verifier, Map.entry(expectedKey, expectedValue));
  }

  /**
   * Verify that actual map either is empty or contains the expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyEmptyOrContains(CVerificationQueue verifier, K expectedKey, V expectedValue, final String message, final Object... params) {
    verifyEmptyOrContains(verifier, Map.entry(expectedKey, expectedValue), message, params);
  }

  /**
   * Verify that actual map either is empty or contains the expected entry.
   *
   * @param expected value to compare
   */
  default void verifyEmptyOrContains(CVerificationQueue verifier, Map.Entry<K, V> expected) {
    verifyEmptyOrContains(verifier, expected, getDefaultMessage(("Is Empty Or Contains The Expected Value")));
  }

  /**
   * Verify that actual map either is empty or contains the expected entry.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifyEmptyOrContains(CVerificationQueue verifier, Map.Entry<K, V> expected, final String message, final Object... params) {
    Objects.requireNonNull(expected);
    Objects.requireNonNull(expected.getKey());
    _verify(verifier, expected, (a, e) -> {
      if (_get() == null) {
        return false;
      }

      if (_get().isEmpty()) {
        return true;
      }

      if (!_get().containsKey(expected.getKey())) {
        logger.warn("Key does not exists in map. Expected Key: '{}' Actual keys: '{}'", expected.getKey(), _get().keySet());
        return false;
      }

      V value = _get().get(expected.getKey());
      if (!Objects.equals(value, expected.getValue())) {
        logger.warn("Expected Value does not match the map entity. Expected Key: '{}' Actual keys: '{}'", expected.getValue(), value);
        return false;
      }

      return true;
    }, message, params);
  }

  /**
   * Verify that actual map either is empty or does not contains the expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   */
  default void verifyEmptyOrNotContains(CVerificationQueue verifier, K expectedKey, V expectedValue) {
    verifyEmptyOrNotContains(verifier, Map.entry(expectedKey, expectedValue));
  }

  /**
   * Verify that actual map either is empty or does not contains the expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyEmptyOrNotContains(CVerificationQueue verifier, K expectedKey, V expectedValue, final String message, final Object... params) {
    verifyEmptyOrNotContains(verifier, Map.entry(expectedKey, expectedValue), message, params);
  }

  /**
   * Verify that actual map either is empty or does not contains the expected entry.
   *
   * @param expected value to compare
   */
  default void verifyEmptyOrNotContains(CVerificationQueue verifier, Map.Entry<K, V> expected) {
    verifyEmptyOrNotContains(verifier, expected, getDefaultMessage(("Is Empty Or Not Contains The Expected Value")));
  }

  /**
   * Verify that actual map either is empty or does not contains the expected entry.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifyEmptyOrNotContains(CVerificationQueue verifier, Map.Entry<K, V> expected, final String message, final Object... params) {
    Objects.requireNonNull(expected.getKey());
    _verify(verifier, expected, (a, e) -> _toState(a).emptyOrNotContains(expected), message, params);
  }

  /**
   * Verify that actual and expected maps have the exact same entries. (Ignore entry order) First we
   * compare that actual map contains all expected map entries and then we verify that expected has
   * all entries from actual.
   *
   * @param verifier CVerificationQueue instance
   * @param expected map to compare
   */
  default void verifyEquals(final CVerificationQueue verifier, Map<K, V> expected) {
    verifyEquals(verifier, expected, getDefaultMessage(("Equals Expected Value")));
  }

  /**
   * Verify that actual and expected maps have the exact same entries. (Ignore entry order) First we
   * compare that actual map contains all expected map entries and then we verify that expected has
   * all entries from actual.
   *
   * @param verifier CVerificationQueue instance
   * @param expected map to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifyEquals(CVerificationQueue verifier, Map<K, V> expected, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> {
      CMap<K, V> diffActual = new CHashMap<>();
      CMap<K, V> diffExpected = new CHashMap<>();
      boolean equals = _toState(o).isEqual(o2, entry -> diffActual.put(entry.getKey(), entry.getValue()), entry -> diffExpected.put(entry.getKey(), entry.getValue()));
      if (!diffExpected.isEmpty()) {
        logger.warn("Actual list does not contain following records:\n" + diffExpected);
      }

      if (!diffActual.isEmpty()) {
        logger.warn("Expected list does not contain following records:\n" + diffActual);
      }
      return equals;
    }, message, params);
  }

  /**
   * Verify that actual map is empty.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsEmpty(final CVerificationQueue verifier) {
    verifyIsEmpty(verifier, getDefaultMessage(("Is Empty")));
  }

  /**
   * Verify that actual map is empty.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifyIsEmpty(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, true, (o, o2) -> _get() == null || _get().isEmpty(), message, params);
  }

  /**
   * Verify that actual map is not empty. (might contains null values)
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsNotEmpty(final CVerificationQueue verifier) {
    verifyIsNotEmpty(verifier, getDefaultMessage(("Is Not Empty")));
  }

  /**
   * Verify that actual map is not empty. (might contains null values)
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifyIsNotEmpty(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, true, (o, o2) -> _toState(o).isNotEmpty(), message, params);
  }

  /**
   * Verify that actual map does not contain the expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   */
  default void verifyNotContains(CVerificationQueue verifier, K expectedKey, V expectedValue) {
    verifyNotContains(verifier, Map.entry(expectedKey, expectedValue));
  }

  /**
   * Verify that actual map does not contain the expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyNotContains(CVerificationQueue verifier, K expectedKey, V expectedValue, final String message, final Object... params) {
    verifyNotContains(verifier, Map.entry(expectedKey, expectedValue), message, params);
  }

  /**
   * Verify that actual map does not contain the expected entry.
   *
   * @param expected value to compare
   */
  default void verifyNotContains(final CVerificationQueue verifier, Map.Entry<K, V> expected) {
    verifyNotContains(verifier, expected, getDefaultMessage(("Not Contains Expected Value")));
  }

  /**
   * Verify that actual map does not contain the expected entry.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifyNotContains(CVerificationQueue verifier, Map.Entry<K, V> expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, e) -> _toState(a).notContains(e), message, params);
  }

  /**
   * Verify that actual map might contains some but not all entries from the expected map. Please
   * note that actual map might have some of entries but the point is to ensure that not all
   * expected entries are exist in it. We do verify that both key and value match in this
   * comparision
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   */
  default void verifyNotContainsAll(final CVerificationQueue verifier, Map<K, V> expected) {
    verifyNotContainsAll(verifier, expected, getDefaultMessage(("Not Contains All Expected Values")));
  }

  /**
   * Verify that actual map might contains some but not all entries from the expected map. Please
   * note that actual map might have some of entries but the point is to ensure that not all
   * expected entries are exist in it. We do verify that both key and value match in this
   * comparision
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifyNotContainsAll(CVerificationQueue verifier, Map<K, V> expected, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).notContainsAll(o2), message, params);
  }

  /**
   * Verify the map size is equal to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   */
  default void verifySizeEquals(final CVerificationQueue verifier, int expected) {
    verifySizeEquals(verifier, expected, getDefaultMessage(("Size Is Equal To Expected Value")));
  }

  /**
   * Verify the map size is equal to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifySizeEquals(CVerificationQueue verifier, int expected, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).sizeEquals(o2), message, params);
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   */
  default void verifySizeIsGreaterThan(final CVerificationQueue verifier, int expected) {
    verifySizeIsGreaterThan(verifier, expected, getDefaultMessage(("Size Is Greater Than Expected Value")));
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifySizeIsGreaterThan(CVerificationQueue verifier, int expected, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).sizeIsGreaterThan(o2), message, params);
  }

  /**
   * Verify that actual has value greater than or equals to expected.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   */
  default void verifySizeIsGreaterThanOrEqual(final CVerificationQueue verifier, int expected) {
    verifySizeIsGreaterThanOrEqual(verifier, expected, getDefaultMessage(("Size Is Greater Than Expected Value")));
  }

  /**
   * Verify that actual has value greater than or equals to expected.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifySizeIsGreaterThanOrEqual(CVerificationQueue verifier, int expected, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).sizeIsGreaterThanOrEqual(o2), message, params);
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   */
  default void verifySizeIsLessThan(final CVerificationQueue verifier, int expected) {
    verifySizeIsLessThan(verifier, expected, getDefaultMessage(("Size Is Less Than Expected Value")));
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifySizeIsLessThan(CVerificationQueue verifier, int expected, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).sizeIsLessThan(o2), message, params);
  }

  /**
   * Verify that actual has value less than or equals to expected.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   */
  default void verifySizeIsLessThanOrEqual(final CVerificationQueue verifier, int expected) {
    verifySizeIsLessThanOrEqual(verifier, expected, getDefaultMessage(("Size Is Less Than Or Equal Expected Value")));
  }

  /**
   * Verify that actual has value less than or equals to expected.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifySizeIsLessThanOrEqual(CVerificationQueue verifier, int expected, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).sizeIsLessThanOrEqual(o2), message, params);
  }
}
