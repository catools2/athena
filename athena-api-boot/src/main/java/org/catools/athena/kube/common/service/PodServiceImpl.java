package org.catools.athena.kube.common.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.kube.common.mapper.KubeMapper;
import org.catools.athena.kube.common.model.Pod;
import org.catools.athena.kube.common.repository.*;
import org.catools.athena.kube.model.PodDto;
import org.catools.athena.kube.utils.KubeUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.catools.athena.core.utils.MetadataPersistentHelper.normalizeMetadata;

@Service
@RequiredArgsConstructor
public class PodServiceImpl implements PodService {

  private final PodRepository podRepository;
  private final PodMetadataRepository podMetadataRepository;
  private final PodAnnotationRepository podAnnotationRepository;
  private final PodLabelRepository podLabelRepository;
  private final PodSelectorRepository podSelectorRepository;

  private final KubeMapper kubeMapper;
  private final KubeUtils kubeUtils;

  @Override
  public PodDto saveOrUpdate(PodDto entity) {
    final Pod pod = kubeMapper.podDtoToPod(entity);
    final Pod podToSave = podRepository.findByNameAndNamespace(entity.getName(), entity.getNamespace())
        .map(p -> {
          p.setName(pod.getName());
          p.setHostname(pod.getHostname());
          p.setNamespace(pod.getNamespace());
          p.setNodeName(pod.getNodeName());
          p.setCreatedAt(pod.getCreatedAt());
          p.setDeletedAt(pod.getDeletedAt());

          p.setStatus(pod.getStatus());
          p.setProject(pod.getProject());

          p.getMetadata().removeIf(d1 -> pod.getMetadata().stream().noneMatch(d2 -> d1.getName().equals(d2.getName()) && d1.getValue().equals(d2.getValue())));
          p.getMetadata().addAll(pod.getMetadata().stream().filter(d1 -> p.getMetadata().stream().noneMatch(d2 -> d1.getName().equals(d2.getName()) && d1.getValue().equals(d2.getValue()))).collect(Collectors.toSet()));

          p.getAnnotations().removeIf(d1 -> pod.getAnnotations().stream().noneMatch(d2 -> d1.getName().equals(d2.getName()) && d1.getValue().equals(d2.getValue())));
          p.getAnnotations().addAll(pod.getAnnotations().stream().filter(d1 -> p.getAnnotations().stream().noneMatch(d2 -> d1.getName().equals(d2.getName()) && d1.getValue().equals(d2.getValue()))).collect(Collectors.toSet()));

          p.getLabels().removeIf(d1 -> pod.getLabels().stream().noneMatch(d2 -> d1.getName().equals(d2.getName()) && d1.getValue().equals(d2.getValue())));
          p.getLabels().addAll(pod.getLabels().stream().filter(d1 -> p.getLabels().stream().noneMatch(d2 -> d1.getName().equals(d2.getName()) && d1.getValue().equals(d2.getValue()))).collect(Collectors.toSet()));

          p.getSelectors().removeIf(d1 -> pod.getSelectors().stream().noneMatch(d2 -> d1.getName().equals(d2.getName()) && d1.getValue().equals(d2.getValue())));
          p.getSelectors().addAll(pod.getSelectors().stream().filter(d1 -> p.getSelectors().stream().noneMatch(d2 -> d1.getName().equals(d2.getName()) && d1.getValue().equals(d2.getValue()))).collect(Collectors.toSet()));

          return p;
        }).orElse(pod);

    podToSave.setMetadata(normalizeMetadata(podToSave.getMetadata(), podMetadataRepository));
    podToSave.setAnnotations(normalizeMetadata(podToSave.getAnnotations(), podAnnotationRepository));
    podToSave.setLabels(normalizeMetadata(podToSave.getLabels(), podLabelRepository));
    podToSave.setSelectors(normalizeMetadata(podToSave.getSelectors(), podSelectorRepository));

    final Pod savedRecord = kubeUtils.save(podToSave);
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
