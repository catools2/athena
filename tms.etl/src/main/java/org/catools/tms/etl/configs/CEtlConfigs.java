package org.catools.tms.etl.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;

public class CEtlConfigs {
  public static int getEtlBulkTransactionPartitionSize() {
    return CHocon.asInteger(Configs.CATOOLS_TMS_ETL_BULK_TRANSACTION_PARTITION_SIZE);
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_TMS_ETL_BULK_TRANSACTION_PARTITION_SIZE("catools.tms.etl.bulk_transaction_partition_size");

    private final String path;
  }
}
