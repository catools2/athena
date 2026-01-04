package org.catools.athena.model.tms;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.athena.common.markers.IdRequired;

import java.io.Serializable;
import java.time.Instant;


@Data
@Accessors(chain = true)
public class TestExecutionDto implements Serializable {

  @NotNull(groups = IdRequired.class, message = "The id must be provided.")
  private Long id;

  @NotNull(message = "The execution created date/time must be provided.")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant createdOn;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant executedOn;

  @NotNull(message = "The execution item must be provided.")
  private String item;

  @NotNull(message = "The item status must be provided.")
  private String status;

  private String executor;

}
