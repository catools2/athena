package org.catools.athena.atlassian.etl.scale.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.HashSet;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScaleTestScriptStep {
  private int id;
  private int index;
  private String expectedResult;
  private String description;
  private String testData;
  private String testCaseKey;
  private HashSet<ScaleAttachment> attachments;
}
