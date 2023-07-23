package org.catools.common.extensions.verify.interfaces.waitVerifier;

import org.catools.common.extensions.states.interfaces.CFileState;
import org.catools.common.extensions.verify.CVerificationQueue;
import org.catools.common.extensions.verify.interfaces.verifier.CFileVerifier;
import org.catools.common.io.CFile;

import java.io.File;

/**
 * CFileVerifier is an interface for File verification related methods.
 *
 * <p>Please Note that we should extend manually other verify and verifier classes for each new added verification here</p>
 */
public interface CFileWaitVerifier extends CFileVerifier, CObjectWaitVerifier<File, CFileState> {

  /**
   * Verify that actual and expected file have the exact same content.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expectedFile  file to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyEqualsStringContent(final CVerificationQueue verifier, final File expectedFile, final int waitInSeconds) {
    verifyEqualsStringContent(verifier, expectedFile, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual and expected file have the exact same content.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expectedFile  file to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsStringContent(final CVerificationQueue verifier, final File expectedFile, final int waitInSeconds, final String message, final Object... params) {
    verifyEqualsStringContent(verifier, expectedFile, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual and expected file have the exact same content.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expectedFile           file to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEqualsStringContent(final CVerificationQueue verifier, final File expectedFile, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEqualsStringContent(verifier, expectedFile, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("String Content Equals"));
  }

  /**
   * Verify that actual and expected file have the exact same content.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expectedFile           file to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsStringContent(final CVerificationQueue verifier, final File expectedFile, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expectedFile, (f1, f2) -> _toState(f1).equalsStringContent(f2), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that the file exists
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyExists(final CVerificationQueue verifier, final int waitInSeconds) {
    this.verifyExists(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that the file exists
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyExists(final CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    this.verifyExists(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that the file exists
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyExists(final CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyExists(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Exists"));
  }

  /**
   * Verify that the file exists
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyExists(final CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (file, aBoolean) -> _get().exists(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that the file does not exists
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotExists(final CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsNotExists(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that the file does not exists
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotExists(final CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsNotExists(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that the file does not exists
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotExists(final CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNotExists(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Does Not Exist"));
  }

  /**
   * Verify that the file does not exists
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotExists(final CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (file, aBoolean) -> !_get().exists(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual and expected file does not have the exact same content.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expectedFile  file to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotEqualsStringContent(final CVerificationQueue verifier, final CFile expectedFile, final int waitInSeconds) {
    verifyNotEqualsStringContent(verifier, expectedFile, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual and expected file does not have the exact same content.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expectedFile  file to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsStringContent(final CVerificationQueue verifier, final CFile expectedFile, final int waitInSeconds, final String message, final Object... params) {
    verifyNotEqualsStringContent(verifier, expectedFile, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual and expected file does not have the exact same content.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expectedFile           file to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotEqualsStringContent(final CVerificationQueue verifier, final CFile expectedFile, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotEqualsStringContent(verifier, expectedFile, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("String Content Not Equals"));
  }

  /**
   * Verify that actual and expected file does not have the exact same content.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expectedFile           file to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsStringContent(final CVerificationQueue verifier, final CFile expectedFile, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expectedFile, (f1, f2) -> _toState(f1).notEqualsStringContent(f2), waitInSeconds, intervalInMilliSeconds, message, params);
  }
}
