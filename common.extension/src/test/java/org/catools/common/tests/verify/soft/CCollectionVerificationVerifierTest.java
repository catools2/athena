package org.catools.common.tests.verify.soft;

import org.catools.common.extensions.verify.CVerifier;
import org.catools.common.extensions.verify.hard.CCollectionVerification;
import org.catools.common.tests.verify.CCollectionVerificationBaseTest;

import java.util.function.Consumer;

public class CCollectionVerificationVerifierTest extends CCollectionVerificationBaseTest {
  @Override
  public void verify(Consumer<CCollectionVerification> action) {
    CVerifier verifier = new CVerifier();
    action.accept(verifier.Collection);
    verifier.verify();
  }
}
