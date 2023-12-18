package org.catools.athena.rest.repository.pipeline;

import org.catools.athena.rest.entity.pipeline.Pipeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface PipelineRepository extends JpaRepository<Pipeline, Long> {
  Optional<Pipeline> findTop1ByNameOrderByNumberDescIdDesc(String name);

  Optional<Pipeline> findTop1ByEnvironmentCodeAndNameOrderByNumberDescIdDesc(String environment, String name);

  Optional<Pipeline> findTop1ByNameAndNumberOrderByIdDesc(String name, String number);

  Optional<Pipeline> findTop1ByEnvironmentCodeAndNameAndNumberOrderByIdDesc(String environment, String name, String number);
}
