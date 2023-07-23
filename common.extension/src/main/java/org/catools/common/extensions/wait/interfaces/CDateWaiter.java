package org.catools.common.extensions.wait.interfaces;

import org.catools.common.extensions.states.interfaces.CDateState;

import java.util.Date;

/**
 * CDateWaiter is an interface for CDate waiter related methods.
 */
public interface CDateWaiter extends CObjectWaiter<Date> {

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual and expected have the exact same string value after they converted using the
   * provided date format. Means that verification of "2019-08-09 12:20" and "2019-08-09 11:20"
   * using "yyyy-MM-dd" passes.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected value to compare
   * @param format   date format to be use
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsByFormat(final Date expected, final String format) {
    return waitEqualsByFormat(expected, format, getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual and expected have the exact same string
   * value after they converted using the provided date format. Means that verification of
   * "2019-08-09 12:20" and "2019-08-09 11:20" using "yyyy-MM-dd" passes.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected      value to compare
   * @param format        date format to be use
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsByFormat(final Date expected, final String format, final int waitInSeconds) {
    return waitEqualsByFormat(expected, format, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual and expected have the exact same string
   * value after they converted using the provided date format. Means that verification of
   * "2019-08-09 12:20" and "2019-08-09 11:20" using "yyyy-MM-dd" passes.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected               value to compare
   * @param format                 date format to be use
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsByFormat(final Date expected, final String format, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).equalsByFormat(expected, format), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual and expected have same string value after they converted using "yyyy-MM-dd" for
   * format. Means that verification of "2019-08-09 12:20" and "2019-08-09 11:20" passes
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsDatePortion(final Date expected) {
    return waitEqualsDatePortion(expected, getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual and expected have same string value after
   * they converted using "yyyy-MM-dd" for format. Means that verification of "2019-08-09 12:20" and
   * "2019-08-09 11:20" passes
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsDatePortion(final Date expected, final int waitInSeconds) {
    return waitEqualsDatePortion(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual and expected have same string value after
   * they converted using "yyyy-MM-dd" for format. Means that verification of "2019-08-09 12:20" and
   * "2019-08-09 11:20" passes
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsDatePortion(final Date expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).equalsDatePortion(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual and expected have same string value after they converted using "HH:mm:ss" for
   * format. Means that verification of "2019-08-09 12:20" and "2019-08-08 12:20" passes
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsTimePortion(final Date expected) {
    return waitEqualsTimePortion(expected, getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual and expected have same string value after
   * they converted using "HH:mm:ss" for format. Means that verification of "2019-08-09 12:20" and
   * "2019-08-08 12:20" passes
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsTimePortion(final Date expected, final int waitInSeconds) {
    return waitEqualsTimePortion(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual and expected have same string value after
   * they converted using "HH:mm:ss" for format. Means that verification of "2019-08-09 12:20" and
   * "2019-08-08 12:20" passes
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsTimePortion(final Date expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).equalsTimePortion(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual and expected have different string value after they converted using the provided
   * date format. Means that verification of "2019-08-09 12:20" and "2019-08-09 11:20" using
   * "yyyy-MM-dd HH" passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param expected value to compare
   * @param format   date format to be use
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEqualsByFormat(final Date expected, final String format) {
    return waitNotEqualsByFormat(expected, format, getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual and expected have different string value
   * after they converted using the provided date format. Means that verification of "2019-08-09
   * 12:20" and "2019-08-09 11:20" using "yyyy-MM-dd HH" passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param expected      value to compare
   * @param format        date format to be use
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEqualsByFormat(final Date expected, final String format, final int waitInSeconds) {
    return waitNotEqualsByFormat(expected, format, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual and expected have different string value
   * after they converted using the provided date format. Means that verification of "2019-08-09
   * 12:20" and "2019-08-09 11:20" using "yyyy-MM-dd HH" passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param expected               value to compare
   * @param format                 date format to be use
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEqualsByFormat(final Date expected, final String format, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).notEqualsByFormat(expected, format), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual and expected have different string value after they converted using "yyyy-MM-dd" for
   * format. Means that verification of "2019-08-09 12:20" and "2019-08-08 12:20" passes (means
   * values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEqualsDatePortion(final Date expected) {
    return waitNotEqualsDatePortion(expected, getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual and expected have different string value
   * after they converted using "yyyy-MM-dd" for format. Means that verification of "2019-08-09
   * 12:20" and "2019-08-08 12:20" passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEqualsDatePortion(final Date expected, final int waitInSeconds) {
    return waitNotEqualsDatePortion(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual and expected have different string value
   * after they converted using "yyyy-MM-dd" for format. Means that verification of "2019-08-09
   * 12:20" and "2019-08-08 12:20" passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEqualsDatePortion(final Date expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).notEqualsDatePortion(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual and expected have different string value after they converted using "HH:mm:ss" for
   * format. Means that verification of "2019-08-09 12:20:31" and "2019-08-09 12:20:30" passes
   * (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEqualsTimePortion(final Date expected) {
    return waitNotEqualsTimePortion(expected, getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual and expected have different string value
   * after they converted using "HH:mm:ss" for format. Means that verification of "2019-08-09
   * 12:20:31" and "2019-08-09 12:20:30" passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEqualsTimePortion(final Date expected, final int waitInSeconds) {
    return waitNotEqualsTimePortion(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual and expected have different string value
   * after they converted using "HH:mm:ss" for format. Means that verification of "2019-08-09
   * 12:20:31" and "2019-08-09 12:20:30" passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEqualsTimePortion(final Date expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).notEqualsTimePortion(expected), waitInSeconds, intervalInMilliSeconds);
  }

  private CDateState toState(Object e) {
    return () -> (Date) e;
  }
}
