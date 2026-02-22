package org.catools.athena.atlassian.etl.scale.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScaleTestCaseParameters {
  public ArrayList<Object> variables;
  public ArrayList<Object> entries;
}
