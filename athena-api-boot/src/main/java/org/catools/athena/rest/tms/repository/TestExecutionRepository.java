package org.catools.athena.rest.tms.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.tms.entity.TestExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Transactional
@Hidden
public interface TestExecutionRepository extends JpaRepository<TestExecution, Long> {

    Optional<TestExecution> findByCreatedOnAndCycleIdAndItemId(LocalDateTime created, Long cycleId, Long itemId);

    Set<TestExecution> findByCycleIdAndItemId(Long cycleId, Long itemId);

    Set<TestExecution> findByCycleId(Long cycleId);

    Set<TestExecution> findByItemId(Long itemId);
}
