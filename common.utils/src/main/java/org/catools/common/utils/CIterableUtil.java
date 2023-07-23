package org.catools.common.utils;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@UtilityClass
public class CIterableUtil {

  /**
   * Check if iterable contain the element.
   *
   * @param iterable to check
   * @param e        value to search for
   * @return execution boolean result
   */
  public static <E> boolean contains(Iterable<E> iterable, E e) {
    if (e == null) {
      return false;
    }

    for (E a : iterable) {
      if (Objects.equals(a, e)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Check if iterable contains any value.
   *
   * @param iterable to check
   * @return execution boolean result
   */
  public static boolean isNotEmpty(Iterable iterable) {
    if (iterable == null) return false;
    iterable.iterator();
    return iterable.iterator().hasNext();
  }

  /**
   * Check if iterable is empty.
   *
   * @param iterable to check
   * @return execution boolean result
   */
  public static boolean isEmpty(Iterable iterable) {
    return !isNotEmpty(iterable);
  }

  /**
   * Check if iterable has any method which its result of predicate call is true.
   *
   * @param iterable to check
   * @return execution boolean result
   */
  public static <E> Boolean has(Iterable<E> iterable, Predicate<E> expected) {
    for (E a : iterable) {
      if (expected.test(a)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get a random value from the stream.
   *
   * @return random value or null if no value available
   */
  public static <E> E getRandom(E[] values) {
    if (values == null) {
      return null;
    }
    return getRandom(List.of(values));
  }

  /**
   * Get a random value from the stream.
   *
   * @return random value or null if no value available
   */
  public static <E> E getRandom(Collection<E> collection) {
    if (collection == null) {
      return null;
    }
    List<E> objects = new ArrayList<>(collection);
    return objects.get(new SecureRandom().nextInt(objects.size()));
  }
}
