package org.catools.athena.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.catools.athena.core.model.ProjectDto;
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
public class AthenaProjectController {

  private final AthenaPipelineService athenaPipelineService;

  public AthenaProjectController(AthenaPipelineService AthenaPipelineService) {
    this.athenaPipelineService = AthenaPipelineService;
  }

  @GetMapping(PROJECTS)
  @Operation(
      summary = "Retrieve projects",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully returned data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<List<ProjectDto>> getProjects() {
    final List<ProjectDto> projects = athenaPipelineService.getProjects();
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
      @Parameter(name = "projectCode")
      @RequestParam final String projectCode
  ) {
    final Optional<ProjectDto> project = athenaPipelineService.getProjectByCode(projectCode);
    return project
        .map(projectDto -> ResponseEntity.ok().cacheControl(MAX_AGE_SINGLE_DAY).body(projectDto))
        .orElseGet(() -> ResponseEntity.noContent().cacheControl(MAX_AGE_SINGLE_DAY).build());
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
      final Optional<ProjectDto> projectByCode = athenaPipelineService.getProjectByCode(project.getCode());
      if (projectByCode.isPresent()) {
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(projectByCode.get());
      }

      // We shouldn't have multiple project with similar name
      final Optional<ProjectDto> projectByName = athenaPipelineService.getProjectByName(project.getName());
      if (projectByName.isPresent()) {
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
      }

      final ProjectDto savedProjectDto = athenaPipelineService.saveProject(project);
      return ResponseEntity.ok().body(savedProjectDto);
    } catch (Throwable generalEx) {
      // let GeneralExceptionHandler to take care of it
      throw new GeneralBadRequestException(generalEx);
    }
  }
}
