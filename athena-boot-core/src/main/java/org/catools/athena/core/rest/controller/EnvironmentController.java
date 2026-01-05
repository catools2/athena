package org.catools.athena.core.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.controlleradvice.ControllerErrorHandler;
import org.catools.athena.common.markers.IdRequired;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.core.common.service.EnvironmentService;
import org.catools.athena.core.entity.EnvironmentFilterDto;
import org.catools.athena.model.core.EnvironmentDto;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Tag(name = "Athena Environment Rest API")
@RequestMapping(value = EnvironmentController.ENVIRONMENT, produces = MediaType.APPLICATION_JSON_VALUE)
@Import(ControllerErrorHandler.class)
@RequiredArgsConstructor
public class EnvironmentController {

  public static final String ENVIRONMENT = "/environment";

  private final EnvironmentService environmentService;

  @GetMapping("/all")
  @Operation(
      summary = "Retrieve all environments with pagination",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<Page<EnvironmentDto>> getAll(
      @Parameter(name = "page", description = "Page number (0-based)")
      @RequestParam(defaultValue = "0") final int page,
      @Parameter(name = "size", description = "Page size")
      @RequestParam(defaultValue = "10") final int size,
      @Parameter(name = "sort", description = "Sort field")
      @RequestParam(defaultValue = "code") final String sort,
      @Parameter(name = "direction", description = "Sort direction (ASC or DESC)")
      @RequestParam(defaultValue = "ASC") final String direction,
      @Parameter(name = "code", description = "Filter by code")
      @RequestParam(required = false) final String code,
      @Parameter(name = "name", description = "Filter by name")
      @RequestParam(required = false) final String name,
      @Parameter(name = "project", description = "Filter by project")
      @RequestParam(required = false) final String project
  ) {
    Sort.Direction sortDirection = Sort.Direction.fromString(direction);
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
    // Build EnvironmentFilterDto from individual parameters
    EnvironmentFilterDto filterDto = new EnvironmentFilterDto(code, name, project);
    Page<EnvironmentDto> result = environmentService.getAll(pageable, filterDto);
    return ResponseEntity.ok(result);
  }

  @GetMapping
  @Operation(
      summary = "Retrieve environment by environment code or name, optionally filtered by project",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<EnvironmentDto> search(
      @Parameter(name = "keyword", description = "The code or name of the environment to retrieve")
      @RequestParam final String keyword,
      @Parameter(name = "project", description = "The project code to filter by")
      @RequestParam final String project
  ) {
    return ResponseEntityUtils.okOrNoContent(environmentService.search(project, keyword));
  }

  @GetMapping("/{id}")
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

  @PostMapping
  @Operation(
      summary = "Save environment or update the current one if any with the same code exists",
      responses = {
          @ApiResponse(responseCode = "201", description = "Environment is created"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The environment to save or update")
      @Validated @RequestBody final EnvironmentDto environment
  ) {
    try {
      final EnvironmentDto savedEnvironmentDto = environmentService.save(environment);
      return ResponseEntityUtils.created(ENVIRONMENT, savedEnvironmentDto.getId());
    } catch (DataIntegrityViolationException ex) {
      if (ex.getCause() instanceof ConstraintViolationException) {
        Optional<EnvironmentDto> dbRecord = environmentService.search(environment.getProject(), environment.getCode());
        if (dbRecord.isPresent())
          return ResponseEntityUtils.alreadyReported(ENVIRONMENT, dbRecord.get().getId());
      }
      return ResponseEntityUtils.conflicted();
    }
  }

  @PutMapping
  @Operation(
      summary = "Update environment if one with the provided id exists",
      responses = {
          @ApiResponse(responseCode = "200", description = "Environment is updated"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> update(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The environment to update")
      @Validated(IdRequired.class) @RequestBody final EnvironmentDto environment
  ) {
    final EnvironmentDto savedEnvironmentDto = environmentService.update(environment);
    return ResponseEntityUtils.updated(ENVIRONMENT, savedEnvironmentDto.getId());
  }
}
