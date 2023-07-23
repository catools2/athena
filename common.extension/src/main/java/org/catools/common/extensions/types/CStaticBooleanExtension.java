package org.catools.common.extensions.types;

import org.catools.common.extensions.states.interfaces.CBooleanState;
import org.catools.common.extensions.verify.interfaces.base.CBooleanVerify;
import org.catools.common.extensions.verify.interfaces.verifier.CBooleanVerifier;

/**
 * CStaticBooleanExtension is an central interface where we extend all boolean related interfaces so
 * adding new functionality will be much easier.
 */
public abstract class CStaticBooleanExtension implements CBooleanState, CBooleanVerify, CBooleanVerifier {

  @Override
  public String toString() {
    return _get().toString();
  }
}
