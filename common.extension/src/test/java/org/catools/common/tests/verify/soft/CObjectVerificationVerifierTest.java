package org.catools.common.tests.verify.soft;

import org.catools.common.extensions.verify.CVerifier;
import org.catools.common.extensions.verify.hard.CObjectVerification;
import org.catools.common.tests.verify.CObjectVerificationBaseTest;

import java.util.function.Consumer;

public class CObjectVerificationVerifierTest extends CObjectVerificationBaseTest {
  @Override
  public void verify(Consumer<CObjectVerification> action) {
    CVerifier verifier = new CVerifier();
    action.accept(verifier.Object);
    verifier.verify();
  }
}
