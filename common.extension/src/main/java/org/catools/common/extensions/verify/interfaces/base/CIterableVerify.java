package org.catools.common.extensions.verify.interfaces.base;

import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CLinkedMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.collections.interfaces.CIterable;
import org.catools.common.extensions.base.CBaseIterableExtension;
import org.catools.common.extensions.states.interfaces.CIterableState;
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
 * @see Map
 * @see CIterable
 * @see CHashMap
 * @see CLinkedMap
 * @see CSet
 * @see CList
 */
public interface CIterableVerify<E, C extends Iterable<E>> extends CBaseIterableExtension<E, C>, CObjectVerify<C, CIterableState<E, C>> {


  /**
   * Verify that actual collection contains the expected element.
   *
   * @param expected value to compare
   */
  default void verifyContains(E expected) {
    verifyContains(expected, getDefaultMessage("Contains The Record"));
  }

  /**
   * Verify that actual collection contains the expected element.
   *
   * @param expected value to compare
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(E expected, final String message, final Object... params) {
    _verify(expected, (a, e) -> CIterableUtil.contains(_get(), e), message, params);
  }

  /**
   * Verify that actual collection contains all elements from the expected collection. Please note
   * that actual collection might have more elements.
   *
   * @param expected value to compare
   */
  default void verifyContainsAll(C expected) {
    verifyContainsAll(expected, getDefaultMessage("Contains All"));
  }

  /**
   * Verify that actual collection contains all elements from the expected collection. Please note
   * that actual collection might have more elements.
   *
   * @param expected value to compare
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsAll(C expected, final String message, final Object... params) {
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
    }, message, params);
  }

  /**
   * Verify that actual collection contains none of elements from the expected collection.
   *
   * @param expected value to compare
   */
  default void verifyContainsNone(C expected) {
    verifyContainsNone(expected, getDefaultMessage("Contains None"));
  }

  /**
   * Verify that actual collection contains none of elements from the expected collection.
   *
   * @param expected value to compare
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsNone(C expected, final String message, final Object... params) {
    _verify(expected, (a, e) -> {
      CList<E> diff = new CList<>();
      _toState(a).containsNone(expected, diff::add);
      if (!diff.isEmpty()) {
        logger.trace("Actual list contains following records:\n" + diff);
      }
      return !CIterableUtil.isEmpty(e) && diff.isEmpty();
    }, message, params);
  }

  /**
   * Verify that actual collection either is empty or contains the expected element.
   *
   * @param expected value to compare
   */
  default void verifyEmptyOrContains(E expected) {
    verifyEmptyOrContains(expected, getDefaultMessage("Is Empty Or Contains The Record"));
  }

  /**
   * Verify that actual collection either is empty or contains the expected element.
   *
   * @param expected value to compare
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyEmptyOrContains(E expected, final String message, final Object... params) {
    _verify(expected, (a, e) -> _toState(a).emptyOrContains(e), message, params);
  }

  /**
   * Verify that actual collection either is empty or does not contain the expected element.
   *
   * @param expected value to compare
   */
  default void verifyEmptyOrNotContains(E expected) {
    verifyEmptyOrNotContains(expected, getDefaultMessage("Is Empty Or Not Contains The Record"));
  }

  /**
   * Verify that actual collection either is empty or does not contain the expected element.
   *
   * @param expected value to compare
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyEmptyOrNotContains(E expected, final String message, final Object... params) {
    _verify(expected, (a, e) -> _toState(a).emptyOrNotContains(e), message, params);
  }

  /**
   * Verify that actual and expected collections have the exact same elements. (Ignore element
   * order) First we compare that actual collection contains all expected collection elements and
   * then we verify that expected has all elements from actual.
   *
   * @param expected value to compare
   */
  default void verifyEquals(C expected) {
    verifyEquals(expected, getDefaultMessage("Records Are Equals"));
  }

  /**
   * Verify that actual and expected collections have the exact same elements. (Ignore element
   * order) First we compare that actual collection contains all expected collection elements and
   * then we verify that expected has all elements from actual.
   *
   * @param expected value to compare
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyEquals(C expected, final String message, final Object... params) {
    _verify(expected, (a, e) -> {
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
    }, message, params);
  }

  /**
   * Verify that actual collection contains the expected predication.
   *
   * @param expected predicate
   */
  default void verifyHas(Predicate<E> expected) {
    verifyHas(expected, getDefaultMessage("Has The Record With Defined Condition"));
  }

  /**
   * Verify that actual collection contains the expected predication.
   *
   * @param expected predicate
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyHas(Predicate<E> expected, final String message, final Object... params) {
    _verify("true", (a, e) -> CIterableUtil.has(_get(), expected), message, params);
  }

  /**
   * Verify that actual collection does not contains the expected predication.
   *
   * @param expected predicate
   */
  default void verifyHasNot(Predicate<E> expected) {
    verifyHasNot(expected, getDefaultMessage("Has Not The Record With Defined Condition"));
  }

  /**
   * Verify that actual collection does not contains the expected predication.
   *
   * @param expected predicate
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyHasNot(Predicate<E> expected, final String message, final Object... params) {
    _verify("true", (a, e) -> !CIterableUtil.has(_get(), expected), message, params);
  }

  /**
   * Verify that actual collection is empty.
   */
  default void verifyIsEmpty() {
    verifyIsEmpty(getDefaultMessage("Is Empty"));
  }

  /**
   * Verify that actual collection is empty.
   *
   * @param message information about the purpose of verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmpty(final String message, final Object... params) {
    _verify(true, (a, e) -> CIterableUtil.isEmpty(_get()), message, params);
  }

  /**
   * Verify that actual collection is not empty. (might contains null values)
   */
  default void verifyIsNotEmpty() {
    verifyIsNotEmpty(getDefaultMessage("Is Not Empty"));
  }

  /**
   * Verify that actual collection is not empty. (might contains null values)
   *
   * @param message information about the purpose of verification
   * @param params  parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotEmpty(final String message, final Object... params) {
    _verify(true, (a, e) -> !CIterableUtil.isEmpty(_get()), message, params);
  }

  /**
   * Verify that actual collection does not contain the expected element.
   *
   * @param expected value to compare
   */
  default void verifyNotContains(E expected) {
    verifyNotContains(expected, getDefaultMessage("Does Not Contains The Record"));
  }

  /**
   * Verify that actual collection does not contain the expected element.
   *
   * @param expected value to compare
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(E expected, final String message, final Object... params) {
    _verify(expected, (a, e) -> _toState(a).notContains(e), message, params);
  }

  /**
   * Verify that actual collection contains some but not all elements from the expected collection.
   * Please note that actual collection might have some elements but the point is to ensure that
   * not all expected elements are exist in it.
   *
   * @param expected value to compare
   */
  default void verifyNotContainsAll(C expected) {
    verifyNotContainsAll(expected, getDefaultMessage("Does Not Contains All"));
  }

  /**
   * Verify that actual collection contains some but not all elements from the expected collection.
   * Please note that actual collection might have some elements but the point is to ensure that
   * not all expected elements are exist in it.
   *
   * @param expected value to compare
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContainsAll(C expected, final String message, final Object... params) {
    _verify(expected, (a, e) -> _toState(a).notContainsAll(expected), message, params);
  }
}
