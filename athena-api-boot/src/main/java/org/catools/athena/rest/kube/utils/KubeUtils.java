package org.catools.athena.rest.kube.utils;

import lombok.RequiredArgsConstructor;
import org.catools.athena.rest.kube.model.Container;
import org.catools.athena.rest.kube.model.ContainerState;
import org.catools.athena.rest.kube.model.Pod;
import org.catools.athena.rest.kube.model.PodStatus;
import org.catools.athena.rest.kube.repository.*;
import org.springframework.stereotype.Component;

import static org.catools.athena.rest.core.utils.MetadataPersistentHelper.normalizeMetadata;

@Component
@RequiredArgsConstructor
public class KubeUtils {

  private final PodStatusRepository podStatusRepository;
  private final PodMetadataRepository podMetadataRepository;
  private final PodAnnotationRepository podAnnotationRepository;
  private final PodLabelRepository podLabelRepository;
  private final PodSelectorRepository podSelectorRepository;
  private final PodRepository podRepository;

  private final ContainerRepository containerRepository;
  private final ContainerStateRepository containerStateRepository;
  private final ContainerMetadataRepository containerMetadataRepository;

  public Pod save(Pod pod) {

    pod.setStatus(normalizePodStatus(pod.getStatus()));
    pod.setMetadata(normalizeMetadata(pod.getMetadata(), podMetadataRepository));
    pod.setAnnotations(normalizeMetadata(pod.getAnnotations(), podAnnotationRepository));
    pod.setLabels(normalizeMetadata(pod.getLabels(), podLabelRepository));
    pod.setSelectors(normalizeMetadata(pod.getSelectors(), podSelectorRepository));

    return podRepository.saveAndFlush(pod);
  }

  public Container save(Container container) {
    container.setMetadata(normalizeMetadata(container.getMetadata(), containerMetadataRepository));
    return containerRepository.saveAndFlush(container);
  }

  public ContainerState save(ContainerState state, Long containerId) {
    return containerStateRepository.findBySyncTimeAndTypeAndMessageAndValueAndContainerId(state.getSyncTime(), state.getType(), state.getMessage(), state.getType(), containerId)
        .orElseGet(() -> containerStateRepository.saveAndFlush(state));
  }

  private PodStatus normalizePodStatus(PodStatus status) {
    return podStatusRepository.findByNameAndPhaseAndMessageAndReason(status.getName(), status.getPhase(), status.getMessage(), status.getReason())
        .orElseGet(() -> podStatusRepository.saveAndFlush(status));
  }

}
