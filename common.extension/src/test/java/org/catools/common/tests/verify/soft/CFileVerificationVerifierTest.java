package org.catools.common.tests.verify.soft;

import org.catools.common.extensions.verify.CVerifier;
import org.catools.common.extensions.verify.hard.CFileVerification;
import org.catools.common.tests.verify.CFileVerificationBaseTest;

import java.util.function.Consumer;

public class CFileVerificationVerifierTest extends CFileVerificationBaseTest {
  public CFileVerificationVerifierTest() {
    super("V1");
  }

  @Override
  public void verify(Consumer<CFileVerification> action) {
    CVerifier verifier = new CVerifier();
    action.accept(verifier.File);
    verifier.verify();
  }
}
