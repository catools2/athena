package org.catools.common.extensions.verify.hard;

import lombok.extern.slf4j.Slf4j;
import org.catools.common.extensions.verify.interfaces.base.CMapVerify;

import java.util.Map;

/**
 * Map verification class contains all verification method which is related to Map
 */
@Slf4j
public class CMapVerification extends CBaseVerification {

  /**
   * Verify that actual map contains the expected entry.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param <K>      type of map key
   * @param <V>      type of map value
   */
  public <K, V> void contains(Map<K, V> actual, Map.Entry<K, V> expected) {
    toVerifier(actual).verifyContains(expected);
  }

  /**
   * Verify that actual map contains the expected entry.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   * @param <K>      type of map key
   * @param <V>      type of map value
   */
  public <K, V> void contains(Map<K, V> actual, Map.Entry<K, V> expected, final String message, final Object... params) {
    toVerifier(actual).verifyContains(expected, message, params);
  }

  /**
   * Verify that actual map contains the expected key and value.
   *
   * @param actual        map to compare
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param <K>           type of map key
   * @param <V>           type of map value
   */
  public <K, V> void contains(Map<K, V> actual, K expectedKey, V expectedValue) {
    toVerifier(actual).verifyContains(expectedKey, expectedValue);
  }

  /**
   * Verify that actual map contains the expected key and value.
   *
   * @param actual        map to compare
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   * @param <K>           type of map key
   * @param <V>           type of map value
   */
  public <K, V> void contains(
      Map<K, V> actual,
      K expectedKey,
      V expectedValue,
      final String message,
      final Object... params) {
    toVerifier(actual).verifyContains(expectedKey, expectedValue, message, params);
  }

  /**
   * Verify that actual map contains all entries from the expected map. Please note that actual map
   * might have more entries.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param <K>      type of map key
   * @param <V>      type of map value
   */
  public <K, V> void containsAll(Map<K, V> actual, Map<K, V> expected) {
    toVerifier(actual).verifyContainsAll(expected);
  }

  /**
   * Verify that actual map contains all entries from the expected map. Please note that actual map
   * might have more entries.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   * @param <K>      type of map key
   * @param <V>      type of map value
   */
  public <K, V> void containsAll(Map<K, V> actual, Map<K, V> expected, final String message, final Object... params) {
    toVerifier(actual).verifyContainsAll(expected, message, params);
  }

  /**
   * Verify that actual map contains none of entries from the expected map.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param <K>      type of map key
   * @param <V>      type of map value
   */
  public <K, V> void containsNone(Map<K, V> actual, Map<K, V> expected) {
    toVerifier(actual).verifyContainsNone(expected);
  }

  /**
   * Verify that actual map contains none of entries from the expected map.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   * @param <K>      type of map key
   * @param <V>      type of map value
   */
  public <K, V> void containsNone(Map<K, V> actual, Map<K, V> expected, final String message, final Object... params) {
    toVerifier(actual).verifyContainsNone(expected, message, params);
  }

  /**
   * Verify that actual map either is empty or contains the expected entry.
   *
   * @param actual        value to compare
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param <K>           type of map key
   * @param <V>           type of map value
   */
  public <K, V> void emptyOrContains(Map<K, V> actual, K expectedKey, V expectedValue) {
    toVerifier(actual).verifyEmptyOrContains(expectedKey, expectedValue);
  }

  /**
   * Verify that actual map either is empty or contains the expected entry.
   *
   * @param actual        value to compare
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   * @param <K>           type of map key
   * @param <V>           type of map value
   */
  public <K, V> void emptyOrContains(
      Map<K, V> actual,
      K expectedKey,
      V expectedValue,
      final String message,
      final Object... params) {
    toVerifier(actual).verifyEmptyOrContains(expectedKey, expectedValue, message, params);
  }

  /**
   * Verify that actual map either is empty or contains the expected entry.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param <K>      type of map key
   * @param <V>      type of map value
   */
  public <K, V> void emptyOrContains(Map<K, V> actual, Map.Entry<K, V> expected) {
    toVerifier(actual).verifyEmptyOrContains(expected);
  }

  /**
   * Verify that actual map either is empty or contains the expected entry.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   * @param <K>      type of map key
   * @param <V>      type of map value
   */
  public <K, V> void emptyOrContains(Map<K, V> actual, Map.Entry<K, V> expected, final String message, final Object... params) {
    toVerifier(actual).verifyEmptyOrContains(expected, message, params);
  }

  /**
   * Verify that actual map either is empty or does not contains the expected entry.
   *
   * @param actual        value to compare
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param <K>           type of map key
   * @param <V>           type of map value
   */
  public <K, V> void emptyOrNotContains(Map<K, V> actual, K expectedKey, V expectedValue) {
    toVerifier(actual).verifyEmptyOrNotContains(expectedKey, expectedValue);
  }

  /**
   * Verify that actual map either is empty or does not contains the expected entry.
   *
   * @param actual        value to compare
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   * @param <K>           type of map key
   * @param <V>           type of map value
   */
  public <K, V> void emptyOrNotContains(
      Map<K, V> actual,
      K expectedKey,
      V expectedValue,
      final String message,
      final Object... params) {
    toVerifier(actual).verifyEmptyOrNotContains(expectedKey, expectedValue, message, params);
  }

  /**
   * Verify that actual map either is empty or does not contain the expected entry.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param <K>      type of map key
   * @param <V>      type of map value
   */
  public <K, V> void emptyOrNotContains(Map<K, V> actual, Map.Entry<K, V> expected) {
    toVerifier(actual).verifyEmptyOrNotContains(expected);
  }

  /**
   * Verify that actual map either is empty or does not contain the expected entry.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   * @param <K>      type of map key
   * @param <V>      type of map value
   */
  public <K, V> void emptyOrNotContains(Map<K, V> actual, Map.Entry<K, V> expected, final String message, final Object... params) {
    toVerifier(actual).verifyEmptyOrNotContains(expected, message, params);
  }

  /**
   * Verify that actual and expected maps have the exact same entries. (Ignore entry order) First we
   * compare that actual map contains all expected map entries and then we verify that expected has
   * all entries from actual.
   *
   * @param actual   map to compare
   * @param expected map to compare
   * @param <K>      type of map key
   * @param <V>      type of map value
   */
  public <K, V> void equals(Map<K, V> actual, Map<K, V> expected) {
    toVerifier(actual).verifyEquals(expected);
  }

  /**
   * Verify that actual and expected maps have the exact same entries. (Ignore entry order) First we
   * compare that actual map contains all expected map entries and then we verify that expected has
   * all entries from actual.
   *
   * @param actual   map to compare
   * @param expected map to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   * @param <K>      type of map key
   * @param <V>      type of map value
   */
  public <K, V> void equals(Map<K, V> actual, Map<K, V> expected, final String message, final Object... params) {
    toVerifier(actual).verifyEquals(expected, message, params);
  }

  /**
   * Verify that actual and expected maps does not have the exact same entries. (Ignore entry order)
   * First we compare that actual map does not contains all expected map entries and then if all
   * records matched we verify that expected does not have all entries from actual.
   *
   * @param actual   map to compare
   * @param expected map to compare
   * @param <K>      type of map key
   * @param <V>      type of map value
   */
  public <K, V> void notEquals(Map<K, V> actual, Map<K, V> expected) {
    toVerifier(actual).verifyNotEquals(expected);
  }

  /**
   * Verify that actual and expected maps does not have the exact same entries. (Ignore entry order)
   * First we compare that actual map does not contains all expected map entries and then if all
   * records matched we verify that expected does not have all entries from actual.
   *
   * @param actual   map to compare
   * @param expected map to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   * @param <K>      type of map key
   * @param <V>      type of map value
   */
  public <K, V> void notEquals(Map<K, V> actual, Map<K, V> expected, final String message, final Object... params) {
    toVerifier(actual).verifyNotEquals(expected, message, params);
  }

  /**
   * Verify that actual map is empty.
   *
   * @param actual value to compare
   * @param <K>    type of map key
   * @param <V>    type of map value
   */
  public <K, V> void isEmpty(Map<K, V> actual) {
    toVerifier(actual).verifyIsEmpty();
  }

  /**
   * Verify that actual map is empty.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   * @param <K>     type of map key
   * @param <V>     type of map value
   */
  public <K, V> void isEmpty(Map<K, V> actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsEmpty(message, params);
  }

  /**
   * Verify that actual map is not empty. (might contains null values)
   *
   * @param actual value to compare
   * @param <K>    type of map key
   * @param <V>    type of map value
   */
  public <K, V> void isNotEmpty(Map<K, V> actual) {
    toVerifier(actual).verifyIsNotEmpty();
  }

  /**
   * Verify that actual map is not empty. (might contains null values)
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   * @param <K>     type of map key
   * @param <V>     type of map value
   */
  public <K, V> void isNotEmpty(Map<K, V> actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsNotEmpty(message, params);
  }

  /**
   * Verify that actual map does not contain the expected entry.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param <K>      type of map key
   * @param <V>      type of map value
   */
  public <K, V> void notContains(Map<K, V> actual, Map.Entry<K, V> expected) {
    toVerifier(actual).verifyNotContains(expected);
  }

  /**
   * Verify that actual map does not contain the expected entry.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   * @param <K>      type of map key
   * @param <V>      type of map value
   */
  public <K, V> void notContains(Map<K, V> actual, Map.Entry<K, V> expected, final String message, final Object... params) {
    toVerifier(actual).verifyNotContains(expected, message, params);
  }

  /**
   * Verify that actual map does not contain the expected entry.
   *
   * @param actual        value to compare
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param <K>           type of map key
   * @param <V>           type of map value
   */
  public <K, V> void notContains(Map<K, V> actual, K expectedKey, V expectedValue) {
    toVerifier(actual).verifyNotContains(expectedKey, expectedValue);
  }

  /**
   * Verify that actual map does not contain the expected entry.
   *
   * @param actual        value to compare
   * @param expectedKey   key to compare
   * @param expectedValue value to compare
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   * @param <K>           type of map key
   * @param <V>           type of map value
   */
  public <K, V> void notContains(
      Map<K, V> actual,
      K expectedKey,
      V expectedValue,
      final String message,
      final Object... params) {
    toVerifier(actual).verifyNotContains(expectedKey, expectedValue, message, params);
  }

  /**
   * Verify that actual map does not contain all entries from the expected map. Please note that
   * actual map might have some entries but the point is to ensure that not all expected entries
   * are exist in it.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param <K>      type of map key
   * @param <V>      type of map value
   */
  public <K, V> void notContainsAll(Map<K, V> actual, Map<K, V> expected) {
    toVerifier(actual).verifyNotContainsAll(expected);
  }

  /**
   * Verify that actual map does not contain all entries from the expected map. Please note that
   * actual map might have some entries but the point is to ensure that not all expected entries
   * are exist in it.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   * @param <K>      type of map key
   * @param <V>      type of map value
   */
  public <K, V> void notContainsAll(Map<K, V> actual, Map<K, V> expected, final String message, final Object... params) {
    toVerifier(actual).verifyNotContainsAll(expected, message, params);
  }


  /**
   * Verify the map size is equal to expected value.
   *
   * @param actual   value to compare
   * @param expected value to compare
   */
  public <K, V> void sizeEquals(Map<K, V> actual, int expected) {
    toVerifier(actual).verifySizeEquals(expected);
  }

  /**
   * Verify the map size is equal to expected value.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  public <K, V> void sizeEquals(Map<K, V> actual, int expected, final String message, final Object... params) {
    toVerifier(actual).verifySizeEquals(expected, message, params);
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param actual   value to compare
   * @param expected value to compare
   */
  public <K, V> void sizeIsGreaterThan(Map<K, V> actual, int expected) {
    toVerifier(actual).verifySizeIsGreaterThan(expected);
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  public <K, V> void sizeIsGreaterThan(Map<K, V> actual, int expected, final String message, final Object... params) {
    toVerifier(actual).verifySizeIsGreaterThan(expected, message, params);
  }

  /**
   * Verify that actual has value greater than or equals to expected.
   *
   * @param actual   value to compare
   * @param expected value to compare
   */
  public <K, V> void sizeIsGreaterThanOrEqual(Map<K, V> actual, int expected) {
    toVerifier(actual).verifySizeIsGreaterThanOrEqual(expected);
  }

  /**
   * Verify that actual has value greater than or equals to expected.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  public <K, V> void sizeIsGreaterThanOrEqual(Map<K, V> actual, int expected, final String message, final Object... params) {
    toVerifier(actual).verifySizeIsGreaterThanOrEqual(expected, message, params);
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param actual   value to compare
   * @param expected value to compare
   */
  public <K, V> void sizeIsLessThan(Map<K, V> actual, int expected) {
    toVerifier(actual).verifySizeIsLessThan(expected);
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  public <K, V> void sizeIsLessThan(Map<K, V> actual, int expected, final String message, final Object... params) {
    toVerifier(actual).verifySizeIsLessThan(expected, message, params);
  }

  /**
   * Verify that actual has value less than or equals to expected.
   *
   * @param actual   value to compare
   * @param expected value to compare
   */
  public <K, V> void sizeIsLessThanOrEqual(Map<K, V> actual, int expected) {
    toVerifier(actual).verifySizeIsLessThanOrEqual(expected);
  }

  /**
   * Verify that actual has value less than or equals to expected.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters for message if message is a string format
   */
  public <K, V> void sizeIsLessThanOrEqual(Map<K, V> actual, int expected, final String message, final Object... params) {
    toVerifier(actual).verifySizeIsLessThanOrEqual(expected, message, params);
  }

  protected <K, V> CMapVerify<K, V> toVerifier(Map<K, V> actual) {
    return () -> actual;
  }
}
