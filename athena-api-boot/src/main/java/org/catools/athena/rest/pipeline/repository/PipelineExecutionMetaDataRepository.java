package org.catools.athena.rest.pipeline.repository;

import org.catools.athena.rest.pipeline.entity.PipelineExecutionMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface PipelineExecutionMetaDataRepository extends JpaRepository<PipelineExecutionMetadata, Long> {

  Optional<PipelineExecutionMetadata> findByNameAndValue(String name, String value);
}
