package org.catools.ws.rest.tests.api.entities;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Data
public class Movies extends ArrayList<Movie> {
  public Movies() {
  }

  public Movies(Collection<? extends Movie> c) {
    super(c);
  }

  public Movies(int initialCapacity) {
    super(initialCapacity);
  }

  public boolean has(Predicate<Movie> predicate) {
    return stream().anyMatch(predicate);
  }

  public Movies getByTitle(String title) {
    return new Movies(stream().filter(m -> title.equals(m.getTitle())).collect(Collectors.toList()));
  }
}
