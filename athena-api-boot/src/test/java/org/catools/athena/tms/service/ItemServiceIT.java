package org.catools.athena.tms.service;

import org.catools.athena.common.exception.EntityNotFoundException;
import org.catools.athena.core.controller.CoreControllerIT;
import org.catools.athena.tms.builder.TmsBuilder;
import org.catools.athena.tms.common.entity.ItemType;
import org.catools.athena.tms.common.entity.Priority;
import org.catools.athena.tms.common.entity.Status;
import org.catools.athena.tms.common.service.ItemService;
import org.catools.athena.tms.model.ItemDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ItemServiceIT extends CoreControllerIT {

  @Autowired
  ItemService itemService;

  @Test
  @Order(1)
  void saveShallNotSaveItemIfItemTypeDoesNotExist() {
    final ItemDto item = TmsBuilder.buildItemDto(TmsBuilder.buildItem(PROJECT, new Priority(), new ItemType(), new Status(), USER, Set.of(VERSION)));
    item.setType("NOT_EXISTS");

    assertThrows(EntityNotFoundException.class, () -> itemService.saveOrUpdate(item));
  }
}