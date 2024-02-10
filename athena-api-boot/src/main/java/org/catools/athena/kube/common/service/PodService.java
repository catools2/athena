package org.catools.athena.kube.common.service;


import org.catools.athena.common.service.BaseIdentifiableService;
import org.catools.athena.kube.model.PodDto;

import java.util.Optional;
import java.util.Set;

public interface PodService extends BaseIdentifiableService<PodDto> {

  /**
   * Retrieve pods by namespace
   */
  Set<PodDto> getByProjectIdAndNamespace(Long projectId, String namespace);

  /**
   * Retrieve pod by name
   */
  Optional<PodDto> getByNameAndNamespace(String name, String namespace);
}