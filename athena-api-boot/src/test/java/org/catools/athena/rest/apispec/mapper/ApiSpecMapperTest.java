package org.catools.athena.rest.apispec.mapper;

import org.catools.athena.apispec.model.ApiParameterDto;
import org.catools.athena.apispec.model.ApiPathDto;
import org.catools.athena.apispec.model.ApiSpecDto;
import org.catools.athena.apispec.model.NameTypePair;
import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.core.model.NameValuePair;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.rest.AthenaBaseTest;
import org.catools.athena.rest.apispec.builder.ApiSpecBuilder;
import org.catools.athena.rest.apispec.entity.*;
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

    private static ApiPathDto API_PATH_DTO;
    private static ApiPath API_PATH;

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
        API_SPEC.setId(apiSpecService.saveApiSpec(API_SPEC_DTO).getId());

        API_PATH_DTO = ApiSpecBuilder.buildApiPathDto(API_SPEC);
        API_PATH = ApiSpecBuilder.buildApiPath(API_PATH_DTO, API_SPEC);
        API_PATH.setId(apiSpecService.saveApiPath(API_PATH_DTO).getId());
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
        for (NameValuePair metadata : API_SPEC_DTO.getMetadata()) {
            verifyNameValuePair(apiSpec.getMetadata(), metadata);
        }
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
        for (ApiSpecMetadata metadata : API_SPEC.getMetadata()) {
            verifyNameValuePair(apiSpecDto.getMetadata(), metadata);
        }
    }

    @Test
    void apiPathToApiPathDto() {
        final ApiPath apiPath = apiSpecMapper.apiPathDtoToApiPath(API_PATH_DTO);
        assertThat(apiPath.getTitle(), equalTo(API_PATH_DTO.getTitle()));
        assertThat(apiPath.getUrl(), equalTo(API_PATH_DTO.getUrl()));
        assertThat(apiPath.getApiSpec().getId(), equalTo(API_PATH_DTO.getApiSpecId()));
        assertThat(apiPath.getDescription(), equalTo(API_PATH_DTO.getDescription()));
        assertThat(apiPath.getMethod(), equalTo(API_PATH_DTO.getMethod()));

        assertThat(API_PATH_DTO.getParameters(), notNullValue());
        assertThat(API_PATH_DTO.getParameters().isEmpty(), equalTo(false));
        for (ApiParameterDto parameterDto : API_PATH_DTO.getParameters()) {
            verifyParameter(apiPath.getParameters(), parameterDto);
        }

        assertThat(API_PATH_DTO.getMetadata(), notNullValue());
        assertThat(API_PATH_DTO.getMetadata().isEmpty(), equalTo(false));
        for (MetadataDto nameValuePair : API_PATH_DTO.getMetadata()) {
            verifyNameValuePair(apiPath.getMetadata(), nameValuePair);
        }
    }

    @Test
    void apiPathDtoToApiPath() {
        final ApiPathDto apiPathDto = apiSpecMapper.apiPathToApiPathDto(API_PATH);
        assertThat(apiPathDto.getTitle(), equalTo(API_PATH.getTitle()));
        assertThat(apiPathDto.getUrl(), equalTo(API_PATH.getUrl()));
        assertThat(apiPathDto.getApiSpecId(), equalTo(API_PATH.getApiSpec().getId()));
        assertThat(apiPathDto.getDescription(), equalTo(API_PATH.getDescription()));
        assertThat(apiPathDto.getMethod(), equalTo(API_PATH.getMethod()));

        assertThat(API_PATH.getParameters(), notNullValue());
        assertThat(API_PATH.getParameters().isEmpty(), equalTo(false));
        for (ApiParameter parameterDto : API_PATH.getParameters()) {
            verifyParameter(apiPathDto.getParameters(), parameterDto);
        }

        assertThat(API_PATH.getMetadata(), notNullValue());
        assertThat(API_PATH.getMetadata().isEmpty(), equalTo(false));
        for (ApiPathMetadata metadataDto : API_PATH.getMetadata()) {
            verifyNameValuePair(apiPathDto.getMetadata(), metadataDto);
        }
    }

    protected static <T1 extends NameTypePair, T2 extends NameTypePair> void verifyParameter(Collection<T1> actuals, T2 expected) {
        T1 actual = actuals.stream().filter(m -> Objects.equals(m.getName(), expected.getName())).findFirst().orElse(null);
        assertThat(actual, Matchers.notNullValue());

        assertThat(actual.getName(), equalTo(expected.getName()));
        assertThat(actual.getType(), equalTo(expected.getType()));
    }

}