package org.catools.athena.core.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.core.common.config.CorePathDefinitions;
import org.catools.athena.core.common.service.EnvironmentService;
import org.catools.athena.core.model.EnvironmentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "Athena Environment Rest API")
@RequestMapping(value = CorePathDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EnvironmentController {

  public static final String ENVIRONMENT = "/environment";

  private final EnvironmentService environmentService;

  @GetMapping(ENVIRONMENT)
  @Operation(
      summary = "Retrieve environment by environment code",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<EnvironmentDto> getByCode(
      @Parameter(name = "envCode", description = "The code of the environment to retrieve")
      @RequestParam final String envCode
  ) {
    return ResponseEntityUtils.okOrNoContent(environmentService.getByCode(envCode));
  }

  @GetMapping(ENVIRONMENT + "/{id}")
  @Operation(
      summary = "Retrieve environment by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<EnvironmentDto> getById(
      @Parameter(name = "id", description = "The id of the environment to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(environmentService.getById(id));
  }

  @PostMapping(ENVIRONMENT)
  @Operation(
      summary = "Save environment",
      responses = {
          @ApiResponse(responseCode = "201", description = "Environment is created"),
          @ApiResponse(responseCode = "208", description = "Environment is already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The environment to save")
      @Validated @RequestBody final EnvironmentDto environment
  ) {
    final Optional<EnvironmentDto> environmentByCode = environmentService.getByCode(environment.getCode());
    if (environmentByCode.isPresent()) {
      return ResponseEntityUtils.alreadyReported(ENVIRONMENT, environmentByCode.get().getId());
    }

    final EnvironmentDto savedEnvironmentDto = environmentService.save(environment);
    return ResponseEntityUtils.created(ENVIRONMENT, savedEnvironmentDto.getId());
  }
}
