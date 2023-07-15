package org.catools.common.extensions.verify.interfaces.base;

import org.catools.common.collections.CList;
import org.catools.common.extensions.states.interfaces.CObjectState;
import org.catools.common.extensions.verify.interfaces.CBaseVerify;

import java.util.List;

/**
 * CObjectVerifier is an interface for Object verification related methods.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 */
public interface CObjectVerify<O, S extends CObjectState<O>> extends CBaseVerify<O, S> {

  /**
   * Verify that actual and expected value are equal objects.
   *
   * @param expected value to compare
   */
  default void verifyEquals(final O expected) {
    verifyEquals(expected, getDefaultMessage("Equals"));
  }

  /**
   * Verify that actual and expected value are equal objects.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyEquals(
      final O expected,
      final String message,
      final Object... params) {
    _verify(
        expected,
        (o1, o2) -> _toState(o1).isEqual(o2),
        message,
        params);
  }

  /**
   * Verify that actual value equals to at least one of expected value.
   *
   * @param expectedList a list of strings, may be {@code null}.
   */
  default void verifyEqualsAny(List<O> expectedList) {
    verifyEqualsAny(
        expectedList, getDefaultMessage("Is Equal To One Of Expected Values"));
  }

  /**
   * Verify that actual value equals to at least one of expected value.
   *
   * @param expectedList a list of strings, may be {@code null}.
   * @param message      information about the purpose of this verification.
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsAny(
      List<O> expectedList,
      final String message,
      final Object... params) {
    _verify(
        expectedList,
        (a, b) -> a != null && b != null && new CList<>(b).has(b2 -> _toState(a).isEqual(b2)),
        message,
        params);
  }

  /**
   * Verify that actual value does not equals to any expected value.
   *
   * @param expectedList a list of strings, may be {@code null}.
   */
  default void verifyEqualsNone(List<O> expectedList) {
    verifyEqualsNone(
        expectedList,
        getDefaultMessage("Is Not Equal To Any Of Expected Values"));
  }

  /**
   * Verify that actual value does not equals to any expected value.
   *
   * @param expectedList a list of strings, may be {@code null}.
   * @param message      information about the purpose of this verification.
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsNone(
      List<O> expectedList,
      final String message,
      final Object... params) {
    _verify(
        expectedList,
        (a, b) -> a != null && b != null && new CList<>(b).hasNot(b2 -> _toState(a).isEqual(b2)),
        message,
        params);
  }

  /**
   * Verify that actual value is NOT null.
   */
  default void verifyIsNotNull() {
    verifyIsNotNull(getDefaultMessage("Is Not Null"));
  }

  /**
   * Verify that actual value is NOT null.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotNull(
      final String message, final Object... params) {
    _verify(null, (o1, o2) -> o1 != o2, message, params);
  }

  /**
   * Verify that actual value is null.
   */
  default void verifyIsNull() {
    verifyIsNull(getDefaultMessage("Is Null"));
  }

  /**
   * Verify that actual value is null.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNull(final String message, final Object... params) {
    _verify(null, (o1, o2) -> _toState(o1).isEqual((O) o2), message, params);
  }

  /**
   * Verify that actual and expected value are not equal objects.
   *
   * @param expected value to compare
   */
  default void verifyNotEquals(final O expected) {
    verifyNotEquals(expected, getDefaultMessage("Not Equals"));
  }

  /**
   * Verify that actual and expected value are not equal objects.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEquals(
      final O expected,
      final String message,
      final Object... params) {
    _verify(
        expected,
        (o1, o2) -> _toState(o1).notEquals(o2),
        message,
        params);
  }
}
