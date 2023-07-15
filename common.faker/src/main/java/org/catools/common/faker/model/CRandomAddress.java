package org.catools.common.faker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CRandomAddress {
  private CRandomCountry country;
  private CRandomState state;
  private CRandomCity city;

  private String streetName;
  private String streetSuffix;
  private String streetPrefix;

  private String streetNumber;
  private String buildingNumber;
}
