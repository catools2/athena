package org.catools.common.tests.wait;

import org.catools.common.extensions.verify.CVerify;
import org.catools.common.extensions.verify.hard.CBooleanVerification;

import java.util.function.Consumer;

public class CNumberWaiterTest extends CNumberBaseWaiterTest {
  @Override
  public void verify(Consumer<CBooleanVerification> action) {
    action.accept(CVerify.Bool);
  }
}
