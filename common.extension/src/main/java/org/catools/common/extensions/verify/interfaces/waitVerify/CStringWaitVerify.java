package org.catools.common.extensions.verify.interfaces.waitVerify;

import org.catools.common.extensions.states.interfaces.CStringState;
import org.catools.common.extensions.verify.hard.CStringVerification;
import org.catools.common.extensions.verify.interfaces.base.CStringVerify;
import org.catools.common.utils.CStringUtil;

import java.util.List;
import java.util.regex.Pattern;

/**
 * CStringVerifier is an interface for String verification related methods.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 *
 * <p>Please Note that we should extend manually {@link
 * CStringVerification} for each new added verification here
 */
public interface CStringWaitVerify extends CStringVerify, CObjectWaitVerify<String, CStringState> {

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is equals to expected
   * value.
   *
   * @param size          the int size of new String, negative treated as zero
   * @param padStr        the String to pad the new String with, must not be null or empty
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyCenterPadEquals(int size, String padStr, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyCenterPadEquals(size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is equals to expected
   * value.
   *
   * @param size                   the int size of new String, negative treated as zero
   * @param padStr                 the String to pad the new String with, must not be null or empty
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyCenterPadEquals(int size, String padStr, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).centerPadEquals(size, padStr, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is equals to expected
   * value.
   *
   * @param size          the int size of new String, negative treated as zero
   * @param padStr        the String to pad the new String with, must not be null or empty
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyCenterPadEquals(int size, String padStr, final String expected, final int waitInSeconds) {
    verifyCenterPadEquals(size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is equals to expected
   * value.
   *
   * @param size                   the int size of new String, negative treated as zero
   * @param padStr                 the String to pad the new String with, must not be null or empty
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyCenterPadEquals(int size, String padStr, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyCenterPadEquals(size, padStr, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Center Pad With '%s' And The Length Of '%d' Equals To Expected Value", padStr, size));
  }

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param size          the int size of new String, negative treated as zero
   * @param padStr        the String to pad the new String with, must not be null or empty
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyCenterPadNotEquals(int size, String padStr, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyCenterPadNotEquals(size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param size                   the int size of new String, negative treated as zero
   * @param padStr                 the String to pad the new String with, must not be null or empty
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyCenterPadNotEquals(int size, String padStr, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).centerPadNotEquals(size, padStr, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param size          the int size of new String, negative treated as zero
   * @param padStr        the String to pad the new String with, must not be null or empty
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyCenterPadNotEquals(int size, String padStr, final String expected, final int waitInSeconds) {
    verifyCenterPadNotEquals(size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param size                   the int size of new String, negative treated as zero
   * @param padStr                 the String to pad the new String with, must not be null or empty
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyCenterPadNotEquals(int size, String padStr, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyCenterPadNotEquals(size, padStr, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Center Pad With '%s' And The Length Of '%d' Is Not Equal To Expected Value", padStr, size));
  }

  /**
   * Verify if result of {@link CStringUtil#compare(String, String)} equals to the expected value.
   *
   * @param stringToCompare the string value to compare against
   * @param expected        the expected result.
   * @param waitInSeconds   maximum wait time
   * @param message         information about the purpose of this verification
   * @param params          parameters in case if message is a format {@link String#format}
   */
  default void verifyCompare(String stringToCompare, int expected, final int waitInSeconds, final String message, final Object... params) {
    verifyCompare(stringToCompare, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#compare(String, String)} equals to the expected value.
   *
   * @param stringToCompare        the string value to compare against
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyCompare(String stringToCompare, int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(stringToCompare, (a, b) -> _toState(a).compare(b, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#compare(String, String)} equals to the expected value.
   *
   * @param stringToCompare the string value to compare against
   * @param expected        the expected result.
   * @param waitInSeconds   maximum wait time
   */
  default void verifyCompare(String stringToCompare, int expected, final int waitInSeconds) {
    verifyCompare(stringToCompare, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#compare(String, String)} equals to the expected value.
   *
   * @param stringToCompare        the string value to compare against
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyCompare(String stringToCompare, int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyCompare(stringToCompare, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Result Of Comparison With The Expected Value Is '%d'", expected));
  }

  /**
   * Verify if result of {@link CStringUtil#compareIgnoreCase(String, String)} equals to the
   * expected value.
   *
   * @param stringToCompare the string value to compare against
   * @param expected        the expected result.
   * @param waitInSeconds   maximum wait time
   * @param message         information about the purpose of this verification
   * @param params          parameters in case if message is a format {@link String#format}
   */
  default void verifyCompareIgnoreCase(String stringToCompare, int expected, final int waitInSeconds, final String message, final Object... params) {
    verifyCompareIgnoreCase(stringToCompare, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#compareIgnoreCase(String, String)} equals to the
   * expected value.
   *
   * @param stringToCompare        the string value to compare against
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyCompareIgnoreCase(String stringToCompare, int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(stringToCompare, (a, b) -> _toState(a).compareIgnoreCase(b, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#compareIgnoreCase(String, String)} equals to the
   * expected value.
   *
   * @param stringToCompare the string value to compare against
   * @param expected        the expected result.
   * @param waitInSeconds   maximum wait time
   */
  default void verifyCompareIgnoreCase(String stringToCompare, int expected, final int waitInSeconds) {
    verifyCompareIgnoreCase(stringToCompare, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#compareIgnoreCase(String, String)} equals to the
   * expected value.
   *
   * @param stringToCompare        the string value to compare against
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyCompareIgnoreCase(String stringToCompare, int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyCompareIgnoreCase(stringToCompare, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Result Of Comparison (Ignoring Case) With The Expected Value Is '%d'", expected));
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is true.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is true.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).contains(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is true.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyContains(final String expected, final int waitInSeconds) {
    verifyContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is true.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContains(final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContains(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Contains The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is true,
   * ignoring case.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsIgnoreCase(final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyContainsIgnoreCase(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is true,
   * ignoring case.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsIgnoreCase(final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).containsIgnoreCase(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is true,
   * ignoring case.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyContainsIgnoreCase(final String expected, final int waitInSeconds) {
    verifyContainsIgnoreCase(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is true,
   * ignoring case.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContainsIgnoreCase(final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContainsIgnoreCase(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Contains The Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is true
   *
   * @param suffix        the suffix to find, may be {@code null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEndsWith(String suffix, final int waitInSeconds, final String message, final Object... params) {
    verifyEndsWith(suffix, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is true
   *
   * @param suffix                 the suffix to find, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEndsWith(String suffix, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(suffix, (a, b) -> _toState(a).endsWith(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is true
   *
   * @param suffix        the suffix to find, may be {@code null}
   * @param waitInSeconds maximum wait time
   */
  default void verifyEndsWith(String suffix, final int waitInSeconds) {
    verifyEndsWith(suffix, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is true
   *
   * @param suffix                 the suffix to find, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEndsWith(String suffix, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEndsWith(suffix, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Ends With The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is true.
   *
   * @param searchInputs  the case-sensitive CharSequences to find, may be empty or contain {@code
   *                      null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEndsWithAny(List<String> searchInputs, final int waitInSeconds, final String message, final Object... params) {
    verifyEndsWithAny(searchInputs, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is true.
   *
   * @param searchInputs           the case-sensitive CharSequences to find, may be empty or contain {@code
   *                               null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEndsWithAny(List<String> searchInputs, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(searchInputs, (a, b) -> _toState(a).endsWithAny(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is true.
   *
   * @param searchInputs  the case-sensitive CharSequences to find, may be empty or contain {@code
   *                      null}
   * @param waitInSeconds maximum wait time
   */
  default void verifyEndsWithAny(List<String> searchInputs, final int waitInSeconds) {
    verifyEndsWithAny(searchInputs, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is true.
   *
   * @param searchInputs           the case-sensitive CharSequences to find, may be empty or contain {@code
   *                               null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEndsWithAny(List<String> searchInputs, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEndsWithAny(searchInputs, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Ends With Any Value From The Expected Values"));
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is true.
   *
   * @param suffix        the suffix to find, may be {@code null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEndsWithIgnoreCase(String suffix, final int waitInSeconds, final String message, final Object... params) {
    verifyEndsWithIgnoreCase(suffix, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is true.
   *
   * @param suffix                 the suffix to find, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEndsWithIgnoreCase(String suffix, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(suffix, (a, b) -> _toState(a).endsWithIgnoreCase(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is true.
   *
   * @param suffix        the suffix to find, may be {@code null}
   * @param waitInSeconds maximum wait time
   */
  default void verifyEndsWithIgnoreCase(String suffix, final int waitInSeconds) {
    verifyEndsWithIgnoreCase(suffix, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is true.
   *
   * @param suffix                 the suffix to find, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEndsWithIgnoreCase(String suffix, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEndsWithIgnoreCase(suffix, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Ends With The Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is false.
   *
   * @param searchInputs  the case-sensitive CharSequences to find, may be empty or contain {@code
   *                      null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEndsWithNone(List<String> searchInputs, final int waitInSeconds, final String message, final Object... params) {
    verifyEndsWithNone(searchInputs, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is false.
   *
   * @param searchInputs           the case-sensitive CharSequences to find, may be empty or contain {@code
   *                               null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEndsWithNone(List<String> searchInputs, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(searchInputs, (a, b) -> _toState(a).endsWithNone(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is false.
   *
   * @param searchInputs  the case-sensitive CharSequences to find, may be empty or contain {@code
   *                      null}
   * @param waitInSeconds maximum wait time
   */
  default void verifyEndsWithNone(List<String> searchInputs, final int waitInSeconds) {
    verifyEndsWithNone(searchInputs, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is false.
   *
   * @param searchInputs           the case-sensitive CharSequences to find, may be empty or contain {@code
   *                               null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEndsWithNone(List<String> searchInputs, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEndsWithNone(searchInputs, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Ends With None Of Value From The Expected Values"));
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)}
   * equals to the expected value, ignoring case.
   *
   * @param expectedList  a list of strings, may be {@code null}.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsAnyIgnoreCase(List<String> expectedList, final int waitInSeconds, final String message, final Object... params) {
    verifyEqualsAnyIgnoreCase(expectedList, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)}
   * equals to the expected value, ignoring case.
   *
   * @param expectedList           a list of strings, may be {@code null}.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsAnyIgnoreCase(List<String> expectedList, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expectedList, (a, b) -> _toState(a).equalsAnyIgnoreCase(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)}
   * equals to the expected value, ignoring case.
   *
   * @param expectedList  a list of strings, may be {@code null}.
   * @param waitInSeconds maximum wait time
   */
  default void verifyEqualsAnyIgnoreCase(List<String> expectedList, final int waitInSeconds) {
    verifyEqualsAnyIgnoreCase(expectedList, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)}
   * equals to the expected value, ignoring case.
   *
   * @param expectedList           a list of strings, may be {@code null}.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEqualsAnyIgnoreCase(List<String> expectedList, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEqualsAnyIgnoreCase(expectedList, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Ends With Any Value From The Expected Values Ignoring Case Sensitivity"));
  }

  /**
   * Verify if {@link CStringUtil#equalsIgnoreCase(CharSequence, CharSequence)} value equals the
   * expected value.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsIgnoreCase(final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyEqualsIgnoreCase(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#equalsIgnoreCase(CharSequence, CharSequence)} value equals the
   * expected value.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsIgnoreCase(final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).equalsIgnoreCase(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#equalsIgnoreCase(CharSequence, CharSequence)} value equals the
   * expected value.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyEqualsIgnoreCase(final String expected, final int waitInSeconds) {
    verifyEqualsIgnoreCase(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#equalsIgnoreCase(CharSequence, CharSequence)} value equals the
   * expected value.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEqualsIgnoreCase(final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEqualsIgnoreCase(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Equals The Expected Values Ignoring Case Sensitivity"));
  }

  /**
   * Verify if value is equal to expected after removing all WhiteSpaces from both.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsIgnoreWhiteSpaces(final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyEqualsIgnoreWhiteSpaces(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if value is equal to expected after removing all WhiteSpaces from both.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsIgnoreWhiteSpaces(final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).equalsIgnoreWhiteSpaces(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if value is equal to expected after removing all WhiteSpaces from both.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyEqualsIgnoreWhiteSpaces(final String expected, final int waitInSeconds) {
    verifyEqualsIgnoreWhiteSpaces(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if value is equal to expected after removing all WhiteSpaces from both.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEqualsIgnoreWhiteSpaces(final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEqualsIgnoreWhiteSpaces(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Equals The Expected Values Ignoring White Spaces"));
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)} is
   * false, ignoring case.
   *
   * @param expectedList  a list of strings, may be {@code null}.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsNoneIgnoreCase(List<String> expectedList, final int waitInSeconds, final String message, final Object... params) {
    verifyEqualsNoneIgnoreCase(expectedList, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)} is
   * false, ignoring case.
   *
   * @param expectedList           a list of strings, may be {@code null}.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsNoneIgnoreCase(List<String> expectedList, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expectedList, (a, b) -> _toState(a).equalsNoneIgnoreCase(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)} is
   * false, ignoring case.
   *
   * @param expectedList  a list of strings, may be {@code null}.
   * @param waitInSeconds maximum wait time
   */
  default void verifyEqualsNoneIgnoreCase(List<String> expectedList, final int waitInSeconds) {
    verifyEqualsNoneIgnoreCase(expectedList, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)} is
   * false, ignoring case.
   *
   * @param expectedList           a list of strings, may be {@code null}.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEqualsNoneIgnoreCase(List<String> expectedList, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEqualsNoneIgnoreCase(expectedList, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Ends With None Of Value From The Expected Values Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAlpha(final int waitInSeconds, final String message, final Object... params) {
    verifyIsAlpha(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAlpha(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isAlpha(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsAlpha(final int waitInSeconds) {
    verifyIsAlpha(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsAlpha(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsAlpha(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Contains Only Alpha Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAlphaSpace(final int waitInSeconds, final String message, final Object... params) {
    verifyIsAlphaSpace(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAlphaSpace(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isAlphaSpace(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsAlphaSpace(final int waitInSeconds) {
    verifyIsAlphaSpace(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsAlphaSpace(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsAlphaSpace(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Contains Only Alpha Or Space Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAlphanumeric(final int waitInSeconds, final String message, final Object... params) {
    verifyIsAlphanumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAlphanumeric(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isAlphanumeric(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsAlphanumeric(final int waitInSeconds) {
    verifyIsAlphanumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsAlphanumeric(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsAlphanumeric(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Contains Only Alpha-Numeric Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAlphanumericSpace(final int waitInSeconds, final String message, final Object... params) {
    verifyIsAlphanumericSpace(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAlphanumericSpace(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isAlphanumericSpace(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsAlphanumericSpace(final int waitInSeconds) {
    verifyIsAlphanumericSpace(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsAlphanumericSpace(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsAlphanumericSpace(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Contains Only Alpha-Numeric Or Space Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAsciiPrintable(final int waitInSeconds, final String message, final Object... params) {
    verifyIsAsciiPrintable(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAsciiPrintable(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isAsciiPrintable(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsAsciiPrintable(final int waitInSeconds) {
    verifyIsAsciiPrintable(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsAsciiPrintable(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsAsciiPrintable(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Contains Only Ascii Printable Characters"));
  }

  /**
   * Verify if String value is blank (Null or Empty)
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlank(final int waitInSeconds, final String message, final Object... params) {
    verifyIsBlank(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if String value is blank (Null or Empty)
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlank(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify("<Blank>", (a, b) -> _toState(a).isBlank(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if String value is blank (Null or Empty)
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsBlank(final int waitInSeconds) {
    verifyIsBlank(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if String value is blank (Null or Empty)
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsBlank(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsBlank(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Blank"));
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrAlpha(final int waitInSeconds, final String message, final Object... params) {
    verifyIsBlankOrAlpha(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrAlpha(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isBlankOrAlpha(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsBlankOrAlpha(final int waitInSeconds) {
    verifyIsBlankOrAlpha(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsBlankOrAlpha(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsBlankOrAlpha(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Contains Only Alpha Characters Or It Is Blank"));
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrAlphanumeric(final int waitInSeconds, final String message, final Object... params) {
    verifyIsBlankOrAlphanumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrAlphanumeric(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isBlankOrAlphanumeric(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsBlankOrAlphanumeric(final int waitInSeconds) {
    verifyIsBlankOrAlphanumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsBlankOrAlphanumeric(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsBlankOrAlphanumeric(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Contains Only Alpha-Numeric Characters Or It Is Blank"));
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrNotAlpha(final int waitInSeconds, final String message, final Object... params) {
    verifyIsBlankOrNotAlpha(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrNotAlpha(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isBlankOrNotAlpha(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsBlankOrNotAlpha(final int waitInSeconds) {
    verifyIsBlankOrNotAlpha(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsBlankOrNotAlpha(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsBlankOrNotAlpha(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Blank Or Not Contains Only Alpha-Numeric Characters"));
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrNotAlphanumeric(final int waitInSeconds, final String message, final Object... params) {
    verifyIsBlankOrNotAlphanumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrNotAlphanumeric(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isBlankOrNotAlphanumeric(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsBlankOrNotAlphanumeric(final int waitInSeconds) {
    verifyIsBlankOrNotAlphanumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsBlankOrNotAlphanumeric(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsBlankOrNotAlphanumeric(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Blank Or Not Contains Only Alpha-Numeric Characters"));
  }

  /**
   * Verify if string is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrNotNumeric(final int waitInSeconds, final String message, final Object... params) {
    verifyIsBlankOrNotNumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if string is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrNotNumeric(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isBlankOrNotNumeric(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if string is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsBlankOrNotNumeric(final int waitInSeconds) {
    verifyIsBlankOrNotNumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if string is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsBlankOrNotNumeric(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsBlankOrNotNumeric(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Blank Or Not Contains Only Numeric Characters"));
  }

  /**
   * Verify if string is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrNumeric(final int waitInSeconds, final String message, final Object... params) {
    verifyIsBlankOrNumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if string is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrNumeric(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isBlankOrNumeric(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if string is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsBlankOrNumeric(final int waitInSeconds) {
    verifyIsBlankOrNumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if string is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsBlankOrNumeric(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsBlankOrNumeric(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Blank Or Not Contains Only Numeric Characters"));
  }

  /**
   * Verify if String value is empty
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmpty(final int waitInSeconds, final String message, final Object... params) {
    verifyIsEmpty(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if String value is empty
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmpty(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify("<Empty>", (a, b) -> _toState(a).isEmpty(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if String value is empty
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsEmpty(final int waitInSeconds) {
    verifyIsEmpty(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if String value is empty
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsEmpty(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsEmpty(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Empty"));
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrAlpha(final int waitInSeconds, final String message, final Object... params) {
    verifyIsEmptyOrAlpha(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrAlpha(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isEmptyOrAlpha(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsEmptyOrAlpha(final int waitInSeconds) {
    verifyIsEmptyOrAlpha(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsEmptyOrAlpha(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsEmptyOrAlpha(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Empty Or Contains Only Alpha Characters"));
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrAlphanumeric(final int waitInSeconds, final String message, final Object... params) {
    verifyIsEmptyOrAlphanumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrAlphanumeric(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isEmptyOrAlphanumeric(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsEmptyOrAlphanumeric(final int waitInSeconds) {
    verifyIsEmptyOrAlphanumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsEmptyOrAlphanumeric(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsEmptyOrAlphanumeric(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Empty Or Contains Only Alpha-Numeric Characters"));
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrNotAlpha(final int waitInSeconds, final String message, final Object... params) {
    verifyIsEmptyOrNotAlpha(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrNotAlpha(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isEmptyOrNotAlpha(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsEmptyOrNotAlpha(final int waitInSeconds) {
    verifyIsEmptyOrNotAlpha(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsEmptyOrNotAlpha(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsEmptyOrNotAlpha(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Empty Or Not Contains Only Alpha Characters"));
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrNotAlphanumeric(final int waitInSeconds, final String message, final Object... params) {
    verifyIsEmptyOrNotAlphanumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrNotAlphanumeric(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isEmptyOrNotAlphanumeric(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsEmptyOrNotAlphanumeric(final int waitInSeconds) {
    verifyIsEmptyOrNotAlphanumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsEmptyOrNotAlphanumeric(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsEmptyOrNotAlphanumeric(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Empty Or Not Contains Only Alpha-Numeric Characters"));
  }

  /**
   * Verify if string is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrNotNumeric(final int waitInSeconds, final String message, final Object... params) {
    verifyIsEmptyOrNotNumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if string is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrNotNumeric(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isEmptyOrNotNumeric(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if string is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsEmptyOrNotNumeric(final int waitInSeconds) {
    verifyIsEmptyOrNotNumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if string is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsEmptyOrNotNumeric(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsEmptyOrNotNumeric(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Empty Or Not Contains Only Numeric Characters"));
  }

  /**
   * Verify if string is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrNumeric(final int waitInSeconds, final String message, final Object... params) {
    verifyIsEmptyOrNumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if string is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrNumeric(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isEmptyOrNumeric(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if string is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsEmptyOrNumeric(final int waitInSeconds) {
    verifyIsEmptyOrNumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if string is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsEmptyOrNumeric(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsEmptyOrNumeric(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Empty Or Contains Only Numeric Characters"));
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param pattern       regular expression pattern
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyMatches(final Pattern pattern, final int waitInSeconds, final String message, final Object... params) {
    verifyMatches(pattern, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param pattern                regular expression pattern
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyMatches(final Pattern pattern, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(pattern, (a, b) -> _toState(a).matches(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if String value match any of provided pattern
   *
   * @param patterns      regular expression patterns
   * @param waitInSeconds maximum wait time
   */
  default void verifyMatchesAny(final List<Pattern> patterns, final int waitInSeconds) {
    verifyMatchesAny(patterns, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if String value match any of provided pattern
   *
   * @param patterns      regular expression patterns
   * @param waitInSeconds maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyMatchesAny(final List<Pattern> patterns, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyMatchesAny(patterns, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Matches Any Of The Provided Pattern"));
  }

  /**
   * Verify if String value match any of provided pattern
   *
   * @param patterns      regular expression patterns
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyMatchesAny(final List<Pattern> patterns, final int waitInSeconds, final String message, final Object... params) {
    verifyMatchesAny(patterns, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if String value match any of provided pattern
   *
   * @param patterns               regular expression patterns
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyMatchesAny(final List<Pattern> patterns, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(patterns, (a, b) -> _toState(a).matchAny(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if String value match NONE of provided pattern
   *
   * @param patterns      regular expression patterns
   * @param waitInSeconds maximum wait time
   */
  default void verifyMatchesNone(final List<Pattern> patterns, final int waitInSeconds) {
    verifyMatchesNone(patterns, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if String value match NONE of provided pattern
   *
   * @param patterns               regular expression patterns
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyMatchesNone(final List<Pattern> patterns, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyMatchesNone(patterns, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Matches None Of The Provided Pattern"));
  }

  /**
   * Verify if String value match NONE of provided pattern
   *
   * @param patterns               regular expression patterns
   * @param waitInSeconds          maximum wait time
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyMatchesNone(final List<Pattern> patterns, final int waitInSeconds, final String message, final Object... params) {
    verifyMatchesNone(patterns, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if String value match NONE of provided pattern
   *
   * @param patterns               regular expression patterns
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyMatchesNone(final List<Pattern> patterns, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(patterns, (a, b) -> _toState(a).matchNone(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param pattern       regular expression pattern
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyMatches(final String pattern, final int waitInSeconds, final String message, final Object... params) {
    verifyMatches(pattern, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param pattern                regular expression pattern
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyMatches(final String pattern, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(pattern, (a, b) -> _toState(a).matches(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param pattern       regular expression pattern
   * @param waitInSeconds maximum wait time
   */
  default void verifyMatches(final Pattern pattern, final int waitInSeconds) {
    verifyMatches(pattern, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param pattern       regular expression pattern
   * @param waitInSeconds maximum wait time
   */
  default void verifyMatches(final String pattern, final int waitInSeconds) {
    verifyMatches(pattern, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param pattern                regular expression pattern
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyMatches(final Pattern pattern, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyMatches(pattern, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Matches The Provided Pattern"));
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param pattern                regular expression pattern
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyMatches(final String pattern, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyMatches(pattern, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Matches The Provided Pattern"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is false.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAlpha(final int waitInSeconds, final String message, final Object... params) {
    verifyIsNotAlpha(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is false.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAlpha(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isNotAlpha(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is false.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotAlpha(final int waitInSeconds) {
    verifyIsNotAlpha(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is false.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotAlpha(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNotAlpha(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Not Contains Only Alpha Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is false.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAlphaSpace(final int waitInSeconds, final String message, final Object... params) {
    verifyIsNotAlphaSpace(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is false.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAlphaSpace(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isNotAlphaSpace(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is false.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotAlphaSpace(final int waitInSeconds) {
    verifyIsNotAlphaSpace(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is false.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotAlphaSpace(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNotAlphaSpace(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Not Contains Only Alpha Characters Or Space"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAlphanumeric(final int waitInSeconds, final String message, final Object... params) {
    verifyIsNotAlphanumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAlphanumeric(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isNotAlphanumeric(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotAlphanumeric(final int waitInSeconds) {
    verifyIsNotAlphanumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotAlphanumeric(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNotAlphanumeric(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Not Contains Only Alpha-Numeric Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is false.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAlphanumericSpace(final int waitInSeconds, final String message, final Object... params) {
    verifyIsNotAlphanumericSpace(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is false.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAlphanumericSpace(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isNotAlphanumericSpace(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is false.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotAlphanumericSpace(final int waitInSeconds) {
    verifyIsNotAlphanumericSpace(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is false.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotAlphanumericSpace(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNotAlphanumericSpace(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Not Contains Only Alpha-Numeric Characters Or Space"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is false.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAsciiPrintable(final int waitInSeconds, final String message, final Object... params) {
    verifyIsNotAsciiPrintable(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is false.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAsciiPrintable(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isNotAsciiPrintable(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is false.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotAsciiPrintable(final int waitInSeconds) {
    verifyIsNotAsciiPrintable(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is false.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotAsciiPrintable(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNotAsciiPrintable(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Not Contains Only Ascii Printable Characters"));
  }

  /**
   * Verify if String value is not blank (Null or Empty)
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotBlank(final int waitInSeconds, final String message, final Object... params) {
    verifyIsNotBlank(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if String value is not blank (Null or Empty)
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotBlank(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify("<Not Blank>", (a, b) -> _toState(a).isNotBlank(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if String value is not blank (Null or Empty)
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotBlank(final int waitInSeconds) {
    verifyIsNotBlank(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if String value is not blank (Null or Empty)
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotBlank(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNotBlank(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Not Blank"));
  }

  /**
   * Verify String value is not empty
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotEmpty(final int waitInSeconds, final String message, final Object... params) {
    verifyIsNotEmpty(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify String value is not empty
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotEmpty(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify("<Not Empty>", (a, b) -> _toState(a).isNotEmpty(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify String value is not empty
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotEmpty(final int waitInSeconds) {
    verifyIsNotEmpty(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify String value is not empty
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotEmpty(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNotEmpty(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Not Empty"));
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param pattern       regular expression pattern
   */
  default void verifyNotMatches(final Pattern pattern, final int waitInSeconds, final String message, final Object... params) {
    verifyNotMatches(pattern, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param pattern                regular expression pattern
   */
  default void verifyNotMatches(final Pattern pattern, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(pattern, (a, b) -> _toState(a).notMatches(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param pattern       regular expression pattern
   */
  default void verifyNotMatches(final String pattern, final int waitInSeconds, final String message, final Object... params) {
    verifyNotMatches(pattern, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param pattern                regular expression pattern
   */
  default void verifyNotMatches(final String pattern, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(pattern, (a, b) -> _toState(a).notMatches(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param waitInSeconds maximum wait time
   * @param pattern       regular expression pattern
   */
  default void verifyNotMatches(final Pattern pattern, final int waitInSeconds) {
    verifyNotMatches(pattern, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param pattern                regular expression pattern
   */
  default void verifyNotMatches(final Pattern pattern, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotMatches(pattern, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Not Match The Expected Pattern"));
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param waitInSeconds maximum wait time
   * @param pattern       regular expression pattern
   */
  default void verifyNotMatches(final String pattern, final int waitInSeconds) {
    verifyNotMatches(pattern, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param pattern                regular expression pattern
   */
  default void verifyNotMatches(final String pattern, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotMatches(pattern, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Is Not Match The Expected Pattern"));
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is false.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotNumeric(final int waitInSeconds, final String message, final Object... params) {
    verifyIsNotNumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is false.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotNumeric(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isNotNumeric(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is false.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotNumeric(final int waitInSeconds) {
    verifyIsNotNumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is false.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotNumeric(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNotNumeric(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Not Contains Only Numeric Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is false.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotNumericSpace(final int waitInSeconds, final String message, final Object... params) {
    verifyIsNotNumericSpace(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is false.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotNumericSpace(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isNotNumericSpace(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is false.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotNumericSpace(final int waitInSeconds) {
    verifyIsNotNumericSpace(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is false.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotNumericSpace(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNotNumericSpace(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Not Contains Only Numeric Or Space Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNumeric(final int waitInSeconds, final String message, final Object... params) {
    verifyIsNumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNumeric(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isNumeric(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNumeric(final int waitInSeconds) {
    verifyIsNumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNumeric(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNumeric(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Contains Only Numeric Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNumericSpace(final int waitInSeconds, final String message, final Object... params) {
    verifyIsNumericSpace(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNumericSpace(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isNumericSpace(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNumericSpace(final int waitInSeconds) {
    verifyIsNumericSpace(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNumericSpace(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNumericSpace(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Value Contains Only Numeric Or Space Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is equals to expected
   * value.
   *
   * @param size          the size to pad to
   * @param padStr        the String to pad with, null or empty treated as single space
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyLeftPadEquals(int size, String padStr, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyLeftPadEquals(size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is equals to expected
   * value.
   *
   * @param size                   the size to pad to
   * @param padStr                 the String to pad with, null or empty treated as single space
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyLeftPadEquals(int size, String padStr, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).leftPadEquals(size, padStr, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is equals to expected
   * value.
   *
   * @param size          the size to pad to
   * @param padStr        the String to pad with, null or empty treated as single space
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyLeftPadEquals(int size, String padStr, final String expected, final int waitInSeconds) {
    verifyLeftPadEquals(size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is equals to expected
   * value.
   *
   * @param size                   the size to pad to
   * @param padStr                 the String to pad with, null or empty treated as single space
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyLeftPadEquals(int size, String padStr, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyLeftPadEquals(size, padStr, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Expected Value Equals To The Actual Value Left Pad With '%s' And The Length Of '%d'", padStr, size));
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param size          the size to pad to
   * @param padStr        the String to pad with, null or empty treated as single space
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyLeftPadNotEquals(int size, String padStr, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyLeftPadNotEquals(size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param size                   the size to pad to
   * @param padStr                 the String to pad with, null or empty treated as single space
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyLeftPadNotEquals(int size, String padStr, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).leftPadNotEquals(size, padStr, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param size          the size to pad to
   * @param padStr        the String to pad with, null or empty treated as single space
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyLeftPadNotEquals(int size, String padStr, final String expected, final int waitInSeconds) {
    verifyLeftPadNotEquals(size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param size                   the size to pad to
   * @param padStr                 the String to pad with, null or empty treated as single space
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyLeftPadNotEquals(int size, String padStr, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyLeftPadNotEquals(size, padStr, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Expected Value Not Equals To The Actual Value Left Pad With '%s' And The Length Of '%d'", padStr, size));
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} equals to expected value.
   *
   * @param expected      the expected result.
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyLeftValueEquals(int len, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyLeftValueEquals(len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} equals to expected value.
   *
   * @param expected               the expected result.
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyLeftValueEquals(int len, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).leftValueEquals(len, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} equals to expected value.
   *
   * @param expected      the expected result.
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time
   */
  default void verifyLeftValueEquals(int len, final String expected, final int waitInSeconds) {
    verifyLeftValueEquals(len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} equals to expected value.
   *
   * @param expected               the expected result.
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyLeftValueEquals(int len, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyLeftValueEquals(len, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Expected Value Equals To The Left '%d' Character Of Actual Value", len));
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} NOT equals to expected value.
   *
   * @param expected      the expected result.
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyLeftValueNotEquals(int len, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyLeftValueNotEquals(len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} NOT equals to expected value.
   *
   * @param expected               the expected result.
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyLeftValueNotEquals(int len, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).leftValueNotEquals(len, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} NOT equals to expected value.
   *
   * @param expected      the expected result.
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time
   */
  default void verifyLeftValueNotEquals(int len, final String expected, final int waitInSeconds) {
    verifyLeftValueNotEquals(len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} NOT equals to expected value.
   *
   * @param expected               the expected result.
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyLeftValueNotEquals(int len, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyLeftValueNotEquals(len, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Expected Value Not Equals To The Left '%d' Character Of Actual Value", len));
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is equals to expected value.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyLengthEquals(int expected, final int waitInSeconds, final String message, final Object... params) {
    verifyLengthEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is equals to expected value.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyLengthEquals(int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).lengthEquals(expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is equals to expected value.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyLengthEquals(int expected, final int waitInSeconds) {
    verifyLengthEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is equals to expected value.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyLengthEquals(int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyLengthEquals(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Expected Value Length Equals To The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is NOT equals to expected value.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyLengthNotEquals(int expected, final int waitInSeconds, final String message, final Object... params) {
    verifyLengthNotEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is NOT equals to expected value.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyLengthNotEquals(int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).lengthNotEquals(expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is NOT equals to expected value.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyLengthNotEquals(int expected, final int waitInSeconds) {
    verifyLengthNotEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is NOT equals to expected value.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyLengthNotEquals(int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyLengthNotEquals(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Expected Value Length Not Equals To The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} equals to expected value.
   *
   * @param expected      the expected result.
   * @param pos           the position to start from, negative treated as zero
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyMidValueEquals(int pos, int len, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyMidValueEquals(pos, len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} equals to expected value.
   *
   * @param expected               the expected result.
   * @param pos                    the position to start from, negative treated as zero
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyMidValueEquals(int pos, int len, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).midValueEquals(pos, len, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} equals to expected value.
   *
   * @param expected      the expected result.
   * @param pos           the position to start from, negative treated as zero
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time
   */
  default void verifyMidValueEquals(int pos, int len, final String expected, final int waitInSeconds) {
    verifyMidValueEquals(pos, len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} equals to expected value.
   *
   * @param expected               the expected result.
   * @param pos                    the position to start from, negative treated as zero
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyMidValueEquals(int pos, int len, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyMidValueEquals(pos, len, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Expected Value Equals To The Characters Of Actual Value From Position '%d' For '%d' Length", pos, len));
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} NOT equals to expected value.
   *
   * @param expected      the expected result.
   * @param pos           the position to start from, negative treated as zero
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyMidValueNotEquals(int pos, int len, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyMidValueNotEquals(pos, len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} NOT equals to expected value.
   *
   * @param expected               the expected result.
   * @param pos                    the position to start from, negative treated as zero
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyMidValueNotEquals(int pos, int len, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).midValueNotEquals(pos, len, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} NOT equals to expected value.
   *
   * @param expected      the expected result.
   * @param pos           the position to start from, negative treated as zero
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time
   */
  default void verifyMidValueNotEquals(int pos, int len, final String expected, final int waitInSeconds) {
    verifyMidValueNotEquals(pos, len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} NOT equals to expected value.
   *
   * @param expected               the expected result.
   * @param pos                    the position to start from, negative treated as zero
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyMidValueNotEquals(int pos, int len, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyMidValueNotEquals(pos, len, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Expected Value Not Equals To The Characters Of Actual Value From Position '%d' For '%d' Length", pos, len));
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is false.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is false.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).notContains(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is false.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotContains(final String expected, final int waitInSeconds) {
    verifyNotContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is false.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContains(final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContains(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Not Contains The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContainsIgnoreCase(final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContainsIgnoreCase(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContainsIgnoreCase(final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).notContainsIgnoreCase(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotContainsIgnoreCase(final String expected, final int waitInSeconds) {
    verifyNotContainsIgnoreCase(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContainsIgnoreCase(final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContainsIgnoreCase(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Not Contains The Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is false
   *
   * @param suffix        the suffix to find, may be {@code null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEndsWith(String suffix, final int waitInSeconds, final String message, final Object... params) {
    verifyNotEndsWith(suffix, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is false
   *
   * @param suffix                 the suffix to find, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEndsWith(String suffix, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(suffix, (a, b) -> _toState(a).notEndsWith(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is false
   *
   * @param suffix        the suffix to find, may be {@code null}
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotEndsWith(String suffix, final int waitInSeconds) {
    verifyNotEndsWith(suffix, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is false
   *
   * @param suffix                 the suffix to find, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotEndsWith(String suffix, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotEndsWith(suffix, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Not Ends With The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param suffix        the suffix to find, may be {@code null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEndsWithIgnoreCase(String suffix, final int waitInSeconds, final String message, final Object... params) {
    verifyNotEndsWithIgnoreCase(suffix, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param suffix                 the suffix to find, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEndsWithIgnoreCase(String suffix, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(suffix, (a, b) -> _toState(a).notEndsWithIgnoreCase(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param suffix        the suffix to find, may be {@code null}
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotEndsWithIgnoreCase(String suffix, final int waitInSeconds) {
    verifyNotEndsWithIgnoreCase(suffix, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param suffix                 the suffix to find, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotEndsWithIgnoreCase(String suffix, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotEndsWithIgnoreCase(suffix, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Not Ends With The Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if {@link CStringUtil#equalsIgnoreCase(CharSequence, CharSequence)} value NOT equals the
   * expected value.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsIgnoreCase(final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNotEqualsIgnoreCase(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#equalsIgnoreCase(CharSequence, CharSequence)} value NOT equals the
   * expected value.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsIgnoreCase(final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).notEqualsIgnoreCase(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#equalsIgnoreCase(CharSequence, CharSequence)} value NOT equals the
   * expected value.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotEqualsIgnoreCase(final String expected, final int waitInSeconds) {
    verifyNotEqualsIgnoreCase(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#equalsIgnoreCase(CharSequence, CharSequence)} value NOT equals the
   * expected value.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotEqualsIgnoreCase(final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotEqualsIgnoreCase(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Not Equals To The Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if value is not equal to expected after removing all WhiteSpaces from both.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsIgnoreWhiteSpaces(final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNotEqualsIgnoreWhiteSpaces(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if value is not equal to expected after removing all WhiteSpaces from both.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsIgnoreWhiteSpaces(final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).notEqualsIgnoreWhiteSpaces(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if value is not equal to expected after removing all WhiteSpaces from both.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotEqualsIgnoreWhiteSpaces(final String expected, final int waitInSeconds) {
    verifyNotEqualsIgnoreWhiteSpaces(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if value is not equal to expected after removing all WhiteSpaces from both.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotEqualsIgnoreWhiteSpaces(final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotEqualsIgnoreWhiteSpaces(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Not Equals To The Expected Value Ignoring White Spaces"));
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is false
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotStartsWith(final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNotStartsWith(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is false
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotStartsWith(final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).notStartsWith(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is false
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotStartsWith(final String expected, final int waitInSeconds) {
    verifyNotStartsWith(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is false
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotStartsWith(final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotStartsWith(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Not Starts With The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * false
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotStartsWithIgnoreCase(final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNotStartsWithIgnoreCase(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * false
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotStartsWithIgnoreCase(final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).notStartsWithIgnoreCase(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * false
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotStartsWithIgnoreCase(final String expected, final int waitInSeconds) {
    verifyNotStartsWithIgnoreCase(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * false
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotStartsWithIgnoreCase(final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotStartsWithIgnoreCase(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Not Starts With The Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is equals to
   * expected value.
   *
   * @param expected      the expected result.
   * @param subString     the substring to count, may be null
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNumberOfMatchesEquals(String subString, int expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNumberOfMatchesEquals(subString, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is equals to
   * expected value.
   *
   * @param expected               the expected result.
   * @param subString              the substring to count, may be null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNumberOfMatchesEquals(String subString, int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).numberOfMatchesEquals(subString, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is equals to
   * expected value.
   *
   * @param expected      the expected result.
   * @param subString     the substring to count, may be null
   * @param waitInSeconds maximum wait time
   */
  default void verifyNumberOfMatchesEquals(String subString, int expected, final int waitInSeconds) {
    verifyNumberOfMatchesEquals(subString, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is equals to
   * expected value.
   *
   * @param expected               the expected result.
   * @param subString              the substring to count, may be null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNumberOfMatchesEquals(String subString, int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNumberOfMatchesEquals(subString, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Contains Exact Number Of Substring"));
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is NOT equals
   * to expected value.
   *
   * @param expected      the expected result.
   * @param subString     the substring to count, may be null
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNumberOfMatchesNotEquals(String subString, int expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNumberOfMatchesNotEquals(subString, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is NOT equals
   * to expected value.
   *
   * @param expected               the expected result.
   * @param subString              the substring to count, may be null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNumberOfMatchesNotEquals(String subString, int expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).numberOfMatchesNotEquals(subString, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is NOT equals
   * to expected value.
   *
   * @param expected      the expected result.
   * @param subString     the substring to count, may be null
   * @param waitInSeconds maximum wait time
   */
  default void verifyNumberOfMatchesNotEquals(String subString, int expected, final int waitInSeconds) {
    verifyNumberOfMatchesNotEquals(subString, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is NOT equals
   * to expected value.
   *
   * @param expected               the expected result.
   * @param subString              the substring to count, may be null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNumberOfMatchesNotEquals(String subString, int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNumberOfMatchesNotEquals(subString, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Not Contains Exact Number Of Substring"));
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is equals to expected value.
   *
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEndEquals(String remove, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRemoveEndEquals(remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is equals to expected value.
   *
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEndEquals(String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).removeEndEquals(remove, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is equals to expected value.
   *
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRemoveEndEquals(String remove, final String expected, final int waitInSeconds) {
    verifyRemoveEndEquals(remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is equals to expected value.
   *
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRemoveEndEquals(String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRemoveEndEquals(remove, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Removing '%s' From End, Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEndIgnoreCaseEquals(String remove, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRemoveEndIgnoreCaseEquals(remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEndIgnoreCaseEquals(String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).removeEndIgnoreCaseEquals(remove, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRemoveEndIgnoreCaseEquals(String remove, final String expected, final int waitInSeconds) {
    verifyRemoveEndIgnoreCaseEquals(remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRemoveEndIgnoreCaseEquals(String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRemoveEndIgnoreCaseEquals(remove, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Removing '%s' From End Ignoring Case Sensitivity, Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEndIgnoreCaseNotEquals(String remove, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRemoveEndIgnoreCaseNotEquals(remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEndIgnoreCaseNotEquals(String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).removeEndIgnoreCaseNotEquals(remove, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRemoveEndIgnoreCaseNotEquals(String remove, final String expected, final int waitInSeconds) {
    verifyRemoveEndIgnoreCaseNotEquals(remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRemoveEndIgnoreCaseNotEquals(String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRemoveEndIgnoreCaseNotEquals(remove, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Removing '%s' From End Ignoring Case Sensitivity, Not Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is NOT equals to expected
   * value.
   *
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEndNotEquals(String remove, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRemoveEndNotEquals(remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is NOT equals to expected
   * value.
   *
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEndNotEquals(String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).removeEndNotEquals(remove, expected), waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is NOT equals to expected
   * value.
   *
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRemoveEndNotEquals(String remove, final String expected, final int waitInSeconds) {
    verifyRemoveEndNotEquals(remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is NOT equals to expected
   * value.
   *
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRemoveEndNotEquals(String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRemoveEndNotEquals(remove, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Removing '%s', Not Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is equals to expected value.
   *
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEquals(String remove, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRemoveEquals(remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is equals to expected value.
   *
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEquals(String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).removeEquals(remove, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is equals to expected value.
   *
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRemoveEquals(String remove, final String expected, final int waitInSeconds) {
    verifyRemoveEquals(remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is equals to expected value.
   *
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRemoveEquals(String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRemoveEquals(remove, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Removing '%s', Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is equals to expected
   * value.
   *
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveIgnoreCaseEquals(String remove, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRemoveIgnoreCaseEquals(remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is equals to expected
   * value.
   *
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveIgnoreCaseEquals(String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).removeIgnoreCaseEquals(remove, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is equals to expected
   * value.
   *
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRemoveIgnoreCaseEquals(String remove, final String expected, final int waitInSeconds) {
    verifyRemoveIgnoreCaseEquals(remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is equals to expected
   * value.
   *
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRemoveIgnoreCaseEquals(String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRemoveIgnoreCaseEquals(remove, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Removing '%s' Ignoring Case Sensitivity, Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveIgnoreCaseNotEquals(String remove, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRemoveIgnoreCaseNotEquals(remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveIgnoreCaseNotEquals(String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).removeIgnoreCaseNotEquals(remove, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRemoveIgnoreCaseNotEquals(String remove, final String expected, final int waitInSeconds) {
    verifyRemoveIgnoreCaseNotEquals(remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRemoveIgnoreCaseNotEquals(String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRemoveIgnoreCaseNotEquals(remove, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Removing '%s' Ignoring Case Sensitivity, Not Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is NOT equals to expected value.
   *
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveNotEquals(String remove, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRemoveNotEquals(remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is NOT equals to expected value.
   *
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveNotEquals(String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).removeNotEquals(remove, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is NOT equals to expected value.
   *
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRemoveNotEquals(String remove, final String expected, final int waitInSeconds) {
    verifyRemoveNotEquals(remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is NOT equals to expected value.
   *
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRemoveNotEquals(String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRemoveNotEquals(remove, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Removing '%s', Not Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is equals to expected
   * value.
   *
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveStartEquals(String remove, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRemoveStartEquals(remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is equals to expected
   * value.
   *
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveStartEquals(String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).removeStartEquals(remove, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is equals to expected
   * value.
   *
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRemoveStartEquals(String remove, final String expected, final int waitInSeconds) {
    verifyRemoveStartEquals(remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is equals to expected
   * value.
   *
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRemoveStartEquals(String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRemoveStartEquals(remove, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Removing '%s' From Start, Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveStartIgnoreCaseEquals(String remove, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRemoveStartIgnoreCaseEquals(remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveStartIgnoreCaseEquals(String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).removeStartIgnoreCaseEquals(remove, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRemoveStartIgnoreCaseEquals(String remove, final String expected, final int waitInSeconds) {
    verifyRemoveStartIgnoreCaseEquals(remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRemoveStartIgnoreCaseEquals(String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRemoveStartIgnoreCaseEquals(remove, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Removing '%s' From Start Ignoring Case Sensitivity, Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveStartIgnoreCaseNotEquals(String remove, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRemoveStartIgnoreCaseNotEquals(remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveStartIgnoreCaseNotEquals(String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).removeStartIgnoreCaseNotEquals(remove, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRemoveStartIgnoreCaseNotEquals(String remove, final String expected, final int waitInSeconds) {
    verifyRemoveStartIgnoreCaseNotEquals(remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRemoveStartIgnoreCaseNotEquals(String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRemoveStartIgnoreCaseNotEquals(remove, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Removing '%s' From Start Ignoring Case Sensitivity, Not Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is NOT equals to expected
   * value.
   *
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveStartNotEquals(String remove, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRemoveStartNotEquals(remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is NOT equals to expected
   * value.
   *
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveStartNotEquals(String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).removeStartNotEquals(remove, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is NOT equals to expected
   * value.
   *
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRemoveStartNotEquals(String remove, final String expected, final int waitInSeconds) {
    verifyRemoveStartNotEquals(remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is NOT equals to expected
   * value.
   *
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRemoveStartNotEquals(String remove, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRemoveStartNotEquals(remove, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Removing '%s' From Start, Not Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is equals to expected
   * value.
   *
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace it with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceEquals(String searchString, String replacement, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyReplaceEquals(searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is equals to expected
   * value.
   *
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace it with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceEquals(String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).replaceEquals(searchString, replacement, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is equals to expected
   * value.
   *
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace it with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyReplaceEquals(String searchString, String replacement, final String expected, final int waitInSeconds) {
    verifyReplaceEquals(searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is equals to expected
   * value.
   *
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace it with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyReplaceEquals(String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyReplaceEquals(searchString, replacement, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Replacing '%s' With '%s', Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is equals to
   * expected value.
   *
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace it with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceIgnoreCaseEquals(String searchString, String replacement, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyReplaceIgnoreCaseEquals(searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is equals to
   * expected value.
   *
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace it with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceIgnoreCaseEquals(String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).replaceIgnoreCaseEquals(searchString, replacement, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is equals to
   * expected value.
   *
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace it with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyReplaceIgnoreCaseEquals(String searchString, String replacement, final String expected, final int waitInSeconds) {
    verifyReplaceIgnoreCaseEquals(searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is equals to
   * expected value.
   *
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace it with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyReplaceIgnoreCaseEquals(String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyReplaceIgnoreCaseEquals(searchString, replacement, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Replacing '%s' With '%s' Ignoring Case Sensitivity, Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is NOT equals
   * to expected value.
   *
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace it with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceIgnoreCaseNotEquals(String searchString, String replacement, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyReplaceIgnoreCaseNotEquals(searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is NOT equals
   * to expected value.
   *
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace it with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceIgnoreCaseNotEquals(String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).replaceIgnoreCaseNotEquals(searchString, replacement, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is NOT equals
   * to expected value.
   *
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace it with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyReplaceIgnoreCaseNotEquals(String searchString, String replacement, final String expected, final int waitInSeconds) {
    verifyReplaceIgnoreCaseNotEquals(searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is NOT equals
   * to expected value.
   *
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace it with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyReplaceIgnoreCaseNotEquals(String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyReplaceIgnoreCaseNotEquals(searchString, replacement, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Replacing '%s' With '%s' Ignoring Case Sensitivity, Not Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace it with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceNotEquals(String searchString, String replacement, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyReplaceNotEquals(searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace it with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceNotEquals(String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).replaceNotEquals(searchString, replacement, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace it with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyReplaceNotEquals(String searchString, String replacement, final String expected, final int waitInSeconds) {
    verifyReplaceNotEquals(searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace it with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyReplaceNotEquals(String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyReplaceNotEquals(searchString, replacement, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Replacing '%s' With '%s', Not Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is equals to
   * expected value.
   *
   * @param searchString  the String to search for, may be null
   * @param replacement   the String to replace with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceOnceEquals(String searchString, String replacement, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyReplaceOnceEquals(searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is equals to
   * expected value.
   *
   * @param searchString           the String to search for, may be null
   * @param replacement            the String to replace with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceOnceEquals(String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).replaceOnceEquals(searchString, replacement, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is equals to
   * expected value.
   *
   * @param searchString  the String to search for, may be null
   * @param replacement   the String to replace with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyReplaceOnceEquals(String searchString, String replacement, final String expected, final int waitInSeconds) {
    verifyReplaceOnceEquals(searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is equals to
   * expected value.
   *
   * @param searchString           the String to search for, may be null
   * @param replacement            the String to replace with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyReplaceOnceEquals(String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyReplaceOnceEquals(searchString, replacement, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Replacing Once '%s' With '%s', Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is equals
   * to expected value.
   *
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceOnceIgnoreCaseEquals(String searchString, String replacement, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyReplaceOnceIgnoreCaseEquals(searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is equals
   * to expected value.
   *
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceOnceIgnoreCaseEquals(String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).replaceOnceIgnoreCaseEquals(searchString, replacement, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is equals
   * to expected value.
   *
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyReplaceOnceIgnoreCaseEquals(String searchString, String replacement, final String expected, final int waitInSeconds) {
    verifyReplaceOnceIgnoreCaseEquals(searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is equals
   * to expected value.
   *
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyReplaceOnceIgnoreCaseEquals(String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyReplaceOnceIgnoreCaseEquals(searchString, replacement, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Replacing Once '%s' With '%s' Ignoring Case Sensitivity, Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is NOT
   * equals to expected value.
   *
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceOnceIgnoreCaseNotEquals(String searchString, String replacement, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyReplaceOnceIgnoreCaseNotEquals(searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is NOT
   * equals to expected value.
   *
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceOnceIgnoreCaseNotEquals(String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).replaceOnceIgnoreCaseNotEquals(searchString, replacement, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is NOT
   * equals to expected value.
   *
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyReplaceOnceIgnoreCaseNotEquals(String searchString, String replacement, final String expected, final int waitInSeconds) {
    verifyReplaceOnceIgnoreCaseNotEquals(searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is NOT
   * equals to expected value.
   *
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyReplaceOnceIgnoreCaseNotEquals(String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyReplaceOnceIgnoreCaseNotEquals(searchString, replacement, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Replacing Once '%s' With '%s' Ignoring Case Sensitivity, Not Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param searchString  the String to search for, may be null
   * @param replacement   the String to replace with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceOnceNotEquals(String searchString, String replacement, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyReplaceOnceNotEquals(searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param searchString           the String to search for, may be null
   * @param replacement            the String to replace with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceOnceNotEquals(String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).replaceOnceNotEquals(searchString, replacement, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param searchString  the String to search for, may be null
   * @param replacement   the String to replace with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyReplaceOnceNotEquals(String searchString, String replacement, final String expected, final int waitInSeconds) {
    verifyReplaceOnceNotEquals(searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param searchString           the String to search for, may be null
   * @param replacement            the String to replace with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyReplaceOnceNotEquals(String searchString, String replacement, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyReplaceOnceNotEquals(searchString, replacement, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Replacing Once '%s' With '%s', Not Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is equals to expected value.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyReverseEquals(final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyReverseEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is equals to expected value.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyReverseEquals(final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).reverseEquals(expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is equals to expected value.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyReverseEquals(final String expected, final int waitInSeconds) {
    verifyReverseEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is equals to expected value.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyReverseEquals(final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyReverseEquals(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Reversing Order Of Characters, Equals To The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is NOT equals to expected value.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyReverseNotEquals(final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyReverseNotEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is NOT equals to expected value.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyReverseNotEquals(final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).reverseNotEquals(expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is NOT equals to expected value.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyReverseNotEquals(final String expected, final int waitInSeconds) {
    verifyReverseNotEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is NOT equals to expected value.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyReverseNotEquals(final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyReverseNotEquals(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value, After Reversing Order Of Characters, Not Equals To The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is equals to expected
   * value.
   *
   * @param size          the size to pad to
   * @param padStr        the String to pad with, null or empty treated as single space
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRightPadEquals(int size, String padStr, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRightPadEquals(size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is equals to expected
   * value.
   *
   * @param size                   the size to pad to
   * @param padStr                 the String to pad with, null or empty treated as single space
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRightPadEquals(int size, String padStr, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).rightPadEquals(size, padStr, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is equals to expected
   * value.
   *
   * @param size          the size to pad to
   * @param padStr        the String to pad with, null or empty treated as single space
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRightPadEquals(int size, String padStr, final String expected, final int waitInSeconds) {
    verifyRightPadEquals(size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is equals to expected
   * value.
   *
   * @param size                   the size to pad to
   * @param padStr                 the String to pad with, null or empty treated as single space
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRightPadEquals(int size, String padStr, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRightPadEquals(size, padStr, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Expected Value Equals To The Actual Value Right Pad With '%s' And The Length Of '%d'", padStr, size));
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param size          the size to pad to
   * @param padStr        the String to pad with, null or empty treated as single space
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRightPadNotEquals(int size, String padStr, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRightPadNotEquals(size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param size                   the size to pad to
   * @param padStr                 the String to pad with, null or empty treated as single space
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRightPadNotEquals(int size, String padStr, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).rightPadNotEquals(size, padStr, expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param size          the size to pad to
   * @param padStr        the String to pad with, null or empty treated as single space
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyRightPadNotEquals(int size, String padStr, final String expected, final int waitInSeconds) {
    verifyRightPadNotEquals(size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param size                   the size to pad to
   * @param padStr                 the String to pad with, null or empty treated as single space
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRightPadNotEquals(int size, String padStr, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRightPadNotEquals(size, padStr, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Expected Value Not Equals To The Actual Value Right Pad With '%s' And The Length Of '%d'", padStr, size));
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} equals to expected value.
   *
   * @param expected      the expected result.
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRightValueEquals(int len, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRightValueEquals(len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} equals to expected value.
   *
   * @param expected               the expected result.
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRightValueEquals(int len, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).rightValueEquals(len, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} equals to expected value.
   *
   * @param expected      the expected result.
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time
   */
  default void verifyRightValueEquals(int len, final String expected, final int waitInSeconds) {
    verifyRightValueEquals(len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} equals to expected value.
   *
   * @param expected               the expected result.
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRightValueEquals(int len, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRightValueEquals(len, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Expected Value Equals To The Right '%d' Character Of Actual Value", len));
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} NOT equals to expected value.
   *
   * @param expected      the expected result.
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyRightValueNotEquals(int len, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyRightValueNotEquals(len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} NOT equals to expected value.
   *
   * @param expected               the expected result.
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyRightValueNotEquals(int len, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).rightValueNotEquals(len, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} NOT equals to expected value.
   *
   * @param expected      the expected result.
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time
   */
  default void verifyRightValueNotEquals(int len, final String expected, final int waitInSeconds) {
    verifyRightValueNotEquals(len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} NOT equals to expected value.
   *
   * @param expected               the expected result.
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyRightValueNotEquals(int len, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyRightValueNotEquals(len, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Expected Value Not Equals To The Right '%d' Character Of Actual Value", len));
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is true
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyStartsWith(final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyStartsWith(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is true
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyStartsWith(final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).startsWith(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is true
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyStartsWith(final String expected, final int waitInSeconds) {
    verifyStartsWith(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is true
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyStartsWith(final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyStartsWith(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Starts With Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is true
   *
   * @param searchInputs  the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                      null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyStartsWithAny(List<String> searchInputs, final int waitInSeconds, final String message, final Object... params) {
    verifyStartsWithAny(searchInputs, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is true
   *
   * @param searchInputs           the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                               null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyStartsWithAny(List<String> searchInputs, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(searchInputs, (a, b) -> _toState(a).startsWithAny(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is true
   *
   * @param searchInputs  the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                      null}
   * @param waitInSeconds maximum wait time
   */
  default void verifyStartsWithAny(List<String> searchInputs, final int waitInSeconds) {
    verifyStartsWithAny(searchInputs, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is true
   *
   * @param searchInputs           the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                               null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyStartsWithAny(List<String> searchInputs, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyStartsWithAny(searchInputs, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Starts With Any Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * true
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyStartsWithIgnoreCase(final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyStartsWithIgnoreCase(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * true
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyStartsWithIgnoreCase(final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).startsWithIgnoreCase(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * true
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyStartsWithIgnoreCase(final String expected, final int waitInSeconds) {
    verifyStartsWithIgnoreCase(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * true
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyStartsWithIgnoreCase(final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyStartsWithIgnoreCase(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Starts With Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is false
   *
   * @param searchInputs  the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                      null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyStartsWithNone(List<String> searchInputs, final int waitInSeconds, final String message, final Object... params) {
    verifyStartsWithNone(searchInputs, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is false
   *
   * @param searchInputs           the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                               null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyStartsWithNone(List<String> searchInputs, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(searchInputs, (a, b) -> _toState(a).startsWithNone(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is false
   *
   * @param searchInputs  the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                      null}
   * @param waitInSeconds maximum wait time
   */
  default void verifyStartsWithNone(List<String> searchInputs, final int waitInSeconds) {
    verifyStartsWithNone(searchInputs, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is false
   *
   * @param searchInputs           the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                               null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyStartsWithNone(List<String> searchInputs, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyStartsWithNone(searchInputs, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Starts With None Of Expected Value"));
  }

  /**
   * Verify if {@link CStringUtil#stripEnd(String, String)} value equals the expected value.
   *
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedEndValue(String stripChars, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyStripedEndValue(stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#stripEnd(String, String)} value equals the expected value.
   *
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedEndValue(String stripChars, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).stripedEndValue(stripChars, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#stripEnd(String, String)} value equals the expected value.
   *
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyStripedEndValue(String stripChars, final String expected, final int waitInSeconds) {
    verifyStripedEndValue(stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#stripEnd(String, String)} value equals the expected value.
   *
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyStripedEndValue(String stripChars, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyStripedEndValue(stripChars, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Striped End '%s' Characters, Equals To The Expected Value", stripChars));
  }

  /**
   * Verify if {@link CStringUtil#stripEnd(String, String)} value NOT equals the expected value.
   *
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedEndValueNot(String stripChars, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyStripedEndValueNot(stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#stripEnd(String, String)} value NOT equals the expected value.
   *
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedEndValueNot(String stripChars, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).stripedEndValueNot(stripChars, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#stripEnd(String, String)} value NOT equals the expected value.
   *
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyStripedEndValueNot(String stripChars, final String expected, final int waitInSeconds) {
    verifyStripedEndValueNot(stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#stripEnd(String, String)} value NOT equals the expected value.
   *
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyStripedEndValueNot(String stripChars, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyStripedEndValueNot(stripChars, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Striped End '%s' Characters, Not Equals To The Expected Value", stripChars));
  }

  /**
   * Verify if {@link CStringUtil#stripStart(String, String)} value equals the expected value.
   *
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedStartValue(String stripChars, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyStripedStartValue(stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#stripStart(String, String)} value equals the expected value.
   *
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedStartValue(String stripChars, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).stripedStartValue(stripChars, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#stripStart(String, String)} value equals the expected value.
   *
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyStripedStartValue(String stripChars, final String expected, final int waitInSeconds) {
    verifyStripedStartValue(stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#stripStart(String, String)} value equals the expected value.
   *
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyStripedStartValue(String stripChars, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyStripedStartValue(stripChars, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Striped Start '%s' Characters, Equals To The Expected Value", stripChars));
  }

  /**
   * Verify if {@link CStringUtil#stripStart(String, String)} value NOT equals the expected value.
   *
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedStartValueNot(String stripChars, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyStripedStartValueNot(stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#stripStart(String, String)} value NOT equals the expected value.
   *
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedStartValueNot(String stripChars, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).stripedStartValueNot(stripChars, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#stripStart(String, String)} value NOT equals the expected value.
   *
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyStripedStartValueNot(String stripChars, final String expected, final int waitInSeconds) {
    verifyStripedStartValueNot(stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#stripStart(String, String)} value NOT equals the expected value.
   *
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyStripedStartValueNot(String stripChars, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyStripedStartValueNot(stripChars, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Striped End '%s' Characters, Not Equals To The Expected Value", stripChars));
  }

  /**
   * Verify if {@link CStringUtil#strip(String)} value equals the expected value.
   *
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedValue(String stripChars, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyStripedValue(stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#strip(String)} value equals the expected value.
   *
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedValue(String stripChars, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).stripedValue(stripChars, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#strip(String)} value equals the expected value.
   *
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyStripedValue(String stripChars, final String expected, final int waitInSeconds) {
    verifyStripedValue(stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#strip(String)} value equals the expected value.
   *
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyStripedValue(String stripChars, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyStripedValue(stripChars, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Striped '%s' Characters, Equals To The Expected Value", stripChars));
  }

  /**
   * Verify if {@link CStringUtil#strip(String)} value NOT equals the expected value.
   *
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedValueNot(String stripChars, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyStripedValueNot(stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#strip(String)} value NOT equals the expected value.
   *
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedValueNot(String stripChars, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).stripedValueNot(stripChars, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#strip(String)} value NOT equals the expected value.
   *
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyStripedValueNot(String stripChars, final String expected, final int waitInSeconds) {
    verifyStripedValueNot(stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#strip(String)} value NOT equals the expected value.
   *
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyStripedValueNot(String stripChars, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyStripedValueNot(stripChars, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Striped '%s' Characters, Not Equals To The Expected Value", stripChars));
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} equals to expected
   * value.
   *
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringAfterEquals(String separator, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringAfterEquals(separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} equals to expected
   * value.
   *
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringAfterEquals(String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringAfterEquals(separator, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} equals to expected
   * value.
   *
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringAfterEquals(String separator, final String expected, final int waitInSeconds) {
    verifySubstringAfterEquals(separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} equals to expected
   * value.
   *
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringAfterEquals(String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringAfterEquals(separator, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring After '%s', Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} equals to expected
   * value.
   *
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringAfterLastEquals(String separator, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringAfterLastEquals(separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} equals to expected
   * value.
   *
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringAfterLastEquals(String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringAfterLastEquals(separator, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} equals to expected
   * value.
   *
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringAfterLastEquals(String separator, final String expected, final int waitInSeconds) {
    verifySubstringAfterLastEquals(separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} equals to expected
   * value.
   *
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringAfterLastEquals(String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringAfterLastEquals(separator, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring After Last '%s', Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} NOT equals to
   * expected value.
   *
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringAfterLastNotEquals(String separator, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringAfterLastNotEquals(separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} NOT equals to
   * expected value.
   *
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringAfterLastNotEquals(String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringAfterLastNotEquals(separator, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} NOT equals to
   * expected value.
   *
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringAfterLastNotEquals(String separator, final String expected, final int waitInSeconds) {
    verifySubstringAfterLastNotEquals(separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} NOT equals to
   * expected value.
   *
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringAfterLastNotEquals(String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringAfterLastNotEquals(separator, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring After Last '%s', Not Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} NOT equals to expected
   * value.
   *
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringAfterNotEquals(String separator, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringAfterNotEquals(separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} NOT equals to expected
   * value.
   *
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringAfterNotEquals(String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringAfterNotEquals(separator, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} NOT equals to expected
   * value.
   *
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringAfterNotEquals(String separator, final String expected, final int waitInSeconds) {
    verifySubstringAfterNotEquals(separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} NOT equals to expected
   * value.
   *
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringAfterNotEquals(String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringAfterNotEquals(separator, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring After '%s', Not Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} equals to expected
   * value.
   *
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBeforeEquals(String separator, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringBeforeEquals(separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} equals to expected
   * value.
   *
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBeforeEquals(String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringBeforeEquals(separator, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} equals to expected
   * value.
   *
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringBeforeEquals(String separator, final String expected, final int waitInSeconds) {
    verifySubstringBeforeEquals(separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} equals to expected
   * value.
   *
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringBeforeEquals(String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringBeforeEquals(separator, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring Before '%s', Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} equals to expected
   * value.
   *
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBeforeLastEquals(String separator, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringBeforeLastEquals(separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} equals to expected
   * value.
   *
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBeforeLastEquals(String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringBeforeLastEquals(separator, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} equals to expected
   * value.
   *
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringBeforeLastEquals(String separator, final String expected, final int waitInSeconds) {
    verifySubstringBeforeLastEquals(separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} equals to expected
   * value.
   *
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringBeforeLastEquals(String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringBeforeLastEquals(separator, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring Before Last '%s', Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} NOT equals to
   * expected value.
   *
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBeforeLastNotEquals(String separator, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringBeforeLastNotEquals(separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} NOT equals to
   * expected value.
   *
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBeforeLastNotEquals(String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringBeforeLastNotEquals(separator, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} NOT equals to
   * expected value.
   *
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringBeforeLastNotEquals(String separator, final String expected, final int waitInSeconds) {
    verifySubstringBeforeLastNotEquals(separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} NOT equals to
   * expected value.
   *
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringBeforeLastNotEquals(String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringBeforeLastNotEquals(separator, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring Before Last '%s', Not Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} NOT equals to expected
   * value.
   *
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBeforeNotEquals(String separator, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringBeforeNotEquals(separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} NOT equals to expected
   * value.
   *
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBeforeNotEquals(String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringBeforeNotEquals(separator, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} NOT equals to expected
   * value.
   *
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringBeforeNotEquals(String separator, final String expected, final int waitInSeconds) {
    verifySubstringBeforeNotEquals(separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} NOT equals to expected
   * value.
   *
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringBeforeNotEquals(String separator, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringBeforeNotEquals(separator, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring Before '%s', Not Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String)} equals to expected
   * value.
   *
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBetweenEquals(String open, String close, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringBetweenEquals(open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String)} equals to expected
   * value.
   *
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBetweenEquals(String open, String close, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringBetweenEquals(open, close, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String)} equals to expected
   * value.
   *
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringBetweenEquals(String open, String close, final String expected, final int waitInSeconds) {
    verifySubstringBetweenEquals(open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String)} equals to expected
   * value.
   *
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringBetweenEquals(String open, String close, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringBetweenEquals(open, close, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring Between '%s' and '%s', Equals To The Expected Value", open, close));
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String)} NOT equals to expected
   * value.
   *
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBetweenNotEquals(String open, String close, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringBetweenNotEquals(open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String)} NOT equals to expected
   * value.
   *
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBetweenNotEquals(String open, String close, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringBetweenNotEquals(open, close, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String)} NOT equals to expected
   * value.
   *
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringBetweenNotEquals(String open, String close, final String expected, final int waitInSeconds) {
    verifySubstringBetweenNotEquals(open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String)} NOT equals to expected
   * value.
   *
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringBetweenNotEquals(String open, String close, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringBetweenNotEquals(open, close, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring Between '%s' and '%s', Not Equals To The Expected Value", open, close));
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} equals to expected value.
   *
   * @param expected      the expected result.
   * @param start         the position to start from, negative means count back from the end of the String
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringEquals(int start, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringEquals(start, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} equals to expected value.
   *
   * @param expected      the expected result.
   * @param start         the position to start from, negative means count back from the end of the String
   * @param end           the position to end at (exclusive), negative means count back from the end of the
   *                      String
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringEquals(int start, int end, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringEquals(start, end, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} equals to expected value.
   *
   * @param expected               the expected result.
   * @param start                  the position to start from, negative means count back from the end of the String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringEquals(int start, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringEquals(start, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} equals to expected value.
   *
   * @param expected               the expected result.
   * @param start                  the position to start from, negative means count back from the end of the String
   * @param end                    the position to end at (exclusive), negative means count back from the end of the
   *                               String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringEquals(int start, int end, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringEquals(start, end, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} equals to expected value.
   *
   * @param expected      the expected result.
   * @param start         the position to start from, negative means count back from the end of the String
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringEquals(int start, final String expected, final int waitInSeconds) {
    verifySubstringEquals(start, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} equals to expected value.
   *
   * @param expected               the expected result.
   * @param start                  the position to start from, negative means count back from the end of the String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringEquals(int start, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringEquals(start, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring After Position '%s', Equals To The Expected Value", start));
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} equals to expected value.
   *
   * @param expected      the expected result.
   * @param start         the position to start from, negative means count back from the end of the String
   * @param end           the position to end at (exclusive), negative means count back from the end of the
   *                      String
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringEquals(int start, int end, final String expected, final int waitInSeconds) {
    verifySubstringEquals(start, end, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} equals to expected value.
   *
   * @param expected               the expected result.
   * @param start                  the position to start from, negative means count back from the end of the String
   * @param end                    the position to end at (exclusive), negative means count back from the end of the
   *                               String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringEquals(int start, int end, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringEquals(start, end, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring From Position '%s' To '%s', Equals To The Expected Value", start, end));
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} NOT equals to expected value.
   *
   * @param expected      the expected result.
   * @param start         the position to start from, negative means count back from the end of the String
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringNotEquals(int start, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringNotEquals(start, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} NOT equals to expected
   * value.
   *
   * @param expected      the expected result.
   * @param start         the position to start from, negative means count back from the end of the String
   * @param end           the position to end at (exclusive), negative means count back from the end of the
   *                      String
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringNotEquals(int start, int end, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringNotEquals(start, end, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} NOT equals to expected value.
   *
   * @param expected               the expected result.
   * @param start                  the position to start from, negative means count back from the end of the String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringNotEquals(int start, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringNotEquals(start, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} NOT equals to expected
   * value.
   *
   * @param expected               the expected result.
   * @param start                  the position to start from, negative means count back from the end of the String
   * @param end                    the position to end at (exclusive), negative means count back from the end of the
   *                               String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringNotEquals(int start, int end, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringNotEquals(start, end, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} NOT equals to expected value.
   *
   * @param expected      the expected result.
   * @param start         the position to start from, negative means count back from the end of the String
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringNotEquals(int start, final String expected, final int waitInSeconds) {
    verifySubstringNotEquals(start, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} NOT equals to expected value.
   *
   * @param expected               the expected result.
   * @param start                  the position to start from, negative means count back from the end of the String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringNotEquals(int start, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringNotEquals(start, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring After Position '%s', Not Equals To The Expected Value", start));
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} NOT equals to expected
   * value.
   *
   * @param expected      the expected result.
   * @param start         the position to start from, negative means count back from the end of the String
   * @param end           the position to end at (exclusive), negative means count back from the end of the
   *                      String
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringNotEquals(int start, int end, final String expected, final int waitInSeconds) {
    verifySubstringNotEquals(start, end, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} NOT equals to expected
   * value.
   *
   * @param expected               the expected result.
   * @param start                  the position to start from, negative means count back from the end of the String
   * @param end                    the position to end at (exclusive), negative means count back from the end of the
   *                               String
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringNotEquals(int start, int end, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringNotEquals(start, end, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring From Position '%s' To '%s', Not Equals To The Expected Value", start, end));
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} contains to
   * expected value.
   *
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringsBetweenContains(String open, String close, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringsBetweenContains(open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} contains to
   * expected value.
   *
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringsBetweenContains(String open, String close, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringsBetweenContains(open, close, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} Contains
   * expected value.
   *
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringsBetweenContains(String open, String close, final String expected, final int waitInSeconds) {
    verifySubstringsBetweenContains(open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} Contains
   * expected value.
   *
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringsBetweenContains(String open, String close, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringsBetweenContains(open, close, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring Between '%s' To '%s' Characters, Contains The Expected Value", open, close));
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} equals to
   * expected value.
   *
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringsBetweenEquals(String open, String close, List<String> expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringsBetweenEquals(open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} equals to
   * expected value.
   *
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringsBetweenEquals(String open, String close, List<String> expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringsBetweenEquals(open, close, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} equals to
   * expected value.
   *
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringsBetweenEquals(String open, String close, List<String> expected, final int waitInSeconds) {
    verifySubstringsBetweenEquals(open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} equals to
   * expected value.
   *
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringsBetweenEquals(String open, String close, List<String> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringsBetweenEquals(open, close, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring Between '%s' To '%s' Characters, Equals To The Expected Value", open, close));
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT contains
   * to expected value.
   *
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringsBetweenNotContains(String open, String close, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringsBetweenNotContains(open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT contains
   * to expected value.
   *
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringsBetweenNotContains(String open, String close, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringsBetweenNotContains(open, close, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT Contains
   * expected value.
   *
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringsBetweenNotContains(String open, String close, final String expected, final int waitInSeconds) {
    verifySubstringsBetweenNotContains(open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT Contains
   * expected value.
   *
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringsBetweenNotContains(String open, String close, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringsBetweenNotContains(open, close, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring Between '%s' To '%s' Characters, Not Contains The Expected Value", open, close));
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT equals to
   * expected value.
   *
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringsBetweenNotEquals(String open, String close, List<String> expected, final int waitInSeconds, final String message, final Object... params) {
    verifySubstringsBetweenNotEquals(open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT equals to
   * expected value.
   *
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringsBetweenNotEquals(String open, String close, List<String> expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringsBetweenNotEquals(open, close, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT equals to
   * expected value.
   *
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time
   */
  default void verifySubstringsBetweenNotEquals(String open, String close, List<String> expected, final int waitInSeconds) {
    verifySubstringsBetweenNotEquals(open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT equals to
   * expected value.
   *
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifySubstringsBetweenNotEquals(String open, String close, List<String> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifySubstringsBetweenNotEquals(open, close, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Value Substring Between '%s' To '%s' Characters, Not Equals The Expected Value", open, close));
  }

  /**
   * Verify if {@link CStringUtil#trim(String)} value equals the expected value.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyTrimmedValueEquals(final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyTrimmedValueEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#trim(String)} value equals the expected value.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyTrimmedValueEquals(final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).trimmedValueEquals(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#trim(String)} value equals the expected value.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyTrimmedValueEquals(final String expected, final int waitInSeconds) {
    verifyTrimmedValueEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#trim(String)} value equals the expected value.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyTrimmedValueEquals(final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyTrimmedValueEquals(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Trimmed Value, Equals The Expected Value"));
  }

  /**
   * Verify if {@link CStringUtil#trim(String)} value NOT equals the expected value.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyTrimmedValueNotEquals(final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyTrimmedValueNotEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#trim(String)} value NOT equals the expected value.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyTrimmedValueNotEquals(final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).trimmedValueNotEquals(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#trim(String)} value NOT equals the expected value.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyTrimmedValueNotEquals(final String expected, final int waitInSeconds) {
    verifyTrimmedValueNotEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#trim(String)} value NOT equals the expected value.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyTrimmedValueNotEquals(final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyTrimmedValueNotEquals(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Trimmed Value, Not Equals The Expected Value"));
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int)} value equals the expected value.
   *
   * @param maxWidth      maximum length of truncated string, must be positive
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyTruncatedValueEquals(int maxWidth, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyTruncatedValueEquals(maxWidth, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int, int)} value equals the expected value.
   *
   * @param offset        left edge of string to start truncate from
   * @param maxWidth      maximum length of truncated string, must be positive
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyTruncatedValueEquals(int offset, int maxWidth, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyTruncatedValueEquals(offset, maxWidth, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int)} value equals the expected value.
   *
   * @param maxWidth               maximum length of truncated string, must be positive
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyTruncatedValueEquals(int maxWidth, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).truncatedValueEquals(maxWidth, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int, int)} value equals the expected value.
   *
   * @param offset                 left edge of string to start truncate from
   * @param maxWidth               maximum length of truncated string, must be positive
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyTruncatedValueEquals(int offset, int maxWidth, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).truncatedValueEquals(offset, maxWidth, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int)} value equals the expected value.
   *
   * @param maxWidth      maximum length of truncated string, must be positive
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyTruncatedValueEquals(int maxWidth, final String expected, final int waitInSeconds) {
    verifyTruncatedValueEquals(maxWidth, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int)} value equals the expected value.
   *
   * @param maxWidth               maximum length of truncated string, must be positive
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyTruncatedValueEquals(int maxWidth, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyTruncatedValueEquals(maxWidth, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Truncated Value With Maximum Width Of %s, Equals The Expected Value", maxWidth));
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int, int)} value equals the expected value.
   *
   * @param offset        left edge of string to start truncate from
   * @param maxWidth      maximum length of truncated string, must be positive
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyTruncatedValueEquals(int offset, int maxWidth, final String expected, final int waitInSeconds) {
    verifyTruncatedValueEquals(offset, maxWidth, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int, int)} value equals the expected value.
   *
   * @param offset                 left edge of string to start truncate from
   * @param maxWidth               maximum length of truncated string, must be positive
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyTruncatedValueEquals(int offset, int maxWidth, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyTruncatedValueEquals(offset, maxWidth, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Truncated Value With Maximum Width Of %s With Offset %s, Equals The Expected Value", maxWidth, offset));
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int)} value equals the expected value.
   *
   * @param maxWidth      maximum length of truncated string, must be positive
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyTruncatedValueNotEquals(int maxWidth, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyTruncatedValueNotEquals(maxWidth, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int, int)} value NOT equals the expected value.
   *
   * @param offset        left edge of string to start truncate from
   * @param maxWidth      maximum length of truncated string, must be positive
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyTruncatedValueNotEquals(int offset, int maxWidth, final String expected, final int waitInSeconds, final String message, final Object... params) {
    verifyTruncatedValueNotEquals(offset, maxWidth, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int)} value equals the expected value.
   *
   * @param maxWidth               maximum length of truncated string, must be positive
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyTruncatedValueNotEquals(int maxWidth, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).truncatedValueNotEquals(maxWidth, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int, int)} value NOT equals the expected value.
   *
   * @param offset                 left edge of string to start truncate from
   * @param maxWidth               maximum length of truncated string, must be positive
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyTruncatedValueNotEquals(int offset, int maxWidth, final String expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).truncatedValueNotEquals(offset, maxWidth, b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int)} value equals the expected value.
   *
   * @param maxWidth      maximum length of truncated string, must be positive
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyTruncatedValueNotEquals(int maxWidth, final String expected, final int waitInSeconds) {
    verifyTruncatedValueNotEquals(maxWidth, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int)} value equals the expected value.
   *
   * @param maxWidth               maximum length of truncated string, must be positive
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyTruncatedValueNotEquals(int maxWidth, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyTruncatedValueNotEquals(maxWidth, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Truncated Value With Maximum Width Of %s, Not Equals The Expected Value", maxWidth));
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int, int)} value NOT equals the expected value.
   *
   * @param offset        left edge of string to start truncate from
   * @param maxWidth      maximum length of truncated string, must be positive
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time
   */
  default void verifyTruncatedValueNotEquals(int offset, int maxWidth, final String expected, final int waitInSeconds) {
    verifyTruncatedValueNotEquals(offset, maxWidth, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int, int)} value NOT equals the expected value.
   *
   * @param offset                 left edge of string to start truncate from
   * @param maxWidth               maximum length of truncated string, must be positive
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyTruncatedValueNotEquals(int offset, int maxWidth, final String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyTruncatedValueNotEquals(offset, maxWidth, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Actual Truncated Value With Maximum Width Of %s With Offset %s, Not Equals The Expected Value", maxWidth, offset));
  }
}
