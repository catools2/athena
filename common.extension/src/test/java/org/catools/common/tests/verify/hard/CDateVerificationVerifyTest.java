package org.catools.common.tests.verify.hard;

import org.catools.common.extensions.verify.CVerify;
import org.catools.common.extensions.verify.hard.CDateVerification;
import org.catools.common.tests.verify.CDateVerificationBaseTest;

import java.util.function.Consumer;

public class CDateVerificationVerifyTest extends CDateVerificationBaseTest {
  @Override
  public void verify(Consumer<CDateVerification> action) {
    action.accept(CVerify.Date);
  }
}
