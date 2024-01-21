package org.catools.athena.rest.kube.mapper;

import org.catools.athena.rest.kube.model.Container;
import org.catools.athena.rest.kube.model.Pod;

public interface KubeMapperService {

  /**
   * Get pod by name
   */
  Pod getPodById(Long id);

  /**
   * Get container by id
   */
  Container getContainerById(Long id);
}