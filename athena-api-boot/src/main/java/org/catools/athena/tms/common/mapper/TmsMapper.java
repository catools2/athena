package org.catools.athena.tms.common.mapper;

import org.catools.athena.core.common.mapper.CoreMapperService;
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


@Mapper(uses = {TmsMapperService.class, CoreMapperService.class})
public interface TmsMapper {

  ItemMetadata metadataDtoToItemMetadata(MetadataDto metadata);

  MetadataDto itemMetadataToMetadataDto(ItemMetadata metadata);

  @Mapping(source = "itemCode", target = "item")
  StatusTransition statusTransitionDtoToStatusTransition(StatusTransitionDto statusTransitionDto, String itemCode);

  @Mapping(source = "author.username", target = "author")
  @Mapping(source = "from.code", target = "from")
  @Mapping(source = "to.code", target = "to")
  StatusTransitionDto statusTransitionToStatusTransitionDto(StatusTransition statusTransition);

  Item itemDtoToItem(ItemDto item);

  @Mapping(source = "type.code", target = "type")
  @Mapping(source = "status.code", target = "status")
  @Mapping(source = "priority.code", target = "priority")
  @Mapping(source = "project.code", target = "project")
  @Mapping(source = "createdBy.username", target = "createdBy")
  @Mapping(source = "updatedBy.username", target = "updatedBy")
  ItemDto itemToItemDto(Item item);

  SyncInfo syncInfoDtoToSyncInfo(SyncInfoDto syncInfo);

  @Mapping(source = "project.code", target = "project")
  SyncInfoDto syncInfoToSyncInfoDto(SyncInfo syncInfo);

  ItemType itemTypeDtoToItemType(ItemTypeDto itemType);

  ItemTypeDto itemTypeToItemTypeDto(ItemType itemType);

  Status statusDtoToStatus(StatusDto status);

  StatusDto statusToStatusDto(Status status);

  Priority priorityDtoToPriority(PriorityDto priority);

  PriorityDto priorityToPriorityDto(Priority priority);

  TestCycle testCycleDtoToTestCycle(TestCycleDto cycle);

  @Mapping(source = "version.code", target = "version")
  TestCycleDto testCycleToTestCycleDto(TestCycle cycle);

  TestExecution testExecutionDtoToTestExecution(TestExecutionDto testExecution);

  @Mapping(source = "item.code", target = "item")
  @Mapping(source = "status.code", target = "status")
  @Mapping(source = "executor.username", target = "executor")
  TestExecutionDto testExecutionToTestExecutionDto(TestExecution testExecution);

}
