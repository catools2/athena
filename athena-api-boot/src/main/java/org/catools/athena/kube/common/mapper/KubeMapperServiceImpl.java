package org.catools.athena.kube.common.mapper;

import lombok.RequiredArgsConstructor;
import org.catools.athena.kube.common.model.Container;
import org.catools.athena.kube.common.model.Pod;
import org.catools.athena.kube.common.repository.ContainerRepository;
import org.catools.athena.kube.common.repository.PodRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KubeMapperServiceImpl implements KubeMapperService {

  private final PodRepository podRepository;
  private final ContainerRepository containerRepository;

  @Override
  public Pod getPodById(Long id) {
    return podRepository.findById(id).orElse(null);
  }

  @Override
  public Container getContainerById(Long id) {
    return containerRepository.findById(id).orElse(null);
  }
}