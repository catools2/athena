package org.catools.common.extensions.verify.hard;

import lombok.extern.slf4j.Slf4j;
import org.catools.common.extensions.verify.interfaces.base.CCollectionVerify;

import java.util.Collection;

/**
 * Collection verification class contains all verification method which is related to Collection
 */
@Slf4j
public class CCollectionVerification extends CIterableVerification {

  /**
   * Verify the map size is equal to expected value.
   *
   * @param expected value to compare
   */
  public <E, C extends Collection<E>> void verifySizeEquals(C actual, int expected) {
    toVerifier(actual).verifySizeEquals(expected);
  }

  /**
   * Verify the map size is equal to expected value.
   *
   * @param expected value to compare
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public <E, C extends Collection<E>> void verifySizeEquals(C actual, int expected, final String message, final Object... params) {
    toVerifier(actual).verifySizeEquals(expected, message, params);
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param expected value to compare
   */
  public <E, C extends Collection<E>> void verifySizeIsGreaterThan(C actual, int expected) {
    toVerifier(actual).verifySizeIsGreaterThan(expected);
  }

  /**
   * Verify that actual has value greater than expected.
   *
   * @param expected value to compare
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public <E, C extends Collection<E>> void verifySizeIsGreaterThan(C actual, int expected, final String message, final Object... params) {
    toVerifier(actual).verifySizeIsGreaterThan(expected, message, params);
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param expected value to compare
   */
  public <E, C extends Collection<E>> void verifySizeIsLessThan(C actual, int expected) {
    toVerifier(actual).verifySizeIsLessThan(expected);
  }

  /**
   * Verify that actual has value less than expected.
   *
   * @param expected value to compare
   * @param message  information about the purpose of verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public <E, C extends Collection<E>> void verifySizeIsLessThan(C actual, int expected, final String message, final Object... params) {
    toVerifier(actual).verifySizeIsLessThan(expected, message, params);
  }

  protected <E> CCollectionVerify<E, Collection<E>> toVerifier(Collection<E> actual) {
    return () -> actual;
  }
}
