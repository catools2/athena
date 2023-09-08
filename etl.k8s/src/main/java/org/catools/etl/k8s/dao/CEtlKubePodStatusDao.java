package org.catools.etl.k8s.dao;

import org.catools.etl.k8s.model.CEtlKubePodStatus;

public class CEtlKubePodStatusDao extends CEtlKubeBaseDao {
  /**
   * Get pod status bu all properties
   * @param status
   * @param type
   * @param phase
   * @param message
   * @param reason
   * @return
   */
  public static CEtlKubePodStatus getEtlKubePodStatus(String status, String type, String phase, String message, String reason) {
    return doTransaction(entityManager -> entityManager
        .createNamedQuery("getEtlKubePodStatus", CEtlKubePodStatus.class)
        .setParameter("type", type)
        .setParameter("status", status)
        .setParameter("phase", phase)
        .setParameter("message", message)
        .setParameter("reason", reason)
        .getResultStream()
        .findFirst()
        .orElse(null));
  }
}
