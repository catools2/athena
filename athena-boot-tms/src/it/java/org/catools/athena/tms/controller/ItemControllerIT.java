package org.catools.athena.tms.controller;

import feign.TypedResponse;
import org.catools.athena.feign.FeignUtils;
import org.catools.athena.tms.builder.TmsBuilder;
import org.catools.athena.tms.common.entity.Item;
import org.catools.athena.tms.model.ItemDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ItemControllerIT extends BaseTmsControllerIT {

  static final String ITEM_CODE = "ABCD1234";

  @Test
  @Order(1)
  void shallSaveRecordIfTheRecordDoesNotExists() {
    final Item item = TmsBuilder.buildItem(projectDto, priority, itemType, statuses, userDto, Set.of(versionDto));
    final ItemDto itemDto = tmsMapper.itemToItemDto(item);
    itemDto.setCode(ITEM_CODE);
    final TypedResponse<Void> response = itemFeignClient.saveOrUpdate(itemDto);
    assertThat(response.status(), equalTo(201));
    Long entityId = FeignUtils.getIdFromLocationHeader(response);
    assertThat(entityId, notNullValue());
  }

  @Test
  @Order(2)
  void shallUpdateRecordIfTheRecordAlreadyExists() {
    final Item item = TmsBuilder.buildItem(projectDto, priority, itemType, statuses, userDto, Set.of(versionDto));
    final ItemDto itemDto = tmsMapper.itemToItemDto(item);
    itemDto.setCode(ITEM_CODE);
    String newName = item.getName() + "A";
    itemDto.setName(newName);
    final TypedResponse<Void> response = itemFeignClient.saveOrUpdate(itemDto);
    assertThat(response.status(), equalTo(201));
    Long entityId = FeignUtils.getIdFromLocationHeader(response);
    assertThat(entityId, notNullValue());
    assertThat(itemFeignClient.search(ITEM_CODE).body().getName(), equalTo(newName));
  }

  @Test
  @Order(3)
  void shallReturnCorrectValueWhenValidIdProvided() {
    final TypedResponse<ItemDto> response = itemFeignClient.getById(1L);
    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getName(), notNullValue());
  }

  @Test
  @Order(4)
  void shallReturnCorrectValueWhenValidCodeProvided() {
    final TypedResponse<ItemDto> response = itemFeignClient.search(ITEM_CODE);
    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getName(), notNullValue());
  }
}