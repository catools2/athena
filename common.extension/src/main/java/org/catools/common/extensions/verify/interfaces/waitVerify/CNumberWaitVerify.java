package org.catools.common.extensions.verify.interfaces.waitVerify;

import org.catools.common.collections.CHashMap;
import org.catools.common.extensions.states.interfaces.CNumberState;
import org.catools.common.extensions.verify.interfaces.base.CNumberVerify;

/**
 * CBooleanVerifier is an interface for Boolean verification related methods.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 */
public interface CNumberWaitVerify<N extends Number & Comparable<N>> extends CNumberVerify<N>, CObjectWaitVerify<N, CNumberState<N>> {

  /**
   * Verify that actual value is between lower and higher bound values (exclusive).
   *
   * @param lowerBound    lower bound inclusive
   * @param higherBound   higher bound inclusive
   * @param waitInSeconds maximum wait time
   */
  default void verifyBetweenExclusive(final N lowerBound, final N higherBound, final int waitInSeconds) {
    verifyBetweenExclusive(lowerBound, higherBound, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual value is between lower and higher bound values (exclusive).
   *
   * @param lowerBound    lower bound inclusive
   * @param higherBound   higher bound inclusive
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyBetweenExclusive(final N lowerBound, final N higherBound, final int waitInSeconds, final String message, final Object... params) {
    verifyBetweenExclusive(lowerBound, higherBound, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual value is between lower and higher bound values (exclusive).
   *
   * @param lowerBound             lower bound inclusive
   * @param higherBound            higher bound inclusive
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyBetweenExclusive(final N lowerBound, final N higherBound, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyBetweenExclusive(lowerBound, higherBound, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Between %s And %s (Exclusive)", lowerBound, higherBound));
  }

  /**
   * Verify that actual value is between lower and higher bound values (exclusive).
   *
   * @param lowerBound             lower bound inclusive
   * @param higherBound            higher bound inclusive
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyBetweenExclusive(final N lowerBound, final N higherBound, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    CHashMap<String, N> map = new CHashMap<>();
    map.put("Lower Bound", lowerBound);
    map.put("Higher Bound", higherBound);
    _verify(map, (o, o2) -> _toState(o).betweenExclusive(lowerBound, higherBound), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual value is between lower and higher bound values (Inclusive).
   *
   * @param lowerBound    lower bound inclusive
   * @param higherBound   higher bound inclusive
   * @param waitInSeconds maximum wait time
   */
  default void verifyBetweenInclusive(final N lowerBound, final N higherBound, final int waitInSeconds) {
    verifyBetweenInclusive(lowerBound, higherBound, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual value is between lower and higher bound values (Inclusive).
   *
   * @param lowerBound    lower bound inclusive
   * @param higherBound   higher bound inclusive
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyBetweenInclusive(final N lowerBound, final N higherBound, final int waitInSeconds, final String message, final Object... params) {
    verifyBetweenInclusive(lowerBound, higherBound, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual value is between lower and higher bound values (Inclusive).
   *
   * @param lowerBound             lower bound inclusive
   * @param higherBound            higher bound inclusive
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyBetweenInclusive(final N lowerBound, final N higherBound, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyBetweenInclusive(lowerBound, higherBound, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Between %s And %s (Inclusive)", lowerBound, higherBound));
  }

  /**
   * Verify that actual value is between lower and higher bound values (Inclusive).
   *
   * @param lowerBound             lower bound inclusive
   * @param higherBound            higher bound inclusive
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyBetweenInclusive(final N lowerBound, final N higherBound, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    CHashMap<String, N> map = new CHashMap<>();
    map.put("Lower Bound", lowerBound);
    map.put("Higher Bound", higherBound);
    _verify(map, (o, o2) -> _toState(o).betweenInclusive(lowerBound, higherBound), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual and expected have the exact same value or their difference is less than
   * precision value.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected      value to compare
   * @param precision     the acceptable precision
   * @param waitInSeconds maximum wait time
   */
  default void verifyEqualsP(final N expected, final N precision, final int waitInSeconds) {
    verifyEqualsP(expected, precision, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual and expected have the exact same value or their difference is less than
   * precision value.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected      value to compare
   * @param precision     the acceptable precision
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsP(final N expected, final N precision, final int waitInSeconds, final String message, final Object... params) {
    verifyEqualsP(expected, precision, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual and expected have the exact same value or their difference is less than
   * precision value.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected               value to compare
   * @param precision              the acceptable precision
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEqualsP(final N expected, final N precision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEqualsP(expected, precision, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Equal To The Expected Value (with precision %s)", precision));
  }

  /**
   * Verify that actual and expected have the exact same value or their difference is less than
   * precision value.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected               value to compare
   * @param precision              the acceptable precision
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsP(final N expected, final N precision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (o, o2) -> _toState(o).isEqual(expected, precision), waitInSeconds, intervalInMilliSeconds, "[precision: " + precision + "]" + message, params);
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyGreater(final N expected, final int waitInSeconds) {
    verifyGreater(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyGreater(final N expected, final int waitInSeconds, final String message, final Object... params) {
    verifyGreater(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyGreater(final N expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyGreater(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Greater Than The Expected Value"));
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyGreater(final N expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (o, o2) -> _toState(o).greater(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual has value greater or equal to expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyGreaterOrEqual(final N expected, final int waitInSeconds) {
    verifyGreaterOrEqual(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual has value greater or equal to expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyGreaterOrEqual(final N expected, final int waitInSeconds, final String message, final Object... params) {
    verifyGreaterOrEqual(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual has value greater or equal to expected.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyGreaterOrEqual(final N expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyGreaterOrEqual(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Greater Than Or Equal To The Expected Value"));
  }

  /**
   * Verify that actual has value greater or equal to expected.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyGreaterOrEqual(final N expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (o, o2) -> _toState(o).greaterOrEqual(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyLess(final N expected, final int waitInSeconds) {
    verifyLess(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyLess(final N expected, final int waitInSeconds, final String message, final Object... params) {
    verifyLess(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyLess(final N expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyLess(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Less Than The Expected Value"));
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyLess(final N expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (o, o2) -> _toState(o).less(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual has value less or equal than expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyLessOrEqual(final N expected, final int waitInSeconds) {
    verifyLessOrEqual(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual has value less or equal than expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyLessOrEqual(final N expected, final int waitInSeconds, final String message, final Object... params) {
    verifyLessOrEqual(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual has value less or equal than expected.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyLessOrEqual(final N expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyLessOrEqual(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Less Than Or Equal To The Expected Value"));
  }

  /**
   * Verify that actual has value less or equal than expected.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyLessOrEqual(final N expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (o, o2) -> _toState(o).lessOrEqual(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Exclusive).
   *
   * @param lowerBound    lower bound inclusive
   * @param higherBound   higher bound inclusive
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotBetweenExclusive(final N lowerBound, final N higherBound, final int waitInSeconds) {
    verifyNotBetweenExclusive(lowerBound, higherBound, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Exclusive).
   *
   * @param lowerBound    lower bound inclusive
   * @param higherBound   higher bound inclusive
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotBetweenExclusive(final N lowerBound, final N higherBound, final int waitInSeconds, final String message, final Object... params) {
    verifyNotBetweenExclusive(lowerBound, higherBound, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Exclusive).
   *
   * @param lowerBound             lower bound inclusive
   * @param higherBound            higher bound inclusive
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotBetweenExclusive(final N lowerBound, final N higherBound, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotBetweenExclusive(lowerBound, higherBound, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Not Is Between %s And %s (Exclusive)", lowerBound, higherBound));
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Exclusive).
   *
   * @param lowerBound             lower bound inclusive
   * @param higherBound            higher bound inclusive
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotBetweenExclusive(final N lowerBound, final N higherBound, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    CHashMap<String, N> map = new CHashMap<>();
    map.put("Lower Bound", lowerBound);
    map.put("Higher Bound", higherBound);
    _verify(map, (o, o2) -> _toState(o).notBetweenExclusive(lowerBound, higherBound), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Inclusive).
   *
   * @param lowerBound    lower bound inclusive
   * @param higherBound   higher bound inclusive
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotBetweenInclusive(final N lowerBound, final N higherBound, final int waitInSeconds) {
    verifyNotBetweenInclusive(lowerBound, higherBound, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Inclusive).
   *
   * @param lowerBound    lower bound inclusive
   * @param higherBound   higher bound inclusive
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotBetweenInclusive(final N lowerBound, final N higherBound, final int waitInSeconds, final String message, final Object... params) {
    verifyNotBetweenInclusive(lowerBound, higherBound, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Inclusive).
   *
   * @param lowerBound             lower bound inclusive
   * @param higherBound            higher bound inclusive
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotBetweenInclusive(final N lowerBound, final N higherBound, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotBetweenInclusive(lowerBound, higherBound, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Not Is Between %s And %s (Inclusive)", lowerBound, higherBound));
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Inclusive).
   *
   * @param lowerBound             lower bound inclusive
   * @param higherBound            higher bound inclusive
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotBetweenInclusive(final N lowerBound, final N higherBound, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    CHashMap<String, N> map = new CHashMap<>();
    map.put("Lower Bound", lowerBound);
    map.put("Higher Bound", higherBound);
    _verify(map, (o, o2) -> _toState(o).notBetweenInclusive(lowerBound, higherBound), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual and expected have different value greater than precision value.
   *
   * <p>Please note that verification consider as passe if one value is null
   *
   * @param expected      value to compare
   * @param precision     the acceptable precision
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotEqualsP(final N expected, final N precision, final int waitInSeconds) {
    verifyNotEqualsP(expected, precision, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual and expected have different value greater than precision value.
   *
   * <p>Please note that verification consider as passe if one value is null
   *
   * @param expected      value to compare
   * @param precision     the acceptable precision
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsP(final N expected, final N precision, final int waitInSeconds, final String message, final Object... params) {
    verifyNotEqualsP(expected, precision, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual and expected have different value greater than precision value.
   *
   * <p>Please note that verification consider as passe if one value is null
   *
   * @param expected               value to compare
   * @param precision              the acceptable precision
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotEqualsP(final N expected, final N precision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotEqualsP(expected, precision, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Not Equal To The Expected Value (with precision %s)", precision));
  }

  /**
   * Verify that actual and expected have different value greater than precision value.
   *
   * <p>Please note that verification consider as passe if one value is null
   *
   * @param expected               value to compare
   * @param precision              the acceptable precision
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsP(final N expected, final N precision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (o, o2) -> _toState(o).notEquals(o2, precision), waitInSeconds, intervalInMilliSeconds, "[precision: " + precision + "]" + message, params);
  }
}
