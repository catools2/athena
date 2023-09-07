package org.catools.etl.git.dao;

import org.catools.etl.git.model.CGitUser;
import org.hibernate.annotations.QueryHints;

public class CGitUserDao extends CGitBaseDao {
  public static CGitUser getByName(String name) {
    return doTransaction(entityManager -> entityManager
        .createNamedQuery("getGitUserByName", CGitUser.class)
        .setParameter("name", name)
        .setHint(QueryHints.CACHEABLE, true)
        .getResultStream()
        .findFirst()
        .orElse(null));
  }
}
