package org.catools.athena.rest.pipeline.service;


import org.catools.athena.pipeline.model.PipelineDto;
import org.catools.athena.pipeline.model.PipelineExecutionDto;
import org.catools.athena.pipeline.model.PipelineExecutionStatusDto;
import org.catools.athena.pipeline.model.PipelineScenarioExecutionDto;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public interface AthenaPipelineService {
    /**
     * Save pipeline
     */
    PipelineDto savePipeline(PipelineDto pipelineDto);

    /**
     * Update pipeline end date
     */
    PipelineDto updatePipelineEndDate(long pipelineId, Date enddate);

    /**
     * Save execution
     */
    PipelineExecutionDto saveExecution(PipelineExecutionDto execution);

    /**
     * Save scenario execution
     */
    PipelineScenarioExecutionDto saveScenarioExecution(PipelineScenarioExecutionDto scenario);

    /**
     * Returns a list of all available users.
     */
    Set<PipelineExecutionStatusDto> getExecutionStatuses();

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
    Optional<PipelineDto> getLastPipelineDto(String pipelineName, String pipelineNumber, String environmentCode);

}