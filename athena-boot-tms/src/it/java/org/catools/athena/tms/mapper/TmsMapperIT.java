package org.catools.athena.tms.mapper;

import org.apache.logging.log4j.util.Strings;
import org.catools.athena.AthenaSpringBootIT;
import org.catools.athena.configs.StagedTestData;
import org.catools.athena.model.core.MetadataDto;
import org.catools.athena.model.core.ProjectDto;
import org.catools.athena.model.core.UserDto;
import org.catools.athena.model.core.VersionDto;
import org.catools.athena.model.tms.ItemDto;
import org.catools.athena.model.tms.ItemTypeDto;
import org.catools.athena.model.tms.PriorityDto;
import org.catools.athena.model.tms.StatusDto;
import org.catools.athena.model.tms.StatusTransitionDto;
import org.catools.athena.model.tms.TestCycleDto;
import org.catools.athena.model.tms.TestExecutionDto;
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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TmsMapperIT extends AthenaSpringBootIT {

  private static ProjectDto project;
  private static UserDto user;
  private static VersionDto appVersion;
  private static final List<Status> STATUSES = new ArrayList<>();
  private static Priority priority;
  private static ItemType type;

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
  void beforeAll() {
    if (user == null) {
      user = StagedTestData.getRandomUser();
    }

    if (project == null) {
      project = StagedTestData.getProject(1);
    }

    if (appVersion == null) {
      appVersion = StagedTestData.getVersion(1);
    }

    if (STATUSES.isEmpty()) {
      List<StatusDto> statusDtos = TmsBuilder.buildStatusDto();
      for (StatusDto statusDto : statusDtos) {
        statusDto.setId(statusService.saveOrUpdate(statusDto).getId());
        STATUSES.add(TmsBuilder.buildStatus(statusDto));
      }
    }

    if (priority == null) {
      final PriorityDto priorityDto = TmsBuilder.buildPriorityDto();
      priorityDto.setId(priorityService.saveOrUpdate(priorityDto).getId());
      priority = TmsBuilder.buildPriority(priorityDto);
    }

    if (type == null) {
      final ItemTypeDto itemTypeDto = TmsBuilder.buildItemTypeDto();
      itemTypeDto.setId(itemTypeService.saveOrUpdate(itemTypeDto).getId());
      type = TmsBuilder.buildItemType(itemTypeDto);
    }

  }

  @Test
  void testItemDtoToItem() {
    final Item item = TmsBuilder.buildItem(project, priority, type, STATUSES, user, Set.of(appVersion));
    final ItemDto itemDto = tmsMapper.itemToItemDto(item);
    verifyItemsMatch(itemDto, item);
  }

  @Test
  void testItemDtoToItem_shallReturnNullIfTheInputIsNull() {
    assertThat(tmsMapper.itemToItemDto(null), nullValue());
  }

  @Test
  void testItemToItemDto() {
    final ItemDto itemDto = itemService.saveOrUpdate(TmsBuilder.buildItemDto(TmsBuilder.buildItem(project, priority, type, STATUSES, user, Set.of(appVersion))));
    final Item item = tmsMapper.itemDtoToItem(itemDto);
    verifyItemsMatch(itemDto, item);
  }

  @Test
  void testItemToItemDto_shallReturnNullIfTheInputIsNull() {
    assertThat(tmsMapper.itemDtoToItem(null), nullValue());
  }

  @Test
  void testCycleDtoToTestCycle() {
    final ItemDto itemDto = itemService.saveOrUpdate(TmsBuilder.buildItemDto(TmsBuilder.buildItem(project, priority, type, STATUSES, user, Set.of(appVersion))));
    final Item item = tmsMapper.itemDtoToItem(itemDto);
    final TestCycleDto testCycleDto = TmsBuilder.buildTestCycleDto(TmsBuilder.buildTestCycle(appVersion, item, STATUSES.get(0), user));
    final TestCycle testCycle = tmsMapper.testCycleDtoToTestCycle(testCycleDto);

    verifyTestCycleMatches(testCycle, testCycleDto);
  }

  @Test
  void testCycleDtoToTestCycle_shallReturnNullIfTheInputIsNull() {
    assertThat(tmsMapper.testCycleDtoToTestCycle(null), nullValue());
  }

  @Test
  void testCycleToTestCycle() {
    final ItemDto itemDto = itemService.saveOrUpdate(TmsBuilder.buildItemDto(TmsBuilder.buildItem(project, priority, type, STATUSES, user, Set.of(appVersion))));
    final Item item = tmsMapper.itemDtoToItem(itemDto);
    final TestCycle testCycle = TmsBuilder.buildTestCycle(appVersion, item, STATUSES.get(0), user);
    final TestCycleDto testCycleDto = tmsMapper.testCycleToTestCycleDto(testCycle);

    verifyTestCycleMatches(testCycle, testCycleDto);
  }

  @Test
  void testCycleToTestCycle_shallReturnNullIfTheInputIsNull() {
    assertThat(tmsMapper.testCycleToTestCycleDto(null), nullValue());
  }

  @Test
  void testExecutionDtoToTestExecution() {
    final ItemDto itemDto = itemService.saveOrUpdate(TmsBuilder.buildItemDto(TmsBuilder.buildItem(project, priority, type, STATUSES, user, Set.of(appVersion))));
    final Item item = tmsMapper.itemDtoToItem(itemDto);
    final TestCycle testCycle = TmsBuilder.buildTestCycle(appVersion, item, STATUSES.get(0), user);
    final TestCycleDto testCycleDto = TmsBuilder.buildTestCycleDto(testCycle);
    testCycle.setId(testCycleService.saveOrUpdate(testCycleDto).getId());

    final TestExecution testExecution = TmsBuilder.buildTestExecution(testCycle, item, STATUSES.get(0), user);
    final TestExecutionDto testExecutionDto = tmsMapper.testExecutionToTestExecutionDto(testExecution);
    verifyTestExecutionMatch(testExecutionDto, testExecution);
  }

  @Test
  void testExecutionDtoToTestExecution_shallReturnNullIfTheInputIsNull() {
    assertThat(tmsMapper.testExecutionToTestExecutionDto(null), nullValue());
  }

  @Test
  void testExecutionToTestExecutionDto() {
    final ItemDto itemDto = itemService.saveOrUpdate(TmsBuilder.buildItemDto(TmsBuilder.buildItem(project, priority, type, STATUSES, user, Set.of(appVersion))));
    final Item item = tmsMapper.itemDtoToItem(itemDto);
    final TestCycle testCycle = TmsBuilder.buildTestCycle(appVersion, item, STATUSES.get(0), user);
    final TestCycleDto testCycleDto = TmsBuilder.buildTestCycleDto(testCycle);
    testCycle.setId(testCycleService.saveOrUpdate(testCycleDto).getId());


    final TestExecutionDto testExecutionDto = TmsBuilder.buildTestExecutionDto(TmsBuilder.buildTestExecution(testCycle, item, STATUSES.get(0), user));
    final TestExecution testExecution = tmsMapper.testExecutionDtoToTestExecution(testCycle, testExecutionDto);
    verifyTestExecutionMatch(testExecutionDto, testExecution);
  }

  @Test
  void testExecutionToTestExecutionDto_shallReturnNullIfTheInputIsNull() {
    assertThat(tmsMapper.testExecutionDtoToTestExecution(null, null), nullValue());
  }

  @Test
  void statusTransitionDtoToStatusTransition() {
    final ItemDto itemDto = itemService.saveOrUpdate(TmsBuilder.buildItemDto(TmsBuilder.buildItem(project, priority, type, STATUSES, user, Set.of(appVersion))));
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
    final Item item = TmsBuilder.buildItem(project, priority, type, STATUSES, user, Set.of(appVersion));
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
    assertThat(StagedTestData.getUser(t2.getExecutorId()).getUsername(), equalTo(t1.getExecutor()));
    assertThat(t2.getStatus().getCode(), equalTo(t1.getStatus()));
  }

  private static void verifyTestCycleMatches(TestCycle t1, TestCycleDto t2) {
    assertThat(t1, notNullValue());
    assertThat(t2, notNullValue());
    assertThat(t2.getCode(), equalTo(t1.getCode()));
    assertThat(t2.getName(), equalTo(t1.getName()));
    assertThat(t2.getStartDate(), equalTo(t1.getStartDate()));
    assertThat(t2.getEndDate(), equalTo(t1.getEndDate()));
    assertThat(t2.getVersion(), equalTo(StagedTestData.getVersion(t1.getVersionId()).getCode()));
  }

  private static void verifyItemsMatch(ItemDto t1, Item t2) {
    assertThat(t1, notNullValue());
    assertThat(t2, notNullValue());

    assertThat(t1.getCode(), equalTo(t2.getCode()));
    assertThat(t1.getName(), equalTo(t2.getName()));
    assertThat(t1.getCreatedOn(), equalTo(t2.getCreatedOn()));
    assertThat(t1.getUpdatedOn(), equalTo(t2.getUpdatedOn()));

    assertThat(t1.getCreatedBy(), equalTo(StagedTestData.getUser(t2.getCreatedBy()).getUsername()));
    assertThat(t1.getUpdatedBy(), equalTo(StagedTestData.getUser(t2.getUpdatedBy()).getUsername()));

    assertThat(t1.getType(), equalTo(t2.getType().getCode()));
    assertThat(t1.getStatus(), equalTo(t2.getStatus().getCode()));
    assertThat(t1.getPriority(), equalTo(t2.getPriority().getCode()));
    assertThat(t1.getProject(), equalTo(StagedTestData.getProject(t2.getProjectId()).getCode()));

    Set<String> actualVersions = t1.getVersions();
    assertThat(actualVersions, notNullValue());
    assertThat(actualVersions.size(), equalTo(1));
    assertThat(actualVersions.stream().findFirst().orElse(null), equalTo(t2.getVersionIds().stream().findFirst().map(id -> StagedTestData.getVersion(id).getCode()).orElse(Strings.EMPTY)));

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