package org.catools.atlassian.zapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CZApiProject {
  @JsonProperty("value")
  private Long id;

  @JsonProperty("label")
  private String name;
}
