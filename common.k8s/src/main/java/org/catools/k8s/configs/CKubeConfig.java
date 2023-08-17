package org.catools.k8s.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;
import org.catools.k8s.enums.CKubeConnectionType;

/**
 * Kubernetes configuration including connection parameters.
 */
@UtilityClass
public class CKubeConfig {

  /**
   * Connection type for k8s api client.
   * it can be one of {@link CKubeConnectionType#values()} values
   * @return
   */
  public static CKubeConnectionType getConnectionType() {
    return CHocon.asEnum(Configs.CATOOLS_K8S_CONNECTION_TYPE, CKubeConnectionType.class);
  }

  /**
   * Indicate if the client should validate SSL during interaction with server
   *
   * @return
   */
  public static boolean shouldValidateSSL() {
    return CHocon.has(Configs.CATOOLS_K8S_CONNECTION_VALIDATE_SSL) ?
        CHocon.asBoolean(Configs.CATOOLS_K8S_CONNECTION_VALIDATE_SSL) :
        false;
  }

  /**
   * The connection url
   * @return
   */
  public static String getConnectionUrl() {
    return CHocon.asString(Configs.CATOOLS_K8S_CONNECTION_URL);
  }

  /**
   * User name for connection if needed
   * @return
   */
  public static String getUsername() {
    return CHocon.asString(Configs.CATOOLS_K8S_CONNECTION_USERNAME);
  }

  /**
   * User password for connection if needed
   * @return
   */
  public static String getPassword() {
    return CHocon.asString(Configs.CATOOLS_K8S_CONNECTION_PASSWORD);
  }

  /**
   * Secure token for connection if needed
   * @return
   */
  public static String getToken() {
    return CHocon.asString(Configs.CATOOLS_K8S_CONNECTION_TOKEN);
  }

  /**
   * Path to kubeconfig file
   * @return
   */
  public static String getKubeConfigPath() {
    return CHocon.asString(Configs.CATOOLS_K8S_CONNECTION_KUBE_CONFIG_PATH);
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_K8S_CONNECTION_TYPE("catools.k8s.connection.type"),
    CATOOLS_K8S_CONNECTION_URL("catools.k8s.connection.url"),
    CATOOLS_K8S_CONNECTION_VALIDATE_SSL("catools.k8s.connection.validate_ssl"),
    CATOOLS_K8S_CONNECTION_USERNAME("catools.k8s.connection.username"),
    CATOOLS_K8S_CONNECTION_PASSWORD("catools.k8s.connection.password"),
    CATOOLS_K8S_CONNECTION_TOKEN("catools.k8s.connection.token"),
    CATOOLS_K8S_CONNECTION_KUBE_CONFIG_PATH("catools.k8s.connection.kube_config_path");

    private final String path;
  }
}
