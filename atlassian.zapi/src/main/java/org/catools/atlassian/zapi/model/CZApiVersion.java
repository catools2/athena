package org.catools.atlassian.zapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CZApiVersion {
  @JsonProperty("value")
  private Long id;

  @JsonProperty("label")
  private String name;

  public CZApiVersion(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
