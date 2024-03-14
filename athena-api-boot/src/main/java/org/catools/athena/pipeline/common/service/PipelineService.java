package org.catools.athena.pipeline.common.service;


import org.catools.athena.common.service.SaveOrUpdateService;
import org.catools.athena.pipeline.model.PipelineDto;

import java.time.Instant;
import java.util.Optional;

public interface PipelineService extends SaveOrUpdateService<PipelineDto> {

  /**
   * Update pipeline end date
   */
  PipelineDto updatePipelineEndDate(long pipelineId, Instant enddate);

  /**
   * Get pipeline
   */
  Optional<PipelineDto> getPipeline(String pipelineName, String pipelineNumber, String versionCode, String environmentCode);

}