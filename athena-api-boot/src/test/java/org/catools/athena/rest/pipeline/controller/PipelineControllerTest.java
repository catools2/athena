package org.catools.athena.rest.pipeline.controller;

import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.pipeline.model.PipelineDto;
import org.catools.athena.pipeline.model.PipelineExecutionDto;
import org.catools.athena.pipeline.model.PipelineExecutionStatusDto;
import org.catools.athena.pipeline.model.PipelineScenarioExecutionDto;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.builder.CoreBuilder;
import org.catools.athena.rest.core.controller.CoreControllerTest;
import org.catools.athena.rest.pipeline.builder.PipelineBuilder;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PipelineControllerTest extends CoreControllerTest {

    private static PipelineDto PIPELINE_DTO;
    private static PipelineExecutionStatusDto STATUS_DTO;

    @BeforeAll
    public void beforeAll() {
        ProjectDto projectDto = CoreBuilder.buildProjectDto();
        URI location = projectController.saveProject(projectDto).getHeaders().getLocation();
        assertThat(location, notNullValue());
        EnvironmentDto environmentDto = CoreBuilder.buildEnvironmentDto(projectDto);
        location = environmentController.saveEnvironment(environmentDto).getHeaders().getLocation();
        assertThat(location, notNullValue());
        PIPELINE_DTO = PipelineBuilder.buildPipelineDto(environmentDto);
        STATUS_DTO = PipelineBuilder.buildPipelineExecutionStatusDto();
    }

    @Test
    @Order(9)
    void savePipeline() {
        ResponseEntity<Void> responseEntity = pipelineController.savePipeline(PIPELINE_DTO);

        URI location = responseEntity.getHeaders().getLocation();
        assertThat(location, notNullValue());
        assertThat(responseEntity.getStatusCode().value(), Matchers.equalTo(201));
        assertThat(responseEntity.getBody(), nullValue());


        Long id = ResponseEntityUtils.getId(location);
        assertThat(id, notNullValue());
        PipelineDto savedPipeline = pipelineController.getPipelineById(id).getBody();
        assertThat(savedPipeline, notNullValue());
        assertThat(savedPipeline.getNumber(), Matchers.equalTo(PIPELINE_DTO.getNumber()));
        assertThat(savedPipeline.getName(), Matchers.equalTo(PIPELINE_DTO.getName()));
        assertThat(savedPipeline.getDescription(), Matchers.equalTo(PIPELINE_DTO.getDescription()));
    }

    @Rollback
    @Transactional
    @Test
    @Order(10)
    void updatePipelineEndDate() {
        LocalDateTime enddate = LocalDateTime.now();
        ResponseEntity<PipelineDto> pipeline = pipelineController.getPipeline(PIPELINE_DTO.getName(), PIPELINE_DTO.getNumber(), PIPELINE_DTO.getEnvironmentCode());
        assertThat(pipeline, notNullValue());
        PipelineDto body = pipeline.getBody();
        assertThat(body, notNullValue());
        Long pipelineId = body.getId();
        ResponseEntity<PipelineDto> response = pipelineController.updatePipelineEndDate(pipelineId, enddate);
        assertThat(response.getStatusCode().value(), equalTo(200));
        assertThat(response.getBody(), notNullValue());
        assertThat(response.getBody().getName(), equalTo(PIPELINE_DTO.getName()));
        assertThat(response.getBody().getNumber(), equalTo(PIPELINE_DTO.getNumber()));
        assertThat(response.getBody().getEndDate().format(DateTimeFormatter.ISO_DATE_TIME), equalTo(enddate.format(DateTimeFormatter.ISO_DATE_TIME)));
    }

    @Test
    @Order(11)
    void getPipeline() {
        ResponseEntity<PipelineDto> response = pipelineController.getPipeline(PIPELINE_DTO.getName(), PIPELINE_DTO.getNumber(), PIPELINE_DTO.getEnvironmentCode());
        assertThat(response.getStatusCode().value(), equalTo(200));
        PipelineDto pipeline = response.getBody();
        assertThat(pipeline, notNullValue());
        assertThat(pipeline.getName(), equalTo(PIPELINE_DTO.getName()));
        assertThat(pipeline.getNumber(), equalTo(PIPELINE_DTO.getNumber()));
        assertThat(pipeline.getName(), equalTo(PIPELINE_DTO.getName()));
        assertThat(pipeline.getDescription(), equalTo(PIPELINE_DTO.getDescription()));
        assertThat(pipeline.getNumber(), equalTo(PIPELINE_DTO.getNumber()));
        assertThat(pipeline.getStartDate().format(DateTimeFormatter.ISO_DATE_TIME), equalTo(pipeline.getStartDate().format(DateTimeFormatter.ISO_DATE_TIME)));
        assertThat(pipeline.getEndDate().format(DateTimeFormatter.ISO_DATE_TIME), equalTo(pipeline.getEndDate().format(DateTimeFormatter.ISO_DATE_TIME)));

        assertThat(pipeline.getEnvironmentCode(), equalTo(PIPELINE_DTO.getEnvironmentCode()));

        List<MetadataDto> expectedList = PIPELINE_DTO.getMetadata().stream().toList();

        verifyNameValuePair(pipeline.getMetadata(), expectedList.get(0));
        verifyNameValuePair(pipeline.getMetadata(), expectedList.get(1));

        // we need this for next 2 testcases
        PIPELINE_DTO = response.getBody();
    }

    @Test
    @Order(12)
    void saveExecutionStatus() {
        ResponseEntity<Void> responseEntity = pipelineController.saveExecutionStatus(STATUS_DTO);
        assertThat(responseEntity.getStatusCode().value(), equalTo(201));

        URI location = responseEntity.getHeaders().getLocation();
        assertThat(location, notNullValue());
        assertThat(responseEntity.getStatusCode().value(), Matchers.equalTo(201));
        assertThat(responseEntity.getBody(), nullValue());

        Long id = ResponseEntityUtils.getId(location);
        assertThat(id, notNullValue());
        PipelineExecutionStatusDto savedStatus = pipelineController.getExecutionStatusById(id).getBody();
        assertThat(savedStatus, notNullValue());
        assertThat(savedStatus.getName(), Matchers.equalTo(STATUS_DTO.getName()));
    }

    @Test
    @Order(12)
    void getExecutionStatus() {
        PipelineExecutionStatusDto pipelineStatus = pipelineController.getExecutionStatusByName(STATUS_DTO.getName()).getBody();
        assertThat(pipelineStatus, notNullValue());
        assertThat(pipelineStatus.getName(), equalTo(STATUS_DTO.getName()));
    }

    @Test
    @Order(12)
    void getExecutionStatuses() {
        Set<PipelineExecutionStatusDto> pipelineStatuses = pipelineController.getExecutionStatuses().getBody();
        assertThat(pipelineStatuses, notNullValue());
        PipelineExecutionStatusDto pipelineStatus = pipelineStatuses.stream().filter(s -> s.getName().equals(STATUS_DTO.getName())).findFirst().orElse(null);
        assertThat(pipelineStatus, notNullValue());
        assertThat(pipelineStatus.getName(), equalTo(STATUS_DTO.getName()));
    }

    @Test
    @Order(12)
    void saveExecution() {
        PipelineExecutionStatusDto pipelineStatus = PipelineBuilder.buildPipelineExecutionStatusDto();
        pipelineController.saveExecutionStatus(pipelineStatus);
        assertThat(pipelineStatus, notNullValue());

        UserDto user = CoreBuilder.buildUserDto();
        userController.saveUser(user);
        assertThat(user, notNullValue());

        PipelineExecutionDto executionDto = PipelineBuilder.buildExecutionDto(PIPELINE_DTO, pipelineStatus, user);
        ResponseEntity<Void> responseEntity = pipelineController.saveExecution(executionDto);
        URI location = responseEntity.getHeaders().getLocation();
        assertThat(location, notNullValue());
        assertThat(responseEntity.getStatusCode().value(), Matchers.equalTo(201));
        assertThat(responseEntity.getBody(), nullValue());

        String[] split = location.getPath().split("/");
        ResponseEntity<PipelineExecutionDto> executionById = pipelineController.getExecutionById(Long.valueOf(split[split.length - 1]));
        assertThat(executionById.getBody(), notNullValue());
    }

    @Test
    @Order(12)
    void saveScenarioExecution() {
        PipelineExecutionStatusDto pipelineStatus = PipelineBuilder.buildPipelineExecutionStatusDto();
        pipelineController.saveExecutionStatus(pipelineStatus);
        assertThat(pipelineStatus, notNullValue());

        UserDto user = CoreBuilder.buildUserDto();
        userController.saveUser(user);
        assertThat(user, notNullValue());

        PipelineScenarioExecutionDto executionDto = PipelineBuilder.buildScenarioExecutionDto(PIPELINE_DTO, pipelineStatus, user);
        ResponseEntity<Void> responseEntity = pipelineController.saveScenarioExecution(executionDto);
        URI location = responseEntity.getHeaders().getLocation();
        assertThat(location, notNullValue());
        assertThat(responseEntity.getStatusCode().value(), Matchers.equalTo(201));
        assertThat(responseEntity.getBody(), nullValue());

        String[] split = location.getPath().split("/");
        ResponseEntity<PipelineScenarioExecutionDto> scenarioExecutionById = pipelineController.getScenarioExecutionById(Long.valueOf(split[split.length - 1]));
        assertThat(scenarioExecutionById.getBody(), notNullValue());
    }
}