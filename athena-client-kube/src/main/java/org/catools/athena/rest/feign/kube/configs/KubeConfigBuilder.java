package org.catools.athena.rest.feign.kube.configs;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.util.Config;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.rest.feign.kube.enums.KubeConnectionType;
import org.catools.athena.rest.feign.kube.exception.KubeOperationException;

import java.io.IOException;

@Slf4j
@UtilityClass
public class KubeConfigBuilder {

  /**
   * Build kubernetes API client based on provided configuration
   *
   * @return
   */
  public static CoreV1Api getKubeApiClient() {
    KubeConnectionType connectionType = StringUtils.isBlank(KubeConfigs.getConnectionType()) ?
        KubeConnectionType.DEFAULT :
        KubeConnectionType.valueOf(KubeConfigs.getConnectionType());

    ApiClient client = getConfig(connectionType);
    Configuration.setDefaultApiClient(client);
    return new CoreV1Api();
  }

  private static ApiClient getConfig(KubeConnectionType kubeConnectionType) {
    if (KubeConnectionType.CONFIG.equals(kubeConnectionType)) {
      log.info("Loading configuration from KubeConfigs");
      return fromConfig();
    }

    if (KubeConnectionType.URL.equals(kubeConnectionType)) {
      log.info("Loading configuration from URL");
      return fromUrl();
    }

    if (KubeConnectionType.TOKEN.equals(kubeConnectionType)) {
      log.info("Loading configuration from TOKEN");
      return fromToken();
    }

    if (KubeConnectionType.CREDENTIAL.equals(kubeConnectionType)) {
      log.info("Loading configuration from CREDENTIAL");
      return fromUserPassword();
    }

    log.info("Loading configuration from default KUBE configuration");
    return fromDefaultClient();

  }

  private static ApiClient fromToken() {
    return Config.fromToken(KubeConfigs.getConnectionUrl(), KubeConfigs.getConnectionToken(), KubeConfigs.getShouldValidateSSL());
  }

  private static ApiClient fromUserPassword() {
    return Config.fromUserPassword(KubeConfigs.getConnectionUrl(),
        KubeConfigs.getConnectionUsername(),
        KubeConfigs.getConnectionPassword(),
        KubeConfigs.getShouldValidateSSL());
  }

  private static ApiClient fromConfig() {
    String kubeConfigPath = KubeConfigs.getKubeConfigPath();
    try {
      log.debug("Loading configuration from KubeConfigs {}", kubeConfigPath);
      return Config.fromConfig(kubeConfigPath);
    } catch (IOException e) {
      throw new KubeOperationException("Failed to build client using the provided configuration file. configFile:" + kubeConfigPath,
          e);
    }
  }

  private static ApiClient fromDefaultClient() {
    try {
      log.debug("Loading configuration from default KUBE configuration");
      return Config.defaultClient();
    } catch (IOException e) {
      throw new KubeOperationException("Failed to build client using the default kubeconfig file.", e);
    }
  }

  private static ApiClient fromUrl() {
    String connectionUrl = KubeConfigs.getConnectionUrl();
    Boolean shouldValidateSSL = KubeConfigs.getShouldValidateSSL();
    log.debug("Loading configuration from URL {}", connectionUrl);
    return Config.fromUrl(connectionUrl, shouldValidateSSL);
  }
}
