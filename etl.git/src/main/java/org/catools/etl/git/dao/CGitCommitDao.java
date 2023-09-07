package org.catools.etl.git.dao;

import org.catools.etl.git.model.CGitCommit;

public class CGitCommitDao extends CGitBaseDao {
  public static CGitCommit getByHash(String hash) {
    return doTransaction(entityManager -> entityManager
        .createNamedQuery("getGitCommitByHash", CGitCommit.class)
        .setParameter("hash", hash)
        .getResultStream()
        .findFirst()
        .orElse(null));
  }
}
