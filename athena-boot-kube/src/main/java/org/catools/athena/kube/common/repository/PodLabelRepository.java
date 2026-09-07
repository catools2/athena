package org.catools.athena.kube.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.kube.common.model.PodLabel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Hidden
public interface PodLabelRepository extends MetadataRepository<PodLabel> {

  /**
   * Insert the (name, value) pair unless a concurrent writer already did.
   *
   * <p>Catching {@link org.springframework.dao.DataIntegrityViolationException} around a plain
   * insert cannot work on PostgreSQL: the constraint aborts the whole transaction, so the recovery
   * lookup that used to follow it could never execute. Letting the database absorb the conflict
   * keeps the transaction usable, so the caller can simply read back the winning row.
   */
  @Modifying
  @Query(
      value = "INSERT INTO athena_kube.pod_label (name, value) VALUES (:name, CAST(:value AS text)) ON CONFLICT DO NOTHING",
      nativeQuery = true)
  int insertIfAbsent(@Param("name") String name, @Param("value") String value);
}
