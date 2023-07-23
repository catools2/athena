package org.catools.common.extensions.verify.interfaces.waitVerifier;

import org.catools.common.collections.CList;
import org.catools.common.extensions.states.interfaces.CIterableState;
import org.catools.common.extensions.verify.CVerificationQueue;
import org.catools.common.extensions.verify.interfaces.verifier.CIterableVerifier;
import org.catools.common.utils.CIterableUtil;

import java.util.function.Predicate;

/**
 * CIterableVerifier is an interface for Iterable verification related methods.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extend cross all other objects:
 *
 * <p>Please Note that we should extend manually other verify and verifier classes for each new added verification here</p>
 */
public interface CIterableWaitVerifier<E, C extends Iterable<E>> extends CIterableVerifier<E, C>, CObjectWaitVerifier<C, CIterableState<E, C>> {

  /**
   * Verify that actual collection contains the expected element.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyContains(CVerificationQueue verifier, E expected, final int waitInSeconds) {
    verifyContains(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual collection contains the expected element.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(CVerificationQueue verifier, E expected, final int waitInSeconds, final String message, final Object... params) {
    verifyContains(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual collection contains the expected element.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContains(CVerificationQueue verifier, E expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContains(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Contains The Record"));
  }

  /**
   * Verify that actual collection contains the expected element.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(CVerificationQueue verifier, E expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, e) -> CIterableUtil.contains(_get(), e), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual collection contains all elements from the expected collection. Please note
   * that actual collection might have more elements.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyContainsAll(CVerificationQueue verifier, C expected, final int waitInSeconds) {
    verifyContainsAll(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual collection contains all elements from the expected collection. Please note
   * that actual collection might have more elements.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsAll(CVerificationQueue verifier, C expected, final int waitInSeconds, final String message, final Object... params) {
    verifyContainsAll(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual collection contains all elements from the expected collection. Please note
   * that actual collection might have more elements.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContainsAll(CVerificationQueue verifier, C expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContainsAll(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Contains All"));
  }

  /**
   * Verify that actual collection contains all elements from the expected collection. Please note
   * that actual collection might have more elements.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsAll(CVerificationQueue verifier, C expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, e) -> {
      if (expected == null) {
        return false;
      }
      CList<E> diff = new CList<>();
      _toState(a).containsAll(e, diff::add);
      if (!diff.isEmpty()) {
        logger.trace("Actual list does not contain following records:\n" + diff);
      }
      return diff.isEmpty();
    }, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual collection contains none of elements from the expected collection.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyContainsNone(CVerificationQueue verifier, C expected, final int waitInSeconds) {
    verifyContainsNone(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual collection contains none of elements from the expected collection.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsNone(CVerificationQueue verifier, C expected, final int waitInSeconds, final String message, final Object... params) {
    verifyContainsNone(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual collection contains none of elements from the expected collection.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContainsNone(CVerificationQueue verifier, C expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContainsNone(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Contains None"));
  }

  /**
   * Verify that actual collection contains none of elements from the expected collection.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsNone(CVerificationQueue verifier, C expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, e) -> {
      CList<E> diff = new CList<>();
      _toState(a).containsNone(expected, e1 -> diff.add(e1));
      if (!diff.isEmpty()) {
        logger.trace("Actual list contains following records:\n" + diff);
      }
      return !CIterableUtil.isEmpty(e) && diff.isEmpty();
    }, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual collection either is empty or contains the expected element.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyEmptyOrContains(CVerificationQueue verifier, E expected, final int waitInSeconds) {
    verifyEmptyOrContains(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual collection either is empty or contains the expected element.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEmptyOrContains(CVerificationQueue verifier, E expected, final int waitInSeconds, final String message, final Object... params) {
    verifyEmptyOrContains(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual collection either is empty or contains the expected element.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEmptyOrContains(CVerificationQueue verifier, E expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEmptyOrContains(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Empty Or Contains The Record"));
  }

  /**
   * Verify that actual collection either is empty or contains the expected element.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEmptyOrContains(CVerificationQueue verifier, E expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, e) -> _toState(a).emptyOrContains(e), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual collection either is empty or does not contain the expected element.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyEmptyOrNotContains(CVerificationQueue verifier, E expected, final int waitInSeconds) {
    verifyEmptyOrNotContains(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual collection either is empty or does not contain the expected element.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEmptyOrNotContains(CVerificationQueue verifier, E expected, final int waitInSeconds, final String message, final Object... params) {
    verifyEmptyOrNotContains(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual collection either is empty or does not contain the expected element.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEmptyOrNotContains(CVerificationQueue verifier, E expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEmptyOrNotContains(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Empty Or Not Contains The Record"));
  }

  /**
   * Verify that actual collection either is empty or does not contain the expected element.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEmptyOrNotContains(CVerificationQueue verifier, E expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, e) -> _toState(a).emptyOrNotContains(e), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual and expected collections have the exact same elements. (Ignore element
   * order) First we compare that actual collection contains all expected collection elements and
   * then we verify that expected has all elements from actual.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyEquals(CVerificationQueue verifier, C expected, final int waitInSeconds) {
    verifyEquals(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual and expected collections have the exact same elements. (Ignore element
   * order) First we compare that actual collection contains all expected collection elements and
   * then we verify that expected has all elements from actual.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEquals(CVerificationQueue verifier, C expected, final int waitInSeconds, final String message, final Object... params) {
    verifyEquals(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual and expected collections have the exact same elements. (Ignore element
   * order) First we compare that actual collection contains all expected collection elements and
   * then we verify that expected has all elements from actual.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEquals(CVerificationQueue verifier, C expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEquals(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Records Are Equals"));
  }

  /**
   * Verify that actual and expected collections have the exact same elements. (Ignore element
   * order) First we compare that actual collection contains all expected collection elements and
   * then we verify that expected has all elements from actual.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEquals(CVerificationQueue verifier, C expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, true, (a, e) -> {
      CList<E> diffActual = new CList<>();
      CList<E> diffExpected = new CList<>();
      boolean result = _toState(a).isEqual(expected, e1 -> diffActual.add(e1), e1 -> diffExpected.add(e1));
      if (!diffExpected.isEmpty()) {
        logger.trace("Actual list does not contain following records:\n" + diffExpected);
      }

      if (!diffActual.isEmpty()) {
        logger.trace("Expected list does not contain following records:\n" + diffActual);
      }
      return result;
    }, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual collection contains the expected element.
   *
   * @param verifier CTest, CVerifier or any other verification queue instance
   * @param expected predicate
   */
  default void verifyHas(final CVerificationQueue verifier, Predicate<E> expected) {
    verifyHas(verifier, expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual collection contains the expected element.
   *
   * @param verifier CTest, CVerifier or any other verification queue instance
   * @param expected predicate
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyHas(CVerificationQueue verifier, Predicate<E> expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, e) -> CIterableUtil.has(_get(), e), message, params);
  }

  /**
   * Verify that actual collection contains the element which returns true from expected predicate.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      predicate
   * @param waitInSeconds maximum wait time
   */
  default void verifyHas(CVerificationQueue verifier, Predicate<E> expected, final int waitInSeconds) {
    verifyHas(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual collection contains the element which returns true from expected predicate.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      predicate
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyHas(CVerificationQueue verifier, Predicate<E> expected, final int waitInSeconds, final String message, final Object... params) {
    verifyHas(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual collection contains the element which returns true from expected predicate.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               predicate
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyHas(CVerificationQueue verifier, Predicate<E> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyHas(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Has The Record With Defined Condition"));
  }

  /**
   * Verify that actual collection contains the element which returns true from expected predicate.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               predicate
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyHas(CVerificationQueue verifier, Predicate<E> expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, e) -> CIterableUtil.has(_get(), e), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual collection is empty.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsEmpty(final CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsEmpty(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual collection is empty.
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
   * Verify that actual collection is empty.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsEmpty(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsEmpty(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Empty"));
  }

  /**
   * Verify that actual collection is empty.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmpty(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, e) -> CIterableUtil.isEmpty(_get()), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual collection is not empty. (might contains null values)
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotEmpty(final CVerificationQueue verifier, final int waitInSeconds) {
    verifyIsNotEmpty(verifier, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual collection is not empty. (might contains null values)
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
   * Verify that actual collection is not empty. (might contains null values)
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotEmpty(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNotEmpty(verifier, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Not Empty"));
  }

  /**
   * Verify that actual collection is not empty. (might contains null values)
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotEmpty(CVerificationQueue verifier, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, true, (a, e) -> !CIterableUtil.isEmpty(_get()), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual collection does not contain the expected element.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotContains(CVerificationQueue verifier, E expected, final int waitInSeconds) {
    verifyNotContains(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual collection does not contain the expected element.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(CVerificationQueue verifier, E expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContains(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual collection does not contain the expected element.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContains(CVerificationQueue verifier, E expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContains(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Does Not Contains The Record"));
  }

  /**
   * Verify that actual collection does not contain the expected element.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(CVerificationQueue verifier, E expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, e) -> _toState(a).notContains(e), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual collection contains some but not all elements from the expected collection.
   * Please note that actual collection might have some elements but the point is to ensure that
   * not all expected elements are exist in it.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotContainsAll(final CVerificationQueue verifier, C expected, final int waitInSeconds) {
    verifyNotContainsAll(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual collection contains some but not all elements from the expected collection.
   * Please note that actual collection might have some elements but the point is to ensure that
   * not all expected elements are exist in it.
   *
   * @param verifier      CTest, CVerifier or any other verification queue instance
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContainsAll(CVerificationQueue verifier, C expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContainsAll(verifier, expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual collection contains some but not all elements from the expected collection.
   * Please note that actual collection might have some elements but the point is to ensure that
   * not all expected elements are exist in it.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContainsAll(CVerificationQueue verifier, C expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContainsAll(verifier, expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Does Not Contains All"));
  }

  /**
   * Verify that actual collection contains some but not all elements from the expected collection.
   * Please note that actual collection might have some elements but the point is to ensure that
   * not all expected elements are exist in it.
   *
   * @param verifier               CTest, CVerifier or any other verification queue instance
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContainsAll(CVerificationQueue verifier, C expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(verifier, expected, (a, e) -> _toState(a).notContainsAll(expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }
}
