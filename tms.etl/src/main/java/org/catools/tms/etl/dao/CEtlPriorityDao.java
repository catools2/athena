package org.catools.tms.etl.dao;

import org.catools.tms.etl.model.CEtlPriority;
import org.hibernate.annotations.QueryHints;

public class CEtlPriorityDao extends CEtlBaseDao {
  public static CEtlPriority getPriorityByName(String name) {
    return getTransactionResult(
        session -> {
          return session
              .createNamedQuery("getPriorityByName", CEtlPriority.class)
              .setParameter("name", name)
              .setHint(QueryHints.CACHEABLE, true)
              .getResultStream()
              .findFirst()
              .orElse(null);
        });
  }

  public static CEtlPriority getPriorityById(String id) {
    return find(CEtlPriority.class, id);
  }
}
