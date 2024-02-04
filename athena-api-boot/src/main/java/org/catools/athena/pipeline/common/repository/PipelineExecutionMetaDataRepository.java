package org.catools.athena.pipeline.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.core.common.repository.MetadataRepository;
import org.catools.athena.pipeline.common.entity.PipelineExecutionMetadata;
import org.springframework.transaction.annotation.Transactional;

@Hidden
@Transactional
public interface PipelineExecutionMetaDataRepository extends MetadataRepository<PipelineExecutionMetadata> {
}
