package org.catools.athena.rest.core.controller;

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
public class AthenaProjectControllerTest extends AthenaCoreControllerTest {

    private static ProjectDto PROJECT_DTO;

    @BeforeAll
    public void beforeAll() {
        PROJECT_DTO = AthenaCoreBuilder.buildProjectDto();
    }

    @Test
    @Order(1)
    void saveProject() {
        ResponseEntity<Void> responseEntity = athenaProjectController.saveProject(PROJECT_DTO);
        URI location = responseEntity.getHeaders().getLocation();
        assertThat(location, notNullValue());
        assertThat(responseEntity.getStatusCode().value(), equalTo(201));
        assertThat(responseEntity.getBody(), nullValue());

        Long id = ResponseEntityUtils.getId(location);
        assertThat(id, notNullValue());
        ProjectDto savedProject = athenaProjectController.getProjectById(id).getBody();
        assertThat(savedProject, notNullValue());
        assertThat(savedProject.getCode(), equalTo(PROJECT_DTO.getCode()));
        assertThat(savedProject.getName(), equalTo(PROJECT_DTO.getName()));
    }

    @Test
    @Order(2)
    void doNotSaveProjectTwice() {
        ResponseEntity<Void> responseEntity = athenaProjectController.saveProject(PROJECT_DTO);
        URI location = responseEntity.getHeaders().getLocation();
        assertThat(location, notNullValue());
        assertThat(responseEntity.getStatusCode().value(), equalTo(208));
        assertThat(responseEntity.getBody(), nullValue());
    }

    @Test
    @Order(3)
    void getProject() {
        ResponseEntity<ProjectDto> response = athenaProjectController.getProjectByCode(PROJECT_DTO.getCode());
        assertThat(response.getStatusCode().value(), equalTo(200));
        assertThat(response.getBody(), notNullValue());
        assertThat(response.getBody().getCode(), equalTo(PROJECT_DTO.getCode()));
        assertThat(response.getBody().getName(), equalTo(PROJECT_DTO.getName()));
    }

    @Test
    @Order(4)
    void getProjects() {
        ResponseEntity<Set<ProjectDto>> response = athenaProjectController.getProjects();
        assertThat(response.getStatusCode().value(), equalTo(200));
        assertThat(response.getBody(), notNullValue());
        ProjectDto projectDto = response.getBody().stream().filter(p -> p.getCode().equals(PROJECT_DTO.getCode())).findFirst().orElse(null);
        assertThat(projectDto, notNullValue());
        assertThat(projectDto.getCode(), equalTo(PROJECT_DTO.getCode()));
        assertThat(projectDto.getName(), equalTo(PROJECT_DTO.getName()));
    }
}