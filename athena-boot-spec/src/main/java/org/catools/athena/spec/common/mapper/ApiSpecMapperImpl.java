package org.catools.athena.spec.common.mapper;

import lombok.RequiredArgsConstructor;
import org.catools.athena.model.apispec.ApiPathDto;
import org.catools.athena.model.apispec.ApiSpecDto;
import org.catools.athena.model.core.MetadataDto;
import org.catools.athena.spec.common.entity.ApiPath;
import org.catools.athena.spec.common.entity.ApiPathMetadata;
import org.catools.athena.spec.common.entity.ApiSpec;
import org.catools.athena.spec.common.entity.ApiSpecMetadata;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ApiSpecMapperImpl implements ApiSpecMapper {

  private final ApiSpecMapperService apiSpecMapperService;

  @Override
  public ApiSpecDto apiSpecToApiSpecDto(final ApiSpec apiSpec) {
    if (apiSpec == null) {
      return null;
    }

    return new ApiSpecDto()
        .setProject(apiSpecMapperService.getProjectCode(apiSpec.getProjectId()))
        .setId(apiSpec.getId())
        .setVersion(apiSpec.getVersion())
        .setName(apiSpec.getName())
        .setTitle(apiSpec.getTitle())
        .setFirstTimeSeen(apiSpec.getFirstTimeSeen())
        .setLastSyncTime(apiSpec.getLastSyncTime())
        .setPaths(apiPathSetToApiPathDtoSet(apiSpec.getPaths()))
        .setMetadata(apiSpecMetadataSetToMetadataDtoSet(apiSpec.getMetadata()));
  }

  @Override
  public ApiSpec apiSpecDtoToApiSpec(final ApiSpecDto apiSpec) {
    if (apiSpec == null) {
      return null;
    }

    final ApiSpec mapped = new ApiSpec()
        .setProjectId(apiSpecMapperService.getProjectId(apiSpec.getProject()))
        .setId(apiSpec.getId())
        .setVersion(apiSpec.getVersion())
        .setName(apiSpec.getName())
        .setTitle(apiSpec.getTitle())
        .setFirstTimeSeen(apiSpec.getFirstTimeSeen())
        .setLastSyncTime(apiSpec.getLastSyncTime())
        .setMetadata(metadataDtoSetToApiSpecMetadataSet(apiSpec.getMetadata()));

    mapped.setPaths(apiPathDtoSetToApiPathSet(apiSpec.getPaths()));
    return mapped;
  }

  @Override
  public ApiPathDto apiPathToApiPathDto(final ApiPath apiPath) {
    if (apiPath == null) {
      return null;
    }

    final ApiPathDto apiPathDto = new ApiPathDto()
        .setSpecId(apiPath.getSpec() == null ? null : apiPath.getSpec().getId())
        .setId(apiPath.getId())
        .setMethod(apiPath.getMethod())
        .setTitle(apiPath.getTitle())
        .setDescription(apiPath.getDescription())
        .setFirstTimeSeen(apiPath.getFirstTimeSeen())
        .setLastSyncTime(apiPath.getLastSyncTime())
        .setUrl(apiPath.getUrl())
        .setMetadata(apiPathMetadataSetToMetadataDtoSet(apiPath.getMetadata()));

    if (apiPath.getParameters() != null) {
      apiPathDto.setParameters(new LinkedHashMap<>(apiPath.getParameters()));
    }

    return apiPathDto;
  }

  @Override
  public ApiPath apiPathDtoToApiPath(final ApiPathDto apiPathDto) {
    if (apiPathDto == null) {
      return null;
    }

    final ApiPath apiPath = new ApiPath()
        .setId(apiPathDto.getId())
        .setMethod(apiPathDto.getMethod())
        .setTitle(apiPathDto.getTitle())
        .setDescription(apiPathDto.getDescription())
        .setUrl(apiPathDto.getUrl())
        .setFirstTimeSeen(apiPathDto.getFirstTimeSeen())
        .setLastSyncTime(apiPathDto.getLastSyncTime())
        .setMetadata(metadataDtoSetToApiPathMetadataSet(apiPathDto.getMetadata()));

    if (apiPathDto.getParameters() != null) {
      apiPath.setParameters(new LinkedHashMap<>(apiPathDto.getParameters()));
    }

    if (apiPathDto.getSpecId() != null) {
      apiPath.setSpec(new ApiSpec().setId(apiPathDto.getSpecId()));
    }

    return apiPath;
  }

  @Override
  public MetadataDto apiSpecMetadataToMetadataDto(final ApiSpecMetadata pathMetadata) {
    if (pathMetadata == null) {
      return null;
    }

    return new MetadataDto()
        .setId(pathMetadata.getId())
        .setName(pathMetadata.getName())
        .setValue(pathMetadata.getValue());
  }

  @Override
  public ApiSpecMetadata metadataDtoToApiSpecMetadata(final MetadataDto metadata) {
    if (metadata == null) {
      return null;
    }

    return new ApiSpecMetadata()
        .setId(metadata.getId())
        .setName(metadata.getName())
        .setValue(metadata.getValue());
  }

  @Override
  public MetadataDto apiPathMetadataToMetadataDto(final ApiPathMetadata pathMetadata) {
    if (pathMetadata == null) {
      return null;
    }

    return new MetadataDto()
        .setId(pathMetadata.getId())
        .setName(pathMetadata.getName())
        .setValue(pathMetadata.getValue());
  }

  @Override
  public ApiPathMetadata metadataDtoToApiPathMetadata(final MetadataDto metadata) {
    if (metadata == null) {
      return null;
    }

    return new ApiPathMetadata()
        .setId(metadata.getId())
        .setName(metadata.getName())
        .setValue(metadata.getValue());
  }

  private Set<ApiPathDto> apiPathSetToApiPathDtoSet(final Set<ApiPath> paths) {
    if (paths == null) {
      return null;
    }

    final Set<ApiPathDto> mapped = new LinkedHashSet<>();
    for (ApiPath apiPath : paths) {
      mapped.add(apiPathToApiPathDto(apiPath));
    }
    return mapped;
  }

  private Set<MetadataDto> apiSpecMetadataSetToMetadataDtoSet(final Set<ApiSpecMetadata> metadata) {
    if (metadata == null) {
      return null;
    }

    final Set<MetadataDto> mapped = new LinkedHashSet<>();
    for (ApiSpecMetadata item : metadata) {
      mapped.add(apiSpecMetadataToMetadataDto(item));
    }
    return mapped;
  }

  private Set<ApiPath> apiPathDtoSetToApiPathSet(final Set<ApiPathDto> paths) {
    if (paths == null) {
      return null;
    }

    final Set<ApiPath> mapped = new LinkedHashSet<>();
    for (ApiPathDto item : paths) {
      mapped.add(apiPathDtoToApiPath(item));
    }
    return mapped;
  }

  private Set<ApiSpecMetadata> metadataDtoSetToApiSpecMetadataSet(final Set<MetadataDto> metadata) {
    if (metadata == null) {
      return null;
    }

    final Set<ApiSpecMetadata> mapped = new LinkedHashSet<>();
    for (MetadataDto item : metadata) {
      mapped.add(metadataDtoToApiSpecMetadata(item));
    }
    return mapped;
  }

  private Set<MetadataDto> apiPathMetadataSetToMetadataDtoSet(final Set<ApiPathMetadata> metadata) {
    if (metadata == null) {
      return null;
    }

    final Set<MetadataDto> mapped = new LinkedHashSet<>();
    for (ApiPathMetadata item : metadata) {
      mapped.add(apiPathMetadataToMetadataDto(item));
    }
    return mapped;
  }

  private Set<ApiPathMetadata> metadataDtoSetToApiPathMetadataSet(final Set<MetadataDto> metadata) {
    if (metadata == null) {
      return null;
    }

    final Set<ApiPathMetadata> mapped = new LinkedHashSet<>();
    for (MetadataDto item : metadata) {
      mapped.add(metadataDtoToApiPathMetadata(item));
    }
    return mapped;
  }
}