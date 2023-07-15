package org.catools.common.tests.concurrent;

import org.assertj.core.api.Assertions;
import org.catools.common.concurrent.CThreadRunner;
import org.catools.common.utils.CDateUtil;
import org.catools.common.utils.CRetry;
import org.catools.common.utils.CSleeper;
import org.testng.annotations.Test;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

public class CThreadRunnerTest {

  @Test
  public void testRun() {
    Date start = new Date();
    AtomicBoolean threadFinishedFlag = new AtomicBoolean();
    Thread run =
        CThreadRunner.run(
            () -> {
              CSleeper.sleepTightInSeconds(5);
              threadFinishedFlag.set(true);
            });
    Assertions.assertThat(CDateUtil.getDiffToNow(start, ChronoUnit.SECONDS)).isLessThan(1L);

    CRetry.retryIfFalse(integer -> threadFinishedFlag.get(), 50, 100, null, false);

    Assertions.assertThat(CDateUtil.getDiffToNow(start, ChronoUnit.SECONDS)).isBetween(5L, 6L);
    Assertions.assertThat(run.getState()).isEqualTo(Thread.State.TERMINATED);
  }
}
