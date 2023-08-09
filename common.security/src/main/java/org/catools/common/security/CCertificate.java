package org.catools.common.security;

import org.catools.common.io.CFile;
import org.catools.common.io.CResource;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.function.BiFunction;

public class CCertificate {
  private CResource resource;
  private CFile file;

  public CCertificate(String certResourceName, Class classLoader) {
    this.resource = new CResource(certResourceName, classLoader);
  }

  public CCertificate(String fileName) {
    this.file = new CFile(fileName);
  }

  public Certificate getCertificate() {
    return performActionOnResource((s, inputStream) -> {
      try {
        CertificateFactory fact = CertificateFactory.getInstance("X.509");
        return fact.generateCertificate(inputStream);
      } catch (Exception e) {
        throw new CCertificateException("Failed to get Certification", e);
      }
    });
  }

  public X509Certificate getX509Certificate() {
    return (X509Certificate) getCertificate();
  }

  public SSLContext toSSLContext(String alias) {
    Certificate caCert = getCertificate();
    try {
      TrustManagerFactory trustManagerFactory =
          TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
      KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
      keyStore.load(null); // You don't need the KeyStore instance to come from a file.
      keyStore.setCertificateEntry(alias, caCert);

      trustManagerFactory.init(keyStore);

      SSLContext sslContext = SSLContext.getInstance("TLS");
      sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
      return sslContext;
    } catch (Exception e) {
      throw new CCertificateException("Failed to get Certification", e);
    }
  }

  private <T> T performActionOnResource(BiFunction<String, InputStream, T> action) {
    if (resource != null) {
      return resource.performActionOnResource(action);
    }
    return action.apply(file.getName(), file.getInputStream());
  }
}
