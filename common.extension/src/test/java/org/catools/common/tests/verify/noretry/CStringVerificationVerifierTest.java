package org.catools.common.tests.verify.noretry;

import org.catools.common.extensions.verify.CVerifier;
import org.catools.common.extensions.verify.hard.CStringVerification;

import java.util.function.Consumer;

public class CStringVerificationVerifierTest extends CStringVerificationBaseTest {
  @Override
  public void verify(Consumer<CStringVerification> action) {
    CVerifier verifier = new CVerifier();
    action.accept(verifier.String);
    verifier.verify();
  }
}
