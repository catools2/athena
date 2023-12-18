package org.catools.athena.rest.repository.core;

import org.catools.athena.rest.entity.core.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface EnvironmentRepository extends JpaRepository<Environment, Long> {
  Optional<Environment> findByCode(String cod);
}
