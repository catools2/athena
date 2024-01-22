package org.catools.athena.tms.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TestExecutionDto implements Serializable {

  private Long id;
  private Instant createdOn;
  private Instant executedOn;
  private String cycle;
  private String item;
  private String status;
  private String executor;
}
