package org.catools.common.extensions.verify.interfaces.waitVerifier;

import org.catools.common.extensions.verify.CVerificationQueue;
import org.catools.common.extensions.verify.interfaces.verifier.CCollectionVerifier;

import java.util.Collection;

/**
 * CCollectionVerifier is an interface for Collection verification related methods.
 *
 * <p>Please Note that we should extend manually other verify and verifier classes for each new added verification here</p>
 */
public interface CCollectionWaitVerifier<E, C extends Collection<E>> extends CCollectionVerifier<E, C>, CIterableWaitVerifier<E, C> {

  /**
   * Verify the map size is equal to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifySizeEquals(final CVerificationQueue verifier, int expected, final int waitInSeconds) {
    verifySizeEquals(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify the map size is equal to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySizeEquals(CVerificationQueue verifier, int expected, final int waitInSeconds, final String message, final Object... params) {
    verifySizeEquals(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify the map size is equal to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySizeEquals(CVerificationQueue verifier, int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySizeEquals(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Size Equals"));
  }

  /**
   * Verify the map size is equal to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySizeEquals(CVerificationQueue verifier, int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).sizeEquals(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifySizeIsGreaterThan(final CVerificationQueue verifier, int expected, final int waitInSeconds) {
    verifySizeIsGreaterThan(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySizeIsGreaterThan(CVerificationQueue verifier, int expected, final int waitInSeconds, final String message, final Object... params) {
    verifySizeIsGreaterThan(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySizeIsGreaterThan(CVerificationQueue verifier, int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySizeIsGreaterThan(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Size Is Greater Than"));
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySizeIsGreaterThan(CVerificationQueue verifier, int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).sizeIsGreaterThan(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifySizeIsLessThan(final CVerificationQueue verifier, int expected, final int waitInSeconds) {
    verifySizeIsLessThan(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySizeIsLessThan(CVerificationQueue verifier, int expected, final int waitInSeconds, final String message, final Object... params) {
    verifySizeIsLessThan(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySizeIsLessThan(CVerificationQueue verifier, int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySizeIsLessThan(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Size Is Less Than"));
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySizeIsLessThan(CVerificationQueue verifier, int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).sizeIsLessThan(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }
}
