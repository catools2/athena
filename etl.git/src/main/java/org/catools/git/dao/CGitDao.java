package org.catools.git.dao;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

@Slf4j
public class CGitDao extends CGitBaseDao {
  public static Integer updateEndDate(int id, Timestamp endtime) {
    return doTransaction(entityManager -> {
      String hqlUpdate = "update CGit set end_date=:end_date where id=:id";
      return entityManager.createQuery(hqlUpdate)
          .setParameter("end_date", endtime)
          .setParameter("id", id)
          .executeUpdate();
    });
  }
}
