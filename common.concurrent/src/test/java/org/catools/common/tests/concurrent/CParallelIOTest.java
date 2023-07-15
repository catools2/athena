package org.catools.common.tests.concurrent;

import org.assertj.core.api.Assertions;
import org.catools.common.concurrent.CParallelIO;
import org.catools.common.concurrent.exceptions.CThreadTimeoutException;
import org.catools.common.exception.CRuntimeException;
import org.catools.common.utils.CSleeper;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CParallelIOTest {

  @Test
  public void testRun() throws Throwable {
    AtomicInteger counter = new AtomicInteger();
    CParallelIO<String> parallelIO = new CParallelIO<>("CParallelIO", 10, 2);
    parallelIO.setInputExecutor(
        atomicBoolean -> {
          if (counter.getAndIncrement() > 50) {
            atomicBoolean.set(true);
          }
          return "1";
        });

    List<String> output = new ArrayList<>();
    parallelIO.setOutputExecutor(
        (atomicBoolean, s) -> {
          Assertions.assertThat(parallelIO.isStarted()).isTrue();
          output.add(s);
        });

    parallelIO.run();

    Assertions.assertThat(parallelIO.isFinished()).isTrue();
    Assertions.assertThat(output.size()).isGreaterThan(50);
  }

  @Test
  public void testRun1() throws Throwable {
    AtomicInteger counter = new AtomicInteger();
    CParallelIO<String> parallelIO = new CParallelIO<>("CParallelIO", 10, 2, 5L, TimeUnit.SECONDS);
    parallelIO.setInputExecutor(
        atomicBoolean -> {
          if (counter.getAndIncrement() > 50) {
            atomicBoolean.set(true);
          }
          return "1";
        });

    List<String> output = new ArrayList<>();
    parallelIO.setOutputExecutor(
        (atomicBoolean, s) -> {
          Assertions.assertThat(parallelIO.isStarted()).isTrue();
          output.add(s);
        });

    parallelIO.run();

    CSleeper.sleepTightInSeconds(1);

    Assertions.assertThat(
            parallelIO.isFinished() || parallelIO.isShutdown() || parallelIO.isTerminated())
        .isTrue();

    Assertions.assertThat(output.size()).isGreaterThan(10);
  }

  @Test(expectedExceptions = CThreadTimeoutException.class)
  public void testRun_Timeout_Input() throws Throwable {
    AtomicInteger counter = new AtomicInteger();
    CParallelIO<String> parallelIO = new CParallelIO<>("CParallelIO", 10, 2);
    parallelIO.setInputExecutor(
        atomicBoolean -> {
          CSleeper.sleepTightInSeconds(20);
          if (counter.getAndIncrement() > 20) {
            atomicBoolean.set(true);
          }
          return "1";
        });

    List<String> output = new ArrayList<>();
    parallelIO.setOutputExecutor(
        (atomicBoolean, s) -> {
          Assertions.assertThat(parallelIO.isStarted()).isTrue();
          output.add(s);
        });

    parallelIO.run(1, TimeUnit.SECONDS);
  }

  @Test(expectedExceptions = CThreadTimeoutException.class)
  public void testRun1_Timeout_Input() throws Throwable {
    AtomicInteger counter = new AtomicInteger();
    CParallelIO<String> parallelIO = new CParallelIO<>("CParallelIO", 2, 2, 1L, TimeUnit.SECONDS);
    parallelIO.setInputExecutor(
        atomicBoolean -> {
          CSleeper.sleepTightInSeconds(1);
          if (counter.getAndIncrement() > 20) {
            atomicBoolean.set(true);
          }
          return "1";
        });

    List<String> output = new ArrayList<>();
    parallelIO.setOutputExecutor(
        (atomicBoolean, s) -> {
          Assertions.assertThat(parallelIO.isStarted()).isTrue();
          output.add(s);
        });

    parallelIO.run();
  }

  @Test(expectedExceptions = CRuntimeException.class)
  public void testRun1_ExceptionInInput() throws Throwable {
    AtomicInteger counter = new AtomicInteger();
    CParallelIO<String> parallelIO = new CParallelIO<>("CParallelIO", 10, 2, 2L, TimeUnit.SECONDS);
    parallelIO.setInputExecutor(
        atomicBoolean -> {
          CSleeper.sleepTightInSeconds(1);
          if (counter.getAndIncrement() > 2) {
            throw new CRuntimeException("Ops");
          }
          return "1";
        });

    List<String> output = new ArrayList<>();
    parallelIO.setOutputExecutor(
        (atomicBoolean, s) -> {
          Assertions.assertThat(parallelIO.isStarted()).isTrue();
          output.add(s);
        });

    parallelIO.run();
  }

  @Test(expectedExceptions = CRuntimeException.class)
  public void testRun1_ExceptionInOutput() throws Throwable {
    AtomicInteger counter = new AtomicInteger();
    CParallelIO<String> parallelIO = new CParallelIO<>("CParallelIO", 10, 2, 1L, TimeUnit.SECONDS);
    parallelIO.setInputExecutor(
        atomicBoolean -> {
          if (counter.getAndIncrement() > 20) {
            atomicBoolean.set(true);
          }
          return "1";
        });

    parallelIO.setOutputExecutor(
        (atomicBoolean, s) -> {
          throw new CRuntimeException("Ops");
        });

    parallelIO.run();
  }
}
