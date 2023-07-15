package org.catools.atlassian.scale.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.atlassian.scale.rest.cycle.CZScaleExecutionStatus;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CZScaleScriptResult {
  private Integer index;
  private CZScaleExecutionStatus status;
  private String testData;
  private String expectedResult;
  private String description;
  private String comment;
}
