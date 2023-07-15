package org.catools.common.collections.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CLinkedMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.extensions.states.interfaces.CCollectionState;
import org.catools.common.extensions.verify.interfaces.base.CCollectionVerify;
import org.catools.common.extensions.verify.interfaces.verifier.CCollectionVerifier;

import java.security.SecureRandom;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This interface {@link CCollection} uses to extend {@link Collection} interface to facilitate
 * testing and reduce coding. We hope to extend this class with more useful methods.
 *
 * @see Map
 * @see CHashMap
 * @see CLinkedMap
 * @see CSet
 * @see CList
 */
@JsonIgnoreProperties(value = {"empty"})
public interface CCollection<E, C extends Collection<E>> extends CIterable<E, C>, Collection<E>, CCollectionVerify<E, C>, CCollectionVerifier<E, C>, CCollectionState<E, C> {

  /**
   * Add {@code e} to the list if the {@code predicate} condition returns true.
   *
   * @param predicate to issue condition
   * @param e         element to add if condition passed
   * @return true if the condition matched otherwise return false
   */
  default boolean addIf(Predicate<E> predicate, E e) {
    return predicate.test(e) && add(e);
  }

  /**
   * Returns {@code true} if this collection contains the specified element. More formally, returns
   * {@code true} if and only if this collection contains at least one element {@code e} such that
   * {@code Objects.equals(o, e)}.
   *
   * @param o element whose presence in this collection is to be tested
   * @return {@code true} if this collection contains the specified element
   * @throws ClassCastException   if the type of the specified element is incompatible with this
   *                              collection
   * @throws NullPointerException if the specified element is null and this collection does not
   *                              permit null elements
   */
  boolean contains(Object o);

  /**
   * get a random value from the collection.
   *
   * @return random value or null if no value available
   */
  @JsonIgnore
  default E getAny() {
    if (isEmpty()) {
      return null;
    }
    List<E> collect = toList();
    return collect.get(new SecureRandom().nextInt(collect.size()));
  }

  /**
   * get a random value from the collection and remove it from the list.
   *
   * @return random value or null if no value available
   */
  @JsonIgnore
  default E getAnyAndRemove() {
    if (isEmpty()) {
      return null;
    }
    E object = getAny();
    remove(object);
    return object;
  }

  @JsonIgnore
  C _get();

  /**
   * Returns {@code true} if this collection contains no elements.
   *
   * @return {@code true} if this collection contains no elements
   */
  @JsonIgnore
  boolean isEmpty();

  /**
   * Returns consecutive {@linkplain CList#subList(int, int) sublists} of a list, each of the same
   * size (the final list may be smaller). For example, partitioning a list containing {@code [a, b,
   * c, d, e]} with a partition size of 3 yields {@code [[a, b, c], [d, e]]} -- an outer list
   * containing two inner lists of three and two elements, all in the original order.
   *
   * <p>The outer list is unmodifiable, but reflects the latest state of the source list. The inner
   * lists are sublist views of the original list, produced on demand using {@link List#subList(int,
   * int)}, and are subject to all the usual caveats about modification as explained in that API.
   *
   * @param size the desired size of each sublist (the last may be smaller)
   * @return a list of consecutive sublists
   * @throws IllegalArgumentException if {@code partitionSize} is nonpositive
   */
  default CList<CList<E>> partition(int size) {
    return new CList<>(
        Lists.partition(new ArrayList<>(_get()), size).stream()
            .map(CList::new)
            .collect(Collectors.toList()));
  }

  /**
   * Removes all of the elements of this collection that satisfy the given predicate. Errors or
   * runtime exceptions thrown during iteration or by the predicate are relayed to the caller.
   *
   * @param filter predicate which returns {@code true} for elements to be removed
   * @return {@code true} if any elements were removed
   * @throws NullPointerException          if the specified filter is null
   * @throws UnsupportedOperationException if elements cannot be removed from this collection.
   *                                       Implementations may throw this exception if CCliItemCollection matching element cannot be
   *                                       removed or if, in general, removal is not supported.
   */
  boolean removeIf(Predicate<? super E> filter);

  /**
   * Creates {@link Spliterator} over the elements in this collection. Implementations should
   * document characteristic values reported by the spliterator. Such characteristic values are not
   * required to be reported if the spliterator reports {@link Spliterator#SIZED} and this
   * collection contains no elements.
   *
   * @return {@code Spliterator} over the elements in this collection
   */
  Spliterator<E> spliterator();

  /**
   * Returns sequential {@code Stream} with this collection as its source.
   *
   * <p>This method should be overridden when the {@see #spliterator()} method cannot return
   * spliterator that is {@code IMMUTABLE}, {@code CONCURRENT}, or <em>late-binding</em>. (See
   * {@see #spliterator()} for details.)
   *
   * @return sequential {@code Stream} over the elements in this collection
   */
  Stream<E> stream();

  /**
   * Convert {@code CCollection} to {@code CMap} and return the result
   *
   * @return get {@code CMap} from elements of {@code CCollection} and return the result
   */
  default <K, V> CMap<K, V> toMap(
      Function<? super E, ? extends K> keyMapper, Function<? super E, ? extends V> valueMapper) {
    return new CHashMap<>(stream().collect(Collectors.toMap(keyMapper, valueMapper)));
  }

  /**
   * Convert {@code CCollection} to {@code CMap} and return the result
   *
   * @return get {@code CMap} from elements of {@code CCollection} and return the result
   */
  default <K, V> CMap<K, V> toMap(
      Function<? super E, ? extends K> keyMapper,
      Function<? super E, ? extends V> valueMapper,
      BinaryOperator<V> mergeFunction) {
    return new CHashMap<>(
        stream().collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction)));
  }

  /**
   * Convert {@code CCollection} to {@code CMap} and return the result
   *
   * @return get {@code CMap} from elements of {@code CCollection} and return the result
   */
  default <K, V, M extends Map<K, V>> CMap<K, V> toMap(
      Function<? super E, ? extends K> keyMapper,
      Function<? super E, ? extends V> valueMapper,
      BinaryOperator<V> mergeFunction,
      Supplier<M> mapFactory) {
    return new CHashMap<>(
        stream().collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction, mapFactory)));
  }
}
