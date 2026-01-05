package org.catools.athena.spec.common.mapper;

import org.catools.athena.model.apispec.ApiPathDto;
import org.catools.athena.model.apispec.ApiSpecDto;
import org.catools.athena.model.core.MetadataDto;
import org.catools.athena.spec.common.entity.ApiPath;
import org.catools.athena.spec.common.entity.ApiPathMetadata;
import org.catools.athena.spec.common.entity.ApiSpec;
import org.catools.athena.spec.common.entity.ApiSpecMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {ApiSpecMapperService.class})
public interface ApiSpecMapper {

  @Mapping(source = "projectId", target = "project")
  ApiSpecDto apiSpecToApiSpecDto(ApiSpec apiSpec);

  @Mapping(source = "project", target = "projectId")
  ApiSpec apiSpecDtoToApiSpec(ApiSpecDto apiSpec);

  @Mapping(source = "spec.id", target = "specId")
  ApiPathDto apiPathToApiPathDto(ApiPath apiPath);

  @Mapping(source = "specId", target = "spec.id")
  ApiPath apiPathDtoToApiPath(ApiPathDto apiPathDto);

  MetadataDto apiSpecMetadataToMetadataDto(ApiSpecMetadata pathMetadata);

  ApiSpecMetadata metadataDtoToApiSpecMetadata(MetadataDto metadata);

  MetadataDto apiPathMetadataToMetadataDto(ApiPathMetadata pathMetadata);

  ApiPathMetadata metadataDtoToApiPathMetadata(MetadataDto metadata);

}
