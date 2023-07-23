package org.catools.common.extensions.verify.soft;

import org.catools.common.extensions.verify.CVerificationQueue;
import org.catools.common.extensions.verify.hard.CDateVerification;
import org.catools.common.extensions.verify.interfaces.base.CDateVerify;

import java.util.Date;

/**
 * Date verification class contains all verification method which is related to Date
 *
 * @param <T> represent any classes which extent {@link CVerificationQueue}.
 */
public class CDateVerifierImpl<T extends CVerificationQueue> extends CDateVerification {

  private final T verifier;

  public CDateVerifierImpl(T verifier) {
    this.verifier = verifier;
  }

  @Override
  protected CDateVerify toVerifier(Date actual) {
    return new CDateVerify() {
      @Override
      public CVerificationQueue getVerificationQueue() {
        return verifier;
      }

      @Override
      public Date _get() {
        return actual;
      }
    };
  }
}
