package org.catools.athena.tms.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.tms.common.entity.TestCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Hidden
@Transactional
public interface TestCycleRepository extends JpaRepository<TestCycle, Long> {

  Optional<TestCycle> findByCode(String code);

  Optional<TestCycle> findTop1ByNameLikeAndVersionIdOrderByIdDesc(String name, Long versionId);
}
