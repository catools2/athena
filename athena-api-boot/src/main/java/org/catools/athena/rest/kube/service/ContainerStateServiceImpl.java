package org.catools.athena.rest.kube.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.kube.model.ContainerStateDto;
import org.catools.athena.rest.kube.mapper.KubeMapper;
import org.catools.athena.rest.kube.model.ContainerState;
import org.catools.athena.rest.kube.repository.ContainerStateRepository;
import org.catools.athena.rest.kube.utils.KubeUtils;
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
  public ContainerStateDto save(ContainerStateDto record, Long containerId) {
    final ContainerState recordToSave = kubeMapper.containerStateDtoToContainerState(record, containerId);
    final ContainerState savedRecord = kubeUtils.saveContainerState(recordToSave, containerId);
    return kubeMapper.containerStateToContainerStateDto(savedRecord);
  }

  @Override
  public Set<ContainerStateDto> getAll() {
    return containerStateRepository.findAll().stream().map(kubeMapper::containerStateToContainerStateDto).collect(Collectors.toSet());
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
