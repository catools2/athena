package org.catools.pipeline.dao;

import org.catools.pipeline.model.CPipelineUser;
import org.hibernate.annotations.QueryHints;

public class CPipelineUserDao extends CPipelineBaseDao {
  public static CPipelineUser getUserByName(String name) {
    return getTransactionResult(entityManager -> {
      return entityManager
          .createNamedQuery("getPipelineUserByName", CPipelineUser.class)
          .setParameter("name", name)
          .setHint(QueryHints.CACHEABLE, true)
          .getResultStream()
          .findFirst()
          .orElse(null);
    });
  }
}
