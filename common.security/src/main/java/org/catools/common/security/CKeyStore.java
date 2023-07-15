package org.catools.common.security;

import org.catools.common.io.CFile;
import org.catools.common.utils.CResourceUtil;

import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

public class CKeyStore {
  private KeyStore keystore;

  public CKeyStore(String resourceName, Class classForLoader, String keystorePassword) {
    CResourceUtil.performActionOnResource(resourceName, classForLoader, (s, inputStream) -> {
      try {
        keystore = KeyStore.getInstance("JKS");
        keystore.load(inputStream, keystorePassword.toCharArray());
        inputStream.close();
      } catch (Throwable e) {
        throw new CKeyStoreException("Failed to initialize keystore", e);
      }
      return null;
    });
  }

  public CKeyStore(CFile keystoreFile, String keystorePassword) {
    this(keystoreFile.getInputStream(), keystorePassword);
  }

  public CKeyStore(InputStream readStream, String keystorePassword) {
    try {
      keystore = KeyStore.getInstance("JKS");
      keystore.load(readStream, keystorePassword.toCharArray());
      readStream.close();
    } catch (Throwable e) {
      throw new CKeyStoreException("Failed to initialize keystore", e);
    }
  }

  public CKeyStore(KeyStore keystore) {
    this.keystore = keystore;
  }

  public KeyStore getKeystore() {
    return keystore;
  }

  public X509Certificate getX509Certificate(String alias) {
    return (X509Certificate) getCertificate(alias);
  }

  public Certificate getCertificate(String alias) {
    try {
      return keystore.getCertificate(alias);
    } catch (KeyStoreException e) {
      throw new CKeyStoreException("Failed to get certification from keystore.", e);
    }
  }

  public <T extends Key> T getPrivate(String alias, String aliasPassword) {
    try {
      return (T) keystore.getKey(alias, aliasPassword.toCharArray());
    } catch (Throwable e) {
      throw new CKeyStoreException("Failed to get privet key from keystore.", e);
    }
  }

  public <T extends PublicKey> T getPublic(String alias) {
    return (T) getCertificate(alias).getPublicKey();
  }
}
