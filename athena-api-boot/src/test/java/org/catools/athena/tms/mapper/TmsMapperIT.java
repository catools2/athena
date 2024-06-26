package org.catools.athena.tms.mapper;

import org.catools.athena.AthenaBaseIT;
import org.catools.athena.core.common.entity.AppVersion;
import org.catools.athena.core.common.entity.Project;
import org.catools.athena.core.common.entity.User;
import org.catools.athena.core.configs.StagedTestData;
import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.tms.builder.TmsBuilder;
import org.catools.athena.tms.common.entity.Item;
import org.catools.athena.tms.common.entity.ItemMetadata;
import org.catools.athena.tms.common.entity.ItemType;
import org.catools.athena.tms.common.entity.Priority;
import org.catools.athena.tms.common.entity.Status;
import org.catools.athena.tms.common.entity.StatusTransition;
import org.catools.athena.tms.common.entity.TestCycle;
import org.catools.athena.tms.common.entity.TestExecution;
import org.catools.athena.tms.common.mapper.TmsMapper;
import org.catools.athena.tms.common.service.ItemService;
import org.catools.athena.tms.common.service.ItemTypeService;
import org.catools.athena.tms.common.service.PriorityService;
import org.catools.athena.tms.common.service.StatusService;
import org.catools.athena.tms.common.service.TestCycleService;
import org.catools.athena.tms.model.ItemDto;
import org.catools.athena.tms.model.ItemTypeDto;
import org.catools.athena.tms.model.PriorityDto;
import org.catools.athena.tms.model.StatusDto;
import org.catools.athena.tms.model.StatusTransitionDto;
import org.catools.athena.tms.model.TestCycleDto;
import org.catools.athena.tms.model.TestExecutionDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

class TmsMapperIT extends AthenaBaseIT {

  private static Project PROJECT;
  private static User USER;
  private static AppVersion AppVERSION;
  private final static List<Status> STATUSES = new ArrayList<>();
  private static Priority PRIORITY;
  private static ItemType TYPE;

  @Autowired
  TmsMapper tmsMapper;

  @Autowired
  ItemService itemService;

  @Autowired
  ItemTypeService itemTypeService;

  @Autowired
  PriorityService priorityService;

  @Autowired
  StatusService statusService;

  @Autowired
  TestCycleService testCycleService;

  @BeforeAll
  public void beforeAll() {
    if (USER == null) {
      USER = StagedTestData.getRandomUser();
    }

    if (PROJECT == null) {
      PROJECT = StagedTestData.getProject(1);
    }

    if (AppVERSION == null) {
      AppVERSION = StagedTestData.getVersion(1);
    }

    if (STATUSES.isEmpty()) {
      List<StatusDto> statusDtos = TmsBuilder.buildStatusDto();
      for (StatusDto statusDto : statusDtos) {
        statusDto.setId(statusService.saveOrUpdate(statusDto).getId());
        STATUSES.add(TmsBuilder.buildStatus(statusDto));
      }
    }

    if (PRIORITY == null) {
      final PriorityDto priorityDto = TmsBuilder.buildPriorityDto();
      priorityDto.setId(priorityService.saveOrUpdate(priorityDto).getId());
      PRIORITY = TmsBuilder.buildPriority(priorityDto);
    }

    if (TYPE == null) {
      final ItemTypeDto itemTypeDto = TmsBuilder.buildItemTypeDto();
      itemTypeDto.setId(itemTypeService.saveOrUpdate(itemTypeDto).getId());
      TYPE = TmsBuilder.buildItemType(itemTypeDto);
    }

  }

  @Test
  void testItemDtoToItem() {
    final Item item = TmsBuilder.buildItem(PROJECT, PRIORITY, TYPE, STATUSES, USER, Set.of(AppVERSION));
    final ItemDto itemDto = tmsMapper.itemToItemDto(item);
    verifyItemsMatch(itemDto, item);
  }

  @Test
  void testItemDtoToItem_shallReturnNullIfTheInputIsNull() {
    assertThat(tmsMapper.itemToItemDto(null), nullValue());
  }

  @Test
  void testItemToItemDto() {
    final ItemDto itemDto = itemService.saveOrUpdate(TmsBuilder.buildItemDto(TmsBuilder.buildItem(PROJECT, PRIORITY, TYPE, STATUSES, USER, Set.of(AppVERSION))));
    final Item item = tmsMapper.itemDtoToItem(itemDto);
    verifyItemsMatch(itemDto, item);
  }

  @Test
  void testItemToItemDto_shallReturnNullIfTheInputIsNull() {
    assertThat(tmsMapper.itemDtoToItem(null), nullValue());
  }

  @Test
  void testCycleDtoToTestCycle() {
    final ItemDto itemDto = itemService.saveOrUpdate(TmsBuilder.buildItemDto(TmsBuilder.buildItem(PROJECT, PRIORITY, TYPE, STATUSES, USER, Set.of(AppVERSION))));
    final Item item = tmsMapper.itemDtoToItem(itemDto);
    final TestCycleDto testCycleDto = TmsBuilder.buildTestCycleDto(TmsBuilder.buildTestCycle(AppVERSION, item, STATUSES.get(0), USER));
    final TestCycle testCycle = tmsMapper.testCycleDtoToTestCycle(testCycleDto);

    verifyTestCycleMatches(testCycle, testCycleDto);
  }

  @Test
  void testCycleDtoToTestCycle_shallReturnNullIfTheInputIsNull() {
    assertThat(tmsMapper.testCycleDtoToTestCycle(null), nullValue());
  }

  @Test
  void testCycleToTestCycle() {
    final ItemDto itemDto = itemService.saveOrUpdate(TmsBuilder.buildItemDto(TmsBuilder.buildItem(PROJECT, PRIORITY, TYPE, STATUSES, USER, Set.of(AppVERSION))));
    final Item item = tmsMapper.itemDtoToItem(itemDto);
    final TestCycle testCycle = TmsBuilder.buildTestCycle(AppVERSION, item, STATUSES.get(0), USER);
    final TestCycleDto testCycleDto = tmsMapper.testCycleToTestCycleDto(testCycle);

    verifyTestCycleMatches(testCycle, testCycleDto);
  }

  @Test
  void testCycleToTestCycle_shallReturnNullIfTheInputIsNull() {
    assertThat(tmsMapper.testCycleToTestCycleDto(null), nullValue());
  }

  @Test
  void testExecutionDtoToTestExecution() {
    final ItemDto itemDto = itemService.saveOrUpdate(TmsBuilder.buildItemDto(TmsBuilder.buildItem(PROJECT, PRIORITY, TYPE, STATUSES, USER, Set.of(AppVERSION))));
    final Item item = tmsMapper.itemDtoToItem(itemDto);
    final TestCycle testCycle = TmsBuilder.buildTestCycle(AppVERSION, item, STATUSES.get(0), USER);
    final TestCycleDto testCycleDto = TmsBuilder.buildTestCycleDto(testCycle);
    testCycle.setId(testCycleService.saveOrUpdate(testCycleDto).getId());

    final TestExecution testExecution = TmsBuilder.buildTestExecution(testCycle, item, STATUSES.get(0), USER);
    final TestExecutionDto testExecutionDto = tmsMapper.testExecutionToTestExecutionDto(testExecution);
    verifyTestExecutionMatch(testExecutionDto, testExecution);
  }

  @Test
  void testExecutionDtoToTestExecution_shallReturnNullIfTheInputIsNull() {
    assertThat(tmsMapper.testExecutionToTestExecutionDto(null), nullValue());
  }

  @Test
  void testExecutionToTestExecutionDto() {
    final ItemDto itemDto = itemService.saveOrUpdate(TmsBuilder.buildItemDto(TmsBuilder.buildItem(PROJECT, PRIORITY, TYPE, STATUSES, USER, Set.of(AppVERSION))));
    final Item item = tmsMapper.itemDtoToItem(itemDto);
    final TestCycle testCycle = TmsBuilder.buildTestCycle(AppVERSION, item, STATUSES.get(0), USER);
    final TestCycleDto testCycleDto = TmsBuilder.buildTestCycleDto(testCycle);
    testCycle.setId(testCycleService.saveOrUpdate(testCycleDto).getId());


    final TestExecutionDto testExecutionDto = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(testCycle, item, STATUSES.get(0), USER));
    final TestExecution testExecution = tmsMapper.testExecutionDtoToTestExecution(testExecutionDto);
    verifyTestExecutionMatch(testExecutionDto, testExecution);
  }

  @Test
  void testExecutionToTestExecutionDto_shallReturnNullIfTheInputIsNull() {
    assertThat(tmsMapper.testExecutionDtoToTestExecution(null), nullValue());
  }

  @Test
  void statusTransitionDtoToStatusTransition() {
    final ItemDto itemDto = itemService.saveOrUpdate(TmsBuilder.buildItemDto(TmsBuilder.buildItem(PROJECT, PRIORITY, TYPE, STATUSES, USER, Set.of(AppVERSION))));
    final Item item = tmsMapper.itemDtoToItem(itemDto);

    for (StatusTransition statusTransition : item.getStatusTransitions()) {
      final StatusTransitionDto statusTransitionDto = tmsMapper.statusTransitionToStatusTransitionDto(statusTransition);
      verifyStatusTransitionSetMatch(statusTransitionDto, statusTransition);
    }

  }

  @Test
  void statusTransitionDtoToStatusTransition_shallReturnNullIfTheInputIsNull() {
    assertThat(tmsMapper.statusTransitionToStatusTransitionDto(null), nullValue());
  }

  @Test
  void statusTransitionToStatusTransitionDto() {
    final Item item = TmsBuilder.buildItem(PROJECT, PRIORITY, TYPE, STATUSES, USER, Set.of(AppVERSION));
    itemService.saveOrUpdate(TmsBuilder.buildItemDto(item));

    for (StatusTransition statusTransition : item.getStatusTransitions()) {
      final StatusTransitionDto statusTransitionDto = tmsMapper.statusTransitionToStatusTransitionDto(statusTransition);
      verifyStatusTransitionSetMatch(statusTransitionDto, statusTransition);
    }
  }

  @Test
  void statusTransitionToStatusTransitionDto_shallReturnNullIfTheInputIsNull() {
    assertThat(tmsMapper.statusTransitionToStatusTransitionDto(null), nullValue());
  }

  private void verifyTestExecutionMatch(TestExecutionDto t1, TestExecution t2) {
    assertThat(t1, notNullValue());
    assertThat(t2, notNullValue());
    assertThat(t2.getId(), equalTo(t1.getId()));
    assertThat(t2.getCreatedOn(), equalTo(t1.getCreatedOn()));
    assertThat(t2.getExecutedOn(), equalTo(t1.getExecutedOn()));
    assertThat(t2.getItem().getCode(), equalTo(t1.getItem()));
    assertThat(t2.getExecutor().getUsername(), equalTo(t1.getExecutor()));
    assertThat(t2.getStatus().getCode(), equalTo(t1.getStatus()));
  }

  private static void verifyTestCycleMatches(TestCycle t1, TestCycleDto t2) {
    assertThat(t1, notNullValue());
    assertThat(t2, notNullValue());
    assertThat(t2.getCode(), equalTo(t1.getCode()));
    assertThat(t2.getName(), equalTo(t1.getName()));
    assertThat(t2.getStartDate(), equalTo(t1.getStartDate()));
    assertThat(t2.getEndDate(), equalTo(t1.getEndDate()));
    assertThat(t2.getVersion(), equalTo(t1.getVersion().getCode()));
  }

  private static void verifyItemsMatch(ItemDto t1, Item t2) {
    assertThat(t1, notNullValue());
    assertThat(t2, notNullValue());

    assertThat(t1.getCode(), equalTo(t2.getCode()));
    assertThat(t1.getName(), equalTo(t2.getName()));
    assertThat(t1.getCreatedOn(), equalTo(t2.getCreatedOn()));
    assertThat(t1.getUpdatedOn(), equalTo(t2.getUpdatedOn()));

    assertThat(t1.getCreatedBy(), equalTo(t2.getCreatedBy().getUsername()));
    assertThat(t1.getUpdatedBy(), equalTo(t2.getUpdatedBy().getUsername()));

    assertThat(t1.getType(), equalTo(t2.getType().getCode()));
    assertThat(t1.getStatus(), equalTo(t2.getStatus().getCode()));
    assertThat(t1.getPriority(), equalTo(t2.getPriority().getCode()));
    assertThat(t1.getProject(), equalTo(t2.getProject().getCode()));

    Set<String> actualVersions = t1.getVersions();
    assertThat(actualVersions, notNullValue());
    assertThat(actualVersions.size(), equalTo(1));
    assertThat(actualVersions.stream().findFirst().orElse(null), equalTo(t2.getVersions().stream().findFirst().map(AppVersion::getCode).orElse("")));

    verifyMetadataSetMatch(t1.getMetadata(), t2.getMetadata());
  }

  private static void verifyMetadataSetMatch(Set<MetadataDto> t1, Set<ItemMetadata> t2) {
    assertThat(t1, notNullValue());
    assertThat(t2, notNullValue());
    assertThat(t1.size(), equalTo(2));

    Optional<MetadataDto> firstActual = t1.stream().findFirst();
    assertThat(firstActual.isPresent(), equalTo(true));

    Optional<ItemMetadata> firstExpected = t2.stream().filter(m -> m.getName().equals(firstActual.get().getName())).findFirst();
    assertThat(firstExpected.isPresent(), equalTo(true));

    assertThat(firstActual.get().getName(), equalTo(firstExpected.get().getName()));
    assertThat(firstActual.get().getValue(), equalTo(firstExpected.get().getValue()));
  }

  private static void verifyStatusTransitionSetMatch(StatusTransitionDto st1, StatusTransition st2) {
    assertThat(st1, notNullValue());
    assertThat(st2, notNullValue());

    assertThat(st1.getOccurred(), equalTo(st2.getOccurred()));
    assertThat(st1.getTo(), equalTo(st2.getTo().getCode()));
    assertThat(st1.getFrom(), equalTo(st2.getFrom().getCode()));
  }
}