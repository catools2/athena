package org.catools.athena.tms.controller;

import org.catools.athena.core.controller.CoreControllerTest;
import org.catools.athena.tms.builder.TmsBuilder;
import org.catools.athena.tms.common.entity.ItemType;
import org.catools.athena.tms.common.entity.Priority;
import org.catools.athena.tms.common.entity.Status;
import org.catools.athena.tms.common.mapper.TmsMapper;
import org.catools.athena.tms.common.service.ItemTypeService;
import org.catools.athena.tms.common.service.PriorityService;
import org.catools.athena.tms.common.service.StatusService;
import org.catools.athena.tms.model.ItemTypeDto;
import org.catools.athena.tms.model.PriorityDto;
import org.catools.athena.tms.model.StatusDto;
import org.catools.athena.tms.rest.controller.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BaseTmsControllerTest extends CoreControllerTest {

  static final List<Status> STATUSES = new ArrayList<>();
  static Priority PRIORITY;
  static ItemType ITEM_TYPE;

  @Autowired
  TmsMapper tmsMapper;

  @Autowired
  ItemTypeService itemTypeService;

  @Autowired
  PriorityService priorityService;

  @Autowired
  StatusService statusService;

  @Autowired
  ItemController itemController;

  @Autowired
  ItemTypeController itemTypeController;

  @Autowired
  StatusTransitionController statusTransitionController;

  @Autowired
  TestCycleController testCycleController;

  @Autowired
  TestExecutionController testExecutionController;

  @BeforeAll
  public void beforeAll() {
    if (STATUSES.isEmpty()) {
      List<StatusDto> statusDtos = TmsBuilder.buildStatusDto();
      for (StatusDto statusDto : statusDtos) {
        statusDto.setId(statusService.save(statusDto).getId());
        STATUSES.add(TmsBuilder.buildStatus(statusDto));
      }
    }

    if (PRIORITY == null) {
      final PriorityDto priorityDto = TmsBuilder.buildPriorityDto();
      priorityDto.setId(priorityService.save(priorityDto).getId());
      PRIORITY = TmsBuilder.buildPriority(priorityDto);
    }

    if (ITEM_TYPE == null) {
      final ItemTypeDto itemTypeDto = TmsBuilder.buildItemTypeDto();
      itemTypeDto.setId(itemTypeService.save(itemTypeDto).getId());
      ITEM_TYPE = TmsBuilder.buildItemType(itemTypeDto);
    }
  }
}