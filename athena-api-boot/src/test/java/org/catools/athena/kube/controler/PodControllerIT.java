package org.catools.athena.kube.controler;

import org.catools.athena.kube.builder.KubeBuilder;
import org.catools.athena.kube.common.model.Pod;
import org.catools.athena.kube.model.PodDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.ResponseEntity;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PodControllerIT extends KubeControllerIT {

  @Test
  @Order(1)
  void saveShallSavePodIfDoesNotExist() {
    Pod pod = KubeBuilder.buildPod(PROJECT);
    PodDto podDto = kubeMapper.podToPodDto(pod);

    ResponseEntity<Void> response = podController.saveOrUpdate(podDto);
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }

  @Test
  @Order(2)
  void saveShallUpdatePodIdPodWithTheSameNameAndNamespaceExists() {
    Pod pod = KubeBuilder.buildPod(PROJECT);
    pod.getAnnotations().add(POD.getAnnotations().stream().findFirst().orElse(null));
    pod.getSelectors().add(POD.getSelectors().stream().findFirst().orElse(null));
    pod.getLabels().add(POD.getLabels().stream().findFirst().orElse(null));
    pod.getMetadata().add(POD.getMetadata().stream().findFirst().orElse(null));

    PodDto podDto = kubeMapper.podToPodDto(pod);
    podDto.setName(POD.getName());
    podDto.setNamespace(POD.getNamespace());

    ResponseEntity<Void> response = podController.saveOrUpdate(podDto);
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }

  @Test
  @Order(2)
  void getPodsShallReturnCorrectValueWhenValidNameAndNamespaceProvided() {

    ResponseEntity<Set<PodDto>> response = podController.getAll(PROJECT.getId(), POD.getNamespace());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().isEmpty(), equalTo(false));
  }

  @Test
  @Order(2)
  void getPodByNameAndNamespaceShallReturnCorrectValueWhenValidNameAndNamespaceProvided() {

    ResponseEntity<PodDto> response = podController.getByNameAndNamespace(POD.getName(), POD.getNamespace());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getId(), notNullValue());
    assertThat(response.getBody().getName(), notNullValue());
    assertThat(response.getBody().getProject(), notNullValue());
  }

  @Test
  @Order(2)
  void shallReturnCorrectValueWhenValidPodIdProvided() {
    ResponseEntity<PodDto> response = podController.getById(POD.getId());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getId(), notNullValue());
    assertThat(response.getBody().getName(), notNullValue());
    assertThat(response.getBody().getProject(), notNullValue());
  }
}