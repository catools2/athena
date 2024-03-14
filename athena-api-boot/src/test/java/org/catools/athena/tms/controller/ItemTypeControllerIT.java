package org.catools.athena.tms.controller;

import org.catools.athena.tms.builder.TmsBuilder;
import org.catools.athena.tms.model.ItemTypeDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ItemTypeControllerIT extends BaseTmsControllerIT {

  @Test
  @Order(1)
  void shallSaveRecordIfTheRecordDoesNotExists() {
    final ItemTypeDto itemTypeDto = TmsBuilder.buildItemTypeDto();
    final ResponseEntity<Void> response = itemTypeController.saveOrUpdate(itemTypeDto);
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }

  @Test
  @Order(3)
  void shallUpdateRecordIfTheRecordAlreadyExists() {
    ItemTypeDto itemTypeDto = tmsMapper.itemTypeToItemTypeDto(ITEM_TYPE);
    final ResponseEntity<Void> response = itemTypeController.saveOrUpdate(itemTypeDto);
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }

  @Test
  @Order(3)
  void shallReturnCorrectValueWhenValidIdProvided() {
    final ResponseEntity<ItemTypeDto> response = itemTypeController.getById(1L);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getName(), notNullValue());
  }

  @Test
  @Order(4)
  void shallReturnCorrectValueWhenValidCodeProvided() {
    final ResponseEntity<ItemTypeDto> response = itemTypeController.search(ITEM_TYPE.getCode());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getName(), notNullValue());
  }
}