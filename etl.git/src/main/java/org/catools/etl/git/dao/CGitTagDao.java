package org.catools.etl.git.dao;

import org.catools.etl.git.model.CGitTag;
import org.hibernate.annotations.QueryHints;

public class CGitTagDao extends CGitBaseDao {
  public static CGitTag getByHash(String hash) {
    return doTransaction(entityManager -> entityManager
        .createNamedQuery("getGitTagByHash", CGitTag.class)
        .setParameter("hash", hash)
        .setHint(QueryHints.CACHEABLE, true)
        .getResultStream()
        .findFirst()
        .orElse(null));
  }
}
