package org.catools.git.dao;

import org.catools.git.model.CGitCommit;

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
