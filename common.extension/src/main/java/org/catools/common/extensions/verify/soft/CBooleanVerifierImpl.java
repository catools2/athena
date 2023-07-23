package org.catools.common.extensions.verify.soft;

import org.catools.common.extensions.verify.CVerificationQueue;
import org.catools.common.extensions.verify.hard.CBooleanVerification;
import org.catools.common.extensions.verify.interfaces.base.CBooleanVerify;

/**
 * Boolean verification class contains all verification method which is related to Boolean
 *
 * @param <T> represent any classes which extent {@link CVerificationQueue}.
 */
public class CBooleanVerifierImpl<T extends CVerificationQueue> extends CBooleanVerification {
  private final T verifier;

  public CBooleanVerifierImpl(T verifier) {
    this.verifier = verifier;
  }

  @Override
  protected CBooleanVerify toVerifier(Boolean actual) {
    return new CBooleanVerify() {
      @Override
      public CVerificationQueue getVerificationQueue() {
        return verifier;
      }

      @Override
      public Boolean _get() {
        return actual;
      }
    };
  }
}
