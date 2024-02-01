package org.catools.athena.rest.apispec.mapper;

import org.catools.athena.apispec.model.ApiPathDto;
import org.catools.athena.apispec.model.ApiSpecDto;
import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.rest.apispec.entity.ApiPath;
import org.catools.athena.rest.apispec.entity.ApiPathMetadata;
import org.catools.athena.rest.apispec.entity.ApiSpec;
import org.catools.athena.rest.apispec.entity.ApiSpecMetadata;
import org.catools.athena.rest.core.mapper.CoreMapperService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(uses = {CoreMapperService.class})
public interface ApiSpecMapper {

  @Mapping(source = "project.code", target = "project")
  ApiSpecDto apiSpecToApiSpecDto(ApiSpec apiSpec);

  ApiSpec apiSpecDtoToApiSpec(ApiSpecDto apiSpec);

  @Mapping(source = "apiSpec.id", target = "apiSpecId")
  ApiPathDto apiPathToApiPathDto(ApiPath apiPath);

  @Mapping(source = "apiSpecId", target = "apiSpec.id")
  ApiPath apiPathDtoToApiPath(ApiPathDto apiPathDto);

  MetadataDto apiSpecMetadataToMetadataDto(ApiSpecMetadata pathMetadata);

  ApiSpecMetadata metadataDtoToApiSpecMetadata(MetadataDto metadata);

  MetadataDto apiPathMetadataToMetadataDto(ApiPathMetadata pathMetadata);

  ApiPathMetadata metadataDtoToApiPathMetadata(MetadataDto metadata);

}
