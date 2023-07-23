package org.catools.common.extensions.verify.interfaces.verifier;

import org.catools.common.collections.CHashMap;
import org.catools.common.extensions.base.CBaseNumberExtension;
import org.catools.common.extensions.states.interfaces.CNumberState;
import org.catools.common.extensions.verify.CVerificationQueue;
import org.catools.common.extensions.verify.hard.CNumberVerification;

/**
 * CBooleanVerifier is an interface for Boolean verification related methods.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 *
 * <p>Please Note that we should extend manually {@link
 * CNumberVerification} for each new added verification here
 */
public interface CNumberVerifier<N extends Number & Comparable<N>> extends CBaseNumberExtension<N>, CObjectVerifier<N, CNumberState<N>> {

  /**
   * Verify that actual value is between lower and higher bound values (exclusive).
   *
   * @param verifier    verification _verify builder for verification
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   */
  default void verifyBetweenExclusive(final CVerificationQueue verifier, final N lowerBound, final N higherBound) {
    verifyBetweenExclusive(verifier, lowerBound, higherBound, getDefaultMessage("Is Between %s And %s (Exclusive)", lowerBound, higherBound));
  }

  /**
   * Verify that actual value is between lower and higher bound values (exclusive).
   *
   * @param verifier    verification _verify builder for verification
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   * @param message     information about the purpose of this verification
   * @param params      parameters in case if message is a format {@link String#format}
   */
  default void verifyBetweenExclusive(final CVerificationQueue verifier, final N lowerBound, final N higherBound, final String message, final Object... params) {
    CHashMap<String, N> map = new CHashMap<>();
    map.put("Lower Bound", lowerBound);
    map.put("Higher Bound", higherBound);
    _verify(verifier, map, (o, o2) -> _toState(o).betweenExclusive(lowerBound, higherBound), message, params);
  }

  /**
   * Verify that actual value is between lower and higher bound values (Inclusive).
   *
   * @param verifier    verification _verify builder for verification
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   */
  default void verifyBetweenInclusive(final CVerificationQueue verifier, final N lowerBound, final N higherBound) {
    verifyBetweenInclusive(verifier, lowerBound, higherBound, getDefaultMessage("Is Between %s And %s (Inclusive)", lowerBound, higherBound));
  }

  /**
   * Verify that actual value is between lower and higher bound values (Inclusive).
   *
   * @param verifier    verification _verify builder for verification
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   * @param message     information about the purpose of this verification
   * @param params      parameters in case if message is a format {@link String#format}
   */
  default void verifyBetweenInclusive(final CVerificationQueue verifier, final N lowerBound, final N higherBound, final String message, final Object... params) {
    CHashMap<String, N> map = new CHashMap<>();
    map.put("Lower Bound", lowerBound);
    map.put("Higher Bound", higherBound);
    _verify(verifier, map, (o, o2) -> _toState(o).betweenInclusive(lowerBound, higherBound), message, params);
  }

  /**
   * Verify that actual and expected have the exact same value or their difference is less than
   * precision value.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param verifier  verification _verify builder for verification
   * @param expected  value to compare
   * @param precision the acceptable precision
   */
  default void verifyEqualsP(final CVerificationQueue verifier, final N expected, final N precision) {
    verifyEqualsP(verifier, expected, precision, getDefaultMessage("Is Equal To The Expected Value"));
  }

  /**
   * Verify that actual and expected have the exact same value or their difference is less than
   * precision value.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param verifier  verification _verify builder for verification
   * @param expected  value to compare
   * @param precision the acceptable precision
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsP(final CVerificationQueue verifier, final N expected, final N precision, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).isEqual(expected, precision), "[precision: " + precision + "]" + message, params);
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param verifier verification _verify builder for verification
   * @param expected value to compare
   */
  default void verifyGreater(final CVerificationQueue verifier, final N expected) {
    verifyGreater(verifier, expected, getDefaultMessage("Is Greater Than The Expected Value"));
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param verifier verification _verify builder for verification
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyGreater(final CVerificationQueue verifier, final N expected, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).greater(o2), message, params);
  }

  /**
   * Verify that actual has value greater or equal to expected.
   *
   * @param verifier verification _verify builder for verification
   * @param expected value to compare
   */
  default void verifyGreaterOrEqual(final CVerificationQueue verifier, final N expected) {
    verifyGreaterOrEqual(verifier, expected, getDefaultMessage("Is Greater Than Or Equal To The Expected Value"));
  }

  /**
   * Verify that actual has value greater or equal to expected.
   *
   * @param verifier verification _verify builder for verification
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyGreaterOrEqual(final CVerificationQueue verifier, final N expected, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).greaterOrEqual(o2), message, params);
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param verifier verification _verify builder for verification
   * @param expected value to compare
   */
  default void verifyLess(final CVerificationQueue verifier, final N expected) {
    verifyLess(verifier, expected, getDefaultMessage("Is Less Than The Expected Value"));
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param verifier verification _verify builder for verification
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyLess(final CVerificationQueue verifier, final N expected, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).less(o2), message, params);
  }

  /**
   * Verify that actual has value less or equal than expected.
   *
   * @param verifier verification _verify builder for verification
   * @param expected value to compare
   */
  default void verifyLessOrEqual(final CVerificationQueue verifier, final N expected) {
    verifyLessOrEqual(verifier, expected, getDefaultMessage("Is Less Than Or Equal To The Expected Value"));
  }

  /**
   * Verify that actual has value less or equal than expected.
   *
   * @param verifier verification _verify builder for verification
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyLessOrEqual(final CVerificationQueue verifier, final N expected, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).lessOrEqual(o2), message, params);
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Exclusive).
   *
   * @param verifier    verification _verify builder for verification
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   */
  default void verifyNotBetweenExclusive(final CVerificationQueue verifier, final N lowerBound, final N higherBound) {
    verifyNotBetweenExclusive(verifier, lowerBound, higherBound, String.format("Not Is Between %s And %s (Exclusive)", lowerBound, higherBound));
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Exclusive).
   *
   * @param verifier    verification _verify builder for verification
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   * @param message     information about the purpose of this verification
   * @param params      parameters in case if message is a format {@link String#format}
   */
  default void verifyNotBetweenExclusive(final CVerificationQueue verifier, final N lowerBound, final N higherBound, final String message, final Object... params) {
    CHashMap<String, N> map = new CHashMap<>();
    map.put("Lower Bound", lowerBound);
    map.put("Higher Bound", higherBound);
    _verify(verifier, map, (o, o2) -> _toState(o).notBetweenExclusive(lowerBound, higherBound), message, params);
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Inclusive).
   *
   * @param verifier    verification _verify builder for verification
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   */
  default void verifyNotBetweenInclusive(final CVerificationQueue verifier, final N lowerBound, final N higherBound) {
    verifyNotBetweenInclusive(verifier, lowerBound, higherBound, String.format("Not Is Between %s And %s (Inclusive)", lowerBound, higherBound));
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Inclusive).
   *
   * @param verifier    verification _verify builder for verification
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   * @param message     information about the purpose of this verification
   * @param params      parameters in case if message is a format {@link String#format}
   */
  default void verifyNotBetweenInclusive(final CVerificationQueue verifier, final N lowerBound, final N higherBound, final String message, final Object... params) {
    CHashMap<String, N> map = new CHashMap<>();
    map.put("Lower Bound", lowerBound);
    map.put("Higher Bound", higherBound);
    _verify(verifier, map, (o, o2) -> _toState(o).notBetweenInclusive(lowerBound, higherBound), message, params);
  }

  /**
   * Verify that actual and expected have different value greater than precision value.
   *
   * <p>Please note that verification consider as passe if one value is null
   *
   * @param verifier  verification _verify builder for verification
   * @param expected  value to compare
   * @param precision the acceptable precision
   */
  default void verifyNotEqualsP(final CVerificationQueue verifier, final N expected, final N precision) {
    verifyNotEqualsP(verifier, expected, precision, "Is Not Equal To The Expected Value");
  }

  /**
   * Verify that actual and expected have different value greater than precision value.
   *
   * <p>Please note that verification consider as passe if one value is null
   *
   * @param verifier  verification _verify builder for verification
   * @param expected  value to compare
   * @param precision the acceptable precision
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsP(final CVerificationQueue verifier, final N expected, final N precision, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).notEquals(o2, precision), "[precision: " + precision + "]" + message, params);
  }
}
