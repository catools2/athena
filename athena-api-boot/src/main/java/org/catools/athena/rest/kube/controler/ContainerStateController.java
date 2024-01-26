package org.catools.athena.rest.kube.controler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.kube.model.ContainerStateDto;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.config.CorePathDefinitions;
import org.catools.athena.rest.kube.service.ContainerStateService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

import static org.catools.athena.rest.kube.config.KubePathDefinitions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "Athena Kube Container State Rest API")
@RequestMapping(value = CorePathDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ContainerStateController {

  private final ContainerStateService containerStateService;

  @GetMapping(CONTAINER_STATES)
  @Operation(
      summary = "Retrieve containers",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<Set<ContainerStateDto>> getContainerStates() {
    return ResponseEntityUtils.okOrNoContent(containerStateService.getAll());
  }

  @GetMapping(CONTAINER_STATES + "/{containerId}")
  @Operation(
      summary = "Retrieve containers by container id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<Set<ContainerStateDto>> getContainerStates(
      @Parameter(name = "containerId", description = "The container id of the container states to retrieve")
      @RequestParam final Long containerId
  ) {
    return ResponseEntityUtils.okOrNoContent(containerStateService.getAllByContainerId(containerId));
  }


  @GetMapping(CONTAINER_STATE + "/{id}")
  @Operation(
      summary = "Retrieve container by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<ContainerStateDto> getContainerStateById(
      @Parameter(name = "id", description = "The id of the container to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(containerStateService.getById(id));
  }

  @PostMapping(CONTAINER_STATE)
  @Operation(
      summary = "Save container state",
      responses = {
          @ApiResponse(responseCode = "201", description = "Container State is created"),
          @ApiResponse(responseCode = "208", description = "Container State is already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> save(
      @Parameter(name = "containerId", description = "The id of the container that state belongs to")
      @PathVariable final Long containerId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The container state to save")
      @Validated @RequestBody final ContainerStateDto stateDto
  ) {
    final Optional<ContainerStateDto> containerByCode = containerStateService.getState(stateDto, containerId);
    if (containerByCode.isPresent()) {
      return ResponseEntityUtils.alreadyReported(CONTAINER, containerByCode.get().getId());
    }

    final ContainerStateDto savedContainerStateDto = containerStateService.save(stateDto, containerId);
    return ResponseEntityUtils.created(CONTAINER_STATE, savedContainerStateDto.getId());
  }
}
