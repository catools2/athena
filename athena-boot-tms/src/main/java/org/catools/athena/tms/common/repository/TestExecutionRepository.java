package org.catools.athena.tms.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.tms.common.entity.TestExecution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

@Hidden
public interface TestExecutionRepository extends JpaRepository<TestExecution, Long> {

  Optional<TestExecution> findByCreatedOnAndCycleIdAndItemId(Instant created, Long cycleId, Long itemId);

  Set<TestExecution> findByCycleIdAndItemId(Long cycleId, Long itemId);

  Set<TestExecution> findByCycleId(Long cycleId);

  Set<TestExecution> findByItemId(Long itemId);
}
