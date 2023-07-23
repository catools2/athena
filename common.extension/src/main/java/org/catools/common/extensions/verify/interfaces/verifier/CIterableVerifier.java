package org.catools.common.extensions.verify.interfaces.verifier;

import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CLinkedMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.collections.interfaces.CIterable;
import org.catools.common.extensions.base.CBaseIterableExtension;
import org.catools.common.extensions.states.interfaces.CIterableState;
import org.catools.common.extensions.verify.CVerificationQueue;
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
 * @see Map
 * @see CIterable
 * @see CHashMap
 * @see CLinkedMap
 * @see CSet
 * @see CList
 */
public interface CIterableVerifier<E, C extends Iterable<E>> extends CBaseIterableExtension<E, C>, CObjectVerifier<C, CIterableState<E, C>>, CIterableVerify<E, C> {

  /**
   * Verify that actual collection contains the expected element.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   */
  default void verifyContains(final CVerificationQueue verifier, E expected) {
    verifyContains(verifier, expected, getDefaultMessage("Contains The Record"));
  }

  /**
   * Verify that actual collection contains the expected element.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final CVerificationQueue verifier, E expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, e) -> CIterableUtil.contains(_get(), e), message, params);
  }

  /**
   * Verify that actual collection contains all elements from the expected collection. Please note
   * that actual collection might have more elements.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   */
  default void verifyContainsAll(final CVerificationQueue verifier, C expected) {
    verifyContainsAll(verifier, expected, getDefaultMessage("Contains All"));
  }

  /**
   * Verify that actual collection contains all elements from the expected collection. Please note
   * that actual collection might have more elements.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsAll(final CVerificationQueue verifier, C expected, final String message, final Object... params) {
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
    }, message, params);
  }

  /**
   * Verify that actual collection contains none of elements from the expected collection.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   */
  default void verifyContainsNone(final CVerificationQueue verifier, C expected) {
    verifyContainsNone(verifier, expected, getDefaultMessage("Contains None"));
  }

  /**
   * Verify that actual collection contains none of elements from the expected collection.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsNone(final CVerificationQueue verifier, C expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, e) -> {
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
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   */
  default void verifyEmptyOrContains(final CVerificationQueue verifier, E expected) {
    verifyEmptyOrContains(verifier, expected, getDefaultMessage("Is Empty Or Contains The Record"));
  }

  /**
   * Verify that actual collection either is empty or contains the expected element.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyEmptyOrContains(final CVerificationQueue verifier, E expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, e) -> _toState(a).emptyOrContains(e), message, params);
  }

  /**
   * Verify that actual collection either is empty or does not contain the expected element.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   */
  default void verifyEmptyOrNotContains(final CVerificationQueue verifier, E expected) {
    verifyEmptyOrNotContains(verifier, expected, getDefaultMessage("Is Empty Or Not Contains The Record"));
  }

  /**
   * Verify that actual collection either is empty or does not contain the expected element.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyEmptyOrNotContains(final CVerificationQueue verifier, E expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, e) -> _toState(a).emptyOrNotContains(e), message, params);
  }

  /**
   * Verify that actual and expected collections have the exact same elements. (Ignore element
   * order) First we compare that actual collection contains all expected collection elements and
   * then we verify that expected has all elements from actual.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   */
  default void verifyEquals(final CVerificationQueue verifier, C expected) {
    verifyEquals(verifier, expected, getDefaultMessage("Records Are Equals"));
  }

  /**
   * Verify that actual and expected collections have the exact same elements. (Ignore element
   * order) First we compare that actual collection contains all expected collection elements and
   * then we verify that expected has all elements from actual.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyEquals(final CVerificationQueue verifier, C expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, e) -> {
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
   * @param verifier CVerificationQueue instance
   * @param expected predicate
   */
  default void verifyHas(final CVerificationQueue verifier, Predicate<E> expected) {
    verifyHas(verifier, expected, getDefaultMessage("Has The Record With Defined Condition"));
  }

  /**
   * Verify that actual collection contains the expected predication.
   *
   * @param verifier CVerificationQueue instance
   * @param expected predicate
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyHas(final CVerificationQueue verifier, Predicate<E> expected, final String message, final Object... params) {
    _verify(verifier, "true", (a, e) -> CIterableUtil.has(_get(), expected), message, params);
  }

  /**
   * Verify that actual collection does not contains the expected predication.
   *
   * @param verifier CVerificationQueue instance
   * @param expected predicate
   */
  default void verifyHasNot(final CVerificationQueue verifier, Predicate<E> expected) {
    verifyHasNot(verifier, expected, getDefaultMessage("Has Not The Record With Defined Condition"));
  }

  /**
   * Verify that actual collection does not contains the expected predication.
   *
   * @param verifier CVerificationQueue instance
   * @param expected predicate
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyHasNot(final CVerificationQueue verifier, Predicate<E> expected, final String message, final Object... params) {
    _verify(verifier, "true", (a, e) -> !CIterableUtil.has(_get(), expected), message, params);
  }

  /**
   * Verify that actual collection is empty.
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsEmpty(final CVerificationQueue verifier) {
    verifyIsEmpty(verifier, getDefaultMessage("Is Empty"));
  }

  /**
   * Verify that actual collection is empty.
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsEmpty(final CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, true, (a, e) -> CIterableUtil.isEmpty(_get()), message, params);
  }

  /**
   * Verify that actual collection is not empty. (might contains null values)
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsNotEmpty(final CVerificationQueue verifier) {
    verifyIsNotEmpty(verifier, getDefaultMessage("Is Not Empty"));
  }

  /**
   * Verify that actual collection is not empty. (might contains null values)
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotEmpty(final CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, true, (a, e) -> !CIterableUtil.isEmpty(_get()), message, params);
  }

  /**
   * Verify that actual collection does not contain the expected element.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   */
  default void verifyNotContains(final CVerificationQueue verifier, E expected) {
    verifyNotContains(verifier, expected, getDefaultMessage("Does Not Contains The Record"));
  }

  /**
   * Verify that actual collection does not contain the expected element.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final CVerificationQueue verifier, E expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, e) -> _toState(a).notContains(e), message, params);
  }

  /**
   * Verify that actual collection contains some but not all elements from the expected collection.
   * Please note that actual collection might have some elements but the point is to ensure that
   * not all expected elements are exist in it.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   */
  default void verifyNotContainsAll(final CVerificationQueue verifier, C expected) {
    verifyNotContainsAll(verifier, expected, getDefaultMessage("Does Not Contains All"));
  }

  /**
   * Verify that actual collection contains some but not all elements from the expected collection.
   * Please note that actual collection might have some elements but the point is to ensure that
   * not all expected elements are exist in it.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContainsAll(final CVerificationQueue verifier, C expected, final String message, final Object... params) {
    _verify(verifier, expected, (a, e) -> _toState(a).notContainsAll(expected), message, params);
  }
}
