package org.catools.athena.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.rest.exception.GeneralBadRequestException;
import org.catools.athena.rest.service.AthenaPipelineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.catools.athena.rest.controller.AthenaApiConstants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = AthenaApiConstants.ROOT, produces = APPLICATION_JSON_VALUE)
@ApiResponses(value = {
    @ApiResponse(responseCode = "401", description = "Request is not authenticated"),
    @ApiResponse(responseCode = "403", description = "Unauthorized to perform function")
})
public class AthenaEnvironmentController {

  private final AthenaPipelineService athenaPipelineService;

  public AthenaEnvironmentController(AthenaPipelineService AthenaPipelineService) {
    this.athenaPipelineService = AthenaPipelineService;
  }

  @GetMapping(ENVIRONMENTS)
  @Operation(
      summary = "Retrieve environments",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully returned data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<List<EnvironmentDto>> getEnvironments() {
    final List<EnvironmentDto> environments = athenaPipelineService.getEnvironments();
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
    final Optional<EnvironmentDto> environment = athenaPipelineService.getEnvironmentByCode(envCode);
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
  public ResponseEntity<EnvironmentDto> saveEnvironment(
      @Parameter(description = "Environment to save", name = "environment", required = true) @Validated @RequestBody final EnvironmentDto environment
  ) {
    try {
      // We shouldn't have multiple environment with similar code
      final Optional<EnvironmentDto> environmentByCode = athenaPipelineService.getEnvironmentByCode(environment.getCode());
      if (environmentByCode.isPresent()) {
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
      }

      final EnvironmentDto savedEnvironmentDto = athenaPipelineService.saveEnvironment(environment);
      return ResponseEntity.ok().body(savedEnvironmentDto);
    } catch (Throwable generalEx) {
      // let GeneralExceptionHandler to take care of it
      throw new GeneralBadRequestException(generalEx);
    }
  }
}
