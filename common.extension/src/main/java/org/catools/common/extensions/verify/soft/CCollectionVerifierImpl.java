package org.catools.common.extensions.verify.soft;

import org.catools.common.extensions.verify.CVerificationQueue;
import org.catools.common.extensions.verify.hard.CCollectionVerification;
import org.catools.common.extensions.verify.interfaces.base.CCollectionVerify;
import org.catools.common.extensions.verify.interfaces.verifier.CCollectionVerifier;

import java.util.Collection;

/**
 * Collection verification class contains all verification method which is related to Collection
 *
 * @param <T> represent any classes which extent {@link CVerificationQueue}.
 */
public class CCollectionVerifierImpl<T extends CVerificationQueue> extends CCollectionVerification {
  private final T verifier;

  public CCollectionVerifierImpl(T verifier) {
    this.verifier = verifier;
  }

  @Override
  protected <E> CCollectionVerify<E, Collection<E>> toVerifier(Collection<E> actual) {
    return new CCollectionVerifier<>() {
      @Override
      public CVerificationQueue getVerificationQueue() {
        return verifier;
      }

      @Override
      public Collection<E> _get() {
        return actual;
      }
    };
  }
}