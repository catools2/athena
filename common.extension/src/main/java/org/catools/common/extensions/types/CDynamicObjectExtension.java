package org.catools.common.extensions.types;

import org.catools.common.extensions.states.interfaces.CObjectState;
import org.catools.common.extensions.verify.interfaces.waitVerifier.CObjectWaitVerifier;
import org.catools.common.extensions.verify.interfaces.waitVerify.CObjectWaitVerify;
import org.catools.common.extensions.wait.interfaces.CObjectWaiter;

/**
 * CDynamicObjectExtension is an central interface where we extend all Object related interfaces so
 * adding new functionality will be much easier.
 */
public abstract class CDynamicObjectExtension<O extends Object> extends CStaticObjectExtension<O>
    implements CObjectState<O>, CObjectWaiter<O>, CObjectWaitVerify<O, CObjectState<O>>, CObjectWaitVerifier<O, CObjectState<O>> {

  @Override
  public boolean withWaiter() {
    return true;
  }
}
