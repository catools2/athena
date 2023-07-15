package org.catools.common.extensions.wait;

import org.catools.common.extensions.wait.interfaces.CStringWaiter;
import org.catools.common.utils.CStringUtil;

import java.util.List;
import java.util.regex.Pattern;

/**
 * String wait class contains all wait method which is related to String
 */
public class CStringWait extends CObjectWait {

  /**
   * Verify if String value is empty
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsEmpty(
      String actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsEmpty(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify String value is not empty
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsNotEmpty(
      String actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsNotEmpty(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if String value is blank (Null or Empty)
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsBlank(
      String actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsBlank(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if String value is not blank (Null or Empty)
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsNotBlank(
      String actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsNotBlank(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if {@code trim()} value equals the expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitTrimmedValueEquals(
      String actual, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitTrimmedValueEquals(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if {@code trim()} value NOT equals the expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitTrimmedValueNotEquals(
      String actual, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitTrimmedValueNotEquals(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if {@code truncate(Int)} value equals the expected value.
   *
   * @param actual                 value to compare
   * @param maxWidth               maximum length of truncated string, must be positive
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitTruncatedValueEquals(
      String actual,
      int maxWidth,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitTruncatedValueEquals(maxWidth, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if {@code truncate(Int)} value equals the expected value.
   *
   * @param actual                 value to compare
   * @param maxWidth               maximum length of truncated string, must be positive
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitTruncatedValueNot(
      String actual,
      int maxWidth,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitTruncatedValueNotEquals(maxWidth, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if {@code truncate(Int, Int)} value equals the expected value.
   *
   * @param actual                 value to compare
   * @param offset                 left edge of string to start truncate from
   * @param maxWidth               maximum length of truncated string, must be positive
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitTruncatedValueEquals(
      String actual,
      int offset,
      int maxWidth,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitTruncatedValueEquals(
            offset, maxWidth, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if {@code truncate(Int, Int)} value NOT equals the expected value.
   *
   * @param actual                 value to compare
   * @param offset                 left edge of string to start truncate from
   * @param maxWidth               maximum length of truncated string, must be positive
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitTruncatedValueNot(
      String actual,
      int offset,
      int maxWidth,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitTruncatedValueNotEquals(
            offset, maxWidth, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if {@code strip(String)} value equals the expected value.
   *
   * @param actual                 value to compare
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitStripedValue(
      String actual,
      String stripChars,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitStripedValue(stripChars, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if {@code strip(String)} value NOT equals the expected value.
   *
   * @param actual                 value to compare
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitStripedValueNot(
      String actual,
      String stripChars,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitStripedValueNot(stripChars, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if {@code stripStart(String)} value equals the expected value.
   *
   * @param actual                 value to compare
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitStripedStartValue(
      String actual,
      String stripChars,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitStripedStartValue(stripChars, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if {@code stripStart(String)} value NOT equals the expected value.
   *
   * @param actual                 value to compare
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitStripedStartValueNot(
      String actual,
      String stripChars,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitStripedStartValueNot(stripChars, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if {@code stripEnd(String)} value equals the expected value.
   *
   * @param actual                 value to compare
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitStripedEndValue(
      String actual,
      String stripChars,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitStripedEndValue(stripChars, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if {@code stripEnd(String)} value NOT equals the expected value.
   *
   * @param actual                 value to compare
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitStripedEndValueNot(
      String actual,
      String stripChars,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitStripedEndValueNot(stripChars, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if {@code equals(String)} value equals the expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEquals(
      String actual, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitEquals(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if {@code equals(String)} value NOT equals the expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotEquals(
      String actual, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitNotEquals(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if {@code equalsIgnoreCase(String)} value equals the expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEqualsIgnoreCase(
      String actual, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitEqualsIgnoreCase(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if {@code equalsIgnoreCase(String)} value NOT equals the expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotEqualsIgnoreCase(
      String actual, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitNotEqualsIgnoreCase(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if {@code equalsIgnoreWhiteSpace(String)} value equals the expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEqualsIgnoreWhiteSpaces(
      String actual, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitEqualsIgnoreWhiteSpaces(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if {@code equalsIgnoreWhiteSpace(String)} value NOT equals the expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotEqualsIgnoreWhiteSpaces(
      String actual, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitNotEqualsIgnoreWhiteSpaces(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#compare(String, String)} equals to the expected value.
   *
   * @param actual                 value to compare
   * @param stringToCompare        the string value to compare against
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitCompare(
      String actual,
      String stringToCompare,
      int expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitCompare(stringToCompare, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#compareIgnoreCase(String, String)} equals to the
   * expected value.
   *
   * @param actual                 value to compare
   * @param stringToCompare        the string value to compare against
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitCompareIgnoreCase(
      String actual,
      String stringToCompare,
      int expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitCompareIgnoreCase(stringToCompare, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAny(CharSequence, CharSequence...)} equals to the
   * expected value.
   *
   * @param actual                 value to compare
   * @param expectedList           a list of strings, may be {@code null}.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEqualsAny(
      String actual,
      List<String> expectedList,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitEqualsAny(expectedList, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAny(CharSequence, CharSequence...)} is false
   *
   * @param actual                 value to compare
   * @param expectedList           a list of strings, may be {@code null}.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEqualsNone(
      String actual,
      List<String> expectedList,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitEqualsNone(expectedList, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)} is
   * true.
   *
   * @param actual                 value to compare
   * @param expectedList           a list of strings, may be {@code null}.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEqualsAnyIgnoreCase(
      String actual,
      List<String> expectedList,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitEqualsAnyIgnoreCase(expectedList, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)} is
   * false.
   *
   * @param actual                 value to compare
   * @param expectedList           a list of strings, may be {@code null}.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEqualsNoneIgnoreCase(
      String actual,
      List<String> expectedList,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitEqualsNoneIgnoreCase(expectedList, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is true.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitContains(
      String actual, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitContains(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#contains(CharSequence, CharSequence)} is false.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotContains(
      String actual, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitNotContains(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is true.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitContainsIgnoreCase(
      String actual, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitContainsIgnoreCase(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotContainsIgnoreCase(
      String actual, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitNotContainsIgnoreCase(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} equals to expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param start                  the position to start from, negative means count back from the end of the String
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitSubstringEquals(
      String actual,
      int start,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitSubstringEquals(start, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int)} NOT equals to expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param start                  the position to start from, negative means count back from the end of the String
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitSubstringNotEquals(
      String actual,
      int start,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitSubstringNotEquals(start, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} equals to expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param start                  the position to start from, negative means count back from the end of the String
   * @param end                    the position to end at (exclusive), negative means count back from the end of the
   *                               String
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitSubstringEquals(
      String actual,
      int start,
      int end,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitSubstringEquals(start, end, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#substring(String, int, int)} NOT equals to expected
   * value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param start                  the position to start from, negative means count back from the end of the String
   * @param end                    the position to end at (exclusive), negative means count back from the end of the
   *                               String
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitSubstringNotEquals(
      String actual,
      int start,
      int end,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitSubstringNotEquals(start, end, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} equals to expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitLeftValueEquals(
      String actual,
      int len,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitLeftValueEquals(len, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#left(String, int)} NOT equals to expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitLeftValueNotEquals(
      String actual,
      int len,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitLeftValueNotEquals(len, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} equals to expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitRightValueEquals(
      String actual,
      int len,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitRightValueEquals(len, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#right(String, int)} NOT equals to expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitRightValueNotEquals(
      String actual,
      int len,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitRightValueNotEquals(len, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} equals to expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param pos                    the position to start from, negative treated as zero
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitMidValueEquals(
      String actual,
      int pos,
      int len,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitMidValueEquals(pos, len, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#mid(String, int, int)} NOT equals to expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param pos                    the position to start from, negative treated as zero
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitMidValueNotEquals(
      String actual,
      int pos,
      int len,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitMidValueNotEquals(pos, len, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} equals to expected
   * value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitSubstringBeforeEquals(
      String actual,
      String separator,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitSubstringBeforeEquals(separator, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBefore(String, String)} NOT equals to expected
   * value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitSubstringBeforeNotEquals(
      String actual,
      String separator,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitSubstringBeforeNotEquals(separator, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} equals to expected
   * value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitSubstringAfterEquals(
      String actual,
      String separator,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitSubstringAfterEquals(separator, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfter(String, String)} NOT equals to expected
   * value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitSubstringAfterNotEquals(
      String actual,
      String separator,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitSubstringAfterNotEquals(separator, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} equals to expected
   * value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitSubstringBeforeLastEquals(
      String actual,
      String separator,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitSubstringBeforeLastEquals(separator, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBeforeLast(String, String)} NOT equals to
   * expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitSubstringBeforeLastNotEquals(
      String actual,
      String separator,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitSubstringBeforeLastNotEquals(
            separator, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} equals to expected
   * value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitSubstringAfterLastEquals(
      String actual,
      String separator,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitSubstringAfterLastEquals(separator, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#substringAfterLast(String, String)} NOT equals to
   * expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitSubstringAfterLastNotEquals(
      String actual,
      String separator,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitSubstringAfterLastNotEquals(
            separator, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String, String)} equals to
   * expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitSubstringBetweenEquals(
      String actual,
      String open,
      String close,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitSubstringBetweenEquals(open, close, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#substringBetween(String, String, String)} NOT equals to
   * expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitSubstringBetweenNotEquals(
      String actual,
      String open,
      String close,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitSubstringBetweenNotEquals(
            open, close, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} equals to
   * expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitSubstringsBetweenEquals(
      String actual,
      String open,
      String close,
      List<String> expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitSubstringsBetweenEquals(open, close, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT equals to
   * expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitSubstringsBetweenNotEquals(
      String actual,
      String open,
      String close,
      List<String> expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitSubstringsBetweenNotEquals(
            open, close, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} contains to
   * expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitSubstringsBetweenContains(
      String actual,
      String open,
      String close,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitSubstringsBetweenContains(
            open, close, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#substringsBetween(String, String, String)} NOT contains
   * to expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitSubstringsBetweenNotContains(
      String actual,
      String open,
      String close,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitSubstringsBetweenNotContains(
            open, close, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is true
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitStartsWith(
      String actual, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitStartsWith(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * true
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitStartsWithIgnoreCase(
      String actual, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitStartsWithIgnoreCase(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is true
   *
   * @param actual                 value to compare
   * @param searchInputs           the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                               null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitStartsWithAny(
      String actual,
      List<String> searchInputs,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitStartsWithAny(searchInputs, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWith(CharSequence, CharSequence)} is false
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotStartsWith(
      String actual, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitNotStartsWith(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is
   * false
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotStartsWithIgnoreCase(
      String actual, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitNotStartsWithIgnoreCase(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#startsWithAny(CharSequence, CharSequence...)} is false
   *
   * @param actual                 value to compare
   * @param searchInputs           the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                               null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitStartsWithNone(
      String actual,
      List<String> searchInputs,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitStartsWithNone(searchInputs, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is true
   *
   * @param actual                 value to compare
   * @param suffix                 the suffix to find, may be {@code null}
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEndsWith(
      String actual, String suffix, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitEndsWith(suffix, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is true.
   *
   * @param actual                 value to compare
   * @param suffix                 the suffix to find, may be {@code null}
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEndsWithIgnoreCase(
      String actual, String suffix, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitEndsWithIgnoreCase(suffix, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is true.
   *
   * @param actual                 value to compare
   * @param searchInputs           the case-sensitive CharSequences to find, may be empty or contain {@code
   *                               null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEndsWithAny(
      String actual,
      List<String> searchInputs,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitEndsWithAny(searchInputs, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is false
   *
   * @param actual                 value to compare
   * @param suffix                 the suffix to find, may be {@code null}
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotEndsWith(
      String actual, String suffix, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitNotEndsWith(suffix, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is
   * false.
   *
   * @param actual                 value to compare
   * @param suffix                 the suffix to find, may be {@code null}
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotEndsWithIgnoreCase(
      String actual, String suffix, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitNotEndsWithIgnoreCase(suffix, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is false.
   *
   * @param actual                 value to compare
   * @param searchInputs           the case-sensitive CharSequences to find, may be empty or contain {@code
   *                               null}
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEndsWithNone(
      String actual,
      List<String> searchInputs,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitEndsWithNone(searchInputs, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is equals to expected
   * value.
   *
   * @param actual                 value to compare
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitRemoveStartEquals(
      String actual,
      String remove,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitRemoveStartEquals(remove, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStart(String, String)} is NOT equals to expected
   * value.
   *
   * @param actual                 value to compare
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitRemoveStartNotEquals(
      String actual,
      String remove,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitRemoveStartNotEquals(remove, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param actual                 value to compare
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitRemoveStartIgnoreCaseEquals(
      String actual,
      String remove,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitRemoveStartIgnoreCaseEquals(remove, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#removeStartIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param actual                 value to compare
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitRemoveStartIgnoreCaseNotEquals(
      String actual,
      String remove,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitRemoveStartIgnoreCaseNotEquals(
            remove, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is equals to expected value.
   *
   * @param actual                 value to compare
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitRemoveEndEquals(
      String actual,
      String remove,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitRemoveEndEquals(remove, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEnd(String, String)} is NOT equals to expected
   * value.
   *
   * @param actual                 value to compare
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitRemoveEndNotEquals(
      String actual,
      String remove,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitRemoveEndNotEquals(remove, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is equals to
   * expected value.
   *
   * @param actual                 value to compare
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitRemoveEndIgnoreCaseEquals(
      String actual,
      String remove,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitRemoveEndIgnoreCaseEquals(remove, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#removeEndIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param actual                 value to compare
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitRemoveEndIgnoreCaseNotEquals(
      String actual,
      String remove,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitRemoveEndIgnoreCaseNotEquals(remove, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is equals to expected value.
   *
   * @param actual                 value to compare
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitRemoveEquals(
      String actual,
      String remove,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitRemoveEquals(remove, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#remove(String, String)} is NOT equals to expected value.
   *
   * @param actual                 value to compare
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitRemoveNotEquals(
      String actual,
      String remove,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitRemoveNotEquals(remove, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is equals to expected
   * value.
   *
   * @param actual                 value to compare
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitRemoveIgnoreCaseEquals(
      String actual,
      String remove,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitRemoveIgnoreCaseEquals(remove, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#removeIgnoreCase(String, String)} is NOT equals to
   * expected value.
   *
   * @param actual                 value to compare
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitRemoveIgnoreCaseNotEquals(
      String actual,
      String remove,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitRemoveIgnoreCaseNotEquals(remove, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is equals to
   * expected value.
   *
   * @param actual                 value to compare
   * @param searchString           the String to search for, may be null
   * @param replacement            the String to replace with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitReplaceOnceEquals(
      String actual,
      String searchString,
      String replacement,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitReplaceOnceEquals(
            searchString, replacement, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnce(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param actual                 value to compare
   * @param searchString           the String to search for, may be null
   * @param replacement            the String to replace with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitReplaceOnceNotEquals(
      String actual,
      String searchString,
      String replacement,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitReplaceOnceNotEquals(
            searchString, replacement, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is equals
   * to expected value.
   *
   * @param actual                 value to compare
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitReplaceOnceIgnoreCaseEquals(
      String actual,
      String searchString,
      String replacement,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitReplaceOnceIgnoreCaseEquals(
            searchString, replacement, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceOnceIgnoreCase(String, String, String)} is NOT
   * equals to expected value.
   *
   * @param actual                 value to compare
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitReplaceOnceIgnoreCaseNotEquals(
      String actual,
      String searchString,
      String replacement,
      String expected,
      final int waitInSeconds,
      int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitReplaceOnceIgnoreCaseNotEquals(
            searchString, replacement, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is equals to expected
   * value.
   *
   * @param actual                 value to compare
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace it with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitReplaceEquals(
      String actual,
      String searchString,
      String replacement,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitReplaceEquals(
            searchString, replacement, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#replace(String, String, String)} is NOT equals to
   * expected value.
   *
   * @param actual                 value to compare
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace it with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitReplaceNotEquals(
      String actual,
      String searchString,
      String replacement,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitReplaceNotEquals(
            searchString, replacement, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is equals to
   * expected value.
   *
   * @param actual                 value to compare
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace it with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitReplaceIgnoreCaseEquals(
      String actual,
      String searchString,
      String replacement,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitReplaceIgnoreCaseEquals(
            searchString, replacement, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#replaceIgnoreCase(String, String, String)} is NOT equals
   * to expected value.
   *
   * @param actual                 value to compare
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace it with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitReplaceIgnoreCaseNotEquals(
      String actual,
      String searchString,
      String replacement,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitReplaceIgnoreCaseNotEquals(
            searchString, replacement, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is equals to expected
   * value.
   *
   * @param actual                 value to compare
   * @param size                   the size to pad to
   * @param padStr                 the String to pad with, null or empty treated as single space
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitRightPadEquals(
      String actual,
      int size,
      String padStr,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitRightPadEquals(size, padStr, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#rightPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param actual                 value to compare
   * @param size                   the size to pad to
   * @param padStr                 the String to pad with, null or empty treated as single space
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitRightPadNotEquals(
      String actual,
      int size,
      String padStr,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitRightPadNotEquals(size, padStr, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is equals to expected
   * value.
   *
   * @param actual                 value to compare
   * @param size                   the size to pad to
   * @param padStr                 the String to pad with, null or empty treated as single space
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitLeftPadEquals(
      String actual,
      int size,
      String padStr,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitLeftPadEquals(size, padStr, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#leftPad(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param actual                 value to compare
   * @param size                   the size to pad to
   * @param padStr                 the String to pad with, null or empty treated as single space
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitLeftPadNotEquals(
      String actual,
      int size,
      String padStr,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitLeftPadNotEquals(size, padStr, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is equals to expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitLengthEquals(
      String actual, int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitLengthEquals(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#length(CharSequence)} is NOT equals to expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitLengthNotEquals(
      String actual, int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitLengthNotEquals(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is equals to expected
   * value.
   *
   * @param actual                 value to compare
   * @param size                   the int size of new String, negative treated as zero
   * @param padStr                 the String to pad the new String with, must not be null or empty
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitCenterPadEquals(
      String actual,
      int size,
      String padStr,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitCenterPadEquals(size, padStr, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#center(String, int, String)} is NOT equals to expected
   * value.
   *
   * @param actual                 value to compare
   * @param size                   the int size of new String, negative treated as zero
   * @param padStr                 the String to pad the new String with, must not be null or empty
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitCenterPadNotEquals(
      String actual,
      int size,
      String padStr,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitCenterPadNotEquals(size, padStr, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is equals to
   * expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param subString              the substring to count, may be null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNumberOfMatchesEquals(
      String actual,
      String subString,
      int expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitNumberOfMatchesEquals(subString, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#countMatches(CharSequence, CharSequence)} is NOT equals
   * to expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param subString              the substring to count, may be null
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNumberOfMatchesNotEquals(
      String actual,
      String subString,
      int expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual)
        .waitNumberOfMatchesNotEquals(subString, expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is true.
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsAlpha(
      String actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsAlpha(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlpha(CharSequence)} is false.
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsNotAlpha(
      String actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsNotAlpha(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is true.
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsAlphaSpace(
      String actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsAlphaSpace(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphaSpace(CharSequence)} is false.
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsNotAlphaSpace(
      String actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsNotAlphaSpace(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsEmptyOrAlpha(
      String actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsEmptyOrAlpha(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsEmptyOrNotAlpha(
      String actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsEmptyOrNotAlpha(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsAlphanumeric(
      String actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsAlphanumeric(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsNotAlphanumeric(
      String actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsNotAlphanumeric(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is true.
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsAlphanumericSpace(
      String actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsAlphanumericSpace(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is false.
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsNotAlphanumericSpace(
      String actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsNotAlphanumericSpace(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsEmptyOrAlphanumeric(
      String actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsEmptyOrAlphanumeric(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsEmptyOrNotAlphanumeric(
      String actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsEmptyOrNotAlphanumeric(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is true.
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsAsciiPrintable(
      String actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsAsciiPrintable(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is false.
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsNotAsciiPrintable(
      String actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsNotAsciiPrintable(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsNumeric(
      String actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsNumeric(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumeric(CharSequence)} is false.
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsNotNumeric(
      String actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsNotNumeric(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is true.
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsNumericSpace(
      String actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsNumericSpace(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#isNumericSpace(CharSequence)} is false.
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsNotNumericSpace(
      String actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsNotNumericSpace(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * true.
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsEmptyOrNumeric(
      String actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsEmptyOrNumeric(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if string value is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is
   * false.
   *
   * @param actual                 value to compare
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsEmptyOrNotNumeric(
      String actual, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitIsEmptyOrNotNumeric(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is equals to expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitReverseEquals(
      String actual, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitReverseEquals(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if result of {@link CStringUtil#reverse(String)} is NOT equals to expected value.
   *
   * @param actual                 value to compare
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitReverseNotEquals(
      String actual, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actual).waitReverseNotEquals(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param actual                 value to compare
   * @param pattern                regular expression pattern
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitMatches(
      String actual,
      final Pattern pattern,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitMatches(pattern, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param actual        value to compare
   * @param waitInSeconds maximum wait time.
   * @param pattern       regular expression pattern
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotMatches(
      String actual,
      final Pattern pattern,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitNotMatches(pattern, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if String value match provided pattern
   *
   * @param actual                 value to compare
   * @param pattern                regular expression pattern
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitMatches(
      String actual,
      final String pattern,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitMatches(pattern, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if String value does not match provided pattern
   *
   * @param actual        value to compare
   * @param waitInSeconds maximum wait time.
   * @param pattern       regular expression pattern
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotMatches(
      String actual,
      final String pattern,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitNotMatches(pattern, waitInSeconds, intervalInMilliSeconds);
  }

  private static CStringWaiter toWaiter(String actual) {
    return () -> actual;
  }
}
