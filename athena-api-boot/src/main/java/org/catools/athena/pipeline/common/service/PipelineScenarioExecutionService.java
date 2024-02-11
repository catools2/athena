package org.catools.athena.pipeline.common.service;


import org.catools.athena.pipeline.model.PipelineScenarioExecutionDto;

import java.util.Optional;

public interface PipelineScenarioExecutionService {

  /**
   * Save execution
   */
  PipelineScenarioExecutionDto save(PipelineScenarioExecutionDto entity);

  /**
   * Retrieve execution by id
   */
  Optional<PipelineScenarioExecutionDto> getById(Long id);

}