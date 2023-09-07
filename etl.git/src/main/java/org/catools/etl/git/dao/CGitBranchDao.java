package org.catools.etl.git.dao;

import org.catools.etl.git.model.CGitBranch;
import org.hibernate.annotations.QueryHints;

public class CGitBranchDao extends CGitBaseDao {
  public static CGitBranch getByHash(String hash) {
    return doTransaction(entityManager -> entityManager
        .createNamedQuery("getGitBranchByHash", CGitBranch.class)
        .setParameter("hash", hash)
        .setHint(QueryHints.CACHEABLE, true)
        .getResultStream()
        .findFirst()
        .orElse(null));
  }
}
