package org.catools.common.tests.security;

import org.catools.common.extensions.verify.CVerify;
import org.catools.common.security.CCipher;
import org.catools.common.security.CCipherException;
import org.catools.common.tests.CBaseUnitTest;
import org.testng.annotations.Test;

public class CCipherTest extends CBaseUnitTest {
  private static final String ORIGINAL_STRING = "This is very super top secret information.";
  private static final String DECRYPTED_STRING = "88V7rr/B7oPh00vivqme1XZIbz/r+ZRzOq7LKCnqLeBLcSAp89C/0MVLOZmTKdNS";
  private static final String PASSWORD = "Password for very super top secret information.";

  @Test
  public void testDecrypt() {
    CVerify.String.equals(
        CCipher.decrypt("AES/ECB/PKCS5PADDING", "SHA-1", DECRYPTED_STRING, PASSWORD), ORIGINAL_STRING, "decryption works fine");
  }

  @Test(expectedExceptions = CCipherException.class)
  public void testDecrypt_PasswordIsNull() {
    CVerify.String.equals(
        CCipher.decrypt("AES/ECB/PKCS5PADDING", "SHA-1", DECRYPTED_STRING, null), ORIGINAL_STRING, "decryption works fine");
  }

  @Test(expectedExceptions = CCipherException.class)
  public void testDecrypt_InputIsNull() {
    CVerify.String.equals(CCipher.decrypt("AES/ECB/PKCS5PADDING", "SHA-1", null, PASSWORD), ORIGINAL_STRING, "decryption works fine");
  }

  @Test
  public void testEncrypt() {
    CVerify.String.equals(
        CCipher.encrypt("AES/ECB/PKCS5PADDING", "SHA-1", ORIGINAL_STRING, PASSWORD), DECRYPTED_STRING, "encryption works fine");
  }

  @Test(expectedExceptions = CCipherException.class)
  public void testEncrypt_PasswordIsNull() {
    CVerify.String.equals(
        CCipher.encrypt("AES/ECB/PKCS5PADDING", "SHA-1", ORIGINAL_STRING, null), DECRYPTED_STRING, "encryption works fine");
  }

  @Test(expectedExceptions = CCipherException.class)
  public void testEncrypt_InputIsNull() {
    CVerify.String.equals(
        CCipher.encrypt("AES/ECB/PKCS5PADDING", "SHA-1", null, PASSWORD), DECRYPTED_STRING, "encryption works fine");
  }
}
