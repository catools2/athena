package org.catools.athena.kube.common.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.kube.common.mapper.KubeMapper;
import org.catools.athena.kube.common.model.Container;
import org.catools.athena.kube.common.repository.ContainerRepository;
import org.catools.athena.kube.model.ContainerDto;
import org.catools.athena.kube.utils.KubeUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContainerServiceImpl implements ContainerService {

  private final ContainerRepository containerRepository;

  private final KubeMapper kubeMapper;

  private final KubeUtils kubeUtils;

  @Override
  public ContainerDto save(ContainerDto entity) {
    final Container entityToSave = kubeMapper.containerDtoToContainer(entity);
    final Container savedRecord = kubeUtils.save(entityToSave);
    return kubeMapper.containerToContainerDto(savedRecord);
  }

  @Override
  public Optional<ContainerDto> getById(Long id) {
    return containerRepository.findById(id).map(kubeMapper::containerToContainerDto);
  }

  @Override
  public Optional<ContainerDto> getByNameAndPodId(String name, Long podId) {
    return containerRepository.findByNameAndPodId(name, podId).map(kubeMapper::containerToContainerDto);
  }

  @Override
  public Set<ContainerDto> getAllByPodId(Long podId) {
    return containerRepository.findAllByPodId(podId).stream().map(kubeMapper::containerToContainerDto).collect(Collectors.toSet());
  }
}
