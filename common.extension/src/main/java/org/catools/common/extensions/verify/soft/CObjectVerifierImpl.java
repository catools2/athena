package org.catools.common.extensions.verify.soft;

import org.catools.common.extensions.states.interfaces.CObjectState;
import org.catools.common.extensions.verify.CVerificationQueue;
import org.catools.common.extensions.verify.hard.CObjectVerification;
import org.catools.common.extensions.verify.interfaces.base.CObjectVerify;

import java.util.Objects;

/**
 * Object verification class contains all verification method which is related to Object
 *
 * @param <T> represent any classes which extent {@link CVerificationQueue}.
 */
public class CObjectVerifierImpl<T extends CVerificationQueue> extends CObjectVerification {

  private final T verifier;

  public CObjectVerifierImpl(T verifier) {
    this.verifier = verifier;
  }

  @Override
  protected CObjectVerify<Object, CObjectState<Object>> toVerifier(Object actual) {
    return new CObjectVerify<>() {
      @Override
      public CObjectState<Object> _toState(Object o) {
        return new CObjectState<>() {
          @Override
          public boolean isEqual(Object expected) {
            return Objects.equals(_get(), expected);
          }

          @Override
          public Object _get() {
            return o;
          }
        };
      }

      @Override
      public CVerificationQueue getVerificationQueue() {
        return verifier;
      }

      @Override
      public Object _get() {
        return actual;
      }
    };
  }
}
