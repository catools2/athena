package org.catools.common.extensions.wait.interfaces;

import org.catools.common.extensions.states.interfaces.CStringState;
import org.catools.common.utils.CStringUtil;

import java.util.List;
import java.util.regex.Pattern;

/**
 * CStringWaiter is an interface for String waiter related methods.
 */
public interface CStringWaiter extends CObjectWaiter<String> {

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * result of {@link CStringUtil#center(String, int, String)} is equals to expected value.
   *
   * @param size     the int size of new String, negative treated as zero
   * @param padStr   the String to pad the new String with, must not be null or empty
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitCenterPadEquals(int size, String padStr, String expected) {
    return waitCenterPadEquals(
        size, padStr, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#center(String, int,
   * String)} is equals to expected value.
   *
   * @param size          the int size of new String, negative treated as zero
   * @param padStr        the String to pad the new String with, must not be null or empty
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitCenterPadEquals(
      int size, String padStr, String expected, final int waitInSeconds) {
    return waitCenterPadEquals(
        size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#center(String, int,
   * String)} is equals to expected value.
   *
   * @param size                   the int size of new String, negative treated as zero
   * @param padStr                 the String to pad the new String with, must not be null or empty
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitCenterPadEquals(
      int size,
      String padStr,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).centerPadEquals(size, padStr, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * result of {@link CStringUtil#center(String, int, String)} is NOT equals to expected value.
   *
   * @param size     the int size of new String, negative treated as zero
   * @param padStr   the String to pad the new String with, must not be null or empty
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitCenterPadNotEquals(int size, String padStr, String expected) {
    return waitCenterPadNotEquals(
        size, padStr, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#center(String, int,
   * String)} is NOT equals to expected value.
   *
   * @param size          the int size of new String, negative treated as zero
   * @param padStr        the String to pad the new String with, must not be null or empty
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitCenterPadNotEquals(
      int size, String padStr, String expected, final int waitInSeconds) {
    return waitCenterPadNotEquals(
        size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#center(String, int,
   * String)} is NOT equals to expected value.
   *
   * @param size                   the int size of new String, negative treated as zero
   * @param padStr                 the String to pad the new String with, must not be null or empty
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitCenterPadNotEquals(
      int size,
      String padStr,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).centerPadNotEquals(size, padStr, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * result of {@link CStringUtil#compare(String, String)} equals to the expected value.
   *
   * @param stringToCompare the string value to compare against
   * @param expected        the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitCompare(String stringToCompare, int expected) {
    return waitCompare(
        stringToCompare,
        expected,
        getDefaultWaitInSeconds(),
        getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#compare(String, String)}
   * equals to the expected value.
   *
   * @param stringToCompare the string value to compare against
   * @param expected        the expected result.
   * @param waitInSeconds   maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitCompare(String stringToCompare, int expected, final int waitInSeconds) {
    return waitCompare(
        stringToCompare, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#compare(String, String)}
   * equals to the expected value.
   *
   * @param stringToCompare        the string value to compare against
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitCompare(
      String stringToCompare,
      int expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).compare(stringToCompare, expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * result of {@link CStringUtil#compareIgnoreCase(String, String)} equals to the expected value.
   *
   * @param stringToCompare the string value to compare against
   * @param expected        the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitCompareIgnoreCase(String stringToCompare, int expected) {
    return waitCompareIgnoreCase(
        stringToCompare,
        expected,
        getDefaultWaitInSeconds(),
        getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#compareIgnoreCase(String,
   * String)} equals to the expected value.
   *
   * @param stringToCompare the string value to compare against
   * @param expected        the expected result.
   * @param waitInSeconds   maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitCompareIgnoreCase(
      String stringToCompare, int expected, final int waitInSeconds) {
    return waitCompareIgnoreCase(
        stringToCompare, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#compareIgnoreCase(String,
   * String)} equals to the expected value.
   *
   * @param stringToCompare        the string value to compare against
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitCompareIgnoreCase(
      String stringToCompare,
      int expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).compareIgnoreCase(stringToCompare, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * result of {@link CStringUtil#contains(CharSequence, CharSequence)} is true.
   *
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitContains(String expected) {
    return waitContains(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#contains(CharSequence,
   * CharSequence)} is true.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitContains(String expected, final int waitInSeconds) {
    return waitContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#contains(CharSequence,
   * CharSequence)} is true.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitContains(
      String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).contains(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * result of {@link CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is true, ignoring
   * case.
   *
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitContainsIgnoreCase(String expected) {
    return waitContainsIgnoreCase(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is true, ignoring case.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitContainsIgnoreCase(String expected, final int waitInSeconds) {
    return waitContainsIgnoreCase(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is true, ignoring case.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitContainsIgnoreCase(
      String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).containsIgnoreCase(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * result of {@link CStringUtil#endsWith(CharSequence, CharSequence)} is true
   *
   * @param suffix the suffix to find, may be {@code null}
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEndsWith(String suffix) {
    return waitEndsWith(suffix, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#endsWith(CharSequence,
   * CharSequence)} is true
   *
   * @param suffix        the suffix to find, may be {@code null}
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEndsWith(String suffix, final int waitInSeconds) {
    return waitEndsWith(suffix, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#endsWith(CharSequence,
   * CharSequence)} is true
   *
   * @param suffix                 the suffix to find, may be {@code null}
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEndsWith(
      String suffix, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).endsWith(suffix), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is true.
   *
   * @param searchInputs the case-sensitive CharSequences to find, may be empty or contain {@code
   *                     null}
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEndsWithAny(List<String> searchInputs) {
    return waitEndsWithAny(
        searchInputs, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#endsWithAny(CharSequence,
   * CharSequence...)} is true.
   *
   * @param searchInputs  the case-sensitive CharSequences to find, may be empty or contain {@code
   *                      null}
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEndsWithAny(List<String> searchInputs, final int waitInSeconds) {
    return waitEndsWithAny(searchInputs, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#endsWithAny(CharSequence,
   * CharSequence...)} is true.
   *
   * @param searchInputs           the case-sensitive CharSequences to find, may be empty or contain {@code
   *                               null}
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEndsWithAny(
      List<String> searchInputs, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).endsWithAny(searchInputs), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * result of {@link CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is true.
   *
   * @param suffix the suffix to find, may be {@code null}
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEndsWithIgnoreCase(String suffix) {
    return waitEndsWithIgnoreCase(
        suffix, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is true.
   *
   * @param suffix        the suffix to find, may be {@code null}
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEndsWithIgnoreCase(String suffix, final int waitInSeconds) {
    return waitEndsWithIgnoreCase(suffix, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is true.
   *
   * @param suffix                 the suffix to find, may be {@code null}
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEndsWithIgnoreCase(
      String suffix, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).endsWithIgnoreCase(suffix), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * result of {@link CStringUtil#endsWithAny(CharSequence, CharSequence...)} is false.
   *
   * @param searchInputs the case-sensitive CharSequences to find, may be empty or contain {@code
   *                     null}
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEndsWithNone(List<String> searchInputs) {
    return waitEndsWithNone(
        searchInputs, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#endsWithAny(CharSequence,
   * CharSequence...)} is false.
   *
   * @param searchInputs  the case-sensitive CharSequences to find, may be empty or contain {@code
   *                      null}
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEndsWithNone(List<String> searchInputs, final int waitInSeconds) {
    return waitEndsWithNone(searchInputs, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#endsWithAny(CharSequence,
   * CharSequence...)} is false.
   *
   * @param searchInputs           the case-sensitive CharSequences to find, may be empty or contain {@code
   *                               null}
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEndsWithNone(
      List<String> searchInputs, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).endsWithNone(searchInputs), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)} equals to the
   * expected value, ignoring case.
   *
   * @param expectedList a list of strings, may be {@code null}.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsAnyIgnoreCase(List<String> expectedList) {
    return waitEqualsAnyIgnoreCase(
        expectedList, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)} equals to the expected value,
   * ignoring case.
   *
   * @param expectedList  a list of strings, may be {@code null}.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsAnyIgnoreCase(List<String> expectedList, final int waitInSeconds) {
    return waitEqualsAnyIgnoreCase(
        expectedList, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)} equals to the expected value,
   * ignoring case.
   *
   * @param expectedList           a list of strings, may be {@code null}.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsAnyIgnoreCase(
      List<String> expectedList, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).equalsAnyIgnoreCase(expectedList), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * {@link CStringUtil#equalsIgnoreCase(CharSequence, CharSequence)} value equals the expected
   * value.
   *
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsIgnoreCase(String expected) {
    return waitEqualsIgnoreCase(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#equalsIgnoreCase(CharSequence,
   * CharSequence)} value equals the expected value.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsIgnoreCase(String expected, final int waitInSeconds) {
    return waitEqualsIgnoreCase(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#equalsIgnoreCase(CharSequence,
   * CharSequence)} value equals the expected value.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsIgnoreCase(
      String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).equalsIgnoreCase(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * value is equal to expected after removing all WhiteSpaces from both.
   *
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsIgnoreWhiteSpaces(String expected) {
    return waitEqualsIgnoreWhiteSpaces(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till value is equal to expected after removing all
   * WhiteSpaces from both.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsIgnoreWhiteSpaces(String expected, final int waitInSeconds) {
    return waitEqualsIgnoreWhiteSpaces(
        expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till value is equal to expected after removing all
   * WhiteSpaces from both.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsIgnoreWhiteSpaces(
      String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).equalsIgnoreWhiteSpaces(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * result of {@link CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)} is false,
   * ignoring case.
   *
   * @param expectedList a list of strings, may be {@code null}.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsNoneIgnoreCase(List<String> expectedList) {
    return waitEqualsNoneIgnoreCase(
        expectedList, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)} is false, ignoring case.
   *
   * @param expectedList  a list of strings, may be {@code null}.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsNoneIgnoreCase(List<String> expectedList, final int waitInSeconds) {
    return waitEqualsNoneIgnoreCase(
        expectedList, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#equalsAnyIgnoreCase(CharSequence, CharSequence...)} is false, ignoring case.
   *
   * @param expectedList           a list of strings, may be {@code null}.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsNoneIgnoreCase(
      List<String> expectedList, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).equalsNoneIgnoreCase(expectedList), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * result of {@link CStringUtil#isAlpha(CharSequence)} is true.
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsAlpha() {
    return waitIsAlpha(getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsAlpha(final int waitInSeconds) {
    return waitIsAlpha(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#isAlpha(CharSequence)} is
   * true.
   *
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsAlpha(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).isAlpha(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * result of {@link CStringUtil#isAlphaSpace(CharSequence)} is true.
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsAlphaSpace() {
    return waitIsAlphaSpace(getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#isAlphaSpace(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsAlphaSpace(final int waitInSeconds) {
    return waitIsAlphaSpace(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#isAlphaSpace(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsAlphaSpace(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).isAlphaSpace(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * result of {@link CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsAlphanumeric() {
    return waitIsAlphanumeric(getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsAlphanumeric(final int waitInSeconds) {
    return waitIsAlphanumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsAlphanumeric(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).isAlphanumeric(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is true.
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsAlphanumericSpace() {
    return waitIsAlphanumericSpace(
        getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#isAlphanumericSpace(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsAlphanumericSpace(final int waitInSeconds) {
    return waitIsAlphanumericSpace(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#isAlphanumericSpace(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsAlphanumericSpace(
      final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).isAlphanumericSpace(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is true.
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsAsciiPrintable() {
    return waitIsAsciiPrintable(getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#isAsciiPrintable(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsAsciiPrintable(final int waitInSeconds) {
    return waitIsAsciiPrintable(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#isAsciiPrintable(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsAsciiPrintable(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).isAsciiPrintable(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * String value is blank (Null or Empty)
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsBlank() {
    return waitIsBlank(getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till String value is blank (Null or Empty)
   *
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsBlank(final int waitInSeconds) {
    return waitIsBlank(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till String value is blank (Null or Empty)
   *
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsBlank(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).isBlank(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * String value is empty
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmpty() {
    return waitIsEmpty(getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till String value is empty
   *
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmpty(final int waitInSeconds) {
    return waitIsEmpty(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till String value is empty
   *
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmpty(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).isEmpty(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is true.
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmptyOrAlpha() {
    return waitIsEmptyOrAlpha(getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till string value is empty or the result of {@link
   * CStringUtil#isAlpha(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmptyOrAlpha(final int waitInSeconds) {
    return waitIsEmptyOrAlpha(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till string value is empty or the result of {@link
   * CStringUtil#isAlpha(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmptyOrAlpha(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).isEmptyOrAlpha(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * string value is empty or the result of {@link CStringUtil#isAlphanumeric(CharSequence)} is
   * true.
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmptyOrAlphanumeric() {
    return waitIsEmptyOrAlphanumeric(
        getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmptyOrAlphanumeric(final int waitInSeconds) {
    return waitIsEmptyOrAlphanumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmptyOrAlphanumeric(
      final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).isEmptyOrAlphanumeric(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * string value is empty or the result of {@link CStringUtil#isAlpha(CharSequence)} is false.
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmptyOrNotAlpha() {
    return waitIsEmptyOrNotAlpha(getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till string value is empty or the result of {@link
   * CStringUtil#isAlpha(CharSequence)} is false.
   *
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmptyOrNotAlpha(final int waitInSeconds) {
    return waitIsEmptyOrNotAlpha(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till string value is empty or the result of {@link
   * CStringUtil#isAlpha(CharSequence)} is false.
   *
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmptyOrNotAlpha(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).isEmptyOrNotAlpha(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * string value is empty or the result of {@link CStringUtil#isAlphanumeric(CharSequence)} is
   * false.
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmptyOrNotAlphanumeric() {
    return waitIsEmptyOrNotAlphanumeric(
        getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmptyOrNotAlphanumeric(final int waitInSeconds) {
    return waitIsEmptyOrNotAlphanumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till string value is empty or the result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmptyOrNotAlphanumeric(
      final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).isEmptyOrNotAlphanumeric(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * string is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is false.
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmptyOrNotNumeric() {
    return waitIsEmptyOrNotNumeric(
        getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till string is empty or the result of {@link
   * CStringUtil#isNumeric(CharSequence)} is false.
   *
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmptyOrNotNumeric(final int waitInSeconds) {
    return waitIsEmptyOrNotNumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till string is empty or the result of {@link
   * CStringUtil#isNumeric(CharSequence)} is false.
   *
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmptyOrNotNumeric(
      final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).isEmptyOrNotNumeric(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * string is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmptyOrNumeric() {
    return waitIsEmptyOrNumeric(getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till string is empty or the result of {@link
   * CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmptyOrNumeric(final int waitInSeconds) {
    return waitIsEmptyOrNumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till string is empty or the result of {@link
   * CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsEmptyOrNumeric(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).isEmptyOrNumeric(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * String value match provided pattern
   *
   * @param pattern regular expression pattern
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitMatches(final Pattern pattern) {
    return waitMatches(pattern, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till String value match provided pattern
   *
   * @param pattern       regular expression pattern
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitMatches(final Pattern pattern, final int waitInSeconds) {
    return waitMatches(pattern, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till String value match provided pattern
   *
   * @param pattern                regular expression pattern
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitMatches(
      final Pattern pattern, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).matches(pattern), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * String value match provided pattern
   *
   * @param pattern regular expression pattern
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitMatches(final String pattern) {
    return waitMatches(pattern, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till String value match provided pattern
   *
   * @param pattern       regular expression pattern
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitMatches(final String pattern, final int waitInSeconds) {
    return waitMatches(pattern, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till String value match provided pattern
   *
   * @param pattern                regular expression pattern
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitMatches(
      final String pattern, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).matches(pattern), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * String value match one of provided pattern
   *
   * @param patterns regular expression patterns
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitMatchesAny(final List<Pattern> patterns) {
    return waitMatchesAny(
        patterns, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till String value match one of provided pattern
   *
   * @param patterns      regular expression patterns
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitMatchesAny(final List<Pattern> patterns, final int waitInSeconds) {
    return waitMatchesAny(patterns, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till String value match one of provided pattern
   *
   * @param patterns               regular expression patterns
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitMatchesAny(
      final List<Pattern> patterns, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).matchAny(patterns), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * String value match NONE of provided pattern
   *
   * @param patterns regular expression patterns
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitMatchesNone(final List<Pattern> patterns) {
    return waitMatchesNone(
        patterns, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till String value match NONE of provided pattern
   *
   * @param patterns      regular expression patterns
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitMatchesNone(final List<Pattern> patterns, final int waitInSeconds) {
    return waitMatchesNone(patterns, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till String value match NONE of provided pattern
   *
   * @param patterns               regular expression patterns
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitMatchesNone(
      final List<Pattern> patterns, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).matchNone(patterns), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * result of {@link CStringUtil#isAlpha(CharSequence)} is false.
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotAlpha() {
    return waitIsNotAlpha(getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotAlpha(final int waitInSeconds) {
    return waitIsNotAlpha(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#isAlpha(CharSequence)} is
   * false.
   *
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotAlpha(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).isNotAlpha(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * result of {@link CStringUtil#isAlphaSpace(CharSequence)} is false.
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotAlphaSpace() {
    return waitIsNotAlphaSpace(getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#isAlphaSpace(CharSequence)} is false.
   *
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotAlphaSpace(final int waitInSeconds) {
    return waitIsNotAlphaSpace(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#isAlphaSpace(CharSequence)} is false.
   *
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotAlphaSpace(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).isNotAlphaSpace(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * result of {@link CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotAlphanumeric() {
    return waitIsNotAlphanumeric(getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotAlphanumeric(final int waitInSeconds) {
    return waitIsNotAlphanumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#isAlphanumeric(CharSequence)} is false.
   *
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotAlphanumeric(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).isNotAlphanumeric(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * result of {@link CStringUtil#isAlphanumericSpace(CharSequence)} is false.
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotAlphanumericSpace() {
    return waitIsNotAlphanumericSpace(
        getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#isAlphanumericSpace(CharSequence)} is false.
   *
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotAlphanumericSpace(final int waitInSeconds) {
    return waitIsNotAlphanumericSpace(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#isAlphanumericSpace(CharSequence)} is false.
   *
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotAlphanumericSpace(
      final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).isNotAlphanumericSpace(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * result of {@link CStringUtil#isAsciiPrintable(CharSequence)} is false.
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotAsciiPrintable() {
    return waitIsNotAsciiPrintable(
        getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#isAsciiPrintable(CharSequence)} is false.
   *
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotAsciiPrintable(final int waitInSeconds) {
    return waitIsNotAsciiPrintable(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#isAsciiPrintable(CharSequence)} is false.
   *
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotAsciiPrintable(
      final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).isNotAsciiPrintable(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * String value is not blank (Null or Empty)
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotBlank() {
    return waitIsNotBlank(getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till String value is not blank (Null or Empty)
   *
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotBlank(final int waitInSeconds) {
    return waitIsNotBlank(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till String value is not blank (Null or Empty)
   *
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotBlank(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).isNotBlank(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * String value is not empty
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotEmpty() {
    return waitIsNotEmpty(getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till String value is not empty
   *
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotEmpty(final int waitInSeconds) {
    return waitIsNotEmpty(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till String value is not empty
   *
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotEmpty(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).isNotEmpty(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * String value does not match provided pattern
   *
   * @param pattern regular expression pattern
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotMatches(final Pattern pattern) {
    return waitNotMatches(
        pattern, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till String value does not match provided pattern
   *
   * @param waitInSeconds maximum wait time.
   * @param pattern       regular expression pattern
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotMatches(final Pattern pattern, final int waitInSeconds) {
    return waitNotMatches(pattern, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till String value does not match provided pattern
   *
   * @param waitInSeconds maximum wait time.
   * @param pattern       regular expression pattern
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotMatches(
      final Pattern pattern, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).notMatches(pattern), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * String value does not match provided pattern
   *
   * @param pattern regular expression pattern
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotMatches(final String pattern) {
    return waitNotMatches(
        pattern, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till String value does not match provided pattern
   *
   * @param waitInSeconds maximum wait time.
   * @param pattern       regular expression pattern
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotMatches(final String pattern, final int waitInSeconds) {
    return waitNotMatches(pattern, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till String value does not match provided pattern
   *
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param pattern                regular expression pattern
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotMatches(
      final String pattern, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).notMatches(pattern), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * result of {@link CStringUtil#isNumeric(CharSequence)} is false.
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotNumeric() {
    return waitIsNotNumeric(getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#isNumeric(CharSequence)}
   * is false.
   *
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotNumeric(final int waitInSeconds) {
    return waitIsNotNumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#isNumeric(CharSequence)}
   * is false.
   *
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotNumeric(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).isNotNumeric(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * result of {@link CStringUtil#isNumericSpace(CharSequence)} is false.
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotNumericSpace() {
    return waitIsNotNumericSpace(getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#isNumericSpace(CharSequence)} is false.
   *
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotNumericSpace(final int waitInSeconds) {
    return waitIsNotNumericSpace(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#isNumericSpace(CharSequence)} is false.
   *
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotNumericSpace(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).isNotNumericSpace(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * result of {@link CStringUtil#isNumeric(CharSequence)} is true.
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNumeric() {
    return waitIsNumeric(getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#isNumeric(CharSequence)}
   * is true.
   *
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNumeric(final int waitInSeconds) {
    return waitIsNumeric(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds till
   * string value is empty or the result of {@link CStringUtil#isNumeric(CharSequence)} is true and
   * string length is between minLength and maxLength.
   *
   * @param minLength minimum expected side of string if it is not empty
   * @param maxLength maximum expected side of string if it is not empty
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNumeric(int minLength, int maxLength) {
    return waitIsNumeric(
        minLength, maxLength, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till string value is empty or the result of {@link
   * CStringUtil#isNumeric(CharSequence)} is true and string length is between minLength and
   * maxLength.
   *
   * @param minLength     minimum expected side of string if it is not empty
   * @param maxLength     maximum expected side of string if it is not empty
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNumeric(int minLength, int maxLength, final int waitInSeconds) {
    return waitIsNumeric(
        minLength, maxLength, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till string value is empty or the result of {@link
   * CStringUtil#isNumeric(CharSequence)} is true and string length is between minLength and
   * maxLength.
   *
   * @param minLength              minimum expected side of string if it is not empty
   * @param maxLength              maximum expected side of string if it is not empty
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNumeric(
      int minLength, int maxLength, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).isNumeric(minLength, maxLength), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * result of {@link CStringUtil#isNumericSpace(CharSequence)} is true.
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNumericSpace() {
    return waitIsNumericSpace(getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#isNumericSpace(CharSequence)} is true.
   *
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNumericSpace(final int waitInSeconds) {
    return waitIsNumericSpace(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#isNumericSpace(CharSequence)} is true.
   *
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNumericSpace(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).isNumericSpace(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#leftPad(String, int,
   * String)} is equals to expected value.
   *
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitLeftPadEquals(int size, String padStr, String expected) {
    return waitLeftPadEquals(
        size, padStr, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#leftPad(String, int,
   * String)} is equals to expected value.
   *
   * @param size          the size to pad to
   * @param padStr        the String to pad with, null or empty treated as single space
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitLeftPadEquals(
      int size, String padStr, String expected, final int waitInSeconds) {
    return waitLeftPadEquals(
        size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#leftPad(String, int,
   * String)} is equals to expected value.
   *
   * @param size                   the size to pad to
   * @param padStr                 the String to pad with, null or empty treated as single space
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitLeftPadEquals(
      int size,
      String padStr,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).leftPadEquals(size, padStr, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#leftPad(String, int,
   * String)} is NOT equals to expected value.
   *
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitLeftPadNotEquals(int size, String padStr, String expected) {
    return waitLeftPadNotEquals(
        size, padStr, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#leftPad(String, int,
   * String)} is NOT equals to expected value.
   *
   * @param size          the size to pad to
   * @param padStr        the String to pad with, null or empty treated as single space
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitLeftPadNotEquals(
      int size, String padStr, String expected, final int waitInSeconds) {
    return waitLeftPadNotEquals(
        size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#leftPad(String, int,
   * String)} is NOT equals to expected value.
   *
   * @param size                   the size to pad to
   * @param padStr                 the String to pad with, null or empty treated as single space
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitLeftPadNotEquals(
      int size,
      String padStr,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).leftPadNotEquals(size, padStr, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#left(String, int)} equals
   * to expected value.
   *
   * @param expected the expected result.
   * @param len      the length of the required String
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitLeftValueEquals(int len, String expected) {
    return waitLeftValueEquals(
        len, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#left(String, int)} equals
   * to expected value.
   *
   * @param expected      the expected result.
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitLeftValueEquals(int len, String expected, final int waitInSeconds) {
    return waitLeftValueEquals(
        len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#left(String, int)} equals
   * to expected value.
   *
   * @param expected               the expected result.
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitLeftValueEquals(
      int len, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).leftValueEquals(len, expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#left(String, int)} NOT
   * equals to expected value.
   *
   * @param expected the expected result.
   * @param len      the length of the required String
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitLeftValueNotEquals(int len, String expected) {
    return waitLeftValueNotEquals(
        len, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#left(String, int)} NOT
   * equals to expected value.
   *
   * @param expected      the expected result.
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitLeftValueNotEquals(int len, String expected, final int waitInSeconds) {
    return waitLeftValueNotEquals(
        len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#left(String, int)} NOT
   * equals to expected value.
   *
   * @param expected               the expected result.
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitLeftValueNotEquals(
      int len, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).leftValueNotEquals(len, expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#length(CharSequence)} is
   * equals to expected value.
   *
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitLengthEquals(int expected) {
    return waitLengthEquals(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#length(CharSequence)} is
   * equals to expected value.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitLengthEquals(int expected, final int waitInSeconds) {
    return waitLengthEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#length(CharSequence)} is
   * equals to expected value.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitLengthEquals(
      int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).lengthEquals(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#length(CharSequence)} is
   * NOT equals to expected value.
   *
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitLengthNotEquals(int expected) {
    return waitLengthNotEquals(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#length(CharSequence)} is
   * NOT equals to expected value.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitLengthNotEquals(int expected, final int waitInSeconds) {
    return waitLengthNotEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#length(CharSequence)} is
   * NOT equals to expected value.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitLengthNotEquals(
      int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).lengthNotEquals(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#mid(String, int, int)}
   * equals to expected value.
   *
   * @param expected the expected result.
   * @param pos      the position to start from, negative treated as zero
   * @param len      the length of the required String
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitMidValueEquals(int pos, int len, String expected) {
    return waitMidValueEquals(
        pos, len, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#mid(String, int, int)}
   * equals to expected value.
   *
   * @param expected      the expected result.
   * @param pos           the position to start from, negative treated as zero
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitMidValueEquals(int pos, int len, String expected, final int waitInSeconds) {
    return waitMidValueEquals(
        pos, len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#mid(String, int, int)}
   * equals to expected value.
   *
   * @param expected               the expected result.
   * @param pos                    the position to start from, negative treated as zero
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitMidValueEquals(
      int pos,
      int len,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).midValueEquals(pos, len, expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#mid(String, int, int)}
   * NOT equals to expected value.
   *
   * @param expected the expected result.
   * @param pos      the position to start from, negative treated as zero
   * @param len      the length of the required String
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitMidValueNotEquals(int pos, int len, String expected) {
    return waitMidValueNotEquals(
        pos, len, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#mid(String, int, int)}
   * NOT equals to expected value.
   *
   * @param expected      the expected result.
   * @param pos           the position to start from, negative treated as zero
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitMidValueNotEquals(
      int pos, int len, String expected, final int waitInSeconds) {
    return waitMidValueNotEquals(
        pos, len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#mid(String, int, int)}
   * NOT equals to expected value.
   *
   * @param expected               the expected result.
   * @param pos                    the position to start from, negative treated as zero
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitMidValueNotEquals(
      int pos,
      int len,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).midValueNotEquals(pos, len, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#contains(CharSequence,
   * CharSequence)} is false.
   *
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotContains(String expected) {
    return waitNotContains(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#contains(CharSequence,
   * CharSequence)} is false.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotContains(String expected, final int waitInSeconds) {
    return waitNotContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#contains(CharSequence,
   * CharSequence)} is false.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotContains(
      String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).notContains(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is false.
   *
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotContainsIgnoreCase(String expected) {
    return waitNotContainsIgnoreCase(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is false.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotContainsIgnoreCase(String expected, final int waitInSeconds) {
    return waitNotContainsIgnoreCase(
        expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#containsIgnoreCase(CharSequence, CharSequence)} is false.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotContainsIgnoreCase(
      String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).notContainsIgnoreCase(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#endsWith(CharSequence,
   * CharSequence)} is false
   *
   * @param suffix the suffix to find, may be {@code null}
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEndsWith(String suffix) {
    return waitNotEndsWith(
        suffix, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#endsWith(CharSequence,
   * CharSequence)} is false
   *
   * @param suffix        the suffix to find, may be {@code null}
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEndsWith(String suffix, final int waitInSeconds) {
    return waitNotEndsWith(suffix, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#endsWith(CharSequence,
   * CharSequence)} is false
   *
   * @param suffix                 the suffix to find, may be {@code null}
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEndsWith(
      String suffix, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).notEndsWith(suffix), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is false.
   *
   * @param suffix the suffix to find, may be {@code null}
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEndsWithIgnoreCase(String suffix) {
    return waitNotEndsWithIgnoreCase(
        suffix, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is false.
   *
   * @param suffix        the suffix to find, may be {@code null}
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEndsWithIgnoreCase(String suffix, final int waitInSeconds) {
    return waitNotEndsWithIgnoreCase(suffix, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#endsWithIgnoreCase(CharSequence, CharSequence)} is false.
   *
   * @param suffix                 the suffix to find, may be {@code null}
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEndsWithIgnoreCase(
      String suffix, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).notEndsWithIgnoreCase(suffix), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#equalsIgnoreCase(CharSequence,
   * CharSequence)} value NOT equals the expected value.
   *
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEqualsIgnoreCase(String expected) {
    return waitNotEqualsIgnoreCase(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#equalsIgnoreCase(CharSequence,
   * CharSequence)} value NOT equals the expected value.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEqualsIgnoreCase(String expected, final int waitInSeconds) {
    return waitNotEqualsIgnoreCase(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#equalsIgnoreCase(CharSequence,
   * CharSequence)} value NOT equals the expected value.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEqualsIgnoreCase(
      String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).notEqualsIgnoreCase(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till value is not equal to expected after removing all
   * WhiteSpaces from both.
   *
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEqualsIgnoreWhiteSpaces(String expected) {
    return waitNotEqualsIgnoreWhiteSpaces(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till value is not equal to expected after removing all
   * WhiteSpaces from both.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEqualsIgnoreWhiteSpaces(String expected, final int waitInSeconds) {
    return waitNotEqualsIgnoreWhiteSpaces(
        expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till value is not equal to expected after removing all
   * WhiteSpaces from both.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEqualsIgnoreWhiteSpaces(
      String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).notEqualsIgnoreWhiteSpaces(expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#startsWith(CharSequence,
   * CharSequence)} is false
   *
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotStartsWith(String expected) {
    return waitNotStartsWith(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#startsWith(CharSequence,
   * CharSequence)} is false
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotStartsWith(String expected, final int waitInSeconds) {
    return waitNotStartsWith(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#startsWith(CharSequence,
   * CharSequence)} is false
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotStartsWith(
      String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).notStartsWith(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is false
   *
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotStartsWithIgnoreCase(String expected) {
    return waitNotStartsWithIgnoreCase(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is false
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotStartsWithIgnoreCase(String expected, final int waitInSeconds) {
    return waitNotStartsWithIgnoreCase(
        expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is false
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotStartsWithIgnoreCase(
      String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).notStartsWithIgnoreCase(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#countMatches(CharSequence, CharSequence)} is equals to expected value.
   *
   * @param expected  the expected result.
   * @param subString the substring to count, may be null
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNumberOfMatchesEquals(String subString, int expected) {
    return waitNumberOfMatchesEquals(
        subString, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#countMatches(CharSequence, CharSequence)} is equals to expected value.
   *
   * @param expected      the expected result.
   * @param subString     the substring to count, may be null
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNumberOfMatchesEquals(
      String subString, int expected, final int waitInSeconds) {
    return waitNumberOfMatchesEquals(
        subString, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#countMatches(CharSequence, CharSequence)} is equals to expected value.
   *
   * @param expected               the expected result.
   * @param subString              the substring to count, may be null
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNumberOfMatchesEquals(
      String subString, int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).numberOfMatchesEquals(subString, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#countMatches(CharSequence, CharSequence)} is NOT equals to expected value.
   *
   * @param expected  the expected result.
   * @param subString the substring to count, may be null
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNumberOfMatchesNotEquals(String subString, int expected) {
    return waitNumberOfMatchesNotEquals(
        subString, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#countMatches(CharSequence, CharSequence)} is NOT equals to expected value.
   *
   * @param expected      the expected result.
   * @param subString     the substring to count, may be null
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNumberOfMatchesNotEquals(
      String subString, int expected, final int waitInSeconds) {
    return waitNumberOfMatchesNotEquals(
        subString, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#countMatches(CharSequence, CharSequence)} is NOT equals to expected value.
   *
   * @param expected               the expected result.
   * @param subString              the substring to count, may be null
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNumberOfMatchesNotEquals(
      String subString, int expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).numberOfMatchesNotEquals(subString, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#removeEnd(String,
   * String)} is equals to expected value.
   *
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveEndEquals(String remove, String expected, final int waitInSeconds) {
    return waitRemoveEndEquals(
        remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#removeEnd(String,
   * String)} is equals to expected value.
   *
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveEndEquals(String remove, String expected) {
    return waitRemoveEndEquals(
        remove, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#removeEnd(String,
   * String)} is equals to expected value.
   *
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveEndEquals(
      String remove, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).removeEndEquals(remove, expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#removeEndIgnoreCase(String, String)} is equals to expected value.
   *
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveEndIgnoreCaseEquals(String remove, String expected) {
    return waitRemoveEndIgnoreCaseEquals(
        remove, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#removeEndIgnoreCase(String, String)} is equals to expected value.
   *
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveEndIgnoreCaseEquals(
      String remove, String expected, final int waitInSeconds) {
    return waitRemoveEndIgnoreCaseEquals(
        remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#removeEndIgnoreCase(String, String)} is equals to expected value.
   *
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveEndIgnoreCaseEquals(
      String remove, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).removeEndIgnoreCaseEquals(remove, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#removeEndIgnoreCase(String, String)} is NOT equals to expected value.
   *
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveEndIgnoreCaseNotEquals(String remove, String expected) {
    return waitRemoveEndIgnoreCaseNotEquals(
        remove, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#removeEndIgnoreCase(String, String)} is NOT equals to expected value.
   *
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveEndIgnoreCaseNotEquals(
      String remove, String expected, final int waitInSeconds) {
    return waitRemoveEndIgnoreCaseNotEquals(
        remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#removeEndIgnoreCase(String, String)} is NOT equals to expected value.
   *
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveEndIgnoreCaseNotEquals(
      String remove, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).removeEndIgnoreCaseNotEquals(remove, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#removeEnd(String,
   * String)} is NOT equals to expected value.
   *
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveEndNotEquals(String remove, String expected) {
    return waitRemoveEndNotEquals(
        remove, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#removeEnd(String,
   * String)} is NOT equals to expected value.
   *
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveEndNotEquals(String remove, String expected, final int waitInSeconds) {
    return waitRemoveEndNotEquals(
        remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#removeEnd(String,
   * String)} is NOT equals to expected value.
   *
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveEndNotEquals(
      String remove, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).removeEndNotEquals(remove, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#remove(String, String)}
   * is equals to expected value.
   *
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveEquals(String remove, String expected) {
    return waitRemoveEquals(
        remove, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#remove(String, String)}
   * is equals to expected value.
   *
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveEquals(String remove, String expected, final int waitInSeconds) {
    return waitRemoveEquals(
        remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#remove(String, String)}
   * is equals to expected value.
   *
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveEquals(
      String remove, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).removeEquals(remove, expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#removeIgnoreCase(String,
   * String)} is equals to expected value.
   *
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveIgnoreCaseEquals(String remove, String expected) {
    return waitRemoveIgnoreCaseEquals(
        remove, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#removeIgnoreCase(String,
   * String)} is equals to expected value.
   *
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveIgnoreCaseEquals(
      String remove, String expected, final int waitInSeconds) {
    return waitRemoveIgnoreCaseEquals(
        remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#removeIgnoreCase(String,
   * String)} is equals to expected value.
   *
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveIgnoreCaseEquals(
      String remove, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).removeIgnoreCaseEquals(remove, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#removeIgnoreCase(String,
   * String)} is NOT equals to expected value.
   *
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveIgnoreCaseNotEquals(String remove, String expected) {
    return waitRemoveIgnoreCaseNotEquals(
        remove, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#removeIgnoreCase(String,
   * String)} is NOT equals to expected value.
   *
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveIgnoreCaseNotEquals(
      String remove, String expected, final int waitInSeconds) {
    return waitRemoveIgnoreCaseNotEquals(
        remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#removeIgnoreCase(String,
   * String)} is NOT equals to expected value.
   *
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveIgnoreCaseNotEquals(
      String remove, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).removeIgnoreCaseNotEquals(remove, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#remove(String, String)}
   * is NOT equals to expected value.
   *
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveNotEquals(String remove, String expected) {
    return waitRemoveNotEquals(
        remove, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#remove(String, String)}
   * is NOT equals to expected value.
   *
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveNotEquals(String remove, String expected, final int waitInSeconds) {
    return waitRemoveNotEquals(
        remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#remove(String, String)}
   * is NOT equals to expected value.
   *
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveNotEquals(
      String remove, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).removeNotEquals(remove, expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#removeStart(String,
   * String)} is equals to expected value.
   *
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveStartEquals(String remove, String expected) {
    return waitRemoveStartEquals(
        remove, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#removeStart(String,
   * String)} is equals to expected value.
   *
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveStartEquals(String remove, String expected, final int waitInSeconds) {
    return waitRemoveStartEquals(
        remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#removeStart(String,
   * String)} is equals to expected value.
   *
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveStartEquals(
      String remove, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).removeStartEquals(remove, expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#removeStartIgnoreCase(String, String)} is equals to expected value.
   *
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveStartIgnoreCaseEquals(String remove, String expected) {
    return waitRemoveStartIgnoreCaseEquals(
        remove, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#removeStartIgnoreCase(String, String)} is equals to expected value.
   *
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveStartIgnoreCaseEquals(
      String remove, String expected, final int waitInSeconds) {
    return waitRemoveStartIgnoreCaseEquals(
        remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#removeStartIgnoreCase(String, String)} is equals to expected value.
   *
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveStartIgnoreCaseEquals(
      String remove, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).removeStartIgnoreCaseEquals(remove, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#removeStartIgnoreCase(String, String)} is NOT equals to expected value.
   *
   * @param remove   the String to search for (case insensitive) and remove, may be null
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveStartIgnoreCaseNotEquals(String remove, String expected) {
    return waitRemoveStartIgnoreCaseNotEquals(
        remove, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#removeStartIgnoreCase(String, String)} is NOT equals to expected value.
   *
   * @param remove        the String to search for (case insensitive) and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveStartIgnoreCaseNotEquals(
      String remove, String expected, final int waitInSeconds) {
    return waitRemoveStartIgnoreCaseNotEquals(
        remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#removeStartIgnoreCase(String, String)} is NOT equals to expected value.
   *
   * @param remove                 the String to search for (case insensitive) and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveStartIgnoreCaseNotEquals(
      String remove, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).removeStartIgnoreCaseNotEquals(remove, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#removeStart(String,
   * String)} is NOT equals to expected value.
   *
   * @param remove   the String to search for and remove, may be null
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveStartNotEquals(String remove, String expected) {
    return waitRemoveStartNotEquals(
        remove, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#removeStart(String,
   * String)} is NOT equals to expected value.
   *
   * @param remove        the String to search for and remove, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveStartNotEquals(
      String remove, String expected, final int waitInSeconds) {
    return waitRemoveStartNotEquals(
        remove, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#removeStart(String,
   * String)} is NOT equals to expected value.
   *
   * @param remove                 the String to search for and remove, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRemoveStartNotEquals(
      String remove, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).removeStartNotEquals(remove, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#replace(String, String,
   * String)} is equals to expected value.
   *
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReplaceEquals(String searchString, String replacement, String expected) {
    return waitReplaceEquals(
        searchString,
        replacement,
        expected,
        getDefaultWaitInSeconds(),
        getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#replace(String, String,
   * String)} is equals to expected value.
   *
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace it with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReplaceEquals(
      String searchString, String replacement, String expected, final int waitInSeconds) {
    return waitReplaceEquals(
        searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#replace(String, String,
   * String)} is equals to expected value.
   *
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace it with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReplaceEquals(
      String searchString,
      String replacement,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).replaceEquals(searchString, replacement, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#replaceIgnoreCase(String,
   * String, String)} is equals to expected value.
   *
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReplaceIgnoreCaseEquals(
      String searchString, String replacement, String expected) {
    return waitReplaceIgnoreCaseEquals(
        searchString,
        replacement,
        expected,
        getDefaultWaitInSeconds(),
        getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#replaceIgnoreCase(String,
   * String, String)} is equals to expected value.
   *
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace it with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReplaceIgnoreCaseEquals(
      String searchString, String replacement, String expected, final int waitInSeconds) {
    return waitReplaceIgnoreCaseEquals(
        searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#replaceIgnoreCase(String,
   * String, String)} is equals to expected value.
   *
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace it with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReplaceIgnoreCaseEquals(
      String searchString,
      String replacement,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).replaceIgnoreCaseEquals(searchString, replacement, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#replaceIgnoreCase(String,
   * String, String)} is NOT equals to expected value.
   *
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace it with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReplaceIgnoreCaseNotEquals(
      String searchString, String replacement, String expected, final int waitInSeconds) {
    return waitReplaceIgnoreCaseNotEquals(
        searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#replaceIgnoreCase(String,
   * String, String)} is NOT equals to expected value.
   *
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReplaceIgnoreCaseNotEquals(
      String searchString, String replacement, String expected) {
    return waitReplaceIgnoreCaseNotEquals(
        searchString,
        replacement,
        expected,
        getDefaultWaitInSeconds(),
        getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#replaceIgnoreCase(String,
   * String, String)} is NOT equals to expected value.
   *
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace it with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReplaceIgnoreCaseNotEquals(
      String searchString,
      String replacement,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).replaceIgnoreCaseNotEquals(searchString, replacement, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#replace(String, String,
   * String)} is NOT equals to expected value.
   *
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace it with, may be null
   * @param expected     the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReplaceNotEquals(String searchString, String replacement, String expected) {
    return waitReplaceNotEquals(
        searchString,
        replacement,
        expected,
        getDefaultWaitInSeconds(),
        getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#replace(String, String,
   * String)} is NOT equals to expected value.
   *
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace it with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReplaceNotEquals(
      String searchString, String replacement, String expected, final int waitInSeconds) {
    return waitReplaceNotEquals(
        searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#replace(String, String,
   * String)} is NOT equals to expected value.
   *
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace it with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReplaceNotEquals(
      String searchString,
      String replacement,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).replaceNotEquals(searchString, replacement, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#replaceOnce(String,
   * String, String)} is equals to expected value.
   *
   * @param searchString the String to search for, may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReplaceOnceEquals(String searchString, String replacement, String expected) {
    return waitReplaceOnceEquals(
        searchString,
        replacement,
        expected,
        getDefaultWaitInSeconds(),
        getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#replaceOnce(String,
   * String, String)} is equals to expected value.
   *
   * @param searchString  the String to search for, may be null
   * @param replacement   the String to replace with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReplaceOnceEquals(
      String searchString, String replacement, String expected, final int waitInSeconds) {
    return waitReplaceOnceEquals(
        searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#replaceOnce(String,
   * String, String)} is equals to expected value.
   *
   * @param searchString           the String to search for, may be null
   * @param replacement            the String to replace with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReplaceOnceEquals(
      String searchString,
      String replacement,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).replaceOnceEquals(searchString, replacement, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#replaceOnceIgnoreCase(String, String, String)} is equals to expected value.
   *
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReplaceOnceIgnoreCaseEquals(
      String searchString, String replacement, String expected) {
    return waitReplaceOnceIgnoreCaseEquals(
        searchString,
        replacement,
        expected,
        getDefaultWaitInSeconds(),
        getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#replaceOnceIgnoreCase(String, String, String)} is equals to expected value.
   *
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReplaceOnceIgnoreCaseEquals(
      String searchString, String replacement, String expected, final int waitInSeconds) {
    return waitReplaceOnceIgnoreCaseEquals(
        searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#replaceOnceIgnoreCase(String, String, String)} is equals to expected value.
   *
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReplaceOnceIgnoreCaseEquals(
      String searchString,
      String replacement,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).replaceOnceIgnoreCaseEquals(searchString, replacement, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#replaceOnceIgnoreCase(String, String, String)} is NOT equals to expected value.
   *
   * @param searchString the String to search for (case insensitive), may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReplaceOnceIgnoreCaseNotEquals(
      String searchString, String replacement, String expected) {
    return waitReplaceOnceIgnoreCaseNotEquals(
        searchString,
        replacement,
        expected,
        getDefaultWaitInSeconds(),
        getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#replaceOnceIgnoreCase(String, String, String)} is NOT equals to expected value.
   *
   * @param searchString  the String to search for (case insensitive), may be null
   * @param replacement   the String to replace with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReplaceOnceIgnoreCaseNotEquals(
      String searchString, String replacement, String expected, final int waitInSeconds) {
    return waitReplaceOnceIgnoreCaseNotEquals(
        searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#replaceOnceIgnoreCase(String, String, String)} is NOT equals to expected value.
   *
   * @param searchString           the String to search for (case insensitive), may be null
   * @param replacement            the String to replace with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReplaceOnceIgnoreCaseNotEquals(
      String searchString,
      String replacement,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).replaceOnceIgnoreCaseNotEquals(searchString, replacement, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#replaceOnce(String,
   * String, String)} is NOT equals to expected value.
   *
   * @param searchString the String to search for, may be null
   * @param replacement  the String to replace with, may be null
   * @param expected     the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReplaceOnceNotEquals(
      String searchString, String replacement, String expected) {
    return waitReplaceOnceNotEquals(
        searchString,
        replacement,
        expected,
        getDefaultWaitInSeconds(),
        getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#replaceOnce(String,
   * String, String)} is NOT equals to expected value.
   *
   * @param searchString  the String to search for, may be null
   * @param replacement   the String to replace with, may be null
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReplaceOnceNotEquals(
      String searchString, String replacement, String expected, final int waitInSeconds) {
    return waitReplaceOnceNotEquals(
        searchString, replacement, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#replaceOnce(String,
   * String, String)} is NOT equals to expected value.
   *
   * @param searchString           the String to search for, may be null
   * @param replacement            the String to replace with, may be null
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReplaceOnceNotEquals(
      String searchString,
      String replacement,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).replaceOnceNotEquals(searchString, replacement, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#reverse(String)} is
   * equals to expected value.
   *
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReverseEquals(String expected) {
    return waitReverseEquals(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#reverse(String)} is
   * equals to expected value.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReverseEquals(String expected, final int waitInSeconds) {
    return waitReverseEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#reverse(String)} is
   * equals to expected value.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitReverseEquals(
      String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).reverseEquals(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#reverse(String)} is NOT
   * equals to expected value.
   *
   * @param expected the expected result.
   */
  default boolean waitReverseNotEquals(String expected) {
    return waitReverseNotEquals(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#reverse(String)} is NOT
   * equals to expected value.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   */
  default boolean waitReverseNotEquals(String expected, final int waitInSeconds) {
    return waitReverseNotEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#reverse(String)} is NOT
   * equals to expected value.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default boolean waitReverseNotEquals(
      String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).reverseNotEquals(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#rightPad(String, int,
   * String)} is equals to expected value.
   *
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRightPadEquals(int size, String padStr, String expected) {
    return waitRightPadEquals(
        size, padStr, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#rightPad(String, int,
   * String)} is equals to expected value.
   *
   * @param size          the size to pad to
   * @param padStr        the String to pad with, null or empty treated as single space
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRightPadEquals(
      int size, String padStr, String expected, final int waitInSeconds) {
    return waitRightPadEquals(
        size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#rightPad(String, int,
   * String)} is equals to expected value.
   *
   * @param size                   the size to pad to
   * @param padStr                 the String to pad with, null or empty treated as single space
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRightPadEquals(
      int size,
      String padStr,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).rightPadEquals(size, padStr, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#rightPad(String, int,
   * String)} is NOT equals to expected value.
   *
   * @param size     the size to pad to
   * @param padStr   the String to pad with, null or empty treated as single space
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRightPadNotEquals(int size, String padStr, String expected) {
    return waitRightPadNotEquals(
        size, padStr, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#rightPad(String, int,
   * String)} is NOT equals to expected value.
   *
   * @param size          the size to pad to
   * @param padStr        the String to pad with, null or empty treated as single space
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRightPadNotEquals(
      int size, String padStr, String expected, final int waitInSeconds) {
    return waitRightPadNotEquals(
        size, padStr, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#rightPad(String, int,
   * String)} is NOT equals to expected value.
   *
   * @param size                   the size to pad to
   * @param padStr                 the String to pad with, null or empty treated as single space
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRightPadNotEquals(
      int size,
      String padStr,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).rightPadNotEquals(size, padStr, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#right(String, int)}
   * equals to expected value.
   *
   * @param expected the expected result.
   * @param len      the length of the required String
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRightValueEquals(int len, String expected) {
    return waitRightValueEquals(
        len, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#right(String, int)}
   * equals to expected value.
   *
   * @param expected      the expected result.
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRightValueEquals(int len, String expected, final int waitInSeconds) {
    return waitRightValueEquals(
        len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#right(String, int)}
   * equals to expected value.
   *
   * @param expected               the expected result.
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRightValueEquals(
      int len, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).rightValueEquals(len, expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#right(String, int)} NOT
   * equals to expected value.
   *
   * @param expected the expected result.
   * @param len      the length of the required String
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRightValueNotEquals(int len, String expected) {
    return waitRightValueNotEquals(
        len, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#right(String, int)} NOT
   * equals to expected value.
   *
   * @param expected      the expected result.
   * @param len           the length of the required String
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRightValueNotEquals(int len, String expected, final int waitInSeconds) {
    return waitRightValueNotEquals(
        len, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#right(String, int)} NOT
   * equals to expected value.
   *
   * @param expected               the expected result.
   * @param len                    the length of the required String
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitRightValueNotEquals(
      int len, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).rightValueNotEquals(len, expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#startsWith(CharSequence,
   * CharSequence)} is true
   *
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStartsWith(String expected) {
    return waitStartsWith(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#startsWith(CharSequence,
   * CharSequence)} is true
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStartsWith(String expected, final int waitInSeconds) {
    return waitStartsWith(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#startsWith(CharSequence,
   * CharSequence)} is true
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStartsWith(
      String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).startsWith(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#startsWithAny(CharSequence, CharSequence...)} is true
   *
   * @param searchInputs the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                     null}
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStartsWithAny(List<String> searchInputs) {
    return waitStartsWithAny(
        searchInputs, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#startsWithAny(CharSequence, CharSequence...)} is true
   *
   * @param searchInputs  the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                      null}
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStartsWithAny(List<String> searchInputs, final int waitInSeconds) {
    return waitStartsWithAny(searchInputs, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#startsWithAny(CharSequence, CharSequence...)} is true
   *
   * @param searchInputs           the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                               null}
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStartsWithAny(
      List<String> searchInputs, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).startsWithAny(searchInputs), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is true
   *
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStartsWithIgnoreCase(String expected) {
    return waitStartsWithIgnoreCase(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is true
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStartsWithIgnoreCase(String expected, final int waitInSeconds) {
    return waitStartsWithIgnoreCase(
        expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#startsWithIgnoreCase(CharSequence, CharSequence)} is true
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStartsWithIgnoreCase(
      String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).startsWithIgnoreCase(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#startsWithAny(CharSequence, CharSequence...)} is false
   *
   * @param searchInputs the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                     null}
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStartsWithNone(List<String> searchInputs) {
    return waitStartsWithNone(
        searchInputs, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#startsWithAny(CharSequence, CharSequence...)} is false
   *
   * @param searchInputs  the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                      null}
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStartsWithNone(List<String> searchInputs, final int waitInSeconds) {
    return waitStartsWithNone(searchInputs, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#startsWithAny(CharSequence, CharSequence...)} is false
   *
   * @param searchInputs           the case-sensitive CharSequence prefixes, may be empty or contain {@code
   *                               null}
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStartsWithNone(
      List<String> searchInputs, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).startsWithNone(searchInputs), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#stripEnd(String, String)} value
   * equals the expected value.
   *
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStripedEndValue(String stripChars, String expected) {
    return waitStripedEndValue(
        stripChars, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#stripEnd(String, String)} value
   * equals the expected value.
   *
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStripedEndValue(String stripChars, String expected, final int waitInSeconds) {
    return waitStripedEndValue(
        stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#stripEnd(String, String)} value
   * equals the expected value.
   *
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStripedEndValue(
      String stripChars,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).stripedEndValue(stripChars, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#stripEnd(String, String)} value NOT
   * equals the expected value.
   *
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStripedEndValueNot(String stripChars, String expected) {
    return waitStripedEndValueNot(
        stripChars, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#stripEnd(String, String)} value NOT
   * equals the expected value.
   *
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStripedEndValueNot(
      String stripChars, String expected, final int waitInSeconds) {
    return waitStripedEndValueNot(
        stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#stripEnd(String, String)} value NOT
   * equals the expected value.
   *
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStripedEndValueNot(
      String stripChars,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).stripedEndValueNot(stripChars, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#stripStart(String, String)} value
   * equals the expected value.
   *
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStripedStartValue(String stripChars, String expected) {
    return waitStripedStartValue(
        stripChars, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#stripStart(String, String)} value
   * equals the expected value.
   *
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStripedStartValue(
      String stripChars, String expected, final int waitInSeconds) {
    return waitStripedStartValue(
        stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#stripStart(String, String)} value
   * equals the expected value.
   *
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStripedStartValue(
      String stripChars,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).stripedStartValue(stripChars, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#stripStart(String, String)} value
   * NOT equals the expected value.
   *
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStripedStartValueNot(String stripChars, String expected) {
    return waitStripedStartValueNot(
        stripChars, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#stripStart(String, String)} value
   * NOT equals the expected value.
   *
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStripedStartValueNot(
      String stripChars, String expected, final int waitInSeconds) {
    return waitStripedStartValueNot(
        stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#stripStart(String, String)} value
   * NOT equals the expected value.
   *
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStripedStartValueNot(
      String stripChars,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).stripedStartValueNot(stripChars, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#strip(String)} value equals the
   * expected value.
   *
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStripedValue(String stripChars, String expected) {
    return waitStripedValue(
        stripChars, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#strip(String)} value equals the
   * expected value.
   *
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStripedValue(String stripChars, String expected, final int waitInSeconds) {
    return waitStripedValue(
        stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#strip(String)} value equals the
   * expected value.
   *
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStripedValue(
      String stripChars,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).stripedValue(stripChars, expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#strip(String)} value NOT equals the
   * expected value.
   *
   * @param stripChars the characters to remove, null treated as whitespace
   * @param expected   the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStripedValueNot(String stripChars, String expected) {
    return waitStripedValueNot(
        stripChars, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#strip(String)} value NOT equals the
   * expected value.
   *
   * @param stripChars    the characters to remove, null treated as whitespace
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStripedValueNot(String stripChars, String expected, final int waitInSeconds) {
    return waitStripedValueNot(
        stripChars, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#strip(String)} value NOT equals the
   * expected value.
   *
   * @param stripChars             the characters to remove, null treated as whitespace
   * @param expected               the expected result.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitStripedValueNot(
      String stripChars,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).stripedValueNot(stripChars, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringAfter(String,
   * String)} equals to expected value.
   *
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringAfterEquals(String separator, String expected) {
    return waitSubstringAfterEquals(
        separator, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringAfter(String,
   * String)} equals to expected value.
   *
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringAfterEquals(
      String separator, String expected, final int waitInSeconds) {
    return waitSubstringAfterEquals(
        separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringAfter(String,
   * String)} equals to expected value.
   *
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringAfterEquals(
      String separator,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).substringAfterEquals(separator, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#substringAfterLast(String, String)} equals to expected value.
   *
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringAfterLastEquals(String separator, String expected) {
    return waitSubstringAfterLastEquals(
        separator, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#substringAfterLast(String, String)} equals to expected value.
   *
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringAfterLastEquals(
      String separator, String expected, final int waitInSeconds) {
    return waitSubstringAfterLastEquals(
        separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#substringAfterLast(String, String)} equals to expected value.
   *
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringAfterLastEquals(
      String separator,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).substringAfterLastEquals(separator, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#substringAfterLast(String, String)} NOT equals to expected value.
   *
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringAfterLastNotEquals(String separator, String expected) {
    return waitSubstringAfterLastNotEquals(
        separator, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#substringAfterLast(String, String)} NOT equals to expected value.
   *
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringAfterLastNotEquals(
      String separator, String expected, final int waitInSeconds) {
    return waitSubstringAfterLastNotEquals(
        separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#substringAfterLast(String, String)} NOT equals to expected value.
   *
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringAfterLastNotEquals(
      String separator,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).substringAfterLastNotEquals(separator, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringAfter(String,
   * String)} NOT equals to expected value.
   *
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringAfterNotEquals(String separator, String expected) {
    return waitSubstringAfterNotEquals(
        separator, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringAfter(String,
   * String)} NOT equals to expected value.
   *
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringAfterNotEquals(
      String separator, String expected, final int waitInSeconds) {
    return waitSubstringAfterNotEquals(
        separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringAfter(String,
   * String)} NOT equals to expected value.
   *
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringAfterNotEquals(
      String separator,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).substringAfterNotEquals(separator, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringBefore(String,
   * String)} equals to expected value.
   *
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringBeforeEquals(String separator, String expected) {
    return waitSubstringBeforeEquals(
        separator, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringBefore(String,
   * String)} equals to expected value.
   *
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringBeforeEquals(
      String separator, String expected, final int waitInSeconds) {
    return waitSubstringBeforeEquals(
        separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringBefore(String,
   * String)} equals to expected value.
   *
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringBeforeEquals(
      String separator,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).substringBeforeEquals(separator, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#substringBeforeLast(String, String)} equals to expected value.
   *
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringBeforeLastEquals(String separator, String expected) {
    return waitSubstringBeforeLastEquals(
        separator, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#substringBeforeLast(String, String)} equals to expected value.
   *
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringBeforeLastEquals(
      String separator, String expected, final int waitInSeconds) {
    return waitSubstringBeforeLastEquals(
        separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#substringBeforeLast(String, String)} equals to expected value.
   *
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringBeforeLastEquals(
      String separator,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).substringBeforeLastEquals(separator, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#substringBeforeLast(String, String)} NOT equals to expected value.
   *
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringBeforeLastNotEquals(String separator, String expected) {
    return waitSubstringBeforeLastNotEquals(
        separator, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#substringBeforeLast(String, String)} NOT equals to expected value.
   *
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringBeforeLastNotEquals(
      String separator, String expected, final int waitInSeconds) {
    return waitSubstringBeforeLastNotEquals(
        separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link
   * CStringUtil#substringBeforeLast(String, String)} NOT equals to expected value.
   *
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringBeforeLastNotEquals(
      String separator,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).substringBeforeLastNotEquals(separator, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringBefore(String,
   * String)} NOT equals to expected value.
   *
   * @param expected  the expected result.
   * @param separator the String to search for, may be {@code null}
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringBeforeNotEquals(String separator, String expected) {
    return waitSubstringBeforeNotEquals(
        separator, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringBefore(String,
   * String)} NOT equals to expected value.
   *
   * @param expected      the expected result.
   * @param separator     the String to search for, may be {@code null}
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringBeforeNotEquals(
      String separator, String expected, final int waitInSeconds) {
    return waitSubstringBeforeNotEquals(
        separator, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringBefore(String,
   * String)} NOT equals to expected value.
   *
   * @param expected               the expected result.
   * @param separator              the String to search for, may be {@code null}
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringBeforeNotEquals(
      String separator,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).substringBeforeNotEquals(separator, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringBetween(String,
   * String)} equals to expected value.
   *
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringBetweenEquals(String open, String close, String expected) {
    return waitSubstringBetweenEquals(
        open, close, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringBetween(String,
   * String)} equals to expected value.
   *
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringBetweenEquals(
      String open, String close, String expected, final int waitInSeconds) {
    return waitSubstringBetweenEquals(
        open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringBetween(String,
   * String)} equals to expected value.
   *
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringBetweenEquals(
      String open,
      String close,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).substringBetweenEquals(open, close, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringBetween(String,
   * String)} NOT equals to expected value.
   *
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringBetweenNotEquals(String open, String close, String expected) {
    return waitSubstringBetweenNotEquals(
        open, close, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringBetween(String,
   * String)} NOT equals to expected value.
   *
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringBetweenNotEquals(
      String open, String close, String expected, final int waitInSeconds) {
    return waitSubstringBetweenNotEquals(
        open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringBetween(String,
   * String)} NOT equals to expected value.
   *
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringBetweenNotEquals(
      String open,
      String close,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).substringBetweenNotEquals(open, close, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substring(String, int)}
   * equals to expected value.
   *
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringEquals(int start, String expected) {
    return waitSubstringEquals(
        start, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substring(String, int)}
   * equals to expected value.
   *
   * @param expected      the expected result.
   * @param start         the position to start from, negative means count back from the end of the String
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringEquals(int start, String expected, final int waitInSeconds) {
    return waitSubstringEquals(
        start, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substring(String, int)}
   * equals to expected value.
   *
   * @param expected               the expected result.
   * @param start                  the position to start from, negative means count back from the end of the String
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringEquals(
      int start, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).substringEquals(start, expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substring(String, int,
   * int)} equals to expected value.
   *
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   * @param end      the position to end at (exclusive), negative means count back from the end of the
   *                 String
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringEquals(int start, int end, String expected) {
    return waitSubstringEquals(
        start, end, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substring(String, int,
   * int)} equals to expected value.
   *
   * @param expected      the expected result.
   * @param start         the position to start from, negative means count back from the end of the String
   * @param end           the position to end at (exclusive), negative means count back from the end of the
   *                      String
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringEquals(
      int start, int end, String expected, final int waitInSeconds) {
    return waitSubstringEquals(
        start, end, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substring(String, int,
   * int)} equals to expected value.
   *
   * @param expected               the expected result.
   * @param start                  the position to start from, negative means count back from the end of the String
   * @param end                    the position to end at (exclusive), negative means count back from the end of the
   *                               String
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringEquals(
      int start,
      int end,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).substringEquals(start, end, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substring(String, int)}
   * NOT equals to expected value.
   *
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringNotEquals(int start, String expected) {
    return waitSubstringNotEquals(
        start, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substring(String, int)}
   * NOT equals to expected value.
   *
   * @param expected      the expected result.
   * @param start         the position to start from, negative means count back from the end of the String
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringNotEquals(int start, String expected, final int waitInSeconds) {
    return waitSubstringNotEquals(
        start, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substring(String, int)}
   * NOT equals to expected value.
   *
   * @param expected               the expected result.
   * @param start                  the position to start from, negative means count back from the end of the String
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringNotEquals(
      int start, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).substringNotEquals(start, expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substring(String, int,
   * int)} NOT equals to expected value.
   *
   * @param expected the expected result.
   * @param start    the position to start from, negative means count back from the end of the String
   * @param end      the position to end at (exclusive), negative means count back from the end of the
   *                 String
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringNotEquals(int start, int end, String expected) {
    return waitSubstringNotEquals(
        start, end, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substring(String, int,
   * int)} NOT equals to expected value.
   *
   * @param expected      the expected result.
   * @param start         the position to start from, negative means count back from the end of the String
   * @param end           the position to end at (exclusive), negative means count back from the end of the
   *                      String
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringNotEquals(
      int start, int end, String expected, final int waitInSeconds) {
    return waitSubstringNotEquals(
        start, end, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substring(String, int,
   * int)} NOT equals to expected value.
   *
   * @param expected               the expected result.
   * @param start                  the position to start from, negative means count back from the end of the String
   * @param end                    the position to end at (exclusive), negative means count back from the end of the
   *                               String
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringNotEquals(
      int start,
      int end,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).substringNotEquals(start, end, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringsBetween(String,
   * String, String)} contains to expected value.
   *
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringsBetweenContains(String open, String close, String expected) {
    return waitSubstringsBetweenContains(
        open, close, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringsBetween(String,
   * String, String)} contains to expected value.
   *
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringsBetweenContains(
      String open, String close, String expected, final int waitInSeconds) {
    return waitSubstringsBetweenContains(
        open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringsBetween(String,
   * String, String)} contains to expected value.
   *
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringsBetweenContains(
      String open,
      String close,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).substringsBetweenContains(open, close, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringsBetween(String,
   * String, String)} equals to expected value.
   *
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringsBetweenEquals(String open, String close, List<String> expected) {
    return waitSubstringsBetweenEquals(
        open, close, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringsBetween(String,
   * String, String)} equals to expected value.
   *
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringsBetweenEquals(
      String open, String close, List<String> expected, final int waitInSeconds) {
    return waitSubstringsBetweenEquals(
        open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringsBetween(String,
   * String, String)} equals to expected value.
   *
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringsBetweenEquals(
      String open,
      String close,
      List<String> expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).substringsBetweenEquals(open, close, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringsBetween(String,
   * String, String)} NOT contains to expected value.
   *
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringsBetweenNotContains(String open, String close, String expected) {
    return waitSubstringsBetweenNotContains(
        open, close, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringsBetween(String,
   * String, String)} NOT contains to expected value.
   *
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringsBetweenNotContains(
      String open, String close, String expected, final int waitInSeconds) {
    return waitSubstringsBetweenNotContains(
        open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringsBetween(String,
   * String, String)} NOT contains to expected value.
   *
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringsBetweenNotContains(
      String open,
      String close,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).substringsBetweenNotContains(open, close, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringsBetween(String,
   * String, String)} NOT equals to expected value.
   *
   * @param expected the expected result.
   * @param open     the String identifying the start of the substring, empty returns null
   * @param close    the String identifying the end of the substring, empty returns null
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringsBetweenNotEquals(String open, String close, List<String> expected) {
    return waitSubstringsBetweenNotEquals(
        open, close, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringsBetween(String,
   * String, String)} NOT equals to expected value.
   *
   * @param expected      the expected result.
   * @param open          the String identifying the start of the substring, empty returns null
   * @param close         the String identifying the end of the substring, empty returns null
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringsBetweenNotEquals(
      String open, String close, List<String> expected, final int waitInSeconds) {
    return waitSubstringsBetweenNotEquals(
        open, close, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until result of {@link CStringUtil#substringsBetween(String,
   * String, String)} NOT equals to expected value.
   *
   * @param expected               the expected result.
   * @param open                   the String identifying the start of the substring, empty returns null
   * @param close                  the String identifying the end of the substring, empty returns null
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitSubstringsBetweenNotEquals(
      String open,
      String close,
      List<String> expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).substringsBetweenNotEquals(open, close, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#trim(String)} value equals the
   * expected value.
   *
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitTrimmedValueEquals(String expected) {
    return waitTrimmedValueEquals(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#trim(String)} value equals the
   * expected value.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitTrimmedValueEquals(String expected, final int waitInSeconds) {
    return waitTrimmedValueEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#trim(String)} value equals the
   * expected value.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitTrimmedValueEquals(
      String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).trimmedValueEquals(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#trim(String)} value NOT equals the
   * expected value.
   *
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitTrimmedValueNotEquals(String expected) {
    return waitTrimmedValueNotEquals(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#trim(String)} value NOT equals the
   * expected value.
   *
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitTrimmedValueNotEquals(String expected, final int waitInSeconds) {
    return waitTrimmedValueNotEquals(
        expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#trim(String)} value NOT equals the
   * expected value.
   *
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitTrimmedValueNotEquals(
      String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).trimmedValueNotEquals(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#truncate(String, int)} value equals
   * the expected value.
   *
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitTruncatedValueEquals(int maxWidth, String expected) {
    return waitTruncatedValueEquals(
        maxWidth, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#truncate(String, int)} value equals
   * the expected value.
   *
   * @param maxWidth      maximum length of truncated string, must be positive
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitTruncatedValueEquals(int maxWidth, String expected, final int waitInSeconds) {
    return waitTruncatedValueEquals(
        maxWidth, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#truncate(String, int)} value equals
   * the expected value.
   *
   * @param maxWidth               maximum length of truncated string, must be positive
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitTruncatedValueEquals(
      int maxWidth, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).truncatedValueEquals(maxWidth, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#truncate(String, int, int)} value
   * equals the expected value.
   *
   * @param offset   left edge of string to start truncate from
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitTruncatedValueEquals(int offset, int maxWidth, String expected) {
    return waitTruncatedValueEquals(
        offset,
        maxWidth,
        expected,
        getDefaultWaitInSeconds(),
        getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#truncate(String, int, int)} value
   * equals the expected value.
   *
   * @param offset        left edge of string to start truncate from
   * @param maxWidth      maximum length of truncated string, must be positive
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitTruncatedValueEquals(
      int offset, int maxWidth, String expected, final int waitInSeconds) {
    return waitTruncatedValueEquals(
        offset, maxWidth, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#truncate(String, int, int)} value
   * equals the expected value.
   *
   * @param offset                 left edge of string to start truncate from
   * @param maxWidth               maximum length of truncated string, must be positive
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitTruncatedValueEquals(
      int offset,
      int maxWidth,
      String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).truncatedValueEquals(offset, maxWidth, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#truncate(String, int)} value equals
   * the expected value.
   *
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitTruncatedValueNotEquals(int maxWidth, String expected) {
    return waitTruncatedValueNotEquals(
        maxWidth, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#truncate(String, int)} value equals
   * the expected value.
   *
   * @param maxWidth      maximum length of truncated string, must be positive
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitTruncatedValueNotEquals(
      int maxWidth, String expected, final int waitInSeconds) {
    return waitTruncatedValueNotEquals(
        maxWidth, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#truncate(String, int)} value equals
   * the expected value.
   *
   * @param maxWidth               maximum length of truncated string, must be positive
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitTruncatedValueNotEquals(
      int maxWidth, String expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).truncatedValueNotEquals(maxWidth, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#truncate(String, int, int)} value
   * NOT equals the expected value.
   *
   * @param offset   left edge of string to start truncate from
   * @param maxWidth maximum length of truncated string, must be positive
   * @param expected the expected result.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitTruncatedValueNotEquals(int offset, int maxWidth, final String expected) {
    return waitTruncatedValueNotEquals(
        offset,
        maxWidth,
        expected,
        getDefaultWaitInSeconds(),
        getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#truncate(String, int, int)} value
   * NOT equals the expected value.
   *
   * @param offset        left edge of string to start truncate from
   * @param maxWidth      maximum length of truncated string, must be positive
   * @param expected      the expected result.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitTruncatedValueNotEquals(
      int offset, int maxWidth, final String expected, final int waitInSeconds) {
    return waitTruncatedValueNotEquals(
        offset, maxWidth, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till {@link CStringUtil#truncate(String, int, int)} value
   * NOT equals the expected value.
   *
   * @param offset                 left edge of string to start truncate from
   * @param maxWidth               maximum length of truncated string, must be positive
   * @param expected               the expected result.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitTruncatedValueNotEquals(
      int offset,
      int maxWidth,
      final String expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).truncatedValueNotEquals(offset, maxWidth, expected),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  private CStringState toState(Object e) {
    return () -> (String) e;
  }
}
