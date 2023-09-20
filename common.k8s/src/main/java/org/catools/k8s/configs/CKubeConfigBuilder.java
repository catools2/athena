package org.catools.k8s.configs;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.util.Config;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.catools.k8s.enums.CKubeConnectionType;
import org.catools.k8s.exception.CKubeOperationException;

import java.io.IOException;

@Slf4j
@UtilityClass
public class CKubeConfigBuilder {

  /**
   * Build kubernetes API client based on provided configuration
   *
   * @return
   */
  public static CoreV1Api getKubeApiClient() {
    return getKubeApiClient(getConfig());
  }

  /**
   * Build kubernetes API client based on provided configuration
   *
   * @param client
   * @return
   */
  public static CoreV1Api getKubeApiClient(ApiClient client) {
    Configuration.setDefaultApiClient(client);
    return new CoreV1Api();
  }

  private static ApiClient getConfig() {
    CKubeConnectionType kubeConnectionType = CKubeConfig.getConnectionType();
    return switch (kubeConnectionType) {
      case CONFIG -> fromConfig();
      case URL -> fromUrl();
      case TOKEN -> fromToken();
      case CREDENTIAL -> fromUserPassword();
      default -> fromDefaultClient();
    };
  }

  private static ApiClient fromToken() {
    return Config.fromToken(CKubeConfig.getConnectionUrl(), CKubeConfig.getToken(), CKubeConfig.shouldValidateSSL());
  }

  private static ApiClient fromUserPassword() {
    return Config.fromUserPassword(CKubeConfig.getConnectionUrl(), CKubeConfig.getUsername(), CKubeConfig.getPassword(), CKubeConfig.shouldValidateSSL());
  }

  private static ApiClient fromConfig() {
    try {
      return Config.fromConfig(CKubeConfig.getKubeConfigPath());
    } catch (IOException e) {
      throw new CKubeOperationException(
          "Failed to build client using the provided configuration file. configFile:" + CKubeConfig.getKubeConfigPath(),
          e);
    }
  }

  private static ApiClient fromDefaultClient() {
    try {
      return Config.defaultClient();
    } catch (IOException e) {
      throw new CKubeOperationException("Failed to build client using the default kubeconfig file.", e);
    }
  }

  private static ApiClient fromUrl() {
    return Config.fromUrl(CKubeConfig.getConnectionUrl(), CKubeConfig.shouldValidateSSL());
  }
}
