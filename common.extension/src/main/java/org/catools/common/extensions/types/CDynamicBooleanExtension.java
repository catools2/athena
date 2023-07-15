package org.catools.common.extensions.types;

import org.catools.common.extensions.states.interfaces.CBooleanState;
import org.catools.common.extensions.verify.interfaces.waitVerifier.CBooleanWaitVerifier;
import org.catools.common.extensions.verify.interfaces.waitVerify.CBooleanWaitVerify;
import org.catools.common.extensions.wait.interfaces.CBooleanWaiter;

/**
 * CDynamicBooleanExtension is an central interface where we extend all boolean related interfaces
 * so adding new functionality will be much easier.
 */
public abstract class CDynamicBooleanExtension extends CStaticBooleanExtension
    implements CBooleanState, CBooleanWaiter, CBooleanWaitVerify, CBooleanWaitVerifier {

  @Override
  public boolean withWaiter() {
    return true;
  }
}
