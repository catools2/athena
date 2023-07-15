package org.catools.common.faker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CRandomCountry {
  private String countryCode2;
  private String countryCode3;
  private String name;
  private String currencyCode;
  private String currencyName;
  private String phonePrefix;
  private String postalCodeFormat;
  private String postalCodeRegex;
}
