package org.catools.common.collections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.catools.common.collections.interfaces.CCollection;
import org.catools.common.utils.CStringUtil;
import org.testng.collections.Lists;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CList<E> extends ArrayList<E> implements CCollection<E, Collection<E>>, List<E> {

  public static <C> CList<C> of(C... c) {
    return new CList<>(c);
  }

  public static <C> CList<C> of(final Stream<C> stream) {
    return new CList<>(stream);
  }

  public static <C> CList<C> of(final Iterable<C> iterable) {
    return new CList<>(iterable);
  }

  public CList() {
    super();
  }

  public CList(E... c) {
    super(c == null ? Lists.newArrayList() : Arrays.asList(c));
  }

  public CList(final Stream<E> stream) {
    super(stream.collect(Collectors.toSet()));
  }

  public CList(final Iterable<E> iterable) {
    super();
    if (iterable != null) {
      iterable.forEach(this::add);
    }
  }

  /**
   * Implement the getValue method of {@link CCollection}
   *
   * @return current instance of {@link CList}
   */
  @Override
  @JsonIgnore
  public Collection<E> _get() {
    return this;
  }

  /**
   * Returns the index of first element from list which matched the {@code predicate} or -1 if no
   * match found. The preferred alternative to this method is {@see #getFirstOrElse(Object)},
   * {@see #getFirstOrElse(Predicate, Object)}, {@see #getFirstOrThrow(RuntimeException)}, {@link
   * #getFirstOrThrow(Predicate, Supplier)}.
   *
   * @param predicate to issue condition
   * @return index of element in collection ot -1 if no match found
   */
  public int indexOf(Predicate<E> predicate) {
    Optional<E> first = stream().filter(predicate).findFirst();
    return first.map(this::indexOf).orElse(-1);
  }

  /**
   * Joins the elements of {@code CList} into a single String containing the provided elements.
   *
   * <p>No delimiter is added before or after the list.
   *
   * <p>See the examples here: {@see #join(String)}.
   *
   * @param fromIndex low endpoint (inclusive) of the subList
   * @param toIndex   high endpoint (exclusive) of the subList
   * @return the joined String with no separator
   */
  public String join(int fromIndex, int toIndex) {
    return join(fromIndex, toIndex, CStringUtil.EMPTY);
  }

  /**
   * Joins the elements of {@code CList} into a single String containing the provided elements.
   *
   * <p>No delimiter is added before or after the list. A {@code null} separator is the same as an
   * empty String (CStringUtil.EMPTY).
   *
   * <p>See the examples here: {@see #join(String)}.
   *
   * @param fromIndex low endpoint (inclusive) of the subList
   * @param toIndex   high endpoint (exclusive) of the subList
   * @param separator the separator character to use, null treated as CStringUtil.EMPTY
   * @return the joined String separated by separator
   */
  public String join(int fromIndex, int toIndex, String separator) {
    return CStringUtil.join(subList(fromIndex, toIndex), separator);
  }

  public Stream<E> stream() {
    return super.stream();
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(Object c) {
    return c instanceof Collection collection
        && collection.size() == size()
        && containsAll(collection)
        && collection.containsAll(this);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return join(", ");
  }

}
