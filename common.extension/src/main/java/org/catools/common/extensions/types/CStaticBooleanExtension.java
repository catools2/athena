package org.catools.common.extensions.types;

import org.catools.common.extensions.states.interfaces.CBooleanState;
import org.catools.common.extensions.verify.interfaces.waitVerifier.CBooleanWaitVerifier;
import org.catools.common.extensions.verify.interfaces.waitVerify.CBooleanWaitVerify;
import org.catools.common.extensions.wait.interfaces.CBooleanWaiter;

/**
 * CStaticBooleanExtension is an central interface where we extend all boolean related interfaces so
 * adding new functionality will be much easier.
 */
public abstract class CStaticBooleanExtension
    implements CBooleanState, CBooleanWaiter, CBooleanWaitVerify, CBooleanWaitVerifier {

  @Override
  public String toString() {
    return _get().toString();
  }
}
