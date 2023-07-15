package org.catools.atlassian.scale.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.atlassian.scale.utils.CustomDateDeserializer;
import org.catools.atlassian.scale.utils.CustomDateSerializer;
import org.catools.common.collections.CHashMap;
import org.catools.common.date.CDate;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CZScalePlanTestRun {
  private String projectKey;
  private String testPlanKey;
  private String issueKey;
  private String name;
  private String status;
  private String iteration;
  private String version;
  private String folder;
  private String owner;

  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  private CDate plannedStartDate;

  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  private CDate plannedEndDate;
  private CHashMap<String, String> customFields;
  private CZScalePlanExecutions items;
}



