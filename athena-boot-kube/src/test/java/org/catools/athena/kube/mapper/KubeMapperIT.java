package org.catools.athena.kube.mapper;

import org.catools.athena.AthenaSpringBootIT;
import org.catools.athena.configs.StagedTestData;
import org.catools.athena.kube.builder.KubeBuilder;
import org.catools.athena.kube.common.mapper.KubeMapper;
import org.catools.athena.kube.common.model.Container;
import org.catools.athena.kube.common.model.Pod;
import org.catools.athena.kube.common.service.PodService;
import org.catools.athena.model.core.ProjectDto;
import org.catools.athena.model.kube.ContainerDto;
import org.catools.athena.model.kube.PodDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KubeMapperIT extends AthenaSpringBootIT {

  ProjectDto projectDto;

  @Autowired
  KubeMapper kubeMapper;

  @Autowired
  PodService podService;

  @BeforeAll
  public void beforeAll() {
    projectDto = StagedTestData.getRandomProject();
  }

  @Test
  void podDtoToPod() {
    final Pod pod = KubeBuilder.buildPod(projectDto);
    final PodDto podDto = kubeMapper.podToPodDto(pod);
    verifyPods(pod, podDto);
  }

  @Test
  void podDtoToPod_shallReturnNullIfTheInputIsNull() {
    assertThat(kubeMapper.podToPodDto(null), nullValue());
  }

  @Test
  void podToPodDto() {
    final PodDto podDto = KubeBuilder.buildPodDto(KubeBuilder.buildPod(projectDto), projectDto);
    final Pod pod = kubeMapper.podDtoToPod(podDto);
    verifyPods(pod, podDto);
  }

  @Test
  void podToPodDto_shallReturnNullIfTheInputIsNull() {
    assertThat(kubeMapper.podDtoToPod(null), nullValue());
  }

  @Test
  void containerDtoToContainer() {
    final Pod pod = KubeBuilder.buildPod(projectDto);
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
    final Container container = kubeMapper.containerDtoToContainer(savedPod, containerDto);

    verifyContainers(container, containerDto);
  }

  @Test
  void containerToContainerDto_shallReturnNullIfTheInputIsNull() {
    assertThat(kubeMapper.containerDtoToContainer(null, null), nullValue());
  }

  private Pod buildAndSavePod() {
    final Pod pod = KubeBuilder.buildPod(projectDto);
    PodDto podDto = podService.saveOrUpdate(KubeBuilder.buildPodDto(pod, projectDto));
    return kubeMapper.podDtoToPod(podDto);
  }

  private void verifyContainers(Container container, ContainerDto containerDto) {
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

  private void verifyPods(Pod pod, PodDto podDto) {
    assertThat(pod.getId(), equalTo(podDto.getId()));
    assertThat(pod.getUid(), equalTo(podDto.getUid()));
    assertThat(pod.getName(), equalTo(podDto.getName()));
    assertThat(pod.getNamespace(), equalTo(podDto.getNamespace()));
    assertThat(pod.getHostname(), equalTo(podDto.getHostname()));
    assertThat(pod.getNodeName(), equalTo(podDto.getNodeName()));
    assertThat(pod.getCreatedAt(), equalTo(podDto.getCreatedAt()));
    assertThat(pod.getDeletedAt(), equalTo(podDto.getDeletedAt()));

    assertThat(pod.getProjectId(), equalTo(StagedTestData.getProjectByCode(podDto.getProject()).getId()));

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