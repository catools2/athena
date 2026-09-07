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


public interface TmsMapper {

  ItemMetadata metadataDtoToItemMetadata(MetadataDto metadata);

  MetadataDto itemMetadataToMetadataDto(ItemMetadata metadata);

  StatusTransition statusTransitionDtoToStatusTransition(StatusTransitionDto statusTransitionDto, String itemCode);

  StatusTransitionDto statusTransitionToStatusTransitionDto(StatusTransition statusTransition);

  Item itemDtoToItem(ItemDto item);

  ItemDto itemToItemDto(Item item);

  SyncInfo syncInfoDtoToSyncInfo(SyncInfoDto syncInfo);

  SyncInfoDto syncInfoToSyncInfoDto(SyncInfo syncInfo);

  ItemType itemTypeDtoToItemType(ItemTypeDto itemType);

  ItemTypeDto itemTypeToItemTypeDto(ItemType itemType);

  Status statusDtoToStatus(StatusDto status);

  StatusDto statusToStatusDto(Status status);

  Priority priorityDtoToPriority(PriorityDto priority);

  PriorityDto priorityToPriorityDto(Priority priority);

  TestCycle testCycleDtoToTestCycle(TestCycleDto cycle);

  TestCycleDto testCycleToTestCycleDto(TestCycle cycle);

  TestExecution testExecutionDtoToTestExecution(TestCycle cycle, TestExecutionDto testExecution);

  TestExecutionDto testExecutionToTestExecutionDto(TestExecution testExecution);

}
