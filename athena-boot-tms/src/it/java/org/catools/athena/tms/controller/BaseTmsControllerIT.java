package org.catools.athena.tms.controller;

import org.catools.athena.AthenaSpringBootIT;
import org.catools.athena.configs.StagedTestData;
import org.catools.athena.model.core.ProjectDto;
import org.catools.athena.model.core.UserDto;
import org.catools.athena.model.core.VersionDto;
import org.catools.athena.model.tms.ItemTypeDto;
import org.catools.athena.model.tms.PriorityDto;
import org.catools.athena.model.tms.StatusDto;
import org.catools.athena.tms.builder.TmsBuilder;
import org.catools.athena.tms.common.entity.ItemType;
import org.catools.athena.tms.common.entity.Priority;
import org.catools.athena.tms.common.entity.Status;
import org.catools.athena.tms.common.mapper.TmsMapper;
import org.catools.athena.tms.common.service.ItemTypeService;
import org.catools.athena.tms.common.service.PriorityService;
import org.catools.athena.tms.common.service.StatusService;
import org.catools.athena.tms.feign.ItemFeignClient;
import org.catools.athena.tms.feign.ItemTypeFeignClient;
import org.catools.athena.tms.feign.StatusFeignClient;
import org.catools.athena.tms.feign.StatusTransitionFeignClient;
import org.catools.athena.tms.feign.TestCycleFeignClient;
import org.catools.athena.tms.feign.TestExecutionFeignClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
abstract class BaseTmsControllerIT extends AthenaSpringBootIT {
  final ProjectDto projectDto = StagedTestData.getProject(1);
  final UserDto userDto = StagedTestData.getUser(1);
  final VersionDto versionDto = StagedTestData.getVersion(1);

  final List<Status> statuses = new ArrayList<>();
  Priority priority;
  ItemType itemType;

  @Autowired
  TmsMapper tmsMapper;

  @Autowired
  ItemTypeService itemTypeService;

  @Autowired
  PriorityService priorityService;

  @Autowired
  StatusService statusService;

  StatusFeignClient statusFeignClient;
  ItemFeignClient itemFeignClient;
  ItemTypeFeignClient itemTypeFeignClient;
  StatusTransitionFeignClient statusTransitionFeignClient;
  TestCycleFeignClient testCycleFeignClient;
  TestExecutionFeignClient testExecutionFeignClient;

  @BeforeAll
  void beforeAll() {
    if (statusFeignClient == null) {
      statusFeignClient = testFeignBuilder.getClient(StatusFeignClient.class);
    }
    if (itemFeignClient == null) {
      itemFeignClient = testFeignBuilder.getClient(ItemFeignClient.class);
    }
    if (itemTypeFeignClient == null) {
      itemTypeFeignClient = testFeignBuilder.getClient(ItemTypeFeignClient.class);
    }
    if (statusTransitionFeignClient == null) {
      statusTransitionFeignClient = testFeignBuilder.getClient(StatusTransitionFeignClient.class);
    }
    if (testCycleFeignClient == null) {
      testCycleFeignClient = testFeignBuilder.getClient(TestCycleFeignClient.class);
    }
    if (testExecutionFeignClient == null) {
      testExecutionFeignClient = testFeignBuilder.getClient(TestExecutionFeignClient.class);
    }
    if (statuses.isEmpty()) {
      List<StatusDto> statusDtos = TmsBuilder.buildStatusDto();
      for (StatusDto statusDto : statusDtos) {
        statusDto.setId(statusService.saveOrUpdate(statusDto).getId());
        statuses.add(TmsBuilder.buildStatus(statusDto));
      }
    }

    if (priority == null) {
      final PriorityDto priorityDto = TmsBuilder.buildPriorityDto();
      priorityDto.setId(priorityService.saveOrUpdate(priorityDto).getId());
      priority = TmsBuilder.buildPriority(priorityDto);
    }

    if (itemType == null) {
      final ItemTypeDto itemTypeDto = TmsBuilder.buildItemTypeDto();
      itemTypeDto.setId(itemTypeService.saveOrUpdate(itemTypeDto).getId());
      itemType = TmsBuilder.buildItemType(itemTypeDto);
    }
  }
}