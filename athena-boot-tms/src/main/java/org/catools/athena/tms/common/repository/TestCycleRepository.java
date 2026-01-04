package org.catools.athena.tms.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.tms.common.entity.TestCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Hidden
public interface TestCycleRepository extends JpaRepository<TestCycle, Long> {

  Optional<TestCycle> findByCode(String code);

  Optional<TestCycle> findTop1ByNameLikeAndVersionIdOrderByIdDesc(String name, Long versionId);

  /**
   * Fetch TestCycle by code with eager-loaded test executions.
   * Prevents N+1 queries by fetching testExecutions in a single query.
   *
   * @param code the cycle code
   * @return Optional containing TestCycle with all test executions loaded
   */
  @Query("SELECT DISTINCT tc FROM TestCycle tc " +
         "LEFT JOIN FETCH tc.testExecutions " +
         "WHERE tc.code = ?1")
  Optional<TestCycle> findByCodeWithRelations(String code);

  /**
   * Fetch TestCycle by ID with eager-loaded test executions.
   * Prevents N+1 queries by fetching testExecutions in a single query.
   *
   * @param id the cycle ID
   * @return Optional containing TestCycle with all test executions loaded
   */
  @Query("SELECT DISTINCT tc FROM TestCycle tc " +
         "LEFT JOIN FETCH tc.testExecutions " +
         "WHERE tc.id = ?1")
  Optional<TestCycle> findByIdWithRelations(Long id);
}
