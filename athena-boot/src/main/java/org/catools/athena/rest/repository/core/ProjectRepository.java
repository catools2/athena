package org.catools.athena.rest.repository.core;

import org.catools.athena.rest.entity.core.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface ProjectRepository extends JpaRepository<Project, Long> {
  Optional<Project> findByCode(String code);
}
