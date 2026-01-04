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
public class ScaleTestRun {

  private String key;
  private String name;
  private String issueKey;
  private String description;
  private String version;
  private String projectKey;
  private String owner;

  private String updatedBy;

  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  private Date updatedOn;

  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  private Date createdOn;

  private String createdBy;

  private String folder;
  private String status;

  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  private Date plannedEndDate;

  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  private Date plannedStartDate;

  private Integer issueCount;
  private Integer executionTime;
  private Integer estimatedTime;
  private Integer testCaseCount;

  private HashMap<String, Integer> executionSummary;
  private HashSet<ScaleTestExecution> items;

}