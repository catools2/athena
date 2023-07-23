package org.catools.common.extensions.verify.interfaces.base;

import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CLinkedMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.collections.interfaces.CCollection;
import org.catools.common.collections.interfaces.CMap;
import org.catools.common.extensions.base.CBaseMapExtension;
import org.catools.common.extensions.states.interfaces.CMapState;
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
public interface CMapVerify<K, V> extends CBaseMapExtension<K, V>, CObjectVerify<Map<K, V>, CMapState<K, V>> {

  /**
   * Verify that actual map contains the expected key and value.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   */
  default void verifyContains(K expectedKey, V expectedValue) {
    verifyContains(Map.entry(expectedKey, expectedValue));
  }

  /**
   * Verify that actual map contains the expected key and value.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyContains(K expectedKey, V expectedValue, final String message, final Object... params) {
    verifyContains(Map.entry(expectedKey, expectedValue), message, params);
  }

  /**
   * Verify that actual map contains the expected key and value.
   *
   * @param expected value to compare
   */
  default void verifyContains(Map.Entry<K, V> expected) {
    verifyContains(expected, getDefaultMessage(("Contains The Expected Value")));
  }

  /**
   * Verify that actual map contains the expected key and value.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifyContains(Map.Entry<K, V> expected, final String message, final Object... params) {
    _verify(expected, (a, e) -> {
      if (_get() == null || expected == null) {
        return false;
      }

      if (!_get().containsKey(expected.getKey())) {
        logger.warn("Key does not exist in map. Expected: '{}' Actual: '{}'", expected.getKey(), _get().keySet());
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
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifyContainsAll(Map<K, V> expected, final String message, final Object... params) {
    _verify(expected, (a, e) -> {
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
   * @param expected value to compare
   */
  default void verifyContainsAll(Map<K, V> expected) {
    verifyContainsAll(expected, getDefaultMessage(("Contains All The Expected Map")));
  }

  /**
   * Verify that actual map contains none of entries from the expected map.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifyContainsNone(Map<K, V> expected, final String message, final Object... params) {
    _verify(expected, (a, e) -> {
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
   * @param expected value to compare
   */
  default void verifyContainsNone(Map<K, V> expected) {
    verifyContainsNone(expected, getDefaultMessage(("Not Contains Any Record From The Expected Map")));
  }

  /**
   * Verify that actual map either is empty or contains the expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   */
  default void verifyEmptyOrContains(K expectedKey, V expectedValue) {
    verifyEmptyOrContains(Map.entry(expectedKey, expectedValue));
  }

  /**
   * Verify that actual map either is empty or contains the expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyEmptyOrContains(K expectedKey, V expectedValue, final String message, final Object... params) {
    verifyEmptyOrContains(Map.entry(expectedKey, expectedValue), message, params);
  }

  /**
   * Verify that actual map either is empty or contains the expected entry.
   *
   * @param expected value to compare
   */
  default void verifyEmptyOrContains(Map.Entry<K, V> expected) {
    verifyEmptyOrContains(expected, getDefaultMessage(("Is Empty Or Contains The Expected Value")));
  }

  /**
   * Verify that actual map either is empty or contains the expected entry.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifyEmptyOrContains(Map.Entry<K, V> expected, final String message, final Object... params) {
    Objects.requireNonNull(expected);
    Objects.requireNonNull(expected.getKey());
    _verify(expected, (a, e) -> {
      if (_get() == null) {
        return false;
      }

      if (_get().isEmpty()) {
        return true;
      }

      if (!_get().containsKey(expected.getKey())) {
        logger.warn("Key does not exist in map. Expected Key: '{}' Actual keys: '{}'", expected.getKey(), _get().keySet());
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
  default void verifyEmptyOrNotContains(K expectedKey, V expectedValue) {
    verifyEmptyOrNotContains(Map.entry(expectedKey, expectedValue));
  }

  /**
   * Verify that actual map either is empty or does not contains the expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyEmptyOrNotContains(K expectedKey, V expectedValue, final String message, final Object... params) {
    verifyEmptyOrNotContains(Map.entry(expectedKey, expectedValue), message, params);
  }

  /**
   * Verify that actual map either is empty or does not contains the expected entry.
   *
   * @param expected value to compare
   */
  default void verifyEmptyOrNotContains(Map.Entry<K, V> expected) {
    verifyEmptyOrNotContains(expected, getDefaultMessage(("Is Empty Or Not Contains The Expected Value")));
  }

  /**
   * Verify that actual map either is empty or does not contains the expected entry.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifyEmptyOrNotContains(Map.Entry<K, V> expected, final String message, final Object... params) {
    Objects.requireNonNull(expected.getKey());
    _verify(expected, (a, e) -> _toState(a).emptyOrNotContains(expected), message, params);
  }

  /**
   * Verify that actual and expected maps have the exact same entries. (Ignore entry order) First we
   * compare that actual map contains all expected map entries and then we verify that expected has
   * all entries from actual.
   *
   * @param expected map to compare
   */
  default void verifyEquals(Map<K, V> expected) {
    verifyEquals(expected, getDefaultMessage(("Equals Expected Value")));
  }

  /**
   * Verify that actual and expected maps have the exact same entries. (Ignore entry order) First we
   * compare that actual map contains all expected map entries and then we verify that expected has
   * all entries from actual.
   *
   * @param expected map to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifyEquals(Map<K, V> expected, final String message, final Object... params) {
    _verify(expected, (o, o2) -> {
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
   */
  default void verifyIsEmpty() {
    verifyIsEmpty(getDefaultMessage(("Is Empty")));
  }

  /**
   * Verify that actual map is empty.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters for message if message is a string format
   */
  default void verifyIsEmpty(final String message, final Object... params) {
    _verify(true, (o, o2) -> _get() == null || _get().isEmpty(), message, params);
  }

  /**
   * Verify that actual map is not empty. (might contains null values)
   */
  default void verifyIsNotEmpty() {
    verifyIsNotEmpty(getDefaultMessage(("Is Not Empty")));
  }

  /**
   * Verify that actual map is not empty. (might contains null values)
   *
   * @param message information about the purpose of this verification
   * @param params  parameters for message if message is a string format
   */
  default void verifyIsNotEmpty(final String message, final Object... params) {
    _verify(true, (o, o2) -> _toState(o).isNotEmpty(), message, params);
  }

  /**
   * Verify that actual map does not contain the expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   */
  default void verifyNotContains(K expectedKey, V expectedValue) {
    verifyNotContains(Map.entry(expectedKey, expectedValue));
  }

  /**
   * Verify that actual map does not contain the expected entry.
   *
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param message       information about the purpose of this verification
   * @param params        parameters for message if message is a string format
   */
  default void verifyNotContains(K expectedKey, V expectedValue, final String message, final Object... params) {
    verifyNotContains(Map.entry(expectedKey, expectedValue), message, params);
  }

  /**
   * Verify that actual map does not contain the expected entry.
   *
   * @param expected value to compare
   */
  default void verifyNotContains(Map.Entry<K, V> expected) {
    verifyNotContains(expected, getDefaultMessage(("Not Contains Expected Value")));
  }

  /**
   * Verify that actual map does not contain the expected entry.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifyNotContains(Map.Entry<K, V> expected, final String message, final Object... params) {
    _verify(expected, (a, e) -> _toState(a).notContains(e), message, params);
  }

  /**
   * Verify that actual map might contains some but not all entries from the expected map. Please
   * note that actual map might have some entries but the point is to ensure that not all
   * expected entries are exist in it. We do verify that both key and value match in this
   * comparision
   *
   * @param expected value to compare
   */
  default void verifyNotContainsAll(Map<K, V> expected) {
    verifyNotContainsAll(expected, getDefaultMessage(("Not Contains All Expected Values")));
  }

  /**
   * Verify that actual map might contains some but not all entries from the expected map. Please
   * note that actual map might have some entries but the point is to ensure that not all
   * expected entries are exist in it. We do verify that both key and value match in this
   * comparision
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifyNotContainsAll(Map<K, V> expected, final String message, final Object... params) {
    _verify(expected, (o, o2) -> _toState(o).notContainsAll(o2), message, params);
  }

  /**
   * Verify the map size is equal to expected value.
   *
   * @param expected value to compare
   */
  default void verifySizeEquals(int expected) {
    verifySizeEquals(expected, getDefaultMessage(("Size Is Equal To Expected Value")));
  }

  /**
   * Verify the map size is equal to expected value.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifySizeEquals(int expected, final String message, final Object... params) {
    _verify(expected, (o, o2) -> _toState(o).sizeEquals(o2), message, params);
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param expected value to compare
   */
  default void verifySizeIsGreaterThan(int expected) {
    verifySizeIsGreaterThan(expected, getDefaultMessage(("Size Is Greater Than Expected Value")));
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifySizeIsGreaterThan(int expected, final String message, final Object... params) {
    _verify(expected, (o, o2) -> _toState(o).sizeIsGreaterThan(o2), message, params);
  }

  /**
   * Verify that actual has value greater than or equals to expected.
   *
   * @param expected value to compare
   */
  default void verifySizeIsGreaterThanOrEqual(int expected) {
    verifySizeIsGreaterThanOrEqual(expected, getDefaultMessage(("Size Is Greater Than Expected Value")));
  }

  /**
   * Verify that actual has value greater than or equals to expected.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifySizeIsGreaterThanOrEqual(int expected, final String message, final Object... params) {
    _verify(expected, (o, o2) -> _toState(o).sizeIsGreaterThanOrEqual(o2), message, params);
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param expected value to compare
   */
  default void verifySizeIsLessThan(int expected) {
    verifySizeIsLessThan(expected, getDefaultMessage(("Size Is Less Than Expected Value")));
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifySizeIsLessThan(int expected, final String message, final Object... params) {
    _verify(expected, (o, o2) -> _toState(o).sizeIsLessThan(o2), message, params);
  }

  /**
   * Verify that actual has value less than or equals to expected.
   *
   * @param expected value to compare
   */
  default void verifySizeIsLessThanOrEqual(int expected) {
    verifySizeIsLessThanOrEqual(expected, getDefaultMessage(("Size Is Less Than Or Equal Expected Value")));
  }

  /**
   * Verify that actual has value less than or equals to expected.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  default void verifySizeIsLessThanOrEqual(int expected, final String message, final Object... params) {
    _verify(expected, (o, o2) -> _toState(o).sizeIsLessThanOrEqual(o2), message, params);
  }
}
