package org.catools.athena.kube.common.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.kube.common.model.Container;
import org.catools.athena.kube.common.repository.ContainerRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KubeMapperServiceImpl implements KubeMapperService {

  private final ContainerRepository containerRepository;

  @Override
  public Container getContainerById(Long id) {
    return containerRepository.findById(id).orElse(null);
  }

}
