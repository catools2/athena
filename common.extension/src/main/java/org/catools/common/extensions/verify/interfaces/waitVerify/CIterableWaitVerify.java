package org.catools.common.extensions.verify.interfaces.waitVerify;

import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CLinkedMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.collections.interfaces.CIterable;
import org.catools.common.extensions.states.interfaces.CIterableState;
import org.catools.common.extensions.verify.hard.CIterableVerification;
import org.catools.common.extensions.verify.interfaces.base.CIterableVerify;
import org.catools.common.utils.CIterableUtil;

import java.util.Map;
import java.util.function.Predicate;

/**
 * CIterableVerifier is an interface for Iterable verification related methods.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 *
 * <p>Please Note that we should extend manually {@link
 * CIterableVerification} for each new added verification here
 *
 * @see Map
 * @see CIterable
 * @see CHashMap
 * @see CLinkedMap
 * @see CSet
 * @see CList
 */
public interface CIterableWaitVerify<E, C extends Iterable<E>> extends CIterableVerify<E, C>, CObjectWaitVerify<C, CIterableState<E, C>> {

  /**
   * Verify that actual collection contains the expected element.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyContains(E expected, final int waitInSeconds) {
    verifyContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual collection contains the expected element.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(E expected, final int waitInSeconds, final String message, final Object... params) {
    verifyContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual collection contains the expected element.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContains(E expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContains(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Contains The Record"));
  }

  /**
   * Verify that actual collection contains the expected element.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(E expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, e) -> CIterableUtil.contains(_get(), e), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual collection contains all elements from the expected collection. Please note
   * that actual collection might have more elements.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyContainsAll(C expected, final int waitInSeconds) {
    verifyContainsAll(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual collection contains all elements from the expected collection. Please note
   * that actual collection might have more elements.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsAll(C expected, final int waitInSeconds, final String message, final Object... params) {
    verifyContainsAll(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual collection contains all elements from the expected collection. Please note
   * that actual collection might have more elements.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContainsAll(C expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContainsAll(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Contains All"));
  }

  /**
   * Verify that actual collection contains all elements from the expected collection. Please note
   * that actual collection might have more elements.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsAll(C expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, e) -> {
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
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyContainsNone(C expected, final int waitInSeconds) {
    verifyContainsNone(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual collection contains none of elements from the expected collection.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsNone(C expected, final int waitInSeconds, final String message, final Object... params) {
    verifyContainsNone(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual collection contains none of elements from the expected collection.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContainsNone(C expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContainsNone(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Contains None"));
  }

  /**
   * Verify that actual collection contains none of elements from the expected collection.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsNone(C expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, e) -> {
      CList<E> diff = new CList<>();
      _toState(a).containsNone(expected, diff::add);
      if (!diff.isEmpty()) {
        logger.trace("Actual list contains following records:\n" + diff);
      }
      return !CIterableUtil.isEmpty(e) && diff.isEmpty();
    }, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual collection either is empty or contains the expected element.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyEmptyOrContains(E expected, final int waitInSeconds) {
    verifyEmptyOrContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual collection either is empty or contains the expected element.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEmptyOrContains(E expected, final int waitInSeconds, final String message, final Object... params) {
    verifyEmptyOrContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual collection either is empty or contains the expected element.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEmptyOrContains(E expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEmptyOrContains(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Empty Or Contains The Record"));
  }

  /**
   * Verify that actual collection either is empty or contains the expected element.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEmptyOrContains(E expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, e) -> _toState(a).emptyOrContains(e), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual collection either is empty or does not contain the expected element.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyEmptyOrNotContains(E expected, final int waitInSeconds) {
    verifyEmptyOrNotContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual collection either is empty or does not contain the expected element.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEmptyOrNotContains(E expected, final int waitInSeconds, final String message, final Object... params) {
    verifyEmptyOrNotContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual collection either is empty or does not contain the expected element.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEmptyOrNotContains(E expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEmptyOrNotContains(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Empty Or Not Contains The Record"));
  }

  /**
   * Verify that actual collection either is empty or does not contain the expected element.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEmptyOrNotContains(E expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, e) -> _toState(a).emptyOrNotContains(e), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual and expected collections have the exact same elements. (Ignore element
   * order) First we compare that actual collection contains all expected collection elements and
   * then we verify that expected has all elements from actual.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyEquals(C expected, final int waitInSeconds) {
    verifyEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual and expected collections have the exact same elements. (Ignore element
   * order) First we compare that actual collection contains all expected collection elements and
   * then we verify that expected has all elements from actual.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEquals(C expected, final int waitInSeconds, final String message, final Object... params) {
    verifyEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual and expected collections have the exact same elements. (Ignore element
   * order) First we compare that actual collection contains all expected collection elements and
   * then we verify that expected has all elements from actual.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEquals(C expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEquals(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Records Are Equals"));
  }

  /**
   * Verify that actual and expected collections have the exact same elements. (Ignore element
   * order) First we compare that actual collection contains all expected collection elements and
   * then we verify that expected has all elements from actual.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEquals(C expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, true, (a, e) -> {
      CList<E> diffActual = new CList<>();
      CList<E> diffExpected = new CList<>();
      boolean result = _toState(a).isEqual(expected, diffActual::add, diffExpected::add);
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
   * @param expected predicate
   */
  default void verifyHas(Predicate<E> expected) {
    verifyHas(expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual collection contains the expected element.
   *
   * @param expected predicate
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyHas(Predicate<E> expected, final String message, final Object... params) {
    _verify(expected, (a, e) -> CIterableUtil.has(_get(), e), message, params);
  }

  /**
   * Verify that actual collection contains the element which returns true from expected predicate.
   *
   * @param expected      predicate
   * @param waitInSeconds maximum wait time
   */
  default void verifyHas(Predicate<E> expected, final int waitInSeconds) {
    verifyHas(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual collection contains the element which returns true from expected predicate.
   *
   * @param expected      predicate
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyHas(Predicate<E> expected, final int waitInSeconds, final String message, final Object... params) {
    verifyHas(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual collection contains the element which returns true from expected predicate.
   *
   * @param expected               predicate
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyHas(Predicate<E> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyHas(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Has The Record With Defined Condition"));
  }

  /**
   * Verify that actual collection contains the element which returns true from expected predicate.
   *
   * @param expected               predicate
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyHas(Predicate<E> expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, e) -> CIterableUtil.has(_get(), e), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual collection is empty.
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsEmpty(final int waitInSeconds) {
    verifyIsEmpty(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual collection is empty.
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmpty(final int waitInSeconds, final String message, final Object... params) {
    verifyIsEmpty(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual collection is empty.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsEmpty(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsEmpty(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Empty"));
  }

  /**
   * Verify that actual collection is empty.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmpty(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, e) -> CIterableUtil.isEmpty(_get()), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual collection is not empty. (might contains null values)
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotEmpty(final int waitInSeconds) {
    verifyIsNotEmpty(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual collection is not empty. (might contains null values)
   *
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotEmpty(final int waitInSeconds, final String message, final Object... params) {
    verifyIsNotEmpty(waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual collection is not empty. (might contains null values)
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotEmpty(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNotEmpty(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Not Empty"));
  }

  /**
   * Verify that actual collection is not empty. (might contains null values)
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotEmpty(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(true, (a, e) -> !CIterableUtil.isEmpty(_get()), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual collection does not contain the expected element.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotContains(E expected, final int waitInSeconds) {
    verifyNotContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual collection does not contain the expected element.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(E expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContains(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual collection does not contain the expected element.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContains(E expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContains(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Does Not Contains The Record"));
  }

  /**
   * Verify that actual collection does not contain the expected element.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(E expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, e) -> _toState(a).notContains(e), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual collection contains some but not all elements from the expected collection.
   * Please note that actual collection might have some elements but the point is to ensure that
   * not all expected elements are exist in it.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotContainsAll(C expected, final int waitInSeconds) {
    verifyNotContainsAll(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Verify that actual collection contains some but not all elements from the expected collection.
   * Please note that actual collection might have some elements but the point is to ensure that
   * not all expected elements are exist in it.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContainsAll(C expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContainsAll(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds(), message, params);
  }

  /**
   * Verify that actual collection contains some but not all elements from the expected collection.
   * Please note that actual collection might have some elements but the point is to ensure that
   * not all expected elements are exist in it.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContainsAll(C expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContainsAll(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Does Not Contains All"));
  }

  /**
   * Verify that actual collection contains some but not all elements from the expected collection.
   * Please note that actual collection might have some elements but the point is to ensure that
   * not all expected elements are exist in it.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContainsAll(C expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, e) -> _toState(a).notContainsAll(expected), waitInSeconds, intervalInMilliSeconds, message, params);
  }
}
