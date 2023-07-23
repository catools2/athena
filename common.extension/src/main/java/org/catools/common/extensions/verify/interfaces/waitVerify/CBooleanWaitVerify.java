package org.catools.common.extensions.verify.interfaces.waitVerify;

import org.catools.common.extensions.states.interfaces.CBooleanState;
import org.catools.common.extensions.verify.hard.CBooleanVerification;
import org.catools.common.extensions.verify.interfaces.base.CBooleanVerify;

import java.util.Objects;

/**
 * CBooleanVerifier is an interface for Boolean verification related methods.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 *
 * <p>Please Note that we should extend manually {@link
 * CBooleanVerification} for each new added verification here
 */
public interface CBooleanWaitVerify extends CObjectWaitVerify<Boolean, CBooleanState>, CBooleanVerify {

  /**
   * Verify that actual value is false
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsFalse(final int waitInSeconds) {
    verifyIsFalse(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual value is false
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsFalse(final int waitInSeconds, final String message, final Object... params) {
    verifyIsFalse(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual value is false
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsFalse(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsFalse(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is False"));
  }

  /**
   * Verify that actual value is false
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsFalse(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(false, Objects::equals, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual value is true
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsTrue(final int waitInSeconds) {
    verifyIsTrue(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual value is true
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsTrue(final int waitInSeconds, final String message, final Object... params) {
    verifyIsTrue(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual value is true
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsTrue(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsTrue(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is True"));
  }

  /**
   * Verify that actual value is true
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsTrue(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, Objects::equals, waitInSeconds, intervalInMilliSeconds, message, params);
  }
}
