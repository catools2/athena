package org.catools.tms.etl.dao;

import org.catools.tms.etl.model.CEtlExecutionStatus;
import org.hibernate.annotations.QueryHints;

public class CEtlExecutionStatusDao extends CEtlBaseDao {
  public static CEtlExecutionStatus getStatusByName(String name) {
    return getTransactionResult(
        session -> {
          return session
              .createNamedQuery("getExecutionStatusByName", CEtlExecutionStatus.class)
              .setParameter("name", name)
              .setHint(QueryHints.CACHEABLE, true)
              .getResultStream()
              .findFirst()
              .orElse(null);
        });
  }
}
