package org.catools.athena.rest.apispec.controller;

import com.google.gson.JsonElement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.apispec.model.ApiSpecDto;
import org.catools.athena.rest.apispec.service.ApiSpecService;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.config.CorePathDefinitions;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.catools.athena.rest.apispec.config.ApiSpecPathDefinitions.API_SPEC_PATH;
import static org.catools.athena.rest.apispec.config.ApiSpecPathDefinitions.OPEN_SPEC_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Athena Api Specification Metric Collector API")
@RestController
@RequestMapping(value = CorePathDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ApiSpecController {

    private final ApiSpecService apiSpecService;

    @PostMapping(OPEN_SPEC_PATH)
    @Operation(
            summary = "Save open api json specification as api spec",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Api spec is created"),
                    @ApiResponse(responseCode = "208", description = "Api spec is already exists"),
                    @ApiResponse(responseCode = "400", description = "Failed to process request")
            })
    public ResponseEntity<Void> saveOpenApiSpec(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The open api specification in json format")
            @RequestBody final JsonElement openAPI,
            @Parameter(name = "apiSpecName", description = "The open api specification unique name")
            @PathVariable final String apiSpecName,
            @Parameter(name = "projectCode", description = "The project code for the api spec")
            @PathVariable final String projectCode
    ) {
        final ApiSpecDto saveExecutionStatus = apiSpecService.saveOpenApiSpec(openAPI, apiSpecName, projectCode).getKey();
        return ResponseEntityUtils.created(API_SPEC_PATH, saveExecutionStatus.getId());
    }

    @PostMapping(API_SPEC_PATH)
    @Operation(
            summary = "Save api spec",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Api spec is created"),
                    @ApiResponse(responseCode = "208", description = "Api spec is already exists"),
                    @ApiResponse(responseCode = "400", description = "Failed to process request")
            })
    public ResponseEntity<Void> saveApiSpec(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The api spec to save")
            @Validated @RequestBody final ApiSpecDto apiSpecDto
    ) {
        final Optional<ApiSpecDto> apiSpecFromDb = apiSpecService.getApiSpecByProjectCodeAndName(apiSpecDto.getProject(), apiSpecDto.getName());

        if (apiSpecFromDb.isPresent()) {
            return ResponseEntityUtils.alreadyReported(API_SPEC_PATH, apiSpecFromDb.get().getId());
        }

        final ApiSpecDto saveApiSpec = apiSpecService.saveApiSpec(apiSpecDto);
        return ResponseEntityUtils.created(API_SPEC_PATH, saveApiSpec.getId());
    }

    @GetMapping(API_SPEC_PATH + "/{id}")
    @Operation(
            summary = "Retrieve api spec by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
                    @ApiResponse(responseCode = "204", description = "No content to return")
            })
    public ResponseEntity<ApiSpecDto> getApiSpecById(
            @Parameter(name = "id", description = "The id of the api spec to retrieve")
            @PathVariable final Long id
    ) {
        return ResponseEntityUtils.okOrNoContent(apiSpecService.getApiSpecById(id));
    }

    @GetMapping(API_SPEC_PATH)
    @Operation(
            summary = "Retrieve api spec by project code and name",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
                    @ApiResponse(responseCode = "204", description = "No content to return")
            })
    public ResponseEntity<ApiSpecDto> getApiSpecByProjectCodeAndName(
            @Parameter(name = "projectCode", description = "The project code of the api spec to retrieve")
            @PathVariable final String projectCode,
            @Parameter(name = "name", description = "The name of the api spec to retrieve")
            @PathVariable final String name
    ) {
        return ResponseEntityUtils.okOrNoContent(apiSpecService.getApiSpecByProjectCodeAndName(projectCode, name));
    }
}
