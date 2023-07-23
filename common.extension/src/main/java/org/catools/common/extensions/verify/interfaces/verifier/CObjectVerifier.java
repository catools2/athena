package org.catools.common.extensions.verify.interfaces.verifier;

import org.catools.common.collections.CList;
import org.catools.common.extensions.states.interfaces.CObjectState;
import org.catools.common.extensions.verify.CVerificationQueue;
import org.catools.common.extensions.verify.interfaces.CBaseVerify;
import org.catools.common.extensions.wait.interfaces.CBaseWaiter;

import java.util.List;

/**
 * CObjectVerifier is an interface for Object verification related methods.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 */
public interface CObjectVerifier<O, S extends CObjectState<O>> extends CBaseVerify<O,S>, CBaseWaiter<O> {

  /**
   * Verify that actual and expected value are equal objects.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   */
  default void verifyEquals(final CVerificationQueue verifier, final O expected) {
    verifyEquals(verifier, expected, getDefaultMessage("Equals"));
  }

  /**
   * Verify that actual and expected value are equal objects.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyEquals(final CVerificationQueue verifier, final O expected, final String message, final Object... params) {
    _verify(verifier, expected, (o1, o2) -> _toState(o1).isEqual(o2), message, params);
  }

  /**
   * Verify that actual value equals to at least one of expected value.
   *
   * @param verifier     CVerificationQueue instance
   * @param expectedList a list of strings, may be {@code null}.
   */
  default void verifyEqualsAny(final CVerificationQueue verifier, List<O> expectedList) {
    verifyEqualsAny(verifier, expectedList, getDefaultMessage("Is Equal To One Of Expected Values"));
  }

  /**
   * Verify that actual value equals to at least one of expected value.
   *
   * @param verifier     CVerificationQueue instance
   * @param expectedList a list of strings, may be {@code null}.
   * @param message      information about the purpose of this verification.
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsAny(CVerificationQueue verifier, List<O> expectedList, final String message, final Object... params) {
    _verify(verifier, expectedList, (a, b) -> a != null && b != null && new CList<>(b).has(b2 -> _toState(a).isEqual(b2)), message, params);
  }

  /**
   * Verify that actual value does not equals to any expected value.
   *
   * @param verifier     CVerificationQueue instance
   * @param expectedList a list of strings, may be {@code null}.
   */
  default void verifyEqualsNone(final CVerificationQueue verifier, List<O> expectedList) {
    verifyEqualsNone(verifier, expectedList, getDefaultMessage("Is Not Equal To Any Of Expected Values"));
  }

  /**
   * Verify that actual value does not equals to any expected value.
   *
   * @param verifier     CVerificationQueue instance
   * @param expectedList a list of strings, may be {@code null}.
   * @param message      information about the purpose of this verification.
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsNone(CVerificationQueue verifier, List<O> expectedList, final String message, final Object... params) {
    _verify(verifier, expectedList, (a, b) -> a != null && b != null && new CList<>(b).hasNot(b2 -> _toState(a).isEqual(b2)), message, params);
  }

  /**
   * Verify that actual value is NOT null.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsNotNull(final CVerificationQueue verifier) {
    verifyIsNotNull(verifier, getDefaultMessage("Is Not Null"));
  }

  /**
   * Verify that actual value is NOT null.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotNull(final CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, null, (o1, o2) -> o1 != o2, message, params);
  }

  /**
   * Verify that actual value is null.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsNull(final CVerificationQueue verifier) {
    verifyIsNull(verifier, getDefaultMessage("Is Null"));
  }

  /**
   * Verify that actual value is null.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNull(final CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, true, (o1, o2) -> _toState(o1).isEqual(null), message, params);
  }

  /**
   * Verify that actual and expected value are not equal objects.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   */
  default void verifyNotEquals(final CVerificationQueue verifier, final O expected) {
    verifyNotEquals(verifier, expected, getDefaultMessage("Not Equals"));
  }

  /**
   * Verify that actual and expected value are not equal objects.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEquals(final CVerificationQueue verifier, final O expected, final String message, final Object... params) {
    _verify(verifier, expected, (o1, o2) -> _toState(o1).notEquals(o2), message, params);
  }
}
