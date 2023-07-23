package org.catools.common.extensions.verify.hard;

import lombok.extern.slf4j.Slf4j;
import org.catools.common.extensions.verify.interfaces.base.CFileVerify;
import org.catools.common.io.CFile;

import java.io.File;

/**
 * File Content verification class contains all verification method which is related to File and its
 * content
 */
@Slf4j
public class CFileVerification extends CBaseVerification {

  /**
   * Verify that actual and expected file have the exact same content.
   *
   * @param actualFile   file full name to compare
   * @param expectedFile file full name to compare
   */
  public void equalsStringContent(final String actualFile, final String expectedFile) {
    equalsStringContent(new CFile(actualFile), new CFile(expectedFile));
  }

  /**
   * Verify that actual and expected file have the exact same content.
   *
   * @param actualFile   file full name to compare
   * @param expectedFile file full name to compare
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  public void equalsStringContent(final String actualFile, final String expectedFile, final String message, final Object... params) {
    equalsStringContent(new CFile(actualFile), new CFile(expectedFile), message, params);
  }

  /**
   * Verify that actual and expected file have the exact same content.
   *
   * @param actualFile   file to compare
   * @param expectedFile file to compare
   */
  public void equalsStringContent(final CFile actualFile, final CFile expectedFile) {
    toVerifier(actualFile).verifyEqualsStringContent(expectedFile);
  }

  /**
   * Verify that actual and expected file have the exact same content.
   *
   * @param actualFile   file to compare
   * @param expectedFile file to compare
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  public void equalsStringContent(final CFile actualFile, final CFile expectedFile, final String message, final Object... params) {
    toVerifier(actualFile).verifyEqualsStringContent(expectedFile, message, params);
  }

  /**
   * Verify that the file exists
   *
   * @param actualFile file full name to compare
   */
  public void exists(final String actualFile) {
    toVerifier(new File(actualFile)).verifyExists();
  }

  /**
   * Verify that the file exists
   *
   * @param actualFile file full name to compare
   * @param message    information about the purpose of this verification
   * @param params     parameters in case if message is a format {@link String#format}
   */
  public void exists(final String actualFile, final String message, final Object... params) {
    toVerifier(new File(actualFile)).verifyExists(message, params);
  }

  /**
   * Verify that the file exists
   *
   * @param actualFile file full name to compare
   */
  public void exists(final CFile actualFile) {
    toVerifier(actualFile).verifyExists();
  }

  /**
   * Verify that the file exists
   *
   * @param actualFile file full name to compare
   * @param message    information about the purpose of this verification
   * @param params     parameters in case if message is a format {@link String#format}
   */
  public void exists(final CFile actualFile, final String message, final Object... params) {
    toVerifier(actualFile).verifyExists(message, params);
  }

  /**
   * Verify that actual and expected file does not have the exact same content.
   *
   * @param actualFile   file full name to compare
   * @param expectedFile file full name to compare
   */
  public void notEqualsStringContent(final String actualFile, final String expectedFile) {
    notEqualsStringContent(new CFile(actualFile), new CFile(expectedFile));
  }

  /**
   * Verify that actual and expected file does not have the exact same content.
   *
   * @param actualFile   file full name to compare
   * @param expectedFile file full name to compare
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  public void notEqualsStringContent(final String actualFile, final String expectedFile, final String message, final Object... params) {
    notEqualsStringContent(new CFile(actualFile), new CFile(expectedFile), message, params);
  }

  /**
   * Verify that actual and expected file does not have the exact same content.
   *
   * @param actualFile   file to compare
   * @param expectedFile file to compare
   */
  public void notEqualsStringContent(final CFile actualFile, final CFile expectedFile) {
    toVerifier(actualFile).verifyNotEqualsStringContent(expectedFile);
  }

  /**
   * Verify that actual and expected file does not have the exact same content.
   *
   * @param actualFile   file to compare
   * @param expectedFile file to compare
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  public void notEqualsStringContent(final CFile actualFile, final CFile expectedFile, final String message, final Object... params) {
    toVerifier(actualFile).verifyNotEqualsStringContent(expectedFile, message, params);
  }

  /**
   * Verify that the file does not exist
   *
   * @param actualFile file full name to compare
   */
  public void notExists(final String actualFile) {
    toVerifier(new File(actualFile)).verifyIsNotExists();
  }

  /**
   * Verify that the file does not exist
   *
   * @param actualFile file full name to compare
   * @param message    information about the purpose of this verification
   * @param params     parameters in case if message is a format {@link String#format}
   */
  public void notExists(final String actualFile, final String message, final Object... params) {
    toVerifier(new File(actualFile)).verifyIsNotExists(message, params);
  }

  /**
   * Verify that the file does not exist
   *
   * @param actualFile file full name to compare
   */
  public void notExists(final CFile actualFile) {
    toVerifier(actualFile).verifyIsNotExists();
  }

  /**
   * Verify that the file does not exist
   *
   * @param actualFile file full name to compare
   * @param message    information about the purpose of this verification
   * @param params     parameters in case if message is a format {@link String#format}
   */
  public void notExists(final CFile actualFile, final String message, final Object... params) {
    toVerifier(actualFile).verifyIsNotExists(message, params);
  }

  protected CFileVerify toVerifier(File actual) {
    return () -> actual;
  }
}
