package org.catools.atlassian.scale;

import org.catools.atlassian.scale.rest.cycle.CZScaleTestRunClient;
import org.catools.atlassian.scale.rest.testcase.CZScaleTestCaseClient;

public class CZScaleClient {
  public static final CZScaleTestRunClient TestRuns = new CZScaleTestRunClient();
  public static final CZScaleTestCaseClient TestCases = new CZScaleTestCaseClient();
}
