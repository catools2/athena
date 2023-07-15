package org.catools.common.tests.verify.noretry;

import org.catools.common.extensions.verify.CVerify;
import org.catools.common.extensions.verify.hard.CStringVerification;

import java.util.function.Consumer;

public class CStringVerificationVerifyTest extends CStringVerificationBaseTest {
  @Override
  public void verify(Consumer<CStringVerification> action) {
    action.accept(CVerify.String);
  }
}
