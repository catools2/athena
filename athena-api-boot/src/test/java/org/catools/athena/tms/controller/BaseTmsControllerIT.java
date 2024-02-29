package org.catools.athena.tms.controller;

import org.catools.athena.core.controller.CoreControllerIT;
import org.catools.athena.tms.builder.TmsBuilder;
import org.catools.athena.tms.common.entity.ItemType;
import org.catools.athena.tms.common.entity.Priority;
import org.catools.athena.tms.common.entity.Status;
import org.catools.athena.tms.common.mapper.TmsMapper;
import org.catools.athena.tms.common.repository.PriorityRepository;
import org.catools.athena.tms.common.repository.StatusRepository;
import org.catools.athena.tms.common.service.ItemTypeService;
import org.catools.athena.tms.model.ItemTypeDto;
import org.catools.athena.tms.model.PriorityDto;
import org.catools.athena.tms.model.StatusDto;
import org.catools.athena.tms.rest.controller.ItemController;
import org.catools.athena.tms.rest.controller.SyncInfoController;
import org.catools.athena.tms.rest.controller.TestCycleController;
import org.catools.athena.tms.rest.controller.TestExecutionController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BaseTmsControllerIT extends CoreControllerIT {

  final List<Status> STATUSES = new ArrayList<>();
  Priority PRIORITY;
  ItemType ITEM_TYPE;

  @Autowired
  TmsMapper tmsMapper;

  @Autowired
  ItemTypeService itemTypeService;

  @Autowired
  PriorityRepository priorityRepository;

  @Autowired
  StatusRepository statusRepository;

  @Autowired
  ItemController itemController;

  @Autowired
  TestCycleController testCycleController;

  @Autowired
  TestExecutionController testExecutionController;

  @Autowired
  SyncInfoController syncInfoController;

  @BeforeAll
  public void beforeAll() {
    if (STATUSES.isEmpty()) {
      List<StatusDto> statusDtos = TmsBuilder.buildStatusDto();
      for (StatusDto statusDto : statusDtos) {
        STATUSES.add(statusRepository.findByCode(statusDto.getCode()).orElseGet(() -> statusRepository.saveAndFlush(tmsMapper.statusDtoToStatus(statusDto))));
      }
    }

    if (PRIORITY == null) {
      final PriorityDto priorityDto = TmsBuilder.buildPriorityDto();
      PRIORITY = priorityRepository.findByCode(priorityDto.getCode()).orElseGet(() -> priorityRepository.saveAndFlush(tmsMapper.priorityDtoToPriority(priorityDto)));
    }

    if (ITEM_TYPE == null) {
      final ItemTypeDto itemTypeDto = TmsBuilder.buildItemTypeDto();
      itemTypeDto.setId(itemTypeService.saveOrUpdate(itemTypeDto).getId());
      ITEM_TYPE = TmsBuilder.buildItemType(itemTypeDto);
    }
  }
}