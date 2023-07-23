package org.catools.common.extensions.types;

import org.catools.common.extensions.states.interfaces.CObjectState;
import org.catools.common.extensions.verify.interfaces.base.CObjectVerify;
import org.catools.common.extensions.verify.interfaces.verifier.CObjectVerifier;

/**
 * CStaticObjectExtension is an central interface where we extend all Object related interfaces so
 * adding new functionality will be much easier.
 */
public abstract class CStaticObjectExtension<O> implements
    CObjectState<O>,
    CObjectVerify<O, CObjectState<O>>,
    CObjectVerifier<O, CObjectState<O>> {

  @Override
  public String toString() {
    return _get().toString();
  }

  @Override
  public CObjectState<O> _toState(O o) {
    return () -> o;
  }
}
