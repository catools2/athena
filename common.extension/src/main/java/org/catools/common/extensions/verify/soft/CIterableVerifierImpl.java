package org.catools.common.extensions.verify.soft;

import org.catools.common.extensions.verify.CVerificationQueue;
import org.catools.common.extensions.verify.hard.CIterableVerification;
import org.catools.common.extensions.verify.interfaces.base.CIterableVerify;
import org.catools.common.extensions.verify.interfaces.verifier.CIterableVerifier;

/**
 * Iterable verification class contains all verification method which is related to Iterable
 *
 * @param <T> represent any classes which extent {@link org.catools.common.extensions.verify.CVerificationQueue}.
 */
public class CIterableVerifierImpl<T extends CVerificationQueue> extends CIterableVerification {
  private final T verifier;

  public CIterableVerifierImpl(T verifier) {
    this.verifier = verifier;
  }

  @Override
  protected <E> CIterableVerify<E, Iterable<E>> toVerifier(Iterable<E> actual) {
    return new CIterableVerifier<>() {
      @Override
      public CVerificationQueue getVerificationQueue() {
        return verifier;
      }

      @Override
      public Iterable<E> _get() {
        return actual;
      }
    };
  }
}
