package org.catools.common.tests.verify.noretry;

import org.catools.common.extensions.verify.CVerifier;
import org.catools.common.extensions.verify.hard.CObjectVerification;

import java.util.function.Consumer;

public class ObjectVerificationVerifierTest extends ObjectVerificationBaseTest {
  @Override
  public void verify(Consumer<CObjectVerification> action) {
    CVerifier verifier = new CVerifier();
    action.accept(verifier.Object);
    verifier.verify();
  }
}
