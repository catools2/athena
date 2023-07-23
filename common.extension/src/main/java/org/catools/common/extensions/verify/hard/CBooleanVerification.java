package org.catools.common.extensions.verify.hard;

import lombok.extern.slf4j.Slf4j;
import org.catools.common.extensions.verify.interfaces.base.CBooleanVerify;

/**
 * Boolean verification class contains all verification method which is related to Boolean
 */
@Slf4j
public class CBooleanVerification extends CBaseVerification {

  /**
   * Verify that actual and expected have same boolean value or be null
   *
   * @param actual   value to compare
   * @param expected value to compare
   */
  public void equals(final Boolean actual, final Boolean expected) {
    toVerifier(actual).verifyEquals(expected);
  }

  /**
   * Verify that actual and expected have same boolean value or be null
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void equals(final Boolean actual, final Boolean expected, final String message, final Object... params) {
    toVerifier(actual).verifyEquals(expected, message, params);
  }

  /**
   * Verify that actual value is false
   *
   * @param actual value to compare
   */
  public void isFalse(Boolean actual) {
    toVerifier(actual).verifyIsFalse();
  }

  /**
   * Verify that actual value is false
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isFalse(Boolean actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsFalse(message, params);
  }

  /**
   * Verify that actual value is true
   *
   * @param actual value to compare
   */
  public void isTrue(Boolean actual) {
    toVerifier(actual).verifyIsTrue();
  }

  /**
   * Verify that actual value is true
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isTrue(Boolean actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsTrue(message, params);
  }

  /**
   * Verify that actual and expected has different boolean value
   *
   * @param actual   value to compare
   * @param expected value to compare
   */
  public void notEquals(final Boolean actual, final Boolean expected) {
    toVerifier(actual).verifyNotEquals(expected);
  }

  /**
   * Verify that actual and expected has different boolean value
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void notEquals(final Boolean actual, final Boolean expected, final String message, final Object... params) {
    toVerifier(actual).verifyNotEquals(expected, message, params);
  }

  protected CBooleanVerify toVerifier(Boolean actual) {
    return () -> actual;
  }
}
