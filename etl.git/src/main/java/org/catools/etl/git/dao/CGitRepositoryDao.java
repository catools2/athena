package org.catools.etl.git.dao;

import lombok.extern.slf4j.Slf4j;
import org.catools.etl.git.model.CGitRepository;
import org.hibernate.annotations.QueryHints;

import java.sql.Timestamp;

@Slf4j
public class CGitRepositoryDao extends CGitBaseDao {
  public static CGitRepository getByName(String name) {
    return doTransaction(entityManager -> entityManager
        .createNamedQuery("getGitRepositoryByName", CGitRepository.class)
        .setParameter("name", name)
        .setHint(QueryHints.CACHEABLE, true)
        .getResultStream()
        .findFirst()
        .orElse(null));
  }
  public static Integer updateEndDate(String url, Timestamp updatedAt) {
    return doTransaction(entityManager -> {
      String hqlUpdate = "update CGitRepository set last_update=:last_update where url=:url";
      return entityManager.createQuery(hqlUpdate)
          .setParameter("last_update", updatedAt)
          .setParameter("url", url)
          .executeUpdate();
    });
  }
}
