package org.catools.common.exception;

import java.io.File;

/**
 * Signals that an attempt to perform operation against the file denoted by a specified pathname has
 * failed.
 */
public class CFileOperationException extends RuntimeException {

  public CFileOperationException(String filename, String message) {
    super(String.format("Failed to perform operation on file %s. message: %s", filename, message));
  }

  public CFileOperationException(File file, String message) {
    this(file.getPath(), message);
  }

  public CFileOperationException(File file, String message, Throwable cause) {
    super(
        String.format(
            "Failed to perform operation on file %s. message: %s", file.getPath(), message),
        cause);
  }
}
