package org.catools.athena.rest.kube.controler;

import org.catools.athena.kube.model.ContainerStateDto;
import org.catools.athena.rest.kube.builder.KubeBuilder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.ResponseEntity;

import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ContainerStateControllerTest extends KubeControllerTest {

  @Test
  @Order(1)
  void saveShallSavePodIfDoesNotExist() {
    final Set<ContainerStateDto> states = KubeBuilder.buildContainerStateDto();
    final Optional<ContainerStateDto> first = states.stream().findFirst();
    assertThat(first.isPresent(), equalTo(true));
    ResponseEntity<Void> response = containerStateController.save(CONTAINER.getId(), first.get());
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }

  @Test
  @Order(2)
  void shallNotSaveContainerIfOneWithTheSameNameExistsForThePod() {
    ResponseEntity<Void> response = containerStateController.save(CONTAINER.getId(), KubeBuilder.buildContainerStateDto(CONTAINER_STATE));
    assertThat(response.getStatusCode().value(), equalTo(208));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }

  @Test
  @Order(3)
  void shallSaveContainerIfOneWithTheSameNameExistsForTheDifferentPod() {
    ResponseEntity<Void> response = containerStateController.save(CONTAINER2.getId(), KubeBuilder.buildContainerStateDto(CONTAINER_STATE));
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }

  @Test
  @Order(2)
  void shallReturnCorrectValueWhenValidIdProvided() {
    ResponseEntity<ContainerStateDto> response = containerStateController.getContainerStateById(CONTAINER_STATE.getId());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getId(), equalTo(CONTAINER_STATE.getId()));
    assertThat(response.getBody().getMessage(), equalTo(CONTAINER_STATE.getMessage()));
    assertThat(response.getBody().getValue(), equalTo(CONTAINER_STATE.getValue()));
    assertThat(response.getBody().getType(), equalTo(CONTAINER_STATE.getType()));
    assertThat(response.getBody().getSyncTime().truncatedTo(ChronoUnit.MILLIS), comparesEqualTo(CONTAINER_STATE.getSyncTime().truncatedTo(ChronoUnit.MILLIS)));
  }

  @Test
  @Order(2)
  void getContainerStatesShallReturnCorrectSetWhenValidContainerIdProvided() {
    ResponseEntity<Set<ContainerStateDto>> response = containerStateController.getContainerStates(CONTAINER.getId());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().size(), notNullValue());
  }

}