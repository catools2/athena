package org.catools.vault.model;

public enum CVaultAuthType {
  /**
   * User token for auth process
   */
  TOKEN,
  /**
   * User ldap username and password for auth process
   */
  LDAP,
  /**
   * User approle_id and secret_id for auth process
   */
  APP_ROLE;
}
