package org.catools.athena.rest.tms.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TmsPathDefinitions {
  public static final String TMS = "/tms";
  public static final String TMS_ITEM = TMS + "/item";
  public static final String TMS_ITEM_TYPE = TMS + "/itemType";
  public static final String TMS_STATUS_TRANSITION = TMS + "/transition";
  public static final String TMS_STATUS_TRANSITIONS = TMS + "/transitions";
  public static final String TMS_TEST_CYCLE = TMS + "/cycle";
  public static final String TMS_TEST_CYCLES = TMS + "/cycles";
  public static final String TMS_TEST_EXECUTION = TMS + "/execution";
  public static final String TMS_TEST_EXECUTIONS = TMS + "/executions";
}