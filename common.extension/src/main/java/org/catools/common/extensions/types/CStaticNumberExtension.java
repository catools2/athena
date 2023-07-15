package org.catools.common.extensions.types;

import org.catools.common.extensions.states.interfaces.CNumberState;
import org.catools.common.extensions.verify.interfaces.waitVerifier.CNumberWaitVerifier;
import org.catools.common.extensions.verify.interfaces.waitVerify.CNumberWaitVerify;
import org.catools.common.extensions.wait.interfaces.CNumberWaiter;

/**
 * CStaticNumberExtension is an central interface where we extend all Number related interfaces so
 * adding new functionality will be much easier.
 */
public abstract class CStaticNumberExtension<N extends Number & Comparable<N>>
    implements CNumberState<N>, CNumberWaiter<N>, CNumberWaitVerify<N>, CNumberWaitVerifier<N> {

  @Override
  public String toString() {
    return _get().toString();
  }
}
