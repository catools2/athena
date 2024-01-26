package org.catools.athena.tms.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;


@Getter
@Setter
@Accessors(chain = true)
public class StatusTransitionDto implements Serializable {

  private Long id;

  @NotNull(message = "The status transition from status must be provided.")
  private String from;

  @NotNull(message = "The status transition to status must be provided.")
  private String to;

  @NotNull(message = "The status transition occurred must be provided.")
  private Instant occurred;

}
