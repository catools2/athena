package org.catools.athena.pipeline.mapper;

import org.catools.athena.AthenaSpringBootIT;
import org.catools.athena.configs.StagedTestData;
import org.catools.athena.model.core.EnvironmentDto;
import org.catools.athena.model.core.MetadataDto;
import org.catools.athena.model.core.UserDto;
import org.catools.athena.model.core.VersionDto;
import org.catools.athena.model.pipeline.PipelineDto;
import org.catools.athena.model.pipeline.PipelineExecutionDto;
import org.catools.athena.model.pipeline.PipelineExecutionStatusDto;
import org.catools.athena.model.pipeline.PipelineScenarioExecutionDto;
import org.catools.athena.pipeline.builder.PipelineBuilder;
import org.catools.athena.pipeline.common.entity.Pipeline;
import org.catools.athena.pipeline.common.entity.PipelineExecution;
import org.catools.athena.pipeline.common.entity.PipelineExecutionMetadata;
import org.catools.athena.pipeline.common.entity.PipelineExecutionStatus;
import org.catools.athena.pipeline.common.entity.PipelineMetadata;
import org.catools.athena.pipeline.common.entity.PipelineScenarioExecution;
import org.catools.athena.pipeline.common.mapper.PipelineMapper;
import org.catools.athena.pipeline.common.service.PipelineExecutionService;
import org.catools.athena.pipeline.common.service.PipelineExecutionStatusService;
import org.catools.athena.pipeline.common.service.PipelineScenarioExecutionService;
import org.catools.athena.pipeline.common.service.PipelineService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PipelineMapperIT extends AthenaSpringBootIT {
  private static PipelineDto pipelineDto;
  private static Pipeline pipeline;
  private static PipelineExecutionDto pipelineExecutionDto;
  private static PipelineExecution pipelineExecution;
  private static PipelineScenarioExecutionDto pipelineScenarioExecutionDto;
  private static PipelineScenarioExecution pipelineScenarioExecution;

  @Autowired
  PipelineMapper pipelineMapper;

  @Autowired
  PipelineService pipelineService;

  @Autowired
  PipelineExecutionService pipelineExecutionService;

  @Autowired
  PipelineScenarioExecutionService pipelineScenarioExecutionService;

  @Autowired
  PipelineExecutionStatusService pipelineExecutionStatusService;

  @BeforeAll
  public void beforeAll() {
    UserDto userDto = StagedTestData.getUser(1);
    EnvironmentDto environmentDto = StagedTestData.getEnvironment(1);
    VersionDto versionDto = StagedTestData.getVersion(1);

    PipelineExecutionStatusDto statusDto = PipelineBuilder.buildPipelineExecutionStatusDto();
    statusDto.setId(pipelineExecutionStatusService.save(statusDto).getId());
    PipelineExecutionStatus status = PipelineBuilder.buildPipelineExecutionStatus(statusDto);

    pipelineDto = PipelineBuilder.buildPipelineDto(versionDto, environmentDto);
    pipelineDto.setId(pipelineService.saveOrUpdate(pipelineDto).getId());
    pipeline = PipelineBuilder.buildPipeline(pipelineDto, environmentDto);

    pipelineExecutionDto = PipelineBuilder.buildExecutionDto(pipelineDto, statusDto, userDto);
    pipelineExecutionDto.setPipelineId(pipelineExecutionService.save(pipelineExecutionDto).getId());
    pipelineExecution = PipelineBuilder.buildExecution(pipelineExecutionDto, pipeline, status, userDto);

    pipelineScenarioExecutionDto = PipelineBuilder.buildScenarioExecutionDto(pipelineDto, statusDto, userDto);
    pipelineScenarioExecutionDto.setId(pipelineScenarioExecutionService.save(pipelineScenarioExecutionDto).getId());
    pipelineScenarioExecution = PipelineBuilder.buildScenarioExecution(pipelineScenarioExecutionDto, pipeline, status, userDto);
  }

  @Test
  void executionToExecutionDto() {
    final PipelineExecutionDto executionDto = pipelineMapper.executionToExecutionDto(pipelineExecution);
    assertThat(executionDto.getId(), equalTo(pipelineExecution.getId()));
    assertThat(executionDto.getPackageName(), equalTo(pipelineExecution.getPackageName()));
    assertThat(executionDto.getClassName(), equalTo(pipelineExecution.getClassName()));
    assertThat(executionDto.getMethodName(), equalTo(pipelineExecution.getMethodName()));
    assertThat(executionDto.getParameters(), equalTo(pipelineExecution.getParameters()));
    assertThat(executionDto.getStartTime(), equalTo(pipelineExecution.getStartTime()));
    assertThat(executionDto.getEndTime(), equalTo(pipelineExecution.getEndTime()));
    assertThat(executionDto.getTestStartTime(), equalTo(pipelineExecution.getTestStartTime()));
    assertThat(executionDto.getTestEndTime(), equalTo(pipelineExecution.getTestEndTime()));
    assertThat(executionDto.getBeforeClassStartTime(), equalTo(pipelineExecution.getBeforeClassStartTime()));
    assertThat(executionDto.getBeforeClassEndTime(), equalTo(pipelineExecution.getBeforeClassEndTime()));
    assertThat(executionDto.getBeforeMethodStartTime(), equalTo(pipelineExecution.getBeforeMethodStartTime()));
    assertThat(executionDto.getBeforeMethodEndTime(), equalTo(pipelineExecution.getBeforeMethodEndTime()));
    assertThat(executionDto.getStatus(), equalTo(pipelineExecution.getStatus().getName()));
    assertThat(executionDto.getExecutor(), equalTo(StagedTestData.getUser(pipelineExecution.getExecutorId()).getUsername()));
    assertThat(executionDto.getPipelineId(), equalTo(pipelineExecution.getPipeline().getId()));

    verifyNameValuePairs(executionDto.getMetadata(), pipelineExecution.getMetadata());
  }


  @Test
  void executionToExecutionDto_shallReturnNullIfTheInputIsNull() {
    assertThat(pipelineMapper.executionToExecutionDto(null), nullValue());
  }

  @Rollback
  @Test
  void executionDtoToExecution() {
    final PipelineExecution execution = pipelineMapper.executionDtoToExecution(pipelineExecutionDto);
    assertThat(execution.getPackageName(), equalTo(pipelineExecutionDto.getPackageName()));
    assertThat(execution.getClassName(), equalTo(pipelineExecutionDto.getClassName()));
    assertThat(execution.getMethodName(), equalTo(pipelineExecutionDto.getMethodName()));
    assertThat(execution.getParameters(), equalTo(pipelineExecutionDto.getParameters()));
    assertThat(execution.getStartTime(), equalTo(pipelineExecutionDto.getStartTime()));
    assertThat(execution.getEndTime(), equalTo(pipelineExecutionDto.getEndTime()));
    assertThat(execution.getTestStartTime(), equalTo(pipelineExecutionDto.getTestStartTime()));
    assertThat(execution.getTestEndTime(), equalTo(pipelineExecutionDto.getTestEndTime()));
    assertThat(execution.getBeforeClassStartTime(), equalTo(pipelineExecutionDto.getBeforeClassStartTime()));
    assertThat(execution.getBeforeClassEndTime(), equalTo(pipelineExecutionDto.getBeforeClassEndTime()));
    assertThat(execution.getBeforeMethodStartTime(), equalTo(pipelineExecutionDto.getBeforeMethodStartTime()));
    assertThat(execution.getBeforeMethodEndTime(), equalTo(pipelineExecutionDto.getBeforeMethodEndTime()));
    assertThat(execution.getStatus().getName(), equalTo(pipelineExecutionDto.getStatus()));
    assertThat(execution.getExecutorId(), equalTo(StagedTestData.getUserByUsername(pipelineExecutionDto.getExecutor()).getId()));
    assertThat(execution.getPipeline().getId(), equalTo(pipelineExecutionDto.getPipelineId()));

    verifyNameValuePairs(execution.getMetadata(), pipelineExecutionDto.getMetadata());
  }

  @Test
  void executionDtoToExecution_shallReturnNullIfTheInputIsNull() {
    assertThat(pipelineMapper.executionDtoToExecution(null), nullValue());
  }

  @Test
  void scenarioExecutionToScenarioExecutionDto() {
    final PipelineScenarioExecutionDto executionDto = pipelineMapper.scenarioExecutionToScenarioExecutionDto(pipelineScenarioExecution);
    assertThat(executionDto.getFeature(), equalTo(pipelineScenarioExecution.getFeature()));
    assertThat(executionDto.getScenario(), equalTo(pipelineScenarioExecution.getScenario()));
    assertThat(executionDto.getParameters(), equalTo(pipelineScenarioExecution.getParameters()));
    assertThat(executionDto.getStartTime(), equalTo(pipelineScenarioExecution.getStartTime()));
    assertThat(executionDto.getEndTime(), equalTo(pipelineScenarioExecution.getEndTime()));
    assertThat(executionDto.getBeforeScenarioStartTime(), equalTo(pipelineScenarioExecution.getBeforeScenarioStartTime()));
    assertThat(executionDto.getBeforeScenarioEndTime(), equalTo(pipelineScenarioExecution.getBeforeScenarioEndTime()));
    assertThat(executionDto.getStatus(), equalTo(pipelineScenarioExecution.getStatus().getName()));
    assertThat(executionDto.getExecutor(), equalTo(StagedTestData.getUser(pipelineScenarioExecution.getExecutorId()).getUsername()));
    assertThat(executionDto.getPipelineId(), equalTo(pipelineScenarioExecution.getPipeline().getId()));

    verifyNameValuePairs(executionDto.getMetadata(), pipelineScenarioExecution.getMetadata());
  }

  @Test
  void scenarioExecutionToScenarioExecutionDto_shallReturnNullIfTheInputIsNull() {
    assertThat(pipelineMapper.scenarioExecutionToScenarioExecutionDto(null), nullValue());
  }


  @Rollback
  @Test
  void scenarioExecutionDtoToScenarioExecution() {
    final PipelineScenarioExecution execution = pipelineMapper.scenarioExecutionDtoToScenarioExecution(pipelineScenarioExecutionDto);
    assertThat(execution.getFeature(), equalTo(pipelineScenarioExecutionDto.getFeature()));
    assertThat(execution.getScenario(), equalTo(pipelineScenarioExecutionDto.getScenario()));
    assertThat(execution.getParameters(), equalTo(pipelineScenarioExecutionDto.getParameters()));
    assertThat(execution.getStartTime(), equalTo(pipelineScenarioExecutionDto.getStartTime()));
    assertThat(execution.getEndTime(), equalTo(pipelineScenarioExecutionDto.getEndTime()));
    assertThat(execution.getBeforeScenarioStartTime(), equalTo(pipelineScenarioExecutionDto.getBeforeScenarioStartTime()));
    assertThat(execution.getBeforeScenarioEndTime(), equalTo(pipelineScenarioExecutionDto.getBeforeScenarioEndTime()));
    assertThat(execution.getStatus().getName(), equalTo(pipelineScenarioExecutionDto.getStatus()));
    assertThat(execution.getExecutorId(), equalTo(StagedTestData.getUserByUsername(pipelineScenarioExecutionDto.getExecutor()).getId()));
    assertThat(execution.getPipeline().getId(), equalTo(pipelineScenarioExecutionDto.getPipelineId()));

    verifyNameValuePairs(execution.getMetadata(), pipelineScenarioExecutionDto.getMetadata());
  }


  @Rollback
  @Test
  void scenarioExecutionDtoToScenarioExecution_shallReturnNullIfTheInputIsNull() {
    assertThat(pipelineMapper.scenarioExecutionDtoToScenarioExecution(null), nullValue());
  }

  @Test
  void pipelineToPipelineDto() {
    final PipelineDto actualPipelineDto = pipelineMapper.pipelineToPipelineDto(pipeline);
    assertThat(actualPipelineDto.getId(), equalTo(pipeline.getId()));
    assertThat(actualPipelineDto.getName(), equalTo(pipeline.getName()));
    assertThat(actualPipelineDto.getDescription(), equalTo(pipeline.getDescription()));
    assertThat(actualPipelineDto.getNumber(), equalTo(pipeline.getNumber()));
    assertThat(actualPipelineDto.getStartDate(), equalTo(pipeline.getStartDate()));
    assertThat(actualPipelineDto.getEndDate(), equalTo(pipeline.getEndDate()));
    assertThat(actualPipelineDto.getEnvironment(), equalTo(StagedTestData.getEnvironment(pipeline.getEnvironmentId()).getCode()));

    verifyNameValuePairs(actualPipelineDto.getMetadata(), pipeline.getMetadata());
  }

  @Test
  void pipelineToPipelineDto_shallReturnNullIfTheInputIsNull() {
    assertThat(pipelineMapper.pipelineToPipelineDto(null), nullValue());
  }

  @Test
  void pipelineDtoToPipeline() {
    final Pipeline actualPipeline = pipelineMapper.pipelineDtoToPipeline(pipelineDto);
    assertThat(actualPipeline.getId(), equalTo(pipelineDto.getId()));
    assertThat(actualPipeline.getName(), equalTo(pipelineDto.getName()));
    assertThat(actualPipeline.getDescription(), equalTo(pipelineDto.getDescription()));
    assertThat(actualPipeline.getNumber(), equalTo(pipelineDto.getNumber()));
    assertThat(actualPipeline.getStartDate(), equalTo(pipelineDto.getStartDate()));
    assertThat(actualPipeline.getEndDate(), equalTo(pipelineDto.getEndDate()));
    assertThat(actualPipeline.getEnvironmentId(), equalTo(StagedTestData.getEnvironmentByCode(pipelineDto.getEnvironment()).getId()));

    verifyNameValuePairs(actualPipeline.getMetadata(), pipelineDto.getMetadata());
  }

  @Test
  void pipelineDtoToPipeline_shallReturnNullIfTheInputIsNull() {
    assertThat(pipelineMapper.pipelineDtoToPipeline(null), nullValue());
  }

  @Test
  void pipelineMetadataToMetadataDto() {
    PipelineMetadata actualPipelineMetadata = pipeline.getMetadata().stream().findFirst().orElse(null);
    assertThat(actualPipelineMetadata, notNullValue());
    final MetadataDto metadataDto = pipelineMapper.pipelineMetadataToMetadataDto(actualPipelineMetadata);
    assertThat(metadataDto.getId(), equalTo(actualPipelineMetadata.getId()));
    assertThat(metadataDto.getValue(), equalTo(actualPipelineMetadata.getValue()));
    assertThat(metadataDto.getName(), equalTo(actualPipelineMetadata.getName()));
  }

  @Test
  void pipelineMetadataToMetadataDto_shallReturnNullIfTheInputIsNull() {
    assertThat(pipelineMapper.pipelineMetadataToMetadataDto(null), nullValue());
  }

  @Test
  void pipelineExecutionMetadataToMetadataDto() {
    final MetadataDto metadataDto = pipelineDto.getMetadata().stream().findFirst().orElse(null);
    assertThat(metadataDto, notNullValue());
    final PipelineMetadata pipelineExecutionMetadata = pipelineMapper.metadataDtoToPipelineMetadata(metadataDto);
    assertThat(metadataDto.getValue(), equalTo(pipelineExecutionMetadata.getValue()));
    assertThat(metadataDto.getName(), equalTo(pipelineExecutionMetadata.getName()));
  }

  @Test
  void pipelineExecutionMetadataToMetadataDto_shallReturnNullIfTheInputIsNull() {
    assertThat(pipelineMapper.metadataDtoToPipelineMetadata(null), nullValue());
  }

  @Test
  void metadataDtoToPipelineMetadata() {
    final PipelineExecutionMetadata pipelineExecutionMetadata = pipelineExecution.getMetadata().stream().findFirst().orElse(null);
    assertThat(pipelineExecutionMetadata, notNullValue());

    final MetadataDto metadataDto = pipelineMapper.pipelineExecutionMetadataToMetadataDto(pipelineExecutionMetadata);
    assertThat(metadataDto.getId(), equalTo(pipelineExecutionMetadata.getId()));
    assertThat(metadataDto.getValue(), equalTo(pipelineExecutionMetadata.getValue()));
    assertThat(metadataDto.getName(), equalTo(pipelineExecutionMetadata.getName()));
  }

  @Test
  void metadataDtoToPipelineMetadata_shallReturnNullIfTheInputIsNull() {
    assertThat(pipelineMapper.pipelineExecutionMetadataToMetadataDto(null), nullValue());
  }

  @Test
  void metadataDtoToPipelineExecutionMetadata() {
    final MetadataDto metadataDto = pipelineExecutionDto.getMetadata().stream().findFirst().orElse(null);
    assertThat(metadataDto, notNullValue());
    final PipelineExecutionMetadata pipelineExecutionMetadata = pipelineMapper.metadataDtoToPipelineExecutionMetadata(metadataDto);
    assertThat(metadataDto.getValue(), equalTo(pipelineExecutionMetadata.getValue()));
    assertThat(metadataDto.getName(), equalTo(pipelineExecutionMetadata.getName()));
  }

  @Test
  void metadataDtoToPipelineExecutionMetadata_shallReturnNullIfTheInputIsNull() {
    assertThat(pipelineMapper.metadataDtoToPipelineExecutionMetadata(null), nullValue());
  }
}