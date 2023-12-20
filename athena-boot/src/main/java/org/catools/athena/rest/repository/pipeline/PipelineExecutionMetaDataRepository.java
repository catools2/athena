package org.catools.athena.rest.repository.pipeline;

import org.catools.athena.rest.entity.pipeline.PipelineExecutionMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface PipelineExecutionMetaDataRepository extends JpaRepository<PipelineExecutionMetadata, Long> {

  Optional<PipelineExecutionMetadata> findByNameAndValue(String name, String value);
}
