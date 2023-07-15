package org.catools.common.exception;

/**
 * Signals that attempt to read yaml information from provided value failed.
 */
public class CInvalidYamlContentFormatException extends RuntimeException {
  private static final String HELP =
      "\nTo read yaml to object you need to ensure that your yaml content has correct format.";

  public CInvalidYamlContentFormatException(String content) {
    super("Yaml file does not have correct format. Content: " + content + HELP);
  }

  public CInvalidYamlContentFormatException(String content, Throwable t) {
    super("Yaml file does not have correct format. content: " + content + HELP, t);
  }
}
