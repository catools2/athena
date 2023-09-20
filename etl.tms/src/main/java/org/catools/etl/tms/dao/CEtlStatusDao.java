package org.catools.etl.tms.dao;

import org.catools.etl.tms.model.CEtlStatus;
import org.hibernate.annotations.QueryHints;

public class CEtlStatusDao extends CEtlBaseDao {
  public static CEtlStatus getStatusByName(String name) {
    return getTransactionResult(
        session -> {
          return session
              .createNamedQuery("getStatusByName", CEtlStatus.class)
              .setParameter("name", name)
              .setHint(QueryHints.CACHEABLE, true)
              .getResultStream()
              .findFirst()
              .orElse(null);
        });
  }
}
