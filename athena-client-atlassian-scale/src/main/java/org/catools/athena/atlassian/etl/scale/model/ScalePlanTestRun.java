package org.catools.athena.atlassian.etl.scale.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.atlassian.etl.scale.utils.CustomDateDeserializer;
import org.catools.athena.atlassian.etl.scale.utils.CustomDateSerializer;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScalePlanTestRun {
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
  private Date plannedStartDate;

  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  private Date plannedEndDate;
  private HashMap<String, String> customFields;
  private HashSet<ScalePlanExecution> items;
}



