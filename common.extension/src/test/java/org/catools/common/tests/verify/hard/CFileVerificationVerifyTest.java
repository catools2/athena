package org.catools.common.tests.verify.hard;

import org.catools.common.extensions.verify.CVerify;
import org.catools.common.extensions.verify.hard.CFileVerification;
import org.catools.common.tests.verify.CFileVerificationBaseTest;

import java.util.function.Consumer;

public class CFileVerificationVerifyTest extends CFileVerificationBaseTest {
  public CFileVerificationVerifyTest() {
    super("V2");
  }

  @Override
  public void verify(Consumer<CFileVerification> action) {
    action.accept(CVerify.File);
  }
}
