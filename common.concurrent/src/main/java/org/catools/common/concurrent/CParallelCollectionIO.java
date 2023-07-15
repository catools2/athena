package org.catools.common.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.catools.common.concurrent.exceptions.CThreadTimeoutException;
import org.catools.common.utils.CSleeper;

import java.util.Collection;
import java.util.Stack;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * CParallel allows to have 2 thread which are running in parallel using same resources with optimized destruction.
 *
 * @param <T>
 */
@Slf4j
public class CParallelCollectionIO<T> {
  private final AtomicReference<Throwable> throwableReference = new AtomicReference<>();
  private final AtomicInteger activeInputThreads = new AtomicInteger(0);
  private final AtomicBoolean eof = new AtomicBoolean(false);
  private final Object lock = new Object();
  private final String name;
  private final int parallelInputCount;
  private final int parallelOutputCount;
  private final Long timeout;
  private final TimeUnit unit;

  private final Stack<T> sharedQueue = new Stack<>();

  private CParallelRunner<Boolean> inputExecutor;
  private CParallelRunner<Boolean> outputExecutor;

  public CParallelCollectionIO(String name, int parallelInputCount, int parallelOutputCount) {
    this(name, parallelInputCount, parallelOutputCount, null, null);
  }

  public CParallelCollectionIO(
      String name, int parallelInputCount, int parallelOutputCount, Long timeout, TimeUnit unit) {
    this.name = "Parallel IO " + name;
    this.parallelInputCount = parallelInputCount;
    this.parallelOutputCount = parallelOutputCount;
    this.timeout = timeout;
    this.unit = unit;
  }

  public void setInputExecutor(Function<AtomicBoolean, Collection<T>> inputFunction) {
    this.inputExecutor =
        new CParallelRunner<>(
            name + " Input",
            parallelInputCount,
            () -> {
              try {
                activeInputThreads.incrementAndGet();

                do {
                  Collection<T> result = inputFunction.apply(eof);

                  if (result != null && !result.isEmpty()) {
                    addInputResultToSharedStack(result);
                  } else {
                    CSleeper.sleepTight(500);
                  }

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

                T temp = readFromSharedQueue();

                if (temp == null) {
                  CSleeper.sleepTight(500);
                  continue;
                }

                outputFunction.accept(eof, temp);

                if (throwableReference.get() != null) {
                  break;
                }
              } while ((!eof.get()
                  || activeInputThreads.get() > 0
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

  private void addInputResultToSharedStack(Collection<T> data) {
    performActionOnSharedQueue(() -> sharedQueue.addAll(data));
    log.debug("{} new records added to the queue {}, new queue size is {}", data.size(), name, sharedQueue.size());
  }

  private T readFromSharedQueue() {
    if (sharedQueue.isEmpty())
      log.debug("The {} queue is empty", name);
    else
      log.debug("1 record removing from the queue {}, new queue size is {}", name, sharedQueue.size());

    return performActionOnSharedQueue(() -> sharedQueue.isEmpty() ? null : sharedQueue.remove(0));
  }

  private synchronized <O> O performActionOnSharedQueue(Supplier<O> supplier) {
    synchronized (lock) {
      return supplier.get();
    }
  }
}
