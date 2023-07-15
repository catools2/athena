package org.catools.common.security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class CCipher {
  public static String decrypt(String transformation, String algorithm, String strToDecrypt, String secret) {
    try {
      Cipher cipher = Cipher.getInstance(transformation);
      cipher.init(Cipher.DECRYPT_MODE, getEncryptionKey(secret, algorithm));
      return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    } catch (Exception e) {
      throw new CCipherException("decrypt", transformation, strToDecrypt, e);
    }
  }

  public static String encrypt(String transformation, String algorithm, String strToEncrypt, String secret) {
    try {
      Cipher cipher = Cipher.getInstance(transformation);
      cipher.init(Cipher.ENCRYPT_MODE, getEncryptionKey(secret, algorithm));
      return Base64.getEncoder()
          .encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
    } catch (Exception e) {
      throw new CCipherException("encrypt", transformation, strToEncrypt, e);
    }
  }

  private static SecretKeySpec getEncryptionKey(String myKey, String algorithm) throws NoSuchAlgorithmException {
    MessageDigest sha;
    byte[] key = myKey.getBytes(StandardCharsets.UTF_8);
    sha = MessageDigest.getInstance(algorithm);
    key = sha.digest(key);
    key = Arrays.copyOf(key, 16);
    return new SecretKeySpec(key, "AES");
  }
}
