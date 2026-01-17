package org.catools.athena.kube.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.common.utils.RetryUtils;
import org.catools.athena.kube.common.mapper.KubeMapper;
import org.catools.athena.kube.common.mapper.KubeMapperService;
import org.catools.athena.kube.common.model.Container;
import org.catools.athena.kube.common.model.Pod;
import org.catools.athena.kube.common.model.PodStatus;
import org.catools.athena.kube.common.repository.ContainerMetadataRepository;
import org.catools.athena.kube.common.repository.MetadataRepository;
import org.catools.athena.kube.common.repository.PodAnnotationRepository;
import org.catools.athena.kube.common.repository.PodLabelRepository;
import org.catools.athena.kube.common.repository.PodMetadataRepository;
import org.catools.athena.kube.common.repository.PodRepository;
import org.catools.athena.kube.common.repository.PodSelectorRepository;
import org.catools.athena.kube.common.repository.PodStatusRepository;
import org.catools.athena.model.core.NameValuePair;
import org.catools.athena.model.kube.PodDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class PodServiceImpl implements PodService {

  private final ContainerMetadataRepository containerMetadataRepository;
  private final PodAnnotationRepository podAnnotationRepository;
  private final PodMetadataRepository podMetadataRepository;
  private final PodSelectorRepository podSelectorRepository;
  private final PodStatusRepository podStatusRepository;
  private final PodLabelRepository podLabelRepository;
  private final PodRepository podRepository;
  private final KubeMapperService kubeMapperService;
  private final KubeMapper kubeMapper;

  @Override
  @Transactional
  public PodDto saveOrUpdate(PodDto entity) {
    log.debug("Saving entity: {}", entity);
    final Pod pod = kubeMapper.podDtoToPod(entity);
    final Pod podToSave = podRepository.findByNameAndNamespace(entity.getName(), entity.getNamespace())
        .map(p -> {
          p.setName(pod.getName());
          p.setHostname(pod.getHostname());
          p.setNamespace(pod.getNamespace());
          p.setNodeName(pod.getNodeName());
          p.setCreatedAt(pod.getCreatedAt());
          p.setDeletedAt(pod.getDeletedAt());

          p.setProjectId(pod.getProjectId());

          normalizeRelationships(p, pod);

          pod.getContainers()
              .stream()
              .filter(d1 -> p.getContainers().stream().noneMatch(d2 -> d1.getName().equals(d2.getName())))
              .forEach(p::addContainer);

          return p;
        }).orElseGet(() -> {
          normalizeRelationships(pod, pod);
          return pod;
        });

    final Pod savedRecord = RetryUtils.retry(3, 1000, integer -> podRepository.saveAndFlush(podToSave));
    return kubeMapper.podToPodDto(savedRecord);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<PodDto> getById(Long id) {
    return podRepository.findById(id).map(kubeMapper::podToPodDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Set<PodDto> getPods(String project, String namespace) {
    // Use query with join on project code; eliminates kubeMapperService.getProjectId() call
    Set<Pod> pods;
    if (project != null && !project.isBlank()) {
      Long projectId = kubeMapperService.getProjectId(project);
      pods = podRepository.findByProjectIdAndNamespace(projectId, namespace);
    } else {
      pods = podRepository.findByNamespace(namespace);
    }
    return pods.stream().map(kubeMapper::podToPodDto).collect(Collectors.toSet());
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<PodDto> getByNameAndNamespace(String name, String namespace) {
    return podRepository.findByNameAndNamespace(name, namespace).map(kubeMapper::podToPodDto);
  }

  private void normalizeRelationships(Pod target, Pod source) {
    target.setStatus(normalizePodStatus(source.getStatus()));

    target.setMetadata(normalizeMetadata(source.getMetadata(), podMetadataRepository));
    target.setAnnotations(normalizeMetadata(source.getAnnotations(), podAnnotationRepository));
    target.setLabels(normalizeMetadata(source.getLabels(), podLabelRepository));
    target.setSelectors(normalizeMetadata(source.getSelectors(), podSelectorRepository));

    for (Container container : source.getContainers()) {
      container.setPod(source);
      container.setMetadata(normalizeMetadata(container.getMetadata(), containerMetadataRepository));
    }
  }

  private synchronized PodStatus normalizePodStatus(PodStatus status) {
    return podStatusRepository.findByNameAndPhaseAndMessageAndReason(status.getName(), status.getPhase(), status.getMessage(), status.getReason())
        .orElseGet(() -> RetryUtils.retry(3, 1000, integer -> podStatusRepository.saveAndFlush(status)));
  }

  public synchronized <T extends NameValuePair> Set<T> normalizeMetadata(Set<T> metadataSet, MetadataRepository<T> metadataRepository) {
    final Set<T> metadata = new HashSet<>();

    for (T md : metadataSet) {
      try {
        // Read md from DB and if MD does not exist we create one and assign it
        T normalizedMd = metadataRepository.findByNameAndValue(md.getName(), md.getValue())
            .orElseGet(() -> {
              try {
                return metadataRepository.saveAndFlush(md);
              } catch (org.springframework.dao.DataIntegrityViolationException e) {
                // Another thread inserted it between our check and save - retry lookup
                return metadataRepository.findByNameAndValue(md.getName(), md.getValue())
                    .orElseThrow(() -> new RuntimeException("Failed to find or create metadata after retry", e));
              }
            });

        metadata.add(normalizedMd);
      } catch (Exception e) {
        throw new RuntimeException("Failed to create detached instance", e);
      }
    }

    return metadata;
  }
}
