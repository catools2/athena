package org.catools.athena.spec.mapper;

import org.catools.athena.AthenaSpringBootIT;
import org.catools.athena.apispec.model.ApiPathDto;
import org.catools.athena.apispec.model.ApiSpecDto;
import org.catools.athena.configs.StagedTestData;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.spec.builder.ApiSpecBuilder;
import org.catools.athena.spec.common.entity.ApiPath;
import org.catools.athena.spec.common.entity.ApiSpec;
import org.catools.athena.spec.common.mapper.ApiSpecMapper;
import org.catools.athena.spec.common.service.ApiSpecService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

class ApiSpecMapperIT extends AthenaSpringBootIT {

  private static Long projectId;
  private static ApiSpecDto apiSpecDto;
  private static ApiSpec apiSpec;

  @Autowired
  ApiSpecMapper apiSpecMapper;

  @Autowired
  ApiSpecService apiSpecService;

  @BeforeAll
  public void beforeAll() {
    ProjectDto project = StagedTestData.getRandomProject();
    String projectCode = project.getCode();
    projectId = project.getId();

    apiSpecDto = ApiSpecBuilder.buildApiSpecDto(projectCode);
    apiSpec = ApiSpecBuilder.buildApiSpec(apiSpecDto, projectId);
    apiSpec.setId(apiSpecService.saveOrUpdate(apiSpecDto).getId());
  }

  @Test
  void apiSpecToApiSpecDto() {
    final ApiSpec spec = apiSpecMapper.apiSpecDtoToApiSpec(apiSpecDto);
    assertThat(spec.getName(), equalTo(apiSpecDto.getName()));
    assertThat(spec.getTitle(), equalTo(apiSpecDto.getTitle()));
    assertThat(spec.getFirstTimeSeen(), equalTo(apiSpecDto.getFirstTimeSeen()));
    assertThat(spec.getLastSyncTime(), equalTo(apiSpecDto.getLastSyncTime()));
    assertThat(spec.getVersion(), equalTo(apiSpecDto.getVersion()));
    assertThat(spec.getProjectId(), equalTo(StagedTestData.getProjectByCode(apiSpecDto.getProject()).getId()));
    assertThat(apiSpecDto.getMetadata(), notNullValue());
    assertThat(apiSpecDto.getMetadata().isEmpty(), equalTo(false));
    verifyNameValuePairs(spec.getMetadata(), apiSpecDto.getMetadata());

    for (ApiPath apiPath : spec.getPaths()) {
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
  void apiSpecToApiSpecDto_shallReturnNullIfTheInputIsNull() {
    assertThat(apiSpecMapper.apiSpecDtoToApiSpec(null), nullValue());
  }

  @Test
  void apiSpecDtoToApiSpec() {
    final ApiSpecDto spec = apiSpecMapper.apiSpecToApiSpecDto(apiSpec);
    assertThat(spec.getName(), equalTo(apiSpec.getName()));
    assertThat(spec.getTitle(), equalTo(apiSpec.getTitle()));
    assertThat(spec.getFirstTimeSeen(), equalTo(apiSpec.getFirstTimeSeen()));
    assertThat(spec.getLastSyncTime(), equalTo(apiSpec.getLastSyncTime()));
    assertThat(spec.getVersion(), equalTo(apiSpec.getVersion()));
    assertThat(spec.getProject(), equalTo(StagedTestData.getProject(apiSpec.getProjectId()).getCode()));
    assertThat(apiSpec.getMetadata(), notNullValue());
    assertThat(apiSpec.getMetadata().isEmpty(), equalTo(false));
    verifyNameValuePairs(spec.getMetadata(), apiSpec.getMetadata());

    for (ApiPath apiPath : apiSpec.getPaths()) {
      ApiPathDto pathDto = spec.getPaths().stream().filter(p2 -> apiPath.getUrl().equals(p2.getUrl())).findFirst().orElse(new ApiPathDto());
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
    final ApiSpec spec = ApiSpecBuilder.buildApiSpec(apiSpecDto, projectId);
    spec.setPaths(null);
    assertThat(apiSpecMapper.apiSpecToApiSpecDto(spec), notNullValue());
  }

  @Test
  void apiSpecDtoToApiSpec_shallReturnValueIfOneOfPathIsNull() {
    final ApiSpec spec = ApiSpecBuilder.buildApiSpec(apiSpecDto, projectId);
    spec.getPaths().add(null);
    assertThat(apiSpecMapper.apiSpecToApiSpecDto(spec), notNullValue());
  }

  @Test
  void apiSpecDtoToApiSpec_shallReturnValueIfMetadataIsNull() {
    final ApiSpec spec = ApiSpecBuilder.buildApiSpec(apiSpecDto, projectId);
    spec.setMetadata(null);
    assertThat(apiSpecMapper.apiSpecToApiSpecDto(spec), notNullValue());
  }

  @Test
  void apiSpecDtoToApiSpec_shallReturnValueIfOneOfMetadataIsNull() {
    final ApiSpec spec = ApiSpecBuilder.buildApiSpec(apiSpecDto, projectId);
    spec.getMetadata().add(null);
    assertThat(apiSpecMapper.apiSpecToApiSpecDto(spec), notNullValue());
  }

  @Test
  void apiSpecDtoToApiSpec_shallReturnNullIfTheInputIsNull() {
    assertThat(apiSpecMapper.apiSpecToApiSpecDto(null), nullValue());
  }
}