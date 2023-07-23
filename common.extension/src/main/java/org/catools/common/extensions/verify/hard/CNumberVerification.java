package org.catools.common.extensions.verify.hard;

import lombok.extern.slf4j.Slf4j;
import org.catools.common.extensions.verify.interfaces.base.CNumberVerify;

/**
 * Number verification class contains all verification method which is related to Number
 * (Int,Double,Long,Float,BigDecimal)
 */
@Slf4j
public class CNumberVerification<N extends Number & Comparable<N>> extends CBaseVerification {

  /**
   * Verify that actual value is between lower and higher bound values (exclusive).
   *
   * @param actual      value to compare
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   */
  public void betweenExclusive(final N actual, final N lowerBound, final N higherBound) {
    toVerifier(actual).verifyBetweenExclusive(lowerBound, higherBound);
  }

  /**
   * Verify that actual value is between lower and higher bound values (exclusive).
   *
   * @param actual      value to compare
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   * @param message     information about the purpose of this verification
   * @param params      parameters in case if message is a format {@link String#format}
   */
  public void betweenExclusive(
      final N actual,
      final N lowerBound,
      final N higherBound,
      final String message,
      final Object... params) {
    toVerifier(actual).verifyBetweenExclusive(lowerBound, higherBound, message, params);
  }

  /**
   * Verify that actual value is between lower and higher bound values (Inclusive).
   *
   * @param actual      value to compare
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   */
  public void betweenInclusive(final N actual, final N lowerBound, final N higherBound) {
    toVerifier(actual).verifyBetweenInclusive(lowerBound, higherBound);
  }

  /**
   * Verify that actual value is between lower and higher bound values (Inclusive).
   *
   * @param actual      value to compare
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   * @param message     information about the purpose of this verification
   * @param params      parameters in case if message is a format {@link String#format}
   */
  public void betweenInclusive(
      final N actual,
      final N lowerBound,
      final N higherBound,
      final String message,
      final Object... params) {
    toVerifier(actual).verifyBetweenInclusive(lowerBound, higherBound, message, params);
  }

  /**
   * Verify that actual and expected have the exact same value.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param actual   value to compare
   * @param expected value to compare
   */
  public void equals(final N actual, final N expected) {
    toVerifier(actual).verifyEquals(expected);
  }

  /**
   * Verify that actual and expected have the exact same value.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void equals(final N actual, final N expected, final String message, final Object... params) {
    toVerifier(actual).verifyEquals(expected, message, params);
  }

  /**
   * Verify that actual and expected have the exact same value or their difference is less than
   * precision value.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param actual    value to compare
   * @param expected  value to compare
   * @param precision the acceptable precision
   */
  public void equalsP(final N actual, final N expected, final N precision) {
    toVerifier(actual).verifyEqualsP(expected, precision);
  }

  /**
   * Verify that actual and expected have the exact same value or their difference is less than
   * precision value.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param actual    value to compare
   * @param expected  value to compare
   * @param precision the acceptable precision
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  public void equalsP(
      final N actual,
      final N expected,
      final N precision,
      final String message,
      final Object... params) {
    toVerifier(actual).verifyEqualsP(expected, precision, message, params);
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param actual   value to compare
   * @param expected value to compare
   */
  public void greater(final N actual, final N expected) {
    toVerifier(actual).verifyGreater(expected);
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void greater(
      final N actual, final N expected, final String message, final Object... params) {
    toVerifier(actual).verifyGreater(expected, message, params);
  }

  /**
   * Verify that actual has value greater or equal to expected.
   *
   * @param actual   value to compare
   * @param expected value to compare
   */
  public void greaterOrEqual(final N actual, final N expected) {
    toVerifier(actual).verifyGreaterOrEqual(expected);
  }

  /**
   * Verify that actual has value greater or equal to expected.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void greaterOrEqual(
      final N actual, final N expected, final String message, final Object... params) {
    toVerifier(actual).verifyGreaterOrEqual(expected, message, params);
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param actual   value to compare
   * @param expected value to compare
   */
  public void less(final N actual, final N expected) {
    toVerifier(actual).verifyLess(expected);
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void less(final N actual, final N expected, final String message, final Object... params) {
    toVerifier(actual).verifyLess(expected, message, params);
  }

  /**
   * Verify that actual has value less or equal than expected.
   *
   * @param actual   value to compare
   * @param expected value to compare
   */
  public void lessOrEqual(final N actual, final N expected) {
    toVerifier(actual).verifyLessOrEqual(expected);
  }

  /**
   * Verify that actual has value less or equal than expected.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void lessOrEqual(
      final N actual, final N expected, final String message, final Object... params) {
    toVerifier(actual).verifyLessOrEqual(expected, message, params);
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Exclusive).
   *
   * @param actual      value to compare
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   */
  public void notBetweenExclusive(final N actual, final N lowerBound, final N higherBound) {
    toVerifier(actual).verifyNotBetweenExclusive(lowerBound, higherBound);
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Exclusive).
   *
   * @param actual      value to compare
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   * @param message     information about the purpose of this verification
   * @param params      parameters in case if message is a format {@link String#format}
   */
  public void notBetweenExclusive(
      final N actual,
      final N lowerBound,
      final N higherBound,
      final String message,
      final Object... params) {
    toVerifier(actual)
        .verifyNotBetweenExclusive(lowerBound, higherBound, message, params);
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Inclusive).
   *
   * @param actual      value to compare
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   */
  public void notBetweenInclusive(final N actual, final N lowerBound, final N higherBound) {
    toVerifier(actual).verifyNotBetweenInclusive(lowerBound, higherBound);
  }

  /**
   * Verify that actual value is NOT between lower and higher bound values (Inclusive).
   *
   * @param actual      value to compare
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   * @param message     information about the purpose of this verification
   * @param params      parameters in case if message is a format {@link String#format}
   */
  public void notBetweenInclusive(
      final N actual,
      final N lowerBound,
      final N higherBound,
      final String message,
      final Object... params) {
    toVerifier(actual)
        .verifyNotBetweenInclusive(lowerBound, higherBound, message, params);
  }

  /**
   * Verify that actual and expected have different values.
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param actual   value to compare
   * @param expected value to compare
   */
  public void notEquals(final N actual, final N expected) {
    toVerifier(actual).verifyNotEquals(expected);
  }

  /**
   * Verify that actual and expected have different values.
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void notEquals(
      final N actual, final N expected, final String message, final Object... params) {
    toVerifier(actual).verifyNotEquals(expected, message, params);
  }

  /**
   * Verify that actual and expected have different value greater than precision value.
   *
   * <p>Please note that verification consider as passe if one value is null
   *
   * @param actual    value to compare
   * @param expected  value to compare
   * @param precision the acceptable precision
   */
  public void notEqualsP(final N actual, final N expected, final N precision) {
    toVerifier(actual).verifyNotEqualsP(expected, precision);
  }

  /**
   * Verify that actual and expected have different value greater than precision value.
   *
   * <p>Please note that verification consider as passe if one value is null
   *
   * @param actual    value to compare
   * @param expected  value to compare
   * @param precision the acceptable precision
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  public void notEqualsP(
      final N actual,
      final N expected,
      final N precision,
      final String message,
      final Object... params) {
    toVerifier(actual).verifyNotEqualsP(expected, precision, message, params);
  }

  protected CNumberVerify<N> toVerifier(N actual) {
    return () -> actual;
  }
}
