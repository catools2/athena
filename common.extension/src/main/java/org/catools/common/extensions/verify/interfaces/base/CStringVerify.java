package org.catools.common.extensions.verify.interfaces.base;

import org.catools.common.extensions.base.CBaseStringExtension;
import org.catools.common.extensions.states.interfaces.CStringState;
import org.catools.common.extensions.verify.hard.CStringVerification;
import org.catools.common.utils.CStringUtil;

import java.util.List;
import java.util.Objects;
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
public interface CStringVerify extends CBaseStringExtension, CObjectVerify<String, CStringState> {

  @Override
  default boolean printDiff() {
    return true;
  }

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is equals to expected
   * value.
   *
   * @param size     the int size of new String, negative treated as zero
   * @param padStr   the String to pad the new String with, must not be null or empty
   * @param expected the expected result.
   */
  default void verifyCenterPadEquals(int size, String padStr, final String expected) {
    verifyCenterPadEquals(size, padStr, expected, getDefaultMessage("Value Center Pad With '%s' And The Length Of '%d' Equals To Expected Value", padStr, size));
  }

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is equals to expected
   * value.
   *
   * @param size     the int size of new String, negative treated as zero
   * @param padStr   the String to pad the new String with, must not be null or empty
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyCenterPadEquals(int size, String padStr, final String expected, String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).centerPadEquals(size, padStr, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param size     the int size of new String, negative treated as zero
   * @param padStr   the String to pad the new String with, must not be null or empty
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyCenterPadNotEquals(int size, String padStr, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).centerPadNotEquals(size, padStr, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param size     the int size of new String, negative treated as zero
   * @param padStr   the String to pad the new String with, must not be null or empty
   * @param expected the expected result.
   */
  default void verifyCenterPadNotEquals(int size, String padStr, final String expected) {
    verifyCenterPadNotEquals(size, padStr, expected, getDefaultMessage("Value Center Pad With '%s' And The Length Of '%d' Is Not Equal To Expected Value", padStr, size));
  }

  /**
   * Verify if result of {@link CStringUtil#compare(String, String)} equals to the expected value.
   *
   * @param stringToCompare the string value to compare against
   * @param expected        the expected result.
   */
  default void verifyCompare(String stringToCompare, int expected) {
    verifyCompare(stringToCompare, expected, getDefaultMessage("Result Of Comparison With The Expected Value Is '%d'", expected));
  }

  /**
   * Verify if result of {@link CStringUtil#compare(String, String)} equals to the expected value.
   *
   * @param stringToCompare the string value to compare against
   * @param expected        the expected result.
   * @param message         information about the purpose of this verification.
   * @param params          parameters in case if message is a format {@link String#format}
   */
  default void verifyCompare(String stringToCompare, int expected, final String message, final Object... params) {
    _verify(stringToCompare, (a, b) -> _toState(a).compare(b, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#compareIgnoreCase(String, String)} equals to the
   * expected value.
   *
   * @param stringToCompare the string value to compare against
   * @param expected        the expected result.
   */
  default void verifyCompareIgnoreCase(String stringToCompare, int expected) {
    verifyCompareIgnoreCase(stringToCompare, expected, getDefaultMessage("Result Of Comparison (Ignoring Case) With The Expected Value Is '%d'", expected));
  }

  /**
   * Verify if result of {@link CStringUtil#compareIgnoreCase(String, String)} equals to the
   * expected value.
   *
   * @param stringToCompare the string value to compare against
   * @param expected        the expected result.
   * @param message         information about the purpose of this verification.
   * @param params          parameters in case if message is a format {@link String#format}
   */
  default void verifyCompareIgnoreCase(String stringToCompare, int expected, String message, final Object... params) {
    _verify(stringToCompare, (a, b) -> _toState(a).compareIgnoreCase(b, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is true.
   *
   * @param expected the expected result.
   */
  default void verifyContains(final String expected) {
    verifyContains(expected, getDefaultMessage("Value Contains The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is true.
   *
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).contains(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is true,
   * ignoring case.
   *
   * @param expected the expected result.
   */
  default void verifyContainsIgnoreCase(final String expected) {
    verifyContainsIgnoreCase(expected, getDefaultMessage("Value Contains The Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is true,
   * ignoring case.
   *
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsIgnoreCase(final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).containsIgnoreCase(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#containsAnyIgnoreCase(CharSequence, CharSequence...)}
   * equals to any expected value, ignoring case.
   *
   * @param expectedList a list of strings, may be {@code null}.
   */
  default void verifyContainsAny(List<String> expectedList) {
    verifyContainsAny(expectedList, getDefaultMessage("Value Ends With Any Value From The Expected Values Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#containsAnyIgnoreCase(CharSequence, CharSequence...)}
   * equals to any expected value, ignoring case.
   *
   * @param expectedList a list of strings, may be {@code null}.
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsAny(List<String> expectedList, final String message, final Object... params) {
    _verify(expectedList, (a, b) -> _toState(a).containsAny(expectedList), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#containsAnyIgnoreCase(CharSequence, CharSequence...)}
   * equals to any expected value, ignoring case.
   *
   * @param expectedList a list of strings, may be {@code null}.
   */
  default void verifyContainsAnyIgnoreCase(List<String> expectedList) {
    verifyContainsAnyIgnoreCase(expectedList, getDefaultMessage("Value Ends With Any Value From The Expected Values Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#containsAnyIgnoreCase(CharSequence, CharSequence...)}
   * equals to any expected value, ignoring case.
   *
   * @param expectedList a list of strings, may be {@code null}.
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsAnyIgnoreCase(List<String> expectedList, final String message, final Object... params) {
    _verify(expectedList, (a, b) -> _toState(a).containsAnyIgnoreCase(expectedList), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is true
   *
   * @param suffix the suffix to find, may be {@code null}
   */
  default void verifyEndsWith(String suffix) {
    verifyEndsWith(suffix, getDefaultMessage("Value Ends With The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is true
   *
   * @param suffix  the suffix to find, may be {@code null}
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyEndsWith(String suffix, final String message, final Object... params) {
    _verify(suffix, (a, b) -> _toState(a).endsWith(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is true.
   *
   * @param searchInputs the case-sensitive CharSequences to find, may be empty or contain {@code
   *                     null}
   */
  default void verifyEndsWithAny(List<String> searchInputs) {
    verifyEndsWithAny(searchInputs, getDefaultMessage("Value Ends With Any Value From The Expected Values"));
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is true.
   *
   * @param searchInputs the case-sensitive CharSequences to find, may be empty or contain {@code
   *                     null}
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyEndsWithAny(List<String> searchInputs, final String message, final Object... params) {
    _verify(searchInputs, (a, b) -> _toState(a).endsWithAny(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is true.
   *
   * @param suffix the suffix to find, may be {@code null}
   */
  default void verifyEndsWithIgnoreCase(String suffix) {
    verifyEndsWithIgnoreCase(suffix, getDefaultMessage("Value Ends With The Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is true.
   *
   * @param suffix  the suffix to find, may be {@code null}
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyEndsWithIgnoreCase(String suffix, final String message, final Object... params) {
    _verify(suffix, (a, b) -> _toState(a).endsWithIgnoreCase(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is false.
   *
   * @param searchInputs the case-sensitive CharSequences to find, may be empty or contain {@code
   *                     null}
   */
  default void verifyEndsWithNone(List<String> searchInputs) {
    verifyEndsWithNone(searchInputs, getDefaultMessage("Value Ends With None Of Value From The Expected Values"));
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is false.
   *
   * @param searchInputs the case-sensitive CharSequences to find, may be empty or contain {@code
   *                     null}
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyEndsWithNone(List<String> searchInputs, final String message, final Object... params) {
    _verify(searchInputs, (a, b) -> _toState(a).endsWithNone(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)}
   * equals to any expected value, ignoring case.
   *
   * @param expectedList a list of strings, may be {@code null}.
   */
  default void verifyEqualsAnyIgnoreCase(List<String> expectedList) {
    verifyEqualsAnyIgnoreCase(expectedList, getDefaultMessage("Value Ends With Any Value From The Expected Values Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)}
   * equals to any expected value, ignoring case.
   *
   * @param expectedList a list of strings, may be {@code null}.
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsAnyIgnoreCase(List<String> expectedList, final String message, final Object... params) {
    _verify(expectedList, (a, b) -> _toState(a).equalsAnyIgnoreCase(b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#equalsIgnoreCase(CharSequence, CharSequence)} value equals the
   * expected value.
   *
   * @param expected the expected result.
   */
  default void verifyEqualsIgnoreCase(final String expected) {
    verifyEqualsIgnoreCase(expected, getDefaultMessage("Value Equals The Expected Values Ignoring Case Sensitivity"));
  }

  /**
   * Verify if {@link CStringUtil#equalsIgnoreCase(CharSequence, CharSequence)} value equals the
   * expected value.
   *
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsIgnoreCase(final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).equalsIgnoreCase(b), message, params);
  }

  /**
   * Verify if value is equal to expected after removing all WhiteSpaces from both.
   *
   * @param expected the expected result.
   */
  default void verifyEqualsIgnoreWhiteSpaces(final String expected) {
    verifyEqualsIgnoreWhiteSpaces(expected, getDefaultMessage("Value Equals The Expected Values Ignoring White Spaces"));
  }

  /**
   * Verify if value is equal to expected after removing all WhiteSpaces from both.
   *
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsIgnoreWhiteSpaces(final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).equalsIgnoreWhiteSpaces(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)} is
   * false, ignoring case.
   *
   * @param expectedList a list of strings, may be {@code null}.
   */
  default void verifyEqualsNoneIgnoreCase(List<String> expectedList) {
    verifyEqualsNoneIgnoreCase(expectedList, getDefaultMessage("Value Ends With None Of Value From The Expected Values Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)} is
   * false, ignoring case.
   *
   * @param expectedList a list of strings, may be {@code null}.
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsNoneIgnoreCase(List<String> expectedList, final String message, final Object... params) {
    _verify(expectedList, (a, b) -> _toState(a).equalsNoneIgnoreCase(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is true.
   */
  default void verifyIsAlpha() {
    verifyIsAlpha(getDefaultMessage("Value Contains Only Alpha Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is true.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAlpha(final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isAlpha(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is true.
   */
  default void verifyIsAlphaSpace() {
    verifyIsAlphaSpace(getDefaultMessage("Value Contains Only Alpha Or Space Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is true.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAlphaSpace(final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isAlphaSpace(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is true.
   */
  default void verifyIsAlphanumeric() {
    verifyIsAlphanumeric(getDefaultMessage("Value Contains Only Alpha-Numeric Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAlphanumeric(final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isAlphanumeric(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is true.
   */
  default void verifyIsAlphanumericSpace() {
    verifyIsAlphanumericSpace(getDefaultMessage("Value Contains Only Alpha-Numeric Or Space Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is true.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAlphanumericSpace(final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isAlphanumericSpace(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is true.
   */
  default void verifyIsAsciiPrintable() {
    verifyIsAsciiPrintable(getDefaultMessage("Value Contains Only Ascii Printable Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is true.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsAsciiPrintable(final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isAsciiPrintable(), message, params);
  }

  /**
   * Verify if String value is blank (Null or Empty)
   */
  default void verifyIsBlank() {
    verifyIsBlank(getDefaultMessage("Value Is Blank"));
  }

  /**
   * Verify if String value is blank (Null or Empty)
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlank(final String message, final Object... params) {
    _verify("<Blank>", (a, b) -> _toState(a).isBlank(), message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   */
  default void verifyIsBlankOrAlpha() {
    verifyIsBlankOrAlpha(getDefaultMessage("Value Contains Only Alpha Characters Or It Is Blank"));
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrAlpha(final String message, final Object... params) {
    _verify(true, (a, b) -> CStringUtil.isBlank(a) || _toState(a).isBlankOrAlpha(), message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   */
  default void verifyIsBlankOrAlphanumeric() {
    verifyIsBlankOrAlphanumeric(getDefaultMessage("Value Contains Only Alpha-Numeric Characters Or It Is Blank"));
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrAlphanumeric(final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isBlankOrAlphanumeric(), message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   */
  default void verifyIsBlankOrNotAlpha() {
    verifyIsBlankOrNotAlpha(getDefaultMessage("Value Is Blank Or Not Contains Only Alpha-Numeric Characters"));
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrNotAlpha(final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isBlankOrNotAlpha(), message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   */
  default void verifyIsBlankOrNotAlphanumeric() {
    verifyIsBlankOrNotAlphanumeric(getDefaultMessage("Value Is Blank Or Not Contains Only Alpha-Numeric Characters"));
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrNotAlphanumeric(final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isBlankOrNotAlphanumeric(), message, params);
  }

  /**
   * Verify if string is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   */
  default void verifyIsBlankOrNotNumeric() {
    verifyIsBlankOrNotNumeric(getDefaultMessage("Value Is Blank Or Not Contains Only Numeric Characters"));
  }

  /**
   * Verify if string is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrNotNumeric(final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isBlankOrNotNumeric(), message, params);
  }

  /**
   * Verify if string is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   */
  default void verifyIsBlankOrNumeric() {
    verifyIsBlankOrNumeric(getDefaultMessage("Value Is Blank Or Not Contains Only Numeric Characters"));
  }

  /**
   * Verify if string is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsBlankOrNumeric(final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isBlankOrNumeric(), message, params);
  }

  /**
   * Verify if String value is empty
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmpty(final String message, final Object... params) {
    _verify("<Empty>", (a, b) -> _toState(a).isEmpty(), message, params);
  }

  /**
   * Verify if String value is empty
   */
  default void verifyIsEmpty() {
    verifyIsEmpty(getDefaultMessage("Value Is Empty"));
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrAlpha(final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isEmptyOrAlpha(), message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   */
  default void verifyIsEmptyOrAlpha() {
    verifyIsEmptyOrAlpha(getDefaultMessage("Value Is Empty Or Contains Only Alpha Characters"));
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrAlphanumeric(final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isEmptyOrAlphanumeric(), message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   */
  default void verifyIsEmptyOrAlphanumeric() {
    verifyIsEmptyOrAlphanumeric(getDefaultMessage("Value Is Empty Or Contains Only Alpha-Numeric Characters"));
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrNotAlpha(final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isEmptyOrNotAlpha(), message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   */
  default void verifyIsEmptyOrNotAlpha() {
    verifyIsEmptyOrNotAlpha(getDefaultMessage("Value Is Empty Or Not Contains Only Alpha Characters"));
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrNotAlphanumeric(final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isEmptyOrNotAlphanumeric(), message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   */
  default void verifyIsEmptyOrNotAlphanumeric() {
    verifyIsEmptyOrNotAlphanumeric(getDefaultMessage("Value Is Empty Or Not Contains Only Alpha-Numeric Characters"));
  }

  /**
   * Verify if string is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrNotNumeric(final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isEmptyOrNotNumeric(), message, params);
  }

  /**
   * Verify if string is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   */
  default void verifyIsEmptyOrNotNumeric() {
    verifyIsEmptyOrNotNumeric(getDefaultMessage("Value Is Empty Or Not Contains Only Numeric Characters"));
  }

  /**
   * Verify if string is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmptyOrNumeric(final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isEmptyOrNumeric(), message, params);
  }

  /**
   * Verify if string is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   */
  default void verifyIsEmptyOrNumeric() {
    verifyIsEmptyOrNumeric(getDefaultMessage("Value Is Empty Or Contains Only Numeric Characters"));
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param pattern regular expression pattern
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyMatches(final Pattern pattern, final String message, final Object... params) {
    _verify(pattern, (a, b) -> _toState(a).matches(b), message, params);
  }

  /**
   * Verify if String value match any of provided patterns
   *
   * @param patterns regular expression pattern
   */
  default void verifyMatchesAny(final List<Pattern> patterns) {
    _verify(patterns, (a, b) -> _toState(a).matchAny(b), "Value Matches Any Of The Provided Patterns");
  }

  /**
   * Verify if String value match any of provided patterns
   *
   * @param patterns regular expression pattern
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyMatchesAny(final List<Pattern> patterns, final String message, final Object... params) {
    _verify(patterns, (a, b) -> _toState(a).matchAny(b), message, params);
  }

  /**
   * Verify if String value NOT match any of provided patterns
   *
   * @param patterns regular expression pattern
   */
  default void verifyMatchesNone(final List<Pattern> patterns) {
    verifyMatchesNone(patterns, getDefaultMessage("Value Matches None Of The Provided Patterns"));
  }

  /**
   * Verify if String value NOT match any of provided patterns
   *
   * @param patterns regular expression pattern
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyMatchesNone(final List<Pattern> patterns, final String message, final Object... params) {
    _verify(patterns, (a, b) -> _toState(a).matchNone(b), message, params);
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param pattern regular expression pattern
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyMatches(final String pattern, final String message, final Object... params) {
    _verify(pattern, (a, b) -> _toState(a).matches(b), message, params);
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param pattern regular expression pattern
   */
  default void verifyMatches(final Pattern pattern) {
    verifyMatches(pattern, getDefaultMessage("Value Matches The Provided Pattern"));
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param pattern regular expression pattern
   */
  default void verifyMatches(final String pattern) {
    Objects.requireNonNull(pattern);
    verifyMatches(Pattern.compile(pattern));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is false.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAlpha(final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isNotAlpha(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is false.
   */
  default void verifyIsNotAlpha() {
    verifyIsNotAlpha(getDefaultMessage("Value Not Contains Only Alpha Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is false.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAlphaSpace(final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isNotAlphaSpace(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is false.
   */
  default void verifyIsNotAlphaSpace() {
    verifyIsNotAlphaSpace(getDefaultMessage("Value Is Not Contains Only Alpha Characters Or Space"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAlphanumeric(final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isNotAlphanumeric(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is false.
   */
  default void verifyIsNotAlphanumeric() {
    verifyIsNotAlphanumeric(getDefaultMessage("Value Not Contains Only Alpha-Numeric Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is false.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAlphanumericSpace(final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isNotAlphanumericSpace(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is false.
   */
  default void verifyIsNotAlphanumericSpace() {
    verifyIsNotAlphanumericSpace(getDefaultMessage("Value Not Contains Only Alpha-Numeric Characters Or Space"));
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is false.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotAsciiPrintable(final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isNotAsciiPrintable(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is false.
   */
  default void verifyIsNotAsciiPrintable() {
    verifyIsNotAsciiPrintable(getDefaultMessage("Value Not Contains Only Ascii Printable Characters"));
  }

  /**
   * Verify if String value is not blank (Null or Empty)
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotBlank(final String message, final Object... params) {
    _verify("<Not Blank>", (a, b) -> _toState(a).isNotBlank(), message, params);
  }

  /**
   * Verify if String value is not blank (Null or Empty)
   */
  default void verifyIsNotBlank() {
    verifyIsNotBlank(getDefaultMessage("Value Is Not Blank"));
  }

  /**
   * Verify String value is not empty
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotEmpty(final String message, final Object... params) {
    _verify("<Not Empty>", (a, b) -> _toState(a).isNotEmpty(), message, params);
  }

  /**
   * Verify String value is not empty
   */
  default void verifyIsNotEmpty() {
    verifyIsNotEmpty(getDefaultMessage("Value Is Not Empty"));
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param message information about the purpose of this verification
   * @param pattern regular expression pattern
   */
  default void verifyNotMatches(final Pattern pattern, final String message, final Object... params) {
    _verify(pattern, (a, b) -> _toState(a).notMatches(b), message, params);
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param message information about the purpose of this verification
   * @param pattern regular expression pattern
   */
  default void verifyNotMatches(final String pattern, final String message, final Object... params) {
    _verify(pattern, (a, b) -> _toState(a).notMatches(b), message, params);
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param pattern regular expression pattern
   */
  default void verifyNotMatches(final Pattern pattern) {
    verifyNotMatches(pattern, getDefaultMessage("Value Is Not Match The Expected Pattern"));
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param pattern regular expression pattern
   */
  default void verifyNotMatches(final String pattern) {
    verifyNotMatches(pattern, getDefaultMessage("Value Is Not Match The Expected Pattern"));
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is false.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotNumeric(final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isNotNumeric(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is false.
   */
  default void verifyIsNotNumeric() {
    verifyIsNotNumeric(getDefaultMessage("Value Not Contains Only Numeric Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is false.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotNumericSpace(final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isNotNumericSpace(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is false.
   */
  default void verifyIsNotNumericSpace() {
    verifyIsNotNumericSpace(getDefaultMessage("Value Not Contains Only Numeric Or Space Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNumeric(final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isNumeric(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   */
  default void verifyIsNumeric() {
    verifyIsNumeric(getDefaultMessage("Value Contains Only Numeric Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is true.
   *
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNumericSpace(final String message, final Object... params) {
    _verify(true, (a, b) -> _toState(a).isNumericSpace(), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is true.
   */
  default void verifyIsNumericSpace() {
    verifyIsNumericSpace(getDefaultMessage("Value Contains Only Numeric Or Space Characters"));
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is equals to expected
   * value.
   *
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyLeftPadEquals(int size, String padStr, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).leftPadEquals(size, padStr, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is equals to expected
   * value.
   *
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   */
  default void verifyLeftPadEquals(int size, String padStr, final String expected) {
    verifyLeftPadEquals(size, padStr, expected, getDefaultMessage("Expected Value Equals To The Actual Value Left Pad With '%s' And The Length Of '%d'", padStr, size));
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyLeftPadNotEquals(int size, String padStr, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).leftPadNotEquals(size, padStr, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   */
  default void verifyLeftPadNotEquals(int size, String padStr, final String expected) {
    verifyLeftPadNotEquals(size, padStr, expected, getDefaultMessage("Expected Value Not Equals To The Actual Value Left Pad With '%s' And The Length Of '%d'", padStr, size));
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} equals to expected value.
   *
   * @param expected the expected result.
   * @param len      the length of the required String
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyLeftValueEquals(int len, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).leftValueEquals(len, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} equals to expected value.
   *
   * @param expected the expected result.
   * @param len      the length of the required String
   */
  default void verifyLeftValueEquals(int len, final String expected) {
    verifyLeftValueEquals(len, expected, getDefaultMessage("Expected Value Equals To The Left '%d' Character Of Actual Value", len));
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} NOT equals to expected value.
   *
   * @param expected the expected result.
   * @param len      the length of the required String
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyLeftValueNotEquals(int len, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).leftValueNotEquals(len, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} NOT equals to expected value.
   *
   * @param expected the expected result.
   * @param len      the length of the required String
   */
  default void verifyLeftValueNotEquals(int len, final String expected) {
    verifyLeftValueNotEquals(len, expected, getDefaultMessage("Expected Value Not Equals To The Left '%d' Character Of Actual Value", len));
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is equals to expected value.
   *
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyLengthEquals(int expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).lengthEquals(expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is equals to expected value.
   *
   * @param expected the expected result.
   */
  default void verifyLengthEquals(int expected) {
    verifyLengthEquals(expected, getDefaultMessage("Expected Value Length Equals To The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is NOT equals to expected value.
   *
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyLengthNotEquals(int expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).lengthNotEquals(expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is NOT equals to expected value.
   *
   * @param expected the expected result.
   */
  default void verifyLengthNotEquals(int expected) {
    verifyLengthNotEquals(expected, getDefaultMessage("Expected Value Length Not Equals To The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} equals to expected value.
   *
   * @param expected the expected result.
   * @param pos      the position to start from, negative treated as zero
   * @param len      the length of the required String
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyMidValueEquals(int pos, int len, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).midValueEquals(pos, len, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} equals to expected value.
   *
   * @param expected the expected result.
   * @param pos      the position to start from, negative treated as zero
   * @param len      the length of the required String
   */
  default void verifyMidValueEquals(int pos, int len, final String expected) {
    verifyMidValueEquals(pos, len, expected, getDefaultMessage("Expected Value Equals To The Characters Of Actual Value From Position '%d' For '%d' Length", pos, len));
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} NOT equals to expected value.
   *
   * @param expected the expected result.
   * @param pos      the position to start from, negative treated as zero
   * @param len      the length of the required String
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyMidValueNotEquals(int pos, int len, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).midValueNotEquals(pos, len, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} NOT equals to expected value.
   *
   * @param expected the expected result.
   * @param pos      the position to start from, negative treated as zero
   * @param len      the length of the required String
   */
  default void verifyMidValueNotEquals(int pos, int len, final String expected) {
    verifyMidValueNotEquals(pos, len, expected, getDefaultMessage("Expected Value Not Equals To The Characters Of Actual Value From Position '%d' For '%d' Length", pos, len));
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is false.
   *
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).notContains(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is false.
   *
   * @param expected the expected result.
   */
  default void verifyNotContains(final String expected) {
    verifyNotContains(expected, getDefaultMessage("Actual Value Not Contains The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContainsIgnoreCase(final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).notContainsIgnoreCase(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param expected the expected result.
   */
  default void verifyNotContainsIgnoreCase(final String expected) {
    verifyNotContainsIgnoreCase(expected, getDefaultMessage("Actual Value Not Contains The Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is false
   *
   * @param suffix  the suffix to find, may be {@code null}
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEndsWith(String suffix, final String message, final Object... params) {
    _verify(suffix, (a, b) -> _toState(a).notEndsWith(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is false
   *
   * @param suffix the suffix to find, may be {@code null}
   */
  default void verifyNotEndsWith(String suffix) {
    verifyNotEndsWith(suffix, getDefaultMessage("Actual Value Not Ends With The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param suffix  the suffix to find, may be {@code null}
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEndsWithIgnoreCase(String suffix, final String message, final Object... params) {
    _verify(suffix, (a, b) -> _toState(a).notEndsWithIgnoreCase(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param suffix the suffix to find, may be {@code null}
   */
  default void verifyNotEndsWithIgnoreCase(String suffix) {
    verifyNotEndsWithIgnoreCase(suffix, getDefaultMessage("Actual Value Not Ends With The Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if {@link CStringUtil#equalsIgnoreCase(CharSequence, CharSequence)} value NOT equals the
   * expected value.
   *
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsIgnoreCase(final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).notEqualsIgnoreCase(b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#equalsIgnoreCase(CharSequence, CharSequence)} value NOT equals the
   * expected value.
   *
   * @param expected the expected result.
   */
  default void verifyNotEqualsIgnoreCase(final String expected) {
    verifyNotEqualsIgnoreCase(expected, getDefaultMessage("Actual Value Not Equals To The Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if value is not equal to expected after removing all WhiteSpaces from both.
   *
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsIgnoreWhiteSpaces(final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).notEqualsIgnoreWhiteSpaces(b), message, params);
  }

  /**
   * Verify if value is not equal to expected after removing all WhiteSpaces from both.
   *
   * @param expected the expected result.
   */
  default void verifyNotEqualsIgnoreWhiteSpaces(final String expected) {
    verifyNotEqualsIgnoreWhiteSpaces(expected, getDefaultMessage("Actual Value Not Equals To The Expected Value Ignoring White Spaces"));
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is false
   *
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotStartsWith(final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).notStartsWith(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is false
   *
   * @param expected the expected result.
   */
  default void verifyNotStartsWith(final String expected) {
    verifyNotStartsWith(expected, getDefaultMessage("Actual Value Not Starts With The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * false
   *
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotStartsWithIgnoreCase(final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).notStartsWithIgnoreCase(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * false
   *
   * @param expected the expected result.
   */
  default void verifyNotStartsWithIgnoreCase(final String expected) {
    verifyNotStartsWithIgnoreCase(expected, getDefaultMessage("Actual Value Not Starts With The Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is equals to
   * expected value.
   *
   * @param expected  the expected result.
   * @param subString the substring to count, may be null
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifyNumberOfMatchesEquals(String subString, int expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).numberOfMatchesEquals(subString, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is equals to
   * expected value.
   *
   * @param expected  the expected result.
   * @param subString the substring to count, may be null
   */
  default void verifyNumberOfMatchesEquals(String subString, int expected) {
    verifyNumberOfMatchesEquals(subString, expected, getDefaultMessage("Actual Value Contains Exact Number Of Substring"));
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is NOT equals
   * to expected value.
   *
   * @param expected  the expected result.
   * @param subString the substring to count, may be null
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifyNumberOfMatchesNotEquals(String subString, int expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).numberOfMatchesNotEquals(subString, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is NOT equals
   * to expected value.
   *
   * @param expected  the expected result.
   * @param subString the substring to count, may be null
   */
  default void verifyNumberOfMatchesNotEquals(String subString, int expected) {
    verifyNumberOfMatchesNotEquals(subString, expected, getDefaultMessage("Actual Value Not Contains Exact Number Of Substring"));
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is equals to expected value.
   *
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEndEquals(String remove, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).removeEndEquals(remove, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is equals to expected value.
   *
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   */
  default void verifyRemoveEndEquals(String remove, final String expected) {
    verifyRemoveEndEquals(remove, expected, getDefaultMessage("Actual Value, After Removing '%s' From End, Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEndIgnoreCaseEquals(String remove, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).removeEndIgnoreCaseEquals(remove, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   */
  default void verifyRemoveEndIgnoreCaseEquals(String remove, final String expected) {
    verifyRemoveEndIgnoreCaseEquals(remove, expected, getDefaultMessage("Actual Value, After Removing '%s' From End Ignoring Case Sensitivity, Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEndIgnoreCaseNotEquals(String remove, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).removeEndIgnoreCaseNotEquals(remove, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   */
  default void verifyRemoveEndIgnoreCaseNotEquals(String remove, final String expected) {
    verifyRemoveEndIgnoreCaseNotEquals(remove, expected, getDefaultMessage("Actual Value, After Removing '%s' From End Ignoring Case Sensitivity, Not Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is NOT equals to expected
   * value.
   *
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEndNotEquals(String remove, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).removeEndNotEquals(remove, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is NOT equals to expected
   * value.
   *
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   */
  default void verifyRemoveEndNotEquals(String remove, final String expected) {
    verifyRemoveEndNotEquals(remove, expected, getDefaultMessage("Actual Value, After Removing '%s', Not Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is equals to expected value.
   *
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveEquals(String remove, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).removeEquals(remove, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is equals to expected value.
   *
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   */
  default void verifyRemoveEquals(String remove, final String expected) {
    verifyRemoveEquals(remove, expected, getDefaultMessage("Actual Value, After Removing '%s', Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is equals to expected
   * value.
   *
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveIgnoreCaseEquals(String remove, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).removeIgnoreCaseEquals(remove, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is equals to expected
   * value.
   *
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   */
  default void verifyRemoveIgnoreCaseEquals(String remove, final String expected) {
    verifyRemoveIgnoreCaseEquals(remove, expected, getDefaultMessage("Actual Value, After Removing '%s' Ignoring Case Sensitivity, Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveIgnoreCaseNotEquals(String remove, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).removeIgnoreCaseNotEquals(remove, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   */
  default void verifyRemoveIgnoreCaseNotEquals(String remove, final String expected) {
    verifyRemoveIgnoreCaseNotEquals(remove, expected, getDefaultMessage("Actual Value, After Removing '%s' Ignoring Case Sensitivity, Not Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is NOT equals to expected value.
   *
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveNotEquals(String remove, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).removeNotEquals(remove, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is NOT equals to expected value.
   *
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   */
  default void verifyRemoveNotEquals(String remove, final String expected) {
    verifyRemoveNotEquals(remove, expected, getDefaultMessage("Actual Value, After Removing '%s', Not Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is equals to expected
   * value.
   *
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveStartEquals(String remove, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).removeStartEquals(remove, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is equals to expected
   * value.
   *
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   */
  default void verifyRemoveStartEquals(String remove, final String expected) {
    verifyRemoveStartEquals(remove, expected, getDefaultMessage("Actual Value, After Removing '%s' From Start, Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveStartIgnoreCaseEquals(String remove, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).removeStartIgnoreCaseEquals(remove, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   */
  default void verifyRemoveStartIgnoreCaseEquals(String remove, final String expected) {
    verifyRemoveStartIgnoreCaseEquals(remove, expected, getDefaultMessage("Actual Value, After Removing '%s' From Start Ignoring Case Sensitivity, Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveStartIgnoreCaseNotEquals(String remove, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).removeStartIgnoreCaseNotEquals(remove, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   */
  default void verifyRemoveStartIgnoreCaseNotEquals(String remove, final String expected) {
    verifyRemoveStartIgnoreCaseNotEquals(remove, expected, getDefaultMessage("Actual Value, After Removing '%s' From Start Ignoring Case Sensitivity, Not Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is NOT equals to expected
   * value.
   *
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRemoveStartNotEquals(String remove, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).removeStartNotEquals(remove, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is NOT equals to expected
   * value.
   *
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   */
  default void verifyRemoveStartNotEquals(String remove, final String expected) {
    verifyRemoveStartNotEquals(remove, expected, getDefaultMessage("Actual Value, After Removing '%s' From Start, Not Equals To The Expected Value", remove));
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is equals to expected
   * value.
   *
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceEquals(String searchString, String replacement, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).replaceEquals(searchString, replacement, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is equals to expected
   * value.
   *
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   */
  default void verifyReplaceEquals(String searchString, String replacement, final String expected) {
    verifyReplaceEquals(searchString, replacement, expected, getDefaultMessage("Actual Value, After Replacing '%s' With '%s', Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is equals to
   * expected value.
   *
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceIgnoreCaseEquals(String searchString, String replacement, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).replaceIgnoreCaseEquals(searchString, replacement, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is equals to
   * expected value.
   *
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   */
  default void verifyReplaceIgnoreCaseEquals(String searchString, String replacement, final String expected) {
    verifyReplaceIgnoreCaseEquals(searchString, replacement, expected, getDefaultMessage("Actual Value, After Replacing '%s' With '%s' Ignoring Case Sensitivity, Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is NOT equals
   * to expected value.
   *
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceIgnoreCaseNotEquals(String searchString, String replacement, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).replaceIgnoreCaseNotEquals(searchString, replacement, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is NOT equals
   * to expected value.
   *
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   */
  default void verifyReplaceIgnoreCaseNotEquals(String searchString, String replacement, final String expected) {
    verifyReplaceIgnoreCaseNotEquals(searchString, replacement, expected, getDefaultMessage("Actual Value, After Replacing '%s' With '%s' Ignoring Case Sensitivity, Not Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceNotEquals(String searchString, String replacement, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).replaceNotEquals(searchString, replacement, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   */
  default void verifyReplaceNotEquals(String searchString, String replacement, final String expected) {
    verifyReplaceNotEquals(searchString, replacement, expected, getDefaultMessage("Actual Value, After Replacing '%s' With '%s', Not Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is equals to
   * expected value.
   *
   * @param searchString the String to search for, may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceOnceEquals(String searchString, String replacement, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).replaceOnceEquals(searchString, replacement, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is equals to
   * expected value.
   *
   * @param searchString the String to search for, may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   */
  default void verifyReplaceOnceEquals(String searchString, String replacement, final String expected) {
    verifyReplaceOnceEquals(searchString, replacement, expected, getDefaultMessage("Actual Value, After Replacing Once '%s' With '%s', Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is equals
   * to expected value.
   *
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceOnceIgnoreCaseEquals(String searchString, String replacement, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).replaceOnceIgnoreCaseEquals(searchString, replacement, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is equals
   * to expected value.
   *
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   */
  default void verifyReplaceOnceIgnoreCaseEquals(String searchString, String replacement, final String expected) {
    verifyReplaceOnceIgnoreCaseEquals(searchString, replacement, expected, getDefaultMessage("Actual Value, After Replacing Once '%s' With '%s' Ignoring Case Sensitivity, Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is NOT
   * equals to expected value.
   *
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceOnceIgnoreCaseNotEquals(String searchString, String replacement, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).replaceOnceIgnoreCaseNotEquals(searchString, replacement, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is NOT
   * equals to expected value.
   *
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   */
  default void verifyReplaceOnceIgnoreCaseNotEquals(String searchString, String replacement, final String expected) {
    verifyReplaceOnceIgnoreCaseNotEquals(searchString, replacement, expected, getDefaultMessage("Actual Value, After Replacing Once '%s' With '%s' Ignoring Case Sensitivity, Not Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param searchString the String to search for, may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyReplaceOnceNotEquals(String searchString, String replacement, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).replaceOnceNotEquals(searchString, replacement, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param searchString the String to search for, may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   */
  default void verifyReplaceOnceNotEquals(String searchString, String replacement, final String expected) {
    verifyReplaceOnceNotEquals(searchString, replacement, expected, getDefaultMessage("Actual Value, After Replacing Once '%s' With '%s', Not Equals To The Expected Value", searchString, replacement));
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is equals to expected value.
   *
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyReverseEquals(final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).reverseEquals(expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is equals to expected value.
   *
   * @param expected the expected result.
   */
  default void verifyReverseEquals(final String expected) {
    verifyReverseEquals(expected, getDefaultMessage("Actual Value, After Reversing Order Of Characters, Equals To The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is NOT equals to expected value.
   *
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyReverseNotEquals(final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).reverseNotEquals(expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is NOT equals to expected value.
   *
   * @param expected the expected result.
   */
  default void verifyReverseNotEquals(final String expected) {
    verifyReverseNotEquals(expected, getDefaultMessage("Actual Value, After Reversing Order Of Characters, Not Equals To The Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is equals to expected
   * value.
   *
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRightPadEquals(int size, String padStr, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).rightPadEquals(size, padStr, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is equals to expected
   * value.
   *
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   */
  default void verifyRightPadEquals(int size, String padStr, final String expected) {
    verifyRightPadEquals(size, padStr, expected, getDefaultMessage("Expected Value Equals To The Actual Value Right Pad With '%s' And The Length Of '%d'", padStr, size));
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRightPadNotEquals(int size, String padStr, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).rightPadNotEquals(size, padStr, expected), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   */
  default void verifyRightPadNotEquals(int size, String padStr, final String expected) {
    verifyRightPadNotEquals(size, padStr, expected, getDefaultMessage("Expected Value Not Equals To The Actual Value Right Pad With '%s' And The Length Of '%d'", padStr, size));
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} equals to expected value.
   *
   * @param expected the expected result.
   * @param len      the length of the required String
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRightValueEquals(int len, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).rightValueEquals(len, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} equals to expected value.
   *
   * @param expected the expected result.
   * @param len      the length of the required String
   */
  default void verifyRightValueEquals(int len, final String expected) {
    verifyRightValueEquals(len, expected, getDefaultMessage("Expected Value Equals To The Right '%d' Character Of Actual Value", len));
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} NOT equals to expected value.
   *
   * @param expected the expected result.
   * @param len      the length of the required String
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyRightValueNotEquals(int len, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).rightValueNotEquals(len, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} NOT equals to expected value.
   *
   * @param expected the expected result.
   * @param len      the length of the required String
   */
  default void verifyRightValueNotEquals(int len, final String expected) {
    verifyRightValueNotEquals(len, expected, getDefaultMessage("Expected Value Not Equals To The Right '%d' Character Of Actual Value", len));
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is true
   *
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyStartsWith(final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).startsWith(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is true
   *
   * @param expected the expected result.
   */
  default void verifyStartsWith(final String expected) {
    verifyStartsWith(expected, getDefaultMessage("Actual Value Starts With Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is true
   *
   * @param searchInputs the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                     null}
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyStartsWithAny(List<String> searchInputs, final String message, final Object... params) {
    _verify(searchInputs, (a, b) -> _toState(a).startsWithAny(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is true
   *
   * @param searchInputs the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                     null}
   */
  default void verifyStartsWithAny(List<String> searchInputs) {
    verifyStartsWithAny(searchInputs, getDefaultMessage("Actual Value Starts With Any Expected Value"));
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * true
   *
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyStartsWithIgnoreCase(final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).startsWithIgnoreCase(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * true
   *
   * @param expected the expected result.
   */
  default void verifyStartsWithIgnoreCase(final String expected) {
    verifyStartsWithIgnoreCase(expected, getDefaultMessage("Actual Value Starts With Expected Value Ignoring Case Sensitivity"));
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is false
   *
   * @param searchInputs the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                     null}
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyStartsWithNone(List<String> searchInputs, final String message, final Object... params) {
    _verify(searchInputs, (a, b) -> _toState(a).startsWithNone(b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is false
   *
   * @param searchInputs the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                     null}
   */
  default void verifyStartsWithNone(List<String> searchInputs) {
    verifyStartsWithNone(searchInputs, getDefaultMessage("Actual Value Starts With None Of Expected Value"));
  }

  /**
   * Verify if {@link CStringUtil#stripEnd(String, String)} value equals the expected value.
   *
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   * @param message    information about the purpose of this verification
   * @param params     parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedEndValue(String stripChars, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).stripedEndValue(stripChars, b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#stripEnd(String, String)} value equals the expected value.
   *
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   */
  default void verifyStripedEndValue(String stripChars, final String expected) {
    verifyStripedEndValue(stripChars, expected, getDefaultMessage("Actual Value Striped End '%s' Characters, Equals To The Expected Value", stripChars));
  }

  /**
   * Verify if {@link CStringUtil#stripEnd(String, String)} value NOT equals the expected value.
   *
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   * @param message    information about the purpose of this verification
   * @param params     parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedEndValueNot(String stripChars, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).stripedEndValueNot(stripChars, b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#stripEnd(String, String)} value NOT equals the expected value.
   *
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   */
  default void verifyStripedEndValueNot(String stripChars, final String expected) {
    verifyStripedEndValueNot(stripChars, expected, getDefaultMessage("Actual Value Striped End '%s' Characters, Not Equals To The Expected Value", stripChars));
  }

  /**
   * Verify if {@link CStringUtil#stripStart(String, String)} value equals the expected value.
   *
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   * @param message    information about the purpose of this verification
   * @param params     parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedStartValue(String stripChars, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).stripedStartValue(stripChars, b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#stripStart(String, String)} value equals the expected value.
   *
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   */
  default void verifyStripedStartValue(String stripChars, final String expected) {
    verifyStripedStartValue(stripChars, expected, getDefaultMessage("Actual Value Striped Start '%s' Characters, Equals To The Expected Value", stripChars));
  }

  /**
   * Verify if {@link CStringUtil#stripStart(String, String)} value NOT equals the expected value.
   *
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   * @param message    information about the purpose of this verification
   * @param params     parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedStartValueNot(String stripChars, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).stripedStartValueNot(stripChars, b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#stripStart(String, String)} value NOT equals the expected value.
   *
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   */
  default void verifyStripedStartValueNot(String stripChars, final String expected) {
    verifyStripedStartValueNot(stripChars, expected, getDefaultMessage("Actual Value Striped End '%s' Characters, Not Equals To The Expected Value", stripChars));
  }

  /**
   * Verify if {@link CStringUtil#strip(String)} value equals the expected value.
   *
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   * @param message    information about the purpose of this verification
   * @param params     parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedValue(String stripChars, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).stripedValue(stripChars, b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#strip(String)} value equals the expected value.
   *
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   */
  default void verifyStripedValue(String stripChars, final String expected) {
    verifyStripedValue(stripChars, expected, getDefaultMessage("Actual Value Striped '%s' Characters, Equals To The Expected Value", stripChars));
  }

  /**
   * Verify if {@link CStringUtil#strip(String)} value NOT equals the expected value.
   *
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   * @param message    information about the purpose of this verification
   * @param params     parameters in case if message is a format {@link String#format}
   */
  default void verifyStripedValueNot(String stripChars, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).stripedValueNot(stripChars, b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#strip(String)} value NOT equals the expected value.
   *
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   */
  default void verifyStripedValueNot(String stripChars, final String expected) {
    verifyStripedValueNot(stripChars, expected, getDefaultMessage("Actual Value Striped '%s' Characters, Not Equals To The Expected Value", stripChars));
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} equals to expected
   * value.
   *
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringAfterEquals(String separator, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringAfterEquals(separator, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} equals to expected
   * value.
   *
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   */
  default void verifySubstringAfterEquals(String separator, final String expected) {
    verifySubstringAfterEquals(separator, expected, getDefaultMessage("Actual Value Substring After '%s', Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} equals to expected
   * value.
   *
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringAfterLastEquals(String separator, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringAfterLastEquals(separator, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} equals to expected
   * value.
   *
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   */
  default void verifySubstringAfterLastEquals(String separator, final String expected) {
    verifySubstringAfterLastEquals(separator, expected, getDefaultMessage("Actual Value Substring After Last '%s', Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} NOT equals to
   * expected value.
   *
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringAfterLastNotEquals(String separator, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringAfterLastNotEquals(separator, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} NOT equals to
   * expected value.
   *
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   */
  default void verifySubstringAfterLastNotEquals(String separator, final String expected) {
    verifySubstringAfterLastNotEquals(separator, expected, getDefaultMessage("Actual Value Substring After Last '%s', Not Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} NOT equals to expected
   * value.
   *
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringAfterNotEquals(String separator, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringAfterNotEquals(separator, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} NOT equals to expected
   * value.
   *
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   */
  default void verifySubstringAfterNotEquals(String separator, final String expected) {
    verifySubstringAfterNotEquals(separator, expected, getDefaultMessage("Actual Value Substring After '%s', Not Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} equals to expected
   * value.
   *
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBeforeEquals(String separator, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringBeforeEquals(separator, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} equals to expected
   * value.
   *
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   */
  default void verifySubstringBeforeEquals(String separator, final String expected) {
    verifySubstringBeforeEquals(separator, expected, getDefaultMessage("Actual Value Substring Before '%s', Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} equals to expected
   * value.
   *
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBeforeLastEquals(String separator, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringBeforeLastEquals(separator, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} equals to expected
   * value.
   *
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   */
  default void verifySubstringBeforeLastEquals(String separator, final String expected) {
    verifySubstringBeforeLastEquals(separator, expected, getDefaultMessage("Actual Value Substring Before Last '%s', Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} NOT equals to
   * expected value.
   *
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBeforeLastNotEquals(String separator, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringBeforeLastNotEquals(separator, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} NOT equals to
   * expected value.
   *
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   */
  default void verifySubstringBeforeLastNotEquals(String separator, final String expected) {
    verifySubstringBeforeLastNotEquals(separator, expected, getDefaultMessage("Actual Value Substring Before Last '%s', Not Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} NOT equals to expected
   * value.
   *
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBeforeNotEquals(String separator, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringBeforeNotEquals(separator, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} NOT equals to expected
   * value.
   *
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   */
  default void verifySubstringBeforeNotEquals(String separator, final String expected) {
    verifySubstringBeforeNotEquals(separator, expected, getDefaultMessage("Actual Value Substring Before '%s', Not Equals To The Expected Value", separator));
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String)} equals to expected
   * value.
   *
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBetweenEquals(String open, String close, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringBetweenEquals(open, close, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String)} equals to expected
   * value.
   *
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   */
  default void verifySubstringBetweenEquals(String open, String close, final String expected) {
    verifySubstringBetweenEquals(open, close, expected, getDefaultMessage("Actual Value Substring Between '%s' and '%s', Equals To The Expected Value", open, close));
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String)} NOT equals to expected
   * value.
   *
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringBetweenNotEquals(String open, String close, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringBetweenNotEquals(open, close, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String)} NOT equals to expected
   * value.
   *
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   */
  default void verifySubstringBetweenNotEquals(String open, String close, final String expected) {
    verifySubstringBetweenNotEquals(open, close, expected, getDefaultMessage("Actual Value Substring Between '%s' and '%s', Not Equals To The Expected Value", open, close));
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} equals to expected value.
   *
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringEquals(int start, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringEquals(start, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} equals to expected value.
   *
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   * @param end      the position to end at (exclusive), negative means count back from the end of the
   *                 String
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringEquals(int start, int end, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringEquals(start, end, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} equals to expected value.
   *
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   */
  default void verifySubstringEquals(int start, final String expected) {
    verifySubstringEquals(start, expected, getDefaultMessage("Actual Value Substring After Position '%s', Equals To The Expected Value", start));
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} equals to expected value.
   *
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   * @param end      the position to end at (exclusive), negative means count back from the end of the
   *                 String
   */
  default void verifySubstringEquals(int start, int end, final String expected) {
    verifySubstringEquals(start, end, expected, getDefaultMessage("Actual Value Substring From Position '%s' To '%s', Equals To The Expected Value", start, end));
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} NOT equals to expected value.
   *
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringNotEquals(int start, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringNotEquals(start, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} NOT equals to expected
   * value.
   *
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   * @param end      the position to end at (exclusive), negative means count back from the end of the
   *                 String
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringNotEquals(int start, int end, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringNotEquals(start, end, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} NOT equals to expected value.
   *
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   */
  default void verifySubstringNotEquals(int start, final String expected) {
    verifySubstringNotEquals(start, expected, getDefaultMessage("Actual Value Substring After Position '%s', Not Equals To The Expected Value", start));
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} NOT equals to expected
   * value.
   *
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   * @param end      the position to end at (exclusive), negative means count back from the end of the
   *                 String
   */
  default void verifySubstringNotEquals(int start, int end, final String expected) {
    verifySubstringNotEquals(start, end, expected, getDefaultMessage("Actual Value Substring From Position '%s' To '%s', Not Equals To The Expected Value", start, end));
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} contains to
   * expected value.
   *
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringsBetweenContains(String open, String close, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringsBetweenContains(open, close, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} Contains
   * expected value.
   *
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   */
  default void verifySubstringsBetweenContains(String open, String close, final String expected) {
    verifySubstringsBetweenContains(open, close, expected, getDefaultMessage("Actual Value Substring Between '%s' To '%s' Characters, Contains The Expected Value", open, close));
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} equals to
   * expected value.
   *
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringsBetweenEquals(String open, String close, List<String> expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringsBetweenEquals(open, close, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} equals to
   * expected value.
   *
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   */
  default void verifySubstringsBetweenEquals(String open, String close, List<String> expected) {
    verifySubstringsBetweenEquals(open, close, expected, getDefaultMessage("Actual Value Substring Between '%s' To '%s' Characters, Equals To The Expected Value", open, close));
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT contains
   * to expected value.
   *
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringsBetweenNotContains(String open, String close, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringsBetweenNotContains(open, close, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT Contains
   * expected value.
   *
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   */
  default void verifySubstringsBetweenNotContains(String open, String close, final String expected) {
    verifySubstringsBetweenNotContains(open, close, expected, getDefaultMessage("Actual Value Substring Between '%s' To '%s' Characters, Not Contains The Expected Value", open, close));
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT equals to
   * expected value.
   *
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifySubstringsBetweenNotEquals(String open, String close, List<String> expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).substringsBetweenNotEquals(open, close, b), message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT equals to
   * expected value.
   *
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   */
  default void verifySubstringsBetweenNotEquals(String open, String close, List<String> expected) {
    verifySubstringsBetweenNotEquals(open, close, expected, getDefaultMessage("Actual Value Substring Between '%s' To '%s' Characters, Not Equals The Expected Value", open, close));
  }

  /**
   * Verify if {@link CStringUtil#trim(String)} value equals the expected value.
   *
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyTrimmedValueEquals(final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).trimmedValueEquals(b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#trim(String)} value equals the expected value.
   *
   * @param expected the expected result.
   */
  default void verifyTrimmedValueEquals(final String expected) {
    verifyTrimmedValueEquals(expected, getDefaultMessage("Actual Trimmed Value, Equals The Expected Value"));
  }

  /**
   * Verify if {@link CStringUtil#trim(String)} value NOT equals the expected value.
   *
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyTrimmedValueNotEquals(final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).trimmedValueNotEquals(b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#trim(String)} value NOT equals the expected value.
   *
   * @param expected the expected result.
   */
  default void verifyTrimmedValueNotEquals(final String expected) {
    verifyTrimmedValueNotEquals(expected, getDefaultMessage("Actual Trimmed Value, Not Equals The Expected Value"));
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int)} value equals the expected value.
   *
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyTruncatedValueEquals(int maxWidth, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).truncatedValueEquals(maxWidth, b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int, int)} value equals the expected value.
   *
   * @param offset   left edge of string to start truncate from
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyTruncatedValueEquals(int offset, int maxWidth, final String expected, final String message, final java.lang.Object... params) {
    _verify(expected, (a, b) -> _toState(a).truncatedValueEquals(offset, maxWidth, b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int)} value equals the expected value.
   *
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   */
  default void verifyTruncatedValueEquals(int maxWidth, final String expected) {
    verifyTruncatedValueEquals(maxWidth, expected, getDefaultMessage("Actual Truncated Value With Maximum Width Of %s, Equals The Expected Value", maxWidth));
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int, int)} value equals the expected value.
   *
   * @param offset   left edge of string to start truncate from
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   */
  default void verifyTruncatedValueEquals(int offset, int maxWidth, final String expected) {
    verifyTruncatedValueEquals(offset, maxWidth, expected, getDefaultMessage("Actual Truncated Value With Maximum Width Of %s With Offset %s, Equals The Expected Value", maxWidth, offset));
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int)} value equals the expected value.
   *
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyTruncatedValueNotEquals(int maxWidth, final String expected, final java.lang.String message, final java.lang.Object... params) {
    _verify(expected, (a, b) -> _toState(a).truncatedValueNotEquals(maxWidth, b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int, int)} value NOT equals the expected value.
   *
   * @param offset   left edge of string to start truncate from
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyTruncatedValueNotEquals(int offset, int maxWidth, final String expected, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).truncatedValueNotEquals(offset, maxWidth, b), message, params);
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int)} value equals the expected value.
   *
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   */
  default void verifyTruncatedValueNotEquals(int maxWidth, final String expected) {
    verifyTruncatedValueNotEquals(maxWidth, expected, getDefaultMessage("Actual Truncated Value With Maximum Width Of %s, Not Equals The Expected Value", maxWidth));
  }

  /**
   * Verify if {@link CStringUtil#truncate(String, int, int)} value NOT equals the expected value.
   *
   * @param offset   left edge of string to start truncate from
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   */
  default void verifyTruncatedValueNotEquals(int offset, int maxWidth, final String expected) {
    verifyTruncatedValueNotEquals(offset, maxWidth, expected, getDefaultMessage("Actual Truncated Value With Maximum Width Of %s With Offset %s, Not Equals The Expected Value", maxWidth, offset));
  }
}
