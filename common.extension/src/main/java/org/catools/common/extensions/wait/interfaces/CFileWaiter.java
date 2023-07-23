package org.catools.common.extensions.wait.interfaces;

import org.catools.common.extensions.states.interfaces.CFileState;
import org.catools.common.io.CFile;

import java.io.File;

/**
 * CFileWaiter is an interface for CFile waiter related methods.
 */
public interface CFileWaiter extends CObjectWaiter<File> {

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the actual and
   * expected file have the exact same content.
   *
   * @param expectedFile file to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsStringContent(final File expectedFile) {
    return waitEqualsStringContent(expectedFile, getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the actual and
   * expected file have the exact same content.
   *
   * @param expectedFile  file to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsStringContent(final File expectedFile, final int waitInSeconds) {
    return waitEqualsStringContent(expectedFile, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual and expected file have the exact same
   * content.
   *
   * @param expectedFile           file to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsStringContent(final File expectedFile, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).equalsStringContent(expectedFile), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the the file exists
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsExists() {
    return waitIsExists(getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the the file exists
   *
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsExists(final int waitInSeconds) {
    return waitIsExists(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the the file exists
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsExists(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> _get().exists(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the the file does
   * not exists
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotExists() {
    return waitIsNotExists(getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the the file does
   * not exists
   *
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotExists(final int waitInSeconds) {
    return waitIsNotExists(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the the file does not exist
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotExists(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> !_get().exists(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the actual and
   * expected file does not have the exact same content.
   *
   * @param expectedFile file to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEqualsStringContent(final CFile expectedFile) {
    return waitNotEqualsStringContent(expectedFile, getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till the actual and
   * expected file does not have the exact same content.
   *
   * @param expectedFile  file to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEqualsStringContent(final CFile expectedFile, final int waitInSeconds) {
    return waitNotEqualsStringContent(expectedFile, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till the actual and expected file does not have the exact
   * same content.
   *
   * @param expectedFile           file to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEqualsStringContent(final CFile expectedFile, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).notEqualsStringContent(expectedFile), waitInSeconds, intervalInMilliSeconds);
  }

  private CFileState toState(Object e) {
    return () -> (File) e;
  }
}
