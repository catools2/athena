package org.catools.athena.rest.core.repository;

import org.catools.athena.rest.core.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface ProjectRepository extends JpaRepository<Project, Long> {
  Optional<Project> findByName(String name);
  Optional<Project> findByCode(String code);
}
