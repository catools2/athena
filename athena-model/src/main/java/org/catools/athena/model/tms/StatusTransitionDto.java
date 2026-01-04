package org.catools.athena.model.tms;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.catools.athena.common.markers.IdRequired;

import java.io.Serializable;
import java.time.Instant;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class StatusTransitionDto implements Serializable {

  @NotNull(groups = IdRequired.class, message = "The id must be provided.")
  private Long id;

  @NotNull(message = "The status transition from status must be provided.")
  private String from;

  @NotNull(message = "The status transition to status must be provided.")
  private String to;

  @NotNull(message = "The status transition author must be provided.")
  private String author;

  @NotNull(message = "The status transition occurred must be provided.")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant occurred;

  public StatusTransitionDto(String from, String to, String author, Instant occurred) {
    this.from = from;
    this.to = to;
    this.author = author;
    this.occurred = occurred;
  }

}
