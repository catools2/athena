package org.catools.athena.rest.core.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.config.CorePathDefinitions;
import org.catools.athena.rest.core.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

import static org.catools.athena.rest.core.config.CorePathDefinitions.PROJECTS_PATH;
import static org.catools.athena.rest.core.config.CorePathDefinitions.PROJECT_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "Athena Project Rest API")
@RequestMapping(value = CorePathDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProjectController {

  private final ProjectService projectService;

  @GetMapping(PROJECTS_PATH)
  @Operation(
      summary = "Retrieve projects",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<Set<ProjectDto>> getProjects() {
    return ResponseEntityUtils.okOrNoContent(projectService.getAll());
  }

  @GetMapping(PROJECT_PATH)
  @Operation(
      summary = "Retrieve project by project code",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<ProjectDto> getProjectByCode(
      @Parameter(name = "projectCode", description = "The code of the project to retrieve")
      @RequestParam final String projectCode
  ) {
    return ResponseEntityUtils.okOrNoContent(projectService.getByCode(projectCode));
  }

  @GetMapping(PROJECT_PATH + "/{id}")
  @Operation(
      summary = "Retrieve project by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<ProjectDto> getProjectById(
      @Parameter(name = "id", description = "The id of the project to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(projectService.getById(id));
  }

  @PostMapping(PROJECT_PATH)
  @Operation(
      summary = "Save project",
      responses = {
          @ApiResponse(responseCode = "201", description = "Project is created"),
          @ApiResponse(responseCode = "208", description = "Project Already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> saveProject(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The project to save")
      @Validated @RequestBody final ProjectDto project
  ) {
    final Optional<ProjectDto> projectByCode = projectService.getByCode(project.getCode());
    if (projectByCode.isPresent()) {
      return ResponseEntityUtils.alreadyReported(PROJECT_PATH, projectByCode.get().getId());
    }

    final ProjectDto savedProjectDto = projectService.save(project);
    return ResponseEntityUtils.created(PROJECT_PATH, savedProjectDto.getId());
  }
}
