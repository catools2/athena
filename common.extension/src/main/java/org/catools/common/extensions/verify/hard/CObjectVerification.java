package org.catools.common.extensions.verify.hard;

import lombok.extern.slf4j.Slf4j;
import org.catools.common.extensions.states.interfaces.CObjectState;
import org.catools.common.extensions.verify.interfaces.base.CObjectVerify;

/**
 * Object verification class contains all verification method which is related to Object
 */
@Slf4j
public class CObjectVerification extends CBaseVerification {

  /**
   * Verify that actual and expected value are equal objects.
   *
   * @param actual   value to compare
   * @param expected value to compare
   */
  public void equals(final Object actual, final Object expected) {
    toVerifier(actual).verifyEquals(expected);
  }

  /**
   * Verify that actual and expected value are equal objects.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void equals(final Object actual, final Object expected, final String message, final Object... params) {
    toVerifier(actual).verifyEquals(expected, message, params);
  }

  /**
   * Verify that actual value is NOT null.
   *
   * @param actual value to compare
   */
  public void isNotNull(final Object actual) {
    toVerifier(actual).verifyIsNotNull();
  }

  /**
   * Verify that actual value is NOT null.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isNotNull(final Object actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsNotNull(message, params);
  }

  /**
   * Verify that actual value is null.
   *
   * @param actual value to compare
   */
  public void isNull(final Object actual) {
    toVerifier(actual).verifyIsNull();
  }

  /**
   * Verify that actual value is null.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isNull(final Object actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsNull(message, params);
  }

  /**
   * Verify that actual and expected value are not equal objects.
   *
   * @param actual   value to compare
   * @param expected value to compare
   */
  public void notEquals(final Object actual, final Object expected) {
    toVerifier(actual).verifyNotEquals(expected);
  }

  /**
   * Verify that actual and expected value are not equal objects.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void notEquals(final Object actual, final Object expected, final String message, final Object... params) {
    toVerifier(actual).verifyNotEquals(expected, message, params);
  }

  protected CObjectVerify<Object, CObjectState<Object>> toVerifier(Object actual) {
    return new CObjectVerify<>() {
      @Override
      public CObjectState<Object> _toState(Object o) {
        return () -> actual;
      }

      @Override
      public Object _get() {
        return actual;
      }
    };
  }
}
