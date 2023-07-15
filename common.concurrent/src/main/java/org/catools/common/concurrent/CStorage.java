package org.catools.common.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.catools.common.concurrent.exceptions.CThreadTimeoutException;
import org.catools.common.utils.CDateUtil;
import org.catools.common.utils.CSleeper;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Slf4j
public class CStorage<T> {
  private final Object lock = new Object();
  private final List<T> available = new ArrayList<>();
  private final List<T> borrowed = new ArrayList<>();
  private final String name;
  private final int requestIntervalInSeconds;
  private final int requestTimeoutInSeconds;

  public CStorage(String name, int requestIntervalInSeconds, int requestTimeoutInSeconds) {
    this.name = name;
    this.requestIntervalInSeconds = requestIntervalInSeconds;
    this.requestTimeoutInSeconds = requestTimeoutInSeconds;
  }

  public void init(List<T> initialObjects) {
    Objects.requireNonNull(initialObjects);
    log.info("Storage {} initiation started with {} records.", name, initialObjects.size());
    performActionOnQueue(
        () -> {
          available.addAll(initialObjects);
          return true;
        });
    log.info("Storage {} initiated.", name);
  }

  public int getAvailableSize() {
    return available.size();
  }

  public int getBorrowedSize() {
    return borrowed.size();
  }

  public <R> R performAction(String borrower, Function<T, R> action) {
    return performAction(borrower, t -> true, action);
  }

  public <R> R performAction(String borrower, Predicate<T> predicate, Function<T, R> action) {
    T t = null;
    try {
      t = borrow(borrower, predicate);
      return action.apply(t);
    } finally {
      if (t != null) {
        release(t);
      }
    }
  }

  public T borrow(String borrower) {
    return borrow(borrower, t -> true);
  }

  public T borrow(String borrower, Predicate<T> predicate) {
    performActionOnQueue(
        () -> {
          log.trace("Attempt to borrow object for " + borrower);
          log.trace(
              "Storage contains {} available and {} borrowed objects",
              available.size(),
              borrowed.size());
          return true;
        });
    Request<T> request = new Request<>(borrower, requestTimeoutInSeconds, predicate);
    return waitForObjectToBeAvailable(request);
  }

  public boolean release(T t) {
    if (t != null) {
      return performActionOnQueue(
          () -> {
            log.trace("Object returned to storage {}.\n" + t, name);
            borrowed.remove(t);
            available.add(t);
            log.trace(
                "Storage contains {} available and {} borrowed objects",
                available.size(),
                borrowed.size());
            return true;
          });
    }
    return false;
  }

  private T waitForObjectToBeAvailable(Request<T> request) {
    do {
      T response =
          performActionOnQueue(
              () -> {
                T firstOrElse =
                    available.isEmpty()
                        ? null
                        : available.stream().filter(request.predicate).findFirst().orElse(null);
                if (firstOrElse != null) {
                  available.remove(firstOrElse);
                  borrowed.add(firstOrElse);
                  return firstOrElse;
                }
                if (request.isTimeOuted()) {
                  throw new CThreadTimeoutException("Request Timeout triggered on storage " + name + " for TestCase:" + request.borrower);
                }
                return null;
              });

      if (response != null) {
        return response;
      }

      CSleeper.sleepTightInSeconds(requestIntervalInSeconds);
    } while (true);
  }

  private synchronized <O> O performActionOnQueue(Supplier<O> supplier) {
    synchronized (lock) {
      return supplier.get();
    }
  }

  static class Request<T> {
    private final Date timeoutAt;
    private final String borrower;
    private final Predicate<T> predicate;

    public Request(String borrower, int timeoutInSeconds, Predicate<T> predicate) {
      this.timeoutAt = CDateUtil.add(new Date(), Calendar.SECOND, timeoutInSeconds);
      this.predicate = predicate;
      this.borrower = borrower;
    }

    public boolean isTimeOuted() {
      return timeoutAt.before(new Date());
    }
  }
}
