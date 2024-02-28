package org.catools.athena.tms.controller;

import org.catools.athena.tms.builder.TmsBuilder;
import org.catools.athena.tms.common.entity.Item;
import org.catools.athena.tms.common.entity.StatusTransition;
import org.catools.athena.tms.model.ItemDto;
import org.catools.athena.tms.model.StatusTransitionDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.ResponseEntity;

import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StatusTransitionControllerIT extends BaseTmsControllerIT {

  @Test
  @Order(1)
  void shallSaveRecordIfTheRecordDoesNotExists() {
    final Item item = TmsBuilder.buildItem(PROJECT, PRIORITY, ITEM_TYPE, STATUSES, USER, Set.of(VERSION));
    final ItemDto itemDto = tmsMapper.itemToItemDto(item);
    itemController.saveOrUpdate(itemDto);

    final StatusTransition statusTransition = TmsBuilder.buildStatusTransition(STATUSES, item, USER);
    final StatusTransitionDto statusTransitionDto = TmsBuilder.buildStatusTransitionDto(statusTransition);

    final ResponseEntity<Void> response = statusTransitionController.save(itemDto.getCode(), statusTransitionDto);
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }

  @Test
  @Order(2)
  void shallNotSaveRecordIfTheRecordAlreadyExists() {
    final Item item = TmsBuilder.buildItem(PROJECT, PRIORITY, ITEM_TYPE, STATUSES, USER, Set.of(VERSION));
    final ItemDto itemDto = tmsMapper.itemToItemDto(item);
    itemController.saveOrUpdate(itemDto);

    final StatusTransition statusTransition = TmsBuilder.buildStatusTransition(STATUSES, item, USER);
    final StatusTransitionDto statusTransitionDto = TmsBuilder.buildStatusTransitionDto(statusTransition);
    statusTransitionController.save(itemDto.getCode(), statusTransitionDto);

    // Repeat The same save to simulate case when the entity already exists
    final ResponseEntity<Void> response = statusTransitionController.save(itemDto.getCode(), statusTransitionDto);
    assertThat(response.getStatusCode().value(), equalTo(208));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }

  @Test
  @Order(3)
  void shallReturnCorrectValueWhenValidIdProvided() {
    final ResponseEntity<StatusTransitionDto> response = statusTransitionController.getById(1L);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getId(), equalTo(1L));
  }

  @Test
  @Order(4)
  void shallReturnCorrectValueWhenValidCodeProvided() {
    final Item item = TmsBuilder.buildItem(PROJECT, PRIORITY, ITEM_TYPE, STATUSES, USER, Set.of(VERSION));
    final ItemDto itemDto = tmsMapper.itemToItemDto(item);
    itemController.saveOrUpdate(itemDto);

    final StatusTransitionDto st1 = TmsBuilder.buildStatusTransitionDto(TmsBuilder.buildStatusTransition(STATUSES, item, USER));
    statusTransitionController.save(itemDto.getCode(), st1);

    final StatusTransitionDto st2 = TmsBuilder.buildStatusTransitionDto(TmsBuilder.buildStatusTransition(STATUSES, item, USER));
    statusTransitionController.save(itemDto.getCode(), st2);

    final StatusTransitionDto st3 = TmsBuilder.buildStatusTransitionDto(TmsBuilder.buildStatusTransition(STATUSES, item, USER));
    statusTransitionController.save(itemDto.getCode(), st3);

    final ResponseEntity<Set<StatusTransitionDto>> response = statusTransitionController.getAllByItemCode(itemDto.getCode());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().size(), greaterThanOrEqualTo(3));
    assertThat(response.getBody().stream().anyMatch(compareStatusTransitions(st1)), equalTo(true));
    assertThat(response.getBody().stream().anyMatch(compareStatusTransitions(st2)), equalTo(true));
    assertThat(response.getBody().stream().anyMatch(compareStatusTransitions(st3)), equalTo(true));
  }

  private static Predicate<StatusTransitionDto> compareStatusTransitions(StatusTransitionDto st1) {
    return st -> st.getTo().equals(st1.getTo()) &&
        st.getFrom().equals(st1.getFrom()) &&
        st.getOccurred().truncatedTo(ChronoUnit.MILLIS).equals(st1.getOccurred().truncatedTo(ChronoUnit.MILLIS));
  }
}