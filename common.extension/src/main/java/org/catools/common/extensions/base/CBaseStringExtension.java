package org.catools.common.extensions.base;

import org.catools.common.extensions.states.interfaces.CStringState;
import org.catools.common.extensions.verify.hard.CStringVerification;

/**
 * CBaseStringExtension is a base interface for String related extensions.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 *
 * <p>Please Note that we should extend manually {@link
 * CStringVerification} for each new added verification here
 */
public interface CBaseStringExtension extends CBaseObjectExtension<String, CStringState> {

  default CStringState _toState(String e) {
    return () -> e;
  }
}
