package org.catools.etl.tms.dao;

import org.catools.etl.tms.model.CEtlExecutionStatus;
import org.hibernate.annotations.QueryHints;

public class CEtlExecutionStatusDao extends CEtlBaseDao {
  public static CEtlExecutionStatus getStatusByName(String name) {
    return getTransactionResult(
        session -> {
          return session
              .createNamedQuery("getEtlExecutionStatusByName", CEtlExecutionStatus.class)
              .setParameter("name", name)
              .setHint(QueryHints.CACHEABLE, true)
              .getResultStream()
              .findFirst()
              .orElse(null);
        });
  }
}
