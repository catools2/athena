package org.catools.athena.rest.feign.kube.enums;

/**
 * Kubernetes connection types
 */
public enum KubeConnectionType {
  /**
   * The default connection using the default kubectl config file from default position
   * using file path defined in ENV_KUBECONFIG environment variable.
   * No additional configuration is needed for this type of connection.
   */
  DEFAULT,
  /**
   * Using the host url to setup the connection.
   * You should set the connection url by setting following variables:
   * CATOOLS_K8S_CONNECTION_URL
   * CATOOLS_K8S_CONNECTION_VALIDATE_SSL (true/false)
   */
  URL,
  /**
   * Using the username and password to setup the connection.
   * You should set the connection url by setting following variables:
   * CATOOLS_K8S_CONNECTION_URL
   * CATOOLS_K8S_CONNECTION_USERNAME
   * CATOOLS_K8S_CONNECTION_PASSWORD
   * CATOOLS_K8S_CONNECTION_VALIDATE_SSL (true/false)
   */
  CREDENTIAL,
  /**
   * Using the kubernetes token to setup the connection.
   * You should set the connection url by setting following variables:
   * CATOOLS_K8S_CONNECTION_URL
   * CATOOLS_K8S_CONNECTION_TOKEN
   * CATOOLS_K8S_CONNECTION_VALIDATE_SSL (true/false)
   */
  TOKEN,
  /**
   * Using the kube configuration file to setup the connection.
   * You should set the connection url by setting following variables:
   * CATOOLS_K8S_CONNECTION_KUBE_CONFIG_PATH
   * CATOOLS_K8S_CONNECTION_VALIDATE_SSL (true/false)
   */
  CONFIG
}
