package org.catools.common.tests.verify.noretry;

import org.catools.common.extensions.verify.CVerifier;
import org.catools.common.extensions.verify.hard.CFileVerification;

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
