package org.catools.common.faker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CRandomStreetInfo {
  private String streetName;
  private String streetNumber;
  private String streetSuffix;
  private String streetPrefix;
  private String buildingNumber;
}
