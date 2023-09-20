package org.catools.pipeline.helpers;

import org.catools.common.annotations.*;
import org.catools.common.date.CDate;
import org.catools.common.testng.model.CTestResult;
import org.catools.common.utils.CJsonUtil;
import org.catools.common.utils.CStringUtil;
import org.catools.pipeline.configs.CPipelineConfigs;
import org.catools.pipeline.configs.CPipelineTestNGConfigs;
import org.catools.pipeline.dao.CPipelineBaseDao;
import org.catools.pipeline.dao.CPipelineDao;
import org.catools.pipeline.model.*;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.catools.pipeline.cache.CPipelineCacheManager.*;

public class CPipelineHelper {

  public static CPipeline getPipeline() {
    if (!CPipelineTestNGConfigs.isEnabled()) return null;

    if (CPipelineTestNGConfigs.always_create_new_pipeline()) {
      return buildPipeline();
    }

    CPipeline lastByName = CPipelineDao.getLastByName(CPipelineConfigs.getPipelineName());
    if (!CPipelineTestNGConfigs.createPipelineIfNotExist()) {
      Objects.requireNonNull(lastByName, "No pipeline with given name found, set CATOOLS_PIPELINE_LISTENER_CREATE_IF_NOT_EXIST environment variable if you want to create one.");
    }
    return lastByName != null ? lastByName : buildPipeline();
  }

  public static void addExecution(CPipeline pipeline, CTestResult testResult) {
    addExecution(pipeline, testResult, new ArrayList<>());
  }

  public static void addExecution(CPipeline pipeline, CTestResult testResult, List<CPipelineExecutionMetaData> executionMetaData) {
    if (!CPipelineTestNGConfigs.isEnabled()) return;

    CPipelineExecution execution = new CPipelineExecution();

    if (testResult.getTestIds() != null)
      testResult.getTestIds().forEach(t -> executionMetaData.add(readExecutionMetaData("Test ID", t)));

    if (testResult.getOpenDefectIds() != null)
      testResult.getOpenDefectIds().forEach(t -> executionMetaData.add(readExecutionMetaData("Open Defect ID", t)));

    if (testResult.getDefectIds() != null)
      testResult.getDefectIds().forEach(t -> executionMetaData.add(readExecutionMetaData("Defect ID", t)));

    if (testResult.getDeferredId() != null)
      testResult.getDeferredId().forEach(t -> executionMetaData.add(readExecutionMetaData("Deferred ID", t)));

    if (testResult.getMethodGroups() != null)
      testResult.getMethodGroups().forEach(t -> executionMetaData.add(readExecutionMetaData("Method Group", t)));

    if (testResult.getParameters() != null && testResult.getParameters().length > 0)
      executionMetaData.add(readExecutionMetaData("Parameters", CJsonUtil.toString(testResult.getParameters())));

    if (CStringUtil.isNotBlank(testResult.getAwaiting()))
      executionMetaData.add(readExecutionMetaData("Awaiting", testResult.getAwaiting()));

    if (testResult.getRegressionDepth() != null)
      executionMetaData.add(readExecutionMetaData("Regression Depth", testResult.getRegressionDepth().toString()));

    if (testResult.getSeverityLevel() != null)
      executionMetaData.add(readExecutionMetaData("Severity Level", testResult.getSeverityLevel().toString()));

    if (CStringUtil.isNotBlank(testResult.getHost()))
      executionMetaData.add(readExecutionMetaData("Host", testResult.getHost()));

    if (testResult.getAnnotations() != null) {
      for (Object annotation : testResult.getAnnotations()) {
        if (skipAlreadyAddedAnnotations(annotation)) continue;
        Annotation annot = (Annotation) annotation;
        Class<? extends Annotation> annotationType = annot.annotationType();
        executionMetaData.add(readExecutionMetaData(
            annotationType.getSimpleName(),
            annot.toString().replace("@" + annotationType.getName(), "")
        ));
      }
    }

    if (testResult.getExceptionInfo() != null)
      execution.setException(new CPipelineExecutionException(
          testResult.getExceptionInfo().getType(),
          testResult.getExceptionInfo().getMessage(),
          testResult.getExceptionInfo().getStackTrace()));

    if (testResult.getExecutionMetadata() != null)
      testResult.getExecutionMetadata().forEach(m -> executionMetaData.add(readExecutionMetaData(m.getName(), m.getValue())));

    execution.setPipeline(pipeline)
        .setStatus(new CPipelineStatus(testResult.getStatus().getId(), testResult.getStatus().name()))
        .setExecutor(getExecutor())
        .setStartTime(testResult.getStartTime())
        .setEndTime(testResult.getEndTime())
        .setTestStartTime(testResult.getTestStartTime())
        .setTestEndTime(testResult.getTestEndTime())
        .setBeforeClassStartTime(testResult.getBeforeClassStartTime())
        .setBeforeClassEndTime(testResult.getBeforeClassEndTime())
        .setBeforeMethodStartTime(testResult.getBeforeMethodStartTime())
        .setBeforeMethodEndTime(testResult.getBeforeMethodEndTime())
        .setPackageName(testResult.getPackageName())
        .setClassName(testResult.getClassName())
        .setMethodName(testResult.getMethodName())
        .setMetadata(executionMetaData);

    CPipelineBaseDao.merge(execution);
  }

  private static boolean skipAlreadyAddedAnnotations(Object annotation) {
    return annotation instanceof Test ||
        annotation instanceof CRegression ||
        annotation instanceof CSeverity ||
        annotation instanceof CDefects ||
        annotation instanceof CAwaiting ||
        annotation instanceof COpenDefects ||
        annotation instanceof CDeferred ||
        annotation instanceof CTestIds ||
        annotation instanceof CIgnored;
  }

  private static CPipeline buildPipeline() {
    CPipelineBaseDao.merge(CPipelineConfigs.getProject());

    List<CPipelineMetaData> metaData = CPipelineConfigs
        .getPipelineMetaData()
        .stream()
        .map(md -> readMetaData(md.getName(), md.getValue()))
        .collect(Collectors.toList());

    CPipeline pipeline = new CPipeline()
        .setType(CPipelineConfigs.getPipelineType())
        .setName(CPipelineConfigs.getPipelineName())
        .setDescription(CPipelineConfigs.getPipelineDescription())
        .setNumber(CPipelineConfigs.getPipelineNumber())
        .setStartDate(CDate.now())
        .setEnvironment(CPipelineConfigs.getEnvironment())
        .setProject(CPipelineConfigs.getProject())
        .setMetadata(metaData);

    return CPipelineBaseDao.merge(pipeline);
  }
}
