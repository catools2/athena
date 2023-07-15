package org.catools.common.faker.provider;

import java.util.HashSet;

public class CFakerStateProviders extends HashSet<CFakerStateProvider> {
  public CFakerStateProviders() {
  }

  public CFakerStateProviders(Iterable<CFakerStateProvider> iterable) {
    super();
    if (iterable != null) {
      iterable.forEach(i -> add(i));
    }
  }
}
