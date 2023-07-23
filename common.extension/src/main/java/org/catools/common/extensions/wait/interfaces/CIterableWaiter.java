package org.catools.common.extensions.wait.interfaces;

import org.catools.common.extensions.states.interfaces.CIterableState;

import java.util.function.Predicate;

/**
 * CIterableWaiter is an interface for Iterable waiter related methods.
 */
public interface CIterableWaiter<E, C extends Iterable<E>> extends CObjectWaiter<C> {

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual collection has the expected predicate.
   *
   * @param expected predicate
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitHas(final Predicate<E> expected) {
    return waitHas(expected, getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual collection contains the expected predicate.
   *
   * @param expected      predicate
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitHas(Predicate<E> expected, final int waitInSeconds) {
    return waitHas(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual collection contains the expected predicate.
   *
   * @param expected               predicate
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitHas(Predicate<E> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(_get()).has(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual collection contains the expected element.
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitContains(final E expected) {
    return waitContains(expected, getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual collection contains the expected element.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitContains(final E expected, final int waitInSeconds) {
    return waitContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual collection contains the expected element.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitContains(final E expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(_get()).contains(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual collection contains all elements from the expected collection. Please note that
   * actual collection might have more elements.
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitContainsAll(final C expected) {
    return waitContainsAll(expected, getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual collection contains all elements from the
   * expected collection. Please note that actual collection might have more elements.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitContainsAll(final C expected, final int waitInSeconds) {
    return waitContainsAll(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual collection contains all elements from the
   * expected collection. Please note that actual collection might have more elements.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitContainsAll(final C expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> o != null && expected != null && toState(o).containsAll(expected, null), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual collection contains none of elements from the expected collection.
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitContainsNone(final C expected) {
    return waitContainsNone(expected, getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual collection contains none of elements from
   * the expected collection.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitContainsNone(final C expected, final int waitInSeconds) {
    return waitContainsNone(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual collection contains none of elements from
   * the expected collection.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitContainsNone(final C expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).containsNone(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual collection either is empty or contains the expected element.
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEmptyOrContains(final E expected) {
    return waitEmptyOrContains(expected, getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual collection either is empty or contains the
   * expected element.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEmptyOrContains(final E expected, final int waitInSeconds) {
    return waitEmptyOrContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual collection either is empty or contains the
   * expected element.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEmptyOrContains(final E expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).emptyOrContains(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual collection either is empty or does not contain the expected element.
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEmptyOrNotContains(final E expected) {
    return waitEmptyOrNotContains(expected, getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual collection either is empty or does not
   * contain the expected element.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEmptyOrNotContains(final E expected, final int waitInSeconds) {
    return _waiter(o -> toState(o).emptyOrNotContains(expected), waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual collection either is empty or does not
   * contain the expected element.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEmptyOrNotContains(final E expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).emptyOrNotContains(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual and expected collections have the exact same elements. (Ignore element order) First
   * we compare that actual collection contains all expected collection elements and then we verify
   * that expected has all elements from actual.
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEquals(final C expected) {
    return waitEquals(expected, getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual and expected collections have the exact same
   * elements. (Ignore element order) First we compare that actual collection contains all expected
   * collection elements and then we verify that expected has all elements from actual.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEquals(final C expected, final int waitInSeconds) {
    return waitEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual and expected collections have the exact same
   * elements. (Ignore element order) First we compare that actual collection contains all expected
   * collection elements and then we verify that expected has all elements from actual.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEquals(final C expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).isEqual(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual collection is empty.
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmpty() {
    return waitIsEmpty(getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual collection is empty.
   *
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmpty(final int waitInSeconds) {
    return waitIsEmpty(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual collection is empty.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmpty(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(_get()).isEmpty(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual collection is not empty. (might contains null values)
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotEmpty() {
    return waitIsNotEmpty(getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual collection is not empty. (might contains
   * null values)
   *
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotEmpty(final int waitInSeconds) {
    return waitIsNotEmpty(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual collection is not empty. (might contains
   * null values)
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotEmpty(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(_get()).isNotEmpty(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual collection does not contain the expected element.
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotContains(final E expected) {
    return waitNotContains(expected, getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual collection does not contain the expected
   * element.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotContains(final E expected, final int waitInSeconds) {
    return waitNotContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual collection does not contain the expected
   * element.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotContains(final E expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).notContains(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual collection contains some but not all elements from the expected collection. Please
   * note that actual collection might have some elements but the point is to ensure that not all
   * expected elements are exist in it.
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotContainsAll(final C expected) {
    return waitNotContainsAll(expected, getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual collection contains some but not all
   * elements from the expected collection. Please note that actual collection might have some of
   * elements but the point is to ensure that not all expected elements are exist in it.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotContainsAll(final C expected, final int waitInSeconds) {
    return waitNotContainsAll(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual collection contains some but not all
   * elements from the expected collection. Please note that actual collection might have some of
   * elements but the point is to ensure that not all expected elements are exist in it.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotContainsAll(final C expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).notContainsAll(expected), waitInSeconds, intervalInMilliSeconds);
  }

  @SuppressWarnings("unchecked")
  private CIterableState<E, C> toState(Object e) {
    return () -> (C) e;
  }
}
