package org.catools.athena.rest.core.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.catools.athena.core.model.EnvironmentDto;
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
@Tag(name = "Athena Environment API")
@RequestMapping(value = AthenaCoreConstant.ROOT_API, produces = APPLICATION_JSON_VALUE)
public class AthenaEnvironmentController {
    public static final String ENVIRONMENT_PATH = "/environment";
    public static final String ENVIRONMENTS_PATH = "/environments";

    private final AthenaCoreService athenaCoreService;

    public AthenaEnvironmentController(AthenaCoreService athenaCoreService) {
        this.athenaCoreService = athenaCoreService;
    }

    @GetMapping(ENVIRONMENTS_PATH)
    @Operation(
            summary = "Retrieve environments",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned data"),
                    @ApiResponse(responseCode = "204", description = "No content to return")
            })
    public ResponseEntity<Set<EnvironmentDto>> getEnvironments() {
        final Set<EnvironmentDto> environments = athenaCoreService.getEnvironments();
        return environments.isEmpty() ?
                ResponseEntityUtils.noContent() :
                ResponseEntityUtils.ok(environments);
    }

    @GetMapping(ENVIRONMENT_PATH)
    @Operation(
            summary = "Retrieve environment by environment code",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned data"),
                    @ApiResponse(responseCode = "204", description = "No content to return")
            })
    public ResponseEntity<EnvironmentDto> getEnvironmentByName(
            @Parameter(name = "envCode", description = "The code of the environment to return")
            @RequestParam final String envCode
    ) {
        final Optional<EnvironmentDto> environment = athenaCoreService.getEnvironmentByCode(envCode);
        return environment
                .map(ResponseEntityUtils::ok)
                .orElseGet(ResponseEntityUtils::noContent);
    }

    @GetMapping(ENVIRONMENT_PATH + "/{id}")
    @Operation(
            summary = "Retrieve environment by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned data"),
                    @ApiResponse(responseCode = "204", description = "No content to return")
            })
    public ResponseEntity<EnvironmentDto> getEnvironmentById(
            @Parameter(name = "id", description = "The id of the environment to return")
            @PathVariable final Long id
    ) {
        final Optional<EnvironmentDto> environment = athenaCoreService.getEnvironmentById(id);
        return environment
                .map(ResponseEntityUtils::ok)
                .orElseGet(ResponseEntityUtils::noContent);
    }

    @PostMapping(ENVIRONMENT_PATH)
    @Operation(
            summary = "Save environment",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Environment is created"),
                    @ApiResponse(responseCode = "208", description = "Environment is already exists"),
                    @ApiResponse(responseCode = "400", description = "Failed to process request")
            })
    public ResponseEntity<Void> saveEnvironment(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The environment to save")
            @Validated @RequestBody final EnvironmentDto environment
    ) {
        try {
            // We shouldn't have multiple environment with similar code
            final Optional<EnvironmentDto> environmentByCode = athenaCoreService.getEnvironmentByCode(environment.getCode());
            if (environmentByCode.isPresent()) {
                return ResponseEntityUtils.alreadyReported(ENVIRONMENT_PATH, environmentByCode.get().getId());
            }

            final EnvironmentDto savedEnvironmentDto = athenaCoreService.saveEnvironment(environment);
            return ResponseEntityUtils.created(ENVIRONMENT_PATH, savedEnvironmentDto.getId());
        } catch (Throwable generalEx) {
            // let GeneralExceptionHandler to take care of it
            throw new GeneralBadRequestException(generalEx);
        }
    }
}
