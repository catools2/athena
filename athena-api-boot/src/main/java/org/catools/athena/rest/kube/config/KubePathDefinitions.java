package org.catools.athena.rest.kube.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class KubePathDefinitions {

  public static final String POD_PATH = "/pod";
  public static final String PODS_PATH = "/pods";

  public static final String CONTAINER_PATH = "/container";
  public static final String CONTAINERS_PATH = "/containers";

  public static final String CONTAINER_STATE_PATH = "/containerState";
  public static final String CONTAINER_STATES_PATH = "/containerStates";
}