package org.catools.athena.pipeline.common.service;


import org.catools.athena.pipeline.model.PipelineExecutionStatusDto;

import java.util.Optional;
import java.util.Set;

public interface PipelineExecutionStatusService {

  /**
   * Save entity
   */
  PipelineExecutionStatusDto save(PipelineExecutionStatusDto entity);

  /**
   * Retrieve entity by id
   */
  Optional<PipelineExecutionStatusDto> getById(Long id);

  /**
   * Retrieve all
   */
  Set<PipelineExecutionStatusDto> getAll();

  /**
   * Retrieve entity by name
   */
  Optional<PipelineExecutionStatusDto> getByName(String name);
}