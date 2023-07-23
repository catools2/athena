package org.catools.common.extensions.verify.interfaces.waitVerifier;

import org.catools.common.collections.CHashMap;
import org.catools.common.extensions.states.interfaces.CNumberState;
import org.catools.common.extensions.verify.CVerificationQueue;
import org.catools.common.extensions.verify.interfaces.verifier.CNumberVerifier;

/**
 * CBooleanVerifier is an interface for Boolean verification related methods.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extend cross all other objects:
 */
public interface CNumberWaitVerifier<N extends Number & Comparable<N>> extends CNumberVerifier<N>, CObjectWaitVerifier<N, CNumberState<N>> {

  /**
   * Verify that actual value is between lower and higher bound values (exclusive).
   *
   * @param verifier      verification _verify builder for verification
   * @param lowerBound    lower bound inclusive
   * @param higherBound   higher bound inclusive
   * @param waitInSeconds maximum wait time
   */
  default void verifyBetweenExclusive(final CVerificationQueue verifier, final N lowerBound, final N higherBound, final int waitInSeconds) {
    verifyBetweenExclusive(verifier, lowerBound, higherBound, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual value is between lower and higher bound values (exclusive).
   *
   * @param verifier      verification _verify builder for verification
   * @param lowerBound    lower bound inclusive
   * @param higherBound   higher bound inclusive
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyBetweenExclusive(final CVerificationQueue verifier, final N lowerBound, final N higherBound, final int waitInSeconds, final String message, final Object... params) {
    verifyBetweenExclusive(verifier, lowerBound, higherBound, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual value is between lower and higher bound values (exclusive).
   *
   * @param verifier               verification _verify builder for verification
   * @param lowerBound             lower bound inclusive
   * @param higherBound            higher bound inclusive
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyBetweenExclusive(final CVerificationQueue verifier, final N lowerBound, final N higherBound, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyBetweenExclusive(verifier, lowerBound, higherBound, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Between %s And %s (Exclusive)", lowerBound, higherBound));
  }

  /**
   * Verify that actual value is between lower and higher bound values (exclusive).
   *
   * @param verifier               verification _verify builder for verification
   * @param lowerBound             lower bound inclusive
   * @param higherBound            higher bound inclusive
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyBetweenExclusive(final CVerificationQueue verifier, final N lowerBound, final N higherBound, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    CHashMap<String, N> map = new CHashMap<>();
    map.put("Lower Bound", lowerBound);
    map.put("Higher Bound", higherBound);
    _verify(verifier, map, (o, o2) -> _toState(o).betweenExclusive(lowerBound, higherBound), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual value is between lower and higher bound values (Inclusive).
   *
   * @param verifier      verification _verify builder for verification
   * @param lowerBound    lower bound inclusive
   * @param higherBound   higher bound inclusive
   * @param waitInSeconds maximum wait time
   */
  default void verifyBetweenInclusive(final CVerificationQueue verifier, final N lowerBound, final N higherBound, final int waitInSeconds) {
    verifyBetweenInclusive(verifier, lowerBound, higherBound, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual value is between lower and higher bound values (Inclusive).
   *
   * @param verifier      verification _verify builder for verification
   * @param lowerBound    lower bound inclusive
   * @param higherBound   higher bound inclusive
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyBetweenInclusive(final CVerificationQueue verifier, final N lowerBound, final N higherBound, final int waitInSeconds, final String message, final Object... params) {
    verifyBetweenInclusive(verifier, lowerBound, higherBound, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual value is between lower and higher bound values (Inclusive).
   *
   * @param verifier               verification _verify builder for verification
   * @param lowerBound             lower bound inclusive
   * @param higherBound            higher bound inclusive
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyBetweenInclusive(final CVerificationQueue verifier, final N lowerBound, final N higherBound, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyBetweenInclusive(verifier, lowerBound, higherBound, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Between %s And %s (Inclusive)", lowerBound, higherBound));
  }

  /**
   * Verify that actual value is between lower and higher bound values (Inclusive).
   *
   * @param verifier               verification _verify builder for verification
   * @param lowerBound             lower bound inclusive
   * @param higherBound            higher bound inclusive
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyBetweenInclusive(final CVerificationQueue verifier, final N lowerBound, final N higherBound, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    CHashMap<String, N> map = new CHashMap<>();
    map.put("Lower Bound", lowerBound);
    map.put("Higher Bound", higherBound);
    _verify(verifier, map, (o, o2) -> _toState(o).betweenInclusive(lowerBound, higherBound), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual and expected have the exact same value or their difference is less than
   * precision value.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param verifier      verification _verify builder for verification
   * @param expected      value to compare
   * @param precision     the acceptable precision
   * @param waitInSeconds maximum wait time
   */
  default void verifyEqualsP(final CVerificationQueue verifier, final N expected, final N precision, final int waitInSeconds) {
    verifyEqualsP(verifier, expected, precision, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual and expected have the exact same value or their difference is less than
   * precision value.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param verifier      verification _verify builder for verification
   * @param expected      value to compare
   * @param precision     the acceptable precision
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsP(final CVerificationQueue verifier, final N expected, final N precision, final int waitInSeconds, final String message, final Object... params) {
    verifyEqualsP(verifier, expected, precision, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual and expected have the exact same value or their difference is less than
   * precision value.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param verifier               verification _verify builder for verification
   * @param expected               value to compare
   * @param precision              the acceptable precision
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEqualsP(final CVerificationQueue verifier, final N expected, final N precision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEqualsP(verifier, expected, precision, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Equal To The Expected Value (with precision %s)", precision));
  }

  /**
   * Verify that actual and expected have the exact same value or their difference is less than
   * precision value.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param verifier               verification _verify builder for verification
   * @param expected               value to compare
   * @param precision              the acceptable precision
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsP(final CVerificationQueue verifier, final N expected, final N precision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).isEqual(expected, precision), waitInSeconds, intervalInMilliSeconds, "[precision: " + precision + "]" + message, params);
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param verifier      verification _verify builder for verification
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyGreater(final CVerificationQueue verifier, final N expected, final int waitInSeconds) {
    verifyGreater(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param verifier      verification _verify builder for verification
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyGreater(final CVerificationQueue verifier, final N expected, final int waitInSeconds, final String message, final Object... params) {
    verifyGreater(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param verifier               verification _verify builder for verification
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyGreater(final CVerificationQueue verifier, final N expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyGreater(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Greater Than The Expected Value"));
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param verifier               verification _verify builder for verification
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyGreater(final CVerificationQueue verifier, final N expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).greater(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual has value greater or equal to expected.
   *
   * @param verifier      verification _verify builder for verification
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyGreaterOrEqual(final CVerificationQueue verifier, final N expected, final int waitInSeconds) {
    verifyGreaterOrEqual(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual has value greater or equal to expected.
   *
   * @param verifier      verification _verify builder for verification
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyGreaterOrEqual(final CVerificationQueue verifier, final N expected, final int waitInSeconds, final String message, final Object... params) {
    verifyGreaterOrEqual(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual has value greater or equal to expected.
   *
   * @param verifier               verification _verify builder for verification
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyGreaterOrEqual(final CVerificationQueue verifier, final N expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyGreaterOrEqual(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Greater Than Or Equal To The Expected Value"));
  }

  /**
   * Verify that actual has value greater or equal to expected.
   *
   * @param verifier               verification _verify builder for verification
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyGreaterOrEqual(final CVerificationQueue verifier, final N expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).greaterOrEqual(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param verifier      verification _verify builder for verification
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyLess(final CVerificationQueue verifier, final N expected, final int waitInSeconds) {
    verifyLess(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param verifier      verification _verify builder for verification
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyLess(final CVerificationQueue verifier, final N expected, final int waitInSeconds, final String message, final Object... params) {
    verifyLess(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param verifier               verification _verify builder for verification
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyLess(final CVerificationQueue verifier, final N expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyLess(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Less Than The Expected Value"));
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param verifier               verification _verify builder for verification
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyLess(final CVerificationQueue verifier, final N expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).less(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual has value less or equal than expected.
   *
   * @param verifier      verification _verify builder for verification
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyLessOrEqual(final CVerificationQueue verifier, final N expected, final int waitInSeconds) {
    verifyLessOrEqual(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual has value less or equal than expected.
   *
   * @param verifier      verification _verify builder for verification
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyLessOrEqual(final CVerificationQueue verifier, final N expected, final int waitInSeconds, final String message, final Object... params) {
    verifyLessOrEqual(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual has value less or equal than expected.
   *
   * @param verifier               verification _verify builder for verification
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyLessOrEqual(final CVerificationQueue verifier, final N expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyLessOrEqual(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Less Than Or Equal To The Expected Value"));
  }

  /**
   * Verify that actual has value less or equal than expected.
   *
   * @param verifier               verification _verify builder for verification
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyLessOrEqual(final CVerificationQueue verifier, final N expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).lessOrEqual(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Exclusive).
   *
   * @param verifier      verification _verify builder for verification
   * @param lowerBound    lower bound inclusive
   * @param higherBound   higher bound inclusive
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotBetweenExclusive(final CVerificationQueue verifier, final N lowerBound, final N higherBound, final int waitInSeconds) {
    verifyNotBetweenExclusive(verifier, lowerBound, higherBound, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Exclusive).
   *
   * @param verifier      verification _verify builder for verification
   * @param lowerBound    lower bound inclusive
   * @param higherBound   higher bound inclusive
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotBetweenExclusive(final CVerificationQueue verifier, final N lowerBound, final N higherBound, final int waitInSeconds, final String message, final Object... params) {
    verifyNotBetweenExclusive(verifier, lowerBound, higherBound, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Exclusive).
   *
   * @param verifier               verification _verify builder for verification
   * @param lowerBound             lower bound inclusive
   * @param higherBound            higher bound inclusive
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotBetweenExclusive(final CVerificationQueue verifier, final N lowerBound, final N higherBound, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotBetweenExclusive(verifier, lowerBound, higherBound, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Not Is Between %s And %s (Exclusive)", lowerBound, higherBound));
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Exclusive).
   *
   * @param verifier               verification _verify builder for verification
   * @param lowerBound             lower bound inclusive
   * @param higherBound            higher bound inclusive
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotBetweenExclusive(final CVerificationQueue verifier, final N lowerBound, final N higherBound, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    CHashMap<String, N> map = new CHashMap<>();
    map.put("Lower Bound", lowerBound);
    map.put("Higher Bound", higherBound);
    _verify(verifier, map, (o, o2) -> _toState(o).notBetweenExclusive(lowerBound, higherBound), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Inclusive).
   *
   * @param verifier      verification _verify builder for verification
   * @param lowerBound    lower bound inclusive
   * @param higherBound   higher bound inclusive
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotBetweenInclusive(final CVerificationQueue verifier, final N lowerBound, final N higherBound, final int waitInSeconds) {
    verifyNotBetweenInclusive(verifier, lowerBound, higherBound, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Inclusive).
   *
   * @param verifier      verification _verify builder for verification
   * @param lowerBound    lower bound inclusive
   * @param higherBound   higher bound inclusive
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotBetweenInclusive(final CVerificationQueue verifier, final N lowerBound, final N higherBound, final int waitInSeconds, final String message, final Object... params) {
    verifyNotBetweenInclusive(verifier, lowerBound, higherBound, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Inclusive).
   *
   * @param verifier               verification _verify builder for verification
   * @param lowerBound             lower bound inclusive
   * @param higherBound            higher bound inclusive
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotBetweenInclusive(final CVerificationQueue verifier, final N lowerBound, final N higherBound, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotBetweenInclusive(verifier, lowerBound, higherBound, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Not Is Between %s And %s (Inclusive)", lowerBound, higherBound));
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Inclusive).
   *
   * @param verifier               verification _verify builder for verification
   * @param lowerBound             lower bound inclusive
   * @param higherBound            higher bound inclusive
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotBetweenInclusive(final CVerificationQueue verifier, final N lowerBound, final N higherBound, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    CHashMap<String, N> map = new CHashMap<>();
    map.put("Lower Bound", lowerBound);
    map.put("Higher Bound", higherBound);
    _verify(verifier, map, (o, o2) -> _toState(o).notBetweenInclusive(lowerBound, higherBound), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual and expected have different value greater than precision value.
   *
   * <p>Please note that verification consider as passe if one value is null
   *
   * @param verifier      verification _verify builder for verification
   * @param expected      value to compare
   * @param precision     the acceptable precision
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotEqualsP(final CVerificationQueue verifier, final N expected, final N precision, final int waitInSeconds) {
    verifyNotEqualsP(verifier, expected, precision, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual and expected have different value greater than precision value.
   *
   * <p>Please note that verification consider as passe if one value is null
   *
   * @param verifier      verification _verify builder for verification
   * @param expected      value to compare
   * @param precision     the acceptable precision
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsP(final CVerificationQueue verifier, final N expected, final N precision, final int waitInSeconds, final String message, final Object... params) {
    verifyNotEqualsP(verifier, expected, precision, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual and expected have different value greater than precision value.
   *
   * <p>Please note that verification consider as passe if one value is null
   *
   * @param verifier               verification _verify builder for verification
   * @param expected               value to compare
   * @param precision              the acceptable precision
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotEqualsP(final CVerificationQueue verifier, final N expected, final N precision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotEqualsP(verifier, expected, precision, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Not Equal To The Expected Value (with precision %s)", precision));
  }

  /**
   * Verify that actual and expected have different value greater than precision value.
   *
   * <p>Please note that verification consider as passe if one value is null
   *
   * @param verifier               verification _verify builder for verification
   * @param expected               value to compare
   * @param precision              the acceptable precision
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsP(final CVerificationQueue verifier, final N expected, final N precision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).notEquals(o2, precision), waitInSeconds, intervalInMilliSeconds, "[precision: " + precision + "]" + message, params);
  }
}
