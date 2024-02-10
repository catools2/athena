package org.catools.athena.pipeline.common.service;


import org.catools.athena.pipeline.model.PipelineExecutionDto;

import java.util.Optional;

public interface PipelineExecutionService {

  /**
   * Save execution
   */
  PipelineExecutionDto save(PipelineExecutionDto entity);

  /**
   * Retrieve execution by id
   */
  Optional<PipelineExecutionDto> getById(Long id);

}