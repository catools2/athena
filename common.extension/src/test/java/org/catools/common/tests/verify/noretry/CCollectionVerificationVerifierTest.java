package org.catools.common.tests.verify.noretry;

import org.catools.common.extensions.verify.CVerifier;
import org.catools.common.extensions.verify.hard.CCollectionVerification;

import java.util.function.Consumer;

public class CCollectionVerificationVerifierTest extends CCollectionVerificationBaseTest {
  @Override
  public void verify(Consumer<CCollectionVerification> action) {
    CVerifier verifier = new CVerifier();
    action.accept(verifier.Collection);
    verifier.verify();
  }
}
