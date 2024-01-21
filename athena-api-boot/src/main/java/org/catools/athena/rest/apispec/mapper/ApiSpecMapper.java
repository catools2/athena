package org.catools.athena.rest.apispec.mapper;

import org.catools.athena.apispec.model.ApiParameterDto;
import org.catools.athena.apispec.model.ApiPathDto;
import org.catools.athena.apispec.model.ApiSpecDto;
import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.rest.apispec.entity.*;
import org.catools.athena.rest.core.mapper.CoreMapperService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(uses = {CoreMapperService.class})
public abstract class ApiSpecMapper {

  @Mappings({
      @Mapping(source = "project.code", target = "project")
  })
  public abstract ApiSpecDto apiSpecToApiSpecDto(ApiSpec apiSpec);

  public abstract ApiSpec apiSpecDtoToApiSpec(ApiSpecDto apiSpec);

  @Mappings({
      @Mapping(source = "apiSpec.id", target = "apiSpecId")
  })
  public abstract ApiPathDto apiPathToApiPathDto(ApiPath apiPath);

  @Mappings({
      @Mapping(source = "apiSpecId", target = "apiSpec.id")
  })
  public abstract ApiPath apiPathDtoToApiPath(ApiPathDto apiPathDto);

  public abstract MetadataDto apiSpecMetadataToMetadataDto(ApiSpecMetadata pathMetadata);

  public abstract ApiSpecMetadata metadataDtoToApiSpecMetadata(MetadataDto metadata);

  public abstract MetadataDto apiPathMetadataToMetadataDto(ApiPathMetadata pathMetadata);

  public abstract ApiPathMetadata metadataDtoToApiPathMetadata(MetadataDto metadata);

  public abstract ApiParameterDto apiParameterToApiParameterDto(ApiParameter parameter);

  public abstract ApiParameter apiParameterDtoToApiParameter(ApiParameterDto parameterDto);

}
