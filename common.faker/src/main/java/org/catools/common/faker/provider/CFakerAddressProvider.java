package org.catools.common.faker.provider;

import lombok.AllArgsConstructor;
import org.catools.common.faker.model.CRandomAddress;
import org.catools.common.faker.model.CRandomCity;
import org.catools.common.faker.model.CRandomStreetInfo;
import org.catools.common.utils.CIterableUtil;

import java.util.Optional;

@AllArgsConstructor
public class CFakerAddressProvider {
  private final CFakerCountryProvider country;
  private final CFakerStreetAddressProvider streetAddressProvider;

  public CRandomAddress getAny() {
    return getAny(CIterableUtil.getRandom(country.getStateProviders()).getState().getCode());
  }

  public CRandomAddress getAny(String stateCode) {
    Optional<CFakerStateProvider> state =
        country.getStateProviders().stream()
            .filter(s -> s.getState().getCode().equalsIgnoreCase(stateCode))
            .findFirst();
    return state.isEmpty() ? null : getAny(stateCode, state.get().getRandomCity().getName());
  }

  public CRandomAddress getAny(String stateCode, String cityname) {
    Optional<CFakerStateProvider> state =
        country.getStateProviders().stream()
            .filter(s -> s.getState().getCode().equalsIgnoreCase(stateCode))
            .findFirst();
    if (state.isEmpty()) return null;

    Optional<CRandomCity> city =
        state.get().getCities().stream()
            .filter(s -> s.getName().equalsIgnoreCase(cityname))
            .findFirst();
    if (city.isEmpty()) return null;

    CRandomStreetInfo streetInfo = streetAddressProvider.getAny();
    return new CRandomAddress(
        country.getCountry(),
        state.get().getState(),
        city.get(),
        streetInfo.getStreetName(),
        streetInfo.getStreetSuffix(),
        streetInfo.getStreetPrefix(),
        streetInfo.getStreetNumber(),
        streetInfo.getBuildingNumber());
  }
}
