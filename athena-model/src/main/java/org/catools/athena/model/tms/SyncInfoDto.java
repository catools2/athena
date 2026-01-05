package org.catools.athena.model.tms;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.common.markers.IdRequired;

import java.io.Serializable;
import java.time.Instant;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SyncInfoDto implements Serializable {

  @NotNull(groups = IdRequired.class, message = "The id must be provided.")
  private Long id;

  @NotBlank(message = "The sync info project code must be provided.")
  @Size(max = 10, message = "The sync info project code can be at most 15 character.")
  private String project;

  @NotBlank(message = "The sync info action must be provided.")
  @Size(max = 50, message = "The sync info action can be at most 300 character.")
  private String action;

  @NotBlank(message = "The sync info component must be provided.")
  @Size(max = 100, message = "The sync info component can be at most 300 character.")
  private String component;

  @NotNull(message = "The sync info start date/time must be provided.")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant startTime;

  @NotNull(message = "The sync info end date/time must be provided.")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant endTime;

  public SyncInfoDto(String project, String action, String component, Instant startTime, Instant endTime) {
    this.project = project;
    this.action = action;
    this.component = component;
    this.startTime = startTime;
    this.endTime = endTime;
  }

}
