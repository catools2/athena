package org.catools.athena.tms.common.mapper;

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
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {TmsMapperService.class})
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
  @Mapping(target = "versionIds", expression = "java(item.getVersions() == null ? null : item.getVersions().stream().map(v -> tmsMapperService.getVersionId(item.getProject(), v)).collect(java.util.stream.Collectors.toSet()))")
  Item itemDtoToItem(ItemDto item);

  @Mapping(source = "type.code", target = "type")
  @Mapping(source = "status.code", target = "status")
  @Mapping(source = "priority.code", target = "priority")
  @Mapping(source = "projectId", target = "project", qualifiedByName = "getProjectCode")
  @Mapping(source = "createdBy", target = "createdBy", qualifiedByName = "getUsername")
  @Mapping(source = "updatedBy", target = "updatedBy", qualifiedByName = "getUsername")
  @Mapping(target = "versions", expression = "java(item.getVersionIds() == null ? null : item.getVersionIds().stream().map(id -> tmsMapperService.getVersion(id).getCode()).collect(java.util.stream.Collectors.toSet()))")
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

  @Mapping(target = "versionId", expression = "java(cycle.getVersion() == null ? null : tmsMapperService.getVersionId(cycle.getProject(), cycle.getVersion()))")
  @Mapping(target = "testExecutions", expression = "java(cycle.getTestExecutions() == null ? null : cycle.getTestExecutions().stream().map(ex -> testExecutionDtoToTestExecution(testCycle, ex)).collect(java.util.stream.Collectors.toSet()))")
  TestCycle testCycleDtoToTestCycle(TestCycleDto cycle);

  @Mapping(target = "version", expression = "java(cycle.getVersionId() == null ? null : tmsMapperService.getVersion(cycle.getVersionId()).getCode())")
  @Mapping(target = "project", expression = "java(cycle.getVersionId() == null ? null : tmsMapperService.getVersion(cycle.getVersionId()).getProject())")
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
