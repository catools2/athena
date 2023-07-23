package org.catools.common.extensions.verify.soft;

import org.catools.common.extensions.verify.CVerificationQueue;
import org.catools.common.extensions.verify.hard.CFileVerification;
import org.catools.common.extensions.verify.interfaces.base.CFileVerify;

import java.io.File;

/**
 * File Content verification class contains all verification method which is related to File and its
 * content
 *
 * @param <T> represent any classes which extent {@link CVerificationQueue}.
 */
public class CFileVerifierImpl<T extends CVerificationQueue> extends CFileVerification {
  private final T verifier;

  public CFileVerifierImpl(T verifier) {
    this.verifier = verifier;
  }

  @Override
  protected CFileVerify toVerifier(File actual) {
    return new CFileVerify() {
      @Override
      public CVerificationQueue getVerificationQueue() {
        return verifier;
      }

      @Override
      public File _get() {
        return actual;
      }
    };
  }
}
