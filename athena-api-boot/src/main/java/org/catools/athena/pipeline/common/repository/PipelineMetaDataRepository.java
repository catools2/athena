package org.catools.athena.pipeline.common.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.catools.athena.core.common.repository.MetadataRepository;
import org.catools.athena.pipeline.common.entity.PipelineMetadata;
import org.springframework.transaction.annotation.Transactional;

@Hidden
@Transactional
public interface PipelineMetaDataRepository extends MetadataRepository<PipelineMetadata> {
}
