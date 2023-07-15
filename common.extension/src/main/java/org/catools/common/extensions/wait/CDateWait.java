package org.catools.common.extensions.wait;

import org.catools.common.date.CDate;
import org.catools.common.extensions.wait.interfaces.CDateWaiter;

import java.util.Date;

/**
 * Date wait class contains all wait method which is related to Date
 */
public class CDateWait extends CObjectWait {

  /**
   * Wait for defined number of seconds till actual and expected have the exact same date value
   * (compare to milliseconds) or they both has null value
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEquals(
      final Date actual,
      final Date expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitEquals(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected have the exact same string value
   * after they converted using the provided date format. Means that verification of "2019-08-09
   * 12:20" and "2019-08-09 11:20" using "yyyy-MM-dd" passes.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param format                 date format to be use
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEqualsByFormat(
      final Date actual,
      final Date expected,
      final String format,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitEqualsByFormat(expected, format, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected have same string value after they
   * converted using "yyyy-MM-dd" for format. Means that verification of "2019-08-09 12:20" and
   * "2019-08-09 11:20" passes
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEqualsDatePortion(
      final Date actual,
      final Date expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitEqualsDatePortion(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected have same string value after they
   * converted using "HH:mm:ss" for format. Means that verification of "2019-08-09 12:20" and
   * "2019-08-08 12:20" passes
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEqualsTimePortion(
      final Date actual,
      final Date expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitEqualsTimePortion(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected does not have the exact same date
   * value (compare to milliseconds) or one of them is null
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotEquals(
      final Date actual,
      final Date expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitNotEquals(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected have different string value after
   * they converted using the provided date format. Means that verification of "2019-08-09 12:20"
   * and "2019-08-09 11:20" using "yyyy-MM-dd HH" passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param format                 date format to be use
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotEqualsByFormat(
      final Date actual,
      final Date expected,
      final String format,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitNotEqualsByFormat(expected, format, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected have different string value after
   * they converted using "yyyy-MM-dd" for format. Means that verification of "2019-08-09 12:20" and
   * "2019-08-08 12:20" passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotEqualsDatePortion(
      final Date actual,
      final Date expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitNotEqualsDatePortion(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected have different string value after
   * they converted using "HH:mm:ss" for format. Means that verification of "2019-08-09 12:20:31"
   * and "2019-08-09 12:20:30" passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotEqualsTimePortion(
      final Date actual,
      final Date expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitNotEqualsTimePortion(expected, waitInSeconds, intervalInMilliSeconds);
  }

  private static CDateWaiter toWaiter(Date actual) {
    return () -> actual == null ? null : new CDate(actual);
  }
}
