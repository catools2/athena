package org.catools.athena.tms.service;

import com.google.common.collect.Sets;
import org.catools.athena.common.exception.EntityNotFoundException;
import org.catools.athena.core.controller.CoreControllerTest;
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

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ItemServiceTest extends CoreControllerTest {

  @Autowired
  ItemService itemService;

  @Test
  @Order(1)
  void saveShallNotSaveItemIfItemTypeDoesNotExist() {
    final ItemDto item = TmsBuilder.buildItemDto(TmsBuilder.buildItem(PROJECT, new Priority(), new ItemType(), new Status(), USER, Sets.newHashSet(VERSION)));
    item.setType("NOT_EXISTS");

    assertThrows(EntityNotFoundException.class, () -> itemService.save(item));
  }
}