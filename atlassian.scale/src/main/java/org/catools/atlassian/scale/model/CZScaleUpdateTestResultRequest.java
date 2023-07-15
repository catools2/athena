package org.catools.atlassian.scale.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.atlassian.scale.rest.cycle.CZScaleExecutionStatus;
import org.catools.atlassian.scale.utils.CustomDateDeserializer;
import org.catools.atlassian.scale.utils.CustomDateSerializer;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CSet;

import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CZScaleUpdateTestResultRequest {
  private CZScaleExecutionStatus status;
  private String environment;
  private String comment;
  private String assignedTo;
  private String executedBy;
  private Integer executionTime;

  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  private Date executionDate;

  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  private Date actualStartDate;

  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  private Date actualEndDate;

  private CHashMap<String, String> customFields;

  private CSet<String> issueLinks;

  private CZScaleScriptResults scriptResults;
}
