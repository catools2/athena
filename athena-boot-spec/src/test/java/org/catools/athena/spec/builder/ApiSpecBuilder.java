package org.catools.athena.spec.builder;

import lombok.experimental.UtilityClass;
import org.catools.athena.model.apispec.ApiPathDto;
import org.catools.athena.model.apispec.ApiSpecDto;
import org.catools.athena.model.core.MetadataDto;
import org.catools.athena.spec.common.entity.ApiPath;
import org.catools.athena.spec.common.entity.ApiPathMetadata;
import org.catools.athena.spec.common.entity.ApiSpec;
import org.catools.athena.spec.common.entity.ApiSpecMetadata;
import org.instancio.Instancio;

import java.util.Set;
import java.util.stream.Collectors;

import static org.instancio.Select.field;

@UtilityClass
public class ApiSpecBuilder {
  public static ApiSpecDto buildApiSpecDto(String projectCode) {
    return Instancio.of(ApiSpecDto.class)
        .ignore(field(ApiSpecDto::getId))
        .ignore(field(ApiPathDto::getId))
        .ignore(field(ApiPathDto::getSpecId))
        .generate(field(ApiSpecDto::getTitle), gen -> gen.string().length(1, 100))
        .generate(field(ApiSpecDto::getName), gen -> gen.string().length(1, 100))
        .generate(field(ApiSpecDto::getVersion), gen -> gen.string().length(1, 10))
        .set(field(ApiSpecDto::getProject), projectCode)
        .set(field(ApiSpecDto::getMetadata), buildMetadataDto())
        .set(field(ApiSpecDto::getPaths), buildApiPathDto())
        .create();
  }

  public static ApiSpec buildApiSpec(ApiSpecDto apiSpecDto, Long projectId) {
    return new ApiSpec()
        .setId(apiSpecDto.getId())
        .setName(apiSpecDto.getName())
        .setTitle(apiSpecDto.getTitle())
        .setLastSyncTime(apiSpecDto.getLastSyncTime())
        .setFirstTimeSeen(apiSpecDto.getFirstTimeSeen())
        .setProjectId(projectId)
        .setMetadata(apiSpecDto.getMetadata().stream().map(ApiSpecBuilder::buildApiSpecMetadata).collect(Collectors.toSet()));
  }

  public static Set<ApiPathDto> buildApiPathDto() {
    return Instancio.of(ApiPathDto.class)
        .ignore(field(ApiPathDto::getId))
        .ignore(field(ApiPathDto::getSpecId))
        .generate(field(ApiPathDto::getTitle), gen -> gen.string().length(1, 1000))
        .set(field(ApiPathDto::getMetadata), buildMetadataDto())
        .stream()
        .limit(20)
        .collect(Collectors.toSet());
  }

  public static ApiPath buildApiPath(ApiPathDto apiPathDto, ApiSpec apiSpec) {
    return new ApiPath()
        .setId(apiPathDto.getId())
        .setUrl(apiPathDto.getUrl())
        .setTitle(apiPathDto.getTitle())
        .setDescription(apiPathDto.getDescription())
        .setSpec(apiSpec)
        .setParameters(apiPathDto.getParameters())
        .setMetadata(apiPathDto.getMetadata().stream().map(ApiSpecBuilder::buildApiPathMetadata).collect(Collectors.toSet()));
  }

  public static ApiPathMetadata buildApiPathMetadata(MetadataDto metadataDto) {
    return new ApiPathMetadata()
        .setId(metadataDto.getId())
        .setName(metadataDto.getName())
        .setValue(metadataDto.getValue());
  }

  public static ApiSpecMetadata buildApiSpecMetadata(MetadataDto metadataDto) {
    return new ApiSpecMetadata()
        .setId(metadataDto.getId())
        .setName(metadataDto.getName())
        .setValue(metadataDto.getValue());
  }

  public static Set<MetadataDto> buildMetadataDto() {
    return Instancio.of(MetadataDto.class)
        .ignore(field(MetadataDto::getId))
        .generate(field(MetadataDto::getName), gen -> gen.string().length(1, 100))
        .generate(field(MetadataDto::getValue), gen -> gen.string().length(1, 2000))
        .stream()
        .limit(4)
        .collect(Collectors.toSet());
  }
}
