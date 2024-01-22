package org.catools.athena.kube.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;


@Data
@Accessors(chain = true)
public class ContainerStateDto {

  private Long id;
  private Instant syncTime;
  private String type;
  private String message;
  private String value;

}
