package org.catools.athena.rest.pipeline.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.catools.athena.pipeline.model.PipelineDto;
import org.catools.athena.pipeline.model.PipelineExecutionDto;
import org.catools.athena.pipeline.model.PipelineExecutionStatusDto;
import org.catools.athena.pipeline.model.PipelineScenarioExecutionDto;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.config.AthenaCoreConstant;
import org.catools.athena.rest.core.exception.GeneralBadRequestException;
import org.catools.athena.rest.pipeline.service.AthenaPipelineService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Athena Pipeline API")
@RestController
@RequestMapping(value = AthenaCoreConstant.ROOT_API, produces = APPLICATION_JSON_VALUE)
public class AthenaPipelineController {
    public static final String PIPELINE_PATH = "/pipeline";
    public static final String EXECUTION_PATH = "/execution";
    public static final String EXECUTION_STATUS_PATH = "/execution_status";
    public static final String EXECUTION_STATUSES_PATH = "/execution_statuses";
    public static final String SCENARIO_PATH = "/scenario";
    private final AthenaPipelineService athenaPipelineService;

    public AthenaPipelineController(AthenaPipelineService AthenaPipelineService) {
        this.athenaPipelineService = AthenaPipelineService;
    }

    @GetMapping(EXECUTION_STATUSES_PATH)
    @Operation(
            summary = "Retrieve execution statuses",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned data"),
                    @ApiResponse(responseCode = "204", description = "No content to return")
            })
    public ResponseEntity<Set<PipelineExecutionStatusDto>> getExecutionStatuses() {
        final Set<PipelineExecutionStatusDto> executionStatuses = athenaPipelineService.getExecutionStatuses();
        return executionStatuses.isEmpty() ?
                ResponseEntityUtils.noContent() :
                ResponseEntityUtils.ok(executionStatuses);
    }

    @GetMapping(EXECUTION_STATUS_PATH)
    @Operation(
            summary = "Retrieve execution status by name",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned data"),
                    @ApiResponse(responseCode = "204", description = "No content to return")
            })
    public ResponseEntity<PipelineExecutionStatusDto> getExecutionStatusByName(
            @Parameter(name = "statusName", description = "The name of the status to return")
            @RequestParam final String statusName
    ) {
        final Optional<PipelineExecutionStatusDto> executionStatusDto = athenaPipelineService.getExecutionStatusByName(statusName);
        return executionStatusDto.map(ResponseEntityUtils::ok)
                .orElseGet(ResponseEntityUtils::noContent);
    }

    @GetMapping(EXECUTION_STATUS_PATH + "/{id}")
    @Operation(
            summary = "Retrieve execution status by name",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned data"),
                    @ApiResponse(responseCode = "204", description = "No content to return")
            })
    public ResponseEntity<PipelineExecutionStatusDto> getExecutionStatusById(
            @Parameter(name = "id", description = "The id of the status to return")
            @PathVariable final Long id
    ) {
        final Optional<PipelineExecutionStatusDto> executionStatusDto = athenaPipelineService.getExecutionStatusById(id);
        return executionStatusDto.map(ResponseEntityUtils::ok)
                .orElseGet(ResponseEntityUtils::noContent);
    }

    @PostMapping(EXECUTION_STATUS_PATH)
    @Operation(
            summary = "Save execution status",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Execution status is created"),
                    @ApiResponse(responseCode = "208", description = "Execution status is already exists"),
                    @ApiResponse(responseCode = "400", description = "Failed to process request")
            })
    public ResponseEntity<Void> saveExecutionStatus(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The execution status to save")
            @Validated @RequestBody final PipelineExecutionStatusDto pipelineExecutionStatusDto
    ) {
        try {
            final Optional<PipelineExecutionStatusDto> executionStatusFromDb = athenaPipelineService.getExecutionStatusByName(pipelineExecutionStatusDto.getName());
            if (executionStatusFromDb.isPresent()) {
                return ResponseEntityUtils.alreadyReported(executionStatusFromDb.get().getId());
            }

            final PipelineExecutionStatusDto saveExecutionStatus = athenaPipelineService.saveExecutionStatus(pipelineExecutionStatusDto);
            return ResponseEntityUtils.created(saveExecutionStatus.getId());
        } catch (Throwable generalEx) {
            // let GeneralExceptionHandler to take care of it
            throw new GeneralBadRequestException(generalEx);
        }
    }

    @GetMapping(PIPELINE_PATH)
    @Operation(
            summary = "Retrieve pipeline by name, number and environment code, if pipeline number not provided, the latest pipeline will be considered",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully processed request"),
                    @ApiResponse(responseCode = "400", description = "Failed to process request")
            })
    public ResponseEntity<PipelineDto> getPipeline(
            @Parameter(name = "pipelineName", description = "The pipeline name")
            @RequestParam final String pipelineName,
            @Parameter(name = "pipelineNumber", description = "The pipeline number")
            @RequestParam(required = false) final String pipelineNumber,
            @Parameter(name = "environmentCode", description = "The environment code")
            @RequestParam(required = false) final String environmentCode
    ) {
        try {
            return athenaPipelineService.getPipeline(pipelineName, pipelineNumber, environmentCode)
                    .map(ResponseEntityUtils::ok)
                    .orElseGet(ResponseEntityUtils::notFound);
        } catch (Throwable generalEx) {
            throw new GeneralBadRequestException(generalEx);
        }
    }

    @GetMapping(PIPELINE_PATH + "/{id}")
    @Operation(
            summary = "Retrieve pipeline by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully processed request"),
                    @ApiResponse(responseCode = "400", description = "Failed to process request")
            })
    public ResponseEntity<PipelineDto> getPipelineById(
            @Parameter(name = "id", description = "The id of the status to return")
            @PathVariable final Long id
    ) {
        try {
            return athenaPipelineService.getPipelineById(id)
                    .map(ResponseEntityUtils::ok)
                    .orElseGet(ResponseEntityUtils::notFound);
        } catch (Throwable generalEx) {
            throw new GeneralBadRequestException(generalEx);
        }
    }

    @PatchMapping(PIPELINE_PATH)
    @Operation(
            summary = "Update pipeline end cate, by pipeline id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully processed request"),
                    @ApiResponse(responseCode = "400", description = "Failed to process request")
            })
    public ResponseEntity<PipelineDto> updatePipelineEndDate(
            @Parameter(name = "pipelineId", description = "The pipeline Id to update")
            @RequestParam final Long pipelineId,
            @Parameter(name = "endDate", description = "The end date in ISO format")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(required = false) final Date date
    ) {
        try {
            Date enddate = date == null ? new Date() : date;
            PipelineDto updatedPipeline = athenaPipelineService.updatePipelineEndDate(pipelineId, enddate);
            return ResponseEntityUtils.ok(updatedPipeline);
        } catch (Throwable generalEx) {
            throw new GeneralBadRequestException(generalEx);
        }
    }

    @PostMapping(PIPELINE_PATH)
    @Operation(
            summary = "Save pipeline",
            responses = {
                    @ApiResponse(responseCode = "201", description = "pipeline is created"),
                    @ApiResponse(responseCode = "208", description = "pipeline is already exists"),
                    @ApiResponse(responseCode = "400", description = "Failed to process request")
            })
    public ResponseEntity<Void> savePipeline(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The pipeline to save")
            @Validated @RequestBody final PipelineDto pipeline
    ) {
        try {
            // We shouldn't have multiple environment with similar code
            final Optional<PipelineDto> oPipeline = athenaPipelineService.getPipeline(pipeline.getName(), pipeline.getNumber(), pipeline.getEnvironmentCode());
            if (oPipeline.isPresent()) {
                return ResponseEntityUtils.alreadyReported(oPipeline.get().getId());
            }

            final PipelineDto savedPipelineDto = athenaPipelineService.savePipeline(pipeline);
            return ResponseEntityUtils.created(savedPipelineDto.getId());
        } catch (Throwable generalEx) {
            throw new GeneralBadRequestException(generalEx);
        }
    }

    @PostMapping(EXECUTION_PATH)
    @Operation(
            summary = "Save execution",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pipeline execution is created"),
                    @ApiResponse(responseCode = "208", description = "Pipeline execution is already exists"),
                    @ApiResponse(responseCode = "400", description = "Failed to process request")
            })
    public ResponseEntity<Void> saveExecution(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The pipeline execution to save")
            @Validated @RequestBody final PipelineExecutionDto execution
    ) {
        try {
            final PipelineExecutionDto savedExecutionDto = athenaPipelineService.saveExecution(execution);
            return ResponseEntityUtils.created(savedExecutionDto.getId());
        } catch (Throwable generalEx) {
            throw new GeneralBadRequestException(generalEx);
        }
    }

    @PostMapping(SCENARIO_PATH)
    @Operation(
            summary = "Save scenario execution",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pipeline scenario execution is created"),
                    @ApiResponse(responseCode = "208", description = "Pipeline scenario execution is already exists"),
                    @ApiResponse(responseCode = "400", description = "Failed to process request")
            })
    public ResponseEntity<Void> saveScenarioExecution(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The pipeline scenario execution to save")
            @Validated @RequestBody final PipelineScenarioExecutionDto scenario
    ) {
        try {
            final PipelineScenarioExecutionDto savedExecutionDto = athenaPipelineService.saveScenarioExecution(scenario);
            return ResponseEntityUtils.created(savedExecutionDto.getId());
        } catch (Throwable generalEx) {
            throw new GeneralBadRequestException(generalEx);
        }
    }
}
