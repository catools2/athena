package org.catools.athena.tms.common.mapper;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.catools.athena.model.core.MetadataDto;
import org.catools.athena.model.tms.ItemDto;
import org.catools.athena.model.tms.ItemTypeDto;
import org.catools.athena.model.tms.PriorityDto;
import org.catools.athena.model.tms.StatusDto;
import org.catools.athena.model.tms.StatusTransitionDto;
import org.catools.athena.model.tms.SyncInfoDto;
import org.catools.athena.model.tms.TestCycleDto;
import org.catools.athena.model.tms.TestExecutionDto;
import org.catools.athena.tms.common.entity.Item;
import org.catools.athena.tms.common.entity.ItemMetadata;
import org.catools.athena.tms.common.entity.ItemType;
import org.catools.athena.tms.common.entity.Priority;
import org.catools.athena.tms.common.entity.Status;
import org.catools.athena.tms.common.entity.StatusTransition;
import org.catools.athena.tms.common.entity.SyncInfo;
import org.catools.athena.tms.common.entity.TestCycle;
import org.catools.athena.tms.common.entity.TestExecution;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TmsMapperImpl implements TmsMapper {

  private final TmsMapperService tmsMapperService;

  @Override
  public ItemMetadata metadataDtoToItemMetadata(final MetadataDto metadata) {
    if (metadata == null) {
      return null;
    }

    return new ItemMetadata()
        .setId(metadata.getId())
        .setName(metadata.getName())
        .setValue(metadata.getValue());
  }

  @Override
  public MetadataDto itemMetadataToMetadataDto(final ItemMetadata metadata) {
    if (metadata == null) {
      return null;
    }

    return new MetadataDto()
        .setId(metadata.getId())
        .setName(metadata.getName())
        .setValue(metadata.getValue());
  }

  @Override
  public StatusTransition statusTransitionDtoToStatusTransition(final StatusTransitionDto statusTransitionDto, final String itemCode) {
    if (statusTransitionDto == null && itemCode == null) {
      return null;
    }

    final StatusTransition statusTransition = new StatusTransition();
    if (statusTransitionDto != null) {
      statusTransition.setAuthorId(tmsMapperService.getUserId(statusTransitionDto.getAuthor()));
      statusTransition.setId(statusTransitionDto.getId());
      statusTransition.setOccurred(statusTransitionDto.getOccurred());
      statusTransition.setFrom(tmsMapperService.getStatus(statusTransitionDto.getFrom()));
      statusTransition.setTo(tmsMapperService.getStatus(statusTransitionDto.getTo()));
    }
    statusTransition.setItem(tmsMapperService.getItemByCode(itemCode));
    return statusTransition;
  }

  @Override
  public StatusTransitionDto statusTransitionToStatusTransitionDto(final StatusTransition statusTransition) {
    if (statusTransition == null) {
      return null;
    }

    return new StatusTransitionDto()
        .setAuthor(tmsMapperService.getUsername(statusTransition.getAuthorId()))
        .setFrom(statusTransitionFromCode(statusTransition))
        .setTo(statusTransitionToCode(statusTransition))
        .setId(statusTransition.getId())
        .setOccurred(statusTransition.getOccurred());
  }

  @Override
  public Item itemDtoToItem(final ItemDto item) {
    if (item == null) {
      return null;
    }

    final Item mapped = new Item()
        .setProjectId(tmsMapperService.getProjectId(item.getProject()))
        .setCreatedBy(tmsMapperService.getUserId(item.getCreatedBy()))
        .setUpdatedBy(tmsMapperService.getUserId(item.getUpdatedBy()))
        .setId(item.getId())
        .setCode(item.getCode())
        .setName(item.getName())
        .setCreatedOn(item.getCreatedOn())
        .setUpdatedOn(item.getUpdatedOn())
        .setType(tmsMapperService.getItemType(item.getType()))
        .setStatus(tmsMapperService.getStatus(item.getStatus()))
        .setPriority(tmsMapperService.getPriority(item.getPriority()))
        .setMetadata(metadataDtoSetToItemMetadataSet(item.getMetadata()));

    mapped.setStatusTransitions(item.getStatusTransitions() == null
        ? null
        : item.getStatusTransitions().stream()
            .map(statusTransition -> statusTransitionDtoToStatusTransition(statusTransition, item.getCode()))
            .collect(Collectors.toSet()));
    mapped.setVersionIds(item.getVersions() == null
        ? null
        : item.getVersions().stream()
            .map(version -> tmsMapperService.getVersionId(item.getProject(), version))
            .collect(Collectors.toSet()));
    return mapped;
  }

  @Override
  public ItemDto itemToItemDto(final Item item) {
    if (item == null) {
      return null;
    }

    final ItemDto mapped = new ItemDto()
        .setType(itemTypeCode(item))
        .setStatus(itemStatusCode(item))
        .setPriority(itemPriorityCode(item))
        .setProject(tmsMapperService.getProjectCode(item.getProjectId()))
        .setCreatedBy(tmsMapperService.getUsername(item.getCreatedBy()))
        .setUpdatedBy(tmsMapperService.getUsername(item.getUpdatedBy()))
        .setId(item.getId())
        .setCode(item.getCode())
        .setName(item.getName())
        .setCreatedOn(item.getCreatedOn())
        .setUpdatedOn(item.getUpdatedOn())
        .setMetadata(itemMetadataSetToMetadataDtoSet(item.getMetadata()))
        .setStatusTransitions(statusTransitionSetToStatusTransitionDtoSet(item.getStatusTransitions()));

    mapped.setVersions(item.getVersionIds() == null
        ? null
        : item.getVersionIds().stream()
            .map(versionId -> tmsMapperService.getVersion(versionId).getCode())
            .collect(Collectors.toSet()));
    return mapped;
  }

  @Override
  public SyncInfo syncInfoDtoToSyncInfo(final SyncInfoDto syncInfo) {
    if (syncInfo == null) {
      return null;
    }

    return new SyncInfo()
        .setProjectId(tmsMapperService.getProjectId(syncInfo.getProject()))
        .setId(syncInfo.getId())
        .setAction(syncInfo.getAction())
        .setComponent(syncInfo.getComponent())
        .setStartTime(syncInfo.getStartTime())
        .setEndTime(syncInfo.getEndTime());
  }

  @Override
  public SyncInfoDto syncInfoToSyncInfoDto(final SyncInfo syncInfo) {
    if (syncInfo == null) {
      return null;
    }

    return new SyncInfoDto()
        .setProject(tmsMapperService.getProjectCode(syncInfo.getProjectId()))
        .setId(syncInfo.getId())
        .setAction(syncInfo.getAction())
        .setComponent(syncInfo.getComponent())
        .setStartTime(syncInfo.getStartTime())
        .setEndTime(syncInfo.getEndTime());
  }

  @Override
  public ItemType itemTypeDtoToItemType(final ItemTypeDto itemType) {
    if (itemType == null) {
      return null;
    }

    return new ItemType()
        .setId(itemType.getId())
        .setCode(itemType.getCode())
        .setName(itemType.getName());
  }

  @Override
  public ItemTypeDto itemTypeToItemTypeDto(final ItemType itemType) {
    if (itemType == null) {
      return null;
    }

    return new ItemTypeDto()
        .setId(itemType.getId())
        .setCode(itemType.getCode())
        .setName(itemType.getName());
  }

  @Override
  public Status statusDtoToStatus(final StatusDto status) {
    if (status == null) {
      return null;
    }

    return new Status()
        .setId(status.getId())
        .setCode(status.getCode())
        .setName(status.getName());
  }

  @Override
  public StatusDto statusToStatusDto(final Status status) {
    if (status == null) {
      return null;
    }

    return new StatusDto()
        .setId(status.getId())
        .setCode(status.getCode())
        .setName(status.getName());
  }

  @Override
  public Priority priorityDtoToPriority(final PriorityDto priority) {
    if (priority == null) {
      return null;
    }

    return new Priority()
        .setId(priority.getId())
        .setCode(priority.getCode())
        .setName(priority.getName());
  }

  @Override
  public PriorityDto priorityToPriorityDto(final Priority priority) {
    if (priority == null) {
      return null;
    }

    return new PriorityDto()
        .setId(priority.getId())
        .setCode(priority.getCode())
        .setName(priority.getName());
  }

  @Override
  public TestCycle testCycleDtoToTestCycle(final TestCycleDto cycle) {
    if (cycle == null) {
      return null;
    }

    final TestCycle testCycle = new TestCycle()
        .setId(cycle.getId())
        .setCode(cycle.getCode())
        .setName(cycle.getName())
        .setStartDate(cycle.getStartDate())
        .setEndDate(cycle.getEndDate())
        .setVersionId(cycle.getVersion() == null ? null : tmsMapperService.getVersionId(cycle.getProject(), cycle.getVersion()));

    testCycle.setTestExecutions(cycle.getTestExecutions() == null
        ? null
        : cycle.getTestExecutions().stream()
            .map(execution -> testExecutionDtoToTestExecution(testCycle, execution))
            .collect(Collectors.toSet()));
    return testCycle;
  }

  @Override
  public TestCycleDto testCycleToTestCycleDto(final TestCycle cycle) {
    if (cycle == null) {
      return null;
    }

    return new TestCycleDto()
        .setId(cycle.getId())
        .setCode(cycle.getCode())
        .setName(cycle.getName())
        .setStartDate(cycle.getStartDate())
        .setEndDate(cycle.getEndDate())
        .setTestExecutions(testExecutionSetToTestExecutionDtoSet(cycle.getTestExecutions()))
        .setVersion(cycle.getVersionId() == null ? null : tmsMapperService.getVersion(cycle.getVersionId()).getCode())
        .setProject(cycle.getVersionId() == null ? null : tmsMapperService.getVersion(cycle.getVersionId()).getProject());
  }

  @Override
  public TestExecution testExecutionDtoToTestExecution(final TestCycle cycle, final TestExecutionDto testExecution) {
    if (cycle == null && testExecution == null) {
      return null;
    }

    final TestExecution mapped = new TestExecution();
    if (testExecution != null) {
      mapped.setExecutorId(tmsMapperService.getUserId(testExecution.getExecutor()));
      mapped.setId(testExecution.getId());
      mapped.setCreatedOn(testExecution.getCreatedOn());
      mapped.setExecutedOn(testExecution.getExecutedOn());
      mapped.setItem(tmsMapperService.getItemByCode(testExecution.getItem()));
      mapped.setStatus(tmsMapperService.getStatus(testExecution.getStatus()));
    }
    mapped.setCycle(cycle);
    return mapped;
  }

  @Override
  public TestExecutionDto testExecutionToTestExecutionDto(final TestExecution testExecution) {
    if (testExecution == null) {
      return null;
    }

    return new TestExecutionDto()
        .setItem(testExecutionItemCode(testExecution))
        .setStatus(testExecutionStatusCode(testExecution))
        .setExecutor(tmsMapperService.getUsername(testExecution.getExecutorId()))
        .setId(testExecution.getId())
        .setCreatedOn(testExecution.getCreatedOn())
        .setExecutedOn(testExecution.getExecutedOn());
  }

  private Set<ItemMetadata> metadataDtoSetToItemMetadataSet(final Set<MetadataDto> metadata) {
    if (metadata == null) {
      return null;
    }

    final Set<ItemMetadata> mapped = new LinkedHashSet<>();
    for (MetadataDto item : metadata) {
      mapped.add(metadataDtoToItemMetadata(item));
    }
    return mapped;
  }

  private Set<MetadataDto> itemMetadataSetToMetadataDtoSet(final Set<ItemMetadata> metadata) {
    if (metadata == null) {
      return null;
    }

    final Set<MetadataDto> mapped = new LinkedHashSet<>();
    for (ItemMetadata item : metadata) {
      mapped.add(itemMetadataToMetadataDto(item));
    }
    return mapped;
  }

  private Set<StatusTransitionDto> statusTransitionSetToStatusTransitionDtoSet(final Set<StatusTransition> transitions) {
    if (transitions == null) {
      return null;
    }

    final Set<StatusTransitionDto> mapped = new LinkedHashSet<>();
    for (StatusTransition item : transitions) {
      mapped.add(statusTransitionToStatusTransitionDto(item));
    }
    return mapped;
  }

  private Set<TestExecutionDto> testExecutionSetToTestExecutionDtoSet(final Set<TestExecution> executions) {
    if (executions == null) {
      return null;
    }

    final Set<TestExecutionDto> mapped = new LinkedHashSet<>();
    for (TestExecution item : executions) {
      mapped.add(testExecutionToTestExecutionDto(item));
    }
    return mapped;
  }

  private String statusTransitionFromCode(final StatusTransition statusTransition) {
    final Status from = statusTransition.getFrom();
    return from == null ? null : from.getCode();
  }

  private String statusTransitionToCode(final StatusTransition statusTransition) {
    final Status to = statusTransition.getTo();
    return to == null ? null : to.getCode();
  }

  private String itemTypeCode(final Item item) {
    final ItemType type = item.getType();
    return type == null ? null : type.getCode();
  }

  private String itemStatusCode(final Item item) {
    final Status status = item.getStatus();
    return status == null ? null : status.getCode();
  }

  private String itemPriorityCode(final Item item) {
    final Priority priority = item.getPriority();
    return priority == null ? null : priority.getCode();
  }

  private String testExecutionItemCode(final TestExecution testExecution) {
    final Item item = testExecution.getItem();
    return item == null ? null : item.getCode();
  }

  private String testExecutionStatusCode(final TestExecution testExecution) {
    final Status status = testExecution.getStatus();
    return status == null ? null : status.getCode();
  }
}