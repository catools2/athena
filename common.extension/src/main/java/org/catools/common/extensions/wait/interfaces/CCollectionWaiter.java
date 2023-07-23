package org.catools.common.extensions.wait.interfaces;

import org.catools.common.extensions.states.interfaces.CCollectionState;

import java.util.Collection;

/**
 * CCollectionWaiter is an interface for Collection waiter related methods.
 */
public interface CCollectionWaiter<E, C extends Collection<E>> extends CIterableWaiter<E, C>, CObjectWaiter<C> {

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the map size is equal to expected value.
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSizeEquals(final int expected) {
    return waitSizeEquals(expected, getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds till the map size is equal to expected value.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSizeEquals(final int expected, final int waitInSeconds) {
    return waitSizeEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the map size is equal to expected value.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSizeEquals(final int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).sizeEquals(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual has value greater than expected.
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSizeIsGreaterThan(final int expected) {
    return waitSizeIsGreaterThan(expected, getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual has value greater than expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSizeIsGreaterThan(final int expected, final int waitInSeconds) {
    return waitSizeIsGreaterThan(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual has value greater than expected.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSizeIsGreaterThan(final int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).sizeIsGreaterThan(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * the actual has value less than expected.
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSizeIsLessThan(final int expected) {
    return waitSizeIsLessThan(expected, getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual has value less than expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSizeIsLessThan(final int expected, final int waitInSeconds) {
    return waitSizeIsLessThan(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual has value less than expected.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSizeIsLessThan(final int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).sizeIsLessThan(expected), waitInSeconds, intervalInMilliSeconds);
  }

  @SuppressWarnings("unchecked")
  private CCollectionState<E, C> toState(Object e) {
    return () -> (C) e;
  }
}
