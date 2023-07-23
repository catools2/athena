package org.catools.common.tests.waitVerifier;

import org.catools.common.extensions.verify.CVerificationQueue;
import org.catools.common.extensions.verify.CVerifier;
import org.catools.common.tests.CBaseUnitTest;

import java.util.function.Consumer;

public class CBaseWaitVerifyTest extends CBaseUnitTest {
  protected static void verify(Consumer<CVerificationQueue> action) {
    CVerifier verifier = new CVerifier();
    action.accept(verifier);
    verifier.verify();
  }

}
