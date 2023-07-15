package org.catools.common.tests.security;

import org.catools.common.configs.CPathConfigs;
import org.catools.common.exception.CResourceNotFoundException;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.io.CResource;
import org.catools.common.security.CCertificate;
import org.catools.common.security.CCertificateException;
import org.catools.common.tests.CBaseUnitTest;
import org.testng.annotations.Test;

import javax.net.ssl.SSLContext;
import java.security.cert.X509Certificate;

public class CCertificateTest extends CBaseUnitTest {

  private final CResource VALID_RESOURCCE =
      new CResource("testData/CCertification/X509_catools.cer", CCertificateTest.class);
  private final CResource INVALID_RESOURCCE =
      new CResource("testData/CCertification/invalid.cer", CCertificateTest.class);
  private final CCertificate VALID_FROM_FILE;
  private final CCertificate INVALID_FROM_FILE;

  public CCertificateTest() {
    super();
    VALID_FROM_FILE = new CCertificate(VALID_RESOURCCE.saveToFolder(CPathConfigs.getTempFolder()).getCanonicalPath());
    INVALID_FROM_FILE = new CCertificate(INVALID_RESOURCCE.saveToFolder(CPathConfigs.getTempFolder()).getCanonicalPath());
  }

  @Test
  public void testGetX509Certificate() {
    X509Certificate cert =
        new CCertificate("testData/CCertification/X509_catools.cer", CCertificateTest.class)
            .getX509Certificate();
    CVerify.String.contains(
        cert.getSubjectX500Principal().getName(),
        "OU=CATS,O=CAF,C=US",
        "Subject information is correct");
    CVerify.String.contains(
        cert.getIssuerDN().getName(), "OU=CATS, O=CAF, C=US", "Issuer DN information is correct");
  }

  @Test(expectedExceptions = CResourceNotFoundException.class)
  public void testGetX509Certificate_InvalidResource() {
    new CCertificate("testData/CCertification/invalid.cer", CCertificateTest.class)
        .getX509Certificate();
  }

  @Test
  public void testToSSLContext() {
    SSLContext sslContext = VALID_FROM_FILE.toSSLContext("catools");
    CVerify.Object.isNotNull(sslContext, "SSLContext has been generated");
  }

  @Test(expectedExceptions = CCertificateException.class)
  public void testToSSLContext_InvalidAlias() {
    VALID_FROM_FILE.toSSLContext(null);
  }

  @Test(expectedExceptions = CCertificateException.class)
  public void testToSSLContext_InvalidCert() {
    INVALID_FROM_FILE.toSSLContext("catools");
  }
}
