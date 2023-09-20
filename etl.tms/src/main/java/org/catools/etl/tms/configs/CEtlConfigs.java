package org.catools.etl.tms.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;

public class CEtlConfigs {
  public static int getEtlBulkTransactionPartitionSize() {
    return CHocon.asInteger(Configs.CATOOLS_ETL_TMS_BULK_TRANSACTION_PARTITION_SIZE);
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_ETL_TMS_BULK_TRANSACTION_PARTITION_SIZE("catools.etl.tms.bulk_transaction_partition_size");

    private final String path;
  }
}
