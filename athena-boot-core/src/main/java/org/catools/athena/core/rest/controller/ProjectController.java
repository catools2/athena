package org.catools.athena.core.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.controlleradvice.ControllerErrorHandler;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.core.common.service.ProjectService;
import org.catools.athena.core.model.ProjectDto;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Athena Project Rest API")
@RequestMapping(value = ProjectController.PROJECT, produces = MediaType.APPLICATION_JSON_VALUE)
@Import(ControllerErrorHandler.class)
@RequiredArgsConstructor
public class ProjectController {

  public static final String PROJECT = "/project";
  private final ProjectService projectService;

  @GetMapping
  @Operation(
      summary = "Retrieve project by project code or name",
      responses = {
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
  @Operation(
      summary = "Retrieve project by id",
      responses = {
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
  @Operation(
      summary = "Save project or update the current one if any with the same code exists",
      responses = {
          @ApiResponse(responseCode = "201", description = "Project is created"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> saveOrUpdate(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The project to save")
      @Validated @RequestBody final ProjectDto project
  ) {
    final ProjectDto savedProjectDto = projectService.saveOrUpdate(project);
    return ResponseEntityUtils.created(PROJECT, savedProjectDto.getId());
  }
}
