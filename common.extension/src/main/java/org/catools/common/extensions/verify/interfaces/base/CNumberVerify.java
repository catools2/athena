package org.catools.common.extensions.verify.interfaces.base;

import org.catools.common.collections.CHashMap;
import org.catools.common.extensions.base.CBaseNumberExtension;
import org.catools.common.extensions.states.interfaces.CNumberState;
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
public interface CNumberVerify<N extends Number & Comparable<N>> extends CBaseNumberExtension<N>, CObjectVerify<N, CNumberState<N>> {

  /**
   * Verify that actual value is between lower and higher bound values (exclusive).
   *
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   */
  default void verifyBetweenExclusive(final N lowerBound, final N higherBound) {
    verifyBetweenExclusive(
        lowerBound,
        higherBound,
        getDefaultMessage("Is Between %s And %s (Exclusive)", lowerBound, higherBound));
  }

  /**
   * Verify that actual value is between lower and higher bound values (exclusive).
   *
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   * @param message     information about the purpose of this verification
   * @param params      parameters in case if message is a format {@link String#format}
   */
  default void verifyBetweenExclusive(
      final N lowerBound,
      final N higherBound,
      final String message,
      final Object... params) {
    CHashMap<String, N> map = new CHashMap<>();
    map.put("Lower Bound", lowerBound);
    map.put("Higher Bound", higherBound);
    _verify(
        map,
        (o, o2) -> _toState(o).betweenExclusive(lowerBound, higherBound),
        message,
        params);
  }

  /**
   * Verify that actual value is between lower and higher bound values (Inclusive).
   *
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   */
  default void verifyBetweenInclusive(
      final N lowerBound, final N higherBound) {
    verifyBetweenInclusive(
        lowerBound,
        higherBound,
        getDefaultMessage("Is Between %s And %s (Inclusive)", lowerBound, higherBound));
  }

  /**
   * Verify that actual value is between lower and higher bound values (Inclusive).
   *
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   * @param message     information about the purpose of this verification
   * @param params      parameters in case if message is a format {@link String#format}
   */
  default void verifyBetweenInclusive(
      final N lowerBound,
      final N higherBound,
      final String message,
      final Object... params) {
    CHashMap<String, N> map = new CHashMap<>();
    map.put("Lower Bound", lowerBound);
    map.put("Higher Bound", higherBound);
    _verify(
        map,
        (o, o2) -> _toState(o).betweenInclusive(lowerBound, higherBound),
        message,
        params);
  }

  /**
   * Verify that actual and expected have the exact same value or their difference is less than
   * precision value.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected  value to compare
   * @param precision the acceptable precision
   */
  default void verifyEqualsP(
      final N expected, final N precision) {
    verifyEqualsP(
        expected,
        precision,
        getDefaultMessage("Is Equal To The Expected Value"));
  }

  /**
   * Verify that actual and expected have the exact same value or their difference is less than
   * precision value.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected  value to compare
   * @param precision the acceptable precision
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsP(
      final N expected,
      final N precision,
      final String message,
      final Object... params) {
    _verify(
        expected,
        (o, o2) -> _toState(o).isEqual(expected, precision),
        "[precision: " + precision + "]" + message,
        params);
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param expected value to compare
   */
  default void verifyGreater(final N expected) {
    verifyGreater(
        expected, getDefaultMessage("Is Greater Than The Expected Value"));
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyGreater(
      final N expected,
      final String message,
      final Object... params) {
    _verify(expected, (o, o2) -> _toState(o).greater(o2), message, params);
  }

  /**
   * Verify that actual has value greater or equal to expected.
   *
   * @param expected value to compare
   */
  default void verifyGreaterOrEqual(final N expected) {
    verifyGreaterOrEqual(
        expected,
        getDefaultMessage("Is Greater Than Or Equal To The Expected Value"));
  }

  /**
   * Verify that actual has value greater or equal to expected.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyGreaterOrEqual(
      final N expected,
      final String message,
      final Object... params) {
    _verify(
        expected,
        (o, o2) -> _toState(o).greaterOrEqual(o2),
        message,
        params);
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param expected value to compare
   */
  default void verifyLess(final N expected) {
    verifyLess(expected, getDefaultMessage("Is Less Than The Expected Value"));
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyLess(
      final N expected,
      final String message,
      final Object... params) {
    _verify(expected, (o, o2) -> _toState(o).less(o2), message, params);
  }

  /**
   * Verify that actual has value less or equal than expected.
   *
   * @param expected value to compare
   */
  default void verifyLessOrEqual(final N expected) {
    verifyLessOrEqual(
        expected,
        getDefaultMessage("Is Less Than Or Equal To The Expected Value"));
  }

  /**
   * Verify that actual has value less or equal than expected.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyLessOrEqual(
      final N expected,
      final String message,
      final Object... params) {
    _verify(
        expected,
        (o, o2) -> _toState(o).lessOrEqual(o2),
        message,
        params);
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Exclusive).
   *
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   */
  default void verifyNotBetweenExclusive(
      final N lowerBound, final N higherBound) {
    verifyNotBetweenExclusive(
        lowerBound,
        higherBound,
        String.format("Not Is Between %s And %s (Exclusive)", lowerBound, higherBound));
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Exclusive).
   *
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   * @param message     information about the purpose of this verification
   * @param params      parameters in case if message is a format {@link String#format}
   */
  default void verifyNotBetweenExclusive(
      final N lowerBound,
      final N higherBound,
      final String message,
      final Object... params) {
    CHashMap<String, N> map = new CHashMap<>();
    map.put("Lower Bound", lowerBound);
    map.put("Higher Bound", higherBound);
    _verify(
        map,
        (o, o2) -> _toState(o).notBetweenExclusive(lowerBound, higherBound),
        message,
        params);
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Inclusive).
   *
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   */
  default void verifyNotBetweenInclusive(
      final N lowerBound, final N higherBound) {
    verifyNotBetweenInclusive(
        lowerBound,
        higherBound,
        String.format("Not Is Between %s And %s (Inclusive)", lowerBound, higherBound));
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Inclusive).
   *
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   * @param message     information about the purpose of this verification
   * @param params      parameters in case if message is a format {@link String#format}
   */
  default void verifyNotBetweenInclusive(
      final N lowerBound,
      final N higherBound,
      final String message,
      final Object... params) {
    CHashMap<String, N> map = new CHashMap<>();
    map.put("Lower Bound", lowerBound);
    map.put("Higher Bound", higherBound);
    _verify(
        map,
        (o, o2) -> _toState(o).notBetweenInclusive(lowerBound, higherBound),
        message,
        params);
  }

  /**
   * Verify that actual and expected have different value greater than precision value.
   *
   * <p>Please note that verification consider as passe if one value is null
   *
   * @param expected  value to compare
   * @param precision the acceptable precision
   */
  default void verifyNotEqualsP(
      final N expected, final N precision) {
    verifyNotEqualsP(
        expected,
        precision,
        "Is Not Equal To The Expected Value");
  }

  /**
   * Verify that actual and expected have different value greater than precision value.
   *
   * <p>Please note that verification consider as passe if one value is null
   *
   * @param expected  value to compare
   * @param precision the acceptable precision
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsP(
      final N expected,
      final N precision,
      final String message,
      final Object... params) {
    _verify(
        expected,
        (o, o2) -> _toState(o).notEquals(o2, precision),
        "[precision: " + precision + "]" + message,
        params);
  }
}
