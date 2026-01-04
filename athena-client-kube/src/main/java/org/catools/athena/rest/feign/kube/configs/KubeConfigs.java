package org.catools.athena.rest.feign.kube.configs;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import org.catools.athena.rest.feign.common.configs.ConfigUtils;
import org.catools.athena.rest.feign.kube.enums.KubeConnectionType;

import java.util.List;

/**
 * Kubernetes' configuration including connection parameters.
 */
@UtilityClass
public class KubeConfigs {

  static {
    reload();
  }

  @Setter
  @Getter
  private static List<String> namespaces;

  @Setter
  @Getter
  private static String connectionType;

  @Setter
  @Getter
  private static Boolean shouldValidateSSL;

  @Setter
  @Getter
  private static String connectionUrl;

  @Setter
  @Getter
  private static String connectionUsername;

  @Setter
  @Getter
  private static String connectionPassword;

  @Setter
  @Getter
  private static String connectionToken;

  @Setter
  @Getter
  private static String kubeConfigPath;

  public static void reload() {
    namespaces = ConfigUtils.getStrings("athena.kube.namespaces", List.of("default"));
    connectionType = ConfigUtils.getString("athena.kube.connection.type", KubeConnectionType.DEFAULT.name());
    shouldValidateSSL = ConfigUtils.getBoolean("athena.kube.connection.validate_ssl", false);
    connectionUrl = ConfigUtils.getString("athena.kube.connection.url", KubeConnectionType.DEFAULT.name());
    connectionUsername = ConfigUtils.getString("athena.kube.connection.username");
    connectionPassword = ConfigUtils.getString("athena.kube.connection.password");
    connectionToken = ConfigUtils.getString("athena.kube.connection.token");
    kubeConfigPath = ConfigUtils.getString("athena.kube.connection.kube_config_path");
  }

}
