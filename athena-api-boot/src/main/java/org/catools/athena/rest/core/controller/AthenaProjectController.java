package org.catools.athena.rest.core.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.rest.core.config.AthenaCoreConstant;
import org.catools.athena.rest.core.exception.GeneralBadRequestException;
import org.catools.athena.rest.core.service.AthenaCoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.catools.athena.rest.core.config.AthenaCoreConstant.CACHE_MAX_AGE_ONE_HOUR;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = AthenaCoreConstant.ROOT_API, produces = APPLICATION_JSON_VALUE)
@ApiResponses(value = {
    @ApiResponse(responseCode = "401", description = "Request is not authenticated"),
    @ApiResponse(responseCode = "403", description = "Unauthorized to perform function")
})
public class AthenaProjectController {
  public static final String PROJECT = "/project";
  public static final String PROJECTS = "/projects";

  private final AthenaCoreService athenaCoreService;

  public AthenaProjectController(AthenaCoreService athenaCoreService) {
    this.athenaCoreService = athenaCoreService;
  }

  @GetMapping(PROJECTS)
  @Operation(
      summary = "Retrieve projects",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully returned data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<List<ProjectDto>> getProjects() {
    final List<ProjectDto> projects = athenaCoreService.getProjects();
    return projects.isEmpty() ?
        ResponseEntity.noContent().cacheControl(CACHE_MAX_AGE_ONE_HOUR).build() :
        ResponseEntity.ok().cacheControl(CACHE_MAX_AGE_ONE_HOUR).body(projects);
  }

  @GetMapping(PROJECT)
  @Operation(
      summary = "Retrieve project by project code",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully returned data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<ProjectDto> getProject(
      @Parameter(name = "projectCode")
      @RequestParam final String projectCode
  ) {
    final Optional<ProjectDto> project = athenaCoreService.getProjectByCode(projectCode);
    return project
        .map(projectDto -> ResponseEntity.ok().cacheControl(CACHE_MAX_AGE_ONE_HOUR).body(projectDto))
        .orElseGet(() -> ResponseEntity.noContent().cacheControl(CACHE_MAX_AGE_ONE_HOUR).build());
  }

  @PostMapping(PROJECT)
  @Operation(
      summary = "Save project",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "208", description = "Project Already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<ProjectDto> saveProject(
      @Parameter(description = "Project to save", name = "project", required = true)
      @Validated @RequestBody final ProjectDto project
  ) {
    try {
      // We shouldn't have multiple project with similar code
      final Optional<ProjectDto> projectByCode = athenaCoreService.getProjectByCode(project.getCode());
      if (projectByCode.isPresent()) {
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(projectByCode.get());
      }

      // We shouldn't have multiple project with similar name
      final Optional<ProjectDto> projectByName = athenaCoreService.getProjectByName(project.getName());
      if (projectByName.isPresent()) {
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
      }

      final ProjectDto savedProjectDto = athenaCoreService.saveProject(project);
      return ResponseEntity.ok().body(savedProjectDto);
    } catch (Throwable generalEx) {
      // let GeneralExceptionHandler to take care of it
      throw new GeneralBadRequestException(generalEx);
    }
  }
}
