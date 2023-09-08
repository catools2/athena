package org.catools.common.testng.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.catools.common.annotations.*;
import org.catools.common.collections.CList;
import org.catools.common.config.CTestManagementConfigs;
import org.catools.common.date.CDate;
import org.catools.common.exception.CExceptionInfo;
import org.catools.common.tests.CTest;
import org.catools.common.tests.CTestMetadata;
import org.catools.common.utils.CStringUtil;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class CTestResult implements Comparable<CTestResult> {
  @Getter
  private static final String project = CTestManagementConfigs.getProjectName();
  @Getter
  private static final String version = CTestManagementConfigs.getVersionName();
  @JsonIgnore
  private ITestResult origin;
  private int testExecutionId;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
  private CDate startTime;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
  private CDate endTime;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
  private CDate testStartTime;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
  private CDate testEndTime;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
  private CDate beforeClassStartTime;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
  private CDate beforeMethodStartTime;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
  private CDate beforeClassEndTime;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
  private CDate beforeMethodEndTime;
  private String packageName;
  private String className;
  private String methodName;
  private CExecutionStatus status;
  private Object[] annotations;
  private Object[] parameters;
  private CExceptionInfo exceptionInfo;
  private long duration;
  private String name;
  private String description;
  private String host;
  private CList<String> methodGroups = new CList<>();
  private CList<String> testIds = new CList<>();
  private CList<String> defectIds = new CList<>();
  private CList<String> openDefectIds = new CList<>();
  private CList<String> deferredId = new CList<>();
  private String awaiting = CStringUtil.EMPTY;
  private boolean configurationMethod = true;
  private Integer severityLevel = null;
  private Integer regressionDepth = null;
  private CTestMetadata executionMetadata = null;

  public CTestResult(ITestResult testResult) {
    this(testResult, null, null);
  }

  public CTestResult(ITestResult testResult, Date testStartTime, Date testEndTime) {
    this.origin = testResult;

    this.className = testResult.getMethod().getRealClass().getSimpleName();
    this.packageName = testResult.getMethod().getRealClass().getPackage().getName();

    if (testResult.getMethod().getGroups() != null) {
      this.methodGroups.addAll(List.of(testResult.getMethod().getGroups()));
    }

    if (testResult.getInstance() instanceof CTest) {
      executionMetadata = ((CTest)testResult.getInstance()).getMetadata();
    }

    if (testResult.getThrowable() != null)
      this.exceptionInfo = new CExceptionInfo(testResult.getThrowable());

    Method method = testResult.getMethod().getConstructorOrMethod().getMethod();
    this.methodName = method.getName();
    this.annotations = method.getAnnotations();
    this.parameters = testResult.getParameters();
    this.duration = testResult.getEndMillis() - testResult.getStartMillis();
    this.name = testResult.getName();
    this.host = testResult.getHost();
    this.description = testResult.getMethod().getDescription();

    this.startTime = new CDate(testResult.getStartMillis());
    this.endTime = new CDate(testResult.getEndMillis());

    this.testStartTime = testStartTime == null ? null : new CDate(testStartTime);
    this.testEndTime = testEndTime == null ? null : new CDate(testEndTime);

    boolean ignored = false;
    for (Object annotation : this.annotations) {
      if (annotation instanceof CTestIds a) {
        this.testIds.addAll(List.of(a.ids()));
      } else if (annotation instanceof CDeferred a) {
        this.deferredId.addAll(List.of(a.ids()));
      } else if (annotation instanceof CDefects a) {
        this.defectIds.addAll(List.of(a.ids()));
      } else if (annotation instanceof COpenDefects a) {
        this.openDefectIds.addAll(List.of(a.ids()));
      } else if (annotation instanceof CAwaiting a) {
        this.awaiting = a.cause();
      } else if (annotation instanceof CIgnored) {
        ignored = true;
      } else if (annotation instanceof CRegression a) {
        this.regressionDepth = a.depth();
      } else if (annotation instanceof CSeverity a) {
        this.severityLevel = a.level();
      } else if (annotation instanceof Test) {
        this.configurationMethod = false;
      }
    }

    if (testResult.getStatus() == ITestResult.SUCCESS) {
      this.status = CExecutionStatus.SUCCESS;
    } else if (!awaiting.isEmpty()) {
      this.status = CExecutionStatus.AWAITING;
    } else {
      if (ignored) {
        this.status = CExecutionStatus.IGNORED;
      } else if (!deferredId.isEmpty()) {
        this.status = CExecutionStatus.DEFERRED;
      } else if (testResult.getStatus() == ITestResult.FAILURE) {
        this.status = CExecutionStatus.FAILURE;
      } else {
        this.status = CExecutionStatus.SKIP;
      }
    }
    // set it at the end to make sure we have all values we need
    this.testExecutionId = hashCode();
  }

  public String getFullName() {
    return className + "." + name;
  }

  public String getTestFullName() {
    return getPackageName() + "." + getClassName() + "::" + getMethodName();
  }

  @Override
  public int compareTo(CTestResult o) {
    if (o == null) {
      return -1;
    }
    return toString().compareTo(o.toString());
  }
}
