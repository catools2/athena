package org.catools.common.tests.functions;

import org.assertj.core.api.Assertions;
import org.catools.common.functions.CAutoResetMemoize;
import org.catools.common.utils.CSleeper;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class CAutoResetMemoizeTest {

  @Test
  public void testGet() {
    AtomicInteger integer = new AtomicInteger(1);
    CAutoResetMemoize<Integer> memoize = new CAutoResetMemoize<>(1, integer::incrementAndGet);
    Assertions.assertThat(memoize.get()).describedAs("CMemoize call triggered").isEqualTo(2);
    Assertions.assertThat(memoize.get())
        .describedAs("CMemoize call does not trigger second time")
        .isEqualTo(2);
    CSleeper.sleepTightInSeconds(2);
    Assertions.assertThat(memoize.get())
        .describedAs("CMemoize call triggered second time after reset")
        .isEqualTo(3);
    Assertions.assertThat(memoize.get())
        .describedAs("CMemoize call Does not trigger any more")
        .isEqualTo(3);
  }
}
