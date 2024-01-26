package org.catools.athena.rest.pipeline.service;


import org.catools.athena.pipeline.model.PipelineDto;
import org.catools.athena.pipeline.model.PipelineExecutionDto;
import org.catools.athena.pipeline.model.PipelineExecutionStatusDto;
import org.catools.athena.pipeline.model.PipelineScenarioExecutionDto;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

public interface PipelineService {
  /**
   * Save pipeline
   */
  PipelineDto savePipeline(PipelineDto pipelineDto);

  /**
   * Update pipeline end date
   */
  PipelineDto updatePipelineEndDate(long pipelineId, Instant enddate);

  /**
   * Save execution
   */
  PipelineExecutionDto saveExecution(PipelineExecutionDto execution);

  /**
   * Save execution
   */
  Optional<PipelineExecutionDto> getExecutionById(Long id);

  /**
   * Save scenario execution
   */
  PipelineScenarioExecutionDto saveScenarioExecution(PipelineScenarioExecutionDto scenario);

  /**
   * Save scenario execution
   */
  Optional<PipelineScenarioExecutionDto> getScenarioExecutionById(Long id);

  /**
   * Returns a list of all available users.
   */
  Set<PipelineExecutionStatusDto> getExecutionStatuses();

  /**
   * Get user by name
   */
  Optional<PipelineExecutionStatusDto> getExecutionStatusById(Long id);

  /**
   * Get user by name
   */
  Optional<PipelineExecutionStatusDto> getExecutionStatusByName(String name);

  /**
   * Save execution
   */
  PipelineExecutionStatusDto saveExecutionStatus(PipelineExecutionStatusDto status);

  /**
   * Get pipeline
   */
  Optional<PipelineDto> getPipeline(String pipelineName, String pipelineNumber, String environmentCode);

  /**
   * Get pipeline by id
   */
  Optional<PipelineDto> getPipelineById(Long id);

}