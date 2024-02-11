package org.catools.athena.apispec.mapper;

import org.catools.athena.AthenaBaseIT;
import org.catools.athena.apispec.builder.ApiSpecBuilder;
import org.catools.athena.apispec.common.entity.ApiPath;
import org.catools.athena.apispec.common.entity.ApiSpec;
import org.catools.athena.apispec.common.mapper.ApiSpecMapper;
import org.catools.athena.apispec.common.service.ApiSpecService;
import org.catools.athena.apispec.model.ApiPathDto;
import org.catools.athena.apispec.model.ApiSpecDto;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.common.entity.Project;
import org.catools.athena.core.common.service.ProjectService;
import org.catools.athena.core.model.ProjectDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

class ApiSpecMapperIT extends AthenaBaseIT {

  private static Project PROJECT;
  private static ApiSpecDto API_SPEC_DTO;
  private static ApiSpec API_SPEC;

  @Autowired
  ApiSpecMapper apiSpecMapper;

  @Autowired
  ProjectService projectService;

  @Autowired
  ApiSpecService apiSpecService;

  @BeforeAll
  public void beforeAll() {
    final ProjectDto projectDto = CoreBuilder.buildProjectDto();
    projectDto.setId(projectService.saveOrUpdate(projectDto).getId());
    PROJECT = CoreBuilder.buildProject(projectDto);

    API_SPEC_DTO = ApiSpecBuilder.buildApiSpecDto(PROJECT.getCode());
    API_SPEC = ApiSpecBuilder.buildApiSpec(API_SPEC_DTO, PROJECT);
    API_SPEC.setId(apiSpecService.saveOrUpdate(API_SPEC_DTO).getId());
  }

  @Test
  void apiSpecToApiSpecDto() {
    final ApiSpec apiSpec = apiSpecMapper.apiSpecDtoToApiSpec(API_SPEC_DTO);
    assertThat(apiSpec.getName(), equalTo(API_SPEC_DTO.getName()));
    assertThat(apiSpec.getTitle(), equalTo(API_SPEC_DTO.getTitle()));
    assertThat(apiSpec.getFirstTimeSeen(), equalTo(API_SPEC_DTO.getFirstTimeSeen()));
    assertThat(apiSpec.getLastSyncTime(), equalTo(API_SPEC_DTO.getLastSyncTime()));
    assertThat(apiSpec.getVersion(), equalTo(API_SPEC_DTO.getVersion()));
    assertThat(apiSpec.getProject().getCode(), equalTo(API_SPEC_DTO.getProject()));
    assertThat(API_SPEC_DTO.getMetadata(), notNullValue());
    assertThat(API_SPEC_DTO.getMetadata().isEmpty(), equalTo(false));
    verifyNameValuePairs(apiSpec.getMetadata(), API_SPEC_DTO.getMetadata());

    for (ApiPath apiPath : apiSpec.getPaths()) {
      ApiPathDto pathDto = API_SPEC_DTO.getPaths().stream().filter(p2 -> apiPath.getUrl().equals(p2.getUrl())).findFirst().orElse(new ApiPathDto());
      assertThat(apiPath.getTitle(), equalTo(pathDto.getTitle()));
      assertThat(apiPath.getUrl(), equalTo(pathDto.getUrl()));
      assertThat(apiPath.getSpec().getId(), equalTo(pathDto.getSpecId()));
      assertThat(apiPath.getDescription(), equalTo(pathDto.getDescription()));
      assertThat(apiPath.getMethod(), equalTo(pathDto.getMethod()));

      assertThat(pathDto.getParameters(), notNullValue());
      assertThat(pathDto.getParameters().isEmpty(), equalTo(false));
      assertThat(pathDto.getParameters(), equalTo(apiPath.getParameters()));

      assertThat(pathDto.getMetadata(), notNullValue());
      assertThat(pathDto.getMetadata().isEmpty(), equalTo(false));
      verifyNameValuePairs(apiPath.getMetadata(), pathDto.getMetadata());
    }
  }

  @Test
  void apiSpecToApiSpecDto_shallReturnNullIfTheInputIsNull() {
    assertThat(apiSpecMapper.apiSpecDtoToApiSpec(null), nullValue());
  }

  @Test
  void apiSpecDtoToApiSpec() {
    final ApiSpecDto apiSpecDto = apiSpecMapper.apiSpecToApiSpecDto(API_SPEC);
    assertThat(apiSpecDto.getName(), equalTo(API_SPEC.getName()));
    assertThat(apiSpecDto.getTitle(), equalTo(API_SPEC.getTitle()));
    assertThat(apiSpecDto.getFirstTimeSeen(), equalTo(API_SPEC.getFirstTimeSeen()));
    assertThat(apiSpecDto.getLastSyncTime(), equalTo(API_SPEC.getLastSyncTime()));
    assertThat(apiSpecDto.getVersion(), equalTo(API_SPEC.getVersion()));
    assertThat(apiSpecDto.getProject(), equalTo(API_SPEC.getProject().getCode()));
    assertThat(API_SPEC.getMetadata(), notNullValue());
    assertThat(API_SPEC.getMetadata().isEmpty(), equalTo(false));
    verifyNameValuePairs(apiSpecDto.getMetadata(), API_SPEC.getMetadata());

    for (ApiPath apiPath : API_SPEC.getPaths()) {
      ApiPathDto pathDto = apiSpecDto.getPaths().stream().filter(p2 -> apiPath.getUrl().equals(p2.getUrl())).findFirst().orElse(new ApiPathDto());
      assertThat(apiPath.getTitle(), equalTo(pathDto.getTitle()));
      assertThat(apiPath.getUrl(), equalTo(pathDto.getUrl()));
      assertThat(apiPath.getSpec().getId(), equalTo(pathDto.getSpecId()));
      assertThat(apiPath.getDescription(), equalTo(pathDto.getDescription()));
      assertThat(apiPath.getMethod(), equalTo(pathDto.getMethod()));

      assertThat(pathDto.getParameters(), notNullValue());
      assertThat(pathDto.getParameters().isEmpty(), equalTo(false));
      assertThat(pathDto.getParameters(), equalTo(apiPath.getParameters()));

      assertThat(pathDto.getMetadata(), notNullValue());
      assertThat(pathDto.getMetadata().isEmpty(), equalTo(false));
      verifyNameValuePairs(apiPath.getMetadata(), pathDto.getMetadata());
    }
  }

  @Test
  void apiSpecDtoToApiSpec_shallReturnValueIfPathsIsNull() {
    final ApiSpec apiSpecDto = ApiSpecBuilder.buildApiSpec(API_SPEC_DTO, PROJECT);
    apiSpecDto.setPaths(null);
    assertThat(apiSpecMapper.apiSpecToApiSpecDto(apiSpecDto), notNullValue());
  }

  @Test
  void apiSpecDtoToApiSpec_shallReturnValueIfOneOfPathIsNull() {
    final ApiSpec apiSpecDto = ApiSpecBuilder.buildApiSpec(API_SPEC_DTO, PROJECT);
    apiSpecDto.getPaths().add(null);
    assertThat(apiSpecMapper.apiSpecToApiSpecDto(apiSpecDto), notNullValue());
  }

  @Test
  void apiSpecDtoToApiSpec_shallReturnValueIfMetadataIsNull() {
    final ApiSpec apiSpecDto = ApiSpecBuilder.buildApiSpec(API_SPEC_DTO, PROJECT);
    apiSpecDto.setMetadata(null);
    assertThat(apiSpecMapper.apiSpecToApiSpecDto(apiSpecDto), notNullValue());
  }

  @Test
  void apiSpecDtoToApiSpec_shallReturnValueIfOneOfMetadataIsNull() {
    final ApiSpec apiSpecDto = ApiSpecBuilder.buildApiSpec(API_SPEC_DTO, PROJECT);
    apiSpecDto.getMetadata().add(null);
    assertThat(apiSpecMapper.apiSpecToApiSpecDto(apiSpecDto), notNullValue());
  }

  @Test
  void apiSpecDtoToApiSpec_shallReturnNullIfTheInputIsNull() {
    assertThat(apiSpecMapper.apiSpecToApiSpecDto(null), nullValue());
  }
}