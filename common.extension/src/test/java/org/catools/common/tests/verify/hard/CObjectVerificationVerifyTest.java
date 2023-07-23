package org.catools.common.tests.verify.hard;

import org.catools.common.extensions.verify.CVerify;
import org.catools.common.extensions.verify.hard.CObjectVerification;
import org.catools.common.tests.verify.CObjectVerificationBaseTest;

import java.util.function.Consumer;

public class CObjectVerificationVerifyTest extends CObjectVerificationBaseTest {
  @Override
  public void verify(Consumer<CObjectVerification> action) {
    action.accept(CVerify.Object);
  }
}
