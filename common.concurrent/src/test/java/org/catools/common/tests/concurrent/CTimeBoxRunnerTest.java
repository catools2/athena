package org.catools.common.tests.concurrent;

import org.assertj.core.api.Assertions;
import org.catools.common.concurrent.CTimeBoxRunner;
import org.catools.common.concurrent.exceptions.CThreadTimeoutException;
import org.catools.common.utils.CDateUtil;
import org.catools.common.utils.CSleeper;
import org.testng.annotations.Test;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

public class CTimeBoxRunnerTest {

  @Test
  public void testGet_FinishByJobDone() {
    Date start = new Date();
    AtomicBoolean threadFinishedFlag = new AtomicBoolean();
    CTimeBoxRunner.get(
        () -> {
          CSleeper.sleepTightInSeconds(2);
          threadFinishedFlag.set(true);
          return true;
        },
        5);
    Assertions.assertThat(CDateUtil.getDiffToNow(start, ChronoUnit.SECONDS)).isBetween(2L, 3L);
  }

  @Test
  public void testGet_FinishByJobDoneWithoutThrowingException() {
    Date start = new Date();
    AtomicBoolean threadFinishedFlag = new AtomicBoolean();
    CTimeBoxRunner.get(
        () -> {
          CSleeper.sleepTightInSeconds(2);
          threadFinishedFlag.set(true);
          return true;
        },
        5,
        true);
    Assertions.assertThat(CDateUtil.getDiffToNow(start, ChronoUnit.SECONDS)).isBetween(2L, 3L);
  }

  @Test
  public void testGet_FinishByTimeoutWithoutThrowingException() {
    Date start = new Date();
    AtomicBoolean threadFinishedFlag = new AtomicBoolean();
    CTimeBoxRunner.get(
        () -> {
          CSleeper.sleepTightInSeconds(5);
          threadFinishedFlag.set(true);
          return true;
        },
        2);
    Assertions.assertThat(CDateUtil.getDiffToNow(start, ChronoUnit.SECONDS)).isBetween(2L, 3L);
  }

  @Test(expectedExceptions = RuntimeException.class)
  public void testGet_ThrowingExceptionInJob() {
    CTimeBoxRunner.get(
        () -> {
          throw new RuntimeException("Lets test this");
        },
        2);
  }

  @Test(expectedExceptions = CThreadTimeoutException.class)
  public void testGet_ThrowingExceptionOnTimeout() {
    AtomicBoolean threadFinishedFlag = new AtomicBoolean();
    CTimeBoxRunner.get(
        () -> {
          CSleeper.sleepTightInSeconds(5);
          threadFinishedFlag.set(true);
          return true;
        },
        2,
        true);
  }
}
