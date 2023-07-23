package org.catools.common.tests.verify.soft;

import org.catools.common.extensions.verify.CVerifier;
import org.catools.common.extensions.verify.hard.CNumberVerification;
import org.catools.common.tests.verify.CNumberVerificationBaseTest;

import java.math.BigDecimal;
import java.util.function.Consumer;

public class CNumberVerificationVerifierTest extends CNumberVerificationBaseTest {
  @Override
  public void verifyBigDecimal(Consumer<CNumberVerification<BigDecimal>> action) {
    CVerifier verifier = new CVerifier();
    action.accept(verifier.BigDecimal);
    verifier.verify();
  }

  @Override
  public void verifyDouble(Consumer<CNumberVerification<Double>> action) {
    CVerifier verifier = new CVerifier();
    action.accept(verifier.Double);
    verifier.verify();
  }

  @Override
  public void verifyInt(Consumer<CNumberVerification<Integer>> action) {
    CVerifier verifier = new CVerifier();
    action.accept(verifier.Int);
    verifier.verify();
  }

  @Override
  public void verifyLong(Consumer<CNumberVerification<Long>> action) {
    CVerifier verifier = new CVerifier();
    action.accept(verifier.Long);
    verifier.verify();
  }
}
