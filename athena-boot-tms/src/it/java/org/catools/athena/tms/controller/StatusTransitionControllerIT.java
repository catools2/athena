package org.catools.athena.tms.controller;

import feign.TypedResponse;
import org.catools.athena.common.feign.FeignUtils;
import org.catools.athena.tms.builder.TmsBuilder;
import org.catools.athena.tms.common.entity.Item;
import org.catools.athena.tms.common.entity.StatusTransition;
import org.catools.athena.tms.model.ItemDto;
import org.catools.athena.tms.model.StatusTransitionDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StatusTransitionControllerIT extends BaseTmsControllerIT {

  @Test
  @Order(1)
  void shallSaveRecordIfTheRecordDoesNotExists() {
    final Item item = TmsBuilder.buildItem(projectDto, priority, itemType, statuses, userDto, Set.of(versionDto));
    final ItemDto itemDto = tmsMapper.itemToItemDto(item);
    itemFeignClient.saveOrUpdate(itemDto);

    final StatusTransition statusTransition = TmsBuilder.buildStatusTransition(statuses, item, userDto);
    final StatusTransitionDto statusTransitionDto = TmsBuilder.buildStatusTransitionDto(statusTransition);

    final TypedResponse<Void> response = statusTransitionFeignClient.save(itemDto.getCode(), statusTransitionDto);
    assertThat(response.status(), equalTo(201));
    Long entityId = FeignUtils.getIdFromLocationHeader(response);
    assertThat(entityId, notNullValue());
  }

  @Test
  @Order(2)
  void shallNotSaveRecordIfTheRecordAlreadyExists() {
    final Item item = TmsBuilder.buildItem(projectDto, priority, itemType, statuses, userDto, Set.of(versionDto));
    final ItemDto itemDto = tmsMapper.itemToItemDto(item);
    itemFeignClient.saveOrUpdate(itemDto);

    final StatusTransition statusTransition = TmsBuilder.buildStatusTransition(statuses, item, userDto);
    final StatusTransitionDto statusTransitionDto = TmsBuilder.buildStatusTransitionDto(statusTransition);
    statusTransitionFeignClient.save(itemDto.getCode(), statusTransitionDto);

    // Repeat The same save to simulate case when the entity already exists
    final TypedResponse<Void> response = statusTransitionFeignClient.save(itemDto.getCode(), statusTransitionDto);
    assertThat(response.status(), equalTo(208));
    Long entityId = FeignUtils.getIdFromLocationHeader(response);
    assertThat(entityId, notNullValue());
  }

  @Test
  @Order(3)
  void shallReturnCorrectValueWhenValidIdProvided() {
    final Item item = TmsBuilder.buildItem(projectDto, priority, itemType, statuses, userDto, Set.of(versionDto));
    final ItemDto itemDto = tmsMapper.itemToItemDto(item);
    itemFeignClient.saveOrUpdate(itemDto);

    final StatusTransitionDto st1 = TmsBuilder.buildStatusTransitionDto(TmsBuilder.buildStatusTransition(statuses, item, userDto));
    statusTransitionFeignClient.save(itemDto.getCode(), st1);

    final TypedResponse<Void> saveResult = statusTransitionFeignClient.save(itemDto.getCode(), st1);
    Long entityId = FeignUtils.getIdFromLocationHeader(saveResult);

    final TypedResponse<StatusTransitionDto> response = statusTransitionFeignClient.getById(entityId);
    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getId(), equalTo(entityId));
    assertThat(compareStatusTransitions(st1).test(response.body()), equalTo(true));
  }

  @Test
  @Order(4)
  void shallReturnCorrectValueWhenValidCodeProvided() {
    final Item item = TmsBuilder.buildItem(projectDto, priority, itemType, statuses, userDto, Set.of(versionDto));
    final ItemDto itemDto = tmsMapper.itemToItemDto(item);
    itemFeignClient.saveOrUpdate(itemDto);

    final StatusTransitionDto st1 = TmsBuilder.buildStatusTransitionDto(TmsBuilder.buildStatusTransition(statuses, item, userDto));
    statusTransitionFeignClient.save(itemDto.getCode(), st1);

    final StatusTransitionDto st2 = TmsBuilder.buildStatusTransitionDto(TmsBuilder.buildStatusTransition(statuses, item, userDto));
    statusTransitionFeignClient.save(itemDto.getCode(), st2);

    final StatusTransitionDto st3 = TmsBuilder.buildStatusTransitionDto(TmsBuilder.buildStatusTransition(statuses, item, userDto));
    statusTransitionFeignClient.save(itemDto.getCode(), st3);

    final TypedResponse<Set<StatusTransitionDto>> response = statusTransitionFeignClient.getAllByItemCode(itemDto.getCode());
    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().size(), greaterThanOrEqualTo(3));
    assertThat(response.body().stream().anyMatch(compareStatusTransitions(st1)), equalTo(true));
    assertThat(response.body().stream().anyMatch(compareStatusTransitions(st2)), equalTo(true));
    assertThat(response.body().stream().anyMatch(compareStatusTransitions(st3)), equalTo(true));
  }

  private static Predicate<StatusTransitionDto> compareStatusTransitions(StatusTransitionDto st1) {
    return st -> st.getTo().equals(st1.getTo()) &&
        st.getFrom().equals(st1.getFrom()) &&
        st.getOccurred().truncatedTo(ChronoUnit.MILLIS).equals(st1.getOccurred().truncatedTo(ChronoUnit.MILLIS));
  }
}