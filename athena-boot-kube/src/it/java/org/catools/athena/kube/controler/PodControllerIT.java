package org.catools.athena.kube.controler;

import feign.TypedResponse;
import org.catools.athena.common.feign.FeignUtils;
import org.catools.athena.kube.builder.KubeBuilder;
import org.catools.athena.kube.common.model.Pod;
import org.catools.athena.kube.model.PodDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PodControllerIT extends KubeControllerIT {

  @Test
  @Order(1)
  void saveShallSavePodIfDoesNotExist() {
    Pod pod = KubeBuilder.buildPod(projectDto);
    PodDto podDto = kubeMapper.podToPodDto(pod);

    TypedResponse<Void> response = podFeignClient.saveOrUpdate(podDto);
    assertThat(response.status(), equalTo(201));
    Long entityId = FeignUtils.getIdFromLocationHeader(response);
    assertThat(entityId, notNullValue());
  }

  @Test
  @Order(2)
  void saveShallUpdatePodIdPodWithTheSameNameAndNamespaceExists() {
    Pod pod = KubeBuilder.buildPod(projectDto);
    pod.getAnnotations().add(KubeControllerIT.pod1.getAnnotations().stream().findFirst().orElse(null));
    pod.getSelectors().add(KubeControllerIT.pod1.getSelectors().stream().findFirst().orElse(null));
    pod.getLabels().add(KubeControllerIT.pod1.getLabels().stream().findFirst().orElse(null));
    pod.getMetadata().add(KubeControllerIT.pod1.getMetadata().stream().findFirst().orElse(null));

    PodDto podDto = kubeMapper.podToPodDto(pod);
    podDto.setName(KubeControllerIT.pod1.getName());
    podDto.setNamespace(KubeControllerIT.pod1.getNamespace());

    TypedResponse<Void> response = podFeignClient.saveOrUpdate(podDto);
    assertThat(response.status(), equalTo(201));
    Long entityId = FeignUtils.getIdFromLocationHeader(response);
    assertThat(entityId, notNullValue());
  }

  @Test
  @Order(2)
  void getPodsShallReturnCorrectValueWhenValidNameAndNamespaceProvided() {

    TypedResponse<Set<PodDto>> response = podFeignClient.getAll(projectDto.getId(), pod1.getNamespace());
    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().isEmpty(), equalTo(false));
  }

  @Test
  @Order(2)
  void getPodByNameAndNamespaceShallReturnCorrectValueWhenValidNameAndNamespaceProvided() {

    TypedResponse<PodDto> response = podFeignClient.getByNameAndNamespace(pod1.getName(), pod1.getNamespace());
    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getId(), notNullValue());
    assertThat(response.body().getName(), notNullValue());
    assertThat(response.body().getProject(), notNullValue());
  }

  @Test
  @Order(2)
  void shallReturnCorrectValueWhenValidPodIdProvided() {
    TypedResponse<PodDto> response = podFeignClient.getById(pod1.getId());
    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getId(), notNullValue());
    assertThat(response.body().getName(), notNullValue());
    assertThat(response.body().getProject(), notNullValue());
  }
}