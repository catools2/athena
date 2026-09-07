package org.catools.athena.pipeline.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.pipeline.common.entity.PipelineExecutionMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Hidden
public interface PipelineExecutionMetaDataRepository extends JpaRepository<PipelineExecutionMetadata, Long> {

  Optional<PipelineExecutionMetadata> findByNameAndValue(String name, String value);

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
      value = "INSERT INTO athena_pipeline.execution_metadata (name, value) VALUES (:name, CAST(:value AS text)) ON CONFLICT DO NOTHING",
      nativeQuery = true)
  int insertIfAbsent(@Param("name") String name, @Param("value") String value);
}
