package org.catools.athena.pipeline.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.pipeline.common.entity.PipelineMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Hidden
public interface PipelineMetaDataRepository extends JpaRepository<PipelineMetadata, Long> {
  Optional<PipelineMetadata> findByNameAndValue(String name, String value);

}
