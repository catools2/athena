package org.catools.common.extensions.types;

import org.catools.common.extensions.states.interfaces.CStringState;
import org.catools.common.extensions.verify.interfaces.base.CStringVerify;
import org.catools.common.extensions.verify.interfaces.verifier.CStringVerifier;

/**
 * CStaticStringExtension is an central interface where we extend all String related interfaces so
 * adding new functionality will be much easier.
 */
public abstract class CStaticStringExtension implements
    CStringState, CStringVerify, CStringVerifier {

  @Override
  public String toString() {
    return _get();
  }
}
