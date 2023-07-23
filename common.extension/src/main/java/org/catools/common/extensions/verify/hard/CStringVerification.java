package org.catools.common.extensions.verify.hard;

import lombok.extern.slf4j.Slf4j;
import org.catools.common.extensions.verify.interfaces.base.CStringVerify;
import org.catools.common.utils.CStringUtil;

import java.util.List;
import java.util.regex.Pattern;

/**
 * String verification class contains all verification method which is related to String
 */
@Slf4j
public class CStringVerification extends CBaseVerification {
  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is equals to expected
   * value.
   *
   * @param actual   value to compare
   * @param size     the int size of new String, negative treated as zero
   * @param padStr   the String to pad the new String with, must not be null or empty
   * @param expected the expected result.
   */
  public void centerPadEquals(String actual, int size, String padStr, String expected) {
    toVerifier(actual).verifyCenterPadEquals(size, padStr, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is equals to expected
   * value.
   *
   * @param actual   value to compare
   * @param size     the int size of new String, negative treated as zero
   * @param padStr   the String to pad the new String with, must not be null or empty
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void centerPadEquals(String actual, int size, String padStr, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyCenterPadEquals(size, padStr, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param actual   value to compare
   * @param size     the int size of new String, negative treated as zero
   * @param padStr   the String to pad the new String with, must not be null or empty
   * @param expected the expected result.
   */
  public void centerPadNotEquals(String actual, int size, String padStr, String expected) {
    toVerifier(actual).verifyCenterPadNotEquals(size, padStr, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param actual   value to compare
   * @param size     the int size of new String, negative treated as zero
   * @param padStr   the String to pad the new String with, must not be null or empty
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void centerPadNotEquals(String actual, int size, String padStr, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyCenterPadNotEquals(size, padStr, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#compare(String, String)} equals to the expected value.
   *
   * @param actual          value to compare
   * @param stringToCompare the string value to compare against
   * @param expected        the expected result.
   */
  public void compare(String actual, String stringToCompare, int expected) {
    toVerifier(actual).verifyCompare(stringToCompare, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#compare(String, String)} equals to the expected value.
   *
   * @param actual          value to compare
   * @param stringToCompare the string value to compare against
   * @param expected        the expected result.
   * @param message         information about the purpose of this verification.
   * @param params          parameters in case if message is a format {@link String#format}
   */
  public void compare(String actual, String stringToCompare, int expected, final String message, final Object... params) {
    toVerifier(actual).verifyCompare(stringToCompare, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#compareIgnoreCase(String, String)} equals to the
   * expected value.
   *
   * @param actual          value to compare
   * @param stringToCompare the string value to compare against
   * @param expected        the expected result.
   */
  public void compareIgnoreCase(String actual, String stringToCompare, int expected) {
    toVerifier(actual).verifyCompareIgnoreCase(stringToCompare, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#compareIgnoreCase(String, String)} equals to the
   * expected value.
   *
   * @param actual          value to compare
   * @param stringToCompare the string value to compare against
   * @param expected        the expected result.
   * @param message         information about the purpose of this verification.
   * @param params          parameters in case if message is a format {@link String#format}
   */
  public void compareIgnoreCase(String actual, String stringToCompare, int expected, final String message, final Object... params) {
    toVerifier(actual).verifyCompareIgnoreCase(stringToCompare, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is true.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   */
  public void contains(String actual, String expected) {
    toVerifier(actual).verifyContains(expected);
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is true.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void contains(String actual, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyContains(expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is true.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   */
  public void containsIgnoreCase(String actual, String expected) {
    toVerifier(actual).verifyContainsIgnoreCase(expected);
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is true.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void containsIgnoreCase(String actual, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyContainsIgnoreCase(expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is true
   *
   * @param actual value to compare
   * @param suffix the suffix to find, may be {@code null}
   */
  public void endsWith(String actual, String suffix) {
    toVerifier(actual).verifyEndsWith(suffix);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is true
   *
   * @param actual  value to compare
   * @param suffix  the suffix to find, may be {@code null}
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void endsWith(String actual, String suffix, final String message, final Object... params) {
    toVerifier(actual).verifyEndsWith(suffix, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is true.
   *
   * @param actual       value to compare
   * @param searchInputs the case-sensitive CharSequences to find, may be empty or contain {@code
   *                     null}
   */
  public void endsWithAny(String actual, List<String> searchInputs) {
    toVerifier(actual).verifyEndsWithAny(searchInputs);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is true.
   *
   * @param actual       value to compare
   * @param searchInputs the case-sensitive CharSequences to find, may be empty or contain {@code
   *                     null}
   * @param message      information about the purpose of this verification.
   * @param params       parameters in case if message is a format {@link String#format}
   */
  public void endsWithAny(String actual, List<String> searchInputs, final String message, final Object... params) {
    toVerifier(actual).verifyEndsWithAny(searchInputs, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is true.
   *
   * @param actual value to compare
   * @param suffix the suffix to find, may be {@code null}
   */
  public void endsWithIgnoreCase(String actual, String suffix) {
    toVerifier(actual).verifyEndsWithIgnoreCase(suffix);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is true.
   *
   * @param actual  value to compare
   * @param suffix  the suffix to find, may be {@code null}
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void endsWithIgnoreCase(String actual, String suffix, final String message, final Object... params) {
    toVerifier(actual).verifyEndsWithIgnoreCase(suffix, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is false.
   *
   * @param actual       value to compare
   * @param searchInputs the case-sensitive CharSequences to find, may be empty or contain {@code
   *                     null}
   */
  public void endsWithNone(String actual, List<String> searchInputs) {
    toVerifier(actual).verifyEndsWithNone(searchInputs);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is false.
   *
   * @param actual       value to compare
   * @param searchInputs the case-sensitive CharSequences to find, may be empty or contain {@code
   *                     null}
   * @param message      information about the purpose of this verification.
   * @param params       parameters in case if message is a format {@link String#format}
   */
  public void endsWithNone(String actual, List<String> searchInputs, final String message, final Object... params) {
    toVerifier(actual).verifyEndsWithNone(searchInputs, message, params);
  }

  /**
   * Verify if {@code equals(String)} value equals the expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   */
  public void equals(String actual, String expected) {
    toVerifier(actual).verifyEquals(expected);
  }

  /**
   * Verify if {@code equals(String)} value equals the expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void equals(String actual, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyEquals(expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAny(CharSequence, CharSequence...)} equals to the
   * expected value.
   *
   * @param actual       value to compare
   * @param expectedList a list of strings, may be {@code null}.
   */
  public void equalsAny(String actual, List<String> expectedList) {
    toVerifier(actual).verifyEqualsAny(expectedList);
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAny(CharSequence, CharSequence...)} equals to the
   * expected value.
   *
   * @param actual       value to compare
   * @param expectedList a list of strings, may be {@code null}.
   * @param message      information about the purpose of this verification.
   * @param params       parameters in case if message is a format {@link String#format}
   */
  public void equalsAny(String actual, List<String> expectedList, final String message, final Object... params) {
    toVerifier(actual).verifyEqualsAny(expectedList, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)} is
   * true.
   *
   * @param actual       value to compare
   * @param expectedList a list of strings, may be {@code null}.
   */
  public void equalsAnyIgnoreCase(String actual, List<String> expectedList) {
    toVerifier(actual).verifyEqualsAnyIgnoreCase(expectedList);
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)} is
   * true.
   *
   * @param actual       value to compare
   * @param expectedList a list of strings, may be {@code null}.
   * @param message      information about the purpose of this verification.
   * @param params       parameters in case if message is a format {@link String#format}
   */
  public void equalsAnyIgnoreCase(String actual, List<String> expectedList, final String message, final Object... params) {
    toVerifier(actual).verifyEqualsAnyIgnoreCase(expectedList, message, params);
  }

  /**
   * Verify if {@code equalsIgnoreCase(String)} value equals the expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   */
  public void equalsIgnoreCase(String actual, String expected) {
    toVerifier(actual).verifyEqualsIgnoreCase(expected);
  }

  /**
   * Verify if {@code equalsIgnoreCase(String)} value equals the expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void equalsIgnoreCase(String actual, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyEqualsIgnoreCase(expected, message, params);
  }

  /**
   * Verify if {@code equalsIgnoreWhiteSpace(String)} value equals the expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   */
  public void equalsIgnoreWhiteSpaces(String actual, String expected) {
    toVerifier(actual).verifyEqualsIgnoreWhiteSpaces(expected);
  }

  /**
   * Verify if {@code equalsIgnoreWhiteSpace(String)} value equals the expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void equalsIgnoreWhiteSpaces(String actual, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyEqualsIgnoreWhiteSpaces(expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAny(CharSequence, CharSequence...)} is false
   *
   * @param actual       value to compare
   * @param expectedList a list of strings, may be {@code null}.
   */
  public void equalsNone(String actual, List<String> expectedList) {
    toVerifier(actual).verifyEqualsNone(expectedList);
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAny(CharSequence, CharSequence...)} is false
   *
   * @param actual       value to compare
   * @param expectedList a list of strings, may be {@code null}.
   * @param message      information about the purpose of this verification.
   * @param params       parameters in case if message is a format {@link String#format}
   */
  public void equalsNone(String actual, List<String> expectedList, final String message, final Object... params) {
    toVerifier(actual).verifyEqualsNone(expectedList, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)} is
   * false.
   *
   * @param actual       value to compare
   * @param expectedList a list of strings, may be {@code null}.
   */
  public void equalsNoneIgnoreCase(String actual, List<String> expectedList) {
    toVerifier(actual).verifyEqualsNoneIgnoreCase(expectedList);
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)} is
   * false.
   *
   * @param actual       value to compare
   * @param expectedList a list of strings, may be {@code null}.
   * @param message      information about the purpose of this verification.
   * @param params       parameters in case if message is a format {@link String#format}
   */
  public void equalsNoneIgnoreCase(String actual, List<String> expectedList, final String message, final Object... params) {
    toVerifier(actual).verifyEqualsNoneIgnoreCase(expectedList, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is true.
   *
   * @param actual value to compare
   */
  public void isAlpha(String actual) {
    toVerifier(actual).verifyIsAlpha();
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is true.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isAlpha(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsAlpha(message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is true.
   *
   * @param actual value to compare
   */
  public void isAlphaSpace(String actual) {
    toVerifier(actual).verifyIsAlphaSpace();
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is true.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isAlphaSpace(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsAlphaSpace(message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param actual value to compare
   */
  public void isAlphanumeric(String actual) {
    toVerifier(actual).verifyIsAlphanumeric();
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isAlphanumeric(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsAlphanumeric(message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is true.
   *
   * @param actual value to compare
   */
  public void isAlphanumericSpace(String actual) {
    toVerifier(actual).verifyIsAlphanumericSpace();
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is true.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isAlphanumericSpace(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsAlphanumericSpace(message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is true.
   *
   * @param actual value to compare
   */
  public void isAsciiPrintable(String actual) {
    toVerifier(actual).verifyIsAsciiPrintable();
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is true.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isAsciiPrintable(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsAsciiPrintable(message, params);
  }

  /**
   * Verify if String value is blank (Null or Empty)
   *
   * @param actual value to compare
   */
  public void isBlank(String actual) {
    toVerifier(actual).verifyIsBlank();
  }

  /**
   * Verify if String value is blank (Null or Empty)
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isBlank(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsBlank(message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param actual value to compare
   */
  public void isBlankOrAlpha(String actual) {
    toVerifier(actual).verifyIsBlankOrAlpha();
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isBlankOrAlpha(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsBlankOrAlpha(message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param actual value to compare
   */
  public void isBlankOrAlphanumeric(String actual) {
    toVerifier(actual).verifyIsBlankOrAlphanumeric();
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isBlankOrAlphanumeric(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsBlankOrAlphanumeric(message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param actual value to compare
   */
  public void isBlankOrNotAlpha(String actual) {
    toVerifier(actual).verifyIsBlankOrNotAlpha();
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isBlankOrNotAlpha(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsBlankOrNotAlpha(message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param actual value to compare
   */
  public void isBlankOrNotAlphanumeric(String actual) {
    toVerifier(actual).verifyIsBlankOrNotAlphanumeric();
  }

  /**
   * Verify if string value is Blank or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isBlankOrNotAlphanumeric(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsBlankOrNotAlphanumeric(message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param actual value to compare
   */
  public void isBlankOrNotNumeric(String actual) {
    toVerifier(actual).verifyIsBlankOrNotNumeric();
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isBlankOrNotNumeric(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsBlankOrNotNumeric(message, params);
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * true.
   *
   * @param actual value to compare
   */
  public void isBlankOrNumeric(String actual) {
    toVerifier(actual).verifyIsBlankOrNumeric();
  }

  /**
   * Verify if string value is Blank or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * true.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isBlankOrNumeric(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsBlankOrNumeric(message, params);
  }

  /**
   * Verify if String value is empty
   *
   * @param actual value to compare
   */
  public void isEmpty(String actual) {
    toVerifier(actual).verifyIsEmpty();
  }

  /**
   * Verify if String value is empty
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isEmpty(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsEmpty(message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param actual value to compare
   */
  public void isEmptyOrAlpha(String actual) {
    toVerifier(actual).verifyIsEmptyOrAlpha();
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isEmptyOrAlpha(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsEmptyOrAlpha(message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param actual value to compare
   */
  public void isEmptyOrAlphanumeric(String actual) {
    toVerifier(actual).verifyIsEmptyOrAlphanumeric();
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isEmptyOrAlphanumeric(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsEmptyOrAlphanumeric(message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param actual value to compare
   */
  public void isEmptyOrNotAlpha(String actual) {
    toVerifier(actual).verifyIsEmptyOrNotAlpha();
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isEmptyOrNotAlpha(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsEmptyOrNotAlpha(message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param actual value to compare
   */
  public void isEmptyOrNotAlphanumeric(String actual) {
    toVerifier(actual).verifyIsEmptyOrNotAlphanumeric();
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isEmptyOrNotAlphanumeric(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsEmptyOrNotAlphanumeric(message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param actual value to compare
   */
  public void isEmptyOrNotNumeric(String actual) {
    toVerifier(actual).verifyIsEmptyOrNotNumeric();
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isEmptyOrNotNumeric(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsEmptyOrNotNumeric(message, params);
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * true.
   *
   * @param actual value to compare
   */
  public void isEmptyOrNumeric(String actual) {
    toVerifier(actual).verifyIsEmptyOrNumeric();
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * true.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isEmptyOrNumeric(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsEmptyOrNumeric(message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is false.
   *
   * @param actual value to compare
   */
  public void isNotAlpha(String actual) {
    toVerifier(actual).verifyIsNotAlpha();
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is false.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isNotAlpha(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsNotAlpha(message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is false.
   *
   * @param actual value to compare
   */
  public void isNotAlphaSpace(String actual) {
    toVerifier(actual).verifyIsNotAlphaSpace();
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is false.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isNotAlphaSpace(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsNotAlphaSpace(message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param actual value to compare
   */
  public void isNotAlphanumeric(String actual) {
    toVerifier(actual).verifyIsNotAlphanumeric();
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isNotAlphanumeric(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsNotAlphanumeric(message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is false.
   *
   * @param actual value to compare
   */
  public void isNotAlphanumericSpace(String actual) {
    toVerifier(actual).verifyIsNotAlphanumericSpace();
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is false.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isNotAlphanumericSpace(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsNotAlphanumericSpace(message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is false.
   *
   * @param actual value to compare
   */
  public void isNotAsciiPrintable(String actual) {
    toVerifier(actual).verifyIsNotAsciiPrintable();
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is false.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isNotAsciiPrintable(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsNotAsciiPrintable(message, params);
  }

  /**
   * Verify if String value is not blank (Null or Empty)
   *
   * @param actual value to compare
   */
  public void isNotBlank(String actual) {
    toVerifier(actual).verifyIsNotBlank();
  }

  /**
   * Verify if String value is not blank (Null or Empty)
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isNotBlank(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsNotBlank(message, params);
  }

  /**
   * Verify String value is not empty
   *
   * @param actual value to compare
   */
  public void isNotEmpty(String actual) {
    toVerifier(actual).verifyIsNotEmpty();
  }

  /**
   * Verify String value is not empty
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isNotEmpty(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsNotEmpty(message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is false.
   *
   * @param actual value to compare
   */
  public void isNotNumeric(String actual) {
    toVerifier(actual).verifyIsNotNumeric();
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is false.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isNotNumeric(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsNotNumeric(message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is false.
   *
   * @param actual value to compare
   */
  public void isNotNumericSpace(String actual) {
    toVerifier(actual).verifyIsNotNumericSpace();
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is false.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isNotNumericSpace(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsNotNumericSpace(message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param actual value to compare
   */
  public void isNumeric(String actual) {
    toVerifier(actual).verifyIsNumeric();
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isNumeric(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsNumeric(message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is true.
   *
   * @param actual value to compare
   */
  public void isNumericSpace(String actual) {
    toVerifier(actual).verifyIsNumericSpace();
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is true.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void isNumericSpace(String actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsNumericSpace(message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is equals to expected
   * value.
   *
   * @param actual   value to compare
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   */
  public void leftPadEquals(String actual, int size, String padStr, String expected) {
    toVerifier(actual).verifyLeftPadEquals(size, padStr, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is equals to expected
   * value.
   *
   * @param actual   value to compare
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void leftPadEquals(String actual, int size, String padStr, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyLeftPadEquals(size, padStr, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param actual   value to compare
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   */
  public void leftPadNotEquals(String actual, int size, String padStr, String expected) {
    toVerifier(actual).verifyLeftPadNotEquals(size, padStr, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param actual   value to compare
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void leftPadNotEquals(String actual, int size, String padStr, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyLeftPadNotEquals(size, padStr, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} equals to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param len      the length of the required String
   */
  public void leftValueEquals(String actual, int len, String expected) {
    toVerifier(actual).verifyLeftValueEquals(len, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} equals to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param len      the length of the required String
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void leftValueEquals(String actual, int len, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyLeftValueEquals(len, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} NOT equals to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param len      the length of the required String
   */
  public void leftValueNotEquals(String actual, int len, String expected) {
    toVerifier(actual).verifyLeftValueNotEquals(len, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} NOT equals to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param len      the length of the required String
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void leftValueNotEquals(String actual, int len, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyLeftValueNotEquals(len, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is equals to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   */
  public void lengthEquals(String actual, int expected) {
    toVerifier(actual).verifyLengthEquals(expected);
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is equals to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void lengthEquals(String actual, int expected, final String message, final Object... params) {
    toVerifier(actual).verifyLengthEquals(expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is NOT equals to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   */
  public void lengthNotEquals(String actual, int expected) {
    toVerifier(actual).verifyLengthNotEquals(expected);
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is NOT equals to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void lengthNotEquals(String actual, int expected, final String message, final Object... params) {
    toVerifier(actual).verifyLengthNotEquals(expected, message, params);
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param actual  value to compare
   * @param pattern regular expression pattern
   */
  public void matches(String actual, final Pattern pattern) {
    toVerifier(actual).verifyMatches(pattern);
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param actual  value to compare
   * @param pattern regular expression pattern
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void matches(String actual, final Pattern pattern, final String message, final Object... params) {
    toVerifier(actual).verifyMatches(pattern, message, params);
  }

  /**
   * Verify if String value match any of provided patterns
   *
   * @param actual   value to compare
   * @param patterns regular expression pattern
   */
  public void matchesAny(String actual, final List<Pattern> patterns) {
    toVerifier(actual).verifyMatchesAny(patterns);
  }

  /**
   * Verify if String value match any of provided patterns
   *
   * @param actual   value to compare
   * @param patterns regular expression pattern
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void matchesAny(String actual, final List<Pattern> patterns, final String message, final Object... params) {
    toVerifier(actual).verifyMatchesAny(patterns, message, params);
  }

  /**
   * Verify if String value NOT match any of provided patterns
   *
   * @param actual   value to compare
   * @param patterns regular expression pattern
   */
  public void matchesNone(String actual, final List<Pattern> patterns) {
    toVerifier(actual).verifyMatchesNone(patterns);
  }

  /**
   * Verify if String value NOT match any of provided patterns
   *
   * @param actual   value to compare
   * @param patterns regular expression pattern
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void matchesNone(String actual, final List<Pattern> patterns, final String message, final Object... params) {
    toVerifier(actual).verifyMatchesNone(patterns, message, params);
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param actual  value to compare
   * @param pattern regular expression pattern
   */
  public void matches(String actual, final String pattern) {
    toVerifier(actual).verifyMatches(pattern);
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param actual  value to compare
   * @param pattern regular expression pattern
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void matches(String actual, final String pattern, final String message, final Object... params) {
    toVerifier(actual).verifyMatches(pattern, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} equals to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param pos      the position to start from, negative treated as zero
   * @param len      the length of the required String
   */
  public void midValueEquals(String actual, int pos, int len, String expected) {
    toVerifier(actual).verifyMidValueEquals(pos, len, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} equals to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param pos      the position to start from, negative treated as zero
   * @param len      the length of the required String
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void midValueEquals(String actual, int pos, int len, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyMidValueEquals(pos, len, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} NOT equals to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param pos      the position to start from, negative treated as zero
   * @param len      the length of the required String
   */
  public void midValueNotEquals(String actual, int pos, int len, String expected) {
    toVerifier(actual).verifyMidValueNotEquals(pos, len, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} NOT equals to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param pos      the position to start from, negative treated as zero
   * @param len      the length of the required String
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void midValueNotEquals(String actual, int pos, int len, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyMidValueNotEquals(pos, len, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is false.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   */
  public void notContains(String actual, String expected) {
    toVerifier(actual).verifyNotContains(expected);
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is false.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void notContains(String actual, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyNotContains(expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   */
  public void notContainsIgnoreCase(String actual, String expected) {
    toVerifier(actual).verifyNotContainsIgnoreCase(expected);
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void notContainsIgnoreCase(String actual, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyNotContainsIgnoreCase(expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is false
   *
   * @param actual value to compare
   * @param suffix the suffix to find, may be {@code null}
   */
  public void notEndsWith(String actual, String suffix) {
    toVerifier(actual).verifyNotEndsWith(suffix);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is false
   *
   * @param actual  value to compare
   * @param suffix  the suffix to find, may be {@code null}
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void notEndsWith(String actual, String suffix, final String message, final Object... params) {
    toVerifier(actual).verifyNotEndsWith(suffix, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param actual value to compare
   * @param suffix the suffix to find, may be {@code null}
   */
  public void notEndsWithIgnoreCase(String actual, String suffix) {
    toVerifier(actual).verifyNotEndsWithIgnoreCase(suffix);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param actual  value to compare
   * @param suffix  the suffix to find, may be {@code null}
   * @param message information about the purpose of this verification.
   * @param params  parameters in case if message is a format {@link String#format}
   */
  public void notEndsWithIgnoreCase(String actual, String suffix, final String message, final Object... params) {
    toVerifier(actual).verifyNotEndsWithIgnoreCase(suffix, message, params);
  }

  /**
   * Verify if {@code equals(String)} value NOT equals the expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   */
  public void notEquals(String actual, String expected) {
    toVerifier(actual).verifyNotEquals(expected);
  }

  /**
   * Verify if {@code equals(String)} value NOT equals the expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void notEquals(String actual, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyNotEquals(expected, message, params);
  }

  /**
   * Verify if {@code equalsIgnoreCase(String)} value NOT equals the expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   */
  public void notEqualsIgnoreCase(String actual, String expected) {
    toVerifier(actual).verifyNotEqualsIgnoreCase(expected);
  }

  /**
   * Verify if {@code equalsIgnoreCase(String)} value NOT equals the expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void notEqualsIgnoreCase(String actual, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyNotEqualsIgnoreCase(expected, message, params);
  }

  /**
   * Verify if {@code equalsIgnoreWhiteSpace(String)} value NOT equals the expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   */
  public void notEqualsIgnoreWhiteSpaces(String actual, String expected) {
    toVerifier(actual).verifyNotEqualsIgnoreWhiteSpaces(expected);
  }

  /**
   * Verify if {@code equalsIgnoreWhiteSpace(String)} value NOT equals the expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void notEqualsIgnoreWhiteSpaces(String actual, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyNotEqualsIgnoreWhiteSpaces(expected, message, params);
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param actual  value to compare
   * @param pattern regular expression pattern
   */
  public void notMatches(String actual, final Pattern pattern) {
    toVerifier(actual).verifyNotMatches(pattern);
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param pattern regular expression pattern
   */
  public void notMatches(String actual, final Pattern pattern, final String message, final Object... params) {
    toVerifier(actual).verifyNotMatches(pattern, message, params);
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param actual  value to compare
   * @param pattern regular expression pattern
   */
  public void notMatches(String actual, final String pattern) {
    toVerifier(actual).verifyNotMatches(pattern);
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification.
   * @param pattern regular expression pattern
   */
  public void notMatches(String actual, final String pattern, final String message, final Object... params) {
    toVerifier(actual).verifyNotMatches(pattern, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is false
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void notStartsWith(String actual, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyNotStartsWith(expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is false
   *
   * @param actual   value to compare
   * @param expected the expected result.
   */
  public void notStartsWith(String actual, String expected) {
    toVerifier(actual).verifyNotStartsWith(expected);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * false
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void notStartsWithIgnoreCase(String actual, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyNotStartsWithIgnoreCase(expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * false
   *
   * @param actual   value to compare
   * @param expected the expected result.
   */
  public void notStartsWithIgnoreCase(String actual, String expected) {
    toVerifier(actual).verifyNotStartsWithIgnoreCase(expected);
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is equals to
   * expected value.
   *
   * @param actual    value to compare
   * @param expected  the expected result.
   * @param subString the substring to count, may be null
   * @param message   information about the purpose of this verification.
   * @param params    parameters in case if message is a format {@link String#format}
   */
  public void numberOfMatchesEquals(String actual, String subString, int expected, final String message, final Object... params) {
    toVerifier(actual).verifyNumberOfMatchesEquals(subString, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is equals to
   * expected value.
   *
   * @param actual    value to compare
   * @param expected  the expected result.
   * @param subString the substring to count, may be null
   */
  public void numberOfMatchesEquals(String actual, String subString, int expected) {
    toVerifier(actual).verifyNumberOfMatchesEquals(subString, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is NOT equals
   * to expected value.
   *
   * @param actual    value to compare
   * @param expected  the expected result.
   * @param subString the substring to count, may be null
   * @param message   information about the purpose of this verification.
   * @param params    parameters in case if message is a format {@link String#format}
   */
  public void numberOfMatchesNotEquals(String actual, String subString, int expected, final String message, final Object... params) {
    toVerifier(actual).verifyNumberOfMatchesNotEquals(subString, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is NOT equals
   * to expected value.
   *
   * @param actual    value to compare
   * @param expected  the expected result.
   * @param subString the substring to count, may be null
   */
  public void numberOfMatchesNotEquals(String actual, String subString, int expected) {
    toVerifier(actual).verifyNumberOfMatchesNotEquals(subString, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is equals to expected value.
   *
   * @param actual   value to compare
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void removeEndEquals(String actual, String remove, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyRemoveEndEquals(remove, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is equals to expected value.
   *
   * @param actual   value to compare
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   */
  public void removeEndEquals(String actual, String remove, String expected) {
    toVerifier(actual).verifyRemoveEndEquals(remove, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param actual   value to compare
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void removeEndIgnoreCaseEquals(String actual, String remove, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyRemoveEndIgnoreCaseEquals(remove, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param actual   value to compare
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   */
  public void removeEndIgnoreCaseEquals(String actual, String remove, String expected) {
    toVerifier(actual).verifyRemoveEndIgnoreCaseEquals(remove, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param actual   value to compare
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void removeEndIgnoreCaseNotEquals(String actual, String remove, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyRemoveEndIgnoreCaseNotEquals(remove, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param actual   value to compare
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   */
  public void removeEndIgnoreCaseNotEquals(String actual, String remove, String expected) {
    toVerifier(actual).verifyRemoveEndIgnoreCaseNotEquals(remove, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is NOT equals to expected
   * value.
   *
   * @param actual   value to compare
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void removeEndNotEquals(String actual, String remove, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyRemoveEndNotEquals(remove, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is NOT equals to expected
   * value.
   *
   * @param actual   value to compare
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   */
  public void removeEndNotEquals(String actual, String remove, String expected) {
    toVerifier(actual).verifyRemoveEndNotEquals(remove, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is equals to expected value.
   *
   * @param actual   value to compare
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void removeEquals(String actual, String remove, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyRemoveEquals(remove, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is equals to expected value.
   *
   * @param actual   value to compare
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   */
  public void removeEquals(String actual, String remove, String expected) {
    toVerifier(actual).verifyRemoveEquals(remove, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is equals to expected
   * value.
   *
   * @param actual   value to compare
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void removeIgnoreCaseEquals(String actual, String remove, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyRemoveIgnoreCaseEquals(remove, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is equals to expected
   * value.
   *
   * @param actual   value to compare
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   */
  public void removeIgnoreCaseEquals(String actual, String remove, String expected) {
    toVerifier(actual).verifyRemoveIgnoreCaseEquals(remove, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param actual   value to compare
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void removeIgnoreCaseNotEquals(String actual, String remove, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyRemoveIgnoreCaseNotEquals(remove, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param actual   value to compare
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   */
  public void removeIgnoreCaseNotEquals(String actual, String remove, String expected) {
    toVerifier(actual).verifyRemoveIgnoreCaseNotEquals(remove, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is NOT equals to expected value.
   *
   * @param actual   value to compare
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void removeNotEquals(String actual, String remove, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyRemoveNotEquals(remove, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is NOT equals to expected value.
   *
   * @param actual   value to compare
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   */
  public void removeNotEquals(String actual, String remove, String expected) {
    toVerifier(actual).verifyRemoveNotEquals(remove, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is equals to expected
   * value.
   *
   * @param actual   value to compare
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void removeStartEquals(String actual, String remove, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyRemoveStartEquals(remove, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is equals to expected
   * value.
   *
   * @param actual   value to compare
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   */
  public void removeStartEquals(String actual, String remove, String expected) {
    toVerifier(actual).verifyRemoveStartEquals(remove, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param actual   value to compare
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void removeStartIgnoreCaseEquals(String actual, String remove, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyRemoveStartIgnoreCaseEquals(remove, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param actual   value to compare
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   */
  public void removeStartIgnoreCaseEquals(String actual, String remove, String expected) {
    toVerifier(actual).verifyRemoveStartIgnoreCaseEquals(remove, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param actual   value to compare
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void removeStartIgnoreCaseNotEquals(String actual, String remove, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyRemoveStartIgnoreCaseNotEquals(remove, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param actual   value to compare
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   */
  public void removeStartIgnoreCaseNotEquals(String actual, String remove, String expected) {
    toVerifier(actual).verifyRemoveStartIgnoreCaseNotEquals(remove, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is NOT equals to expected
   * value.
   *
   * @param actual   value to compare
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void removeStartNotEquals(String actual, String remove, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyRemoveStartNotEquals(remove, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is NOT equals to expected
   * value.
   *
   * @param actual   value to compare
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   */
  public void removeStartNotEquals(String actual, String remove, String expected) {
    toVerifier(actual).verifyRemoveStartNotEquals(remove, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is equals to expected
   * value.
   *
   * @param actual       value to compare
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   * @param message      information about the purpose of this verification.
   * @param params       parameters in case if message is a format {@link String#format}
   */
  public void replaceEquals(String actual, String searchString, String replacement, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyReplaceEquals(searchString, replacement, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is equals to expected
   * value.
   *
   * @param actual       value to compare
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   */
  public void replaceEquals(String actual, String searchString, String replacement, String expected) {
    toVerifier(actual).verifyReplaceEquals(searchString, replacement, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is equals to
   * expected value.
   *
   * @param actual       value to compare
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   * @param message      information about the purpose of this verification.
   * @param params       parameters in case if message is a format {@link String#format}
   */
  public void replaceIgnoreCaseEquals(String actual, String searchString, String replacement, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyReplaceIgnoreCaseEquals(searchString, replacement, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is equals to
   * expected value.
   *
   * @param actual       value to compare
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   */
  public void replaceIgnoreCaseEquals(String actual, String searchString, String replacement, String expected) {
    toVerifier(actual).verifyReplaceIgnoreCaseEquals(searchString, replacement, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is NOT equals
   * to expected value.
   *
   * @param actual       value to compare
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   * @param message      information about the purpose of this verification.
   * @param params       parameters in case if message is a format {@link String#format}
   */
  public void replaceIgnoreCaseNotEquals(String actual, String searchString, String replacement, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyReplaceIgnoreCaseNotEquals(searchString, replacement, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is NOT equals
   * to expected value.
   *
   * @param actual       value to compare
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   */
  public void replaceIgnoreCaseNotEquals(String actual, String searchString, String replacement, String expected) {
    toVerifier(actual).verifyReplaceIgnoreCaseNotEquals(searchString, replacement, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param actual       value to compare
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   * @param message      information about the purpose of this verification.
   * @param params       parameters in case if message is a format {@link String#format}
   */
  public void replaceNotEquals(String actual, String searchString, String replacement, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyReplaceNotEquals(searchString, replacement, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param actual       value to compare
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   */
  public void replaceNotEquals(String actual, String searchString, String replacement, String expected) {
    toVerifier(actual).verifyReplaceNotEquals(searchString, replacement, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is equals to
   * expected value.
   *
   * @param actual       value to compare
   * @param searchString the String to search for, may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   * @param message      information about the purpose of this verification.
   * @param params       parameters in case if message is a format {@link String#format}
   */
  public void replaceOnceEquals(String actual, String searchString, String replacement, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyReplaceOnceEquals(searchString, replacement, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is equals to
   * expected value.
   *
   * @param actual       value to compare
   * @param searchString the String to search for, may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   */
  public void replaceOnceEquals(String actual, String searchString, String replacement, String expected) {
    toVerifier(actual).verifyReplaceOnceEquals(searchString, replacement, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is equals
   * to expected value.
   *
   * @param actual       value to compare
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   * @param message      information about the purpose of this verification.
   * @param params       parameters in case if message is a format {@link String#format}
   */
  public void replaceOnceIgnoreCaseEquals(String actual, String searchString, String replacement, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyReplaceOnceIgnoreCaseEquals(searchString, replacement, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is equals
   * to expected value.
   *
   * @param actual       value to compare
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   */
  public void replaceOnceIgnoreCaseEquals(String actual, String searchString, String replacement, String expected) {
    toVerifier(actual).verifyReplaceOnceIgnoreCaseEquals(searchString, replacement, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is NOT
   * equals to expected value.
   *
   * @param actual       value to compare
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   * @param message      information about the purpose of this verification.
   * @param params       parameters in case if message is a format {@link String#format}
   */
  public void replaceOnceIgnoreCaseNotEquals(String actual, String searchString, String replacement, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyReplaceOnceIgnoreCaseNotEquals(searchString, replacement, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is NOT
   * equals to expected value.
   *
   * @param actual       value to compare
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   */
  public void replaceOnceIgnoreCaseNotEquals(String actual, String searchString, String replacement, String expected) {
    toVerifier(actual).verifyReplaceOnceIgnoreCaseNotEquals(searchString, replacement, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param actual       value to compare
   * @param searchString the String to search for, may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   * @param message      information about the purpose of this verification.
   * @param params       parameters in case if message is a format {@link String#format}
   */
  public void replaceOnceNotEquals(String actual, String searchString, String replacement, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyReplaceOnceNotEquals(searchString, replacement, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param actual       value to compare
   * @param searchString the String to search for, may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   */
  public void replaceOnceNotEquals(String actual, String searchString, String replacement, String expected) {
    toVerifier(actual).verifyReplaceOnceNotEquals(searchString, replacement, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is equals to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void reverseEquals(String actual, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyReverseEquals(expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is equals to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   */
  public void reverseEquals(String actual, String expected) {
    toVerifier(actual).verifyReverseEquals(expected);
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is NOT equals to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void reverseNotEquals(String actual, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyReverseNotEquals(expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is NOT equals to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   */
  public void reverseNotEquals(String actual, String expected) {
    toVerifier(actual).verifyReverseNotEquals(expected);
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is equals to expected
   * value.
   *
   * @param actual   value to compare
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void rightPadEquals(String actual, int size, String padStr, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyRightPadEquals(size, padStr, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is equals to expected
   * value.
   *
   * @param actual   value to compare
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   */
  public void rightPadEquals(String actual, int size, String padStr, String expected) {
    toVerifier(actual).verifyRightPadEquals(size, padStr, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param actual   value to compare
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void rightPadNotEquals(String actual, int size, String padStr, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyRightPadNotEquals(size, padStr, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param actual   value to compare
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   */
  public void rightPadNotEquals(String actual, int size, String padStr, String expected) {
    toVerifier(actual).verifyRightPadNotEquals(size, padStr, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} equals to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param len      the length of the required String
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void rightValueEquals(String actual, int len, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyRightValueEquals(len, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} equals to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param len      the length of the required String
   */
  public void rightValueEquals(String actual, int len, String expected) {
    toVerifier(actual).verifyRightValueEquals(len, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} NOT equals to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param len      the length of the required String
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void rightValueNotEquals(String actual, int len, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyRightValueNotEquals(len, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} NOT equals to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param len      the length of the required String
   */
  public void rightValueNotEquals(String actual, int len, String expected) {
    toVerifier(actual).verifyRightValueNotEquals(len, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is true
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void startsWith(String actual, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyStartsWith(expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is true
   *
   * @param actual   value to compare
   * @param expected the expected result.
   */
  public void startsWith(String actual, String expected) {
    toVerifier(actual).verifyStartsWith(expected);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is true
   *
   * @param actual       value to compare
   * @param searchInputs the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                     null}
   * @param message      information about the purpose of this verification.
   * @param params       parameters in case if message is a format {@link String#format}
   */
  public void startsWithAny(String actual, List<String> searchInputs, final String message, final Object... params) {
    toVerifier(actual).verifyStartsWithAny(searchInputs, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is true
   *
   * @param actual       value to compare
   * @param searchInputs the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                     null}
   */
  public void startsWithAny(String actual, List<String> searchInputs) {
    toVerifier(actual).verifyStartsWithAny(searchInputs);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * true
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void startsWithIgnoreCase(String actual, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyStartsWithIgnoreCase(expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * true
   *
   * @param actual   value to compare
   * @param expected the expected result.
   */
  public void startsWithIgnoreCase(String actual, String expected) {
    toVerifier(actual).verifyStartsWithIgnoreCase(expected);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is false
   *
   * @param actual       value to compare
   * @param searchInputs the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                     null}
   * @param message      information about the purpose of this verification.
   * @param params       parameters in case if message is a format {@link String#format}
   */
  public void startsWithNone(String actual, List<String> searchInputs, final String message, final Object... params) {
    toVerifier(actual).verifyStartsWithNone(searchInputs, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is false
   *
   * @param actual       value to compare
   * @param searchInputs the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                     null}
   */
  public void startsWithNone(String actual, List<String> searchInputs) {
    toVerifier(actual).verifyStartsWithNone(searchInputs);
  }

  /**
   * Verify if {@code stripEnd(String)} value equals the expected value.
   *
   * @param actual     value to compare
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   * @param message    information about the purpose of this verification.
   * @param params     parameters in case if message is a format {@link String#format}
   */
  public void stripedEndValue(String actual, String stripChars, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyStripedEndValue(stripChars, expected, message, params);
  }

  /**
   * Verify if {@code stripEnd(String)} value equals the expected value.
   *
   * @param actual     value to compare
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   */
  public void stripedEndValue(String actual, String stripChars, String expected) {
    toVerifier(actual).verifyStripedEndValue(stripChars, expected);
  }

  /**
   * Verify if {@code stripEnd(String)} value NOT equals the expected value.
   *
   * @param actual     value to compare
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   * @param message    information about the purpose of this verification.
   * @param params     parameters in case if message is a format {@link String#format}
   */
  public void stripedEndValueNot(String actual, String stripChars, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyStripedEndValueNot(stripChars, expected, message, params);
  }

  /**
   * Verify if {@code stripEnd(String)} value NOT equals the expected value.
   *
   * @param actual     value to compare
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   */
  public void stripedEndValueNot(String actual, String stripChars, String expected) {
    toVerifier(actual).verifyStripedEndValueNot(stripChars, expected);
  }

  /**
   * Verify if {@code stripStart(String)} value equals the expected value.
   *
   * @param actual     value to compare
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   * @param message    information about the purpose of this verification.
   * @param params     parameters in case if message is a format {@link String#format}
   */
  public void stripedStartValue(String actual, String stripChars, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyStripedStartValue(stripChars, expected, message, params);
  }

  /**
   * Verify if {@code stripStart(String)} value equals the expected value.
   *
   * @param actual     value to compare
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   */
  public void stripedStartValue(String actual, String stripChars, String expected) {
    toVerifier(actual).verifyStripedStartValue(stripChars, expected);
  }

  /**
   * Verify if {@code stripStart(String)} value NOT equals the expected value.
   *
   * @param actual     value to compare
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   * @param message    information about the purpose of this verification.
   * @param params     parameters in case if message is a format {@link String#format}
   */
  public void stripedStartValueNot(String actual, String stripChars, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyStripedStartValueNot(stripChars, expected, message, params);
  }

  /**
   * Verify if {@code stripStart(String)} value NOT equals the expected value.
   *
   * @param actual     value to compare
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   */
  public void stripedStartValueNot(String actual, String stripChars, String expected) {
    toVerifier(actual).verifyStripedStartValueNot(stripChars, expected);
  }

  /**
   * Verify if {@code strip(String)} value equals the expected value.
   *
   * @param actual     value to compare
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   * @param message    information about the purpose of this verification.
   * @param params     parameters in case if message is a format {@link String#format}
   */
  public void stripedValue(String actual, String stripChars, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyStripedValue(stripChars, expected, message, params);
  }

  /**
   * Verify if {@code strip(String)} value equals the expected value.
   *
   * @param actual     value to compare
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   */
  public void stripedValue(String actual, String stripChars, String expected) {
    toVerifier(actual).verifyStripedValue(stripChars, expected);
  }

  /**
   * Verify if {@code strip(String)} value NOT equals the expected value.
   *
   * @param actual     value to compare
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   * @param message    information about the purpose of this verification.
   * @param params     parameters in case if message is a format {@link String#format}
   */
  public void stripedValueNot(String actual, String stripChars, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyStripedValueNot(stripChars, expected, message, params);
  }

  /**
   * Verify if {@code strip(String)} value NOT equals the expected value.
   *
   * @param actual     value to compare
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   */
  public void stripedValueNot(String actual, String stripChars, String expected) {
    toVerifier(actual).verifyStripedValueNot(stripChars, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} equals to expected
   * value.
   *
   * @param actual    value to compare
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @param message   information about the purpose of this verification.
   * @param params    parameters in case if message is a format {@link String#format}
   */
  public void substringAfterEquals(String actual, String separator, String expected, final String message, final Object... params) {
    toVerifier(actual).verifySubstringAfterEquals(separator, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} equals to expected
   * value.
   *
   * @param actual    value to compare
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   */
  public void substringAfterEquals(String actual, String separator, String expected) {
    toVerifier(actual).verifySubstringAfterEquals(separator, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} equals to expected
   * value.
   *
   * @param actual    value to compare
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @param message   information about the purpose of this verification.
   * @param params    parameters in case if message is a format {@link String#format}
   */
  public void substringAfterLastEquals(String actual, String separator, String expected, final String message, final Object... params) {
    toVerifier(actual).verifySubstringAfterLastEquals(separator, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} equals to expected
   * value.
   *
   * @param actual    value to compare
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   */
  public void substringAfterLastEquals(String actual, String separator, String expected) {
    toVerifier(actual).verifySubstringAfterLastEquals(separator, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} NOT equals to
   * expected value.
   *
   * @param actual    value to compare
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @param message   information about the purpose of this verification.
   * @param params    parameters in case if message is a format {@link String#format}
   */
  public void substringAfterLastNotEquals(String actual, String separator, String expected, final String message, final Object... params) {
    toVerifier(actual).verifySubstringAfterLastNotEquals(separator, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} NOT equals to
   * expected value.
   *
   * @param actual    value to compare
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   */
  public void substringAfterLastNotEquals(String actual, String separator, String expected) {
    toVerifier(actual).verifySubstringAfterLastNotEquals(separator, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} NOT equals to expected
   * value.
   *
   * @param actual    value to compare
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @param message   information about the purpose of this verification.
   * @param params    parameters in case if message is a format {@link String#format}
   */
  public void substringAfterNotEquals(String actual, String separator, String expected, final String message, final Object... params) {
    toVerifier(actual).verifySubstringAfterNotEquals(separator, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} NOT equals to expected
   * value.
   *
   * @param actual    value to compare
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   */
  public void substringAfterNotEquals(String actual, String separator, String expected) {
    toVerifier(actual).verifySubstringAfterNotEquals(separator, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} equals to expected
   * value.
   *
   * @param actual    value to compare
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @param message   information about the purpose of this verification.
   * @param params    parameters in case if message is a format {@link String#format}
   */
  public void substringBeforeEquals(String actual, String separator, String expected, final String message, final Object... params) {
    toVerifier(actual).verifySubstringBeforeEquals(separator, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} equals to expected
   * value.
   *
   * @param actual    value to compare
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   */
  public void substringBeforeEquals(String actual, String separator, String expected) {
    toVerifier(actual).verifySubstringBeforeEquals(separator, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} equals to expected
   * value.
   *
   * @param actual    value to compare
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @param message   information about the purpose of this verification.
   * @param params    parameters in case if message is a format {@link String#format}
   */
  public void substringBeforeLastEquals(String actual, String separator, String expected, final String message, final Object... params) {
    toVerifier(actual).verifySubstringBeforeLastEquals(separator, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} equals to expected
   * value.
   *
   * @param actual    value to compare
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   */
  public void substringBeforeLastEquals(String actual, String separator, String expected) {
    toVerifier(actual).verifySubstringBeforeLastEquals(separator, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} NOT equals to
   * expected value.
   *
   * @param actual    value to compare
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @param message   information about the purpose of this verification.
   * @param params    parameters in case if message is a format {@link String#format}
   */
  public void substringBeforeLastNotEquals(String actual, String separator, String expected, final String message, final Object... params) {
    toVerifier(actual).verifySubstringBeforeLastNotEquals(separator, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} NOT equals to
   * expected value.
   *
   * @param actual    value to compare
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   */
  public void substringBeforeLastNotEquals(String actual, String separator, String expected) {
    toVerifier(actual).verifySubstringBeforeLastNotEquals(separator, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} NOT equals to expected
   * value.
   *
   * @param actual    value to compare
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @param message   information about the purpose of this verification.
   * @param params    parameters in case if message is a format {@link String#format}
   */
  public void substringBeforeNotEquals(String actual, String separator, String expected, final String message, final Object... params) {
    toVerifier(actual).verifySubstringBeforeNotEquals(separator, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} NOT equals to expected
   * value.
   *
   * @param actual    value to compare
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   */
  public void substringBeforeNotEquals(String actual, String separator, String expected) {
    toVerifier(actual).verifySubstringBeforeNotEquals(separator, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String, String)} equals to
   * expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void substringBetweenEquals(String actual, String open, String close, String expected, final String message, final Object... params) {
    toVerifier(actual).verifySubstringBetweenEquals(open, close, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String, String)} equals to
   * expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   */
  public void substringBetweenEquals(String actual, String open, String close, String expected) {
    toVerifier(actual).verifySubstringBetweenEquals(open, close, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String, String)} NOT equals to
   * expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void substringBetweenNotEquals(String actual, String open, String close, String expected, final String message, final Object... params) {
    toVerifier(actual).verifySubstringBetweenNotEquals(open, close, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String, String)} NOT equals to
   * expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   */
  public void substringBetweenNotEquals(String actual, String open, String close, String expected) {
    toVerifier(actual).verifySubstringBetweenNotEquals(open, close, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} equals to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void substringEquals(String actual, int start, String expected, final String message, final Object... params) {
    toVerifier(actual).verifySubstringEquals(start, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} equals to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   */
  public void substringEquals(String actual, int start, String expected) {
    toVerifier(actual).verifySubstringEquals(start, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} equals to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   * @param end      the position to end at (exclusive), negative means count back from the end of the
   *                 String
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void substringEquals(String actual, int start, int end, String expected, final String message, final Object... params) {
    toVerifier(actual).verifySubstringEquals(start, end, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} equals to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   * @param end      the position to end at (exclusive), negative means count back from the end of the
   *                 String
   */
  public void substringEquals(String actual, int start, int end, String expected) {
    toVerifier(actual).verifySubstringEquals(start, end, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} NOT equals to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void substringNotEquals(String actual, int start, String expected, final String message, final Object... params) {
    toVerifier(actual).verifySubstringNotEquals(start, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} NOT equals to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   */
  public void substringNotEquals(String actual, int start, String expected) {
    toVerifier(actual).verifySubstringNotEquals(start, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} NOT equals to expected
   * value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   * @param end      the position to end at (exclusive), negative means count back from the end of the
   *                 String
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void substringNotEquals(String actual, int start, int end, String expected, final String message, final Object... params) {
    toVerifier(actual).verifySubstringNotEquals(start, end, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} NOT equals to expected
   * value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   * @param end      the position to end at (exclusive), negative means count back from the end of the
   *                 String
   */
  public void substringNotEquals(String actual, int start, int end, String expected) {
    toVerifier(actual).verifySubstringNotEquals(start, end, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} contains to
   * expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void substringsBetweenContains(String actual, String open, String close, String expected, final String message, final Object... params) {
    toVerifier(actual).verifySubstringsBetweenContains(open, close, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} contains to
   * expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   */
  public void substringsBetweenContains(String actual, String open, String close, String expected) {
    toVerifier(actual).verifySubstringsBetweenContains(open, close, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} equals to
   * expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void substringsBetweenEquals(String actual, String open, String close, List<String> expected, final String message, final Object... params) {
    toVerifier(actual).verifySubstringsBetweenEquals(open, close, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} equals to
   * expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   */
  public void substringsBetweenEquals(String actual, String open, String close, List<String> expected) {
    toVerifier(actual).verifySubstringsBetweenEquals(open, close, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT contains
   * to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void substringsBetweenNotContains(String actual, String open, String close, String expected, final String message, final Object... params) {
    toVerifier(actual).verifySubstringsBetweenNotContains(open, close, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT contains
   * to expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   */
  public void substringsBetweenNotContains(String actual, String open, String close, String expected) {
    toVerifier(actual).verifySubstringsBetweenNotContains(open, close, expected);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT equals to
   * expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void substringsBetweenNotEquals(String actual, String open, String close, List<String> expected, final String message, final Object... params) {
    toVerifier(actual).verifySubstringsBetweenNotEquals(open, close, expected, message, params);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT equals to
   * expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   */
  public void substringsBetweenNotEquals(String actual, String open, String close, List<String> expected) {
    toVerifier(actual).verifySubstringsBetweenNotEquals(open, close, expected);
  }

  /**
   * Verify if {@code trim()} value equals the expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void trimmedValue(String actual, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyTrimmedValueEquals(expected, message, params);
  }

  /**
   * Verify if {@code trim()} value equals the expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   */
  public void trimmedValue(String actual, String expected) {
    toVerifier(actual).verifyTrimmedValueEquals(expected);
  }

  /**
   * Verify if {@code trim()} value NOT equals the expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void trimmedValueNot(String actual, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyTrimmedValueNotEquals(expected, message, params);
  }

  /**
   * Verify if {@code trim()} value NOT equals the expected value.
   *
   * @param actual   value to compare
   * @param expected the expected result.
   */
  public void trimmedValueNot(String actual, String expected) {
    toVerifier(actual).verifyTrimmedValueNotEquals(expected);
  }

  /**
   * Verify if {@code truncate(Int)} value equals the expected value.
   *
   * @param actual   value to compare
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void truncatedValue(String actual, int maxWidth, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyTruncatedValueEquals(maxWidth, expected, message, params);
  }

  /**
   * Verify if {@code truncate(Int)} value equals the expected value.
   *
   * @param actual   value to compare
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   */
  public void truncatedValue(String actual, int maxWidth, String expected) {
    toVerifier(actual).verifyTruncatedValueEquals(maxWidth, expected);
  }

  /**
   * Verify if {@code truncate(Int, Int)} value equals the expected value.
   *
   * @param actual   value to compare
   * @param offset   left edge of string to start truncate from
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void truncatedValue(String actual, int offset, int maxWidth, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyTruncatedValueEquals(offset, maxWidth, expected, message, params);
  }

  /**
   * Verify if {@code truncate(Int, Int)} value equals the expected value.
   *
   * @param actual   value to compare
   * @param offset   left edge of string to start truncate from
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   */
  public void truncatedValue(String actual, int offset, int maxWidth, String expected) {
    toVerifier(actual).verifyTruncatedValueEquals(offset, maxWidth, expected);
  }

  /**
   * Verify if {@code truncate(Int)} value equals the expected value.
   *
   * @param actual   value to compare
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void truncatedValueNot(String actual, int maxWidth, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyTruncatedValueNotEquals(maxWidth, expected, message, params);
  }

  /**
   * Verify if {@code truncate(Int)} value equals the expected value.
   *
   * @param actual   value to compare
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   */
  public void truncatedValueNot(String actual, int maxWidth, String expected) {
    toVerifier(actual).verifyTruncatedValueNotEquals(maxWidth, expected);
  }

  /**
   * Verify if {@code truncate(Int, Int)} value NOT equals the expected value.
   *
   * @param actual   value to compare
   * @param offset   left edge of string to start truncate from
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   * @param message  information about the purpose of this verification.
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void truncatedValueNot(String actual, int offset, int maxWidth, String expected, final String message, final Object... params) {
    toVerifier(actual).verifyTruncatedValueNotEquals(offset, maxWidth, expected, message, params);
  }

  /**
   * Verify if {@code truncate(Int, Int)} value NOT equals the expected value.
   *
   * @param actual   value to compare
   * @param offset   left edge of string to start truncate from
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   */
  public void truncatedValueNot(String actual, int offset, int maxWidth, String expected) {
    toVerifier(actual).verifyTruncatedValueNotEquals(offset, maxWidth, expected);
  }

  protected CStringVerify toVerifier(String actual) {
    return () -> actual;
  }
}
