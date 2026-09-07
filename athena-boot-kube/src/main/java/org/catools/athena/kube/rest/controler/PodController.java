package org.catools.athena.kube.rest.controler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.model.page.PageDto;
import org.catools.athena.kube.common.service.PodService;
import org.catools.athena.model.kube.PodDto;
import org.catools.athena.model.kube.PodInventoryDto;
import org.catools.athena.model.kube.PodSummaryDto;
import org.catools.athena.model.kube.PodTrendPointDto;
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
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "Athena Kube Pod Rest API")
@RequestMapping(produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PodController {
  public static final String POD = "/pod";
  public static final String PODS = "/pods";

  private final PodService podService;

  @GetMapping("/summary")
  @Operation(
      summary = "Retrieve kube pod dashboard summary",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<PodSummaryDto> getSummary(
      @Parameter(name = "project", description = "Filter by project code")
      @RequestParam(required = false) final String project,
      @Parameter(name = "namespace", description = "Filter by namespace")
      @RequestParam(required = false) final String namespace,
      @Parameter(name = "name", description = "Filter by pod name")
      @RequestParam(required = false) final String name,
      @Parameter(name = "nodeName", description = "Filter by node name")
      @RequestParam(required = false) final String nodeName,
      @Parameter(name = "status", description = "Filter by pod status")
            @RequestParam(required = false) final String status,
            @Parameter(name = "windowDays", description = "Limit summary data to the latest N days relative to the newest matching sync time")
            @RequestParam(required = false) final Integer windowDays
  ) {
        return ResponseEntity.ok(podService.getSummary(project, namespace, name, nodeName, status, windowDays));
  }

  @GetMapping("/trend")
  @Operation(
      summary = "Retrieve kube pod dashboard trend buckets",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<List<PodTrendPointDto>> getTrend(
      @Parameter(name = "project", description = "Filter by project code")
      @RequestParam(required = false) final String project,
      @Parameter(name = "namespace", description = "Filter by namespace")
      @RequestParam(required = false) final String namespace,
      @Parameter(name = "name", description = "Filter by pod name")
      @RequestParam(required = false) final String name,
      @Parameter(name = "nodeName", description = "Filter by node name")
      @RequestParam(required = false) final String nodeName,
      @Parameter(name = "status", description = "Filter by pod status")
            @RequestParam(required = false) final String status,
            @Parameter(name = "windowDays", description = "Limit trend buckets to the latest N days relative to the newest bucket")
            @RequestParam(required = false) final Integer windowDays
  ) {
        return ResponseEntity.ok(podService.getTrend(project, namespace, name, nodeName, status, windowDays));
  }

  @GetMapping("/all")
  @Operation(
      summary = "Retrieve kube pod dashboard inventory with pagination",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<PageDto<PodInventoryDto>> getAllPaged(
      @Parameter(name = "page", description = "Page number (0-based)")
      @RequestParam(defaultValue = "0") final int page,
      @Parameter(name = "size", description = "Page size")
      @RequestParam(defaultValue = "10") final int size,
      @Parameter(name = "sort", description = "Sort field")
      @RequestParam(defaultValue = "lastSync") final String sort,
      @Parameter(name = "direction", description = "Sort direction (ASC or DESC)")
      @RequestParam(defaultValue = "DESC") final String direction,
      @Parameter(name = "project", description = "Filter by project code")
      @RequestParam(required = false) final String project,
      @Parameter(name = "namespace", description = "Filter by namespace")
      @RequestParam(required = false) final String namespace,
      @Parameter(name = "name", description = "Filter by pod name")
      @RequestParam(required = false) final String name,
      @Parameter(name = "nodeName", description = "Filter by node name")
      @RequestParam(required = false) final String nodeName,
      @Parameter(name = "status", description = "Filter by pod status")
      @RequestParam(required = false) final String status
  ) {
    final Sort.Direction sortDirection = Sort.Direction.fromString(direction);
    final Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
    return ResponseEntity.ok(toPageDto(podService.getAll(pageable, project, namespace, name, nodeName, status)));
  }

  @GetMapping(PODS)
  @Operation(
      summary = "Retrieve pods by project id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<Set<PodDto>> getAll(
      @Parameter(name = "project", description = "The project code of the pod to retrieve")
      @RequestParam final String project,
      @Parameter(name = "namespace", description = "The namespace of the pod to retrieve")
      @RequestParam final String namespace
  ) {
    return ResponseEntityUtils.okOrNoContent(podService.getPods(project, namespace));
  }

  @GetMapping(POD)
  @Operation(
      summary = "Retrieve pod by pod name and namespace",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<PodDto> getByNameAndNamespace(
      @Parameter(name = "name", description = "The name of the pod to retrieve")
      @RequestParam final String name,
      @Parameter(name = "namespace", description = "The namespace of the pod to retrieve")
      @RequestParam final String namespace
  ) {
    return ResponseEntityUtils.okOrNoContent(podService.getByNameAndNamespace(name, namespace));
  }

  @GetMapping(POD + "/{id}")
  @Operation(
      summary = "Retrieve pod by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<PodDto> getById(
      @Parameter(name = "id", description = "The id of the pod to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(podService.getById(id));
  }

  @PostMapping(POD)
  @Operation(
      summary = "Save pod or update the current one if any with the same name and namespace exists",
      responses = {
          @ApiResponse(responseCode = "201", description = "Pod is created"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> saveOrUpdate(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The pod to save or update")
      @Validated @RequestBody final PodDto pod
  ) {
    final PodDto savedPodDto = podService.saveOrUpdate(pod);
    return ResponseEntityUtils.created(POD, savedPodDto.getId());
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
