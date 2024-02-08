package org.catools.athena.kube.controler;

import org.catools.athena.kube.builder.KubeBuilder;
import org.catools.athena.kube.model.ContainerDto;
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
class ContainerControllerTest extends KubeControllerTest {

  @Test
  @Order(1)
  void saveShallSavePodIfDoesNotExist() {
    final ContainerDto container = KubeBuilder.buildContainerDto(KubeBuilder.buildContainer(POD));

    ResponseEntity<Void> response = containerController.save(container);
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }

  @Test
  @Order(10)
  void shallNotSaveContainerIfOneWithTheSameNameExistsForThePod() {
    final ContainerDto container = KubeBuilder.buildContainerDto(CONTAINER);

    ResponseEntity<Void> response = containerController.save(container);
    assertThat(response.getStatusCode().value(), equalTo(208));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }

  @Test
  @Order(1)
  void shallSaveContainerIfOneWithTheSameNameExistsForTheDifferentPod() {
    final ContainerDto container = KubeBuilder.buildContainerDto(CONTAINER);
    container.setId(null);
    container.setPodId(POD2.getId());

    ResponseEntity<Void> response = containerController.save(container);
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }

  @Test
  @Order(2)
  void shallReturnCorrectValueWhenValidIdProvided() {
    ResponseEntity<ContainerDto> response = containerController.getById(CONTAINER.getId());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getId(), equalTo(CONTAINER.getId()));
    assertThat(response.getBody().getName(), equalTo(CONTAINER.getName()));
    assertThat(response.getBody().getPodId(), equalTo(CONTAINER.getPod().getId()));
  }

  @Test
  @Order(10)
  void shallReturnCorrectValueWhenValidNameProvided() {
    ResponseEntity<ContainerDto> response = containerController.getByName(CONTAINER.getName(), CONTAINER.getPod().getId());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getId(), equalTo(CONTAINER.getId()));
    assertThat(response.getBody().getName(), equalTo(CONTAINER.getName()));
    assertThat(response.getBody().getPodId(), equalTo(CONTAINER.getPod().getId()));
  }

  @Test
  @Order(2)
  void getContainersShallReturnCorrectSetWhenValidPodIdProvided() {
    ResponseEntity<Set<ContainerDto>> response = containerController.getAll(CONTAINER.getPod().getId());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().size(), notNullValue());
  }
}