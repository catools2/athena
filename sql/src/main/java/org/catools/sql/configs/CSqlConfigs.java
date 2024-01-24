package org.catools.sql.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;

public class CSqlConfigs {
  public static int getNumberOfRetryOnConnectionException() {
    return CHocon.asInteger(Configs.CATOOLS_SQL_RETRY_ON_CONNECTION_EXCEPTION);
  }

  public static int getInternalTimeOfRetryOnConnectionException() {
    return CHocon.asInteger(Configs.CATOOLS_SQL_INTERNAL_BETWEEN_CONNECTION_EXCEPTION);
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_SQL_RETRY_ON_CONNECTION_EXCEPTION("catools.sql.retry_on_connection_exception"),
    CATOOLS_SQL_INTERNAL_BETWEEN_CONNECTION_EXCEPTION("catools.sql.internal_between_connection_exception");

    private final String path;
  }
}
