package org.catools.athena.tms.common.mapper;

import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.tms.common.entity.Item;
import org.catools.athena.tms.common.entity.ItemMetadata;
import org.catools.athena.tms.common.entity.ItemType;
import org.catools.athena.tms.common.entity.Priority;
import org.catools.athena.tms.common.entity.Status;
import org.catools.athena.tms.common.entity.StatusTransition;
import org.catools.athena.tms.common.entity.SyncInfo;
import org.catools.athena.tms.common.entity.TestCycle;
import org.catools.athena.tms.common.entity.TestExecution;
import org.catools.athena.tms.model.ItemDto;
import org.catools.athena.tms.model.ItemTypeDto;
import org.catools.athena.tms.model.PriorityDto;
import org.catools.athena.tms.model.StatusDto;
import org.catools.athena.tms.model.StatusTransitionDto;
import org.catools.athena.tms.model.SyncInfoDto;
import org.catools.athena.tms.model.TestCycleDto;
import org.catools.athena.tms.model.TestExecutionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(uses = {TmsMapperService.class})
public interface TmsMapper {

  ItemMetadata metadataDtoToItemMetadata(MetadataDto metadata);

  MetadataDto itemMetadataToMetadataDto(ItemMetadata metadata);

  @Mapping(source = "statusTransitionDto.author", target = "authorId", qualifiedByName = "getUserId")
  @Mapping(source = "itemCode", target = "item")
  StatusTransition statusTransitionDtoToStatusTransition(StatusTransitionDto statusTransitionDto, String itemCode);

  @Mapping(source = "authorId", target = "author", qualifiedByName = "getUsername")
  @Mapping(source = "from.code", target = "from")
  @Mapping(source = "to.code", target = "to")
  StatusTransitionDto statusTransitionToStatusTransitionDto(StatusTransition statusTransition);

  @Mapping(source = "project", target = "projectId", qualifiedByName = "getProjectId")
  @Mapping(source = "createdBy", target = "createdBy", qualifiedByName = "getUserId")
  @Mapping(source = "updatedBy", target = "updatedBy", qualifiedByName = "getUserId")
  @Mapping(target = "statusTransitions", expression = "java(item == null || item.getStatusTransitions() == null ? null : item.getStatusTransitions().stream().map( st -> statusTransitionDtoToStatusTransition(st, item.getCode())).collect(java.util.stream.Collectors.toSet()))")
  @Mapping(target = "versionIds", expression = "java(item.getVersions() == null ? null : item.getVersions().stream().map(tmsMapperService::getVersionId).collect(java.util.stream.Collectors.toSet()))")
  Item itemDtoToItem(ItemDto item);

  @Mapping(source = "type.code", target = "type")
  @Mapping(source = "status.code", target = "status")
  @Mapping(source = "priority.code", target = "priority")
  @Mapping(source = "projectId", target = "project", qualifiedByName = "getProjectCode")
  @Mapping(source = "createdBy", target = "createdBy", qualifiedByName = "getUsername")
  @Mapping(source = "updatedBy", target = "updatedBy", qualifiedByName = "getUsername")
  @Mapping(target = "versions", expression = "java(item.getVersionIds() == null ? null : item.getVersionIds().stream().map(tmsMapperService::getVersionCode).collect(java.util.stream.Collectors.toSet()))")
  ItemDto itemToItemDto(Item item);

  @Mapping(source = "project", target = "projectId", qualifiedByName = "getProjectId")
  SyncInfo syncInfoDtoToSyncInfo(SyncInfoDto syncInfo);

  @Mapping(source = "projectId", target = "project", qualifiedByName = "getProjectCode")
  SyncInfoDto syncInfoToSyncInfoDto(SyncInfo syncInfo);

  ItemType itemTypeDtoToItemType(ItemTypeDto itemType);

  ItemTypeDto itemTypeToItemTypeDto(ItemType itemType);

  Status statusDtoToStatus(StatusDto status);

  StatusDto statusToStatusDto(Status status);

  Priority priorityDtoToPriority(PriorityDto priority);

  PriorityDto priorityToPriorityDto(Priority priority);

  @Mapping(source = "version", target = "versionId", qualifiedByName = "getVersionId")
  @Mapping(target = "testExecutions", expression = "java(cycle.getTestExecutions() == null ? null : cycle.getTestExecutions().stream().map(ex -> testExecutionDtoToTestExecution(testCycle, ex)).collect(java.util.stream.Collectors.toSet()))")
  TestCycle testCycleDtoToTestCycle(TestCycleDto cycle);

  @Mapping(source = "versionId", target = "version", qualifiedByName = "getVersionCode")
  TestCycleDto testCycleToTestCycleDto(TestCycle cycle);

  @Mapping(source = "testExecution.executor", target = "executorId", qualifiedByName = "getUserId")
  @Mapping(source = "testExecution.id", target = "id")
  @Mapping(source = "cycle", target = "cycle")
  TestExecution testExecutionDtoToTestExecution(TestCycle cycle, TestExecutionDto testExecution);

  @Mapping(source = "item.code", target = "item")
  @Mapping(source = "status.code", target = "status")
  @Mapping(source = "executorId", target = "executor", qualifiedByName = "getUsername")
  TestExecutionDto testExecutionToTestExecutionDto(TestExecution testExecution);

}
