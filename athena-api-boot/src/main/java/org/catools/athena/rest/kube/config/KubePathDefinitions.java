package org.catools.athena.rest.kube.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class KubePathDefinitions {

  public static final String POD = "/pod";
  public static final String PODS = "/pods";

  public static final String CONTAINER = "/container";
  public static final String CONTAINERS = "/containers";

  public static final String CONTAINER_STATE = "/containerState";
  public static final String CONTAINER_STATES = "/containerStates";
}