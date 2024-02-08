package org.catools.athena.kube.common.mapper;

import org.catools.athena.kube.common.model.Container;
import org.catools.athena.kube.common.model.Pod;

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