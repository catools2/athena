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
class PodControllerTest extends KubeControllerTest {

  @Test
  @Order(1)
  void saveShallSavePodIfDoesNotExist() {
    Pod pod = KubeBuilder.buildPod(PROJECT);
    PodDto podDto = kubeMapper.podToPodDto(pod);


    ResponseEntity<Void> response = podController.save(podDto);
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }

  @Test
  @Order(2)
  void saveShallNotSavePodIdPodWithTheSameNameAndNamespaceExists() {
    PodDto podDto = kubeMapper.podToPodDto(POD);


    ResponseEntity<Void> response = podController.save(podDto);
    assertThat(response.getStatusCode().value(), equalTo(208));
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