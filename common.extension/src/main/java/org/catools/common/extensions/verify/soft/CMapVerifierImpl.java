package org.catools.common.extensions.verify.soft;

import org.catools.common.extensions.verify.CVerificationQueue;
import org.catools.common.extensions.verify.hard.CMapVerification;
import org.catools.common.extensions.verify.interfaces.base.CMapVerify;

import java.util.Map;

/**
 * Map verification class contains all verification method which is related to Map
 *
 * @param <T> represent any classes which extent {@link org.catools.common.extensions.verify.CVerificationQueue}.
 */
public class CMapVerifierImpl<T extends CVerificationQueue> extends CMapVerification {
  private final T verifier;

  public CMapVerifierImpl(T verifier) {
    this.verifier = verifier;
  }

  @Override
  protected <K, V> CMapVerify<K, V> toVerifier(Map<K, V> actual) {
    return new CMapVerify<K, V>() {
      @Override
      public CVerificationQueue getVerificationQueue() {
        return verifier;
      }

      @Override
      public Map<K, V> _get() {
        return actual;
      }
    };
  }
}
