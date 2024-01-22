package org.catools.athena.apispec.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.MetadataDto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ApiPathDto implements Serializable {

  private Long id;
  private Long apiSpecId;
  private String method;
  private String title;
  private String description;
  private String url;
  private Set<ApiParameterDto> parameters = new HashSet<>();
  private Set<MetadataDto> metadata = new HashSet<>();
}
