package org.catools.common.concurrent;

import org.catools.common.concurrent.exceptions.CThreadTimeoutException;
import org.catools.common.utils.CSleeper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * CParallel allows to have 2 thread which are running in parallel using same resources
 *
 * @param <T>
 */
public class CParallelIO<T> {
  private final AtomicReference<Throwable> throwableReference = new AtomicReference<>();
  private final AtomicInteger activeInputThreads = new AtomicInteger(0);
  private final AtomicBoolean eof = new AtomicBoolean(false);
  private final Object lock = new Object();
  private final String name;
  private final int parallelInputCount;
  private final int parallelOutputCount;
  private final Long timeout;
  private final TimeUnit unit;

  private final List<T> sharedQueue = new ArrayList<>();
  private final List<T> outputQueue = new ArrayList<>();

  private CParallelRunner<Boolean> inputExecutor;
  private CParallelRunner<Boolean> outputExecutor;

  public CParallelIO(String name, int parallelInputCount, int parallelOutputCount) {
    this(name, parallelInputCount, parallelOutputCount, null, null);
  }

  public CParallelIO(
      String name, int parallelInputCount, int parallelOutputCount, Long timeout, TimeUnit unit) {
    this.name = "Parallel IO " + name;
    this.parallelInputCount = parallelInputCount;
    this.parallelOutputCount = parallelOutputCount;
    this.timeout = timeout;
    this.unit = unit;
  }

  public void setInputExecutor(Function<AtomicBoolean, T> inputFunction) {
    this.inputExecutor =
        new CParallelRunner<>(
            name + " Input",
            parallelInputCount,
            () -> {
              try {
                activeInputThreads.incrementAndGet();
                do {
                  T input = inputFunction.apply(eof);
                  performActionOnSharedQueue(() -> sharedQueue.add(input));
                } while (!eof.get() && isLive());
              } catch (Throwable t) {
                throwableReference.set(t);
                throw t;
              } finally {
                activeInputThreads.decrementAndGet();
              }
              return true;
            });
  }

  public void setOutputExecutor(BiConsumer<AtomicBoolean, T> outputFunction) {
    this.outputExecutor =
        new CParallelRunner<>(
            name + " Output",
            parallelOutputCount,
            () -> {
              do {
                if (throwableReference.get() != null) {
                  break;
                }

                AtomicReference<T> clone = new AtomicReference<>();
                performActionOnSharedQueue(
                    () -> {
                      outputQueue.addAll(sharedQueue);
                      sharedQueue.clear();
                      if (!outputQueue.isEmpty()) {
                        clone.set(outputQueue.remove(0));
                      }
                      return true;
                    });

                if (clone.get() == null) {
                  CSleeper.sleepTight(500);
                  continue;
                }

                outputFunction.accept(eof, clone.get());
                if (throwableReference.get() != null) {
                  break;
                }
              } while ((!eof.get()
                  || activeInputThreads.get() > 0
                  || !outputQueue.isEmpty()
                  || !sharedQueue.isEmpty())
                  && isLive());
              return true;
            });
  }

  public void run() throws Throwable {
    if (timeout == null || unit == null) {
      try {
        CThreadRunner.run(
            () -> {
              try {
                inputExecutor.invokeAll();
              } catch (Throwable t) {
                throwableReference.set(t);
              } finally {
                eof.set(true);
              }
            });
        try {
          outputExecutor.invokeAll();
        } catch (Throwable t) {
          throwableReference.set(t);
        }
      } catch (CThreadTimeoutException e) {
        eof.set(true);
        inputExecutor.shutdownNow();
        outputExecutor.shutdownNow();
        throw e;
      }

      if (throwableReference.get() != null) {
        throw throwableReference.get();
      }
    } else {
      run(timeout, unit);
    }
  }

  public void run(long timeout, TimeUnit unit) throws Throwable {
    try {
      CTimeBoxRunner.get(
          () -> {
            CThreadRunner.run(
                () -> {
                  try {
                    inputExecutor.invokeAll(timeout, unit);
                  } catch (Throwable t) {
                    throwableReference.set(t);
                  }
                  eof.set(true);
                });

            try {
              outputExecutor.invokeAll(timeout, unit);
            } catch (Throwable t) {
              throwableReference.set(t);
            }
            return true;
          },
          timeout,
          unit,
          true);
    } catch (CThreadTimeoutException e) {
      eof.set(true);
      inputExecutor.shutdownNow();
      outputExecutor.shutdownNow();
      throw e;
    }

    if (throwableReference.get() != null) {
      throw throwableReference.get();
    }
  }

  public boolean isStarted() {
    return inputExecutor.isStarted() || outputExecutor.isStarted();
  }

  public boolean isLive() {
    return !(isFinished() || isShutdown() || isTerminated()) && throwableReference.get() == null;
  }

  public boolean isFinished() {
    return inputExecutor.isFinished() && outputExecutor.isFinished();
  }

  public boolean isShutdown() {
    return inputExecutor.isShutdown() && outputExecutor.isShutdown();
  }

  public boolean isTerminated() {
    return inputExecutor.isTerminated() && outputExecutor.isTerminated();
  }

  private synchronized <C> void performActionOnSharedQueue(Supplier<C> supplier) {
    synchronized (lock) {
      supplier.get();
    }
  }
}
