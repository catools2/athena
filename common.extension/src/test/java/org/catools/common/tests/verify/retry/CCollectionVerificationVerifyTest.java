package org.catools.common.tests.verify.retry;

import org.catools.common.extensions.verify.CVerify;
import org.catools.common.extensions.verify.hard.CCollectionVerification;

import java.util.function.Consumer;

public class CCollectionVerificationVerifyTest extends CCollectionVerificationBaseTest {
  @Override
  public void verify(Consumer<CCollectionVerification> action) {
    action.accept(CVerify.Collection);
  }
}
