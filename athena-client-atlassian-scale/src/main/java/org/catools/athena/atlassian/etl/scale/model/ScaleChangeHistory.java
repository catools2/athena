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
import java.util.HashSet;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScaleChangeHistory {
  private Integer id;
  private String source;
  private Integer sourceId;
  private String type;
  private String userKey;

  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  private Date historyDate;
  private HashSet<ScaleChangeHistoryItem> changeHistoryItems;
}
