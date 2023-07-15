package org.catools.common.exception;

import java.io.File;

/**
 * Signals that an attempt to open the file denoted by a specified pathname has failed.
 */
public class CFileNotFoundException extends RuntimeException {
  public CFileNotFoundException(String filename, String message) {
    super(String.format("File %s not found. message: %s", filename, message));
  }

  public CFileNotFoundException(File file, String message) {
    this(file.getPath(), message);
  }

  public CFileNotFoundException(File file, Throwable cause) {
    super(String.format("File %s not found.", file.getPath()), cause);
  }
}
