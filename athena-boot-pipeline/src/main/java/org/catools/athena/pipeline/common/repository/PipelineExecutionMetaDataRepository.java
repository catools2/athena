package org.catools.athena.pipeline.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.pipeline.common.entity.PipelineExecutionMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Hidden
@Transactional
public interface PipelineExecutionMetaDataRepository extends JpaRepository<PipelineExecutionMetadata, Long> {
  Optional<PipelineExecutionMetadata> findByNameAndValue(String name, String value);

}
