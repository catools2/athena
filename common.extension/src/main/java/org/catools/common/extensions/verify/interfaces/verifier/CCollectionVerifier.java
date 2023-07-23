package org.catools.common.extensions.verify.interfaces.verifier;

import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CLinkedMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.collections.interfaces.CCollection;
import org.catools.common.extensions.base.CBaseCollectionExtension;
import org.catools.common.extensions.verify.CVerificationQueue;

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
public interface CCollectionVerifier<E, C extends Collection<E>> extends CBaseCollectionExtension<E, C>, CIterableVerifier<E, C> {

  /**
   * Verify the map size is equal to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   */
  default void verifySizeEquals(final CVerificationQueue verifier, int expected) {
    verifySizeEquals(verifier, expected, getDefaultMessage("Size Equals"));
  }

  /**
   * Verify the map size is equal to expected value.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifySizeEquals(CVerificationQueue verifier, int expected, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).sizeEquals(o2), message, params);
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   */
  default void verifySizeIsGreaterThan(final CVerificationQueue verifier, int expected) {
    verifySizeIsGreaterThan(verifier, expected, getDefaultMessage("Size Is Greater Than"));
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifySizeIsGreaterThan(CVerificationQueue verifier, int expected, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).sizeIsGreaterThan(o2), message, params);
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   */
  default void verifySizeIsLessThan(final CVerificationQueue verifier, int expected) {
    verifySizeIsLessThan(verifier, expected, getDefaultMessage("Size Is Less Than"));
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param verifier CVerificationQueue instance
   * @param expected value to compare
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifySizeIsLessThan(CVerificationQueue verifier, int expected, final String message, final Object... params) {
    _verify(verifier, expected, (o, o2) -> _toState(o).sizeIsLessThan(o2), message, params);
  }
}
