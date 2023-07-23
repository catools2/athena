package org.catools.common.extensions.verify.soft;

import org.catools.common.extensions.verify.CVerificationQueue;
import org.catools.common.extensions.verify.hard.CStringVerification;
import org.catools.common.extensions.verify.interfaces.base.CStringVerify;

/**
 * String verification class contains all verification method which is related to String
 *
 * @param <T> represent any classes which extent {@link CVerificationQueue}.
 */
public class CStringVerifierImpl<T extends CVerificationQueue> extends CStringVerification {
  private final T verifier;

  public CStringVerifierImpl(T verifier) {
    this.verifier = verifier;
  }

  @Override
  protected CStringVerify toVerifier(String actual) {
    return new CStringVerify() {
      @Override
      public CVerificationQueue getVerificationQueue() {
        return verifier;
      }

      @Override
      public String _get() {
        return actual;
      }
    };
  }
}
