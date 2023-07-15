package org.catools.common.extensions.wait;

import org.catools.common.extensions.wait.interfaces.CNumberWaiter;

/**
 * Number wait class contains all wait method which is related to Number
 * (Int,Double,Long,Float,BigDecimal)
 */
public class CNumberWait extends CObjectWait {
  /**
   * Wait for defined number of seconds till actual value is between lower and higher bound values
   * (exclusive).
   *
   * @param actual                 value to compare
   * @param lowerBound             lower bound inclusive
   * @param higherBound            higher bound inclusive
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static <N extends Number & Comparable<N>> boolean waitBetweenExclusive(
      final N actual,
      final N lowerBound,
      final N higherBound,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitBetweenExclusive(lowerBound, higherBound, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual value is between lower and higher bound values
   * (Inclusive).
   *
   * @param actual                 value to compare
   * @param lowerBound             lower bound inclusive
   * @param higherBound            higher bound inclusive
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static <N extends Number & Comparable<N>> boolean waitBetweenInclusive(
      final N actual,
      final N lowerBound,
      final N higherBound,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitBetweenInclusive(lowerBound, higherBound, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected have the exact same value.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static <N extends Number & Comparable<N>> boolean waitEquals(
      final N actual, final N expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitEquals(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected have the exact same value or their
   * difference is less than precision value.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param precision              the acceptable precision
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static <N extends Number & Comparable<N>> boolean waitEqualsP(
      final N actual,
      final N expected,
      final N precision,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitEqualsP(expected, precision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual has value greater than expected.
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static <N extends Number & Comparable<N>> boolean waitGreater(
      final N actual, final N expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitGreater(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual has value greater or equal to expected.
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static <N extends Number & Comparable<N>> boolean waitGreaterOrEqual(
      final N actual, final N expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitGreaterOrEqual(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual has value less than expected.
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static <N extends Number & Comparable<N>> boolean waitLess(
      final N actual, final N expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitLess(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual has value less or equal than expected.
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static <N extends Number & Comparable<N>> boolean waitLessOrEqual(
      final N actual, final N expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitLessOrEqual(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual value is NOT between lower and higher bound
   * values (Exclusive).
   *
   * @param actual                 value to compare
   * @param lowerBound             lower bound inclusive
   * @param higherBound            higher bound inclusive
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static <N extends Number & Comparable<N>> boolean waitNotBetweenExclusive(
      final N actual,
      final N lowerBound,
      final N higherBound,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitNotBetweenExclusive(lowerBound, higherBound, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual value is NOT between lower and higher bound
   * values (Inclusive).
   *
   * @param actual                 value to compare
   * @param lowerBound             lower bound inclusive
   * @param higherBound            higher bound inclusive
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static <N extends Number & Comparable<N>> boolean waitNotBetweenInclusive(
      final N actual,
      final N lowerBound,
      final N higherBound,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitNotBetweenInclusive(lowerBound, higherBound, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected have different values.
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static <N extends Number & Comparable<N>> boolean waitNotEquals(
      final N actual, final N expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitNotEquals(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected have different value greater than
   * precision value.
   *
   * <p>Please note that verification consider as passe if one value is null
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param precision              the acceptable precision
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static <N extends Number & Comparable<N>> boolean waitNotEqualsP(
      final N actual,
      final N expected,
      final N precision,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitNotEqualsP(expected, precision, waitInSeconds, intervalInMilliSeconds);
  }

  private static CNumberWaiter toWaiter(Number actual) {
    return () -> actual;
  }
}
