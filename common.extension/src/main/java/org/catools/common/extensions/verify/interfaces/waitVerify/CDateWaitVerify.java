package org.catools.common.extensions.verify.interfaces.waitVerify;

import org.catools.common.extensions.states.interfaces.CDateState;
import org.catools.common.extensions.verify.interfaces.base.CDateVerify;

import java.util.Date;

/**
 * CDateVerifier is an interface for Date verification related methods.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 */
public interface CDateWaitVerify extends CDateVerify, CObjectWaitVerify<Date, CDateState> {

  /**
   * Verify that actual and expected have the exact same string value after they converted using the
   * provided date format. Means that verification of "2019-08-09 12:20" and "2019-08-09 11:20"
   * using "yyyy-MM-dd" passes.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected      value to compare
   * @param format        date format to be use
   * @param waitInSeconds maximum wait time
   */
  default void verifyEqualsByFormat(
      final Date expected,
      final String format,
      final int waitInSeconds) {
    verifyEqualsByFormat(
        expected, format, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual and expected have the exact same string value after they converted using the
   * provided date format. Means that verification of "2019-08-09 12:20" and "2019-08-09 11:20"
   * using "yyyy-MM-dd" passes.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected      value to compare
   * @param format        date format to be use
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsByFormat(
      final Date expected,
      final String format,
      final int waitInSeconds,
      final String message,
      final Object... params) {
    verifyEqualsByFormat(
        expected,
        format,
        waitInSeconds,
        getDefaultWaitIntervalInMilliSeconds(),
        message,
        params);
  }

  /**
   * Verify that actual and expected have the exact same string value after they converted using the
   * provided date format. Means that verification of "2019-08-09 12:20" and "2019-08-09 11:20"
   * using "yyyy-MM-dd" passes.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected               value to compare
   * @param format                 date format to be use
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEqualsByFormat(
      final Date expected,
      final String format,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    verifyEqualsByFormat(
        expected,
        format,
        waitInSeconds,
        intervalInMilliSeconds,
        "Equals By Format " + format);
  }

  /**
   * Verify that actual and expected have the exact same string value after they converted using the
   * provided date format. Means that verification of "2019-08-09 12:20" and "2019-08-09 11:20"
   * using "yyyy-MM-dd" passes.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected               value to compare
   * @param format                 date format to be use
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsByFormat(
      final Date expected,
      final String format,
      final int waitInSeconds,
      final int intervalInMilliSeconds,
      final String message,
      final Object... params) {
    _verify(
        expected,
        (o, o2) -> _toState(o).equalsByFormat(o2, format),
        waitInSeconds,
        intervalInMilliSeconds,
        message,
        params);
  }

  /**
   * Verify that actual and expected have same string value after they converted using "yyyy-MM-dd"
   * for format. Means that verification of "2019-08-09 12:20" and "2019-08-09 11:20" passes
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyEqualsDatePortion(final Date expected, final int waitInSeconds) {
    verifyEqualsDatePortion(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual and expected have same string value after they converted using "yyyy-MM-dd"
   * for format. Means that verification of "2019-08-09 12:20" and "2019-08-09 11:20" passes
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsDatePortion(
      final Date expected,
      final int waitInSeconds,
      final String message,
      final Object... params) {
    verifyEqualsDatePortion(
        expected,
        waitInSeconds,
        getDefaultWaitIntervalInMilliSeconds(),
        message,
        params);
  }

  /**
   * Verify that actual and expected have same string value after they converted using "yyyy-MM-dd"
   * for format. Means that verification of "2019-08-09 12:20" and "2019-08-09 11:20" passes
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEqualsDatePortion(
      final Date expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    verifyEqualsDatePortion(
        expected,
        waitInSeconds,
        intervalInMilliSeconds,
        getDefaultMessage("Date Portion Equals"));
  }

  /**
   * Verify that actual and expected have same string value after they converted using "yyyy-MM-dd"
   * for format. Means that verification of "2019-08-09 12:20" and "2019-08-09 11:20" passes
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsDatePortion(
      final Date expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds,
      final String message,
      final Object... params) {
    _verify(
        expected,
        (o, o2) -> _toState(o).equalsDatePortion(o2),
        waitInSeconds,
        intervalInMilliSeconds,
        message,
        params);
  }

  /**
   * Verify that actual and expected have same string value after they converted using "HH:mm:ss"
   * for format. Means that verification of "2019-08-09 12:20" and "2019-08-08 12:20" passes
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyEqualsTimePortion(final Date expected, final int waitInSeconds) {
    verifyEqualsTimePortion(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual and expected have same string value after they converted using "HH:mm:ss"
   * for format. Means that verification of "2019-08-09 12:20" and "2019-08-08 12:20" passes
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsTimePortion(
      final Date expected,
      final int waitInSeconds,
      final String message,
      final Object... params) {
    verifyEqualsTimePortion(
        expected,
        waitInSeconds,
        getDefaultWaitIntervalInMilliSeconds(),
        message,
        params);
  }

  /**
   * Verify that actual and expected have same string value after they converted using "HH:mm:ss"
   * for format. Means that verification of "2019-08-09 12:20" and "2019-08-08 12:20" passes
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEqualsTimePortion(
      final Date expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    verifyEqualsTimePortion(
        expected,
        waitInSeconds,
        intervalInMilliSeconds,
        getDefaultMessage("Time Portion Equals"));
  }

  /**
   * Verify that actual and expected have same string value after they converted using "HH:mm:ss"
   * for format. Means that verification of "2019-08-09 12:20" and "2019-08-08 12:20" passes
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsTimePortion(
      final Date expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds,
      final String message,
      final Object... params) {
    _verify(
        expected,
        (o, o2) -> _toState(o).equalsTimePortion(o2),
        waitInSeconds,
        intervalInMilliSeconds,
        message,
        params);
  }

  /**
   * Verify that actual and expected have different string value after they converted using the
   * provided date format. Means that verification of "2019-08-09 12:20" and "2019-08-09 11:20"
   * using "yyyy-MM-dd HH" passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param expected      value to compare
   * @param format        date format to be use
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotEqualsByFormat(
      final Date expected,
      final String format,
      final int waitInSeconds) {
    verifyNotEqualsByFormat(
        expected, format, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual and expected have different string value after they converted using the
   * provided date format. Means that verification of "2019-08-09 12:20" and "2019-08-09 11:20"
   * using "yyyy-MM-dd HH" passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param expected      value to compare
   * @param format        date format to be use
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsByFormat(
      final Date expected,
      final String format,
      final int waitInSeconds,
      final String message,
      final Object... params) {
    verifyNotEqualsByFormat(
        expected,
        format,
        waitInSeconds,
        getDefaultWaitIntervalInMilliSeconds(),
        message,
        params);
  }

  /**
   * Verify that actual and expected have different string value after they converted using the
   * provided date format. Means that verification of "2019-08-09 12:20" and "2019-08-09 11:20"
   * using "yyyy-MM-dd HH" passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param expected               value to compare
   * @param format                 date format to be use
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotEqualsByFormat(
      final Date expected,
      final String format,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    verifyNotEqualsByFormat(
        expected,
        format,
        waitInSeconds,
        intervalInMilliSeconds,
        "Not Equals By Format " + format);
  }

  /**
   * Verify that actual and expected have different string value after they converted using the
   * provided date format. Means that verification of "2019-08-09 12:20" and "2019-08-09 11:20"
   * using "yyyy-MM-dd HH" passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param expected               value to compare
   * @param format                 date format to be use
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsByFormat(
      final Date expected,
      final String format,
      final int waitInSeconds,
      final int intervalInMilliSeconds,
      final String message,
      final Object... params) {
    _verify(
        expected,
        (o, o2) -> _toState(o).notEqualsByFormat(o2, format),
        waitInSeconds,
        intervalInMilliSeconds,
        message,
        params);
  }

  /**
   * Verify that actual and expected have different string value after they converted using
   * "yyyy-MM-dd" for format. Means that verification of "2019-08-09 12:20" and "2019-08-08 12:20"
   * passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotEqualsDatePortion(final Date expected, final int waitInSeconds) {
    verifyNotEqualsDatePortion(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual and expected have different string value after they converted using
   * "yyyy-MM-dd" for format. Means that verification of "2019-08-09 12:20" and "2019-08-08 12:20"
   * passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsDatePortion(
      final Date expected,
      final int waitInSeconds,
      final String message,
      final Object... params) {
    verifyNotEqualsDatePortion(
        expected,
        waitInSeconds,
        getDefaultWaitIntervalInMilliSeconds(),
        message,
        params);
  }

  /**
   * Verify that actual and expected have different string value after they converted using
   * "yyyy-MM-dd" for format. Means that verification of "2019-08-09 12:20" and "2019-08-08 12:20"
   * passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotEqualsDatePortion(
      final Date expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    verifyNotEqualsDatePortion(
        expected,
        waitInSeconds,
        intervalInMilliSeconds,
        getDefaultMessage("Date Portion Not Equals"));
  }

  /**
   * Verify that actual and expected have different string value after they converted using
   * "yyyy-MM-dd" for format. Means that verification of "2019-08-09 12:20" and "2019-08-08 12:20"
   * passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsDatePortion(
      final Date expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds,
      final String message,
      final Object... params) {
    _verify(
        expected,
        (o, o2) -> _toState(o).notEqualsDatePortion(o2),
        waitInSeconds,
        intervalInMilliSeconds,
        message,
        params);
  }

  /**
   * Verify that actual and expected have different string value after they converted using
   * "HH:mm:ss" for format. Means that verification of "2019-08-09 12:20:31" and "2019-08-09
   * 12:20:30" passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotEqualsTimePortion(final Date expected, final int waitInSeconds) {
    verifyNotEqualsTimePortion(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual and expected have different string value after they converted using
   * "HH:mm:ss" for format. Means that verification of "2019-08-09 12:20:31" and "2019-08-09
   * 12:20:30" passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsTimePortion(
      final Date expected,
      final int waitInSeconds,
      final String message,
      final Object... params) {
    verifyNotEqualsTimePortion(
        expected,
        waitInSeconds,
        getDefaultWaitIntervalInMilliSeconds(),
        message,
        params);
  }

  /**
   * Verify that actual and expected have different string value after they converted using
   * "HH:mm:ss" for format. Means that verification of "2019-08-09 12:20:31" and "2019-08-09
   * 12:20:30" passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotEqualsTimePortion(
      final Date expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    verifyNotEqualsTimePortion(
        expected,
        waitInSeconds,
        intervalInMilliSeconds,
        getDefaultMessage("Time Portion Not Equals"));
  }

  /**
   * Verify that actual and expected have different string value after they converted using
   * "HH:mm:ss" for format. Means that verification of "2019-08-09 12:20:31" and "2019-08-09
   * 12:20:30" passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsTimePortion(
      final Date expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds,
      final String message,
      final Object... params) {
    _verify(
        expected,
        (o, o2) -> _toState(o).notEqualsTimePortion(o2),
        waitInSeconds,
        intervalInMilliSeconds,
        message,
        params);
  }
}
