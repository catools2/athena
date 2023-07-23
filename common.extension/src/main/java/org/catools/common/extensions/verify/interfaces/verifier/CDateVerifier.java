package org.catools.common.extensions.verify.interfaces.verifier;

import org.catools.common.extensions.base.CBaseDateExtension;
import org.catools.common.extensions.states.interfaces.CDateState;
import org.catools.common.extensions.verify.CVerificationQueue;

import java.util.Date;

/**
 * CDateVerifier is an interface for Date verification related methods.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 */
public interface CDateVerifier extends CBaseDateExtension, CObjectVerifier<Date, CDateState> {

  /**
   * Verify that actual and expected have the exact same string value after they converted using the
   * provided date format. Means that verification of "2019-08-09 12:20" and "2019-08-09 11:20"
   * using "yyyy-MM-dd" passes.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param format   date format to be use
   */
  default void verifyEqualsByFormat(final CVerificationQueue verifier, final Date expected, final String format) {
    verifyEqualsByFormat(verifier, expected, format, getDefaultMessage("Equals By Format " + format));
  }

  /**
   * Verify that actual and expected have the exact same string value after they converted using the
   * provided date format. Means that verification of "2019-08-09 12:20" and "2019-08-09 11:20"
   * using "yyyy-MM-dd" passes.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param format   date format to be use
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsByFormat(final CVerificationQueue verifier, final Date expected, final String format, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).equalsByFormat(o2, format), message, params);
  }

  /**
   * Verify that actual and expected have same string value after they converted using "yyyy-MM-dd"
   * for format. Means that verification of "2019-08-09 12:20" and "2019-08-09 11:20" passes
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   */
  default void verifyEqualsDatePortion(final CVerificationQueue verifier, final Date expected) {
    verifyEqualsDatePortion(verifier, expected, getDefaultMessage("Date Portion Equals"));
  }

  /**
   * Verify that actual and expected have same string value after they converted using "yyyy-MM-dd"
   * for format. Means that verification of "2019-08-09 12:20" and "2019-08-09 11:20" passes
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsDatePortion(final CVerificationQueue verifier, final Date expected, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).equalsDatePortion(o2), message, params);
  }

  /**
   * Verify that actual and expected have same string value after they converted using "HH:mm:ss"
   * for format. Means that verification of "2019-08-09 12:20" and "2019-08-08 12:20" passes
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   */
  default void verifyEqualsTimePortion(final CVerificationQueue verifier, final Date expected) {
    verifyEqualsTimePortion(verifier, expected, getDefaultMessage("Time Portion Equals"));
  }

  /**
   * Verify that actual and expected have same string value after they converted using "HH:mm:ss"
   * for format. Means that verification of "2019-08-09 12:20" and "2019-08-08 12:20" passes
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsTimePortion(final CVerificationQueue verifier, final Date expected, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).equalsTimePortion(o2), message, params);
  }

  /**
   * Verify that actual and expected have different string value after they converted using the
   * provided date format. Means that verification of "2019-08-09 12:20" and "2019-08-09 11:20"
   * using "yyyy-MM-dd HH" passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param format   date format to be use
   */
  default void verifyNotEqualsByFormat(final CVerificationQueue verifier, final Date expected, final String format) {
    verifyNotEqualsByFormat(verifier, expected, format, getDefaultMessage("Not Equals By Format " + format));
  }

  /**
   * Verify that actual and expected have different string value after they converted using the
   * provided date format. Means that verification of "2019-08-09 12:20" and "2019-08-09 11:20"
   * using "yyyy-MM-dd HH" passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param format   date format to be use
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsByFormat(final CVerificationQueue verifier, final Date expected, final String format, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).notEqualsByFormat(o2, format), message, params);
  }

  /**
   * Verify that actual and expected have different string value after they converted using
   * "yyyy-MM-dd" for format. Means that verification of "2019-08-09 12:20" and "2019-08-08 12:20"
   * passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   */
  default void verifyNotEqualsDatePortion(final CVerificationQueue verifier, final Date expected) {
    verifyNotEqualsDatePortion(verifier, expected, getDefaultMessage("Date Portion Not Equals"));
  }

  /**
   * Verify that actual and expected have different string value after they converted using
   * "yyyy-MM-dd" for format. Means that verification of "2019-08-09 12:20" and "2019-08-08 12:20"
   * passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsDatePortion(final CVerificationQueue verifier, final Date expected, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).notEqualsDatePortion(o2), message, params);
  }

  /**
   * Verify that actual and expected have different string value after they converted using
   * "HH:mm:ss" for format. Means that verification of "2019-08-09 12:20:31" and "2019-08-09
   * 12:20:30" passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   */
  default void verifyNotEqualsTimePortion(final CVerificationQueue verifier, final Date expected) {
    verifyNotEqualsTimePortion(verifier, expected, getDefaultMessage("Time Portion Not Equals"));
  }

  /**
   * Verify that actual and expected have different string value after they converted using
   * "HH:mm:ss" for format. Means that verification of "2019-08-09 12:20:31" and "2019-08-09
   * 12:20:30" passes (means values are different)
   *
   * <p>Please note that verification consider as passe if one of value is null
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsTimePortion(final CVerificationQueue verifier, final Date expected, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).notEqualsTimePortion(o2), message, params);
  }
}
