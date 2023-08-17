package org.catools.common.collections.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.collections4.CollectionUtils;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CLinkedMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.extensions.states.interfaces.CIterableState;
import org.catools.common.extensions.verify.interfaces.base.CIterableVerify;
import org.catools.common.extensions.verify.interfaces.verifier.CIterableVerifier;
import org.catools.common.utils.CStringUtil;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * This interface {@link CIterable} uses to extend {@link Collection} interface to facilitate
 * testing and reduce coding. We hope to extend this class with more useful methods.
 *
 * @see Map
 * @see CHashMap
 * @see CLinkedMap
 * @see CSet
 * @see CList
 */
@JsonIgnoreProperties(value = {"empty"})
public interface CIterable<E, C extends Iterable<E>> extends Iterable<E>, CIterableVerify<E, C>, CIterableVerifier<E, C>, CIterableState<E, C> {

  /**
   * Returns {@code true} if this collection equals the specified collection.
   *
   * @param c collection to be checked for equality
   * @return {@code true} if this collection contains none of the elements in the specified
   * collection
   * @throws ClassCastException   if the collections of one or more elements in the specified
   *                              collection are incompatible with this collection
   * @throws NullPointerException if the specified collection contains one or more null elements and
   *                              this collection does not permit null elements , or if the specified collection is null.
   * @see #contains(Object)
   */
  boolean equals(Object c);

  /**
   * Returns sequence of values which matched the {@code predicate}.
   *
   * @return the collection of value described by this {@code Optional}
   */
  default CList<E> getAll() {
    return new CList<>(stream().collect(Collectors.toList()));
  }

  /**
   * Returns sequence of values which matched the {@code predicate}.
   *
   * @param predicate to issue condition
   * @return the collection of value described by this {@code Optional}
   */
  default CList<E> getAll(Predicate<E> predicate) {
    return new CList<>(stream().filter(predicate).collect(Collectors.toList()));
  }

  /**
   * get a random value from the collection.
   *
   * @return random value or null if no value available
   */
  @JsonIgnore
  default E getRandom() {
    if (isEmpty()) {
      return null;
    }
    List<E> collect = toList();
    return collect.get(new SecureRandom().nextInt(collect.size()));
  }

  /**
   * Returns a first element from list.
   *
   * @return a first element from list.
   * @throws NoSuchElementException if no value is present
   */
  @JsonIgnore
  default E getFirst() {
    return stream().findFirst().get();
  }

  /**
   * Returns a first element from list which matched the {@code predicate} otherwise throws {@code
   * NoSuchElementException}. The preferred alternative to this method is {@link
   * #getFirstOrElse(Object)}, {@see #getFirstOrElse(Predicate, Object)}, {@link
   * #getFirstOrThrow(RuntimeException)}, {@see #getFirstOrThrow(Predicate, Supplier)}.
   *
   * @param predicate to issue condition
   * @return the non-{@code null} value described by this {@code Optional}
   * @throws NoSuchElementException if no value is present
   */
  default E getFirst(Predicate<E> predicate) {
    return stream().filter(predicate).findFirst().get();
  }

  /**
   * Returns a first element from list if the list is not empty otherwise returns {@code other}
   * value. The preferred alternative to this method is {@see #getFirstOrElse(Object)}, {@link
   * #getFirstOrElse(Predicate, Object)}, {@see #getFirstOrThrow(RuntimeException)}, {@link
   * #getFirstOrThrow(Predicate, Supplier)}.
   *
   * @param other value to be return if not record found
   * @return the non-{@code null} value described by this {@code Optional}
   */
  default E getFirstOrElse(E other) {
    return stream().findFirst().orElse(other);
  }

  /**
   * Returns a first element from list which matched the {@code predicate} otherwise returns {@code
   * other} value. The preferred alternative to this method is {@see #getFirst()}, {@link
   * #getFirstOrElse(Object)}, {@see #getFirstOrThrow(RuntimeException)}, {@link
   * #getFirstOrThrow(Predicate, Supplier)}.
   *
   * @param predicate to issue condition
   * @param other     value to be turn if no matched found
   * @return the non-{@code null} value described by this {@code Optional}
   */
  default E getFirstOrElse(Predicate<E> predicate, E other) {
    return stream().filter(predicate).findFirst().orElse(other);
  }

  /**
   * Returns a first element from list if the list is not empty otherwise returns {@code other}
   * value. The preferred alternative to this method is {@see #getFirstOrElse(Object)}, {@link
   * #getFirstOrElse(Predicate, Object)}, {@see #getFirstOrThrow(RuntimeException)}, {@link
   * #getFirstOrThrow(Predicate, Supplier)}.
   *
   * @param other value to be return if not record found
   * @return the non-{@code null} value described by this {@code Optional}
   */
  default E getFirstOrElseGet(Supplier<E> other) {
    return stream().findFirst().orElseGet(other);
  }

  /**
   * Returns a first element from list which matched the {@code predicate} otherwise returns {@code
   * other} value. The preferred alternative to this method is {@see #getFirst()}, {@link
   * #getFirstOrElse(Object)}, {@see #getFirstOrThrow(RuntimeException)}, {@link
   * #getFirstOrThrow(Predicate, Supplier)}.
   *
   * @param predicate to issue condition
   * @param other     value to be turn if no matched found
   * @return the non-{@code null} value described by this {@code Optional}
   */
  default E getFirstOrElseGet(Predicate<E> predicate, Supplier<E> other) {
    return stream().filter(predicate).findFirst().orElseGet(other);
  }

  /**
   * Returns a first element from list if the list is not empty otherwise returns {@code null}
   * value. The preferred alternative to this method is {@see #getFirstOrElse(Object)}, {@link
   * #getFirstOrElse(Predicate, Object)}, {@see #getFirstOrThrow(RuntimeException)}, {@link
   * #getFirstOrThrow(Predicate, Supplier)}.
   *
   * @return the non-{@code null} value described by this {@code Optional}
   */
  @JsonIgnore
  default E getFirstOrNull() {
    return getFirstOrElse(null);
  }

  /**
   * Returns first element from list which matched the {@code predicate} otherwise returns {@code
   * null} value. The preferred alternative to this method is {@see #getFirst()}, {@link
   * #getFirstOrElse(Object)}, {@see #getFirstOrThrow(RuntimeException)}, {@link
   * #getFirstOrThrow(Predicate, Supplier)}.
   *
   * @param predicate to issue condition
   * @return the non-{@code null} value described by this {@code Optional}
   */
  default E getFirstOrNull(Predicate<E> predicate) {
    return getFirstOrElse(predicate, () -> null);
  }

  /**
   * Returns first element from list which matched the {@code predicate} otherwise returns any
   * value. The preferred alternative to this method is {@see #getFirst()}, {@link
   * #getFirstOrElse(Object)}, {@see #getFirstOrThrow(RuntimeException)}, {@link
   * #getFirstOrThrow(Predicate, Supplier)}.
   *
   * @param predicate to issue condition
   * @return the non-{@code null} value described by this {@code Optional}
   */
  default E getFirstOrAny(Predicate<E> predicate) {
    return getFirstOrElse(predicate, getRandom());
  }

  /**
   * Returns first element from list if the list is not empty otherwise throws {@code
   * RuntimeException}. The preferred alternative to this method is {@see #getFirst()}, {@see
   * #getFirstOrElse(Object)}, {@see #getFirstOrElse(Predicate, Object)},
   * {@see #getFirstOrThrow(RuntimeException)}, {@see #getFirstOrThrow(Predicate, Supplier)}.
   *
   * @param e exception to be throws if no record found
   * @return the non-{@code null} value described by this {@code Optional}
   * @throws RuntimeException if no value is present
   */
  default E getFirstOrThrow(RuntimeException e) {
    return stream().findFirst().orElseThrow(() -> e);
  }

  /**
   * Returns first element from list which matched the {@code predicate} otherwise throws {@code
   * RuntimeException}. The preferred alternative to this method is {@see #getFirst()}, {@link
   * #getFirstOrElse(Object)}, {@see #getFirstOrElse(Predicate, Object)}, {@link
   * #getFirstOrThrow(RuntimeException)},
   *
   * @param predicate         to issue condition
   * @param exceptionSupplier the supplying function that produces an exception to be thrown
   * @return the non-{@code null} value described by this {@code Optional}
   * @throws RuntimeException if no value is present
   */
  default <X extends RuntimeException> E getFirstOrThrow(
      Predicate<E> predicate, Supplier<? extends X> exceptionSupplier) {
    return stream().filter(predicate).findFirst().orElseThrow(exceptionSupplier);
  }

  /**
   * Returns a first element from list which matched the {@code predicate} otherwise returns {@code
   * other} value. The preferred alternative to this method is {@see #getFirst()}, {@link
   * #getFirstOrElse(Object)}, {@see #getFirstOrThrow(RuntimeException)}, {@link
   * #getFirstOrThrow(Predicate, Supplier)},
   *
   * @param predicate to issue condition
   * @param other     supplier to be call if not match found
   * @return the non-{@code null} value described by this {@code Optional}
   */
  @JsonIgnore
  default E getFirstOrElse(Predicate<E> predicate, Supplier<E> other) {
    return stream().filter(predicate).findFirst().orElseGet(other);
  }

  /**
   * Joins the elements of {@code CCollection} into single String containing the provided elements.
   *
   * <p>No delimiter is added before or after the list.
   *
   * <p>See the examples here: {@see #join(String)}.
   *
   * @return the joined String with no separator
   */
  default String join() {
    return join(CStringUtil.EMPTY);
  }

  // ------------------------------------------------------------
  // Collection interface to keep documentation here
  // ------------------------------------------------------------

  /**
   * Joins the elements of {@code CCollection} into single String containing the provided elements.
   *
   * <p>No delimiter is added before or after the list. A {@code null} separator is the same as an
   * empty String (CStringUtil.EMPTY).
   *
   * <p>See the examples here: {@see #join(String)}.
   *
   * @param separator the separator character to use, null treated as CStringUtil.EMPTY
   * @return the joined String separated by separator
   */
  default String join(String separator) {
    return CStringUtil.join(_get(), separator);
  }

  /**
   * Joins the elements of {@code CCollection} into single String containing the provided elements.
   *
   * <p>No delimiter is added before or after the list. A {@code null} separator is the same as an
   * empty String (CStringUtil.EMPTY).
   *
   * <p>See the examples here: {@see #join(String)}.
   *
   * @param mapper    the mapper to use, null treated as CStringUtil.EMPTY
   * @param separator the separator character to use, null treated as CStringUtil.EMPTY
   * @return the joined String separated by separator
   */
  default String join(Function<? super E, ? extends String> mapper, String separator) {
    return CStringUtil.join(mapToList(mapper), separator);
  }

  /**
   * Returns stream consisting of the results of applying the given function to the elements of this
   * stream.
   *
   * @param <R>    The element type of the new stream
   * @param mapper function to apply to each element
   * @return the new stream
   */
  default <R> Stream<R> map(Function<? super E, ? extends R> mapper) {
    return stream().map(mapper);
  }

  /**
   * Returns {@code CList} consisting of the results of applying the given function to the elements
   * of the {@code CCollection}.
   *
   * @param <R>    The element type of the new {@code CList}
   * @param mapper function to apply to each element
   * @return the new {@code CList}
   */
  default <R> CList<R> mapToList(Function<? super E, ? extends R> mapper) {
    return new CList<>(map(mapper).collect(Collectors.toList()));
  }

  /**
   * Returns {@code CSet} consisting of the results of applying the given function to the elements
   * of the {@code CCollection}.
   *
   * @param <R>    The element type of the new {@code CSet}
   * @param mapper function to apply to each element
   * @return the new {@code CSet}
   */
  default <R> CSet<R> mapToSet(Function<? super E, ? extends R> mapper) {
    return new CSet<>(map(mapper).collect(Collectors.toList()));
  }

  /**
   * Returns a {@link CList} containing the union with the given {@link Iterable}.
   *
   * @param iterable the collection, must not be null
   * @return the union of the two collections
   */
  default CList<E> getUnion(final Iterable<E> iterable) {
    return new CList<>(CollectionUtils.union(this, iterable));
  }

  /**
   * Returns a {@link CList} containing the intersection with the given {@link Iterable}.
   *
   * @param iterable the collection, must not be null
   * @return the intersection of the two collections
   */
  default CList<E> getIntersection(final Iterable<E> iterable) {
    return new CList<>(CollectionUtils.intersection(this, iterable));
  }

  /**
   * Returns a {@link CList} containing the exclusive disjunction with the given {@link Iterable}.
   *
   * @param iterable the collection, must not be null
   * @return the symmetric difference of the two collections
   */
  default CList<E> getDisjunction(final Iterable<E> iterable) {
    return new CList<>(CollectionUtils.disjunction(this, iterable));
  }

  /**
   * Returns sequential {@code Stream} with this collection as its source.
   *
   * <p>This method should be overridden when the {@see #spliterator()} method cannot return
   * spliterator that is {@code IMMUTABLE}, {@code CONCURRENT}, or <em>late-binding</em>. (See
   * {@see #spliterator()} for details.)
   *
   * @return sequential {@code Stream} over the elements in this collection
   */
  default Stream<E> stream() {
    return StreamSupport.stream(spliterator(), false);
  }

  /**
   * Convert {@code CCollection} to {@code CList} and return the result
   *
   * @return get {@code CList} from elements of {@code CCollection} and return the result
   */
  default CList<E> toList() {
    return new CList<>(_get());
  }

  /**
   * Convert {@code CCollection} to {@code CSet} and return the result
   *
   * @return get {@code CSet} from elements of {@code CCollection} and return the result
   */
  default CSet<E> toSet() {
    return new CSet<>(_get());
  }
}
