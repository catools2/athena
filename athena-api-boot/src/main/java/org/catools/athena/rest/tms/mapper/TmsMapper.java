package org.catools.athena.rest.tms.mapper;

import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.rest.core.mapper.CoreMapperService;
import org.catools.athena.rest.tms.entity.*;
import org.catools.athena.tms.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(uses = {TmsMapperService.class, CoreMapperService.class})
public interface TmsMapper {

  ItemMetadata metadataDtoToItemMetadata(MetadataDto metadata);

  MetadataDto itemMetadataToMetadataDto(ItemMetadata metadata);

  @Mapping(source = "itemCode", target = "item")
  StatusTransition statusTransitionDtoToStatusTransition(String itemCode, StatusTransitionDto statusTransitionDto);

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

  @Mapping(source = "cycle.code", target = "cycle")
  @Mapping(source = "item.code", target = "item")
  @Mapping(source = "status.code", target = "status")
  @Mapping(source = "executor.username", target = "executor")
  TestExecutionDto testExecutionToTestExecutionDto(TestExecution testExecution);
}
