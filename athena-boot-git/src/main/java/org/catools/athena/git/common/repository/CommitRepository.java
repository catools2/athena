package org.catools.athena.git.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.git.common.entity.Commit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Hidden
public interface CommitRepository extends JpaRepository<Commit, Long> {

  Optional<Commit> findByHash(String hash);

  /**
   * Fetch Commit with eager-loaded relationships to avoid N+1 queries.
   * This query uses LEFT JOIN FETCH to load repository, diffEntries, tags, and metadata in a single query.
   *
   * @param hash the commit hash
   * @return Optional containing Commit with all relations loaded, or empty if not found
   */
  @Query("SELECT DISTINCT c FROM Commit c " +
      "LEFT JOIN FETCH c.repository " +
      "LEFT JOIN FETCH c.diffEntries " +
      "LEFT JOIN FETCH c.tags " +
      "LEFT JOIN FETCH c.metadata " +
      "WHERE c.hash = ?1")
  Optional<Commit> findByHashWithRelations(String hash);

  /**
   * Fetch Commit by ID with eager-loaded relationships to avoid N+1 queries.
   * This query uses LEFT JOIN FETCH to load repository, diffEntries, tags, and metadata in a single query.
   *
   * @param id the commit ID
   * @return Optional containing Commit with all relations loaded, or empty if not found
   */
  @Query("SELECT DISTINCT c FROM Commit c " +
      "LEFT JOIN FETCH c.repository " +
      "LEFT JOIN FETCH c.diffEntries " +
      "LEFT JOIN FETCH c.tags " +
      "LEFT JOIN FETCH c.metadata " +
      "WHERE c.id = ?1")
  Optional<Commit> findByIdWithRelations(Long id);

}
