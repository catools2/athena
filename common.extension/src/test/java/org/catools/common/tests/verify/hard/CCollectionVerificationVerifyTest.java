package org.catools.common.tests.verify.hard;

import org.catools.common.extensions.verify.CVerify;
import org.catools.common.extensions.verify.hard.CCollectionVerification;
import org.catools.common.tests.verify.CCollectionVerificationBaseTest;

import java.util.function.Consumer;

public class CCollectionVerificationVerifyTest extends CCollectionVerificationBaseTest {
  @Override
  public void verify(Consumer<CCollectionVerification> action) {
    action.accept(CVerify.Collection);
  }
}
