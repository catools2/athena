package org.catools.common.extensions.wait;

import org.catools.common.extensions.wait.interfaces.CBooleanWaiter;

/**
 * Boolean wait class contains all wait method which is related to Boolean
 */
public class CBooleanWait extends CObjectWait {
  /**
   * Wait for defined number of seconds till actual value is true
   *
   * @param actual value to compare
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsTrue(Boolean actual) {
    return toWaiter(actual).waitIsTrue();
  }

  /**
   * Wait for defined number of seconds till actual value is true
   *
   * @param actual        value to compare
   * @param waitInSeconds maximum wait time
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsTrue(Boolean actual, final int waitInSeconds) {
    return toWaiter(actual).waitIsTrue(waitInSeconds);
  }

  /**
   * Wait for defined number of seconds till actual value is true
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsTrue(
      Boolean actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsTrue(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual value is false
   *
   * @param actual value to compare
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsFalse(Boolean actual) {
    return toWaiter(actual).waitIsFalse();
  }

  /**
   * Wait for defined number of seconds till actual value is false
   *
   * @param actual        value to compare
   * @param waitInSeconds maximum wait time
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsFalse(Boolean actual, final int waitInSeconds) {
    return toWaiter(actual).waitIsFalse(waitInSeconds);
  }

  /**
   * Wait for defined number of seconds till actual value is false
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsFalse(
      Boolean actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsFalse(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected have same boolean value or be null
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEquals(final Boolean actual, final Boolean expected) {
    return toWaiter(actual).waitEquals(expected);
  }

  /**
   * Wait for defined number of seconds till actual and expected have same boolean value or be null
   *
   * @param actual        value to compare
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEquals(
      final Boolean actual, final Boolean expected, final int waitInSeconds) {
    return toWaiter(actual).waitEquals(expected, waitInSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected have same boolean value or be null
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEquals(
      final Boolean actual,
      final Boolean expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitEquals(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected has different boolean value
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotEquals(final Boolean actual, final Boolean expected) {
    return toWaiter(actual).waitNotEquals(expected);
  }

  /**
   * Wait for defined number of seconds till actual and expected has different boolean value
   *
   * @param actual        value to compare
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotEquals(
      final Boolean actual, final Boolean expected, final int waitInSeconds) {
    return toWaiter(actual).waitNotEquals(expected, waitInSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected has different boolean value
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotEquals(
      final Boolean actual,
      final Boolean expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitNotEquals(expected, waitInSeconds, intervalInMilliSeconds);
  }

  private static CBooleanWaiter toWaiter(Boolean actual) {
    return () -> actual;
  }
}
