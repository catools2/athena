package org.catools.common.extensions.verify.soft;

import org.catools.common.extensions.verify.CVerificationQueue;
import org.catools.common.extensions.verify.hard.CNumberVerification;
import org.catools.common.extensions.verify.interfaces.base.CNumberVerify;

/**
 * Number verification class contains all verification method which is related to Number
 * (Int,Double,Long,Float,BigDecimal)
 *
 * @param <T> represent any classes which extent {@link CVerificationQueue}.
 */
public class CNumberVerifierImpl<T extends CVerificationQueue, N extends Number & Comparable<N>> extends CNumberVerification<N> {

  private final T verifier;

  public CNumberVerifierImpl(T verifier) {
    this.verifier = verifier;
  }

  @Override
  protected CNumberVerify<N> toVerifier(N actual) {
    return new CNumberVerify<>() {
      @Override
      public CVerificationQueue getVerificationQueue() {
        return verifier;
      }

      @Override
      public N _get() {
        return actual;
      }
    };
  }
}
