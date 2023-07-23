package org.catools.common.extensions.base;

import org.catools.common.extensions.states.interfaces.CObjectState;
import org.catools.common.extensions.verify.interfaces.CBaseVerify;

/**
 * CBaseObjectExtension is a base interface for general Object related extensions.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 */
public interface CBaseObjectExtension<O, S extends CObjectState<O>> extends CBaseVerify<O, S> {

}
