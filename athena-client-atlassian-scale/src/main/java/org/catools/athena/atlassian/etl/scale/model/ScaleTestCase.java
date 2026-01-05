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
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScaleTestCase {
  private String owner;
  private String updatedBy;
  private String precondition;
  private int majorVersion;
  private int estimatedTime;
  private String priority;
  private String objective;
  private String projectKey;
  private String createdBy;
  private boolean latestVersion;
  private String lastTestResultStatus;
  private String name;
  private String id;
  private String key;
  private String status;
  private String component;
  private String folder;

  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  private Date updatedOn;

  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  private Date createdOn;

  private List<String> labels;
  private List<String> issueLinks;
  private ScaleTestScript testScript;
  private HashMap<String, String> customFields;
  private ScaleTestCaseParameters parameters;
  private HashSet<ScaleChangeHistory> histories;
}
