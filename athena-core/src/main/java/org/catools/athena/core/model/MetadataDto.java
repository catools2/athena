package org.catools.athena.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MetadataDto implements NameValuePair {
  private Long id;

  private String name;

  private String value;
}
