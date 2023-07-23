package org.catools.common.extensions.verify.interfaces.waitVerifier;

import org.catools.common.extensions.states.interfaces.CBooleanState;
import org.catools.common.extensions.verify.CVerificationQueue;
import org.catools.common.extensions.verify.interfaces.verifier.CBooleanVerifier;

import java.util.Objects;

/**
 * CBooleanVerifier is an interface for Boolean verification related methods.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extend cross all other objects:
 *
 * <p>Please Note that we should extend manually other verify and verifier classes for each new added verification here</p>
 */
public interface CBooleanWaitVerifier extends CObjectWaitVerifier<Boolean, CBooleanState>, CBooleanVerifier {

  /**
   * Verify that actual value is false
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsFalse(final CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsFalse(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual value is false
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsFalse(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsFalse(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual value is false
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsFalse(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsFalse(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is False"));
  }

  /**
   * Verify that actual value is false
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsFalse(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, false, Objects::equals, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual value is true
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsTrue(final CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsTrue(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual value is true
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsTrue(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsTrue(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual value is true
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsTrue(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsTrue(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is True"));
  }

  /**
   * Verify that actual value is true
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsTrue(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, Objects::equals, waitInSeconds, intervalInMilliSeconds, message, params);
  }
}
