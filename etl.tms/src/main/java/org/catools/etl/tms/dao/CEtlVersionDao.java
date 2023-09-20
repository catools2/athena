package org.catools.etl.tms.dao;

import org.catools.etl.tms.model.CEtlProject;
import org.catools.etl.tms.model.CEtlVersion;
import org.hibernate.annotations.QueryHints;

public class CEtlVersionDao extends CEtlBaseDao {
  public static CEtlVersion getVersion(CEtlProject project, String versionName) {
    return getTransactionResult(
        session -> {
          return session
              .createNamedQuery("getVersionForProjectNameAndName", CEtlVersion.class)
              .setParameter("name", versionName)
              .setParameter("projectName", project.getName())
              .setHint(QueryHints.CACHEABLE, true)
              .getResultStream()
              .findFirst()
              .orElse(null);
        });
  }
}
