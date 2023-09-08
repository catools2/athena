package org.catools.etl.k8s.dao;

import org.catools.etl.k8s.model.CEtlKubePodMetaData;

public class CEtlKubePodMetaDataDao extends CEtlKubeBaseDao {
  /**
   * Get pod metadata bu all properties
   * @param type
   * @param name
   * @param value
   * @return
   */
  public static CEtlKubePodMetaData getKubePodMetaData(String type, String name, String value) {
    return doTransaction(entityManager -> entityManager
        .createNamedQuery("getEtlKubePodMetaData", CEtlKubePodMetaData.class)
        .setParameter("type", type)
        .setParameter("name", name)
        .setParameter("value", value)
        .getResultStream()
        .findFirst()
        .orElse(null));
  }
}
