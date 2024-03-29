package org.catools.athena.apispec.builder;

import lombok.experimental.UtilityClass;
import org.catools.athena.apispec.common.entity.ApiPath;
import org.catools.athena.apispec.common.entity.ApiPathMetadata;
import org.catools.athena.apispec.common.entity.ApiSpec;
import org.catools.athena.apispec.common.entity.ApiSpecMetadata;
import org.catools.athena.apispec.model.ApiPathDto;
import org.catools.athena.apispec.model.ApiSpecDto;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.common.entity.Project;
import org.catools.athena.core.model.MetadataDto;
import org.instancio.Instancio;

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
        .set(field(ApiSpecDto::getMetadata), CoreBuilder.buildMetadataDto())
        .create();
  }

  public static ApiSpec buildApiSpec(ApiSpecDto apiSpecDto, Project project) {
    return new ApiSpec()
        .setId(apiSpecDto.getId())
        .setName(apiSpecDto.getName())
        .setTitle(apiSpecDto.getTitle())
        .setLastSyncTime(apiSpecDto.getLastSyncTime())
        .setFirstTimeSeen(apiSpecDto.getFirstTimeSeen())
        .setProject(project)
        .setMetadata(apiSpecDto.getMetadata().stream().map(ApiSpecBuilder::buildApiSpecMetadata).collect(Collectors.toSet()));
  }

  public static ApiPathDto buildApiPathDto(ApiSpec apiSpec) {
    return Instancio.of(ApiPathDto.class)
        .ignore(field(ApiPathDto::getId))
        .generate(field(ApiPathDto::getTitle), gen -> gen.string().length(1, 1000))
        .set(field(ApiPathDto::getMetadata), CoreBuilder.buildMetadataDto())
        .set(field(ApiPathDto::getSpecId), apiSpec.getId())
        .create();
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

}
