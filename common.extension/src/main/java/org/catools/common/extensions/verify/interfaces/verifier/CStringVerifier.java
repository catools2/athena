package org.catools.common.extensions.verify.interfaces.verifier;

import org.catools.common.extensions.base.CBaseStringExtension;
import org.catools.common.extensions.states.interfaces.CStringState;
import org.catools.common.extensions.verify.CVerificationQueue;
import org.catools.common.extensions.verify.hard.CStringVerification;
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
public interface CStringVerifier extends CBaseStringExtension, CObjectVerifier<String, CStringState> {

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is equals to expected
   * value.
   *
   * @param verifier CVerificationQueue instance
   * @param size     the int size of new String, negative treated as zero
   * @param padStr   the String to pad the new String with, must not be null or empty
   * @param expected the expected result.
   */
  default void verifyCenterPadEquals(final CVerificationQueue verifier, int size, String padStr, final String expected) {
    verifyCenterPadEquals(verifier, size, padStr, expected, getDefaultMessage("Value Center Pad With '%s' And The Length Of '%d' Equals To Expected Value", padStr, size));
  }

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is equals to expected
   * value.
   *
   * @param verifier CVerificationQueue instance
   * @param size     the int size of new String, negative treated as zero
   * @param padStr   the String to pad the new String with, must not be null or empty
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyCenterPadEquals(CVerificationQueue verifier, int size, String padStr, final String expected, String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).centerPadEquals(size, padStr, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param verifier CVerificationQueue instance
   * @param size     the int size of new String, negative treated as zero
   * @param padStr   the String to pad the new String with, must not be null or empty
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyCenterPadNotEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).centerPadNotEquals(size, padStr, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param verifier CVerificationQueue instance
   * @param size     the int size of new String, negative treated as zero
   * @param padStr   the String to pad the new String with, must not be null or empty
   * @param expected the expected result.
   */
  default void verifyCenterPadNotEquals(CVerificationQueue verifier, int size, String padStr, final String expected) {
    verifyCenterPadNotEquals(verifier, size, padStr, expected, getDefaultMessage("Value Center Pad With '%s' And The Length Of '%d' Is Not Equal To Expected Value", padStr, size));
  }

  /**
   * Verify if result of {@link CStringUtil#compare(String, String)} equals to the expected value.
   *
   * @param verifier        CVerificationQueue instance
   * @param stringToCompare the string value to compare against
   * @param expected        the expected result.
   */
  default void verifyCompare(CVerificationQueue verifier, String stringToCompare, int expected) {
    verifyCompare(verifier, stringToCompare, expected, getDefaultMessage("Result Of Comparison With The Expected Value Is '%d'", expected));
  }

  /**
   * Verify if result of {@link CStringUtil#compare(String, String)} equals to the expected value.
   *
   * @param verifier        CVerificationQueue instance
   * @param stringToCompare the string value to compare against
   * @param expected        the expected result.
   * @param message         information about the purpose of this verification.
   * @param params          parameters in case if message is a format {@link String#format}
   */
  default void verifyCompare(CVerificationQueue verifier, String stringToCompare, int expected, final String message, final Object... params) {
    _verify(verifier, stringToCompare, (a, b) -> _toState(a).compare(b, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#compareIgnoreCase(String, String)} equals to the
   * expected value.
   *
   * @param verifier        CVerificationQueue instance
   * @param stringToCompare the string value to compare against
   * @param expected        the expected result.
   */
  default void verifyCompareIgnoreCase(CVerificationQueue verifier, String stringToCompare, int expected) {
    verifyCompareIgnoreCase(verifier, stringToCompare, expected, getDefaultMessage("Result Of Comparison (Ignoring Case) With The Expected Value Is '%d'", expected));
  }

  /**
   * Verify if result of {@link CStringUtil#compareIgnoreCase(String, String)} equals to the
   * expected value.
   *
   * @param verifier        CVerificationQueue instance
   * @param stringToCompare the string value to compare against
   * @param expected        the expected result.
   * @param message         information about the purpose of this verification.
   * @param params          parameters in case if message is a format {@link String#format}
   */
  default void verifyCompareIgnoreCase(CVerificationQueue verifier, String stringToCompare, int expected, String message, final Object... params) {
    _verify(verifier, stringToCompare, (a, b) -> _toState(a).compareIgnoreCase(b, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is true.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   */
  default void verifyContains(final CVerificationQueue verifier, final String expected) {
    verifyContains(verifier, expected, getDefaultMessage("Value Contains The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is true.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(CVerificationQueue verifier, String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).contains(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is true,
   * ignoring case.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   */
  default void verifyContainsIgnoreCase(CVerificationQueue verifier, final String expected) {
    verifyContainsIgnoreCase(verifier, expected, getDefaultMessage("Value Contains The Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is true,
   * ignoring case.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsIgnoreCase(CVerificationQueue verifier, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).containsIgnoreCase(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#containsAnyIgnoreCase(CharSequence, CharSequence...)}
   * equals to any expected value, ignoring case.
   *
   * @param verifier     CVerificationQueue instance
   * @param expectedList a list of strings, may be {@code null}.
   */
  default void verifyContainsAny(final CVerificationQueue verifier, List<String> expectedList) {
    verifyContainsAny(verifier, expectedList, getDefaultMessage("Value Ends With Any Value From The Expected Values Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#containsAnyIgnoreCase(CharSequence, CharSequence...)}
   * equals to any expected value, ignoring case.
   *
   * @param verifier     CVerificationQueue instance
   * @param expectedList a list of strings, may be {@code null}.
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsAny(CVerificationQueue verifier, List<String> expectedList, final String message, final Object... params) {
    _verify(verifier, expectedList, (a, b) -> _toState(a).containsAny(expectedList), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#containsAnyIgnoreCase(CharSequence, CharSequence...)}
   * equals to any expected value, ignoring case.
   *
   * @param verifier     CVerificationQueue instance
   * @param expectedList a list of strings, may be {@code null}.
   */
  default void verifyContainsAnyIgnoreCase(CVerificationQueue verifier, List<String> expectedList) {
    verifyContainsAnyIgnoreCase(verifier, expectedList, getDefaultMessage("Value Ends With Any Value From The Expected Values Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#containsAnyIgnoreCase(CharSequence, CharSequence...)}
   * equals to any expected value, ignoring case.
   *
   * @param verifier     CVerificationQueue instance
   * @param expectedList a list of strings, may be {@code null}.
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsAnyIgnoreCase(CVerificationQueue verifier, List<String> expectedList, final String message, final Object... params) {
    _verify(verifier, expectedList, (a, b) -> _toState(a).containsAnyIgnoreCase(expectedList), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is true
   *
   * @param verifier CVerificationQueue instance
   * @param suffix   the suffix to find, may be {@code null}
   */
  default void verifyEndsWith(final CVerificationQueue verifier, String suffix) {
    verifyEndsWith(verifier, suffix, getDefaultMessage("Value Ends With The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is true
   *
   * @param verifier CVerificationQueue instance
   * @param suffix   the suffix to find, may be {@code null}
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyEndsWith(CVerificationQueue verifier, String suffix, final String message, final Object... params) {
    _verify(verifier, suffix, (a, b) -> _toState(a).endsWith(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is true.
   *
   * @param verifier     CVerificationQueue instance
   * @param searchInputs the case-sensitive CharSequences to find, may be empty or contain {@code
   *                     null}
   */
  default void verifyEndsWithAny(final CVerificationQueue verifier, List<String> searchInputs) {
    verifyEndsWithAny(verifier, searchInputs, getDefaultMessage("Value Ends With Any Value From The Expected Values"));
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is true.
   *
   * @param verifier     CVerificationQueue instance
   * @param searchInputs the case-sensitive CharSequences to find, may be empty or contain {@code
   *                     null}
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyEndsWithAny(CVerificationQueue verifier, List<String> searchInputs, final String message, final Object... params) {
    _verify(verifier, searchInputs, (a, b) -> _toState(a).endsWithAny(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is true.
   *
   * @param verifier CVerificationQueue instance
   * @param suffix   the suffix to find, may be {@code null}
   */
  default void verifyEndsWithIgnoreCase(final CVerificationQueue verifier, String suffix) {
    verifyEndsWithIgnoreCase(verifier, suffix, getDefaultMessage("Value Ends With The Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is true.
   *
   * @param verifier CVerificationQueue instance
   * @param suffix   the suffix to find, may be {@code null}
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyEndsWithIgnoreCase(CVerificationQueue verifier, String suffix, final String message, final Object... params) {
    _verify(verifier, suffix, (a, b) -> _toState(a).endsWithIgnoreCase(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is false.
   *
   * @param verifier     CVerificationQueue instance
   * @param searchInputs the case-sensitive CharSequences to find, may be empty or contain {@code
   *                     null}
   */
  default void verifyEndsWithNone(final CVerificationQueue verifier, List<String> searchInputs) {
    verifyEndsWithNone(verifier, searchInputs, getDefaultMessage("Value Ends With None Of Value From The Expected Values"));
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is false.
   *
   * @param verifier     CVerificationQueue instance
   * @param searchInputs the case-sensitive CharSequences to find, may be empty or contain {@code
   *                     null}
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyEndsWithNone(CVerificationQueue verifier, List<String> searchInputs, final String message, final Object... params) {
    _verify(verifier, searchInputs, (a, b) -> _toState(a).endsWithNone(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)}
   * equals to any expected value, ignoring case.
   *
   * @param verifier     CVerificationQueue instance
   * @param expectedList a list of strings, may be {@code null}.
   */
  default void verifyEqualsAnyIgnoreCase(CVerificationQueue verifier, List<String> expectedList) {
    verifyEqualsAnyIgnoreCase(verifier, expectedList, getDefaultMessage("Value Ends With Any Value From The Expected Values Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)}
   * equals to any expected value, ignoring case.
   *
   * @param verifier     CVerificationQueue instance
   * @param expectedList a list of strings, may be {@code null}.
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsAnyIgnoreCase(CVerificationQueue verifier, List<String> expectedList, final String message, final Object... params) {
    _verify(verifier, expectedList, (a, b) -> _toState(a).equalsAnyIgnoreCase(b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#equalsIgnoreCase(CharSequence, CharSequence)} value equals the
   * expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   */
  default void verifyEqualsIgnoreCase(final CVerificationQueue verifier, final String expected) {
    verifyEqualsIgnoreCase(verifier, expected, getDefaultMessage("Value Equals The Expected Values Ignoring Case Sensitivity"));
  }

  /**
   * Verify if {@link CStringUtil#equalsIgnoreCase(CharSequence, CharSequence)} value equals the
   * expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsIgnoreCase(CVerificationQueue verifier, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).equalsIgnoreCase(b), message, params);
  }

  /**
   * Verify if value is equal to expected after removing all WhiteSpaces from both.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   */
  default void verifyEqualsIgnoreWhiteSpaces(CVerificationQueue verifier, final String expected) {
    verifyEqualsIgnoreWhiteSpaces(verifier, expected, getDefaultMessage("Value Equals The Expected Values Ignoring White Spaces"));
  }

  /**
   * Verify if value is equal to expected after removing all WhiteSpaces from both.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsIgnoreWhiteSpaces(CVerificationQueue verifier, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).equalsIgnoreWhiteSpaces(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)} is
   * false, ignoring case.
   *
   * @param verifier     CVerificationQueue instance
   * @param expectedList a list of strings, may be {@code null}.
   */
  default void verifyEqualsNoneIgnoreCase(CVerificationQueue verifier, List<String> expectedList) {
    verifyEqualsNoneIgnoreCase(verifier, expectedList, getDefaultMessage("Value Ends With None Of Value From The Expected Values Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)} is
   * false, ignoring case.
   *
   * @param verifier     CVerificationQueue instance
   * @param expectedList a list of strings, may be {@code null}.
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsNoneIgnoreCase(CVerificationQueue verifier, List<String> expectedList, final String message, final Object... params) {
    _verify(verifier, expectedList, (a, b) -> _toState(a).equalsNoneIgnoreCase(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is true.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsAlpha(final CVerificationQueue verifier) {
    verifyIsAlpha(verifier, getDefaultMessage("Value Contains Only Alpha Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is true.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAlpha(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isAlpha(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is true.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsAlphaSpace(final CVerificationQueue verifier) {
    verifyIsAlphaSpace(verifier, getDefaultMessage("Value Contains Only Alpha Or Space Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is true.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAlphaSpace(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isAlphaSpace(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsAlphanumeric(final CVerificationQueue verifier) {
    verifyIsAlphanumeric(verifier, getDefaultMessage("Value Contains Only Alpha-Numeric Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAlphanumeric(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isAlphanumeric(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is true.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsAlphanumericSpace(final CVerificationQueue verifier) {
    verifyIsAlphanumericSpace(verifier, getDefaultMessage("Value Contains Only Alpha-Numeric Or Space Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is true.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAlphanumericSpace(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, (a, b) -> _toState(a).isAlphanumericSpace(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is true.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsAsciiPrintable(final CVerificationQueue verifier) {
    verifyIsAsciiPrintable(verifier, getDefaultMessage("Value Contains Only Ascii Printable Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is true.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAsciiPrintable(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isAsciiPrintable(), message, params);
  }

  /**
   * Verify if String value is blank (Null or Empty)
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsBlank(final CVerificationQueue verifier) {
    verifyIsBlank(verifier, getDefaultMessage("Value Is Blank"));
  }

  /**
   * Verify if String value is blank (Null or Empty)
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlank(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, "<Blank>", (a, b) -> _toState(a).isBlank(), message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsBlankOrAlpha(final CVerificationQueue verifier) {
    verifyIsBlankOrAlpha(verifier, getDefaultMessage("Value Contains Only Alpha Characters Or It Is Blank"));
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrAlpha(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isBlankOrAlpha(), message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsBlankOrAlphanumeric(final CVerificationQueue verifier) {
    verifyIsBlankOrAlphanumeric(verifier, getDefaultMessage("Value Contains Only Alpha-Numeric Characters Or It Is Blank"));
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrAlphanumeric(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, (a, b) -> _toState(a).isBlankOrAlphanumeric(), message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsBlankOrNotAlpha(final CVerificationQueue verifier) {
    verifyIsBlankOrNotAlpha(verifier, getDefaultMessage("Value Is Blank Or Not Contains Only Alpha-Numeric Characters"));
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrNotAlpha(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isBlankOrNotAlpha(), message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsBlankOrNotAlphanumeric(final CVerificationQueue verifier) {
    verifyIsBlankOrNotAlphanumeric(verifier, getDefaultMessage("Value Is Blank Or Not Contains Only Alpha-Numeric Characters"));
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrNotAlphanumeric(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, (a, b) -> _toState(a).isBlankOrNotAlphanumeric(), message, params);
  }

  /**
   * Verify if string is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsBlankOrNotNumeric(final CVerificationQueue verifier) {
    verifyIsBlankOrNotNumeric(verifier, getDefaultMessage("Value Is Blank Or Not Contains Only Numeric Characters"));
  }

  /**
   * Verify if string is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrNotNumeric(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, (a, b) -> _toState(a).isBlankOrNotNumeric(), message, params);
  }

  /**
   * Verify if string is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsBlankOrNumeric(final CVerificationQueue verifier) {
    verifyIsBlankOrNumeric(verifier, getDefaultMessage("Value Is Blank Or Not Contains Only Numeric Characters"));
  }

  /**
   * Verify if string is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrNumeric(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isBlankOrNumeric(), message, params);
  }

  /**
   * Verify if String value is empty
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmpty(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, "<Empty>", (a, b) -> _toState(a).isEmpty(), message, params);
  }

  /**
   * Verify if String value is empty
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsEmpty(final CVerificationQueue verifier) {
    verifyIsEmpty(verifier, getDefaultMessage("Value Is Empty"));
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrAlpha(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isEmptyOrAlpha(), message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsEmptyOrAlpha(final CVerificationQueue verifier) {
    verifyIsEmptyOrAlpha(verifier, getDefaultMessage("Value Is Empty Or Contains Only Alpha Characters"));
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrAlphanumeric(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, (a, b) -> _toState(a).isEmptyOrAlphanumeric(), message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsEmptyOrAlphanumeric(final CVerificationQueue verifier) {
    verifyIsEmptyOrAlphanumeric(verifier, getDefaultMessage("Value Is Empty Or Contains Only Alpha-Numeric Characters"));
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrNotAlpha(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isEmptyOrNotAlpha(), message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsEmptyOrNotAlpha(final CVerificationQueue verifier) {
    verifyIsEmptyOrNotAlpha(verifier, getDefaultMessage("Value Is Empty Or Not Contains Only Alpha Characters"));
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrNotAlphanumeric(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, (a, b) -> _toState(a).isEmptyOrNotAlphanumeric(), message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsEmptyOrNotAlphanumeric(final CVerificationQueue verifier) {
    verifyIsEmptyOrNotAlphanumeric(verifier, getDefaultMessage("Value Is Empty Or Not Contains Only Alpha-Numeric Characters"));
  }

  /**
   * Verify if string is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrNotNumeric(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, (a, b) -> _toState(a).isEmptyOrNotNumeric(), message, params);
  }

  /**
   * Verify if string is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsEmptyOrNotNumeric(final CVerificationQueue verifier) {
    verifyIsEmptyOrNotNumeric(verifier, getDefaultMessage("Value Is Empty Or Not Contains Only Numeric Characters"));
  }

  /**
   * Verify if string is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrNumeric(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isEmptyOrNumeric(), message, params);
  }

  /**
   * Verify if string is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsEmptyOrNumeric(final CVerificationQueue verifier) {
    verifyIsEmptyOrNumeric(verifier, getDefaultMessage("Value Is Empty Or Contains Only Numeric Characters"));
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param verifier CVerificationQueue instance
   * @param pattern  regular expression pattern
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyMatches(CVerificationQueue verifier, final Pattern pattern, final String message, final Object... params) {
    _verify(verifier, pattern, (a, b) -> _toState(a).matches(b), message, params);
  }

  /**
   * Verify if String value match any of provided patterns
   *
   * @param patterns regular expression pattern
   */
  default void verifyMatchesAny(final CVerificationQueue verifier, final List<Pattern> patterns) {
    verifyMatchesAny(verifier, patterns, getDefaultMessage("Value Matches Any Of The Provided Patterns"));
  }

  /**
   * Verify if String value match any of provided patterns
   *
   * @param patterns regular expression pattern
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyMatchesAny(CVerificationQueue verifier, final List<Pattern> patterns, final String message, final Object... params) {
    _verify(verifier, patterns, (a, b) -> _toState(a).matchAny(b), message, params);
  }

  /**
   * Verify if String value NOT match any of provided patterns
   *
   * @param patterns regular expression pattern
   */
  default void verifyMatchesNone(final CVerificationQueue verifier, final List<Pattern> patterns) {
    verifyMatchesNone(verifier, patterns, getDefaultMessage("Value Matches None Of The Provided Patterns"));
  }

  /**
   * Verify if String value NOT match any of provided patterns
   *
   * @param patterns regular expression pattern
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyMatchesNone(CVerificationQueue verifier, final List<Pattern> patterns, final String message, final Object... params) {
    _verify(verifier, patterns, (a, b) -> _toState(a).matchNone(b), message, params);
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param verifier CVerificationQueue instance
   * @param pattern  regular expression pattern
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyMatches(CVerificationQueue verifier, final String pattern, final String message, final Object... params) {
    _verify(verifier, pattern, (a, b) -> _toState(a).matches(b), message, params);
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param verifier CVerificationQueue instance
   * @param pattern  regular expression pattern
   */
  default void verifyMatches(final CVerificationQueue verifier, final Pattern pattern) {
    verifyMatches(verifier, pattern, getDefaultMessage("Value Matches The Provided Pattern"));
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param verifier CVerificationQueue instance
   * @param pattern  regular expression pattern
   */
  default void verifyMatches(final CVerificationQueue verifier, final String pattern) {
    verifyMatches(verifier, Pattern.compile(pattern));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is false.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAlpha(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isNotAlpha(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is false.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsNotAlpha(final CVerificationQueue verifier) {
    verifyIsNotAlpha(verifier, getDefaultMessage("Value Not Contains Only Alpha Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is false.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAlphaSpace(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isNotAlphaSpace(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is false.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsNotAlphaSpace(final CVerificationQueue verifier) {
    verifyIsNotAlphaSpace(verifier, getDefaultMessage("Value Is Not Contains Only Alpha Characters Or Space"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAlphanumeric(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isNotAlphanumeric(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsNotAlphanumeric(final CVerificationQueue verifier) {
    verifyIsNotAlphanumeric(verifier, getDefaultMessage("Value Not Contains Only Alpha-Numeric Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is false.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAlphanumericSpace(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, (a, b) -> _toState(a).isNotAlphanumericSpace(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is false.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsNotAlphanumericSpace(final CVerificationQueue verifier) {
    verifyIsNotAlphanumericSpace(verifier, getDefaultMessage("Value Not Contains Only Alpha-Numeric Characters Or Space"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is false.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAsciiPrintable(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, (a, b) -> _toState(a).isNotAsciiPrintable(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is false.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsNotAsciiPrintable(final CVerificationQueue verifier) {
    verifyIsNotAsciiPrintable(verifier, getDefaultMessage("Value Not Contains Only Ascii Printable Characters"));
  }

  /**
   * Verify if String value is not blank (Null or Empty)
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotBlank(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, "<Not Blank>", (a, b) -> _toState(a).isNotBlank(), message, params);
  }

  /**
   * Verify if String value is not blank (Null or Empty)
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsNotBlank(final CVerificationQueue verifier) {
    verifyIsNotBlank(verifier, getDefaultMessage("Value Is Not Blank"));
  }

  /**
   * Verify String value is not empty
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotEmpty(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, "<Not Empty>", (a, b) -> _toState(a).isNotEmpty(), message, params);
  }

  /**
   * Verify String value is not empty
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsNotEmpty(final CVerificationQueue verifier) {
    verifyIsNotEmpty(verifier, getDefaultMessage("Value Is Not Empty"));
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param pattern  regular expression pattern
   */
  default void verifyNotMatches(CVerificationQueue verifier, final Pattern pattern, final String message, final Object... params) {
    _verify(verifier, pattern, (a, b) -> _toState(a).notMatches(b), message, params);
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param pattern  regular expression pattern
   */
  default void verifyNotMatches(CVerificationQueue verifier, final String pattern, final String message, final Object... params) {
    _verify(verifier, pattern, (a, b) -> _toState(a).notMatches(b), message, params);
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param verifier CVerificationQueue instance
   * @param pattern  regular expression pattern
   */
  default void verifyNotMatches(final CVerificationQueue verifier, final Pattern pattern) {
    verifyNotMatches(verifier, pattern, getDefaultMessage("Value Is Not Match The Expected Pattern"));
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param verifier CVerificationQueue instance
   * @param pattern  regular expression pattern
   */
  default void verifyNotMatches(final CVerificationQueue verifier, final String pattern) {
    verifyNotMatches(verifier, pattern, getDefaultMessage("Value Is Not Match The Expected Pattern"));
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is false.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotNumeric(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isNotNumeric(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is false.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsNotNumeric(final CVerificationQueue verifier) {
    verifyIsNotNumeric(verifier, getDefaultMessage("Value Not Contains Only Numeric Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is false.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotNumericSpace(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isNotNumericSpace(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is false.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsNotNumericSpace(final CVerificationQueue verifier) {
    verifyIsNotNumericSpace(verifier, getDefaultMessage("Value Not Contains Only Numeric Or Space Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNumeric(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isNumeric(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsNumeric(final CVerificationQueue verifier) {
    verifyIsNumeric(verifier, getDefaultMessage("Value Contains Only Numeric Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is true.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNumericSpace(CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, true, (a, b) -> _toState(a).isNumericSpace(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is true.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsNumericSpace(final CVerificationQueue verifier) {
    verifyIsNumericSpace(verifier, getDefaultMessage("Value Contains Only Numeric Or Space Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is equals to expected
   * value.
   *
   * @param verifier CVerificationQueue instance
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyLeftPadEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).leftPadEquals(size, padStr, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is equals to expected
   * value.
   *
   * @param verifier CVerificationQueue instance
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   */
  default void verifyLeftPadEquals(CVerificationQueue verifier, int size, String padStr, final String expected) {
    verifyLeftPadEquals(verifier, size, padStr, expected, getDefaultMessage("Expected Value Equals To The Actual Value Left Pad With '%s' And The Length Of '%d'", padStr, size));
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param verifier CVerificationQueue instance
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyLeftPadNotEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).leftPadNotEquals(size, padStr, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param verifier CVerificationQueue instance
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   */
  default void verifyLeftPadNotEquals(CVerificationQueue verifier, int size, String padStr, final String expected) {
    verifyLeftPadNotEquals(verifier, size, padStr, expected, getDefaultMessage("Expected Value Not Equals To The Actual Value Left Pad With '%s' And The Length Of '%d'", padStr, size));
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param len      the length of the required String
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyLeftValueEquals(CVerificationQueue verifier, int len, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).leftValueEquals(len, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param len      the length of the required String
   */
  default void verifyLeftValueEquals(CVerificationQueue verifier, int len, final String expected) {
    verifyLeftValueEquals(verifier, len, expected, getDefaultMessage("Expected Value Equals To The Left '%d' Character Of Actual Value", len));
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} NOT equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param len      the length of the required String
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyLeftValueNotEquals(CVerificationQueue verifier, int len, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).leftValueNotEquals(len, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} NOT equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param len      the length of the required String
   */
  default void verifyLeftValueNotEquals(CVerificationQueue verifier, int len, final String expected) {
    verifyLeftValueNotEquals(verifier, len, expected, getDefaultMessage("Expected Value Not Equals To The Left '%d' Character Of Actual Value", len));
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyLengthEquals(CVerificationQueue verifier, int expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).lengthEquals(expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   */
  default void verifyLengthEquals(final CVerificationQueue verifier, int expected) {
    verifyLengthEquals(verifier, expected, getDefaultMessage("Expected Value Length Equals To The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is NOT equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyLengthNotEquals(CVerificationQueue verifier, int expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).lengthNotEquals(expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is NOT equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   */
  default void verifyLengthNotEquals(final CVerificationQueue verifier, int expected) {
    verifyLengthNotEquals(verifier, expected, getDefaultMessage("Expected Value Length Not Equals To The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param pos      the position to start from, negative treated as zero
   * @param len      the length of the required String
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyMidValueEquals(CVerificationQueue verifier, int pos, int len, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).midValueEquals(pos, len, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param pos      the position to start from, negative treated as zero
   * @param len      the length of the required String
   */
  default void verifyMidValueEquals(CVerificationQueue verifier, int pos, int len, final String expected) {
    verifyMidValueEquals(verifier, pos, len, expected, getDefaultMessage("Expected Value Equals To The Characters Of Actual Value From Position '%d' For '%d' Length", pos, len));
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} NOT equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param pos      the position to start from, negative treated as zero
   * @param len      the length of the required String
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyMidValueNotEquals(CVerificationQueue verifier, int pos, int len, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).midValueNotEquals(pos, len, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} NOT equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param pos      the position to start from, negative treated as zero
   * @param len      the length of the required String
   */
  default void verifyMidValueNotEquals(CVerificationQueue verifier, int pos, int len, final String expected) {
    verifyMidValueNotEquals(verifier, pos, len, expected, getDefaultMessage("Expected Value Not Equals To The Characters Of Actual Value From Position '%d' For '%d' Length", pos, len));
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is false.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(CVerificationQueue verifier, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).notContains(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is false.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   */
  default void verifyNotContains(final CVerificationQueue verifier, final String expected) {
    verifyNotContains(verifier, expected, getDefaultMessage("Actual Value Not Contains The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContainsIgnoreCase(CVerificationQueue verifier, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).notContainsIgnoreCase(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   */
  default void verifyNotContainsIgnoreCase(CVerificationQueue verifier, final String expected) {
    verifyNotContainsIgnoreCase(verifier, expected, getDefaultMessage("Actual Value Not Contains The Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is false
   *
   * @param verifier CVerificationQueue instance
   * @param suffix   the suffix to find, may be {@code null}
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEndsWith(CVerificationQueue verifier, String suffix, final String message, final Object... params) {
    _verify(verifier, suffix, (a, b) -> _toState(a).notEndsWith(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is false
   *
   * @param verifier CVerificationQueue instance
   * @param suffix   the suffix to find, may be {@code null}
   */
  default void verifyNotEndsWith(final CVerificationQueue verifier, String suffix) {
    verifyNotEndsWith(verifier, suffix, getDefaultMessage("Actual Value Not Ends With The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param verifier CVerificationQueue instance
   * @param suffix   the suffix to find, may be {@code null}
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEndsWithIgnoreCase(CVerificationQueue verifier, String suffix, final String message, final Object... params) {
    _verify(verifier, suffix, (a, b) -> _toState(a).notEndsWithIgnoreCase(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param verifier CVerificationQueue instance
   * @param suffix   the suffix to find, may be {@code null}
   */
  default void verifyNotEndsWithIgnoreCase(final CVerificationQueue verifier, String suffix) {
    verifyNotEndsWithIgnoreCase(verifier, suffix, getDefaultMessage("Actual Value Not Ends With The Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if {@link CStringUtil#equalsIgnoreCase(CharSequence, CharSequence)} value NOT equals the
   * expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsIgnoreCase(CVerificationQueue verifier, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).notEqualsIgnoreCase(b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#equalsIgnoreCase(CharSequence, CharSequence)} value NOT equals the
   * expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   */
  default void verifyNotEqualsIgnoreCase(CVerificationQueue verifier, final String expected) {
    verifyNotEqualsIgnoreCase(verifier, expected, getDefaultMessage("Actual Value Not Equals To The Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if value is not equal to expected after removing all WhiteSpaces from both.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsIgnoreWhiteSpaces(CVerificationQueue verifier, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).notEqualsIgnoreWhiteSpaces(b), message, params);
  }

  /**
   * Verify if value is not equal to expected after removing all WhiteSpaces from both.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   */
  default void verifyNotEqualsIgnoreWhiteSpaces(CVerificationQueue verifier, final String expected) {
    verifyNotEqualsIgnoreWhiteSpaces(verifier, expected, getDefaultMessage("Actual Value Not Equals To The Expected Value Ignoring White Spaces"));
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is false
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotStartsWith(CVerificationQueue verifier, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).notStartsWith(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is false
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   */
  default void verifyNotStartsWith(final CVerificationQueue verifier, final String expected) {
    verifyNotStartsWith(verifier, expected, getDefaultMessage("Actual Value Not Starts With The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * false
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotStartsWithIgnoreCase(CVerificationQueue verifier, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).notStartsWithIgnoreCase(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * false
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   */
  default void verifyNotStartsWithIgnoreCase(CVerificationQueue verifier, final String expected) {
    verifyNotStartsWithIgnoreCase(verifier, expected, getDefaultMessage("Actual Value Not Starts With The Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is equals to
   * expected value.
   *
   * @param verifier  CVerificationQueue instance
   * @param expected  the expected result.
   * @param subString the substring to count, may be null
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifyNumberOfMatchesEquals(CVerificationQueue verifier, String subString, int expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).numberOfMatchesEquals(subString, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is equals to
   * expected value.
   *
   * @param verifier  CVerificationQueue instance
   * @param expected  the expected result.
   * @param subString the substring to count, may be null
   */
  default void verifyNumberOfMatchesEquals(CVerificationQueue verifier, String subString, int expected) {
    verifyNumberOfMatchesEquals(verifier, subString, expected, getDefaultMessage("Actual Value Contains Exact Number Of Substring"));
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is NOT equals
   * to expected value.
   *
   * @param verifier  CVerificationQueue instance
   * @param expected  the expected result.
   * @param subString the substring to count, may be null
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifyNumberOfMatchesNotEquals(CVerificationQueue verifier, String subString, int expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).numberOfMatchesNotEquals(subString, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is NOT equals
   * to expected value.
   *
   * @param verifier  CVerificationQueue instance
   * @param expected  the expected result.
   * @param subString the substring to count, may be null
   */
  default void verifyNumberOfMatchesNotEquals(CVerificationQueue verifier, String subString, int expected) {
    verifyNumberOfMatchesNotEquals(verifier, subString, expected, getDefaultMessage("Actual Value Not Contains Exact Number Of Substring"));
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEndEquals(CVerificationQueue verifier, String remove, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).removeEndEquals(remove, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   */
  default void verifyRemoveEndEquals(CVerificationQueue verifier, String remove, final String expected) {
    verifyRemoveEndEquals(verifier, remove, expected, getDefaultMessage("Actual Value, After Removing '%s' From End, Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEndIgnoreCaseEquals(CVerificationQueue verifier, String remove, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).removeEndIgnoreCaseEquals(remove, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   */
  default void verifyRemoveEndIgnoreCaseEquals(CVerificationQueue verifier, String remove, final String expected) {
    verifyRemoveEndIgnoreCaseEquals(verifier, remove, expected, getDefaultMessage("Actual Value, After Removing '%s' From End Ignoring Case Sensitivity, Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEndIgnoreCaseNotEquals(CVerificationQueue verifier, String remove, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).removeEndIgnoreCaseNotEquals(remove, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   */
  default void verifyRemoveEndIgnoreCaseNotEquals(CVerificationQueue verifier, String remove, final String expected) {
    verifyRemoveEndIgnoreCaseNotEquals(verifier, remove, expected, getDefaultMessage("Actual Value, After Removing '%s' From End Ignoring Case Sensitivity, Not Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is NOT equals to expected
   * value.
   *
   * @param verifier CVerificationQueue instance
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEndNotEquals(CVerificationQueue verifier, String remove, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).removeEndNotEquals(remove, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is NOT equals to expected
   * value.
   *
   * @param verifier CVerificationQueue instance
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   */
  default void verifyRemoveEndNotEquals(CVerificationQueue verifier, String remove, final String expected) {
    verifyRemoveEndNotEquals(verifier, remove, expected, getDefaultMessage("Actual Value, After Removing '%s', Not Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEquals(CVerificationQueue verifier, String remove, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).removeEquals(remove, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   */
  default void verifyRemoveEquals(CVerificationQueue verifier, String remove, final String expected) {
    verifyRemoveEquals(verifier, remove, expected, getDefaultMessage("Actual Value, After Removing '%s', Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is equals to expected
   * value.
   *
   * @param verifier CVerificationQueue instance
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveIgnoreCaseEquals(CVerificationQueue verifier, String remove, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).removeIgnoreCaseEquals(remove, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is equals to expected
   * value.
   *
   * @param verifier CVerificationQueue instance
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   */
  default void verifyRemoveIgnoreCaseEquals(CVerificationQueue verifier, String remove, final String expected) {
    verifyRemoveIgnoreCaseEquals(verifier, remove, expected, getDefaultMessage("Actual Value, After Removing '%s' Ignoring Case Sensitivity, Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveIgnoreCaseNotEquals(CVerificationQueue verifier, String remove, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).removeIgnoreCaseNotEquals(remove, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   */
  default void verifyRemoveIgnoreCaseNotEquals(CVerificationQueue verifier, String remove, final String expected) {
    verifyRemoveIgnoreCaseNotEquals(verifier, remove, expected, getDefaultMessage("Actual Value, After Removing '%s' Ignoring Case Sensitivity, Not Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is NOT equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveNotEquals(CVerificationQueue verifier, String remove, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).removeNotEquals(remove, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is NOT equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   */
  default void verifyRemoveNotEquals(CVerificationQueue verifier, String remove, final String expected) {
    verifyRemoveNotEquals(verifier, remove, expected, getDefaultMessage("Actual Value, After Removing '%s', Not Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is equals to expected
   * value.
   *
   * @param verifier CVerificationQueue instance
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveStartEquals(CVerificationQueue verifier, String remove, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).removeStartEquals(remove, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is equals to expected
   * value.
   *
   * @param verifier CVerificationQueue instance
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   */
  default void verifyRemoveStartEquals(CVerificationQueue verifier, String remove, final String expected) {
    verifyRemoveStartEquals(verifier, remove, expected, getDefaultMessage("Actual Value, After Removing '%s' From Start, Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveStartIgnoreCaseEquals(CVerificationQueue verifier, String remove, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).removeStartIgnoreCaseEquals(remove, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   */
  default void verifyRemoveStartIgnoreCaseEquals(CVerificationQueue verifier, String remove, final String expected) {
    verifyRemoveStartIgnoreCaseEquals(verifier, remove, expected, getDefaultMessage("Actual Value, After Removing '%s' From Start Ignoring Case Sensitivity, Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveStartIgnoreCaseNotEquals(CVerificationQueue verifier, String remove, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).removeStartIgnoreCaseNotEquals(remove, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   */
  default void verifyRemoveStartIgnoreCaseNotEquals(CVerificationQueue verifier, String remove, final String expected) {
    verifyRemoveStartIgnoreCaseNotEquals(verifier, remove, expected, getDefaultMessage("Actual Value, After Removing '%s' From Start Ignoring Case Sensitivity, Not Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is NOT equals to expected
   * value.
   *
   * @param verifier CVerificationQueue instance
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveStartNotEquals(CVerificationQueue verifier, String remove, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).removeStartNotEquals(remove, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is NOT equals to expected
   * value.
   *
   * @param verifier CVerificationQueue instance
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   */
  default void verifyRemoveStartNotEquals(CVerificationQueue verifier, String remove, final String expected) {
    verifyRemoveStartNotEquals(verifier, remove, expected, getDefaultMessage("Actual Value, After Removing '%s' From Start, Not Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is equals to expected
   * value.
   *
   * @param verifier     CVerificationQueue instance
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).replaceEquals(searchString, replacement, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is equals to expected
   * value.
   *
   * @param verifier     CVerificationQueue instance
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   */
  default void verifyReplaceEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected) {
    verifyReplaceEquals(verifier, searchString, replacement, expected, getDefaultMessage("Actual Value, After Replacing '%s' With '%s', Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is equals to
   * expected value.
   *
   * @param verifier     CVerificationQueue instance
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceIgnoreCaseEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).replaceIgnoreCaseEquals(searchString, replacement, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is equals to
   * expected value.
   *
   * @param verifier     CVerificationQueue instance
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   */
  default void verifyReplaceIgnoreCaseEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected) {
    verifyReplaceIgnoreCaseEquals(verifier, searchString, replacement, expected, getDefaultMessage("Actual Value, After Replacing '%s' With '%s' Ignoring Case Sensitivity, Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is NOT equals
   * to expected value.
   *
   * @param verifier     CVerificationQueue instance
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceIgnoreCaseNotEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).replaceIgnoreCaseNotEquals(searchString, replacement, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is NOT equals
   * to expected value.
   *
   * @param verifier     CVerificationQueue instance
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   */
  default void verifyReplaceIgnoreCaseNotEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected) {
    verifyReplaceIgnoreCaseNotEquals(verifier, searchString, replacement, expected, getDefaultMessage("Actual Value, After Replacing '%s' With '%s' Ignoring Case Sensitivity, Not Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier     CVerificationQueue instance
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceNotEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).replaceNotEquals(searchString, replacement, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier     CVerificationQueue instance
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   */
  default void verifyReplaceNotEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected) {
    verifyReplaceNotEquals(verifier, searchString, replacement, expected, getDefaultMessage("Actual Value, After Replacing '%s' With '%s', Not Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is equals to
   * expected value.
   *
   * @param verifier     CVerificationQueue instance
   * @param searchString the String to search for, may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceOnceEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).replaceOnceEquals(searchString, replacement, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is equals to
   * expected value.
   *
   * @param verifier     CVerificationQueue instance
   * @param searchString the String to search for, may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   */
  default void verifyReplaceOnceEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected) {
    verifyReplaceOnceEquals(verifier, searchString, replacement, expected, getDefaultMessage("Actual Value, After Replacing Once '%s' With '%s', Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is equals
   * to expected value.
   *
   * @param verifier     CVerificationQueue instance
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceOnceIgnoreCaseEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).replaceOnceIgnoreCaseEquals(searchString, replacement, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is equals
   * to expected value.
   *
   * @param verifier     CVerificationQueue instance
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   */
  default void verifyReplaceOnceIgnoreCaseEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected) {
    verifyReplaceOnceIgnoreCaseEquals(verifier, searchString, replacement, expected, getDefaultMessage("Actual Value, After Replacing Once '%s' With '%s' Ignoring Case Sensitivity, Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is NOT
   * equals to expected value.
   *
   * @param verifier     CVerificationQueue instance
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceOnceIgnoreCaseNotEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).replaceOnceIgnoreCaseNotEquals(searchString, replacement, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is NOT
   * equals to expected value.
   *
   * @param verifier     CVerificationQueue instance
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   */
  default void verifyReplaceOnceIgnoreCaseNotEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected) {
    verifyReplaceOnceIgnoreCaseNotEquals(verifier, searchString, replacement, expected, getDefaultMessage("Actual Value, After Replacing Once '%s' With '%s' Ignoring Case Sensitivity, Not Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier     CVerificationQueue instance
   * @param searchString the String to search for, may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceOnceNotEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).replaceOnceNotEquals(searchString, replacement, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param verifier     CVerificationQueue instance
   * @param searchString the String to search for, may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   */
  default void verifyReplaceOnceNotEquals(CVerificationQueue verifier, String searchString, String replacement, final String expected) {
    verifyReplaceOnceNotEquals(verifier, searchString, replacement, expected, getDefaultMessage("Actual Value, After Replacing Once '%s' With '%s', Not Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyReverseEquals(CVerificationQueue verifier, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).reverseEquals(expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   */
  default void verifyReverseEquals(final CVerificationQueue verifier, final String expected) {
    verifyReverseEquals(verifier, expected, getDefaultMessage("Actual Value, After Reversing Order Of Characters, Equals To The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is NOT equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyReverseNotEquals(CVerificationQueue verifier, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).reverseNotEquals(expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is NOT equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   */
  default void verifyReverseNotEquals(final CVerificationQueue verifier, final String expected) {
    verifyReverseNotEquals(verifier, expected, getDefaultMessage("Actual Value, After Reversing Order Of Characters, Not Equals To The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is equals to expected
   * value.
   *
   * @param verifier CVerificationQueue instance
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRightPadEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).rightPadEquals(size, padStr, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is equals to expected
   * value.
   *
   * @param verifier CVerificationQueue instance
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   */
  default void verifyRightPadEquals(CVerificationQueue verifier, int size, String padStr, final String expected) {
    verifyRightPadEquals(verifier, size, padStr, expected, getDefaultMessage("Expected Value Equals To The Actual Value Right Pad With '%s' And The Length Of '%d'", padStr, size));
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param verifier CVerificationQueue instance
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRightPadNotEquals(CVerificationQueue verifier, int size, String padStr, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).rightPadNotEquals(size, padStr, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param verifier CVerificationQueue instance
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   */
  default void verifyRightPadNotEquals(CVerificationQueue verifier, int size, String padStr, final String expected) {
    verifyRightPadNotEquals(verifier, size, padStr, expected, getDefaultMessage("Expected Value Not Equals To The Actual Value Right Pad With '%s' And The Length Of '%d'", padStr, size));
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param len      the length of the required String
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRightValueEquals(CVerificationQueue verifier, int len, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).rightValueEquals(len, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param len      the length of the required String
   */
  default void verifyRightValueEquals(CVerificationQueue verifier, int len, final String expected) {
    verifyRightValueEquals(verifier, len, expected, getDefaultMessage("Expected Value Equals To The Right '%d' Character Of Actual Value", len));
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} NOT equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param len      the length of the required String
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRightValueNotEquals(CVerificationQueue verifier, int len, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).rightValueNotEquals(len, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} NOT equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param len      the length of the required String
   */
  default void verifyRightValueNotEquals(CVerificationQueue verifier, int len, final String expected) {
    verifyRightValueNotEquals(verifier, len, expected, getDefaultMessage("Expected Value Not Equals To The Right '%d' Character Of Actual Value", len));
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is true
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyStartsWith(CVerificationQueue verifier, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).startsWith(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is true
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   */
  default void verifyStartsWith(final CVerificationQueue verifier, final String expected) {
    verifyStartsWith(verifier, expected, getDefaultMessage("Actual Value Starts With Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is true
   *
   * @param verifier     CVerificationQueue instance
   * @param searchInputs the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                     null}
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyStartsWithAny(CVerificationQueue verifier, List<String> searchInputs, final String message, final Object... params) {
    _verify(verifier, searchInputs, (a, b) -> _toState(a).startsWithAny(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is true
   *
   * @param verifier     CVerificationQueue instance
   * @param searchInputs the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                     null}
   */
  default void verifyStartsWithAny(CVerificationQueue verifier, List<String> searchInputs) {
    verifyStartsWithAny(verifier, searchInputs, getDefaultMessage("Actual Value Starts With Any Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * true
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyStartsWithIgnoreCase(CVerificationQueue verifier, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).startsWithIgnoreCase(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * true
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   */
  default void verifyStartsWithIgnoreCase(CVerificationQueue verifier, final String expected) {
    verifyStartsWithIgnoreCase(verifier, expected, getDefaultMessage("Actual Value Starts With Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is false
   *
   * @param verifier     CVerificationQueue instance
   * @param searchInputs the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                     null}
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyStartsWithNone(CVerificationQueue verifier, List<String> searchInputs, final String message, final Object... params) {
    _verify(verifier, searchInputs, (a, b) -> _toState(a).startsWithNone(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is false
   *
   * @param verifier     CVerificationQueue instance
   * @param searchInputs the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                     null}
   */
  default void verifyStartsWithNone(CVerificationQueue verifier, List<String> searchInputs) {
    verifyStartsWithNone(verifier, searchInputs, getDefaultMessage("Actual Value Starts With None Of Expected Value"));
  }

  /**
   * Verify if {@link CStringUtil#stripEnd(String, String)} value equals the expected value.
   *
   * @param verifier   CVerificationQueue instance
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   * @param message    information about the purpose of this verification
   * @param params     parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedEndValue(CVerificationQueue verifier, String stripChars, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).stripedEndValue(stripChars, b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#stripEnd(String, String)} value equals the expected value.
   *
   * @param verifier   CVerificationQueue instance
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   */
  default void verifyStripedEndValue(CVerificationQueue verifier, String stripChars, final String expected) {
    verifyStripedEndValue(verifier, stripChars, expected, getDefaultMessage("Actual Value Striped End '%s' Characters, Equals To The Expected Value", stripChars));
  }

  /**
   * Verify if {@link CStringUtil#stripEnd(String, String)} value NOT equals the expected value.
   *
   * @param verifier   CVerificationQueue instance
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   * @param message    information about the purpose of this verification
   * @param params     parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedEndValueNot(CVerificationQueue verifier, String stripChars, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).stripedEndValueNot(stripChars, b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#stripEnd(String, String)} value NOT equals the expected value.
   *
   * @param verifier   CVerificationQueue instance
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   */
  default void verifyStripedEndValueNot(CVerificationQueue verifier, String stripChars, final String expected) {
    verifyStripedEndValueNot(verifier, stripChars, expected, getDefaultMessage("Actual Value Striped End '%s' Characters, Not Equals To The Expected Value", stripChars));
  }

  /**
   * Verify if {@link CStringUtil#stripStart(String, String)} value equals the expected value.
   *
   * @param verifier   CVerificationQueue instance
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   * @param message    information about the purpose of this verification
   * @param params     parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedStartValue(CVerificationQueue verifier, String stripChars, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).stripedStartValue(stripChars, b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#stripStart(String, String)} value equals the expected value.
   *
   * @param verifier   CVerificationQueue instance
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   */
  default void verifyStripedStartValue(CVerificationQueue verifier, String stripChars, final String expected) {
    verifyStripedStartValue(verifier, stripChars, expected, getDefaultMessage("Actual Value Striped Start '%s' Characters, Equals To The Expected Value", stripChars));
  }

  /**
   * Verify if {@link CStringUtil#stripStart(String, String)} value NOT equals the expected value.
   *
   * @param verifier   CVerificationQueue instance
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   * @param message    information about the purpose of this verification
   * @param params     parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedStartValueNot(CVerificationQueue verifier, String stripChars, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).stripedStartValueNot(stripChars, b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#stripStart(String, String)} value NOT equals the expected value.
   *
   * @param verifier   CVerificationQueue instance
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   */
  default void verifyStripedStartValueNot(CVerificationQueue verifier, String stripChars, final String expected) {
    verifyStripedStartValueNot(verifier, stripChars, expected, getDefaultMessage("Actual Value Striped End '%s' Characters, Not Equals To The Expected Value", stripChars));
  }

  /**
   * Verify if {@link CStringUtil#strip(String)} value equals the expected value.
   *
   * @param verifier   CVerificationQueue instance
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   * @param message    information about the purpose of this verification
   * @param params     parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedValue(CVerificationQueue verifier, String stripChars, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).stripedValue(stripChars, b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#strip(String)} value equals the expected value.
   *
   * @param verifier   CVerificationQueue instance
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   */
  default void verifyStripedValue(CVerificationQueue verifier, String stripChars, final String expected) {
    verifyStripedValue(verifier, stripChars, expected, getDefaultMessage("Actual Value Striped '%s' Characters, Equals To The Expected Value", stripChars));
  }

  /**
   * Verify if {@link CStringUtil#strip(String)} value NOT equals the expected value.
   *
   * @param verifier   CVerificationQueue instance
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   * @param message    information about the purpose of this verification
   * @param params     parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedValueNot(CVerificationQueue verifier, String stripChars, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).stripedValueNot(stripChars, b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#strip(String)} value NOT equals the expected value.
   *
   * @param verifier   CVerificationQueue instance
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   */
  default void verifyStripedValueNot(CVerificationQueue verifier, String stripChars, final String expected) {
    verifyStripedValueNot(verifier, stripChars, expected, getDefaultMessage("Actual Value Striped '%s' Characters, Not Equals To The Expected Value", stripChars));
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} equals to expected
   * value.
   *
   * @param verifier  CVerificationQueue instance
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringAfterEquals(CVerificationQueue verifier, String separator, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringAfterEquals(separator, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} equals to expected
   * value.
   *
   * @param verifier  CVerificationQueue instance
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   */
  default void verifySubstringAfterEquals(CVerificationQueue verifier, String separator, final String expected) {
    verifySubstringAfterEquals(verifier, separator, expected, getDefaultMessage("Actual Value Substring After '%s', Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} equals to expected
   * value.
   *
   * @param verifier  CVerificationQueue instance
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringAfterLastEquals(CVerificationQueue verifier, String separator, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringAfterLastEquals(separator, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} equals to expected
   * value.
   *
   * @param verifier  CVerificationQueue instance
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   */
  default void verifySubstringAfterLastEquals(CVerificationQueue verifier, String separator, final String expected) {
    verifySubstringAfterLastEquals(verifier, separator, expected, getDefaultMessage("Actual Value Substring After Last '%s', Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} NOT equals to
   * expected value.
   *
   * @param verifier  CVerificationQueue instance
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringAfterLastNotEquals(CVerificationQueue verifier, String separator, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringAfterLastNotEquals(separator, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} NOT equals to
   * expected value.
   *
   * @param verifier  CVerificationQueue instance
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   */
  default void verifySubstringAfterLastNotEquals(CVerificationQueue verifier, String separator, final String expected) {
    verifySubstringAfterLastNotEquals(verifier, separator, expected, getDefaultMessage("Actual Value Substring After Last '%s', Not Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} NOT equals to expected
   * value.
   *
   * @param verifier  CVerificationQueue instance
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringAfterNotEquals(CVerificationQueue verifier, String separator, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringAfterNotEquals(separator, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} NOT equals to expected
   * value.
   *
   * @param verifier  CVerificationQueue instance
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   */
  default void verifySubstringAfterNotEquals(CVerificationQueue verifier, String separator, final String expected) {
    verifySubstringAfterNotEquals(verifier, separator, expected, getDefaultMessage("Actual Value Substring After '%s', Not Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} equals to expected
   * value.
   *
   * @param verifier  CVerificationQueue instance
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBeforeEquals(CVerificationQueue verifier, String separator, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringBeforeEquals(separator, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} equals to expected
   * value.
   *
   * @param verifier  CVerificationQueue instance
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   */
  default void verifySubstringBeforeEquals(CVerificationQueue verifier, String separator, final String expected) {
    verifySubstringBeforeEquals(verifier, separator, expected, getDefaultMessage("Actual Value Substring Before '%s', Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} equals to expected
   * value.
   *
   * @param verifier  CVerificationQueue instance
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBeforeLastEquals(CVerificationQueue verifier, String separator, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringBeforeLastEquals(separator, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} equals to expected
   * value.
   *
   * @param verifier  CVerificationQueue instance
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   */
  default void verifySubstringBeforeLastEquals(CVerificationQueue verifier, String separator, final String expected) {
    verifySubstringBeforeLastEquals(verifier, separator, expected, getDefaultMessage("Actual Value Substring Before Last '%s', Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} NOT equals to
   * expected value.
   *
   * @param verifier  CVerificationQueue instance
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBeforeLastNotEquals(CVerificationQueue verifier, String separator, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringBeforeLastNotEquals(separator, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} NOT equals to
   * expected value.
   *
   * @param verifier  CVerificationQueue instance
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   */
  default void verifySubstringBeforeLastNotEquals(CVerificationQueue verifier, String separator, final String expected) {
    verifySubstringBeforeLastNotEquals(verifier, separator, expected, getDefaultMessage("Actual Value Substring Before Last '%s', Not Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} NOT equals to expected
   * value.
   *
   * @param verifier  CVerificationQueue instance
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBeforeNotEquals(CVerificationQueue verifier, String separator, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringBeforeNotEquals(separator, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} NOT equals to expected
   * value.
   *
   * @param verifier  CVerificationQueue instance
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   */
  default void verifySubstringBeforeNotEquals(CVerificationQueue verifier, String separator, final String expected) {
    verifySubstringBeforeNotEquals(verifier, separator, expected, getDefaultMessage("Actual Value Substring Before '%s', Not Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String)} equals to expected
   * value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBetweenEquals(CVerificationQueue verifier, String open, String close, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringBetweenEquals(open, close, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String)} equals to expected
   * value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   */
  default void verifySubstringBetweenEquals(CVerificationQueue verifier, String open, String close, final String expected) {
    verifySubstringBetweenEquals(verifier, open, close, expected, getDefaultMessage("Actual Value Substring Between '%s' and '%s', Equals To The Expected Value", open, close));
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String)} NOT equals to expected
   * value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBetweenNotEquals(CVerificationQueue verifier, String open, String close, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringBetweenNotEquals(open, close, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String)} NOT equals to expected
   * value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   */
  default void verifySubstringBetweenNotEquals(CVerificationQueue verifier, String open, String close, final String expected) {
    verifySubstringBetweenNotEquals(verifier, open, close, expected, getDefaultMessage("Actual Value Substring Between '%s' and '%s', Not Equals To The Expected Value", open, close));
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringEquals(CVerificationQueue verifier, int start, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringEquals(start, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   * @param end      the position to end at (exclusive), negative means count back from the end of the
   *                 String
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringEquals(CVerificationQueue verifier, int start, int end, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringEquals(start, end, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   */
  default void verifySubstringEquals(CVerificationQueue verifier, int start, final String expected) {
    verifySubstringEquals(verifier, start, expected, getDefaultMessage("Actual Value Substring After Position '%s', Equals To The Expected Value", start));
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   * @param end      the position to end at (exclusive), negative means count back from the end of the
   *                 String
   */
  default void verifySubstringEquals(CVerificationQueue verifier, int start, int end, final String expected) {
    verifySubstringEquals(verifier, start, end, expected, getDefaultMessage("Actual Value Substring From Position '%s' To '%s', Equals To The Expected Value", start, end));
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} NOT equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringNotEquals(CVerificationQueue verifier, int start, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringNotEquals(start, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} NOT equals to expected
   * value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   * @param end      the position to end at (exclusive), negative means count back from the end of the
   *                 String
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringNotEquals(CVerificationQueue verifier, int start, int end, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringNotEquals(start, end, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} NOT equals to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   */
  default void verifySubstringNotEquals(CVerificationQueue verifier, int start, final String expected) {
    verifySubstringNotEquals(verifier, start, expected, getDefaultMessage("Actual Value Substring After Position '%s', Not Equals To The Expected Value", start));
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} NOT equals to expected
   * value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   * @param end      the position to end at (exclusive), negative means count back from the end of the
   *                 String
   */
  default void verifySubstringNotEquals(CVerificationQueue verifier, int start, int end, final String expected) {
    verifySubstringNotEquals(verifier, start, end, expected, getDefaultMessage("Actual Value Substring From Position '%s' To '%s', Not Equals To The Expected Value", start, end));
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} contains to
   * expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringsBetweenContains(CVerificationQueue verifier, String open, String close, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringsBetweenContains(open, close, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} Contains
   * expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   */
  default void verifySubstringsBetweenContains(CVerificationQueue verifier, String open, String close, final String expected) {
    verifySubstringsBetweenContains(verifier, open, close, expected, getDefaultMessage("Actual Value Substring Between '%s' To '%s' Characters, Contains The Expected Value", open, close));
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} equals to
   * expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringsBetweenEquals(CVerificationQueue verifier, String open, String close, List<String> expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringsBetweenEquals(open, close, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} equals to
   * expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   */
  default void verifySubstringsBetweenEquals(CVerificationQueue verifier, String open, String close, List<String> expected) {
    verifySubstringsBetweenEquals(verifier, open, close, expected, getDefaultMessage("Actual Value Substring Between '%s' To '%s' Characters, Equals To The Expected Value", open, close));
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT contains
   * to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringsBetweenNotContains(CVerificationQueue verifier, String open, String close, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringsBetweenNotContains(open, close, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT Contains
   * expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   */
  default void verifySubstringsBetweenNotContains(CVerificationQueue verifier, String open, String close, final String expected) {
    verifySubstringsBetweenNotContains(verifier, open, close, expected, getDefaultMessage("Actual Value Substring Between '%s' To '%s' Characters, Not Contains The Expected Value", open, close));
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT equals to
   * expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringsBetweenNotEquals(CVerificationQueue verifier, String open, String close, List<String> expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).substringsBetweenNotEquals(open, close, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT equals to
   * expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   */
  default void verifySubstringsBetweenNotEquals(CVerificationQueue verifier, String open, String close, List<String> expected) {
    verifySubstringsBetweenNotEquals(verifier, open, close, expected, getDefaultMessage("Actual Value Substring Between '%s' To '%s' Characters, Not Equals The Expected Value", open, close));
  }

  /**
   * Verify if {@link CStringUtil#trim(String)} value equals the expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyTrimmedValueEquals(CVerificationQueue verifier, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).trimmedValueEquals(b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#trim(String)} value equals the expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   */
  default void verifyTrimmedValueEquals(CVerificationQueue verifier, final String expected) {
    verifyTrimmedValueEquals(verifier, expected, getDefaultMessage("Actual Trimmed Value, Equals The Expected Value"));
  }

  /**
   * Verify if {@link CStringUtil#trim(String)} value NOT equals the expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyTrimmedValueNotEquals(CVerificationQueue verifier, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).trimmedValueNotEquals(b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#trim(String)} value NOT equals the expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected the expected result.
   */
  default void verifyTrimmedValueNotEquals(CVerificationQueue verifier, final String expected) {
    verifyTrimmedValueNotEquals(verifier, expected, getDefaultMessage("Actual Trimmed Value, Not Equals The Expected Value"));
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int)} value equals the expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyTruncatedValueEquals(CVerificationQueue verifier, int maxWidth, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).truncatedValueEquals(maxWidth, b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int, int)} value equals the expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param offset   left edge of string to start truncate from
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyTruncatedValueEquals(CVerificationQueue verifier, int offset, int maxWidth, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).truncatedValueEquals(offset, maxWidth, b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int)} value equals the expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   */
  default void verifyTruncatedValueEquals(CVerificationQueue verifier, int maxWidth, final String expected) {
    verifyTruncatedValueEquals(verifier, maxWidth, expected, getDefaultMessage("Actual Truncated Value With Maximum Width Of %s, Equals The Expected Value", maxWidth));
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int, int)} value equals the expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param offset   left edge of string to start truncate from
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   */
  default void verifyTruncatedValueEquals(CVerificationQueue verifier, int offset, int maxWidth, final String expected) {
    verifyTruncatedValueEquals(verifier, offset, maxWidth, expected, getDefaultMessage("Actual Truncated Value With Maximum Width Of %s With Offset %s, Equals The Expected Value", maxWidth, offset));
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int)} value equals the expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyTruncatedValueNotEquals(CVerificationQueue verifier, int maxWidth, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).truncatedValueNotEquals(maxWidth, b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int, int)} value NOT equals the expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param offset   left edge of string to start truncate from
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyTruncatedValueNotEquals(CVerificationQueue verifier, int offset, int maxWidth, final String expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, b) -> _toState(a).truncatedValueNotEquals(offset, maxWidth, b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int)} value equals the expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   */
  default void verifyTruncatedValueNotEquals(CVerificationQueue verifier, int maxWidth, final String expected) {
    verifyTruncatedValueNotEquals(verifier, maxWidth, expected, getDefaultMessage("Actual Truncated Value With Maximum Width Of %s, Not Equals The Expected Value", maxWidth));
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int, int)} value NOT equals the expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param offset   left edge of string to start truncate from
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   */
  default void verifyTruncatedValueNotEquals(CVerificationQueue verifier, int offset, int maxWidth, final String expected) {
    verifyTruncatedValueNotEquals(verifier, offset, maxWidth, expected, getDefaultMessage("Actual Truncated Value With Maximum Width Of %s With Offset %s, Not Equals The Expected Value", maxWidth, offset));
  }
}
