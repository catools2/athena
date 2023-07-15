package org.catools.tms.etl.dao;

import org.catools.tms.etl.model.CEtlItemType;
import org.hibernate.annotations.QueryHints;

public class CEtlItemTypeDao extends CEtlBaseDao {
  public static CEtlItemType getItemTypeByName(String name) {
    return getTransactionResult(
        session -> {
          return session
              .createNamedQuery("getItemTypeByName", CEtlItemType.class)
              .setParameter("name", name)
              .setHint(QueryHints.CACHEABLE, true)
              .getResultStream()
              .findFirst()
              .orElse(null);
        });
  }
}
