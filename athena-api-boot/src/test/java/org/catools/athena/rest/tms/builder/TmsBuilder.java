package org.catools.athena.rest.tms.builder;

import lombok.experimental.UtilityClass;
import org.apache.commons.compress.utils.Sets;
import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.rest.core.entity.Project;
import org.catools.athena.rest.core.entity.User;
import org.catools.athena.rest.core.entity.Version;
import org.catools.athena.rest.tms.entity.*;
import org.catools.athena.tms.model.*;
import org.instancio.Instancio;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.instancio.Select.field;

@UtilityClass
public class TmsBuilder {

  public static TestCycle buildTestCycle(final Version version) {
    return Instancio.of(TestCycle.class)
        .ignore(field(TestCycle::getId))
        .generate(field(TestCycle::getCode), gen -> gen.string().length(1, 10))
        .set(field(TestCycle::getVersion), version)
        .create();
  }

  public static TestCycleDto buildTestCycleDto(final TestCycle cycle) {
    return new TestCycleDto()
        .setId(cycle.getId())
        .setCode(cycle.getCode())
        .setName(cycle.getName())
        .setStartInstant(cycle.getStartInstant())
        .setEndInstant(cycle.getEndInstant())
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
        .setCycle(testExecution.getCycle().getCode())
        .setItem(testExecution.getItem().getCode())
        .setStatus(testExecution.getStatus().getCode())
        .setExecutor(testExecution.getExecutor().getName());
  }

  public static Item buildItem(Project project, Priority priority, ItemType itemType, Status status, User user, Set<Version> versions) {
    return Instancio.of(Item.class)
        .ignore(field(Item::getId))
        .generate(field(Item::getCode), gen -> gen.string().length(1, 10))
        .set(field(Item::getType), itemType)
        .set(field(Item::getStatus), status)
        .set(field(Item::getPriority), priority)
        .set(field(Item::getProject), project)
        .set(field(Item::getVersions), versions)
        .set(field(Item::getCreatedBy), user)
        .set(field(Item::getUpdatedBy), user)
        .set(field(Item::getMetadata), Sets.newHashSet(buildItemMetadata(), buildItemMetadata()))
        .create();
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
        .setCreatedBy(item.getCreatedBy().getName())
        .setUpdatedBy(item.getUpdatedBy().getName())
        .setVersions(item.getVersions().stream().map(Version::getCode).collect(Collectors.toSet()))
        .setMetadata(item.getMetadata().stream().map(m -> new MetadataDto().setName(m.getName()).setValue(m.getValue())).collect(Collectors.toSet()));
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
        .limit(3)
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

  public static StatusTransition buildStatusTransition(List<Status> statuses, Item item) {
    return Instancio.of(StatusTransition.class)
        .ignore(field(StatusTransition::getId))
        .set(field(StatusTransition::getItem), item)
        .set(field(StatusTransition::getFrom), statuses.get(RandomUtils.nextInt(0, statuses.size())))
        .set(field(StatusTransition::getTo), statuses.get(RandomUtils.nextInt(0, statuses.size())))
        .create();
  }

  public static StatusTransitionDto buildStatusTransitionDto(StatusTransition statusTransition) {
    return new StatusTransitionDto()
        .setOccurred(statusTransition.getOccurred())
        .setFrom(statusTransition.getFrom().getCode())
        .setTo(statusTransition.getTo().getCode());
  }

}