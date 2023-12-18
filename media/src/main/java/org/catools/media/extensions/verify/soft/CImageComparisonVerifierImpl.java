package org.catools.media.extensions.verify.soft;

import org.catools.common.extensions.verify.CVerificationQueue;
import org.catools.media.extensions.verify.hard.CImageComparisonVerification;
import org.catools.media.extensions.verify.interfaces.base.CImageComparisonVerify;

/**
 * Boolean verification class contains all verification method which is related to Boolean
 *
 * @param <T> represent any classes which extent {@link CVerificationQueue}.
 */
public class CImageComparisonVerifierImpl<O, T extends CVerificationQueue> extends CImageComparisonVerification<O> {
  private final T verifier;

  public CImageComparisonVerifierImpl(T verifier) {
    this.verifier = verifier;
  }

  @Override
  protected CImageComparisonVerify<O> toVerifier(O actual) {
    return new CImageComparisonVerify<>() {
      @Override
      public CVerificationQueue getVerificationQueue() {
        return verifier;
      }

      @Override
      public O _get() {
        return actual;
      }
    };
  }
}
