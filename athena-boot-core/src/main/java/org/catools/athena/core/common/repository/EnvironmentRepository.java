package org.catools.athena.core.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.core.common.entity.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Hidden
public interface EnvironmentRepository extends JpaRepository<Environment, Long> {
  Optional<Environment> findByCode(String code);

  Optional<Environment> findByCodeOrName(String code, String name);

  @Query("SELECT e FROM Environment e JOIN e.project p WHERE p.code = :projectCode AND (e.code = :code OR e.name = :name)")
  Optional<Environment> findByProjectIdAndCodeOrName(String projectCode, String code, String name);

}
