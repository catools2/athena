package org.catools.common.extensions.verify.interfaces.verifier;

import org.catools.common.extensions.base.CBaseBooleanExtension;
import org.catools.common.extensions.states.interfaces.CBooleanState;
import org.catools.common.extensions.verify.CVerificationQueue;

/**
 * CBooleanVerifier is an interface for Boolean verification related methods.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 */
public interface CBooleanVerifier extends CBaseBooleanExtension, CObjectVerifier<Boolean, CBooleanState> {

  /**
   * Verify that actual value is false
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsFalse(final CVerificationQueue verifier) {
    verifyIsFalse(verifier, getDefaultMessage("Is False"));
  }

  /**
   * Verify that actual value is false
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsFalse(final CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, false, (a, b) -> _toState(a).isFalse(), message, params);
  }

  /**
   * Verify that actual value is true
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsTrue(final CVerificationQueue verifier) {
    verifyIsTrue(verifier, getDefaultMessage("Is True"));
  }

  /**
   * Verify that actual value is true
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsTrue(final CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isTrue(), message, params);
  }

  /**
   * Verify that actual and expected has different boolean value
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   */
  default void verifyNotEquals(final CVerificationQueue verifier, final Boolean expected) {
    verifyNotEquals(verifier, expected, getDefaultMessage("Not Equals"));
  }

  /**
   * Verify that actual and expected has different boolean value
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEquals(final CVerificationQueue verifier, final Boolean expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).notEquals(b), message, params);
  }
}
