package org.catools.athena.pipeline.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.pipeline.common.entity.Pipeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Hidden
@Transactional
public interface PipelineRepository extends JpaRepository<Pipeline, Long> {
  Optional<Pipeline> findTop1ByNameLikeOrderByNumberDescIdDesc(String name);

  Optional<Pipeline> findTop1ByNameLikeAndNumberLikeOrderByIdDesc(String name, String number);

  Optional<Pipeline> findTop1ByEnvironmentCodeAndNameLikeOrderByNumberDescIdDesc(String environment, String name);

  Optional<Pipeline> findTop1ByEnvironmentCodeAndNameLikeAndNumberLikeOrderByIdDesc(String environment, String name, String number);

  Optional<Pipeline> findTop1ByVersionCodeAndEnvironmentCodeAndNameLikeOrderByNumberDescIdDesc(String version, String environment, String name);

  Optional<Pipeline> findTop1ByVersionCodeAndEnvironmentCodeAndNameLikeAndNumberLikeOrderByIdDesc(String version, String environment, String name, String number);
}
