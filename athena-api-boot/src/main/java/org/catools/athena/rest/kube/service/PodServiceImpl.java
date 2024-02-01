package org.catools.athena.rest.kube.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.kube.model.PodDto;
import org.catools.athena.rest.kube.mapper.KubeMapper;
import org.catools.athena.rest.kube.model.Pod;
import org.catools.athena.rest.kube.repository.PodRepository;
import org.catools.athena.rest.kube.utils.KubeUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PodServiceImpl implements PodService {

  private final PodRepository podRepository;

  private final KubeMapper kubeMapper;
  private final KubeUtils kubeUtils;

  @Override
  public PodDto save(PodDto entity) {
    final Pod entityToSave = kubeMapper.podDtoToPod(entity);
    final Pod savedRecord = kubeUtils.save(entityToSave);
    return kubeMapper.podToPodDto(savedRecord);
  }

  @Override
  public Optional<PodDto> getById(Long id) {
    return podRepository.findById(id).map(kubeMapper::podToPodDto);
  }

  @Override
  public Set<PodDto> getByProjectIdAndNamespace(Long projectId, String namespace) {
    return podRepository.findByNamespace(namespace).stream().map(kubeMapper::podToPodDto).collect(Collectors.toSet());
  }

  @Override
  public Optional<PodDto> getByNameAndNamespace(String name, String namespace) {
    return podRepository.findByNameAndNamespace(name, namespace).map(kubeMapper::podToPodDto);
  }
}
