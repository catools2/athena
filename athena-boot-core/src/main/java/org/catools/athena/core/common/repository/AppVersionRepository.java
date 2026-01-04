package org.catools.athena.core.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.core.common.entity.AppVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Hidden
public interface AppVersionRepository extends JpaRepository<AppVersion, Long> {
  Optional<AppVersion> findByNameAndProjectId(String name, Long projectId);

  Optional<AppVersion> findByCodeOrName(String code, String name);

  @Query("SELECT v FROM AppVersion v JOIN v.project p WHERE p.code = :projectCode AND (v.code = :code OR v.name = :name)")
  Optional<AppVersion> findByProjectIdAndCodeOrName(String projectCode, String code, String name);

}
