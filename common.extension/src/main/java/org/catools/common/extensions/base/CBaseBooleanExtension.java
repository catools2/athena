package org.catools.common.extensions.base;

import org.catools.common.extensions.states.interfaces.CBooleanState;

/**
 * CBaseBooleanExtension is a base interface for Boolean related extensions.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 */
public interface CBaseBooleanExtension extends CBaseObjectExtension<Boolean, CBooleanState> {

  default CBooleanState _toState(Boolean e) {
    return () -> (Boolean) e;
  }
}
