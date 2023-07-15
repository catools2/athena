package org.catools.common.exception;

/**
 * Signals that attempt to find provided resource failed.
 */
public class CConvertFigletFontException extends RuntimeException {

  public CConvertFigletFontException(Throwable cause) {
    super("Failed to read figlet font.", cause);
  }
}
