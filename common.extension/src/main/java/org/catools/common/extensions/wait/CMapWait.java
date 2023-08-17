package org.catools.common.extensions.wait;

import org.catools.common.collections.CHashMap;
import org.catools.common.collections.interfaces.CMap;
import org.catools.common.extensions.wait.interfaces.CMapWaiter;

import java.util.Map;

/**
 * Map wait class contains all wait method which is related to Map
 */
public class CMapWait extends CObjectWait {
  /**
   * Wait for defined number of seconds till actual map contains the expected entry.
   *
   * @param <K>                    type of map key
   * @param <V>                    type of map value
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static <K, V> boolean waitContains(Map<K, V> actual, Map.Entry<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitContains(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual map contains the expected key and value.
   *
   * @param actual                 map to compare
   * @param expectedKey            key to compare
   * @param expectedValue          value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param <K>                    type of map key
   * @param <V>                    type of map value
   * @return return actual value or null if the timeout reached
   */
  public static <K, V> boolean waitContains(Map<K, V> actual, K expectedKey, V expectedValue, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitContains(expectedKey, expectedValue, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual map contains all entries from the expected map.
   * Please note that actual map might have more entries.
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param <K>                    type of map key
   * @param <V>                    type of map value
   * @return return actual value or null if the timeout reached
   */
  public static <K, V> boolean waitContainsAll(Map<K, V> actual, Map<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitContainsAll(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual map contains none of entries from the expected
   * map.
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param <K>                    type of map key
   * @param <V>                    type of map value
   * @return return actual value or null if the timeout reached
   */
  public static <K, V> boolean waitContainsNone(Map<K, V> actual, Map<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitContainsNone(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual map either is empty or contains the expected
   * entry.
   *
   * @param actual                 value to compare
   * @param expectedKey            key to compare
   * @param expectedValue          value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param <K>                    type of map key
   * @param <V>                    type of map value
   * @return return actual value or null if the timeout reached
   */
  public static <K, V> boolean waitEmptyOrContains(Map<K, V> actual, K expectedKey, V expectedValue, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitEmptyOrContains(expectedKey, expectedValue, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual map either is empty or contains the expected
   * entry.
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param <K>                    type of map key
   * @param <V>                    type of map value
   * @return return actual value or null if the timeout reached
   */
  public static <K, V> boolean waitEmptyOrContains(Map<K, V> actual, Map.Entry<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitEmptyOrContains(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual map either is empty or does not contains the
   * expected entry.
   *
   * @param actual                 value to compare
   * @param expectedKey            key to compare
   * @param expectedValue          value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param <K>                    type of map key
   * @param <V>                    type of map value
   * @return return actual value or null if the timeout reached
   */
  public static <K, V> boolean waitEmptyOrNotContains(Map<K, V> actual, K expectedKey, V expectedValue, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitEmptyOrNotContains(expectedKey, expectedValue, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual map either is empty or does not contain the
   * expected entry.
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param <K>                    type of map key
   * @param <V>                    type of map value
   * @return return actual value or null if the timeout reached
   */
  public static <K, V> boolean waitEmptyOrNotContains(Map<K, V> actual, Map.Entry<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitEmptyOrNotContains(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected maps have the exact same entries.
   * (Ignore entry order) First we compare that actual map contains all expected map entries and
   * then we Wait for defined number of seconds till expected has all entries from actual.
   *
   * @param actual                 map to compare
   * @param expected               map to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param <K>                    type of map key
   * @param <V>                    type of map value
   * @return return actual value or null if the timeout reached
   */
  public static <K, V> boolean waitEquals(Map<K, V> actual, Map<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitEquals(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual map is empty.
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param <K>                    type of map key
   * @param <V>                    type of map value
   * @return return actual value or null if the timeout reached
   */
  public static <K, V> boolean waitIsEmpty(Map<K, V> actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsEmpty(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual map is not empty. (might contains null values)
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param <K>                    type of map key
   * @param <V>                    type of map value
   * @return return actual value or null if the timeout reached
   */
  public static <K, V> boolean waitIsNotEmpty(Map<K, V> actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsNotEmpty(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual map does not contain the expected entry.
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param <K>                    type of map key
   * @param <V>                    type of map value
   * @return return actual value or null if the timeout reached
   */
  public static <K, V> boolean waitNotContains(Map<K, V> actual, Map.Entry<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitNotContains(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual map does not contain the expected entry.
   *
   * @param actual                 value to compare
   * @param expectedKey            key to compare
   * @param expectedValue          value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param <K>                    type of map key
   * @param <V>                    type of map value
   * @return return actual value or null if the timeout reached
   */
  public static <K, V> boolean waitNotContains(Map<K, V> actual, K expectedKey, V expectedValue, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitNotContains(expectedKey, expectedValue, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual map does not contain all entries from the
   * expected map. Please note that actual map might have some entries but the point is to ensure
   * that not all expected entries are exist in it.
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param <K>                    type of map key
   * @param <V>                    type of map value
   * @return return actual value or null if the timeout reached
   */
  public static <K, V> boolean waitNotContainsAll(Map<K, V> actual, Map<K, V> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitNotContainsAll(expected, waitInSeconds, intervalInMilliSeconds);
  }

  private static <K, V> CMapWaiter<K, V> toWaiter(Map<K, V> actual) {
    return () -> {
      if (actual instanceof CMap actualMap) {
        return actualMap;
      }
      return new CHashMap<>();
    };
  }
}
