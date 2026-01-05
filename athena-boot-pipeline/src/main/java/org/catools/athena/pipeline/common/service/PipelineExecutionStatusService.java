package org.catools.athena.pipeline.common.service;


import org.catools.athena.common.service.SaveService;
import org.catools.athena.model.pipeline.PipelineExecutionStatusDto;

import java.util.Optional;
import java.util.Set;

public interface PipelineExecutionStatusService extends SaveService<PipelineExecutionStatusDto> {

  /**
   * Retrieve all
   */
  Set<PipelineExecutionStatusDto> getAll();

  /**
   * Retrieve entity by name
   */
  Optional<PipelineExecutionStatusDto> getByName(String name);
}