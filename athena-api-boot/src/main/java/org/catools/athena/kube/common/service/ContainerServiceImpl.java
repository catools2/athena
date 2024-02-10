package org.catools.athena.kube.common.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.kube.common.mapper.KubeMapper;
import org.catools.athena.kube.common.model.Container;
import org.catools.athena.kube.common.repository.ContainerMetadataRepository;
import org.catools.athena.kube.common.repository.ContainerRepository;
import org.catools.athena.kube.model.ContainerDto;
import org.catools.athena.kube.utils.KubeUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.catools.athena.core.utils.MetadataPersistentHelper.normalizeMetadata;

@Service
@RequiredArgsConstructor
public class ContainerServiceImpl implements ContainerService {

  private final ContainerMetadataRepository containerMetadataRepository;

  private final ContainerRepository containerRepository;

  private final KubeMapper kubeMapper;

  private final KubeUtils kubeUtils;

  @Override
  public ContainerDto saveOrUpdate(ContainerDto entity) {
    Container container = kubeMapper.containerDtoToContainer(entity);
    final Container entityToSave = containerRepository.findByNameAndPodId(entity.getName(), entity.getPodId())
        .map(c -> {
          c.setImage(entity.getImage());
          c.setImageId(entity.getImageId());
          c.setReady(entity.getReady());
          c.setRestartCount(entity.getRestartCount());
          c.setStarted(entity.getStarted());
          c.setStartedAt(entity.getStartedAt());
          c.setType(entity.getType());

          c.getMetadata().removeIf(d1 -> container.getMetadata().stream().noneMatch(d2 -> d1.getName().equals(d2.getName()) && d1.getValue().equals(d2.getValue())));
          c.getMetadata().addAll(container.getMetadata().stream().filter(d1 -> c.getMetadata().stream().noneMatch(d2 -> d1.getName().equals(d2.getName()) && d1.getValue().equals(d2.getValue()))).collect(Collectors.toSet()));

          return c;
        }).orElse(container);

    entityToSave.setMetadata(normalizeMetadata(entityToSave.getMetadata(), containerMetadataRepository));

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
