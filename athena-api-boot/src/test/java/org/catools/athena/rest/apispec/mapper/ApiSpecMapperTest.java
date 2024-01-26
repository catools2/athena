package org.catools.athena.rest.apispec.mapper;

import org.catools.athena.apispec.model.ApiParameterDto;
import org.catools.athena.apispec.model.ApiPathDto;
import org.catools.athena.apispec.model.ApiSpecDto;
import org.catools.athena.apispec.model.NameTypePair;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.rest.AthenaBaseTest;
import org.catools.athena.rest.apispec.builder.ApiSpecBuilder;
import org.catools.athena.rest.apispec.entity.ApiParameter;
import org.catools.athena.rest.apispec.entity.ApiPath;
import org.catools.athena.rest.apispec.entity.ApiSpec;
import org.catools.athena.rest.apispec.service.ApiSpecService;
import org.catools.athena.rest.core.builder.CoreBuilder;
import org.catools.athena.rest.core.entity.Project;
import org.catools.athena.rest.core.service.ProjectService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

class ApiSpecMapperTest extends AthenaBaseTest {

  private static ApiSpecDto API_SPEC_DTO;
  private static ApiSpec API_SPEC;

  private static ApiPathDto API_DTO;
  private static ApiPath API;

  @Autowired
  ApiSpecMapper apiSpecMapper;

  @Autowired
  ProjectService projectService;

  @Autowired
  ApiSpecService apiSpecService;

  @BeforeAll
  public void beforeAll() {
    final ProjectDto projectDto = CoreBuilder.buildProjectDto();
    projectDto.setId(projectService.save(projectDto).getId());
    final Project project = CoreBuilder.buildProject(projectDto);

    API_SPEC_DTO = ApiSpecBuilder.buildApiSpecDto(project.getCode());
    API_SPEC = ApiSpecBuilder.buildApiSpec(API_SPEC_DTO, project);
    API_SPEC.setId(apiSpecService.save(API_SPEC_DTO).getId());

    API_DTO = ApiSpecBuilder.buildApiPathDto(API_SPEC);
    API = ApiSpecBuilder.buildApiPath(API_DTO, API_SPEC);
    API.setId(apiSpecService.saveApiPath(API_DTO).getId());
  }

  @Test
  void apiSpecToApiSpecDto() {
    final ApiSpec apiSpec = apiSpecMapper.apiSpecDtoToApiSpec(API_SPEC_DTO);
    assertThat(apiSpec.getName(), equalTo(API_SPEC_DTO.getName()));
    assertThat(apiSpec.getTitle(), equalTo(API_SPEC_DTO.getTitle()));
    assertThat(apiSpec.getFirstTimeSeen(), equalTo(API_SPEC_DTO.getFirstTimeSeen()));
    assertThat(apiSpec.getLastTimeSeen(), equalTo(API_SPEC_DTO.getLastTimeSeen()));
    assertThat(apiSpec.getVersion(), equalTo(API_SPEC_DTO.getVersion()));
    assertThat(apiSpec.getProject().getCode(), equalTo(API_SPEC_DTO.getProject()));
    assertThat(API_SPEC_DTO.getMetadata(), notNullValue());
    assertThat(API_SPEC_DTO.getMetadata().isEmpty(), equalTo(false));
    verifyNameValuePairs(apiSpec.getMetadata(), API_SPEC_DTO.getMetadata());
  }

  @Test
  void apiSpecDtoToApiSpec() {
    final ApiSpecDto apiSpecDto = apiSpecMapper.apiSpecToApiSpecDto(API_SPEC);
    assertThat(apiSpecDto.getName(), equalTo(API_SPEC.getName()));
    assertThat(apiSpecDto.getTitle(), equalTo(API_SPEC.getTitle()));
    assertThat(apiSpecDto.getFirstTimeSeen(), equalTo(API_SPEC.getFirstTimeSeen()));
    assertThat(apiSpecDto.getLastTimeSeen(), equalTo(API_SPEC.getLastTimeSeen()));
    assertThat(apiSpecDto.getVersion(), equalTo(API_SPEC.getVersion()));
    assertThat(apiSpecDto.getProject(), equalTo(API_SPEC.getProject().getCode()));
    assertThat(API_SPEC.getMetadata(), notNullValue());
    assertThat(API_SPEC.getMetadata().isEmpty(), equalTo(false));
    verifyNameValuePairs(apiSpecDto.getMetadata(), API_SPEC.getMetadata());
  }

  @Test
  void apiPathToApiPathDto() {
    final ApiPath apiPath = apiSpecMapper.apiPathDtoToApiPath(API_DTO);
    assertThat(apiPath.getTitle(), equalTo(API_DTO.getTitle()));
    assertThat(apiPath.getUrl(), equalTo(API_DTO.getUrl()));
    assertThat(apiPath.getApiSpec().getId(), equalTo(API_DTO.getApiSpecId()));
    assertThat(apiPath.getDescription(), equalTo(API_DTO.getDescription()));
    assertThat(apiPath.getMethod(), equalTo(API_DTO.getMethod()));

    assertThat(API_DTO.getParameters(), notNullValue());
    assertThat(API_DTO.getParameters().isEmpty(), equalTo(false));
    for (ApiParameterDto parameterDto : API_DTO.getParameters()) {
      verifyParameter(apiPath.getParameters(), parameterDto);
    }

    assertThat(API_DTO.getMetadata(), notNullValue());
    assertThat(API_DTO.getMetadata().isEmpty(), equalTo(false));
    verifyNameValuePairs(apiPath.getMetadata(), API_DTO.getMetadata());
  }

  @Test
  void apiPathDtoToApiPath() {
    final ApiPathDto apiPathDto = apiSpecMapper.apiPathToApiPathDto(API);
    assertThat(apiPathDto.getTitle(), equalTo(API.getTitle()));
    assertThat(apiPathDto.getUrl(), equalTo(API.getUrl()));
    assertThat(apiPathDto.getApiSpecId(), equalTo(API.getApiSpec().getId()));
    assertThat(apiPathDto.getDescription(), equalTo(API.getDescription()));
    assertThat(apiPathDto.getMethod(), equalTo(API.getMethod()));

    assertThat(API.getParameters(), notNullValue());
    assertThat(API.getParameters().isEmpty(), equalTo(false));
    for (ApiParameter parameterDto : API.getParameters()) {
      verifyParameter(apiPathDto.getParameters(), parameterDto);
    }

    assertThat(API.getMetadata(), notNullValue());
    assertThat(API.getMetadata().isEmpty(), equalTo(false));
    verifyNameValuePairs(apiPathDto.getMetadata(), API.getMetadata());
  }

  protected static <T1 extends NameTypePair, T2 extends NameTypePair> void verifyParameter(Collection<T1> actuals, T2 expected) {
    T1 actual = actuals.stream().filter(m -> Objects.equals(m.getName(), expected.getName())).findFirst().orElse(null);
    assertThat(actual, Matchers.notNullValue());

    assertThat(actual.getName(), equalTo(expected.getName()));
    assertThat(actual.getType(), equalTo(expected.getType()));
  }

}