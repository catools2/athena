package org.catools.common.faker.provider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.common.faker.model.CRandomCountry;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class CFakerCountryProvider {
  private final CRandomCountry country;
  private final CFakerStateProviders stateProviders;
  private final CFakerNameProvider nameProvider;
  private final CFakerCompanyProvider companyProvider;
  private final CFakerAddressProvider addressProvider;

  public CFakerCountryProvider(
      CRandomCountry country,
      CFakerStateProviders stateProviders,
      CFakerNameProvider personNameProvider,
      CFakerCompanyProvider companyProvider,
      CFakerStreetAddressProvider streetAddressProvider) {
    this.country = country;
    this.stateProviders = stateProviders;
    this.nameProvider = personNameProvider;
    this.companyProvider = companyProvider;
    this.addressProvider = new CFakerAddressProvider(this, streetAddressProvider);
  }
}
