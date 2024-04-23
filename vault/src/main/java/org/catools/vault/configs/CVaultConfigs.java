package org.catools.vault.configs;

import com.bettercloud.vault.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;
import org.catools.vault.exception.CVaultAuthenticationException;
import org.catools.vault.exception.CVaultOperationException;
import org.catools.vault.model.CVault;
import org.catools.vault.model.VaultAuthType;

import java.io.File;
import java.util.Objects;

public class CVaultConfigs {

  public static String getUrl() {
    return CHocon.asString(Configs.CATOOLS_VAULT_URL);
  }

  public static String getPath() {
    return CHocon.asString(Configs.CATOOLS_VAULT_PATH);
  }

  public static String getKeyPrefix() {
    return CHocon.asString(Configs.CATOOLS_VAULT_KEY_PREFIX);
  }

  public static int getOpenTimeout() {
    return CHocon.asInteger(Configs.CATOOLS_VAULT_OPEN_TIMEOUT);
  }

  public static int getReadTimeout() {
    return CHocon.asInteger(Configs.CATOOLS_VAULT_READ_TIMEOUT);
  }

  public static VaultAuthType getAuthType() {
    return CHocon.asEnum(Configs.CATOOLS_VAULT_AUTH_TYPE, VaultAuthType.class);
  }

  public static String getAuthToken() {
    return CHocon.asString(Configs.CATOOLS_VAULT_AUTH_TOKEN);
  }

  public static String getAuthLdapUsername() {
    return CHocon.asString(Configs.CATOOLS_VAULT_AUTH_LDAP_USERNAME);
  }

  public static String getAuthLdapPassword() {
    return CHocon.asString(Configs.CATOOLS_VAULT_AUTH_LDAP_PASSWORD);
  }

  public static String getAuthAppRoleRoleId() {
    return CHocon.asString(Configs.CATOOLS_VAULT_AUTH_APP_ROLE_ROLE_ID);
  }

  public static String getAuthAppRoleSecretId() {
    return CHocon.asString(Configs.CATOOLS_VAULT_AUTH_APP_ROLE_SECRET_ID);
  }

  public static String getTlsCertFile() {
    return CHocon.asString(Configs.CATOOLS_VAULT_TLS_CERT_FILE);
  }

  public static String getTlsCertString() {
    return CHocon.asString(Configs.CATOOLS_VAULT_TLS_CERT_STRING);
  }


  public static VaultConfig getVaultBaseConfig() {
    try {
      return new VaultConfig().address(CVaultConfigs.getUrl())
                              .openTimeout(getOpenTimeout())
                              .readTimeout(getReadTimeout())
                              .sslConfig(getSslConfig())
                              .build();
    }
    catch (VaultException e) {
      throw new CVaultOperationException("Build vault config.", e);
    }
  }

  public static SslConfig getSslConfig() {
    SslConfig sslConfig = new SslConfig();
    try {
      if (!StringUtils.isBlank(CVaultConfigs.getTlsCertFile())) {
        sslConfig.pemFile(new File(CVaultConfigs.getTlsCertFile()));
      }
      else if (!StringUtils.isBlank(CVaultConfigs.getTlsCertString())) {
        sslConfig.pemUTF8(CVaultConfigs.getTlsCertString());
      }
      return sslConfig.verify(true).build();
    }
    catch (VaultException e) {
      throw new CVaultOperationException("Build ssl config.", e);
    }
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_VAULT_URL("catools.vault.url"),
    CATOOLS_VAULT_PATH("catools.vault.path"),
    CATOOLS_VAULT_KEY_PREFIX("catools.vault.key_prefix"),
    CATOOLS_VAULT_OPEN_TIMEOUT("catools.vault.open_timeout"),
    CATOOLS_VAULT_READ_TIMEOUT("catools.vault.read_timeout"),
    CATOOLS_VAULT_AUTH_TYPE("catools.vault.auth.type"),
    CATOOLS_VAULT_AUTH_TOKEN("catools.vault.auth.token"),
    CATOOLS_VAULT_AUTH_LDAP_USERNAME("catools.vault.auth.ldap.username"),
    CATOOLS_VAULT_AUTH_LDAP_PASSWORD("catools.vault.auth.ldap.password"),
    CATOOLS_VAULT_AUTH_APP_ROLE_ROLE_ID("catools.vault.auth.app_role.role_id"),
    CATOOLS_VAULT_AUTH_APP_ROLE_SECRET_ID("catools.vault.auth.app_role.secret_id"),
    CATOOLS_VAULT_TLS_CERT_FILE("catools.vault.tls.cert_file"),
    CATOOLS_VAULT_TLS_CERT_STRING("catools.vault.tls.cert_string");

    private final String path;
  }
}
