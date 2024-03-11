package org.catools.athena.kube.mapper;

import org.catools.athena.AthenaBaseIT;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.common.entity.Project;
import org.catools.athena.core.common.service.ProjectService;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.kube.builder.KubeBuilder;
import org.catools.athena.kube.common.mapper.KubeMapper;
import org.catools.athena.kube.common.model.Container;
import org.catools.athena.kube.common.model.Pod;
import org.catools.athena.kube.common.repository.*;
import org.catools.athena.kube.common.service.PodService;
import org.catools.athena.kube.model.ContainerDto;
import org.catools.athena.kube.model.PodDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;

class KubeMapperIT extends AthenaBaseIT {

  Project PROJECT;

  @Autowired
  ProjectService projectService;

  @Autowired
  KubeMapper kubeMapper;

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
  PodService podService;

  @BeforeAll
  public void beforeAll() {
    final ProjectDto projectDto = CoreBuilder.buildProjectDto();
    projectDto.setId(projectService.saveOrUpdate(projectDto).getId());
    PROJECT = CoreBuilder.buildProject(projectDto);
  }

  @Test
  void podDtoToPod() {
    final Pod pod = KubeBuilder.buildPod(PROJECT);
    final PodDto podDto = kubeMapper.podToPodDto(pod);
    verifyPods(pod, podDto);
  }

  @Test
  void podDtoToPod_shallReturnNullIfTheInputIsNull() {
    assertThat(kubeMapper.podToPodDto(null), nullValue());
  }

  @Test
  void podToPodDto() {
    final PodDto podDto = KubeBuilder.buildPodDto(KubeBuilder.buildPod(PROJECT));
    final Pod pod = kubeMapper.podDtoToPod(podDto);
    verifyPods(pod, podDto);
  }

  @Test
  void podToPodDto_shallReturnNullIfTheInputIsNull() {
    assertThat(kubeMapper.podDtoToPod(null), nullValue());
  }

  @Test
  void containerDtoToContainer() {
    final Pod pod = KubeBuilder.buildPod(PROJECT);
    final Container container = KubeBuilder.buildContainer(pod);
    final ContainerDto containerDto = kubeMapper.containerToContainerDto(container);

    verifyContainers(container, containerDto);
  }

  @Test
  void containerDtoToContainer_shallReturnNullIfTheInputIsNull() {
    assertThat(kubeMapper.containerToContainerDto(null), nullValue());
  }

  @Test
  void containerToContainerDto() {
    final Pod savedPod = buildAndSavePod();

    final ContainerDto containerDto = KubeBuilder.buildContainerDto(KubeBuilder.buildContainer(savedPod));
    final Container container = kubeMapper.containerDtoToContainer(containerDto);

    verifyContainers(container, containerDto);
  }

  @Test
  void containerToContainerDto_shallReturnNullIfTheInputIsNull() {
    assertThat(kubeMapper.containerDtoToContainer(null), nullValue());
  }

  private Pod buildAndSavePod() {
    final PodDto pod = KubeBuilder.buildPodDto(KubeBuilder.buildPod(PROJECT));
    PodDto podDto = podService.saveOrUpdate(pod);
    return kubeMapper.podDtoToPod(podDto);
  }

  private static void verifyContainers(Container container, ContainerDto containerDto) {
    assertThat(container.getId(), equalTo(containerDto.getId()));
    assertThat(container.getType(), equalTo(containerDto.getType()));
    assertThat(container.getName(), equalTo(containerDto.getName()));
    assertThat(container.getImage(), equalTo(containerDto.getImage()));
    assertThat(container.getImageId(), equalTo(containerDto.getImageId()));
    assertThat(container.getReady(), equalTo(containerDto.getReady()));
    assertThat(container.getStarted(), equalTo(containerDto.getStarted()));
    assertThat(container.getRestartCount(), equalTo(containerDto.getRestartCount()));
    assertThat(container.getStartedAt(), equalTo(containerDto.getStartedAt()));
    verifyNameValuePairs(container.getMetadata(), containerDto.getMetadata());
  }

  private static void verifyPods(Pod pod, PodDto podDto) {
    assertThat(pod.getId(), equalTo(podDto.getId()));
    assertThat(pod.getUid(), equalTo(podDto.getUid()));
    assertThat(pod.getName(), equalTo(podDto.getName()));
    assertThat(pod.getNamespace(), equalTo(podDto.getNamespace()));
    assertThat(pod.getHostname(), equalTo(podDto.getHostname()));
    assertThat(pod.getNodeName(), equalTo(podDto.getNodeName()));
    assertThat(pod.getCreatedAt(), equalTo(podDto.getCreatedAt()));
    assertThat(pod.getDeletedAt(), equalTo(podDto.getDeletedAt()));

    assertThat(pod.getProject().getCode(), equalTo(podDto.getProject()));

    assertThat(pod.getStatus().getId(), equalTo(podDto.getStatus().getId()));
    assertThat(pod.getStatus().getName(), equalTo(podDto.getStatus().getName()));
    assertThat(pod.getStatus().getMessage(), equalTo(podDto.getStatus().getMessage()));
    assertThat(pod.getStatus().getPhase(), equalTo(podDto.getStatus().getPhase()));
    assertThat(pod.getStatus().getReason(), equalTo(podDto.getStatus().getReason()));

    verifyNameValuePairs(pod.getMetadata(), podDto.getMetadata());
    verifyNameValuePairs(pod.getAnnotations(), podDto.getAnnotations());
    verifyNameValuePairs(pod.getLabels(), podDto.getLabels());
    verifyNameValuePairs(pod.getSelectors(), podDto.getSelectors());
  }
}