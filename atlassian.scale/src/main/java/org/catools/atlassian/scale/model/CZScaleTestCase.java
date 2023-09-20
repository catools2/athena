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
import org.catools.common.collections.CList;
import org.catools.common.date.CDate;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CZScaleTestCase {
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
  private CDate updatedOn;

  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  private CDate createdOn;

  private CList<String> labels;
  private CList<String> issueLinks;
  private CZScaleTestScript testScript;
  private CHashMap<String, String> customFields;
  private CZScaleTestCaseParameters parameters;
  private CZScaleChangeHistories histories;
}
