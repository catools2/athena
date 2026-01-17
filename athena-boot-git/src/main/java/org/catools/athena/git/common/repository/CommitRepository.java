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
   * Fetch Commit with repository and diffEntries to avoid cartesian product explosion.
   * Tags and metadata are loaded separately to prevent memory issues with large collections.
   *
   * @param hash the commit hash
   * @return Optional containing Commit with repository and diffEntries loaded, or empty if not found
   */
  @Query("SELECT DISTINCT c FROM Commit c " +
      "LEFT JOIN FETCH c.repository " +
      "LEFT JOIN FETCH c.diffEntries " +
      "WHERE c.hash = ?1")
  Optional<Commit> findByHashWithRelations(String hash);

  /**
   * Fetch tags for a commit by hash. Separate query to avoid cartesian product.
   *
   * @param hash the commit hash
   * @return Optional containing Commit with tags loaded, or empty if not found
   */
  @Query("SELECT DISTINCT c FROM Commit c " +
      "LEFT JOIN FETCH c.tags " +
      "WHERE c.hash = ?1")
  Optional<Commit> findByHashWithTags(String hash);

  /**
   * Fetch metadata for a commit by hash. Separate query to avoid cartesian product.
   *
   * @param hash the commit hash
   * @return Optional containing Commit with metadata loaded, or empty if not found
   */
  @Query("SELECT DISTINCT c FROM Commit c " +
      "LEFT JOIN FETCH c.metadata " +
      "WHERE c.hash = ?1")
  Optional<Commit> findByHashWithMetadata(String hash);

  /**
   * Fetch Commit by ID with repository and diffEntries to avoid cartesian product explosion.
   * Tags and metadata are loaded separately to prevent memory issues with large collections.
   *
   * @param id the commit ID
   * @return Optional containing Commit with repository and diffEntries loaded, or empty if not found
   */
  @Query("SELECT DISTINCT c FROM Commit c " +
      "LEFT JOIN FETCH c.repository " +
      "LEFT JOIN FETCH c.diffEntries " +
      "WHERE c.id = ?1")
  Optional<Commit> findByIdWithRelations(Long id);

  /**
   * Fetch tags for a commit by ID. Separate query to avoid cartesian product.
   *
   * @param id the commit ID
   * @return Optional containing Commit with tags loaded, or empty if not found
   */
  @Query("SELECT DISTINCT c FROM Commit c " +
      "LEFT JOIN FETCH c.tags " +
      "WHERE c.id = ?1")
  Optional<Commit> findByIdWithTags(Long id);

  /**
   * Fetch metadata for a commit by ID. Separate query to avoid cartesian product.
   *
   * @param id the commit ID
   * @return Optional containing Commit with metadata loaded, or empty if not found
   */
  @Query("SELECT DISTINCT c FROM Commit c " +
      "LEFT JOIN FETCH c.metadata " +
      "WHERE c.id = ?1")
  Optional<Commit> findByIdWithMetadata(Long id);

}
