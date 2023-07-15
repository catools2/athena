package org.catools.common.tests.verify;

import org.catools.common.extensions.verify.CVerifier;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

public class CVerifierTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerify() {
    CVerifier verifier = new CVerifier();
    verifier.Int.equals(1, 1, "Right");
    verifier.String.equals("input", "input", "Right");
    verifier.Bool.equals(true, true, "Right");
    verifier.Object.isNull(null, "Right");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyWithHeader() {
    CVerifier verifier = new CVerifier();
    verifier.Int.equals(1, 1, "Right");
    verifier.String.equals("input", "input", "Right");
    verifier.Bool.equals(true, true, "Right");
    verifier.Object.isNull(null, "Right");
    verifier.verify("Header");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyWithHeader_Failed() {
    CVerifier verifier = new CVerifier();
    verifier.Int.equals(1, 2, "Wrong");
    verifier.String.equals("input", "input", "Right");
    verifier.Bool.equals(true, true, "Right");
    verifier.Object.isNull(null, "Right");
    verifier.verify("Header");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyAny() {
    CVerifier verifier = new CVerifier();
    verifier.Int.equals(1, 2, "Wrong");
    verifier.String.equals("input", "input2", "Wrong");
    verifier.Bool.equals(true, false, "Wrong");
    verifier.Object.isNull(null, "Only Match");
    verifier.verifyAny();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyAnyWithHeader() {
    CVerifier verifier = new CVerifier();
    verifier.Int.equals(1, 2, "Wrong");
    verifier.String.equals("input", "input2", "Wrong");
    verifier.Bool.equals(true, false, "Wrong");
    verifier.Object.isNull(null, "Only Match");
    verifier.verifyAny("Header");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyAnyWithHeader_Failed() {
    CVerifier verifier = new CVerifier();
    verifier.Int.equals(1, 2, "Wrong");
    verifier.String.equals("input", "inpt2", "Wrong");
    verifier.Bool.equals(true, false, "Wrong");
    verifier.Object.isNotNull(null, "Only Match");
    verifier.verifyAny("Header");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyNone() {
    CVerifier verifier = new CVerifier();
    verifier.Int.equals(1, 2, "Wrong");
    verifier.String.equals("input", "input2", "Wrong");
    verifier.Bool.equals(true, false, "Wrong");
    verifier.Object.isNotNull(null, "Wrong");
    verifier.verifyNone();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyNoneWithHeader() {
    CVerifier verifier = new CVerifier();
    verifier.Int.equals(1, 2, "Wrong");
    verifier.String.equals("input", "input2", "Wrong");
    verifier.Bool.equals(true, false, "Wrong");
    verifier.Object.isNotNull(null, "Wrong");
    verifier.verifyNone("Header");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyNoneWithHeader_Failed() {
    CVerifier verifier = new CVerifier();
    verifier.Int.equals(1, 1, "Right");
    verifier.String.equals("input", "input2", "Wrong");
    verifier.Bool.equals(true, false, "Wrong");
    verifier.Object.isNotNull(null, "Wrong");
    verifier.verifyNone("Header");
  }
}
