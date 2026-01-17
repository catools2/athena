package org.catools.athena.rest.feign.common.cache;

import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
public class CacheStorage<K, V> {
  private final Map<K, V> storage = new HashMap<>();
  private final String name;
  private final Function<V, K> keyGenerator;
  private final Function<V, V> valueGenerator;

  public synchronized V read(V v) {
    RetryPolicy<Object> retryPolicy = RetryPolicy.builder().handle(FeignException.class).withDelay(Duration.ofSeconds(2)).withMaxRetries(3).build();

    K key = keyGenerator.apply(v);
    if (!storage.containsKey(key)) {
      Failsafe.with(retryPolicy).run(() -> {
        storage.put(key, valueGenerator.apply(v));
      });
      log.debug("{} CacheStorage: new record added. Total {} records.", name, storage.size());
    }
    return storage.get(key);
  }
}
