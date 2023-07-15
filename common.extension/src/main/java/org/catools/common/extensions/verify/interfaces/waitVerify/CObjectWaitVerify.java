package org.catools.common.extensions.verify.interfaces.waitVerify;

import org.catools.common.collections.CList;
import org.catools.common.extensions.states.interfaces.CObjectState;
import org.catools.common.extensions.verify.interfaces.CBaseVerify;
import org.catools.common.extensions.verify.interfaces.base.CObjectVerify;

import java.util.List;

/**
 * CObjectVerifier is an interface for Object verification related methods.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 */
public interface CObjectWaitVerify<O, S extends CObjectState<O>> extends CObjectVerify<O, S>, CBaseVerify<O, S> {

  /**
   * Verify that actual and expected value are equal objects.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyEquals(final O expected, final int waitInSeconds) {
    verifyEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual and expected value are equal objects.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEquals(final O expected, final int waitInSeconds, final String message, final Object... params) {
    verifyEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual and expected value are equal objects.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEquals(final O expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEquals(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Are Equals"));
  }

  /**
   * Verify that actual and expected value are equal objects.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEquals(final O expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (o1, o2) -> _toState(o1).isEqual(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual value equals to at least one of expected value.
   *
   * @param expectedList  a list of strings, may be {@code null}.
   * @param waitInSeconds maximum wait time
   */
  default void verifyEqualsAny(List<O> expectedList, final int waitInSeconds) {
    verifyEqualsAny(expectedList, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual value equals to at least one of expected value.
   *
   * @param expectedList  a list of strings, may be {@code null}.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsAny(List<O> expectedList, final int waitInSeconds, final String message, final Object... params) {
    verifyEqualsAny(expectedList, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual value equals to at least one of expected value.
   *
   * @param expectedList           a list of strings, may be {@code null}.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEqualsAny(List<O> expectedList, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEqualsAny(expectedList, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Equal To One Of Expected Values"));
  }

  /**
   * Verify that actual value equals to at least one of expected value.
   *
   * @param expectedList           a list of strings, may be {@code null}.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsAny(List<O> expectedList, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expectedList, (a, b) -> a != null && b != null && new CList<>(b).has(b2 -> _toState(a).isEqual(b2)), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual value does not equals to any expected value.
   *
   * @param expectedList  a list of strings, may be {@code null}.
   * @param waitInSeconds maximum wait time
   */
  default void verifyEqualsNone(List<O> expectedList, final int waitInSeconds) {
    verifyEqualsNone(expectedList, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual value does not equals to any expected value.
   *
   * @param expectedList  a list of strings, may be {@code null}.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsNone(List<O> expectedList, final int waitInSeconds, final String message, final Object... params) {
    verifyEqualsNone(expectedList, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual value does not equals to any expected value.
   *
   * @param expectedList           a list of strings, may be {@code null}.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEqualsNone(List<O> expectedList, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEqualsNone(expectedList, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Not Equal To Any Of Expected Values"));
  }

  /**
   * Verify that actual value does not equals to any expected value.
   *
   * @param expectedList           a list of strings, may be {@code null}.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsNone(List<O> expectedList, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expectedList, (a, b) -> a != null && b != null && new CList<O>(b).hasNot(b2 -> _toState(a).isEqual(b2)), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual value is NOT null.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotNull(final int waitInSeconds) {
    verifyIsNotNull(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual value is NOT null.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotNull(final int waitInSeconds, final String message, final Object... params) {
    verifyIsNotNull(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual value is NOT null.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotNull(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNotNull(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Not Null"));
  }

  /**
   * Verify that actual value is NOT null.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotNull(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(new Object(), (o1, o2) -> o1 != null, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual value is null.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNull(final int waitInSeconds) {
    verifyIsNull(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual value is null.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNull(final int waitInSeconds, final String message, final Object... params) {
    verifyIsNull(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual value is null.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNull(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNull(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Null"));
  }

  /**
   * Verify that actual value is null.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNull(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(null, (o1, o2) -> _toState(o1).isEqual((O) o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual and expected value are not equal objects.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotEquals(final O expected, final int waitInSeconds) {
    verifyNotEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual and expected value are not equal objects.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEquals(final O expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNotEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual and expected value are not equal objects.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotEquals(final O expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotEquals(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Are Not Equals"));
  }

  /**
   * Verify that actual and expected value are not equal objects.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEquals(final O expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (o1, o2) -> _toState(o1).notEquals(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }
}
