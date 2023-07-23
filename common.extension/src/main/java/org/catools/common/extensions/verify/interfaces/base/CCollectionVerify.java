package org.catools.common.extensions.verify.interfaces.base;

import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CLinkedMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.collections.interfaces.CCollection;
import org.catools.common.extensions.base.CBaseCollectionExtension;

import java.util.Collection;
import java.util.Map;

/**
 * CCollectionVerifier is an interface for Collection verification related methods.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 *
 * @see Map
 * @see CCollection
 * @see CHashMap
 * @see CLinkedMap
 * @see CSet
 * @see CList
 */
public interface CCollectionVerify<E, C extends Collection<E>> extends CBaseCollectionExtension<E, C>, CIterableVerify<E, C> {

  /**
   * Verify the map size is equal to expected value.
   *
   * @param expected value to compare
   */
  default void verifySizeEquals(int expected) {
    verifySizeEquals(expected, getDefaultMessage("Size Equals"));
  }

  /**
   * Verify the map size is equal to expected value.
   *
   * @param expected value to compare
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifySizeEquals(
      int expected,
      final String message,
      final Object... params) {
    _verify(expected, (o, o2) -> _toState(o).sizeEquals(o2), message, params);
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param expected value to compare
   */
  default void verifySizeIsGreaterThan(int expected) {
    verifySizeIsGreaterThan(expected, getDefaultMessage("Size Is Greater Than"));
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param expected value to compare
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifySizeIsGreaterThan(
      int expected,
      final String message,
      final Object... params) {
    _verify(
        expected,
        (o, o2) -> _toState(o).sizeIsGreaterThan(o2),
        message,
        params);
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param expected value to compare
   */
  default void verifySizeIsLessThan(int expected) {
    verifySizeIsLessThan(expected, getDefaultMessage("Size Is Less Than"));
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param expected value to compare
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifySizeIsLessThan(
      int expected,
      final String message,
      final Object... params) {
    _verify(
        expected,
        (o, o2) -> _toState(o).sizeIsLessThan(o2),
        message,
        params);
  }
}
