package org.catools.common.functions;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

/**
 * Often while working with expensive resources like file, it is reasonable to perform action once
 * and then save result of compute for future use. This is what Memoize do, for more information see
 * {@code https://en.wikipedia.org/wiki/Memoization}.
 *
 * @param <T> the result of
 */
public class CMemoize<T> implements Supplier<T> {
  private final Supplier<T> delegate;
  private AtomicBoolean initialized = new AtomicBoolean();
  private transient T value;

  public CMemoize(Supplier<T> delegate) {
    this.delegate = delegate;
  }

  public T get() {
    if (!this.initialized.get()) {
      synchronized (this) {
        if (!this.initialized.get()) {
          T t;
          try {
            t = this.delegate.get();
            this.value = t;
          } finally {
            this.initialized.set(true);
          }
          return t;
        }
      }
    }
    return this.value;
  }

  public synchronized void reset() {
    this.initialized.set(false);
  }
}
