package org.catools.athena.rest.kube.utils;

import lombok.RequiredArgsConstructor;
import org.catools.athena.rest.kube.model.*;
import org.catools.athena.rest.kube.repository.*;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

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

  public Pod savePod(Pod pod) {

    pod.setStatus(normalizePodStatus(pod.getStatus()));
    pod.setMetadata(normalizePodMetadata(pod.getMetadata()));
    pod.setAnnotations(normalizePodAnnotation(pod.getAnnotations()));
    pod.setLabels(normalizePodLabel(pod.getLabels()));
    pod.setSelectors(normalizePodSelector(pod.getSelectors()));

    return podRepository.saveAndFlush(pod);
  }

  public Container saveContainer(Container container) {
    container.setMetadata(normalizeContainerMetadata(container.getMetadata()));
    return containerRepository.saveAndFlush(container);
  }

  public ContainerState saveContainerState(ContainerState state, Long containerId) {
    return containerStateRepository.findBySyncTimeAndTypeAndMessageAndValueAndContainerId(state.getSyncTime(), state.getType(), state.getMessage(), state.getType(), containerId)
        .orElseGet(() -> containerStateRepository.saveAndFlush(state));
  }

  private PodStatus normalizePodStatus(PodStatus status) {
    return podStatusRepository.findByNameAndPhaseAndMessageAndReason(status.getName(), status.getPhase(), status.getMessage(), status.getReason())
        .orElseGet(() -> podStatusRepository.saveAndFlush(status));
  }

  private Set<PodMetadata> normalizePodMetadata(Set<PodMetadata> input) {
    return input.stream().map(nvp -> podMetadataRepository.findByNameAndValue(nvp.getName(), nvp.getValue())
            .orElseGet(() -> podMetadataRepository.saveAndFlush(nvp)))
        .collect(Collectors.toSet());
  }

  private Set<PodAnnotation> normalizePodAnnotation(Set<PodAnnotation> input) {
    return input.stream().map(nvp -> podAnnotationRepository.findByNameAndValue(nvp.getName(), nvp.getValue())
            .orElseGet(() -> podAnnotationRepository.saveAndFlush(nvp)))
        .collect(Collectors.toSet());
  }

  private Set<PodLabel> normalizePodLabel(Set<PodLabel> input) {
    return input.stream().map(nvp -> podLabelRepository.findByNameAndValue(nvp.getName(), nvp.getValue())
            .orElseGet(() -> podLabelRepository.saveAndFlush(nvp)))
        .collect(Collectors.toSet());
  }

  private Set<PodSelector> normalizePodSelector(Set<PodSelector> input) {
    return input.stream().map(nvp -> podSelectorRepository.findByNameAndValue(nvp.getName(), nvp.getValue())
            .orElseGet(() -> podSelectorRepository.saveAndFlush(nvp)))
        .collect(Collectors.toSet());
  }

  private Set<ContainerMetadata> normalizeContainerMetadata(Set<ContainerMetadata> input) {
    return input.stream().map(nvp -> containerMetadataRepository.findByNameAndValue(nvp.getName(), nvp.getValue())
            .orElseGet(() -> containerMetadataRepository.saveAndFlush(nvp)))
        .collect(Collectors.toSet());
  }
}
