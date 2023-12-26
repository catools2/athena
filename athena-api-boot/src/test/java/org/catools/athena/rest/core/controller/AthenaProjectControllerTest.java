package org.catools.athena.rest.core.controller;

import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.rest.core.builder.AthenaCoreBuilder;
import org.junit.jupiter.api.*;
import org.springframework.http.ResponseEntity;

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
        ResponseEntity<ProjectDto> response = athenaProjectController.saveProject(PROJECT_DTO);
        assertThat(response.getStatusCode().value(), equalTo(200));
        assertThat(response.getBody(), notNullValue());
        assertThat(response.getBody().getId(), greaterThanOrEqualTo(1L));
    }

    @Test
    @Order(2)
    void doNotSaveProjectTwice() {
        ResponseEntity<ProjectDto> response = athenaProjectController.saveProject(PROJECT_DTO);
        assertThat(response.getStatusCode().value(), equalTo(208));
        assertThat(response.getBody(), notNullValue());
        assertThat(response.getBody().getId(), greaterThanOrEqualTo(1L));
    }

    @Test
    @Order(3)
    void getProject() {
        ResponseEntity<ProjectDto> response = athenaProjectController.getProject(PROJECT_DTO.getCode());
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