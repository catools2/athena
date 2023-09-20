package org.catools.etl.tms.dao;

import org.catools.etl.tms.model.CEtlProject;
import org.catools.etl.tms.model.CEtlProjects;
import org.hibernate.annotations.QueryHints;

public class CEtlProjectDao extends CEtlBaseDao {
  public static CEtlProject getProjectByName(String projectName) {
    return getTransactionResult(
        session -> {
          return session
              .createNamedQuery("getProjectByName", CEtlProject.class)
              .setParameter("name", projectName)
              .setHint(QueryHints.CACHEABLE, true)
              .getResultStream()
              .findFirst()
              .orElse(null);
        });
  }

  public static CEtlProjects getProjects() {
    return getTransactionResult(
        session -> {
          return new CEtlProjects(
              session
                  .createQuery("From CEtlProject", CEtlProject.class)
                  .setHint(QueryHints.CACHEABLE, true)
                  .getResultList());
        });
  }
}
