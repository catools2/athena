package org.catools.athena.core.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.core.common.entity.AppVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Hidden
@Transactional
public interface VersionRepository extends JpaRepository<AppVersion, Long> {
  Optional<AppVersion> findByCode(String code);

  Optional<AppVersion> findByCodeOrName(String code, String name);

  Optional<AppVersion> findByCodeAndProjectId(String name, Long projectId);
}
