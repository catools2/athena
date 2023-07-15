package org.catools.common.extensions.verify.hard;

import lombok.extern.slf4j.Slf4j;
import org.catools.common.extensions.verify.interfaces.base.CIterableVerify;

import java.util.function.Predicate;

/**
 * Iterable verification class contains all verification method which is related to Iterable
 */
@Slf4j
public class CIterableVerification extends CBaseVerification {

  /**
   * Verify that actual collection contains the element which returns true from expected predicate.
   *
   * @param actual   value to compare
   * @param expected predicate
   * @param <C       extends Iterable<E>, E> type of collection elements
   */
  public <C extends Iterable<E>, E> void has(C actual, Predicate<E> expected) {
    toVerifier(actual).verifyHas(expected);
  }

  /**
   * Verify that actual collection contains the element which returns true from expected predicate.
   *
   * @param actual   value to compare
   * @param expected predicate
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   * @param <C       extends Iterable<E>, E> type of collection elements
   */
  public <C extends Iterable<E>, E> void has(
      C actual, Predicate<E> expected, final String message, final Object... params) {
    toVerifier(actual).verifyHas(expected, message, params);
  }

  /**
   * Verify that actual collection contains the expected element.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param <C       extends Iterable<E>, E> type of collection elements
   */
  public <C extends Iterable<E>, E> void contains(C actual, E expected) {
    toVerifier(actual).verifyContains(expected);
  }

  /**
   * Verify that actual collection contains the expected element.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   * @param <C       extends Iterable<E>, E> type of collection elements
   */
  public <C extends Iterable<E>, E> void contains(
      C actual, E expected, final String message, final Object... params) {
    toVerifier(actual).verifyContains(expected, message, params);
  }

  /**
   * Verify that actual collection contains all elements from the expected collection. Please note
   * that actual collection might have more elements.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param <C       extends Iterable<E>, E> type of collection elements
   */
  public <C extends Iterable<E>, E> void containsAll(C actual, C expected) {
    toVerifier(actual).verifyContainsAll(expected);
  }

  /**
   * Verify that actual collection contains all elements from the expected collection. Please note
   * that actual collection might have more elements.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   * @param <C       extends Iterable<E>, E> type of collection elements
   */
  public <C extends Iterable<E>, E> void containsAll(
      C actual, C expected, final String message, final Object... params) {
    toVerifier(actual).verifyContainsAll(expected, message, params);
  }

  /**
   * Verify that actual collection contains none of elements from the expected collection.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param <C       extends Iterable<E>, E> type of collection elements
   */
  public <C extends Iterable<E>, E> void containsNone(C actual, C expected) {
    toVerifier(actual).verifyContainsNone(expected);
  }

  /**
   * Verify that actual collection contains none of elements from the expected collection.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   * @param <C       extends Iterable<E>, E> type of collection elements
   */
  public <C extends Iterable<E>, E> void containsNone(
      C actual, C expected, final String message, final Object... params) {
    toVerifier(actual).verifyContainsNone(expected, message, params);
  }

  /**
   * Verify that actual collection either is empty or contains the expected element.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param <C       extends Iterable<E>, E> type of collection elements
   */
  public <C extends Iterable<E>, E> void emptyOrContains(C actual, E expected) {
    toVerifier(actual).verifyEmptyOrContains(expected);
  }

  /**
   * Verify that actual collection either is empty or contains the expected element.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   * @param <C       extends Iterable<E>, E> type of collection elements
   */
  public <C extends Iterable<E>, E> void emptyOrContains(
      C actual, E expected, final String message, final Object... params) {
    toVerifier(actual).verifyEmptyOrContains(expected, message, params);
  }

  /**
   * Verify that actual collection either is empty or does not contain the expected element.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param <C       extends Iterable<E>, E> type of collection elements
   */
  public <C extends Iterable<E>, E> void emptyOrNotContains(C actual, E expected) {
    toVerifier(actual).verifyEmptyOrNotContains(expected);
  }

  /**
   * Verify that actual collection either is empty or does not contain the expected element.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   * @param <C       extends Iterable<E>, E> type of collection elements
   */
  public <C extends Iterable<E>, E> void emptyOrNotContains(
      C actual, E expected, final String message, final Object... params) {
    toVerifier(actual).verifyEmptyOrNotContains(expected, message, params);
  }

  /**
   * Verify that actual and expected collections have the exact same elements. (Ignore element
   * order) First we compare that actual collection contains all expected collection elements and
   * then we verify that expected has all elements from actual.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param <C       extends Iterable<E>, E> type of collection elements
   */
  public <C extends Iterable<E>, E> void equals(C actual, C expected) {
    toVerifier(actual).verifyEquals(expected);
  }

  /**
   * Verify that actual and expected collections have the exact same elements. (Ignore element
   * order) First we compare that actual collection contains all expected collection elements and
   * then we verify that expected has all elements from actual.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   * @param <C       extends Iterable<E>, E> type of collection elements
   */
  public <C extends Iterable<E>, E> void equals(
      C actual, C expected, final String message, final Object... params) {
    toVerifier(actual).verifyEquals(expected, message, params);
  }

  /**
   * Verify that actual collection is empty.
   *
   * @param actual value to compare
   * @param <C     extends Iterable<E>, E> type of collection elements
   */
  public <C extends Iterable<E>, E> void isEmpty(C actual) {
    toVerifier(actual).verifyIsEmpty();
  }

  /**
   * Verify that actual collection is empty.
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   * @param <C      extends Iterable<E>, E> type of collection elements
   */
  public <C extends Iterable<E>, E> void isEmpty(
      C actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsEmpty(message, params);
  }

  /**
   * Verify that actual collection is not empty. (might contains null values)
   *
   * @param actual value to compare
   * @param <C     extends Iterable<E>, E> type of collection elements
   */
  public <C extends Iterable<E>, E> void isNotEmpty(C actual) {
    toVerifier(actual).verifyIsNotEmpty();
  }

  /**
   * Verify that actual collection is not empty. (might contains null values)
   *
   * @param actual  value to compare
   * @param message information about the purpose of this verification
   * @param params  parameters in case if message is a format {@link String#format}
   * @param <C      extends Iterable<E>, E> type of collection elements
   */
  public <C extends Iterable<E>, E> void isNotEmpty(
      C actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsNotEmpty(message, params);
  }

  /**
   * Verify that actual collection does not contain the expected element.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param <C       extends Iterable<E>, E> type of collection elements
   */
  public <C extends Iterable<E>, E> void notContains(C actual, E expected) {
    toVerifier(actual).verifyNotContains(expected);
  }

  /**
   * Verify that actual collection does not contain the expected element.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   * @param <C       extends Iterable<E>, E> type of collection elements
   */
  public <C extends Iterable<E>, E> void notContains(
      C actual, E expected, final String message, final Object... params) {
    toVerifier(actual).verifyNotContains(expected, message, params);
  }

  /**
   * Verify that actual collection does not contain all elements from the expected collection.
   * Please note that actual collection might have some elements but the point is to ensure that
   * not all expected elements are exist in it.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param <C       extends Iterable<E>, E> type of collection elements
   */
  public <C extends Iterable<E>, E> void notContainsAll(C actual, C expected) {
    toVerifier(actual).verifyNotContainsAll(expected);
  }

  /**
   * Verify that actual collection does not contain all elements from the expected collection.
   * Please note that actual collection might have some elements but the point is to ensure that
   * not all expected elements are exist in it.
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   * @param <C       extends Iterable<E>, E> type of collection elements
   */
  public <C extends Iterable<E>, E> void notContainsAll(
      C actual, C expected, final String message, final Object... params) {
    toVerifier(actual).verifyNotContainsAll(expected, message, params);
  }

  protected <E> CIterableVerify<E, Iterable<E>> toVerifier(Iterable<E> actual) {
    return () -> actual;
  }
}
