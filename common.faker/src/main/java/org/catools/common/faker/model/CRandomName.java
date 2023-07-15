package org.catools.common.faker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CRandomName {
  private String firstName;
  private String middleName;
  private String lastName;
  private String prefix;
  private String suffix;
}
