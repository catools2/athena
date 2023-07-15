package org.catools.common.extensions.wait;

import org.catools.common.extensions.wait.interfaces.CIterableWaiter;

/**
 * Iterable wait class contains all wait method which is related to Iterable
 */
public class CIterableWait extends CObjectWait {

  /**
   * Wait for defined number of seconds till actual collection contains the expected element.
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param <C>                    type of collection elements
   * @return return actual value or null if the timeout reached
   */
  public static <C> boolean waitContains(
      Iterable<C> actual, C expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitContains(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual collection contains all elements from the
   * expected collection. Please note that actual collection might have more elements.
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param <C>                    type of collection elements
   * @return return actual value or null if the timeout reached
   */
  public static <C> boolean waitContainsAll(
      Iterable<C> actual,
      Iterable<C> expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitContainsAll(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual collection contains none of elements from the
   * expected collection.
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param <C>                    type of collection elements
   * @return return actual value or null if the timeout reached
   */
  public static <C> boolean waitContainsNone(
      Iterable<C> actual,
      Iterable<C> expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitContainsNone(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual collection either is empty or contains the
   * expected element.
   *
   * @param <C>                    type of collection elements
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static <C> boolean waitEmptyOrContains(
      Iterable<C> actual, C expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitEmptyOrContains(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual collection either is empty or does not contain
   * the expected element.
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param <C>                    type of collection elements
   * @return return actual value or null if the timeout reached
   */
  public static <C> boolean waitEmptyOrNotContains(
      Iterable<C> actual, C expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitEmptyOrNotContains(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected collections have the exact same
   * elements. (Ignore element order) First we compare that actual collection contains all expected
   * collection elements and then we Wait for defined number of seconds till expected has all
   * elements from actual.
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param <C>                    type of collection elements
   * @return return actual value or null if the timeout reached
   */
  public static <C> boolean waitEquals(
      Iterable<C> actual,
      Iterable<C> expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitEquals(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual collection is empty.
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param <C>                    type of collection elements
   * @return return actual value or null if the timeout reached
   */
  public static <C> boolean waitIsEmpty(
      Iterable<C> actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsEmpty(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual collection is not empty. (might contains null
   * values)
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param <C>                    type of collection elements
   * @return return actual value or null if the timeout reached
   */
  public static <C> boolean waitIsNotEmpty(
      Iterable<C> actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsNotEmpty(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual collection does not contain the expected
   * element.
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param <C>                    type of collection elements
   * @return return actual value or null if the timeout reached
   */
  public static <C> boolean waitNotContains(
      Iterable<C> actual, C expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitNotContains(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual collection does not contain all elements from
   * the expected collection. Please note that actual collection might have some elements but the
   * point is to ensure that not all expected elements are exist in it.
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param <C>                    type of collection elements
   * @return return actual value or null if the timeout reached
   */
  public static <C> boolean waitNotContainsAll(
      Iterable<C> actual,
      Iterable<C> expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitNotContainsAll(expected, waitInSeconds, intervalInMilliSeconds);
  }

  private static <C> CIterableWaiter toWaiter(Iterable<C> actual) {
    return () -> actual;
  }
}
