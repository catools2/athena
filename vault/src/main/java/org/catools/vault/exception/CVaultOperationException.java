package org.catools.vault.exception;

import com.bettercloud.vault.VaultException;
import org.catools.vault.configs.CVaultConfigs;

public class CVaultOperationException extends RuntimeException {
  public CVaultOperationException(String operation, VaultException e) {
    super(String.format("Failed to perform %s operation against vault. Host: %s, Path: %s.",
        operation,
        CVaultConfigs.getUrl(),
        CVaultConfigs.getPath()), e);
  }
}
