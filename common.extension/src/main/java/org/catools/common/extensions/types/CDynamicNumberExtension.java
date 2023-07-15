package org.catools.common.extensions.types;

import org.catools.common.extensions.states.interfaces.CNumberState;
import org.catools.common.extensions.verify.interfaces.waitVerifier.CNumberWaitVerifier;
import org.catools.common.extensions.verify.interfaces.waitVerify.CNumberWaitVerify;
import org.catools.common.extensions.wait.interfaces.CNumberWaiter;

/**
 * CDynamicNumberExtension is an central interface where we extend all Number related interfaces so
 * adding new functionality will be much easier.
 */
public abstract class CDynamicNumberExtension<N extends Number & Comparable<N>> extends CStaticNumberExtension<N>
    implements CNumberState<N>, CNumberWaiter<N>, CNumberWaitVerify<N>, CNumberWaitVerifier<N> {

  @Override
  public boolean withWaiter() {
    return true;
  }
}
