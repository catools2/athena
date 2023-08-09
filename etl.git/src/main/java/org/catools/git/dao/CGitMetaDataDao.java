package org.catools.git.dao;

import org.catools.git.model.CGitCommit;
import org.hibernate.annotations.QueryHints;

public class CGitMetaDataDao extends CGitBaseDao {
  public static CGitCommit getMetaDataByNameAndValue(String name, String value) {
    return doTransaction(entityManager -> {
      return entityManager
          .createNamedQuery("getMetaDataByNameAndValue", CGitCommit.class)
          .setParameter("name", name)
          .setParameter("value", value)
          .setHint(QueryHints.CACHEABLE, true)
          .getResultStream()
          .findFirst()
          .orElse(null);
    });
  }
}
