package org.catools.common.extensions.verify.interfaces.waitVerifier;

import org.catools.common.collections.CList;
import org.catools.common.extensions.states.interfaces.CObjectState;
import org.catools.common.extensions.verify.CVerificationQueue;
import org.catools.common.extensions.verify.interfaces.CBaseVerify;
import org.catools.common.extensions.verify.interfaces.verifier.CObjectVerifier;

import java.util.List;

/**
 * CObjectVerifier is an interface for Object verification related methods.
 *
 * <p>Please Note that we should extend manually other verify and verifier classes for each new added verification here</p>
 */
public interface CObjectWaitVerifier<O, S extends CObjectState<O>> extends CObjectVerifier<O, S>, CBaseVerify<O, S> {

  /**
   * Verify that actual and expected value are equal objects.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyEquals(final CVerificationQueue verifier, final O expected, final int waitInSeconds) {
    verifyEquals(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual and expected value are equal objects.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEquals(final CVerificationQueue verifier, final O expected, final int waitInSeconds, final String message, final Object... params) {
    verifyEquals(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual and expected value are equal objects.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEquals(final CVerificationQueue verifier, final O expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEquals(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Are Equals"));
  }

  /**
   * Verify that actual and expected value are equal objects.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEquals(final CVerificationQueue verifier, final O expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (o1, o2) -> _toState(o1).isEqual(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual value equals to at least one of expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expectedList  a list of strings, may be {@code null}.
   * @param waitInSeconds maximum wait time
   */
  default void verifyEqualsAny(CVerificationQueue verifier, List<O> expectedList, final int waitInSeconds) {
    verifyEqualsAny(verifier, expectedList, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual value equals to at least one of expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expectedList  a list of strings, may be {@code null}.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsAny(CVerificationQueue verifier, List<O> expectedList, final int waitInSeconds, final String message, final Object... params) {
    verifyEqualsAny(verifier, expectedList, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual value equals to at least one of expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expectedList           a list of strings, may be {@code null}.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEqualsAny(CVerificationQueue verifier, List<O> expectedList, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEqualsAny(verifier, expectedList, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Equal To One Of Expected Values"));
  }

  /**
   * Verify that actual value equals to at least one of expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expectedList           a list of strings, may be {@code null}.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsAny(CVerificationQueue verifier, List<O> expectedList, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expectedList, (a, b) -> a != null && b != null && new CList<>(b).has(b2 -> _toState(a).isEqual(b2)), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual value does not equals to any expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expectedList  a list of strings, may be {@code null}.
   * @param waitInSeconds maximum wait time
   */
  default void verifyEqualsNone(CVerificationQueue verifier, List<O> expectedList, final int waitInSeconds) {
    verifyEqualsNone(verifier, expectedList, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual value does not equals to any expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expectedList  a list of strings, may be {@code null}.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsNone(CVerificationQueue verifier, List<O> expectedList, final int waitInSeconds, final String message, final Object... params) {
    verifyEqualsNone(verifier, expectedList, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual value does not equals to any expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expectedList           a list of strings, may be {@code null}.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEqualsNone(CVerificationQueue verifier, List<O> expectedList, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEqualsNone(verifier, expectedList, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Not Equal To Any Of Expected Values"));
  }

  /**
   * Verify that actual value does not equals to any expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expectedList           a list of strings, may be {@code null}.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsNone(CVerificationQueue verifier, List<O> expectedList, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expectedList, (a, b) -> a != null && b != null && new CList<O>(b).hasNot(b2 -> _toState(a).isEqual(b2)), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual value is NOT null.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotNull(final CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsNotNull(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual value is NOT null.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotNull(final CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsNotNull(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual value is NOT null.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotNull(final CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNotNull(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Not Null"));
  }

  /**
   * Verify that actual value is NOT null.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotNull(final CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, new Object(), (o1, o2) -> o1 != null, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual value is null.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNull(final CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsNull(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual value is null.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNull(final CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsNull(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual value is null.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNull(final CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNull(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Null"));
  }

  /**
   * Verify that actual value is null.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNull(final CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, null, true, (o1, o2) -> o1 == null, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual and expected value are not equal objects.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotEquals(final CVerificationQueue verifier, final O expected, final int waitInSeconds) {
    verifyNotEquals(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual and expected value are not equal objects.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEquals(final CVerificationQueue verifier, final O expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNotEquals(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual and expected value are not equal objects.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotEquals(final CVerificationQueue verifier, final O expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotEquals(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Are Not Equals"));
  }

  /**
   * Verify that actual and expected value are not equal objects.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEquals(final CVerificationQueue verifier, final O expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (o1, o2) -> _toState(o1).notEquals(o2), waitInSeconds, intervalInMilliSeconds, message, params);
  }
}
