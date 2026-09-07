package org.catools.athena.spec.common.mapper;

import org.catools.athena.model.apispec.ApiPathDto;
import org.catools.athena.model.apispec.ApiSpecDto;
import org.catools.athena.model.core.MetadataDto;
import org.catools.athena.spec.common.entity.ApiPath;
import org.catools.athena.spec.common.entity.ApiPathMetadata;
import org.catools.athena.spec.common.entity.ApiSpec;
import org.catools.athena.spec.common.entity.ApiSpecMetadata;

public interface ApiSpecMapper {

  ApiSpecDto apiSpecToApiSpecDto(ApiSpec apiSpec);

  ApiSpec apiSpecDtoToApiSpec(ApiSpecDto apiSpec);

  ApiPathDto apiPathToApiPathDto(ApiPath apiPath);

  ApiPath apiPathDtoToApiPath(ApiPathDto apiPathDto);

  MetadataDto apiSpecMetadataToMetadataDto(ApiSpecMetadata pathMetadata);

  ApiSpecMetadata metadataDtoToApiSpecMetadata(MetadataDto metadata);

  MetadataDto apiPathMetadataToMetadataDto(ApiPathMetadata pathMetadata);

  ApiPathMetadata metadataDtoToApiPathMetadata(MetadataDto metadata);

}
