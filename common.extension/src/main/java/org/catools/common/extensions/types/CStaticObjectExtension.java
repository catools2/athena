package org.catools.common.extensions.types;

import org.catools.common.extensions.states.interfaces.CObjectState;
import org.catools.common.extensions.verify.interfaces.waitVerifier.CObjectWaitVerifier;
import org.catools.common.extensions.verify.interfaces.waitVerify.CObjectWaitVerify;
import org.catools.common.extensions.wait.interfaces.CObjectWaiter;

import java.util.Objects;

/**
 * CStaticObjectExtension is an central interface where we extend all Object related interfaces so
 * adding new functionality will be much easier.
 */
public abstract class CStaticObjectExtension<O> implements
    CObjectState<O>,
    CObjectWaiter<O>,
    CObjectWaitVerify<O, CObjectState<O>>,
    CObjectWaitVerifier<O, CObjectState<O>> {

  @Override
  public String toString() {
    return _get().toString();
  }

  @Override
  public CObjectState<O> _toState(O o) {
    return new CObjectState<>() {
      @Override
      public boolean isEqual(Object expected) {
        return Objects.equals(_get(), expected);
      }

      @Override
      public O _get() {
        return o;
      }
    };
  }
}
