package org.catools.athena.rest.tms.controller;

import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.core.model.VersionDto;
import org.catools.athena.rest.core.builder.CoreBuilder;
import org.catools.athena.rest.core.controller.CoreControllerTest;
import org.catools.athena.rest.core.entity.Project;
import org.catools.athena.rest.core.entity.User;
import org.catools.athena.rest.core.entity.Version;
import org.catools.athena.rest.core.service.ProjectService;
import org.catools.athena.rest.core.service.UserService;
import org.catools.athena.rest.core.service.VersionService;
import org.catools.athena.rest.tms.builder.TmsBuilder;
import org.catools.athena.rest.tms.entity.ItemType;
import org.catools.athena.rest.tms.entity.Priority;
import org.catools.athena.rest.tms.entity.Status;
import org.catools.athena.rest.tms.mapper.TmsMapper;
import org.catools.athena.rest.tms.service.ItemTypeService;
import org.catools.athena.rest.tms.service.PriorityService;
import org.catools.athena.rest.tms.service.StatusService;
import org.catools.athena.tms.model.ItemTypeDto;
import org.catools.athena.tms.model.PriorityDto;
import org.catools.athena.tms.model.StatusDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BaseTmsControllerTest extends CoreControllerTest {

  static Project PROJECT;
  static User USER;
  static Version VERSION;
  static final List<Status> STATUSES = new ArrayList<>();
  static Priority PRIORITY;
  static ItemType ITEM_TYPE;

  @Autowired
  TmsMapper tmsMapper;

  @Autowired
  UserService userService;

  @Autowired
  ProjectService projectService;

  @Autowired
  VersionService versionService;

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
    if (USER == null) {
      final UserDto userDto = CoreBuilder.buildUserDto();
      userDto.setId(userService.save(userDto).getId());
      USER = CoreBuilder.buildUser(userDto);
    }

    final ProjectDto projectDto = CoreBuilder.buildProjectDto();
    if (PROJECT == null) {
      projectDto.setId(projectService.save(projectDto).getId());
      PROJECT = CoreBuilder.buildProject(projectDto);
    }

    if (VERSION == null) {
      final VersionDto versionDto = CoreBuilder.buildVersionDto(projectDto);
      versionDto.setId(versionService.save(versionDto).getId());
      VERSION = CoreBuilder.buildVersion(versionDto, PROJECT);
    }

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