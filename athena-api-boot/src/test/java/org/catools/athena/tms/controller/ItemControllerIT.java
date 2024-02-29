package org.catools.athena.tms.controller;

import org.catools.athena.tms.builder.TmsBuilder;
import org.catools.athena.tms.common.entity.Item;
import org.catools.athena.tms.model.ItemDto;
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
class ItemControllerIT extends BaseTmsControllerIT {

  String ITEM_CODE = "ABCD1234";

  @Test
  @Order(1)
  void shallSaveRecordIfTheRecordDoesNotExists() {
    final Item item = TmsBuilder.buildItem(PROJECT, PRIORITY, ITEM_TYPE, STATUSES, USER, Set.of(VERSION));
    final ItemDto itemDto = tmsMapper.itemToItemDto(item);
    itemDto.setCode(ITEM_CODE);
    final ResponseEntity<Void> response = itemController.saveOrUpdate(itemDto);
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }

  @Test
  @Order(2)
  void shallUpdateRecordIfTheRecordAlreadyExists() {
    final Item item = TmsBuilder.buildItem(PROJECT, PRIORITY, ITEM_TYPE, STATUSES, USER, Set.of(VERSION));
    final ItemDto itemDto = tmsMapper.itemToItemDto(item);
    itemDto.setCode(ITEM_CODE);
    final ResponseEntity<Void> response = itemController.saveOrUpdate(itemDto);
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }

  @Test
  @Order(3)
  void shallReturnCorrectValueWhenValidIdProvided() {
    final ResponseEntity<ItemDto> response = itemController.getById(1L);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getName(), notNullValue());
  }

  @Test
  @Order(4)
  void shallReturnCorrectValueWhenValidCodeProvided() {
    final ResponseEntity<ItemDto> response = itemController.getByCode(ITEM_CODE);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getName(), notNullValue());
  }
}