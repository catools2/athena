package org.catools.common.extensions.verify.interfaces.base;

import org.catools.common.extensions.base.CBaseBooleanExtension;
import org.catools.common.extensions.states.interfaces.CBooleanState;

/**
 * CBooleanVerifier is an interface for Boolean verification related methods.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 */
public interface CBooleanVerify extends CBaseBooleanExtension, CObjectVerify<Boolean, CBooleanState> {

  /**
   * Verify that actual value is false
   */
  default void verifyIsFalse() {
    verifyIsFalse(getDefaultMessage("Is False"));
  }

  /**
   * Verify that actual value is false
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsFalse(final String message, final Object... params) {
    _verify(false, (a, b) -> _toState(a).isFalse(), message, params);
  }

  /**
   * Verify that actual value is true
   */
  default void verifyIsTrue() {
    verifyIsTrue(getDefaultMessage("Is True"));
  }

  /**
   * Verify that actual value is true
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsTrue(final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isTrue(), message, params);
  }

  /**
   * Verify that actual and expected has different boolean value
   *
   * @param expected value to compare
   */
  default void verifyNotEquals(final Boolean expected) {
    verifyNotEquals(expected, getDefaultMessage("Not Equals"));
  }

  /**
   * Verify that actual and expected has different boolean value
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEquals(final Boolean expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).notEquals(b), message, params);
  }
}
