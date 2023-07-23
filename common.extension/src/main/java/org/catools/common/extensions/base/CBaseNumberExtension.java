package org.catools.common.extensions.base;

import org.catools.common.extensions.states.interfaces.CNumberState;
import org.catools.common.extensions.verify.hard.CNumberVerification;

/**
 * CBaseNumberExtension is a base interface for Number related extensions.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 *
 * <p>Please Note that we should extend manually {@link
 * CNumberVerification} for each new added verification here
 */
public interface CBaseNumberExtension<N extends Number & Comparable<N>> extends CBaseObjectExtension<N, CNumberState<N>> {

  default CNumberState<N> _toState(N e) {
    return () -> e;
  }
}
