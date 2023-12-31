package org.catools.athena.rest.pipeline.controller;

import org.assertj.core.util.DateUtil;
import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.pipeline.model.PipelineDto;
import org.catools.athena.pipeline.model.PipelineExecutionDto;
import org.catools.athena.pipeline.model.PipelineExecutionStatusDto;
import org.catools.athena.pipeline.model.PipelineScenarioExecutionDto;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.builder.AthenaCoreBuilder;
import org.catools.athena.rest.core.controller.AthenaCoreControllerTest;
import org.catools.athena.rest.pipeline.builder.AthenaPipelineBuilder;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AthenaPipelineControllerTest extends AthenaCoreControllerTest {

    private static PipelineDto PIPELINE_DTO;
    private static PipelineExecutionStatusDto STATUS_DTO;

    @BeforeAll
    public void beforeAll() {
        ProjectDto projectDto = AthenaCoreBuilder.buildProjectDto();
        URI location = athenaProjectController.saveProject(projectDto).getHeaders().getLocation();
        assertThat(location, notNullValue());
        EnvironmentDto environmentDto = AthenaCoreBuilder.buildEnvironmentDto(projectDto);
        location = athenaEnvironmentController.saveEnvironment(environmentDto).getHeaders().getLocation();
        assertThat(location, notNullValue());
        PIPELINE_DTO = AthenaPipelineBuilder.buildPipelineDto(environmentDto);
        STATUS_DTO = AthenaPipelineBuilder.buildPipelineExecutionStatusDto();
    }

    @Test
    @Order(9)
    void savePipeline() {
        ResponseEntity<Void> responseEntity = athenaPipelineController.savePipeline(PIPELINE_DTO);

        URI location = responseEntity.getHeaders().getLocation();
        assertThat(location, notNullValue());
        assertThat(responseEntity.getStatusCode().value(), Matchers.equalTo(201));
        assertThat(responseEntity.getBody(), nullValue());


        Long id = ResponseEntityUtils.getId(location);
        assertThat(id, notNullValue());
        PipelineDto savedPipeline = athenaPipelineController.getPipelineById(id).getBody();
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
        Date enddate = new Date();
        ResponseEntity<PipelineDto> pipeline = athenaPipelineController.getPipeline(PIPELINE_DTO.getName(), PIPELINE_DTO.getNumber(), PIPELINE_DTO.getEnvironmentCode());
        assertThat(pipeline, notNullValue());
        PipelineDto body = pipeline.getBody();
        assertThat(body, notNullValue());
        Long pipelineId = body.getId();
        ResponseEntity<PipelineDto> response = athenaPipelineController.updatePipelineEndDate(pipelineId, enddate);
        assertThat(response.getStatusCode().value(), equalTo(200));
        assertThat(response.getBody(), notNullValue());
        assertThat(response.getBody().getName(), equalTo(PIPELINE_DTO.getName()));
        assertThat(response.getBody().getNumber(), equalTo(PIPELINE_DTO.getNumber()));
        assertThat(DateUtil.formatAsDatetime(response.getBody().getEndDate()), equalTo(DateUtil.formatAsDatetime(enddate)));
    }

    @Test
    @Order(11)
    void getPipeline() {
        ResponseEntity<PipelineDto> response = athenaPipelineController.getPipeline(PIPELINE_DTO.getName(), PIPELINE_DTO.getNumber(), PIPELINE_DTO.getEnvironmentCode());
        assertThat(response.getStatusCode().value(), equalTo(200));
        PipelineDto pipeline = response.getBody();
        assertThat(pipeline, notNullValue());
        assertThat(pipeline.getName(), equalTo(PIPELINE_DTO.getName()));
        assertThat(pipeline.getNumber(), equalTo(PIPELINE_DTO.getNumber()));
        assertThat(pipeline.getName(), equalTo(PIPELINE_DTO.getName()));
        assertThat(pipeline.getDescription(), equalTo(PIPELINE_DTO.getDescription()));
        assertThat(pipeline.getNumber(), equalTo(PIPELINE_DTO.getNumber()));
        assertThat(DateUtil.formatAsDatetime(pipeline.getStartDate()), equalTo(DateUtil.formatAsDatetime(PIPELINE_DTO.getStartDate())));
        assertThat(DateUtil.formatAsDatetime(pipeline.getEndDate()), equalTo(DateUtil.formatAsDatetime(PIPELINE_DTO.getEndDate())));
        assertThat(pipeline.getEnvironmentCode(), equalTo(PIPELINE_DTO.getEnvironmentCode()));

        List<MetadataDto> expectedList = PIPELINE_DTO.getMetadata().stream().toList();

        verifyMetadata(pipeline, expectedList.get(0));
        verifyMetadata(pipeline, expectedList.get(1));

        // we need this for next 2 testcases
        PIPELINE_DTO = response.getBody();
    }

    @Test
    @Order(12)
    void saveExecutionStatus() {
        ResponseEntity<Void> responseEntity = athenaPipelineController.saveExecutionStatus(STATUS_DTO);
        assertThat(responseEntity.getStatusCode().value(), equalTo(201));

        URI location = responseEntity.getHeaders().getLocation();
        assertThat(location, notNullValue());
        assertThat(responseEntity.getStatusCode().value(), Matchers.equalTo(201));
        assertThat(responseEntity.getBody(), nullValue());

        Long id = ResponseEntityUtils.getId(location);
        assertThat(id, notNullValue());
        PipelineExecutionStatusDto savedStatus = athenaPipelineController.getExecutionStatusById(id).getBody();
        assertThat(savedStatus, notNullValue());
        assertThat(savedStatus.getName(), Matchers.equalTo(STATUS_DTO.getName()));
    }

    @Test
    @Order(12)
    void getExecutionStatus() {
        PipelineExecutionStatusDto pipelineStatus = athenaPipelineController.getExecutionStatusByName(STATUS_DTO.getName()).getBody();
        assertThat(pipelineStatus, notNullValue());
        assertThat(pipelineStatus.getName(), equalTo(STATUS_DTO.getName()));
    }

    @Test
    @Order(12)
    void getExecutionStatuses() {
        Set<PipelineExecutionStatusDto> pipelineStatuses = athenaPipelineController.getExecutionStatuses().getBody();
        assertThat(pipelineStatuses, notNullValue());
        PipelineExecutionStatusDto pipelineStatus = pipelineStatuses.stream().filter(s -> s.getName().equals(STATUS_DTO.getName())).findFirst().orElse(null);
        assertThat(pipelineStatus, notNullValue());
        assertThat(pipelineStatus.getName(), equalTo(STATUS_DTO.getName()));
    }

    @Test
    @Order(12)
    void saveExecution() {
        PipelineExecutionStatusDto pipelineStatus = AthenaPipelineBuilder.buildPipelineExecutionStatusDto();
        athenaPipelineController.saveExecutionStatus(pipelineStatus).getHeaders().getLocation();
        assertThat(pipelineStatus, notNullValue());

        UserDto user = AthenaCoreBuilder.buildUserDto();
        athenaUserController.saveUser(user);
        assertThat(user, notNullValue());

        PipelineExecutionDto executionDto = AthenaPipelineBuilder.buildExecutionDto(PIPELINE_DTO, pipelineStatus, user);
        ResponseEntity<Void> responseEntity = athenaPipelineController.saveExecution(executionDto);
        URI location = responseEntity.getHeaders().getLocation();
        assertThat(location, notNullValue());
        assertThat(responseEntity.getStatusCode().value(), Matchers.equalTo(201));
        assertThat(responseEntity.getBody(), nullValue());
    }

    @Test
    @Order(12)
    void saveScenarioExecution() {
        PipelineExecutionStatusDto pipelineStatus = AthenaPipelineBuilder.buildPipelineExecutionStatusDto();
        athenaPipelineController.saveExecutionStatus(pipelineStatus).getHeaders().getLocation();
        assertThat(pipelineStatus, notNullValue());

        UserDto user = AthenaCoreBuilder.buildUserDto();
        athenaUserController.saveUser(user);
        assertThat(user, notNullValue());

        PipelineScenarioExecutionDto executionDto = AthenaPipelineBuilder.buildScenarioExecutionDto(PIPELINE_DTO, pipelineStatus, user);
        ResponseEntity<Void> responseEntity = athenaPipelineController.saveScenarioExecution(executionDto);
        URI location = responseEntity.getHeaders().getLocation();
        assertThat(location, notNullValue());
        assertThat(responseEntity.getStatusCode().value(), Matchers.equalTo(201));
        assertThat(responseEntity.getBody(), nullValue());
    }
}