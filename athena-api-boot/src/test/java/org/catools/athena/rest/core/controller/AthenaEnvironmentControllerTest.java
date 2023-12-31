package org.catools.athena.rest.core.controller;

import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.builder.AthenaCoreBuilder;
import org.junit.jupiter.api.*;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AthenaEnvironmentControllerTest extends AthenaCoreControllerTest {

    private static ProjectDto PROJECT_DTO;
    private static EnvironmentDto ENVIRONMENT_DTO;

    @BeforeAll
    public void beforeAll() {
        ProjectDto project = AthenaCoreBuilder.buildProjectDto();
        athenaProjectController.saveProject(project).getHeaders().getLocation();
        PROJECT_DTO = athenaProjectController.getProjectByCode(project.getCode()).getBody();
        assertThat(PROJECT_DTO, notNullValue());
        ENVIRONMENT_DTO = AthenaCoreBuilder.buildEnvironmentDto(PROJECT_DTO);
    }

    @Test
    @Order(1)
    void saveEnvironment() {
        ResponseEntity<Void> responseEntity = athenaEnvironmentController.saveEnvironment(ENVIRONMENT_DTO);
        URI location = responseEntity.getHeaders().getLocation();
        assertThat(location, notNullValue());
        assertThat(responseEntity.getStatusCode().value(), equalTo(201));
        assertThat(responseEntity.getBody(), nullValue());

        Long id = ResponseEntityUtils.getId(location);
        assertThat(id, notNullValue());
        EnvironmentDto savedEnv = athenaEnvironmentController.getEnvironmentById(id).getBody();
        assertThat(savedEnv, notNullValue());
        assertThat(savedEnv.getCode(), equalTo(ENVIRONMENT_DTO.getCode()));
        assertThat(savedEnv.getName(), equalTo(ENVIRONMENT_DTO.getName()));
    }

    @Test
    @Order(2)
    void doNotSaveEnvironmentTwice() {
        ResponseEntity<Void> responseEntity = athenaEnvironmentController.saveEnvironment(ENVIRONMENT_DTO);
        URI location = responseEntity.getHeaders().getLocation();
        assertThat(location, notNullValue());
        assertThat(responseEntity.getStatusCode().value(), equalTo(208));
        assertThat(responseEntity.getBody(), nullValue());
    }

    @Test
    @Order(2)
    void getEnvironment() {
        ResponseEntity<EnvironmentDto> response = athenaEnvironmentController.getEnvironmentByName(ENVIRONMENT_DTO.getCode());
        assertThat(response.getStatusCode().value(), equalTo(200));
        assertThat(response.getBody(), notNullValue());
        assertThat(response.getBody().getCode(), equalTo(ENVIRONMENT_DTO.getCode()));
        assertThat(response.getBody().getName(), equalTo(ENVIRONMENT_DTO.getName()));
        assertThat(response.getBody().getProjectCode(), equalTo(PROJECT_DTO.getCode()));
    }

    @Test
    @Order(3)
    void getEnvironments() {
        ResponseEntity<Set<EnvironmentDto>> response = athenaEnvironmentController.getEnvironments();
        assertThat(response.getStatusCode().value(), equalTo(200));
        assertThat(response.getBody(), notNullValue());
        EnvironmentDto environmentDto = response.getBody().stream().filter(p -> p.getCode().equals(ENVIRONMENT_DTO.getCode())).findFirst().orElse(null);
        assertThat(environmentDto, notNullValue());
        assertThat(environmentDto.getCode(), equalTo(ENVIRONMENT_DTO.getCode()));
        assertThat(environmentDto.getName(), equalTo(ENVIRONMENT_DTO.getName()));
        assertThat(environmentDto.getProjectCode(), equalTo(PROJECT_DTO.getCode()));
    }
}