package org.catools.common.exception;

import java.io.File;

/**
 * Signals that attempt to convert file content to yaml information failed.
 */
public class CInvalidYamlFileFormatException extends RuntimeException {
  private static final String HELP =
      "\nTo read yaml to object you need to ensure that your yaml file has correct file format.";

  public CInvalidYamlFileFormatException(String fileName) {
    super("Yaml file does not have correct format. Filename: " + fileName + HELP);
  }

  public CInvalidYamlFileFormatException(String fileName, Throwable t) {
    super("Yaml file does not have correct format. Filename: " + fileName + HELP, t);
  }

  public CInvalidYamlFileFormatException(File file) {
    this(file.getPath());
  }
}
