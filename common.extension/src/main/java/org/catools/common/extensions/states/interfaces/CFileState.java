package org.catools.common.extensions.states.interfaces;

import org.catools.common.io.CFile;
import org.catools.common.utils.CStringUtil;

import java.io.File;
import java.util.Objects;

/**
 * CFileState is an interface for File state related methods.
 *
 * <p>We need this interface to have possibility of adding state to any exists objects with the
 * minimum change in the code.
 */
public interface CFileState extends CObjectState<File> {

  default boolean isEqual(final File expected) {
    return Objects.equals(_get(), expected);
  }

  /**
   * Check if actual and expected file have the exact same content.
   *
   * @param expectedFile file to compare
   * @return execution boolean result
   */
  default boolean equalsStringContent(final File expectedFile) {
    File f1 = _get();
    return f1 != null
        && expectedFile != null
        && f1.exists()
        && expectedFile.exists()
        && CStringUtil.equals(new CFile(f1).readString(), new CFile(expectedFile).readString());
  }

  /**
   * Check if actual and expected file does not have the exact same content.
   *
   * @param expectedFile file to compare
   * @return execution boolean result
   */
  default boolean notEqualsStringContent(final File expectedFile) {
    File f1 = _get();
    return f1.exists()
        && expectedFile.exists()
        && !CStringUtil.equals(new CFile(f1).readString(), new CFile(expectedFile).readString());
  }
}
