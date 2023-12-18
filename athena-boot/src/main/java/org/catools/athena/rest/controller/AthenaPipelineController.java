package org.catools.athena.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.Nullable;
import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.pipeline.model.PipelineDto;
import org.catools.athena.pipeline.model.PipelineExecutionDto;
import org.catools.athena.pipeline.model.PipelineScenarioExecutionDto;
import org.catools.athena.rest.exception.GeneralBadRequestException;
import org.catools.athena.rest.service.AthenaPipelineService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.catools.athena.rest.controller.AthenaApiConstants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = AthenaApiConstants.ROOT, produces = APPLICATION_JSON_VALUE)
@ApiResponses(value = {
    @ApiResponse(responseCode = "401", description = "Request is not authenticated"),
    @ApiResponse(responseCode = "403", description = "Unauthorized to perform function")
})
public class AthenaPipelineController {
  public static final CacheControl MAX_AGE_SINGLE_DAY = CacheControl.maxAge(24L, TimeUnit.HOURS);

  private final AthenaPipelineService AthenaPipelineService;

  public AthenaPipelineController(AthenaPipelineService AthenaPipelineService) {
    this.AthenaPipelineService = AthenaPipelineService;
  }

  @GetMapping(PROJECTS)
  @Operation(
      summary = "Retrieve projects",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully returned data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<List<ProjectDto>> getProjects() {
    final List<ProjectDto> projects = AthenaPipelineService.getProjects();
    return projects.isEmpty() ?
        ResponseEntity.noContent().cacheControl(MAX_AGE_SINGLE_DAY).build() :
        ResponseEntity.ok().cacheControl(MAX_AGE_SINGLE_DAY).body(projects);
  }

  @GetMapping(PROJECT)
  @Operation(
      summary = "Retrieve project by project code",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully returned data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<ProjectDto> getProject(
      @Parameter(name = "projectCode") @RequestParam(name = "projectCode") final String projectCode
  ) {
    final Optional<ProjectDto> project = AthenaPipelineService.getProjectByCode(projectCode);
    return project
        .map(projectDto -> ResponseEntity.ok().cacheControl(MAX_AGE_SINGLE_DAY).body(projectDto))
        .orElseGet(() -> ResponseEntity.noContent().cacheControl(MAX_AGE_SINGLE_DAY).build());
  }

  @PostMapping(PROJECT)
  @Operation(
      summary = "Save project",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<HashMap<String, Long>> saveProject(
      @Parameter(description = "Project to save", name = "project", required = true) @Validated @RequestBody final ProjectDto project
  ) {
    try {
      // We shouldn't have multiple project with similar code
      final Optional<ProjectDto> projectByCode = AthenaPipelineService.getProjectByCode(project.getCode());
      if (projectByCode.isPresent()) {
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
      }

      // We shouldn't have multiple project with similar name
      final Optional<ProjectDto> projectByName = AthenaPipelineService.getProjectByCode(project.getName());
      if (projectByName.isPresent()) {
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
      }

      final long savedId = AthenaPipelineService.saveProject(project);
      final HashMap<String, Long> responseBody = new HashMap<>();
      responseBody.put("projectId", savedId);
      return ResponseEntity.ok().body(responseBody);
    } catch (Throwable generalEx) {
      // let GeneralExceptionHandler to take care of it
      throw new GeneralBadRequestException(generalEx);
    }
  }

  @GetMapping(ENVIRONMENTS)
  @Operation(
      summary = "Retrieve environments",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully returned data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<List<EnvironmentDto>> getEnvironments() {
    final List<EnvironmentDto> environments = AthenaPipelineService.getEnvironments();
    return environments.isEmpty() ?
        ResponseEntity.noContent().cacheControl(MAX_AGE_SINGLE_DAY).build() :
        ResponseEntity.ok().cacheControl(MAX_AGE_SINGLE_DAY).body(environments);
  }

  @GetMapping(ENVIRONMENT)
  @Operation(
      summary = "Retrieve environment by environment code",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully returned data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<EnvironmentDto> getEnvironment(
      @Parameter(name = "environmentCode") @RequestParam(name = "environmentCode") final String envCode
  ) {
    final Optional<EnvironmentDto> environment = AthenaPipelineService.getEnvironmentByCode(envCode);
    return environment
        .map(environmentDto -> ResponseEntity.ok().cacheControl(MAX_AGE_SINGLE_DAY).body(environmentDto))
        .orElseGet(() -> ResponseEntity.noContent().cacheControl(MAX_AGE_SINGLE_DAY).build());
  }

  @PostMapping(ENVIRONMENT)
  @Operation(
      summary = "Save environment",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<HashMap<String, Long>> saveEnvironment(
      @Parameter(description = "Environment to save", name = "environment", required = true) @Validated @RequestBody final EnvironmentDto environment
  ) {
    try {
      // We shouldn't have multiple environment with similar code
      final Optional<EnvironmentDto> environmentByCode = AthenaPipelineService.getEnvironmentByCode(environment.getCode());
      if (environmentByCode.isPresent()) {
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
      }

      final long savedId = AthenaPipelineService.saveEnvironment(environment);
      final HashMap<String, Long> responseBody = new HashMap<>();
      responseBody.put("environmentId", savedId);
      return ResponseEntity.ok().body(responseBody);
    } catch (Throwable generalEx) {
      // let GeneralExceptionHandler to take care of it
      throw new GeneralBadRequestException(generalEx);
    }
  }

  @GetMapping(USERS)
  @Operation(
      summary = "Retrieve users",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully returned data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<List<UserDto>> getUsers() {
    final List<UserDto> users = AthenaPipelineService.getUsers();
    return users.isEmpty() ?
        ResponseEntity.noContent().cacheControl(MAX_AGE_SINGLE_DAY).build() :
        ResponseEntity.ok().cacheControl(MAX_AGE_SINGLE_DAY).body(users);
  }

  @GetMapping(USER)
  @Operation(
      summary = "Retrieve user by name",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully returned data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<UserDto> getUser(
      @Parameter(name = "username") @RequestParam(name = "username") final String username
  ) {
    final Optional<UserDto> user = AthenaPipelineService.getUserByName(username);
    return user.map(userDto -> ResponseEntity.ok().cacheControl(MAX_AGE_SINGLE_DAY).body(userDto))
        .orElseGet(() -> ResponseEntity.noContent().cacheControl(MAX_AGE_SINGLE_DAY).build());
  }

  @PostMapping(USER)
  @Operation(
      summary = "Save user",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "208", description = "User is already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<HashMap<String, Long>> saveUser(
      @Parameter(description = "User to save", name = "user", required = true) @Validated @RequestBody final UserDto user
  ) {
    try {
      final Optional<UserDto> userFromDb = AthenaPipelineService.getUserByName(user.getName());
      if (userFromDb.isPresent()) {
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
      }

      final long savedId = AthenaPipelineService.saveUser(user);
      final HashMap<String, Long> responseBody = new HashMap<>();
      responseBody.put("userId", savedId);
      return ResponseEntity.ok().body(responseBody);
    } catch (Throwable generalEx) {
      // let GeneralExceptionHandler to take care of it
      throw new GeneralBadRequestException(generalEx);
    }
  }

  @GetMapping(PIPELINE)
  @Operation(
      summary = "Retrieve pipeline by name, number and environment code, if pipeline number not provided, the latest pipeline number will be considered",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<PipelineDto> getPipeline(
      @Parameter(description = "Pipeline name", name = "name", required = true) @RequestParam(name = "name") final String pipelineName,
      @Parameter(description = "Pipeline number", name = "number") @Nullable @RequestParam(name = "number") final String pipelineNumber,
      @Parameter(description = "Environment code", name = "env", required = true) @RequestParam(name = "env") final String environmentCode
  ) {
    try {
      final Optional<PipelineDto> pipeline = AthenaPipelineService.getLastPipelineDto(pipelineName, pipelineNumber, environmentCode);
      return pipeline
          .map(pipelineDto -> ResponseEntity.ok().body(pipelineDto))
          .orElseGet(() -> ResponseEntity.notFound().build());
    } catch (Throwable generalEx) {
      throw new GeneralBadRequestException(generalEx);
    }
  }

  @PatchMapping(PIPELINE)
  @Operation(
      summary = "Update pipeline end cate, by pipeline id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<PipelineDto> updatePipelineEndDate(
      @Parameter(description = "Pipeline Id", name = "pipelineId", required = true) @RequestParam(name = "pipelineId") final Long pipelineId,
      @Parameter(description = "End date", name = "endDate") @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name = "date") final Date date
  ) {
    try {
      Date enddate = date == null ? new Date() : date;
      PipelineDto updatedPipeline = AthenaPipelineService.updatePipelineEndDate(pipelineId, enddate);
      return ResponseEntity.ok().body(updatedPipeline);
    } catch (Throwable generalEx) {
      throw new GeneralBadRequestException(generalEx);
    }
  }

  @PostMapping(PIPELINE)
  @Operation(
      summary = "Save pipeline",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<HashMap<String, Long>> savePipeline(
      @Parameter(description = "Pipeline to save", name = "pipeline", required = true) @Validated @RequestBody final PipelineDto pipeline
  ) {
    try {
      // We shouldn't have multiple environment with similar code
      final Optional<PipelineDto> oPipeline = AthenaPipelineService.getLastPipelineDto(pipeline.getName(), pipeline.getNumber(), pipeline.getEnvironmentCode());
      if (oPipeline.isPresent()) {
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
      }

      final long savedId = AthenaPipelineService.savePipeline(pipeline);
      final HashMap<String, Long> responseBody = new HashMap<>();
      responseBody.put("pipelineId", savedId);
      return ResponseEntity.ok().body(responseBody);
    } catch (Throwable generalEx) {
      throw new GeneralBadRequestException(generalEx);
    }
  }

  @PostMapping(EXECUTION)
  @Operation(
      summary = "Save execution",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<HashMap<String, Long>> saveExecution(
      @Parameter(description = "Execution to save", name = "execution", required = true) @Validated @RequestBody final PipelineExecutionDto execution
  ) {
    try {
      final long savedId = AthenaPipelineService.saveExecution(execution);
      final HashMap<String, Long> responseBody = new HashMap<>();
      responseBody.put("executionId", savedId);
      return ResponseEntity.ok().body(responseBody);
    } catch (Throwable generalEx) {
      throw new GeneralBadRequestException(generalEx);
    }
  }

  @PostMapping(SCENARIO)
  @Operation(
      summary = "Save scenario execution",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<HashMap<String, Long>> saveScenarioExecution(
      @Parameter(description = "Scenario execution to save", name = "scenario", required = true) @Validated @RequestBody final PipelineScenarioExecutionDto scenario
  ) {
    try {
      final long savedId = AthenaPipelineService.saveScenarioExecution(scenario);
      final HashMap<String, Long> responseBody = new HashMap<>();
      responseBody.put("executionId", savedId);
      return ResponseEntity.ok().body(responseBody);
    } catch (Throwable generalEx) {
      throw new GeneralBadRequestException(generalEx);
    }
  }
}
