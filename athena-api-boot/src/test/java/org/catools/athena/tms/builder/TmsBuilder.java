package org.catools.athena.tms.builder;

import lombok.experimental.UtilityClass;
import org.catools.athena.core.common.entity.AppVersion;
import org.catools.athena.core.common.entity.Project;
import org.catools.athena.core.common.entity.User;
import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.tms.common.entity.*;
import org.catools.athena.tms.model.*;
import org.instancio.Instancio;
import org.instancio.InstancioApi;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.instancio.Select.field;

@UtilityClass
public class TmsBuilder {

  public static TestCycle buildTestCycle(final AppVersion appVersion, final Item item, final Status status, final User executor) {
    TestCycle testCycle = Instancio.of(TestCycle.class)
        .ignore(field(TestCycle::getId))
        .ignore(field(TestExecution::getId))
        .generate(field(TestCycle::getCode), gen -> gen.string().length(1, 10))
        .set(field(TestCycle::getVersion), appVersion)
        .create();

    testCycle.getTestExecutions().forEach(e -> {
      e.setCycle(testCycle);
      e.setItem(item);
      e.setStatus(status);
      e.setExecutor(executor);
    });

    return testCycle;
  }

  public static TestCycleDto buildTestCycleDto(final TestCycle cycle) {
    return new TestCycleDto()
        .setId(cycle.getId())
        .setCode(cycle.getCode())
        .setName(cycle.getName())
        .setStartDate(cycle.getStartDate())
        .setEndDate(cycle.getEndDate())
        .setVersion(cycle.getVersion().getCode());
  }

  public static TestExecution buildTestExecution(TestCycle cycle, Item item, Status status, User user) {
    return Instancio.of(TestExecution.class)
        .ignore(field(TestExecution::getId))
        .set(field(TestExecution::getCycle), cycle)
        .set(field(TestExecution::getStatus), status)
        .set(field(TestExecution::getItem), item)
        .set(field(TestExecution::getExecutor), user)
        .create();
  }

  public static TestExecutionDto buildTestExecutionDto(TestExecution testExecution) {
    return new TestExecutionDto()
        .setId(testExecution.getId())
        .setCreatedOn(testExecution.getCreatedOn())
        .setExecutedOn(testExecution.getExecutedOn())
        .setItem(testExecution.getItem().getCode())
        .setStatus(testExecution.getStatus().getCode())
        .setExecutor(testExecution.getExecutor().getUsername());
  }

  public static Item buildItem(Project project, Priority priority, ItemType itemType, List<Status> statuses, User user, Set<AppVersion> appVersions) {
    Item item = Instancio.of(Item.class)
        .ignore(field(Item::getId))
        .ignore(field(Status::getId))
        .ignore(field(StatusTransition::getId))
        .generate(field(Item::getCode), gen -> gen.string().length(1, 10))
        .set(field(Item::getType), itemType)
        .set(field(Item::getStatus), statuses.get(0))
        .set(field(Item::getPriority), priority)
        .set(field(Item::getProject), project)
        .set(field(Item::getVersions), appVersions)
        .set(field(Item::getCreatedBy), user)
        .set(field(Item::getUpdatedBy), user)
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
        .setProject(item.getProject().getCode())
        .setType(item.getType().getCode())
        .setStatus(item.getStatus().getCode())
        .setPriority(item.getPriority().getCode())
        .setCreatedBy(item.getCreatedBy().getUsername())
        .setUpdatedBy(item.getUpdatedBy().getUsername())
        .setVersions(item.getVersions().stream().map(AppVersion::getCode).collect(Collectors.toSet()))
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
        .collect(Collectors.toList());
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

  public static StatusTransition buildStatusTransition(List<Status> statuses, Item item, User user) {
    return getStatusTransitionInstancioApi(statuses, item, user)
        .create();
  }

  public static Set<StatusTransition> buildStatusTransitions(List<Status> statuses, Item item, User user) {
    return getStatusTransitionInstancioApi(statuses, item, user)
        .stream()
        .limit(3)
        .collect(Collectors.toSet());
  }

  private static InstancioApi<StatusTransition> getStatusTransitionInstancioApi(List<Status> statuses, Item item, User user) {
    return Instancio.of(StatusTransition.class)
        .ignore(field(StatusTransition::getId))
        .set(field(StatusTransition::getItem), item)
        .set(field(StatusTransition::getAuthor), user)
        .generate(field(StatusTransition::getFrom), gen -> gen.oneOf(statuses))
        .generate(field(StatusTransition::getTo), gen -> gen.oneOf(statuses));
  }

  public static StatusTransitionDto buildStatusTransitionDto(StatusTransition statusTransition) {
    return new StatusTransitionDto(
        statusTransition.getFrom().getCode(),
        statusTransition.getTo().getCode(),
        statusTransition.getAuthor().getUsername(),
        statusTransition.getOccurred());
  }

}