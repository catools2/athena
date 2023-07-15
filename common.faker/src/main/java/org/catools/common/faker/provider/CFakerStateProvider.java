package org.catools.common.faker.provider;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.catools.common.faker.model.CRandomCities;
import org.catools.common.faker.model.CRandomCity;
import org.catools.common.faker.model.CRandomState;
import org.catools.common.utils.CIterableUtil;

@Data
@AllArgsConstructor
public class CFakerStateProvider {
  private final CRandomState state;
  private final CRandomCities cities;

  public CRandomCity getRandomCity() {
    return CIterableUtil.getRandom(cities);
  }
}
