package org.catools.athena.tms.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.tms.common.entity.ItemMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Hidden
public interface ItemMetadataRepository extends JpaRepository<ItemMetadata, Long> {

  /**
   * Equality lookup that matches the shape of {@code uk_metadata_name_value_md5}.
   *
   * <p>Since V12 the only index on this table is the expression index over {@code (name,
   * md5(value))}. A derived {@code findByNameAndValue} emits {@code WHERE name = ? AND value = ?},
   * which the planner cannot satisfy from an expression index and would resolve by sequential scan
   * on every metadata lookup - and there is one per metadata entry per item save. Spelling the
   * predicate as {@code md5(value) = md5(...)} lets the index serve it.
   *
   * <p>The cast is not decorative: without it PostgreSQL sees an untyped parameter and cannot
   * choose between {@code md5(text)} and {@code md5(bytea)}.
   */
  @Query(
      value = "SELECT * FROM athena_tms.metadata WHERE name = :name AND md5(value) = md5(CAST(:value AS text))",
      nativeQuery = true)
  Optional<ItemMetadata> findByNameAndValueIndexed(@Param("name") String name, @Param("value") String value);

  /**
   * Insert the (name, value) pair unless a concurrent writer already did.
   *
   * <p>Catching {@link org.springframework.dao.DataIntegrityViolationException} around a plain
   * insert cannot work here: PostgreSQL marks the whole transaction aborted the moment a constraint
   * fires, so the recovery lookup issued afterwards runs inside a transaction that can no longer
   * execute anything, and Hibernate then reports the far less obvious "has a null identifier"
   * assertion failure instead of the 23505 that actually happened. Letting the database absorb the
   * conflict keeps the transaction usable, so the follow-up lookup can simply read the winner's row.
   */
  @Modifying
  @Query(
      value = "INSERT INTO athena_tms.metadata (name, value) VALUES (:name, CAST(:value AS text)) ON CONFLICT DO NOTHING",
      nativeQuery = true)
  int insertIfAbsent(@Param("name") String name, @Param("value") String value);
}
