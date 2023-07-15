package org.catools.atlassian.scale.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.common.collections.CList;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CZScaleTestCaseParameters {
  public CList<Object> variables;
  public CList<Object> entries;
}
