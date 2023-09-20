package org.catools.etl.k8s.dao;

import org.catools.etl.k8s.model.CEtlKubePod;

import java.util.Optional;

public class CEtlKubePodDao extends CEtlKubeBaseDao {
  /**
   * delete pod by name
   * @param name
   * @return
   */
  public static boolean deletePodByNameIfExists(String name) {
    return doTransaction(entityManager -> {
      Optional<CEtlKubePod> first = entityManager
          .createNamedQuery("getEtlKubePodByName", CEtlKubePod.class)
          .setParameter("name", name)
          .getResultStream()
          .findFirst();

      if (first.isPresent())
        entityManager.remove(first.get());

      return true;
    });
  }
}
