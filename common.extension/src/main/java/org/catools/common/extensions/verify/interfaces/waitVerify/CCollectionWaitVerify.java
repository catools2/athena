package org.catools.common.extensions.verify.interfaces.waitVerify;

import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CLinkedMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.collections.interfaces.CCollection;
import org.catools.common.extensions.verify.interfaces.base.CCollectionVerify;

import java.util.Collection;
import java.util.Map;

/**
 * CCollectionVerifier is an interface for Collection verification related methods.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 *
 * @see Map
 * @see CCollection
 * @see CHashMap
 * @see CLinkedMap
 * @see CSet
 * @see CList
 */
public interface CCollectionWaitVerify<E, C extends Collection<E>> extends CCollectionVerify<E, C>, CIterableWaitVerify<E, C> {

  /**
   * Verify the map size is equal to expected value.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifySizeEquals(int expected, final int waitInSeconds) {
    verifySizeEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify the map size is equal to expected value.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySizeEquals(int expected, final int waitInSeconds, final String message, final Object... params) {
    verifySizeEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify the map size is equal to expected value.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySizeEquals(int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySizeEquals(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Size Equals"));
  }

  /**
   * Verify the map size is equal to expected value.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySizeEquals(int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (o, o2) -> _toState(o).sizeEquals(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifySizeIsGreaterThan(int expected, final int waitInSeconds) {
    verifySizeIsGreaterThan(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySizeIsGreaterThan(int expected, final int waitInSeconds, final String message, final Object... params) {
    verifySizeIsGreaterThan(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySizeIsGreaterThan(int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySizeIsGreaterThan(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Size Is Greater Than"));
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
  default void verifySizeIsGreaterThan(int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (o, o2) -> _toState(o).sizeIsGreaterThan(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifySizeIsLessThan(int expected, final int waitInSeconds) {
    verifySizeIsLessThan(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySizeIsLessThan(int expected, final int waitInSeconds, final String message, final Object... params) {
    verifySizeIsLessThan(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySizeIsLessThan(int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySizeIsLessThan(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Size Is Less Than"));
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
  default void verifySizeIsLessThan(int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (o, o2) -> _toState(o).sizeIsLessThan(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }
}
