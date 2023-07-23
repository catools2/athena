package org.catools.common.extensions.verify.interfaces.verifier;

import org.catools.common.extensions.base.CBaseFileExtension;
import org.catools.common.extensions.states.interfaces.CFileState;
import org.catools.common.extensions.verify.CVerificationQueue;
import org.catools.common.io.CFile;

import java.io.File;

/**
 * CFileVerifier is an interface for File verification related methods.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 */
public interface CFileVerifier extends CBaseFileExtension, CObjectVerifier<File, CFileState> {

  /**
   * Verify that actual and expected file have the exact same content.
   *
   * @param verifier     CVerificationQueue instance
   * @param expectedFile file to compare
   */
  default void verifyEqualsStringContent(final CVerificationQueue verifier, final File expectedFile) {
    verifyEqualsStringContent(verifier, expectedFile, getDefaultMessage(("String Content Equals")));
  }

  /**
   * Verify that actual and expected file have the exact same content.
   *
   * @param verifier     CVerificationQueue instance
   * @param expectedFile file to compare
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsStringContent(final CVerificationQueue verifier, final File expectedFile, final String message, final Object... params) {
    _verify(verifier, expectedFile, (f1, f2) -> _toState(f1).equalsStringContent(f2), message, params);
  }

  /**
   * Verify that the file exists
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyExists(final CVerificationQueue verifier) {
    verifyExists(verifier, getDefaultMessage(("Exists")));
  }

  /**
   * Verify that the file exists
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyExists(final CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, true, (file, aBoolean) -> _get().exists(), message, params);
  }

  /**
   * Verify that the file does not exists
   *
   * @param verifier CVerificationQueue instance
   */
  default void verifyIsNotExists(final CVerificationQueue verifier) {
    verifyIsNotExists(verifier, getDefaultMessage(("Does Not Exist")));
  }

  /**
   * Verify that the file does not exists
   *
   * @param verifier CVerificationQueue instance
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyIsNotExists(final CVerificationQueue verifier, final String message, final Object... params) {
    _verify(verifier, true, (file, aBoolean) -> !_get().exists(), message, params);
  }

  /**
   * Verify that actual and expected file does not have the exact same content.
   *
   * @param verifier     CVerificationQueue instance
   * @param expectedFile file to compare
   */
  default void verifyNotEqualsStringContent(final CVerificationQueue verifier, final CFile expectedFile) {
    verifyNotEqualsStringContent(verifier, expectedFile, getDefaultMessage(("String Content Not Equals")));
  }

  /**
   * Verify that actual and expected file does not have the exact same content.
   *
   * @param verifier     CVerificationQueue instance
   * @param expectedFile file to compare
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEqualsStringContent(final CVerificationQueue verifier, final CFile expectedFile, final String message, final Object... params) {
    _verify(verifier, expectedFile, (f1, f2) -> _toState(f1).notEqualsStringContent(f2), message, params);
  }
}
