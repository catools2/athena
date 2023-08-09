package org.catools.excel.exception;

/**
 * Signals that an attempt to perform operation against the Excel file denoted by a specified filename has
 * failed.
 */
public class CExcelOperationException extends RuntimeException {

  public CExcelOperationException(String message, Throwable cause) {
    super(String.format("Failed to perform operation on workbook. message: %s", message), cause);
  }

  public CExcelOperationException(String filename, String message, Throwable cause) {
    super(String.format("Failed to perform operation on workbook from %s. message: %s", filename, message), cause);
  }
}
