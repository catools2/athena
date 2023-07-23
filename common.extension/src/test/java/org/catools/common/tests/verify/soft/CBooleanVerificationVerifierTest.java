package org.catools.common.tests.verify.soft;

import org.catools.common.extensions.verify.CVerifier;
import org.catools.common.extensions.verify.hard.CBooleanVerification;
import org.catools.common.tests.verify.CBooleanVerificationBaseTest;

import java.util.function.Consumer;

public class CBooleanVerificationVerifierTest extends CBooleanVerificationBaseTest {
  @Override
  public void verify(Consumer<CBooleanVerification> action) {
    CVerifier verifier = new CVerifier();
    action.accept(verifier.Bool);
    verifier.verify();
  }
}
