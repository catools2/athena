package org.catools.athena.tms.controller;

import feign.TypedResponse;
import org.catools.athena.common.feign.FeignUtils;
import org.catools.athena.model.tms.ItemTypeDto;
import org.catools.athena.tms.builder.TmsBuilder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ItemTypeControllerIT extends BaseTmsControllerIT {

  @Test
  @Order(1)
  void shallSaveRecordIfTheRecordDoesNotExists() {
    final ItemTypeDto itemTypeDto = TmsBuilder.buildItemTypeDto();
    final TypedResponse<Void> response = itemTypeFeignClient.saveOrUpdate(itemTypeDto);
    assertThat(response.status(), equalTo(201));
    Long entityId = FeignUtils.getIdFromLocationHeader(response);
    assertThat(entityId, notNullValue());
  }

  @Test
  @Order(3)
  void shallUpdateRecordIfTheRecordAlreadyExists() {
    ItemTypeDto itemTypeDto = tmsMapper.itemTypeToItemTypeDto(itemType);
    final TypedResponse<Void> response = itemTypeFeignClient.saveOrUpdate(itemTypeDto);
    assertThat(response.status(), equalTo(201));
    Long entityId = FeignUtils.getIdFromLocationHeader(response);
    assertThat(entityId, notNullValue());
  }

  @Test
  @Order(3)
  void shallReturnCorrectValueWhenValidIdProvided() {
    final TypedResponse<ItemTypeDto> response = itemTypeFeignClient.getById(itemType.getId());
    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getName(), notNullValue());
  }

  @Test
  @Order(4)
  void shallReturnCorrectValueWhenValidCodeProvided() {
    final TypedResponse<ItemTypeDto> response = itemTypeFeignClient.search(itemType.getCode());
    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getName(), notNullValue());
  }
}