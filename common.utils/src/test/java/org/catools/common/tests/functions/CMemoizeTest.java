package org.catools.common.tests.functions;

import org.assertj.core.api.Assertions;
import org.catools.common.functions.CMemoize;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class CMemoizeTest {

  @Test
  public void testGet() {
    AtomicInteger integer = new AtomicInteger(1);
    CMemoize<Integer> memoize = new CMemoize<>(() -> integer.incrementAndGet());
    Assertions.assertThat(memoize.get()).describedAs("CMemoize call triggered").isEqualTo(2);
    Assertions.assertThat(memoize.get())
        .describedAs("CMemoize call does not trigger second time")
        .isEqualTo(2);
    memoize.reset();
    Assertions.assertThat(memoize.get())
        .describedAs("CMemoize call triggered second time after reset")
        .isEqualTo(3);
    Assertions.assertThat(memoize.get())
        .describedAs("CMemoize call Does not trigger any more")
        .isEqualTo(3);
  }
}
