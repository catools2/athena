package org.catools.athena.spec.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.model.apispec.ApiSpecDto;
import org.catools.athena.model.apispec.ApiSpecDriftDto;
import org.catools.athena.model.apispec.ApiSpecFreshnessDto;
import org.catools.athena.model.apispec.ApiSpecInventoryDto;
import org.catools.athena.model.apispec.ApiSpecSummaryDto;
import org.catools.athena.model.page.PageDto;
import org.catools.athena.spec.common.service.ApiSpecService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Tag(name = "Athena Api Specification API")
@RestController
@RequestMapping(value = {"", ApiSpecController.API_SPEC}, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ApiSpecController {
  public static final String API_SPEC = "/spec";

  private final ApiSpecService apiSpecService;

  @GetMapping("/summary")
  @Operation(
      summary = "Retrieve API specification workspace summary values",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<ApiSpecSummaryDto> getSummary(
      @Parameter(name = "project", description = "Filter by project code")
      @RequestParam(required = false) final String project,
      @Parameter(name = "name", description = "Filter by specification name")
      @RequestParam(required = false) final String name,
      @Parameter(name = "title", description = "Filter by specification title")
      @RequestParam(required = false) final String title,
      @Parameter(name = "version", description = "Filter by specification version")
    @RequestParam(required = false) final String version,
    @Parameter(name = "freshness", description = "Filter by freshness category")
    @RequestParam(required = false) final String freshness,
    @Parameter(name = "windowDays", description = "Limit summary data to the latest N days relative to the newest matching sync time")
    @RequestParam(required = false) final Integer windowDays
  ) {
    log.info("getSummary(project={}, name={}, title={}, version={}, freshness={}, windowDays={})", project, name, title, version, freshness, windowDays);
    return ResponseEntity.ok(apiSpecService.getSummary(project, name, title, version, freshness, windowDays));
  }

    @GetMapping("/freshness")
    @Operation(
            summary = "Retrieve API specification freshness distribution values",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
                    @ApiResponse(responseCode = "204", description = "No content to return")
            })
    public ResponseEntity<ApiSpecFreshnessDto> getFreshness(
            @Parameter(name = "project", description = "Filter by project code")
            @RequestParam(required = false) final String project,
            @Parameter(name = "name", description = "Filter by specification name")
            @RequestParam(required = false) final String name,
            @Parameter(name = "title", description = "Filter by specification title")
            @RequestParam(required = false) final String title,
            @Parameter(name = "version", description = "Filter by specification version")
            @RequestParam(required = false) final String version,
            @Parameter(name = "freshness", description = "Filter by freshness category")
            @RequestParam(required = false) final String freshness,
            @Parameter(name = "windowDays", description = "Limit freshness data to the latest N days relative to the newest matching sync time")
            @RequestParam(required = false) final Integer windowDays
    ) {
        log.info("getFreshness(project={}, name={}, title={}, version={}, freshness={}, windowDays={})", project, name, title, version, freshness, windowDays);
        return ResponseEntity.ok(apiSpecService.getFreshness(project, name, title, version, freshness, windowDays));
    }

    @GetMapping("/drift")
    @Operation(
            summary = "Retrieve API specifications that are aging, stale, or missing sync timestamps",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
                    @ApiResponse(responseCode = "204", description = "No content to return")
            })
    public ResponseEntity<PageDto<ApiSpecDriftDto>> getDrift(
            @Parameter(name = "page", description = "Page number (0-based)")
            @RequestParam(defaultValue = "0") final int page,
            @Parameter(name = "size", description = "Page size")
            @RequestParam(defaultValue = "10") final int size,
            @Parameter(name = "sort", description = "Sort field")
            @RequestParam(defaultValue = "syncAgeDays") final String sort,
            @Parameter(name = "direction", description = "Sort direction (ASC or DESC)")
            @RequestParam(defaultValue = "DESC") final String direction,
            @Parameter(name = "project", description = "Filter by project code")
            @RequestParam(required = false) final String project,
            @Parameter(name = "name", description = "Filter by specification name")
            @RequestParam(required = false) final String name,
            @Parameter(name = "title", description = "Filter by specification title")
            @RequestParam(required = false) final String title,
            @Parameter(name = "version", description = "Filter by specification version")
            @RequestParam(required = false) final String version,
            @Parameter(name = "freshness", description = "Filter by freshness category")
            @RequestParam(required = false) final String freshness
    ) {
        log.info("getDrift(page={}, size={}, sort={}, direction={}, project={}, name={}, title={}, version={}, freshness={})", page, size, sort, direction, project, name, title, version, freshness);
        final Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        final Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        return ResponseEntity.ok(toPageDto(apiSpecService.getDrift(pageable, project, name, title, version, freshness)));
    }

  @GetMapping("/all")
  @Operation(
      summary = "Retrieve API specifications with pagination and optional filters",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
    public ResponseEntity<PageDto<ApiSpecInventoryDto>> getAll(
      @Parameter(name = "page", description = "Page number (0-based)")
      @RequestParam(defaultValue = "0") final int page,
      @Parameter(name = "size", description = "Page size")
      @RequestParam(defaultValue = "10") final int size,
      @Parameter(name = "sort", description = "Sort field")
      @RequestParam(defaultValue = "name") final String sort,
      @Parameter(name = "direction", description = "Sort direction (ASC or DESC)")
      @RequestParam(defaultValue = "ASC") final String direction,
      @Parameter(name = "project", description = "Filter by project code")
      @RequestParam(required = false) final String project,
      @Parameter(name = "name", description = "Filter by specification name")
      @RequestParam(required = false) final String name,
      @Parameter(name = "title", description = "Filter by specification title")
      @RequestParam(required = false) final String title,
      @Parameter(name = "version", description = "Filter by specification version")
            @RequestParam(required = false) final String version,
            @Parameter(name = "freshness", description = "Filter by freshness category")
            @RequestParam(required = false) final String freshness
  ) {
        log.info("getAll(page={}, size={}, sort={}, direction={}, project={}, name={}, title={}, version={}, freshness={})", page, size, sort, direction, project, name, title, version, freshness);
    final Sort.Direction sortDirection = Sort.Direction.fromString(direction);
    final Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
                return ResponseEntity.ok(toPageDto(apiSpecService.getAll(pageable, project, name, title, version, freshness)));
  }

  @PostMapping
  @Operation(
      summary = "Save new API specification or update the exist one if the specification with the same name exists for the same project",
      responses = {
          @ApiResponse(responseCode = "201", description = "API specification is created"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> saveOrUpdate(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The api spec to save or update")
      @Validated @RequestBody final ApiSpecDto apiSpecDto
  ) {
    log.info("saveOrUpdate(apiSpecDto.name={}, apiSpecDto.project={})", apiSpecDto.getName(), apiSpecDto.getProject());
    final ApiSpecDto savedApiSpec = apiSpecService.saveOrUpdate(apiSpecDto);
    return ResponseEntityUtils.created(API_SPEC, savedApiSpec.getId());
  }

  @GetMapping("/{id}")
  @Operation(
      summary = "Retrieve api spec by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<ApiSpecDto> getById(
      @Parameter(name = "id", description = "The id of the api spec to retrieve")
      @PathVariable final Long id
  ) {
    log.info("getById(id={})", id);
    return ResponseEntityUtils.okOrNoContent(apiSpecService.getById(id));
  }

  @GetMapping
  @Operation(
      summary = "Retrieve api spec by project code and name",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<ApiSpecDto> search(
      @Parameter(name = "project", description = "The project code of the api spec to retrieve")
      @RequestParam final String project,
      @Parameter(name = "name", description = "The name of the api spec to retrieve")
      @RequestParam final String name
  ) {
    log.info("search(project={}, name={})", project, name);
    return ResponseEntityUtils.okOrNoContent(apiSpecService.getByProjectCodeAndName(project, name));
  }

    private static <T> PageDto<T> toPageDto(Page<T> page) {
        return PageDto.<T>builder()
                .content(page.getContent())
                .number(page.getNumber())
                .size(page.getSize())
                .numberOfElements(page.getNumberOfElements())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .empty(page.isEmpty())
                .build();
    }
}
