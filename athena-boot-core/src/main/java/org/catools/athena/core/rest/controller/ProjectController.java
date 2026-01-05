package org.catools.athena.core.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.controlleradvice.ControllerErrorHandler;
import org.catools.athena.common.markers.IdRequired;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.core.common.service.ProjectService;
import org.catools.athena.core.entity.ProjectFilterDto;
import org.catools.athena.model.core.ProjectDto;
import org.hibernate.exception.ConstraintViolationException;
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
@Tag(name = "Athena Project Rest API")
@RequestMapping(value = ProjectController.PROJECT, produces = MediaType.APPLICATION_JSON_VALUE)
@Import(ControllerErrorHandler.class)
@RequiredArgsConstructor
public class ProjectController {

  public static final String PROJECT = "/project";
  private final ProjectService projectService;

  @GetMapping("/all")
  @Operation(summary = "Retrieve all projects with pagination", responses = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
      @ApiResponse(responseCode = "204", description = "No content to return")
  })
  public ResponseEntity<Page<ProjectDto>> getAll(
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
      @RequestParam(required = false) final String name
  ) {
    Sort.Direction sortDirection = Sort.Direction.fromString(direction);
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
    // Build ProjectFilterDto from individual parameters
    ProjectFilterDto filter = new ProjectFilterDto(code, name);
    Page<ProjectDto> result = projectService.getAll(pageable, filter);
    return ResponseEntity.ok(result);
  }

  @GetMapping
  @Operation(summary = "Retrieve project by project code or name", responses = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
      @ApiResponse(responseCode = "204", description = "No content to return")
  })
  public ResponseEntity<ProjectDto> search(
      @Parameter(name = "keyword", description = "The code or name of the project to retrieve")
      @RequestParam final String keyword
  ) {
    return ResponseEntityUtils.okOrNoContent(projectService.search(keyword));
  }

  @GetMapping("/{id}")
  @Operation(summary = "Retrieve project by id", responses = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
      @ApiResponse(responseCode = "204", description = "No content to return")
  })
  public ResponseEntity<ProjectDto> getById(
      @Parameter(name = "id", description = "The id of the project to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(projectService.getById(id));
  }

  @PostMapping
  @Operation(summary = "Save project or update the current one if any with the same code exists", responses = {
      @ApiResponse(responseCode = "201", description = "Project is created"),
      @ApiResponse(responseCode = "208", description = "Project already exists"),
      @ApiResponse(responseCode = "400", description = "Failed to process request"),
      @ApiResponse(responseCode = "409", description = "Project already exists")
  })
  public ResponseEntity<Void> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The project to save")
      @Validated @RequestBody final ProjectDto project
  ) {
    try {
      final ProjectDto savedProjectDto = projectService.save(project);
      return ResponseEntityUtils.created(PROJECT, savedProjectDto.getId());
    } catch (DataIntegrityViolationException ex) {
      if (ex.getCause() instanceof ConstraintViolationException) {
        Optional<ProjectDto> dbRecord = projectService.search(project.getCode());
        if (dbRecord.isPresent()) return ResponseEntityUtils.alreadyReported(PROJECT, dbRecord.get().getId());
      }
      return ResponseEntityUtils.conflicted();
    }
  }

  @PutMapping
  @Operation(summary = "Update project if one with the provided id exists", responses = {
      @ApiResponse(responseCode = "200", description = "Project is updated"),
      @ApiResponse(responseCode = "400", description = "Failed to process request")
  })
  public ResponseEntity<Void> update(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The project to update")
      @Validated(IdRequired.class) @RequestBody final ProjectDto project
  ) {
    final ProjectDto savedProjectDto = projectService.update(project);
    return ResponseEntityUtils.updated(PROJECT, savedProjectDto.getId());
  }
}
