package org.catools.athena.kube.common.service;


import org.catools.athena.common.service.SaveOrUpdateService;
import org.catools.athena.model.kube.PodDto;

import java.util.Optional;
import java.util.Set;

public interface PodService extends SaveOrUpdateService<PodDto> {

  /**
   * Retrieve pods by namespace
   */
  Set<PodDto> getPods(String project, String namespace);

  /**
   * Retrieve pod by name
   */
  Optional<PodDto> getByNameAndNamespace(String name, String namespace);
}