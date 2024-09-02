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

  Optional<Pipeline> findTop1ByEnvironmentIdAndNameLikeOrderByNumberDescIdDesc(Long environmentId, String name);

  Optional<Pipeline> findTop1ByEnvironmentIdAndNameLikeAndNumberLikeOrderByIdDesc(Long environmentId, String name, String number);

  Optional<Pipeline> findTop1ByVersionIdAndEnvironmentIdAndNameLikeOrderByNumberDescIdDesc(Long versionId, Long environmentId, String name);

  Optional<Pipeline> findTop1ByVersionIdAndEnvironmentIdAndNameLikeAndNumberLikeOrderByIdDesc(Long versionId, Long environmentId, String name, String number);
}
