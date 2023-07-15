package org.catools.tms.etl.dao;

import org.catools.tms.etl.model.CEtlProject;
import org.catools.tms.etl.model.CEtlProjects;
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
