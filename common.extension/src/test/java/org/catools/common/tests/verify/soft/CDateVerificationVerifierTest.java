package org.catools.common.tests.verify.soft;

import org.catools.common.extensions.verify.CVerifier;
import org.catools.common.extensions.verify.hard.CDateVerification;
import org.catools.common.tests.verify.CDateVerificationBaseTest;

import java.util.function.Consumer;

public class CDateVerificationVerifierTest extends CDateVerificationBaseTest {
  @Override
  public void verify(Consumer<CDateVerification> action) {
    CVerifier verifier = new CVerifier();
    action.accept(verifier.Date);
    verifier.verify();
  }
}
