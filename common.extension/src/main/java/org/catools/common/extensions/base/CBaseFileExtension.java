package org.catools.common.extensions.base;

import org.catools.common.extensions.states.interfaces.CFileState;

import java.io.File;

/**
 * CBaseFileExtension is a base interface for file related extensions.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 */
public interface CBaseFileExtension extends CBaseObjectExtension<File, CFileState> {
  default CFileState _toState(File e) {
    return () -> e;
  }
}
