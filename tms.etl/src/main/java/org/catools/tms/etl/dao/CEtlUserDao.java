package org.catools.tms.etl.dao;

import org.catools.tms.etl.model.CEtlUser;
import org.hibernate.annotations.QueryHints;

public class CEtlUserDao extends CEtlBaseDao {
  public static CEtlUser getUserByName(String name) {
    return getTransactionResult(
        session -> {
          return session
              .createNamedQuery("getEtlUserByName", CEtlUser.class)
              .setParameter("name", name)
              .setHint(QueryHints.CACHEABLE, true)
              .getResultStream()
              .findFirst()
              .orElse(null);
        });
  }
}
