package org.catools.common.extensions.verify.interfaces.waitVerifier;

import org.catools.common.extensions.states.interfaces.CStringState;
import org.catools.common.extensions.verify.CVerificationQueue;
import org.catools.common.extensions.verify.interfaces.verifier.CStringVerifier;
import org.catools.common.utils.CStringUtil;

import java.util.List;
import java.util.regex.Pattern;

/**
 * CStringVerifier is an interface for String verification related methods.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extend cross all other objects:
 *
 * <p>Please Note that we should extend manually other verify and verifier classes for each new added verification here</p>
 */
public interface CStringWaitVerifier extends CStringVerifier, CObjectWaitVerifier<String, CStringState> {

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param size          the int size of new String, negative treated as zero
   * @param padStr        the String to pad the new String with, must not be null or empty
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyCenterPadEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyCenterPadEquals(verifier, size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param size                   the int size of new String, negative treated as zero
   * @param padStr                 the String to pad the new String with, must not be null or empty
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyCenterPadEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).centerPadEquals(size, padStr, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param size          the int size of new String, negative treated as zero
   * @param padStr        the String to pad the new String with, must not be null or empty
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyCenterPadEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final int waitInSeconds) {
    verifyCenterPadEquals(verifier, size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param size                   the int size of new String, negative treated as zero
   * @param padStr                 the String to pad the new String with, must not be null or empty
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyCenterPadEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyCenterPadEquals(verifier, size, padStr, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Center Pad With '%s' And The Length Of '%d' Equals To Expected Value", padStr, size));
  }

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param size          the int size of new String, negative treated as zero
   * @param padStr        the String to pad the new String with, must not be null or empty
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyCenterPadNotEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyCenterPadNotEquals(verifier, size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param size                   the int size of new String, negative treated as zero
   * @param padStr                 the String to pad the new String with, must not be null or empty
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyCenterPadNotEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).centerPadNotEquals(size, padStr, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param size          the int size of new String, negative treated as zero
   * @param padStr        the String to pad the new String with, must not be null or empty
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyCenterPadNotEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final int waitInSeconds) {
    verifyCenterPadNotEquals(verifier, size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param size                   the int size of new String, negative treated as zero
   * @param padStr                 the String to pad the new String with, must not be null or empty
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyCenterPadNotEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyCenterPadNotEquals(verifier, size, padStr, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Center Pad With '%s' And The Length Of '%d' Is Not Equal To Expected Value", padStr, size));
  }

  /**
   * Verify if result of {@link CStringUtil#compare(String, String)} equals to the expected value.
   *
   * @param verifier        CTest, CVerifier or any other verification queue instance
   * @param stringToCompare the string value to compare against
   * @param expected        the expected result.
   * @param waitInSeconds   maximum wait time
   * @param message         information about the purpose of this verification
   * @param params          parameters in case if message is a format {@link String#format}
   */
  default void verifyCompare(CVerificationQueue verifier, String stringToCompare, int expected, final int waitInSeconds, final String message, final Object... params) {
    verifyCompare(verifier, stringToCompare, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#compare(String, String)} equals to the expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param stringToCompare        the string value to compare against
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyCompare(CVerificationQueue verifier, String stringToCompare, int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, stringToCompare, (a, b) -> _toState(a).compare(b, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#compare(String, String)} equals to the expected value.
   *
   * @param verifier        CTest, CVerifier or any other verification queue instance
   * @param stringToCompare the string value to compare against
   * @param expected        the expected result.
   * @param waitInSeconds   maximum wait time
   */
  default void verifyCompare(CVerificationQueue verifier, String stringToCompare, int expected, final int waitInSeconds) {
    verifyCompare(verifier, stringToCompare, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#compare(String, String)} equals to the expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param stringToCompare        the string value to compare against
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyCompare(CVerificationQueue verifier, String stringToCompare, int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyCompare(verifier, stringToCompare, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Result Of Comparison With The Expected Value Is '%d'", expected));
  }

  /**
   * Verify if result of {@link CStringUtil#compareIgnoreCase(String, String)} equals to the
   * expected value.
   *
   * @param verifier        CTest, CVerifier or any other verification queue instance
   * @param stringToCompare the string value to compare against
   * @param expected        the expected result.
   * @param waitInSeconds   maximum wait time
   * @param message         information about the purpose of this verification
   * @param params          parameters in case if message is a format {@link String#format}
   */
  default void verifyCompareIgnoreCase(CVerificationQueue verifier, String stringToCompare, int expected, final int waitInSeconds, final String message, final Object... params) {
    verifyCompareIgnoreCase(verifier, stringToCompare, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#compareIgnoreCase(String, String)} equals to the
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param stringToCompare        the string value to compare against
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyCompareIgnoreCase(CVerificationQueue verifier, String stringToCompare, int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, stringToCompare, (a, b) -> _toState(a).compareIgnoreCase(b, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#compareIgnoreCase(String, String)} equals to the
   * expected value.
   *
   * @param verifier        CTest, CVerifier or any other verification queue instance
   * @param stringToCompare the string value to compare against
   * @param expected        the expected result.
   * @param waitInSeconds   maximum wait time
   */
  default void verifyCompareIgnoreCase(CVerificationQueue verifier, String stringToCompare, int expected, final int waitInSeconds) {
    verifyCompareIgnoreCase(verifier, stringToCompare, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#compareIgnoreCase(String, String)} equals to the
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param stringToCompare        the string value to compare against
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyCompareIgnoreCase(CVerificationQueue verifier, String stringToCompare, int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyCompareIgnoreCase(verifier, stringToCompare, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Result Of Comparison (Ignoring Case) With The Expected Value Is '%d'", expected));
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(CVerificationQueue verifier, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyContains(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).contains(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyContains(CVerificationQueue verifier, final String expected, final int waitInSeconds) {
    verifyContains(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContains(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContains(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Contains The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is true,
   * ignoring case.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsIgnoreCase(CVerificationQueue verifier, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyContainsIgnoreCase(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is true,
   * ignoring case.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsIgnoreCase(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).containsIgnoreCase(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is true,
   * ignoring case.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyContainsIgnoreCase(CVerificationQueue verifier, final String expected, final int waitInSeconds) {
    verifyContainsIgnoreCase(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is true,
   * ignoring case.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContainsIgnoreCase(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContainsIgnoreCase(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Contains The Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is true
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param suffix        the suffix to find, may be {@code null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEndsWith(CVerificationQueue verifier, String suffix, final int waitInSeconds, final String message, final Object... params) {
    verifyEndsWith(verifier, suffix, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is true
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param suffix                 the suffix to find, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEndsWith(CVerificationQueue verifier, String suffix, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, suffix, (a, b) -> _toState(a).endsWith(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is true
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param suffix        the suffix to find, may be {@code null}
   * @param waitInSeconds maximum wait time
   */
  default void verifyEndsWith(CVerificationQueue verifier, String suffix, final int waitInSeconds) {
    verifyEndsWith(verifier, suffix, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is true
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param suffix                 the suffix to find, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEndsWith(CVerificationQueue verifier, String suffix, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEndsWith(verifier, suffix, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Ends With The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param searchInputs  the case-sensitive CharSequences to find, may be empty or contain {@code
   *                      null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEndsWithAny(CVerificationQueue verifier, List<String> searchInputs, final int waitInSeconds, final String message, final Object... params) {
    verifyEndsWithAny(verifier, searchInputs, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param searchInputs           the case-sensitive CharSequences to find, may be empty or contain {@code
   *                               null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEndsWithAny(CVerificationQueue verifier, List<String> searchInputs, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, searchInputs, (a, b) -> _toState(a).endsWithAny(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param searchInputs  the case-sensitive CharSequences to find, may be empty or contain {@code
   *                      null}
   * @param waitInSeconds maximum wait time
   */
  default void verifyEndsWithAny(CVerificationQueue verifier, List<String> searchInputs, final int waitInSeconds) {
    verifyEndsWithAny(verifier, searchInputs, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param searchInputs           the case-sensitive CharSequences to find, may be empty or contain {@code
   *                               null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEndsWithAny(CVerificationQueue verifier, List<String> searchInputs, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEndsWithAny(verifier, searchInputs, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Ends With Any Value From The Expected Values"));
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param suffix        the suffix to find, may be {@code null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEndsWithIgnoreCase(CVerificationQueue verifier, String suffix, final int waitInSeconds, final String message, final Object... params) {
    verifyEndsWithIgnoreCase(verifier, suffix, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param suffix                 the suffix to find, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEndsWithIgnoreCase(CVerificationQueue verifier, String suffix, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, suffix, (a, b) -> _toState(a).endsWithIgnoreCase(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param suffix        the suffix to find, may be {@code null}
   * @param waitInSeconds maximum wait time
   */
  default void verifyEndsWithIgnoreCase(CVerificationQueue verifier, String suffix, final int waitInSeconds) {
    verifyEndsWithIgnoreCase(verifier, suffix, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param suffix                 the suffix to find, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEndsWithIgnoreCase(CVerificationQueue verifier, String suffix, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEndsWithIgnoreCase(verifier, suffix, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Ends With The Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param searchInputs  the case-sensitive CharSequences to find, may be empty or contain {@code
   *                      null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEndsWithNone(CVerificationQueue verifier, List<String> searchInputs, final int waitInSeconds, final String message, final Object... params) {
    verifyEndsWithNone(verifier, searchInputs, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param searchInputs           the case-sensitive CharSequences to find, may be empty or contain {@code
   *                               null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEndsWithNone(CVerificationQueue verifier, List<String> searchInputs, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, searchInputs, (a, b) -> _toState(a).endsWithNone(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param searchInputs  the case-sensitive CharSequences to find, may be empty or contain {@code
   *                      null}
   * @param waitInSeconds maximum wait time
   */
  default void verifyEndsWithNone(CVerificationQueue verifier, List<String> searchInputs, final int waitInSeconds) {
    verifyEndsWithNone(verifier, searchInputs, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param searchInputs           the case-sensitive CharSequences to find, may be empty or contain {@code
   *                               null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEndsWithNone(CVerificationQueue verifier, List<String> searchInputs, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEndsWithNone(verifier, searchInputs, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Ends With None Of Value From The Expected Values"));
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)}
   * equals to the expected value, ignoring case.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expectedList  a list of strings, may be {@code null}.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsAnyIgnoreCase(CVerificationQueue verifier, List<String> expectedList, final int waitInSeconds, final String message, final Object... params) {
    verifyEqualsAnyIgnoreCase(verifier, expectedList, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)}
   * equals to the expected value, ignoring case.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expectedList           a list of strings, may be {@code null}.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsAnyIgnoreCase(CVerificationQueue verifier, List<String> expectedList, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expectedList, (a, b) -> _toState(a).equalsAnyIgnoreCase(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)}
   * equals to the expected value, ignoring case.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expectedList  a list of strings, may be {@code null}.
   * @param waitInSeconds maximum wait time
   */
  default void verifyEqualsAnyIgnoreCase(CVerificationQueue verifier, List<String> expectedList, final int waitInSeconds) {
    verifyEqualsAnyIgnoreCase(verifier, expectedList, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)}
   * equals to the expected value, ignoring case.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expectedList           a list of strings, may be {@code null}.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEqualsAnyIgnoreCase(CVerificationQueue verifier, List<String> expectedList, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEqualsAnyIgnoreCase(verifier, expectedList, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Ends With Any Value From The Expected Values Ignoring Case Sensitivity"));
  }

  /**
   * Verify if {@link CStringUtil#equalsIgnoreCase(CharSequence, CharSequence)} value equals the
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsIgnoreCase(CVerificationQueue verifier, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyEqualsIgnoreCase(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#equalsIgnoreCase(CharSequence, CharSequence)} value equals the
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsIgnoreCase(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).equalsIgnoreCase(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#equalsIgnoreCase(CharSequence, CharSequence)} value equals the
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyEqualsIgnoreCase(CVerificationQueue verifier, final String expected, final int waitInSeconds) {
    verifyEqualsIgnoreCase(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#equalsIgnoreCase(CharSequence, CharSequence)} value equals the
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEqualsIgnoreCase(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEqualsIgnoreCase(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Equals The Expected Values Ignoring Case Sensitivity"));
  }

  /**
   * Verify if value is equal to expected after removing all WhiteSpaces from both.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsIgnoreWhiteSpaces(CVerificationQueue verifier, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyEqualsIgnoreWhiteSpaces(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if value is equal to expected after removing all WhiteSpaces from both.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsIgnoreWhiteSpaces(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).equalsIgnoreWhiteSpaces(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if value is equal to expected after removing all WhiteSpaces from both.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyEqualsIgnoreWhiteSpaces(CVerificationQueue verifier, final String expected, final int waitInSeconds) {
    verifyEqualsIgnoreWhiteSpaces(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if value is equal to expected after removing all WhiteSpaces from both.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEqualsIgnoreWhiteSpaces(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEqualsIgnoreWhiteSpaces(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Equals The Expected Values Ignoring White Spaces"));
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)} is
   * false, ignoring case.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expectedList  a list of strings, may be {@code null}.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsNoneIgnoreCase(CVerificationQueue verifier, List<String> expectedList, final int waitInSeconds, final String message, final Object... params) {
    verifyEqualsNoneIgnoreCase(verifier, expectedList, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)} is
   * false, ignoring case.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expectedList           a list of strings, may be {@code null}.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsNoneIgnoreCase(CVerificationQueue verifier, List<String> expectedList, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expectedList, (a, b) -> _toState(a).equalsNoneIgnoreCase(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)} is
   * false, ignoring case.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expectedList  a list of strings, may be {@code null}.
   * @param waitInSeconds maximum wait time
   */
  default void verifyEqualsNoneIgnoreCase(CVerificationQueue verifier, List<String> expectedList, final int waitInSeconds) {
    verifyEqualsNoneIgnoreCase(verifier, expectedList, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)} is
   * false, ignoring case.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expectedList           a list of strings, may be {@code null}.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEqualsNoneIgnoreCase(CVerificationQueue verifier, List<String> expectedList, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEqualsNoneIgnoreCase(verifier, expectedList, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Ends With None Of Value From The Expected Values Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAlpha(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsAlpha(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAlpha(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isAlpha(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsAlpha(final CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsAlpha(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsAlpha(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsAlpha(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Contains Only Alpha Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAlphaSpace(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsAlphaSpace(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAlphaSpace(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isAlphaSpace(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsAlphaSpace(final CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsAlphaSpace(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsAlphaSpace(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsAlphaSpace(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Contains Only Alpha Or Space Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAlphanumeric(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsAlphanumeric(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAlphanumeric(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isAlphanumeric(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsAlphanumeric(final CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsAlphanumeric(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsAlphanumeric(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsAlphanumeric(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Contains Only Alpha-Numeric Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAlphanumericSpace(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsAlphanumericSpace(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAlphanumericSpace(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isAlphanumericSpace(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsAlphanumericSpace(CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsAlphanumericSpace(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsAlphanumericSpace(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsAlphanumericSpace(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Contains Only Alpha-Numeric Or Space Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAsciiPrintable(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsAsciiPrintable(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAsciiPrintable(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isAsciiPrintable(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsAsciiPrintable(CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsAsciiPrintable(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsAsciiPrintable(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsAsciiPrintable(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Contains Only Ascii Printable Characters"));
  }

  /**
   * Verify if String value is blank (Null or Empty)
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlank(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsBlank(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if String value is blank (Null or Empty)
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlank(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, "<Blank>", (a, b) -> _toState(a).isBlank(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if String value is blank (Null or Empty)
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsBlank(final CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsBlank(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if String value is blank (Null or Empty)
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsBlank(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsBlank(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Blank"));
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrAlpha(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsBlankOrAlpha(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrAlpha(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isBlankOrAlpha(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsBlankOrAlpha(final CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsBlankOrAlpha(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsBlankOrAlpha(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsBlankOrAlpha(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Contains Only Alpha Characters Or It Is Blank"));
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrAlphanumeric(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsBlankOrAlphanumeric(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrAlphanumeric(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isBlankOrAlphanumeric(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsBlankOrAlphanumeric(CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsBlankOrAlphanumeric(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsBlankOrAlphanumeric(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsBlankOrAlphanumeric(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Contains Only Alpha-Numeric Characters Or It Is Blank"));
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrNotAlpha(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsBlankOrNotAlpha(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrNotAlpha(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isBlankOrNotAlpha(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsBlankOrNotAlpha(CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsBlankOrNotAlpha(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsBlankOrNotAlpha(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsBlankOrNotAlpha(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Blank Or Not Contains Only Alpha-Numeric Characters"));
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrNotAlphanumeric(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsBlankOrNotAlphanumeric(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrNotAlphanumeric(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isBlankOrNotAlphanumeric(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsBlankOrNotAlphanumeric(CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsBlankOrNotAlphanumeric(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsBlankOrNotAlphanumeric(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsBlankOrNotAlphanumeric(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Blank Or Not Contains Only Alpha-Numeric Characters"));
  }

  /**
   * Verify if string is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrNotNumeric(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsBlankOrNotNumeric(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if string is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrNotNumeric(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isBlankOrNotNumeric(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if string is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsBlankOrNotNumeric(CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsBlankOrNotNumeric(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if string is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsBlankOrNotNumeric(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsBlankOrNotNumeric(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Blank Or Not Contains Only Numeric Characters"));
  }

  /**
   * Verify if string is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrNumeric(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsBlankOrNumeric(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if string is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrNumeric(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isBlankOrNumeric(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if string is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsBlankOrNumeric(CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsBlankOrNumeric(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if string is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsBlankOrNumeric(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsBlankOrNumeric(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Blank Or Not Contains Only Numeric Characters"));
  }

  /**
   * Verify if String value is empty
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmpty(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsEmpty(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if String value is empty
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmpty(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, "<Empty>", (a, b) -> _toState(a).isEmpty(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if String value is empty
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsEmpty(final CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsEmpty(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if String value is empty
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsEmpty(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsEmpty(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Empty"));
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrAlpha(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsEmptyOrAlpha(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrAlpha(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isEmptyOrAlpha(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsEmptyOrAlpha(final CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsEmptyOrAlpha(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsEmptyOrAlpha(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsEmptyOrAlpha(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Empty Or Contains Only Alpha Characters"));
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrAlphanumeric(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsEmptyOrAlphanumeric(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrAlphanumeric(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isEmptyOrAlphanumeric(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsEmptyOrAlphanumeric(CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsEmptyOrAlphanumeric(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsEmptyOrAlphanumeric(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsEmptyOrAlphanumeric(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Empty Or Contains Only Alpha-Numeric Characters"));
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrNotAlpha(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsEmptyOrNotAlpha(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrNotAlpha(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isEmptyOrNotAlpha(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsEmptyOrNotAlpha(CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsEmptyOrNotAlpha(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsEmptyOrNotAlpha(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsEmptyOrNotAlpha(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Empty Or Not Contains Only Alpha Characters"));
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrNotAlphanumeric(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsEmptyOrNotAlphanumeric(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrNotAlphanumeric(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isEmptyOrNotAlphanumeric(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsEmptyOrNotAlphanumeric(CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsEmptyOrNotAlphanumeric(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsEmptyOrNotAlphanumeric(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsEmptyOrNotAlphanumeric(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Empty Or Not Contains Only Alpha-Numeric Characters"));
  }

  /**
   * Verify if string is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrNotNumeric(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsEmptyOrNotNumeric(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if string is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrNotNumeric(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isEmptyOrNotNumeric(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if string is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsEmptyOrNotNumeric(CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsEmptyOrNotNumeric(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if string is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsEmptyOrNotNumeric(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsEmptyOrNotNumeric(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Empty Or Not Contains Only Numeric Characters"));
  }

  /**
   * Verify if string is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrNumeric(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsEmptyOrNumeric(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if string is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrNumeric(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isEmptyOrNumeric(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if string is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsEmptyOrNumeric(CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsEmptyOrNumeric(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if string is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsEmptyOrNumeric(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsEmptyOrNumeric(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Empty Or Contains Only Numeric Characters"));
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param pattern       regular expression pattern
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyMatches(CVerificationQueue verifier, final Pattern pattern, final int waitInSeconds, final String message, final Object... params) {
    verifyMatches(verifier, pattern, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param pattern                regular expression pattern
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyMatches(CVerificationQueue verifier, final Pattern pattern, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, pattern, (a, b) -> _toState(a).matches(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if String value match any of provided pattern
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param patterns      regular expression patterns
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyMatchesAny(CVerificationQueue verifier, final List<Pattern> patterns, final int waitInSeconds, final String message, final Object... params) {
    verifyMatchesAny(verifier, patterns, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if String value match any of provided pattern
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param patterns               regular expression patterns
   * @param waitInSeconds          maximum wait time
   */
  default void verifyMatchesAny(CVerificationQueue verifier, final List<Pattern> patterns, final int waitInSeconds) {
    verifyMatchesAny(verifier, patterns, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if String value match any of provided pattern
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param patterns               regular expression patterns
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyMatchesAny(CVerificationQueue verifier, final List<Pattern> patterns, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyMatchesAny(verifier, patterns, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), getDefaultMessage("Value Matches Any Of the Provided Patterns"));
  }

  /**
   * Verify if String value match any of provided pattern
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param patterns               regular expression patterns
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyMatchesAny(CVerificationQueue verifier, final List<Pattern> patterns, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, patterns, (a, b) -> _toState(a).matchAny(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if String value match NONE of provided pattern
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param patterns      regular expression patterns
   * @param waitInSeconds maximum wait time
   */
  default void verifyMatchesNone(CVerificationQueue verifier, final List<Pattern> patterns, final int waitInSeconds) {
    verifyMatchesNone(verifier, patterns, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if String value match NONE of provided pattern
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param patterns      regular expression patterns
   * @param waitInSeconds maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyMatchesNone(CVerificationQueue verifier, final List<Pattern> patterns, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyMatchesNone(verifier, patterns, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Matches None Of Provided Pattern"));
  }

  /**
   * Verify if String value match NONE of provided pattern
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param patterns      regular expression patterns
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyMatchesNone(CVerificationQueue verifier, final List<Pattern> patterns, final int waitInSeconds, final String message, final Object... params) {
    verifyMatchesNone(verifier, patterns, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if String value match NONE of provided pattern
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param patterns               regular expression patterns
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyMatchesNone(CVerificationQueue verifier, final List<Pattern> patterns, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, patterns, (a, b) -> _toState(a).matchNone(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param pattern       regular expression pattern
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyMatches(CVerificationQueue verifier, final String pattern, final int waitInSeconds, final String message, final Object... params) {
    verifyMatches(verifier, pattern, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param pattern                regular expression pattern
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyMatches(CVerificationQueue verifier, final String pattern, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, pattern, (a, b) -> _toState(a).matches(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param pattern       regular expression pattern
   * @param waitInSeconds maximum wait time
   */
  default void verifyMatches(CVerificationQueue verifier, final Pattern pattern, final int waitInSeconds) {
    verifyMatches(verifier, pattern, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param pattern       regular expression pattern
   * @param waitInSeconds maximum wait time
   */
  default void verifyMatches(CVerificationQueue verifier, final String pattern, final int waitInSeconds) {
    verifyMatches(verifier, pattern, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param pattern                regular expression pattern
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyMatches(CVerificationQueue verifier, final Pattern pattern, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyMatches(verifier, pattern, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Matches The Provided Pattern"));
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param pattern                regular expression pattern
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyMatches(CVerificationQueue verifier, final String pattern, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyMatches(verifier, pattern, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Matches The Provided Pattern"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAlpha(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsNotAlpha(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAlpha(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isNotAlpha(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotAlpha(final CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsNotAlpha(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotAlpha(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNotAlpha(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Not Contains Only Alpha Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAlphaSpace(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsNotAlphaSpace(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAlphaSpace(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isNotAlphaSpace(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotAlphaSpace(CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsNotAlphaSpace(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotAlphaSpace(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNotAlphaSpace(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Not Contains Only Alpha Characters Or Space"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAlphanumeric(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsNotAlphanumeric(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAlphanumeric(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isNotAlphanumeric(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotAlphanumeric(CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsNotAlphanumeric(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotAlphanumeric(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNotAlphanumeric(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Not Contains Only Alpha-Numeric Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAlphanumericSpace(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsNotAlphanumericSpace(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAlphanumericSpace(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isNotAlphanumericSpace(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotAlphanumericSpace(CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsNotAlphanumericSpace(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotAlphanumericSpace(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNotAlphanumericSpace(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Not Contains Only Alpha-Numeric Characters Or Space"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAsciiPrintable(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsNotAsciiPrintable(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAsciiPrintable(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isNotAsciiPrintable(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotAsciiPrintable(CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsNotAsciiPrintable(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotAsciiPrintable(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNotAsciiPrintable(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Not Contains Only Ascii Printable Characters"));
  }

  /**
   * Verify if String value is not blank (Null or Empty)
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotBlank(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsNotBlank(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if String value is not blank (Null or Empty)
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotBlank(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, "<Not Blank>", (a, b) -> _toState(a).isNotBlank(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if String value is not blank (Null or Empty)
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotBlank(final CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsNotBlank(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if String value is not blank (Null or Empty)
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotBlank(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNotBlank(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Not Blank"));
  }

  /**
   * Verify String value is not empty
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotEmpty(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsNotEmpty(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify String value is not empty
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotEmpty(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, "<Not Empty>", (a, b) -> _toState(a).isNotEmpty(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify String value is not empty
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotEmpty(final CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsNotEmpty(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify String value is not empty
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotEmpty(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNotEmpty(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Not Empty"));
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param pattern       regular expression pattern
   */
  default void verifyNotMatches(CVerificationQueue verifier, final Pattern pattern, final int waitInSeconds, final String message, final Object... params) {
    verifyNotMatches(verifier, pattern, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param pattern                regular expression pattern
   */
  default void verifyNotMatches(CVerificationQueue verifier, final Pattern pattern, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, pattern, (a, b) -> _toState(a).notMatches(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param pattern       regular expression pattern
   */
  default void verifyNotMatches(CVerificationQueue verifier, final String pattern, final int waitInSeconds, final String message, final Object... params) {
    verifyNotMatches(verifier, pattern, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param pattern                regular expression pattern
   */
  default void verifyNotMatches(CVerificationQueue verifier, final String pattern, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, pattern, (a, b) -> _toState(a).notMatches(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param pattern       regular expression pattern
   */
  default void verifyNotMatches(CVerificationQueue verifier, final Pattern pattern, final int waitInSeconds) {
    verifyNotMatches(verifier, pattern, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param pattern                regular expression pattern
   */
  default void verifyNotMatches(CVerificationQueue verifier, final Pattern pattern, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotMatches(verifier, pattern, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Not Match The Expected Pattern"));
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param pattern       regular expression pattern
   */
  default void verifyNotMatches(CVerificationQueue verifier, final String pattern, final int waitInSeconds) {
    verifyNotMatches(verifier, pattern, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param pattern                regular expression pattern
   */
  default void verifyNotMatches(CVerificationQueue verifier, final String pattern, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotMatches(verifier, pattern, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Not Match The Expected Pattern"));
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotNumeric(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsNotNumeric(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotNumeric(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isNotNumeric(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotNumeric(final CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsNotNumeric(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotNumeric(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNotNumeric(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Not Contains Only Numeric Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotNumericSpace(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsNotNumericSpace(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotNumericSpace(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isNotNumericSpace(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotNumericSpace(CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsNotNumericSpace(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotNumericSpace(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNotNumericSpace(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Not Contains Only Numeric Or Space Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNumeric(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsNumeric(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNumeric(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isNumeric(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNumeric(final CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsNumeric(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNumeric(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNumeric(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Contains Only Numeric Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNumericSpace(CVerificationQueue verifier, final int waitInSeconds, final String message, final Object... params) {
    verifyIsNumericSpace(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNumericSpace(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isNumericSpace(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is true.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNumericSpace(final CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsNumericSpace(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is true.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNumericSpace(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNumericSpace(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Contains Only Numeric Or Space Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param size          the size to pad to
   * @param padStr        the String to pad with, null or empty treated as single space
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyLeftPadEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyLeftPadEquals(verifier, size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param size                   the size to pad to
   * @param padStr                 the String to pad with, null or empty treated as single space
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyLeftPadEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).leftPadEquals(size, padStr, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param size          the size to pad to
   * @param padStr        the String to pad with, null or empty treated as single space
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyLeftPadEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final int waitInSeconds) {
    verifyLeftPadEquals(verifier, size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param size                   the size to pad to
   * @param padStr                 the String to pad with, null or empty treated as single space
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyLeftPadEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyLeftPadEquals(verifier, size, padStr, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Expected Value Equals To The Actual Value Left Pad With '%s' And The Length Of '%d'", padStr, size));
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param size          the size to pad to
   * @param padStr        the String to pad with, null or empty treated as single space
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyLeftPadNotEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyLeftPadNotEquals(verifier, size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param size                   the size to pad to
   * @param padStr                 the String to pad with, null or empty treated as single space
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyLeftPadNotEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).leftPadNotEquals(size, padStr, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param size          the size to pad to
   * @param padStr        the String to pad with, null or empty treated as single space
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyLeftPadNotEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final int waitInSeconds) {
    verifyLeftPadNotEquals(verifier, size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param size                   the size to pad to
   * @param padStr                 the String to pad with, null or empty treated as single space
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyLeftPadNotEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyLeftPadNotEquals(verifier, size, padStr, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Expected Value Not Equals To The Actual Value Left Pad With '%s' And The Length Of '%d'", padStr, size));
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyLeftValueEquals(CVerificationQueue verifier, int len, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyLeftValueEquals(verifier, len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyLeftValueEquals(CVerificationQueue verifier, int len, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).leftValueEquals(len, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time
   */
  default void verifyLeftValueEquals(CVerificationQueue verifier, int len, final String expected, final int waitInSeconds) {
    verifyLeftValueEquals(verifier, len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyLeftValueEquals(CVerificationQueue verifier, int len, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyLeftValueEquals(verifier, len, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Expected Value Equals To The Left '%d' Character Of Actual Value", len));
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} NOT equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyLeftValueNotEquals(CVerificationQueue verifier, int len, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyLeftValueNotEquals(verifier, len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} NOT equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyLeftValueNotEquals(CVerificationQueue verifier, int len, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).leftValueNotEquals(len, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} NOT equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time
   */
  default void verifyLeftValueNotEquals(CVerificationQueue verifier, int len, final String expected, final int waitInSeconds) {
    verifyLeftValueNotEquals(verifier, len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} NOT equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyLeftValueNotEquals(CVerificationQueue verifier, int len, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyLeftValueNotEquals(verifier, len, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Expected Value Not Equals To The Left '%d' Character Of Actual Value", len));
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyLengthEquals(CVerificationQueue verifier, int expected, final int waitInSeconds, final String message, final Object... params) {
    verifyLengthEquals(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyLengthEquals(CVerificationQueue verifier, int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).lengthEquals(expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyLengthEquals(CVerificationQueue verifier, int expected, final int waitInSeconds) {
    verifyLengthEquals(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyLengthEquals(CVerificationQueue verifier, int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyLengthEquals(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Expected Value Length Equals To The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is NOT equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyLengthNotEquals(CVerificationQueue verifier, int expected, final int waitInSeconds, final String message, final Object... params) {
    verifyLengthNotEquals(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is NOT equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyLengthNotEquals(CVerificationQueue verifier, int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).lengthNotEquals(expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is NOT equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyLengthNotEquals(CVerificationQueue verifier, int expected, final int waitInSeconds) {
    verifyLengthNotEquals(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is NOT equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyLengthNotEquals(CVerificationQueue verifier, int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyLengthNotEquals(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Expected Value Length Not Equals To The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param pos           the position to start from, negative treated as zero
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyMidValueEquals(CVerificationQueue verifier, int pos, int len, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyMidValueEquals(verifier, pos, len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param pos                    the position to start from, negative treated as zero
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyMidValueEquals(CVerificationQueue verifier, int pos, int len, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).midValueEquals(pos, len, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param pos           the position to start from, negative treated as zero
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time
   */
  default void verifyMidValueEquals(CVerificationQueue verifier, int pos, int len, final String expected, final int waitInSeconds) {
    verifyMidValueEquals(verifier, pos, len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param pos                    the position to start from, negative treated as zero
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyMidValueEquals(CVerificationQueue verifier, int pos, int len, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyMidValueEquals(verifier, pos, len, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Expected Value Equals To The Characters Of Actual Value From Position '%d' For '%d' Length", pos, len));
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} NOT equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param pos           the position to start from, negative treated as zero
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyMidValueNotEquals(CVerificationQueue verifier, int pos, int len, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyMidValueNotEquals(verifier, pos, len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} NOT equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param pos                    the position to start from, negative treated as zero
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyMidValueNotEquals(CVerificationQueue verifier, int pos, int len, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).midValueNotEquals(pos, len, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} NOT equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param pos           the position to start from, negative treated as zero
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time
   */
  default void verifyMidValueNotEquals(CVerificationQueue verifier, int pos, int len, final String expected, final int waitInSeconds) {
    verifyMidValueNotEquals(verifier, pos, len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} NOT equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param pos                    the position to start from, negative treated as zero
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyMidValueNotEquals(CVerificationQueue verifier, int pos, int len, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyMidValueNotEquals(verifier, pos, len, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Expected Value Not Equals To The Characters Of Actual Value From Position '%d' For '%d' Length", pos, len));
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(CVerificationQueue verifier, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContains(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).notContains(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotContains(CVerificationQueue verifier, final String expected, final int waitInSeconds) {
    verifyNotContains(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContains(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContains(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Not Contains The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContainsIgnoreCase(CVerificationQueue verifier, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContainsIgnoreCase(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContainsIgnoreCase(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).notContainsIgnoreCase(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotContainsIgnoreCase(CVerificationQueue verifier, final String expected, final int waitInSeconds) {
    verifyNotContainsIgnoreCase(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContainsIgnoreCase(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContainsIgnoreCase(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Not Contains The Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is false
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param suffix        the suffix to find, may be {@code null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEndsWith(CVerificationQueue verifier, String suffix, final int waitInSeconds, final String message, final Object... params) {
    verifyNotEndsWith(verifier, suffix, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is false
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param suffix                 the suffix to find, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEndsWith(CVerificationQueue verifier, String suffix, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, suffix, (a, b) -> _toState(a).notEndsWith(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is false
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param suffix        the suffix to find, may be {@code null}
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotEndsWith(CVerificationQueue verifier, String suffix, final int waitInSeconds) {
    verifyNotEndsWith(verifier, suffix, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is false
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param suffix                 the suffix to find, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotEndsWith(CVerificationQueue verifier, String suffix, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotEndsWith(verifier, suffix, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Not Ends With The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param suffix        the suffix to find, may be {@code null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEndsWithIgnoreCase(CVerificationQueue verifier, String suffix, final int waitInSeconds, final String message, final Object... params) {
    verifyNotEndsWithIgnoreCase(verifier, suffix, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param suffix                 the suffix to find, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEndsWithIgnoreCase(CVerificationQueue verifier, String suffix, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, suffix, (a, b) -> _toState(a).notEndsWithIgnoreCase(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param suffix        the suffix to find, may be {@code null}
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotEndsWithIgnoreCase(CVerificationQueue verifier, String suffix, final int waitInSeconds) {
    verifyNotEndsWithIgnoreCase(verifier, suffix, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param suffix                 the suffix to find, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotEndsWithIgnoreCase(CVerificationQueue verifier, String suffix, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotEndsWithIgnoreCase(verifier, suffix, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Not Ends With The Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if {@link CStringUtil#equalsIgnoreCase(CharSequence, CharSequence)} value NOT equals the
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsIgnoreCase(CVerificationQueue verifier, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNotEqualsIgnoreCase(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#equalsIgnoreCase(CharSequence, CharSequence)} value NOT equals the
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsIgnoreCase(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).notEqualsIgnoreCase(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#equalsIgnoreCase(CharSequence, CharSequence)} value NOT equals the
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotEqualsIgnoreCase(CVerificationQueue verifier, final String expected, final int waitInSeconds) {
    verifyNotEqualsIgnoreCase(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#equalsIgnoreCase(CharSequence, CharSequence)} value NOT equals the
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotEqualsIgnoreCase(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotEqualsIgnoreCase(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Not Equals To The Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if value is not equal to expected after removing all WhiteSpaces from both.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsIgnoreWhiteSpaces(CVerificationQueue verifier, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNotEqualsIgnoreWhiteSpaces(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if value is not equal to expected after removing all WhiteSpaces from both.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsIgnoreWhiteSpaces(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).notEqualsIgnoreWhiteSpaces(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if value is not equal to expected after removing all WhiteSpaces from both.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotEqualsIgnoreWhiteSpaces(CVerificationQueue verifier, final String expected, final int waitInSeconds) {
    verifyNotEqualsIgnoreWhiteSpaces(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if value is not equal to expected after removing all WhiteSpaces from both.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotEqualsIgnoreWhiteSpaces(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotEqualsIgnoreWhiteSpaces(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Not Equals To The Expected Value Ignoring White Spaces"));
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is false
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotStartsWith(CVerificationQueue verifier, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNotStartsWith(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is false
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotStartsWith(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).notStartsWith(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is false
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotStartsWith(CVerificationQueue verifier, final String expected, final int waitInSeconds) {
    verifyNotStartsWith(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is false
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotStartsWith(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotStartsWith(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Not Starts With The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * false
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotStartsWithIgnoreCase(CVerificationQueue verifier, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNotStartsWithIgnoreCase(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * false
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotStartsWithIgnoreCase(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).notStartsWithIgnoreCase(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * false
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotStartsWithIgnoreCase(CVerificationQueue verifier, final String expected, final int waitInSeconds) {
    verifyNotStartsWithIgnoreCase(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * false
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotStartsWithIgnoreCase(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotStartsWithIgnoreCase(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Not Starts With The Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param subString     the substring to count, may be null
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNumberOfMatchesEquals(CVerificationQueue verifier, String subString, int expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNumberOfMatchesEquals(verifier, subString, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param subString              the substring to count, may be null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNumberOfMatchesEquals(CVerificationQueue verifier, String subString, int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).numberOfMatchesEquals(subString, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param subString     the substring to count, may be null
   * @param waitInSeconds maximum wait time
   */
  default void verifyNumberOfMatchesEquals(CVerificationQueue verifier, String subString, int expected, final int waitInSeconds) {
    verifyNumberOfMatchesEquals(verifier, subString, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param subString              the substring to count, may be null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNumberOfMatchesEquals(CVerificationQueue verifier, String subString, int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNumberOfMatchesEquals(verifier, subString, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Contains Exact Number Of Substring"));
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is NOT equals
   * to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param subString     the substring to count, may be null
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNumberOfMatchesNotEquals(CVerificationQueue verifier, String subString, int expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNumberOfMatchesNotEquals(verifier, subString, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is NOT equals
   * to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param subString              the substring to count, may be null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNumberOfMatchesNotEquals(CVerificationQueue verifier, String subString, int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).numberOfMatchesNotEquals(subString, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is NOT equals
   * to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param subString     the substring to count, may be null
   * @param waitInSeconds maximum wait time
   */
  default void verifyNumberOfMatchesNotEquals(CVerificationQueue verifier, String subString, int expected, final int waitInSeconds) {
    verifyNumberOfMatchesNotEquals(verifier, subString, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is NOT equals
   * to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param subString              the substring to count, may be null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNumberOfMatchesNotEquals(CVerificationQueue verifier, String subString, int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNumberOfMatchesNotEquals(verifier, subString, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Not Contains Exact Number Of Substring"));
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEndEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRemoveEndEquals(verifier, remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEndEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).removeEndEquals(remove, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRemoveEndEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds) {
    verifyRemoveEndEquals(verifier, remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRemoveEndEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRemoveEndEquals(verifier, remove, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Removing '%s' From End, Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEndIgnoreCaseEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRemoveEndIgnoreCaseEquals(verifier, remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEndIgnoreCaseEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).removeEndIgnoreCaseEquals(remove, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRemoveEndIgnoreCaseEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds) {
    verifyRemoveEndIgnoreCaseEquals(verifier, remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRemoveEndIgnoreCaseEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRemoveEndIgnoreCaseEquals(verifier, remove, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Removing '%s' From End Ignoring Case Sensitivity, Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEndIgnoreCaseNotEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRemoveEndIgnoreCaseNotEquals(verifier, remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEndIgnoreCaseNotEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).removeEndIgnoreCaseNotEquals(remove, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRemoveEndIgnoreCaseNotEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds) {
    verifyRemoveEndIgnoreCaseNotEquals(verifier, remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRemoveEndIgnoreCaseNotEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRemoveEndIgnoreCaseNotEquals(verifier, remove, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Removing '%s' From End Ignoring Case Sensitivity, Not Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is NOT equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEndNotEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRemoveEndNotEquals(verifier, remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is NOT equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEndNotEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).removeEndNotEquals(remove, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is NOT equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRemoveEndNotEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds) {
    verifyRemoveEndNotEquals(verifier, remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is NOT equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRemoveEndNotEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRemoveEndNotEquals(verifier, remove, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Removing '%s', Not Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRemoveEquals(verifier, remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).removeEquals(remove, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRemoveEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds) {
    verifyRemoveEquals(verifier, remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRemoveEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRemoveEquals(verifier, remove, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Removing '%s', Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveIgnoreCaseEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRemoveIgnoreCaseEquals(verifier, remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveIgnoreCaseEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).removeIgnoreCaseEquals(remove, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRemoveIgnoreCaseEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds) {
    verifyRemoveIgnoreCaseEquals(verifier, remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRemoveIgnoreCaseEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRemoveIgnoreCaseEquals(verifier, remove, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Removing '%s' Ignoring Case Sensitivity, Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveIgnoreCaseNotEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRemoveIgnoreCaseNotEquals(verifier, remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveIgnoreCaseNotEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).removeIgnoreCaseNotEquals(remove, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRemoveIgnoreCaseNotEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds) {
    verifyRemoveIgnoreCaseNotEquals(verifier, remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRemoveIgnoreCaseNotEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRemoveIgnoreCaseNotEquals(verifier, remove, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Removing '%s' Ignoring Case Sensitivity, Not Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is NOT equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveNotEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRemoveNotEquals(verifier, remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is NOT equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveNotEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).removeNotEquals(remove, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is NOT equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRemoveNotEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds) {
    verifyRemoveNotEquals(verifier, remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is NOT equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRemoveNotEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRemoveNotEquals(verifier, remove, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Removing '%s', Not Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveStartEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRemoveStartEquals(verifier, remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveStartEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).removeStartEquals(remove, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRemoveStartEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds) {
    verifyRemoveStartEquals(verifier, remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRemoveStartEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRemoveStartEquals(verifier, remove, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Removing '%s' From Start, Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveStartIgnoreCaseEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRemoveStartIgnoreCaseEquals(verifier, remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveStartIgnoreCaseEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).removeStartIgnoreCaseEquals(remove, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRemoveStartIgnoreCaseEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds) {
    verifyRemoveStartIgnoreCaseEquals(verifier, remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRemoveStartIgnoreCaseEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRemoveStartIgnoreCaseEquals(verifier, remove, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Removing '%s' From Start Ignoring Case Sensitivity, Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveStartIgnoreCaseNotEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRemoveStartIgnoreCaseNotEquals(verifier, remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveStartIgnoreCaseNotEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).removeStartIgnoreCaseNotEquals(remove, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRemoveStartIgnoreCaseNotEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds) {
    verifyRemoveStartIgnoreCaseNotEquals(verifier, remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRemoveStartIgnoreCaseNotEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRemoveStartIgnoreCaseNotEquals(verifier, remove, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Removing '%s' From Start Ignoring Case Sensitivity, Not Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is NOT equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveStartNotEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRemoveStartNotEquals(verifier, remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is NOT equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveStartNotEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).removeStartNotEquals(remove, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is NOT equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRemoveStartNotEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds) {
    verifyRemoveStartNotEquals(verifier, remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is NOT equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRemoveStartNotEquals(CVerificationQueue verifier, String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRemoveStartNotEquals(verifier, remove, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Removing '%s' From Start, Not Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace it with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyReplaceEquals(verifier, searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace it with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).replaceEquals(searchString, replacement, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace it with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyReplaceEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds) {
    verifyReplaceEquals(verifier, searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace it with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyReplaceEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyReplaceEquals(verifier, searchString, replacement, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Replacing '%s' With '%s', Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace it with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceIgnoreCaseEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyReplaceIgnoreCaseEquals(verifier, searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace it with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceIgnoreCaseEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).replaceIgnoreCaseEquals(searchString, replacement, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace it with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyReplaceIgnoreCaseEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds) {
    verifyReplaceIgnoreCaseEquals(verifier, searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace it with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyReplaceIgnoreCaseEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyReplaceIgnoreCaseEquals(verifier, searchString, replacement, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Replacing '%s' With '%s' Ignoring Case Sensitivity, Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is NOT equals
   * to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace it with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceIgnoreCaseNotEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyReplaceIgnoreCaseNotEquals(verifier, searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is NOT equals
   * to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace it with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceIgnoreCaseNotEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).replaceIgnoreCaseNotEquals(searchString, replacement, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is NOT equals
   * to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace it with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyReplaceIgnoreCaseNotEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds) {
    verifyReplaceIgnoreCaseNotEquals(verifier, searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is NOT equals
   * to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace it with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyReplaceIgnoreCaseNotEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyReplaceIgnoreCaseNotEquals(verifier, searchString, replacement, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Replacing '%s' With '%s' Ignoring Case Sensitivity, Not Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace it with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceNotEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyReplaceNotEquals(verifier, searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace it with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceNotEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).replaceNotEquals(searchString, replacement, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace it with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyReplaceNotEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds) {
    verifyReplaceNotEquals(verifier, searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace it with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyReplaceNotEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyReplaceNotEquals(verifier, searchString, replacement, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Replacing '%s' With '%s', Not Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param searchString  the String to search for, may be null
   * @param replacement   the String to replace with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceOnceEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyReplaceOnceEquals(verifier, searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param searchString           the String to search for, may be null
   * @param replacement            the String to replace with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceOnceEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).replaceOnceEquals(searchString, replacement, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param searchString  the String to search for, may be null
   * @param replacement   the String to replace with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyReplaceOnceEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds) {
    verifyReplaceOnceEquals(verifier, searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param searchString           the String to search for, may be null
   * @param replacement            the String to replace with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyReplaceOnceEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyReplaceOnceEquals(verifier, searchString, replacement, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Replacing Once '%s' With '%s', Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is equals
   * to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceOnceIgnoreCaseEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyReplaceOnceIgnoreCaseEquals(verifier, searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is equals
   * to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceOnceIgnoreCaseEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).replaceOnceIgnoreCaseEquals(searchString, replacement, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is equals
   * to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyReplaceOnceIgnoreCaseEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds) {
    verifyReplaceOnceIgnoreCaseEquals(verifier, searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is equals
   * to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyReplaceOnceIgnoreCaseEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyReplaceOnceIgnoreCaseEquals(verifier, searchString, replacement, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Replacing Once '%s' With '%s' Ignoring Case Sensitivity, Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is NOT
   * equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceOnceIgnoreCaseNotEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyReplaceOnceIgnoreCaseNotEquals(verifier, searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is NOT
   * equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceOnceIgnoreCaseNotEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).replaceOnceIgnoreCaseNotEquals(searchString, replacement, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is NOT
   * equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyReplaceOnceIgnoreCaseNotEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds) {
    verifyReplaceOnceIgnoreCaseNotEquals(verifier, searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is NOT
   * equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyReplaceOnceIgnoreCaseNotEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyReplaceOnceIgnoreCaseNotEquals(verifier, searchString, replacement, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Replacing Once '%s' With '%s' Ignoring Case Sensitivity, Not Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param searchString  the String to search for, may be null
   * @param replacement   the String to replace with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceOnceNotEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyReplaceOnceNotEquals(verifier, searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param searchString           the String to search for, may be null
   * @param replacement            the String to replace with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceOnceNotEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).replaceOnceNotEquals(searchString, replacement, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param searchString  the String to search for, may be null
   * @param replacement   the String to replace with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyReplaceOnceNotEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds) {
    verifyReplaceOnceNotEquals(verifier, searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param searchString           the String to search for, may be null
   * @param replacement            the String to replace with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyReplaceOnceNotEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyReplaceOnceNotEquals(verifier, searchString, replacement, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Replacing Once '%s' With '%s', Not Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyReverseEquals(CVerificationQueue verifier, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyReverseEquals(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyReverseEquals(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).reverseEquals(expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyReverseEquals(CVerificationQueue verifier, final String expected, final int waitInSeconds) {
    verifyReverseEquals(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyReverseEquals(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyReverseEquals(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Reversing Order Of Characters, Equals To The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is NOT equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyReverseNotEquals(CVerificationQueue verifier, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyReverseNotEquals(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is NOT equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyReverseNotEquals(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).reverseNotEquals(expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is NOT equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyReverseNotEquals(CVerificationQueue verifier, final String expected, final int waitInSeconds) {
    verifyReverseNotEquals(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is NOT equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyReverseNotEquals(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyReverseNotEquals(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Reversing Order Of Characters, Not Equals To The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param size          the size to pad to
   * @param padStr        the String to pad with, null or empty treated as single space
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRightPadEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRightPadEquals(verifier, size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param size                   the size to pad to
   * @param padStr                 the String to pad with, null or empty treated as single space
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRightPadEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).rightPadEquals(size, padStr, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param size          the size to pad to
   * @param padStr        the String to pad with, null or empty treated as single space
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRightPadEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final int waitInSeconds) {
    verifyRightPadEquals(verifier, size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param size                   the size to pad to
   * @param padStr                 the String to pad with, null or empty treated as single space
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRightPadEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRightPadEquals(verifier, size, padStr, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Expected Value Equals To The Actual Value Right Pad With '%s' And The Length Of '%d'", padStr, size));
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param size          the size to pad to
   * @param padStr        the String to pad with, null or empty treated as single space
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRightPadNotEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRightPadNotEquals(verifier, size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param size                   the size to pad to
   * @param padStr                 the String to pad with, null or empty treated as single space
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRightPadNotEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).rightPadNotEquals(size, padStr, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param size          the size to pad to
   * @param padStr        the String to pad with, null or empty treated as single space
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRightPadNotEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final int waitInSeconds) {
    verifyRightPadNotEquals(verifier, size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param size                   the size to pad to
   * @param padStr                 the String to pad with, null or empty treated as single space
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRightPadNotEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRightPadNotEquals(verifier, size, padStr, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Expected Value Not Equals To The Actual Value Right Pad With '%s' And The Length Of '%d'", padStr, size));
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRightValueEquals(CVerificationQueue verifier, int len, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRightValueEquals(verifier, len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRightValueEquals(CVerificationQueue verifier, int len, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).rightValueEquals(len, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time
   */
  default void verifyRightValueEquals(CVerificationQueue verifier, int len, final String expected, final int waitInSeconds) {
    verifyRightValueEquals(verifier, len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRightValueEquals(CVerificationQueue verifier, int len, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRightValueEquals(verifier, len, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Expected Value Equals To The Right '%d' Character Of Actual Value", len));
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} NOT equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRightValueNotEquals(CVerificationQueue verifier, int len, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRightValueNotEquals(verifier, len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} NOT equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRightValueNotEquals(CVerificationQueue verifier, int len, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).rightValueNotEquals(len, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} NOT equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time
   */
  default void verifyRightValueNotEquals(CVerificationQueue verifier, int len, final String expected, final int waitInSeconds) {
    verifyRightValueNotEquals(verifier, len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} NOT equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRightValueNotEquals(CVerificationQueue verifier, int len, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRightValueNotEquals(verifier, len, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Expected Value Not Equals To The Right '%d' Character Of Actual Value", len));
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is true
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyStartsWith(CVerificationQueue verifier, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyStartsWith(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is true
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyStartsWith(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).startsWith(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is true
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyStartsWith(CVerificationQueue verifier, final String expected, final int waitInSeconds) {
    verifyStartsWith(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is true
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyStartsWith(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyStartsWith(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Starts With Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is true
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param searchInputs  the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                      null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyStartsWithAny(CVerificationQueue verifier, List<String> searchInputs, final int waitInSeconds, final String message, final Object... params) {
    verifyStartsWithAny(verifier, searchInputs, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is true
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param searchInputs           the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                               null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyStartsWithAny(CVerificationQueue verifier, List<String> searchInputs, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, searchInputs, (a, b) -> _toState(a).startsWithAny(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is true
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param searchInputs  the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                      null}
   * @param waitInSeconds maximum wait time
   */
  default void verifyStartsWithAny(CVerificationQueue verifier, List<String> searchInputs, final int waitInSeconds) {
    verifyStartsWithAny(verifier, searchInputs, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is true
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param searchInputs           the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                               null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyStartsWithAny(CVerificationQueue verifier, List<String> searchInputs, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyStartsWithAny(verifier, searchInputs, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Starts With Any Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * true
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyStartsWithIgnoreCase(CVerificationQueue verifier, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyStartsWithIgnoreCase(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * true
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyStartsWithIgnoreCase(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).startsWithIgnoreCase(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * true
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyStartsWithIgnoreCase(CVerificationQueue verifier, final String expected, final int waitInSeconds) {
    verifyStartsWithIgnoreCase(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * true
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyStartsWithIgnoreCase(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyStartsWithIgnoreCase(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Starts With Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is false
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param searchInputs  the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                      null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyStartsWithNone(CVerificationQueue verifier, List<String> searchInputs, final int waitInSeconds, final String message, final Object... params) {
    verifyStartsWithNone(verifier, searchInputs, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is false
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param searchInputs           the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                               null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyStartsWithNone(CVerificationQueue verifier, List<String> searchInputs, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, searchInputs, (a, b) -> _toState(a).startsWithNone(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is false
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param searchInputs  the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                      null}
   * @param waitInSeconds maximum wait time
   */
  default void verifyStartsWithNone(CVerificationQueue verifier, List<String> searchInputs, final int waitInSeconds) {
    verifyStartsWithNone(verifier, searchInputs, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is false
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param searchInputs           the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                               null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyStartsWithNone(CVerificationQueue verifier, List<String> searchInputs, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyStartsWithNone(verifier, searchInputs, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Starts With None Of Expected Value"));
  }

  /**
   * Verify if {@link CStringUtil#stripEnd(String, String)} value equals the expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedEndValue(CVerificationQueue verifier, String stripChars, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyStripedEndValue(verifier, stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#stripEnd(String, String)} value equals the expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedEndValue(CVerificationQueue verifier, String stripChars, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).stripedEndValue(stripChars, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#stripEnd(String, String)} value equals the expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyStripedEndValue(CVerificationQueue verifier, String stripChars, final String expected, final int waitInSeconds) {
    verifyStripedEndValue(verifier, stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#stripEnd(String, String)} value equals the expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyStripedEndValue(CVerificationQueue verifier, String stripChars, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyStripedEndValue(verifier, stripChars, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Striped End '%s' Characters, Equals To The Expected Value", stripChars));
  }

  /**
   * Verify if {@link CStringUtil#stripEnd(String, String)} value NOT equals the expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedEndValueNot(CVerificationQueue verifier, String stripChars, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyStripedEndValueNot(verifier, stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#stripEnd(String, String)} value NOT equals the expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedEndValueNot(CVerificationQueue verifier, String stripChars, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).stripedEndValueNot(stripChars, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#stripEnd(String, String)} value NOT equals the expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyStripedEndValueNot(CVerificationQueue verifier, String stripChars, final String expected, final int waitInSeconds) {
    verifyStripedEndValueNot(verifier, stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#stripEnd(String, String)} value NOT equals the expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyStripedEndValueNot(CVerificationQueue verifier, String stripChars, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyStripedEndValueNot(verifier, stripChars, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Striped End '%s' Characters, Not Equals To The Expected Value", stripChars));
  }

  /**
   * Verify if {@link CStringUtil#stripStart(String, String)} value equals the expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedStartValue(CVerificationQueue verifier, String stripChars, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyStripedStartValue(verifier, stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#stripStart(String, String)} value equals the expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedStartValue(CVerificationQueue verifier, String stripChars, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).stripedStartValue(stripChars, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#stripStart(String, String)} value equals the expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyStripedStartValue(CVerificationQueue verifier, String stripChars, final String expected, final int waitInSeconds) {
    verifyStripedStartValue(verifier, stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#stripStart(String, String)} value equals the expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyStripedStartValue(CVerificationQueue verifier, String stripChars, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyStripedStartValue(verifier, stripChars, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Striped Start '%s' Characters, Equals To The Expected Value", stripChars));
  }

  /**
   * Verify if {@link CStringUtil#stripStart(String, String)} value NOT equals the expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedStartValueNot(CVerificationQueue verifier, String stripChars, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyStripedStartValueNot(verifier, stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#stripStart(String, String)} value NOT equals the expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedStartValueNot(CVerificationQueue verifier, String stripChars, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).stripedStartValueNot(stripChars, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#stripStart(String, String)} value NOT equals the expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyStripedStartValueNot(CVerificationQueue verifier, String stripChars, final String expected, final int waitInSeconds) {
    verifyStripedStartValueNot(verifier, stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#stripStart(String, String)} value NOT equals the expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyStripedStartValueNot(CVerificationQueue verifier, String stripChars, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyStripedStartValueNot(verifier, stripChars, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Striped End '%s' Characters, Not Equals To The Expected Value", stripChars));
  }

  /**
   * Verify if {@link CStringUtil#strip(String)} value equals the expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedValue(CVerificationQueue verifier, String stripChars, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyStripedValue(verifier, stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#strip(String)} value equals the expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedValue(CVerificationQueue verifier, String stripChars, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).stripedValue(stripChars, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#strip(String)} value equals the expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyStripedValue(CVerificationQueue verifier, String stripChars, final String expected, final int waitInSeconds) {
    verifyStripedValue(verifier, stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#strip(String)} value equals the expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyStripedValue(CVerificationQueue verifier, String stripChars, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyStripedValue(verifier, stripChars, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Striped '%s' Characters, Equals To The Expected Value", stripChars));
  }

  /**
   * Verify if {@link CStringUtil#strip(String)} value NOT equals the expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedValueNot(CVerificationQueue verifier, String stripChars, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyStripedValueNot(verifier, stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#strip(String)} value NOT equals the expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedValueNot(CVerificationQueue verifier, String stripChars, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).stripedValueNot(stripChars, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#strip(String)} value NOT equals the expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyStripedValueNot(CVerificationQueue verifier, String stripChars, final String expected, final int waitInSeconds) {
    verifyStripedValueNot(verifier, stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#strip(String)} value NOT equals the expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyStripedValueNot(CVerificationQueue verifier, String stripChars, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyStripedValueNot(verifier, stripChars, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Striped '%s' Characters, Not Equals To The Expected Value", stripChars));
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringAfterEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringAfterEquals(verifier, separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringAfterEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringAfterEquals(separator, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringAfterEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds) {
    verifySubstringAfterEquals(verifier, separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringAfterEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringAfterEquals(verifier, separator, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring After '%s', Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringAfterLastEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringAfterLastEquals(verifier, separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringAfterLastEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringAfterLastEquals(separator, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringAfterLastEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds) {
    verifySubstringAfterLastEquals(verifier, separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringAfterLastEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringAfterLastEquals(verifier, separator, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring After Last '%s', Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} NOT equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringAfterLastNotEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringAfterLastNotEquals(verifier, separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} NOT equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringAfterLastNotEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringAfterLastNotEquals(separator, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} NOT equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringAfterLastNotEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds) {
    verifySubstringAfterLastNotEquals(verifier, separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} NOT equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringAfterLastNotEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringAfterLastNotEquals(verifier, separator, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring After Last '%s', Not Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} NOT equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringAfterNotEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringAfterNotEquals(verifier, separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} NOT equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringAfterNotEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringAfterNotEquals(separator, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} NOT equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringAfterNotEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds) {
    verifySubstringAfterNotEquals(verifier, separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} NOT equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringAfterNotEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringAfterNotEquals(verifier, separator, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring After '%s', Not Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBeforeEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringBeforeEquals(verifier, separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBeforeEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringBeforeEquals(separator, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringBeforeEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds) {
    verifySubstringBeforeEquals(verifier, separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringBeforeEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringBeforeEquals(verifier, separator, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring Before '%s', Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBeforeLastEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringBeforeLastEquals(verifier, separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBeforeLastEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringBeforeLastEquals(separator, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringBeforeLastEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds) {
    verifySubstringBeforeLastEquals(verifier, separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringBeforeLastEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringBeforeLastEquals(verifier, separator, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring Before Last '%s', Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} NOT equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBeforeLastNotEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringBeforeLastNotEquals(verifier, separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} NOT equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBeforeLastNotEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringBeforeLastNotEquals(separator, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} NOT equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringBeforeLastNotEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds) {
    verifySubstringBeforeLastNotEquals(verifier, separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} NOT equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringBeforeLastNotEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringBeforeLastNotEquals(verifier, separator, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring Before Last '%s', Not Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} NOT equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBeforeNotEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringBeforeNotEquals(verifier, separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} NOT equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBeforeNotEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringBeforeNotEquals(separator, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} NOT equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringBeforeNotEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds) {
    verifySubstringBeforeNotEquals(verifier, separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} NOT equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringBeforeNotEquals(CVerificationQueue verifier, String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringBeforeNotEquals(verifier, separator, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring Before '%s', Not Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String)} equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBetweenEquals(CVerificationQueue verifier, String open, String close, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringBetweenEquals(verifier, open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String)} equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBetweenEquals(CVerificationQueue verifier, String open, String close, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringBetweenEquals(open, close, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String)} equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringBetweenEquals(CVerificationQueue verifier, String open, String close, final String expected, final int waitInSeconds) {
    verifySubstringBetweenEquals(verifier, open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String)} equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringBetweenEquals(CVerificationQueue verifier, String open, String close, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringBetweenEquals(verifier, open, close, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring Between '%s' and '%s', Equals To The Expected Value", open, close));
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String)} NOT equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBetweenNotEquals(CVerificationQueue verifier, String open, String close, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringBetweenNotEquals(verifier, open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String)} NOT equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBetweenNotEquals(CVerificationQueue verifier, String open, String close, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringBetweenNotEquals(open, close, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String)} NOT equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringBetweenNotEquals(CVerificationQueue verifier, String open, String close, final String expected, final int waitInSeconds) {
    verifySubstringBetweenNotEquals(verifier, open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String)} NOT equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringBetweenNotEquals(CVerificationQueue verifier, String open, String close, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringBetweenNotEquals(verifier, open, close, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring Between '%s' and '%s', Not Equals To The Expected Value", open, close));
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param start         the position to start from, negative means count back from the end of the String
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringEquals(CVerificationQueue verifier, int start, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringEquals(verifier, start, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param start         the position to start from, negative means count back from the end of the String
   * @param end           the position to end at (exclusive), negative means count back from the end of the
   *                      String
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringEquals(CVerificationQueue verifier, int start, int end, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringEquals(verifier, start, end, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param start                  the position to start from, negative means count back from the end of the String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringEquals(CVerificationQueue verifier, int start, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringEquals(start, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param start                  the position to start from, negative means count back from the end of the String
   * @param end                    the position to end at (exclusive), negative means count back from the end of the
   *                               String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringEquals(CVerificationQueue verifier, int start, int end, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringEquals(start, end, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param start         the position to start from, negative means count back from the end of the String
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringEquals(CVerificationQueue verifier, int start, final String expected, final int waitInSeconds) {
    verifySubstringEquals(verifier, start, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param start                  the position to start from, negative means count back from the end of the String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringEquals(CVerificationQueue verifier, int start, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringEquals(verifier, start, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring After Position '%s', Equals To The Expected Value", start));
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param start         the position to start from, negative means count back from the end of the String
   * @param end           the position to end at (exclusive), negative means count back from the end of the
   *                      String
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringEquals(CVerificationQueue verifier, int start, int end, final String expected, final int waitInSeconds) {
    verifySubstringEquals(verifier, start, end, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param start                  the position to start from, negative means count back from the end of the String
   * @param end                    the position to end at (exclusive), negative means count back from the end of the
   *                               String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringEquals(CVerificationQueue verifier, int start, int end, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringEquals(verifier, start, end, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring From Position '%s' To '%s', Equals To The Expected Value", start, end));
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} NOT equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param start         the position to start from, negative means count back from the end of the String
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringNotEquals(CVerificationQueue verifier, int start, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringNotEquals(verifier, start, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} NOT equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param start         the position to start from, negative means count back from the end of the String
   * @param end           the position to end at (exclusive), negative means count back from the end of the
   *                      String
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringNotEquals(CVerificationQueue verifier, int start, int end, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringNotEquals(verifier, start, end, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} NOT equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param start                  the position to start from, negative means count back from the end of the String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringNotEquals(CVerificationQueue verifier, int start, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringNotEquals(start, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} NOT equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param start                  the position to start from, negative means count back from the end of the String
   * @param end                    the position to end at (exclusive), negative means count back from the end of the
   *                               String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringNotEquals(CVerificationQueue verifier, int start, int end, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringNotEquals(start, end, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} NOT equals to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param start         the position to start from, negative means count back from the end of the String
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringNotEquals(CVerificationQueue verifier, int start, final String expected, final int waitInSeconds) {
    verifySubstringNotEquals(verifier, start, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} NOT equals to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param start                  the position to start from, negative means count back from the end of the String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringNotEquals(CVerificationQueue verifier, int start, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringNotEquals(verifier, start, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring After Position '%s', Not Equals To The Expected Value", start));
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} NOT equals to expected
   * value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param start         the position to start from, negative means count back from the end of the String
   * @param end           the position to end at (exclusive), negative means count back from the end of the
   *                      String
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringNotEquals(CVerificationQueue verifier, int start, int end, final String expected, final int waitInSeconds) {
    verifySubstringNotEquals(verifier, start, end, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} NOT equals to expected
   * value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param start                  the position to start from, negative means count back from the end of the String
   * @param end                    the position to end at (exclusive), negative means count back from the end of the
   *                               String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringNotEquals(CVerificationQueue verifier, int start, int end, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringNotEquals(verifier, start, end, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring From Position '%s' To '%s', Not Equals To The Expected Value", start, end));
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} contains to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringsBetweenContains(CVerificationQueue verifier, String open, String close, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringsBetweenContains(verifier, open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} contains to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringsBetweenContains(CVerificationQueue verifier, String open, String close, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringsBetweenContains(open, close, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} Contains
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringsBetweenContains(CVerificationQueue verifier, String open, String close, final String expected, final int waitInSeconds) {
    verifySubstringsBetweenContains(verifier, open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} Contains
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringsBetweenContains(CVerificationQueue verifier, String open, String close, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringsBetweenContains(verifier, open, close, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring Between '%s' To '%s' Characters, Contains The Expected Value", open, close));
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringsBetweenEquals(CVerificationQueue verifier, String open, String close, List<String> expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringsBetweenEquals(verifier, open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringsBetweenEquals(CVerificationQueue verifier, String open, String close, List<String> expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringsBetweenEquals(open, close, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringsBetweenEquals(CVerificationQueue verifier, String open, String close, List<String> expected, final int waitInSeconds) {
    verifySubstringsBetweenEquals(verifier, open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringsBetweenEquals(CVerificationQueue verifier, String open, String close, List<String> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringsBetweenEquals(verifier, open, close, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring Between '%s' To '%s' Characters, Equals To The Expected Value", open, close));
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT contains
   * to expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringsBetweenNotContains(CVerificationQueue verifier, String open, String close, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringsBetweenNotContains(verifier, open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT contains
   * to expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringsBetweenNotContains(CVerificationQueue verifier, String open, String close, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringsBetweenNotContains(open, close, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT Contains
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringsBetweenNotContains(CVerificationQueue verifier, String open, String close, final String expected, final int waitInSeconds) {
    verifySubstringsBetweenNotContains(verifier, open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT Contains
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringsBetweenNotContains(CVerificationQueue verifier, String open, String close, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringsBetweenNotContains(verifier, open, close, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring Between '%s' To '%s' Characters, Not Contains The Expected Value", open, close));
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringsBetweenNotEquals(CVerificationQueue verifier, String open, String close, List<String> expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringsBetweenNotEquals(verifier, open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringsBetweenNotEquals(CVerificationQueue verifier, String open, String close, List<String> expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringsBetweenNotEquals(open, close, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT equals to
   * expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringsBetweenNotEquals(CVerificationQueue verifier, String open, String close, List<String> expected, final int waitInSeconds) {
    verifySubstringsBetweenNotEquals(verifier, open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT equals to
   * expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringsBetweenNotEquals(CVerificationQueue verifier, String open, String close, List<String> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringsBetweenNotEquals(verifier, open, close, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring Between '%s' To '%s' Characters, Not Equals The Expected Value", open, close));
  }

  /**
   * Verify if {@link CStringUtil#trim(String)} value equals the expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyTrimmedValueEquals(CVerificationQueue verifier, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyTrimmedValueEquals(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#trim(String)} value equals the expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyTrimmedValueEquals(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).trimmedValueEquals(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#trim(String)} value equals the expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyTrimmedValueEquals(CVerificationQueue verifier, final String expected, final int waitInSeconds) {
    verifyTrimmedValueEquals(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#trim(String)} value equals the expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyTrimmedValueEquals(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyTrimmedValueEquals(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Trimmed Value, Equals The Expected Value"));
  }

  /**
   * Verify if {@link CStringUtil#trim(String)} value NOT equals the expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyTrimmedValueNotEquals(CVerificationQueue verifier, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyTrimmedValueNotEquals(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#trim(String)} value NOT equals the expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyTrimmedValueNotEquals(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).trimmedValueNotEquals(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#trim(String)} value NOT equals the expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyTrimmedValueNotEquals(CVerificationQueue verifier, final String expected, final int waitInSeconds) {
    verifyTrimmedValueNotEquals(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#trim(String)} value NOT equals the expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyTrimmedValueNotEquals(CVerificationQueue verifier, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyTrimmedValueNotEquals(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Trimmed Value, Not Equals The Expected Value"));
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int)} value equals the expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param maxWidth      maximum length of truncated string, must be positive
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyTruncatedValueEquals(CVerificationQueue verifier, int maxWidth, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyTruncatedValueEquals(verifier, maxWidth, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int, int)} value equals the expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param offset        left edge of string to start truncate from
   * @param maxWidth      maximum length of truncated string, must be positive
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyTruncatedValueEquals(CVerificationQueue verifier, int offset, int maxWidth, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyTruncatedValueEquals(verifier, offset, maxWidth, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int)} value equals the expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param maxWidth               maximum length of truncated string, must be positive
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyTruncatedValueEquals(CVerificationQueue verifier, int maxWidth, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).truncatedValueEquals(maxWidth, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int, int)} value equals the expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param offset                 left edge of string to start truncate from
   * @param maxWidth               maximum length of truncated string, must be positive
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyTruncatedValueEquals(CVerificationQueue verifier, int offset, int maxWidth, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).truncatedValueEquals(offset, maxWidth, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int)} value equals the expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param maxWidth      maximum length of truncated string, must be positive
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyTruncatedValueEquals(CVerificationQueue verifier, int maxWidth, final String expected, final int waitInSeconds) {
    verifyTruncatedValueEquals(verifier, maxWidth, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int)} value equals the expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param maxWidth               maximum length of truncated string, must be positive
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyTruncatedValueEquals(CVerificationQueue verifier, int maxWidth, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyTruncatedValueEquals(verifier, maxWidth, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Truncated Value With Maximum Width Of %s, Equals The Expected Value", maxWidth));
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int, int)} value equals the expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param offset        left edge of string to start truncate from
   * @param maxWidth      maximum length of truncated string, must be positive
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyTruncatedValueEquals(CVerificationQueue verifier, int offset, int maxWidth, final String expected, final int waitInSeconds) {
    verifyTruncatedValueEquals(verifier, offset, maxWidth, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int, int)} value equals the expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param offset                 left edge of string to start truncate from
   * @param maxWidth               maximum length of truncated string, must be positive
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyTruncatedValueEquals(CVerificationQueue verifier, int offset, int maxWidth, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyTruncatedValueEquals(verifier, offset, maxWidth, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Truncated Value With Maximum Width Of %s With Offset %s, Equals The Expected Value", maxWidth, offset));
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int)} value equals the expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param maxWidth      maximum length of truncated string, must be positive
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyTruncatedValueNotEquals(CVerificationQueue verifier, int maxWidth, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyTruncatedValueNotEquals(verifier, maxWidth, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int, int)} value NOT equals the expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param offset        left edge of string to start truncate from
   * @param maxWidth      maximum length of truncated string, must be positive
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyTruncatedValueNotEquals(CVerificationQueue verifier, int offset, int maxWidth, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyTruncatedValueNotEquals(verifier, offset, maxWidth, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int)} value equals the expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param maxWidth               maximum length of truncated string, must be positive
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyTruncatedValueNotEquals(CVerificationQueue verifier, int maxWidth, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).truncatedValueNotEquals(maxWidth, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int, int)} value NOT equals the expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param offset                 left edge of string to start truncate from
   * @param maxWidth               maximum length of truncated string, must be positive
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyTruncatedValueNotEquals(CVerificationQueue verifier, int offset, int maxWidth, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).truncatedValueNotEquals(offset, maxWidth, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int)} value equals the expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param maxWidth      maximum length of truncated string, must be positive
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyTruncatedValueNotEquals(CVerificationQueue verifier, int maxWidth, final String expected, final int waitInSeconds) {
    verifyTruncatedValueNotEquals(verifier, maxWidth, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int)} value equals the expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param maxWidth               maximum length of truncated string, must be positive
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyTruncatedValueNotEquals(CVerificationQueue verifier, int maxWidth, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyTruncatedValueNotEquals(verifier, maxWidth, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Truncated Value With Maximum Width Of %s, Not Equals The Expected Value", maxWidth));
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int, int)} value NOT equals the expected value.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param offset        left edge of string to start truncate from
   * @param maxWidth      maximum length of truncated string, must be positive
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyTruncatedValueNotEquals(CVerificationQueue verifier, int offset, int maxWidth, final String expected, final int waitInSeconds) {
    verifyTruncatedValueNotEquals(verifier, offset, maxWidth, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int, int)} value NOT equals the expected value.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param offset                 left edge of string to start truncate from
   * @param maxWidth               maximum length of truncated string, must be positive
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyTruncatedValueNotEquals(CVerificationQueue verifier, int offset, int maxWidth, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyTruncatedValueNotEquals(verifier, offset, maxWidth, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Truncated Value With Maximum Width Of %s With Offset %s, Not Equals The Expected Value", maxWidth, offset));
  }
}
