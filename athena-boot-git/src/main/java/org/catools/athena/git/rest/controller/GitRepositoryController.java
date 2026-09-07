package org.catools.athena.git.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.model.page.PageDto;
import org.catools.athena.git.common.service.GitRepositoryService;
import org.catools.athena.model.git.GitRepositoryDto;
import org.catools.athena.model.git.GitRepositoryInventoryDto;
import org.catools.athena.model.git.GitRepositorySummaryDto;
import org.catools.athena.model.git.GitRepositoryTrendPointDto;
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

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@Tag(name = "Athena Git Repository Rest API")
@RequestMapping(produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GitRepositoryController {
  public static final String REPOSITORY = "/repo";

  private final GitRepositoryService repositoryService;

  @GetMapping("/summary")
  @Operation(
      summary = "Retrieve repository dashboard summary",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<GitRepositorySummaryDto> getSummary(
      @Parameter(name = "keyword", description = "Filter by repository name, url, or host")
      @RequestParam(required = false) final String keyword,
      @Parameter(name = "host", description = "Filter by repository host")
      @RequestParam(required = false) final String host,
      @Parameter(name = "freshness", description = "Filter by freshness status")
            @RequestParam(required = false) final String freshness,
            @Parameter(name = "windowDays", description = "Limit summary data to the latest N days relative to the newest matching sync time")
            @RequestParam(required = false) final Integer windowDays
  ) {
        return ResponseEntity.ok(repositoryService.getSummary(keyword, host, freshness, windowDays));
  }

  @GetMapping("/trend")
  @Operation(
      summary = "Retrieve repository dashboard trend buckets",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<List<GitRepositoryTrendPointDto>> getTrend(
      @Parameter(name = "keyword", description = "Filter by repository name, url, or host")
      @RequestParam(required = false) final String keyword,
      @Parameter(name = "host", description = "Filter by repository host")
      @RequestParam(required = false) final String host,
      @Parameter(name = "freshness", description = "Filter by freshness status")
            @RequestParam(required = false) final String freshness,
            @Parameter(name = "windowDays", description = "Limit trend buckets to the latest N days relative to the newest bucket")
            @RequestParam(required = false) final Integer windowDays
  ) {
        return ResponseEntity.ok(repositoryService.getTrend(keyword, host, freshness, windowDays));
  }

  @GetMapping("/all")
  @Operation(
      summary = "Retrieve repository dashboard inventory with pagination",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<PageDto<GitRepositoryInventoryDto>> getAll(
      @Parameter(name = "page", description = "Page number (0-based)")
      @RequestParam(defaultValue = "0") final int page,
      @Parameter(name = "size", description = "Page size")
      @RequestParam(defaultValue = "10") final int size,
      @Parameter(name = "sort", description = "Sort field")
      @RequestParam(defaultValue = "lastSync") final String sort,
      @Parameter(name = "direction", description = "Sort direction (ASC or DESC)")
      @RequestParam(defaultValue = "DESC") final String direction,
      @Parameter(name = "keyword", description = "Filter by repository name, url, or host")
      @RequestParam(required = false) final String keyword,
      @Parameter(name = "host", description = "Filter by repository host")
      @RequestParam(required = false) final String host,
      @Parameter(name = "freshness", description = "Filter by freshness status")
      @RequestParam(required = false) final String freshness
  ) {
    final Sort.Direction sortDirection = Sort.Direction.fromString(direction);
    final Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
    return ResponseEntity.ok(toPageDto(repositoryService.getAll(pageable, keyword, host, freshness)));
  }

  @GetMapping(REPOSITORY)
  @Operation(
      summary = "Retrieve repository where keyword can be either repository name or url",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<GitRepositoryDto> search(
      @Parameter(name = "keyword", description = "The repository name or url to search for")
      @RequestParam final String keyword
  ) {
    log.info("search(keyword={})", keyword);
    return ResponseEntityUtils.okOrNoContent(repositoryService.findByNameOrUrl(keyword));
  }

    @GetMapping(REPOSITORY + "/{id}")
  @Operation(
      summary = "Retrieve repository by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<GitRepositoryDto> getById(
      @Parameter(name = "id", description = "The id of the repository to retrieve")
      @PathVariable final Long id
  ) {
    log.info("getById(id={})", id);
    return ResponseEntityUtils.okOrNoContent(repositoryService.getById(id));
  }

    @PostMapping(REPOSITORY)
  @Operation(
      summary = "Save repository or update the current one if any with the same name or url exists",
      responses = {
          @ApiResponse(responseCode = "201", description = "Repository is created"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> saveOrUpdate(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The repository to save or update")
      @Validated @RequestBody final GitRepositoryDto repository
  ) {
    log.info("saveOrUpdate(repository.name={}, repository.url={})", repository.getName(), repository.getUrl());
    final GitRepositoryDto savedRepositoryDto = repositoryService.saveOrUpdate(repository);
    return ResponseEntityUtils.created(REPOSITORY, savedRepositoryDto.getId());
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
