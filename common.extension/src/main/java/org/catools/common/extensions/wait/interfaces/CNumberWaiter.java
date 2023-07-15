package org.catools.common.extensions.wait.interfaces;

import org.catools.common.extensions.states.interfaces.CNumberState;

/**
 * CNumberWaiter is an interface for Number waiter related methods.
 */
public interface CNumberWaiter<N extends Number & Comparable<N>> extends CObjectWaiter<N> {

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds with
   * {@code CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the actual
   * value is between lower and higher bound values (exclusive).
   *
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitBetweenExclusive(final N lowerBound, final N higherBound) {
    return waitBetweenExclusive(
        lowerBound, higherBound, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the actual value is
   * between lower and higher bound values (exclusive).
   *
   * @param lowerBound    lower bound inclusive
   * @param higherBound   higher bound inclusive
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitBetweenExclusive(
      final N lowerBound, final N higherBound, final int waitInSeconds) {
    return waitBetweenExclusive(
        lowerBound, higherBound, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual value is between lower and higher bound
   * values (exclusive).
   *
   * @param lowerBound             lower bound inclusive
   * @param higherBound            higher bound inclusive
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitBetweenExclusive(
      final N lowerBound,
      final N higherBound,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        o -> toState(o).betweenExclusive(lowerBound, higherBound),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds with
   * {@code CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the actual
   * value is between lower and higher bound values (Inclusive).
   *
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitBetweenInclusive(final N lowerBound, final N higherBound) {
    return waitBetweenInclusive(
        lowerBound, higherBound, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the actual value is
   * between lower and higher bound values (Inclusive).
   *
   * @param lowerBound    lower bound inclusive
   * @param higherBound   higher bound inclusive
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitBetweenInclusive(
      final N lowerBound, final N higherBound, final int waitInSeconds) {
    return waitBetweenInclusive(
        lowerBound, higherBound, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual value is between lower and higher bound
   * values (Inclusive).
   *
   * @param lowerBound             lower bound inclusive
   * @param higherBound            higher bound inclusive
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitBetweenInclusive(
      final N lowerBound,
      final N higherBound,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        o -> toState(o).betweenInclusive(lowerBound, higherBound),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds with
   * {@code CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the actual
   * and expected have the exact same value or their difference is less than precision value.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected  value to compare
   * @param precision the acceptable precision
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsP(final N expected, final N precision) {
    return waitEqualsP(
        expected, precision, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the actual and
   * expected have the exact same value or their difference is less than precision value.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected      value to compare
   * @param precision     the acceptable precision
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsP(final N expected, final N precision, final int waitInSeconds) {
    return waitEqualsP(expected, precision, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual and expected have the exact same value or
   * their difference is less than precision value.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected               value to compare
   * @param precision              the acceptable precision
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsP(
      final N expected,
      final N precision,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        o -> toState(o).isEqual(expected, precision), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds with
   * {@code CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the actual
   * has value greater than expected.
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitGreater(final N expected) {
    return waitGreater(expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the actual has
   * value greater than expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitGreater(final N expected, final int waitInSeconds) {
    return waitGreater(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual has value greater than expected.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitGreater(
      final N expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).greater(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds with
   * {@code CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the actual
   * has value greater or equal to expected.
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitGreaterOrEqual(final N expected) {
    return waitGreaterOrEqual(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the actual has
   * value greater or equal to expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitGreaterOrEqual(final N expected, final int waitInSeconds) {
    return waitGreaterOrEqual(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual has value greater or equal to expected.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitGreaterOrEqual(
      final N expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).greaterOrEqual(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds with
   * {@code CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the actual
   * has value less than expected.
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitLess(final N expected) {
    return waitLess(expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the actual has
   * value less than expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitLess(final N expected, final int waitInSeconds) {
    return waitLess(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual has value less than expected.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitLess(
      final N expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).less(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds with
   * {@code CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the actual
   * has value less or equal than expected.
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitLessOrEqual(final N expected) {
    return waitLessOrEqual(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the actual has
   * value less or equal than expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitLessOrEqual(final N expected, final int waitInSeconds) {
    return waitLessOrEqual(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual has value less or equal than expected.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitLessOrEqual(
      final N expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).lessOrEqual(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds with
   * {@code CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the actual
   * value is NOT between lower and higher bound values (Exclusive).
   *
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotBetweenExclusive(final N lowerBound, final N higherBound) {
    return waitNotBetweenExclusive(
        lowerBound, higherBound, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the actual value is
   * NOT between lower and higher bound values (Exclusive).
   *
   * @param lowerBound    lower bound inclusive
   * @param higherBound   higher bound inclusive
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotBetweenExclusive(
      final N lowerBound, final N higherBound, final int waitInSeconds) {
    return waitNotBetweenExclusive(
        lowerBound, higherBound, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual value is NOT between lower and higher bound
   * values (Exclusive).
   *
   * @param lowerBound             lower bound inclusive
   * @param higherBound            higher bound inclusive
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotBetweenExclusive(
      final N lowerBound,
      final N higherBound,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        o -> toState(o).notBetweenExclusive(lowerBound, higherBound),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds with
   * {@code CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the actual
   * value is NOT between lower and higher bound values (Inclusive).
   *
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotBetweenInclusive(final N lowerBound, final N higherBound) {
    return waitNotBetweenInclusive(
        lowerBound, higherBound, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the actual value is
   * NOT between lower and higher bound values (Inclusive).
   *
   * @param lowerBound    lower bound inclusive
   * @param higherBound   higher bound inclusive
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotBetweenInclusive(
      final N lowerBound, final N higherBound, final int waitInSeconds) {
    return waitNotBetweenInclusive(
        lowerBound, higherBound, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual value is NOT between lower and higher bound
   * values (Inclusive).
   *
   * @param lowerBound             lower bound inclusive
   * @param higherBound            higher bound inclusive
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotBetweenInclusive(
      final N lowerBound,
      final N higherBound,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        o -> toState(o).notBetweenInclusive(lowerBound, higherBound),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds with
   * {@code CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the actual
   * and expected have different value greater than precision value.
   *
   * <p>Please note that verification consider as passe if one value is null
   *
   * @param expected  value to compare
   * @param precision the acceptable precision
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEqualsP(final N expected, final N precision) {
    return waitNotEqualsP(
        expected, precision, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the actual and
   * expected have different value greater than precision value.
   *
   * <p>Please note that verification consider as passe if one value is null
   *
   * @param expected      value to compare
   * @param precision     the acceptable precision
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEqualsP(final N expected, final N precision, final int waitInSeconds) {
    return waitNotEqualsP(
        expected, precision, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual and expected have different value greater
   * than precision value.
   *
   * <p>Please note that verification consider as passe if one value is null
   *
   * @param expected               value to compare
   * @param precision              the acceptable precision
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEqualsP(
      final N expected,
      final N precision,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        o -> toState(o).notEquals(expected, precision), waitInSeconds, intervalInMilliSeconds);
  }

  private CNumberState<N> toState(Object e) {
    return () -> (N) e;
  }
}
