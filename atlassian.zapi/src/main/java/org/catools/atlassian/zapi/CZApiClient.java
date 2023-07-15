package org.catools.atlassian.zapi;

import org.catools.atlassian.zapi.rest.cycle.CZApiCycleClient;
import org.catools.atlassian.zapi.rest.execution.CZApiExecutionClient;
import org.catools.atlassian.zapi.rest.util.CZApiExecutionStatusClient;
import org.catools.atlassian.zapi.rest.util.CZApiProjectClient;
import org.catools.atlassian.zapi.rest.util.CZApiVersionClient;
import org.catools.atlassian.zapi.rest.zql.CZApiSearchClient;

public class CZApiClient {
  public static final CZApiExecutionStatusClient ExecutionStatus = new CZApiExecutionStatusClient();
  public static final CZApiCycleClient Cycle = new CZApiCycleClient();
  public static final CZApiSearchClient Search = new CZApiSearchClient();
  public static final CZApiExecutionClient Execution = new CZApiExecutionClient();
  public static final CZApiProjectClient Project = new CZApiProjectClient();
  public static final CZApiVersionClient Version = new CZApiVersionClient();
}
