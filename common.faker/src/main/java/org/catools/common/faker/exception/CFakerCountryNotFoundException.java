package org.catools.common.faker.exception;

import org.catools.common.faker.enums.CFakerCountryCode3;

public class CFakerCountryNotFoundException extends RuntimeException {
  public CFakerCountryNotFoundException(String countryCode3) {
    super(
        "Country code "
            + countryCode3
            + " is not a valid ISO3 country code or it is not support at the moment of time. Supporting Countries: "
            + CFakerCountryCode3.values());
  }
}
