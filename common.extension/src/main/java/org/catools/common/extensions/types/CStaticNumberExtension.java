package org.catools.common.extensions.types;

import org.catools.common.extensions.states.interfaces.CNumberState;
import org.catools.common.extensions.verify.interfaces.base.CNumberVerify;
import org.catools.common.extensions.verify.interfaces.verifier.CNumberVerifier;

/**
 * CStaticNumberExtension is an central interface where we extend all Number related interfaces so
 * adding new functionality will be much easier.
 */
public abstract class CStaticNumberExtension<N extends Number & Comparable<N>> implements CNumberState<N>, CNumberVerify<N>, CNumberVerifier<N> {

  @Override
  public String toString() {
    return _get().toString();
  }
}
