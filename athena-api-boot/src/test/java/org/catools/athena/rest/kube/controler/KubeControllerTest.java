package org.catools.athena.rest.kube.controler;

import org.catools.athena.rest.core.controller.CoreControllerTest;
import org.catools.athena.rest.kube.builder.KubeBuilder;
import org.catools.athena.rest.kube.mapper.KubeMapper;
import org.catools.athena.rest.kube.model.Container;
import org.catools.athena.rest.kube.model.ContainerState;
import org.catools.athena.rest.kube.model.Pod;
import org.catools.athena.rest.kube.repository.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class KubeControllerTest extends CoreControllerTest {
  static Pod POD;
  static Pod POD2;
  static Container CONTAINER;
  static Container CONTAINER2;
  static ContainerState CONTAINER_STATE;

  @Autowired
  PodRepository podRepository;

  @Autowired
  ContainerRepository containerRepository;

  @Autowired
  ContainerMetadataRepository containerMetadataRepository;

  @Autowired
  PodStatusRepository podStatusRepository;

  @Autowired
  PodMetadataRepository podMetadataRepository;

  @Autowired
  PodAnnotationRepository podAnnotationRepository;

  @Autowired
  PodLabelRepository podLabelRepository;

  @Autowired
  PodSelectorRepository podSelectorRepository;

  @Autowired
  ContainerStateRepository containerStateRepository;

  @Autowired
  ContainerStateController containerStateController;

  @Autowired
  ContainerController containerController;

  @Autowired
  PodController podController;

  @Autowired
  KubeMapper kubeMapper;

  @BeforeAll
  public void beforeAll() {
    POD = buildAndSavePod();
    POD2 = buildAndSavePod();
    CONTAINER = buildAndSaveContainer();
    CONTAINER2 = buildAndSaveContainer();
    CONTAINER_STATE = containerStateRepository.saveAndFlush(KubeBuilder.buildContainerState(CONTAINER));
  }

  private Pod buildAndSavePod() {
    final Pod pod = KubeBuilder.buildPod(PROJECT);

    pod.setStatus(podStatusRepository.saveAndFlush(pod.getStatus()));
    pod.setLabels(pod.getLabels().stream().map(podLabelRepository::saveAndFlush).collect(Collectors.toSet()));
    pod.setSelectors(pod.getSelectors().stream().map(podSelectorRepository::saveAndFlush).collect(Collectors.toSet()));
    pod.setAnnotations(pod.getAnnotations().stream().map(podAnnotationRepository::saveAndFlush).collect(Collectors.toSet()));
    pod.setMetadata(pod.getMetadata().stream().map(podMetadataRepository::saveAndFlush).collect(Collectors.toSet()));

    return podRepository.saveAndFlush(pod);
  }

  private Container buildAndSaveContainer() {
    final Container container = KubeBuilder.buildContainer(POD);

    container.setMetadata(container.getMetadata().stream().map(containerMetadataRepository::saveAndFlush).collect(Collectors.toSet()));

    return containerRepository.saveAndFlush(container);
  }
}