package org.catools.athena.kube.common.mapper;

import org.catools.athena.kube.common.model.Container;

public interface KubeMapperService {

  /**
   * Get container by id
   */
  Container getContainerById(Long id);

}