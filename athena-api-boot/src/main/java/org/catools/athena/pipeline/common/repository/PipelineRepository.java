package org.catools.athena.pipeline.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.pipeline.common.entity.Pipeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Hidden
@Transactional
public interface PipelineRepository extends JpaRepository<Pipeline, Long> {
  Optional<Pipeline> findTop1ByNameOrderByNumberDescIdDesc(String name);

  Optional<Pipeline> findTop1ByEnvironmentCodeAndNameOrderByNumberDescIdDesc(String environment, String name);

  Optional<Pipeline> findTop1ByNameAndNumberOrderByIdDesc(String name, String number);

  Optional<Pipeline> findTop1ByEnvironmentCodeAndNameAndNumberOrderByIdDesc(String environment, String name, String number);
}
