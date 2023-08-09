package org.catools.git.dao;

import org.catools.git.model.CGitUser;
import org.hibernate.annotations.QueryHints;

public class CGitUserDao extends CGitBaseDao {
  public static CGitUser getUserByName(String name) {
    return doTransaction(entityManager -> {
      return entityManager
          .createNamedQuery("getUserByName", CGitUser.class)
          .setParameter("name", name)
          .setHint(QueryHints.CACHEABLE, true)
          .getResultStream()
          .findFirst()
          .orElse(null);
    });
  }
}
