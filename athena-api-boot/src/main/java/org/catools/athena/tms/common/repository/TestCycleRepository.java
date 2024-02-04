package org.catools.athena.tms.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.tms.common.entity.TestCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Hidden
@Transactional
public interface TestCycleRepository extends JpaRepository<TestCycle, Long> {

  Optional<TestCycle> findByCode(String code);

  Set<TestCycle> findByVersionId(Long versionId);
}
