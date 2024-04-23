package org.catools.vault.exception;

import org.catools.vault.configs.CVaultConfigs;

public class CVaultAuthenticationException extends RuntimeException {
  public CVaultAuthenticationException(String authType) {
    super(String.format("Failed to perform %s authentication against vault. Host: %s, Path: %s.",
        authType,
        CVaultConfigs.getUrl(),
        CVaultConfigs.getPath()));
  }
}
