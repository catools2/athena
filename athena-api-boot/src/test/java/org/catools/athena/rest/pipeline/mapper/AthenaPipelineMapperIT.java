package org.catools.athena.rest.pipeline.mapper;

import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.pipeline.model.PipelineDto;
import org.catools.athena.pipeline.model.PipelineExecutionDto;
import org.catools.athena.pipeline.model.PipelineExecutionStatusDto;
import org.catools.athena.pipeline.model.PipelineScenarioExecutionDto;
import org.catools.athena.rest.AthenaBaseTest;
import org.catools.athena.rest.core.builder.AthenaCoreBuilder;
import org.catools.athena.rest.core.entity.Environment;
import org.catools.athena.rest.core.entity.Project;
import org.catools.athena.rest.core.entity.User;
import org.catools.athena.rest.core.service.AthenaCoreService;
import org.catools.athena.rest.pipeline.builder.AthenaPipelineBuilder;
import org.catools.athena.rest.pipeline.entity.*;
import org.catools.athena.rest.pipeline.service.AthenaPipelineService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;


public class AthenaPipelineMapperIT extends AthenaBaseTest {
    private static PipelineDto PIPELINE_DTO;
    private static Pipeline PIPELINE;
    private static PipelineExecutionDto PIPELINE_EXECUTION_DTO;
    private static PipelineExecution PIPELINE_EXECUTION;
    private static PipelineScenarioExecutionDto PIPELINE_SCENARIO_EXECUTION_DTO;
    private static PipelineScenarioExecution PIPELINE_SCENARIO_EXECUTION;

    @Autowired
    AthenaPipelineMapper pipelineMapper;

    @Autowired
    AthenaCoreService athenaCoreService;

    @Autowired
    AthenaPipelineService athenaPipelineService;

    @BeforeAll
    public void beforeAll() {
        UserDto userDto = AthenaCoreBuilder.buildUserDto();
        userDto.setId(athenaCoreService.saveUser(userDto).getId());
        User user = AthenaCoreBuilder.buildUser(userDto);

        ProjectDto projectDto = AthenaCoreBuilder.buildProjectDto();
        projectDto.setId(athenaCoreService.saveProject(projectDto).getId());
        Project project = AthenaCoreBuilder.buildProject(projectDto);

        PipelineExecutionStatusDto statusDto = AthenaPipelineBuilder.buildPipelineExecutionStatusDto();
        statusDto.setId(athenaPipelineService.saveExecutionStatus(statusDto).getId());
        PipelineExecutionStatus STATUS = AthenaPipelineBuilder.buildPipelineExecutionStatus(statusDto);

        EnvironmentDto environmentDto = AthenaCoreBuilder.buildEnvironmentDto(projectDto);
        environmentDto.setId(athenaCoreService.saveEnvironment(environmentDto).getId());
        Environment environment = AthenaCoreBuilder.buildEnvironment(environmentDto, project);

        PIPELINE_DTO = AthenaPipelineBuilder.buildPipelineDto(environmentDto);
        PIPELINE_DTO.setId(athenaPipelineService.savePipeline(PIPELINE_DTO).getId());
        PIPELINE = AthenaPipelineBuilder.buildPipeline(PIPELINE_DTO, environment);

        PIPELINE_EXECUTION_DTO = AthenaPipelineBuilder.buildExecutionDto(PIPELINE_DTO, statusDto, userDto);
        PIPELINE_EXECUTION_DTO.setPipelineId(athenaPipelineService.saveExecution(PIPELINE_EXECUTION_DTO).getId());
        PIPELINE_EXECUTION = AthenaPipelineBuilder.buildExecution(PIPELINE_EXECUTION_DTO, PIPELINE, STATUS, user);

        PIPELINE_SCENARIO_EXECUTION_DTO = AthenaPipelineBuilder.buildScenarioExecutionDto(PIPELINE_DTO, statusDto, userDto);
        PIPELINE_SCENARIO_EXECUTION_DTO.setId(athenaPipelineService.saveScenarioExecution(PIPELINE_SCENARIO_EXECUTION_DTO).getId());
        PIPELINE_SCENARIO_EXECUTION = AthenaPipelineBuilder.buildScenarioExecution(PIPELINE_SCENARIO_EXECUTION_DTO, PIPELINE, STATUS, user);
    }

    @Test
    public void executionToExecutionDto() {
        final PipelineExecutionDto pipelineExecutionDto = pipelineMapper.executionToExecutionDto(PIPELINE_EXECUTION);
        assertThat(pipelineExecutionDto.getId(), equalTo(PIPELINE_EXECUTION.getId()));
        assertThat(pipelineExecutionDto.getPackageName(), equalTo(PIPELINE_EXECUTION.getPackageName()));
        assertThat(pipelineExecutionDto.getClassName(), equalTo(PIPELINE_EXECUTION.getClassName()));
        assertThat(pipelineExecutionDto.getMethodName(), equalTo(PIPELINE_EXECUTION.getMethodName()));
        assertThat(pipelineExecutionDto.getParameters(), equalTo(PIPELINE_EXECUTION.getParameters()));
        assertThat(pipelineExecutionDto.getStartTime(), equalTo(PIPELINE_EXECUTION.getStartTime()));
        assertThat(pipelineExecutionDto.getEndTime(), equalTo(PIPELINE_EXECUTION.getEndTime()));
        assertThat(pipelineExecutionDto.getTestStartTime(), equalTo(PIPELINE_EXECUTION.getTestStartTime()));
        assertThat(pipelineExecutionDto.getTestEndTime(), equalTo(PIPELINE_EXECUTION.getTestEndTime()));
        assertThat(pipelineExecutionDto.getBeforeClassStartTime(), equalTo(PIPELINE_EXECUTION.getBeforeClassStartTime()));
        assertThat(pipelineExecutionDto.getBeforeClassEndTime(), equalTo(PIPELINE_EXECUTION.getBeforeClassEndTime()));
        assertThat(pipelineExecutionDto.getBeforeMethodStartTime(), equalTo(PIPELINE_EXECUTION.getBeforeMethodStartTime()));
        assertThat(pipelineExecutionDto.getBeforeMethodEndTime(), equalTo(PIPELINE_EXECUTION.getBeforeMethodEndTime()));
        assertThat(pipelineExecutionDto.getStatus(), equalTo(PIPELINE_EXECUTION.getStatus().getName()));
        assertThat(pipelineExecutionDto.getExecutor(), equalTo(PIPELINE_EXECUTION.getExecutor().getName()));
        assertThat(pipelineExecutionDto.getPipelineId(), equalTo(PIPELINE_EXECUTION.getPipeline().getId()));

        List<PipelineExecutionMetadata> expectedList = PIPELINE_EXECUTION.getMetadata().stream().toList();

        verifyMetadata(pipelineExecutionDto, expectedList.get(0));
        verifyMetadata(pipelineExecutionDto, expectedList.get(1));
    }


    @Rollback
    @Transactional
    @Test
    public void executionDtoToExecution() {
        final PipelineExecution execution = pipelineMapper.executionDtoToExecution(PIPELINE_EXECUTION_DTO);
        assertThat(execution.getPackageName(), equalTo(PIPELINE_EXECUTION_DTO.getPackageName()));
        assertThat(execution.getClassName(), equalTo(PIPELINE_EXECUTION_DTO.getClassName()));
        assertThat(execution.getMethodName(), equalTo(PIPELINE_EXECUTION_DTO.getMethodName()));
        assertThat(execution.getParameters(), equalTo(PIPELINE_EXECUTION_DTO.getParameters()));
        assertThat(execution.getStartTime(), equalTo(PIPELINE_EXECUTION_DTO.getStartTime()));
        assertThat(execution.getEndTime(), equalTo(PIPELINE_EXECUTION_DTO.getEndTime()));
        assertThat(execution.getTestStartTime(), equalTo(PIPELINE_EXECUTION_DTO.getTestStartTime()));
        assertThat(execution.getTestEndTime(), equalTo(PIPELINE_EXECUTION_DTO.getTestEndTime()));
        assertThat(execution.getBeforeClassStartTime(), equalTo(PIPELINE_EXECUTION_DTO.getBeforeClassStartTime()));
        assertThat(execution.getBeforeClassEndTime(), equalTo(PIPELINE_EXECUTION_DTO.getBeforeClassEndTime()));
        assertThat(execution.getBeforeMethodStartTime(), equalTo(PIPELINE_EXECUTION_DTO.getBeforeMethodStartTime()));
        assertThat(execution.getBeforeMethodEndTime(), equalTo(PIPELINE_EXECUTION_DTO.getBeforeMethodEndTime()));
        assertThat(execution.getStatus().getName(), equalTo(PIPELINE_EXECUTION_DTO.getStatus()));
        assertThat(execution.getExecutor().getName(), equalTo(PIPELINE_EXECUTION_DTO.getExecutor()));
        assertThat(execution.getPipeline().getId(), equalTo(PIPELINE_EXECUTION_DTO.getPipelineId()));

        List<MetadataDto> expectedList = PIPELINE_EXECUTION_DTO.getMetadata().stream().toList();

        verifyMetadata(execution, expectedList.get(0));
        verifyMetadata(execution, expectedList.get(1));
    }

    @Test
    public void scenarioExecutionToScenarioExecutionDto() {
        final PipelineScenarioExecutionDto pipelineExecutionDto = pipelineMapper.scenarioExecutionToScenarioExecutionDto(PIPELINE_SCENARIO_EXECUTION);
        assertThat(pipelineExecutionDto.getFeature(), equalTo(PIPELINE_SCENARIO_EXECUTION.getFeature()));
        assertThat(pipelineExecutionDto.getScenario(), equalTo(PIPELINE_SCENARIO_EXECUTION.getScenario()));
        assertThat(pipelineExecutionDto.getParameters(), equalTo(PIPELINE_SCENARIO_EXECUTION.getParameters()));
        assertThat(pipelineExecutionDto.getStartTime(), equalTo(PIPELINE_SCENARIO_EXECUTION.getStartTime()));
        assertThat(pipelineExecutionDto.getEndTime(), equalTo(PIPELINE_SCENARIO_EXECUTION.getEndTime()));
        assertThat(pipelineExecutionDto.getBeforeScenarioStartTime(), equalTo(PIPELINE_SCENARIO_EXECUTION.getBeforeScenarioStartTime()));
        assertThat(pipelineExecutionDto.getBeforeScenarioEndTime(), equalTo(PIPELINE_SCENARIO_EXECUTION.getBeforeScenarioEndTime()));
        assertThat(pipelineExecutionDto.getStatus(), equalTo(PIPELINE_SCENARIO_EXECUTION.getStatus().getName()));
        assertThat(pipelineExecutionDto.getExecutor(), equalTo(PIPELINE_SCENARIO_EXECUTION.getExecutor().getName()));
        assertThat(pipelineExecutionDto.getPipelineId(), equalTo(PIPELINE_SCENARIO_EXECUTION.getPipeline().getId()));

        List<PipelineExecutionMetadata> expectedList = PIPELINE_SCENARIO_EXECUTION.getMetadata().stream().toList();

        verifyMetadata(pipelineExecutionDto, expectedList.get(0));
        verifyMetadata(pipelineExecutionDto, expectedList.get(1));
    }


    @Rollback
    @Transactional
    @Test
    public void scenarioExecutionDtoToScenarioExecution() {
        final PipelineScenarioExecution execution = pipelineMapper.scenarioExecutionDtoToScenarioExecution(PIPELINE_SCENARIO_EXECUTION_DTO);
        assertThat(execution.getFeature(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getFeature()));
        assertThat(execution.getScenario(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getScenario()));
        assertThat(execution.getParameters(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getParameters()));
        assertThat(execution.getStartTime(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getStartTime()));
        assertThat(execution.getEndTime(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getEndTime()));
        assertThat(execution.getBeforeScenarioStartTime(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getBeforeScenarioStartTime()));
        assertThat(execution.getBeforeScenarioEndTime(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getBeforeScenarioEndTime()));
        assertThat(execution.getStatus().getName(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getStatus()));
        assertThat(execution.getExecutor().getName(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getExecutor()));
        assertThat(execution.getPipeline().getId(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getPipelineId()));

        List<MetadataDto> expectedList = PIPELINE_SCENARIO_EXECUTION_DTO.getMetadata().stream().toList();

        verifyMetadata(execution, expectedList.get(0));
        verifyMetadata(execution, expectedList.get(1));
    }

    @Test
    public void pipelineToPipelineDto() {
        final PipelineDto pipelineDto = pipelineMapper.pipelineToPipelineDto(PIPELINE);
        assertThat(pipelineDto.getId(), equalTo(PIPELINE.getId()));
        assertThat(pipelineDto.getName(), equalTo(PIPELINE.getName()));
        assertThat(pipelineDto.getDescription(), equalTo(PIPELINE.getDescription()));
        assertThat(pipelineDto.getNumber(), equalTo(PIPELINE.getNumber()));
        assertThat(pipelineDto.getStartDate(), equalTo(PIPELINE.getStartDate()));
        assertThat(pipelineDto.getEndDate(), equalTo(PIPELINE.getEndDate()));
        assertThat(pipelineDto.getEnvironmentCode(), equalTo(PIPELINE.getEnvironment().getCode()));

        List<PipelineMetadata> expectedList = PIPELINE.getMetadata().stream().toList();

        verifyMetadata(pipelineDto, expectedList.get(0));
        verifyMetadata(pipelineDto, expectedList.get(1));
    }

    @Test
    public void pipelineDtoToPipeline() {
        final Pipeline pipeline = pipelineMapper.pipelineDtoToPipeline(PIPELINE_DTO);
        assertThat(pipeline.getId(), equalTo(PIPELINE_DTO.getId()));
        assertThat(pipeline.getName(), equalTo(PIPELINE_DTO.getName()));
        assertThat(pipeline.getDescription(), equalTo(PIPELINE_DTO.getDescription()));
        assertThat(pipeline.getNumber(), equalTo(PIPELINE_DTO.getNumber()));
        assertThat(pipeline.getStartDate(), equalTo(PIPELINE_DTO.getStartDate()));
        assertThat(pipeline.getEndDate(), equalTo(PIPELINE_DTO.getEndDate()));
        assertThat(pipeline.getEnvironment().getCode(), equalTo(PIPELINE_DTO.getEnvironmentCode()));

        List<MetadataDto> expectedList = PIPELINE_DTO.getMetadata().stream().toList();

        verifyMetadata(pipeline, expectedList.get(0));
        verifyMetadata(pipeline, expectedList.get(1));
    }

    @Test
    public void pipelineMetadataToMetadataDto() {
        PipelineMetadata pipelineMetadata = PIPELINE.getMetadata().stream().findFirst().orElse(null);
        assertThat(pipelineMetadata, notNullValue());
        final MetadataDto metadataDto = pipelineMapper.pipelineMetadataToMetadataDto(pipelineMetadata);
        assertThat(metadataDto.getId(), equalTo(pipelineMetadata.getId()));
        assertThat(metadataDto.getValue(), equalTo(pipelineMetadata.getValue()));
        assertThat(metadataDto.getName(), equalTo(pipelineMetadata.getName()));
    }

    @Test
    public void pipelineExecutionMetadataToMetadataDto() {
        final MetadataDto metadataDto = PIPELINE_DTO.getMetadata().stream().findFirst().orElse(null);
        assertThat(metadataDto, notNullValue());
        final PipelineMetadata pipelineExecutionMetadata = pipelineMapper.metadataDtoToPipelineMetadata(metadataDto);
        assertThat(metadataDto.getValue(), equalTo(pipelineExecutionMetadata.getValue()));
        assertThat(metadataDto.getName(), equalTo(pipelineExecutionMetadata.getName()));
    }

    @Test
    public void metadataDtoToPipelineMetadata() {
        final PipelineExecutionMetadata pipelineExecutionMetadata = PIPELINE_EXECUTION.getMetadata().stream().findFirst().orElse(null);
        assertThat(pipelineExecutionMetadata, notNullValue());

        final MetadataDto metadataDto = pipelineMapper.pipelineExecutionMetadataToMetadataDto(pipelineExecutionMetadata);
        assertThat(metadataDto.getId(), equalTo(pipelineExecutionMetadata.getId()));
        assertThat(metadataDto.getValue(), equalTo(pipelineExecutionMetadata.getValue()));
        assertThat(metadataDto.getName(), equalTo(pipelineExecutionMetadata.getName()));
    }

    @Test
    public void metadataDtoToPipelineExecutionMetadata() {
        final MetadataDto metadataDto = PIPELINE_EXECUTION_DTO.getMetadata().stream().findFirst().orElse(null);
        assertThat(metadataDto, notNullValue());
        final PipelineExecutionMetadata pipelineExecutionMetadata = pipelineMapper.metadataDtoToPipelineExecutionMetadata(metadataDto);
        assertThat(metadataDto.getValue(), equalTo(pipelineExecutionMetadata.getValue()));
        assertThat(metadataDto.getName(), equalTo(pipelineExecutionMetadata.getName()));
    }
}