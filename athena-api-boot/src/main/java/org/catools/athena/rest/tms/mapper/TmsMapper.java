package org.catools.athena.rest.tms.mapper;

import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.rest.core.mapper.CoreMapperService;
import org.catools.athena.rest.tms.entity.*;
import org.catools.athena.tms.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(uses = {TmsMapperService.class, CoreMapperService.class})
public abstract class TmsMapper {

    public abstract ItemMetadata metadataDtoToItemMetadata(MetadataDto metadata);

    public abstract MetadataDto itemMetadataToMetadataDto(ItemMetadata metadata);

    @Mappings({
            @Mapping(source = "itemCode", target = "item")
    })
    public abstract StatusTransition statusTransitionDtoToStatusTransition(String itemCode, StatusTransitionDto statusTransitionDto);

    @Mappings({
            @Mapping(source = "from.code", target = "from"),
            @Mapping(source = "to.code", target = "to"),
    })
    public abstract StatusTransitionDto statusTransitionToStatusTransitionDto(StatusTransition statusTransition);

    public abstract Item itemDtoToItem(ItemDto item);

    @Mappings({
            @Mapping(source = "type.code", target = "type"),
            @Mapping(source = "status.code", target = "status"),
            @Mapping(source = "priority.code", target = "priority"),
            @Mapping(source = "project.code", target = "project"),
            @Mapping(source = "createdBy.name", target = "createdBy"),
            @Mapping(source = "updatedBy.name", target = "updatedBy")
    })
    public abstract ItemDto itemToItemDto(Item item);

    public abstract ItemType itemTypeDtoToItemType(ItemTypeDto itemType);

    public abstract ItemTypeDto itemTypeToItemTypeDto(ItemType itemType);

    public abstract Status statusDtoToStatus(StatusDto status);

    public abstract StatusDto statusToStatusDto(Status status);

    public abstract Priority priorityDtoToPriority(PriorityDto priority);

    public abstract PriorityDto priorityToPriorityDto(Priority priority);

    public abstract TestCycle testCycleDtoToTestCycle(TestCycleDto cycle);

    @Mappings({
            @Mapping(source = "version.code", target = "version")
    })
    public abstract TestCycleDto testCycleToTestCycleDto(TestCycle cycle);

    public abstract TestExecution testExecutionDtoToTestExecution(TestExecutionDto testExecution);

    @Mappings({
            @Mapping(source = "cycle.code", target = "cycle"),
            @Mapping(source = "item.code", target = "item"),
            @Mapping(source = "status.code", target = "status"),
            @Mapping(source = "executor.name", target = "executor")
    })
    public abstract TestExecutionDto testExecutionToTestExecutionDto(TestExecution testExecution);
}
