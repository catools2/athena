package org.catools.athena.core.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.core.common.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Hidden
@Transactional
public interface ProjectRepository extends JpaRepository<Project, Long> {
  Optional<Project> findByCode(String code);

  Optional<Project> findByCodeOrName(String code, String name);

  @Override
  <S extends Project> S save(S entity);
}
