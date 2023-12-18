package org.catools.etl.k8s.dao;

import org.catools.etl.k8s.model.CEtlKubeProject;
import org.hibernate.annotations.QueryHints;

public class CEtlKubeProjectDao extends CEtlKubeBaseDao {
  /**
   * Get project by name
   *
   * @param projectName
   * @return
   */
  public static CEtlKubeProject getProjectByName(String projectName) {
    return doTransaction(
        session -> {
          return session
              .createNamedQuery("getEtlKubeProjectByName", CEtlKubeProject.class)
              .setParameter("name", projectName)
              .setHint(QueryHints.CACHEABLE, true)
              .getResultStream()
              .findFirst()
              .orElse(null);
        });
  }
}
