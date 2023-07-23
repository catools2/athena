package org.catools.common.extensions.wait.interfaces;

/**
 * CBooleanWaiter is an interface for Boolean waiter related methods.
 */
public interface CBooleanWaiter extends CObjectWaiter<Boolean> {

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds with
   * {@code CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till actual value
   * is false
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsFalse() {
    return waitIsFalse(getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till actual value is
   * false
   *
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsFalse(final int waitInSeconds) {
    return waitIsFalse(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till actual value is false
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsFalse(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> !o, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds with
   * {@code CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till actual value
   * is true
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsTrue() {
    return waitIsTrue(getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till actual value is
   * true
   *
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsTrue(final int waitInSeconds) {
    return waitIsTrue(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till actual value is true
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsTrue(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> o, waitInSeconds, intervalInMilliSeconds);
  }
}
