package org.catools.common.extensions.types;

import org.catools.common.extensions.states.interfaces.CStringState;
import org.catools.common.extensions.verify.interfaces.waitVerifier.CStringWaitVerifier;
import org.catools.common.extensions.verify.interfaces.waitVerify.CStringWaitVerify;
import org.catools.common.extensions.wait.interfaces.CStringWaiter;

/**
 * CStaticStringExtension is an central interface where we extend all String related interfaces so
 * adding new functionality will be much easier.
 */
public abstract class CStaticStringExtension implements
    CStringState, CStringWaiter, CStringWaitVerify, CStringWaitVerifier {

  @Override
  public String toString() {
    return _get();
  }
}
