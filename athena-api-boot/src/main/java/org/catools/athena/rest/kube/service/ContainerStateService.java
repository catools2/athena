package org.catools.athena.rest.kube.service;

import org.catools.athena.kube.model.ContainerStateDto;

import java.util.Optional;
import java.util.Set;

public interface ContainerStateService {
  /**
   * Save entity
   */
  ContainerStateDto save(ContainerStateDto entity, Long containerId);

  /**
   * Retrieve all entity
   */
  Set<ContainerStateDto> getAll();

  /**
   * Retrieve entity by id
   */
  Optional<ContainerStateDto> getById(Long id);

  /**
   * Retrieve entity by syncTime, type, message, value and container id
   */
  Optional<ContainerStateDto> getState(ContainerStateDto stateDto, Long containerId);

  /**
   * Retrieve all entity by container id
   */
  Set<ContainerStateDto> getAllByContainerId(Long containerId);
}
