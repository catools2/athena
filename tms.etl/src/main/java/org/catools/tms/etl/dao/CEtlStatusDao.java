package org.catools.tms.etl.dao;

import org.catools.tms.etl.model.CEtlStatus;
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
