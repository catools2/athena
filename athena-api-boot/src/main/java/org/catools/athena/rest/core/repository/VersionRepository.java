package org.catools.athena.rest.core.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.core.entity.Version;
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
