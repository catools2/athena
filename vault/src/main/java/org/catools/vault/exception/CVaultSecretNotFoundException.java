package org.catools.vault.exception;

import org.catools.vault.configs.CVaultConfigs;

public class CVaultSecretNotFoundException extends RuntimeException {
  public CVaultSecretNotFoundException(String secretName) {
    super(String.format("Secret %s not found. Host: %s, Path: %s.", secretName, CVaultConfigs.getUrl(), CVaultConfigs.getPath()));
  }
}
