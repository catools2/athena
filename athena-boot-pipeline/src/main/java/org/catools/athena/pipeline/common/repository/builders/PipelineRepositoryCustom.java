package org.catools.athena.pipeline.common.repository.builders;

import org.catools.athena.pipeline.common.entity.Pipeline;

import java.util.Optional;

public interface PipelineRepositoryCustom {

  Optional<Pipeline> findLastPipeline(String pipelineName, String pipelineNumber, Long versionId, Long environmentId);

}