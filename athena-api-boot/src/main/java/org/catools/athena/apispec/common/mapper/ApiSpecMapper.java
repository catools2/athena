package org.catools.athena.apispec.common.mapper;

import org.catools.athena.apispec.common.entity.ApiPath;
import org.catools.athena.apispec.common.entity.ApiPathMetadata;
import org.catools.athena.apispec.common.entity.ApiSpec;
import org.catools.athena.apispec.common.entity.ApiSpecMetadata;
import org.catools.athena.apispec.model.ApiPathDto;
import org.catools.athena.apispec.model.ApiSpecDto;
import org.catools.athena.core.common.mapper.CoreMapperService;
import org.catools.athena.core.model.MetadataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(uses = {CoreMapperService.class})
public interface ApiSpecMapper {

  @Mapping(source = "project.code", target = "project")
  ApiSpecDto apiSpecToApiSpecDto(ApiSpec apiSpec);

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
