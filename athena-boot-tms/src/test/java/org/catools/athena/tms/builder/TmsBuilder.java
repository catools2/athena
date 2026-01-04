package org.catools.athena.tms.builder;

import lombok.experimental.UtilityClass;
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
import org.catools.athena.tms.common.entity.Item;
import org.catools.athena.tms.common.entity.ItemMetadata;
import org.catools.athena.tms.common.entity.ItemType;
import org.catools.athena.tms.common.entity.Priority;
import org.catools.athena.tms.common.entity.Status;
import org.catools.athena.tms.common.entity.StatusTransition;
import org.catools.athena.tms.common.entity.TestCycle;
import org.catools.athena.tms.common.entity.TestExecution;
import org.instancio.Instancio;
import org.instancio.InstancioApi;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.instancio.Select.field;

@UtilityClass
public class TmsBuilder {

  public static TestCycle buildTestCycle(final VersionDto appVersion, final Item item, final Status status, final UserDto executor) {
    TestCycle testCycle = Instancio.of(TestCycle.class)
        .ignore(field(TestCycle::getId))
        .ignore(field(TestExecution::getId))
        .generate(field(TestCycle::getCode), gen -> gen.string().length(1, 10))
        .set(field(TestCycle::getVersionId), appVersion.getId())
        .create();

    testCycle.getTestExecutions().forEach(e -> {
      e.setCycle(testCycle);
      e.setItem(item);
      e.setStatus(status);
      e.setExecutorId(executor.getId());
    });

    return testCycle;
  }

  public static TestCycleDto buildTestCycleDto(final TestCycle cycle) {
    VersionDto version = StagedTestData.getVersion(cycle.getVersionId());
    return new TestCycleDto()
        .setId(cycle.getId())
        .setCode(cycle.getCode())
        .setName(cycle.getName())
        .setStartDate(cycle.getStartDate())
        .setEndDate(cycle.getEndDate())
        .setVersion(version.getCode())
        .setProject(version.getProject());
  }

  public static TestExecution buildTestExecution(TestCycle cycle, Item item, Status status, UserDto user) {
    return Instancio.of(TestExecution.class)
        .ignore(field(TestExecution::getId))
        .set(field(TestExecution::getCycle), cycle)
        .set(field(TestExecution::getStatus), status)
        .set(field(TestExecution::getItem), item)
        .set(field(TestExecution::getExecutorId), user.getId())
        .create();
  }

  public static TestExecutionDto buildTestExecutionDto(TestExecution testExecution) {
    return new TestExecutionDto()
        .setId(testExecution.getId())
        .setCreatedOn(testExecution.getCreatedOn())
        .setExecutedOn(testExecution.getExecutedOn())
        .setItem(testExecution.getItem().getCode())
        .setStatus(testExecution.getStatus().getCode())
        .setExecutor(StagedTestData.getUser(testExecution.getExecutorId()).getUsername());
  }

  public static Item buildItem(ProjectDto project, Priority priority, ItemType itemType, List<Status> statuses, UserDto user, Set<VersionDto> appVersions) {
    Item item = Instancio.of(Item.class)
        .ignore(field(Item::getId))
        .ignore(field(Status::getId))
        .ignore(field(StatusTransition::getId))
        .generate(field(Item::getCode), gen -> gen.string().length(1, 10))
        .set(field(Item::getType), itemType)
        .set(field(Item::getStatus), statuses.get(0))
        .set(field(Item::getPriority), priority)
        .set(field(Item::getProjectId), project.getId())
        .set(field(Item::getVersionIds), appVersions.stream().map(VersionDto::getId).collect(Collectors.toSet()))
        .set(field(Item::getCreatedBy), user.getId())
        .set(field(Item::getUpdatedBy), user.getId())
        .set(field(Item::getMetadata), Set.of(buildItemMetadata(), buildItemMetadata()))
        .create();
    item.setStatusTransitions(TmsBuilder.buildStatusTransitions(statuses.stream().toList(), item, user));
    return item;
  }

  public static ItemDto buildItemDto(Item item) {
    return new ItemDto()
        .setId(item.getId())
        .setCode(item.getCode())
        .setName(item.getName())
        .setCreatedOn(item.getCreatedOn())
        .setUpdatedOn(item.getUpdatedOn())
        .setProject(StagedTestData.getProject(item.getProjectId()).getCode())
        .setType(item.getType().getCode())
        .setStatus(item.getStatus().getCode())
        .setPriority(item.getPriority().getCode())
        .setCreatedBy(StagedTestData.getUser(item.getCreatedBy()).getUsername())
        .setUpdatedBy(StagedTestData.getUser(item.getUpdatedBy()).getUsername())
        .setVersions(item.getVersionIds().stream().map(id -> StagedTestData.getVersion(id).getCode()).collect(Collectors.toSet()))
        .setMetadata(item.getMetadata().stream().map(m -> new MetadataDto(m.getName(), m.getValue())).collect(Collectors.toSet()));
  }

  public static ItemTypeDto buildItemTypeDto() {
    return Instancio.of(ItemTypeDto.class)
        .generate(field(ItemTypeDto::getCode), gen -> gen.string().length(1, 10))
        .ignore(field(ItemTypeDto::getId))
        .create();
  }

  public static ItemType buildItemType(ItemTypeDto itemType) {
    return new ItemType()
        .setId(itemType.getId())
        .setCode(itemType.getCode())
        .setName(itemType.getName());
  }

  public static List<StatusDto> buildStatusDto() {
    return Instancio.of(StatusDto.class)
        .generate(field(StatusDto::getCode), gen -> gen.string().length(1, 10))
        .ignore(field(StatusDto::getId))
        .stream()
        .limit(20)
        .toList();
  }

  public static Status buildStatus(StatusDto status) {
    return new Status()
        .setId(status.getId())
        .setCode(status.getCode())
        .setName(status.getName());
  }

  public static PriorityDto buildPriorityDto() {
    return Instancio.of(PriorityDto.class)
        .ignore(field(PriorityDto::getId))
        .generate(field(PriorityDto::getCode), gen -> gen.string().length(1, 10))
        .create();
  }

  public static Priority buildPriority(PriorityDto priority) {
    return new Priority()
        .setId(priority.getId())
        .setCode(priority.getCode())
        .setName(priority.getName());
  }

  public static ItemMetadata buildItemMetadata() {
    return Instancio.of(ItemMetadata.class)
        .ignore(field(ItemMetadata::getId))
        .create();
  }

  public static StatusTransition buildStatusTransition(List<Status> statuses, Item item, UserDto user) {
    return getStatusTransitionInstancioApi(statuses, item, user)
        .create();
  }

  public static Set<StatusTransition> buildStatusTransitions(List<Status> statuses, Item item, UserDto user) {
    return getStatusTransitionInstancioApi(statuses, item, user)
        .stream()
        .limit(3)
        .collect(Collectors.toSet());
  }

  private static InstancioApi<StatusTransition> getStatusTransitionInstancioApi(List<Status> statuses, Item item, UserDto user) {
    return Instancio.of(StatusTransition.class)
        .ignore(field(StatusTransition::getId))
        .set(field(StatusTransition::getItem), item)
        .set(field(StatusTransition::getAuthorId), user.getId())
        .generate(field(StatusTransition::getFrom), gen -> gen.oneOf(statuses))
        .generate(field(StatusTransition::getTo), gen -> gen.oneOf(statuses));
  }

  public static StatusTransitionDto buildStatusTransitionDto(StatusTransition statusTransition) {
    return new StatusTransitionDto(
        statusTransition.getFrom().getCode(),
        statusTransition.getTo().getCode(),
        StagedTestData.getUser(statusTransition.getAuthorId()).getUsername(),
        statusTransition.getOccurred());
  }

}