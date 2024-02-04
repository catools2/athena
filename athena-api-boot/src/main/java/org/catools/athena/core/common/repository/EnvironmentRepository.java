package org.catools.athena.core.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.core.common.entity.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Hidden
@Transactional
public interface EnvironmentRepository extends JpaRepository<Environment, Long> {
  Optional<Environment> findByCode(String cod);
}
