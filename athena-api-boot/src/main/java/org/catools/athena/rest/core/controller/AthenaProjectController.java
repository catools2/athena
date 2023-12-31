package org.catools.athena.rest.core.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.config.AthenaCoreConstant;
import org.catools.athena.rest.core.exception.GeneralBadRequestException;
import org.catools.athena.rest.core.service.AthenaCoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "Athena Project API")
@RequestMapping(value = AthenaCoreConstant.ROOT_API, produces = APPLICATION_JSON_VALUE)
public class AthenaProjectController {
    public static final String PROJECT_PATH = "/project";
    public static final String PROJECTS_PATH = "/projects";

    private final AthenaCoreService athenaCoreService;

    public AthenaProjectController(AthenaCoreService athenaCoreService) {
        this.athenaCoreService = athenaCoreService;
    }

    @GetMapping(PROJECTS_PATH)
    @Operation(
            summary = "Retrieve projects",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned data"),
                    @ApiResponse(responseCode = "204", description = "No content to return")
            })
    public ResponseEntity<Set<ProjectDto>> getProjects() {
        final Set<ProjectDto> projects = athenaCoreService.getProjects();
        return projects.isEmpty() ?
                ResponseEntityUtils.noContent() :
                ResponseEntityUtils.ok(projects);
    }

    @GetMapping(PROJECT_PATH)
    @Operation(
            summary = "Retrieve project by project code",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned data"),
                    @ApiResponse(responseCode = "204", description = "No content to return")
            })
    public ResponseEntity<ProjectDto> getProjectByCode(
            @Parameter(name = "projectCode", description = "The code of the project to return")
            @RequestParam final String projectCode
    ) {
        final Optional<ProjectDto> project = athenaCoreService.getProjectByCode(projectCode);
        return project
                .map(ResponseEntityUtils::ok)
                .orElseGet(ResponseEntityUtils::noContent);
    }

    @GetMapping(PROJECT_PATH + "/{id}")
    @Operation(
            summary = "Retrieve project by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned data"),
                    @ApiResponse(responseCode = "204", description = "No content to return")
            })
    public ResponseEntity<ProjectDto> getProjectById(
            @Parameter(name = "id", description = "The id of the project to return")
            @PathVariable final Long id
    ) {
        final Optional<ProjectDto> project = athenaCoreService.getProjectById(id);
        return project
                .map(ResponseEntityUtils::ok)
                .orElseGet(ResponseEntityUtils::noContent);
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
        try {
            // We shouldn't have multiple project with similar code
            final Optional<ProjectDto> projectByCode = athenaCoreService.getProjectByCode(project.getCode());
            if (projectByCode.isPresent()) {
                return ResponseEntityUtils.alreadyReported(projectByCode.get().getId());
            }

            // We shouldn't have multiple project with similar name
            final Optional<ProjectDto> projectByName = athenaCoreService.getProjectByName(project.getName());
            if (projectByName.isPresent()) {
                return ResponseEntityUtils.alreadyReported(projectByName.get().getId());
            }

            final ProjectDto savedProjectDto = athenaCoreService.saveProject(project);
            return ResponseEntityUtils.created(savedProjectDto.getId());
        } catch (Throwable generalEx) {
            // let GeneralExceptionHandler to take care of it
            throw new GeneralBadRequestException(generalEx);
        }
    }
}
