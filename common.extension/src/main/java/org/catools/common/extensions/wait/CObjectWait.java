package org.catools.common.extensions.wait;

import org.catools.common.extensions.wait.interfaces.CObjectWaiter;

/**
 * Object wait class contains all wait method which is related to Object
 */
public class CObjectWait {
  /**
   * Wait for defined number of seconds till actual and expected value are equal objects.
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static <T> boolean waitEquals(
      final T actual, final T expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitEquals(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected value are not equal objects.
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static <T> boolean waitNotEquals(
      final T actual, final T expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitNotEquals(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual value is NOT null.
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static <T> boolean waitIsNull(
      final T actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsNull(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual value is NOT null.
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static <T> boolean waitIsNotNull(
      final T actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsNotNull(waitInSeconds, intervalInMilliSeconds);
  }

  private static CObjectWaiter toWaiter(Object actual) {
    return () -> actual;
  }
}
