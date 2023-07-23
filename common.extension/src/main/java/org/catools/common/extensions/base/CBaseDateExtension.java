package org.catools.common.extensions.base;

import org.catools.common.extensions.states.interfaces.CDateState;

import java.util.Date;

/**
 * CBaseDateExtension is a base interface for date related extensions.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 */
public interface CBaseDateExtension extends CBaseObjectExtension<Date, CDateState> {

  default CDateState _toState(Date e) {
    return () -> e;
  }
}
