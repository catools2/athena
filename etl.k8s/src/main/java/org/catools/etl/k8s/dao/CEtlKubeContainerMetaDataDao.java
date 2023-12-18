package org.catools.etl.k8s.dao;

import org.catools.etl.k8s.model.CEtlKubeContainerMetaData;

public class CEtlKubeContainerMetaDataDao extends CEtlKubeBaseDao {
  /**
   * Get container metadata by all properties
   *
   * @param name
   * @param value
   * @return
   */
  public static CEtlKubeContainerMetaData getKubeContainerMetaData(String name, String value) {
    return doTransaction(entityManager -> entityManager
        .createNamedQuery("getEtlKubeContainerMetaData", CEtlKubeContainerMetaData.class)
        .setParameter("name", name)
        .setParameter("value", value)
        .getResultStream()
        .findFirst()
        .orElse(null));
  }
}
