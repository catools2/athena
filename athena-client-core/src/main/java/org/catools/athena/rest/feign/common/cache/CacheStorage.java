package org.catools.athena.rest.feign.common.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    K key = keyGenerator.apply(v);
    if (!storage.containsKey(key)) {
      storage.put(key, valueGenerator.apply(v));
      log.debug("{} CacheStorage: new record added. Total {} records.", name, storage.size());
    }
    return storage.get(key);
  }
}
