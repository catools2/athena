package org.catools.athena.rest.core.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.service.EnvironmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

import static org.catools.athena.rest.core.controller.CoreDefinitions.ENVIRONMENTS_PATH;
import static org.catools.athena.rest.core.controller.CoreDefinitions.ENVIRONMENT_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "Athena Environment Rest API")
@RequestMapping(value = CoreDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EnvironmentController {

    private final EnvironmentService environmentService;

    @GetMapping(ENVIRONMENTS_PATH)
    @Operation(
            summary = "Retrieve environments",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
                    @ApiResponse(responseCode = "204", description = "No content to return")
            })
    public ResponseEntity<Set<EnvironmentDto>> getEnvironments() {
        return ResponseEntityUtils.okOrNoContent(environmentService.getEnvironments());
    }

    @GetMapping(ENVIRONMENT_PATH)
    @Operation(
            summary = "Retrieve environment by environment code",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
                    @ApiResponse(responseCode = "204", description = "No content to return")
            })
    public ResponseEntity<EnvironmentDto> getEnvironmentByCode(
            @Parameter(name = "envCode", description = "The code of the environment to retrieve")
            @RequestParam final String envCode
    ) {
        return ResponseEntityUtils.okOrNoContent(environmentService.getEnvironmentByCode(envCode));
    }

    @GetMapping(ENVIRONMENT_PATH + "/{id}")
    @Operation(
            summary = "Retrieve environment by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
                    @ApiResponse(responseCode = "204", description = "No content to return")
            })
    public ResponseEntity<EnvironmentDto> getEnvironmentById(
            @Parameter(name = "id", description = "The id of the environment to retrieve")
            @PathVariable final Long id
    ) {
        return ResponseEntityUtils.okOrNoContent(environmentService.getEnvironmentById(id));
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
        final Optional<EnvironmentDto> environmentByCode = environmentService.getEnvironmentByCode(environment.getCode());
        if (environmentByCode.isPresent()) {
            return ResponseEntityUtils.alreadyReported(ENVIRONMENT_PATH, environmentByCode.get().getId());
        }

        final EnvironmentDto savedEnvironmentDto = environmentService.saveEnvironment(environment);
        return ResponseEntityUtils.created(ENVIRONMENT_PATH, savedEnvironmentDto.getId());
    }
}
