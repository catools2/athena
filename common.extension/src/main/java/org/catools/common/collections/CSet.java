package org.catools.common.collections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.catools.common.collections.interfaces.CCollection;
import org.testng.collections.Lists;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSet<E> extends HashSet<E> implements CCollection<E, Collection<E>>, Set<E> {

  public static <C> CSet<C> of(C... c) {
    return new CSet<>(c);
  }

  public static <C> CSet<C> of(final Stream<C> stream) {
    return new CSet<>(stream);
  }

  public static <C> CSet<C> of(final Iterable<C> iterable) {
    return new CSet<>(iterable);
  }

  public CSet() {
    super();
  }

  public CSet(E... c) {
    super(c == null ? Lists.newArrayList() : List.of(c));
  }

  public CSet(final Stream<E> stream) {
    super(stream.collect(Collectors.toSet()));
  }

  public CSet(final Iterable<E> iterable) {
    super();
    if (iterable != null) {
      iterable.forEach(this::add);
    }
  }

  /**
   * get the {@link Collection} of items
   *
   * @return the collection of items to be used in interface
   */
  @Override
  @JsonIgnore
  public Collection<E> _get() {
    return this;
  }

  @Override
  public boolean removeIf(Predicate<? super E> filter) {
    return super.removeIf(filter);
  }

  @Override
  public Stream<E> stream() {
    return super.stream();
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(Object c) {
    return c instanceof Collection
        && ((Collection<E>) c).size() == size()
        && containsAll((Collection<E>) c)
        && ((Collection<E>) c).containsAll(this);
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
