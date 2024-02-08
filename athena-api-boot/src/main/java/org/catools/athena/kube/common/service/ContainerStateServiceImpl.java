package org.catools.athena.kube.common.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.kube.common.mapper.KubeMapper;
import org.catools.athena.kube.common.model.ContainerState;
import org.catools.athena.kube.common.repository.ContainerStateRepository;
import org.catools.athena.kube.model.ContainerStateDto;
import org.catools.athena.kube.utils.KubeUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContainerStateServiceImpl implements ContainerStateService {
  private final ContainerStateRepository containerStateRepository;

  private final KubeMapper kubeMapper;

  private final KubeUtils kubeUtils;

  @Override
  public ContainerStateDto save(ContainerStateDto entity, Long containerId) {
    final ContainerState entityToSave = kubeMapper.containerStateDtoToContainerState(entity, containerId);
    final ContainerState savedRecord = kubeUtils.save(entityToSave, containerId);
    return kubeMapper.containerStateToContainerStateDto(savedRecord);
  }

  @Override
  public Optional<ContainerStateDto> getById(Long id) {
    return containerStateRepository.findById(id).map(kubeMapper::containerStateToContainerStateDto);
  }

  @Override
  public Optional<ContainerStateDto> getState(ContainerStateDto stateDto, Long containerId) {
    return containerStateRepository.findBySyncTimeAndTypeAndMessageAndValueAndContainerId(stateDto.getSyncTime(), stateDto.getType(), stateDto.getMessage(), stateDto.getValue(), containerId).map(kubeMapper::containerStateToContainerStateDto);
  }

  @Override
  public Set<ContainerStateDto> getAllByContainerId(Long containerId) {
    return containerStateRepository.findAllByContainerId(containerId).stream().map(kubeMapper::containerStateToContainerStateDto).collect(Collectors.toSet());
  }
}
