package org.catools.athena.rest.pipeline.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.rest.core.repository.MetadataRepository;
import org.catools.athena.rest.pipeline.entity.PipelineExecutionMetadata;
import org.springframework.transaction.annotation.Transactional;

@Hidden
@Transactional
public interface PipelineExecutionMetaDataRepository extends MetadataRepository<PipelineExecutionMetadata> {
}
