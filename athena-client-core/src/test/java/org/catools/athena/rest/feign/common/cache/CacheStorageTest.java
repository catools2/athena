package org.catools.athena.rest.feign.common.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class CacheStorageTest {

  private CacheStorage<String, TestObject> cacheStorage;
  private Function<TestObject, String> keyGenerator;
  private Function<TestObject, TestObject> valueGenerator;

  @BeforeEach
  void setUp() {
    keyGenerator = TestObject::getId;
    valueGenerator = obj -> new TestObject(obj.getId(), obj.getName() + "_PROCESSED");
    cacheStorage = new CacheStorage<>("TestCache", keyGenerator, valueGenerator);
  }

  @Test
  void read_withNewValue_shouldStoreAndReturnProcessedValue() {
    // Given
    TestObject input = new TestObject("1", "Test");

    // When
    TestObject result = cacheStorage.read(input);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("1");
    assertThat(result.getName()).isEqualTo("Test_PROCESSED");
  }

  @Test
  void read_withExistingKey_shouldReturnCachedValue() {
    // Given
    TestObject input1 = new TestObject("1", "Test1");
    TestObject input2 = new TestObject("1", "Test2");

    // When
    TestObject result1 = cacheStorage.read(input1);
    TestObject result2 = cacheStorage.read(input2);

    // Then
    assertThat(result1).isSameAs(result2);
    assertThat(result1.getName()).isEqualTo("Test1_PROCESSED");
    assertThat(result2.getName()).isEqualTo("Test1_PROCESSED"); // Should return cached value
  }

  @Test
  void read_withDifferentKeys_shouldStoreMultipleValues() {
    // Given
    TestObject input1 = new TestObject("1", "Test1");
    TestObject input2 = new TestObject("2", "Test2");
    TestObject input3 = new TestObject("3", "Test3");

    // When
    TestObject result1 = cacheStorage.read(input1);
    TestObject result2 = cacheStorage.read(input2);
    TestObject result3 = cacheStorage.read(input3);

    // Then
    assertThat(result1.getId()).isEqualTo("1");
    assertThat(result2.getId()).isEqualTo("2");
    assertThat(result3.getId()).isEqualTo("3");
    assertThat(result1).isNotSameAs(result2);
    assertThat(result2).isNotSameAs(result3);
  }

  @Test
  void read_withValueGeneratorCalledOnce_shouldCacheResult() {
    // Given
    final java.util.concurrent.atomic.AtomicInteger callCount = new java.util.concurrent.atomic.AtomicInteger(0);
    Function<TestObject, TestObject> countingGenerator = obj -> {
      callCount.incrementAndGet();
      return new TestObject(obj.getId(), obj.getName() + "_PROCESSED");
    };

    CacheStorage<String, TestObject> cache = new CacheStorage<>("TestCache", keyGenerator, countingGenerator);
    TestObject input = new TestObject("1", "Test");

    // When
    cache.read(input);
    cache.read(input);
    cache.read(input);

    // Then - valueGenerator should only be called once (on first read)
    assertThat(callCount.get()).isEqualTo(1);
  }

  @Test
  void read_threadSafe_shouldHandleConcurrentAccess() throws InterruptedException {
    // Given
    int threadCount = 10;
    TestObject input = new TestObject("1", "Test");
    Thread[] threads = new Thread[threadCount];

    // When
    for (int i = 0; i < threadCount; i++) {
      threads[i] = new Thread(() -> cacheStorage.read(input));
      threads[i].start();
    }

    for (Thread thread : threads) {
      thread.join();
    }

    // Then - should not throw ConcurrentModificationException
    TestObject result = cacheStorage.read(input);
    assertThat(result).isNotNull();
  }

  @Test
  void read_withNullKey_shouldHandleGracefully() {
    // Given
    Function<TestObject, String> nullKeyGenerator = obj -> null;
    CacheStorage<String, TestObject> cache = new CacheStorage<>("TestCache", nullKeyGenerator, valueGenerator);
    TestObject input = new TestObject("1", "Test");

    // When
    TestObject result = cache.read(input);

    // Then
    assertThat(result).isNotNull();
  }

  // Helper class for testing
  private static class TestObject {
    private final String id;
    private final String name;

    TestObject(String id, String name) {
      this.id = id;
      this.name = name;
    }

    String getId() {
      return id;
    }

    String getName() {
      return name;
    }
  }
}

