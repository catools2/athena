package org.catools.athena.core.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.core.common.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Hidden
@Transactional
public interface VersionRepository extends JpaRepository<Version, Long> {
  Optional<Version> findByCode(String code);

  Set<Version> findAllByProjectCode(String projectCode);
}
