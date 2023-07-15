package org.catools.pipeline.dao;

import lombok.extern.slf4j.Slf4j;
import org.catools.pipeline.model.CPipeline;
import org.hibernate.annotations.QueryHints;

import java.sql.Timestamp;

@Slf4j
public class CPipelineDao extends CPipelineBaseDao {
  public static Integer updateEndDate(int id, Timestamp endtime) {
    return getTransactionResult(entityManager -> {
      String hqlUpdate = "update CPipeline set end_date=:end_date where id=:id";
      return entityManager.createQuery(hqlUpdate)
          .setParameter("end_date", endtime)
          .setParameter("id", id)
          .executeUpdate();
    });
  }

  public static CPipeline getLastByName(String name) {
    return getTransactionResult(entityManager -> {
      return entityManager
          .createNamedQuery("getLastByName", CPipeline.class)
          .setParameter("name", name)
          .setHint(QueryHints.CACHEABLE, true)
          .getResultStream()
          .findFirst()
          .orElse(null);
    });
  }

}
