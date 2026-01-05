package org.catools.athena.rest.feign.pipeline.listeners;

import lombok.extern.slf4j.Slf4j;
import org.catools.athena.model.pipeline.PipelineDto;
import org.catools.athena.model.pipeline.PipelineExecutionDto;
import org.catools.athena.rest.feign.common.utils.JsonUtils;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;
import org.catools.athena.rest.feign.pipeline.configs.PipelineConfigs;
import org.catools.athena.rest.feign.pipeline.helpers.PipelineHelper;
import org.catools.athena.rest.feign.pipeline.utils.PipelineUtils;
import org.jetbrains.annotations.NotNull;
import org.testng.IClassListener;
import org.testng.IConfigurationListener;
import org.testng.IExecutionListener;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.internal.IResultListener;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Date;

@Slf4j
public class PipelineListener
    implements IExecutionListener, ISuiteListener, IClassListener, IResultListener, IConfigurationListener, IInvokedMethodListener {
  private static PipelineDto pipeline;
  private Instant beforeClassStartTime;
  private Instant beforeMethodStartTime;
  private Instant beforeClassEndTime;
  private Instant beforeMethodEndTime;

  @Override
  public void onExecutionStart() {
    pipeline = getPipeline();
  }

  @Override
  public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    setStartTime(testResult.getMethod());
  }

  @Override
  public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    setEndTime(testResult);
  }

  @Override
  public synchronized void onTestSuccess(ITestResult result) {
    addResult(result);
  }

  @Override
  public synchronized void onTestFailure(ITestResult result) {
    addResult(result);
  }

  @Override
  public synchronized void onTestSkipped(ITestResult result) {
    addResult(result);
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    addResult(result);
  }

  @Override
  public void onExecutionFinish() {
    PipelineUtils.getPipelineClient().updatePipelineEndDate(pipeline.getId(), Instant.now());
  }

  protected static PipelineDto getPipeline() {
    if (PipelineConfigs.isCreateNewPipelineEnabled()) {
      return buildPipeline();
    }

    PipelineDto lastByName = PipelineHelper.getPipeline();

    return lastByName != null ? lastByName : buildPipeline();
  }

  protected void addResult(ITestResult testResult) {
    PipelineExecutionDto executionDto = buildExecutionDto(testResult);
    PipelineHelper.addExecution(executionDto);
  }

  protected PipelineExecutionDto buildExecutionDto(ITestResult testResult) {
    String packageName = testResult.getMethod().getRealClass().getPackage().getName();
    String className = testResult.getMethod().getRealClass().getSimpleName();
    Method method = testResult.getMethod().getConstructorOrMethod().getMethod();

    String methodName = method.getName();
    Object[] parameters = testResult.getParameters();

    PipelineExecutionDto executionDto = new PipelineExecutionDto();

    if (parameters != null && parameters.length > 0) {
      executionDto.setParameters(JsonUtils.writeValueAsString(parameters));
    }

    executionDto.setPackageName(packageName);
    executionDto.setClassName(className);
    executionDto.setMethodName(methodName);
    executionDto.setStartTime(beforeClassStartTime);
    executionDto.setEndTime(Instant.now());
    executionDto.setTestStartTime(testResult.getEndMillis() < 0 ? null : new Date(testResult.getStartMillis()).toInstant());
    executionDto.setTestEndTime(testResult.getEndMillis() < 0 ? null : new Date(testResult.getEndMillis()).toInstant());
    executionDto.setBeforeClassStartTime(beforeClassStartTime);
    executionDto.setBeforeClassEndTime(beforeClassEndTime);
    executionDto.setBeforeMethodStartTime(beforeMethodStartTime);
    executionDto.setBeforeMethodEndTime(beforeMethodEndTime);
    executionDto.setStatus(getStatus(testResult));
    executionDto.setExecutor(PipelineConfigs.getExecutorName());
    executionDto.setPipelineId(pipeline.getId());
    executionDto.setMetadata(PipelineConfigs.getPipelineExecutionMetadata());
    return executionDto;
  }

  protected void setStartTime(ITestNGMethod method) {
    if (method.isBeforeClassConfiguration()) {
      beforeClassStartTime = Instant.now();
    } else if (method.isBeforeMethodConfiguration()) {
      beforeMethodStartTime = Instant.now();
    }
  }

  protected void setEndTime(ITestResult testResult) {
    ITestNGMethod method = testResult.getMethod();
    if (method.isBeforeClassConfiguration()) {
      beforeClassEndTime = Instant.now();
    } else if (method.isBeforeMethodConfiguration()) {
      beforeMethodEndTime = Instant.now();
    }
  }

  private static String getStatus(ITestResult testResult) {
    switch (testResult.getStatus()) {
      case ITestResult.CREATED:
        return "CREATED";
      case ITestResult.SUCCESS:
      case ITestResult.SUCCESS_PERCENTAGE_FAILURE:
        return "SUCCESS";
      case ITestResult.FAILURE:
        return "FAILURE";
      case ITestResult.SKIP:
        return "SKIP";
      case ITestResult.STARTED:
        return "STARTED";
      default:
        return "NOT_SET";
    }
  }

  @NotNull
  private static PipelineDto buildPipeline() {
    return PipelineHelper.buildPipeline(CoreConfigs.getAthenaHost(),
        CoreConfigs.getProject(),
        CoreConfigs.getVersion(),
        CoreConfigs.getEnvironment(),
        PipelineConfigs.getPipelineName(),
        PipelineConfigs.getPipelineNumber(),
        PipelineConfigs.getPipelineDescription(),
        PipelineConfigs.getPipelineMetadata());
  }

}
